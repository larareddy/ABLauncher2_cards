package com.android.hmlauncher2.cards.phone;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.hmlauncher2.cards.phone.listner.IFCACallState;

import java.util.List;

/**
 * Created by Bikash on 1/25/2017.
 */

public class FCAPhoneStateListner extends PhoneStateListener {
    private Context mContext;
    private String TAG = FCAPhoneStateListner.class.getCanonicalName();
    private IFCACallState mCallStateListner;

    /**
     * Constructor
     * @param aCxt Application Context
     */
    public FCAPhoneStateListner(Context aCxt , IFCACallState aCallState) {
        this.mContext = aCxt;
        this.mCallStateListner = aCallState;

    }
    public FCAPhoneStateListner(Context aCxt ) {
        this.mContext = aCxt;

    }
    public void registerStateListner(IFCACallState aStateListner) {
        this.mCallStateListner = aStateListner;
    }

    /**
     * Register Phone state listner
     */
    public void registerLstner() {
        TelephonyManager telephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyMgr.listen(this,
                PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * Call State change listener to listen the state change
     * @param state Call state
     * @param incomingNumber Incoming Number
     */

    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        if(mCallStateListner != null) {
            mCallStateListner.onStateChange(state,incomingNumber);
        }
        /*switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                // CALL_STATE_IDLE;
                Toast.makeText(mContext, "CALL_STATE_IDLE",
                        Toast.LENGTH_LONG).show();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                // CALL_STATE_OFFHOOK;
                Toast.makeText(mContext, "CALL_STATE_OFFHOOK",
                        Toast.LENGTH_LONG).show();
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                // CALL_STATE_RINGING
                Toast.makeText(mContext, incomingNumber,
                        Toast.LENGTH_LONG).show();
                Toast.makeText(mContext, "CALL_STATE_RINGING",
                        Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }*/
    }

    /**
     *
     * @param cellInfo
     */

    public void onCellInfoChanged(List<CellInfo> cellInfo) {
        Log.d(TAG, "onCellInfoChanged-->"+cellInfo.size());
    }
}
