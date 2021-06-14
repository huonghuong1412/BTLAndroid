package com.example.btl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.model.Tag;
import com.example.btl.receiver.MyReceiver;
import com.example.btl.sqlite.TodoSqliteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTagActivity extends AppCompatActivity {

    private EditText txtName, txtDes, txtDate, txtTime;
    private Button btnAdd;
    private TodoSqliteHelper todoSqliteHelper;
    private Integer categoryId;
    private ImageButton btnDate, btnTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        getSupportActionBar().hide();
        initView();
        todoSqliteHelper = new TodoSqliteHelper(this);

        Intent intent = getIntent();
        if(intent != null) {
            categoryId = intent.getIntExtra("categoryId", 1);
        }

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddTagActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                TimePickerDialog dialog = new TimePickerDialog(AddTagActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTime.setText(hourOfDay+":"+minute);
                    }
                }, h,m, false);
                dialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=txtName.getText().toString();
                String description = txtDes.getText().toString();
                String date = txtDate.getText().toString() != null ? txtDate.getText().toString() : "";
                String time = txtTime.getText().toString() != null ? txtTime.getText().toString() : "";
                Tag tag = new Tag(name, description, date, time, categoryId);
                todoSqliteHelper.addTag(tag);

                String [] time_spilt=date.split("/");
                int day=Integer.parseInt(time_spilt[0]);
                int month=Integer.parseInt(time_spilt[1]);
                int year=Integer.parseInt(time_spilt[2]);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(year, month, day);
                int month_now = calendar.get(Calendar.MONTH);
                int day_now = calendar.get(Calendar.DAY_OF_MONTH);
                int year_now = calendar.get(Calendar.YEAR);

                if((month_now == month) && (day_now == day) && year_now == year ) {
                    AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                    Intent intent = new Intent(AddTagActivity.this, MyReceiver.class);
                    intent.putExtra("myAction", "mDoNotify");
                    intent.putExtra("Title", txtName.getText().toString());
                    intent.putExtra("Description", txtDes.getText().toString() + " - " + txtDate.getText().toString());

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTagActivity.this, 0, intent, 0);
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }

                Toast.makeText(AddTagActivity.this,"Thêm thẻ thành công" ,Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void initView(){
        txtName = findViewById(R.id.addTaskTitle);
        txtDes = findViewById(R.id.addTaskDescription);
        txtDate = findViewById(R.id.taskDate);;
        txtTime = findViewById(R.id.taskTime);
        btnAdd = findViewById(R.id.addTask);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
    }
}