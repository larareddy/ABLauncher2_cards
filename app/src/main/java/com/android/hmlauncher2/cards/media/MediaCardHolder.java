package com.android.hmlauncher2.cards.media;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.BaseCardHolder;
import com.android.hmlauncher2.cards.CardsProvider;
import com.android.hmlauncher2.cards.util.UiUtils;

/**
 * Created by soma on 2/13/2017.
 */

public class MediaCardHolder extends BaseCardHolder {
    private static final String TAG = MediaCardHolder.class.getCanonicalName();
    private static final boolean DEBUG = true;

    public MediaCardHolder(Context context) {
        super(context);
    }

    public MediaCardHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaCardHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void updateLayout(final int cardType) {
        switch (cardType) {
            case CardsProvider.TYPE_MEAL:
                setCardView(R.layout.media_meal_views);
                break;
            case CardsProvider.TYPE_SNACK:
                setCardView(R.layout.media_snack_views);
                break;
            case CardsProvider.TYPE_BITE:
                setCardView(R.layout.media_bite_views);
                break;
            default:
                setCardView(R.layout.media_meal_views);
                break;
        }
    }
}
