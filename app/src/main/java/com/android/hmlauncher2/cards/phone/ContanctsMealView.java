package com.android.hmlauncher2.cards.phone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hmlauncher2.R;
import com.android.hmlauncher2.cards.phone.adapter.ContactslistAdapter;
import com.android.hmlauncher2.cards.phone.listner.ICallRequestObs;
import com.android.hmlauncher2.cards.util.FCALinearLayoutManager;
import com.android.hmlauncher2.cards.util.UiUtils;
import com.android.hmlauncher2.cards.widgets.HomeScreenViewPager;
import com.android.hmlauncher2.cards.widgets.PageIndicator;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


/**
 * Created by soma on 1/26/2017.
 */

public class ContanctsMealView extends LinearLayout  {

    private PageIndicator mPageIndicator;
    private Context mContext;
    private ArrayList<Contacts> mAdapterList = new ArrayList();
    private ArrayList<Contacts> mRecentAdapterList = new ArrayList();

    private ContactslistAdapter mContactListAdapter;
    private ContactslistAdapter mRecentContactAdapter;
    private FCALinearLayoutManager mFCALinearLayoutManager;
    private FCALinearLayoutManager mRecentLinearLayoutManager;

    private RecyclerView favoriteRecyclerView;
    private RecyclerView recentRecyclerView;

    public static final String TAG = ContanctsMealView.class.getCanonicalName();

    public ContanctsMealView(Context context) {
        super(context);
        mContext = context;
    }

    public ContanctsMealView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ContanctsMealView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public ContanctsMealView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


//        AppWidgetResizeFrame.registerWidgetChange(HomeScreenConstants.CARD_ACTIVE_CALL , this);
        mAdapterList.clear();
        mAdapterList = getFavoriteContacts();
        mRecentAdapterList.clear();
        mRecentAdapterList = getRecentListContacts();

        mContactListAdapter = new ContactslistAdapter(mContext,mAdapterList,ContactslistAdapter.FAVORITE_ADAPTER );


        mPageIndicator = (PageIndicator) findViewById(R.id.page_indicator);
        TextView tv = (TextView) findViewById(R.id.card_title);
        tv.setText("Contacts");

        LinearLayout favParent = (LinearLayout) this.findViewById(R.id.fav_layout);
        LinearLayout recentParent = (LinearLayout) this.findViewById( R.id.recent_layout);

        mFCALinearLayoutManager = new FCALinearLayoutManager(getContext());
        mFCALinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        favoriteRecyclerView = (RecyclerView) favParent.findViewById(R.id.recycler_view);

        favoriteRecyclerView.setLayoutManager(mFCALinearLayoutManager);
        favoriteRecyclerView.setAdapter(mContactListAdapter);
        favoriteRecyclerView.setNestedScrollingEnabled(true);
        favoriteRecyclerView.setHasFixedSize(true);

        mRecentLinearLayoutManager = new FCALinearLayoutManager(getContext());
        mRecentLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecentContactAdapter = new ContactslistAdapter(mContext,mRecentAdapterList,ContactslistAdapter.RECENTLIST_ADAPTER );
        recentRecyclerView = (RecyclerView) recentParent.findViewById(R.id.recent_recycler_view);

        recentRecyclerView.setLayoutManager(mRecentLinearLayoutManager);
        recentRecyclerView.setAdapter(mRecentContactAdapter);
        recentRecyclerView.setNestedScrollingEnabled(true);
        recentRecyclerView.setHasFixedSize(true);



