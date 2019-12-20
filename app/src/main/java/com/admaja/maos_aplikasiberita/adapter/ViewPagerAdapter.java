package com.admaja.maos_aplikasiberita.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.admaja.maos_aplikasiberita.fragment.BusinessFragment;
import com.admaja.maos_aplikasiberita.fragment.EntertainmentFragment;
import com.admaja.maos_aplikasiberita.fragment.HeadlineFragment;
import com.admaja.maos_aplikasiberita.fragment.HealthFragment;
import com.admaja.maos_aplikasiberita.fragment.ScienceFragment;
import com.admaja.maos_aplikasiberita.fragment.SportsFragment;
import com.admaja.maos_aplikasiberita.fragment.TechnologyFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> fragmentsTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        fragmentsTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }
}
