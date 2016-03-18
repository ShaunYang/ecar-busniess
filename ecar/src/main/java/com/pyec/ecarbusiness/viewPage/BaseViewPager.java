package com.pyec.ecarbusiness.viewPage;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义高度的viewpapger
 */
public class BaseViewPager extends ViewPager {
	private boolean scrollable = true;
	private Context mContext;

	public BaseViewPager(Context context) {
		super(context);
		mContext = context;
	}

	public BaseViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	/**
	 * 设置viewpager是否可以滚动
	 * 
	 * @param enable
	 */
	public void setScrollable(boolean enable) {
		scrollable = enable;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (scrollable) {
			return super.onInterceptTouchEvent(event);
		} else {
			return false;
		}
	}
	
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int w = child.getMeasuredWidth();
            child.measure(heightMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            height = (int)( w * (576.0/1024.0));
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}