package sample.entities;

public class reservation {

    private int idreservation;
    private String datereservation;
    private String heurereservation;
    private int prixreservation;
    private int idclient;
    private int idterrain;

    public reservation(int idreservation, String datereservation, String heurereservation, int idclient, int idterrain, int prixreservation) {
        this.idreservation = idreservation;
        this.datereservation = datereservation;
        this.heurereservation = heurereservation;
        this.prixreservation = prixreservation;
        this.idclient = idclient;
        this.idterrain = idterrain;
    }

    public reservation() {
    }


    public int getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(int idreservation) {
        this.idreservation = idreservation;
    }

    public String getDatereservation() {
        return datereservation;
    }

    public void setDatereservation(String datereservation) {
        this.datereservation = datereservation;
    }

    public String getHeurereservation() {
        return heurereservation;
    }

    public void setHeurereservation(String heurereservation) {
        this.heurereservation = heurereservation;
    }


    public int getPrixreservation() {
        return prixreservation;
    }

    public void setPrixreservation(int prixreservation) {
        this.prixreservation = prixreservation;
    }

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public int getIdterrain() {
        return idterrain;
    }

    public void setIdterrain(int idterrain) {
        this.idterrain = idterrain;
    }
}
