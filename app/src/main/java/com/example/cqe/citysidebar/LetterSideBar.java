package com.example.cqe.citysidebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选工具条
 * Author: CQE
 * Date: 2018/1/25.
 */

public class LetterSideBar extends View {

    private Context context;
    private Paint mPaint; // 画笔
    private int mWidth;   // 总宽
    private int mHeight;  // 总长
    private int mEachLetterHeight; // 每个字母的高度
    private int mEachLetterWidth; // ，每个字母的宽度
    private boolean maskShown; // 是否显示背景
    private int mSelectedIndex = -1; // 已选择的字母
    private TextView mOverLayTextView; // 显示的字母

    private OnTouchLetterListener mOnTouchLetterListener;


    private List<String> mLetters = new ArrayList<>(); // 字母列表


    public LetterSideBar(Context context) {
        super(context);
        this.context = context;
        mPaint = new Paint();
        initLetters();
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mPaint = new Paint();
        initLetters();
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPaint = new Paint();
        initLetters();
    }

    private void initLetters(){
        //初始化字母列表
        for (int i=0; i<26; i++){
            char letter = (char) ('A'+ i);
            Log.e("LetterSideBar","letter = " + letter);
            mLetters.add(String.valueOf(letter));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setTextSize(getResources().getDimension(R.dimen.side_bar_letter)); // 设置字母字体大小
        if (mLetters.size() > 0){
            mEachLetterHeight = mHeight / mLetters.size();
            mEachLetterWidth = (int) (mWidth / 2 - mPaint.measureText(mLetters.get(0)) / 2);
        }else {
            mEachLetterHeight = mHeight;
        }

        //绘制所有显示的字母
        for (int i=0; i<mLetters.size(); i++){
            mPaint.setColor(ContextCompat.getColor(context,R.color.colorPrimary)); //设置字母颜色
            if (mSelectedIndex == i && mSelectedIndex != -1) {
                mPaint.setColor(Color.YELLOW); // 设置选中时的颜色
            }
            canvas.drawText(mLetters.get(i),mEachLetterWidth,i * mEachLetterHeight + mEachLetterHeight, mPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float y = event.getY();
        // 获取点击的字母
        int letterIndex = (int) (( y / mHeight ) * mLetters.size());
        switch (action){
            // 选中字母高亮
            case MotionEvent.ACTION_DOWN:
                Log.e("LetterSideBar","Letter is : " + mLetters.get(letterIndex));
                selectedLetter(letterIndex);
                break;
            // 在字母范围内,仍视为选中字母
            case MotionEvent.ACTION_MOVE:
                selectedLetter(letterIndex);
                break;
            case MotionEvent.ACTION_UP:
                mSelectedIndex = -1;
                invalidate();
                hideOverLayText();
                break;
        }

        return true;
    }

    public void setOnTouchLetterListener(OnTouchLetterListener mOnTouchLetterListener) {
        this.mOnTouchLetterListener = mOnTouchLetterListener;
    }

    public void setLetterList(List<String> mLetters) {
        this.mLetters = mLetters;
    }

    /**
     * 高亮显示选中的字母,并显示mask
     */
    private void selectedLetter(int letterIndex) {
        if (letterIndex >= 0 && letterIndex < mLetters.size()) {
            // 显示遮罩
            maskShown = true;
            if (mOnTouchLetterListener != null) {
                mOnTouchLetterListener.onLetterSelected(mLetters.get(letterIndex));
            }
            mSelectedIndex = letterIndex;
            // 通知重绘
            invalidate();
            // 显示字母
            showOverLayText();
        }
    }

    /**
     * 显示选择的字母
     */
    public void showOverLayText() {
        if (mOverLayTextView != null) {
            mOverLayTextView.setVisibility(View.VISIBLE);
            mOverLayTextView.setText(mLetters.get(mSelectedIndex));
        }
    }

    public void hideOverLayText() {
        if (mOverLayTextView != null) {
            mOverLayTextView.setVisibility(View.GONE);
        }
    }

    public TextView getOverLayTextView() {
        return mOverLayTextView;
    }

    public void setOverLayTextView(TextView overLayTextView) {
        this.mOverLayTextView = overLayTextView;
    }

    /**
     * 通过此接口设置recyclerView的position
     */
    public interface OnTouchLetterListener {
        void onLetterSelected(String letter);
    }
}
