package com.example.greensolarenergy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;


import java.util.ArrayList;

public class korabbiprojekt extends AppCompatActivity {

    ListView listkorabbip;
    DatabaseHandler databaseHandler;

    ArrayList<String> listItem;
    ArrayAdapter adapter;
    Button toroltbtnkorabb;
    EditText idmegad;
    SharedPreferences sharedPref_k;
    Button startProjectButton;
    Button endProjectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korabbiprojekt);

        //adatbazis kapcsolat
         databaseHandler =new DatabaseHandler(this);

         toroltbtnkorabb=findViewById(R.id.torolbtnkorabb);
         startProjectButton=findViewById(R.id.start_project_button);
         endProjectButton=findViewById(R.id.end_project_button);
         idmegad=findViewById(R.id.idmegad);

        listItem = new ArrayList<>();
        listkorabbip=findViewById(R.id.projekt);

        //jogosultsagok
        sharedPref_k = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String Userf = sharedPref_k.getString(getString(R.string.last_user_key), "");


        if(Userf.equals("felmero")){
            startProjectButton.setEnabled(false);
            toroltbtnkorabb.setEnabled(false);
        } else if(Userf.equals("raktaros")){
            endProjectButton.setEnabled(false);
        } else if(Userf.equals("raktarvezeto")){
            endProjectButton.setEnabled(false);
            startProjectButton.setEnabled(false);
        }


        viewData();

        toroltbtnkorabb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Projekt törölve",Toast.LENGTH_SHORT).show();
                databaseHandler.deleteMegrendelesek(Integer.valueOf(idmegad.getText().toString().trim()));
                viewData();
            }
        });


        startProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Projekt kiválasztva kivételezéshez",Toast.LENGTH_SHORT).show();
                databaseHandler.projektinditas(Integer.valueOf(idmegad.getText().toString().trim()));
                viewData();
            }
        });

        endProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Projekt lezárva",Toast.LENGTH_SHORT).show();
                databaseHandler.projektlezaras(Integer.valueOf(idmegad.getText().toString().trim()));
                viewData();
            }
        });

    }

    public void viewData(){
        Cursor cursor = databaseHandler.viewData();

        if(cursor.getCount() == 0){
            Toast.makeText(this,"NINCS ADAT",Toast.LENGTH_SHORT).show();
        } else {
            listItem.clear(); // Törli a listItem listát
            while(cursor.moveToNext()){
                listItem.add("ID: "+ cursor.getString(0)+" Name: "+cursor.getString(1)+" Datum: "+cursor.getString(2)+" Price: "+cursor.getString(3)+" Statusz: "+cursor.getString(4));
            }
            if (adapter == null) { // Ellenőrzi, hogy az adapter null értékű-e
                adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
                listkorabbip.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged(); // Ha az adapter nem null, akkor csak értesíti, hogy az adatok megváltoztak.
            }
        }
    }
}