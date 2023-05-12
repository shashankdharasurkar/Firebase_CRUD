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

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {

    TextInputEditText courseNameEdt, coursePriceEdt, courseSuitedEdt, courseLinkEdt, courseImgEdt, courseDescEdt;
    Button updatecourseBtn, dltcourseBtn;
    ProgressBar mprogressbar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CourseRVModel courseRVModel;

    String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        mprogressbar = findViewById(R.id.progress);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        firebaseDatabase = FirebaseDatabase.getInstance();
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        courseSuitedEdt = findViewById(R.id.idEdtCoursesuitedfor);
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseDescEdt = findViewById(R.id.idEdtCourseDescription);
        updatecourseBtn = findViewById(R.id.idBtnupdateCourse);
        dltcourseBtn = findViewById(R.id.idBtndeleteCourse);

        courseRVModel = getIntent().getParcelableExtra("course");

        if (courseRVModel != null) {
            courseNameEdt.setText(courseRVModel.getCourseName());
            coursePriceEdt.setText(courseRVModel.getCoursePrice());
            courseSuitedEdt.setText(courseRVModel.getCourseBestSuitedFor());
            courseImgEdt.setText(courseRVModel.getCourseImg());
            courseLinkEdt.setText(courseRVModel.getCourseLink());
            courseDescEdt.setText(courseRVModel.getCourseDescription());
            courseId = courseRVModel.getCourseId();
        }

        databaseReference = firebaseDatabase.getReference("Courses").child(courseId);

        updatecourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogressbar.setVisibility(View.VISIBLE);
                String courseName = courseNameEdt.getText().toString();
                String coursePrice = coursePriceEdt.getText().toString();
                String courseSuitedfor = courseSuitedEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();

                Map<String, Object> map = new HashMap<>();

                map.put("courseName", courseName);
                map.put("coursePrice", coursePrice);
                map.put("courseImg", courseImg);
                map.put("courseDescription", courseDesc);
                map.put("courseLink", courseLink);
                map.put("courseBestSuitedFor", courseSuitedfor);
                map.put("courseId", courseId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mprogressbar.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditCourseActivity.this, "Course Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditCourseActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditCourseActivity.this, "Failed to update course", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        dltcourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCourse();

            }
        });
    }

    private void deleteCourse(){
        databaseReference.removeValue();
        Toast.makeText(this, "Course Deleted...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCourseActivity.this, MainActivity.class));
    }
}