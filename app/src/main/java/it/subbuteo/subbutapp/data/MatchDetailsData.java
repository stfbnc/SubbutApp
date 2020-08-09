package it.subbuteo.subbutapp.data;

import java.util.ArrayList;

public class MatchDetailsData {

    private String firstOrSecond = "";
    private ArrayList<DetailsItemData> goals = new ArrayList<>();
    private ArrayList<DetailsItemData> cards = new ArrayList<>();
    private ArrayList<DetailsItemData> inOut = new ArrayList<>();

    public void setFirstOrSecond(String firstOrSecond){
        this.firstOrSecond = firstOrSecond;
    }

    public String getFirstOrSecond(){
        return firstOrSecond;
    }

    public void setGoals(ArrayList<DetailsItemData> goals) { this.goals = goals; }

    public ArrayList<DetailsItemData> getGoals() { return goals; }

    public void setCards(ArrayList<DetailsItemData> cards) { this.cards = cards; }

    public ArrayList<DetailsItemData> getCards() { return cards; }

    public void setInOut(ArrayList<DetailsItemData> inOut) { this.inOut = inOut; }

    public ArrayList<DetailsItemData> getInOut() { return inOut; }

}
