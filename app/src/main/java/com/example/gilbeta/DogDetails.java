package com.example.gilbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.gilbeta.FBref.refUpload;

public class DogDetails extends AppCompatActivity {

     String Breed, SizeDog, City, FullName, PhoneNumber, Age, Email, Description, DogName;
     boolean tame, Vaccinated;
     ImageView imageView;

     TextView tvBreed, tvSize, tvCity, tvAge, tvDescription, tvDogName, tvtame, tvVaccinated;

    LinearLayout mydialog;
    AlertDialog.Builder alert;


    ArrayList<String> als = new ArrayList<>();
    ArrayList<Upload> alupload = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_details);
        //imageView = findViewById(R.id.imageView);



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




    }














    public void ShowDetails(View view) throws IOException {
        View mView = getLayoutInflater().inflate(R.layout.my_dialog, null);
        alert.setView(mView);
        final AlertDialog dialog = alert.create();
        dialog.show();
        /*alert = new AlertDialog.Builder(DogDetails.this);
        alert.setTitle("asdasda");
        alert.setMessage("asdasd");
        alert.setPositiveButton("Ok", null);
        alert.setNegativeButton("Close" , null);
        alert.show();*/



    }
}
