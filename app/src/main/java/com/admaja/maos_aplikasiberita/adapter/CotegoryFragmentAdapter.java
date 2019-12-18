package com.admaja.maos_aplikasiberita.adapter;

import android.content.Context;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.admaja.maos_aplikasiberita.R;
import com.admaja.maos_aplikasiberita.fragment.BusinessFragment;
import com.admaja.maos_aplikasiberita.fragment.EntertainmentFreagment;
import com.admaja.maos_aplikasiberita.fragment.HeadlineFragment;
import com.admaja.maos_aplikasiberita.fragment.HealthFragment;
import com.admaja.maos_aplikasiberita.fragment.ScienceFragment;
import com.admaja.maos_aplikasiberita.fragment.SportsFragment;
import com.admaja.maos_aplikasiberita.fragment.TechnologyFragment;
import com.admaja.maos_aplikasiberita.utils.Constants;

public class CotegoryFragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;

    /**
     * Create a new {@link CategoryFragmentPagerAdapter} object.
     *
     * @param context is the context of the app
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     * across swipes.
     */

    public CotegoryFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case Constants.HEADLINE:
                return new HeadlineFragment();
            case Constants.BUSINESS:
                return new BusinessFragment();
            case Constants.ENTERTAIMENT:
                return new EntertainmentFreagment();
            case Constants.HEALTH:
                return new HealthFragment();
            case Constants.SICENCE:
                return new ScienceFragment();
            case Constants.SPORTS:
                return new SportsFragment();
            case Constants.TECHNOLOGY:
                return new TechnologyFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        int titleResId;
        switch(position){
            case Constants.HEADLINE:
                titleResId = R.string.ic_title_home;
                break;
            case Constants.BUSINESS:
                titleResId = R.string.ic_title_business;
                break;
            case Constants.ENTERTAIMENT:
                titleResId = R.string.ic_title_society;
                break;
            case Constants.HEALTH:
                titleResId = R.string.ic_title_environment;
                break;
            case Constants.SICENCE:
                titleResId = R.string.ic_title_science;
                break;
            case Constants.SPORTS:
                titleResId = R.string.ic_title_sport;
                break;
            default:
                titleResId = R.string.ic_title_world;
                break;
        }
        return mContext.getString(titleResId);
    }
}
