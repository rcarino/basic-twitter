package com.codepath.apps.basictwitter.utilities;

import android.view.View;
import android.widget.ImageView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by rcarino on 10/6/14.
 */
public class AttachmentRenderer {
    public static void renderAttachment(Tweet tweet, ImageView imageView) {
        String attachmentUrl = tweet.getAttachmentUrl();
        if (attachmentUrl != null) {
            imageView.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(attachmentUrl, imageView);
        }
    }
}
