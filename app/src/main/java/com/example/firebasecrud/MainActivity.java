package com.example.firebasecrud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CourseRVAdapter.CourseClickInterface {

    RecyclerView courseRV;
    ProgressBar loadingPB;
    FloatingActionButton addFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<CourseRVModel> courseRVModelArrayList;
    RelativeLayout bottomsheetRL;
    CourseRVAdapter courseRVAdapter;

    FirebaseAuth mfirebaseauth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courseRV = findViewById(R.id.idRVCourses);
        loadingPB = findViewById(R.id.idPBLOading);
        addFAB = findViewById(R.id.fabAdd);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");
        courseRVModelArrayList = new ArrayList<>();
        bottomsheetRL = findViewById(R.id.idRLBottomSheet);
        courseRVAdapter = new CourseRVAdapter(courseRVModelArrayList, this, this);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        courseRV.setAdapter(courseRVAdapter);

        mfirebaseauth = FirebaseAuth.getInstance();

        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddCourceActivity.class));

            }
        });
        getAllCourses();

    }

    private void getAllCourses() {
        courseRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                courseRVModelArrayList.add(snapshot.getValue(CourseRVModel.class));
                courseRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onCourseClick(int position) {
        displayBottomSheet(courseRVModelArrayList.get(position));

    }

    private void displayBottomSheet(CourseRVModel courseRVModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottomsheet_dialog, bottomsheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView courseNameTv = layout.findViewById(R.id.idTVCourseName);
        TextView courseDesc = layout.findViewById(R.id.idTVDescription);
        TextView courseSuitedFor = layout.findViewById(R.id.idTVSuitedFor);
        TextView coursePrice = layout.findViewById(R.id.idTVPrice);
        ImageView courseImage = layout.findViewById(R.id.idIVcourse);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        Button viewDetailsBtn = layout.findViewById(R.id.idBtnviewDetails);

        courseNameTv.setText(courseRVModel.getCourseName());
        courseDesc.setText(courseRVModel.getCourseDescription());
        courseSuitedFor.setText(courseRVModel.getCourseBestSuitedFor());
        coursePrice.setText("Rs. " + courseRVModel.getCoursePrice());
        Picasso.get().load(courseRVModel.getCourseImg()).into(courseImage);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(MainActivity.this, EditCourseActivity.class);
                edit.putExtra("course", courseRVModel);
                startActivity(edit);
            }
        });


        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = courseRVModel.getCourseLink();
                if (url != null && !url.isEmpty()) {
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "http://" + url;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Link not available", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.idLogOut) {
            Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
            mfirebaseauth.signOut();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}