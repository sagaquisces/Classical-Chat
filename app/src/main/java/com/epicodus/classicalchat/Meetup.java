package com.epicodus.classicalchat;

/**
 * Created by Guest on 10/3/17.
 */

public class Meetup {
    private double mScore; //score
    private String mName; //name
    private String mLink; //link
    private String mDescription; //description in HTML format
    private String mLocation; //localized_location, may be in suburb city
    private String mOrganizer; //organizer
    private String mImageUrl; //group_photo --> photo_link

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
        return mLink;
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
