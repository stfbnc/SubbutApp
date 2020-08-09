package it.subbuteo.subbutapp.data;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.globals.Globals;

public class DataRetriever implements VolleyResponseListener {

    public static final int NO_RESULT = -1;
    public static final String EMPTY_PLAYER = "";
    public static final int LIVE = 1;
    public static final int NOT_LIVE = 0;
    public static final String FIRST_MATCH = "FIRST";
    public static final String SECOND_MATCH = "SECOND";
    public static final int NO_PLAYER = 0;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public static final String GOAL = "GOAL";
    public static final String AUTOGOAL = "OWNGOAL";
    public static final String PENALTY = "PENALTY";
    public static final String PENALTY_FAILED = "PENALTY_FAILED";
    public static final String YELLOW_CARD = "YELLOW";
    public static final String RED_CARD = "RED";
    public static final String CHANGE = "CHANGE";

    public static String jsonString = "";
    public static HashMap<String, DayData> dataHashMap = new HashMap<>();
    public static HashMap<String, TournamentData> dataHashMapLeague = new HashMap<>();
    public static HashMap<String, TournamentData> dataHashMapCup = new HashMap<>();
    public static HashMap<String, SupercoppaData> dataHashMapSupercoppa = new HashMap<>();
    private Context ctx;

    public DataRetriever(Context AppCtx) {
        ctx = AppCtx;
    }

