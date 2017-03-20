package com.android.hmlauncher2.cards.climate;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.CardsProvider;
import com.android.hmlauncher2.cards.util.HomeScreenConstants;
import com.android.hmlauncher2.cards.util.UiUtils;

/**
 * Created by soma on 2/13/2017.
 */

public class ClimateCardView extends FrameLayout {
    private static final String TAG = ClimateCardView.class.getCanonicalName();
    private static final boolean DEBUG = true;
    Context mContext;
    private int mCardType = CardsProvider.TYPE_INVALID;

    public ClimateCardView(Context context) {
        super(context);
        this.mContext = context;
    }

    public ClimateCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public ClimateCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public ClimateCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    void setScreenView(int aLayoutId) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        View v = View.inflate(getContext(), aLayoutId, null);
        v.setLayoutParams(params);
        removeAllViews();
        addView(v, params);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //TO-DO: unregisterPhoneListener();
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

    public void updateLayout(final int cardType) {
        switch (cardType) {
            case CardsProvider.TYPE_MEAL:
                setScreenView(R.layout.climate_meal_views);
                break;
            case CardsProvider.TYPE_SNACK:
                setScreenView(R.layout.climate_snack_views);
                break;
            case CardsProvider.TYPE_BITE:
                setScreenView(R.layout.climate_bite_views);
                break;
            default:
                setScreenView(R.layout.climate_meal_views);
                break;
        }
    }
}
