package com.codepath.apps.basictwitter.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Created by rcarino on 9/26/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    public void addAndSaveIfNotCached(Collection<Tweet> collection) {
        for (Tweet tweet : collection) {
            if (Tweet.findByUid(tweet.getUid()) == null) {
                add(tweet);
                tweet.save();
                Log.w("Tweet adapter", "SAVING TWEET " + tweet);
            }
        }

        sort(new Comparator<Tweet>() {
            @Override
            public int compare(Tweet tweet, Tweet tweet2) {
                if (tweet.getUid() < tweet2.getUid()) {
                    return 1;
                } else if (tweet.getUid() > tweet2.getUid()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = setupConvertView(parent);
        }

        Tweet tweet = getItem(position);
        ViewHolder viewHolder = getViewHolder(convertView);
        attachModelDataToViews(tweet, viewHolder);

        return convertView;
    }

    private void attachModelDataToViews(Tweet tweet, ViewHolder viewHolder) {
        User user = tweet.getUser();

        viewHolder.profileImage.setImageResource(android.R.color.transparent);
        ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), viewHolder.profileImage);

        viewHolder.fullName.setText(user.getName());
        viewHolder.userName.setText("@" + user.getScreenName());
        viewHolder.timestamp.setText(tweet.getRelativeTimestamp());
        viewHolder.body.setText(tweet.getBody());
    }

    private View setupConvertView(ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
    }

    private ViewHolder getViewHolder(View convertView) {
        assert convertView != null;

        if (convertView.getTag() != null) {
            return (ViewHolder) convertView.getTag();
        } else {
            ViewHolder newViewHolder = new ViewHolder();
            populateViewHolder(newViewHolder, convertView);
            convertView.setTag(newViewHolder);

            return newViewHolder;
        }
    }

    private void populateViewHolder(ViewHolder viewHolder, View convertView) {
        viewHolder.profileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        viewHolder.fullName = (TextView) convertView.findViewById(R.id.tvFullName);
        viewHolder.userName = (TextView) convertView.findViewById(R.id.tvUserName);
        viewHolder.timestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
        viewHolder.body = (TextView) convertView.findViewById(R.id.tvBody);
    }

    private static class ViewHolder {
        ImageView profileImage;
        TextView fullName;
        TextView userName;
        TextView timestamp;
        TextView body;
    }
}
