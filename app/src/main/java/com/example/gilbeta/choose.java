package com.example.gilbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
    }

    public void moser(View view) {
       Intent t = new Intent(this, moser_dog.class);
        startActivity(t);


    }

    public void mehmezh(View view) {
        Intent t = new Intent(this, mehmezh_dog.class);
        startActivity(t);
    }
}
