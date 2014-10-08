package com.codepath.apps.basictwitter.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.InvocationTargetException;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private long userId;

    public static UserTimelineFragment newInstance(long userId) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putLong("user_id", userId);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        timelineMethodName = "getUserTimeline";
        userId = getArguments().getLong("user_id", 0);
        super.onCreate(savedInstanceState);
    }

    protected void fetchTimeline(AsyncHttpResponseHandler handler, Long maxId, Long sinceId) {
        try {
            timelineMethod.invoke(client, handler, maxId, sinceId, userId);
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
                    Long.class, Long.class, Long.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