    public void sendJsonRequest(RequestQueue rs, String url, final VolleyResponseListener listener){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    listener.onErrorResponse(error.toString());
                }
        });
        rs.add(jsonObjectRequest);
    }

    public void unpackJSON(JSONObject jObj){
        Resources res = ctx.getResources();
        try{
            //CHAMPIONSHIP DATA
            JSONArray dayArr = jObj.getJSONArray(res.getString(R.string.championship_tag));
            for(int i = 0; i < dayArr.length(); i++) {
                JSONObject dayObj = dayArr.getJSONObject(i);
                DayData dayData = new DayData();
                dayData.setNum(dayObj.get(res.getString(R.string.day_num_tag)).toString());
                dayData.setDate(dayObj.get(res.getString(R.string.day_date_tag)).toString());
                dayData.setRest(dayObj.get(res.getString(R.string.rest_player_tag)).toString());
                ArrayList<MatchData> matchData = new ArrayList<>();
                JSONArray matchesArr = dayObj.getJSONArray(res.getString(R.string.matches_tag));
                for(int j = 0; j < matchesArr.length(); j++){
                    JSONObject mtch = matchesArr.getJSONObject(j);
                    MatchData md = new MatchData();
                    md.setPl1(mtch.get(res.getString(R.string.left_player_tag)).toString());
                    md.setRes1(Integer.parseInt(mtch.get(res.getString(R.string.left_res_tag)).toString()));
                    md.setPl2(mtch.get(res.getString(R.string.right_player_tag)).toString());
                    md.setRes2(Integer.parseInt(mtch.get(res.getString(R.string.right_res_tag)).toString()));
                    md.setLive1(Integer.parseInt(mtch.get(res.getString(R.string.live1_tag)).toString()));
                    ArrayList<MatchDetailsData> matchDetailsData = new ArrayList<>();
                    JSONArray details = mtch.getJSONArray(res.getString(R.string.details_tag));
                    for(int k = 0; k < details.length(); k++){
                        JSONObject detObj = details.getJSONObject(k);
                        MatchDetailsData mdd = new MatchDetailsData();
                        mdd.setFirstOrSecond(detObj.get(res.getString(R.string.first_or_second_tag)).toString());
                        ArrayList<DetailsItemData> itemGoals = new ArrayList<>();
                        JSONArray goals = detObj.getJSONArray(res.getString(R.string.goals_tag));
                        for(int kk = 0; kk < goals.length(); kk++){
                            JSONObject goalsObj = goals.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(goalsObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(goalsObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(goalsObj.get(res.getString(R.string.type_tag)).toString());
                            itemGoals.add(did);
                        }
                        mdd.setGoals(itemGoals);
                        ArrayList<DetailsItemData> itemCards = new ArrayList<>();
                        JSONArray cards = detObj.getJSONArray(res.getString(R.string.cards_tag));
                        for(int kk = 0; kk < cards.length(); kk++){
                            JSONObject cardsObj = cards.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(cardsObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(cardsObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(cardsObj.get(res.getString(R.string.type_tag)).toString());
                            itemCards.add(did);
                        }
                        mdd.setCards(itemCards);
                        ArrayList<DetailsItemData> itemInOut = new ArrayList<>();
                        JSONArray inOuts = detObj.getJSONArray(res.getString(R.string.inout_tag));
                        for(int kk = 0; kk < inOuts.length(); kk++){
                            JSONObject inoutObj = inOuts.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(inoutObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(inoutObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(inoutObj.get(res.getString(R.string.type_tag)).toString());
                            itemInOut.add(did);
                        }
                        mdd.setInOut(itemInOut);
                        matchDetailsData.add(mdd);
                    }
                    md.setMatchDetailsData(matchDetailsData);
                    matchData.add(md);
                }
                dayData.setMatchData(matchData);
                dataHashMap.put(dayData.getNum(), dayData);
            }
            //LEAGUE DATA
            JSONArray leagueArr = jObj.getJSONArray(res.getString(R.string.league_tag));
            for(int i = 0; i < leagueArr.length(); i++) {
                JSONObject phaseObj = leagueArr.getJSONObject(i);
                TournamentData tournamentData = new TournamentData();
                tournamentData.setType(phaseObj.get(res.getString(R.string.match_type_tag)).toString());
                tournamentData.setDate(phaseObj.get(res.getString(R.string.day_date_tag)).toString());
                ArrayList<MatchData> matchData = new ArrayList<>();
                JSONArray matchesArr = phaseObj.getJSONArray(res.getString(R.string.matches_tag));
                for(int j = 0; j < matchesArr.length(); j++){
                    JSONObject mtch = matchesArr.getJSONObject(j);
                    MatchData md = new MatchData();
                    md.setPl1(mtch.get(res.getString(R.string.left_player_tag)).toString());
                    md.setRes1(Integer.parseInt(mtch.get(res.getString(R.string.left_res_tag)).toString()));
                    md.setPl2(mtch.get(res.getString(R.string.right_player_tag)).toString());
                    md.setRes2(Integer.parseInt(mtch.get(res.getString(R.string.right_res_tag)).toString()));
                    md.setLive1(Integer.parseInt(mtch.get(res.getString(R.string.live1_tag)).toString()));
                    try{
                        md.setRes3(Integer.parseInt(mtch.get(res.getString(R.string.left_res_second_tag)).toString()));
                        md.setRes4(Integer.parseInt(mtch.get(res.getString(R.string.right_res_second_tag)).toString()));
                        md.setLive2(Integer.parseInt(mtch.get(res.getString(R.string.live2_tag)).toString()));
                    }catch(Exception e){
                        Log.d("singleMatch", "League: Single Match");
                    }
                    ArrayList<MatchDetailsData> matchDetailsData = new ArrayList<>();
                    JSONArray details = mtch.getJSONArray(res.getString(R.string.details_tag));
                    for(int k = 0; k < details.length(); k++){
                        JSONObject detObj = details.getJSONObject(k);
                        MatchDetailsData mdd = new MatchDetailsData();
                        mdd.setFirstOrSecond(detObj.get(res.getString(R.string.first_or_second_tag)).toString());
                        ArrayList<DetailsItemData> itemGoals = new ArrayList<>();
                        JSONArray goals = detObj.getJSONArray(res.getString(R.string.goals_tag));
                        for(int kk = 0; kk < goals.length(); kk++){
                            JSONObject goalsObj = goals.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(goalsObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(goalsObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(goalsObj.get(res.getString(R.string.type_tag)).toString());
                            itemGoals.add(did);
                        }
                        mdd.setGoals(itemGoals);
                        ArrayList<DetailsItemData> itemCards = new ArrayList<>();
                        JSONArray cards = detObj.getJSONArray(res.getString(R.string.cards_tag));
                        for(int kk = 0; kk < cards.length(); kk++){
                            JSONObject cardsObj = cards.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(cardsObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(cardsObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(cardsObj.get(res.getString(R.string.type_tag)).toString());
                            itemCards.add(did);
                        }
                        mdd.setCards(itemCards);
                        ArrayList<DetailsItemData> itemInOut = new ArrayList<>();
                        JSONArray inOuts = detObj.getJSONArray(res.getString(R.string.inout_tag));
                        for(int kk = 0; kk < inOuts.length(); kk++){
                            JSONObject inoutObj = inOuts.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(inoutObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(inoutObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(inoutObj.get(res.getString(R.string.type_tag)).toString());
                            itemInOut.add(did);
                        }
                        mdd.setInOut(itemInOut);
                        matchDetailsData.add(mdd);
                    }
                    md.setMatchDetailsData(matchDetailsData);
                    matchData.add(md);
                }
                tournamentData.setMatchData(matchData);
                dataHashMapLeague.put(tournamentData.getType(), tournamentData);
            }
            //CUP DATA
            JSONArray cupArr = jObj.getJSONArray(res.getString(R.string.cup_tag));
            for(int i = 0; i < cupArr.length(); i++) {
                JSONObject phaseObj = cupArr.getJSONObject(i);
                TournamentData tournamentData = new TournamentData();
                tournamentData.setType(phaseObj.get(res.getString(R.string.match_type_tag)).toString());
                tournamentData.setDate(phaseObj.get(res.getString(R.string.day_date_tag)).toString());
                ArrayList<MatchData> matchData = new ArrayList<>();
                JSONArray matchesArr = phaseObj.getJSONArray(res.getString(R.string.matches_tag));
                for(int j = 0; j < matchesArr.length(); j++){
                    JSONObject mtch = matchesArr.getJSONObject(j);
                    MatchData md = new MatchData();
                    md.setPl1(mtch.get(res.getString(R.string.left_player_tag)).toString());
                    md.setRes1(Integer.parseInt(mtch.get(res.getString(R.string.left_res_tag)).toString()));
                    md.setPl2(mtch.get(res.getString(R.string.right_player_tag)).toString());
                    md.setRes2(Integer.parseInt(mtch.get(res.getString(R.string.right_res_tag)).toString()));
                    md.setLive1(Integer.parseInt(mtch.get(res.getString(R.string.live1_tag)).toString()));
                    try{
                        md.setRes3(Integer.parseInt(mtch.get(res.getString(R.string.left_res_second_tag)).toString()));
                        md.setRes4(Integer.parseInt(mtch.get(res.getString(R.string.right_res_second_tag)).toString()));
                        md.setLive2(Integer.parseInt(mtch.get(res.getString(R.string.live2_tag)).toString()));
                    }catch(Exception e){
                        Log.d("singleMatch", "Cup: Single Match");
                    }
                    ArrayList<MatchDetailsData> matchDetailsData = new ArrayList<>();
                    JSONArray details = mtch.getJSONArray(res.getString(R.string.details_tag));
                    for(int k = 0; k < details.length(); k++){
                        JSONObject detObj = details.getJSONObject(k);
                        MatchDetailsData mdd = new MatchDetailsData();
                        mdd.setFirstOrSecond(detObj.get(res.getString(R.string.first_or_second_tag)).toString());
                        ArrayList<DetailsItemData> itemGoals = new ArrayList<>();
                        JSONArray goals = detObj.getJSONArray(res.getString(R.string.goals_tag));
                        for(int kk = 0; kk < goals.length(); kk++){
                            JSONObject goalsObj = goals.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(goalsObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(goalsObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(goalsObj.get(res.getString(R.string.type_tag)).toString());
                            itemGoals.add(did);
                        }
                        mdd.setGoals(itemGoals);
                        ArrayList<DetailsItemData> itemCards = new ArrayList<>();
                        JSONArray cards = detObj.getJSONArray(res.getString(R.string.cards_tag));
                        for(int kk = 0; kk < cards.length(); kk++){
                            JSONObject cardsObj = cards.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(cardsObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(cardsObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(cardsObj.get(res.getString(R.string.type_tag)).toString());
                            itemCards.add(did);
                        }
                        mdd.setCards(itemCards);
                        ArrayList<DetailsItemData> itemInOut = new ArrayList<>();
                        JSONArray inOuts = detObj.getJSONArray(res.getString(R.string.inout_tag));
                        for(int kk = 0; kk < inOuts.length(); kk++){
                            JSONObject inoutObj = inOuts.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(inoutObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(inoutObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(inoutObj.get(res.getString(R.string.type_tag)).toString());
                            itemInOut.add(did);
                        }
                        mdd.setInOut(itemInOut);
                        matchDetailsData.add(mdd);
                    }
                    md.setMatchDetailsData(matchDetailsData);
                    matchData.add(md);
                }
                tournamentData.setMatchData(matchData);
                dataHashMapCup.put(tournamentData.getType(), tournamentData);
            }
            // SUPERCOPPA DATA
            JSONArray supercoppaArr = jObj.getJSONArray(res.getString(R.string.supercoppa_tag));
            for(int i = 0; i < supercoppaArr.length(); i++) {
                JSONObject phaseObj = supercoppaArr.getJSONObject(i);
                SupercoppaData supercoppaData = new SupercoppaData();
                supercoppaData.setDate(phaseObj.get(res.getString(R.string.day_date_tag)).toString());
                ArrayList<MatchData> matchData = new ArrayList<>();
                JSONArray matchesArr = phaseObj.getJSONArray(res.getString(R.string.matches_tag));
                for(int j = 0; j < matchesArr.length(); j++){
                    JSONObject mtch = matchesArr.getJSONObject(j);
                    MatchData md = new MatchData();
                    md.setPl1(mtch.get(res.getString(R.string.left_player_tag)).toString());
                    md.setRes1(Integer.parseInt(mtch.get(res.getString(R.string.left_res_tag)).toString()));
                    md.setPl2(mtch.get(res.getString(R.string.right_player_tag)).toString());
                    md.setRes2(Integer.parseInt(mtch.get(res.getString(R.string.right_res_tag)).toString()));
                    md.setLive1(Integer.parseInt(mtch.get(res.getString(R.string.live1_tag)).toString()));
                    ArrayList<MatchDetailsData> matchDetailsData = new ArrayList<>();
                    JSONArray details = mtch.getJSONArray(res.getString(R.string.details_tag));
                    for(int k = 0; k < details.length(); k++){
                        JSONObject detObj = details.getJSONObject(k);
                        MatchDetailsData mdd = new MatchDetailsData();
                        mdd.setFirstOrSecond(detObj.get(res.getString(R.string.first_or_second_tag)).toString());
                        ArrayList<DetailsItemData> itemGoals = new ArrayList<>();
                        JSONArray goals = detObj.getJSONArray(res.getString(R.string.goals_tag));
                        for(int kk = 0; kk < goals.length(); kk++){
                            JSONObject goalsObj = goals.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(goalsObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(goalsObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(goalsObj.get(res.getString(R.string.type_tag)).toString());
                            itemGoals.add(did);
                        }
                        mdd.setGoals(itemGoals);
                        ArrayList<DetailsItemData> itemCards = new ArrayList<>();
                        JSONArray cards = detObj.getJSONArray(res.getString(R.string.cards_tag));
                        for(int kk = 0; kk < cards.length(); kk++){
                            JSONObject cardsObj = cards.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(cardsObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(cardsObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(cardsObj.get(res.getString(R.string.type_tag)).toString());
                            itemCards.add(did);
                        }
                        mdd.setCards(itemCards);
                        ArrayList<DetailsItemData> itemInOut = new ArrayList<>();
                        JSONArray inOuts = detObj.getJSONArray(res.getString(R.string.inout_tag));
                        for(int kk = 0; kk < inOuts.length(); kk++){
                            JSONObject inoutObj = inOuts.getJSONObject(kk);
                            DetailsItemData did = new DetailsItemData();
                            did.setMin(Integer.parseInt(inoutObj.get(res.getString(R.string.min_tag)).toString()));
                            did.setPlayer(Integer.parseInt(inoutObj.get(res.getString(R.string.player_tag)).toString()));
                            did.setType(inoutObj.get(res.getString(R.string.type_tag)).toString());
                            itemInOut.add(did);
                        }
                        mdd.setInOut(itemInOut);
                        matchDetailsData.add(mdd);
                    }
                    md.setMatchDetailsData(matchDetailsData);
                    matchData.add(md);
                }
                supercoppaData.setMatchData(matchData);
                dataHashMapSupercoppa.put(ctx.getString(R.string.bundle_supercoppa_tag), supercoppaData);
            }
            jsonString = jObj.toString();
        }catch (JSONException je){
            Log.d("respKO", "responseKO = " + jObj);
        }
    }
    
    public void unpackJSONHof(JSONObject jObj) {
        Resources res = ctx.getResources();
        try {
            JSONArray champHof = jObj.getJSONArray(res.getString(R.string.championship_tag));
            for (int i = 0; i < champHof.length(); i++) {
                JSONObject hofObj = champHof.getJSONObject(i);
                Globals.hallFameMap.put(hofObj.get(res.getString(R.string.player_tag)).toString(), Integer.valueOf(hofObj.get(res.getString(R.string.wins_tag)).toString()));
            }
            JSONArray cupHof = jObj.getJSONArray(res.getString(R.string.cup_tag));
            for (int i = 0; i < cupHof.length(); i++) {
                JSONObject hofObj = cupHof.getJSONObject(i);
                Globals.hallFameCupMap.put(hofObj.get(res.getString(R.string.player_tag)).toString(), Integer.valueOf(hofObj.get(res.getString(R.string.wins_tag)).toString()));
            }
            JSONArray leagueHof = jObj.getJSONArray(res.getString(R.string.league_tag));
            for (int i = 0; i < leagueHof.length(); i++) {
                JSONObject hofObj = leagueHof.getJSONObject(i);
                Globals.hallFameLeagueMap.put(hofObj.get(res.getString(R.string.player_tag)).toString(), Integer.valueOf(hofObj.get(res.getString(R.string.wins_tag)).toString()));
            }
            JSONArray supercoppaHof = jObj.getJSONArray(res.getString(R.string.supercoppa_tag));
            for (int i = 0; i < supercoppaHof.length(); i++) {
                JSONObject hofObj = supercoppaHof.getJSONObject(i);
                Globals.hallFameSupercoppaMap.put(hofObj.get(res.getString(R.string.player_tag)).toString(), Integer.valueOf(hofObj.get(res.getString(R.string.wins_tag)).toString()));
            }
        }catch (JSONException je){
            Log.d("respKO", "responseKO = " + jObj);
        }
    }
    
    public void unpackJSONImg(JSONObject jObj) {
        Resources res = ctx.getResources();
        try {
            JSONArray champImg = jObj.getJSONArray(res.getString(R.string.championship_tag));
            for (int i = 0; i < champImg.length(); i++) {
                JSONObject imgObj = champImg.getJSONObject(i);
                String name = imgObj.get(res.getString(R.string.img_tag)).toString();
                Globals.plIMg.put(imgObj.get(res.getString(R.string.player_tag)).toString(), res.getIdentifier(name, "drawable", ctx.getPackageName()));
            }
            JSONArray cupImg = jObj.getJSONArray(res.getString(R.string.cup_tag));
            for (int i = 0; i < cupImg.length(); i++) {
                JSONObject imgObj = cupImg.getJSONObject(i);
                String name = imgObj.get(res.getString(R.string.img_tag)).toString();
                Globals.plIMgCup.put(imgObj.get(res.getString(R.string.player_tag)).toString(), res.getIdentifier(name, "drawable", ctx.getPackageName()));
            }
            JSONArray leagueImg = jObj.getJSONArray(res.getString(R.string.league_tag));
            for (int i = 0; i < leagueImg.length(); i++) {
                JSONObject imgObj = leagueImg.getJSONObject(i);
                String name = imgObj.get(res.getString(R.string.img_tag)).toString();
                Globals.plIMgLeague.put(imgObj.get(res.getString(R.string.player_tag)).toString(), res.getIdentifier(name, "drawable", ctx.getPackageName()));
            }
            JSONArray supercoppaImg = jObj.getJSONArray(res.getString(R.string.supercoppa_tag));
            for (int i = 0; i < supercoppaImg.length(); i++) {
                JSONObject imgObj = supercoppaImg.getJSONObject(i);
                String name = imgObj.get(res.getString(R.string.img_tag)).toString();
                Globals.plIMgSupercoppa.put(imgObj.get(res.getString(R.string.player_tag)).toString(), res.getIdentifier(name, "drawable", ctx.getPackageName()));
            }
        }catch (JSONException je){
            Log.d("respKO", "responseKO = " + jObj);
        }
    }

    public String getJsonString(){
        return jsonString;
    }

    @Override
    public void onErrorResponse(String str){}

    @Override
    public void onResponse(JSONObject jobj){}

}
