package com.vungn.luckywheel;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Vũ Nguyễn on 10/01/2025.
 */

public class WheelItem implements Parcelable {
    private int backgroundColor;
    private int textColor;
    private Bitmap iconBitmap;
    private String text;
    private int probability;

    public WheelItem(int backgroundColor, int textColor, String text) {
        this(backgroundColor, textColor, text, 1);
    }

    public WheelItem(int backgroundColor, int textColor, String text, int probability) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.text = text;
        this.probability = probability;
    }

    public WheelItem(int backgroundColor, int textColor, Bitmap iconBitmap, String text) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.iconBitmap = iconBitmap;
        this.text = text;
    }

    protected WheelItem(Parcel in) {
        backgroundColor = in.readInt();
        textColor = in.readInt();
        iconBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        text = in.readString();
        probability = in.readInt();
    }

    public static final Creator<WheelItem> CREATOR = new Creator<WheelItem>() {
        @Override
        public WheelItem createFromParcel(Parcel in) {
            return new WheelItem(in);
        }

        @Override
        public WheelItem[] newArray(int size) {
            return new WheelItem[size];
        }
    };

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }

    public String getText() {
        return text;
    }

    public int getProbability() {
        return probability;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.iconBitmap = iconBitmap;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof WheelItem) {
            WheelItem item = (WheelItem) obj;
            return item.getBackgroundColor() == backgroundColor && item.getTextColor() == textColor && item.getText().equals(text) && item.getProbability() == probability;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(backgroundColor);
        dest.writeInt(textColor);
        dest.writeString(text);
        dest.writeInt(probability);
    }
}
