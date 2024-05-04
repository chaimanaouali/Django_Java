package models;

public enum Reaction {
    NON(0,"Like","/images/thumb-up.png"),
    Like(1,"Like","/images/thumb-up.png"),
    LOVE(2,"Love","/images/like.png"),
    HATE(3,"Hate","/images/dislike.png"),
    NONE;
    private int id;
    private String name;
    private String imgSrc;

    Reaction(int id, String name, String imgSrc) {
        this.id = id;
        this.name = name;
        this.imgSrc = imgSrc;
    }

    Reaction() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }
}


