package com.admaja.maos_aplikasiberita.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.admaja.maos_aplikasiberita.R;
import com.admaja.maos_aplikasiberita.utils.Function;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessFragment extends Fragment {

    ListView listNews;
    ProgressBar loader;


    public BusinessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_business, container, false);
        listNews = view.findViewById(R.id.listNewsBusiness);
        loader = view.findViewById(R.id.loader2);
        listNews.setEmptyView(loader);

        if (Function.isNetworkAvailable(getContext())){

        }
        return view;
    }



}
