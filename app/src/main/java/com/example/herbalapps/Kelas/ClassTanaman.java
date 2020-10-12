package com.example.herbalapps.Kelas;

public class ClassTanaman {
    String IdTanaman;
    String NamaTanaman;
    String FotoTanaman;

    public ClassTanaman(String idTanaman, String namaTanaman, String fotoTanaman) {
        IdTanaman = idTanaman;
        NamaTanaman = namaTanaman;
        FotoTanaman = fotoTanaman;
    }

    public String getIdTanaman() {
        return IdTanaman;
    }

    public void setIdTanaman(String idTanaman) {
        IdTanaman = idTanaman;
    }

    public String getNamaTanaman() {
        return NamaTanaman;
    }

    public void setNamaTanaman(String namaTanaman) {
        NamaTanaman = namaTanaman;
    }

    public String getFotoTanaman() {
        return FotoTanaman;
    }

    public void setFotoTanaman(String fotoTanaman) {
        FotoTanaman = fotoTanaman;
    }

}
