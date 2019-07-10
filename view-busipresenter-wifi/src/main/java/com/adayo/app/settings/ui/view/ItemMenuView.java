package com.adayo.app.settings.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adayo.app.settings.R;



/**
 * @author damanz
 * @className ItemMenuView
 * @date 2018-07-27.
 */
public class ItemMenuView extends RelativeLayout implements View.OnClickListener {

    public static final String TAG =  "ItemMenuView";

    private CharSequence mLeftText;
    private int mTextSize;
    private int mTextColor;
    private TextView mRightTextView;
    private RelativeLayout mSwitchContainer;
    private boolean mShowSwitchButton;
    private SwitchButton mSwitchButtonView;
    private boolean mSwitchButtonState = false;
    private int mSwitchButtonWidth;

    private OnClickListener mClickListener;
    //private long lastClickTime;
    private boolean mAutoSwitchState = true;


    public void setOnItemClickListener(OnClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public ItemMenuView(Context context) {
        this(context, null);
    }

    public ItemMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemMenuView);
        mShowSwitchButton = typedArray.getBoolean(R.styleable.ItemMenuView_showSwitchButton, false);
        mSwitchButtonState = typedArray.getBoolean(R.styleable.ItemMenuView_switchButtonState, false);
        mSwitchButtonWidth = (int) typedArray.getDimension(R.styleable.ItemMenuView_switchButtonWidth, 38);

        mLeftText = typedArray.getText(R.styleable.ItemMenuView_text);
        mTextSize = (int) typedArray.getDimension(R.styleable.ItemMenuView_textSize, 26);
        mTextColor = typedArray.getColor(R.styleable.ItemMenuView_textColor, Color.parseColor("#000000"));

        typedArray.recycle();

        initView();
        initViewData();
        initListener();
    }

    private void initListener() {
        if (mSwitchContainer != null) {
            mSwitchContainer.setOnClickListener(this);
        }
        if (mSwitchButtonView != null) {
            mSwitchButtonView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mSwitchButtonState = isChecked;
                }
            });
        }
    }

    private void initViewData() {
        mRightTextView.setText(mLeftText);
        mRightTextView.setTextColor(mTextColor);

        if (mSwitchButtonView != null) {
            mSwitchButtonView.setChecked(mSwitchButtonState);
            mSwitchButtonView.setClickable(false);
            mSwitchButtonView.setFocusable(false);
        }

    }

    private void initView() {
        setGravity(Gravity.CENTER_VERTICAL);
        setBackgroundColor(Color.TRANSPARENT);
        setSoundEffectsEnabled(true);
        mRightTextView = new TextView(getContext());
        LayoutParams leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);//与父容器的左侧对齐
        mRightTextView.setGravity(Gravity.CENTER_VERTICAL);
        mRightTextView.setPadding(193, 0, 0, 0);
        mRightTextView.setId(R.id.item_menu_view_left_text);
        mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        addView(mRightTextView, leftParams);
        mSwitchContainer = new RelativeLayout(getContext());
        mSwitchContainer.setGravity(Gravity.CENTER);
        LayoutParams containerParams = new LayoutParams(100, 60);
        containerParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        containerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        containerParams.setMargins(45, 0, 0, 0);
        addView(mSwitchContainer, containerParams);
        mRightTextView.setVisibility(VISIBLE);
        mSwitchButtonView = new SwitchButton(getContext());
        mSwitchButtonView.setGravity(Gravity.CENTER);
        mSwitchButtonView.setThumbDrawableRes(R.drawable.miui_thumb_drawable);
        mSwitchButtonView.setBackDrawableRes(R.drawable.miui_back_drawable);
        mSwitchButtonView.setThumbSize(60, 60);
        mSwitchButtonView.setThumbRangeRatio(1.6f);
        LayoutParams switchParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        switchParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        switchParams.setMargins(0, 0, 0, 0);
        mSwitchContainer.addView(mSwitchButtonView, switchParams);
    }


    public void setSwitchButtonState(boolean mSwitchButtonState) {
        if (mSwitchButtonView != null) {
            mSwitchButtonView.setChecked(mSwitchButtonState);
        }
    }

    public boolean isSwitchButtonState() {
        return mSwitchButtonState;
    }


    @Override
    public void onClick(View v) {
        long l = System.currentTimeMillis();
//        if (l - lastClickTime > 500) {
//        lastClickTime = l;
        if (mAutoSwitchState && mShowSwitchButton) {
            mSwitchButtonState = !mSwitchButtonState;
            mSwitchButtonView.setChecked(mSwitchButtonState);
        }

        if (mClickListener != null) {
            mClickListener.onClick(v);
        }

    }

    public void setAutoSwitchState(boolean mAutoSwitchState) {
        this.mAutoSwitchState = mAutoSwitchState;
    }

    public void setSwitchBg() {
        if (mSwitchButtonView != null) {
            mSwitchButtonView.setThumbDrawableRes(R.drawable.miui_thumb_drawable);
            mSwitchButtonView.setBackDrawableRes(R.drawable.miui_back_drawable);
            mRightTextView.setTextColor(getResources().getColor(R.color.common_title_color));
        }
    }

    /**
     * 中英文切换的时候调用
     *
     * @param text
     */
    public void setText(String text) {
        if (mRightTextView != null) {
            mRightTextView.setText(text);
        }
    }

}
