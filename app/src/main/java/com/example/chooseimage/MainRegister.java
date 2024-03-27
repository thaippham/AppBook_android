package com.example.chooseimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainRegister extends AppCompatActivity {
    private Button btnback, btnregister;
    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText,roleEditText;

    private DBImageTitle dbImageTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
        dbImageTitle = new DBImageTitle(this);
        setControl();
        setEvent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setControl() {
        btnregister = findViewById(R.id.btn_register);
        usernameEditText = findViewById(R.id.edtregiser_username);
        emailEditText = findViewById(R.id.edtregiser_email);
        passwordEditText = findViewById(R.id.edtregiser_password);
        confirmPasswordEditText = findViewById(R.id.edtregister_confirmpassword);
        roleEditText = findViewById(R.id.edtregiser_role);
    }
    private void setEvent(){
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    registerUser();
                }
            }
        });
    }
    private void registerUser(){
        Account acc = new Account();
        String role = "User";
        acc.setUsername(usernameEditText.getText().toString());
        acc.setEmail(emailEditText.getText().toString());
        acc.setPassword(passwordEditText.getText().toString());
        acc.setRole(role);

        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        if (dbImageTitle.isUserRegistered(username, email)) {
            Toast.makeText(MainRegister.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }
        dbImageTitle.ThemDLAccount(acc);
        Toast.makeText(MainRegister.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainRegister.this, MainLogin.class);
        startActivity(intent);
        finish();
    }
    private boolean validateInput() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String role = roleEditText.getText().toString().trim();


        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Vui lòng điền đầy đủ thông tin");
            return false;
        } else if (!isValidEmail(email)) {
            showToast("Định dạng email không hợp lệ");
            return false;
        } else if (!password.equals(confirmPassword)) {
            showToast("Mật khẩu không khớp");
            return false;
        }
        return true;
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}