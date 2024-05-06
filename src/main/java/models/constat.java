package models;

import java.time.LocalDateTime;

public class constat {
    private int id;
    private LocalDateTime date;
    private String lieu;
    private String description;
    private String conditionroute;
    private int rapportpolice;
    private String  photo;
    private String  iduser;
    private String email;


    public constat() {
    }

    public constat(LocalDateTime date, String lieu, String description,String photo, String conditionroute, int rapportpolice) {
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.conditionroute = conditionroute;
        this.photo = photo;
        this.rapportpolice=rapportpolice;
    }
    public constat(LocalDateTime date, String lieu, String description,String photo, String conditionroute, int rapportpolice,String email) {
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.conditionroute = conditionroute;
        this.photo = photo;
        this.rapportpolice=rapportpolice;
        this.email=email;
    }

    public constat(int id, LocalDateTime date, String lieu, String description, String conditionroute, int rapportpolice, String photo) {
        this.id = id;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.conditionroute = conditionroute;
        this.rapportpolice = rapportpolice;
        this.photo = photo;
    }
    public constat(int id, LocalDateTime date, String lieu, String description, String conditionroute, int rapportpolice, String photo,String email) {
        this.id = id;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.conditionroute = conditionroute;
        this.rapportpolice = rapportpolice;
        this.photo = photo;
        this.email = email;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = LocalDateTime.now();
    }

    public String getLieu() {
        return lieu;
    }
    public void setLieu(String lieu) {
            this.lieu = lieu;
        }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConditionroute() {
        return conditionroute;
    }

    public void setConditionroute(String conditionroute) {
        this.conditionroute = conditionroute;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public int getRapportpolice() {
        return rapportpolice;
    }

    public void setRapportpolice(int rapportpolice) {
        this.rapportpolice = rapportpolice;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    @Override
    public String toString() {
        return "constat{" +
                "id=" + id +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", description='" + description + '\'' +
                ", conditionroute='" + conditionroute + '\'' +
                ", photo='" + photo + '\'' +
                ", iduser='" + iduser + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
