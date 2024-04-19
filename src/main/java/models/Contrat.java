package models;

import java.time.LocalDate;

public class Contrat {
    private int id;
    private  int type_couverture_id;
    private LocalDate date_debut_contrat;
    private LocalDate datefin_contrat;
    private String adresse_assur;
    private String numero_assur;
    private String nom;
    private String prenom;
    private String email;
    private String type_couverture;

    public Contrat(int id, int type_couverture_id, LocalDate date_debut_contrat, LocalDate datefin_contrat, String adresse_assur, String numero_assur, String nom, String prenom, String email) {
        this.id = id;
        this.type_couverture_id = type_couverture_id;
        this.date_debut_contrat = date_debut_contrat;
        this.datefin_contrat = datefin_contrat;
        this.adresse_assur = adresse_assur;
        this.numero_assur = numero_assur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Contrat(int type_couverture_id, LocalDate date_debut_contrat, LocalDate datefin_contrat, String adresse_assur, String numero_assur, String nom, String prenom, String email) {
        this.type_couverture_id = type_couverture_id;
        this.date_debut_contrat = date_debut_contrat;
        this.datefin_contrat = datefin_contrat;
        this.adresse_assur = adresse_assur;
        this.numero_assur = numero_assur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Contrat() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Contrat{");
        sb.append("\n");
        sb.append("id=").append(id);
        sb.append("\n");
        sb.append("type_couverture_id=").append(type_couverture_id);
        sb.append("\n");
        sb.append("date_debut_contrat='").append(date_debut_contrat).append("'");
        sb.append("\n");
        sb.append("datefin_contrat='").append(datefin_contrat).append("'");
        sb.append("\n");
        sb.append("adresse_assur='").append(adresse_assur).append("'");
        sb.append("\n");
        sb.append("numero_assur='").append(numero_assur).append("'");
        sb.append("\n");
        sb.append("nom='").append(nom).append("'");
        sb.append("\n");
        sb.append("prenom='").append(prenom).append("'");
        sb.append("\n");
        sb.append("email='").append(email).append("'");
        sb.append("\n");
        sb.append("}");
        return sb.toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType_couverture_id(int type_couverture_id) {
        this.type_couverture_id = type_couverture_id;
    }

    public void setDate_debut_contrat(LocalDate date_debut_contrat) {
        this.date_debut_contrat = date_debut_contrat;
    }

    public void setDatefin_contrat(LocalDate datefin_contrat) {
        this.datefin_contrat = datefin_contrat;
    }

    public String getType_couverture() {
        return type_couverture;
    }

    public void setType_couverture(String type_couverture) {
        this.type_couverture = type_couverture;
    }

    public void setAdresse_assur(String adresse_assur) {
        this.adresse_assur = adresse_assur;
    }

    public void setNumero_assur(String numero_assur) {
        this.numero_assur = numero_assur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public int getType_couverture_id() {
        return type_couverture_id;
    }

    public LocalDate getDate_debut_contrat() {
        return date_debut_contrat;
    }

    public LocalDate getDatefin_contrat() {
        return datefin_contrat;
    }

    public String getAdresse_assur() {
        return adresse_assur;
    }

    public String getNumero_assur() {
        return numero_assur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }
}

