package com.victorsquarecity.app.messages;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by app on 6/8/2017.
 */
public class UserMeta implements Parcelable {

    private String _id;
    private  String name;
    private  String email;
    private String avatar_url;
    private String lng;
    private  String lat;

    public UserMeta(){
        super();
    }


    public UserMeta(Parcel in) {
        this._id = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.avatar_url = in.readString();
        this.lng = in.readString();
        this.lat = in.readString();

    }

    public String getid() {
        return _id;
    }

    public void setid(String _id) {
        _id = _id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }


    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getid());
        dest.writeString(getname());
        dest.writeString(getemail());
        dest.writeString(getAvatar_url());
        dest.writeString(getLng());
        dest.writeString(getLat());


    }

    @Override
    public String toString() {
        return "UserMeta [id=" + _id + ", " +
                "name=" + name +
                ", email=" + email +
                ", avatar_url=" + avatar_url +
                ", lng=" + lng +
                ", lat=" + lat +"]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserMeta other = (UserMeta) obj;
        if (_id != other._id)
            return false;
        return true;
    }

    public static final Creator<UserMeta> CREATOR = new Creator<UserMeta>() {
        public UserMeta createFromParcel(Parcel in) {
            return new UserMeta(in);
        }

        public UserMeta[] newArray(int size) {
            return new UserMeta[size];
        }
    };
}