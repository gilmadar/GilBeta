package com.example.gilbeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import static com.example.gilbeta.FBref.refUpload;

public class moser_dog extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String Breed, SizeDog, City, Age, FullName, PhoneNumber;
    Boolean tame = true, Vaccinated = true;
    RadioButton rbYes,rbNo,rbYes2,rbNo2;
    EditText etCity, age;
    User us;
    Upload Upload;
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

        //FullName=us.getName();
        //PhoneNumber=us.getPhone();

        /*Spinner tame_spinner = findViewById(R.id.tame_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.yes_no, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tame_spinner.setAdapter(adapter);
        tame_spinner.setOnItemSelectedListener(this);*/

        /*Spinner vaccinated_spinner = findViewById(R.id.vaccinated_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.yes_no, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vaccinated_spinner.setAdapter(adapter);
        vaccinated_spinner.setOnItemSelectedListener(this);*/


        Spinner size_spinner = findViewById(R.id.size_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size_spinner.setAdapter(adapter2);
        size_spinner.setOnItemSelectedListener(this);

        // קורא בספינר "קטן/בינוני/גדול


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
        Breed = data.getStringExtra("Breed");
        Toast.makeText(this, Breed, Toast.LENGTH_SHORT).show();
        // מקבל מאקטיביטי "Breed_Choos" מה הוא הגזע של הכלב ושומר במשתנה "Breed"
    }

    public void read(View view) {
        if (rbYes.isChecked()) {
            tame = true;
        } else {

                tame = false;

        }

        /*if(tame){
            Toast.makeText(this, "yesssss", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "nooo", Toast.LENGTH_LONG).show();
        }*/
        if(!rbYes.isChecked() && !rbNo.isChecked())
            Toast.makeText(this, "You must mark whether the dog is tame or not", Toast.LENGTH_LONG).show();

        if (rbYes2.isChecked()) {
            Vaccinated = true;
        } else {

            Vaccinated = false;

        }

        /*if(Vaccinated){
            Toast.makeText(this, "yesssss", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "nooo", Toast.LENGTH_LONG).show();
        }*/
        if(!rbYes2.isChecked() && !rbNo2.isChecked())
            Toast.makeText(this, "You must mark whether the dog is vaccinated or not", Toast.LENGTH_LONG).show();

        City = etCity.getText().toString();
        Age = age.getText().toString();

        Upload=new Upload( Breed, SizeDog, City, tame, Vaccinated, Age);
        refUpload.child("Breed").child(Breed).setValue(Upload);
        Toast.makeText(this, "Successful registration", Toast.LENGTH_LONG).show();



    }
}
