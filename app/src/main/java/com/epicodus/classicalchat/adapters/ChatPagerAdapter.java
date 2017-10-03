package com.epicodus.classicalchat.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.classicalchat.ui.ChatsFragment;
import com.epicodus.classicalchat.ui.FriendsFragment;
import com.epicodus.classicalchat.ui.RequestsFragment;

/**
 * Created by Guest on 10/3/17.
 */

public class ChatPagerAdapter extends FragmentPagerAdapter {
    public ChatPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                RequestsFragment requestsFragment = new RequestsFragment();
                return requestsFragment;

            case 1:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;

            case 2:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "REQUESTS";

            case 1:
                return "CHATS";

            case 2:
                return "FRIENDS";

            default:
                return null;
        }
    }
}
