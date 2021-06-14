package com.example.btl.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.btl.model.Category;
import com.example.btl.model.Tag;
import com.example.btl.model.Task;
import com.example.btl.model.User;

import java.util.ArrayList;
import java.util.List;

public class TodoSqliteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="TodoDB.db";
    private static final int DATABSE_VERSION=1;

    public TodoSqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCategory = "CREATE TABLE category(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "userId TEXT)";
        String sqlTag = "CREATE TABLE tag(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "date TEXT," +
                "time TEXT," +
                "categoryId INTEGER," +
                "FOREIGN KEY(categoryId) REFERENCES category(id))";
        String sqlTodo = "CREATE TABLE task(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "status INTEGER," +
                "tagId INTEGER," +
                "FOREIGN KEY(tagId) REFERENCES tag(id))";
        String sqlUser = "CREATE TABLE `user`(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT," +
                "phone TEXT," +
                "address TEXT," +
                "userId TEXT NOT NULL UNIQUE)";
        db.execSQL(sqlCategory);
        db.execSQL(sqlTag);
        db.execSQL(sqlTodo);
        db.execSQL(sqlUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Category
    //add
    public void addCategory(Category category){
        String sql="INSERT INTO category(name, description, userId) VALUES(?, ?, ?)";
        String[] args={
                category.getName(),
                category.getDescription(),
                category.getUserId()
        };
        SQLiteDatabase statement=getWritableDatabase();
        statement.execSQL(sql,args);
    }

    // get all by user
    public List<Category> getAllByUser(String user) {
        String sql = "userId like ?";
        String[] args = { "%" + user + "%" };
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("category", null, sql, args, null, null, null);
        List<Category> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            int id=cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            String userId = cursor.getString(3);
            Category category = new Category(id, name, description, userId);
            list.add(category);
        }
        return list;
    }

    //update
    public int updateCategory(Category category){
        ContentValues v=new ContentValues();
        v.put("name",category.getName());
        v.put("description",category.getDescription());
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(category.getId())};
        SQLiteDatabase st=getWritableDatabase();
        return st.update("category", v, whereClause, whereArgs);
    }

    //delete
    public int deleteCategory(int id){
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(id)};
        SQLiteDatabase st=getWritableDatabase();
        return st.delete("category",whereClause,whereArgs);
    }

    // Tag
    //add
    public void addTag(Tag tag){
        String sql="INSERT INTO tag(name, description, date, time, categoryId) VALUES(?, ?, ?, ?, ?)";
        String[] args={
                tag.getName(),
                tag.getDescription(),
                tag.getDate(),
                tag.getTime(),
                Integer.toString(tag.getCategoryId()),
        };
        SQLiteDatabase statement=getWritableDatabase();
        statement.execSQL(sql,args);
    }

    // get all
    public List<Tag> getAllTagByCategory(Integer category){
        List<Tag> list=new ArrayList<>();
        SQLiteDatabase statement=getReadableDatabase();
        Cursor rs = statement.query("tag JOIN category ON tag.categoryId = category.id and categoryId = " + category ,new String[] {
                "tag.id as tagId",
                        "tag.name as tagName",
                        "tag.description as tagDescription",
                        "tag.date as tagDate",
                        "tag.time as tgTime",
                        "tag.categoryId as tagCategoryId",
                        "category.name as categoryName"},
                null,
                null,
                null,
                null,
                null);
        while((rs!=null && rs.moveToNext())){
            int id=rs.getInt(0);
            String name = rs.getString(1);
            String description = rs.getString(2);
            String date = rs.getString(3);
            String time = rs.getString(4);
            Integer categoryId = rs.getInt(5);
            String categoryName = rs.getString(6);
            list.add(new Tag(id, name, description, date, time, categoryId, categoryName));
        }
        return list;
    }

    //update
    public int updateTag(Tag tag){
        ContentValues v=new ContentValues();
        v.put("name",tag.getName());
        v.put("description",tag.getDescription());
        v.put("date",tag.getDate());
        v.put("time",tag.getTime());
        v.put("categoryId", tag.getCategoryId());
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(tag.getId())};
        SQLiteDatabase st=getWritableDatabase();
        return st.update("tag", v, whereClause, whereArgs);
    }
    //delete
    public int deleteTag(int id){
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(id)};
        SQLiteDatabase st=getWritableDatabase();
        return st.delete("tag",whereClause,whereArgs);
    }

    // Todo
    // Tag
    //add
    public void addTodo(Task todo){
        String sql="INSERT INTO task(name, status, tagId) VALUES(?, ?, ?)";
        String[] args={
                todo.getName(),
                Integer.toString(todo.getStatus()),
                Integer.toString(todo.getTagId()),
        };
        SQLiteDatabase statement=getWritableDatabase();
        statement.execSQL(sql,args);
    }

    // get all
    public List<Task> getAllTodoByTag(Integer tag){
        List<Task> list=new ArrayList<>();
        SQLiteDatabase statement=getReadableDatabase();
        Cursor rs = statement.query("task JOIN tag ON task.tagId = tag.id and tagId = " + tag ,new String[] {
                        "task.id as taskId",
                        "task.name as taskName",
                        "task.status as taskStatus",
                        "task.tagId as taskTagId",
                        "tag.name as tagName"},
                null,
                null,
                null,
                null,
                null);
        while((rs!=null && rs.moveToNext())){
            int id=rs.getInt(0);
            String name = rs.getString(1);
            Integer status = rs.getInt(2);
            Integer tagId = rs.getInt(3);
            String tagName = rs.getString(4);
            list.add(new Task(id, name, status, tagId, tagName));
        }
        return list;
    }

    //update
    public int updateTodo(Task todo){
        ContentValues v=new ContentValues();
        v.put("name", todo.getName());
        v.put("status", todo.getStatus());
        v.put("tagId", todo.getTagId());
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(todo.getId())};
        SQLiteDatabase st=getWritableDatabase();
        return st.update("task", v, whereClause, whereArgs);
    }

    //update
    public int isCompleteTodo(Task todo){
        ContentValues v=new ContentValues();
        v.put("status", todo.getStatus());
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(todo.getId())};
        SQLiteDatabase st=getWritableDatabase();
        return st.update("task", v, whereClause, whereArgs);
    }

    //delete
    public int deleteTodo(int id){
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(id)};
        SQLiteDatabase st=getWritableDatabase();
        return st.delete("task",whereClause,whereArgs);
    }


    // User
    // Category
    //add
    public void addUser(User user){
        String sql="INSERT INTO `user`(name, email, phone, address, userId) VALUES(?, ?, ?, ?, ?)";
        String[] args={
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getUserId()
        };
        SQLiteDatabase statement=getWritableDatabase();
        statement.execSQL(sql,args);
    }

    // get by id
    public User getUserById(String user){
        String whereClause="userId =?";
        String[] whereArgs={String.valueOf(user)};
        SQLiteDatabase st=getReadableDatabase();
        Cursor rs=st.query("`user`",null,whereClause,
                whereArgs,null,null,null);
        if(rs.moveToNext()){
            Integer id = rs.getInt(0);
            String name = rs.getString(1);
            String email = rs.getString(2);
            String phone = rs.getString(3);
            String address = rs.getString(4);
            String userId = rs.getString(5);
            return new User(id, name, email, phone, address, userId);
        }
        return null;
    }

    //update
    public int updateUser(User user){
        ContentValues v=new ContentValues();
        v.put("name",user.getName());
        v.put("phone",user.getPhone());
        v.put("address",user.getAddress());
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(user.getId())};
        SQLiteDatabase st=getWritableDatabase();
        return st.update("`user`", v, whereClause, whereArgs);
    }

}
