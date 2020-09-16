package com.example.herbalapps.Kelas;

public class ClassHerbal {
    String IdHerbal;
    String NamaHerbal;
    String FotoHerbal;
    String Cara;
    String Manfaat;
    String Dosis;

    public ClassHerbal(String idHerbal, String namaHerbal, String fotoHerbal, String cara, String manfaat, String dosis) {
        IdHerbal = idHerbal;
        NamaHerbal = namaHerbal;
        FotoHerbal = fotoHerbal;
        Cara = cara;
        Manfaat = manfaat;
        Dosis = dosis;
    }

    public String getIdHerbal() {
        return IdHerbal;
    }

    public void setIdHerbal(String idHerbal) {
        IdHerbal = idHerbal;
    }

    public String getNamaHerbal() {
        return NamaHerbal;
    }

    public void setNamaHerbal(String namaHerbal) {
        NamaHerbal = namaHerbal;
    }

    public String getFotoHerbal() {
        return FotoHerbal;
    }

    public void setFotoHerbal(String fotoHerbal) {
        FotoHerbal = fotoHerbal;
    }

    public String getCara() {
        return Cara;
    }

    public void setCara(String cara) {
        Cara = cara;
    }

    public String getManfaat() {
        return Manfaat;
    }

    public void setManfaat(String manfaat) {
        Manfaat = manfaat;
    }

    public String getDosis() {
        return Dosis;
    }

    public void setDosis(String dosis) {
        Dosis = dosis;
    }

}
