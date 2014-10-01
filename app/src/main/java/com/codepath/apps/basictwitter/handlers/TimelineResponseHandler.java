package com.codepath.apps.basictwitter.handlers;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by rcarino on 9/27/14.
 */
public class TimelineResponseHandler extends JsonHttpResponseHandler {
    private Context context;
    private TweetArrayAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public TimelineResponseHandler(Context context, TweetArrayAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    public TimelineResponseHandler(Context context, TweetArrayAdapter adapter, SwipeRefreshLayout swipeRefreshLayout) {
        this(context, adapter);
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
        List<Tweet> tweetList = Tweet.fromJSONArray(jsonArray);
        adapter.addAndSaveIfNotCached(tweetList);
        turnOffRefreshingIndicator(swipeRefreshLayout);
    }


    @Override
    public void onFailure(Throwable throwable, JSONObject jsonObject) {
        throwable.printStackTrace();
        Toast.makeText(context, "Network is unavailable", Toast.LENGTH_SHORT).show();
        turnOffRefreshingIndicator(swipeRefreshLayout);
    }

    private void turnOffRefreshingIndicator(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}