package com.suwonsmartapp.hello.graphic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by junsuk on 15. 4. 6..
 */
public class CustomView extends View {
    // 선을 그릴 페인트
    private Paint mPaint;

    // 최종 결과물이 그려지는 곳
    private Bitmap mBitmap;

    // mBitmap 과 연결 되는 캔버스
    private Canvas mCanvas;

    // xml 에서 생성시 호출 되는 생성자
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Toast.makeText(context, "CustomView(Context context, AttributeSet attrs, int defStyleAttr)", Toast.LENGTH_SHORT).show();

        mPaint = new Paint();
        mPaint.setStrokeWidth(3);                   // 굵기
        mPaint.setColor(Color.BLUE);                // 색상
        mPaint.setStyle(Paint.Style.STROKE);        // 선

        mBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Toast.makeText(context, "CustomView(Context context, AttributeSet attrs)", Toast.LENGTH_SHORT).show();
    }

    // 코드 상에서 생성시 호출 되는 생성자
    // CustomView view = new CustomView(context);
    public CustomView(Context context) {
        this(context, null);
        Toast.makeText(context, "CustomView(Context context)", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//
//        canvas.drawRect(100, 100, 300, 500, paint);
//
//        Paint paint2 = new Paint();
//        paint2.setColor(Color.BLUE);
//        paint2.setStrokeWidth(10);
//        paint2.setAlpha(100);
//
//        canvas.drawLine(50, 50, 500, 50, paint2);

        // 최종 결과물 mBitmap을 CustomView 의 표면에 실제로 그림
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    // 두 점의 좌표
    private float mX1, mY1, mX2, mY2;

    // 선을 그릴 Path 객체
    private Path mPath;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();
                mX1 = event.getX();
                mY1 = event.getY();

                mPath.moveTo(mX1, mY1);
                break;
            case MotionEvent.ACTION_MOVE:
                mX2 = event.getX();
                mY2 = event.getY();

                // 두 점을 선으로 연결
//                mPath.lineTo(mX2, mY2);

                // 두 점을 베지어 곡선 알고리즘 적용한 선으로 연결
                mPath.quadTo(mX1, mY1, mX2, mY2);

                mCanvas.drawPath(mPath, mPaint);

                // 첫 번째 좌표를 갱신
                mX1 = mX2;
                mY1 = mY2;

                break;
            case MotionEvent.ACTION_UP:
                mPath.setLastPoint(event.getX(), event.getY());
                break;
        }

        // onDraw 를 호출, 화면 갱신
        invalidate();

        // 처리 완료 되었다 : true
        // 여기서 처리 안 했다 : false
        // Action_Down 에서 터치를 받을 객체의 레퍼런스를 얻는다
        // Action_Move 가 이 객체로 바로 들어오도록
        return true;
    }

    // 크기를 결정
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // 레이아웃 (위치) 결정
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
