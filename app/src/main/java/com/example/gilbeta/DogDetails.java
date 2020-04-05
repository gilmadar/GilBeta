package com.example.gilbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//import static com.example.gilbeta.FBref.storageReference;


import static com.example.gilbeta.FBref.refUpload;

public class DogDetails extends AppCompatActivity {

     String Breed, SizeDog, City, FullName, PhoneNumber, Age, Email, Description, DogName;
     boolean tame, Vaccinated;
     ImageView imageView;
     long count;
     public Uri imguri;

     TextView tvBreed, tvSize, tvCity, tvAge, tvDescription, tvDogName, tvtame, tvVaccinated;

    LinearLayout mydialog;
    AlertDialog.Builder alert;


    ArrayList<String> als = new ArrayList<>();
    ArrayList<Upload> alupload = new ArrayList<>();


    // היקלי
    StorageReference mStorageRef;
    public static StorageReference Ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_details);
        imageView = findViewById(R.id.imageView);



        tvBreed = findViewById(R.id.tvBreed);
        tvSize = findViewById(R.id.tvSize);
        tvCity = findViewById(R.id.tvCity);
        tvAge = findViewById(R.id.tvAge);
        tvDescription = findViewById(R.id.tvDescription);
        tvDogName = findViewById(R.id.tvDogName);
        tvtame = findViewById(R.id.tvtame);
        tvVaccinated = findViewById(R.id.tvVaccinated);

        Intent dt = getIntent();
        Breed = dt.getStringExtra("Breed");
        SizeDog = dt.getStringExtra("SizeDog");
        City = dt.getStringExtra("City");
        FullName = dt.getStringExtra("FullName");
        PhoneNumber = dt.getStringExtra("PhoneNumber");
        Age = dt.getStringExtra("Age");
        Email = dt.getStringExtra("Email");
        DogName = dt.getStringExtra("DogName");
        Description = dt.getStringExtra("Description");
        tame = dt.getBooleanExtra("tame", false);
        Vaccinated = dt.getBooleanExtra("Vaccinated", false);
        count = dt.getLongExtra("count", 777);




        tvDogName.setText("The dog's name is " + DogName);
        tvBreed.setText("The dog breed is " + Breed);
        tvSize.setText("The size of the dog is " + SizeDog);
        tvCity.setText("The dog is in " + City);
        tvAge.setText("The dog's age is " + Age);
        tvDescription.setText("More details about the dog: " + Description);
        if (tame) {
            tvtame.setText("The dog is tame");
        } else {
            tvtame.setText("The dog is not tame");
        }
        if (Vaccinated) {
            tvVaccinated.setText("The dog is vaccinated");
        } else {
            tvVaccinated.setText("The dog is not vaccinated");
        }
        // היקלי
        mStorageRef = FirebaseStorage.getInstance().getReference();
        DownloadImg();



    }




    private void DownloadImg() {// a method that downloads the url of the last added image
        //Ref = mStorageRef.child("shoshe.jpg");
        Ref = mStorageRef.child("" + count + ".jpg");
        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(DogDetails.this).load(uri).fit().centerCrop().into(imageView);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void ShowDetails(View view){

    }










    /*public void ShowDetails(View view) throws IOException {


        Toast.makeText(DogDetails.this, ""+count, Toast.LENGTH_LONG).show();
        final ProgressDialog pd=ProgressDialog.show(this,"Image download","downloading...",true);

        /*StorageReference reference = storageReference.child("" + count + ".jpg");
        final File localFile = File.createTempFile(""+count,"jpg");*/



        /*StorageReference reference = storageReference.child("shoshe.jpg");
        final File localFile = File.createTempFile("shoshe","jpg");*/



        /*reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(DogDetails.this, "Image download success", Toast.LENGTH_LONG).show();
                String filePath = localFile.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                imageView.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(DogDetails.this, "Image download failed", Toast.LENGTH_LONG).show();
            }
        });








    }*/
}
