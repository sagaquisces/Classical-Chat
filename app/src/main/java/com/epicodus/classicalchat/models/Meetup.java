package com.epicodus.classicalchat.models;

/**
 * Created by Guest on 10/3/17.
 */

import org.parceler.Parcel;


@Parcel
public class Meetup {
    double score; //score
    String name; //name
    String link; //link
    String description; //description in HTML format
    String location; //localized_location, may be in suburb city
    String organizer; //organizer
    String imageUrl; //group_photo --> photo_link

    public Meetup() {}

    public Meetup(double score, String name, String link, String description, String location, String organizer, String imageUrl) {
        this.score = score;
        this.name = name;
        this.link = link;
        this.description = description;
        this.location = location;
        this.organizer = organizer;
        this.imageUrl = imageUrl;
    }

    public double getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
