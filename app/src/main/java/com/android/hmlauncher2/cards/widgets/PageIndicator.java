package com.android.hmlauncher2.cards.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.hmlauncher2.R;

public class PageIndicator extends LinearLayout {
    private int mNumOfPage;
    private int mCurrentPage;

    public PageIndicator(Context context, AttributeSet attr){
        super(context, attr);
        setLayoutDirection(LAYOUT_DIRECTION_LTR);
    }

    public void setNumberOfPage(int numOfPage){
        if (mNumOfPage == numOfPage){
            return;
        }
        mNumOfPage = numOfPage;
        removeAllViews();
        addIndicators(numOfPage);
    }

    private void addIndicators(int num){
        for (int i=0; i < num ; i++){
            ImageView indicator = new ImageView(getContext());
            indicator.setImageResource(R.drawable.pagination_off);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if(i != 0){
                lp.setMargins(10, 0, 0, 0);
            }
            indicator.setLayoutParams(lp);
            indicator.setScaleType(ImageView.ScaleType.CENTER);
            addView(indicator);
        }
    }

    public void setCurrentPage(int pageNum){
        if(pageNum > getChildCount() - 1){
            return;
        }
        // Set previous current page to inactive.
        if( mCurrentPage < getChildCount()){
            ((ImageView)getChildAt(mCurrentPage)).setImageResource(R.drawable.pagination_off);
        }

        // Set current page to active.
        mCurrentPage = pageNum;
        ((ImageView)getChildAt(mCurrentPage)).setImageResource(R.drawable.pagination_on);

    }

}
