package com.example.collegeuser.ui.gallery;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeuser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private Gallery_Adapter adapter;
    DatabaseReference reference;

    private RecyclerView affiRecycl, convoRecycl, tourRecycl, otherRecycl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("Gallery");

        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        affiRecycl = rootView.findViewById(R.id.affiRecycl);
        convoRecycl = rootView.findViewById(R.id.convoRecycl);
        tourRecycl = rootView.findViewById(R.id.tourRecycl);
        otherRecycl = rootView.findViewById(R.id.otherRecycl);
        // RecyclerView recyclerView = findViewById(R.id.affiRecycl);

        reference = FirebaseDatabase.getInstance().getReference().child("Spotlights");


        getAffiliations();
        //getCelebration();
        getConvocation();
       getOthers();
        getPicnic();
        return rootView;
    }
    private void getPicnic() {
        reference.child("Picnic").addValueEventListener(new ValueEventListener() {

            List<String> imagelist = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = (String) dataSnapshot.getValue();
                    imagelist.add(data);
                }
                adapter = new Gallery_Adapter(getContext(), imagelist);
                tourRecycl.setLayoutManager(new GridLayoutManager(getContext(), 3));
                tourRecycl.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOthers() {
        reference.child("Others").addValueEventListener(new ValueEventListener() {

            List<String> imagelist = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = (String) dataSnapshot.getValue();
                    imagelist.add(data);
                }
                adapter = new Gallery_Adapter(getContext(), imagelist);
                otherRecycl.setLayoutManager(new GridLayoutManager(getContext(), 3));
                otherRecycl.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getConvocation() {
        reference.child("Convocation").addValueEventListener(new ValueEventListener() {

            List<String> imagelist = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = (String) dataSnapshot.getValue();
                    imagelist.add(data);
                }
                adapter = new Gallery_Adapter(getContext(), imagelist);
                convoRecycl.setLayoutManager(new GridLayoutManager(getContext(), 3));
                convoRecycl.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAffiliations() {
        reference.child("Affiliations").addValueEventListener(new ValueEventListener() {

            List<String> imagelist = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = (String) dataSnapshot.getValue();
                    imagelist.add(data);
                }
                adapter = new Gallery_Adapter(getContext(), imagelist);
                affiRecycl.setLayoutManager(new GridLayoutManager(getContext(), 3));
                affiRecycl.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
