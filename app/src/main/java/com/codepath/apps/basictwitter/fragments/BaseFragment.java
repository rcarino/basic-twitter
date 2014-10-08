package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.codepath.apps.basictwitter.activities.BaseTwitterActivity;
import com.codepath.apps.basictwitter.utilities.TwitterApplication;
import com.codepath.apps.basictwitter.utilities.TwitterClient;

/**
 * Created by rcarino on 10/5/14.
 */
public abstract class BaseFragment extends Fragment {
    protected TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }
}
