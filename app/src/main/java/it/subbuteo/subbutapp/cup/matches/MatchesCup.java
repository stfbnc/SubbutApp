package it.subbuteo.subbutapp.cup.matches;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.data.DataRetriever;
import it.subbuteo.subbutapp.data.MatchData;
import it.subbuteo.subbutapp.data.OnDataUpdateListener;
import it.subbuteo.subbutapp.data.UpdatableFragment;
import it.subbuteo.subbutapp.globals.Globals;
import it.subbuteo.subbutapp.matchdetails.MatchDetails;
import it.subbuteo.subbutapp.matchdetails.RecyclerItemClickListener;

public class MatchesCup extends UpdatableFragment {

    private RecyclerView.Adapter qAdapter, sAdapter, ffAdapter;
    private ArrayList<MatchData> qList = new ArrayList<>();
    private ArrayList<MatchData> sList = new ArrayList<>();
    private ArrayList<MatchData> ffList = new ArrayList<>();

    public MatchesCup() {}

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

        View v = inflater.inflate(R.layout.matches_cup, container, false);

        TextView qView = v.findViewById(R.id.quarters_title);
        qView.setText(getString(R.string.quarters));
        RecyclerView recyclerView_q = v.findViewById(R.id.recycle_quarters);
        recyclerView_q.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager_q = new LinearLayoutManager(getActivity());
        recyclerView_q.setLayoutManager(layoutManager_q);
        getqList();
        qAdapter = new MatchesCupAdapter(qList, true);
        recyclerView_q.setAdapter(qAdapter);
        recyclerView_q.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                openDetailsFragment(position, qList, true);
            }
        }));

        TextView sView = v.findViewById(R.id.semifinals_title);
        sView.setText(getString(R.string.semifinals));
        RecyclerView recyclerView_s = v.findViewById(R.id.recycle_semifinals);
        recyclerView_s.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager_s = new LinearLayoutManager(getActivity());
        recyclerView_s.setLayoutManager(layoutManager_s);
        getsList();
        sAdapter = new MatchesCupAdapter(sList, false);
        recyclerView_s.setAdapter(sAdapter);
        recyclerView_s.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                openDetailsFragment(position, sList, false);
            }
        }));

        TextView ffView = v.findViewById(R.id.first_final_title);
        ffView.setText(getString(R.string.first_final));
        RecyclerView recyclerView_ff = v.findViewById(R.id.recycle_first_final);
        recyclerView_ff.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager_ff = new LinearLayoutManager(getActivity());
        recyclerView_ff.setLayoutManager(layoutManager_ff);
        getffList();
        ffAdapter = new MatchesCupAdapter(ffList, true);
        recyclerView_ff.setAdapter(ffAdapter);
        recyclerView_ff.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                openDetailsFragment(position, ffList, true);
            }
        }));

        final SwipeRefreshLayout pullToRefresh = v.findViewById(R.id.pullToRefreshMatchesCup);
        setSwipeRefresh(pullToRefresh, new OnDataUpdateListener() {
            @Override
            public void notifyJsonStringChange() {
                refreshData();
            }
        });

        return v;
    }

    private void openDetailsFragment(int pos, ArrayList<MatchData> list, boolean isSingleMatch){
        Bundle bndl = new Bundle();
        bndl.putString(getString(R.string.bundle_details_tag), new Gson().toJson(list.get(pos)));
        bndl.putBoolean(getString(R.string.bundle_single_match_tag), isSingleMatch);
        bndl.putInt(getString(R.string.bundle_competition_type_tag), Globals.CUP);
        MatchDetails mtchDet = new MatchDetails(getActivity(), bndl);
        mtchDet.showDetails();
    }

    private void getqList(){
        qList.addAll(DataRetriever.dataHashMapCup.get(getString(R.string.quarters)).getMatchData());
    }

    private void getsList(){
        sList.addAll(DataRetriever.dataHashMapCup.get(getString(R.string.semifinals)).getMatchData());
    }

    private void getffList(){
        ffList.addAll(DataRetriever.dataHashMapCup.get(getString(R.string.first_final)).getMatchData());
    }

    private void refreshData(){
        qList.clear();
        sList.clear();
        ffList.clear();
        getqList();
        getsList();
        getffList();
        qAdapter.notifyDataSetChanged();
        sAdapter.notifyDataSetChanged();
        ffAdapter.notifyDataSetChanged();
    }

}
