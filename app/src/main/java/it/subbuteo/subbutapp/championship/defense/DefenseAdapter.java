package it.subbuteo.subbutapp.championship.defense;

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
import it.subbuteo.subbutapp.data.DefenseData;

public class DefenseAdapter extends RecyclerView.Adapter<DefenseAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DefenseData> mList;

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
            scoredGoals.setVisibility(View.GONE);
            concededGoals = v.findViewById(R.id.player_conceded_goals);
            img = v.findViewById(R.id.player_img);
        }
    }

    public DefenseAdapter(ArrayList<DefenseData> lst) {
        mList = lst;
    }

    @Override
    public DefenseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View match_row = inflater.inflate(R.layout.ranking_row, parent, false);
        DefenseAdapter.ViewHolder viewHolder = new DefenseAdapter.ViewHolder(match_row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefenseAdapter.ViewHolder holder, int position) {
        if(mList.size() > 0) {
            holder.pos.setText(String.valueOf(mList.get(position).getPosition()+"."));
            holder.name.setText(mList.get(position).getName());
            holder.concededGoals.setText(String.valueOf(mList.get(position).getcGoals()));
            holder.img.setImageDrawable(ContextCompat.getDrawable(context, mList.get(position).getImg()));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
