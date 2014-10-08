package com.codepath.apps.basictwitter.fragments;

import android.content.Intent;
import android.os.Bundle;

import com.codepath.apps.basictwitter.activities.BaseTwitterActivity;
import com.codepath.apps.basictwitter.handlers.TimelineResponseHandler;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utilities.TwitterApplication;

/**
 * Created by rcarino on 10/2/14.
 */
public class HomeTimelineFragment extends TweetsListFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        timelineMethodName = "getHomeTimeline";
        super.onCreate(savedInstanceState);
        if (!((BaseTwitterActivity)getActivity()).isNetworkAvailable()) {
            aTweets.addAll(Tweet.getRecentFromLocalStore());
        }
    }

    public void handleComposeSuccessResponse(Intent data) {
        Tweet newTweet = (Tweet) data.getExtras().getParcelable("newTweet");
        TimelineResponseHandler handler = new TimelineResponseHandler(getActivity(), aTweets);
        // Grab interval between freshest existing tweet and newly created tweet
        long maxId = newTweet.getUid() - 1;
        long sinceId = aTweets.getItem(0).getUid();
        client.getHomeTimeline(handler, maxId, sinceId);
        aTweets.insert(newTweet, 0);
    }

}

