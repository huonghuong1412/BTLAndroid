package com.example.btl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.model.Tag;
import com.example.btl.sqlite.TodoSqliteHelper;

import java.util.Calendar;

public class UpdateTagActivity extends AppCompatActivity {

    private EditText txtId, txtName, txtDes, txtDate, txtTime;
    private Button btnEdit;
    private ImageButton btnDate, btnTime;
    private TodoSqliteHelper todoSqliteHelper;
    private Integer categoryId;
    private Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tag);
        getSupportActionBar().hide();
        initView();
        todoSqliteHelper = new TodoSqliteHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            tag = (Tag)intent.getSerializableExtra("tag");
            Integer id = tag.getId();
            String name = tag.getName();
            String description = tag.getDescription();
            String date = tag.getDate();
            String time = tag.getTime();
            categoryId = tag.getCategoryId();
            txtId.setText(id.toString());
            txtName.setText(name);
            txtDes.setText(description);
            txtDate.setText(date);
            txtTime.setText(time);
        }

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(UpdateTagActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int h = calendar.get(Calendar.HOUR);
                int m = calendar.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(UpdateTagActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTime.setText(hourOfDay+":"+minute);
                    }
                }, h,m, false);
                dialog.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=Integer.parseInt(txtId.getText().toString());
                String name=txtName.getText().toString();
                String description=txtDes.getText().toString();
                String date = txtDate.getText().toString();
                String time=txtTime.getText().toString();
                Tag tag = new Tag(id, name, description, date, time, categoryId);
                todoSqliteHelper.updateTag(tag);
                Toast.makeText(UpdateTagActivity.this,"Sửa tên thẻ thành công" ,Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void initView(){
        txtId = findViewById(R.id.taskId);
        txtName = findViewById(R.id.addTaskTitle);
        txtDes = findViewById(R.id.addTaskDescription);
        txtDate = findViewById(R.id.taskDate);;
        txtTime = findViewById(R.id.taskTime);
        btnEdit = findViewById(R.id.editTask);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
    }
}