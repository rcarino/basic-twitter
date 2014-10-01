// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.basictwitter.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TweetDetailActivity$$ViewInjector {
  public static void inject(Finder finder, final com.codepath.apps.basictwitter.activities.TweetDetailActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230726, "field 'ivProfileImage'");
    target.ivProfileImage = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131230727, "field 'tvFullName'");
    target.tvFullName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230728, "field 'tvUserName'");
    target.tvUserName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230729, "field 'tvBody'");
    target.tvBody = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230730, "field 'ivAttachment'");
    target.ivAttachment = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131230731, "field 'etReply'");
    target.etReply = (android.widget.EditText) view;
  }

  public static void reset(com.codepath.apps.basictwitter.activities.TweetDetailActivity target) {
    target.ivProfileImage = null;
    target.tvFullName = null;
    target.tvUserName = null;
    target.tvBody = null;
    target.ivAttachment = null;
    target.etReply = null;
  }
}
