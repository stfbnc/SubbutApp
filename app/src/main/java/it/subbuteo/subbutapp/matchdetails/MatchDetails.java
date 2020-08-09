package it.subbuteo.subbutapp.matchdetails;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.data.DataRetriever;
import it.subbuteo.subbutapp.data.DetailsItemData;
import it.subbuteo.subbutapp.data.MatchData;
import it.subbuteo.subbutapp.data.MatchDetailsData;
import it.subbuteo.subbutapp.globals.Globals;

public class MatchDetails {

    private Context ctx;
    private Bundle bndl;
    private ArrayList<DetailsItemData> lst;

    public MatchDetails(Context ctx, Bundle bndl) {
        this.ctx = ctx;
        this.bndl = bndl;
    }

    public void showDetails(){
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View popupView = inflater.inflate(R.layout.match_details, null);
        setDetailData(popupView);

        PopupWindow mpopup = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        WindowManager wm = (WindowManager) popupView.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;
        mpopup.setWidth(width*9/10);
        mpopup.setHeight(height*8/10);
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        mpopup.setOutsideTouchable(true);
        mpopup.setFocusable(true);
        mpopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mpopup.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    private void setDetailData(View v){
        int competitionType = bndl.getInt(ctx.getString(R.string.bundle_competition_type_tag), 0);
        String jsonMatchData = bndl.getString(ctx.getString(R.string.bundle_details_tag), "");
        boolean singleMatch = bndl.getBoolean(ctx.getString(R.string.bundle_single_match_tag), true);
        MatchData data = new Gson().fromJson(jsonMatchData, MatchData.class);
        int drw1 = 0, drw2 = 0;
        if(competitionType == Globals.CHAMPIONSHIP) {
            try {
                drw1 = Globals.plIMg.get(data.getPl1());
            }catch (Exception e){
                Log.d("No_drawable", "No drawable");
            }
            try{
                drw2 = Globals.plIMg.get(data.getPl2());
            }catch (Exception e){
                Log.d("No_drawable", "No drawable");
            }
        }else if(competitionType == Globals.CHAMPIONS_LEAGUE) {
            try{
                drw1 = Globals.plIMgLeague.get(data.getPl1());
            }catch (Exception e){
                Log.d("No_drawable", "No drawable");
            }
            try{
                drw2 = Globals.plIMgLeague.get(data.getPl2());
            }catch (Exception e){
                Log.d("No_drawable", "No drawable");
            }
        }else if(competitionType == Globals.CUP){
            try{
                drw1 = Globals.plIMgCup.get(data.getPl1());
            }catch (Exception e){
                Log.d("No_drawable", "No drawable");
            }
            try{
                drw2 = Globals.plIMgCup.get(data.getPl2());
            }catch (Exception e){
                Log.d("No_drawable", "No drawable");
            }
        }

        LinearLayout firstMatch = v.findViewById(R.id.players_row_first);
        RecyclerView firstRecycler = v.findViewById(R.id.recycle_match_det_first);
        TextView pl1 = firstMatch.findViewById(R.id.player_left);
        pl1.setText(data.getPl1());
        TextView pl2 = firstMatch.findViewById(R.id.player_right);
        pl2.setText(data.getPl2());
        ImageView im1 = firstMatch.findViewById(R.id.img_left);
        ImageView im2 = firstMatch.findViewById(R.id.img_right);
        if(!data.getPl1().equals(DataRetriever.EMPTY_PLAYER) && drw1 != 0)
            im1.setImageDrawable(ContextCompat.getDrawable(ctx, drw1));
        if(!data.getPl2().equals(DataRetriever.EMPTY_PLAYER) && drw2 != 0)
            im2.setImageDrawable(ContextCompat.getDrawable(ctx, drw2));
        TextView res1 = firstMatch.findViewById(R.id.res_left);
        res1.setText(data.getRes1() == DataRetriever.NO_RESULT ? "" : String.valueOf(data.getRes1()));
        res1.setTextColor(data.getLive1() == DataRetriever.NOT_LIVE ? ContextCompat.getColor(ctx, R.color.black) : ContextCompat.getColor(ctx, R.color.red));
        TextView res2 = firstMatch.findViewById(R.id.res_right);
        res2.setText(data.getRes2() == DataRetriever.NO_RESULT ? "" : String.valueOf(data.getRes2()));
        res2.setTextColor(data.getLive1() == DataRetriever.NOT_LIVE ? ContextCompat.getColor(ctx, R.color.black) : ContextCompat.getColor(ctx, R.color.red));
        firstRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        firstRecycler.setLayoutManager(layoutManager);
        getList(data, DataRetriever.FIRST_MATCH);
        RecyclerView.Adapter adapter = new MatchDetailsAdapter(lst, DataRetriever.FIRST_MATCH);
        firstRecycler.setAdapter(adapter);

        LinearLayout secondMatch = v.findViewById(R.id.players_row_second);
        RecyclerView secondRecycler = v.findViewById(R.id.recycle_match_det_second);
        if(singleMatch){
            secondMatch.setVisibility(View.GONE);
            secondRecycler.setVisibility(View.GONE);
            TextView div4 = v.findViewById(R.id.div4);
            div4.setVisibility(View.GONE);
            TextView div5 = v.findViewById(R.id.div5);
            div5.setVisibility(View.GONE);
        }else{
            TextView pl1sec = secondMatch.findViewById(R.id.player_left);
            pl1sec.setText(data.getPl2());
            TextView pl2sec = secondMatch.findViewById(R.id.player_right);
            pl2sec.setText(data.getPl1());
            ImageView im1sec = secondMatch.findViewById(R.id.img_left);
            ImageView im2sec = secondMatch.findViewById(R.id.img_right);
            if(!data.getPl2().equals(DataRetriever.EMPTY_PLAYER) && drw2 != 0)
                im1sec.setImageDrawable(ContextCompat.getDrawable(ctx, drw2));
            if(!data.getPl1().equals(DataRetriever.EMPTY_PLAYER) && drw1 != 0)
                im2sec.setImageDrawable(ContextCompat.getDrawable(ctx, drw1));
            TextView res4 = secondMatch.findViewById(R.id.res_left);
            res4.setText(data.getRes4() == DataRetriever.NO_RESULT ? "" : String.valueOf(data.getRes4()));
            res4.setTextColor(data.getLive2() == DataRetriever.NOT_LIVE ? ContextCompat.getColor(ctx, R.color.black) : ContextCompat.getColor(ctx, R.color.red));
            TextView res3 = secondMatch.findViewById(R.id.res_right);
            res3.setText(data.getRes3() == DataRetriever.NO_RESULT ? "" : String.valueOf(data.getRes3()));
            res3.setTextColor(data.getLive2() == DataRetriever.NOT_LIVE ? ContextCompat.getColor(ctx, R.color.black) : ContextCompat.getColor(ctx, R.color.red));
            secondRecycler.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(ctx);
            secondRecycler.setLayoutManager(layoutManager2);
            getList(data, DataRetriever.SECOND_MATCH);
            RecyclerView.Adapter adapter2 = new MatchDetailsAdapter(lst, DataRetriever.SECOND_MATCH);
            secondRecycler.setAdapter(adapter2);
        }
    }

    private void getList(MatchData md, String matchNum){
        lst = new ArrayList<>();
        for(int i = 0; i < md.getMatchDetailsData().size(); i++){
            MatchDetailsData mdd = md.getMatchDetailsData().get(i);
            if(mdd.getFirstOrSecond().equals(matchNum)){
                lst.addAll(mdd.getGoals());
                lst.addAll(mdd.getCards());
                lst.addAll(mdd.getInOut());
            }
        }
        Collections.sort(lst, new Comparator<DetailsItemData>() {
            @Override
            public int compare(DetailsItemData i1, DetailsItemData i2) {
                return Integer.compare(i1.getMin(), i2.getMin());
            }
        });
    }

}
