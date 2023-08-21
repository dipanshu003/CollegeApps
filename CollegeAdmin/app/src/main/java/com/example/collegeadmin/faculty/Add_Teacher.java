package com.example.collegeadmin.faculty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Add_Teacher extends AppCompatActivity {

    private ImageView ivAddTeacher;
    private EditText addTeacherName, addTeacherEmail, addTeacherPost;
    private Spinner addTeacherCategory;
    private Button btnAddTeacher;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private  String category,name,email,post,Download_URL="";
    private ProgressDialog pd;
    private DatabaseReference reference,dbRef;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        ivAddTeacher = findViewById(R.id.ivAddTeacher);
        addTeacherCategory = findViewById(R.id.addTeacherCategory);
        addTeacherEmail = findViewById(R.id.addTeacherEmail);
        addTeacherName = findViewById(R.id.addTeacherName);
        addTeacherPost = findViewById(R.id.addTeacherPost);
        btnAddTeacher = findViewById(R.id.btnAddTeacher);

        reference = FirebaseDatabase.getInstance().getReference().child("faculty");
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);


        String[] items = new String[]{"Select Category", "Programing Languages","Management", "Lab Management", "President & Monitors","Competitive(Career First)"};
        addTeacherCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        //here we set spineer and select category
        addTeacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = addTeacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ivAddTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        btnAddTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        name= addTeacherName.getText().toString();
        email = addTeacherEmail.getText().toString();
        post = addTeacherPost.getText().toString();

        if (name.isEmpty()){
            addTeacherName.setError("Enter Name");
            addTeacherName.requestFocus();
        }else if (email.isEmpty()){
            addTeacherEmail.setError("Enter E-mail");
            addTeacherEmail.requestFocus();
        }else if (post.isEmpty()){
            addTeacherPost.setError("Enter Post");
            addTeacherPost.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(this, "PLease Select Any Category", Toast.LENGTH_SHORT).show();
        }else if (bitmap == null){
            insertData();
        }else{
            insertImage();
        }
    }

    private void insertImage() {

        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference FilePath;

        FilePath = storageReference.child("Teachers").child(finalimg+"jpg");
        final UploadTask uploadTask = FilePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(Add_Teacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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

                                    insertData();
                                    addTeacherName.setText("");
                                    addTeacherEmail.setText("");
                                    addTeacherPost.setText("");
//                                    Bitmap reset = BitmapFactory.decodeFile("D:\\Dip_Android\\CollegeAdmin\\app\\src\\main\\res\\drawable-v24\\teacher.webp");
//                                    ivAddTeacher.setImageBitmap(reset);
                                    ivAddTeacher.setImageResource(R.drawable.teacher);
                                    addTeacherName.requestFocus();
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(Add_Teacher.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void insertData() {

        dbRef = reference.child(category);
        final String uniquekey = dbRef.push().getKey();


        TeacherData teacherData = new TeacherData(name,email,post,Download_URL,uniquekey);

        dbRef.child(uniquekey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(Add_Teacher.this, "Notice Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Add_Teacher.this, "Upload Failed", Toast.LENGTH_SHORT).show();
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
            ivAddTeacher.setImageBitmap(bitmap);
        }
    }
}
