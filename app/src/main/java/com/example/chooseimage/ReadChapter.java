package com.example.chooseimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ReadChapter extends AppCompatActivity {
    DBImageTitle db;
    BottomNavigationView bottomNavigationView;
    int currentUserId = 0;
    TextView tvChap, tvNoidung;
    private int currentProgress = 20;
    private int currentChapter = 0;
    private int maxChapter;
    private int bookId;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_chapter);
        db = new DBImageTitle(this);
        int chap = getIntent().getIntExtra("chap", 0);
        String noidung = getIntent().getStringExtra("noidung");
        bookId = getIntent().getIntExtra("book_id", 0);
        currentUserId = UserSession.getInstance().getUserId();
        tvChap = findViewById(R.id.tvtrangdoc);
        tvNoidung = findViewById(R.id.tvnoidung);
        currentChapter = chap;
        maxChapter = db.getMaxChapterByBookId(bookId);
        tvNoidung.setTextSize(20);
        tvChap.setText("Trang " + chap);
        tvNoidung.setText(noidung);
        bottomNavigationView = findViewById(R.id.menu_setting);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.next:
                        if (currentChapter < maxChapter) {
                            currentChapter++;
                            navigateToChapter(currentChapter);
                        }else{
                            Toast.makeText(ReadChapter.this, "Bạn đang đọc cuối trang rồi!", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.settingbtn:
                        showBottomDialog();
                        return true;
                    case R.id.previous:
                        if (currentChapter > 1) {
                            currentChapter--;
                            navigateToChapter(currentChapter);
                        }else{
                            Toast.makeText(ReadChapter.this, "Đây là trang 1 và không có trang 0!", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
        tvNoidung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i == 2){
                            if (bottomNavigationView.getVisibility() == View.GONE){
                                bottomNavigationView.setVisibility(View.VISIBLE);
                            }
                            else{
                                bottomNavigationView.setVisibility(View.GONE);
                            }
                        }
                        i = 0;
                    }
                }, 500);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    private void showBottomDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottomsheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        SeekBar seekBarVolume = bottomSheetView.findViewById(R.id.seekBarVolume);
        seekBarVolume.setProgress(currentProgress);
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvNoidung.setTextSize(progress);
                currentProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        bottomSheetDialog.show();
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void navigateToChapter(int chapter) {
        Chapter selectedChapter = db.getChapterByBookIdAndChapter(bookId, chapter);
        if (selectedChapter != null) {
            tvChap.setText("Trang " + selectedChapter.getChapter());
            tvNoidung.setText(selectedChapter.getContent());
            int chapterId = selectedChapter.getId();
            int newViews = selectedChapter.getViews() + 1;
            db.updateChapterViews(chapterId, newViews);

            if(currentUserId != 0 && !kiemTraTrang(currentUserId, chapterId)){
                luuTrang(currentUserId, chapterId);
            }
        }
    }
    private boolean kiemTraTrang(int userId, int chapterId) {
        DBImageTitle dbImageTitle = new DBImageTitle(this);
        return dbImageTitle.kiemTraTrang(userId, chapterId);
    }
    private void luuTrang(int userId, int chapterId) {
        DBImageTitle dbImageTitle = new DBImageTitle(this);
        dbImageTitle.luuTrang(userId, chapterId);
    }
}
