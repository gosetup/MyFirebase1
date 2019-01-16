package com.goset.adoni.myfirebase;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ImageUpload extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button buttonChooseImage;
    private Button ButtonUploadImage;
    private EditText editFilename;
    private TextView textshow;
    private TextView uploadans;
    private ImageView upload_image;
    private ProgressBar mprogress;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        buttonChooseImage = findViewById(R.id.btnchoose);
        ButtonUploadImage = findViewById(R.id.btnuploadimg);
        editFilename = findViewById(R.id.editimagename);
        textshow = findViewById(R.id.textshow);
        upload_image =findViewById(R.id.image_view);
        uploadans = findViewById(R.id.textanswer);
        mprogress = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("Imageupload");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Imageupload");


        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilechooser();

            }
        });

        ButtonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(ImageUpload.this,"Upload in Progress",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }

            }
        });


        textshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagesActivity();

            }
        });

    }

    private void openFilechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null){
            mImageUri= data.getData();

            Picasso.with(this).load(mImageUri).into(upload_image);
            upload_image.setImageURI(mImageUri);
        }
    }
     // get the extension from the image file
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if(mImageUri != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mprogress.setProgress(0);
                                }
                            },500);
                            Toast.makeText(ImageUpload.this,"Upload Successful",Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(editFilename.getText().toString().trim(),taskSnapshot.toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ImageUpload.this,e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mprogress.setProgress((int) progress);

                        }
                    });

        }
        else{
            Toast.makeText(this,"No File Selected" , Toast.LENGTH_SHORT).show();
        }

    }

    public void openImagesActivity(){
        Intent intent = new Intent(this,ShowImages.class);
        startActivity(intent);
    }

    public void uploadanswer(View view){
        Intent intent = new Intent(this,UploadAnswer.class);
        startActivity(intent);
    }
}
