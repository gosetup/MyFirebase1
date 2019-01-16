package com.goset.adoni.myfirebase;

import android.content.Intent;
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

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText name ,username,email,pass;
    private Spinner stream,subj1,subj2,subj3;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference mdatareference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        name =(EditText)findViewById(R.id.etName);
        username =(EditText)findViewById(R.id.etUserName);
        email = (EditText)findViewById(R.id.etmail);
        pass = (EditText)findViewById(R.id.etPass);
        stream = (Spinner)findViewById(R.id.spinner1);
        subj1 = (Spinner)findViewById(R.id.spinner2);
        subj2 =(Spinner)findViewById(R.id.spinner3);
        subj3 = (Spinner)findViewById(R.id.spinner4);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Stream,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner1 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.Subject1,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        Spinner spinner2 = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Subject2,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        Spinner spinner3 = findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,R.array.Subject3,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);


        mdatareference = FirebaseDatabase.getInstance().getReference().child("Student");


    }

    public void addUser(View view){

        String getname = name.getText().toString().trim();
        String getuser = username.getText().toString().trim();
        String getemail = email.getText().toString().trim();
        String getpassword = pass.getText().toString().trim();
        String getstream= stream.getSelectedItem().toString();
        String getsubj1= subj1.getSelectedItem().toString();
        String getsubj2= subj2.getSelectedItem().toString();
        String getsubj3 = subj3.getSelectedItem().toString();

        HashMap<String,String>datamap = new  HashMap<String,String>();
        datamap.put("Name",getname);
        datamap.put("UserName",getuser);
        datamap.put("Email",getemail);
        datamap.put("Password",getpassword);
        datamap.put("Stream",getstream);
        datamap.put("Subject1",getsubj1);
        datamap.put("Subject2",getsubj2);
        datamap.put("Subject3",getsubj3);

        mdatareference.push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this,"Successfully Insert!!!",Toast.LENGTH_SHORT).show();
                    name.setText("");
                    username.setText("");
                    email.setText("");
                    pass.setText("");
                }
                else {
                    Toast.makeText(SignUp.this,"Insertion Failed!!!",Toast.LENGTH_SHORT).show();
                    name.setText("");
                    username.setText("");
                    email.setText("");
                    pass.setText("");
                }


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void viewdata(View view){

        Intent intent = new Intent(this,TeaSignUp.class);
        startActivity(intent);
    }
}
