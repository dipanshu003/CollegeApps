package com.example.collegeadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.collegeadmin.faculty.Update_Faculty;

public class MainActivity2 extends AppCompatActivity {
    CardView cv_notice,cv_faculty,cv_gallery,cv_delete,cv_course;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        cv_notice = findViewById(R.id.cvNotice);
        cv_gallery = findViewById(R.id.cdAddGallery);
        cv_faculty = findViewById(R.id.cvFaculty);
        cv_delete = findViewById(R.id.cdDelete);
        cv_course = findViewById(R.id.cdUplaodCourses);

        cv_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,Upload_Courses.class);
                startActivity(intent);
            }
        });

        cv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,Upload_Spotlights.class);
                startActivity(intent);
            }
        });

        cv_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Update_Faculty.class);
                startActivity(intent);
            }
        });

        cv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, DeleteNoticeActivity.class);
                startActivity(intent);
            }
        });

        cv_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Upload_Courses.class);
                startActivity(intent);
            }
        });



    }
}