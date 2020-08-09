package it.subbuteo.subbutapp.archive.cup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.subbuteo.subbutapp.R;

public class OldCups extends Fragment {

    private it.subbuteo.subbutapp.archive.cup.TabAdapter adapter;

    public OldCups() {}

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
        adapter = new it.subbuteo.subbutapp.archive.cup.TabAdapter(getChildFragmentManager());
        fillAdapter();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    public void fillAdapter(){
        String[] years = {"I NE"};
        int[] layouts = {R.layout.cu_ne1};
        for (int i = 0; i < years.length; i++) {
            it.subbuteo.subbutapp.archive.cup.TabFragment tabFragment = new it.subbuteo.subbutapp.archive.cup.TabFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(getString(R.string.bundle_layout), layouts[i]);
            tabFragment.setArguments(bundle);
            adapter.addFragment(tabFragment, years[i]);
        }
    }

}
