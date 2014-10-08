package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by rcarino on 10/7/14.
 */
public class SearchResultsFragment extends TweetsListFragment {
   private String query;

    public static SearchResultsFragment newInstance(String query) {
        SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        searchResultsFragment.setArguments(args);
        return searchResultsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        timelineMethodName = "searchForTweets";
        query = getArguments().getString("query");
        super.onCreate(savedInstanceState);
    }

    protected void fetchTimeline(AsyncHttpResponseHandler handler, Long maxId, Long sinceId) {
        try {
            timelineMethod.invoke(client, handler, maxId, sinceId, query);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void setTimelineMethodFromName(String timelineMethodName) {
        try {
            timelineMethod = client.getClass().getMethod(timelineMethodName,
                    AsyncHttpResponseHandler.class,
                    Long.class, Long.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
