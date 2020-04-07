package com.example.projet3_crusson;

import java.util.Date;
public class Soin {
    int id_categ_soins;
    int id_type_soins;
    int id;
    String libel;
    String description;
    float  coefficient;
    Date date;

    public Soin(){

    }
    public Soin(
            int vid_categ_soins,
            int vid_type_soins,
            int vid,
            String vlibel,
            String vdescription,
            float  vcoefficient,
            Date vdate)
    {
        id_categ_soins=vid_categ_soins;
        id_type_soins=vid_type_soins;
        id=vid;
        libel=vlibel;
        description=vdescription;
        coefficient=vcoefficient;
        date=vdate;
    }
    public void recopieSoin(Soin vsoin)

    {
        id_categ_soins=vsoin.getId_categ_soins();
        id_type_soins=vsoin.getId_type_soins();
        id=vsoin.getId();
        libel=vsoin.getLibel();
        description=vsoin.getDescription();
        coefficient=vsoin.getCoefficient();
        date=vsoin.getDate();
    }
    public int getId_categ_soins() {
        return id_categ_soins;
    }

    public void setId_categ_soins(int id_categ_soins) {
        this.id_categ_soins = id_categ_soins;
    }

    public int getId_type_soins() {
        return id_type_soins;
    }

    public void setId_type_soins(int id_type_soins) {
        this.id_type_soins = id_type_soins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibel() {
        return libel;
    }

    public void setLibel(String libel) {
        this.libel = libel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /*
     *   Méthodes Spécifiques à la classe Soin
     */
// listeSoin retourne la liste de tous les soins
    public ArrayList<Soin> listeSoin() {
        open();
        ArrayList<Soin> listeSoin = new ArrayList<Soin>();
        ObjectSet<Soin> result = dataBase.queryByExample(Soin.class);
        while (result.hasNext()) {
            listeSoin.add(result.next());
        }
        dataBase.close();
        return listeSoin;
    }
    // trouveSoin retourne le soin à partir de sa clé (3 int passés en paramètre
    public Soin trouveSoin(int id_categ_soins, int id_type_soins, int id) {
        open();
        Soin vretour = new Soin();
        vretour.setId_categ_soins(id_categ_soins);
        vretour.setId_type_soins(id_type_soins);
        vretour.setId(id);
        ObjectSet<Soin> result = dataBase.queryByExample(vretour);
        if(result.size()==0) {
            Log.d("Soins", "Recherche soins introuvable" + String.valueOf(vretour.getId_categ_soins()) + "/" + String.valueOf(vretour.getId_type_soins()) + "/" + String.valueOf(vretour.getId()));
        }
        else
        {
            Log.d("Soins", "Recherche soins ok" + String.valueOf(vretour.getId_categ_soins()) + "/" + String.valueOf(vretour.getId_type_soins()) + "/" + String.valueOf(vretour.getId()));
        }
        vretour = (Soin) result.next();
        dataBase.close();
        return vretour;
    }

    // deleteSoin() qui permet de supprimer toutes les instances de la classe Soin
    public void deleteSoin() {
        open();
        ObjectSet<Soin> result = dataBase.queryByExample(Soin.class);
        while (result.hasNext()) {
            dataBase.delete(result.next());
        }
        dataBase.close();
    }
    // addSoin(ArrayList<Soin> vSoin) qui, à partir d'une collection de Soin, va les ajouter à DB4o. Nous ne testerons pas l'existence de ces objets puisque c'est une création (appel de la méthode après l'appel de deleteSoin()).
    public void addSoin(ArrayList<Soin> vSoin) {
        open();
        for (Soin v : vSoin) {
            dataBase.store(v);
        }
        dataBase.close();
    }

    // addUnSoin ajoute un soin à DB4o à partir d'un objet de la classe Soin
    public void addUnSoin(Soin vSoin) {
        open();
        dataBase.store(vSoin);

        dataBase.close();
    }


}

