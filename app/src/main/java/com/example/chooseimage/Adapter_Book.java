package com.example.chooseimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class Adapter_Book extends ArrayAdapter<ImageTitle> {
    private final Context context;
    private final List<ImageTitle> imageTitles;
    int resource;

    public Adapter_Book(Context context, int resource, List<ImageTitle> imageTitles) {
        super(context, R.layout.activity_adapter_book, imageTitles);
        this.context = context;
        this.resource = resource;
        this.imageTitles = imageTitles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_adapter_book, parent, false);

        ImageView imageViewbook = rowView.findViewById(R.id.imgvbook);
        TextView titleTextViewbook = rowView.findViewById(R.id.tvnamebook);
        TextView authorbook = rowView.findViewById(R.id.tvauthorbook);
        TextView categorybook = rowView.findViewById(R.id.tvcategorybook);
        ImageView imgFollowBook = rowView.findViewById(R.id.imgfollowbook);

        final ImageTitle currentImageTitle = imageTitles.get(position);
        File imgFile = new File(context.getFilesDir(), currentImageTitle.getImage());

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageViewbook.setImageBitmap(myBitmap);
        }

        titleTextViewbook.setText(currentImageTitle.getTitleimg());
        authorbook.setText("Tác giả: " + currentImageTitle.getAuthor());
        categorybook.setText("Thể loại: " + currentImageTitle.getCategory());
        int currentUserId = UserSession.getInstance().getUserId();
        if (currentUserId == 0) {
            imgFollowBook.setVisibility(View.GONE);
        } else {
            boolean isBookmarked = kiemTraSachDaLuu(currentUserId, currentImageTitle.getId());
            if (isBookmarked) {
                imgFollowBook.setImageResource(R.drawable.bookmark_solid);
            } else {
                imgFollowBook.setImageResource(R.drawable.bookmark_regular);
            }
            imgFollowBook.setVisibility(View.VISIBLE);
        }

        imgFollowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isBookmarked = kiemTraSachDaLuu(currentUserId, currentImageTitle.getId());

                if (isBookmarked) {
                    xoaSachKhoiDanhSachLuu(currentUserId, currentImageTitle.getId());
                    imgFollowBook.setImageResource(R.drawable.bookmark_regular);
                    Toast.makeText(context, "Đã xóa sách khỏi danh sách lưu!", Toast.LENGTH_SHORT).show();
                } else {
                    luuSachVaoDanhSachLuu(currentUserId, currentImageTitle.getId());
                    imgFollowBook.setImageResource(R.drawable.bookmark_solid);
                    Toast.makeText(context, "Đã thêm sách vào danh sách lưu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rowView;
    }
    private boolean kiemTraSachDaLuu(int userId, int bookId) {
        DBImageTitle dbImageTitle = new DBImageTitle(context);
        return dbImageTitle.kiemTraSachDaLuu(userId, bookId);
    }

    private void xoaSachKhoiDanhSachLuu(int userId, int bookId) {
        DBImageTitle dbImageTitle = new DBImageTitle(context);
        dbImageTitle.xoaSachKhoiDanhSachLuu(userId, bookId);
    }

    private void luuSachVaoDanhSachLuu(int userId, int bookId) {
        DBImageTitle dbImageTitle = new DBImageTitle(context);
        dbImageTitle.luuSachVaoDanhSachLuu(userId, bookId);
    }
}