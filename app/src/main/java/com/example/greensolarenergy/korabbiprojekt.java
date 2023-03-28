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

import java.util.ArrayList;

public class korabbiprojekt extends AppCompatActivity {

    ListView listkorabbip;
    DatabaseHandler databaseHandler;

    ArrayList<String> listItem;
    ArrayAdapter adapter;
    Button toroltbtnkorabb;
    EditText idmegad;
    SharedPreferences sharedPref_k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korabbiprojekt);

        //adatbazis kapcsolat
         databaseHandler =new DatabaseHandler(this);

         toroltbtnkorabb=findViewById(R.id.torolbtnkorabb);
         idmegad=findViewById(R.id.idmegad);

        listItem = new ArrayList<>();
        listkorabbip=findViewById(R.id.projekt);

        //jogosultsagok
        sharedPref_k = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String Userf = sharedPref_k.getString(getString(R.string.last_user_key), "");

        if(Userf.equals("felmero")){
            toroltbtnkorabb.setEnabled(false);
        }


        viewData();

        toroltbtnkorabb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.deleteMegrendelesek(Integer.valueOf(idmegad.getText().toString().trim()));
            }
        });

    }


    public void viewData(){
        Cursor cursor= databaseHandler.viewData();

        if(cursor.getCount() == 0){
            Toast.makeText(this,"NINCS ADAT",Toast.LENGTH_SHORT).show();

        }else{
            while(cursor.moveToNext()){

               listItem.add("ID: "+ cursor.getString(0)+" Name: "+cursor.getString(1)+" Datum: "+cursor.getString(2)+" Price: "+cursor.getString(3));

            }
            adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
            listkorabbip.setAdapter(adapter);
        }
    }
}