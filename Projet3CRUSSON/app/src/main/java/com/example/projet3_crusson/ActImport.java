package com.example.projet3_crusson;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.Date;


public class ActImport extends AppCompatActivity {
    private Fragment leFragment;
    private ImageView limage;

    // appel internet
    private AsyncTask<String, String, Boolean> mThreadCon = null;

    private SharedPreferences myPrefs;
    private SharedPreferences.Editor prefsEditor;

    private int idPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_import);

        Button btImport=(Button) findViewById(R.id.btnImport);
        btImport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertmsg("Import ALERT","Importation");
                String url = "http://www.btssio-carcouet.fr/ppe4/public/mesvisites/3";
                String[] mesparams = {url};
                mThreadCon = new Async(ActImport.this).execute(mesparams);
                finish();

            }
        });
    }

    public void alertmsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(msg)
                .setTitle(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.
                TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    public void retourImport(StringBuilder sb)
    {
        //alertmsg("retour Connexion", sb.toString());
        ArrayList<Integer> listePatient = new ArrayList<Integer>();

        try {
            Modele vmodel = new Modele();
            JsonElement json = new JsonParser().parse(sb.toString());
            JsonArray varray = json.getAsJsonArray();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd HH:mm:ss").create();
            ArrayList<Visite> listeVisite = new ArrayList<Visite>();

            for (JsonElement obj : varray) {
                if(obj.getAsJsonObject().has("patient")) {
                    idPatient = obj.getAsJsonObject().get("patient").getAsInt();

                    if (!listePatient.contains(idPatient)) {
                        listePatient.add(idPatient);
                    }
                }
                Visite visite = gson.fromJson(obj.getAsJsonObject(), Visite.class);
                visite.setCompte_rendu_infirmiere("");
                visite.setDate_reelle(visite.getDate_prevue());
                listeVisite.add(visite);
            }
            vmodel.deleteVisite();
            vmodel.addVisite(listeVisite);

            vmodel.deletePatient();
            //recherche patients pour les visites
            for (Integer p : listePatient) {
                String[] mesparams = {"http://www.btssio-carcouet.fr/ppe4/public/personne/".concat(p.toString()), "Patient"};
                mThreadCon = new AsyncPatient().execute(mesparams);
                //Log.d("Patient", "appel"+p.toString());
            }

            vmodel.deleteVisiteSoin();
            for (Visite v : listeVisite) {
                String[] mesparams = {"http://www.btssio-carcouet.fr/ppe4/public/visitesoins/".concat(Integer.toString(v.getId())), "VisiteSoin"};
                mThreadCon = new AsyncPatient().execute(mesparams);
            }

            if (vmodel.listeSoin().size()==0) {

                String[] mesparams = {"http://www.btssio-carcouet.fr/ppe4/public/soins/", "Soin"};
                mThreadCon = new AsyncPatient().execute(mesparams);
            }

            alertmsg("Retour", "Vos informations ont bien été importé avec succès !");
        }
        catch (Exception e) {

            alertmsg("Erreur retour import", e.getMessage());

        }

    }

    public void retourImportPlus(StringBuilder sb, String demande) {

        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).serializeNulls().create();

        switch(demande) {
            case "Patient":
                Patient pat = null;
                pat.add(gson.fromJson(obj.getAsJsonObject(), Patient.class));
                break;
            case "VisiteSoin":

                break;

            case "Patient":

                break;
        }

    }




}
