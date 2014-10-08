package com.codepath.apps.basictwitter.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.utilities.AttachmentRenderer;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TweetDetailActivity extends BaseTwitterActivity {
    @InjectView(R.id.ivProfileImage)
    ImageView ivProfileImage;

    @InjectView(R.id.tvFullName)
    TextView tvFullName;

    @InjectView(R.id.tvUserName)
    TextView tvUserName;

    @InjectView(R.id.tvBody)
    TextView tvBody;

    @InjectView(R.id.ivAttachment)
    ImageView ivAttachment;

    @InjectView(R.id.etReply)
    EditText etReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        ButterKnife.inject(this);

        Tweet clickedTweet = (Tweet) getIntent().getParcelableExtra("clickedTweet");
        populateViews(clickedTweet);
        AttachmentRenderer.renderAttachment(clickedTweet, ivAttachment);
        setupEditTextReply(etReply);
    }

    private void populateViews(Tweet tweet) {
        User user = tweet.getUser();

        ivProfileImage.setImageResource(android.R.color.transparent);
        ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);

        tvFullName.setText(user.getName());
        tvUserName.setText("@" + user.getScreenName());
        tvBody.setText(tweet.getBody());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweet_detail, menu);
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

    public void submitTweet(View button) {
        handleTweetSubmission(etReply.getText().toString());
    }
}
