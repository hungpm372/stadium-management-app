package vn.id.phanminhhung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import vn.id.phanminhhung.R;
import vn.id.phanminhhung.dialogs.LoadingDialog;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtCurrentPass, edtNewPass, edtConfirmPass;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.title_change_password));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

    }

    @Override
    public void onClick(View view) {
        String currentPass = edtCurrentPass.getText().toString().trim();
        String newPass = edtNewPass.getText().toString().trim();
        String confirmPass = edtConfirmPass.getText().toString().trim();

        if (!validatePassword(currentPass, newPass, confirmPass)) {
            showDialogError(getString(R.string.text_password_empty_error));
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showDialogError(getString(R.string.text_password_mismatched_error));
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // re-authenticate
        if (user != null && user.getEmail() != null) {
            dialog.show();
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPass);
            user.reauthenticate(credential)
                    .addOnSuccessListener(unused -> user.updatePassword(newPass)
                            .addOnSuccessListener(unused1 -> {
                                dialog.dismiss();
                                new AlertDialog.Builder(this)
                                        .setTitle("Thông báo")
                                        .setMessage(R.string.text_password_change_success)
                                        .setCancelable(false)
                                        .setPositiveButton(getString(R.string.text_button_close), (dialogInterface, i) -> {
                                            dialogInterface.dismiss();
                                            finish();
                                        })
                                        .show();
                            })
                            .addOnFailureListener(e -> {
                                dialog.dismiss();
                                showDialogError(getString(R.string.text_password_change_error));
                            }))
                    .addOnFailureListener(e -> {
                        dialog.dismiss();
                        showDialogError(getString(R.string.text_password_validate_error));
                    });
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        }

    }

    private void initViews() {
        edtCurrentPass = findViewById(R.id.edt_current_password);
        edtNewPass = findViewById(R.id.edt_new_password);
        edtConfirmPass = findViewById(R.id.edt_confirm_password);
        Button btnSend = findViewById(R.id.btn_send_change_password);
        btnSend.setOnClickListener(this);
        dialog = new LoadingDialog(this);
    }

    private void showDialogError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.text_button_close), (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    private boolean validatePassword(String currentPass, String newPass, String confirmPass) {
        return !(currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}