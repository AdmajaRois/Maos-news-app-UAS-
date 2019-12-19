package com.admaja.maos_aplikasiberita.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.admaja.maos_aplikasiberita.News;
import com.admaja.maos_aplikasiberita.R;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context mContext;
    private List<News> mNewsList;
    private SharedPreferences sharePref;

    /**
     * Constructs a new {@link NewsAdapter}
     * @param context of the app
     * @param newsList is the list of news, which is the data source of the adapter
     */

    public NewsAdapter(Context context, List<News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView sectionTextView;
        private TextView authorTextView;
        private TextView dateTextView;
        private ImageView thumbnailImageView;
        private ImageView sharedImageView;
        private TextView trailTextView;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_card);
            sectionTextView = itemView.findViewById(R.id.section_card);
            authorTextView = itemView.findViewById(R.id.author_card);
            dateTextView = itemView.findViewById(R.id.date_card);
            thumbnailImageView = itemView.findViewById(R.id.thumbnail_image_card);
            sharedImageView = itemView.findViewById(R.id.share_image_card);
            trailTextView = itemView.findViewById(R.id.trail_text_card);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        sharePref = PreferenceManager.getDefaultSharedPreferences(mContext);

        setColorTheme(holder);

        setTextSize(holder);

        final  News currentNews = mNewsList.get(position);

        holder.titleTextView.setText(currentNews.getmTitle());
        holder.sectionTextView.setText(currentNews.getmSection());

        if (currentNews.getmAuthor()==null){
            holder.authorTextView.setVisibility(View.GONE);
        }else {
            holder.authorTextView.setVisibility(View.VISIBLE);
            holder.authorTextView.setText(currentNews.getmAuthor());
        }

        holder.dateTextView.setText(getTimeDifference(currentNews.getmDate()));


        String trailTextHTML = currentNews.getmTrailTextHtml();
        holder.trailTextView.setText(Html.fromHtml(Html.fromHtml(trailTextHTML).toString()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri newsUri = Uri.parse(currentNews.getmUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                mContext.startActivity(websiteIntent);
            }
        });

        if (currentNews.getmThumbnail() == null){
            holder.thumbnailImageView.setVisibility(View.GONE);
        }else {
            holder.thumbnailImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext.getApplicationContext())
                    .load(currentNews.getmThumbnail())
                    .into(holder.thumbnailImageView);
        }

        holder.sharedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData(currentNews);
            }
        });
    }

    private void setColorTheme(ViewHolder holder){
        String colorTheme = sharePref.getString(
                "color theme",
                "white"
        );

        if (colorTheme.equals(mContext.getString(R.string.settings_color_white_value))) {
            holder.titleTextView.setBackgroundResource(R.color.white);
            holder.titleTextView.setTextColor(Color.BLACK);
        }else if (colorTheme.equals(mContext.getString(R.string.settings_color_sky_blue_value))) {
            holder.titleTextView.setBackgroundResource(R.color.nav_bar_start);
            holder.titleTextView.setTextColor(Color.WHITE);
        } else if (colorTheme.equals(mContext.getString(R.string.settings_color_dark_blue_value))) {
            holder.titleTextView.setBackgroundResource(R.color.color_app_bar_text);
            holder.titleTextView.setTextColor(Color.WHITE);
        } else if (colorTheme.equals(mContext.getString(R.string.settings_color_violet_value))) {
            holder.titleTextView.setBackgroundResource(R.color.violet);
            holder.titleTextView.setTextColor(Color.WHITE);
        } else if (colorTheme.equals(mContext.getString(R.string.settings_color_light_green_value))) {
            holder.titleTextView.setBackgroundResource(R.color.light_green);
            holder.titleTextView.setTextColor(Color.WHITE);
        } else if (colorTheme.equals(mContext.getString(R.string.settings_color_green_value))) {
            holder.titleTextView.setBackgroundResource(R.color.color_section);
            holder.titleTextView.setTextColor(Color.WHITE);
        }
    }

    private  void  setTextSize(ViewHolder holder) {
        String textSize = sharePref.getString(
                mContext.getString(R.string.settings_text_size_key),
                mContext.getString(R.string.settings_text_size_default));

        if(textSize.equals(mContext.getString(R.string.settings_text_size_medium_value))) {
            holder.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp22));
            holder.sectionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp14));
            holder.trailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp16));
            holder.authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp14));
            holder.dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp14));
        } else if(textSize.equals(mContext.getString(R.string.settings_text_size_small_value))) {
            holder.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp20));
            holder.sectionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp12));
            holder.trailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp14));
            holder.authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp12));
            holder.dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp12));
        } else if(textSize.equals(mContext.getString(R.string.settings_text_size_large_value))) {
            holder.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp24));
            holder.sectionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp16));
            holder.trailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp18));
            holder.authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp16));
            holder.dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.sp16));
        }
    }

    /**
     * Share the article with friends in social network
     * @param news {@link News} object
     */

    private void shareData(News news){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT,
                news.getmTitle()+" : "+news.getmUrl());

        mContext.startActivity(Intent.createChooser(sharingIntent, "Share Article"));
    }

    /**
     *  Clear all data (a list of {@link News} objects)
     */

    public void clearAll(){
        mNewsList.clear();
        notifyDataSetChanged();
    }

    /**
     * Add  a list of {@link News}
     * @param newsList is the list of news, which is the data source of the adapter
     */

    public  void addAll(List<News> newsList){
        mNewsList.clear();
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    /**
     * Convert date and time in UTC (webPublicationDate) into a more readable representation
     * in Local time
     *
     * @param dateStringUTC is the web publication date of the article (i.e. 2014-02-04T08:00:00Z)
     * @return the formatted date string in Local time(i.e "Jan 1, 2000  2:15 AM")
     * from a date and time in UTC
     */

    private String formatDate(String dateStringUTC){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd'T'kk:mm:ss'Z'");
        Date dateObject  =null;
        try {
            dateObject = simpleDateFormat.parse(dateStringUTC);
        }catch (ParseException e){
            e.printStackTrace();
        }

        SimpleDateFormat df = new SimpleDateFormat("MM d, yyyy h:mm a", Locale.ROOT);
        String formattedDateUTC = df.format(dateObject);

        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(formattedDateUTC);
            df.setTimeZone(TimeZone.getDefault());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(date);
    }

    /**
     * Get the formatted web publication date string in milliseconds
     * @param formattedDate the formatted web publication date string
     * @return the formatted web publication date in milliseconds
     */

    private static long getDateInMillis(String formattedDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a");
        long dateInMillis;
        Date dateObject;
        try {
            dateObject = simpleDateFormat.parse(formattedDate);
            dateInMillis = dateObject.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            Log.e("Problem parsing date", e.getMessage() );
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get the time difference between the current date and web publication date
     * @param formattedDate the formatted web publication date string
     * @return time difference (i.e "9 hours ago")
     */

    private CharSequence getTimeDifference(String formattedDate) {
        long currentTime = System.currentTimeMillis();
        long publicationTime = getDateInMillis(formattedDate);
        return DateUtils.getRelativeTimeSpanString(publicationTime, currentTime,
                DateUtils.SECOND_IN_MILLIS);
    }

}
