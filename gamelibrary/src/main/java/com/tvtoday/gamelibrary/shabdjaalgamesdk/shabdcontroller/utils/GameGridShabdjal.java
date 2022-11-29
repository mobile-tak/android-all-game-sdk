package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

public class GameGridShabdjal extends View {
    private static final String TAG = "DrawView";

    private static final float MINP = 0.25f;
    private static final float MAXP = 0.75f;

    private Context context;
    private Paint mPaint = new Paint();
    private int[] pencolor = { Color.BLUE, Color.GREEN, Color.MAGENTA,
            Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
            Color.LTGRAY, Color.YELLOW };

    private Canvas  mCanvas;
    private Path    mPath;

    private ArrayList<Path> paths = new ArrayList<Path>();


    public void resetPenColor()
    {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        int color_index = random.nextInt(pencolor.length);
        //   Toast.makeText(context, "Pen Color "+color_index, Toast.LENGTH_SHORT).show();
        mPaint.setColor(pencolor[color_index]);
    }

    private void init(Context context) {
        this.context = context;
        setFocusable(true);
        setFocusableInTouchMode(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6);
        mCanvas = new Canvas();
        mPath = new Path();
        paths.add(mPath);
    }

    public GameGridShabdjal(Context context)
    {
        super(context);
        init(context);
    }

    public GameGridShabdjal(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);

    }

    public GameGridShabdjal(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (Path p : paths){
            canvas.drawPath(p, mPaint);
            Log.v("draw", "---line");
        }
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        mPath = new Path();
        paths.add(mPath);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                resetPenColor();
                //  Toast.makeText(context, "Multitouch", Toast.LENGTH_SHORT).show();
                invalidate();
                break;
        }
        return true;
    }
    }