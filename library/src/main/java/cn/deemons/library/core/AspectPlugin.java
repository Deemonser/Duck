package cn.deemons.library.core;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import cn.deemons.library.view.DuckFrameLayout;
import cn.deemons.library.view.DuckRelativeLayout;
import cn.deemons.library.view.DuckTableLayout;

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

//
//    @Pointcut("execution(* *..AppCompatViewInflater+.createView(..))")
//    public void callCreateView() {
//    }
//
//
//    //@Pointcut("call(android.view.ViewGroup+.new(..))")
//    @Pointcut("execution(android.view.ViewGroup+.new(..))")
//    public void callViewGroupConstructor() {
//    }


    @Around("callLayoutInflater()")
    public Object replaceView(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        Signature signature = joinPoint.getSignature();
        Log.i(TAG, "replaceView =====> " + signature.toString());


        Object[] args = joinPoint.getArgs();
        int length = args.length;
        if (length != 4) {
            return result;
        }

        AttributeSet attrs = (AttributeSet) args[length - 1];
        Context context = (Context) args[length - 2];
        String name = (String) args[length - 3];

        Log.i(TAG, "AttributeSet:" + attrs.toString() + " ,context:" + context + " ,name=" + name);

        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            Log.i(TAG, attrs.getAttributeName(i) + " = " + attrs.getAttributeValue(i));
        }

        switch (name) {
            case "RelativeLayout":
                return new DuckRelativeLayout(context, attrs);
            case "LinearLayout":
                return new LinearLayoutCompat(context, attrs);
            case "FrameLayout":
                return new DuckFrameLayout(context, attrs);
            case "TableLayout":
                return new DuckTableLayout(context, attrs);
            default:
                break;
        }

        return result;
    }


    @After("callViewConstructor()")
    public void inject(JoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        Log.d(TAG, "inject =====> " + signature.toString());

        Object target = joinPoint.getTarget();

        Object[] args = joinPoint.getArgs();

        int length = args.length;
        if (!(target instanceof View) || length < 2 || target.hashCode() == lastHash || !(args[0] instanceof Context) || !(args[1] instanceof AttributeSet)) {
            return;
        }
        lastHash = target.hashCode();

        Context context = (Context) args[0];
        AttributeSet attrs = (AttributeSet) args[1];

        int count = attrs.getAttributeCount();

        for (int i = 0; i < count; i++) {
            Log.i(TAG, attrs.getAttributeName(i) + " = " + attrs.getAttributeValue(i));
        }

        Log.i(TAG, "inject =====> " + signature.toString());
        DuckFactor.getFactor().inject((View) target, context, attrs);

    }

}
