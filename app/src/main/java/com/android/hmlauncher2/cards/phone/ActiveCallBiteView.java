package com.android.hmlauncher2.cards.phone;

import android.content.Context;
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
 * Created by soma on 2/15/2017.
 */

public class ActiveCallBiteView extends LinearLayout {
    private PageIndicator mPageIndicator;

    public ActiveCallBiteView(Context context) {
        super(context);
    }

    public ActiveCallBiteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActiveCallBiteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ActiveCallBiteView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        TextView titleTV = (TextView) findViewById(R.id.card_title);
        titleTV.setText("Phone");
        mPageIndicator = (PageIndicator) findViewById(R.id.page_indicator);
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
}
