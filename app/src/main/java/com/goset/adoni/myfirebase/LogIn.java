package com.goset.adoni.myfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private TextView forgotpassword;
    private FirebaseAuth auth;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        // set the view now
        setContentView(R.layout.activity_log_in);

        auth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        inputEmail =(EditText)findViewById(R.id.etmail);
        inputPassword = (EditText)findViewById(R.id.etPass);
        btnLogin = (Button)findViewById(R.id.btnlogin);
        forgotpassword = (TextView)findViewById(R.id.forgot);
    }

    public void forgot(View view){
        Intent intent = new Intent(this,ForgotPassword.class);
        startActivity(intent);


    }


    public void login(View view){

        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LogIn.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(LogIn.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });



    }
}
