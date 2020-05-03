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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;



import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import static com.example.gilbeta.FBref.refUsers;

import static com.example.gilbeta.FBref.refAuth;
import static com.example.gilbeta.FBref.refUpload;

//import static com.example.gilbeta.FBref.refImages;


public class moser_dog extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String Breed, SizeDog, City, Age, FullName, PhoneNumber, Email, Description = " ", DogName, UID ;
    Boolean tame, Vaccinated,photo = false;
    RadioButton isTame, notTame, isVaccinated, notVaccinated;
    EditText etCity, age, EtDescription, etDogName;
    Upload Upload;
    User user = new User();
    TextView tvBreed;

    Button btn_choose;
    ImageView imageView;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    long count ;

    // City - מיקום
    // Breed - גזע
    // tame - מאולף/לא מאולף
    // Vaccinated - מחוסן / לא מחוסן
    //SizeDog - גודל הכלב


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moser_dog);

        isTame = findViewById(R.id.isTame);
        notTame = findViewById(R.id.notTame);
        isVaccinated = findViewById(R.id.isVaccinated);
        notVaccinated = findViewById(R.id.notVaccinated);
        etCity = findViewById(R.id.etCity);
        age = findViewById(R.id.age);
        etDogName = findViewById(R.id.etDogName);
        tvBreed = findViewById(R.id.tvBreed);



        btn_choose = findViewById(R.id.btn_choose);
        imageView = findViewById(R.id.myImage);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        EtDescription = findViewById(R.id.Description);

        Spinner size_spinner = findViewById(R.id.size_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size_spinner.setAdapter(adapter2);
        size_spinner.setOnItemSelectedListener(this);
        // מכניס לספינר ערכים "קטן/בינוני/גדול"

        FirebaseUser firebaseUser = refAuth.getCurrentUser();
        refUsers.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.copyUser(dataSnapshot.getValue(User.class));
                FullName = user.getName();
                PhoneNumber = user.getPhone();
                Email = user.getEmail();
                UID = user.getUid();
                // קורא מידע מהפיירבייס את הפרטים האישיים של המשתמש מUser
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

    } // סוגר oncreate

    private void chooseImage() {
       Intent intent = new Intent();
       intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
       /*
       מתבצע פתיחה של "מסך" הגלריה
        */
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Breed = data.getStringExtra("Breed");
            tvBreed.setText("The breed of the dog: " + Breed);
            Toast.makeText(this, Breed, Toast.LENGTH_SHORT).show();
            /*
            מקבל ממסך בחירת הגזע את הגזע שבחר המשתמש, שומר אותו ומציג אותו למשתמש בToast
             */

        }

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                photo = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
            מציג למשתמש בimageview את התמונה שבחר במסך הגלריה
             */

        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SizeDog = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), SizeDog, Toast.LENGTH_SHORT).show();
        // קורא מהספינר את גודל הכלב שבחר המשתמש ושומר את הערךר במשתנה SizeDog
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void FindBreed(View view) {

        Intent t = new Intent(this, Breed_Chooce.class);
        startActivityForResult(t, 100);
        // מעביר את המשתמש למסך בחירת הגזע
    }


    public void read(View view) {
        /*
        פעולה זאת ראשית בודקץ האם המשתמש מילא את כל השדות שחובה, אם כן מתבצע העלה לפייר בייס
        בהעלה לפיירביס יש 2 אופציות:
        א. אם לא קיים מודעות אז מספר הסידורי של המודעה מקבל 1
        ב. אם קיים מודעות יש בדיקה כמה מודעות קיימות ונותן למודעה שמועלה את המס הסידורי המתאים
        בנוסף יש העלה לfirebase storage
        אם המשתמש לא מילא את כל השדות שחובה קופץ לו toast שעליו למלא את כל השדות
         */
        if (isTame.isChecked()) {
            tame = true;
        } else {

            tame = false;

        }

        if (!isTame.isChecked() && !notTame.isChecked()) {
            Toast.makeText(this, "You must mark whether the dog is tame or not", Toast.LENGTH_LONG).show();
        }
        if (isVaccinated.isChecked()) {
            Vaccinated = true;
        } else {
            Vaccinated = false;
        }

        if (!isVaccinated.isChecked() && !notVaccinated.isChecked())
            Toast.makeText(this, "You must mark whether the dog is vaccinated or not", Toast.LENGTH_LONG).show();

        City = etCity.getText().toString();
        Age = age.getText().toString();
        Description = EtDescription.getText().toString();
        DogName = etDogName.getText().toString();

        if(TextUtils.isEmpty(City) || TextUtils.isEmpty(Age) || TextUtils.isEmpty(Breed) ||
                (!isVaccinated.isChecked() && !notVaccinated.isChecked()) || (!isTame.isChecked() && !notTame.isChecked()) ||  SizeDog.equals("None") || photo==false){
            Toast.makeText(this, "You must fill in all fields", Toast.LENGTH_LONG).show();
        }
        else {

            DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
            mDatabaseRef.child("Upload").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        count = dataSnapshot.getChildrenCount();
                        count = count + 1;
                        Upload = new Upload(Breed, SizeDog, City, tame, Vaccinated, Age, FullName, PhoneNumber, Email, Description, DogName, UID,count, true, 0);
                        refUpload.child(""+count).setValue(Upload);
                        Toast.makeText(moser_dog.this, "Successful upload", Toast.LENGTH_LONG).show();
                        Intent it = new Intent(moser_dog.this, profile.class);
                        startActivity(it);
                    }
                    else{
                        count = 1;
                        Upload = new Upload(Breed, SizeDog, City, tame, Vaccinated, Age, FullName, PhoneNumber, Email, Description, DogName, UID,count, true, 0);
                        refUpload.child(""+count).setValue(Upload);
                        Toast.makeText(moser_dog.this, "Successful upload", Toast.LENGTH_LONG).show();
                        Intent it2 = new Intent(moser_dog.this, profile.class);
                        startActivity(it2);

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });




        }






        if(filePath!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
            mDatabaseRef.child("Upload").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        count = dataSnapshot.getChildrenCount();
                        count = count + 1;

                        StorageReference reference = storageReference.child("" + count + ".jpg");

                        reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(moser_dog.this, "Image Uploaded", Toast.LENGTH_LONG);
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                            double progres = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progres+"%");

                        }
                    });

                    }
                    else{
                        count = 1;
                        StorageReference reference = storageReference.child("" + count + ".jpg");
                        reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(moser_dog.this, "Image Uploaded", Toast.LENGTH_LONG);
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                                double progres = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progres+"%");

                            }
                        });

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }
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