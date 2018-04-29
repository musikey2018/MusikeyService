package com.victorsquarecity.app.utils;

/**
 * Created by bilaw on 6/7/2017.
 */

class ResponseModelJSON {
    public static String RESPONSE = "response";
    public static String RESPCODE = "res";
    public static String SESSION_ID = "sessionid";
    public static String AUTH_TOKEN = "token";
}

class ResponseModel {
    public  String response;
    public  String respcode;
    public  String sessionid;
    public  String token;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

class UserModelJSON {

    public static String USERNAME = "username";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";
    public static String PROFILE_ID ="_id";
    public static String IMAGE_URL = "ImageUrl";
    public static String STATE = "state";
    public static String CITY = "city";
    public static String AGE = "age";
    public static String ADDRESS = "address";
    public static String MOBILE_NUMBER = "number";
};

public class UserModel {

    private String mUserName;
    private String mEmail;
    private String mPassword;
    private String mProfileID;
    private String mImageUrl;
    private String mState;
    private String mCity;
    private String mAddress;
    private String mMobileNumber;
    private String mAge;

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmProfileID() {
        return mProfileID;
    }

    public void setmProfileID(String mProfileID) {
        this.mProfileID = mProfileID;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmMobileNumber() {
        return mMobileNumber;
    }

    public void setmMobileNumber(String mMobileNumber) {
        this.mMobileNumber = mMobileNumber;
    }

    public String getmAge() {
        return mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "mUserName='" + mUserName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mProfileID='" + mProfileID + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mState='" + mState + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mAge='" + mAge + '\'' +
                ", mMobileNumber='" + mMobileNumber + '\'' +
                '}';
    }
};

