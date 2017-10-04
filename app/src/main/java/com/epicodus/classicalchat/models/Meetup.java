package com.epicodus.classicalchat.models;

/**
 * Created by Guest on 10/3/17.
 */

import org.parceler.Parcel;


@Parcel
public class Meetup {
    double mScore; //score
    String mName; //name
    String mLink; //link
    String mDescription; //description in HTML format
    String mLocation; //localized_location, may be in suburb city
    String mOrganizer; //organizer
    String mImageUrl; //group_photo --> photo_link

    public Meetup() {}

    public Meetup(double score, String name, String link, String description, String location, String organizer, String imageUrl) {
        this.mScore = score;
        this.mName = name;
        this.mLink = link;
        this.mDescription = description;
        this.mLocation = location;
        this.mOrganizer = organizer;
        this.mImageUrl = imageUrl;
    }

    public double getScore() {
        return mScore;
    }

    public String getName() {
        return mName;
    }

    public String getLink() {
        return mLink;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getOrganizer() {
        return mOrganizer;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

}
