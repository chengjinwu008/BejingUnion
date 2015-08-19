package com.cjq.bejingunion.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.cjq.bejingunion.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by CJQ on 2015/8/13.
 */
public class SwipeListItemView extends FrameLayout {
    private View content;
    private View menu;
    private boolean first = true;
    private final int VELOCITY = 20;
    private ExecutorService executorService;
    private Handler mHandler=new Handler(Looper.getMainLooper());
    private boolean scrolling = false;

    public SwipeListItemView(Context context) {
        this(context, null);
    }

    public SwipeListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.RightSlideMenu);
        int id = ta.getResourceId(R.styleable.RightSlideMenu_menu_content_layout, R.layout.sliding_menu);
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        ta.recycle();
        View view = LayoutInflater.from(context).inflate(id, null, false);
        content = view.findViewById(R.id.sliding_content);
        menu = view.findViewById(R.id.sliding_menu);
        addView(view);
        executorService = Executors.newSingleThreadExecutor();
    }

//    public void animateMenu() {
//        if (!isInAnimation)
//            if (menuDrewout) {
//                pullMenuBack();
//            } else {
//                drawMenuOut();
//            }
//    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        float x = ev.getX();
//        float y = ev.getY();
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mDownX = x;
//                mDownY = y;
//                isHorizontal = false;
//                isJudged = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
////                if (!isJudged) {
////                    int deltaX = (int) Math.abs(mDownX - x);
////                    int deltaY = (int) Math.abs(mDownY - y);
////
////                    if (deltaX >= mTouchSlop || deltaY >= mTouchSlop) {
////                        if (deltaX > deltaY) {
////                            isHorizontal = true;
////                        } else {
////                            isHorizontal = false;
////                        }
////                        isJudged = true;
////                    }
////                }
//            case MotionEvent.ACTION_UP:
//
//                mSmoothScrollTo(200);
////                mScrollTo(-200);
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        final int width = menu.getMeasuredWidth();
//
//
//        if (isHorizontal) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_MOVE:
//                    if (event.getX() - mDownX < 0) {
//                        mScrollTo((int) (mDownX-event.getX()));
//                    }
//                    break;
//                case MotionEvent.ACTION_UP:
//
//                    break;
//            }
//        }
//        return true;
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed && first){
            menu.setX(content.getWidth());
            first = false;
        }
    }

    public void mScrollTo(int x){
        int xwidth = getMeasuredWidth();
        menu.setX(xwidth+x);
        content.setX(+x);
        System.out.println(x);
    }

    public void mSmoothScrollTo(final int x){
        int xwidth = getMeasuredWidth();
        final int menuTarget = xwidth+x;

        final int dx = content.getX()>x?-VELOCITY:VELOCITY;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                scrolling = true;
                while (Math.abs(menu.getX() - menuTarget) > VELOCITY && Math.abs(menu.getX()+dx - menuTarget)<Math.abs(menu.getX()-menuTarget)) {
                    int xxx = (int) (content.getX() + dx);
                    int mxxx = (int) menu.getX();

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mScrollTo((int) (content.getX() + dx));
                        }
                    });
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollTo(x);
                        scrolling = false;
                    }
                });
            }
        });
    }

    public int getRightEdge(){
        return -menu.getWidth();
    }

    public int getCurrentX(){
        return (int) content.getX();
    }

    public boolean isScrolling(){
        return scrolling;
    }

//    private void drawMenuOut() {
//        final int width = menu.getMeasuredWidth();
//        final int xwidth = getMeasuredWidth();
//
//        ObjectAnimator animator = ObjectAnimator.ofFloat(menu, "x", xwidth, xwidth - width);
//        animator.setDuration(animationTime);
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                isInAnimation = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                menuDrewout = true;
//                isInAnimation = false;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                isInAnimation = false;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                isInAnimation = true;
//            }
//        });
//        animator.start();
//    }
//
//    private void pullMenuBack() {
//        final int width = menu.getMeasuredWidth();
//        final int xwidth = getMeasuredWidth();
//
//        ObjectAnimator animator = ObjectAnimator.ofFloat(menu, "x", xwidth - width, xwidth);
//        animator.setDuration(animationTime);
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                isInAnimation = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                menuDrewout = false;
//                isInAnimation = false;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                isInAnimation = false;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                isInAnimation = true;
//            }
//        });
//        animator.start();
//    }

}
