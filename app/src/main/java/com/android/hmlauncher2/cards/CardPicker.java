package com.android.hmlauncher2.cards;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.hmlauncher2.R;

import java.util.List;

/**
 * Created by AnTang on 1/24/2017.
 */

public class CardPicker<T> extends BaseAdapter {
    private static final String TAG = "CardPicker";
    private static final boolean DEBUG = true;
    private static final int MSG_UPDATE_APP_LIST = 1;
    private static final int VAL_UNKNOWN = 0;
    protected final Context mContext;
    private final Dialog mDialog;
    private GridView mGridView;
    private TextView mTextView;
    private CardPickerListener mListener;
    private List<T> mList;

    public static class ItemViewHolder {
        TextView mTextView;
    }

    private boolean mDismissed;

    public interface CardPickerListener<T> {
        public void onCardSelected(int index, T data);

        public Drawable onGetThumbnail(T data);

        public String onGetLabel(T data);
    }

    public CardPicker(Context context) {
        mContext = context;
        mDialog = new Dialog(context) {
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if (isShowing() && event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    CardPicker.this.dismiss();
                    return true;
                }
                return false;
            }
        };

        final Window window = mDialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(true);
        //mDialog.setContentView(R.layout.card_pick);

        //TO-DO: remove after screen size confirmed, apply static value in card_pick.xml
        View contentView = View.inflate(mContext, R.layout.card_pick, null);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        if (DEBUG) {
            Log.d(TAG, "DISPLAY: widthPixels=" + metrics.widthPixels + ", heightPixels=" + metrics.heightPixels + ", density=" + metrics.density + ",densityDpi=" + metrics.densityDpi);
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int)(metrics.widthPixels*0.85), (int)(metrics.heightPixels*0.85));
        mDialog.setContentView(contentView, params);
        //TO-DO: remove after screen size confirmed, apply static value in card_pick.xml

        mDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.d(TAG, "onDismiss");
            }
        });

        mDialog.create();

        final LayoutParams lp = window.getAttributes();
        lp.token = null;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.windowAnimations = R.style.CardPickerAnimation;
        lp.setTitle(getClass().getCanonicalName());
        lp.width = LayoutParams.WRAP_CONTENT;
        lp.height = LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        //window.setBackgroundDrawable(context.getDrawable(R.drawable.add_apps_popup_bg));
        window.clearFlags(LayoutParams.FLAG_DIM_BEHIND);
        window.addFlags(LayoutParams.FLAG_NOT_FOCUSABLE
                | LayoutParams.FLAG_NOT_TOUCH_MODAL
                | LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | LayoutParams.FLAG_HARDWARE_ACCELERATED);

        window.setBackgroundDrawable(null);
        mGridView = (GridView) window.findViewById(R.id.card_pick_content);
        mTextView = (TextView) window.findViewById(R.id.picker_title);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (DEBUG) {
                    Log.d(TAG, "onItemClick: parent=" + parent + ", v=" + v + ", pos=" + position + ", id=" + id);
                }
                T data = getItem(position);
                mListener.onCardSelected(position, data);
                dismiss();
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final ClipData data = ClipData.newPlainText("abc", "efg");
                TextView v = (TextView)view;
                v.setText(null);
                final View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                boolean ret = v.startDrag(data, shadowBuilder, v, 0);
                return false;
            }
        });
        (window.findViewById(R.id.btn_card_picker_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public CardPicker setListener(CardPickerListener listener) {
        mListener = listener;
        return this;
    }

    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dismiss() {
        mListener = null;
        mDismissed = true;
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


    public CardPicker setCardList(List<T> list) {
        if (!mDismissed) {
            mList = list;
            mGridView.setAdapter(this);
        }
        return this;
    }

    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    public T getItem(int position) {
        return mList != null ? mList.get(position) : null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ItemViewHolder();
            convertView = View.inflate(mContext, R.layout.card_pick_item, null);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.card_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) convertView.getTag();
        }

        T data = getItem(position);
        if (data != null && mListener != null) {
            viewHolder.mTextView.setText(mListener.onGetLabel(data));
            viewHolder.mTextView.setCompoundDrawablesWithIntrinsicBounds(null, mListener.onGetThumbnail(data), null, null);
        } else {
            Log.e(TAG, "resolveInfo null at " + position);
        }
        return convertView;
    }

    public CardPicker setNumColumns(int num) {
        mGridView.setNumColumns(num);
        return this;
    }

    public CardPicker setTitle(String title) {
        mTextView.setText(title);
        return this;
    }
}
