package sample.entities;

public class terrain {

    private int idterrain;
    private String nomterrain;
    private int prixterrain;
    private String adresseterrain;
    private String typeterrain;

    public terrain(int idterrain, String nomterrain, String adresseterrain, String typeterrain, int prixterrain) {
        this.idterrain = idterrain;
        this.nomterrain = nomterrain;
        this.prixterrain = prixterrain;
        this.adresseterrain = adresseterrain;
        this.typeterrain = typeterrain;
    }

    public terrain() {
    }

    public int getIdterrain() {
        return idterrain;
    }

    public void setIdterrain(int idterrain) {
        this.idterrain = idterrain;
    }

    public String getNomterrain() {
        return nomterrain;
    }

    public void setNomterrain(String nomterrain) {
        this.nomterrain = nomterrain;
    }

    public float getPrixterrain() {
        return prixterrain;
    }

    public void setPrixterrain(int prix) {
        this.prixterrain = prix;
    }

    public String getAdresseterrain() {
        return adresseterrain;
    }

    public void setAdresseterrain(String adresseterrain) {
        this.adresseterrain = adresseterrain;
    }

    public String getTypeterrain() {
        return typeterrain;
    }

    public void setTypeterrain(String typeterrain) {
        this.typeterrain = typeterrain;
    }
}
