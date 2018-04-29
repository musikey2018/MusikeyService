package com.victorsquarecity.app.utils.pojo;

import java.io.Serializable;

/**
 * Created by mujahidmasood on 29.08.17.
 */

public class LocationReview implements Serializable {

    String author_name;
    String author_url;
    String language;
    String profile_photo_url;
    String rating;
    String relative_time_description;
    String text;
    String time;


    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_url() {
        return author_url;
    }

    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfile_photo_url() {
        return profile_photo_url;
    }

    public void setProfile_photo_url(String profile_photo_url) {
        this.profile_photo_url = profile_photo_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRelative_time_description() {
        return relative_time_description;
    }

    public void setRelative_time_description(String relative_time_description) {
        this.relative_time_description = relative_time_description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LocationReview{" +
                "author_name='" + author_name + '\'' +
                ", author_url='" + author_url + '\'' +
                ", language='" + language + '\'' +
                ", profile_photo_url='" + profile_photo_url + '\'' +
                ", rating='" + rating + '\'' +
                ", relative_time_description='" + relative_time_description + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
