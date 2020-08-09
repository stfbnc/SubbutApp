package it.subbuteo.subbutapp.championship.matches;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.data.DataRetriever;
import it.subbuteo.subbutapp.data.OnDataUpdateListener;
import it.subbuteo.subbutapp.data.UpdatableFragment;

public class Matches extends UpdatableFragment {

    private TabAdapter adapter;
    private int num_tabs;

    public Matches() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        num_tabs = DataRetriever.dataHashMap.size();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshTabFragmentsData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        refreshTabFragmentsData();
        super.onHiddenChanged(hidden);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.tab_fragment, container, false);
        ViewPager viewPager = v.findViewById(R.id.viewPager);
        final TabLayout tabLayout = v.findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getChildFragmentManager());
        fillAdapter();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        final SwipeRefreshLayout pullToRefresh = v.findViewById(R.id.pullToRefresh);
        setSwipeRefresh(pullToRefresh, new OnDataUpdateListener() {
            @Override
            public void notifyJsonStringChange() {
                refreshTabFragmentsData();
            }
        });

        return v;
    }

    public void fillAdapter(){
        for (int i = 0; i < num_tabs; i++) {
            String iStr = Integer.toString(i + 1);
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.bundle_tab), iStr);
            tabFragment.setArguments(bundle);
            adapter.addFragment(tabFragment, getString(R.string.matches) + " " + iStr);
        }
    }

    public void refreshTabFragmentsData() {
        for (int i = 0; i < num_tabs; i++) {
            try {
                TabFragment f = (TabFragment) adapter.getItem(i);
                f.refreshData();
            } catch (Exception e) {
                Log.d("frag_not_open", "Fragment not open");
            }
        }
    }

}
