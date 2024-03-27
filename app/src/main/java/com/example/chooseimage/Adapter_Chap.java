package com.example.chooseimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chooseimage.Chapter;
import com.example.chooseimage.R;

import java.util.List;

public class Adapter_Chap extends ArrayAdapter {
    Context context;
    int resource;
    List<Chapter> data;
    public Adapter_Chap(@NonNull Context context, int resource, List<Chapter> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @NonNull View converView, @NonNull ViewGroup parent){
        converView = LayoutInflater.from(context).inflate(resource, null);
        LinearLayout lnlinfo = converView.findViewById(R.id.info_chap);
        TextView tvtrang = converView.findViewById(R.id.tvtrang);
        TextView tvluotxem = converView.findViewById(R.id.tvluotxem);
        Chapter chapters = data.get(position);
        int currentUserId = UserSession.getInstance().getUserId();
        if (currentUserId == 0) {
            tvtrang.setTextColor(Color.WHITE);
            tvluotxem.setTextColor(Color.WHITE);
        } else {
            boolean Readed = kiemTraTrang(currentUserId, chapters.getId());
            if (Readed) {
                tvtrang.setTextColor(Color.GRAY);
                tvluotxem.setTextColor(Color.GRAY);
            } else {
                tvtrang.setTextColor(Color.WHITE);
                tvluotxem.setTextColor(Color.WHITE);
            }
        }
        if(data != null && position >= 0 && position < data.size()){
            Chapter ct = data.get(position);
            if(ct != null){
                int chapter = ct.getChapter();
                tvtrang.setText("Trang " + String.valueOf(chapter));
                int viewer = ct.getViews();
                tvluotxem.setText(String.valueOf(viewer) + " views");
            }
        }
        return converView;
    }
    private boolean kiemTraTrang(int userId, int chapterId) {
        DBImageTitle dbImageTitle = new DBImageTitle(context);
        return dbImageTitle.kiemTraTrang(userId, chapterId);
    }
}