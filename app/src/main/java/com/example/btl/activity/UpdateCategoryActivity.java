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

public class UpdateCategoryActivity extends AppCompatActivity {
    private EditText txtId, txtName, txtDes;
    private Button btnEdit;
    private TodoSqliteHelper todoSqliteHelper;
    private Category category;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        getSupportActionBar().hide();
        initView();
        todoSqliteHelper = new TodoSqliteHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            category = (Category) intent.getSerializableExtra("category");
            Integer id = category.getId();
            String name = category.getName();
            String description = category.getDescription();
            userId = category.getUserId();
            txtId.setText(id.toString());
            txtName.setText(name);
            txtDes.setText(description);
        }
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=Integer.parseInt(txtId.getText().toString());
                String name=txtName.getText().toString();
                String description=txtDes.getText().toString();
                Category category = new Category(id, name, description, userId);
                todoSqliteHelper.updateCategory(category);
                Toast.makeText(UpdateCategoryActivity.this,"Sửa danh sách thành công" ,Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initView() {
        txtId = findViewById(R.id.categoryId);
        txtName = findViewById(R.id.categoryName);
        txtDes = findViewById(R.id.categoryDescription);
        btnEdit = findViewById(R.id.editTask);
    }

}