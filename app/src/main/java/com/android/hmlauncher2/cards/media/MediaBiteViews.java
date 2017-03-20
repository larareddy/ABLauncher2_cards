package com.android.hmlauncher2.cards.media;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.widgets.HomeScreenViewPager;
import com.android.hmlauncher2.cards.widgets.PageIndicator;

public class MediaBiteViews extends LinearLayout {
    private static final String TAG = "MediaBiteViews";
    private PageIndicator mPageIndicator;

    public MediaBiteViews(Context context) {
        this(context, null);
    }

    public MediaBiteViews(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPageIndicator = (PageIndicator) findViewById(R.id.page_indicator);
//        TextView tv = (TextView) findViewById(R.id.card_title);
//        tv.setVisibility(View.GONE);
        HomeScreenViewPager pager = (HomeScreenViewPager)findViewById(R.id.pager);
        mPageIndicator.setNumberOfPage(pager.getChildCount());
        mPageIndicator.setCurrentPage(pager.getCurrentItem());
        pager.setOnPageChangeListener(new HomeScreenViewPager.OnPageChangeListener() {
            @Override
            public void onPageChanged(int currentPage) {
                mPageIndicator.setCurrentPage(currentPage);
            }
        });
        mPageIndicator.setVisibility(View.GONE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}