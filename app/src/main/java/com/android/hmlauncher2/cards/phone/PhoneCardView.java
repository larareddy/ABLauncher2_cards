package com.android.hmlauncher2.cards.phone;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.CardsProvider;
import com.android.hmlauncher2.cards.phone.listner.IFCACallState;
import com.android.hmlauncher2.cards.util.UiUtils;

/**
 * Created by soma on 2/13/2017.
 */

public class PhoneCardView extends FrameLayout implements IFCACallState {
    private static final String TAG = PhoneCardView.class.getCanonicalName();
    private static final boolean DEBUG = true;
    Context mContext;
    private int mCardType = CardsProvider.TYPE_INVALID;

    public PhoneCardView(Context context) {
        super(context);
        this.mContext = context;
    }

    public PhoneCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public PhoneCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public PhoneCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

   void registerPhonelistner() {
        FCAPhoneStateListner lTelephony = new FCAPhoneStateListner(getContext(), this);
        lTelephony.registerLstner();
    }

    void setScreenView(int aLayoutId) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        View v = View.inflate(getContext(), aLayoutId, null);
        v.setLayoutParams(params);
        removeAllViews();
        addView(v, params);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerPhonelistner();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //TO-DO: unregisterPhoneListener();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (DEBUG) {
            Log.d(TAG, "onSizeChanged w=" + w + ", h=" + h + ", oldw=" + oldw + ", oldh=" + oldh);
        }
        int cardType = UiUtils.checkCardTypeWithLayoutSize(w, h);
        if (cardType != mCardType) {
            mCardType = cardType;
            post(new Runnable() {
                @Override
                public void run() {
                    updateLayout(mCardType);
                }
            });
        }
    }

    public void updateLayout(final int cardType) {
        switch (cardType) {
            case CardsProvider.TYPE_MEAL:
                setScreenView(R.layout.fca_phone_meal);
                break;
            case CardsProvider.TYPE_SNACK:
                setScreenView(R.layout.fca_phone_snack_view);
                break;
            case CardsProvider.TYPE_BITE:
                setScreenView(R.layout.fca_phone_bite);
                break;
            default:
                setScreenView(R.layout.fca_phone_meal);
                break;
        }
    }

    @Override
    public void onStateChange(int aState, String aIncomingNo) {
        Log.d(TAG, "onStateChange() ---> state =" + aState + " Incoming Call from " + aIncomingNo);

        //getCalldetails(mContext);
    }

    private void getCalldetails(Context cxt) {

     /*   if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }*/
        Cursor cur = cxt.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,null,null, CallLog.Calls.DATE + " DESC limit 1;");

        if (cur.moveToFirst()) { /* false = no calls */
            do {
                String msgInfo = "";

       /* Phone No */
                int colPhoneNo = cur.getColumnIndex(CallLog.Calls.NUMBER);
                int colDuration = cur.getColumnIndex(CallLog.Calls.DURATION);
                int colName = cur.getColumnIndex(CallLog.Calls.CACHED_NAME);

                TextView name = (TextView) findViewById(R.id.caller_name);
                String nameValue = cur.getString(colName);
                String phoneValue = UiUtils.getFormattedPhoneNo(cur.getString(colPhoneNo));
                String callDuration = UiUtils.getCallDuration(cur.getString(colDuration));
                TextView phoneNo = (TextView) findViewById(R.id.phone_no);
                phoneNo.setText(phoneValue);
                TextView duration = (TextView) findViewById(R.id.call_ducaration);
                duration.setText(callDuration);
                if(nameValue != null && !nameValue.isEmpty()) {
                    name.setText(nameValue);
                }/*else {
                    name.setText(phoneValue);
                }*/

                msgInfo += "Name: "+nameValue +"\nNumber: " + phoneValue + "\n" ;

       /* Call Type */
       /* 1 - incoming, 2 - outgoing, 3 - missed */
                int colType = cur.getColumnIndex("type");
                int valType = cur.getInt(colType);
                if (valType == 1) {
                    msgInfo += "Call Type: Incoming\n";
                } else if (valType == 2) {
                    msgInfo += "Call Type: Outgoing\n";
                } else if (valType == 3) {
                    msgInfo += "Call Type: Missed\n";
                }

       /* Date (timestamp) */
                int colDate = cur.getColumnIndex("date");
                msgInfo += "Date: " + cur.getString(colDate);

//                Toast.makeText(cxt, msgInfo, Toast.LENGTH_SHORT).show();
            } while (cur.moveToNext());
        }
    }

}
