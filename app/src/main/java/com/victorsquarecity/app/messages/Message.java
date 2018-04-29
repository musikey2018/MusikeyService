package com.victorsquarecity.app.messages;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by app on 6/8/2017.
 */
public class Message implements Parcelable {

    private String id;
    private  String frined_id;
    private String friend_email;
    private  String frined_avatar_url;
    private String text;
    private String avatar_url;
    private String media_url;
    private  String media_type;
    private String media_size;
    private String media_name;
    private  String received_timestamp;
    private  String send_timestamp;
    private  String frined_msg_enc_key;

    private Chat chat;

    public Message(){
        super();
    }


    public Message(Parcel in) {
        this.id = in.readString();
        this.frined_id = in.readString();
        this.friend_email = in.readString();
        this.frined_avatar_url = in.readString();
        this.text = in.readString();
        this.avatar_url = in.readString();
        this.media_url = in.readString();
        this.media_type = in.readString();
        this.media_size = in.readString();
        this.media_name = in.readString();
        this.received_timestamp = in.readString();
        this.send_timestamp = in.readString();
        this.frined_msg_enc_key = in.readString();
        this.chat = in.readParcelable(Chat.class.getClassLoader());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrined_id() {
        return frined_id;
    }

    public void setFrined_id(String frined_id) {
        this.frined_id = frined_id;
    }

    public String getFriend_email() {
        return friend_email;
    }

    public void setFriend_email(String friend_email) {
        this.friend_email = friend_email;
    }

    public String getFrined_avatar_url() {
        return frined_avatar_url;
    }

    public void setFrined_avatar_url(String frined_avatar_url) {
        this.frined_avatar_url = frined_avatar_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getMedia_size() {
        return media_size;
    }

    public void setMedia_size(String media_size) {
        this.media_size = media_size;
    }

    public String getMedia_name() {
        return media_name;
    }

    public void setMedia_name(String media_name) {
        this.media_name = media_name;
    }

    public String getReceived_timestamp() {
        return received_timestamp;
    }

    public void setReceived_timestamp(String received_timestamp) {
        this.received_timestamp = received_timestamp;
    }

    public String getSend_timestamp() {
        return send_timestamp;
    }

    public void setSend_timestamp(String send_timestamp) {
        this.send_timestamp = send_timestamp;
    }

    public String getFrined_msg_enc_key() {
        return frined_msg_enc_key;
    }

    public void setFrined_msg_enc_key(String frined_msg_enc_key) {
        this.frined_msg_enc_key = frined_msg_enc_key;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getFrined_id());
        dest.writeString(getFriend_email());
        dest.writeString(getFrined_avatar_url());
        dest.writeString(getText());
        dest.writeString(getAvatar_url());
        dest.writeString(getMedia_url());
        dest.writeString(getMedia_type());
        dest.writeString(getMedia_size());
        dest.writeString(getMedia_name());
        dest.writeString(getReceived_timestamp());
        dest.writeString(getSend_timestamp());
        dest.writeString(getFrined_msg_enc_key());
        dest.writeParcelable(getChat(),flags);

    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", Text=" + text + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Message other = (Message) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}