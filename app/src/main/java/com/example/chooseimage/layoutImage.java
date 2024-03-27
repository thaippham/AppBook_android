package com.example.chooseimage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class layoutImage extends ArrayAdapter<ImageTitle> {
    private final Context context;
    private final List<ImageTitle> imageTitles;
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    private OnDeleteClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }
    public layoutImage(Context context, List<ImageTitle> imageTitles) {
        super(context, R.layout.activity_layout_image, imageTitles);
        this.context = context;
        this.imageTitles = imageTitles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_layout_image, parent, false);

        ImageView imageView = rowView.findViewById(R.id.imageViewItem);
        TextView titleTextView = rowView.findViewById(R.id.tvtitle);
        TextView author = rowView.findViewById(R.id.tvauthor);
        TextView category = rowView.findViewById(R.id.tvcategory);
        ImageView imgremove = rowView.findViewById(R.id.imgremove);
        ImageView imgedit = rowView.findViewById(R.id.imgedit);
        ImageView imgaddchapter = rowView.findViewById(R.id.addchapter);

        ImageTitle currentImageTitle = imageTitles.get(position);
        File imgFile = new File(context.getFilesDir(), currentImageTitle.getImage());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
        titleTextView.setText(currentImageTitle.getTitleimg());
        author.setText("Tác giả: " + currentImageTitle.getAuthor());
        category.setText("Thể loại: " + currentImageTitle.getCategory());
        imgremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc muốn xóa!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (onDeleteClickListener != null) {
                                    onDeleteClickListener.onDeleteClick(position);
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainEdit.class);
                intent.putExtra("item", currentImageTitle);
                ((Activity) context).startActivityForResult(intent, 2);
            }
        });
        imgaddchapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bookId = currentImageTitle.getId();
                Intent intent = new Intent(context, MainAddChapter.class);
                intent.putExtra("BOOK_ID", bookId);
                context.startActivity(intent);
            }
        });
        return rowView;
    }

}