package com.codepath.apps.basictwitter.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.activities.ComposeTweetActivity;
import com.codepath.apps.basictwitter.activities.ProfileActivity;
import com.codepath.apps.basictwitter.activities.TweetDetailActivity;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.utilities.AttachmentRenderer;
import com.codepath.apps.basictwitter.utilities.ListenerAttacher;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rcarino on 9/26/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    public void saveIfNotCached(Collection<Tweet> collection) {
        for (Tweet tweet : collection) {
            if (Tweet.findByUid(tweet.getUid()) == null) {
                tweet.save();
                Log.w("Tweet adapter", "SAVING TWEET " + tweet);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = setupConvertView(parent);
        }

        Tweet tweet = getItem(position);
        ViewHolder viewHolder = getViewHolder(convertView);
        attachModelDataToViews(tweet, viewHolder);
        setProfileImageClickListener(viewHolder.profileImage);
        ListenerAttacher.attachReplyClickListener(getContext(), viewHolder.reply,
                ComposeTweetActivity.class);

        return convertView;
    }

    private void attachModelDataToViews(Tweet tweet, ViewHolder viewHolder) {
        User user = tweet.getUser();

        ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), viewHolder.profileImage);
        viewHolder.profileImage.setTag(user.getUid());

        viewHolder.fullName.setText(user.getName());
        viewHolder.userName.setText("@" + user.getScreenName());
        viewHolder.timestamp.setText(tweet.getRelativeTimestamp());
        viewHolder.body.setText(tweet.getBody());

        long favoriteCount = tweet.getfavoriteCount();
        if (favoriteCount > 0) {
            viewHolder.favoriteCount.setText(Long.toString(favoriteCount));
        }
        long retweetCount = tweet.getRetweetCount();
        if (retweetCount > 0) {
            viewHolder.retweetsCount.setText(Long.toString(retweetCount));
        }

        AttachmentRenderer.renderAttachment(tweet, viewHolder.attachment);

        viewHolder.reply.setTag(user);
    }

    private void setProfileImageClickListener(ImageView profileImage) {
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent request = new Intent(getContext(), ProfileActivity.class);
                request.putExtra("user_id", (Long) view.getTag());
                getContext().startActivity(request);
            }
        });
    }

    private View setupConvertView(ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
    }

    private ViewHolder getViewHolder(View convertView) {
        assert convertView != null;

        if (convertView.getTag() != null) {
            return (ViewHolder) convertView.getTag();
        } else {
            ViewHolder newViewHolder = new ViewHolder(convertView);
            convertView.setTag(newViewHolder);

            return newViewHolder;
        }
    }


    static class ViewHolder {
        @InjectView(R.id.ivProfileImage)
        ImageView profileImage;
        @InjectView(R.id.tvFullName)
        TextView fullName;
        @InjectView(R.id.tvUserName)
        TextView userName;
        @InjectView(R.id.tvTimestamp)
        TextView timestamp;
        @InjectView(R.id.tvBody)
        TextView body;
        @InjectView(R.id.ivReply)
        ImageView reply;
        @InjectView(R.id.tvfavoriteCount)
        TextView favoriteCount;
        @InjectView(R.id.tvRetweetCount)
        TextView retweetsCount;
        @InjectView(R.id.ivAttachment)
        ImageView attachment;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
