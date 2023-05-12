package com.example.firebasecrud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCourceActivity extends AppCompatActivity {

    TextInputEditText courseNameEdt, coursePriceEdt, courseSuitedEdt, courseLinkEdt, courseImgEdt, courseDescEdt;
    Button addcourseBtn;
    ProgressBar mprogressbar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cource);

        mprogressbar = findViewById(R.id.progress);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        courseSuitedEdt = findViewById(R.id.idEdtCoursesuitedfor);
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseDescEdt = findViewById(R.id.idEdtCourseDescription);
        addcourseBtn = findViewById(R.id.idBtnAddCourse);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");


        addcourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogressbar.setVisibility(View.VISIBLE);
                String courseName = courseNameEdt.getText().toString();
                String coursePrice = coursePriceEdt.getText().toString();
                String courseSuitedfor = courseSuitedEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();
                courseId = courseName;
                CourseRVModel courseRVModel = new CourseRVModel(courseName, coursePrice, courseImg,courseDesc,courseLink,courseSuitedfor,courseId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mprogressbar.setVisibility(View.GONE);
                        databaseReference.child(courseId).setValue(courseRVModel);
                        Toast.makeText(AddCourceActivity.this, "Course Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCourceActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddCourceActivity.this, "Error adding course, Please Try again", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}