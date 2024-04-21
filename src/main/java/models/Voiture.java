package models;

public class Voiture {
    private int id,puissance;
    private String matricule,marque;
    private float prix_voiture;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMartricule(String martricule) {
        this.matricule = martricule;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public float getPrix_voiture() {
        return prix_voiture;
    }

    public void setPrix_voiture(float prix_voiture) {
        this.prix_voiture = prix_voiture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Voiture() {
    }

    public Voiture(int puissance, String matricule, String marque, float prix_voiture, User user) {
        this.puissance = puissance;
        this.matricule = matricule;
        this.marque = marque;
        this.prix_voiture = prix_voiture;
        this.user = user;
    }

    public Voiture(int id, int puissance, String matricule, String marque, float prix_voiture, User user) {
        this.id = id;
        this.puissance = puissance;
        this.matricule = matricule;
        this.marque = marque;
        this.prix_voiture = prix_voiture;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "id=" + id +
                ", puissance=" + puissance +
                ", matricule='" + matricule + '\'' +
                ", marque='" + marque + '\'' +
                ", prix_voiture=" + prix_voiture +
                ", user=" + user +
                '}';
    }
}
