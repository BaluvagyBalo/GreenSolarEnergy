package com.example.greensolarenergy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class raktar extends AppCompatActivity {

    EditText megnevezes, elhelyezkedes, darab, ar,id, idmodosit,armodosit;
    Button hozzaad, listaz, torolmodosit,modosit;
   TextView text;
   ListView lista;
    DatabaseHandler raktarDB;
    ArrayList<String> listItemraktar;
    ArrayAdapter adapterraktar;
    SharedPreferences sharedPrefr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raktar);

        megnevezes= findViewById(R.id.megnevez);
        elhelyezkedes= findViewById(R.id.elhelyez);
        darab= findViewById(R.id.darabszam);
        ar = findViewById(R.id.ar);
        hozzaad= findViewById(R.id.hozzaadasbtn);
        listaz =findViewById(R.id.listazasbtn);

        //modify
        idmodosit=findViewById(R.id.idmodosit);
        armodosit=findViewById(R.id.armododsit);
        modosit=findViewById(R.id.modosit);

        torolmodosit = findViewById(R.id.torolmodosbtn);
        id =findViewById(R.id.id2);
        lista=findViewById(R.id.lista);
        listItemraktar = new ArrayList<>();

        //adatbazis kapcsolat
         raktarDB =new DatabaseHandler(this);

         //jogosultsagok
        sharedPrefr = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String User = sharedPrefr.getString(getString(R.string.last_user_key), "");

        if(User.equals("raktaros")){
            hozzaad.setEnabled(false);
            torolmodosit.setEnabled(false);
            modosit.setEnabled(false);

        }




        hozzaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raktarDB.addraktar(megnevezes.getText().toString().trim(),
                        Integer.valueOf(darab.getText().toString().trim()),
                        Integer.valueOf(ar.getText().toString().trim()),
                        elhelyezkedes.getText().toString().trim());
            }
        });

        listaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   if(!adapterraktar.isEmpty()){
                    lista.setAdapter(null);
                }*/

                listraktarinArray();
            }
        });


        torolmodosit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raktarDB.deleteRaktarData( Integer.valueOf(id.getText().toString().trim()));
            }
        });

        modosit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raktarDB.modifydata(Integer.valueOf(idmodosit.getText().toString().trim()),Integer.valueOf(armodosit.getText().toString().trim()));
            }
        });


    }

    public void listraktarinArray(){
        Cursor cur= raktarDB.listraktarinArray();




        if(cur.getCount() == 0){
            Toast.makeText(this,"NINCS ADAT",Toast.LENGTH_SHORT).show();

        }else{
            while(cur.moveToNext()){

                listItemraktar.add("ID: "+ cur.getString(0)+" "+cur.getString(1)+" Darab: "+cur.getString(2)+" Price: "+cur.getString(3)+
                        " Helye: "+cur.getString(4));

            }
            adapterraktar= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItemraktar);
            lista.setAdapter(adapterraktar);
        }

    }
}