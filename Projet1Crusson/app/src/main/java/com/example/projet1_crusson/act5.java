package com.example.projet1_crusson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

public class act5 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act5);

            //------------ GLOBAL VARIABLES ---------------------

        SharedPreferences myPrefs = this.getSharedPreferences("mesvariablesglobales", 0);
        String mapref = myPrefs.getString("mapref", "nothing");
        JsonElement vjson = new JsonParser().parse(mapref);
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        eleve vmapref=gson.fromJson(vjson,eleve.class);
        //Toast.makeText(getApplicationContext(),    vmapref.getNom()+" "+vmapref.getPrenom()+" "+new SimpleDateFormat("dd/MM/yyyy").format(vmapref.getDatenaiss()) ,Toast.LENGTH_LONG).show();


        //----------------- FILL EDIT TEXT ---------------------
        EditText editNom = (EditText) findViewById(R.id.etAct4Nom);
        editNom.setText(vmapref.getNom());

        EditText editPrenom = (EditText) findViewById(R.id.etAct4Pnom);
        editPrenom.setText(vmapref.getPrenom());

        EditText editAge = (EditText) findViewById(R.id.etAct4Age);
        editAge.setText(String.valueOf(vmapref.getAge()));

        EditText editNumFet = (EditText) findViewById(R.id.etAct4NumFetiche);
        editNumFet.setText(String.valueOf(vmapref.getNum()));


            //---------------- Declare Boutons ---------------------
        Button finok=(Button) findViewById(R.id.ok);
        Button fincancel=(Button) findViewById(R.id.cancel);

            //------------ OK STATUT ---------------------
        finok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				//VARIABLES
                String age=((EditText)findViewById(R.id.etAct4Age)).getText().toString();
                int iage=Integer.parseInt(age);
                String numf=((EditText)findViewById(R.id.etAct4NumFetiche)).getText().toString();
                int lfetiche=Integer.parseInt(numf);
				
				//METHODES INTENT				
				//METHODES INTENT				
                Intent intent = new Intent ();
                intent.putExtra ("string_1", ((EditText)findViewById(R.id.etAct4Nom)).getText().toString());
                intent.putExtra ("string_2", ((EditText)findViewById(R.id.etAct4Pnom)).getText().toString());
                intent.putExtra ("int_1", iage);
                intent.putExtra ("int_2", lfetiche);

                setResult (Activity.RESULT_OK,intent);
                finish();

            }
        });

            //-------------- CANCEL STATUT ---------------------
        fincancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult (Activity.RESULT_CANCELED);
                finish();

            }
        });
    }
}
