package com.example.projet2_crusson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Intent i;
    private Random r;
    private int choix,valeurMin=1,valeurMax=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("P2","onCreate");

        Button act1=(Button) findViewById(R.id.btnAct1);
        act1.setOnClickListener(btnclick);

        //Génère
        Button galea=(Button) findViewById(R.id.gAlea);
        galea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r = new Random();
                choix = valeurMin + r.nextInt(valeurMax - valeurMin+1);
            }
        });

        //Affiche
        Button aalea=(Button) findViewById(R.id.aAlea);
        aalea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),    String.valueOf(choix),Toast.LENGTH_SHORT).show();
            }
        });

    }

        //Ajout TP n°2
    @Override
    public void onStart() {
        super.onStart();Log.w("P2", "onStart"); }
    @Override
    public void onRestart() {
        super.onRestart();Log.w("P2", "onRestart"); }
    @Override
    public void onResume() {
        super.onResume();Log.w("P2", "onResume"); }

    @Override
    public void onPause() {
        super.onPause();Log.w("P2", "onPause"); }
    @Override
    public void onStop() {
        super.onStop();Log.w("P2", "onStop"); }
    @Override
    public void onDestroy() {
        super.onDestroy();Log.w("P2", "onDestroy "); }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
// save

        savedInstanceState.putInt("choixGen",choix);
        Log.w("P2", "onSaveInstanceState ");
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        choix = savedInstanceState.getInt("choixGen");
        // Restore
        Log.w("P2", "onRestoreInstanceState ");
    }



    //Event Click
    private OnClickListener btnclick = new OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAct1:
                    //Toast.makeText(getApplicationContext(),    "clic sur act1",Toast.LENGTH_SHORT).show();
                    i = new Intent(getApplicationContext(), Act1.class);
                    startActivity(i);

                    break;
            }
        }
    };
}
