package com.example.lumi.pracainzynierska;

/**
 * Created by Lumi on 16.01.2018.
 */

public class Aim {
    private int idAim;
    private int userID;
    private String name;
    private String dataDo;
    private String opis;
    private String kategoria;

    public Aim(int idAim,int userID, String name, String dataDo, String opis, String kategoria) {
        this.idAim = idAim;
        this.userID= userID;
        this.name = name;
        this.dataDo = dataDo;
        this.opis = opis;
        this.kategoria = kategoria;
    }


    public int getIdAim() {
        return idAim;
    }

    public void setIdAim(int idAim) {
        this.idAim = idAim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataDo() {
        return dataDo;
    }

    public void setDataDo(String dataDo) {
        this.dataDo = dataDo;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
