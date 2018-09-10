//package cn.deemons.library;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//import java.util.Locale;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//
///**
// * author： deemons
// * date:    2018/9/4
// * desc:
// */
//@Aspect
//public class AspectPlugin {
//
//    @Pointcut("execution(android.view.View+.new(..))")
//    public void callViewConstructor() {
//    }
//
//    @Pointcut("call(android.view.ViewGroup+.new(..))")
//    public void callViewGroupConstructor() {
//    }
//
//    @After("callViewConstructor()")
//    public void method(JoinPoint joinPoint) throws Throwable {
//        //        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        String className = joinPoint.getThis().getClass().getSimpleName();
//
//        Signature signature = joinPoint.getSignature();
//
//        View view = (View) joinPoint.getTarget();
//        Object[] args = joinPoint.getArgs();
//        if (args.length < 2 || view.getBackground() != null) {
//            return;
//        }
//
//        StringBuilder keyBuilder = new StringBuilder();
//
//        keyBuilder.append(signature.toString());
//        keyBuilder.append(" \n ");
//
//        Context context = (Context) args[0];
//        AttributeSet attrs = (AttributeSet) args[1];
//        int defStyleAttr = args.length > 2 ? (int) args[2] : 0;
//        int defStyleRes = args.length > 3 ? (int) args[3] : 0;
//
//        TypedArray a =
//            context.obtainStyledAttributes(attrs, R.styleable.myview, defStyleAttr, defStyleRes);
//
//        Drawable drawable = null;
//        int N = a.getIndexCount();
//        keyBuilder.append(N);
//        keyBuilder.append("-----");
//        for (int i = 0; i < N; i++) {
//
//            int attr = a.getIndex(i);
//            keyBuilder.append(attr);
//            //if (attr == R.styleable.MyView_android_solid) {
//            //    keyBuilder.append("solid");
//            //    drawable = new ShapeUtils().solid(a.getColor(attr, Color.WHITE)).create();
//            //} else if (attr == R.styleable.MyView_android_solid) {
//            //
//            //} else {
//            //
//            //}
//        }
//
//        a.recycle();
//        if (drawable != null) {
//            view.setBackground(drawable);
//        }
//        keyBuilder.append("\n");
//
//        for (Object obj : args) {
//            if (obj != null) {
//                String format =
//                    String.format(Locale.getDefault(), "%s = %s", obj.getClass().getSimpleName(),
//                        obj.toString());
//                keyBuilder.append(format);
//            } else {
//                keyBuilder.append("null");
//            }
//            keyBuilder.append(" ,");
//        }
//
//        Log.d("AspectPlugin", "aspect =======>>>>>>>" + keyBuilder.toString());
//    }
//}
