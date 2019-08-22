package it.subbuteo.subbutapp.championship.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;

import it.subbuteo.subbutapp.data.DataRetriever;
import it.subbuteo.subbutapp.data.DefenseData;
import it.subbuteo.subbutapp.data.PlayerData;
import it.subbuteo.subbutapp.globals.Globals;
import it.subbuteo.subbutapp.data.HallOfFameData;
import it.subbuteo.subbutapp.data.MatchData;
import it.subbuteo.subbutapp.data.ScoredData;

public class RankingMatrix {

    public int getNumPlayers(){
        String numDay = "1";
        int dayPlayers = DataRetriever.dataHashMap.get(numDay).getMatchData().size() * 2;
        if(!DataRetriever.dataHashMap.get(numDay).getRest().equals(""))
            dayPlayers += 1;
        return dayPlayers;
    }

    public int getNumDays(){
        return DataRetriever.dataHashMap.size();
    }

    public ArrayList<String> getPlayersList(){
        ArrayList<String> plList = new ArrayList<>();
        String numDay = "1";
        ArrayList<MatchData> md = DataRetriever.dataHashMap.get(numDay).getMatchData();
        for(int i = 0; i < md.size(); i++){
            plList.add(md.get(i).getPl1());
            plList.add(md.get(i).getPl2());
        }
        if(!DataRetriever.dataHashMap.get(numDay).getRest().equals(""))
            plList.add(DataRetriever.dataHashMap.get(numDay).getRest());
        return plList;
    }

    public Hashtable<String, PlayerData> initializedRnkMap(int numPl){
        Hashtable<String, PlayerData> map = new Hashtable<>();
        ArrayList<String> arr = getPlayersList();
        for(int i = 0; i < numPl; i++) {
            PlayerData pd = new PlayerData();
            String name = arr.get(i);
            pd.setName(name);
            pd.setImg(Globals.plIMg.get(name));
            map.put(name, pd);
        }
        return map;
    }

    public Hashtable<String, PlayerData> getMatrix(){
        Hashtable<String, PlayerData> mtx = initializedRnkMap(getNumPlayers());
        int days = getNumDays();
        for(int i = 0; i < days; i++) {
            ArrayList<MatchData> arrMd = DataRetriever.dataHashMap.get(String.valueOf(i+1)).getMatchData();
            for (int j = 0; j < arrMd.size(); j++) {
                MatchData md = arrMd.get(j);
                if (md.getRes1() != DataRetriever.NO_RESULT && md.getRes2() != DataRetriever.NO_RESULT) {
                    PlayerData pl1 = mtx.get(md.getPl1());
                    PlayerData pl2 = mtx.get(md.getPl2());
                    pl1.setPlayed(pl1.getPlayed()+1);
                    pl2.setPlayed(pl2.getPlayed()+1);
                    pl1.setsGoals(pl1.getsGoals()+md.getRes1());
                    pl2.setsGoals(pl2.getsGoals()+md.getRes2());
                    pl1.setcGoals(pl1.getcGoals()+md.getRes2());
                    pl2.setcGoals(pl2.getcGoals()+md.getRes1());
                    if(md.getRes1() > md.getRes2()){
                        pl1.setPoints(pl1.getPoints()+3);
                        pl1.setWins(pl1.getWins()+1);
                        pl2.setDefeats(pl2.getDefeats()+1);
                    }else if(md.getRes1() < md.getRes2()){
                        pl2.setPoints(pl2.getPoints()+3);
                        pl2.setWins(pl2.getWins()+1);
                        pl1.setDefeats(pl1.getDefeats()+1);
                    }else if(md.getRes1() == md.getRes2()){
                        pl1.setPoints(pl1.getPoints()+1);
                        pl2.setPoints(pl2.getPoints()+1);
                        pl1.setDraws(pl1.getDraws()+1);
                        pl2.setDraws(pl2.getDraws()+1);
                    }
                }
            }
        }
        getRankOrder(mtx);
        return mtx;
    }

