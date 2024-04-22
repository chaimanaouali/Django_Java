    package models;

    import java.time.LocalDateTime;

    public class traitement {
        private int idT;
        LocalDateTime date_traitement;
        private String responsable;
        private String statut;
        private String remarque;
        private int identifiant_id;
        private String photo;

        public traitement() {
        }

        public traitement(int idT, LocalDateTime date_traitement, String responsable, String statut, String remarque, int identifiant_id, String photo) {
            this.idT = idT;
            this.date_traitement = date_traitement;
            this.responsable = responsable;
            this.statut = statut;
            this.remarque = remarque;
            this.identifiant_id = identifiant_id;
            this.photo = photo;
        }

        public traitement(int idT, LocalDateTime date_traitement, String responsable, String statut, String remarque) {
            this.idT = idT;
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

        public int getIdT() {
            return idT;
        }

        public void setIdT(int idT) {
            this.idT = idT;
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

        public int getIdentifiant_id() {
            return identifiant_id;
        }

        public void setIdentifiant_id(int identifiant_id) {
            this.identifiant_id = identifiant_id;
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
                    "idT=" + idT +
                    ", date_traitement=" + date_traitement +
                    ", responsable='" + responsable + '\'' +
                    ", statut='" + statut + '\'' +
                    ", remarque='" + remarque + '\'' +
                    ", identifiant_id=" + identifiant_id +
                    ", photo='" + photo + '\'' +
                    '}';
        }



    }
