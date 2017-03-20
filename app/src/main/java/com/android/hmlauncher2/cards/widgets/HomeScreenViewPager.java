package com.android.hmlauncher2.cards.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EdgeEffect;
import android.widget.Scroller;

public class HomeScreenViewPager extends ViewGroup {
    private static final boolean DEBUG = true;
    private static final String TAG = "HomeScreenViewPager";
    private static final int INVALID_POINTER = -1;
    private static final int MIN_DISTANCE_FOR_FLING = 25; // dips
    private static final int DEFAULT_GUTTER_SIZE = 16; // dips
    private static final int SWIPE_VELOCITY_THRESHOLD = 1000;

    // If the pager is at least this close to its final position, complete the scroll
    // on touch down and let the user interact with the content inside instead of
    // "catching" the flinging pager.
    private static final int CLOSE_ENOUGH = 2; // dp

    private int mActivePointerId = INVALID_POINTER;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mMaximumVelocity;
    private int mCurItem = 0;
    private static int sDefaultScreen = 0;
    /**
     * Position of the last motion event.
     */
    private float mLastMotionX;
    private float mLastMotionY;
    private float mInitialMotionX;
    private float mInitialMotionY;

    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private int mDefaultGutterSize;
    private int mGutterSize = -1;
    private int mTouchSlop;
    private int mMinFlingDistance = -1;
    
    private final int mEasterEggSize;

    // Offsets of the first and last items, if known.
    // Set during population, used to determine if we are at the beginning
    // or end of the pager data set during touch scrolling.
    private float mFirstOffset = -Float.MAX_VALUE;
    private float mLastOffset = Float.MAX_VALUE;

    private EdgeEffect mLeftEdge = null;
    private EdgeEffect mRightEdge = null;

    private OnPageChangeListener mOnViewChangeListener;
    //private EasterEggListener mEasterEggListener;
    
    private int mScrollFlag = 0x00;
    private static final int SCROLL_ALLOWED = 0x01;
    private static final int SCROLL_LOOP = 0x02;
    
