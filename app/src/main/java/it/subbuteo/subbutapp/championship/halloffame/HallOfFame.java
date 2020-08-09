package it.subbuteo.subbutapp.championship.halloffame;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import it.subbuteo.subbutapp.data.HallOfFameData;
import it.subbuteo.subbutapp.data.OnDataUpdateListener;
import it.subbuteo.subbutapp.data.UpdatableFragment;
import it.subbuteo.subbutapp.championship.ranking.RankingMatrix;

public class HallOfFame extends UpdatableFragment {

    private RecyclerView.Adapter adapter;
    private ArrayList<HallOfFameData> lst = new ArrayList<>();

    public HallOfFame() {}

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.hall_of_fame, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycle_ranking);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        getList();
        adapter = new HallOfFameAdapter(lst);
        recyclerView.setAdapter(adapter);

        final SwipeRefreshLayout pullToRefresh = v.findViewById(R.id.pullToRefreshRankingHalloffame);
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
        Hashtable<String, HallOfFameData> ht = rankMtx.getHOFMatrix();
        lst.addAll(ht.values());
        Collections.sort(lst, new Comparator<HallOfFameData>() {
            @Override
            public int compare(HallOfFameData h1, HallOfFameData h2) {
                return Integer.compare(h1.getPosition(), h2.getPosition());
            }
        });
    }

}
