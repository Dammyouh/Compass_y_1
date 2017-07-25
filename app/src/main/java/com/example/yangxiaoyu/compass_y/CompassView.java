package com.example.yangxiaoyu.compass_y;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yangxiaoyu on 17-7-24.
 */

public class CompassView extends View {
    private static final String TAG = "CompassView";

    private float mDegree = 0f;
    public float getmDegree() {
        return mDegree;
    }
    public void setmDegree(float mDegree) {
        this.mDegree = mDegree;
//        invalidate();
    }

    public CompassView(Context context) {
        super(context);
    }

    public CompassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int px = getMeasuredWidth()/2;
        int py = getMeasuredHeight()/2;
        int radius = Math.min(px,py);
        Log.d(TAG, "onDraw: getMeasuredWidth ="+px);
        Log.d(TAG, "onDraw: getMeasuredHeight = "+py);

        Paint bigCicle = new Paint();
        bigCicle.setColor(Color.blue(1));
        bigCicle.setAlpha(50);

        Paint smallCicle = new Paint();
        smallCicle.setColor(Color.blue(5));
        smallCicle.setAlpha(100);

        Paint TextPaint = new Paint();
        TextPaint.setColor(Color.GREEN);
        TextPaint.setTextSize(50);

        Paint mLinepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinepaint.setColor(Color.DKGRAY);

        Paint mPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerPaint.setColor(Color.RED);
        mPointerPaint.setStrokeWidth(8f);

        Paint mDegreePain = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDegreePain.setColor(Color.YELLOW);
        mDegreePain.setTextSize(30);

        canvas.drawCircle(px,py,radius,bigCicle);
        canvas.drawCircle(px,py,radius/7*6,smallCicle);

        Resources resources = this.getResources();
        Drawable drawable = resources.getDrawable(R.drawable.pic);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        Matrix matrix = new Matrix();
        matrix.postScale(0.1f,-0.1f);
//        matrix.setRotate(90);
        Bitmap newbitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

//        canvas.drawText(String.valueOf((int)mDegree)+"°", px-50*pic, py+50, TextPaint);//指针度数
        canvas.save();

        canvas.rotate(-mDegree, px, py);
        for(int i = 0;i< 24;i++){
            int linePy = py-radius+(radius/20);
            int textPy = linePy +10;
            canvas.drawLine(px, py - radius, px, linePy, mLinepaint);//短刻度
            canvas.save();
            if (i % 6 == 0) {
                String location = null;
                switch (i) {
                    case 0:
                        location = "N";
//                        canvas.drawLine(px, py,radius, textPy+20, mPointerPaint);//指南针
                        canvas.drawBitmap(newbitmap, px-120, py-300, mPointerPaint);

                        Log.d(TAG, "onDraw: "+bitmap);
//                        canvas.drawLine(px, py-50, px, py-50, mPointerPaint);//指针
                        break;
                    case 6:
                        location = "W";
                        break;
                    case 12:
                        location = "S";
                        break;
                    case 18:
                        location = "E";
                        break;
                }

                canvas.drawLine(px, py - radius, px, linePy*2, mLinepaint);//长刻度
                canvas.drawText(location, px-50/2-6, textPy+15, TextPaint);//方位
            }else if(i%3 == 0){
                canvas.drawText(String.valueOf(i*15), px-10, textPy, mDegreePain);//刻度文字
            }
            canvas.restore();
            canvas.rotate(15, px, py);
        }
        canvas.restore();
    }

}
