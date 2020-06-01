package com.example.demo.batch.modele;

public class Evenement {
    public String id;
    public String codeclient;
    public String codesejour;
    public String evenementa;
    public String somme;
    public String     dateevenement;
    public String methode;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeclient() {
        return codeclient;
    }

    public void setCodeclient(String codeclient) {
        this.codeclient = codeclient;
    }

    public String getCodesejour() {
        return codesejour;
    }

    public void setCodesejour(String codesejour) {
        this.codesejour = codesejour;
    }


    public String getSomme() {
        return somme;
    }

    public void setSomme(String somme) {
        this.somme = somme;
    }



    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

    public String getEvenementa() {
        return evenementa;
    }

    public void setEvenementa(String evenementa) {
        this.evenementa = evenementa;
    }

    public String getDateevenement() {
        return dateevenement;
    }

    public void setDateevenement(String dateevenement) {
        this.dateevenement = dateevenement;
    }
}
