package it.subbuteo.subbutapp.championship.matches;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.data.DataRetriever;
import it.subbuteo.subbutapp.data.MatchData;
import it.subbuteo.subbutapp.globals.Globals;

public class MatchesListAdapter extends RecyclerView.Adapter<MatchesListAdapter.ViewHolder> {

    private ArrayList<MatchData> mList;
    private Context ctx;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lp, lr, rp, rr;
        public ImageView li, ri;
        public ViewHolder(View v) {
            super(v);
            lp = v.findViewById(R.id.player_left);
            lr = v.findViewById(R.id.res_left);
            rp = v.findViewById(R.id.player_right);
            rr = v.findViewById(R.id.res_right);
            li = v.findViewById(R.id.img_left);
            ri = v.findViewById(R.id.img_right);
        }
    }

    public MatchesListAdapter(ArrayList<MatchData> lst, Context context) {
        mList = lst;
        ctx = context;
    }

    @Override
    public MatchesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View match_row = inflater.inflate(R.layout.match_layout_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(match_row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesListAdapter.ViewHolder holder, int position) {
        if(mList.size() > 0) {
            String pl1 = mList.get(position).getPl1();
            holder.lp.setText(pl1);
            String pl2 = mList.get(position).getPl2();
            holder.rp.setText(pl2);
            int live1 = mList.get(position).getLive1();
            int res1 = mList.get(position).getRes1();
            holder.lr.setText(res1 == DataRetriever.NO_RESULT ? "" : String.valueOf(res1));
            holder.lr.setTextColor(live1 == DataRetriever.NOT_LIVE ? ContextCompat.getColor(ctx, R.color.black) : ContextCompat.getColor(ctx, R.color.red));
            int res2 = mList.get(position).getRes2();
            holder.rr.setText(res2 == DataRetriever.NO_RESULT ? "" : String.valueOf(res2));
            holder.rr.setTextColor(live1 == DataRetriever.NOT_LIVE ? ContextCompat.getColor(ctx, R.color.black) : ContextCompat.getColor(ctx, R.color.red));
            holder.li.setImageDrawable(ContextCompat.getDrawable(ctx, Globals.plIMg.get(pl1)));
            holder.ri.setImageDrawable(ContextCompat.getDrawable(ctx, Globals.plIMg.get(pl2)));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
