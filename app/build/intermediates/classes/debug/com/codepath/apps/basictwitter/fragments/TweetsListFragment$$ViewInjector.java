// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.basictwitter.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TweetsListFragment$$ViewInjector {
  public static void inject(Finder finder, final com.codepath.apps.basictwitter.fragments.TweetsListFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230741, "field 'lvTweets'");
    target.lvTweets = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131230740, "field 'tweetsSwipeContainer'");
    target.tweetsSwipeContainer = (android.support.v4.widget.SwipeRefreshLayout) view;
    view = finder.findRequiredView(source, 2131230742, "field 'pbLoading'");
    target.pbLoading = (android.widget.ProgressBar) view;
  }

  public static void reset(com.codepath.apps.basictwitter.fragments.TweetsListFragment target) {
    target.lvTweets = null;
    target.tweetsSwipeContainer = null;
    target.pbLoading = null;
  }
}
