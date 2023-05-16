package com.example.greensolarenergy;

import androidx.appcompat.app.AlertDialog;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class raktar extends AppCompatActivity {

    EditText megnevezes, elhelyezkedes, darab, ar,id, idmodosit,armodosit;
    Button hozzaad, listaz, torolmodosit,modosit, btnlistazhasmap;
   TextView text;
   ListView lista;
    DatabaseHandler raktarDB;
    ArrayList<String> listItemraktar;
    ArrayAdapter adapterraktar;
    SharedPreferences sharedPrefr;
     int darabszam;
    private static final String PREF_NAME = "MyPrefs";
    private static final String HASHMAP_KEY = "MyHashMap";
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
        btnlistazhasmap=findViewById(R.id.rekeszbtn);

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



        //map-rekesz

        HashMap<Integer, Integer> hashMap =new HashMap<>();
        hashMap.put(1,45);
        hashMap.put(2,15);
        hashMap.put(3,54);
        hashMap.put(4,64);
        hashMap.put(5,145);
        hashMap.put(6,74);
        hashMap.put(7,95);
        hashMap.put(8,35);
        hashMap.put(9,36);
        hashMap.put(10,22);


         //   saveHashMap(raktar.this, hashMap);


        btnlistazhasmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the saved HashMap from SharedPreferences
                HashMap<Integer, Integer> savedHashMap = readHashMap(raktar.this);

                if (savedHashMap != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(raktar.this);
                    builder.setTitle("Saved Data Map");

                    // Create a string to store the data
                    StringBuilder sb = new StringBuilder();
                    for (Map.Entry<Integer, Integer> entry : savedHashMap.entrySet()) {
                        sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                    }

                    builder.setMessage(sb.toString());
                    builder.setPositiveButton("OK", null);
                    builder.show();
                } else {
                    Toast.makeText(raktar.this, "No saved HashMap found.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //rekeszvege

        hozzaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //elhelyez
                String elhelyez = elhelyezkedes.getText().toString();
                String[] rekesz_1 = elhelyez.split(",");
                String uj2 = rekesz_1[2];
                int rekesz_szam = Integer.parseInt(uj2);
                darabszam = Integer.parseInt(darab.getText().toString().trim());

                // Retrieve the saved HashMap
                HashMap<Integer, Integer> savedHashMap = readHashMap(raktar.this);

                // Check if the rekesz_szam exists in the HashMap
                if (savedHashMap.containsKey(rekesz_szam)) {
                    int availableDarabszam = savedHashMap.get(rekesz_szam);
                    if (availableDarabszam < darabszam) {
                        Toast.makeText(raktar.this, "Rekesz nem megfelelő!", Toast.LENGTH_SHORT).show();
                    } else {
                        raktarDB.addraktar(megnevezes.getText().toString().trim(),
                                Integer.valueOf(darab.getText().toString().trim()),
                                Integer.valueOf(ar.getText().toString().trim()),
                                elhelyezkedes.getText().toString().trim());

                        // Update the value in the saved HashMap
                        savedHashMap.put(rekesz_szam, availableDarabszam - darabszam);

                        // Save the updated HashMap
                        saveHashMap(raktar.this, savedHashMap);
                    }
                } else {
                    Toast.makeText(raktar.this, "Rekesz nem található a raktárban!", Toast.LENGTH_SHORT).show();
                }
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

    public static void saveHashMap(Context context, HashMap<Integer, Integer> hashMap) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String hashMapJson = gson.toJson(hashMap);

        editor.putString(HASHMAP_KEY, hashMapJson);
        editor.apply();
    }
    public static HashMap<Integer, Integer> readHashMap(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String hashMapJson = sharedPreferences.getString(HASHMAP_KEY, null);

        if (hashMapJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<Integer, Integer>>() {}.getType();
            return gson.fromJson(hashMapJson, type);
        }

        // Return an empty HashMap if no saved data is found
        return new HashMap<>();
    }



}