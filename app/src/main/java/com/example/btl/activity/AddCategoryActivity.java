package com.example.btl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.model.Category;
import com.example.btl.model.Tag;
import com.example.btl.sqlite.TodoSqliteHelper;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText txtName, txtDes;
    private Button btnAdd;
    private TodoSqliteHelper todoSqliteHelper;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        getSupportActionBar().hide();
        initView();
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra("userId");
        }
//        txtName.setText(userId);
        todoSqliteHelper = new TodoSqliteHelper(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=txtName.getText().toString();
                String description = txtDes.getText().toString();
                Category category = new Category(name, description, userId);
                todoSqliteHelper.addCategory(category);
                Toast.makeText(AddCategoryActivity.this,"Thêm danh sách thành công" ,Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initView() {
        txtName = findViewById(R.id.categoryName);
        txtDes = findViewById(R.id.categoryDescription);
        btnAdd = findViewById(R.id.addTask);
    }

}