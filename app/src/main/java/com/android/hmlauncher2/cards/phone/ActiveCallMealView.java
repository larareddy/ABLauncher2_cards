package com.android.hmlauncher2.cards.phone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.phone.adapter.ContactslistAdapter;
import com.android.hmlauncher2.cards.util.UiUtils;
import com.android.hmlauncher2.cards.widgets.HomeScreenViewPager;
import com.android.hmlauncher2.cards.widgets.PageIndicator;


/**
 * Created by soma on 1/26/2017.
 */

public class ActiveCallMealView extends LinearLayout  {

    private PageIndicator mPageIndicator;
    private Context mContext;

    public static final String TAG = ActiveCallMealView.class.getCanonicalName();

    public ActiveCallMealView(Context context) {
        super(context);
        mContext = context;
    }

    public ActiveCallMealView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ActiveCallMealView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public ActiveCallMealView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


//        AppWidgetResizeFrame.registerWidgetChange(HomeScreenConstants.CARD_ACTIVE_CALL , this);

        mPageIndicator = (PageIndicator) findViewById(R.id.page_indicator);
        TextView tv = (TextView) findViewById(R.id.card_title);
        tv.setText("Active Call View");
        HomeScreenViewPager pager = (HomeScreenViewPager) findViewById(R.id.pager);
        mPageIndicator.setNumberOfPage(pager.getChildCount());
        mPageIndicator.setCurrentPage(pager.getCurrentItem());
        pager.setOnPageChangeListener(new HomeScreenViewPager.OnPageChangeListener() {
            @Override
            public void onPageChanged(int currentPage) {
                mPageIndicator.setCurrentPage(currentPage);
            }
        });

        Contacts callContact = ContactslistAdapter.mContactsClicked;

        ImageView img1 = (ImageView) findViewById(R.id.caller_img_id);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.bg_signin);
        Bitmap resized = Bitmap.createScaledBitmap(bm, 100, 100, true);
        Bitmap conv_bm = UiUtils.getRoundedBitmap(resized);
        img1.setImageBitmap(conv_bm);

        TextView callerName = (TextView) findViewById(R.id.caller_name);
        callerName.setText(callContact.getCallerName());
        TextView callerMDN = (TextView) findViewById(R.id.phone_no);
        callerMDN.setText(UiUtils.getFormattedPhoneNo(callContact.getCallerPhoneNo()));

        ImageView endCall = (ImageView) findViewById(R.id.end_call);
        endCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContactslistAdapter.mCallReqObs != null) {
                    ContactslistAdapter.mCallReqObs.switchLayout(false);
                }
            }
        });

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        AppWidgetResizeFrame.unRegisterWidgetChange(HomeScreenConstants.CARD_ACTIVE_CALL);
    }

//    @Override
//    public void notifyWidgetSizeChanged(Bundle newOptions, int minWidth, int minHeight, int maxWidth, int maxHeight, boolean ignorePadding) {
//        Log.d(TAG, "notifyWidgetSizeChanged() ----->>>> minWidth =="+minWidth + " Min Height="+minHeight + " MaxHeight="+maxHeight );
//    }
}
