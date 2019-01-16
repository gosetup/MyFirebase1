package com.goset.adoni.myfirebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class TeaSignUp extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    private EditText name ,username,email,pass;
    private Spinner specialization;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference mdatareference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_sign_up);

        name =(EditText)findViewById(R.id.etName);
        username =(EditText)findViewById(R.id.etUserName);
        email =(EditText)findViewById(R.id.etmail);
        pass = (EditText)findViewById(R.id.etPass);
        specialization = (Spinner)findViewById(R.id.spinner1);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);


        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Specialization,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        mdatareference = FirebaseDatabase.getInstance().getReference().child("Teacher");




    }

    public void addUser(View view){

        String getname = name.getText().toString().trim();
        String getuser = username.getText().toString().trim();
        String getemail = email.getText().toString().trim();
        String getpassword = pass.getText().toString().trim();
        String getspecialization = specialization.getSelectedItem().toString();


        HashMap<String,String> datamap = new  HashMap<String,String>();
        datamap.put("Name",getname);
        datamap.put("UserName",getuser);
        datamap.put("Email",getemail);
        datamap.put("Password",getpassword);
        datamap.put("Stream",getspecialization);

        mdatareference.push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(TeaSignUp.this,"Successfully Insert!!!",Toast.LENGTH_SHORT).show();
                    name.setText("");
                    username.setText("");
                    email.setText("");
                    pass.setText("");
                }
                else {
                    Toast.makeText(TeaSignUp.this,"Insertion Failed!!!",Toast.LENGTH_SHORT).show();
                    name.setText("");
                    username.setText("");
                    email.setText("");
                    pass.setText("");
                }
            }
        });



    }

    public void viewdata(View view){


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
