package cn.deemons.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * author： deemons
 * date:    2018/9/5
 * desc:
 */
public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a =
            context.obtainStyledAttributes(attrs, cn.deemons.library.R.styleable.MyView, defStyleAttr, 0);






        a.recycle();
    }



}
