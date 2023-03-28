package com.example.greensolarenergy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btn;
    SharedPreferences sharedPref;

    // Define the valid usernames and passwords in arrays
    String[] validUsernames = {"felmero", "raktarvezeto", "raktaros"};
    String[] validPasswords = {"1234", "12345", "123456"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=findViewById(R.id.idfelhaszn);
        password=findViewById(R.id.jelszo);
        btn=findViewById(R.id.btn);

        //sharedpref
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String lastUser = sharedPref.getString(getString(R.string.last_user_key), "");
        if (!lastUser.equals("")) {
            username.setText(lastUser);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();
                boolean isValidUser = false;


                for (int i = 0; i < validUsernames.length; i++) {
                    if (enteredUsername.equals(validUsernames[i]) && enteredPassword.equals(validPasswords[i])) {
                        isValidUser = true;
                        break;
                    }
                }


                if (isValidUser) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.last_user_key), enteredUsername);
                    editor.apply();

                    Intent intentLoadNewActivity = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intentLoadNewActivity);
                }

                else {
                    Toast.makeText(MainActivity.this,"Bejelentkezés sikertelen!",Toast.LENGTH_SHORT).show();
                  //  btn.setEnabled(false);
                }
            }
        });
    }

}