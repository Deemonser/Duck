package cn.deemons.library.shape;

import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.graphics.drawable.GradientDrawable.LINE;
import static android.graphics.drawable.GradientDrawable.OVAL;
import static android.graphics.drawable.GradientDrawable.RECTANGLE;
import static android.graphics.drawable.GradientDrawable.RING;

/**
 * author： deemons
 * date:    2018/9/3
 * desc:
 */
public class ShapeUtils {


    private final GradientDrawable mDrawable;

    @IntDef({RECTANGLE, OVAL, LINE, RING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Shape {
    }


    public ShapeUtils() {
        mDrawable = new GradientDrawable();
    }

    public ShapeUtils(@Shape int shape) {
        mDrawable = new GradientDrawable();
        mDrawable.setShape(shape);
    }

    public ShapeUtils shape(@Shape int shape) {
        mDrawable.setShape(shape);
        return this;
    }

    public ShapeUtils solid(@ColorInt int color) {
        mDrawable.setColor(color);
        return this;
    }

    public ShapeUtils stroke(int width, @ColorInt int color) {
        mDrawable.setStroke(width, color);
        return this;
    }

    public ShapeUtils stroke(int width, @ColorInt int color, float dashGap, float dashWidth) {
        mDrawable.setStroke(width, color, dashGap, dashWidth);
        return this;
    }

    public ShapeUtils corner(float radius) {
        mDrawable.setCornerRadius(radius);
        return this;
    }


    public ShapeUtils corners(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        float[] floats = {topLeft, topRight, bottomRight, bottomLeft};
        mDrawable.setCornerRadii(floats);
        return this;
    }

    public ShapeUtils gradientLinear(GradientDrawable.Orientation orientation) {
        mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mDrawable.setOrientation(orientation);
        return this;
    }

    public ShapeUtils gradientSweep(float centerX, float centerY) {
        mDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        mDrawable.setGradientCenter(centerX, centerY);
        return this;
    }

    public ShapeUtils gradientRadial(float centerX, float centerY) {
        mDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        mDrawable.setGradientCenter(centerX, centerY);
        return this;
    }

    public ShapeUtils gradientColor(@ColorInt int... colors) {
        mDrawable.setColors(colors);
        return this;
    }

    public ShapeUtils size(int width, int height) {
        mDrawable.setSize(width, height);
        return this;
    }

    public ShapeUtils padding(int left, int top, int right, int bottom) {
        mDrawable.getPadding(new Rect(left, top, right, bottom));
        return this;
    }


    public GradientDrawable create() {
        return mDrawable;
    }

}
