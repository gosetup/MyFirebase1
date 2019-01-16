package com.goset.adoni.myfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UploadAnswer extends AppCompatActivity {

    private EditText ans1,ans2,ans3,ans4;

    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference mdatareference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_answer);


        ans1 =(EditText)findViewById(R.id.etans1);
        ans2 =(EditText)findViewById(R.id.etans2);
        ans3 =(EditText)findViewById(R.id.etans3);
        ans4 = (EditText)findViewById(R.id.etans4);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        mdatareference = FirebaseDatabase.getInstance().getReference().child("Answers");
    }

    public void uploadanswer(View view) {

        String getans1 = ans1.getText().toString().trim();
        String getans2 = ans2.getText().toString().trim();
        String getans3 = ans3.getText().toString().trim();
        String getans4 = ans4.getText().toString().trim();



        HashMap<String, String> datamap = new HashMap<String, String>();
        datamap.put("Answer1", getans1);
        datamap.put("Answer2", getans2);
        datamap.put("Answer3", getans3);
        datamap.put("Answer4", getans4);

        mdatareference.push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UploadAnswer.this, "Successfully Insert!!!", Toast.LENGTH_SHORT).show();
                    ans1.setText("");
                    ans2.setText("");
                    ans3.setText("");
                    ans4.setText("");
                } else {
                    Toast.makeText(UploadAnswer.this, "Insertion Failed!!!", Toast.LENGTH_SHORT).show();
                    ans1.setText("");
                    ans2.setText("");
                    ans3.setText("");
                    ans4.setText("");
                }
            }
        });
    }

    }
