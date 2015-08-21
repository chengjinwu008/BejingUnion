package com.cjq.bejingunion.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/13.
 */
public class RightSlideMenu extends FrameLayout {
    private View content;
    private View menu;
    private boolean first = true;
    private boolean menuDrewOut = false;
    private boolean isInAnimation = false;
    private int animationTime = 500;

    public RightSlideMenu(Context context) {
        this(context, null);
    }

    public RightSlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightSlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.RightSlideMenu);
        int id = ta.getResourceId(R.styleable.RightSlideMenu_menu_content_layout, R.layout.sliding_menu);

        ta.recycle();
        View view = LayoutInflater.from(context).inflate(id, null, false);
        content = view.findViewById(R.id.sliding_content);
        menu = view.findViewById(R.id.sliding_menu);
        addView(view);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int width = menu.getMeasuredWidth();
        final int xwidth = getMeasuredWidth();
        if (menuDrewOut) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                int x = (int) ev.getX();
                if (x > xwidth - width) {
                    return super.onInterceptTouchEvent(ev);
                } else {
                    animateMenu();
                }
            }else{
                return super.onInterceptTouchEvent(ev);
            }
        }else{
            return super.onInterceptTouchEvent(ev);
        }
        return true;
    }

    public void animateMenu() {
        if (!isInAnimation)
            if (menuDrewOut) {
                pullMenuBack();
            } else {
                drawMenuOut();
            }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed && first) {
            menu.setX(content.getWidth());
            first = false;
        }
    }

    private void drawMenuOut() {
        final int width = menu.getMeasuredWidth();
        final int xwidth = getMeasuredWidth();

        ObjectAnimator animator = ObjectAnimator.ofFloat(menu, "x", xwidth, xwidth - width);
        animator.setDuration(animationTime);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isInAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                menuDrewOut = true;
                isInAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isInAnimation = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isInAnimation = true;
            }
        });
        animator.start();
    }

    private void pullMenuBack() {
        final int width = menu.getMeasuredWidth();
        final int xwidth = getMeasuredWidth();

        ObjectAnimator animator = ObjectAnimator.ofFloat(menu, "x", xwidth - width, xwidth);
        animator.setDuration(animationTime);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isInAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                menuDrewOut = false;
                isInAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isInAnimation = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isInAnimation = true;
            }
        });
        animator.start();
    }

}
