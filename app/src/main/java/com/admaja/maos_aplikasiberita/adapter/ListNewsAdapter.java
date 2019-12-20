package com.admaja.maos_aplikasiberita.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.admaja.maos_aplikasiberita.R;
import com.admaja.maos_aplikasiberita.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ListNewsAdapter extends BaseAdapter {
    private Fragment fragment;
    private ArrayList<HashMap<String, String>> data;

    public ListNewsAdapter(Fragment f, ArrayList<HashMap<String, String>> d){
        fragment = f;
        data = d;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListNewsViewHolder holder = null;
        if (convertView == null){
            holder = new ListNewsViewHolder();
            convertView = LayoutInflater.from(fragment.getActivity()).inflate(
                    R.layout.item_berita, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.gallery_image);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.sdetails = (TextView) convertView.findViewById(R.id.sdetails);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else {
            holder = (ListNewsViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try {
            holder.author.setText(song.get(Constants.KEY_AUTHOR));
            holder.title.setText(song.get(Constants.KEY_TITLE));
            holder.time.setText(song.get(Constants.KEY_PUBLISHEDAT));
            holder.sdetails.setText(song.get(Constants.KEY_DESCRIPTION));

            if (song.get(Constants.KEY_URLTOIMAGE).toString().length() < 5){
                holder.galleryImage.setVisibility(View.GONE);
            }else {
                Picasso.get()
                        .load(song.get(Constants.KEY_URLTOIMAGE))
                        .resize(300, 200)
                        .centerCrop()
                        .into(holder.galleryImage);
            }
        }catch (Exception e){ }
        return convertView;
    }
    class ListNewsViewHolder{
        ImageView galleryImage;
        TextView author, title, sdetails, time;
    }
}
