package com.android.hmlauncher2.cards.climate;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.BaseCardHolder;
import com.android.hmlauncher2.cards.CardsProvider;

import java.util.ArrayList;

/**
 * Created by soma on 2/13/2017.
 */

public class ClimateCardHolder extends BaseCardHolder implements View.OnClickListener {
    private static final String TAG = ClimateCardHolder.class.getCanonicalName();
    private ViewHolder mViewHolder;
    ArrayList<ImageView> mFanSpeedViews = new ArrayList<>();

    class ViewHolder {
        ImageView mLeftHavcWarmer;
        ImageView mLeftHavcCool;
        ImageView mRightHavcWarmer;
        ImageView mRightHavcCool;

        TextView mLeftTemp;
        TextView mRightTemp;

        ImageView mFanLowspeed;
        ImageView mWind1;
        ImageView mWind2;
        ImageView mWind3;
        ImageView mWind4;
        ImageView mWind5;
        ImageView mWind6;
        ImageView mWind7;
        ImageView mFanHighspeed;


    }

    public ClimateCardHolder(Context context) {
        super(context);
    }

    public ClimateCardHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClimateCardHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public void updateLayout(final int cardType) {

        switch (cardType) {
            case CardsProvider.TYPE_MEAL:
                setCardView(R.layout.climate_mealview);
                break;
            case CardsProvider.TYPE_SNACK:
//                setCardView(R.layout.climate_snack_views);
                setCardView(R.layout.climate_snackviews);
                break;
            case CardsProvider.TYPE_BITE:
                setCardView(R.layout.climate_biteviews);
                break;
            default:
                setCardView(R.layout.climate_mealview);
                break;
        }

        resetActions(cardType);
    }

    private void resetActions(int aType) {
        resetViews(aType);
        switch (aType) {
            case CardsProvider.TYPE_MEAL:

                break;
            case CardsProvider.TYPE_SNACK:
                break;
            case CardsProvider.TYPE_BITE:
                break;
            default:
                break;

        }

    }

    private void setFanSpeedColor(int aFan) {
        int count =0;
        for (ImageView mFanView : mFanSpeedViews) {
            if(count < aFan) {
//                mFanView.setBackgroundColor(getContext().getColor(R.color.colorSyncButton));

                mFanView.setBackgroundColor(getColor(getContext() ,R.color.colorSyncButton));
            }else {
//                mFanView.setBackgroundColor(getContext().getColor(R.color.colorDarkGray));
                mFanView.setBackgroundColor(getColor(getContext() ,R.color.colorDarkGray));
            }
            ++count;
        }
    }

    public static  int getColor(Context context , int id ) {

//            return context.getResources().getColor(id);
        return ContextCompat.getColor(context , id);
    }





