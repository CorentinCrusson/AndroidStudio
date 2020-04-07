package com.example.projet3_crusson;
import java.util.Date;

public class Patient {

    private int id;
    private String nom;
    private String prenom;
    private char sexe;
    private Date date_naiss;
    private Date date_deces;
    private String ad1;
    private String ad2;
    private int cp;
    private String ville;
    private String tel_fixe;
    private String tel_port;
    private String mail;
    public Patient() {
    }
    public Patient(int vid,String vnom,String vprenom,char vsexe,Date vdate_naiss,Date vdate_deces,String vad1,String vad2,int vcp,String vville,String vtel_fixe,String vtel_port,String vmail) {
        id=vid;
        nom=vnom;
        prenom=vprenom;
        sexe=vsexe;
        date_naiss=vdate_naiss;
        date_deces=vdate_deces;
        ad1=vad1;
        ad2=vad2;
        cp=vcp;
        ville=vville;
        tel_fixe=vtel_fixe;
        tel_port=vtel_port;
        mail=vmail;
    }
    public void recopiePatient(Patient patient)
    {
        id=patient.getId();
        nom=patient.getNom();
        prenom=patient.getPrenom();
        sexe=patient.getSexe();
        date_naiss=patient.getDate_naiss();
        date_deces=patient.getDate_deces();
        ad1=patient.getAd1();
        ad2=patient.getAd2();
        cp=patient.getCp();
        ville=patient.getVille();
        tel_fixe=patient.getTel_fixe();
        tel_port=patient.getTel_port();
        mail=patient.getMail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public char getSexe() {
        return sexe;
    }

    public void setSexe(char sexe) {
        this.sexe = sexe;
    }

    public Date getDate_naiss() {
        return date_naiss;
    }

    public void setDate_naiss(Date date_naiss) {
        this.date_naiss = date_naiss;
    }

    public Date getDate_deces() {
        return date_deces;
    }

    public void setDate_deces(Date date_deces) {
        this.date_deces = date_deces;
    }

    public String getAd1() {
        return ad1;
    }

    public void setAd1(String ad1) {
        this.ad1 = ad1;
    }

    public String getAd2() {
        return ad2;
    }

    public void setAd2(String ad2) {
        this.ad2 = ad2;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTel_fixe() {
        return tel_fixe;
    }

    public void setTel_fixe(String tel_fixe) {
        this.tel_fixe = tel_fixe;
    }

    public String getTel_port() {
        return tel_port;
    }

    public void setTel_port(String tel_port) {
        this.tel_port = tel_port;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    /*
     *   Méthodes Spécifiques à la classe Patient
     */

    // Trouve un patient à partir de son id
    public Patient trouvePatient(int id) {
        open();
        Patient vretour = new Patient();
        vretour.setId(id);
        ObjectSet<Patient> result = dataBase.queryByExample(vretour);
        vretour = (Patient) result.next();
        dataBase.close();
        return vretour;
    }

    // deletePatient() qui permet de supprimer toutes les instances de la classe Patient
    public void deletePatient() {
        open();
        ObjectSet<Patient> result = dataBase.queryByExample(Patient.class);
        while (result.hasNext()) {
            dataBase.delete(result.next());
        }
        dataBase.close();
    }
    // addPatient(ArrayList<Patient> vPatient) qui, à partir d'une collection de Patient, va les ajouter à DB4o. Nous ne testerons pas l'existence de ces objets puisque c'est une création (appel de la méthode après l'appel de deletePatient()).
    public void addPatient(ArrayList<Patient> vPatient) {
        open();
        for (Patient v : vPatient) {
            dataBase.store(v);
        }
        dataBase.close();
    }

    // Ajoute à DB4o un objet de la classe patient
    public void addUnPatient(Patient vPatient) {
        open();
        dataBase.store(vPatient);

        dataBase.close();
    }


}

