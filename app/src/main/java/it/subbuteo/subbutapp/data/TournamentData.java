package it.subbuteo.subbutapp.data;

import java.util.ArrayList;

public class TournamentData {

    private String type = "";
    private ArrayList<MatchData> matchData = null;

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setMatchData(ArrayList<MatchData> matchData){
        this.matchData = matchData;
    }

    public ArrayList<MatchData> getMatchData(){
        return matchData;
    }

}
