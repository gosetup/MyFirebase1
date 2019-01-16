package com.goset.adoni.myfirebase;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class UploadPDF extends AppCompatActivity {
    Button select , upload;
    TextView selectedfile;
    Uri pdfUri;  // uri are actually URLs that are meant for local storage

    FirebaseDatabase database; // used to store URL of upload file.
    FirebaseStorage storage;   // used for upload files  (pdf)..
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        select = (Button)findViewById(R.id.selectfile);
        upload = (Button)findViewById(R.id.upload);
        selectedfile = (TextView) findViewById(R.id.textView6);

        storage = FirebaseStorage.getInstance(); //return an object of firebase storage
        database =FirebaseDatabase.getInstance(); //return an object of firebase database


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission( UploadPDF.this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

                    selectPdf();
                }
                else
                    ActivityCompat.requestPermissions(UploadPDF.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdfUri != null) //the user has selected the file
                    uploadFile(pdfUri);
                else
                    Toast.makeText(UploadPDF.this,"Select a File",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadFile(Uri pdfUri) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file......");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = System.currentTimeMillis()+"";
        StorageReference storageReference = storage.getReference(); // return root path

        storageReference.child("Uploads").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String url = taskSnapshot.toString();//return the url of you uploaded file.....
                        //store the url in realtime database.......
                        DatabaseReference reference =database.getReference(); // return the path root.....

                        reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(UploadPDF.this,"File Successfully Uploaded!!!",Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(UploadPDF.this,"Upload Fail!!!",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UploadPDF.this,"Upload Fail!!!",Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                // track the progress of = our upload....
                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress  );


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }
        else
            Toast.makeText(UploadPDF.this,"Please provide permission..." , Toast.LENGTH_SHORT).show();


    }

    private void selectPdf() {

        //to offer user to select a file using file manager

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT); // to fetch file
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //check whether user has selected a file or not (pdf)

        if(requestCode == 86 && resultCode == RESULT_OK && data !=null){

            pdfUri = data.getData(); //return the uri of selected file...
            selectedfile.setText("A File is Selected :" +data.getData().getLastPathSegment());

        }
        else {
            Toast.makeText(UploadPDF.this,"Please select a file",Toast.LENGTH_SHORT).show();

        }
    }
}
