package it.subbuteo.subbutapp.supercoppa.matches;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.data.DataRetriever;
import it.subbuteo.subbutapp.data.MatchData;
import it.subbuteo.subbutapp.data.OnDataUpdateListener;
import it.subbuteo.subbutapp.data.UpdatableFragment;
import it.subbuteo.subbutapp.globals.Globals;
import it.subbuteo.subbutapp.matchdetails.MatchDetails;
import it.subbuteo.subbutapp.matchdetails.RecyclerItemClickListener;

public class MatchesSupercoppa extends UpdatableFragment {

    private RecyclerView.Adapter fAdapter;
    private ArrayList<MatchData> fList = new ArrayList<>();

    public MatchesSupercoppa() {}

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

        View v = inflater.inflate(R.layout.matches_supercoppa, container, false);

        TextView fDate = v.findViewById(R.id.supercoppa_date);
        fDate.setText(Objects.requireNonNull(DataRetriever.dataHashMapSupercoppa.get(getString(R.string.bundle_supercoppa_tag))).getDate());
        RecyclerView recyclerView_f = v.findViewById(R.id.recycle_final);
        recyclerView_f.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager_f = new LinearLayoutManager(getActivity());
        recyclerView_f.setLayoutManager(layoutManager_f);
        getfList();
        fAdapter = new MatchesSupercoppaAdapter(fList, true);
        recyclerView_f.setAdapter(fAdapter);
        recyclerView_f.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                openDetailsFragment(position, fList, true);
            }
        }));

        final SwipeRefreshLayout pullToRefresh = v.findViewById(R.id.pullToRefreshMatchesSupercoppa);
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
        bndl.putInt(getString(R.string.bundle_competition_type_tag), Globals.SUPERCOPPA);
        MatchDetails mtchDet = new MatchDetails(getActivity(), bndl);
        mtchDet.showDetails();
    }

    private void getfList(){
        fList.addAll(Objects.requireNonNull(DataRetriever.dataHashMapSupercoppa.get(getString(R.string.bundle_supercoppa_tag))).getMatchData());
    }

    private void refreshData(){
        fList.clear();
        getfList();
        fAdapter.notifyDataSetChanged();
    }

}
