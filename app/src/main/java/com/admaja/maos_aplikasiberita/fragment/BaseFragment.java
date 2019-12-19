package com.admaja.maos_aplikasiberita.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admaja.maos_aplikasiberita.EmptyRecyclerView;
import com.admaja.maos_aplikasiberita.News;
import com.admaja.maos_aplikasiberita.R;
import com.admaja.maos_aplikasiberita.adapter.NewsAdapter;
import com.admaja.maos_aplikasiberita.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * The BaseArticlesFragment is a {@link Fragment} subclass that implements the LoaderManager.LoaderCallbacks
 * interface in order for Fragment to be a client that interacts with the LoaderManager. It is
 * base class that is responsible for displaying a set of articles, regardless of type.
 */
public class BaseFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String LOG_TAG = BaseFragment.class.getName();

  private static final int  NEWS_LOADER_ID = 1;

  private NewsAdapter mAdapter;

  private TextView mEmptyStateTextView;

  private View mLoadingIndicator;

    /** The {@link SwipeRefreshLayout} that detects swipe gestures and
     * triggers callbacks in the app.
     */

  private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_headline, container, false);

        EmptyRecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(layoutManager);

        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.swipe_color_1),
               getResources().getColor(R.color.swipe_color_2),
                getResources().getColor(R.color.swipe_color_3),
                getResources().getColor(R.color.swipe_color_4));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, "onRefresh called from SwiperefreshLayout");

                initiateRefresh();
                Toast.makeText(getActivity(), "Sedang Merefresh", Toast.LENGTH_SHORT).show();
            }
        });

        mLoadingIndicator = rootView.findViewById(R.id.loading_indicatot);

        mEmptyStateTextView= rootView.findViewById(R.id.empty_view);
        mRecyclerView.setmEmptyView(mEmptyStateTextView);

        mAdapter =new NewsAdapter(getActivity(), new ArrayList<News>());

        mRecyclerView.setAdapter(mAdapter);

        initializeLoader(isConnected());

        return rootView;
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> newsData) {
        mLoadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText("Berita baru tidak ditemukan");

        mAdapter.clearAll();

        if (newsData != null && !newsData.isEmpty()){
            mAdapter.addAll(newsData);
        }

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        mAdapter.clearAll();
    }

    @Override
    public void onResume() {
        super.onResume();
        restartLoader(isConnected());
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
    /**
     * If there is internet connectivity, initialize the loader as
     * usual. Otherwise, hide loading indicator and set empty state TextView to display
     * "No internet connection."
     *
     * @param isConnected internet connection is available or not
     */
    private void initializeLoader(boolean isConnected){
        if (isConnected){
            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }else {
            mLoadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText("Anda sedang offline, cek koneksi internet anda");
            mEmptyStateTextView.setCompoundDrawablesWithIntrinsicBounds(Constants.DEFAULT_NUMBER,
                    R.drawable.ic_network_check_black_24dp, Constants.DEFAULT_NUMBER,Constants.DEFAULT_NUMBER);

        }
    }
    /**
     * Restart the loader if there is internet connectivity.
     * @param isConnected internet connection is available or not
     */

    private void restartLoader(boolean isConnected){
        if (isConnected){
            LoaderManager loaderManager = getLoaderManager();

            loaderManager.restartLoader(NEWS_LOADER_ID,null,this);
        }else {
            mLoadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText("Kamu sedang offline, cek jaringan anda");
            mEmptyStateTextView.setCompoundDrawablesWithIntrinsicBounds(Constants.DEFAULT_NUMBER,
                    R.drawable.ic_network_check_black_24dp, Constants.DEFAULT_NUMBER, Constants.DEFAULT_NUMBER);

            mSwipeRefreshLayout.setVisibility(View.GONE);
        }
    }

    private void initiateRefresh(){restartLoader(isConnected());}
}
