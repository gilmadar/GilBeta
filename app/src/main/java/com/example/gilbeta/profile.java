package com.example.gilbeta;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.gilbeta.FBref.refAuth;
import static com.example.gilbeta.FBref.refUpload;
import static com.example.gilbeta.FBref.refUsers;

public class profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String FullName, PhoneNumber, Email, UID, DogName;
    User user = new User();

    ArrayList<Upload> alupload = new ArrayList<>();
    ArrayList<String> als = new ArrayList<>();

    EditText etname, etphone, etmail;

    Spinner spinner;
    TextView tvdog;
    User userdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvdog = findViewById(R.id.tvdog);
        spinner = findViewById(R.id.spinner);
        etmail = findViewById(R.id.etmail);
        etname = findViewById(R.id.etname);
        etphone = findViewById(R.id.etphone);


        als.add("Your Dogs");

            FirebaseUser firebaseUser = refAuth.getCurrentUser();
            UID=firebaseUser.getUid();
            Query query = refUpload
                    .orderByChild("uid")
                    .equalTo(UID);
        query.addListenerForSingleValueEvent(VEL);


        ArrayAdapter<String> adp = new ArrayAdapter<String>(profile.this, R.layout.support_simple_spinner_dropdown_item, als);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);
        spinner.setOnItemSelectedListener(this);

        refUsers.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.copyUser(dataSnapshot.getValue(User.class));
                FullName = user.getName();
                etname.setHint(FullName);
                PhoneNumber = user.getPhone();
                etphone.setHint(PhoneNumber);
                Email = user.getEmail();
                etmail.setHint(Email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

        com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                if (dS.exists()) {
                    for (DataSnapshot data : dS.getChildren()) {

                        Upload upload = data.getValue(Upload.class);
                        if (upload.isAct()){
                            alupload.add(upload);
                        }

                    }
                    for(
                            int i = 0; i<alupload.size();i++)

                    {
                        als.add(alupload.get(i).getDogName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };



    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        DogName = parent.getItemAtPosition(position).toString();
        tvdog.setText("Dog Name: "+DogName);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public void Update(View view) {

        if (TextUtils.isEmpty(etname.getText().toString())){
            FullName = etname.getHint().toString();
        }
        else{
            FullName =etname.getText().toString();
        }
        if (TextUtils.isEmpty(etphone.getText().toString())){
            PhoneNumber = etphone.getHint().toString();
        }
        else{
            PhoneNumber = etphone.getText().toString();
        }
        if (TextUtils.isEmpty(etmail.getText().toString())){
            Email = etmail.getHint().toString();
        }
        else{
            Email = etmail.getText().toString();
        }
        etname.setHint(FullName);
        etphone.setHint(PhoneNumber);
        etmail.setHint(Email);

        userdb=new User(FullName,Email,PhoneNumber,UID);
        refUsers.child(UID).setValue(userdb);

    }

    public void toMehmezh(View view) {
        Intent t = new Intent(this, mehmezh_dog.class);
        startActivity(t);
    }

    public void Details(View view) {
        if (DogName.equals("Your Dogs"))
            Toast.makeText(profile.this,"You must choose which ad you want to change", Toast.LENGTH_LONG).show();
        else {
            Intent dt = new Intent(this, YourAd.class);
            Toast.makeText(this, "" + DogName, Toast.LENGTH_SHORT).show();
            dt.putExtra("DogNmae", DogName);
            startActivity(dt);
        }

    }

    public void tomoser(View view) {
        Intent t = new Intent(this, moser_dog.class);
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
