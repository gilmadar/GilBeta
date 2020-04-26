    package com.example.gilbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.gilbeta.FBref.refAuth;
import static com.example.gilbeta.FBref.refUpload;
import static com.example.gilbeta.FBref.refUsers;

    public class mehmezh_dog extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener {
        User user = new User();
        String FullName, PhoneNumber, Email;
        ListView lv;
        ArrayList<String> als = new ArrayList<>();
        ArrayList<String> alsnew = new ArrayList<>();
        Spinner size_spinner;
        ArrayAdapter<CharSequence> adapter2;

        ArrayList<Upload> alupload = new ArrayList<>();
        long count;

        RadioButton isTameSearch, notTameSearch, isVaccinatedSearch, notVaccinatedSearch;
        RadioGroup rgtame,rgvaccinated;
        String breed, size;
        boolean tame,tame3, vaccinated,vaccinated3,show,tame2 = false,vaccinated2 = false,ifbreed2 = false,ifbreed3,size2 = false, size3;
        TextView tvclear, tvsearch,tvBreed3;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mehmezh_dog);

            lv = findViewById(R.id.lv);
            tvclear = (TextView) findViewById(R.id.tvclear);
            tvsearch = (TextView) findViewById(R.id.tvsearch);
            tvBreed3 = (TextView) findViewById(R.id.tvBreed3);
            rgtame = (RadioGroup) findViewById(R.id.rgtame);
            rgvaccinated = (RadioGroup) findViewById(R.id.rgvaccinated);


            size_spinner = findViewById(R.id.size_spinner);
            adapter2 = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            size_spinner.setAdapter(adapter2);
            size_spinner.setOnItemSelectedListener(this);
            isTameSearch = findViewById(R.id.isTameSearch);
            notTameSearch = findViewById(R.id.notTameSearch);
            isVaccinatedSearch = findViewById(R.id.isVaccinatedSearch);
            notVaccinatedSearch = findViewById(R.id.notVaccinatedSearch);




            FirebaseUser firebaseUser = refAuth.getCurrentUser();
            refUsers.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user.copyUser(dataSnapshot.getValue(User.class));
                    FullName = user.getName();
                    PhoneNumber = user.getPhone();
                    Email = user.getEmail();
                    // קורא מידע מהפיירבייס
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


            ValueEventListener uploadlitner = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot ds) {
                    als.clear();
                    alupload.clear();
                    for (DataSnapshot data : ds.getChildren()) {
                        String UID = (String) data.getKey();
                        Upload upload = data.getValue(Upload.class);
                        if (upload.isAct()) {
                            alupload.add(upload);
                            String Breed = upload.getBreed();
                            String size = upload.getSizeDog();
                            //String age = upload.getAge();
                            String DogName = upload.getDogName();
                            als.add("Name:" + DogName + ",Breed:" + Breed + ",Size:"
                                    + size);

                        }

                    }
                    if (alsnew.isEmpty()) {
                        ArrayAdapter<String> adp = new ArrayAdapter<String>(mehmezh_dog.this, R.layout.support_simple_spinner_dropdown_item, als);
                        lv.setAdapter(adp);
                    }
                    else{
                        ArrayAdapter<String> adp = new ArrayAdapter<String>(mehmezh_dog.this, R.layout.support_simple_spinner_dropdown_item, alsnew);
                        lv.setAdapter(adp);
                    }

                    lv.setOnItemClickListener(mehmezh_dog.this);
                    lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("mehmezh_dog", "Failed to read value", databaseError.toException());
                }
            };
            refUpload.addValueEventListener(uploadlitner);


            //
            tvclear.setText("Clear");
            SpannableString ss = new SpannableString("Clear");
            ClickableSpan span = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    Toast.makeText(mehmezh_dog.this, "There are no ads that match your requirements", Toast.LENGTH_SHORT).show();

                    size_spinner.setAdapter(adapter2);
                    rgtame.clearCheck();
                    rgvaccinated.clearCheck();
                    size = "" ;
                    breed = "";
                    tame2 = false;
                    vaccinated2 = false;
                    ifbreed2 = false;
                    size2 = false;
                    tvBreed3.setText("To select the dog breed, click the breed button");
                    ArrayAdapter<String> adp = new ArrayAdapter<String>(mehmezh_dog.this, R.layout.support_simple_spinner_dropdown_item, als);
                    lv.setAdapter(adp);


                }
            };
            ss.setSpan(span, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvclear.setText(ss);
            tvclear.setMovementMethod(LinkMovementMethod.getInstance());

            tvsearch.setText("Search");
            SpannableString ss2 = new SpannableString("Search");
            ClickableSpan span2 = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    alsnew.clear();

                    /*if(!TextUtils.isEmpty(FromSearch.getText().toString()))
                        from = Integer.parseInt(FromSearch.getText().toString());
                    if(!TextUtils.isEmpty(FromSearch.getText().toString()))
                        to = Integer.parseInt(ToSearch.getText().toString());*/
                    if(isTameSearch.isChecked() || notTameSearch.isChecked()){
                        tame2 = true;
                        if(isTameSearch.isChecked())
                            tame = true;
                        else
                            tame = false;

                    }
                    if(isVaccinatedSearch.isChecked() || notVaccinatedSearch.isChecked()){
                        vaccinated2 = true;
                        if(isVaccinatedSearch.isChecked())
                            vaccinated = true;
                        else
                            vaccinated = false;

                    }
                    if (!size.equals("None"))
                        size2 = true;


                    for(int i = 0; i<alupload.size(); i++){
                        tame3 = true;
                        vaccinated3 = true;
                        ifbreed3 = true;
                        size3 = true;
                        show = false;
                        if(tame2){
                            if (alupload.get(i).gettame() == tame) {
                                tame3 = true;
                            }
                            else{
                                tame3 = false;
                            }
                        }
                        else {
                            show = true;
                        }


                        if(vaccinated2 ){
                            if (alupload.get(i).getVaccinated() == vaccinated) {
                                vaccinated3 = true;
                            }
                            else{
                                vaccinated3 = false;
                            }
                        }

                        if(ifbreed2 ){
                            if (breed.equals(alupload.get(i).getBreed())) {
                                ifbreed3 = true;
                            }
                            else{
                                ifbreed3 = false;
                            }
                        }

                        if(size2 ){
                            if (size.equals(alupload.get(i).getSizeDog())) {
                                size3 = true;
                            }
                            else{
                                size3 = false;
                            }
                        }

                       // if(!tame2 && !vaccinated2 && !ifbreed2 && !size2)

                        if(tame3 && vaccinated3 && ifbreed3 && size3){
                            alsnew.add("Name:" + alupload.get(i).getDogName() + ",Breed:" + alupload.get(i).getBreed() + ",Size:"
                                    + alupload.get(i).getSizeDog());
                        }

                    }



                    if (!tame2 && !vaccinated2 && !ifbreed2 && !size2){
                        Toast.makeText(mehmezh_dog.this, "You have not selected the filter", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (alsnew.isEmpty()) {
                            Toast.makeText(mehmezh_dog.this, "There are no ads that match your requirements", Toast.LENGTH_SHORT).show();
                            size_spinner.setAdapter(adapter2);
                            rgtame.clearCheck();
                            rgvaccinated.clearCheck();
                            size = "" ;
                            breed = "";
                            tame2 = false;
                            vaccinated2 = false;
                            ifbreed2 = false;
                            size2 = false;
                            tvBreed3.setText("To select the dog breed, click the breed button");
                            ArrayAdapter<String> adp = new ArrayAdapter<String>(mehmezh_dog.this, R.layout.support_simple_spinner_dropdown_item, als);
                            lv.setAdapter(adp);

                        }
                        else {
                            ArrayAdapter<String> adp = new ArrayAdapter<String>(mehmezh_dog.this, R.layout.support_simple_spinner_dropdown_item, alsnew);
                            lv.setAdapter(adp);
                        }
                    }





                }
            };
            ss2.setSpan(span2, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvsearch.setText(ss2);
            tvsearch.setMovementMethod(LinkMovementMethod.getInstance());
        }





        public void onItemClick(AdapterView<?> parent, View view, int position, long id){

            Upload up = alupload.get(position);
            up.setViewers(up.getViewers() +1);
            refUpload.child(""+up.getSerialNumbe()).setValue(up);
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

        public void back(View view) {
            finish();
        }

        public void FindBreed2(View view) {
            Intent t = new Intent(this, Breed_Chooce.class);
            startActivityForResult(t, 200);
        }

        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 200 && resultCode == RESULT_OK) {
                breed = data.getStringExtra("Breed");
                ifbreed2 = true;
                tvBreed3.setText("The breed of the dog: " + breed);
            }
        }

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            size = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), size, Toast.LENGTH_SHORT).show();
            // קורא מהספינר מה נבחר ושומר במשתנה SizeDog
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
