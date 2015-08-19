package by.drobmax.brothersbybottle.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.drobmax.brothersbybottle.R;
import by.drobmax.brothersbybottle.adapters.ViewPagerAdapter;
import by.drobmax.brothersbybottle.utils.SlidingTabLayout;


/**
 * Created by Admin on 05.08.2015.
 */
public class PagerFragment extends Fragment {
    SlidingTabLayout tabs;
    CharSequence Titles[]= {"reserve","credit","archive"};
    //{getString(R.string.to_me), getString(R.string.from_me), getString(R.string.archive)};
    int Numboftabs = 3;


    public static PagerFragment newInstance() {
        PagerFragment   pageFragment = new PagerFragment();
        return pageFragment;
    }

    public PagerFragment() {
//        Titles[0] = getString(R.string.to_me);
//        Titles[1] = getString(R.string.from_me);
//        Titles[2] = getString(R.string.archive);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    PagerAdapter adapter;
    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, null);
        pager = (ViewPager) view.findViewById(R.id.pager1);
        adapter = new ViewPagerAdapter(getActivity().
                getSupportFragmentManager(), Titles, Numboftabs);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        return view;
    }

}
