package it.subbuteo.subbutapp.championship.scored;

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
import it.subbuteo.subbutapp.data.ScoredData;

public class ScoredAdapter extends RecyclerView.Adapter<ScoredAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ScoredData> mList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pos, name, points, playedMatches, wins, draws, defeats, scoredGoals, concededGoals;
        public ImageView img;
        public ViewHolder(View v) {
            super(v);
            pos = v.findViewById(R.id.player_pos);
            name = v.findViewById(R.id.player_name);
            points = v.findViewById(R.id.player_points);
            points.setVisibility(View.GONE);
            playedMatches = v.findViewById(R.id.player_num_matches);
            playedMatches.setVisibility(View.GONE);
            wins = v.findViewById(R.id.player_wins);
            wins.setVisibility(View.GONE);
            draws = v.findViewById(R.id.player_draws);
            draws.setVisibility(View.GONE);
            defeats = v.findViewById(R.id.player_defeats);
            defeats.setVisibility(View.GONE);
            scoredGoals = v.findViewById(R.id.player_scored_goals);
            concededGoals = v.findViewById(R.id.player_conceded_goals);
            concededGoals.setVisibility(View.GONE);
            img = v.findViewById(R.id.player_img);
        }
    }

    public ScoredAdapter(ArrayList<ScoredData> lst) {
        mList = lst;
    }

    @Override
    public ScoredAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View match_row = inflater.inflate(R.layout.ranking_row, parent, false);
        ScoredAdapter.ViewHolder viewHolder = new ScoredAdapter.ViewHolder(match_row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScoredAdapter.ViewHolder holder, int position) {
        if(mList.size() > 0) {
            holder.pos.setText(String.valueOf(mList.get(position).getPosition()+"."));
            holder.name.setText(mList.get(position).getName());
            holder.scoredGoals.setText(String.valueOf(mList.get(position).getsGoals()));
            holder.img.setImageDrawable(ContextCompat.getDrawable(context, mList.get(position).getImg()));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
