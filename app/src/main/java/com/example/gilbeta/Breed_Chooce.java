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
    String text2;
    Intent dt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed__chooce);
        lv = (ListView) findViewById(R.id.lv);

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this, R.array.Breeds, android.R.layout.simple_spinner_item);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setAdapter(adp);
        dt = getIntent();
        /*
        מתבצע כאן הצגת סוגי הגזעים בlistview

         */
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

         text2 = parent.getItemAtPosition(position).toString();
         Toast.makeText(parent.getContext(), text2, Toast.LENGTH_SHORT).show();
         dt.putExtra("Breed", text2);
        setResult(RESULT_OK,dt);
        /*
        מתבצע בדיקה באיזה גזע המשתמש בחר ומעביר את הערך למסך שממנו המשתמש הגיע
        */
    }

    public void Back(View view) {
        finish();
    }
}
