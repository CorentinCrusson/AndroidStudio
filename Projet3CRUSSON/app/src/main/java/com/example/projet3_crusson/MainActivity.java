package com.example.projet3_crusson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.MenuItem;
import android.net.Uri;

import android.Manifest;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;


import android.os.AsyncTask;
import android.view.WindowManager;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.google.gson.JsonObject;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener{
    private Fragment leFragment;
    private ImageView limage;

    public static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;

    // appel internet
    private AsyncTask<String, String, Boolean> mThreadCon = null;

    private SharedPreferences myPrefs;
    private SharedPreferences.Editor prefsEditor;

    private Menu leMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionAlert();

        //Fragment Creation
        leFragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        leFragment.getView().setVisibility(View.GONE);
        limage = (ImageView) findViewById(R.id.imageView);
        Button bOk = (Button) leFragment.getView().findViewById(R.id.bFragOk);

        //Edit Text Fill
        String login = "fnightingale";
        EditText edtLogin = (EditText) leFragment.getView().findViewById(R.id.etFragId);
        EditText edtPass = (EditText) leFragment.getView().findViewById(R.id.etFragPassword);

        edtLogin.setText(login);
        edtPass.setText(login);

        myPrefs = this.getSharedPreferences("mesvariablesglobales", 0);
        prefsEditor = myPrefs.edit();

        prefsEditor.putString("login", md5("fnightingale"));
        prefsEditor.putString("mdp", md5("fnightingale"));
        prefsEditor.commit();


                //OnClick Button
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "clic sur ok", Toast.LENGTH_SHORT
                ).show();

                //On vérifie si le login est égal au login présent dans les shares preferences
                if(md5( ((EditText) leFragment.getView().findViewById(R.id.etFragId)).getText().toString()).intern()!=myPrefs.getString("login","nothing").intern())
                {
                    Log.d("Connexion","Connexion Avec Internet");
                    alertmsgId("Alert","Avez-vous changé de login ?");
                } else {

                    /* DebugLog pour vérififer si les informations ont bien été mis dans les shares preferences
                    Log.d("Connexion",myPrefs.getString("Infos","nothing"));  */

                    if(myPrefs.getString("Infos","nothing")!="nothing") {
                        Log.d("Connexion","Connexion sans Internet");
                        retourConnexion(null);
                    }
                    else {
                        Log.d("Connexion","Appel à l'Api");
                        api();
                    }
                }
            }
        });

        Button bCancel = (Button) leFragment.getView().findViewById(R.id.bFragCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leFragment.getView().setVisibility(View.GONE);
                limage.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        super.onResume();
        checkPermissionAlert();
        checkPermissions();
        /* VERIFICATION OVERLAY PERMISSION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(Settings.canDrawOverlays(this)) {
                Toast.makeText(getApplicationContext(),"PermissionOK",Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(getApplicationContext(),"Permission NON OK",Toast.LENGTH_SHORT).show();
            }
        }*/
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        leMenu = menu;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                Toast.makeText(getApplicationContext(), "clic sur connect", Toast.LENGTH_LONG).show();

                leFragment.getView().setVisibility(View.VISIBLE);
                limage.setVisibility(View.GONE);
                return true;
            case R.id.menu_deconnect:
                Toast.makeText(getApplicationContext(), "clic sur deconnect", Toast.LENGTH_LONG).show();

                Log.d("Connexion","Déconnexion de l'utilisateur");

                //Affichage du bouton "Se connecter" et désaffichage des autres boutons
                leMenu.getItem(0).setVisible(true);
                leMenu.getItem(1).setVisible(false);
                leMenu.getItem(2).setVisible(false);
                leMenu.getItem(3).setVisible(false);
                leMenu.getItem(4).setVisible(false);

                return true;
            case R.id.menu_list:
                Toast.makeText(getApplicationContext(), "clic sur list", Toast.LENGTH_LONG).show();

                return true;
            case R.id.menu_import:
                Toast.makeText(getApplicationContext(), "clic sur import", Toast.LENGTH_LONG).show();

                return true;
            case R.id.menu_export:

                Toast.makeText(getApplicationContext(), "clic sur export", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
        public void checkPermissionAlert() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
                }
            }
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // on regarde quelle Activity a répondu
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Settings.canDrawOverlays(this)) {
                            alertmsg("Permission ALERT","Permission OK");
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Pbs demande de permissions"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }

            }


        }

        //Gestion des permissions
    private boolean permissionOK=false;
    private static final int MULTIPLE_PERMISSIONS = 10; // code you want.
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET,Manifest.permission.READ_CONTACTS};
    private List<String> listPermissionsNeeded;

    public void demanderPermission() {
        int result;
        listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            for (String permission : listPermissionsNeeded) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission) == true) {
                    //Auorisations déja demandées et refusées
                    // explique pourquoi l'autorisation est nécessaire
                    new AlertDialog.Builder(this)
                            .setTitle("Permissions")
                            .setMessage("Les permissions internet et écriture sont nécessaires pour le bon fonctionnement de l'application")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // si ok on demande l'autorisation
                                    ActivityCompat.requestPermissions(MainActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Toast.makeText(getApplicationContext(), "Nous ne pouvons pas continuer l'application car ces permissions sont nécéssaires",Toast.LENGTH_LONG).show();

                                    // Perform Your Task Here--When No is pressed
                                    dialog.cancel();
                                }
                            }).show();
                } else {
                    // autorisations jamais demandées on demande l'autorisation
                    ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
                }
            }
        }
        else {
            // toutes les permissions sont ok
            permissionOK=true;
        }
    }
    private  void checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
        }
        else
        {
            // Toutes les permissions sont ok
            permissionOK=true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied += "\n" + per;

                        }

                    }
                    // Show permissionsDenied
                    if(permissionsDenied.length()>0) {
                        Toast.makeText(getApplicationContext(),    "Nous ne pouvons pas continuer l'application car ces permissions sont nécéssaires : \n"+permissionsDenied,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        permissionOK=true;
                    }
                }
                return;
            }
        }
    }

    //---------- Alert Msg ----------------
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

    public void alertmsgId(String title,String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Avez-vous changé de login ?")
                .setTitle("Alert");

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                prefsEditor.clear();
                prefsEditor.commit();
                api();
            }

        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                api();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.
                TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    //----------- APPEL VERS l'API ( URL bts sio ) -------------
    public void api()
    {
        String url = "http://www.btssio-carcouet.fr/ppe4/public/connect2/";
        EditText login = (EditText) findViewById(R.id.etFragId);
        EditText pass = (EditText) findViewById(R.id.etFragPassword);
        url = ((String) url).concat(login.getText().toString().trim()).concat("/").concat(pass.getText().toString().trim()).concat("/infirmiere");
        String[] mesparams = {url};
        mThreadCon = new Async(MainActivity.this).execute(mesparams);
    }

    //----------- Connexion Return --------------
    public void retourConnexion(StringBuilder sb)
    {
        String ret;
        JsonObject jobj;
        JsonElement json;

        //Si string builder est nul alors il n'a pas eu de connexion via Internet
        //Ainsi on récupère dans les shares preferences les infos

        if(sb==null) {
            Log.d("RecupInfos","Informations Récupérés depuis les shares preferences");

            ret = myPrefs.getString("Infos", "nothing");
        }
        else {
            Log.d("RecupInfos","Informations Récupérés depuis l'api");

            ret = sb.toString();

            prefsEditor.putString("Infos", ret);
            prefsEditor.commit();
        }


        json = new JsonParser().parse(ret);
        jobj = json.getAsJsonObject();

        //KO
        if (jobj.has("status"))
        {
            alertmsg("Erreur","Login/Mot De Passe ");
        }
        //OK
        else {
            ret = jobj.get("nom").getAsString()+" "+jobj.get("prenom").getAsString()+" !";

            alertmsg("Bienvenue",ret);

            leFragment.getView().setVisibility(View.INVISIBLE);
            limage.setVisibility(View.VISIBLE);

            //On Affiche le Menu
            leMenu.getItem(0).setVisible(false);
            leMenu.getItem(1).setVisible(true);
            leMenu.getItem(2).setVisible(true);
            leMenu.getItem(3).setVisible(true);
            leMenu.getItem(4).setVisible(true);
        }

    }

    //MD5 HACHAGE
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}

