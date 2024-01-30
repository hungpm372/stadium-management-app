package vn.id.phanminhhung.activities;

import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import vn.id.phanminhhung.R;
import vn.id.phanminhhung.dialogs.LoadingDialog;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnSend;
    private FirebaseAuth auth;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();

        auth = FirebaseAuth.getInstance();

        btnSend.setOnClickListener(view -> {
            String email = edtEmail.getText().toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
                showDialogError(getString(R.string.text_email_invalid));
                return;
            }

            dialog.show();

            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    showDialogSuccess();
                } else
                    showDialogError(getString(R.string.text_send_email_error));
            });

        });

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.forgot_password));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return true;
    }

    private void showDialogSuccess() {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(R.string.text_send_email_success)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.text_button_close), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    finish();
                })
                .show();
    }

    private void showDialogError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.text_button_close), (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edt_email_forgot_password);
        btnSend = findViewById(R.id.btn_send_forgot_password);
        dialog = new LoadingDialog(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}