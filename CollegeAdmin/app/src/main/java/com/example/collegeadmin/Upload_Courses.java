package com.example.collegeadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Upload_Courses extends AppCompatActivity {
    private CardView addpdf;
    private  final int REQ = 1;
    private Uri pdfData;
    private EditText pdfTitle;
    private Button btn_upPdf;
    private com.google.firebase.database.DatabaseReference DatabaseReference;
    private StorageReference storageReference;
    String Download_URL= "";
    private ProgressDialog pd;
    private TextView txtpdf;
    private String pdf_Name,title;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_courses);


        pd = new ProgressDialog(this);
        
        DatabaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        
       txtpdf = findViewById(R.id.txtpdf);
        addpdf = findViewById(R.id.addPdf);
        pdfTitle = findViewById(R.id.pdfTitle);
        btn_upPdf = findViewById(R.id.btnUpPdf);

        addpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        btn_upPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = pdfTitle.getText().toString();
                if (title.isEmpty()){
                    pdfTitle.setError("Empty");
                    pdfTitle.requestFocus();
                }else if (pdfData == null){
                    Toast.makeText(Upload_Courses.this, "Please Upload PDF", Toast.LENGTH_SHORT).show();
                }else{
                    uploadPDF();
                }

            }
        });
        
    }

    private void uploadPDF() {
        pd.setTitle("Please Wait...");
        pd.setMessage("Uploading PDF");
        pd.show();
        StorageReference reference = storageReference.child("pdf/"+pdf_Name+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                txtpdf.setText("Select PDF File");
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri = uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                txtpdf.setText("Select PDF File");
                Toast.makeText(Upload_Courses.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData(String download_URL) {
        String uniqueKey = DatabaseReference.child("pdf").push().getKey();
        HashMap data = new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",download_URL);

        DatabaseReference.child("pdf").child(uniqueKey).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                txtpdf.setText("Select PDF File");
                Toast.makeText(Upload_Courses.this, "PDF Upload Successfully", Toast.LENGTH_SHORT).show();
                pdfTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override

            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                txtpdf.setText("Select PDF File");
                Toast.makeText(Upload_Courses.this, "Failed to Upload PDF", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void openGallery() {
       Intent intent = new Intent();
       intent.setType("*/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent,"Select PDF File"),REQ);

    }
    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {
           pdfData = data.getData();
            if (pdfData.toString().startsWith("content://")){
                Cursor cursor= null;
                try {
                    cursor = Upload_Courses.this.getContentResolver().query(pdfData,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        pdf_Name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (pdfData.toString().startsWith("file://")){
                pdf_Name=new File(pdfData.toString()).getName();
            }
            txtpdf.setText(pdf_Name);

        }
    }
}