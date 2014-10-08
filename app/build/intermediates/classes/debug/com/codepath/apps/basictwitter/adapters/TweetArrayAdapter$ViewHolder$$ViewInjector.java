// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.basictwitter.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TweetArrayAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.codepath.apps.basictwitter.adapters.TweetArrayAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230726, "field 'profileImage'");
    target.profileImage = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131230735, "field 'fullName'");
    target.fullName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230736, "field 'userName'");
    target.userName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230743, "field 'timestamp'");
    target.timestamp = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230737, "field 'body'");
    target.body = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230744, "field 'reply'");
    target.reply = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131230748, "field 'favoriteCount'");
    target.favoriteCount = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230746, "field 'retweetsCount'");
    target.retweetsCount = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230738, "field 'attachment'");
    target.attachment = (android.widget.ImageView) view;
  }

  public static void reset(com.codepath.apps.basictwitter.adapters.TweetArrayAdapter.ViewHolder target) {
    target.profileImage = null;
    target.fullName = null;
    target.userName = null;
    target.timestamp = null;
    target.body = null;
    target.reply = null;
    target.favoriteCount = null;
    target.retweetsCount = null;
    target.attachment = null;
  }
}
