package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;

/**
 * Created by rcarino on 10/2/14.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        timelineMethodName = "getMentionsTimeline";
        super.onCreate(savedInstanceState);
    }
}