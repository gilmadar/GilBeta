package com.example.gilbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static com.example.gilbeta.FBref.refUpload;

public class YourAd extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String DogName;
    boolean tame, Vaccinated;
    String Breed, SizeDog, City, Age, Description, UID;
    long SerialNumbe;
    EditText etCity2, age2, EtDescription2, Dog2;
    RadioButton rbYes3, rbNo3, rbYes4, rbNo4;
    TextView tvbreed2,size;
    ImageView myImage2;
    Button btn_choose2;
    AlertDialog.Builder adb;
    TextView viewcount;

    Upload upload = new Upload();
    Uri filePath;
    StorageReference mStorageRef;
    public static StorageReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_ad);
        Intent dt = getIntent();

        btn_choose2 = findViewById(R.id.btn_choose2);

        rbYes3 = findViewById(R.id.rbYes3);
        rbNo3 = findViewById(R.id.rbNo3);
        rbYes4 = findViewById(R.id.rbYes4);
        rbNo4 = findViewById(R.id.rbNo4);
        etCity2 = findViewById(R.id.etCity2);
        age2 = findViewById(R.id.age2);
        Dog2 = findViewById(R.id.Dog2);
        EtDescription2 = findViewById(R.id.Description2);
        tvbreed2 = findViewById(R.id.tvbreed2);
        size= findViewById(R.id.size);
        viewcount= findViewById(R.id.viewcount);


        myImage2 = findViewById(R.id.myImage2);
        Spinner size_spinner2 = findViewById(R.id.size_spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size_spinner2.setAdapter(adapter2);
        size_spinner2.setOnItemSelectedListener(this);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        DogName = dt.getStringExtra("DogNmae");
        Query query = refUpload
                .orderByChild("dogName")
                .equalTo(DogName);
        query.addListenerForSingleValueEvent(VEL);


        btn_choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200 && resultCode == RESULT_OK){
            Breed = data.getStringExtra("Breed");
            tvbreed2.setText("The breed of the dog: " + Breed);
        }

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                myImage2.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                e.printStackTrace();
            }

        }
    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    upload = data.getValue(Upload.class);
                }

            }
            Breed = upload.getBreed();
            SizeDog = upload.getSizeDog();
            City = upload.getCity();
            Age = upload.getAge();
            Description = upload.getDescription();
            UID = upload.getUID();
            tame = upload.gettame();
            Vaccinated = upload.getVaccinated();
            SerialNumbe = upload.getSerialNumbe();
            viewcount.setText("number viewers: " + upload.getViewers());
            Dog2.setHint("The Name Of The Dog: " + DogName);
            age2.setHint("Age: " + Age);
            EtDescription2.setHint(Description);

            size.setText("The dog size: " + SizeDog);
            if(tame)
                rbYes3.setChecked(true);
            else
                rbNo3.setChecked(true);
            if(Vaccinated)
                rbYes4.setChecked(true);
            else
                rbNo4.setChecked(true);
            etCity2.setHint("The dog is in city: " + City);
            tvbreed2.setText("The breed of the dog: " + Breed);
            DownloadImg();


        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    public void read(View view) {
        if (!TextUtils.isEmpty(Dog2.getText().toString())){
            upload.setDogName(Dog2.getText().toString());
        }
        if (!TextUtils.isEmpty(age2.getText().toString())){
            upload.setAge(age2.getText().toString());
        }
        if (!TextUtils.isEmpty(etCity2.getText().toString())){
            upload.setCity(etCity2.getText().toString());
        }
        if (!TextUtils.isEmpty(EtDescription2.getText().toString())){
            upload.setDescription(EtDescription2.getText().toString());
        }
        if (rbYes3.isChecked()) {
            upload.setTame(true);
        } else {

            upload.setTame(false);
        }
        if (rbYes4.isChecked()) {
            upload.setVaccinated(true);
        } else {

            upload.setVaccinated(false);
        }
        if(!SizeDog.equals("None"))
            upload.setSizeDog(SizeDog);
        upload.setBreed(Breed);
        refUpload.child(""+SerialNumbe).setValue(upload);
        Toast.makeText(YourAd.this, "The change successful", Toast.LENGTH_SHORT).show();




        if(filePath!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();

            StorageReference reference = mStorageRef.child("" + SerialNumbe + ".jpg");
            reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(YourAd.this, "Image Uploaded", Toast.LENGTH_LONG);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progres = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progres+"%");
                }
            });
        }
        Intent y = new Intent(YourAd.this, profile.class);
        startActivity(y);



    }

    private void DownloadImg() {// a method that downloads the url of the last added image
        Ref = mStorageRef.child("" + SerialNumbe + ".jpg");
        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(YourAd.this).load(uri).fit().centerCrop().into(myImage2);

            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();

            }
        });

    }



    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SizeDog = parent.getItemAtPosition(position).toString();
        // קורא מהספינר מה נבחר ושומר במשתנה SizeDog
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }



    public void FindBreed(View view) {
        Intent t = new Intent(this, Breed_Chooce.class);
        startActivityForResult(t, 200);
    }

    public void del(View view) {

        adb=new AlertDialog.Builder(this);
        adb.setMessage("Do you want to delete this text?");
        adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //refUpload.child(""+SerialNumbe).removeValue();
                upload.setAct(false);
                refUpload.child(""+upload.getSerialNumbe()).setValue(upload);

                Intent tt = new Intent(YourAd.this, profile.class);
                startActivity(tt);
            }
        });
        adb.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog ad = adb.create();
        ad.show();

    }

    public void back(View view) {
        Intent g = new Intent(this, profile.class);
        startActivity(g);
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

