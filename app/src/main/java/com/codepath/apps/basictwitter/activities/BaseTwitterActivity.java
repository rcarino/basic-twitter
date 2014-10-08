package com.codepath.apps.basictwitter.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utilities.TwitterApplication;
import com.codepath.apps.basictwitter.utilities.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

/**
 * Created by rcarino on 10/5/14.
 */
public abstract class BaseTwitterActivity extends FragmentActivity {
    protected TwitterClient client;
    protected String replyToId;
    protected String replyToScreenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }

    protected void setupEditTextReply(EditText reply) {
        replyToId = getIntent().getStringExtra("reply_to_id");
        replyToScreenName = getIntent().getStringExtra("reply_to_screen_name");
        if (replyToScreenName != null) {
            reply.setText("@" + replyToScreenName);
        }
    }

    protected void handleTweetSubmission(String tweetContents) {
        client.submitTweet(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Tweet newTweet = Tweet.fromJSON(jsonObject);
                newTweet.save();
                Intent response = new Intent();
                response.putExtra("newTweet", newTweet);

                setResult(RESULT_OK, response);
                finish();
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                throwable.printStackTrace();
                Toast.makeText(getApplicationContext(), "Failed to submit new tweet", Toast.LENGTH_SHORT).show();
            }
        }, tweetContents, replyToId);
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        if (!isAvailable) {
            Toast.makeText(this, "Network is not currently available. Displaying Content from local store", Toast.LENGTH_LONG).show();
        }

        return isAvailable;
    }
}
