package com.android.hmlauncher2.cards;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.hmlauncher2.cards.util.UiUtils;

/**
 * Created by antang on 2/17/2017.
 */

public abstract class BaseCardHolder extends FrameLayout {
    static final String TAG = BaseCardHolder.class.getCanonicalName();
    static final boolean DEBUG = true;
    private int mCardType = CardsProvider.TYPE_INVALID;

    public BaseCardHolder(Context context) {
        this(context, null);
    }

    public BaseCardHolder(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCardHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BaseCardHolder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void setCardView(int layoutId) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        View v = View.inflate(getContext(), layoutId, null);
        v.setLayoutParams(params);
        removeAllViews();
        addView(v, params);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (DEBUG) {
            Log.d(TAG, "onSizeChanged w=" + w + ", h=" + h + ", oldw=" + oldw + ", oldh=" + oldh);
        }
        int cardType = UiUtils.checkCardTypeWithLayoutSize(w, h);
        if (cardType != mCardType) {
            mCardType = cardType;
            post(new Runnable() {
                @Override
                public void run() {
                    updateLayout(mCardType);
                }
            });
        }
    }

    protected abstract void updateLayout(final int cardType);
}
