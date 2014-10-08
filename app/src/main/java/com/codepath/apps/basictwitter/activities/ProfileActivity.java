package com.codepath.apps.basictwitter.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.utilities.TwitterApplication;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileActivity extends BaseTwitterActivity {
    private long currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        currentUserId = TwitterApplication.getCurrentUser().getUid();
        long userId = getIntent().getLongExtra("user_id", currentUserId);
        loadProfileInfo(userId);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(userId);
        ft.replace(R.id.flUserTimeline, userTimelineFragment);
        ft.commit();
    }

    private void loadProfileInfo(long userId) {
        if (userId == currentUserId) {
            TwitterApplication.getRestClient().getCurrentUser(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    loadProfileSuccessHandler(jsonObject);
                }
            });
        } else {
            TwitterApplication.getRestClient().getUserById(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    loadProfileSuccessHandler(jsonObject);
                }
            }, userId);
        }
    }

    private void loadProfileSuccessHandler(JSONObject jsonObject) {
        User user = User.fromJSON(jsonObject);
        getActionBar().setTitle("@" + user.getScreenName());
        populateProfileHeader(user);
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvScreenName= (TextView) findViewById(R.id.tvScreenName);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ImageView ivProfileBanner = (ImageView) findViewById(R.id.ivProfileBanner);

        tvName.setText(user.getName());
        tvScreenName.setText("@" + user.getScreenName());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFriendsCount() + " Following");
        ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
        ImageLoader.getInstance().displayImage(user.getProfileBannerUrl(), ivProfileBanner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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
}
