package com.example.chooseimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, addbook, logout, chartbook;
    List<ImageTitle> data_book = new ArrayList<>();
    ArrayAdapter adapter_book;
    private DBImageTitle dbImageTitle;
    public void DocDL(){
        data_book.clear();
        List<ImageTitle> book = dbImageTitle.DocDL();
        if(book != null){
            data_book.addAll(book);
        }
        adapter_book.notifyDataSetChanged();
    }
    EditText editTextImageName, edtTitle, edtAuthor, edtCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbImageTitle = new DBImageTitle(this);
        drawerLayout = findViewById(R.id.drawerLayoutAdd);
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
                redirectActivity(MainActivity.this, ShowListImage.class);
            }
        });
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, MainHello.class);
                UserSession.getInstance().logout();
            }
        });
        chartbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, StatiscalCotnroller.class);
            }
        });

        editTextImageName = findViewById(R.id.editTextImageName);
        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtCategory = findViewById(R.id.edtCategorys);
        Button btnChooseImage = findViewById(R.id.btnChooseImage);
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        Button btnThem = findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageTitle imgtt = new ImageTitle();
                imgtt.setImage(editTextImageName.getText().toString());
                imgtt.setTitleimg(edtTitle.getText().toString());
                imgtt.setAuthor(edtAuthor.getText().toString());
                imgtt.setCategory(edtCategory.getText().toString());
                dbImageTitle.ThemDL(imgtt);
                setResult(RESULT_OK);
                Toast.makeText(MainActivity.this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                editTextImageName.setText("");
                edtTitle.setText("");
                edtAuthor.setText("");
                edtCategory.setText("");
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String imagePath = cursor.getString(columnIndex);
                String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                editTextImageName.setText(imageName);
                saveImageToInternalStorage(uri, imageName);
                cursor.close();
            }
        }
    }


    private void saveImageToInternalStorage(Uri uri, String imageName) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File storageDir = getFilesDir();
            File imageFile = new File(storageDir, imageName);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);

    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }
    public static void redirectActivity(Activity activity, Class secondActivity){
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
}