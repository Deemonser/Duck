package cn.deemons.library;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author： deemons
 * date:    2018/9/4
 * desc:
 */
@Aspect
public class AspectPlugin {

    //@Pointcut("execution(* *.onCreateView(..))")
    @Pointcut("execution(android.view.View+.new(..))")
    public void callViewConstructor() {
    }

    //@Pointcut("call(android.view.ViewGroup+.new(..))")
    @Pointcut("call(android.view.ViewGroup+.new(..))")
    public void callViewGroupConstructor() {
    }

    @After("callViewConstructor()")
    public void method(JoinPoint joinPoint) throws Throwable {
        //        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getThis().getClass().getSimpleName();

        Signature signature = joinPoint.getSignature();

        View view = (View) joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        if (args.length < 2 || view.getBackground() != null) {
            return;
        }

        StringBuilder keyBuilder = new StringBuilder();

        keyBuilder.append(signature.toString());
        keyBuilder.append(" \n ");

        AttributeSet attrs = (AttributeSet) args[1];

        if (attrs != null) {
            Drawable drawable = null;
            int N = attrs.getAttributeCount();
            keyBuilder.append("-----");
            for (int i = 0; i < N; i++) {
                keyBuilder.append(attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
            }
        }

        Log.d("AspectPlugin", "aspect =======>>>>>>>" + keyBuilder.toString());
    }
}
