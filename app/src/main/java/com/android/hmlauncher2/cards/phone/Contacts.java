package com.android.hmlauncher2.cards.phone;

/**
 * Created by BMohanty2 on 2/28/2017.
 */

public class Contacts {

    public static int INCOMING_CALL_TYPE = 1;
    public static int OUTGOING_CALL_TYPE = 2;
    public static int MISSED_CALL_TYPE = 3;

    private String mCallerName;
    private String mCalerPhoneNo;
    private String mImgUrl;
    private int mCallType;

    public void setCallerName(String aCallerName) {
        this.mCallerName = aCallerName;
    }
    public String getCallerName() {
        return mCallerName;
    }
    public void setCallerPhoneNo(String aPhoneNo) {
        this.mCalerPhoneNo = aPhoneNo;
    }
    public String getCallerPhoneNo() {
        return mCalerPhoneNo;
    }

    public void setCallerImgUri(String aImgUrl) {
        this.mImgUrl = aImgUrl;
    }

    public int getCallType() {
        return mCallType;
    }

    public void setCallType(int mCallType) {
        this.mCallType = mCallType;
    }
}
