package com.android.hmlauncher2.cards.climate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.widgets.HomeScreenViewPager;
import com.android.hmlauncher2.cards.widgets.PageIndicator;

public class ClimateMealViews extends LinearLayout {
    private static final String TAG = "ClimateMealViews";
    private PageIndicator mPageIndicator;

    public ClimateMealViews(Context context) {
        this(context, null);
    }

    public ClimateMealViews(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPageIndicator = (PageIndicator) findViewById(R.id.page_indicator);
        TextView tv = (TextView) findViewById(R.id.card_title);
//        tv.setVisibility(View.GONE);
        tv.setText("Climate");
        HomeScreenViewPager pager = (HomeScreenViewPager)findViewById(R.id.pager);
        mPageIndicator.setNumberOfPage(pager.getChildCount());
        mPageIndicator.setCurrentPage(pager.getCurrentItem());
        pager.setOnPageChangeListener(new HomeScreenViewPager.OnPageChangeListener() {
            @Override
            public void onPageChanged(int currentPage) {
                mPageIndicator.setCurrentPage(currentPage);
            }
        });
        mPageIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}