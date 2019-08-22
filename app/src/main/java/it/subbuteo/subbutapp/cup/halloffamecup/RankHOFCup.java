package it.subbuteo.subbutapp.cup.halloffamecup;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.data.DataRetriever;
import it.subbuteo.subbutapp.data.HallOfFameData;
import it.subbuteo.subbutapp.data.MatchData;
import it.subbuteo.subbutapp.globals.Globals;

public class RankHOFCup {

    private Context ctx;

    public RankHOFCup(Context ctx){
        this.ctx = ctx;
    }

    public Hashtable<String, HallOfFameData> initializedHOFRnkMap(){
        Hashtable<String, HallOfFameData> ht = new Hashtable<>();
        Enumeration<String> enu = Globals.hallFameCupMap.keys();
        if(enu != null){
            while(enu.hasMoreElements()){
                String name = enu.nextElement();
                HallOfFameData hof = new HallOfFameData();
                hof.setName(name);
                hof.setImg(Globals.plIMgCup.get(name));
                hof.setWins(Globals.hallFameCupMap.get(name));
                ht.put(name, hof);
            }
        }
        return ht;
    }

    public boolean isCupEnded(){
        ArrayList<MatchData> arr = DataRetriever.dataHashMapCup.get(ctx.getString(R.string.first_final)).getMatchData();
        for(int i = 0; i < arr.size(); i++){
            MatchData md = arr.get(i);
            if(md.getPl1().equals(DataRetriever.EMPTY_PLAYER) || md.getPl2().equals(DataRetriever.EMPTY_PLAYER)) {
                return false;
            }else{
                if(md.getRes1() == DataRetriever.NO_RESULT || md.getRes2() == DataRetriever.NO_RESULT){
                    return false;
                }
            }
        }
        return true;
    }

    public String getWinner(){
        ArrayList<MatchData> arr = DataRetriever.dataHashMapCup.get(ctx.getString(R.string.first_final)).getMatchData();
        String pl = "";
        for(int i = 0; i < arr.size(); i++) {
            MatchData md = arr.get(i);
            if(md.getRes1() > md.getRes2()){
                pl =  md.getPl1();
            }else{
                pl =  md.getPl2();
            }
        }
        return pl;
    }

    public Hashtable<String, HallOfFameData> getHOFMatrix(){
        Hashtable<String, HallOfFameData> mtx = initializedHOFRnkMap();
        boolean isEnded = isCupEnded();
        if(isEnded) {
            String name = getWinner();
            if(mtx.containsKey(name)){
                mtx.get(name).setWins(mtx.get(name).getWins()+1);
            }else{
                HallOfFameData hof = new HallOfFameData();
                hof.setName(name);
                hof.setImg(Globals.plIMgCup.get(name));
                hof.setWins(1);
                mtx.put(name, hof);
            }
        }
        getHOFRankOrder(mtx);
        return mtx;
    }

    public void getHOFRankOrder(Hashtable<String, HallOfFameData> ht){
        ArrayList<HallOfFameData> arr = new ArrayList<>();
        arr.addAll(ht.values());
        Collections.sort(arr, new Comparator<HallOfFameData>() {
            @Override
            public int compare(HallOfFameData h1, HallOfFameData h2) {
                int val1 = Integer.compare(h1.getWins(), h2.getWins());
                if(val1 == 0){
                    return -h1.getName().compareToIgnoreCase(h2.getName());
                }else {
                    return val1;
                }
            }
        });
        for(int i = 0; i < arr.size(); i++)
            ht.get(arr.get(i).getName()).setPosition(arr.size()-i);
    }

}
