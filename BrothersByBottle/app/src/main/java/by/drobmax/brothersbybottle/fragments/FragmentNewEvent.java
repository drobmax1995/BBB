package by.drobmax.brothersbybottle.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.drobmax.brothersbybottle.R;

/**
 * Created by Admin on 11.08.2015.
 */
public class FragmentNewEvent extends Fragment {


    public static FragmentNewEvent newInstance() {
        FragmentNewEvent    pageFragment = new FragmentNewEvent();
        return pageFragment;
    }

    public FragmentNewEvent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new, null);
        return view;
    }
}
