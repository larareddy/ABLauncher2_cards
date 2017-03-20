package com.android.hmlauncher2.cards.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.BaseCardHolder;
import com.android.hmlauncher2.cards.CardsProvider;

/**
 * Created by soma on 2/13/2017.
 */

public class NavigationCardHolder extends BaseCardHolder {
    private static final String TAG = NavigationCardHolder.class.getCanonicalName();

    public NavigationCardHolder(Context context) {
        super(context);
    }

    public NavigationCardHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationCardHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updateLayout(final int cardType) {
        switch (cardType) {
            case CardsProvider.TYPE_MEAL:
                setCardView(R.layout.navigation_meal_views);
                break;
            case CardsProvider.TYPE_SNACK:
                setCardView(R.layout.navigation_snack_views);
                break;
            case CardsProvider.TYPE_BITE:
                setCardView(R.layout.navigation_bite_views);
                break;
            default:
                setCardView(R.layout.navigation_meal_views);
                break;
        }
    }
}
