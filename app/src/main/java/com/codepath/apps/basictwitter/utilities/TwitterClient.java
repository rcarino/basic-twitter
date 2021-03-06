package com.codepath.apps.basictwitter.utilities;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "XGEdNyjZ6FbTznsUBDXZLjOd9";
    public static final String REST_CONSUMER_SECRET = "oZ6O318HVCwXvT4ZSZr6sisqebJKqig83yX8uszeV1kh4nH3pm";
    public static final String REST_CALLBACK_URL = "oauth://cpbasictweets";

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getHomeTimeline(AsyncHttpResponseHandler handler, Long maxId, Long sinceId) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = getTimelineParams(maxId, sinceId);
        client.get(apiUrl, params, handler);
    }

    public void getMentionsTimeline(AsyncHttpResponseHandler handler, Long maxId, Long sinceId) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = getTimelineParams(maxId, sinceId);
        client.get(apiUrl, params, handler);
    }

    public void getUserTimeline(AsyncHttpResponseHandler handler, Long maxId, Long sinceId, Long userId) {
        String url = getApiUrl("statuses/user_timeline.json");
        RequestParams params = getTimelineParams(maxId, sinceId);
        if (userId != null) {
            params.put("user_id", Long.toString(userId));
        }
        client.get(url, params, handler);
    }

    private RequestParams getTimelineParams(Long maxId, Long sinceId) {
        RequestParams params = new RequestParams();
        params.put("since_id", "1");
        if (maxId != null) {
            params.put("max_id", Long.toString(maxId));
        }
        if (sinceId != null) {
            params.put("since_id", Long.toString(sinceId));
        }
        return params;
    }

    public void getCurrentUser(AsyncHttpResponseHandler handler) {
        String currentUserInfoUrl = getApiUrl("account/verify_credentials.json");
        client.get(currentUserInfoUrl, null, handler);
    }

    public void getUserById(AsyncHttpResponseHandler handler, long id) {
        String url = getApiUrl("users/show.json");
        RequestParams params = new RequestParams("user_id", Long.toString(id));
        client.get(url, params, handler);
    }

    public void submitTweet(AsyncHttpResponseHandler handler, String tweetBody, String replyToId) {
        String submitTweetUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams("status", tweetBody);
        if (replyToId != null) {
            params.put("in_reply_to_status_id", replyToId);
        }
        client.post(submitTweetUrl, params, handler);
    }

    public void searchForTweets(AsyncHttpResponseHandler handler, Long maxId, Long sinceId, String searchQuery) {
        String url = getApiUrl("search/tweets.json");
        RequestParams params = getTimelineParams(maxId, sinceId);
        params.put("q", searchQuery);
        client.get(url, params, handler);
    }
}