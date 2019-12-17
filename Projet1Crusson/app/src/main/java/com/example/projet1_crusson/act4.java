package com.example.projet1_crusson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;


public class act4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act4);

        Button finok=(Button) findViewById(R.id.ok);
        Button fincancel=(Button) findViewById(R.id.cancel);

        finok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String age=((EditText)findViewById(R.id.etAct4Age)).getText().toString();
                int iage=Integer.parseInt(age);
                String numf=((EditText)findViewById(R.id.etAct4NumFetiche)).getText().toString();
                long lfetiche=Long.parseLong(numf);
                Intent intent = new Intent ();
                intent.putExtra ("string_1", ((EditText)findViewById(R.id.etAct4Nom)).getText().toString());
                intent.putExtra ("string_2", ((EditText)findViewById(R.id.etAct4Pnom)).getText().toString());
                intent.putExtra ("int_1", iage);
                intent.putExtra ("long_1", lfetiche);

                setResult (Activity.RESULT_OK,intent);
                finish();

            }
        });

        fincancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult (Activity.RESULT_CANCELED);
                finish();

            }
        });


    }
}
