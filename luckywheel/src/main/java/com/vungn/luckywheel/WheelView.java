package com.vungn.luckywheel;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Created by Vũ Nguyễn on 10/01/2025.
 */

final class WheelView extends View {
    private static final String TAG = "WheelView";
    public static final int DEFAULT_TEXT_SIZE = 60;
    private static final int UNAVAILABLE_ITEM_ALPHA = 51; // 20% transparent
    private RectF range = new RectF();
    private Paint archPaint, textPaint, backgroundPaint;
    private int textSize = DEFAULT_TEXT_SIZE;
    private int rotateTime = SpinTime.X3.value;
    private int padding, radius, center, wheelBorderColor, imagePadding, textPadding = 0;
    @Nullable
    private Bitmap shadow;
    @Nullable
    private Typeface fontFamily;
    private List<WheelItem> wheelItems;
    private List<WheelItem> wheelItemsOriginal;
    @Nullable
    private OnLuckyWheelReachTheTarget onLuckyWheelReachTheTarget;
    @Nullable
    private OnRotationListener onRotationListener;
    @Nullable
    private OnSliceClick onSliceClick;
    private WheelMode wheelMode = WheelMode.NORMAL;

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initComponents() {
        //arc paint object
        archPaint = new Paint();
        archPaint.setAntiAlias(true);
        archPaint.setDither(true);
        //text paint object
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(fontFamily);
        //background paint object
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setDither(true);

        //rect rang of the arc
        range = new RectF(padding, padding, padding + radius, padding + radius);
    }

    /**
     * Get the angele of the target
     *
     * @return Number of angle
     */
    private float getAngleOfIndexTarget(int target) {
        return ((float) 360 / wheelItems.size()) * (target + 1);
    }

    /**
     * Function to get wheel mode
     *
     * @return Wheel mode
     */
    public WheelMode getWheelMode() {
        return wheelMode;
    }

    /**
     * Function to set wheel mode
     *
     * @param wheelMode Wheel mode
     */
    public void setWheelMode(WheelMode wheelMode) {
        this.wheelMode = wheelMode;
    }

    /**
     * Function to set wheel background color
     *
     * @param color Wheel background color
     */
    public void setWheelBackgroundColor(int color) {
        this.wheelBorderColor = color;
        invalidate();
    }

    /**
     * Function to set padding of the wheel
     *
     * @param padding Padding of the wheel
     */
    public void setPadding(int padding) {
        this.padding = padding;
        invalidate();
    }

    /**
     * Function to set shadow of the wheel
     *
     * @param shadow Shadow of the wheel
     */
    public void setShadow(Bitmap shadow) {
        this.shadow = shadow;
        invalidate();
    }

    /**
     * Function to set items image padding
     *
     * @param imagePadding Image padding
     */
    public void setItemsImagePadding(int imagePadding) {
        this.imagePadding = imagePadding;
        invalidate();
    }

    /**
     * Function to set items text padding from edge of the arc
     *
     * @param textPadding Text padding
     */
    public void setItemsTextPadding(int textPadding) {
        this.textPadding = textPadding;
        invalidate();
    }

    /**
     * Function to set items text size
     *
     * @param textSize Text size
     */
    public void setItemsTextSize(int textSize) {
        this.textSize = textSize;
        postInvalidate();
    }

    /**
     * Function to set items font family
     *
     * @param fontFamily Font family
     */
    public void setItemsFontFamily(Typeface fontFamily) {
        this.fontFamily = fontFamily;
        postInvalidate();
    }

    /**
     * Function to set rotate time
     *
     * @param time Time to rotate
     */
    public void setRotateTime(int time) {
        this.rotateTime = time;
    }

    /**
     * Function to set slice repeat
     *
     * @param sliceRepeat Slice repeat
     */
    public void setSliceRepeat(int sliceRepeat) {
        boolean isShowUnavailableItems = wheelMode == WheelMode.VISUAL;
        this.wheelItems = WheelUtils.generateListBaseOnSliceRepeat(wheelItemsOriginal, sliceRepeat, isShowUnavailableItems);
        invalidate();
    }

    /**
     * Function to set wheel listener
     *
     * @param onLuckyWheelReachTheTarget target reach listener
     */
    public void setWheelListener(OnLuckyWheelReachTheTarget onLuckyWheelReachTheTarget) {
        this.onLuckyWheelReachTheTarget = onLuckyWheelReachTheTarget;
    }

    /**
     * Function to set rotation listener
     *
     * @param onRotationListener Rotation listener
     */
    public void setOnRotationListener(@Nullable OnRotationListener onRotationListener) {
        this.onRotationListener = onRotationListener;
    }

