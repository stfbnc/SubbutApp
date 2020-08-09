package it.subbuteo.subbutapp.cup.matches;

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

public class MatchesCupAdapter extends RecyclerView.Adapter<MatchesCupAdapter.ViewHolder> {

    private ArrayList<MatchData> mList;
    private boolean singleMatch;
    private Context ctx;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lp, lr, lr2, rp, rr, rr2, sep;
        public ImageView li, ri;
        public ViewHolder(View v) {
            super(v);
            lp = v.findViewById(R.id.player_left);
            lr = v.findViewById(R.id.res_left);
            lr2 = v.findViewById(R.id.res_left_second);
            rp = v.findViewById(R.id.player_right);
            rr = v.findViewById(R.id.res_right);
            rr2 = v.findViewById(R.id.res_right_second);
            li = v.findViewById(R.id.img_left);
            ri = v.findViewById(R.id.img_right);
            sep = v.findViewById(R.id.dash_sep_second);
        }
    }

    public MatchesCupAdapter(ArrayList<MatchData> lst, boolean snglMtch) {
        mList = lst;
        singleMatch = snglMtch;
    }

    @Override
    public MatchesCupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View match_row = inflater.inflate(R.layout.match_layout_tournament_row, parent, false);
        MatchesCupAdapter.ViewHolder viewHolder = new MatchesCupAdapter.ViewHolder(match_row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesCupAdapter.ViewHolder holder, int position) {
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
            if(!singleMatch) {
                int live2 = mList.get(position).getLive2();
                int res3 = mList.get(position).getRes3();
                holder.lr2.setText(res3 == DataRetriever.NO_RESULT ? "" : String.valueOf(res3));
                holder.lr2.setTextColor(live2 == DataRetriever.NOT_LIVE ? ContextCompat.getColor(ctx, R.color.black) : ContextCompat.getColor(ctx, R.color.red));
                int res4 = mList.get(position).getRes4();
                holder.rr2.setText(res4 == DataRetriever.NO_RESULT ? "" : String.valueOf(res4));
                holder.rr2.setTextColor(live2 == DataRetriever.NOT_LIVE ? ContextCompat.getColor(ctx, R.color.black) : ContextCompat.getColor(ctx, R.color.red));
            }else{
                holder.lr2.setVisibility(View.GONE);
                holder.rr2.setVisibility(View.GONE);
                holder.sep.setVisibility(View.GONE);
            }
            if(!pl1.equals(DataRetriever.EMPTY_PLAYER))
                holder.li.setImageDrawable(ContextCompat.getDrawable(ctx, Globals.plIMgCup.get(pl1)));
            if(!pl2.equals(DataRetriever.EMPTY_PLAYER))
                holder.ri.setImageDrawable(ContextCompat.getDrawable(ctx, Globals.plIMgCup.get(pl2)));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
