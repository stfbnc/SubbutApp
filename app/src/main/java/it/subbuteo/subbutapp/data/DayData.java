package it.subbuteo.subbutapp.data;

import java.util.ArrayList;

public class DayData {

    private String num = "";
    private ArrayList<MatchData> matchData = null;
    private String rest = "";

    public void setNum(String num){
        this.num = num;
    }

    public String getNum(){
        return num;
    }

    public void setMatchData(ArrayList<MatchData> matchData){
        this.matchData = matchData;
    }

    public ArrayList<MatchData> getMatchData(){
        return matchData;
    }

    public void setRest(String rest){
        this.rest = rest;
    }

    public String getRest(){
        return rest;
    }

}
