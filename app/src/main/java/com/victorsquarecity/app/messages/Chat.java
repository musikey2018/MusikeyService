package com.victorsquarecity.app.messages;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by app on 6/8/2017.
 */
public class Chat implements Parcelable {

    private String _id;
    private  String friend_name;
    private String friend_id;
    private  String friend_email;
    private  String friend_avatar_url;
    private String avatar_url;
    private  String friend_msg_enc_key;

    private FriendEntry friendEntry;


    public Chat(){
        super();
    }


    public Chat(Parcel in) {
        this._id = in.readString();
        this.friend_name = in.readString();
        this.friend_id = in.readString();
        this.friend_email = in.readString();
        this.friend_avatar_url = in.readString();
        this.avatar_url = in.readString();
        this.friend_msg_enc_key = in.readString();
        this.friendEntry = in.readParcelable(FriendEntry.class.getClassLoader());
    }

    public String getid() {
        return _id;
    }

    public void setid(String _id) {
        _id = _id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getfriend_id() {
        return friend_id;
    }

    public void setfriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriend_email() {
        return friend_email;
    }

    public void setFriend_email(String friend_email) {
        this.friend_email = friend_email;
    }

    public String getfriend_avatar_url() {
        return friend_avatar_url;
    }

    public void setfriend_avatar_url(String friend_avatar_url) {
        this.friend_avatar_url = friend_avatar_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getfriend_msg_enc_key() {
        return friend_msg_enc_key;
    }

    public void setfriend_msg_enc_key(String friend_msg_enc_key) {
        this.friend_msg_enc_key = friend_msg_enc_key;
    }

    public FriendEntry getFriendEntry() {
        return friendEntry;
    }

    public void setFriendEntry(FriendEntry friendEntry) {
        this.friendEntry = friendEntry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getid());
        dest.writeString(getFriend_name());
        dest.writeString(getfriend_id());
        dest.writeString(getFriend_email());
        dest.writeString(getfriend_avatar_url());
        dest.writeString(getAvatar_url());
        dest.writeString(getfriend_msg_enc_key());

        dest.writeParcelable(getFriendEntry(),flags);

    }

    @Override
    public String toString() {
        return "Chat [id=" + _id + ", " +
                "friend_name=" + friend_name +
                ", friend_id=" + friend_id +
                ", friend_email=" + friend_email +
                ", friend_avatar_url=" + friend_avatar_url +
                ", avatar_url=" + avatar_url +
                ", friend_msg_enc_key=" + friend_msg_enc_key +"]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chat other = (Chat) obj;
        if (_id != other._id)
            return false;
        return true;
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };
}