package it.subbuteo.subbutapp.championship.defense;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.data.DefenseData;
import it.subbuteo.subbutapp.data.OnDataUpdateListener;
import it.subbuteo.subbutapp.data.UpdatableFragment;
import it.subbuteo.subbutapp.championship.ranking.RankingMatrix;

public class Defense extends UpdatableFragment {

    private RecyclerView.Adapter adapter;
    private ArrayList<DefenseData> lst = new ArrayList<>();

    public Defense() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        refreshData();
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        refreshData();
        super.onHiddenChanged(hidden);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.defense, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycle_ranking);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        getList();
        adapter = new DefenseAdapter(lst);
        recyclerView.setAdapter(adapter);

        final SwipeRefreshLayout pullToRefresh = v.findViewById(R.id.pullToRefreshRankingDefense);
        setSwipeRefresh(pullToRefresh, new OnDataUpdateListener() {
            @Override
            public void notifyJsonStringChange() {
                refreshData();
            }
        });

        return v;
    }

    private void refreshData(){
        lst.clear();
        getList();
        adapter.notifyDataSetChanged();
    }

    private void getList(){
        RankingMatrix rankMtx = new RankingMatrix();
        Hashtable<String, DefenseData> ht = rankMtx.getDefenseMatrix();
        lst.addAll(ht.values());
        Collections.sort(lst, new Comparator<DefenseData>() {
            @Override
            public int compare(DefenseData d1, DefenseData d2) {
                return Integer.compare(d1.getPosition(), d2.getPosition());
            }
        });
    }

}
