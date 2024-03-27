package com.example.chooseimage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainRead extends AppCompatActivity {

    private int bookId;
    private DBImageTitle db;
    private List<Chapter> chapters;
    private Adapter_Chap adapter;
    int currentUserId = 0;
    Button btnsapxep;
    private boolean isDescending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_read);

        Intent intent = getIntent();
        ImageTitle book = (ImageTitle) intent.getSerializableExtra("SELECTED_BOOK");
        bookId = book.getId();
        db = new DBImageTitle(this);
        chapters = db.getChaptersByBookId(bookId);
        currentUserId = UserSession.getInstance().getUserId();
        adapter = new Adapter_Chap(this, R.layout.activity_adapter_chap, chapters);
        btnsapxep = findViewById(R.id.btnsapxep);

        setEvent();

        initializeViews(book);
        setupListView();
    }
    private void setEvent(){
        ListView listView = findViewById(R.id.lvchapter);
        btnsapxep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDescending){
                    Collections.sort(chapters, new Comparator<Chapter>() {
                        @Override
                        public int compare(Chapter chap1, Chapter chap2) {
                            return Integer.compare(chap1.getChapter(), chap2.getChapter());
                        }
                    });
                    isDescending = false;
                } else {
                    Collections.sort(chapters, new Comparator<Chapter>() {
                        @Override
                        public int compare(Chapter chap1, Chapter chap2) {
                            return Integer.compare(chap2.getChapter(), chap1.getChapter());
                        }
                    });
                    isDescending = true;
                }
                Adapter_Chap adapter = new Adapter_Chap(MainRead.this, R.layout.activity_adapter_chap, chapters);
                listView.setAdapter(adapter);
            }
        });
    }
    private void initializeViews(ImageTitle book) {
        ImageView imgBook = findViewById(R.id.imgbook);
        File imgFile = new File(this.getFilesDir(), book.getImage());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgBook.setImageBitmap(myBitmap);
        }

        TextView tvname = findViewById(R.id.tvname);
        tvname.setText(book.getTitleimg());

        TextView tvauthor = findViewById(R.id.tvauthor);
        tvauthor.setText("Tác giả: " + book.getAuthor());

        TextView tvcategory = findViewById(R.id.tvcategory);
        tvcategory.setText("Thể loại: " + book.getCategory());

        TextView tvviews = findViewById(R.id.tvviews);
        int totalViews = 0;
        for (Chapter chapters : chapters) {
            totalViews += chapters.getViews();
        }
        tvviews.setText(totalViews + " Views");
    }

    private void setupListView() {
        ListView listView = findViewById(R.id.lvchapter);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Chapter selectedChapter = chapters.get(position);
            boolean Readed = kiemTraTrang(currentUserId, selectedChapter.getId());
            if(currentUserId != 0 && !Readed){
                luuTrang(currentUserId, selectedChapter.getId());
            }
            Intent read = new Intent(MainRead.this, ReadChapter.class);
            db.updateChapterViews(selectedChapter.getId(), selectedChapter.getViews() + 1);
            read.putExtra("chap", selectedChapter.getChapter());
            read.putExtra("noidung", selectedChapter.getContent());
            read.putExtra("book_id", bookId);
            startActivity(read);
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private boolean kiemTraTrang(int userId, int chapterId) {
        DBImageTitle dbImageTitle = new DBImageTitle(this);
        return dbImageTitle.kiemTraTrang(userId, chapterId);
    }
    private void luuTrang(int userId, int chapterId) {
        DBImageTitle dbImageTitle = new DBImageTitle(this);
        dbImageTitle.luuTrang(userId, chapterId);
    }
    @Override
    protected void onResume() {
        super.onResume();
        chapters = db.getChaptersByBookId(bookId);
        adapter.clear();
        adapter.addAll(chapters);
        adapter.notifyDataSetChanged();
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
