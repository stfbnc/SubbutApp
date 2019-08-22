package it.subbuteo.subbutapp.championship.ranking;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.data.PlayerData;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PlayerData> mList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pos, name, points, playedMatches, wins, draws, defeats, scoredGoals, concededGoals;
        public ImageView img;
        public ViewHolder(View v) {
            super(v);
            pos = v.findViewById(R.id.player_pos);
            name = v.findViewById(R.id.player_name);
            points = v.findViewById(R.id.player_points);
            playedMatches = v.findViewById(R.id.player_num_matches);
            wins = v.findViewById(R.id.player_wins);
            draws = v.findViewById(R.id.player_draws);
            defeats = v.findViewById(R.id.player_defeats);
            scoredGoals = v.findViewById(R.id.player_scored_goals);
            concededGoals = v.findViewById(R.id.player_conceded_goals);
            img = v.findViewById(R.id.player_img);
        }
    }

    public RankingAdapter(ArrayList<PlayerData> lst) {
        mList = lst;
    }

    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View match_row = inflater.inflate(R.layout.ranking_row, parent, false);
        RankingAdapter.ViewHolder viewHolder = new RankingAdapter.ViewHolder(match_row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RankingAdapter.ViewHolder holder, int position) {
        if(mList.size() > 0) {
            holder.pos.setText(String.valueOf(mList.get(position).getPosition()+"."));
            holder.name.setText(mList.get(position).getName());
            holder.points.setText(String.valueOf(mList.get(position).getPoints()));
            holder.playedMatches.setText(String.valueOf(mList.get(position).getPlayed()));
            holder.wins.setText(String.valueOf(mList.get(position).getWins()));
            holder.draws.setText(String.valueOf(mList.get(position).getDraws()));
            holder.defeats.setText(String.valueOf(mList.get(position).getDefeats()));
            holder.scoredGoals.setText(String.valueOf(mList.get(position).getsGoals()));
            holder.concededGoals.setText(String.valueOf(mList.get(position).getcGoals()));
            holder.img.setImageDrawable(ContextCompat.getDrawable(context, mList.get(position).getImg()));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
