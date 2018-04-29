package com.victorsquarecity.app.utils;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.victorsquarecity.app.R;

import java.io.Serializable;
import java.net.Socket;
import java.util.List;

/**
 * Created by mmasod on 2/19/15.
 */
public class LocationModel implements Serializable {
    private String locationName;
    private String locationDescription;
    private String locationAddress;
    private LatLng locationCoordinates;
    private String locationPhotoReference;
    private Bitmap locationImage;
    private String icon;
    private String priceRange;
    private String openingHours;
    private String locationRatings;
    private List<String> placePhotos;
    String id;
    String reference;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public LatLng getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(LatLng locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

    public String getLocationPhotoReference() {
        return locationPhotoReference;
    }

    public void setLocationPhotoReference(String locationPhotoReference) {
        this.locationPhotoReference = locationPhotoReference;
    }

    public Bitmap getLocationImage() {
        return locationImage;
    }

    public void setLocationImage(Bitmap locationImage) {
        this.locationImage = locationImage;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getLocationRatings() {
        return locationRatings;
    }

    public void setLocationRatings(String locationRatings) {
        this.locationRatings = locationRatings;
    }

    public List<String> getPlacePhotos() {
        return placePhotos;
    }

    public void setPlacePhotos(List<String> placePhotos) {
        this.placePhotos = placePhotos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "LocationModel{" +
                "locationName='" + locationName + '\'' +
                ", locationDescription='" + locationDescription + '\'' +
                ", locationAddress='" + locationAddress + '\'' +
                ", locationCoordinates=" + locationCoordinates +
                ", locationPhotoReference='" + locationPhotoReference + '\'' +
                ", locationImage=" + locationImage +
                ", icon='" + icon + '\'' +
                ", priceRange='" + priceRange + '\'' +
                ", openingHours='" + openingHours + '\'' +
                ", locationRatings='" + locationRatings + '\'' +
                ", placePhotos=" + placePhotos +
                ", id='" + id + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}
