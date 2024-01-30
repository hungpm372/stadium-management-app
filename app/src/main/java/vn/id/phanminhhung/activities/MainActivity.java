package vn.id.phanminhhung.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.id.phanminhhung.R;
import vn.id.phanminhhung.adapters.StadiumAdapter;
import vn.id.phanminhhung.dialogs.LoadingDialog;
import vn.id.phanminhhung.models.Stadium;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private static final int UPDATE_STADIUM_CODE = 1;
    private static final int ADD_STADIUM_CODE = 2;
    private RecyclerView rvStadiumList;
    private SearchView svStadium;
    private ImageView ivAvatar;
    private FloatingActionButton fabAdd;
    private TextView tvName;
    private List<Stadium> stadiums, fixedStadiums;
    private StadiumAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        initViews();

        stadiums = new ArrayList<>();
        fixedStadiums = new ArrayList<>();

        adapter = new StadiumAdapter(this, stadiums, stadium -> {
            svStadium.clearFocus();
            Intent intent = new Intent(this, StadiumDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("stadium", stadium);
            intent.putExtras(bundle);
            startActivityForResult(intent, UPDATE_STADIUM_CODE);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvStadiumList.setLayoutManager(layoutManager);
        rvStadiumList.setAdapter(adapter);

        if (savedInstanceState != null) {
            List<Stadium> savedStadiums = (List<Stadium>) savedInstanceState.getSerializable("stadiums");
            if (savedStadiums != null) {
                stadiums.addAll(savedStadiums);
                fixedStadiums.addAll(savedStadiums);
                adapter.setStadiums(stadiums);
            } else {
                getData();
            }
        } else {
            getData();
        }

        svStadium.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Stadium> newList = new ArrayList<>();
                fixedStadiums.forEach(stadium -> {
                    if (stadium.getName().toLowerCase().contains(s.toLowerCase()))
                        newList.add(stadium);
                });
                adapter.setStadiums(newList);
                return true;
            }
        });

        ivAvatar.setOnClickListener(v -> {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
            View viewSheet = getLayoutInflater().inflate(R.layout.bottom_sheet_main, null, false);

            LinearLayout llChangePass = viewSheet.findViewById(R.id.ll_change_password_sheet);
            Button btnLogout = viewSheet.findViewById(R.id.btn_logout_sheet);

            llChangePass.setOnClickListener(view -> {
                sheetDialog.dismiss();
                startActivity(new Intent(this, ChangePasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });

            btnLogout.setOnClickListener(view -> {
                sheetDialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });

            sheetDialog.setContentView(viewSheet);
            sheetDialog.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnimation;
            sheetDialog.show();
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String avatarUrl = String.valueOf(user.getPhotoUrl());
        email = email == null || email.isEmpty() ? "hung@gmail.com" : email;

        tvName.setText("Xin chào " + email);
        Glide.with(this).load(avatarUrl).error(R.drawable.default_avatar).into(ivAvatar);

        refreshLayout.setOnRefreshListener(() -> {
            stadiums.clear();
            fixedStadiums.clear();

            getData();
        });

        rvStadiumList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    fabAdd.hide();
                else
                    fabAdd.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddStadiumActivity.class);
            startActivityForResult(intent, ADD_STADIUM_CODE);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("stadiums", (Serializable) stadiums);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == UPDATE_STADIUM_CODE && resultCode == Activity.RESULT_OK) {
                Stadium stadium = (Stadium) data.getSerializableExtra("stadium");
                stadiums.set(stadium.getId(), stadium);
                fixedStadiums.set(stadium.getId(), stadium);
                adapter.notifyItemChanged(stadium.getId());
            } else if (requestCode == ADD_STADIUM_CODE && resultCode == Activity.RESULT_OK) {
                refreshLayout.setRefreshing(true);
                stadiums.clear();
                fixedStadiums.clear();
                getData();
            }
        }
    }

    private void getData() {
        dialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("stadiums");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(dataSnapshot -> stadiums.add(dataSnapshot.getValue(Stadium.class)));
                adapter.setStadiums(stadiums);
                fixedStadiums.addAll(stadiums);
                dialog.dismiss();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        rvStadiumList = findViewById(R.id.rv_stadium_list_main);
        rvStadiumList.setHasFixedSize(true);
        rvStadiumList.setItemViewCacheSize(5);
        svStadium = findViewById(R.id.sv_search_main);
        ivAvatar = findViewById(R.id.iv_avatar_main);
        fabAdd = findViewById(R.id.fab_add_stadium_main);
        tvName = findViewById(R.id.tv_name_main);
        refreshLayout = findViewById(R.id.srl_main);
        refreshLayout.setColorSchemeResources(R.color.main);
        dialog = new LoadingDialog(this);
    }
}