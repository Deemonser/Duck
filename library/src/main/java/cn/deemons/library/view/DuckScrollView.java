package cn.deemons.library.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * author： deemons
 * date:    2018/10/9
 * desc:
 */
public class DuckScrollView extends ScrollView {
    public DuckScrollView(@NonNull Context context) {
        super(context);
    }

    public DuckScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DuckScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
