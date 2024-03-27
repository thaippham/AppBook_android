package com.example.chooseimage;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ShowListImage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, addbook, logout, chartbook;

    private DBImageTitle dbImageTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_image);
        dbImageTitle = new DBImageTitle(this);
        ListView listView = findViewById(R.id.listImage);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        addbook = findViewById(R.id.addbook);
        chartbook = findViewById(R.id.chartbook);
        logout = findViewById(R.id.logout);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ShowListImage.this, MainActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ShowListImage.this, MainHello.class);
                UserSession.getInstance().logout();
            }
        });
        chartbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ShowListImage.this, StatiscalCotnroller.class);
            }
        });
        List<ImageTitle> imageTitles = dbImageTitle.DocDL();
        layoutImage adapter = new layoutImage(this, imageTitles);
        listView.setAdapter(adapter);
        adapter.setOnDeleteClickListener(new layoutImage.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                ImageTitle bookToDelete = imageTitles.get(position);
                dbImageTitle.XoaDLById(bookToDelete.getId());
                imageTitles.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);

    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }
    public static void redirectActivity(Activity activity,Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                updateListViewData();
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            updateListViewData();
        }
    }
    private void updateListViewData() {
        List<ImageTitle> updatedImageTitles = dbImageTitle.DocDL();
        layoutImage adapter = new layoutImage(this, updatedImageTitles);
        ListView listView = findViewById(R.id.listImage);
        listView.setAdapter(adapter);
        adapter.setOnDeleteClickListener(new layoutImage.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                ImageTitle bookToDelete = updatedImageTitles.get(position);
                dbImageTitle.XoaDLById(bookToDelete.getId());
                updatedImageTitles.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }
}