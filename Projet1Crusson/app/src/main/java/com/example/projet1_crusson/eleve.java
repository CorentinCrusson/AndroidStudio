package com.example.projet1_crusson;

import java.util.Date;

public class eleve {
    private int num;
    private String nom;
    private String prenom;
    private int age;
    private String adresse;
    private Date datenaiss;

    public eleve(int vnum,String vnom,String vprenom,int vage,String vadresse, Date vdate){
        num=vnum;
        nom=vnom;
        prenom=vprenom;
        age=vage;
        adresse=vadresse;
        datenaiss=vdate;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDatenaiss() {
        return datenaiss;
    }

    public void setDatenaiss(Date datenaiss) {
        this.datenaiss = datenaiss;
    }


}