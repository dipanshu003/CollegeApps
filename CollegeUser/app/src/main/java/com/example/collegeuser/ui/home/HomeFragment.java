package com.example.collegeuser.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.collegeuser.R;

import java.net.URI;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ImageSlider imageSlider;
    private ImageView map;

    public HomeFragment(){
        //required empty constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("Home");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.imgSlider);

        //now we will create a list for image

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/collegeadmin-19be0.appspot.com/o/%5BB%403b6be79jpg?alt=media&token=8bf4f1e4-95e1-4408-bb59-94e050879d06", "Blood Donation Camp",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/collegeadmin-19be0.appspot.com/o/%5BB%40262184djpg?alt=media&token=bfc48bfb-0ac9-4c67-a195-d765abae0a51","Garba at College", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://sonycomputer.co.in/wp-content/uploads/2022/07/koo_banner.jpeg","Associated wtih Koo ", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://sonycomputer.co.in/wp-content/uploads/2019/03/16195220_856108087863528_3542208001186855668_n.jpg","Convocation Picture", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/collegeadmin-19be0.appspot.com/o/%5BB%403374029jpg?alt=media&token=b1d65626-4f30-4bbf-a11f-a3892b3d00c3","Birthday Celeration of Kamalnath Ji ",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://sonycomputer.co.in/wp-content/uploads/2019/06/banner10.jpg","Our Students Selected in MPPSC", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://sonycomputer.co.in/wp-content/uploads/2019/03/DSC04836.jpg","Practical Laboratory", ScaleTypes.FIT));


        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        //click listener on Image View (map)

        map = view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        return view;
    }

    private void openMap() {

        Uri uri = Uri.parse("geo:0,0?q=Sony Computer Education , Chhindwara");

        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

}