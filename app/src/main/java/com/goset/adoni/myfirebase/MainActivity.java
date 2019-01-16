package com.goset.adoni.myfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button signin , signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signin=(Button)findViewById(R.id.sigin);
        signup = (Button)findViewById(R.id.sigup);
    }

    public void register(View view){
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);

    }

    public void login(View view){

        Intent intent = new Intent(this,LogIn.class);
        startActivity(intent);

    }
}
