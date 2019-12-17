package com.example.projet1_crusson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;


public class act3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act3);

        Button finok=(Button) findViewById(R.id.ok);
        finok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult (Activity.RESULT_OK);
                finish();

            }
        });


    }
}
