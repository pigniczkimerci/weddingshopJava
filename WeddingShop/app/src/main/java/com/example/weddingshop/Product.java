package com.example.weddingshop;

public class Product {

    private String id, nev,ar,kategoria,kep;

    public Product() {
    }



    public Product(String id, String nev, String ar, String kep) {
        this.nev = nev;
        this.ar = ar;
        this.id = id;
        this.kep = kep;
    }



    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public String getKep() {
        return kep;
    }

    public void setKep(String kep) {
        this.kep = kep;
    }
}
