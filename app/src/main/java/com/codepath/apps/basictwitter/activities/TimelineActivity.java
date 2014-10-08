package com.codepath.apps.basictwitter.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.handlers.TimelineResponseHandler;
import com.codepath.apps.basictwitter.listeners.EndlessScrollListener;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utilities.TwitterApplication;
import com.codepath.apps.basictwitter.utilities.TwitterClient;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TimelineActivity extends BaseTwitterActivity {
    private static final int NEW_TWEET_REQUEST = 0;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        TwitterApplication.setCurrentUser();
        setupTabs();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        setupMenuSearch(menu);
        return true;
    }

    private void setupMenuSearch(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void doSearch(String s) {
        Intent searchRequest = new Intent(this, SearchResultsActivity.class);
        searchRequest.putExtra("query", s);
        startActivity(searchRequest);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == NEW_TWEET_REQUEST) {
            HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.flContainer);
            homeTimelineFragment.handleComposeSuccessResponse(data);
        }
    }

    public void viewProfile(MenuItem menuItem) {
        Intent request = new Intent(this, ProfileActivity.class);
        startActivity(request);
    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("Home")
                .setTag("HomeTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "first",
                                HomeTimelineFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Mentions")
                .setTag("MentionsTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "second",
                                MentionsTimelineFragment.class));

        actionBar.addTab(tab2);
    }
}
