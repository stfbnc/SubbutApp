package it.subbuteo.subbutapp.data;

import java.util.ArrayList;

public class MatchData {

    private String pl1 = "";
    private int res1 = DataRetriever.NO_RESULT;
    private int res3 = DataRetriever.NO_RESULT;
    private String pl2 = "";
    private int res2 = DataRetriever.NO_RESULT;
    private int res4 = DataRetriever.NO_RESULT;
    private int live1 = DataRetriever.NOT_LIVE;
    private int live2 = DataRetriever.NOT_LIVE;
    private ArrayList<MatchDetailsData> matchDetailsData = new ArrayList<>();

    public void setPl1(String pl1){
        this.pl1 = pl1;
    }

    public String getPl1(){
        return pl1;
    }

    public void setRes1(int res1){
        this.res1 = res1;
    }

    public int getRes1(){
        return res1;
    }

    public void setRes3(int res3){
        this.res3 = res3;
    }

    public int getRes3(){
        return res3;
    }

    public void setPl2(String pl2){
        this.pl2 = pl2;
    }

    public String getPl2(){
        return pl2;
    }

    public void setRes2(int res2){
        this.res2 = res2;
    }

    public int getRes2(){
        return res2;
    }

    public void setRes4(int res4){
        this.res4 = res4;
    }

    public int getRes4(){
        return res4;
    }

    public void setLive1(int live1){
        this.live1 = live1;
    }

    public int getLive1(){
        return live1;
    }

    public void setLive2(int live2){
        this.live2 = live2;
    }

    public int getLive2(){
        return live2;
    }

    public void setMatchDetailsData(ArrayList<MatchDetailsData> matchDetailsData){ this.matchDetailsData = matchDetailsData; }

    public ArrayList<MatchDetailsData> getMatchDetailsData() { return matchDetailsData; }

}
