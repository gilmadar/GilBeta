package com.example.gilbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Breed_Chooce extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView lv;
    String Breedd, text2;
    int act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed__chooce);
        lv = (ListView) findViewById(R.id.lv);

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this, R.array.Breeds, android.R.layout.simple_spinner_item);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setAdapter(adp);
        //act= dt.getIntExtra("moser",999);
        //act= dt.getIntExtra("Ad",9999);

        //        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_item);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

         text2 = parent.getItemAtPosition(position).toString();
         Toast.makeText(parent.getContext(), text2, Toast.LENGTH_SHORT).show();
    }

    public void Back(View view) {
        Intent dt = getIntent();
        dt.putExtra("Breed", text2);
        setResult(RESULT_OK,dt);
        finish();
    }
}
