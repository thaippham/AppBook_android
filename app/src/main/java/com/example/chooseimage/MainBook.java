package com.example.chooseimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainBook extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private SearchView searchView;
    private List<ImageTitle> originalData = new ArrayList<>();
    List<ImageTitle> data_book = new ArrayList<>();
    ArrayAdapter adapter_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_book);
        getSupportActionBar().setTitle("Thư viện sách");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BookFragment bookFragment = new BookFragment();
        fragmentTransaction.replace(R.id.frame_container, bookFragment);
        fragmentTransaction.commit();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                switch (item.getItemId()) {
                    case R.id.homebottom:
                        if (!(currentFragment instanceof BookFragment)) {
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                    .replace(R.id.frame_container, new BookFragment())
                                    .commit();
                            getSupportActionBar().setTitle("Thư viện sách");
                        }
                        return true;
                    case R.id.savedbottom:
                        if (currentFragment instanceof BookFragment) {
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                    .replace(R.id.frame_container, new SavedFragment())
                                    .commit();
                            getSupportActionBar().setTitle("Đã lưu");
                        } else if (currentFragment instanceof AccountFragment) {
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                    .replace(R.id.frame_container, new SavedFragment())
                                    .commit();
                            getSupportActionBar().setTitle("Đã lưu");
                        }
                        return true;
                    case R.id.accountbottom:
                        if (!(currentFragment instanceof AccountFragment)) {
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                    .replace(R.id.frame_container, new AccountFragment())
                                    .commit();
                            getSupportActionBar().setTitle("Trang cá nhân");
                        }
                        return true;
                }
                return false;
            }
        });
    }
}