package by.drobmax.brothersbybottle.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.drobmax.brothersbybottle.R;

/**
 * Created by Admin on 11.08.2015.
 */
public class FragmentArchive extends Fragment {
    @Nullable
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;


    public static FragmentArchive newInstance(int page) {

        FragmentArchive    pageFragment = new FragmentArchive();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    public FragmentArchive() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closed, null);
        return view;
    }
}
