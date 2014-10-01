package com.codepath.apps.basictwitter.models;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by rcarino on 9/26/14.
 */
@Table(name = "Tweets")
public class Tweet extends Model implements Serializable {
    private static final String TWITTER_TIMESTAMP_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    @Column(name = "body")
    private String body;

    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.ABORT)
    private long uid;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "user", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;

    public Tweet() {
        super();
    }

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return tweet;
    }

    public static List<Tweet> getRecentFromLocalStore() {
        List<Tweet> result = new Select()
                .from(Tweet.class)
                .orderBy("uid DESC")
                .execute();

        for (Tweet t : result) {
            Log.w("LOCAL TWEETS", t.toString());
        }

        return result;
    }

    public static Tweet findByUid(long uid) {
        return new Select().from(Tweet.class).where("uid = ?", uid).executeSingle();
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJSON(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRelativeTimestamp() {
        SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(TWITTER_TIMESTAMP_FORMAT, Locale.ENGLISH);
        simpleDateFormatter.setLenient(true);

        try {
            Date createdDate = simpleDateFormatter.parse(getCreatedAt());
            PrettyTime prettyTimeFormatter = new PrettyTime();
            return prettyTimeFormatter.format(createdDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return getUser().getScreenName() + ": " + getBody() + " - " + getUid();
    }
}
