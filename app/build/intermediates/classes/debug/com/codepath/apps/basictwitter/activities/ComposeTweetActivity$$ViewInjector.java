// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.basictwitter.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ComposeTweetActivity$$ViewInjector {
  public static void inject(Finder finder, final com.codepath.apps.basictwitter.activities.ComposeTweetActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230723, "field 'etTweetBody'");
    target.etTweetBody = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131230721, "field 'tvCharacterCount'");
    target.tvCharacterCount = (android.widget.TextView) view;
  }

  public static void reset(com.codepath.apps.basictwitter.activities.ComposeTweetActivity target) {
    target.etTweetBody = null;
    target.tvCharacterCount = null;
  }
}
