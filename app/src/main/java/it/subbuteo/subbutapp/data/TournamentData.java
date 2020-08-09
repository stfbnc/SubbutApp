package it.subbuteo.subbutapp.data;

import java.util.ArrayList;

public class TournamentData {

    private String type = "";
    private String date = "";
    private ArrayList<MatchData> matchData = null;

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

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
