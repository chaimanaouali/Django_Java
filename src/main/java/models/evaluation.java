package models;

import javax.persistence.*;
import java.time.LocalDate;

public class evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private int mecanicien_id;
private String nom_client;
private String prenom_client;
private String avis_client;
private LocalDate date_eval;
    @ManyToOne
    @JoinColumn(name = "mecanicien_id") // Nom de la colonne dans la table Evaluation faisant référence à Mecanicien
    private mecanicien mecanicien;
    public evaluation(int id, int mecanicien_id, String nom_client, String prenom_client, String avis_client, LocalDate date_eval) {
        this.id = id;
        this.mecanicien_id = mecanicien_id;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.avis_client = avis_client;
        this.date_eval = date_eval;
    }

    public evaluation(int mecanicien_id, String nom_client, String prenom_client, String avis_client, LocalDate date_eval) {
        this.mecanicien_id = mecanicien_id;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.avis_client = avis_client;
        this.date_eval = date_eval;
    }
    public evaluation(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMecanicien_id() {
        return mecanicien_id;
    }

    public void setMecanicien_id(int mecanicien_id) {
        this.mecanicien_id = mecanicien_id;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public String getAvis_client() {
        return avis_client;
    }

    public void setAvis_client(String avis_client) {
        this.avis_client = avis_client;
    }

    public LocalDate getDate_eval() {
        return date_eval;
    }

    public void setDate_eval(LocalDate date_eval) {
        this.date_eval = date_eval;
    }

    @Override
    public String toString() {
        return "evaluation{" +
                "id=" + id +
                ", mecanicien_id=" + mecanicien_id +
                ", nom_client='" + nom_client + '\'' +
                ", prenom_client='" + prenom_client + '\'' +
                ", avis_client='" + avis_client + '\'' +
                ", date_eval=" + date_eval +
                '}';
    }
}
