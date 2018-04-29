package com.victorsquarecity.app.utils.db.dao;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

/**
 * Created by mmasood on 3/6/2017.
 */

@Entity
public class UserORM {

    @Id(autoincrement = true)
    private long _id;

    @NotNull
    private String mUserName;

    @NotNull
    private String mEmail;
    @NotNull
    private String mPassword;
    private String mImageUrl;
    @NotNull
    private String mState;
    @NotNull
    private String mCity;
    private String mAddress;
    @NotNull
    private String mMobileNumber;
    @NotNull
    private String mAge;

    private String mProfileID;
    
    @Convert(converter = GreenConverter.class, columnType = String.class)
    private List<String> images;

    @Convert(converter = GreenConverter.class, columnType = String.class)
    private List<String> comments;

    @Convert(converter = GreenConverter.class, columnType = String.class)
    private List<String> checkIns;

    @Convert(converter = GreenConverter.class, columnType = String.class)
    private List<String> friends;

    public List<String> getFriends() {
        return this.friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getCheckIns() {
        return this.checkIns;
    }

    public void setCheckIns(List<String> checkIns) {
        this.checkIns = checkIns;
    }

    public List<String> getComments() {
        return this.comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getMProfileID() {
        return this.mProfileID;
    }

    public void setMProfileID(String mProfileID) {
        this.mProfileID = mProfileID;
    }

    public String getMAge() {
        return this.mAge;
    }

    public void setMAge(String mAge) {
        this.mAge = mAge;
    }

    public String getMMobileNumber() {
        return this.mMobileNumber;
    }

    public void setMMobileNumber(String mMobileNumber) {
        this.mMobileNumber = mMobileNumber;
    }

    public String getMAddress() {
        return this.mAddress;
    }

    public void setMAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getMCity() {
        return this.mCity;
    }

    public void setMCity(String mCity) {
        this.mCity = mCity;
    }

    public String getMState() {
        return this.mState;
    }

    public void setMState(String mState) {
        this.mState = mState;
    }

    public String getMImageUrl() {
        return this.mImageUrl;
    }

    public void setMImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getMPassword() {
        return this.mPassword;
    }

    public void setMPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getMEmail() {
        return this.mEmail;
    }

    public void setMEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getMUserName() {
        return this.mUserName;
    }

    public void setMUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public long get_id() {
        return this._id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    @Generated(hash = 1428604218)
    public UserORM(long _id, @NotNull String mUserName, @NotNull String mEmail,
            @NotNull String mPassword, String mImageUrl, @NotNull String mState,
            @NotNull String mCity, String mAddress, @NotNull String mMobileNumber,
            @NotNull String mAge, String mProfileID, List<String> images,
            List<String> comments, List<String> checkIns, List<String> friends) {
        this._id = _id;
        this.mUserName = mUserName;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mImageUrl = mImageUrl;
        this.mState = mState;
        this.mCity = mCity;
        this.mAddress = mAddress;
        this.mMobileNumber = mMobileNumber;
        this.mAge = mAge;
        this.mProfileID = mProfileID;
        this.images = images;
        this.comments = comments;
        this.checkIns = checkIns;
        this.friends = friends;
    }

    @Generated(hash = 664256343)
    public UserORM() {
    }

    @Override
    public String toString() {
        return "UserORM{" +
                "_id=" + _id +
                ", mUserName='" + mUserName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mState='" + mState + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mMobileNumber='" + mMobileNumber + '\'' +
                ", mAge='" + mAge + '\'' +
                ", mProfileID='" + mProfileID + '\'' +
                ", images=" + images +
                ", comments=" + comments +
                ", checkIns=" + checkIns +
                ", friends=" + friends +
                '}';
    }
}