    /**
     * Function to set slice click listener
     *
     * @param onSliceClick Slice click listener
     */
    public void setOnSliceClick(@Nullable OnSliceClick onSliceClick) {
        this.onSliceClick = onSliceClick;
    }

    /**
     * Function to add wheels items
     *
     * @param wheelItems Wheels model item
     */
    public void addWheelItems(List<WheelItem> wheelItems) {
        boolean isShowUnavailableItems = wheelMode == WheelMode.VISUAL;
        this.wheelItemsOriginal = wheelItems;
        this.wheelItems = WheelUtils.generateListBaseOnSliceRepeat(wheelItems, 1, isShowUnavailableItems);
        invalidate();
    }

    /**
     * Function to draw wheel background
     *
     * @param canvas Canvas of draw
     */
    private void drawWheelBackground(Canvas canvas) {
        backgroundPaint.setColor(wheelBorderColor);
        canvas.drawCircle(center, center, center - padding, backgroundPaint);
    }

    /**
     * Function to draw image in the center of arc
     *
     * @param canvas    Canvas to draw
     * @param tempAngle Temporary angle
     * @param bitmap    Bitmap to draw
     */
    private void drawImage(Canvas canvas, float tempAngle, Bitmap bitmap) {
        //get every arc img width and angle
        int imgWidth = (radius / wheelItems.size()) - imagePadding;
        float angle = (float) ((tempAngle + 360 / wheelItems.size() / 2) * Math.PI / 180);
        //calculate x and y
        int x = (int) (center + radius / 2 / 2 * Math.cos(angle));
        int y = (int) (center + radius / 2 / 2 * Math.sin(angle));
        //create arc to draw
        Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth / 2, y + imgWidth / 2);
        //rotate main bitmap
        float px = rect.exactCenterX();
        float py = rect.exactCenterY();
        Matrix matrix = new Matrix();
        matrix.postTranslate((float) -bitmap.getWidth() / 2, (float) -bitmap.getHeight() / 2);
        matrix.postRotate(tempAngle + 120);
        matrix.postTranslate(px, py);
        canvas.drawBitmap(bitmap, matrix, new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG));
        matrix.reset();
    }

    /**
     * Function to draw text below image
     *
     * @param canvas     Canvas to draw
     * @param tempAngle  Temporary angle
     * @param sweepAngle current index angle
     * @param text       string to show
     * @param textColor  text color
     */
    private void drawText(Canvas canvas, float tempAngle, float sweepAngle, String text, int textColor) {
        canvas.rotate((tempAngle + sweepAngle / 2 + 180), center, center);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        float maxTextWidth = (float) radius / 2 - 20 - textPadding * 2;
        float textWidth = textPaint.measureText(text);
        if (textWidth > maxTextWidth) {
            String ellipsis = "...";
            float ellipsisWidth = textPaint.measureText(ellipsis);
            int endIndex = textPaint.breakText(text, true, maxTextWidth - ellipsisWidth, null);
            text = text.substring(0, endIndex) + ellipsis;
            textWidth = textPaint.measureText(text);
        }
        Path path = new Path();
        RectF bounds = new RectF();
        float rectLeft = padding + textPadding;
        float rectTop = center - textHeight / 2;
        float rectRight = padding + textPadding + textWidth;
        float rectBottom = center + textHeight / 2;
        path.addRect(rectLeft, rectTop, rectRight, rectBottom, Path.Direction.CW);
        path.computeBounds(bounds, true);
        textPaint.setColor(textColor);
        canvas.drawTextOnPath(text, path, 0, textHeight - 10, textPaint);
        canvas.rotate(-(tempAngle + sweepAngle / 2 + 180), center, center);
    }

    /**
     * Function to draw shadow of the inside wheel's edge
     *
     * @param canvas Canvas to draw
     */
    private void drawShadow(Canvas canvas) {
        if (shadow == null) {
            return;
        }
        Rect rect = new Rect(padding, padding, radius + padding, radius + padding);
        Bitmap scaledShadow = Bitmap.createScaledBitmap(shadow, rect.width(), rect.height(), true);
        canvas.drawBitmap(scaledShadow, null, rect, new Paint());
    }

    /**
     * Function to rotate wheel to target
     *
     * @param target target number
     */
    public void rotateWheelToTarget(int target) {
        setRotation(0);
        float targetAngle = getAngleOfIndexTarget(target);
        float sweepAngle = (float) 360 / wheelItems.size();
        float wheelItemCenter = (sweepAngle / 2 + 270) - targetAngle;

        final float startRotation = 0;
        final float endRotation = (360 * 15) + wheelItemCenter;

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(rotateTime);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int lastTargetedIndex = -1;

            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                float currentRotation = startRotation + (endRotation - startRotation) * fraction;
//                Log.d(TAG, "onAnimationUpdate -> current:" + currentRotation);
                setRotation(currentRotation);

                // Calculate the current targeted slice
                int currentTargetedIndex = calculateTargetedSliceIndex(currentRotation);
                Log.d(TAG, "onAnimationUpdate: currentTargetedIndex -> " + currentTargetedIndex);
                if (currentTargetedIndex != lastTargetedIndex && onLuckyWheelReachTheTarget != null) {
                    onLuckyWheelReachTheTarget.onTargetChanged(wheelItems.get(currentTargetedIndex));
                    lastTargetedIndex = currentTargetedIndex;
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (onLuckyWheelReachTheTarget != null) {
                    Log.d(TAG, "onAnimationEnd: " + target);
                    onLuckyWheelReachTheTarget.onReachFinalTarget(wheelItems.get(target));
                }
                if (onRotationListener != null) {
                    onRotationListener.onFinishRotation();
                }
                clearAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    /**
     * Function to reset rotation location to zero angle
     */
    public void resetRotationLocationToZeroAngle() {
        animate().setDuration(0).rotation(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                clearAnimation();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });
    }

    /**
     * Function to cancel animation
     */
    public void cancelAnimation() {
        animate().cancel();
        clearAnimation();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        initComponents();
        drawWheelBackground(canvas);
        if (wheelItems == null || wheelItems.isEmpty()) {
            String message = "Wheel items is null or empty, please add wheel items before draw";
            Log.e(TAG, message);
            return;
        }
        float tempAngle = 0;
        float sweepAngle = (float) 360 / wheelItems.size();
        float currentId = -1;
        int count = 1;
        for (int i = 0; i < wheelItems.size(); i++) {
            if (currentId != wheelItems.get(i).getId()) {
                currentId = wheelItems.get(i).getId();
                count = 1;
            }
            if (wheelItems.get(i).isVisible()) {
                archPaint.setColor(wheelItems.get(i).getBackgroundColor());
            } else {
                archPaint.setColor(setAlpha(wheelItems.get(i).getBackgroundColor(), UNAVAILABLE_ITEM_ALPHA));
            }
            canvas.drawArc(range, tempAngle, sweepAngle + 1, true, archPaint);
            if (count == wheelItems.get(i).getProbability() / 2 + 1) {
                int textColor = wheelItems.get(i).isVisible() ? wheelItems.get(i).getTextColor() : setAlpha(wheelItems.get(i).getTextColor(), UNAVAILABLE_ITEM_ALPHA); // 20% transparent
                float centerAngle = wheelItems.get(i).getProbability() % 2 == 0 ? 0 : sweepAngle;
                drawText(canvas, tempAngle, centerAngle, wheelItems.get(i).getText() == null ? "" : wheelItems.get(i).getText(), textColor);
                count++;
            }
            count++;
            tempAngle += sweepAngle;
        }
        drawShadow(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        radius = width - padding * 2;
        center = width / 2;
        setMeasuredDimension(width, width);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (wheelMode == WheelMode.VISUAL) {
                float x = event.getX();
                float y = event.getY();
                int touchedItemIndex = getTouchedItemIndex(x, y);
                if (touchedItemIndex != -1) {
                    WheelItem touchedItem = wheelItems.get(touchedItemIndex);
                    touchedItem.setVisible(!touchedItem.isVisible());
                    invalidate();
                    if (onSliceClick != null) {
                        onSliceClick.onClick(touchedItem);
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private int getTouchedItemIndex(float x, float y) {
        // Calculate the angle and determine which item was touched
        float angle = (float) (Math.toDegrees(Math.atan2(y - center, x - center)) + 360) % 360;
        float sweepAngle = 360f / wheelItems.size();
        for (int i = 0; i < wheelItems.size(); i++) {
            if (angle >= i * sweepAngle && angle < (i + 1) * sweepAngle) {
                return i;
            }
        }
        return -1;
    }

    private int calculateTargetedSliceIndex(float rotation) {
        float rotatedAngle = (rotation + 360) % 360;
        float sweepAngle = 360f / wheelItems.size();
        float theta = (270 - rotatedAngle + 360) % 360;
        return (int) (theta / sweepAngle);
    }

    private int setAlpha(int color, int alpha) {
        return (color & 0x00FFFFFF) | (alpha << 24);
    }
}
