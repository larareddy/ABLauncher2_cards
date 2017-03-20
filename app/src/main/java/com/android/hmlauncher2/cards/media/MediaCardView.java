package com.android.hmlauncher2.cards.media;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.CardsProvider;
import com.android.hmlauncher2.cards.util.UiUtils;

/**
 * Created by soma on 2/13/2017.
 */

public class MediaCardView extends FrameLayout {
    private static final String TAG = MediaCardView.class.getCanonicalName();
    private static final boolean DEBUG = true;
    Context mContext;
    private int mCardType = CardsProvider.TYPE_INVALID;

    public MediaCardView(Context context) {
        super(context);
        this.mContext = context;
    }

    public MediaCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public MediaCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public MediaCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    void setScreenView(int aLayoutId) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                setScreenView(R.layout.media_meal_views);
                break;
            case CardsProvider.TYPE_SNACK:
                setScreenView(R.layout.media_snack_views);
                break;
            case CardsProvider.TYPE_BITE:
                setScreenView(R.layout.media_bite_views);
                break;
            default:
                setScreenView(R.layout.media_meal_views);
                break;
        }
    }
}
