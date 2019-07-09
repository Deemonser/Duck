package cn.deemons.library.shape;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import cn.deemons.library.R;
import cn.deemons.library.core.Injector;

/**
 * @author： deemons
 * date:    2018/9/28
 * desc:
 */
public class ShapeInjector implements Injector {

    static final int COLOR_EMPTY_VALUE = -2;
    static final int SIZE_EMPTY_VALUE = -3;

    @Override
    public void inject(View view, Context context, AttributeSet attrs) {

        TypedArray shapeType = context.obtainStyledAttributes(attrs, R.styleable.Shape);

        int shape = shapeType.getInt(R.styleable.Shape_shape, -1);
        int sizeWidth = shapeType.getDimensionPixelOffset(R.styleable.Shape_size_width, SIZE_EMPTY_VALUE);
        int sizeHeight = shapeType.getDimensionPixelOffset(R.styleable.Shape_size_height, SIZE_EMPTY_VALUE);

        int solidColor = shapeType.getColor(R.styleable.Shape_solid, COLOR_EMPTY_VALUE);
        ColorStateList solidColorStateList = shapeType.getColorStateList(R.styleable.Shape_solid);
        float corner = shapeType.getDimension(R.styleable.Shape_corner, -1);

        float topLeft = shapeType.getDimension(R.styleable.Shape_corner_top_left, 0);
        float topRight = shapeType.getDimension(R.styleable.Shape_corner_top_right, 0);
        float bottomLeft = shapeType.getDimension(R.styleable.Shape_corner_bottom_left, 0);
        float bottomRight = shapeType.getDimension(R.styleable.Shape_corner_bottom_right, 0);


        float paddingLeft = shapeType.getDimension(R.styleable.Shape_padding_left, 0);
        float paddingRight = shapeType.getDimension(R.styleable.Shape_padding_right, 0);
        float paddingTop = shapeType.getDimension(R.styleable.Shape_padding_top, 0);
        float paddingBottom = shapeType.getDimension(R.styleable.Shape_padding_bottom, 0);


        int strokeWidth = shapeType.getDimensionPixelOffset(R.styleable.Shape_stroke_width, 0);
        int strokeColor = shapeType.getColor(R.styleable.Shape_stroke_color, COLOR_EMPTY_VALUE);
        ColorStateList strokeColorStateList = shapeType.getColorStateList(R.styleable.Shape_stroke_color);
        float strokeDashGap = shapeType.getDimension(R.styleable.Shape_stroke_dash_gap, 0);
        float strokeDashWidth = shapeType.getDimension(R.styleable.Shape_stroke_dash_width, 0);

        //线性渐变
        int gradientLinearOrientation = shapeType.getInt(R.styleable.Shape_gradient_linear_orientation, 0);

        float gradientSweepCenterX = shapeType.getFloat(R.styleable.Shape_gradient_sweep_centerX, -1);
        float gradientSweepCenterY = shapeType.getFloat(R.styleable.Shape_gradient_sweep_centerY, -1);

        float gradientRadialCenterX = shapeType.getFloat(R.styleable.Shape_gradient_radial_centerX, -1);
        float gradientRadialCenterY = shapeType.getFloat(R.styleable.Shape_gradient_radial_centerY, -1);
        float gradientRadialRadius = shapeType.getDimension(R.styleable.Shape_gradient_radial_radius, 0);

        int gradientColorStart = shapeType.getColor(R.styleable.Shape_gradient_color_start, COLOR_EMPTY_VALUE);
        int gradientColorCenter = shapeType.getColor(R.styleable.Shape_gradient_color_center, COLOR_EMPTY_VALUE);
        int gradientColorEnd = shapeType.getColor(R.styleable.Shape_gradient_color_end, COLOR_EMPTY_VALUE);

        shapeType.recycle();


        ShapeUtils utils = null;

        if (shape != -1) {
            utils = new ShapeUtils(shape);
        }
        if (sizeWidth != SIZE_EMPTY_VALUE || sizeHeight != SIZE_EMPTY_VALUE) {
            utils = checkAndCreateShapeUtils(utils);
            utils.size(sizeWidth != SIZE_EMPTY_VALUE ? sizeWidth : -1, sizeHeight != SIZE_EMPTY_VALUE ? sizeHeight : -1);
        }

        if (solidColor != COLOR_EMPTY_VALUE || solidColorStateList != null) {
            utils = checkAndCreateShapeUtils(utils);
            if (solidColorStateList != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                utils.solid(solidColorStateList);
            } else if (solidColor != -1) {
                utils.solid(solidColor);
            }
        }

        if (corner > 0) {
            utils = checkAndCreateShapeUtils(utils);
            utils.corner(corner);
        }

        if (topLeft > 0 || topRight > 0 || bottomLeft > 0 || bottomRight > 0) {
            utils = checkAndCreateShapeUtils(utils);
            utils.corners(topLeft, topRight, bottomRight, bottomLeft);
        }

        if (paddingLeft > 0 || paddingRight > 0 || paddingTop > 0 || paddingBottom > 0) {
            utils = checkAndCreateShapeUtils(utils);
            utils.padding((int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom);
        }

        if (strokeWidth > 0 && (strokeColor != COLOR_EMPTY_VALUE || strokeColorStateList != null)) {
            utils = checkAndCreateShapeUtils(utils);
            if (strokeDashGap > 0 || strokeDashWidth > 0) {
                if (strokeColorStateList != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    utils.stroke(strokeWidth, strokeColorStateList, strokeDashGap > 0 ? strokeDashGap : 0, strokeDashWidth > 0 ? strokeDashWidth : 0);
                } else {
                    utils.stroke(strokeWidth, strokeColor, strokeDashGap > 0 ? strokeDashGap : 0, strokeDashWidth > 0 ? strokeDashWidth : 0);
                }
            } else if (strokeColorStateList != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                utils.stroke(strokeWidth, strokeColorStateList);
            } else {
                utils.stroke(strokeWidth, strokeColor);
            }
        }

        if (gradientLinearOrientation > 0) {
            utils = checkAndCreateShapeUtils(utils);
            if (gradientLinearOrientation == 1) {
                utils.gradientLinear(GradientDrawable.Orientation.TOP_BOTTOM);
            } else if (gradientLinearOrientation == 2) {
                utils.gradientLinear(GradientDrawable.Orientation.TR_BL);
            } else if (gradientLinearOrientation == 3) {
                utils.gradientLinear(GradientDrawable.Orientation.RIGHT_LEFT);
            } else if (gradientLinearOrientation == 4) {
                utils.gradientLinear(GradientDrawable.Orientation.BR_TL);
            } else if (gradientLinearOrientation == 5) {
                utils.gradientLinear(GradientDrawable.Orientation.BOTTOM_TOP);
            } else if (gradientLinearOrientation == 6) {
                utils.gradientLinear(GradientDrawable.Orientation.BL_TR);
            } else if (gradientLinearOrientation == 7) {
                utils.gradientLinear(GradientDrawable.Orientation.LEFT_RIGHT);
            } else if (gradientLinearOrientation == 8) {
                utils.gradientLinear(GradientDrawable.Orientation.TL_BR);
            }
        }

        if (gradientSweepCenterX >= 0 || gradientSweepCenterY >= 0) {
            utils = checkAndCreateShapeUtils(utils);
            utils.gradientSweep(gradientSweepCenterX > 0 ? gradientSweepCenterX : 0.5f, gradientSweepCenterY > 0 ? gradientSweepCenterY : 0.5f);
        }

        if (gradientRadialCenterX >= 0 || gradientRadialCenterY >= 0 || gradientRadialRadius > 0) {
            utils = checkAndCreateShapeUtils(utils);
            utils.gradientRadial(gradientRadialCenterX > 0 ? gradientRadialCenterX : 0.5f, gradientRadialCenterY > 0 ? gradientRadialCenterY : 0.5f, gradientRadialRadius);
        }

        if (gradientColorStart != COLOR_EMPTY_VALUE || gradientColorCenter != COLOR_EMPTY_VALUE || gradientColorEnd != COLOR_EMPTY_VALUE) {
            utils = checkAndCreateShapeUtils(utils);
            ArrayList<Integer> list = new ArrayList<>();

            if (gradientColorStart != COLOR_EMPTY_VALUE) {
                list.add(gradientColorStart);
            }
            if (gradientColorCenter != COLOR_EMPTY_VALUE) {
                list.add(gradientColorCenter);
            }
            if (gradientColorEnd != COLOR_EMPTY_VALUE) {
                list.add(gradientColorEnd);
            }

            int[] colors = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                colors[i] = list.get(i);
            }
            Log.i("Duck", "gradientColor " + colors.length);
            utils.gradientColor(colors);
        }


        if (utils != null) {
            view.setBackground(utils.create());
            Log.i("Duck", "createShape");
        }
    }


    @NonNull
    private ShapeUtils checkAndCreateShapeUtils(ShapeUtils utils) {
        if (utils == null) {
            utils = new ShapeUtils();
        }
        return utils;
    }

}
