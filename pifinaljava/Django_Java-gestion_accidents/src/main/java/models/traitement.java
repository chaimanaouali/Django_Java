    package models;

    import java.time.LocalDateTime;

    public class traitement {
        private int id;
        LocalDateTime date_traitement;
        private String responsable;
        private String statut;
        private String remarque;

        private String photo;

        public traitement() {
        }

        public traitement(int id, LocalDateTime date_traitement, String responsable, String statut, String remarque, String photo) {
            this.id = id;
            this.date_traitement = date_traitement;
            this.responsable = responsable;
            this.statut = statut;
            this.remarque = remarque;
            this.photo = photo;
        }

        public traitement(int id, LocalDateTime date_traitement, String responsable, String statut, String remarque) {
            this.id = id;
            this.date_traitement = date_traitement;
            this.responsable = responsable;
            this.statut = statut;
            this.remarque = remarque;
        }

        public traitement(LocalDateTime date_traitement, String responsable, String statut, String remarque) {
            this.date_traitement = date_traitement;
            this.responsable = responsable;
            this.statut = statut;
            this.remarque = remarque;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public LocalDateTime getDate_traitement() {
            return date_traitement;
        }

        public void setDate_traitement(LocalDateTime date_traitement) {
            this.date_traitement = date_traitement;
        }

        public String getResponsable() {
            return responsable;
        }

        public void setResponsable(String responsable) {
            this.responsable = responsable;
        }

        public String getStatut() {
            return statut;
        }

        public void setStatut(String statut) {
            this.statut = statut;
        }

        public String getRemarque() {
            return remarque;
        }

        public void setRemarque(String remarque) {
            this.remarque = remarque;
        }



        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        @Override
        public String toString() {
            return "traitement{" +
                    "idT=" + id+
                    ", date_traitement=" + date_traitement +
                    ", responsable='" + responsable + '\'' +
                    ", statut='" + statut + '\'' +
                    ", remarque='" + remarque + '\'' +
                    ", photo='" + photo + '\'' +
                    '}';
        }



    }
