package com.example.greensolarenergy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class alkatresz_rendel extends AppCompatActivity {

    EditText megrendelo, termek, darabsz;
    Button megrendel;
    DatabaseHandler dbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alkatresz_rendel);

        megrendelo=findViewById(R.id.megrendelo);
        termek=findViewById(R.id.termek);
        darabsz=findViewById(R.id.darabszamok);
        megrendel= findViewById(R.id.megrendel_btn);

        //dbo kapcsolat
        dbo =new DatabaseHandler(this);


        megrendel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbo.addtermekrendel(megrendelo.getText().toString().trim(),termek.getText().toString().trim(),Integer.valueOf(darabsz.getText().toString().trim()));
            }
        });


    }
}