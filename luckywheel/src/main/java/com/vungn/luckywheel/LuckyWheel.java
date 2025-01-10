package com.vungn.luckywheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.List;

/**
 * Created by Vũ Nguyễn on 10/01/2025.
 */

public class LuckyWheel extends FrameLayout implements View.OnTouchListener, OnRotationListener {
    private WheelView wheelView;
    private ImageView arrow;
    private int target = -1;
    private boolean isRotate = false;
    private WheelListener listener;

    public LuckyWheel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponent();
        applyAttribute(attrs);
    }

    public LuckyWheel(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent();
        applyAttribute(attrs);
    }

    private void initComponent() {
        inflate(getContext(), R.layout.lucky_wheel_layout, this);
        setOnTouchListener(this);
        wheelView = findViewById(R.id.wv_main_wheel);
        wheelView.setOnRotationListener(this);
        arrow = findViewById(R.id.iv_arrow);
        arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTouchTheSpin();
            }
        });
    }

    /**
     * Function to add items to wheel items
     *
     * @param wheelItems Wheel items
     */
    public void addWheelItems(List<WheelItem> wheelItems) {
        wheelView.addWheelItems(wheelItems);
    }

    public void applyAttribute(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LuckyWheel, 0, 0);
        try {
            int backgroundColor = typedArray.getColor(R.styleable.LuckyWheel_background_color, Color.GREEN);
            int arrowImage = typedArray.getResourceId(R.styleable.LuckyWheel_arrow_image, R.drawable.ig_arrow);
            int imagePadding = typedArray.getDimensionPixelSize(R.styleable.LuckyWheel_image_padding, 0);
            int textPadding = typedArray.getDimensionPixelSize(R.styleable.LuckyWheel_text_padding, 0);
            int textSize = typedArray.getDimensionPixelSize(R.styleable.LuckyWheel_text_size, 60);
            int textColor = typedArray.getColor(R.styleable.LuckyWheel_text_color, Color.WHITE);
            int fontFamily = typedArray.getResourceId(R.styleable.LuckyWheel_font_family, 0);
            wheelView.setWheelBackgoundWheel(backgroundColor);
            wheelView.setItemsImagePadding(imagePadding);
            wheelView.setItemsTextPadding(textPadding);
            wheelView.setItemsTextSize(textSize);
            wheelView.setItemsTextColor(textColor);
            wheelView.setItemsFontFamily(ResourcesCompat.getFont(getContext(), fontFamily));
            arrow.setImageResource(arrowImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        typedArray.recycle();
    }

    /**
     * Function to set lucky wheel reach the target listener
     *
     * @param onLuckyWheelReachTheTarget Lucky wheel listener
     */
    public void setLuckyWheelReachTheTarget(OnLuckyWheelReachTheTarget onLuckyWheelReachTheTarget) {
        wheelView.setWheelListener(onLuckyWheelReachTheTarget);
    }

    /**
     * @param target target to rotate before swipe
     */
    public void setTarget(int target) {
        this.target = target;
    }

    public void setFontFamily(Typeface typeface) {
        wheelView.setItemsFontFamily(typeface);
    }

    public void setTextSize(int textSize) {
        wheelView.setItemsTextSize(textSize);
    }

    public void setListener(WheelListener listener) {
        this.listener = listener;
    }

    public void setSpinTime(SpinTime spinTime) {
        wheelView.setRotateTime(spinTime.value);
    }

    private void setSpinTime(int millisecond) {
        wheelView.setRotateTime(millisecond);
    }

    /**
     * Function to rotate wheel to degree
     *
     * @param number Number to rotate
     */
    public void rotateWheelTo(int number) {
        if (isRotate) {
            return;
        }
        isRotate = true;
        wheelView.resetRotationLocationToZeroAngle(number);
    }

    final int SWIPE_DISTANCE_THRESHOLD = 100;
    float x1, x2, y1, y2, dx, dy;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isRotate) {
            return false;
        }

        switch (event.getAction()) {
            case (MotionEvent.ACTION_DOWN):
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                dx = x2 - x1;
                dy = y2 - y1;
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx < 0 && Math.abs(dx) > SWIPE_DISTANCE_THRESHOLD)
                        listener.onSlideTheWheel();
                } else {
                    if (dy > 0 && Math.abs(dy) > SWIPE_DISTANCE_THRESHOLD)
                        listener.onSlideTheWheel();
                }
                break;
            default:
                return true;
        }
        return true;
    }

    @Override
    public void onFinishRotation() {
        isRotate = false;
    }
}
