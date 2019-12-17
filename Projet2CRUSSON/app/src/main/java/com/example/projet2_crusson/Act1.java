package com.example.projet2_crusson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Act1 extends AppCompatActivity {

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
    }
}
