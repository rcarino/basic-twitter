package com.codepath.apps.basictwitter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.utilities.TwitterApplication;
import com.codepath.apps.basictwitter.utilities.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.Date;

public class ComposeTweetActivity extends Activity {
    private User currentUser;
    private EditText etTweetBody;
    private TextView tvCharacterCount;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        client = TwitterApplication.getRestClient();

        tvCharacterCount = (TextView) findViewById(R.id.tvCharacterCount);
        etTweetBody = (EditText) findViewById(R.id.etTweetBody);
        etTweetBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int tweetLength = etTweetBody.getText().toString().length();
                tvCharacterCount.setText(Integer.toString(tweetLength));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancelTweet(View button) {
        finish();
    }

    public void submitTweet(View button) {
        String tweetBody = etTweetBody.getText().toString();

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
        }, tweetBody);
    }
}
