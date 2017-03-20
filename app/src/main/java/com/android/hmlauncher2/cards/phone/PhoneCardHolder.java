package com.android.hmlauncher2.cards.phone;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.BaseCardHolder;
import com.android.hmlauncher2.cards.CardsProvider;
import com.android.hmlauncher2.cards.phone.adapter.ContactslistAdapter;
import com.android.hmlauncher2.cards.phone.listner.ICallRequestObs;
import com.android.hmlauncher2.cards.phone.listner.IFCACallState;
import com.android.hmlauncher2.cards.util.UiUtils;

/**
 * Created by soma on 2/13/2017.
 */

public class PhoneCardHolder extends BaseCardHolder implements IFCACallState,ICallRequestObs {
    private static final String TAG = PhoneCardHolder.class.getCanonicalName();
    private static final boolean DEBUG = true;
    private int mCurrentCardType;
    private boolean isActiveCall = false;

    public PhoneCardHolder(Context context) {
        super(context);
    }

    public PhoneCardHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhoneCardHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void updateLayout(final int cardType) {
        ContactslistAdapter.mLayoutType = cardType;
        mCurrentCardType = cardType;
        int layout = R.layout.fca_phone_meal;
        switch (cardType) {
            case CardsProvider.TYPE_MEAL:

                if(isActiveCall) {
                   layout = R.layout.fca_phone_meal;
                }else {
                    ContactslistAdapter.mContactListLayout = R.layout.fca_contacts_list;
                    layout = R.layout.fca_contacts;
                }

                break;
            case CardsProvider.TYPE_SNACK:

                if(isActiveCall) {
                    layout = R.layout.fca_phone_snack_view;
                }else {
                    ContactslistAdapter.mContactListLayout = R.layout.fca_contacts_list;
                    layout = R.layout.fca_contacts;
                }

//                break;
//            case CardsProvider.TYPE_SNACK:
//                setCardView(R.layout.fca_phone_snack_view);
//                ContactslistAdapter.mContactListLayout = R.layout.fca_contacts_list;
//                setCardView(R.layout.fca_contacts);
                break;
            case CardsProvider.TYPE_BITE:
//                setCardView(R.layout.fca_phone_bite);
                if(isActiveCall) {
                    layout = R.layout.fca_phone_bite;
                }else {
                    ContactslistAdapter.mContactListLayout = R.layout.fca_contacts_list_bite;
                    layout = R.layout.fca_contacts_bite;
                }
                break;
            default:
                ContactslistAdapter.mContactListLayout = R.layout.fca_contacts_list;
//                setCardView(R.layout.fca_phone_meal);
                layout = R.layout.fca_phone_meal;
                break;
        }

        setCardView(layout);
    }



    @Override
    public void onStateChange(int aState, String aIncomingNo) {
        Log.d(TAG, "onStateChange() ---> state =" + aState + " Incoming Call from " + aIncomingNo);

        //getCalldetails(mContext);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ContactslistAdapter.registerCallReq(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ContactslistAdapter.unregisterCallReq();
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
        Cursor cur = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null,null,null, CallLog.Calls.DATE + " DESC limit 1;");

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

    @Override
    public void onCallRequest(int aCallRequestType, Contacts contacts) {
        switch (aCallRequestType) {
            case ICallRequestObs.CALL_ORIGINATE_REQUEST:
                break;
            case ICallRequestObs.HANG_UP_REQUEST:
                break;
            case ICallRequestObs.INCOMING_CALL_REQUEST:
                break;
        }
    }

    @Override
    public void switchLayout(boolean isActiveCall) {
        this.isActiveCall = isActiveCall;
        updateLayout(mCurrentCardType);
    }
}
