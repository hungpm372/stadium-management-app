package vn.id.phanminhhung.fragments;

import static android.app.Activity.RESULT_OK;
import static vn.id.phanminhhung.activities.StadiumDetailActivity.DRESSING_ROOM_TYPE;
import static vn.id.phanminhhung.activities.StadiumDetailActivity.HEALTH_STATION_TYPE;
import static vn.id.phanminhhung.activities.StadiumDetailActivity.PLAYGROUND_TYPE;
import static vn.id.phanminhhung.activities.StadiumDetailActivity.STAND_TYPE;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import vn.id.phanminhhung.R;
import vn.id.phanminhhung.activities.FacilityDetailActivity;
import vn.id.phanminhhung.adapters.FacilityImageAdapter;
import vn.id.phanminhhung.dialogs.LoadingDialog;

public class ImageFragment extends Fragment {

    public static final int REQUEST_CODE = 1;
    private FacilityDetailActivity activity;
    private FacilityImageAdapter adapter;
    private LoadingDialog dialog;
    private List<String> images;
    private TextView tvNoImage;

    private ActivityResultLauncher<Intent> launcher;

    private String getFileExtension(Uri uri) {
        ContentResolver resolver = activity.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    }

    private String getFacilityPath(int type) {
        if (type == PLAYGROUND_TYPE) return "playGround";
        else if (type == STAND_TYPE) return "stand";
        else if (type == DRESSING_ROOM_TYPE) return "dressingRoom";
        else if (type == HEALTH_STATION_TYPE) return "healthStation";
        return "media";
    }

    public ImageFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (FacilityDetailActivity) context;
        dialog = new LoadingDialog(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvImages = view.findViewById(R.id.rc_fr_image);
        tvNoImage = view.findViewById(R.id.tv_fm_no_image);
        FloatingActionButton fabAddImage = view.findViewById(R.id.fab_fr_image);

        images = activity.getImages();

        if (images == null) {
            tvNoImage.setVisibility(View.VISIBLE);
            rvImages.setVisibility(View.GONE);
            images = new ArrayList<>();
        }
        adapter = new FacilityImageAdapter(getActivity(), images);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvImages.setLayoutManager(manager);
        rvImages.setAdapter(adapter);

        rvImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    fabAddImage.hide();
                else
                    fabAddImage.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        fabAddImage.setOnClickListener(v -> {
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent == null) return;
                        dialog.show();
                        Uri uri = intent.getData();
                        StorageReference storageReference = FirebaseStorage
                                .getInstance()
                                .getReference(System.currentTimeMillis() + "." + getFileExtension(uri));

                        storageReference.putFile(uri)
                                .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri1 -> {
                                    images.add(uri1.toString());
                                    tvNoImage.setVisibility(View.GONE);
                                    rvImages.setVisibility(View.VISIBLE);
                                    adapter.setData(images);
                                    activity.setImages(images);
                                    FirebaseDatabase
                                            .getInstance()
                                            .getReference("stadiums")
                                            .child(String.valueOf(activity.getStadiumId()))
                                            .child(getFacilityPath(activity.getType()))
                                            .child("images")
                                            .setValue(images)
                                            .addOnSuccessListener(unused -> {
                                                dialog.dismiss();
                                                Toast.makeText(activity, getString(R.string.text_toast_update_success), Toast.LENGTH_SHORT).show();
                                            });
                                }))
                                .addOnFailureListener(e -> {
                                    dialog.dismiss();
                                    Toast.makeText(activity, getString(R.string.text_toast_update_error), Toast.LENGTH_SHORT).show();
                                });
                    }
                });
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ImageFragment.REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(Intent.createChooser(intent, "Chọn hình ảnh"));
    }

}