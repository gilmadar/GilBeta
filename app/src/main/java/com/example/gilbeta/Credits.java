package com.example.gilbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String s = item.getTitle().toString();
        Intent t;

        if (s.equals("To upload an ad")) {
            t = new Intent(this, moser_dog.class);
            startActivity(t);
        }

        if (s.equals("Look for a dog")) {
            t = new Intent(this, mehmezh_dog.class);
            startActivity(t);
        }
        if (s.equals("Profile")) {
            t = new Intent(this, profile.class);
            startActivity(t);
        }
        if (s.equals("Credits")) {
            t = new Intent(this, Credits.class);
            startActivity(t);
        }
        return super.onOptionsItemSelected(item);
    }
}
