package com.adayo.app.settings.ui.view.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.adayo.app.settings.R;


public class SwitchButton extends View implements View.OnTouchListener  {
    //背景图片
    private Bitmap bgBitmap;
    //按钮图片
    private Bitmap btnBitmap;
    private Paint paint;
    private int leftDis = 0;
    //标记最大滑动
    private int slidingMax;
    //标记按钮开关状态
    public boolean mCurrent;
    //标记是否点击事件
    private boolean isClickable;
    //标记是否移动
    private boolean isMove;
    //"开"事件监听器
    private SoftFloorListener softFloorListener;
    //"关"事件监听器
    private HydropowerListener hydropowerListener;
    //标记开关文本的宽度
    float width1, width2;
    //记录文本中心点 cx1:绘制文本1的x坐标  cx2:绘制文本2的x坐标
    //cy记录绘制文本的高度
    float cx1, cy, cx2;
    //定义"开"文本
    String textOn;
    //定义"关"文本
    String textOff;
    //定义文本大小
    float textSize;
    private AttributeSet mAttrs;
    private Context mContext;
    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("TAG", "SwitchButton: 构造方法--attrs="+attrs+"--defStyleAttr="+defStyleAttr );
        mAttrs = attrs;
        mContext = context;
        initData(context, attrs);
        initView();
    }
    private void initData(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton1);
        Drawable bg_Drawable = a.getDrawable(R.styleable.SwitchButton1_bg_bitmap);
        Drawable btn_Drawable = a.getDrawable(R.styleable.SwitchButton1_btn_bitmap);
        textOn = a.getString(R.styleable.SwitchButton1_textOn);
        textOff = a.getString(R.styleable.SwitchButton1_textOff);
        textSize = a.getDimension(R.styleable.SwitchButton1_textSize_ab, 22);
        a.recycle();
        Log.d("TAG", "initData: textOn=="+textOn+"--textOff=="+textOff );
        bgBitmap = ((BitmapDrawable) bg_Drawable).getBitmap();
        bgBitmap = getNewBitmap(bgBitmap,306,54);
        btnBitmap = ((BitmapDrawable) btn_Drawable).getBitmap();
        btnBitmap = getNewBitmap(btnBitmap,150,54);
    }
    private void initData1() {
        textOn = mContext.getResources().getString(R.string.string13);
        textOff = mContext.getResources().getString(R.string.string14);
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.setting_button_bg_p);
        bgBitmap = ((BitmapDrawable) drawable).getBitmap();
        bgBitmap = getNewBitmap(bgBitmap,306,54);
        Drawable drawable1 = mContext.getResources().getDrawable(R.drawable.setting_button_bg_n);
        btnBitmap = ((BitmapDrawable) drawable1).getBitmap();
        btnBitmap = getNewBitmap(btnBitmap,150,54);

    }

    public Bitmap getNewBitmap(Bitmap bitmap, int newWidth , int newHeight){
        // 获得图片的宽高.
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newBitmap;
    }
    private void initView() {
        paint = new Paint();
        slidingMax = bgBitmap.getWidth() - btnBitmap.getWidth();
        paint.setTextSize(textSize);
        width1 = paint.measureText(textOn);
        cx1 = btnBitmap.getWidth() / 2 - width1 / 2;

        //测量绘制文本高度
        Paint.FontMetrics fontMetrics=paint.getFontMetrics();
        float fontHeight=fontMetrics.bottom-fontMetrics.top;
        cy = btnBitmap.getHeight() -(btnBitmap.getHeight()-fontHeight)/2-fontMetrics.bottom;
        width2 = paint.measureText(textOff);
        cx2 = (bgBitmap.getWidth() * 2 - btnBitmap.getWidth()) / 2 - width2 / 2;
        paint.setAntiAlias(true);
        setOnTouchListener(this);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(bgBitmap.getWidth(), bgBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bgBitmap, 0, 0, paint);
        canvas.drawBitmap(btnBitmap, leftDis, 0, paint);
        Log.d("TAG", "onDraw: textOff="+textOff+"--textOn="+textOn );
        if (mCurrent) {
            paint.setColor(getResources().getColor(R.color.switch_select_color));
            canvas.drawText(textOff, cx2, cy, paint);
            paint.setColor(getResources().getColor(R.color.switch_unselect_color));
            canvas.drawText(textOn, cx1, cy, paint);
        } else {
            paint.setColor(getResources().getColor(R.color.switch_select_color));
            canvas.drawText(textOn, cx1, cy, paint);
            paint.setColor(getResources().getColor(R.color.switch_unselect_color));
            canvas.drawText(textOff, cx2, cy, paint);
        }


    }

    public void setIsRight(boolean isRight){
        mCurrent = isRight;
    }
    //刷新视图
    public void flush(Context context) {
        if (mCurrent) {
            leftDis = slidingMax;
        } else {
            leftDis = 0;
        }
        Log.d("TAG", "flush: mContext=="+mContext+"--mAttrs="+mAttrs );
        initData1();
        initView();
        invalidate();
//        requestLayout();
    }
    //刷新视图
    private void flushView() {
        mCurrent = !mCurrent;
        if (mCurrent) {
            leftDis = slidingMax;
            if (hydropowerListener != null) {
                hydropowerListener.hydropower();
            }
        } else {
            leftDis = 0;
            if (softFloorListener != null) {
                softFloorListener.softFloor();
            }
        }
//        System.out.println("mCurrent:="+mCurrent);
        invalidate();
    }

    //startX 标记按下的X坐标,  lastX标记移动后的X坐标 ,disX移动的距离
    float startX, lastX, disX;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isClickable = true;
                startX = event.getX();
                isMove = false;
                break;
            case MotionEvent.ACTION_MOVE:
                lastX = event.getX();
                disX = lastX - startX;
                if (Math.abs(disX) < 5) break;
                isMove = true;
                isClickable = false;
                moveBtn();
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (isClickable) {
                    flushView();
                }
                if (isMove) {
                    if (leftDis > slidingMax / 2) {
                        mCurrent = false;
                    } else {
                        mCurrent = true;
                    }
                    flushView();
                }
                break;
        }

        return true;
    }


    //移动后判断位置
    private void moveBtn() {
        leftDis += disX;
        if (leftDis > slidingMax) {
            leftDis = slidingMax;
        } else if (leftDis < 0) {
            leftDis = 0;
        }
        invalidate();
    }


    //设置左边按钮点击事件监听器
    public void setSoftFloorListener(SoftFloorListener softFloorListener) {
        this.softFloorListener = softFloorListener;
    }

    //设置右边按钮点击事件监听器
    public void setHydropowerListener(HydropowerListener hydropowerListener) {
        this.hydropowerListener = hydropowerListener;
    }

    //开点击事件
    public interface SoftFloorListener {
        void softFloor();
    }

    //关点击事件
    public interface HydropowerListener {
        void hydropower();
    }
}
