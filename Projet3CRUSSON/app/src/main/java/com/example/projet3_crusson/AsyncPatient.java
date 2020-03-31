package com.example.projet3_crusson;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncPatient extends AsyncTask<String, String, Boolean> {
    private WeakReference<Activity> activityAppelante = null;
    private String classActivityAppelante;

    private StringBuilder stringBuilder = new StringBuilder();
    private String demande;

    public AsyncPatient() {

    }

    public AsyncPatient(Activity pActivity) {
        activityAppelante = new WeakReference<Activity>(pActivity);
        classActivityAppelante = pActivity.getClass().toString();
    }
    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            ((ActImport) activityAppelante.get()).retourImportPlus(stringBuilder, demande);
        } else {
            Toast.makeText((activityAppelante.get()), "Fin ko retour patient",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected Boolean doInBackground(String... params) {// Exécution en arrière plan
        String vurl = "";
        vurl = params[0];
        demande = params[1];
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(vurl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(4000);
            // récupération du serveur
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
                br.close();
            } else {
                String[] vstring0 = {"Erreur",
                        urlConnection.getResponseMessage()};
                publishProgress(vstring0);
            }
        } catch (MalformedURLException e) {
            String[] vstring0 = {"Erreur", "Pbs url"};
            publishProgress(vstring0);
            return false;
        } catch (java.net.SocketTimeoutException e) {
            String[] vstring0 = {"Erreur", "temps trop long"};
            publishProgress(vstring0);
            return false;
        } catch (IOException e) {
            String[] vstring0 = {"Erreur", "Pbs IO->".concat(e.getMessage())};
            publishProgress(vstring0);
            return false;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return true;
    }
    @Override
    protected void onProgressUpdate(String... param) {
        // utilisation de on progress pour afficher des message pendant le doInBackground
        //Toast.makeText(getApplicationContext(), param[1].toString(),Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCancelled() {
        //Toast.makeText(getApplicationContext(), "Annulation patient", Toast.LENGTH_SHORT).show();
    }
}

