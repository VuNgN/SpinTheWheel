package com.vungn.luckywheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
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
        arrow.setOnClickListener(v -> listener.onTouchTheSpin());
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
            int borderColor = typedArray.getColor(R.styleable.LuckyWheel_border_color, Color.TRANSPARENT);
            int borderWidth = typedArray.getDimensionPixelSize(R.styleable.LuckyWheel_border_width, getDpOf(20));
            int shadowSrc = typedArray.getResourceId(R.styleable.LuckyWheel_shadow_src, 0);
            int arrowImage = typedArray.getResourceId(R.styleable.LuckyWheel_arrow_image, R.drawable.ig_arrow);
            int imagePadding = typedArray.getDimensionPixelSize(R.styleable.LuckyWheel_image_padding, 0);
            int textPadding = typedArray.getDimensionPixelSize(R.styleable.LuckyWheel_text_padding, 0);
            int textSize = typedArray.getDimensionPixelSize(R.styleable.LuckyWheel_text_size, getDpOf(15));
            int textColor = typedArray.getColor(R.styleable.LuckyWheel_text_color, Color.WHITE);
            int fontFamily = typedArray.getResourceId(R.styleable.LuckyWheel_font_family, 0);
            wheelView.setBorder(borderColor);
            wheelView.setPadding(borderWidth);
            wheelView.setShadow(getBitmapFromResource(shadowSrc));
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
     * Function to set background color of wheel
     *
     * @param color background color
     */
    public void setWheelBorder(int color) {
        wheelView.setBorder(color);
    }

    /**
     * Function to set padding of wheel
     *
     * @param padding padding
     */
    public void setWheelPadding(int padding) {
        wheelView.setPadding(padding);
    }

    /**
     * Function to set shadow of wheel by resource
     *
     * @param shadow shadow image resource
     */
    public void setWheelShadow(int shadow) {
        wheelView.setShadow(getBitmapFromResource(shadow));
    }

    /**
     * Function to set shadow of wheel by bitmap
     *
     * @param shadow shadow image bitmap
     */
    public void setWheelShadow(Bitmap shadow) {
        wheelView.setShadow(shadow);
    }

    /**
     * Function to set arrow image of wheel by resource
     *
     * @param arrowImage arrow image
     */
    public void setArrowImage(int arrowImage) {
        arrow.setImageResource(arrowImage);
    }

    /**
     * Function to set arrow image of wheel by drawable
     *
     * @param arrowImage arrow image
     */
    public void setArrowImage(Drawable arrowImage) {
        arrow.setImageDrawable(arrowImage);
    }

    /**
     * Function to set arrow image of wheel by bitmap
     *
     * @param arrowImage arrow image
     */
    public void setArrowImage(Bitmap arrowImage) {
        arrow.setImageBitmap(arrowImage);
    }

    /**
     * Function to set text padding of wheel items
     *
     * @param textPadding text padding
     */
    public void setTextPadding(int textPadding) {
        wheelView.setItemsTextPadding(textPadding);
    }

    /**
     * Function to set text color of wheel items
     *
     * @param textColor text color
     */
    public void setTextColor(int textColor) {
        wheelView.setItemsTextColor(textColor);
    }

    /**
     * Function to set font family of wheel items
     *
     * @param typeface font from resource
     */
    public void setFontFamily(Typeface typeface) {
        wheelView.setItemsFontFamily(typeface);
    }

    /**
     * Function to set text size of wheel items
     *
     * @param textSize text size
     */
    public void setTextSize(int textSize) {
        wheelView.setItemsTextSize(textSize);
    }

    /**
     * Function to set listener of wheel
     *
     * @param listener wheel listener
     */
    public void setListener(WheelListener listener) {
        this.listener = listener;
    }

    /**
     * Function to set spin time of wheel by default configs
     *
     * @param spinTime default spin times
     */
    public void setSpinTime(SpinTime spinTime) {
        wheelView.setRotateTime(spinTime.value);
    }

    /**
     * Function to set spin time of wheel by millisecond
     *
     * @param millisecond millisecond to spin
     */
    public void setSpinTime(int millisecond) {
        wheelView.setRotateTime(millisecond);
    }

    /**
     * Function to set slice repeat of wheel
     *
     * @param sliceRepeat slice repeat
     */
    public void setSliceRepeat(int sliceRepeat) {
        wheelView.setSliceRepeat(sliceRepeat);
    }

    /**
     * Function to reset wheel to zero angle
     */
    public void resetWheel() {
        wheelView.resetRotationLocationToZeroAngle();
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
        wheelView.rotateWheelToTarget(number);
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

    private int getDpOf(int value) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics()));
    }

    private Bitmap getBitmapFromResource(int res) {
        return BitmapFactory.decodeResource(getResources(), res);
    }
}
