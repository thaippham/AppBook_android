package com.example.chooseimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainAddChapter extends AppCompatActivity {
    private EditText trangEditText;
    private List<Chapter> chapters;
    private DBImageTitle db;
    private EditText noidungEditText;
    private Button themchapButton;
    int bookId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_chapter);
        db = new DBImageTitle(this);
        trangEditText = findViewById(R.id.trang);
        noidungEditText = findViewById(R.id.noidung);
        themchapButton = findViewById(R.id.themchap);
        bookId = getIntent().getIntExtra("BOOK_ID", -1);
        chapters = db.getChaptersByBookId(bookId);
        int chap = chapters.size();
        trangEditText.setText(String.valueOf(chap + 1));
        themchapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themChapVaoDatabase();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void themChapVaoDatabase() {
        String trang = trangEditText.getText().toString().trim();
        String noiDung = noidungEditText.getText().toString().trim();

        if (TextUtils.isEmpty(trang) || TextUtils.isEmpty(noiDung)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        Chapter chapter = new Chapter();
        chapter.setBook_id(bookId);
        chapter.setChapter(Integer.parseInt(trang));
        chapter.setViews(0);
        chapter.setContent(noiDung);
        DBImageTitle db = new DBImageTitle(this);
        db.ThemChapter(chapter);
        Toast.makeText(this, "Đã thêm chương!", Toast.LENGTH_SHORT).show();
        finish();
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