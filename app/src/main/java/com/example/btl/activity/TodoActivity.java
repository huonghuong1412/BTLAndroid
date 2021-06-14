package com.example.btl.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.adapter.TaskAdapter;
import com.example.btl.model.Task;
import com.example.btl.sqlite.TodoSqliteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private FloatingActionButton floatingActionButton;
    private TaskAdapter adapter;
    private TodoSqliteHelper sqliteHelper;
    private List<Task> list;
    private Integer tagId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        getSupportActionBar().hide();
        initView();
        Intent intent = getIntent();
        if(intent != null) {
            tagId = intent.getIntExtra("tagId", 1);
        }

        sqliteHelper = new TodoSqliteHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TaskAdapter();
        list = sqliteHelper.getAllTodoByTag(tagId);
        adapter.setList(list);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlertDialog(tagId);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void displayAlertDialog(Integer tagId) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_todo_form, null);
        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_Username);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add task");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etUsername.getText().toString();
                Integer status = 0;
                Task task = new Task(name, status, tagId);
                sqliteHelper.addTodo(task);
                Toast.makeText(getBaseContext(), "Add task success", Toast.LENGTH_SHORT).show();
                list = sqliteHelper.getAllTodoByTag(tagId);
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                dialog.cancel();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

        list = sqliteHelper.getAllTodoByTag(tagId);
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        imageButton = findViewById(R.id.imageButton);
        recyclerView = findViewById(R.id.tasksRecyclerView);
        floatingActionButton = findViewById(R.id.fab);
    }

    @Override
    protected void onRestart() {
        list = sqliteHelper.getAllTodoByTag(tagId);
        adapter.setList(list);
        recyclerView.setAdapter(adapter);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        list = sqliteHelper.getAllTodoByTag(tagId);
        adapter.setList(list);
        recyclerView.setAdapter(adapter);
        super.onResume();
    }
}