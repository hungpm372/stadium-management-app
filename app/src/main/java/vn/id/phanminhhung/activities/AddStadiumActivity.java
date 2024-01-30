package vn.id.phanminhhung.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator3;
import vn.id.phanminhhung.R;
import vn.id.phanminhhung.adapters.StadiumImageAdapter;
import vn.id.phanminhhung.dialogs.LoadingDialog;
import vn.id.phanminhhung.models.DressingRoom;
import vn.id.phanminhhung.models.HealthStation;
import vn.id.phanminhhung.models.Media;
import vn.id.phanminhhung.models.PlayGround;
import vn.id.phanminhhung.models.Stadium;
import vn.id.phanminhhung.models.Stand;

public class AddStadiumActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 1;
    private EditText edtName, edtSize, edtCapacity, edtRepair, edtAddress;
    private RelativeLayout rlUploadImages;
    private CardView cvImages;
    private ViewPager2 vpStadiumImages;
    private CircleIndicator3 indicator;
    private int stadiumId;
    private List<String> images;
    private ActivityResultLauncher<Intent> launcher;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stadium);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.title_add_stadium));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
    }

    private void initViews() {
        vpStadiumImages = findViewById(R.id.vp_image_add_stadium);
        indicator = findViewById(R.id.indicator_add_stadium);
        cvImages = findViewById(R.id.cv_list_image_add_stadium);
        edtName = findViewById(R.id.edt_name_add_stadium);
        edtSize = findViewById(R.id.edt_size_add_stadium);
        edtCapacity = findViewById(R.id.edt_capacity_add_stadium);
        edtRepair = findViewById(R.id.edt_repair_add_stadium);
        edtAddress = findViewById(R.id.edt_address_add_stadium);
        rlUploadImages = findViewById(R.id.rl_select_image_add_stadium);
        rlUploadImages.setOnClickListener(this);
        dialog = new LoadingDialog(this);
        Button btnAdd = findViewById(R.id.btn_add_stadium);
        btnAdd.setOnClickListener(this);
        images = new ArrayList<>();
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent intent = result.getData();
                    if (result.getResultCode() == RESULT_OK && intent != null) {
                        if (intent.getClipData() != null) {
                            int count = intent.getClipData().getItemCount();
                            for (int i = 0; i < count; i++)
                                images.add(intent.getClipData().getItemAt(i).getUri().toString());
                        } else if (intent.getData() != null) {
                            images.add(intent.getData().toString());
                        }
                        StadiumImageAdapter adapter = new StadiumImageAdapter(this, images);
                        vpStadiumImages.setAdapter(adapter);
                        indicator.setViewPager(vpStadiumImages);
                        rlUploadImages.setVisibility(View.GONE);
                        cvImages.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_select_image_add_stadium) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        } else if (view.getId() == R.id.btn_add_stadium) {
            String name = edtName.getText().toString().trim();
            String size = edtSize.getText().toString().trim();
            String capacity = edtCapacity.getText().toString().trim();
            String repair = edtRepair.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            if (validateInputs(name, size, capacity, repair, address)) {
                dialog.show();
                Stadium stadium = new Stadium();
                stadium.setName(name);
                stadium.setSize(size);
                stadium.setCapacity(Integer.parseInt(capacity));
                stadium.setRepair(repair);
                stadium.setAddress(address);
                stadium.setPlayGround(new PlayGround());
                stadium.setStand(new Stand());
                stadium.setDressingRoom(new DressingRoom());
                stadium.setHealthStation(new HealthStation());
                stadium.setMedia(new Media());

                FirebaseDatabase.getInstance().getReference("stadiums").orderByKey().limitToLast(1)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.getKey() != null)
                                        stadiumId = Integer.parseInt(dataSnapshot.getKey()) + 1;
                                }
                                List<String> imageUrl = new ArrayList<>();
                                stadium.setId(stadiumId);
                                for (int i = 0; i < images.size(); i++) {
                                    StorageReference storageReference = FirebaseStorage
                                            .getInstance()
                                            .getReference(System.currentTimeMillis() + "." + getFileExtension(Uri.parse(images.get(i))));
                                    storageReference.putFile(Uri.parse(images.get(i)))
                                            .addOnSuccessListener(taskSnapshot ->
                                                    storageReference.getDownloadUrl().addOnSuccessListener(uri1 -> {
                                                        imageUrl.add(uri1.toString());
                                                        if (imageUrl.size() == images.size()) {
                                                            stadium.setImages(imageUrl);
                                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("stadiums/" + stadiumId);
                                                            reference
                                                                    .setValue(stadium)
                                                                    .addOnSuccessListener(unused -> {
                                                                        dialog.dismiss();
                                                                        Toast.makeText(AddStadiumActivity.this, getString(R.string.text_toast_add_success), Toast.LENGTH_SHORT).show();
                                                                        Intent intent = new Intent();
                                                                        setResult(Activity.RESULT_OK, intent);
                                                                        finish();
                                                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                                                    });
                                                        }
                                                    }).addOnFailureListener(e -> {
                                                        dialog.dismiss();
                                                        Toast.makeText(AddStadiumActivity.this, getString(R.string.text_toast_add_error), Toast.LENGTH_SHORT).show();
                                                    }))
                                            .addOnFailureListener(e -> {
                                                dialog.dismiss();
                                                Toast.makeText(AddStadiumActivity.this, getString(R.string.text_toast_add_error), Toast.LENGTH_SHORT).show();
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                dialog.dismiss();
                                Toast.makeText(AddStadiumActivity.this, getString(R.string.text_toast_add_error), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    }

    private boolean validateInputs(String name, String size, String capacity, String repair, String address) {
        if (images.size() == 0) {
            return false;
        }

        if (name.isEmpty()) {
            edtName.setError(getString(R.string.text_name_error));
            return false;
        }

        if (size.isEmpty()) {
            edtSize.setError(getString(R.string.text_size_error));
            return false;
        }

        if (capacity.isEmpty()) {
            edtCapacity.setError(getString(R.string.text_capacity_error));
            return false;
        }

        if (repair.isEmpty()) {
            edtRepair.setError(getString(R.string.text_repair_error));
            return false;
        }

        if (address.isEmpty()) {
            edtAddress.setError(getString(R.string.text_address_error));
            return false;
        }

        return true;
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(Intent.createChooser(intent, "Chọn hình ảnh"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }
}