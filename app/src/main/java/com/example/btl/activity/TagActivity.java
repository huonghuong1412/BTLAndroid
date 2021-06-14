package com.example.btl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.btl.R;
import com.example.btl.adapter.TodoTagAdapter;
import com.example.btl.model.Tag;
import com.example.btl.sqlite.TodoSqliteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TagActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private FloatingActionButton floatingActionButton;
    private TodoSqliteHelper sqliteHelper;
    private Integer categoryId;
    private TodoTagAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        initView();
        getSupportActionBar().hide();

        Intent intent = getIntent();
        if(intent != null) {
            categoryId = intent.getIntExtra("categoryId", 1);
        }
        sqliteHelper = new TodoSqliteHelper(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoTagAdapter();
        List<Tag> list = sqliteHelper.getAllTagByCategory(categoryId);
        adapter.setList(list);
        recyclerView.setAdapter(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TagActivity.this, AddTagActivity.class);
                intent.putExtra("categoryId", categoryId);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        imageButton = findViewById(R.id.imageButton);
        floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void onRestart() {
        List<Tag> list = sqliteHelper.getAllTagByCategory(categoryId);
        adapter.setList(list);
        recyclerView.setAdapter(adapter);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        List<Tag> list = sqliteHelper.getAllTagByCategory(categoryId);
        adapter.setList(list);
        recyclerView.setAdapter(adapter);
        super.onRestart();
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        List<Tag> list = sqliteHelper.getAllTagByCategory(categoryId);
        adapter.setList(list);
        recyclerView.setAdapter(adapter);
        super.onRestart();
        super.onResumeFragments();
    }
}