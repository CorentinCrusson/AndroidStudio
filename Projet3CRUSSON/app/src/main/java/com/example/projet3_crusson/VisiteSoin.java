package com.example.projet3_crusson;

public class VisiteSoin {
    private int visite;
    private int id_categ_soins;
    private int id_type_soins;
    private int id_soins;
    private boolean prevu;
    private boolean realise;

    public VisiteSoin(){

    }
    public VisiteSoin(
            int vvisite,
            int vid_categ_soins,
            int vid_type_soins,
            int vid_soins,
            boolean vprevu,
            boolean vrealise
    )
    {
        visite=vvisite;
        id_categ_soins=vid_categ_soins;
        id_type_soins=vid_type_soins;
        id_soins=vid_soins;
        prevu=vprevu;
        realise=vrealise;
    }
    public void recopieVisiteSoin(VisiteSoin vVisiteSoin)
    {
        visite=vVisiteSoin.getVisite();
        id_categ_soins=vVisiteSoin.getId_categ_soins();
        id_type_soins=vVisiteSoin.getId_type_soins();
        id_soins=vVisiteSoin.getId_soins();
        prevu=vVisiteSoin.isPrevu();
        realise=vVisiteSoin.isRealise();
    }

    public int getVisite() {
        return visite;
    }

    public void setVisite(int visite) {
        this.visite = visite;
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

    public int getId_soins() {
        return id_soins;
    }

    public void setId_soins(int id_soins) {
        this.id_soins = id_soins;
    }

    public boolean isPrevu() {
        return prevu;
    }

    public void setPrevu(boolean prevu) {
        this.prevu = prevu;
    }

    public boolean isRealise() {
        return realise;
    }

    public void setRealise(boolean realise) {
        this.realise = realise;
    }

    public ArrayList<VisiteSoin> listeVisiteSoin() {
        open();
        ArrayList<VisiteSoin> listeVisiteSoin = new ArrayList<VisiteSoin>();
        ObjectSet<VisiteSoin> result = dataBase.queryByExample(VisiteSoin.class);
        while (result.hasNext()) {
            listeVisiteSoin.add(result.next());
        }
        dataBase.close();
        return listeVisiteSoin;
    }
    // trouveVisiteSoin retourne l'objet de la classe VisiteSin à partir de sa clé (4 int passés en paramètre)
    public VisiteSoin trouveVisiteSoin(int visite, int id_categ_soins, int id_type_soins, int id) {
        open();
        VisiteSoin vretour = new VisiteSoin();
        vretour.setVisite(visite);
        vretour.setId_categ_soins(id_categ_soins);
        vretour.setId_type_soins(id_type_soins);
        vretour.setId_soins(id);
        ObjectSet<VisiteSoin> result = dataBase.queryByExample(vretour);
        vretour = (VisiteSoin) result.next();
        dataBase.close();
        return vretour;
    }
    // trouveSoinsUneVisite retourne la collection de tous les objets VisiteSoin d'une visite à parir de son identifiant
    public ArrayList<VisiteSoin> trouveSoinsUneVisite(int visite) {
        open();
        VisiteSoin vretour = new VisiteSoin();
        vretour.setVisite(visite);
        ArrayList<VisiteSoin> listeVisiteSoin = new ArrayList<VisiteSoin>();
        ObjectSet<VisiteSoin> result = dataBase.queryByExample(vretour);
        while (result.hasNext()) {
            listeVisiteSoin.add(result.next());
        }
        dataBase.close();
        return listeVisiteSoin;
    }
    //  saveVisiteSoin sauvegarde un objet de la classe VisiteSoin dans DB4O en vérifiant son existence
    public void saveVisiteSoin(VisiteSoin VisiteSoin) {
        open();
        VisiteSoin vretour = new VisiteSoin();
        vretour.setVisite(VisiteSoin.getVisite());
        vretour.setId_soins(VisiteSoin.getId_soins());
        vretour.setId_categ_soins(VisiteSoin.getId_categ_soins());
        vretour.setId_type_soins(VisiteSoin.getId_type_soins());
        ObjectSet<VisiteSoin> result = dataBase.queryByExample(vretour);
        if (result.size() == 0) {
            dataBase.store(VisiteSoin);
        } else {
            vretour = (VisiteSoin) result.next();
            vretour.recopieVisiteSoin(VisiteSoin);
            dataBase.store(vretour);
        }
        dataBase.close();
    }
    // deleteVisiteSoin() qui permet de supprimer toutes les instances de la classe VisiteSoin
    public void deleteVisiteSoin() {
        open();
        ObjectSet<VisiteSoin> result = dataBase.queryByExample(VisiteSoin.class);
        while (result.hasNext()) {
            dataBase.delete(result.next());
        }
        dataBase.close();
    }
    // addVisiteSoin(ArrayList<VisiteSoin> vVisiteSoin) qui, à partir d'une collection de VisiteSoin, va les ajouter à DB4o. Nous ne testerons pas l'existence de ces objets puisque c'est une création (appel de la méthode après l'appel de deleteVisiteSoin()).
    public void addVisiteSoin(ArrayList<VisiteSoin> vVisiteSoin) {
        open();
        for (VisiteSoin v : vVisiteSoin) {
            dataBase.store(v);
        }
        dataBase.close();
    }



}
