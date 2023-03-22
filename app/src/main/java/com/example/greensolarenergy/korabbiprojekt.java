package com.example.greensolarenergy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class korabbiprojekt extends AppCompatActivity {

    ListView listkorabbip;
    DatabaseHandler databaseHandler;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korabbiprojekt);

        //adatbazis kapcsolat
         databaseHandler =new DatabaseHandler(this);


        listItem = new ArrayList<>();
        listkorabbip=findViewById(R.id.projekt);

        viewData();


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