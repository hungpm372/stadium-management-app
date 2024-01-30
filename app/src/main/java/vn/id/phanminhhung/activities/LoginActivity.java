package vn.id.phanminhhung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import vn.id.phanminhhung.R;
import vn.id.phanminhhung.dialogs.LoadingDialog;

public class LoginActivity extends AppCompatActivity {

    // Khai báo biến cho các thành phần giao diện
    private EditText edtEmail, edtPass;
    private TextView tvForgotPass;
    private Button btnLogin;
    private FirebaseAuth auth;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ẩn thanh ActionBar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Khởi tạo các thành phần giao diện
        initViews();

        // Khởi tạo đối tượng FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Xử lý sự kiện khi nhấn phím Enter trên bàn phím ảo khi ở ô nhập mật khẩu
        edtPass.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_GO) {
                return btnLogin.performClick();
            }
            return false;
        });

        // Xử lý sự kiện khi nhấn vào "Quên mật khẩu"
        tvForgotPass.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Xử lý sự kiện khi nhấn vào nút "Đăng nhập"
        btnLogin.setOnClickListener(view -> {
            String email = edtEmail.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();

            // Kiểm tra úng định dạng của địa chỉ email và mật khẩu
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || pass.isEmpty()) {
                // Hiển thị thông báo nếu thông tin không hợp lệ
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Thông tin không hợp lệ")
                        .setCancelable(false)
                        .setPositiveButton("Đóng", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                return;
            }

            // Hiển thị hộp thoại loading
            dialog.show();

            // Thực hiện đăng nhập bằng email và mật khẩu
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
// Chuyển sang màn hình MainActivity nếu đăng nhập thành công
                    startActivity(new Intent(this, MainActivity.class));
                    dialog.dismiss();
                    finish();
                } else {
                    // Hiển thị thông báo nếu đăng nhập thất bại
                    dialog.dismiss();
                    new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Sai thông tin đăng nhập")
                            .setCancelable(false)
                            .setPositiveButton("Đóng", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                }
            });
        });

    }

    // Phương thức khởi tạo các thành phần giao diện
    private void initViews() {
        edtEmail = findViewById(R.id.edit_email_login);
        edtPass = findViewById(R.id.edt_password_login);
        tvForgotPass = findViewById(R.id.tv_forgot_password_login);
        btnLogin = findViewById(R.id.btn_login);
        dialog = new LoadingDialog(this);
    }
}