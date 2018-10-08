package cn.deemons.library.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
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

    @Pointcut("execution(* *..LayoutInflater.Factory+.onCreateView(..))")
    public void callLayoutInflater() {
    }


    //@Pointcut("call(android.view.ViewGroup+.new(..))")
    @Pointcut("execution(android.view.ViewGroup+.new(..))")
    public void callViewGroupConstructor() {
    }

    @Around("callLayoutInflater()")
    public Object method(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        Signature signature = joinPoint.getSignature();
        Log.i(TAG, "pointcut =====> " + signature.toString());

        Object[] args = joinPoint.getArgs();

        int length = args.length;
        if (length != 4) {
            return result;
        }

        Context context = (Context) args[length - 2];

        AttributeSet attrs = (AttributeSet) args[length - 1];

        if (result instanceof View) {
            Log.i(TAG, "inject =====> " + signature.toString());
            DuckFactor.getFactor().inject((View) result, context, attrs);
        }

        return result;
    }

}
