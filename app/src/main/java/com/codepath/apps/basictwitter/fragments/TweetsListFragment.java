package com.codepath.apps.basictwitter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.activities.TweetDetailActivity;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.handlers.TimelineResponseHandler;
import com.codepath.apps.basictwitter.listeners.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TweetsListFragment extends BaseFragment {
    protected ArrayList<Tweet> tweets;
    protected TweetArrayAdapter aTweets;
    protected String timelineMethodName;
    protected Method timelineMethod;

    @InjectView(R.id.lvTweets)
    ListView lvTweets;
    @InjectView(R.id.tweetsSwipeContainer)
    SwipeRefreshLayout tweetsSwipeContainer;
    @InjectView(R.id.pbLoading)
    ProgressBar pbLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
        setTimelineMethodFromName(timelineMethodName);
    }

    protected void setTimelineMethodFromName(String timelineMethodName) {
        try {
            timelineMethod = client.getClass().getMethod(timelineMethodName,
                    AsyncHttpResponseHandler.class,
                    Long.class, Long.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        ButterKnife.inject(this, view);
        fetchTimeline(new TimelineResponseHandler(getActivity(), aTweets, tweetsSwipeContainer, pbLoading),
                null, getSinceId());
        lvTweets.setAdapter(aTweets);
        setupViewListeners();
        return view;
    }

    public void addAll(ArrayList<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void setupViewListeners() {
        final TimelineResponseHandler timelineResponseHandler =
                new TimelineResponseHandler(getActivity(), aTweets, tweetsSwipeContainer, pbLoading);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchTimeline(timelineResponseHandler, getMaxId(), null);
            }
        });

        tweetsSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimeline(timelineResponseHandler, null, getSinceId());
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
                User clickedTweetUser = (User) clickedTweet.getUser();
                Intent detailRequest = new Intent(getActivity(), TweetDetailActivity.class);
                detailRequest.putExtra("clickedTweet", clickedTweet);
                detailRequest.putExtra("reply_to_id", Long.toString(clickedTweetUser.getUid()));
                detailRequest.putExtra("reply_to_screen_name", clickedTweetUser.getScreenName());
                startActivity(detailRequest);
            }
        });
    }

    protected void fetchTimeline(AsyncHttpResponseHandler handler, Long maxId, Long sinceId) {
        pbLoading.setVisibility(View.VISIBLE);
        try {
            timelineMethod.invoke(client, handler, maxId, sinceId);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Long getMaxId() {
        return (getOldestTweetId() != null) ? getOldestTweetId() - 1 : null;
    }

    private Long getOldestTweetId() {
        if (tweets != null && tweets.size() > 0) {
            return tweets.get(tweets.size() - 1).getUid();
        }

        return null;
    }

    protected Long getSinceId() {
        return (tweets != null && tweets.size() > 1) ? tweets.get(0).getUid() : null;
    }
}
