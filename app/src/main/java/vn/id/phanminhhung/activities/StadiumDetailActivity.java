package vn.id.phanminhhung.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator3;
import vn.id.phanminhhung.R;
import vn.id.phanminhhung.adapters.StadiumImageAdapter;
import vn.id.phanminhhung.dialogs.LoadingDialog;
import vn.id.phanminhhung.models.Stadium;

public class StadiumDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int PLAYGROUND_TYPE = 0;
    public static final int STAND_TYPE = 1;
    public static final int DRESSING_ROOM_TYPE = 2;
    public static final int HEALTH_STATION_TYPE = 3;
    public static final int MEDIA_TYPE = 4;
    private static final int SLIDE_DURATION = 4000;
    private static final int EDIT_STADIUM_CODE = 0;
    private ViewPager2 vpStadiumImages;
    private CircleIndicator3 indicator;
    private TextView tvStadiumName, tvStadiumSize, tvStadiumCapacity, tvStadiumAddress, tvStadiumStatus, tvStadiumRepair;
    private ImageView ivStadiumOption;
    private List<LinearLayout> listFacility;
    private final Handler handler = new Handler();
    private int currentPage = 0;
    private Stadium stadium;
    private LoadingDialog dialog;
    private String[] statusStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium_detail);

        initViews();

        Bundle bundle = getIntent().getExtras();
        stadium = (Stadium) bundle.getSerializable("stadium");

        Objects.requireNonNull(getSupportActionBar()).setTitle(stadium.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StadiumImageAdapter adapter = new StadiumImageAdapter(this, stadium.getImages());
        vpStadiumImages.setAdapter(adapter);
        indicator.setViewPager(vpStadiumImages);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage++;
                if (currentPage == stadium.getImages().size()) {
                    currentPage = 0;
                }
                vpStadiumImages.setCurrentItem(currentPage);
                handler.postDelayed(this, SLIDE_DURATION);
            }
        }, SLIDE_DURATION);

        setData();
        statusStrings = getResources().getStringArray(R.array.stadium_status);

        listFacility.forEach(linearLayout -> linearLayout.setOnClickListener(this));

        ivStadiumOption.setOnClickListener(view -> handleClickStadiumOption());

    }

    @SuppressWarnings("deprecation")
    private void handleClickStadiumOption() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        @SuppressLint("InflateParams") View viewSheet = getLayoutInflater().inflate(R.layout.bottom_sheet_stadium_detail, null, false);

        TextView tvEditInfo = viewSheet.findViewById(R.id.tv_edit_stadium_info_sheet);
        TextView tvEditStatus = viewSheet.findViewById(R.id.tv_edit_stadium_status_sheet);

        tvEditInfo.setOnClickListener(v -> {
            sheetDialog.dismiss();
            Intent intent = new Intent(this, EditStadiumActivity.class);
            intent.putExtra("stadium", stadium);
            startActivityForResult(intent, EDIT_STADIUM_CODE);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        tvEditStatus.setOnClickListener(v -> {
            sheetDialog.dismiss();
            dialog.show();
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_status_title_stadium_detail))
                    .setCancelable(false)
                    .setSingleChoiceItems(statusStrings, getCurrentStatusIndex(), (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        stadium.setStatus(statusStrings[i]);
                        tvStadiumStatus.setText(String.format("Tình trạng: %s", stadium.getStatus().toLowerCase()));
                        FirebaseDatabase.getInstance().getReference("stadiums/" + stadium.getId() + "/status")
                                .setValue(stadium.getStatus())
                                .addOnSuccessListener(unused -> {
                                    dialog.dismiss();
                                    Toast.makeText(this, getString(R.string.text_toast_update_success), Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> dialog.dismiss());
                    })
                    .show();
        });

        sheetDialog.setContentView(viewSheet);
        sheetDialog.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnimation;
        sheetDialog.show();
    }

    private int getCurrentStatusIndex() {
        for (int i = 0; i < statusStrings.length; i++) {
            if (statusStrings[i].equalsIgnoreCase(stadium.getStatus()))
                return i;
        }
        return 0;
    }

    private void setData() {
        tvStadiumName.setText(stadium.getName());
        tvStadiumSize.setText(String.format("Kích thước: %s", stadium.getSize()));
        tvStadiumCapacity.setText(String.format(Locale.getDefault(), "Sức chứa: %d", stadium.getCapacity()));
        tvStadiumAddress.setText(String.format("Địa chỉ: %s", stadium.getAddress()));
        tvStadiumStatus.setText(String.format("Tình trạng: %s", stadium.getStatus().toLowerCase()));
        tvStadiumRepair.setText(String.format("Sữa chữa lại: %s", stadium.getRepair()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_STADIUM_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                stadium = (Stadium) data.getSerializableExtra("stadium");
            }
            setData();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("stadium", stadium);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private void initViews() {
        vpStadiumImages = findViewById(R.id.vp_image_stadium_detail);
        indicator = findViewById(R.id.indicator);
        ivStadiumOption = findViewById(R.id.iv_stadium_option);
        tvStadiumName = findViewById(R.id.tv_stadium_name);
        tvStadiumSize = findViewById(R.id.tv_stadium_size);
        tvStadiumCapacity = findViewById(R.id.tv_stadium_capacity);
        tvStadiumAddress = findViewById(R.id.tv_stadium_address);
        tvStadiumStatus = findViewById(R.id.tv_stadium_status);
        tvStadiumRepair = findViewById(R.id.tv_stadium_repair);
        listFacility = new ArrayList<>();
        listFacility.add(findViewById(R.id.ll_playground_stadium_detail));
        listFacility.add(findViewById(R.id.ll_stand_stadium_detail));
        listFacility.add(findViewById(R.id.ll_dressing_room_stadium_detail));
        listFacility.add(findViewById(R.id.ll_health_station_stadium_detail));
        listFacility.add(findViewById(R.id.ll_media_stadium_detail));
        dialog = new LoadingDialog(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, FacilityDetailActivity.class);
        Bundle bundle = new Bundle();
        int id = view.getId();
        if (id == R.id.ll_playground_stadium_detail) {
            bundle.putInt("type", PLAYGROUND_TYPE);
            bundle.putSerializable("facility", stadium.getPlayGround());
        } else if (id == R.id.ll_stand_stadium_detail) {
            bundle.putInt("type", STAND_TYPE);
            bundle.putSerializable("facility", stadium.getStand());
        } else if (id == R.id.ll_dressing_room_stadium_detail) {
            bundle.putInt("type", DRESSING_ROOM_TYPE);
            bundle.putSerializable("facility", stadium.getDressingRoom());
        } else if (id == R.id.ll_health_station_stadium_detail) {
            bundle.putInt("type", HEALTH_STATION_TYPE);
            bundle.putSerializable("facility", stadium.getHealthStation());
        } else if (id == R.id.ll_media_stadium_detail) {
            bundle.putInt("type", MEDIA_TYPE);
            bundle.putSerializable("facility", stadium.getMedia());
        }
        bundle.putInt("stadiumId", stadium.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}