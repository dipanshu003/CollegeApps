package com.example.collegeadmin.faculty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeadmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Update_Faculty extends AppCompatActivity {
    FloatingActionButton btn_fab;
    private RecyclerView rePandM,reLabMang,reMangDepartment,rePrograminglang,reCareerFirst;
    private LinearLayout ProLangNoData,mangNoData,LabNoData,pmNoData,careerFirstNoData;
    private List<TeacherData> list1,list2,list3,list4,list5;
    private TeacherAdapter adapter;

    private DatabaseReference reference,dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        btn_fab = findViewById(R.id.fbAdd);
        rePandM = findViewById(R.id.rePandM);
        reLabMang = findViewById(R.id.reLabMang);
        reMangDepartment= findViewById(R.id.reMangDepartment);
        rePrograminglang = findViewById(R.id.rePrograminglang);
        ProLangNoData= findViewById(R.id.ProLangNoData);
        mangNoData = findViewById(R.id.mangNoData);
        LabNoData = findViewById(R.id.LabNoData);
        pmNoData = findViewById(R.id.pmNoData);
        careerFirstNoData = findViewById(R.id.careerFirstNoData);
        reCareerFirst = findViewById(R.id.reCareerFirst);

        reference = FirebaseDatabase.getInstance().getReference().child("faculty");

        ProLanguagesDepartment();
        ManagementDepartment();
        LabManagementDepartment();
        PresidentAndMonitors();
        Competitive();

        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Update_Faculty.this, Add_Teacher.class));
            }
        });

    }

    private void ProLanguagesDepartment() {
        dbRef = reference.child("Programing Languages");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    ProLangNoData.setVisibility(View.VISIBLE);
                    rePrograminglang.setVisibility(View.GONE);
                }else {

                    ProLangNoData.setVisibility(View.GONE);
                    rePrograminglang.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    rePrograminglang.setHasFixedSize(true);
                    rePrograminglang.setLayoutManager(new LinearLayoutManager(Update_Faculty.this));
                    adapter = new TeacherAdapter(list1,Update_Faculty.this,"Programing Languages");
                    rePrograminglang.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_Faculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ManagementDepartment() {
        dbRef = reference.child("Management");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    mangNoData.setVisibility(View.VISIBLE);
                    reMangDepartment.setVisibility(View.GONE);
                }else {

                    mangNoData.setVisibility(View.GONE);
                    reMangDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    reMangDepartment.setHasFixedSize(true);
                    reMangDepartment.setLayoutManager(new LinearLayoutManager(Update_Faculty.this));
                    adapter = new TeacherAdapter(list2,Update_Faculty.this,"Management");
                    reMangDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_Faculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LabManagementDepartment() {
        dbRef = reference.child("Lab Management");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list3 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    LabNoData.setVisibility(View.VISIBLE);
                    reLabMang.setVisibility(View.GONE);
                }else {

                    LabNoData.setVisibility(View.GONE);
                    reLabMang.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    reLabMang.setHasFixedSize(true);
                    reLabMang.setLayoutManager(new LinearLayoutManager(Update_Faculty.this));
                    adapter = new TeacherAdapter(list3,Update_Faculty.this,"Lab Management");
                    reLabMang.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_Faculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PresidentAndMonitors() {
        dbRef = reference.child("President & Monitors");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    pmNoData.setVisibility(View.VISIBLE);
                    rePandM.setVisibility(View.GONE);
                }else {

                    pmNoData.setVisibility(View.GONE);
                    rePandM.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    rePandM.setHasFixedSize(true);
                    rePandM.setLayoutManager(new LinearLayoutManager(Update_Faculty.this));
                    adapter = new TeacherAdapter(list4,Update_Faculty.this,"President & Monitors");
                    rePandM.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_Faculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Competitive() {
        dbRef = reference.child("Competitive(Career First)");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list5 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    careerFirstNoData.setVisibility(View.VISIBLE);
                    reCareerFirst.setVisibility(View.GONE);
                }else {

                    careerFirstNoData.setVisibility(View.GONE);
                    reCareerFirst.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list5.add(data);
                    }
                    reCareerFirst.setHasFixedSize(true);
                    reCareerFirst.setLayoutManager(new LinearLayoutManager(Update_Faculty.this));
                    adapter = new TeacherAdapter(list5,Update_Faculty.this,"Competitive(Career First)");
                    reCareerFirst.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_Faculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}