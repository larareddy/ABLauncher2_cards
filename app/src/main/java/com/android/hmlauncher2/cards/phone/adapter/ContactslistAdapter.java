package com.android.hmlauncher2.cards.phone.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.hmlauncher2.R;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.hmlauncher2.cards.CardsProvider;
import com.android.hmlauncher2.cards.phone.Contacts;
import com.android.hmlauncher2.cards.phone.listner.ICallRequestObs;
import com.android.hmlauncher2.cards.util.UiUtils;

/**
 * Created by BMohanty2 on 2/28/2017.
 */

public class ContactslistAdapter extends RecyclerView.Adapter<ContactslistAdapter.RecylerViewHolder>{

    public static final int FAVORITE_ADAPTER = 1000;
    public static final int RECENTLIST_ADAPTER = 2000;
    public static int mContactListLayout = R.layout.fca_phone_recentlist;
    public static int mLayoutType = CardsProvider.TYPE_INVALID;

    public static Contacts mContactsClicked;

    private String TAG = ContactslistAdapter.class.getCanonicalName();
    private View mView;
    private ArrayList<Contacts> mAdapterItemList = new ArrayList();
    private Context mContext;
    private int mAdapterType;

    public ContactslistAdapter(Context aContext , ArrayList<Contacts> aAdapterList , int aAdapterType) {
        this.mContext = aContext;
        this.mAdapterItemList = aAdapterList;
        this.mAdapterType = aAdapterType;
    }
    public static ICallRequestObs mCallReqObs ;

    public static void registerCallReq(ICallRequestObs aCallReqObs) {
        mCallReqObs = aCallReqObs;
    }
    public static ICallRequestObs getCallReqObs() {
        return mCallReqObs;
    }
    public static void unregisterCallReq() {
        mCallReqObs = null;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.fca_phone_recentlist, parent, false);
        return new RecylerViewHolder(mView, mAdapterType);
    }
    @Override
    public void onBindViewHolder(RecylerViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }


    @Override
    public void onBindViewHolder(final RecylerViewHolder holder, int position) {

//        Log.v(TAG,"onBindViewHolder()-->position" + position);
        if (holder != null && holder instanceof RecylerViewHolder) {
            if(holder.mAddLayout != null) {
                holder.mAddLayout.setVisibility(View.GONE);
            }
            if (position == (mAdapterItemList.size() )) {
                holder.mContactLayout.setVisibility(View.GONE);
                if(holder.mAddLayout != null) {
                    holder.mAddLayout.setVisibility(View.VISIBLE);
                }
            } else {
                Contacts lContacts = mAdapterItemList.get(position);
                if(holder.mCallerImg != null)
                holder.mCallerImg.setImageBitmap(getCallerImg(lContacts.getCallType()));
                holder.mCallerMDN.setText(UiUtils.getFormattedPhoneNo(lContacts.getCallerPhoneNo()));
                holder.mCallerName.setText(lContacts.getCallerName());

            }
        }
    }

    @Override
    public void onViewAttachedToWindow(RecylerViewHolder holder) {
        super.onViewAttachedToWindow(holder);

    }


    private Bitmap getCallerImg(int aCallerType) {
        int aDrawable = R.drawable.incoming_call_ic;
        if(aCallerType == Contacts.OUTGOING_CALL_TYPE) {
            aDrawable = R.drawable.outgoing_call_ic;
        }else {
            aDrawable = R.drawable.missed_call_ic;
        }
        Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), aDrawable);
        Bitmap resized = Bitmap.createScaledBitmap(bm, 100, 100, true);
        Bitmap conv_bm = UiUtils.getRoundedBitmap(resized);
        return conv_bm;
    }

    @Override
    public int getItemCount() {
//        Logger.v("RecyclerViewTest","getItemCount()-->item list size"+mAdapterItemList.size());
        if (mAdapterItemList.size()!=0)
            return mAdapterItemList.size()+1;
        else
            return 0;
    }


    class RecylerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mCallerImg;
        private TextView  mCallerName;
        private TextView  mCallerMDN;
        private ImageView mAddFavoriteButton;
        private TextView mAddFavorite;
        private LinearLayout mAddLayout;
        private LinearLayout mCallLayout;
        RelativeLayout mContactLayout;

        private int viewHolderPosition;

        public RecylerViewHolder(final View itemView , int aAdapterType) {
            super(itemView);
            viewHolderPosition = getAdapterPosition();
            mAddLayout = (LinearLayout)itemView.findViewById(R.id.contact_recent_layout);
            mCallLayout = (LinearLayout)itemView.findViewById(R.id.call_layout);
            mContactLayout = (RelativeLayout) itemView.findViewById(R.id.contact_rel_layout);
            mCallerImg = (ImageView) itemView.findViewById(R.id.caller_img);
            mAddFavoriteButton = (ImageView) mAddLayout.findViewById(R.id.add_favorite_img);
            mAddFavorite = (TextView) mAddLayout.findViewById(R.id.add_favorite_txt);
            mCallerName = (TextView) itemView.findViewById(R.id.caller_name);
            mCallerMDN      = (TextView) itemView.findViewById( R.id.phone_no);
           if(mLayoutType == CardsProvider.TYPE_BITE) {
                mCallerImg.setVisibility(View.GONE);
                mCallerName.setTextSize(18);
                mCallerMDN.setTextSize(18);
                mAddFavoriteButton.setVisibility(View.GONE);
               mAddFavorite.setTextSize(18);
            }else {
               mCallerImg.setVisibility(View.VISIBLE);
               mCallerName.setTextSize(20);
               mCallerMDN.setTextSize(20);
               mAddFavoriteButton.setVisibility(View.VISIBLE);
               mAddFavorite.setTextSize(20);
           }
            if(mAddLayout != null)
            mAddLayout.setOnClickListener(this);
            if(mCallLayout != null)
            mCallLayout.setOnClickListener(this);
            if(mCallerMDN != null) {
                mCallerMDN.setOnClickListener(this);
            }
        }
        @Override
        public void onClick(View view) {

//            Logger.d(TAG, "onClick()---"+view.toString());

            if (view.equals(mAddLayout))
            {
                Toast.makeText(mContext,"Add Layout view clicked "  , Toast.LENGTH_LONG).show();
            }else if(view.equals(mCallLayout) || view.equals(mCallerMDN) ) {
//                Toast.makeText(mContext,"Call Layout view clicked "  , Toast.LENGTH_LONG).show();
                if(mCallReqObs != null) {
                    int position = getAdapterPosition();
                    if(position < mAdapterItemList.size()) {
                        mContactsClicked = mAdapterItemList.get(position);
                        mCallReqObs.switchLayout(true);
                    }
                }
            }


        }
    }
}


















