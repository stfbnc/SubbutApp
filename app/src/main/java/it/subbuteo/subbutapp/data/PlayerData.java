package it.subbuteo.subbutapp.data;

public class PlayerData {

    private int position = 0;
    private int img;
    private String name = "";
    private int points = 0;
    private int played = 0;
    private int wins = 0;
    private int draws = 0;
    private int defeats = 0;
    private int sGoals = 0;
    private int cGoals = 0;

    public void setPosition(int position){ this.position = position; }

    public int getPosition() { return position; }

    public void setImg(int img){ this.img = img; }

    public int getImg(){ return img; }

    public void setName(String name){ this.name = name; }

    public String getName(){ return name; }

    public void setPoints(int points){ this.points = points; }

    public int getPoints(){ return points; }

    public void setPlayed(int played){ this.played = played; }

    public int getPlayed(){ return played; }

    public void setWins(int wins){ this.wins = wins; }

    public int getWins(){ return wins; }

    public void setDraws(int draws){ this.draws = draws; }

    public int getDraws(){ return draws; }

    public void setDefeats(int defeats){ this.defeats = defeats; }

    public int getDefeats(){ return defeats; }

    public void setsGoals(int sGoals){ this.sGoals = sGoals; }

    public int getsGoals(){ return sGoals; }

    public void setcGoals(int cGoals){ this.cGoals = cGoals; }

    public int getcGoals(){ return cGoals; }

}