   private void resetViews(int aType) {
       mViewHolder = new ViewHolder();
       mFanSpeedViews.clear();
       mViewHolder.mFanLowspeed = (ImageView) findViewById(R.id.wind_fan_low_speed);
       mViewHolder.mWind1 = (ImageView) findViewById(R.id.wind1);
       mViewHolder.mWind2 = (ImageView) findViewById(R.id.wind2);
       mViewHolder.mWind3 = (ImageView) findViewById(R.id.wind3);
       mViewHolder.mWind4 = (ImageView) findViewById(R.id.wind4);
       mViewHolder.mWind5 = (ImageView) findViewById(R.id.wind5);
       mViewHolder.mWind6 = (ImageView) findViewById(R.id.wind6);
       mViewHolder.mWind7 = (ImageView) findViewById(R.id.wind7);
       mViewHolder.mFanHighspeed = (ImageView) findViewById(R.id.windfan_high_speed);

       mViewHolder.mLeftHavcWarmer = (ImageView) findViewById(R.id.left_havc_warm);
       mViewHolder.mLeftHavcCool = (ImageView) findViewById(R.id.left_havc_cooler);
       mViewHolder.mRightHavcWarmer = (ImageView) findViewById(R.id.right_havc_warm);
       mViewHolder.mRightHavcCool = (ImageView) findViewById(R.id.right_havc_cooler);

       mViewHolder.mRightTemp = (TextView) findViewById(R.id.temp_right) ;
       mViewHolder.mLeftTemp = (TextView) findViewById(R.id.temp_left);

       mFanSpeedViews.add(mViewHolder.mWind1);
       mFanSpeedViews.add(mViewHolder.mWind2);
       mFanSpeedViews.add(mViewHolder.mWind3);
       mFanSpeedViews.add(mViewHolder.mWind4);
       mFanSpeedViews.add(mViewHolder.mWind5);
       mFanSpeedViews.add(mViewHolder.mWind6);
       mFanSpeedViews.add(mViewHolder.mWind7);

       //OnClick Listners
       mViewHolder.mFanLowspeed.setOnClickListener(this);
       mViewHolder.mWind1.setOnClickListener(this);
       mViewHolder.mWind2.setOnClickListener(this);
       mViewHolder.mWind3.setOnClickListener(this);
       mViewHolder.mWind4.setOnClickListener(this);
       mViewHolder.mWind5.setOnClickListener(this);
       mViewHolder.mWind6.setOnClickListener(this);
       mViewHolder.mWind7.setOnClickListener(this);
       mViewHolder.mFanHighspeed.setOnClickListener(this);

       mViewHolder.mLeftHavcCool.setOnClickListener(this);
       mViewHolder.mLeftHavcWarmer.setOnClickListener(this);
       mViewHolder.mRightHavcWarmer.setOnClickListener(this);
       mViewHolder.mRightHavcCool.setOnClickListener(this);



        switch (aType) {

            case CardsProvider.TYPE_MEAL:

                break;
            case CardsProvider.TYPE_SNACK:
                break;
            case CardsProvider.TYPE_BITE:
                break;
            default:
                break;

        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinish Inflate() ----");
    }



    @Override
    public void onClick(View view) {

        int lActiveFans = 0;

        if(view.equals(mViewHolder.mFanLowspeed)) {
            setFanSpeedColor(lActiveFans);
        }else if(view.equals(mViewHolder.mWind1)) {
            lActiveFans =1;
            setFanSpeedColor(lActiveFans);

        }else if(view.equals(mViewHolder.mWind2)) {
            lActiveFans = 2;
            setFanSpeedColor(lActiveFans);
        }else if(view.equals(mViewHolder.mWind3)) {
            lActiveFans = 3;
            setFanSpeedColor(lActiveFans);
        }else if(view.equals(mViewHolder.mWind4)) {
            lActiveFans = 4;
            setFanSpeedColor(lActiveFans);
        }else if(view.equals(mViewHolder.mWind5)) {
            lActiveFans = 5;
            setFanSpeedColor(lActiveFans);
        }else if(view.equals(mViewHolder.mWind6)) {
            lActiveFans = 6;
            setFanSpeedColor(lActiveFans);
        }else if(view.equals(mViewHolder.mWind7)) {
            lActiveFans = 7;
            setFanSpeedColor(lActiveFans);
        } else if(view.equals(mViewHolder.mFanHighspeed)) {

            lActiveFans = 7;
            setFanSpeedColor(lActiveFans);
        }else if(view.equals(mViewHolder.mLeftHavcWarmer)) {
            changeTemp(true,true);
        }else if(view.equals(mViewHolder.mLeftHavcCool)) {
            changeTemp(true,false);
        }else if(view.equals(mViewHolder.mRightHavcWarmer)) {
            changeTemp(false,true);
        }else if(view.equals(mViewHolder.mRightHavcCool)) {
            changeTemp(false,false);
        }




    }

    private void changeTemp(boolean isLeftTemp, boolean IsIncreaseTemp) {
        String lText;
        TextView lTextView;
        if(isLeftTemp) {
            lTextView = mViewHolder.mLeftTemp;

        }else {
           lTextView = mViewHolder.mRightTemp;
        }
        lText = lTextView.getText().toString();
        int temp = Integer.parseInt(lText.substring(0, lText.indexOf("°")));
        if(IsIncreaseTemp) {
            temp +=2;
            lText= ""+temp + "°";
        }else {
            temp -=2;
            lText= ""+temp + "°";
        }
        lTextView.setText(lText);

    }
}
