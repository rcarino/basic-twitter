package com.codepath.apps.basictwitter.handlers;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by rcarino on 9/27/14.
 */
public class TimelineResponseHandler extends JsonHttpResponseHandler {
    private Context context;
    private TweetArrayAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    public TimelineResponseHandler(Context context, TweetArrayAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    public TimelineResponseHandler(Context context, TweetArrayAdapter adapter, SwipeRefreshLayout swipeRefreshLayout, ProgressBar progressBar) {
        this(context, adapter);
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.progressBar = progressBar;
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        try {
            if (jsonObject.has("statuses")) {
                onSuccess(jsonObject.getJSONArray("statuses"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
        List<Tweet> tweetList = Tweet.fromJSONArray(jsonArray);
        adapter.saveIfNotCached(tweetList);
        adapter.addAll(tweetList);
        turnOffRefreshingIndicator(swipeRefreshLayout);
        turnOffProgressBar(progressBar);
    }


    @Override
    public void onFailure(Throwable throwable, JSONObject jsonObject) {
        throwable.printStackTrace();
        Toast.makeText(context, "Network is unavailable", Toast.LENGTH_SHORT).show();
        turnOffRefreshingIndicator(swipeRefreshLayout);
        turnOffProgressBar(progressBar);
    }

    private void turnOffRefreshingIndicator(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void turnOffProgressBar(ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}