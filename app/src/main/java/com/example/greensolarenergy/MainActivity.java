package com.example.greensolarenergy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       username=findViewById(R.id.idfelhaszn);
        password=findViewById(R.id.jelszo);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("admin")&& password.getText().toString().equals("1234")){
                    Intent intentLoadNewActivity = new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(intentLoadNewActivity);
                }else{
                    Toast.makeText(MainActivity.this,"Bejelentkezés sikertelen!",Toast.LENGTH_SHORT).show();
                    btn.setEnabled(false);
                }
            }
        });
    }
}