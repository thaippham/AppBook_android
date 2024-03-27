package com.example.chooseimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainHello extends AppCompatActivity {
    Button btnlogin, btnregister, btnxoa, skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hello);
        setControl();
        setEvent();
    }
    private void setControl(){
        btnlogin = findViewById(R.id.btnmain_login);
        btnregister = findViewById(R.id.btnmain_register);
        skip = findViewById(R.id.skip);
    }

    private void setEvent(){
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainHello.this, MainLogin.class);
                startActivity(intent);
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainHello.this, MainRegister.class);
                startActivity(intent);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainHello.this, MainBook.class);
                startActivity(intent);
            }
        });
    }
}