package models;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.Date;

public class Devis {
    private int id;
    private String nom, prenom;
    private String adresse, email;
    private LocalDate date_naiss;
    private int num_tel;
    private String modele, puissance;

    private double prix;


    public Devis() {}
    public Devis(String nom, String prenom, String adresse,String email,LocalDate date_naiss,int num_tel,String modele,String puissance,double prix) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.date_naiss = date_naiss;
        this.num_tel = num_tel;
        this.modele = modele;
        this.puissance = puissance;
        this.prix = prix;

    }

    public Devis(int id, String nom, String prenom, String adresse,String email,LocalDate date_naiss,int num_tel,String modele,String puissance,double prix) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.date_naiss = date_naiss;
        this.num_tel = num_tel;
        this.modele = modele;
        this.puissance = puissance;
        this.prix = prix;
    }





    public Devis(String text, String text1, String text2, String text3, LocalDate value, String text4, String text5, String text6, String text7) {
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate_naiss() {
        return date_naiss;
    }

    public void setDate_naiss(LocalDate date_naiss) {
        this.date_naiss = date_naiss;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getPuissance() {
        return puissance;
    }

    public void setPuissance(String puissance) {
        this.puissance = puissance;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Devis{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", date_naiss=" + date_naiss +
                ", num_tel=" + num_tel +
                ", modele='" + modele + '\'' +
                ", puissance='" + puissance + '\'' +
                ", prix=" + prix +
                '}';
    }

}
