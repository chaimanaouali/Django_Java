package models;

public enum Reaction {
    NON(0,"Like","/images/like1.png"),
    Like(1,"Like","/images/like1.png"),
    LOVE(2,"Love","/images/love.png"),
    HATE(3,"Hate","/images/no-fight.png");
    private int id;
    private String name;
    private String imgSrc;

    Reaction(int id, String name, String imgSrc) {
        this.id = id;
        this.name = name;
        this.imgSrc = imgSrc;
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

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }
}


