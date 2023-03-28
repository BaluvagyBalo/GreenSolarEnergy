package com.example.greensolarenergy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    ImageButton ujfelmeres;
    ImageButton raktarkeszlet;
    ImageButton korabbip, rendel;
    TextView felhasznalo;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        ujfelmeres=findViewById(R.id.ujfelmeresbtn);
        raktarkeszlet=findViewById(R.id.raktarbtn);
        korabbip=findViewById(R.id.keszprojektbtn);
        felhasznalo=findViewById(R.id.felhasznalo);
        rendel=findViewById(R.id.rendel);

        String lastUser = sharedPref.getString(getString(R.string.last_user_key), "");
        felhasznalo.setText(lastUser+" jogosultsággal");


    //jogosultsagok
        if (lastUser.equals("felmero")) {
            raktarkeszlet.setEnabled(false);
        }

        if(lastUser.equals("raktaros") || lastUser.equals("raktarvezeto")){
            ujfelmeres.setEnabled(false);
            rendel.setEnabled(false);
        }



        ujfelmeres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity2.this,ujfelmeres.class);
                startActivity(intentLoadNewActivity);
            }
        });

        raktarkeszlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,raktar.class);
                startActivity(intent);
            }
        });

        korabbip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity2.this,korabbiprojekt.class);
                startActivity(intent2);
            }
        });

        rendel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity2.this,alkatresz_rendel.class);
                startActivity(intent3);
            }
        });
    }
}