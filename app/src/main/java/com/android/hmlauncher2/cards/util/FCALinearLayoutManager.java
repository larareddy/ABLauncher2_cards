package com.android.hmlauncher2.cards.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;


/**
 * Created by bmohanty2 on 2/28/2016.
 */
public class FCALinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;
    private static final String TAG = FCALinearLayoutManager.class.getCanonicalName();



    public FCALinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        //return isScrollEnabled && super.canScrollVertically();
        return isScrollEnabled;
    }


}
