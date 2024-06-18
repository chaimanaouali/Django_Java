package models;
public class Type {
    private int id;
    private String description;
    private String type_couverture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType_couverture() {
        return type_couverture;
    }

    public void setType_couverture(String type_couverture) {
        this.type_couverture = type_couverture;
    }

    public Type() {
    }

    public Type(int id, String description, String type_couverture) {
        this.id = id;
        this.description = description;
        this.type_couverture = type_couverture;
    }

    public Type(String description, String type_couverture) {
        this.description = description;
        this.type_couverture = type_couverture;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", type_couverture='" + type_couverture + '\'' +
                '}';
    }
}
