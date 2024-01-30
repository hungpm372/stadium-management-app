package vn.id.phanminhhung.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.Objects;

import vn.id.phanminhhung.R;
import vn.id.phanminhhung.dialogs.LoadingDialog;
import vn.id.phanminhhung.models.Stadium;

public class EditStadiumActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtSize, edtCapacity, edtRepair, edtAddress;
    private LoadingDialog dialog;
    private Stadium stadium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stadium);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.title_update_stadium));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stadium = (Stadium) getIntent().getSerializableExtra("stadium");
        if (stadium == null) return;

        initViews();

        setData(stadium);

    }

    private void setData(Stadium stadium) {
        edtName.setText(stadium.getName());
        edtSize.setText(stadium.getSize());
        edtCapacity.setText(String.format(Locale.getDefault(), "%d", stadium.getCapacity()));
        edtRepair.setText(stadium.getRepair());
        edtAddress.setText(stadium.getAddress());
    }

    private void initViews() {
        edtName = findViewById(R.id.edt_name_edit_stadium);
        edtSize = findViewById(R.id.edt_size_edit_stadium);
        edtCapacity = findViewById(R.id.edt_capacity_edit_stadium);
        edtRepair = findViewById(R.id.edt_repair_edit_stadium);
        edtAddress = findViewById(R.id.edt_address_edit_stadium);
        Button btnUpdate = findViewById(R.id.btn_update_info_edit_stadium);
        dialog = new LoadingDialog(this);

        btnUpdate.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return true;
    }

    private boolean validateInputs(String name, String size, String capacity, String repair, String address) {

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

    @Override
    public void onClick(View view) {
        String name = edtName.getText().toString().trim();
        String size = edtSize.getText().toString().trim();
        String capacity = edtCapacity.getText().toString().trim();
        String repair = edtRepair.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        if (validateInputs(name, size, capacity, repair, address)) {
            dialog.show();

            stadium.setName(name);
            stadium.setSize(size);
            stadium.setCapacity(Integer.parseInt(capacity));
            stadium.setRepair(repair);
            stadium.setAddress(address);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("stadiums/" + stadium.getId());
            reference.setValue(stadium)
                    .addOnSuccessListener(unused -> {
                        dialog.dismiss();
                        Toast.makeText(this, getString(R.string.text_toast_update_success), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("stadium", stadium);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    })
                    .addOnFailureListener(e -> dialog.dismiss());

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}