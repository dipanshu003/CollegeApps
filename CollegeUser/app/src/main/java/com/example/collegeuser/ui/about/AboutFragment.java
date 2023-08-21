package com.example.collegeuser.ui.about;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.collegeuser.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AboutFragment extends Fragment {

    VideoView videoView;
    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        assert activity != null;
        Objects.requireNonNull(activity.getSupportActionBar()).setTitle("About");

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Inflate the layout for this fragment
        videoView = view.findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.raw.college_video);

        // Set the video URI
        videoView.setVideoURI(videoUri);


        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (videoView.isPlaying()) {
                        videoView.pause();
                    } else {
                        videoView.start();
                    }
                    return true;
                }
                return false;
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // Handle the error here
                return true; // Return true to indicate that the error has been handled
            }
        });


        // Set a listener to restart the video when it finishes playing
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        // Start the video
        videoView.start();


        List<BranchModel> list = new ArrayList<>();
        list.add(new BranchModel("1).DCA & PGDCA", "Both DCA and PGDCA courses are beneficial for individuals who wish to pursue a career in the field of computer applications, software development, and IT. The courses provide a strong foundation in computer fundamentals and help students develop advanced skills in software development, programming, and web development."));
        list.add(new BranchModel("2).BCA", "BCA is a popular choice among students who want to build a career in the field of computer science and IT, as it provides them with a solid foundation in both theoretical and practical aspects of computer applications."));
        list.add(new BranchModel("3).MSCCS", "MSCCS is a highly sought-after program for students who want to specialize in computer science and technology, and it provides them with advanced knowledge and skills that are necessary to succeed in the fast-paced world of computing."));
        list.add(new BranchModel("4).Career First Programs", "It is a Program for Youth of our City to get Prepared for Compatitive Exam, Which is held in our Institute."));
        list.add(new BranchModel("5).JEE/NEET Preparation", "JEE and NEET exams play a crucial role in shaping the careers of students who want to pursue a career in engineering or medicine. Students who perform well in these exams can secure admission to the best colleges in India"));

        BranchAdapter branchAdapter = new BranchAdapter(getContext(), list);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(branchAdapter);



        return view;
    }

}