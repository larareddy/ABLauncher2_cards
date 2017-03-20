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

/**
 * Created by soma on 1/26/2017.
 */

public class ActiveCallSnackViewnNew extends LinearLayout {
    public ActiveCallSnackViewnNew(Context context) {
        super(context);
    }
    public ActiveCallSnackViewnNew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActiveCallSnackViewnNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ActiveCallSnackViewnNew(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        TextView tv = (TextView) findViewById(R.id.card_title);
        tv.setText("Call Snack view");

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
