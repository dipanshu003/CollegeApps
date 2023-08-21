package com.example.collegeuser.ui.faculty;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collegeuser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyFragment extends Fragment {

    private RecyclerView rePandM,reLabMang,reMangDepartment,rePrograminglang,reCareerFirst;
    private LinearLayout ProLangNoData,mangNoData,LabNoData,pmNoData,careerFirstNoData;
    private List<TeacherData> list1,list2,list3,list4,list5;
    private TeacherAdapter adapter;
    private DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("Faculty");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_faculty, container, false);

        rePandM = view.findViewById(R.id.rePandM);
        reLabMang = view.findViewById(R.id.reLabMang);
        reMangDepartment= view.findViewById(R.id.reMangDepartment);
        rePrograminglang = view.findViewById(R.id.rePrograminglang);
        ProLangNoData= view.findViewById(R.id.ProLangNoData);
        mangNoData = view.findViewById(R.id.mangNoData);
        LabNoData = view.findViewById(R.id.LabNoData);
        pmNoData = view.findViewById(R.id.pmNoData);
        careerFirstNoData = view.findViewById(R.id.careerFirstNoData);
        reCareerFirst = view.findViewById(R.id.reCareerFirst);

        reference = FirebaseDatabase.getInstance().getReference().child("faculty");

        ProLanguagesDepartment();
        ManagementDepartment();
        LabManagementDepartment();
        PresidentAndMonitors();
        Competitive();

        return view;

    }

    private void ProLanguagesDepartment() {
        DatabaseReference dbRef = reference.child("Programing Languages");
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
                    rePrograminglang.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list1,getContext());
                    rePrograminglang.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ManagementDepartment() {
        DatabaseReference dbRef = reference.child("Management");
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
                    reMangDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list2,getContext());
                    reMangDepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LabManagementDepartment() {
        DatabaseReference dbRef = reference.child("Lab Management");
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
                    reLabMang.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list3,getContext());
                    reLabMang.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PresidentAndMonitors() {
        DatabaseReference dbRef = reference.child("President & Monitors");
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
                    rePandM.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list4,getContext());
                    rePandM.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Competitive() {
        DatabaseReference dbRef = reference.child("Competitive(Career First)");
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
                    reCareerFirst.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list5,getContext());
                    reCareerFirst.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}