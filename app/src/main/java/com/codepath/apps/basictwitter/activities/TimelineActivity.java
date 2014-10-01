package com.codepath.apps.basictwitter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.handlers.TimelineResponseHandler;
import com.codepath.apps.basictwitter.listeners.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utilities.TwitterApplication;
import com.codepath.apps.basictwitter.utilities.TwitterClient;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TimelineActivity extends Activity {
    private static final int NEW_TWEET_REQUEST = 0;

    @InjectView(R.id.lvTweets)
    ListView lvTweets;
    @InjectView(R.id.tweetsSwipeContainer)
    SwipeRefreshLayout tweetsSwipeContainer;

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient();
        ButterKnife.inject(this);

        setupTimeline();
    }

    private void setupTimeline() {
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        aTweets.addAll(Tweet.getRecentFromLocalStore());
        setupEventListeners();
    }

    private void setupEventListeners() {
        final TimelineResponseHandler timelineResponseHandler = new TimelineResponseHandler(getApplicationContext(), aTweets, tweetsSwipeContainer);

        client.getHomeTimeline(timelineResponseHandler, null, getSinceId());

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                client.getHomeTimeline(timelineResponseHandler, getMaxId(), null);
            }
        });

        tweetsSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                client.getHomeTimeline(timelineResponseHandler, null, getSinceId());
            }
        });
        tweetsSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tweet clickedTweet = (Tweet) adapterView.getAdapter().getItem(i);
                Intent detailRequest = new Intent(getApplicationContext(), TweetDetailActivity.class);
                detailRequest.putExtra("clickedTweet", clickedTweet);
                startActivity(detailRequest);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
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

    public void openComposeTweetActivity(MenuItem menuItem) {
        Intent request = new Intent(this, ComposeTweetActivity.class);
        startActivityForResult(request, NEW_TWEET_REQUEST);
    }

    private Long getMaxId() {
        return (getOldestTweetId() != null) ? getOldestTweetId() - 1 : null;
    }

    private Long getSinceId() {
        return (tweets.size() > 1) ? tweets.get(0).getId() : null;
    }

    private Long getOldestTweetId() {
        if (tweets.size() > 0) {
            return tweets.get(tweets.size() - 1).getUid();
        }

        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == NEW_TWEET_REQUEST) {
            Tweet newTweet = (Tweet) data.getExtras().getSerializable("newTweet");
            TimelineResponseHandler handler = new TimelineResponseHandler(getApplicationContext(), aTweets);
            // Grab interval between freshest existing tweet and newly created tweet
            long maxId = newTweet.getUid() - 1;
            long sinceId = aTweets.getItem(0).getId();
            client.getHomeTimeline(handler, maxId, sinceId);
            aTweets.insert(newTweet, 0);
        }
    }
}
