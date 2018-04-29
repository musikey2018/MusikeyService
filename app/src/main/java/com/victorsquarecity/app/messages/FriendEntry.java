package com.victorsquarecity.app.messages;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by app on 6/8/2017.
 */
public class FriendEntry implements Parcelable {

    private String friend_id;
    private  String friend_name;
    private  String friend_email;
    private  String friend_avatar_url;
    private int isFriend =0;
    private String lng;
    private  String lat;
    private  String frined_new_msg_count;
    private  String friend_msg_enc_key;

    public FriendEntry(){
        super();
    }


    public FriendEntry(Parcel in) {
        this.friend_id = in.readString();
        this.friend_name = in.readString();
        this.friend_email = in.readString();
        this.friend_avatar_url = in.readString();
        this.isFriend = in.readInt();
        this.lng = in.readString();
        this.lat = in.readString();
        this.frined_new_msg_count = in.readString();
        this.friend_msg_enc_key = in.readString();

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

    public String getfriend_msg_enc_key() {
        return friend_msg_enc_key;
    }

    public void setfriend_msg_enc_key(String friend_msg_enc_key) {
        this.friend_msg_enc_key = friend_msg_enc_key;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getFrined_new_msg_count() {
        return frined_new_msg_count;
    }

    public void setFrined_new_msg_count(String frined_new_msg_count) {
        this.frined_new_msg_count = frined_new_msg_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getfriend_id());
        dest.writeString(getFriend_name());
        dest.writeString(getFriend_email());
        dest.writeString(getfriend_avatar_url());
        dest.writeInt(getIsFriend());
        dest.writeString(getLng());
        dest.writeString(getLat());
        dest.writeString(getFrined_new_msg_count());
        dest.writeString(getfriend_msg_enc_key());


    }

    @Override
    public String toString() {
        return "FriendEntry [friend_id=" + friend_id + ", " +
                "friend_name=" + friend_name +
                ", friend_email=" + friend_email +
                ", friend_avatar_url=" + friend_avatar_url +
                ", isFriend=" + isFriend +
                ", lng=" + lng +
                ", lat=" + lat +
                ", frined_new_msg_count=" + frined_new_msg_count +
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
        FriendEntry other = (FriendEntry) obj;
        if (friend_id != other.friend_id)
            return false;
        return true;
    }

    public static final Creator<FriendEntry> CREATOR = new Creator<FriendEntry>() {
        public FriendEntry createFromParcel(Parcel in) {
            return new FriendEntry(in);
        }

        public FriendEntry[] newArray(int size) {
            return new FriendEntry[size];
        }
    };
}