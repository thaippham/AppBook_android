package com.example.chooseimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainLogin extends AppCompatActivity {
    Button btnlogin;
    CheckBox cbghinhomk;
    EditText edtusername, edtpw;
    DBImageTitle dbImageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        dbImageTitle = new DBImageTitle(this);

        setControl();
        setEvent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean remember = preferences.getBoolean("remember", false);
        if (remember) {
            String savedUsername = preferences.getString("username", "");
            String savedPassword = preferences.getString("password", "");

            edtusername.setText(savedUsername);
            edtpw.setText(savedPassword);
            cbghinhomk.setChecked(true);
        }
    }
    private void setControl(){
        btnlogin = findViewById(R.id.btn_login);
        cbghinhomk = findViewById(R.id.cbghinho);
        edtusername = findViewById(R.id.edtlogin_username);
        edtpw = findViewById(R.id.edtlogin_password);
    }
    private void setEvent(){

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtusername.getText().toString().trim();
                String password = edtpw.getText().toString().trim();
                SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", MODE_PRIVATE).edit();

                if (cbghinhomk.isChecked()) {
                    editor.putBoolean("remember", true);
                    editor.putString("username", username);
                    editor.putString("password", password);
                } else {
                    editor.clear();
                }

                editor.apply();
                if (dbImageTitle.kiemTraDangNhap(username, password)) {
                    int userId = dbImageTitle.getIdByUsername(username);
                    UserSession userSession = UserSession.getInstance();
                    userSession.setUserId(userId);
                    String role = dbImageTitle.getRoleByUsername(username);
                    if ("Admin".equals(role)) {
                        Intent intent = new Intent(MainLogin.this, ShowListImage.class);
                        startActivity(intent);
                        finish();
                    } else if ("User".equals(role)) {
                        Intent intent = new Intent(MainLogin.this, MainBook.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(MainLogin.this, "Thông tin đăng nhập không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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