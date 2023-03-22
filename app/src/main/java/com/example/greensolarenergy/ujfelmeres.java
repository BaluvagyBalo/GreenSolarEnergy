package com.example.greensolarenergy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.media.session.MediaController;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ujfelmeres extends AppCompatActivity {

    ListView inverter, panel;
    DatabaseHandler databaseHandler;
    TextView osszesen, kabel,inverterar,panelar;
    EditText csaladnev,paneldarab;
    Button ajanlat;

    ArrayList<String> listItem2, listItem3;
    ArrayAdapter adapter2,adapter3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujfelmeres);
        //adatbazis kapcsolat
        databaseHandler =new DatabaseHandler(this);
        inverter=findViewById(R.id.inverterlista);
        panel=findViewById(R.id.panellista);
        osszesen= findViewById(R.id.osszesen);
        csaladnev= findViewById(R.id.csaladnav);
        ajanlat= findViewById(R.id.ajanlat);
       //szamolas

        paneldarab=findViewById(R.id.paneldarab);
        panelar=findViewById(R.id.panellerarai);//
        inverterar=findViewById(R.id.inverterar);//inverter csak 1 van rendszerhez illően
        kabel=findViewById(R.id.rogzitokkabelar);


        listItem2 = new ArrayList<>();
        listItem3 = new ArrayList<>();
        solarData();
        inverterData();


       // int darab= Integer.valueOf(paneldarab.getText().toString());

    panel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position, long id ) {



                String arak = adapter2.getItem(position).toString();
                String[] separated= arak.split("Price:");

          //      int dbar= Integer.parseInt(paneldarab.getText().toString());

                String uj= separated[1];

             //   Integer egydar= Integer.valueOf(uj);
               // int sorsz_uj=Integer.parseInt(uj);
     //  panelar.setText(dbar+"");

           //     int o=(egydar)*(dbar);
               panelar.setText(uj);

            }
        });



        inverter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position, long id ) {


                //       Cursor cursor = (Cursor) adapter2.getItem(position)//
                // string arak = cursor.getString( 1);

                String arak2 = adapter3.getItem(position).toString();
                String[] separated2= arak2.split("Price:");



                String uj2= separated2[1];

                inverterar.setText(uj2);

            }
        });


        ajanlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.addMegrendeles(csaladnev.getText().toString().trim(),getCurrentDate(),Integer.valueOf(osszesen.getText().toString().trim()));
            }
        });

      //  String h= panelid.getText().toString();
     //   int p= Integer.valueOf(panelid.getText().toString());
      //  int i=2;
       // int number= panalarint*i;
     //   panelar.setText((p+""));

        int kabelar= 110;
        kabel.setText(kabelar+"");

     /*   int pan= Integer.valueOf(panelar.getText().toString());
        int in= Integer.valueOf(inverterar.getText().toString());
        int vege= kabelar+pan+in;*/

        osszesen.setText(kabelar+"" );




    }


    public String getCurrentDate() {
        // Get the current date
        Date currentDate = new Date();

        // Define the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the date as a string
        String dateString = dateFormat.format(currentDate);

        // Return the formatted date string
        return dateString;
    }

    public void solarData(){
        Cursor cur= databaseHandler.solarData();

        if(cur.getCount() == 0){
            Toast.makeText(this,"NINCS ADAT",Toast.LENGTH_SHORT).show();

        }else{
            while(cur.moveToNext()){

                listItem2.add("ID: "+ cur.getString(0)+" "+cur.getString(1)+" Darab: "+cur.getString(2)+" Price: "+cur.getString(3));

            }
            adapter2= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem2);
            panel.setAdapter(adapter2);
        }
    }

    public void inverterData(){
        Cursor cur= databaseHandler.inverterData();

        if(cur.getCount() == 0){
            Toast.makeText(this,"NINCS ADAT",Toast.LENGTH_SHORT).show();

        }else{
            while(cur.moveToNext()){

                listItem3.add("ID: "+ cur.getString(0)+" "+cur.getString(1)+" Darab: "+cur.getString(2)+" Price: "+cur.getString(3));

            }
            adapter3= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem3);
            inverter.setAdapter(adapter3);
        }
    }

}