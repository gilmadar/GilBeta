    package com.example.gilbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.gilbeta.FBref.refUpload;

    public class mehmezh_dog extends AppCompatActivity implements AdapterView.OnItemClickListener{

        ListView lv;
        ArrayList<String> als = new ArrayList<>();
        ArrayList<Upload> alupload = new ArrayList<>();
        long count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mehmezh_dog);

        lv = findViewById(R.id.lv);
        ValueEventListener uploadlitner = new ValueEventListener (){


            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                als.clear();
                alupload.clear();
                for(DataSnapshot data : ds.getChildren()){
                    String UID =  (String) data.getKey();
                    Upload upload = data.getValue(Upload.class);
                    alupload.add(upload);
                    String Breed = upload.getBreed();
                    String size = upload.getSizeDog();
                    String age = upload.getAge();
                    String DogName = upload.getDogName();
                    als.add(DogName + " , " + Breed + " , " + size + " , " + age );
                }
                ArrayAdapter<String> adp = new ArrayAdapter<String>(mehmezh_dog.this, R.layout.support_simple_spinner_dropdown_item, als);
                lv.setOnItemClickListener(mehmezh_dog.this);
                lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                lv.setAdapter(adp);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("mehmezh_dog", "Failed to read value", databaseError.toException());
            }
        };
        refUpload.addValueEventListener(uploadlitner);
    }


        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
           // Toast.makeText(this, " " +position, Toast.LENGTH_LONG).show();

            Upload up = alupload.get(position);
            //Toast.makeText(this, " " + up.getBreed(), Toast.LENGTH_LONG).show();
            Intent dt = new Intent(this, DogDetails.class);





            dt.putExtra("Breed", up.getBreed());
            dt.putExtra("Age", up.getAge());
            dt.putExtra("City", up.getCity());
            dt.putExtra("Description", up.getDescription());
            dt.putExtra("DogName", up.getDogName());
            dt.putExtra("Email", up.getEmail());
            dt.putExtra("FullName", up.getFullName());
            dt.putExtra("PhoneNumber", up.getPhoneNumber());
            dt.putExtra("SizeDog", up.getSizeDog());
            dt.putExtra("tame", up.gettame());
            dt.putExtra("Vaccinated", up.getVaccinated());
            dt.putExtra("count", up.getSerialNumbe());

            startActivity(dt);

        }



}
