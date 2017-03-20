package com.android.hmlauncher2.cards.phone.listner;

import com.android.hmlauncher2.cards.phone.Contacts;

/**
 * Created by soma on 3/2/2017.
 */

public interface ICallRequestObs {
    public static final int CALL_ORIGINATE_REQUEST = 1000;
    public static final int HANG_UP_REQUEST = 1001;
    public static final int INCOMING_CALL_REQUEST = 1002;
    void onCallRequest(int aCallRequestType , Contacts contacts);
    void switchLayout(boolean isActiveCall );
}