    public void getRankOrder(Hashtable<String, PlayerData> ht){
        ArrayList<PlayerData> arr = new ArrayList<>();
        arr.addAll(ht.values());
        Collections.sort(arr, new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData p1, PlayerData p2) {
                int val1 = Integer.compare(p1.getPoints(), p2.getPoints());
                if(val1 == 0){
                    int val2 = Integer.compare(p1.getsGoals()-p1.getcGoals(), p2.getsGoals()-p2.getcGoals());
                    if(val2 == 0){
                        int val3 = Integer.compare(p1.getsGoals(), p2.getsGoals());
                        if (val3 == 0){
                            int val4 = Integer.compare(p1.getWins(), p2.getWins());
                            if(val4 == 0){
                                return -p1.getName().compareToIgnoreCase(p2.getName());
                            }else{
                                return val4;
                            }
                        }else{
                            return val3;
                        }
                    }else{
                        return val2;
                    }
                }else {
                    return val1;
                }
            }
        });
        for(int i = 0; i < arr.size(); i++)
            ht.get(arr.get(i).getName()).setPosition(arr.size()-i);
    }

    public Hashtable<String, DefenseData> initializedDefRnkMap(int numPl){
        Hashtable<String, DefenseData> map = new Hashtable<>();
        ArrayList<String> arr = getPlayersList();
        for(int i = 0; i < numPl; i++) {
            DefenseData dd = new DefenseData();
            String name = arr.get(i);
            dd.setName(name);
            dd.setImg(Globals.plIMg.get(name));
            map.put(name, dd);
        }
        return map;
    }

    public Hashtable<String, DefenseData> getDefenseMatrix(){
        Hashtable<String, DefenseData> mtx = initializedDefRnkMap(getNumPlayers());
        int days = getNumDays();
        for(int i = 0; i < days; i++) {
            ArrayList<MatchData> arrMd = DataRetriever.dataHashMap.get(String.valueOf(i+1)).getMatchData();
            for (int j = 0; j < arrMd.size(); j++) {
                MatchData md = arrMd.get(j);
                if (md.getRes1() != DataRetriever.NO_RESULT && md.getRes2() != DataRetriever.NO_RESULT) {
                    DefenseData pl1 = mtx.get(md.getPl1());
                    DefenseData pl2 = mtx.get(md.getPl2());
                    pl1.setcGoals(pl1.getcGoals()+md.getRes2());
                    pl2.setcGoals(pl2.getcGoals()+md.getRes1());
                }
            }
        }
        getDefenseRankOrder(mtx);
        return mtx;
    }

    public void getDefenseRankOrder(Hashtable<String, DefenseData> ht){
        ArrayList<DefenseData> arr = new ArrayList<>();
        arr.addAll(ht.values());
        Collections.sort(arr, new Comparator<DefenseData>() {
            @Override
            public int compare(DefenseData d1, DefenseData d2) {
                int val1 = Integer.compare(d1.getcGoals(), d2.getcGoals());
                if(val1 == 0){
                    return d1.getName().compareToIgnoreCase(d2.getName());
                }else {
                    return val1;
                }
            }
        });
        for(int i = 0; i < arr.size(); i++)
            ht.get(arr.get(i).getName()).setPosition(i+1);
    }

    public Hashtable<String, ScoredData> initializedScoRnkMap(int numPl){
        Hashtable<String, ScoredData> map = new Hashtable<>();
        ArrayList<String> arr = getPlayersList();
        for(int i = 0; i < numPl; i++) {
            ScoredData sd = new ScoredData();
            String name = arr.get(i);
            sd.setName(name);
            sd.setImg(Globals.plIMg.get(name));
            map.put(name, sd);
        }
        return map;
    }

    public Hashtable<String, ScoredData> getScoredMatrix(){
        Hashtable<String, ScoredData> mtx = initializedScoRnkMap(getNumPlayers());
        int days = getNumDays();
        for(int i = 0; i < days; i++) {
            ArrayList<MatchData> arrMd = DataRetriever.dataHashMap.get(String.valueOf(i+1)).getMatchData();
            for (int j = 0; j < arrMd.size(); j++) {
                MatchData md = arrMd.get(j);
                if (md.getRes1() != DataRetriever.NO_RESULT && md.getRes2() != DataRetriever.NO_RESULT) {
                    ScoredData pl1 = mtx.get(md.getPl1());
                    ScoredData pl2 = mtx.get(md.getPl2());
                    pl1.setsGoals(pl1.getsGoals()+md.getRes1());
                    pl2.setsGoals(pl2.getsGoals()+md.getRes2());
                }
            }
        }
        getScoredRankOrder(mtx);
        return mtx;
    }

    public void getScoredRankOrder(Hashtable<String, ScoredData> ht){
        ArrayList<ScoredData> arr = new ArrayList<>();
        arr.addAll(ht.values());
        Collections.sort(arr, new Comparator<ScoredData>() {
            @Override
            public int compare(ScoredData s1, ScoredData s2) {
                int val1 = Integer.compare(s1.getsGoals(), s2.getsGoals());
                if(val1 == 0){
                    return -s1.getName().compareToIgnoreCase(s2.getName());
                }else {
                    return val1;
                }
            }
        });
        for(int i = 0; i < arr.size(); i++)
            ht.get(arr.get(i).getName()).setPosition(arr.size()-i);
    }

    public int getCumulativeMatchesNumber(){
        Hashtable<String, PlayerData> ht = getMatrix();
        int cumMtcNum = 0;
        Enumeration<String> enPl = ht.keys();
        if(enPl != null){
            while(enPl.hasMoreElements()){
                String name = enPl.nextElement();
                cumMtcNum += ht.get(name).getPlayed();
            }
        }
        return cumMtcNum;
    }

    public String getFirstPlayer(){
        Hashtable<String, PlayerData> ht = getMatrix();
        String name = "";
        Enumeration<String> enu = ht.keys();
        if(enu != null){
            while(enu.hasMoreElements()){
                String n = enu.nextElement();
                int pos = ht.get(n).getPosition();
                if(pos == 1){
                    name = n;
                    break;
                }
            }
        }
        return name;
    }

    public boolean isNumPlayersEven(){
        if(getNumPlayers() % 2 == 0)
            return true;
        else
            return false;
    }

    public boolean isChampionshipEnded(){
        int cumMatches = getCumulativeMatchesNumber();
        int numMatches = getNumDays();
        int numPlayers = getNumPlayers();
        if(isNumPlayersEven())
            return cumMatches == (numMatches * numPlayers);
        else
            return cumMatches == ((numMatches - 2) * numPlayers);
    }

    public Hashtable<String, HallOfFameData> initializedHOFRnkMap(){
        Hashtable<String, HallOfFameData> ht = new Hashtable<>();
        Enumeration<String> enu = Globals.hallFameMap.keys();
        if(enu != null){
            while(enu.hasMoreElements()){
                String name = enu.nextElement();
                HallOfFameData hof = new HallOfFameData();
                hof.setName(name);
                hof.setImg(Globals.plIMg.get(name));
                hof.setWins(Globals.hallFameMap.get(name));
                ht.put(name, hof);
            }
        }
        return ht;
    }

    public Hashtable<String, HallOfFameData> getHOFMatrix(){
        Hashtable<String, HallOfFameData> mtx = initializedHOFRnkMap();
        boolean isEnded = isChampionshipEnded();
        if(isEnded) {
            String name = getFirstPlayer();
            if(mtx.containsKey(name)){
                mtx.get(name).setWins(mtx.get(name).getWins()+1);
            }else{
                HallOfFameData hof = new HallOfFameData();
                hof.setName(name);
                hof.setImg(Globals.plIMg.get(name));
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
