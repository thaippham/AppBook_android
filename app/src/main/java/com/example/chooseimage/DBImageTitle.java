package com.example.chooseimage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBImageTitle extends SQLiteOpenHelper {
    public DBImageTitle(@Nullable Context context){
        super(context, "dbAppBook", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table tbBook (id integer primary key, image text, title text, author text, category text)";
        db.execSQL(sql);
        String sqlacc = "create table tbAccount (id integer primary key, username text, email text, password text, role text)";
        db.execSQL(sqlacc);
        String sqlsavebook = "create table tbBookSave (id integer primary key, user_id integer, book_id integer)";
        db.execSQL(sqlsavebook);
        String sqlchapter = "create table tbChapterBook (id integer primary key, book_id integer, chapter integer, views integer, content text)";
        db.execSQL(sqlchapter);
        String sqlreadchapter = "create table tbReadChapter (id integer primary key, user_id integer, chapter_id integer)";
        db.execSQL(sqlreadchapter);
        db.execSQL("CREATE TABLE IF NOT EXISTS " +"tbReading"+ "(ID integer PRIMARY KEY AUTOINCREMENT , Book_id integer  , MONTH integer ,VIEWS integer)");
    }

    public void ThemDL(ImageTitle it){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into tbBook (image, title, author, category) values(?,?,?,?)";
        db.execSQL(sql, new String[]{it.getImage(), it.getTitleimg(), it.getAuthor(), it.getCategory()});
    }
    public void ThemDLAccount(Account acc){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into tbAccount (username, email, password, role) values(?,?,?,?)";
        db.execSQL(sql, new String[]{acc.getUsername(), acc.getEmail(), acc.getPassword(), acc.getRole()});
    }
    public void ThemBookSave(SaveBook sb){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into tbBookSave(user_id, book_id) values(?,?)";
        db.execSQL(sql, new String[]{String.valueOf(sb.getUser_id()), String.valueOf(sb.getBook_id())});
    }
    public void ThemChapter(Chapter chapter){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into tbChapterBook(book_id, chapter, views, content) values(?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(chapter.getBook_id()), String.valueOf(chapter.getChapter()), String.valueOf(chapter.getViews()), chapter.getContent()});
    }
    public boolean kiemTraSachDaLuu(int userId, int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM tbBookSave WHERE user_id = ? AND book_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(bookId)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    public boolean kiemTraTrang(int userId, int chapterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM tbReadChapter WHERE user_id = ? AND chapter_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(chapterId)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    public void luuTrang(int userId, int chapterId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("chapter_id", chapterId);
        db.insert("tbReadChapter", null, values);
        db.close();
    }
    public void xoaSachKhoiDanhSachLuu(int userId, int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tbBookSave", "user_id = ? AND book_id = ?", new String[]{String.valueOf(userId), String.valueOf(bookId)});
        db.close();
    }

    public void luuSachVaoDanhSachLuu(int userId, int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("book_id", bookId);
        db.insert("tbBookSave", null, values);
        db.close();
    }
    public boolean kiemTraDangNhap(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id"};
        String selection = "username=? and password=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("tbAccount", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }
    public String getRoleByUsername(String username) {
        String role = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"role"};
        String selection = "username=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query("tbAccount", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("role");

            if (columnIndex != -1) {
                role = cursor.getString(columnIndex);
            }

            cursor.close();
        }

        db.close();

        return role;
    }
    public int getIdByUsername(String username) {
        int id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id"};
        String selection = "username=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query("tbAccount", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("id");

            if (columnIndex != -1) {
                id = Integer.parseInt(cursor.getString(columnIndex));
            }

            cursor.close();
        }

        db.close();

        return id;
    }
    public void XoaDLById(int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tbBook", "id = ?", new String[]{String.valueOf(bookId)});
        db.close();
    }
    public void CapNhatDL(ImageTitle it) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image", it.getImage());
        values.put("title", it.getTitleimg());
        values.put("author", it.getAuthor());
        values.put("category", it.getCategory());

        db.update("tbBook", values, "id = ?", new String[]{String.valueOf(it.getId())});
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public boolean isUserRegistered(String username, String email) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM tbAccount where username=? or email=?" ;
        Cursor cursor = db.rawQuery(query, new String[]{username, email});

        boolean isRegistered = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isRegistered;
    }

    public List<ImageTitle> DocDL(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from tbBook";
        List<ImageTitle> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                ImageTitle it = new ImageTitle();
                it.setId(Integer.parseInt((cursor.getString(0))));
                it.setImage((cursor.getString(1)));
                it.setTitleimg((cursor.getString(2)));
                it.setAuthor((cursor.getString(3)));
                it.setCategory((cursor.getString(4)));
                data.add(it);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }
    public List<ImageTitle> getSavedBooksForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT tbBook.* FROM tbBook INNER JOIN tbBookSave ON tbBook.id = tbBookSave.book_id WHERE tbBookSave.user_id = ?";

        List<ImageTitle> savedBooks = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                ImageTitle book = new ImageTitle();
                book.setId(cursor.getInt(0));
                book.setImage(cursor.getString(1));
                book.setTitleimg(cursor.getString(2));
                book.setAuthor(cursor.getString(3));
                book.setCategory(cursor.getString(4));

                savedBooks.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return savedBooks;
    }
    public String getUsernameById(int userId) {
        String username = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("tbAccount", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("username");

            if (columnIndex != -1) {
                username = cursor.getString(columnIndex);
            }

            cursor.close();
        }

        db.close();

        return username;
    }

    public String getEmailById(int userId) {
        String email = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"email"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("tbAccount", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("email");

            if (columnIndex != -1) {
                email = cursor.getString(columnIndex);
            }

            cursor.close();
        }

        db.close();

        return email;
    }
    public List<Chapter> getChaptersByBookId(int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Chapter> chapters = new ArrayList<>();

        String selectQuery = "SELECT * FROM tbChapterBook WHERE book_id = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(bookId)});

        if (cursor.moveToFirst()) {
            do {
                Chapter chapter = new Chapter();
                chapter.setId(cursor.getInt(0));
                chapter.setBook_id(cursor.getInt(1));
                chapter.setChapter(cursor.getInt(2));
                chapter.setViews(cursor.getInt(3));
                chapter.setContent(cursor.getString(4));
                chapters.add(chapter);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return chapters;
    }
    public void updateChapterViews(int chapterId, int newViews) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("views", newViews);

        db.update("tbChapterBook", values, "id = ?", new String[]{String.valueOf(chapterId)});
        db.close();
    }
    public int getMaxChapterByBookId(int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(chapter) FROM tbChapterBook WHERE book_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bookId)});
        int maxChapter = 0;
        if (cursor.moveToFirst()) {
            maxChapter = cursor.getInt(0);
        }
        cursor.close();
        return maxChapter;
    }

    public Chapter getChapterByBookIdAndChapter(int bookId, int chapter) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM tbChapterBook WHERE book_id = ? AND chapter = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bookId), String.valueOf(chapter)});
        Chapter selectedChapter = null;
        if (cursor.moveToFirst()) {
            selectedChapter = new Chapter();
            selectedChapter.setId(cursor.getInt(0));
            selectedChapter.setBook_id(cursor.getInt(1));
            selectedChapter.setChapter(cursor.getInt(2));
            selectedChapter.setViews(cursor.getInt(3));
            selectedChapter.setContent(cursor.getString(4));
        }
        cursor.close();
        return selectedChapter;
    }
    public Cursor getAllDataStatiscal() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "tbReading" , null);
        return cursor;
    }
}
