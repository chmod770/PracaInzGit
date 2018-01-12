package com.example.lumi.pracainzynierska;

/**
 * Created by Lumi on 04.01.2018.
 */

public class Task {

    private int idTask;
    private  String nazwa;
    private String data;
    private int idUsera;
    private int czas;
    private boolean czyZrobione;


    public Task(int idTask ,int idUsera, String nazwa, String data, int czas, boolean czyZrobione)
    {
        this.idTask = idTask;
        this.nazwa = nazwa;
        this.data = data;
        this.idUsera = idUsera;
        this.czas = czas;
        this.czyZrobione = czyZrobione;
    }


    public int getCzas() {
        return czas;
    }

    public void setCzas(int czas) {
        this.czas = czas;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getIdUsera() {

        return idUsera;
    }

    public void setIdUsera(int idUsera) {
        this.idUsera = idUsera;
    }

    public String getData() {

        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isCzyZrobione() {
        return czyZrobione;
    }

    public void setCzyZrobione(boolean czyZrobione) {
        this.czyZrobione = czyZrobione;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
}
