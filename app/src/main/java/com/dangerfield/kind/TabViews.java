package com.dangerfield.kind;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by ELIJAH DANGERFIELD on 12/30/2018.
 */
public class TabViews extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ImageView mCenterImage;
    private ImageView mStartImage;
    private ImageView mEndImage;
    private View mIndicator;

    private int mEndViewsTranslationX;
    private int mIndicatorTranslationX;
    private int mCenterTranslationY;

    public TabViews(@NonNull Context context) {
        this(context,null);
    }

    public TabViews(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabViews(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_tabs,this,true);

        mCenterImage = (ImageView) findViewById(R.id.vst_center_image);
        mEndImage = (ImageView) findViewById(R.id.vst_end_image);
        mStartImage = (ImageView) findViewById(R.id.vst_start_image);
        mIndicator =  (View) findViewById(R.id.vst_indicator);

        //this value lets us know how far left and right we want the indicator to move
        mIndicatorTranslationX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,70,getResources().getDisplayMetrics());


        mCenterImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //this says wait until bottom image is drawn, and THEN do our calculations because otherwise it will use the views
                //"creation"/spawn point

                //this says. move the end views as much as we move the indicator (so they meet eachother)
                mEndViewsTranslationX = (int)
                        (((mIndicator.getX()-mStartImage.getX())-mIndicatorTranslationX));

                mCenterImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                //okay so this spicy part animats the center button to move down
                //the number 50 is just because i wanted it to be a little more in line with the endViews
                mCenterTranslationY = -1*(mCenterImage.getBottom() - mEndImage.getBottom()-50);

            }
        });

    }

    public void initViewPager(final ViewPager pager){
        pager.addOnPageChangeListener(this);

        mStartImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pager.getCurrentItem() != 0){
                    pager.setCurrentItem(0);
                }
            }
        });

        mEndImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pager.getCurrentItem() != 2){
                    pager.setCurrentItem(2);
                }
            }
        });

        mCenterImage.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View view) {
                if(pager.getCurrentItem() != 1){
                    pager.setCurrentItem(1);
                }
                Toast.makeText(getContext(),"Make this create a post",Toast.LENGTH_LONG).show();
                /**
                 * likely we will make some class that extends dialog and has its own layout. When the button is
                 * clicked we will show the view and request focus on some plain text view
                 */
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(position==0){

            moveViews(1-Math.abs(positionOffset));
            mIndicator.setTranslationX((Math.abs(positionOffset)-1)*mIndicatorTranslationX);
            moveAndScaleCenter(1-positionOffset);
        }

        else if(position ==1){ //position {0,1,2}
            moveViews(positionOffset);
            mIndicator.setTranslationX(positionOffset*mIndicatorTranslationX);
            moveAndScaleCenter(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void moveAndScaleCenter(float fractionFromCenter) {

        float scale = .7f + ((1-fractionFromCenter) *.3f);
        mCenterImage.setScaleX(scale);
        mCenterImage.setScaleY(scale);
        mCenterImage.setTranslationY(fractionFromCenter * mCenterTranslationY);
    }

    private void moveViews(float fractionFromCenter) {

        mStartImage.setTranslationX(fractionFromCenter *mEndViewsTranslationX);
        mEndImage.setTranslationX(-fractionFromCenter*mEndViewsTranslationX);
        mIndicator.setAlpha(fractionFromCenter);
        mIndicator.setScaleX(fractionFromCenter);
        mIndicator.setScaleY(fractionFromCenter);

    }
}