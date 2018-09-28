package cn.deemons.library.core;

import cn.deemons.library.shape.ShapeInjector;

/**
 * author： deemons
 * date:    2018/9/28
 * desc:
 */
public class DuckFactor {

    private static Injector mInjector;


    public static void setFactor(Injector injector) {
        mInjector = injector;
    }

    public static Injector getFactor() {
        if (mInjector == null) {
            mInjector = new ShapeInjector();
        }
        return mInjector;
    }
}
