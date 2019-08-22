package it.subbuteo.subbutapp.data;

public class HallOfFameData {

    private int position = 0;
    private int img;
    private String name = "";
    private int wins = 0;

    public void setPosition(int position){ this.position = position; }

    public int getPosition() { return position; }

    public void setImg(int img){ this.img = img; }

    public int getImg(){ return img; }

    public void setName(String name){ this.name = name; }

    public String getName(){ return name; }

    public void setWins(int wins){ this.wins = wins; }

    public int getWins(){ return wins; }

}
