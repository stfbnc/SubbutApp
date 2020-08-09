package it.subbuteo.subbutapp.archive.championship;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.championship.matches.TabAdapter;

public class OldChampionship extends Fragment {

    private it.subbuteo.subbutapp.archive.championship.TabAdapter adapter;

    public OldChampionship() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.tab_fragment_archive, container, false);
        ViewPager viewPager = v.findViewById(R.id.viewPager);
        final TabLayout tabLayout = v.findViewById(R.id.tabLayout);
        adapter = new it.subbuteo.subbutapp.archive.championship.TabAdapter(getChildFragmentManager());
        fillAdapter();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    public void fillAdapter(){
        String[] years = {"I VE", "II VE", "III VE", "IV VE", "V VE", "VI VE", "I NE", "II NE", "III NE", "IV NE"};
        int[] layouts = {R.layout.ch_ve1, R.layout.ch_ve2, R.layout.ch_ve3, R.layout.ch_ve4, R.layout.ch_ve5,
                         R.layout.ch_ve6, R.layout.ch_ne1, R.layout.ch_ne2, R.layout.ch_ne3, R.layout.ch_ne4};
        for (int i = 0; i < years.length; i++) {
            it.subbuteo.subbutapp.archive.championship.TabFragment tabFragment = new it.subbuteo.subbutapp.archive.championship.TabFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(getString(R.string.bundle_layout), layouts[i]);
            tabFragment.setArguments(bundle);
            adapter.addFragment(tabFragment, years[i]);
        }
    }

}