    public HomeScreenViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeScreenViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        mScroller = new Scroller(context);
        mCurItem = sDefaultScreen;
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        final float density = context.getResources().getDisplayMetrics().density;
        mMinFlingDistance = (int) (MIN_DISTANCE_FOR_FLING * density);
        mDefaultGutterSize = (int) (DEFAULT_GUTTER_SIZE * density);
        mLeftEdge = new EdgeEffect(context);
        mRightEdge = new EdgeEffect(context);
        mEasterEggSize = 0;//getResources().getDimensionPixelSize(R.dimen.hm_easter_egg_width);
    }
    
    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(mode);
        if (mode != OVER_SCROLL_NEVER) {
            setAllowScroll(true);
        }else{
            setAllowScroll(false);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                final int childWidth = childView.getMeasuredWidth();
                childView.layout(childLeft, 0, childLeft + childWidth,
                        childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        /*if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException(TAG + "only run at EXACTLY mode");
        }
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException(TAG + "only run at EXACTLY mode");
        }*/

        // The children are given the same width and height as the scrollLayout
        if (mGutterSize == -1){
            final int measuredWidth = getMeasuredWidth();
            final int maxGutterSize = measuredWidth / 10;
            mGutterSize = Math.min(maxGutterSize, mDefaultGutterSize);
        }
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }

        scrollTo(mCurItem * width, 0);
    }

    /**
     * According to the position of current layout scroll to the destination
     * page.
     */
    public void snapToDestination() {
        final int screenWidth = getWidth();
        final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
        snapToScreen(destScreen);
    }

    public void snapToScreen(int whichScreen) {
        if(!isAllowScroll()) {
            setToScreen(whichScreen);
        }else{
            scrollToScreen(whichScreen);
        }
    }

    /**
     * scroll to specified page with animation
     * */
    public void scrollToScreen(int whichScreen) {
        // get the valid layout page
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        if (getScrollX() != (whichScreen * getWidth())) {
            final int delta = whichScreen * getWidth() - getScrollX();
            mScroller.startScroll(getScrollX(), 0, delta, 0,
                    Math.abs(delta) * 1);
            mCurItem = whichScreen;
            invalidate(); // Redraw the layout

            if (mOnViewChangeListener != null){
                mOnViewChangeListener.onPageChanged(mCurItem);
            }
        }
    }

    /**
     * scroll to specified page without animation
     * */
    public void setToScreen(int whichScreen) {
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        mCurItem = whichScreen;
        scrollTo(whichScreen * getWidth(), 0);

        if (mOnViewChangeListener != null){
            mOnViewChangeListener.onPageChanged(mCurItem);
        }
    }

    public View getCurrentView(){
        return getChildAt(mCurItem);
    }
    
    public int getCurrentItem() {
        return mCurItem;
    }
    
    public void setCurrentItem(int item) {
        setToScreen(item);
    }
    
    public void setCurrentItem(int item, boolean smoothScroll){
        if (smoothScroll){
            scrollToScreen(item);
        }else{
            setToScreen(item);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent " + ev.toString());
        /*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onMotionEvent will be called and we do the actual
         * scrolling there.
         */

        final int action = ev.getAction() & MotionEvent.ACTION_MASK;

        // Always take care of the touch gesture being complete.
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Release the drag.
            mIsBeingDragged = false;
            mIsUnableToDrag = false;
            mActivePointerId = INVALID_POINTER;
            if (mVelocityTracker != null) {
                mVelocityTracker.recycle();
                mVelocityTracker = null;
            }
            return false;
        }

        // Nothing more to do here if we have decided whether or not we
        // are dragging.
        if (action != MotionEvent.ACTION_DOWN) {
            if (mIsBeingDragged) {
                return false;
            }
            if (mIsUnableToDrag) {
                return false;
            }
        }

        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                /*
                 * mIsBeingDragged == false, otherwise the shortcut would have caught it. Check
                 * whether the user has moved far enough from his original down touch.
                 */

                /*
                * Locally do absolute value. mLastMotionY is set to the y value
                * of the down event.
                */
                final int activePointerId = mActivePointerId;
                if (activePointerId == INVALID_POINTER) {
                    // If we don't have a valid id, the touch down wasn't on content.
                    break;
                }

                final int pointerIndex = ev.findPointerIndex(activePointerId);
                final float x = ev.getX(pointerIndex);
                final float dx = x - mLastMotionX;
                final float xDiff = Math.abs(dx);
                final float y = ev.getY(pointerIndex);
                final float yDiff = Math.abs(y - mInitialMotionY);
                
                if (dx != 0 && !isGutterDrag(mLastMotionX, dx) &&
                        canScroll(this, false, (int) dx, (int) x, (int) y)) {
                    // Nested view has scrollable area under this point. Let it be handled there.
                    mLastMotionX = x;
                    mLastMotionY = y;
                    mIsUnableToDrag = true;
                    return false;
                }
                if (xDiff > mTouchSlop && xDiff * 0.5f > yDiff) {
                    if (DEBUG){
                        Log.d(TAG, "Starting drag when Intercept MOVE , this=" + this);
                    }
                    mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    mLastMotionX = dx > 0 ? mInitialMotionX + mTouchSlop :
                            mInitialMotionX - mTouchSlop;
                    mLastMotionY = y;
                } else if (yDiff > mTouchSlop) {
                    // The finger has moved enough in the vertical
                    // direction to be counted as a drag...  abort
                    // any attempt to drag horizontally, to work correctly
                    // with children that have scrolling containers.
                    if (DEBUG){
                        Log.d(TAG, "Starting unable to drag when Intercept MOVE");
                    }
                    mIsUnableToDrag = true;
                }
                if (mIsBeingDragged) {
                    // Scroll to follow the motion event
                    if (performDrag(x)) {
                        postInvalidateOnAnimation();
                    }
                }
                break;
            }

            case MotionEvent.ACTION_DOWN: {
                /*
                 * Remember location of down touch.
                 * ACTION_DOWN always refers to pointer index 0.
                 */
                mLastMotionX = mInitialMotionX = ev.getX();
                mLastMotionY = mInitialMotionY = ev.getY();
                mActivePointerId = ev.getPointerId(0);
                mIsUnableToDrag = false;

                mScroller.computeScrollOffset();
                
                mIsBeingDragged = false;
                //if (mEasterEggListener != null){
                //    mIsBeingDragged = pickEasterEgg(ev); //omg
                //}

                if (DEBUG){
                    Log.d(TAG, "Down at " + mLastMotionX + " , " + mLastMotionY
                        + " mIsBeingDragged=" + mIsBeingDragged
                        + " mIsUnableToDrag=" + mIsUnableToDrag + ", this=" + this);
                }
                break;
            }

            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        /*
         * The only time we want to intercept motion events is if we are in the
         * drag mode.
         */
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onTouchEvent " + ev.toString());
        if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getEdgeFlags() != 0) {
            // Don't handle edge touches immediately -- they may actually belong to one of our
            // descendants.
            return false;
        }

        if (getChildCount() == 0) {
            // Nothing to present or scroll; nothing to touch.
            return false;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();
        boolean needsInvalidate = false;

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mScroller.abortAnimation();
                // Remember where the motion event started
                mLastMotionX = mInitialMotionX = ev.getX();
                mLastMotionY = mInitialMotionY = ev.getY();
                mActivePointerId = ev.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_MOVE:
                if (!mIsBeingDragged) {
                    final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                    final float x = ev.getX(pointerIndex);
                    final float xDiff = Math.abs(x - mLastMotionX);
                    final float y = ev.getY(pointerIndex);
                    final float yDiff = Math.abs(y - mLastMotionY);
                    if (DEBUG){
                        Log.d(TAG, "Moved x to " + x + "," + y + " diff=" + xDiff + "," + yDiff);
                    }
                    if (xDiff > mTouchSlop && xDiff > yDiff) {
                        mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        mLastMotionX = x - mInitialMotionX > 0 ? mInitialMotionX + mTouchSlop :
                                mInitialMotionX - mTouchSlop;
                        mLastMotionY = y;
                        // Disallow Parent Intercept, just in case
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                // Not else! Note that mIsBeingDragged can be set above.
                if (mIsBeingDragged) {
                    // Scroll to follow the motion event
                    final int activePointerIndex = ev.findPointerIndex(
                            mActivePointerId);
                    final float x = ev.getX(activePointerIndex);
                    needsInvalidate |= performDrag(x);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int initialVelocity = (int) velocityTracker.getXVelocity(
                            mActivePointerId);
                    final int width = getClientWidth();
                    final int scrollX = getScrollX();
                    final float pageOffset = ((float) scrollX / width);
                    final int activePointerIndex =
                            ev.findPointerIndex(mActivePointerId);
                    final float x = ev.getX(activePointerIndex);
                    final int totalDelta = (int) (x - mInitialMotionX);
                    int nextPage = determineTargetPage(mCurItem, pageOffset, initialVelocity,
                            totalDelta);
                    if (!isLoopScroll()) {
                        snapToScreen(nextPage);
                    }else{
                        setToScreen(nextPage);
                    }

                    mActivePointerId = INVALID_POINTER;
                    endDrag();
                    mLeftEdge.onRelease();
                    mRightEdge.onRelease();
                    needsInvalidate = mLeftEdge.isFinished() | mRightEdge.isFinished();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged) {
                    snapToScreen(mCurItem);
                    mActivePointerId = INVALID_POINTER;
                    endDrag();
                    mLeftEdge.onRelease();
                    mRightEdge.onRelease();
                    needsInvalidate = mLeftEdge.isFinished() | mRightEdge.isFinished();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                final int index = ev.getActionIndex();
                final float x = ev.getX(index);
                mLastMotionX = x;
                mActivePointerId = ev.getPointerId(index);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                mLastMotionX = ev.getX(ev.findPointerIndex(mActivePointerId));
                break;
        }
        if (needsInvalidate) {
            postInvalidateOnAnimation();
        }
        return true;
    }

    private boolean pickEasterEgg(MotionEvent ev) {
        boolean isHit = true;
        /*CalibrationModel calModel = CalibrationModel.getInstance(getContext());
        boolean porchViewAvailable = calModel.getPorchViewEnabledCal();
        int easterEggDen = porchViewAvailable ? 1 : 0;
        if (mCurItem != easterEggDen) {
            return false;
        }
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        Rect hitRect = new Rect();
        getHitRect(hitRect);
        DevAppScreenEasterTriggerIds hitId;

        if (x > hitRect.left && x < hitRect.left + mEasterEggSize && y > hitRect.top && y < hitRect.top + mEasterEggSize) {
            hitId = DevAppScreenEasterTriggerIds.TRIGGER_ID_0;
        } else if (x > hitRect.right - mEasterEggSize && x < hitRect.right && y > hitRect.top && y < hitRect.top + mEasterEggSize) {
            hitId = DevAppScreenEasterTriggerIds.TRIGGER_ID_1;
        } else if (x > hitRect.right - mEasterEggSize && x < hitRect.right && y > hitRect.bottom - mEasterEggSize && y < hitRect.bottom) {
            hitId = DevAppScreenEasterTriggerIds.TRIGGER_ID_2;
        } else if (x > hitRect.left && x < hitRect.left + mEasterEggSize && y > hitRect.bottom - mEasterEggSize && y < hitRect.bottom) {
            hitId = DevAppScreenEasterTriggerIds.TRIGGER_ID_3;
        } else {
            hitId = DevAppScreenEasterTriggerIds.TRIGGER_ID_COMMON;
            isHit = false;
        }
        
        if (isHit && mEasterEggListener !=  null) {
            mEasterEggListener.onPick(hitId);
        }*/
        return isHit;
    }
    
    private int determineTargetPage(int currentPage, float pageOffset, int velocity, int deltaX) {
        int targetPage;
        boolean isSwipe = Math.abs(velocity) > SWIPE_VELOCITY_THRESHOLD;
        int flingDistance = mMinFlingDistance;
        if (!isSwipe){
            flingDistance = getWidth()/2; // fling distance should more than half-way across the screen if drag
        }

        if (deltaX > flingDistance) {
            targetPage = currentPage - 1;
        } else if (deltaX < -flingDistance) {
            targetPage = currentPage + 1;
        } else {
            targetPage = currentPage;
        }

        final int N = getChildCount();
        if (N > 0) {
            // Only let the user target pages we have items for
            if (!isLoopScroll()) {
                // Only let the user target pages we have items for
                targetPage = Math.max(0, Math.min(targetPage, N - 1));
            }else{
                if (targetPage < 0){
                    targetPage = N - 1;
                }else if (targetPage > N-1){
                    targetPage = 0;
                }
            }
        }
        Log.i(TAG, "determineTargetPage currentPage=" + currentPage + ", pageOffset=" + pageOffset + ", velocity=" + velocity + ", deltax=" + deltaX + ", targetPage=" + targetPage);
        return targetPage;
    }

    private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        final ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    private boolean isGutterDrag(float x, float dx) {
        return (x < mGutterSize && dx > 0) || (x > getWidth() - mGutterSize && dx < 0);
    }

    private int getClientWidth() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * Tests scrollability within child views of v given a delta of dx.
     *
     * @param v View to test for horizontal scrollability
     * @param checkV Whether the view v passed should itself be checked for scrollability (true),
     *               or just its children (false).
     * @param dx Delta scrolled in pixels
     * @param x X coordinate of the active touch point
     * @param y Y coordinate of the active touch point
     * @return true if child views of v can be scrolled by delta of dx.
     */
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            final int count = group.getChildCount();
            // Count backwards - let topmost views consume scroll distance first.
            for (int i = count - 1; i >= 0; i--) {
                final View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() &&
                        y + scrollY >= child.getTop() && y + scrollY < child.getBottom() &&
                        canScroll(child, true, dx, x + scrollX - child.getLeft(),
                                y + scrollY - child.getTop())) {
                    return true;
                }
            }
        }

        return checkV && v.canScrollHorizontally(-dx);
    }


    public boolean canScrollHorizontally(int direction) {
        if (getChildCount() < 1) {
            return false;
        }

        final int width = getClientWidth();
        final int scrollX = getScrollX();
        if (direction < 0) {
            return (scrollX >= (int) (width * mFirstOffset));
        } else if (direction > 0) {
            return (scrollX <= (int) (width * mLastOffset));
        } else {
            return false;
        }
    }

    /*
     * handle scroll effect, if allow loop scroll, then we discard the pull effect when drag at first/last page
     * */
    private boolean performDrag(float x) {
        boolean needsInvalidate = false;
        final float deltaX = mLastMotionX - x;
        mLastMotionX = x;

        float oldScrollX = getScrollX();
        float scrollX = oldScrollX + deltaX;
        final int width = getClientWidth();

        final int N = getChildCount();
        mFirstOffset = mCurItem == 0 ? 0 : -Float.MAX_VALUE;
        mLastOffset = mCurItem == N - 1 ?
                mCurItem : Float.MAX_VALUE;

        float leftBound = width * mFirstOffset;
        float rightBound = width * mLastOffset;

        boolean leftAbsolute = true;
        boolean rightAbsolute = true;

        if (mCurItem != 0) {
            leftAbsolute = false;
        }
        if (mCurItem != N - 1) {
            rightAbsolute = false;
            rightBound = (mCurItem + 1) * width;
        }

        boolean loopScroll = isLoopScroll();
        if (scrollX < leftBound) {
            if (leftAbsolute && !loopScroll) {
                float over = leftBound - scrollX;
                mLeftEdge.onPull(Math.abs(over) / width);
                needsInvalidate = true;
            }
            scrollX = leftBound;
        } else if (scrollX > rightBound) {
            if (rightAbsolute  && !loopScroll) {
                float over = scrollX - rightBound;
                mRightEdge.onPull(Math.abs(over) / width);
                needsInvalidate = true;
            }
            scrollX = rightBound;
        }
        // Don't lose the rounded component
        mLastMotionX += scrollX - (int) scrollX;
        scrollTo((int) scrollX, getScrollY());

        return needsInvalidate || loopScroll;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = ev.getActionIndex();
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionX = ev.getX(newPointerIndex);
            mActivePointerId = ev.getPointerId(newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() {
        mIsBeingDragged = false;
        mIsUnableToDrag = false;

        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mLeftEdge != null) {
            final int scrollX = getScrollX();
            if (!mLeftEdge.isFinished()) {
                final int restoreCount = canvas.save();
                final int height = getHeight() - getPaddingTop() - getPaddingBottom();

                canvas.rotate(270);
                canvas.translate(-height + getPaddingTop(), Math.min(0, scrollX));
                mLeftEdge.setSize(height, getWidth());
                if (mLeftEdge.draw(canvas)) {
                    invalidate();
                }
                canvas.restoreToCount(restoreCount);
            }
            if (!mRightEdge.isFinished()) {
                final int restoreCount = canvas.save();
                final int width = getWidth();
                final int height = getHeight() - getPaddingTop() - getPaddingBottom();

                canvas.rotate(90);
                canvas.translate(-getPaddingTop(),
                        -(Math.max(getScrollRange(), scrollX) + width));
                mRightEdge.setSize(height, width);
                if (mRightEdge.draw(canvas)) {
                    invalidate();
                }
                canvas.restoreToCount(restoreCount);
            }
        }
    }
    
    private int getScrollRange() {
        int scrollRange = 0;
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            scrollRange = Math.max(0,
                    child.getWidth() - (getWidth() - getPaddingLeft() - getPaddingRight()));
        }
        return scrollRange;
    }
    
    private void setAllowScroll(boolean allowScroll) {
        if (allowScroll){
            mScrollFlag |= SCROLL_ALLOWED;
        }else{
            mScrollFlag &= ~SCROLL_ALLOWED;
        }  
    }
    
    public void setScrollCategory(boolean allowLoop) {
        if (allowLoop){
            mScrollFlag |= SCROLL_LOOP;
        }else{
            mScrollFlag &= ~SCROLL_LOOP;
        }  
    }
    
    private boolean isAllowScroll(){
        return (mScrollFlag & SCROLL_ALLOWED) == SCROLL_ALLOWED;
    }
    
    public boolean isLoopScroll(){
        return (mScrollFlag & SCROLL_LOOP) == SCROLL_LOOP;
    }

    public void setOnPageChangeListener(OnPageChangeListener listener)
    {
        mOnViewChangeListener = listener;
    }


    /**
     * Callback interface for responding to changing state of the selected page.
     */
    public interface OnPageChangeListener {
        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param currentPage Position index of the new selected page.
         */
        public void onPageChanged(int currentPage);
    }
    
    /*public void setEasterEggListener(EasterEggListener listener) {
        mEasterEggListener = listener;
    }*/
}