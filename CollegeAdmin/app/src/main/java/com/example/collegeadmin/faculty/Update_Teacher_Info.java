package com.example.collegeadmin.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.collegeadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Update_Teacher_Info extends AppCompatActivity {

    private ImageView updateInfoTeacherImg;
    private EditText updateInfoName,updateInfoEmail,updateInfoPost;
    private Button btnUpdateTeacher,btnDeleteTeacher;
    private  String name,image,email,post;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String Download_URL,category,uniqueKey;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher_info);

        name = getIntent().getStringExtra("name");
        post = getIntent().getStringExtra("post");
        email= getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");


       uniqueKey = getIntent().getStringExtra("key");
       category  = getIntent().getStringExtra("category");


        pd = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference("faculty");
        storageReference = FirebaseStorage.getInstance().getReference();
        updateInfoTeacherImg = findViewById(R.id.updateInfoTeacherImg);
        updateInfoName = findViewById(R.id.updateInfoName);
        updateInfoEmail =  findViewById(R.id.updateInfoEmail);
        updateInfoPost = findViewById(R.id.updateInfoPost);
        btnDeleteTeacher = findViewById(R.id.btnDeleteTeacher);
        btnUpdateTeacher = findViewById(R.id.btnUpdateTeacher);



        try {
            Picasso.get().load(image).into(updateInfoTeacherImg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateInfoPost.setText(post);
        updateInfoName.setText(name);
        updateInfoEmail.setText(email);
        updateInfoTeacherImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        btnUpdateTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = updateInfoName.getText().toString();
                post= updateInfoPost.getText().toString();
                email = updateInfoEmail.getText().toString();

                checkValidation();
            }
        });
        btnDeleteTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteData();
            }
        });

    }

    private void checkValidation() {
        if (name.isEmpty()){
            updateInfoName.setError("Empty");
            updateInfoName.requestFocus();
        }else if(post.isEmpty()){
            updateInfoPost.setError("Empty");
            updateInfoPost.requestFocus();
        }else if(email.isEmpty()){
            updateInfoEmail.setError("Empty");
            updateInfoEmail.requestFocus();
        }else if (bitmap == null){
            updateData(image);
        }else{
            uploadImage();
        }
    }

    private void uploadImage() {

        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference FilePath;

        FilePath = storageReference.child("Notice").child(finalimg+"jpg");
        final UploadTask uploadTask = FilePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(Update_Teacher_Info.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            FilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Download_URL = String.valueOf(uri);
                                    updateData(Download_URL);
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(Update_Teacher_Info.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateData(String s) {
        HashMap hp = new HashMap();
        hp.put("name",name);
        hp.put("post",post);
        hp.put("email",email);
        hp.put("image",s);

        reference.child(category).child(uniqueKey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(Update_Teacher_Info.this, "Data Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Update_Teacher_Info.this,Update_Faculty.class);
                //addFlags -> wif we back press again after the data updated we will not go in update teacher info again
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Update_Teacher_Info.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DeleteData() {
        reference.child(category).child(uniqueKey).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Update_Teacher_Info.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Update_Teacher_Info.this,Update_Faculty.class);
                        //addFlags -> wif we back press again after the data updated we will not go in update teacher info again
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Update_Teacher_Info.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void openGallery() {
        Intent pickImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImg, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateInfoTeacherImg.setImageBitmap(bitmap);
        }
    }
}