package com.codepath.apps.basictwitter.utilities;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.codepath.apps.basictwitter.models.User;

/**
 * Created by rcarino on 10/6/14.
 */
public class ListenerAttacher {
    public static void attachReplyClickListener(final Context context, View view,
                                                final Class targetActivity) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyRequest = new Intent(context, targetActivity);
                User clickedUser = (User) view.getTag();
                replyRequest.putExtra("reply_to_id", Long.toString(clickedUser.getUid()));
                replyRequest.putExtra("reply_to_screen_name", clickedUser.getScreenName());
                context.startActivity(replyRequest);
            }
        });
    }
}
