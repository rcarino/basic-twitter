// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.basictwitter.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TimelineActivity$$ViewInjector {
  public static void inject(Finder finder, final com.codepath.apps.basictwitter.activities.TimelineActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230725, "field 'lvTweets'");
    target.lvTweets = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131230724, "field 'tweetsSwipeContainer'");
    target.tweetsSwipeContainer = (android.support.v4.widget.SwipeRefreshLayout) view;
  }

  public static void reset(com.codepath.apps.basictwitter.activities.TimelineActivity target) {
    target.lvTweets = null;
    target.tweetsSwipeContainer = null;
  }
}
