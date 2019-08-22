package it.subbuteo.subbutapp.matchdetails;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.data.DataRetriever;
import it.subbuteo.subbutapp.data.DetailsItemData;

public class MatchDetailsAdapter extends RecyclerView.Adapter<MatchDetailsAdapter.ViewHolder> {

    private ArrayList<DetailsItemData> mList;
    private String firstOrSecond;
    private Context ctx;
    private HashMap<String, Integer> detImg = new HashMap<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lm, rm;
        public ImageView li, ri;
        public ViewHolder(View v) {
            super(v);
            lm = v.findViewById(R.id.min1);
            rm = v.findViewById(R.id.min2);
            li = v.findViewById(R.id.img1);
            ri = v.findViewById(R.id.img2);
        }
    }

    public MatchDetailsAdapter(ArrayList<DetailsItemData> lst, String f_or_s) {
        mList = lst;
        firstOrSecond = f_or_s;
        detImg.put(DataRetriever.GOAL, R.drawable.black_ball);
        detImg.put(DataRetriever.AUTOGOAL, R.drawable.red_ball);
        detImg.put(DataRetriever.PENALTY, R.drawable.penalty_black);
        detImg.put(DataRetriever.PENALTY_FAILED, R.drawable.penalty_red);
        detImg.put(DataRetriever.YELLOW_CARD, R.drawable.yellow_card);
        detImg.put(DataRetriever.RED_CARD, R.drawable.red_card);
    }

    @Override
    public MatchDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View match_row = inflater.inflate(R.layout.details_row, parent, false);
        MatchDetailsAdapter.ViewHolder viewHolder = new MatchDetailsAdapter.ViewHolder(match_row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MatchDetailsAdapter.ViewHolder holder, int position) {
        if(mList.size() > 0) {
            int min = mList.get(position).getMin();
            String strMin = String.valueOf(min)+"'";
            String type = mList.get(position).getType();
            if(mList.get(position).getPlayer() == DataRetriever.PLAYER_1) {
                if(firstOrSecond.equals(DataRetriever.FIRST_MATCH)) {
                    holder.lm.setText(strMin);
                    holder.li.setImageDrawable(ContextCompat.getDrawable(ctx, detImg.get(type)));
                }else if(firstOrSecond.equals(DataRetriever.SECOND_MATCH)){
                    holder.rm.setText(strMin);
                    holder.ri.setImageDrawable(ContextCompat.getDrawable(ctx, detImg.get(type)));
                }
            }else if(mList.get(position).getPlayer() == DataRetriever.PLAYER_2){
                if(firstOrSecond.equals(DataRetriever.FIRST_MATCH)) {
                    holder.rm.setText(strMin);
                    holder.ri.setImageDrawable(ContextCompat.getDrawable(ctx, detImg.get(type)));
                }else if(firstOrSecond.equals(DataRetriever.SECOND_MATCH)){
                    holder.lm.setText(strMin);
                    holder.li.setImageDrawable(ContextCompat.getDrawable(ctx, detImg.get(type)));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
