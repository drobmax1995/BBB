package by.drobmax.brothersbybottle.activities;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import by.drobmax.brothersbybottle.fragments.FragmentMyEvents;
import by.drobmax.brothersbybottle.fragments.FragmentNewEvent;
import by.drobmax.brothersbybottle.fragments.NavigationDrawerFragment;
import by.drobmax.brothersbybottle.R;
import by.drobmax.brothersbybottle.fragments.PagerFragment;
import by.drobmax.brothersbybottle.interfaces.OnReplaceFragmentListener;
import by.drobmax.brothersbybottle.utils.Const;
import by.drobmax.brothersbybottle.utils.ListenerHosting;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnReplaceFragmentListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar toolBar;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        LinearLayout cl = (LinearLayout)findViewById(R.id.containerLayout);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mNavigationDrawerFragment.setContainerLayout(cl);
        ListenerHosting.getInstance().setOnReplaceFragmentListener(this);
        ListenerHosting.getInstance().getOnReplaceFragmentListener().
                onReplaceFragment(Const.ID_MY_EVENTS);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    public void replaceFragment(final Fragment fragment) {
        try {
            Log.d("myLogs", "tryReplace");
            String backStackName = fragment.getClass().getSimpleName();
            FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStackName, 0);
            if (!fragmentPopped && manager.findFragmentByTag(backStackName) == null) {
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment, backStackName);
                fragmentTransaction.addToBackStack(backStackName);
                fragmentTransaction.commitAllowingStateLoss();
            }

        } catch (Exception e) {
            e.printStackTrace();
            replaceFragment(fragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onReplaceFragment(int idPurpose) {
        Fragment fragment = null;
        switch (idPurpose){
            case Const.ID_MY_EVENTS:
                fragment = FragmentMyEvents.newInstance();
                break;
            case Const.ID_NEW_EVENT:
                fragment = FragmentNewEvent.newInstance();
                break;
            case Const.ID_MY_DEBT_BOOK:
                fragment = PagerFragment.newInstance();
                break;
        }
        replaceFragment(fragment);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

    }

}
