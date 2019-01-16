package com.goset.adoni.myfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SelectCategory extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner selectcate;

    private DatabaseReference mdatareference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);


        selectcate = (Spinner)findViewById(R.id.selectcategory);


        Spinner spinner = findViewById(R.id.selectcategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.SelectCategory,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
