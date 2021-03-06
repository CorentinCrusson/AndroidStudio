package com.example.projet3_crusson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import java.util.List;


public class AfficheListeVisite extends AppCompatActivity {
    private ListView listView;
    private List<Visite> listeVisite;
    private Modele vmodele;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_liste_visite);

        vmodele=new Modele();
        listeVisite = vmodele.listeVisite();
        listView = (ListView)findViewById(R.id.lvListe);
        VisiteAdapter visiteAdapter = new VisiteAdapter(this, listeVisite);
        listView.setAdapter(visiteAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),"Choix : "+listeVisite.get(position).getId(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