        HomeScreenViewPager pager = (HomeScreenViewPager) findViewById(R.id.pager);
        mPageIndicator.setNumberOfPage(pager.getChildCount());
        mPageIndicator.setCurrentPage(pager.getCurrentItem());
        pager.setOnPageChangeListener(new HomeScreenViewPager.OnPageChangeListener() {
            @Override
            public void onPageChanged(int currentPage) {
                mPageIndicator.setCurrentPage(currentPage);
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

    private ArrayList<Contacts> getFavoriteContacts() {
        ArrayList<Contacts> lFavContacts = new ArrayList<Contacts>();
        Contacts lFav1 = new Contacts();
        lFav1.setCallerName("Matthew");
        lFav1.setCallerPhoneNo("9739870123");
        lFav1.setCallerImgUri("image");
        lFav1.setCallType(Contacts.INCOMING_CALL_TYPE);
        lFavContacts.add(lFav1);

        Contacts lFav2 = new Contacts();
        lFav2.setCallerName("Andy");
        lFav2.setCallType(Contacts.OUTGOING_CALL_TYPE);
        lFav2.setCallerPhoneNo("9738760123");
        lFav2.setCallerImgUri("image");
        lFavContacts.add(lFav2);

        Contacts lFav7 = new Contacts();
        lFav7.setCallerName("Jeanne Anderson");
        lFav7.setCallerPhoneNo("2348769321");
        lFav7.setCallerImgUri("image");
        lFav7.setCallType(Contacts.INCOMING_CALL_TYPE);
        lFavContacts.add(lFav7);


        Contacts lFav3 = new Contacts();
        lFav3.setCallerName("Bikash");
        lFav1.setCallType(Contacts.MISSED_CALL_TYPE);
        lFav3.setCallerPhoneNo("9739060123");
        lFav3.setCallerImgUri("image");
        lFavContacts.add(lFav3);

        Contacts lFav4 = new Contacts();
        lFav4.setCallerName("John");
        lFav1.setCallType(Contacts.INCOMING_CALL_TYPE);
        lFav4.setCallerPhoneNo("9731230987");
        lFav4.setCallerImgUri("image");
        lFavContacts.add(lFav4);

        Contacts lFav5 = new Contacts();
        lFav5.setCallerName("Nike");
        lFav1.setCallType(Contacts.INCOMING_CALL_TYPE);
        lFav5.setCallerPhoneNo("9733435987");
        lFav5.setCallerImgUri("image");
        lFavContacts.add(lFav5);

        Contacts lFav6 = new Contacts();
        lFav6.setCallerName("Smith");
        lFav1.setCallType(Contacts.MISSED_CALL_TYPE);
        lFav6.setCallerPhoneNo("9876543210");
        lFav6.setCallerImgUri("image");
        lFavContacts.add(lFav6);




        return lFavContacts;
    }

    private ArrayList<Contacts> getRecentListContacts() {
        ArrayList<Contacts> lFavContacts = new ArrayList<Contacts>();
        Contacts lFav1 = new Contacts();
        lFav1.setCallerName("Matthew");
        lFav1.setCallerPhoneNo("9739870123");
        lFav1.setCallerImgUri("image");
        lFav1.setCallType(Contacts.MISSED_CALL_TYPE);
        lFavContacts.add(lFav1);

        Contacts lFav2 = new Contacts();
        lFav2.setCallerName("Andy");
        lFav2.setCallerPhoneNo("9738760123");
        lFav2.setCallerImgUri("image");
        lFav2.setCallType(Contacts.OUTGOING_CALL_TYPE);
        lFavContacts.add(lFav2);
        /*
        Contacts lFav3 = new Contacts();
        lFav3.setCallerName("Bikash");
        lFav3.setCallerPhoneNo("9739060123");
        lFav3.setCallerImgUri("image");
        lFavContacts.add(lFav3);*/

        return lFavContacts;
    }


//    @Override
//    public void notifyWidgetSizeChanged(Bundle newOptions, int minWidth, int minHeight, int maxWidth, int maxHeight, boolean ignorePadding) {
//        Log.d(TAG, "notifyWidgetSizeChanged() ----->>>> minWidth =="+minWidth + " Min Height="+minHeight + " MaxHeight="+maxHeight );
//    }
}
