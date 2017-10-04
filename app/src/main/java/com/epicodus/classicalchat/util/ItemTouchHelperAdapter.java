package com.epicodus.classicalchat.util;

/**
 * Created by Guest on 10/4/17.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove (int fromPosition, int toPosition);
    void onItemDismiss (int position);
}
