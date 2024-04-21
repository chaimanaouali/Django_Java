package models;

import java.time.LocalDate;

public class ReponseDevis {
    private int id;
    private int email_id;

    private String etat, decision;
    private String delai_reparation, duree_validite,documents;
    private LocalDate date_reglement;

    public int getId() {
        return id;
    }

    public int getEmail_id() {
        return email_id;
    }

    public String getEtat() {
        return etat;
    }

    public String getDecision() {
        return decision;
    }

    public String getDelai_reparation() {
        return delai_reparation;
    }

    public String getDuree_validite() {
        return duree_validite;
    }

    public String getDocuments() {
        return documents;
    }

    public LocalDate getDate_reglement() {
        return date_reglement;
    }

    public ReponseDevis(int id, int email_id, String etat, String decision, String delai_reparation, String duree_validite, String documents, LocalDate date_reglement) {
        this.id = id;
        this.email_id = email_id;
        this.etat = etat;
        this.decision = decision;
        this.delai_reparation = delai_reparation;
        this.duree_validite = duree_validite;
        this.documents = documents;
        this.date_reglement = date_reglement;
    }

    public ReponseDevis(int email_id, String etat, String decision, String delai_reparation, String duree_validite, String documents, LocalDate date_reglement) {
        this.email_id = email_id;
        this.etat = etat;
        this.decision = decision;
        this.delai_reparation = delai_reparation;
        this.duree_validite = duree_validite;
        this.documents = documents;
        this.date_reglement = date_reglement;
    }

    @Override
    public String toString() {
        return "ReponseDevis{" +
                "id=" + id +
                ", email_id=" + email_id +
                ", etat='" + etat + '\'' +
                ", decision='" + decision + '\'' +
                ", delai_reparation='" + delai_reparation + '\'' +
                ", duree_validite='" + duree_validite + '\'' +
                ", documents='" + documents + '\'' +
                ", date_reglement=" + date_reglement +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail_id(int email_id) {
        this.email_id = email_id;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public void setDelai_reparation(String delai_reparation) {
        this.delai_reparation = delai_reparation;
    }

    public void setDuree_validite(String duree_validite) {
        this.duree_validite = duree_validite;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public void setDate_reglement(LocalDate date_reglement) {
        this.date_reglement = date_reglement;
    }
}
