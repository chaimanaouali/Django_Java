package models;

import javax.persistence.*;

public class mecanicien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String localisation;
    private String disponibilite;
    private String numero;
    private String image;
    private String email;

    public mecanicien(int id, String nom, String prenom, String localisation, String disponibilite, String numero, String image, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.localisation = localisation;
        this.disponibilite = disponibilite;
        this.numero = numero;
        this.image = image;
        this.email = email;
    }

    public mecanicien(String nom, String prenom, String localisation, String disponibilite, String numero, String image, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.localisation = localisation;
        this.disponibilite = disponibilite;
        this.numero = numero;
        this.image = image;
        this.email = email;
    }

    public mecanicien() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "mecanicien{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", localisation='" + localisation + '\'' +
                ", disponibilite='" + disponibilite + '\'' +
                ", numero='" + numero + '\'' +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
