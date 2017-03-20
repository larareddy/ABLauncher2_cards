package com.android.hmlauncher2.cards.phone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.util.UiUtils;

/**
 * Created by soma on 1/26/2017.
 */

public class ActiveFullScreenCallSnackView extends LinearLayout {
    public ActiveFullScreenCallSnackView(Context context) {
        super(context);
    }
    public ActiveFullScreenCallSnackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActiveFullScreenCallSnackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ActiveFullScreenCallSnackView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        TextView tv = (TextView) findViewById(R.id.card_title);
        tv.setText("Call Ended");
        ImageView img1 = (ImageView) findViewById(R.id.caller_img_id);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.bg_signin);
        Bitmap resized = Bitmap.createScaledBitmap(bm, 100, 100, true);
        Bitmap conv_bm = UiUtils.getRoundedBitmap(resized);
        img1.setImageBitmap(conv_bm);
    }
}
