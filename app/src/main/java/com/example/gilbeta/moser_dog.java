package com.example.gilbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.io.File;
import java.io.IOException;
import java.util.Locale;

import static com.example.gilbeta.FBref.refUsers;

import static com.example.gilbeta.FBref.refAuth;
import static com.example.gilbeta.FBref.refUpload;

import static com.example.gilbeta.FBref.refImages;


public class moser_dog extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String Breed, SizeDog, City, Age, FullName, PhoneNumber, Email, Description;
    Boolean tame, Vaccinated;
    RadioButton rbYes,rbNo,rbYes2,rbNo2;
    EditText etCity, age, EtDescription;
    //User us;
    Upload Upload;
    User user = new User();
    int Gallery=200;
    ImageView Iv;



    // City - מיקום
    // Breed - גזע
    // tame - מאולף/לא מאולף
    // Vaccinated - מחוסן / לא מחוסן
    //SizeDog - גודל הכלב


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moser_dog);

        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);
        rbYes2 = findViewById(R.id.rbYes2);
        rbNo2 = findViewById(R.id.rbNo2);
        etCity = findViewById(R.id.etCity);
        age = findViewById(R.id.age);
        Iv = findViewById(R.id.Iv);

        EtDescription= findViewById(R.id.Description);

        Spinner size_spinner = findViewById(R.id.size_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size_spinner.setAdapter(adapter2);
        size_spinner.setOnItemSelectedListener(this);
        // קורא בספינר "קטן/בינוני/גדול

        FirebaseUser firebaseUser = refAuth.getCurrentUser();
        refUsers.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.copyUser(dataSnapshot.getValue(User.class));
                FullName = user.getName();
                PhoneNumber = user.getPhone();
                Email = user.getEmail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SizeDog = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), SizeDog, Toast.LENGTH_SHORT).show();
        // קורא מהספינר מה נבחר ושומר במשתנה SizeDog
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void FindBreed(View view) {

        Intent t = new Intent (this, Breed_Chooce.class);
        startActivityForResult(t,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100) {
            Breed = data.getStringExtra("Breed");
            Toast.makeText(this, Breed, Toast.LENGTH_SHORT).show();

        }

            if (requestCode == 200) {
                Uri file = data.getData();
                if (file != null) {
                    final ProgressDialog pd=ProgressDialog.show(this,"Upload image","Uploading...",true);
                    StorageReference refImg = refImages.child("aaa.jpg");
                    refImg.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    pd.dismiss();
                                    Toast.makeText(moser_dog.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    pd.dismiss();
                                    Toast.makeText(moser_dog.this, "Upload failed", Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "No Image was selected", Toast.LENGTH_LONG).show();
                }
            }

            }

    public void Download(View view) throws IOException {
        final ProgressDialog pd=ProgressDialog.show(this,"Image download","downloading...",true);

        StorageReference refImg = refImages.child("aaa.jpg");

        final File localFile = File.createTempFile("aaa","jpg");
        refImg.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(moser_dog.this, "Image download success", Toast.LENGTH_LONG).show();
                String filePath = localFile.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                Iv.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(moser_dog.this, "Image download failed", Toast.LENGTH_LONG).show();
            }
        });
    }




    public void Uploadd(View view) {
        Intent si = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(si, Gallery);
    }




            public void read(View view) {
        if (rbYes.isChecked()) {
            tame = true;
        } else {

                tame = false;

        }

        if(!rbYes.isChecked() && !rbNo.isChecked()) {
            Toast.makeText(this, "You must mark whether the dog is tame or not", Toast.LENGTH_LONG).show();
        }
        if (rbYes2.isChecked()) {
            Vaccinated = true;
            Toast.makeText(this, "Yes", Toast.LENGTH_LONG).show();

        } else {
            Vaccinated = false;
            Toast.makeText(this, "No", Toast.LENGTH_LONG).show();

        }

        if(!rbYes2.isChecked() && !rbNo2.isChecked())
            Toast.makeText(this, "You must mark whether the dog is vaccinated or not", Toast.LENGTH_LONG).show();

        City = etCity.getText().toString();
        Age = age.getText().toString();
        Description = EtDescription.getText().toString();

        Upload=new Upload( Breed, SizeDog, City, tame, Vaccinated, Age, FullName, PhoneNumber, Email, Description);
        refUpload.child("Breed").child(Breed).setValue(Upload);
        //Toast.makeText(this, "Successful registration", Toast.LENGTH_LONG).show();
    }
}
