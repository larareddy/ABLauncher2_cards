package com.android.hmlauncher2.cards.media;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.widgets.HomeScreenViewPager;
import com.android.hmlauncher2.cards.widgets.PageIndicator;

/**
 * Created by AnTang on 1/24/2017.
 */

public class NowPlayingView extends LinearLayout {
    private static final String TAG = "NowPlayingView";
    private PageIndicator mPageIndicator;

    public NowPlayingView(Context context) {
        this(context, null);
    }

    public NowPlayingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPageIndicator = (PageIndicator) findViewById(R.id.page_indicator);
        TextView tv = (TextView) findViewById(R.id.card_title);
        tv.setText("Now Playing FM");
        HomeScreenViewPager pager = (HomeScreenViewPager)findViewById(R.id.pager);
        mPageIndicator.setNumberOfPage(pager.getChildCount());
        mPageIndicator.setCurrentPage(pager.getCurrentItem());
        pager.setOnPageChangeListener(new HomeScreenViewPager.OnPageChangeListener() {
            @Override
            public void onPageChanged(int currentPage) {
                mPageIndicator.setCurrentPage(currentPage);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}