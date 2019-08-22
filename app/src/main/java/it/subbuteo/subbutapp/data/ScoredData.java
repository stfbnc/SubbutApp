package it.subbuteo.subbutapp.data;

public class ScoredData {

    private int position = 0;
    private int img;
    private String name = "";
    private int sGoals = 0;

    public void setPosition(int position){ this.position = position; }

    public int getPosition() { return position; }

    public void setImg(int img){ this.img = img; }

    public int getImg(){ return img; }

    public void setName(String name){ this.name = name; }

    public String getName(){ return name; }

    public void setsGoals(int sGoals){ this.sGoals = sGoals; }

    public int getsGoals(){ return sGoals; }

}
