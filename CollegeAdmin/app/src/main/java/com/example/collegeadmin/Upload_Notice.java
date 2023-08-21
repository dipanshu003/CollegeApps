package com.example.collegeadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Upload_Notice extends AppCompatActivity {

    private CardView addImg;
    Uri imageUri;
    private  final int REQ = 1;
    private Bitmap bitmap;
    private ImageView noticeImgView;
    private EditText upNoticeTitle;
    private Button btn_upNotice;
    private DatabaseReference reference;
    private StorageReference storageReference;
    String Download_URL= "";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        pd = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        addImg = findViewById(R.id.addImage);
        noticeImgView = findViewById(R.id.ivNoticeImage);
        upNoticeTitle = findViewById(R.id.NoticeTitle);
        btn_upNotice = findViewById(R.id.btnUpnotice);

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        btn_upNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (upNoticeTitle.getText().toString().isEmpty()){
                    upNoticeTitle.setError("Empty");
                    upNoticeTitle.requestFocus();
                }else if(noticeImgView.getDrawable()==null){
                    btn_upNotice.setError("Please Select Any Image");
                    btn_upNotice.setFocusable(true);
                    btn_upNotice.setFocusableInTouchMode(true);
                    btn_upNotice.requestFocus();
                }else if (bitmap == null){
                    uploadData();
                }else{
                    uploadImage();
                }
            }
        });
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
        uploadTask.addOnCompleteListener(Upload_Notice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    uploadData();
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(Upload_Notice.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void uploadData() {

        reference = reference.child("Notice");
        final String uniquekey = reference.push().getKey();
        String title = upNoticeTitle.getText().toString();


        Calendar calForDate = Calendar .getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar .getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:MM a");
        String time = currentDate.format(calForTime.getTime());

        NoticeData noticeData = new NoticeData(title,Download_URL,date,time,uniquekey);

        reference.child(uniquekey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(Upload_Notice.this, "Notice Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Upload_Notice.this, "Upload Failed", Toast.LENGTH_SHORT).show();
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
            noticeImgView.setImageBitmap(bitmap);
        }
    }
}