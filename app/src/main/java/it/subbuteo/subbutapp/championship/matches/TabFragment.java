package it.subbuteo.subbutapp.championship.matches;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import it.subbuteo.subbutapp.globals.Globals;
import it.subbuteo.subbutapp.matchdetails.MatchDetails;
import it.subbuteo.subbutapp.matchdetails.RecyclerItemClickListener;

public class TabFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private ArrayList<MatchData> lst = new ArrayList<>();
    private String dayNum;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        View view = inflater.inflate(R.layout.match_layout, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_match);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Bundle bundle = getArguments();
        dayNum = bundle.get(getString(R.string.bundle_tab)).toString();
        lst.addAll(DataRetriever.dataHashMap.get(dayNum).getMatchData());
        mAdapter = new MatchesListAdapter(lst, getActivity());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                openDetailsFragment(position, lst, true);
            }
        }));

        String rest_player = DataRetriever.dataHashMap.get(dayNum).getRest();
        TextView tv_rest = view.findViewById(R.id.rest_pl);
        if(!rest_player.equals("")) {
            String txt = getString(R.string.rest_str) + " " + rest_player;
            tv_rest.setText(txt);
        }else{
            tv_rest.setVisibility(View.GONE);
        }

        return view;
    }

    private void openDetailsFragment(int pos, ArrayList<MatchData> list, boolean isSingleMatch){
        Bundle bndl = new Bundle();
        bndl.putString(getString(R.string.bundle_details_tag), new Gson().toJson(list.get(pos)));
        bndl.putBoolean(getString(R.string.bundle_single_match_tag), isSingleMatch);
        bndl.putInt(getString(R.string.bundle_competition_type_tag), Globals.CHAMPIONSHIP);
        MatchDetails mtchDet = new MatchDetails(getActivity(), bndl);
        mtchDet.showDetails();
    }

    public void refreshData(){
        lst.clear();
        lst.addAll(DataRetriever.dataHashMap.get(dayNum).getMatchData());
        mAdapter.notifyDataSetChanged();
    }

}
