package com.example.chooseimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainEdit extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button btndoianh, btnsua, btnthoat;
    EditText edtImageB, edtNameB, edtAuthorB, edtCateB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_edit);
        setControl();
        setEvent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setControl(){
        btndoianh = findViewById(R.id.btndoianh);
        btnsua = findViewById(R.id.btnsua);
        btnthoat = findViewById(R.id.btnthoat);
        edtImageB = findViewById(R.id.edtImageB);
        edtNameB = findViewById(R.id.edtNameB);
        edtAuthorB = findViewById(R.id.edtAuthorB);
        edtCateB = findViewById(R.id.edtCateB);
    }
    private void setEvent(){
        ImageTitle sp =(ImageTitle) getIntent().getSerializableExtra("item");
        edtImageB.setText(sp.getImage());
        edtNameB.setText(sp.getTitleimg());
        edtAuthorB.setText(sp.getAuthor());
        edtCateB.setText(sp.getCategory());
        btndoianh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newImage = edtImageB.getText().toString();
                String newName = edtNameB.getText().toString();
                String newAuthor = edtAuthorB.getText().toString();
                String newCategory = edtCateB.getText().toString();
                ImageTitle update = new ImageTitle(
                        sp.getId(),
                        newImage,
                        newName,
                        newAuthor,
                        newCategory
                );
                DBImageTitle db = new DBImageTitle(MainEdit.this);
                db.CapNhatDL(update);
                Toast.makeText(MainEdit.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
            }
        });
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainEdit.this, ShowListImage.class);
                startActivity(intent);
                finish();
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
                edtImageB.setText(imageName);
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

}