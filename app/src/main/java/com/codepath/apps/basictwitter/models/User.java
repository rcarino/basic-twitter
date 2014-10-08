package com.codepath.apps.basictwitter.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rcarino on 9/26/14.
 */
@Table(name = "Users")
public class User extends Model implements Parcelable {
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };
    @Column(name = "name")
    private String name;
    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.ABORT)
    private long uid;
    @Column(name = "screen_name")
    private String screenName;
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    @Column(name = "is_current_user")
    private boolean isCurrentUser;
    private String description;
    private int friendsCount;
    private int followersCount;
    private String profileBannerUrl;

    public User() {
        super();
    }

    private User(Parcel in) {
        name = in.readString();
        uid = in.readLong();
        screenName = in.readString();
        profileImageUrl = in.readString();
        description = in.readString();
        friendsCount = in.readInt();
        followersCount = in.readInt();
        profileBannerUrl = in.readString();

    }

    public static User fromJSON(JSONObject json) {
        User newUser = new User();
        try {
            newUser.name = json.getString("name");
            newUser.uid = json.getLong("id");
            newUser.screenName = json.getString("screen_name");
            newUser.profileImageUrl = json.getString("profile_image_url");
            newUser.description = json.getString("description");
            newUser.friendsCount = json.getInt("friends_count");
            newUser.followersCount = json.getInt("followers_count");
            if (json.has("profile_banner_url")) {
                newUser.profileBannerUrl = json.getString("profile_banner_url");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return newUser;
    }

    public static User currentUserFromJSON(JSONObject jsonObject) {
        User currentUser = fromJSON(jsonObject);
        currentUser.isCurrentUser = true;
        currentUser.save();

        return currentUser;
    }

    public static User getCurrentUserFromLocal() {
        return new Select().from(User.class).where("is_current_user = ?", 1).executeSingle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeLong(uid);
        parcel.writeString(screenName);
        parcel.writeString(profileImageUrl);
        parcel.writeString(description);
        parcel.writeInt(friendsCount);
        parcel.writeInt(followersCount);
        parcel.writeString(profileBannerUrl);
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public String getTagline() {
        return description;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    private User preferLocalIfExists() {
        User existing = new Select().from(User.class).where("uid = ?", uid).executeSingle();
        if (existing != null) {
            return existing;
        } else {
            save();
            return this;
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (uid != user.uid) return false;
        if (!name.equals(user.name)) return false;
        if (!profileImageUrl.equals(user.profileImageUrl)) return false;
        if (!screenName.equals(user.screenName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + screenName.hashCode();
        result = 31 * result + profileImageUrl.hashCode();
        return result;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public long getUid() {
        return uid;
    }
}
