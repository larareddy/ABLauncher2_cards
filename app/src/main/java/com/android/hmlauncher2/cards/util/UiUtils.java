package com.android.hmlauncher2.cards.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
//import android.support.v4.app.ActivityCompat;

import com.android.hmlauncher2.LauncherModel;
import com.android.hmlauncher2.cards.CardsProvider;

import java.util.concurrent.TimeUnit;

/**
 * Created by soma on 1/27/2017.
 */

public class UiUtils {

    /**
     *  <uses-permission android:name="android.permission.READ_CONTACTS" />
     <uses-permission android:name="android.permission.CALL_PHONE" />
     <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
     <uses-permission android:name="android.permission.READ_CALL_LOG" />
     <uses-permission android:name="android.permission.WRITE_CALL_LOG" />
     <uses-permission android:name="android.permission.SET_WALLPAPER" />
     <uses-permission android:name="android.permission.SET_WALLPAPER_HINTS" />
     <uses-permission android:name="android.permission.VIBRATE" />
     <uses-permission android:name="android.permission.BIND_APPWIDGET" />
     <uses-permission android:name="android.permission.GET_ACCOUNTS_PRIVILEGED" />
     <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" />
     <uses-permission android:name="com.android.launcher.permission.WRITE_SETTINGS" />
     */

    private static int[] SIZE_MEAL_CARD;
    private static int[] SIZE_SNACK_CARD;
    private static int[] SIZE_BITE_CARD;
    private static int TOL_VAL = 100;

    private static String[] permissionNeed =
            {
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.PROCESS_OUTGOING_CALLS,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.SET_WALLPAPER,
                    Manifest.permission.SET_WALLPAPER_HINTS,
                    Manifest.permission.VIBRATE,
                    Manifest.permission.BIND_APPWIDGET,
//                    Manifest.permission.GET_ACCOUNTS_PRIVILEGED,
                    Manifest.permission.WRITE_SETTINGS


            };

    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 200, 200);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(50, 50, 50, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }
    public static Bitmap getRoundedBitmap(Bitmap bitmap)
    {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

 /*  public static boolean checkPermissionGranted(Activity activity, String permission)
    {
        return activity.checkSelfPermission( permission) == PackageManager.PERMISSION_GRANTED;

    }*/

   /* public static  void checkPermission(Activity activity)
    {
        for (String lPerm : permissionNeed) {
            if (!checkPermissionGranted(activity, lPerm)) {

                // Should we show an explanation?
                *//*if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        lPerm)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(activity,
                            permissionNeed,
                            1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }*//*
            }
        }
    }

    *//**
     * This api format the tracking number for UI
//     * @param aPhoneNo Phone number to be formtted
     * @return String
     *         Formatted Phone Number
     */
    public static String getFormattedPhoneNo(String aPhoneNo) {
        String lPhoneNo = aPhoneNo;
        if(aPhoneNo != null && !aPhoneNo.isEmpty()) {
            lPhoneNo  = "(" + aPhoneNo.substring(0,3) + ") " + aPhoneNo.substring(3,6) + "-" + aPhoneNo.substring(6 , aPhoneNo.length());
        }
        return lPhoneNo;
    }

    public static String getCallDuration(String aData) {
        String fomatedData = "00:00:00";
        long millis = 0;
        if (aData != null && !aData.isEmpty()) {
            millis = Long.parseLong(aData) * 1000;

            fomatedData = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(fomatedData);
        }


        return fomatedData;
    }

    public static int checkCardTypeWithLayoutSize(int width, int height) {
        final int cellWidth = LauncherModel.getCellWidth();
        final int cellHeight = LauncherModel.getCellHeight();
        if (SIZE_MEAL_CARD == null) {
            SIZE_MEAL_CARD = new int [2];
            SIZE_MEAL_CARD[0] = LauncherModel.MEAL_CARD[0].length * cellWidth;
            SIZE_MEAL_CARD[1] = LauncherModel.MEAL_CARD.length * cellHeight;
        }

        if (SIZE_SNACK_CARD == null) {
            SIZE_SNACK_CARD = new int [2];
            SIZE_SNACK_CARD[0] = LauncherModel.SNACK_CARD[0].length * cellWidth;
            SIZE_SNACK_CARD[1] = LauncherModel.SNACK_CARD.length * cellHeight;
        }

        if (SIZE_BITE_CARD == null) {
            SIZE_BITE_CARD = new int [2];
            SIZE_BITE_CARD[0] = LauncherModel.BITE_CARD[0].length * cellWidth;
            SIZE_BITE_CARD[1] = LauncherModel.BITE_CARD.length * cellHeight;
        }

        int type = CardsProvider.TYPE_INVALID;

        if (Math.abs(width - SIZE_MEAL_CARD[0]) < TOL_VAL &&
                Math.abs(height - SIZE_MEAL_CARD[1]) < TOL_VAL) {
            type = CardsProvider.TYPE_MEAL;
        } else if (Math.abs(width - SIZE_SNACK_CARD[0]) < TOL_VAL &&
                Math.abs(height - SIZE_SNACK_CARD[1]) < TOL_VAL) {
            type = CardsProvider.TYPE_SNACK;
        } else if (Math.abs(width - SIZE_BITE_CARD[0]) < TOL_VAL &&
                Math.abs(height - SIZE_BITE_CARD[1]) < TOL_VAL) {
            type = CardsProvider.TYPE_BITE;
        }

        return type;
    }
}
