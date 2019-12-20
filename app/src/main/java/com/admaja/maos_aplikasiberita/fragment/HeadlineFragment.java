package com.admaja.maos_aplikasiberita.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.admaja.maos_aplikasiberita.utils.Constants;
import com.admaja.maos_aplikasiberita.utils.Function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadlineFragment extends Fragment {
    ListView listNews;
    ProgressBar loader;


    public HeadlineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_headline, container, false);
        listNews = view.findViewById(R.id.listNewsUtama);
        loader = view.findViewById(R.id.loader);
        listNews.setEmptyView(loader);

        if (Function.isNetworkAvailable(getContext())){
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        }else {
            Toast.makeText(getContext(), "Koneksi internet tidak ada",Toast.LENGTH_LONG ).show();
        }
        return view;
    }

    class DownloadNews extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country="+ Constants.NEWS_SOURCE+"&apiKey="+Constants.API_KEY);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {
           if (xml.length() > 10){
               try {
                   JSONObject jsonResponse = new JSONObject(xml);
                   JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                   for (int i = 0; i < jsonArray.length(); i++){
                       JSONObject jsonObject = jsonArray.getJSONObject(i);
                       HashMap<String, String> map = new HashMap<>();
                       map.put(Constants.KEY_AUTHOR, jsonObject.optString(Constants.KEY_AUTHOR));
                       map.put(Constants.KEY_TITLE, jsonObject.optString(Constants.KEY_TITLE));
                       map.put(Constants.KEY_DESCRIPTION, jsonObject.optString(Constants.KEY_DESCRIPTION));
                       map.put(Constants.KEY_URL, jsonObject.optString(Constants.KEY_URL));
                       map.put(Constants.KEY_URLTOIMAGE, jsonObject.optString(Constants.KEY_URLTOIMAGE));
                       map.put(Constants.KEY_PUBLISHEDAT, jsonObject.optString(Constants.KEY_PUBLISHEDAT));
                       Constants.dataList.add(map);

                   }
               } catch (JSONException e) {
                   Toast.makeText(getContext(), "Terjadi Masalah", Toast.LENGTH_SHORT).show();
               }

               ListNewsAdapter adapter = new ListNewsAdapter(HeadlineFragment.this, Constants.dataList);
               listNews.setAdapter(adapter);

               listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Intent intent = new Intent(getContext(), DetailsActivity.class);
                       intent.putExtra("url", Constants.dataList.get(+position).get(Constants.KEY_URL));
                       startActivity(intent);
                   }
               });
           }else {
               Toast.makeText(getContext(), "Berita tidak ditemukan", Toast.LENGTH_SHORT).show();
           }
        }
    }

}
