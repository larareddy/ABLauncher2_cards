/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.hmlauncher2;

import android.appwidget.AppWidgetHostView;
import android.content.ContentValues;
import android.content.Context;

import com.android.hmlauncher2.cards.CardsProvider;

/**
 * Represents a widget (either instantiated or about to be) in the Launcher.
 */
class LauncherAppCardInfo extends ItemInfo {

    /**
     * Indicates that the widget hasn't been instantiated yet.
     */
    static final int NO_ID = -1;

    /**
     * Identifier for this widget when talking with
     * {@link android.appwidget.AppWidgetManager} for updates.
     */
    int appCardId = NO_ID; //layoutResId

    String providerName;

    // TODO: Are these necessary here?
    int minWidth = -1;
    int minHeight = -1;

    private boolean mHasNotifiedInitialWidgetSizeChanged;

    /**
     * View that holds this widget after it's been created.  This view isn't created
     * until Launcher knows it's needed.
     */
    AppWidgetHostView hostView = null;

    boolean mIsDummy;
    int mCardType; // CardProvider.TYPE_MEAL, TYPE_SNACK, TYPE_BITE

    /*
    *
    * @param appCardId the layoutResId
    * @param providerName
    * */
    LauncherAppCardInfo(int appCardId, String providerName) {
        itemType = LauncherSettings.Favorites.ITEM_TYPE_CARD;
        this.appCardId = appCardId;
        this.providerName = providerName;

        // Since the widget isn't instantiated yet, we don't know these values. Set them to -1
        // to indicate that they should be calculated based on the layout and minWidth/minHeight
        spanX = -1;
        spanY = -1;
    }

    @Override
    void onAddToDatabase(Context context, ContentValues values) {
        super.onAddToDatabase(context, values);
        values.put(LauncherSettings.Favorites.APPWIDGET_ID, appCardId);  //we re-use APPWIDGET_ID field in database to store layoutId
    }

    /**
     * When we bind the widget, we should notify the widget that the size has changed if we have not
     * done so already (only really for default workspace widgets).
     */
    void onBindAppWidget(Launcher launcher) {
        if (!mHasNotifiedInitialWidgetSizeChanged) {
            notifyWidgetSizeChanged(launcher);
        }
    }

    /**
     * Trigger an update callback to the widget to notify it that its size has changed.
     */
    void notifyWidgetSizeChanged(Launcher launcher) {
        //AppWidgetResizeFrame.updateWidgetSizeRanges(hostView, launcher, spanX, spanY);
        mHasNotifiedInitialWidgetSizeChanged = true;
    }

    @Override
    public String toString() {
        return "AppCard(id=" + Integer.toString(appCardId) + ")";
    }

    @Override
    void unbind() {
        super.unbind();
        hostView = null;
    }

    public static int checkCardType(int spanX, int spanY) {
        if (spanX == LauncherModel.MEAL_CARD[0].length &&
                spanY == LauncherModel.MEAL_CARD.length) {
            return CardsProvider.TYPE_MEAL;
        } else if (spanX == LauncherModel.SNACK_CARD[0].length &&
                spanY == LauncherModel.SNACK_CARD.length) {
            return CardsProvider.TYPE_SNACK;
        } else if (spanX == LauncherModel.BITE_CARD[0].length &&
                spanY == LauncherModel.BITE_CARD.length) {
            return CardsProvider.TYPE_BITE;
        } else {
            return -1;
        }
    }

    public int getCardType() {
        if (mCardType == 0) {
            checkCardType(spanX, spanY);
        }

        return mCardType;
    }
}
