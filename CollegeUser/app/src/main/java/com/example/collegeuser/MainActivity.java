 package com.example.collegeuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.collegeuser.ebook.EbookActivity;
import com.example.collegeuser.ui.about.AboutFragment;
import com.example.collegeuser.ui.faculty.FacultyFragment;
import com.example.collegeuser.ui.gallery.GalleryFragment;
import com.example.collegeuser.ui.home.HomeFragment;
import com.example.collegeuser.ui.notice.NoticeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

 public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
 {

    private BottomNavigationView bottomNavigationView2;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected;

    private final String CHECKEDITEM = "checked_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("themes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        switch (getCheckedItem())
        {
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;

        }


//        ActionBar actionBar = getSupportActionBar();
//
//        // Set the custom drawable as the background for the action bar
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.titlecolor));

       // getSupportActionBar().setTitle("My College");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_drawer);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView2=findViewById(R.id.bottomNavigationView2);
        navController = Navigation.findNavController(this,R.id.frame_lyt);
        NavigationUI.setupWithNavController(bottomNavigationView2,navController);
    }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
         return true;
     }

     @SuppressLint("NonConstantResourceId")
     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_ebook:
                startActivity(new Intent(getApplicationContext(), EbookActivity.class));
                break;
            case R.id.navigation_website:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sonycomputer.co.in "));
                startActivity(browserIntent);
                break;
            case R.id.navigation_theme:
                showDialog();
                break;
            case R.id.navigation_developer:
                Intent intent = new Intent(MainActivity.this,DeveloperActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_share:

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT,"Sony Computer Education");
                    i.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/app/details?id="+getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(i,"Share With"));
                }
                catch (Exception e)
                {
                    Toast.makeText(this, "Unable to Share ", Toast.LENGTH_SHORT).show();
                }
                break;
        }
         return true;
     }

     private void showDialog() {

        String [] themes = this.getResources().getStringArray(R.array.theme);
         MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
         builder.setTitle("Select Theme");
         builder.setSingleChoiceItems(R.array.theme, getCheckedItem(), new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int i) {
                 selected = themes[i];
                 checkedItem = i;

             }
         });
         builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int i) {
                 if(selected == null)
                 {
                     selected = themes[i];
                     checkedItem = i;
                 }
                 switch (selected)
                 {
                     case "System Default":
                         AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                         Toast.makeText(MainActivity.this, "System Default Selected", Toast.LENGTH_SHORT).show();
                         break;
                     case "Light":
                         AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                         Toast.makeText(MainActivity.this, "Light Theme Selected", Toast.LENGTH_SHORT).show();
                         break;
                     case "Dark":
                         AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                         Toast.makeText(MainActivity.this, "Dark Theme Selected", Toast.LENGTH_SHORT).show();
                         break;

                 }
                 setCheckedItem(checkedItem);
             }
         });
         builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int i) {
                 dialog.dismiss();
             }
         });

         AlertDialog dialog = builder.create();
         dialog.show();

     }
     private int getCheckedItem()
     {
         return sharedPreferences.getInt(CHECKEDITEM,0);
     }

     private void setCheckedItem(int i)
     {
         editor.putInt(CHECKEDITEM,i);
         editor.apply();
     }

     @Override
     public void onBackPressed() {
         if (drawerLayout.isDrawerOpen(GravityCompat.START))
         {
             drawerLayout.closeDrawer(GravityCompat.START);
             Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
         }
         else {
             super.onBackPressed();
         }
     }
 }