package com.example.chooseimage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatiscalCotnroller extends AppCompatActivity {

    private List<StaticcalEntity> xvalue= new ArrayList<>();
    private List<String> xvalue1= new ArrayList<>();
    private DBImageTitle myDB;
    //widgets
    private EditText idEdit, nameEdit, monthEdit , viewEdit;
    LinearLayout home, addbook, logout, chartbook;
    DrawerLayout drawerLayout;
    ImageView menu;

    private Button addButton, deleteButton, showButton, showAllButton, deleteAllButton, updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statiscal_cotnroller);

        Integer month=1;
        myDB = new DBImageTitle(StatiscalCotnroller.this);
        drawerLayout = findViewById(R.id.drawerLayoutc);
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
                redirectActivity(StatiscalCotnroller.this, ShowListImage.class);
            }
        });
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(StatiscalCotnroller.this, MainActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(StatiscalCotnroller.this, MainHello.class);
                UserSession.getInstance().logout();
            }
        });
        chartbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        Cursor cursor = myDB.getAllDataStatiscal();

        while (cursor.moveToNext()){
            StaticcalEntity book = new StaticcalEntity();
            book.setId(Integer.parseInt(cursor.getString(0)));
            book.setName(Integer.parseInt(cursor.getString(1)));
            book.setMonth(Integer.parseInt(cursor.getString(2)));
            book.setTotal(Integer.parseInt(cursor.getString(3)));
            xvalue.add(book);
        }
//        myDB = new DatabaseHelper(MainActivity.this);
//        idEdit = findViewById(R.id.idid);
//        nameEdit = findViewById(R.id.name);
//        monthEdit = findViewById(R.id.month);
//        viewEdit = findViewById(R.id.total);
//        addButton = findViewById(R.id.addbtn);
//        showAllButton = findViewById(R.id.showallbtn);
//        showButton = findViewById(R.id.showbtn);
//        addData();
//        showData();
//        getAllData();


        // Tạo đối tượng BarChart
        int i=0;
        BarChart barChart = findViewById(R.id.chart); // lấy barchart bên view
        barChart.getAxisRight().setDrawLabels(false);
        // Tạo một danh sách các giá trị
        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(1, 45f));
//        entries.add(new BarEntry(1, 80f));
//        entries.add(new BarEntry(2, 65f));
//        entries.add(new BarEntry(3, 38f));
        for (StaticcalEntity data : xvalue) { // chạy vòng lặp để bỏ vào entries

            if(data.getMonth()==month)
            {
                entries.add(new BarEntry(i,Float.parseFloat(data.getTotal().toString()))); // này là chuyển sang float số lượng đọc
                xvalue1.add(data.getName().toString());
                System.out.println(i);
                i=i+1;
            }
        }
        //giữ nguyê
        YAxis yAxis =barChart.getAxisLeft();
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(400f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setAxisLineWidth(2f);
        yAxis.setLabelCount(10);

        BarDataSet barDataSet = new BarDataSet(entries,"Subjects");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xvalue1));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);

    }



    public void getAllData(){
        showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = myDB.getAllDataStatiscal();
                StringBuffer buffer = new StringBuffer();
                if(cursor.getCount() == 0){
                    showMessage("Data" , "Nothing found");
                    return;
                }
                while (cursor.moveToNext()){
                    buffer.append("ID : " + cursor.getString(0) + "\n");
                    buffer.append("NAME : " + cursor.getString(1) + "\n");
                    buffer.append("MONTH : " + cursor.getString(2) + "\n\n");
                    buffer.append("VIEWS : " + cursor.getString(2) + "\n\n");
                }
                showMessage("DATA" , buffer.toString());

            }
        });
    }


    public void showMessage(String title , String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();

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
