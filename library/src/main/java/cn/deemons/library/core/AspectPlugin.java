package cn.deemons.library.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import cn.deemons.library.R;
import cn.deemons.library.shape.ShapeUtils;

import static android.graphics.drawable.GradientDrawable.LINE;
import static android.graphics.drawable.GradientDrawable.OVAL;
import static android.graphics.drawable.GradientDrawable.RECTANGLE;
import static android.graphics.drawable.GradientDrawable.RING;

/**
 * author： deemons
 * date:    2018/9/4
 * desc:
 */
@Aspect
public class AspectPlugin {

    private static final String TAG = "Duck";
    private static int lastHash = 0;

    //@Pointcut("execution(* *.onCreateView(..))")
    @Pointcut("execution(android.view.View+.new(..))")
    public void callViewConstructor() {
    }

    //@Pointcut("call(android.view.ViewGroup+.new(..))")
    @Pointcut("execution(android.view.ViewGroup+.new(..))")
    public void callViewGroupConstructor() {
    }

    @After("callViewConstructor()")
    public void method(JoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        Log.i(TAG, "pointcut =====> " + signature.toString());

        View view = (View) joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();

        int hashCode = view.hashCode();

        if (filter(hashCode) || args.length < 2 || !(args[0] instanceof Context)) {
            return;
        }
        lastHash = hashCode;

        Context context = (Context) args[0];

        AttributeSet attrs = (AttributeSet) args[1];

        Log.i(TAG, "inject =====> " + signature.toString());
        DuckFactor.getFactor().inject(view, context, attrs);
    }

    private boolean filter(int hashCode) {
        return lastHash == hashCode;
    }
}
