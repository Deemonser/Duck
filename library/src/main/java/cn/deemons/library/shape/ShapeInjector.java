package cn.deemons.library.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.deemons.library.R;
import cn.deemons.library.core.Injector;

/**
 * @author： deemons
 * date:    2018/9/28
 * desc:
 */
public class ShapeInjector implements Injector {


    @Override
    public void inject(View view, Context context, AttributeSet attrs) {

        TypedArray shapeType = context.obtainStyledAttributes(attrs, R.styleable.Shape);


        int solidColor = shapeType.getColor(R.styleable.Shape_solid, -1);
        float corner = shapeType.getDimension(R.styleable.Shape_corner, -1);

        float topLeft = shapeType.getDimension(R.styleable.Shape_corner_top_left, 0);
        float topRight = shapeType.getDimension(R.styleable.Shape_corner_top_right, 0);
        float bottomLeft = shapeType.getDimension(R.styleable.Shape_corner_bottom_left, 0);
        float bottomRight = shapeType.getDimension(R.styleable.Shape_corner_bottom_right, 0);


        int strokeWidth = shapeType.getDimensionPixelOffset(R.styleable.Shape_stroke_width, 0);
        int strokeColor = shapeType.getColor(R.styleable.Shape_stroke_color, -1);
        float strokeDashGap = shapeType.getDimension(R.styleable.Shape_stroke_dash_gap, 0);
        float strokeDashWidth = shapeType.getDimension(R.styleable.Shape_stroke_dash_width, 0);

        int shape = shapeType.getInt(R.styleable.Shape_shape, -1);

        shapeType.recycle();


        ShapeUtils utils = null;

        if (shape != -1) {
            utils = new ShapeUtils(shape);
        }

        if (solidColor != -1) {
            utils = checkAndCreateShapeUtils(utils);
            utils.solid(solidColor);
        }

        if (corner > 0) {
            utils = checkAndCreateShapeUtils(utils);
            utils.corner(corner);
        }

        if (topLeft > 0 || topRight > 0 || bottomLeft > 0 || bottomRight > 0) {
            utils = checkAndCreateShapeUtils(utils);
            utils.corners(topLeft, topRight, bottomRight, bottomLeft);
        }

        if (strokeWidth > 0 && strokeColor != -1) {
            utils = checkAndCreateShapeUtils(utils);
            if (strokeDashGap > 0 && strokeDashWidth > 0) {
                utils.stroke(strokeWidth, strokeColor, strokeDashGap, strokeDashWidth);
            } else {
                utils.stroke(strokeWidth, strokeColor);
            }
        }

        Log.i("Duck", "solid=" + solidColor + " ,corner=" + corner);

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
