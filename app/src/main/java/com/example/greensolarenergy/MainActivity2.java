package com.example.greensolarenergy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity2 extends AppCompatActivity {

    ImageButton ujfelmeres;
    ImageButton raktarkeszlet;
    ImageButton korabbip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ujfelmeres=findViewById(R.id.ujfelmeresbtn);
        raktarkeszlet=findViewById(R.id.raktarbtn);
        korabbip=findViewById(R.id.keszprojektbtn);

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
    }
}