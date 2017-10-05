package com.epicodus.classicalchat;

/**
 * Created by Guest on 10/3/17.
 */

public class Constants {
    public static final String MEETUP_API_KEY_PARAMETER = "key";
    public static final String MEETUP_API_KEY = BuildConfig.MEETUP_API_KEY;
    public static final String MEETUP_TOPIC_QUERY_PARAMETER = "topic_id";
    public static final String MEETUP_CLASSICAL_MUSIC_TOPIC_ID = "133";
    public static final String MEETUP_BASE_URL = "https://api.meetup.com/find/groups?photo-host=public";
    public static final String MEETUP_LOCATION_QUERY_PARAMETER = "location";
    public static final String MEETUP_RADIUS_QUERY_PARAMETER = "radius";
    public static final String MEETUP_RADIUS_VALUE = "100";

    public static final String PREFERENCE_LOCATION_KEY = "location";

    public static final String FIREBASE_CHILD_MEETUPS = "meetups";
    public static final String FIREBASE_QUERY_INDEX = "index";

    public static final String EXTRA_KEY_POSITION = "position";
    public static final String EXTRA_KEY_MEETUPS = "restaurants";

    public static final String KEY_SOURCE = "source";
    public static final String SOURCE_SAVED = "saved";
    public static final String SOURCE_FIND = "find";

}
