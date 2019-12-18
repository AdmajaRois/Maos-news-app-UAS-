package com.admaja.maos_aplikasiberita.utils;

import android.text.TextUtils;
import android.util.Log;

import com.admaja.maos_aplikasiberita.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){}


    /**
     * Query the Guardian data set and return a list of {@link News} objects.
     */

    public static List<News> fetchNewsData(String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
           Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<News> newsList = extractFeatureFromJSON(jsonResponse);

        return newsList;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL.", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(Constants.RAD_TIMEOUT);
            urlConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT);
            urlConnection.setRequestMethod(Constants.REQUEST_METHHOD_GET);
            urlConnection.connect();


            if (urlConnection.getResponseCode()==Constants.SUCCESS_RESPONSE_CODE){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code: "+urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retriving the news JSON result.", e);
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        }
        return  jsonResponse;
    }
    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */

    private static List<News> extractFeatureFromJSON(String newsJSON){
        if (TextUtils.isEmpty(newsJSON)){
            return null;
        }

        List<News> newsList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject responseJsonObject = baseJsonResponse.getJSONObject(Constants.JSON_KEY_RESPONSE);
            JSONArray resultArray =  responseJsonObject.getJSONArray(Constants.JSON_KEY_RESULT);

            for (int i = 0; i < resultArray.length(); i++){
                JSONObject currentNews = resultArray.getJSONObject(i);
                String webTitle = currentNews.getString(Constants.JSON_KEY_WEB_TITLE);
                String sectionNAme = currentNews.getString(Constants.JSON_KEY_SECTION_NAME);
                String webPublicationDate = currentNews.getString(Constants.JSON_KEY_WEB_PUBLICATION_DATE);
                String webUrl = currentNews.getString(Constants.JSON_KEY_WEB_URl);


                String author = null;
                if (currentNews.has(Constants.JSON_KEY_TAGS)) {
                    JSONArray tagsArray = currentNews.getJSONArray(Constants.JSON_KEY_TAGS);
                    if (tagsArray.length() != 0){
                        JSONObject firstTagsItem = tagsArray.getJSONObject(0);
                        author = firstTagsItem.getString(Constants.JSON_KEY_WEB_TITLE);
                    }
                }

                String thumnail = null;
                String trailText = null;

                if (currentNews.has(Constants.JSON_KEY_FIELDS)){
                    JSONObject fieldsObjecct = currentNews.getJSONObject(Constants.JSON_KEY_FIELDS);

                    if (fieldsObjecct.has(Constants.JSON_KEY_THUMBNAIL)){
                        thumnail = fieldsObjecct.getString(Constants.JSON_KEY_THUMBNAIL);
                    }

                    if (fieldsObjecct.has(Constants.JSON_KEY_TRAIL_TEXT)){
                        trailText = fieldsObjecct.getString(Constants.JSON_KEY_TRAIL_TEXT);
                    }
                }

                News news = new News(webTitle, sectionNAme, author, webPublicationDate,webUrl, thumnail,trailText );


                newsList.add(news);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem pairing the news JSON result", e);
        }

        return newsList;
    }


}
