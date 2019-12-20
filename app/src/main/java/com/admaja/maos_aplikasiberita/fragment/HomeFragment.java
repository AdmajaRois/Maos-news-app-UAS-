package com.admaja.maos_aplikasiberita.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.admaja.maos_aplikasiberita.R;
import com.admaja.maos_aplikasiberita.activity.DetailsActivity;
import com.admaja.maos_aplikasiberita.adapter.ListNewsAdapter;
import com.admaja.maos_aplikasiberita.adapter.ViewPagerAdapter;
import com.admaja.maos_aplikasiberita.utils.Constants;
import com.admaja.maos_aplikasiberita.utils.Function;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new HeadlineFragment(), "BERITA UTAMA");
        adapter.addFragment(new BusinessFragment(), "BISNIS");
        adapter.addFragment(new EntertainmentFragment(), "ENTERTAINMENT");
        adapter.addFragment(new HealthFragment(), "KESEHATAN");
        adapter.addFragment(new ScienceFragment(), "SAINS");
        adapter.addFragment(new SportsFragment(), "OLAHRAGA");
        adapter.addFragment(new TechnologyFragment(), "TEKNOLOGI");
        viewPager.setAdapter(adapter);
    }
}