package com.android.hmlauncher2.cards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.android.hmlauncher2.R;

import java.util.ArrayList;

/**
 * Created by AnTang on 1/24/2017.
 */

public class CardsProvider {
    private static final String TAG = "CardsProvider";
    private static CardsProvider sInstance;

    public static final int TYPE_INVALID = -1;
    public static final int TYPE_MEAL = 1;
    public static final int TYPE_SNACK = 2;
    public static final int TYPE_BITE = 3;

    private static class CardInfo {
        public int mThumbnailResId;
        public int mLabelResId;
        public int mLayoutResId;

        public CardInfo(int thumbnailResId, int nameResId, int layoutResId) {
            mThumbnailResId = thumbnailResId;
            mLabelResId = nameResId;
            mLayoutResId = layoutResId;
        }
    }

    public static class DecodedCardInfo {
        public Drawable mThumbnail;
        public String mLabel;
        public int mLayoutResId;

        public DecodedCardInfo(Drawable thumbnail, String name, int layoutResId) {
            mThumbnail = thumbnail;
            mLabel = name;
            mLayoutResId = layoutResId;
        }
    }

    private static ArrayList<CardInfo> sCards = new ArrayList<CardInfo>() {{
        add(new CardInfo(R.drawable.media_enabled, R.string.media, R.layout.media_card_holder));
        add(new CardInfo(R.drawable.phone_enabled, R.string.phone, R.layout.phone_card_holder));
        add(new CardInfo(R.drawable.climate_enabled, R.string.climate, R.layout.climate_card_holder));
        add(new CardInfo(R.drawable.navigation_enabled, R.string.nav, R.layout.navigation_card_holder));
//        add(new CardInfo(R.drawable.phone_enabled, R.string.phone, R.layout.activecall_card_holder));
    }};

    public static class LauncherLayoutInfo {
        public int mThumbnailResId;
        public String mLayoutName;
        public int mType;
        public LauncherLayoutInfo(int type, int thumbnailResId, String layoutName) {
            mType = type;
            mThumbnailResId = thumbnailResId;
            mLayoutName = layoutName;
        }
    }

    public static final int DEF_LAYOUT_1_MEAL_1_SNACK_2_BITES = 0;
    public static final int DEF_LAYOUT_2_MEALS = 1;
    public static final int DEF_LAYOUT_4_SNACKS = 2;

    public static final ArrayList<LauncherLayoutInfo> sLayouts = new ArrayList<LauncherLayoutInfo>(){{
       //add(new LauncherLayoutInfo(DEF_LAYOUT_1_MEAL_1_SNACK_2_BITES, R.drawable.porch_layout_0, "default_workspace"));
        add(new LauncherLayoutInfo(DEF_LAYOUT_2_MEALS, R.drawable.porch_layout_1, "default_workspace_2_meals"));
       // add(new LauncherLayoutInfo(DEF_LAYOUT_4_SNACKS, R.drawable.porch_layout_2, "default_workspace_4_snacks"));
    }};

    /*private static ArrayList<CardInfo> sMealCards = new ArrayList<CardInfo>() {{
        add(new CardInfo(R.drawable.media_enabled, R.string.now_playing, R.layout.now_playing_meal));
        add(new CardInfo(R.drawable.climate_enabled, R.string.comfort, R.layout.climate_meal_1));
        add(new CardInfo(R.drawable.climate_enabled, R.string.climate, R.layout.climate_meal_1));
        add(new CardInfo(R.drawable.climate_disabled, R.string.comfort, R.layout.climate_meal_1));
    }};

    private static ArrayList<CardInfo> sSnackCards = new ArrayList<CardInfo>() {{
        add(new CardInfo(R.drawable.navigation_enabled, R.string.where_to, R.layout.navigation_snack_1));
        add(new CardInfo(R.drawable.navigation_enabled, R.string.where_to, R.layout.navigation_snack_1));
        add(new CardInfo(R.drawable.navigation_disabled, R.string.current_location, R.layout.navigation_snack_1));
        add(new CardInfo(R.drawable.navigation_disabled, R.string.current_location, R.layout.navigation_snack_1));
    }};

    private static ArrayList<CardInfo> sBiteCards = new ArrayList<CardInfo>() {{
        add(new CardInfo(R.drawable.phone_enabled, R.string.phone, R.layout.phone_bite_1));
        add(new CardInfo(R.drawable.phone_enabled, R.string.phone, R.layout.phone_bite_1));
        add(new CardInfo(R.drawable.phone_enabled, R.string.phone, R.layout.phone_bite_1));
    }};*/

    private volatile ArrayList<DecodedCardInfo> mDecodedCards;
    /*private volatile ArrayList<DecodedCardInfo> mDecodedMealCards;
    private volatile ArrayList<DecodedCardInfo> mDecodedSnackCards;
    private volatile ArrayList<DecodedCardInfo> mDecodedBiteCards;*/

    public interface DecodeCardInfoListener {
        public void onDecodeDone(ArrayList<DecodedCardInfo> list);
    }

    public static CardsProvider getsInstance() {
        if (sInstance == null) {
            sInstance = new CardsProvider();
        }

        return sInstance;
    }

    public void loadCards(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*mDecodedMealCards = loadCardInfo(context, TYPE_MEAL);
                mDecodedSnackCards = loadCardInfo(context, TYPE_SNACK);
                mDecodedBiteCards = loadCardInfo(context, TYPE_BITE);*/
                mDecodedCards = loadCardInfo(context);
            }
        }).start();
    }

    public ArrayList<DecodedCardInfo> getCards(int type){
        return mDecodedCards;
    }

    /*public static void decodeCardInfo(final Context context, final int type, final DecodeCardInfoListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onDecodeDone(loadCardInfo(context, type));
                }
            }
        }).start();
    }*/

    private static ArrayList<DecodedCardInfo> loadCardInfo(final Context context) {
        final ArrayList<CardInfo> srcList = sCards;
        /*if (type == TYPE_BITE) {
            srcList = sBiteCards;
        } else if (type == TYPE_MEAL) {
            srcList = sMealCards;
        } else if (type == TYPE_SNACK) {
            srcList = sSnackCards;
        } else {
            Log.e(TAG, "decodeCardInfo: unknown type");
            return null;
        }*/


        ArrayList<DecodedCardInfo> result = new ArrayList<DecodedCardInfo>(srcList.size());
        for (CardInfo cardInfo : srcList) {
            String name = context.getString(cardInfo.mLabelResId);
            Drawable thumbnail = context.getDrawable(cardInfo.mThumbnailResId);
            DecodedCardInfo decodedCardInfo = new DecodedCardInfo(thumbnail, name, cardInfo.mLayoutResId);
            result.add(decodedCardInfo);
        }
        return result;
    }
}