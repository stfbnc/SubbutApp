package it.subbuteo.subbutapp.data;

import java.util.ArrayList;

public class SupercoppaData {

    private String date = "";
    private ArrayList<MatchData> matchData = null;

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setMatchData(ArrayList<MatchData> matchData){
        this.matchData = matchData;
    }

    public ArrayList<MatchData> getMatchData(){
        return matchData;
    }

}
