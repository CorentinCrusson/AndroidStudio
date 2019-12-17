package com.example.projet1_crusson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.Toast;


public class act1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act1);

        Button fin=(Button) findViewById(R.id.fin);
        fin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();

            }
        });

        SharedPreferences myPrefs = this.getSharedPreferences("mesvariablesglobales", 0);
        String svar1 = myPrefs.getString("svar1", "nothing");
        int ivar2 = myPrefs.getInt("ivar2", 0);
        Toast.makeText(getApplicationContext(),	svar1+" "+Integer.toString(ivar2),Toast.LENGTH_SHORT).show();


    }
}
