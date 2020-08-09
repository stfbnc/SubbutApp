package it.subbuteo.subbutapp.archive.supercoppa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.subbuteo.subbutapp.R;

public class OldSupercoppa extends Fragment {

    private it.subbuteo.subbutapp.archive.supercoppa.TabAdapter adapter;

    public OldSupercoppa() {}

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
        adapter = new it.subbuteo.subbutapp.archive.supercoppa.TabAdapter(getChildFragmentManager());
        fillAdapter();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    public void fillAdapter(){
        String[] years = {"I NE", "II NE"};
        int[] layouts = {R.layout.su_ne1, R.layout.su_ne2};
        for (int i = 0; i < years.length; i++) {
            it.subbuteo.subbutapp.archive.supercoppa.TabFragment tabFragment = new it.subbuteo.subbutapp.archive.supercoppa.TabFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(getString(R.string.bundle_layout), layouts[i]);
            tabFragment.setArguments(bundle);
            adapter.addFragment(tabFragment, years[i]);
        }
    }

}
