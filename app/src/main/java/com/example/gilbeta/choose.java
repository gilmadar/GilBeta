package com.example.gilbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.gilbeta.FBref.refAuth;
import static com.example.gilbeta.FBref.refUpload;
import static com.example.gilbeta.FBref.refUsers;

public class choose extends AppCompatActivity {

    String UID;
    boolean newuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Intent gi=getIntent();
        newuser=gi.getBooleanExtra("newuser",false);

        FirebaseUser firebaseUser = refAuth.getCurrentUser();
        UID=firebaseUser.getUid();
        if (!newuser) {
            Query query = refUpload
                    .orderByChild("uid")
                    .equalTo(UID);
            query.addListenerForSingleValueEvent(VEL);
        }
    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                Intent tt = new Intent(choose.this, profile.class);
                startActivity(tt);
                finish();
                }
            }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    public void moser(View view) {
       Intent t = new Intent(this, moser_dog.class);
        startActivity(t);

    }

    public void mehmezh(View view) {
        Intent t = new Intent(this, mehmezh_dog.class);
        startActivity(t);
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

