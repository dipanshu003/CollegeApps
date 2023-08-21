package com.example.collegeuser.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collegeuser.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EbookActivity extends AppCompatActivity {

    ShimmerFrameLayout shimmerFrameLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ebooks");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Courses/pdf");
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        LinearLayout shimmerLyt = findViewById(R.id.shimmerLyt);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Course> courses = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String pdfTitle = snapshot.child("pdfTitle").getValue(String.class);
                    String pdfUrl = snapshot.child("pdfUrl").getValue(String.class);

                    Course course = new Course(pdfTitle, pdfUrl);
                    courses.add(course);
                }

                // Call a method to set up the RecyclerView with the courses list
                setUpRecyclerView(courses);
            }

            private void setUpRecyclerView(List<Course> courses) {
                RecyclerView recyclerView = findViewById(R.id.ebookRecyl);
                recyclerView.setLayoutManager(new LinearLayoutManager(EbookActivity.this));
                CourseAdapter adapter = new CourseAdapter(courses);
                recyclerView.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                shimmerLyt.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error getting courses", databaseError.toException());
            }
        });
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }
}