### Duck 文档
Duck 能帮助开发者直接在 xml 的任意控件上实现 Shape 效果，无需创建额外的xml文件，并且没有任何侵入性。

![duck](img/duck.gif)





<br>

#### 使用

1. 在项目的 build.gradle 文件下添加插件依赖

```gr
buildscript {
    ...    
    dependencies {
    	...
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.2'
    }
}
```

2. 在模块的 build.gradle 文件下添加

```groovy
api 'com.deemons.duck:duck:0.0.2'
```

3. 可选

在 xml 中使用自定义属性时，是没有提示的，我们可以通过 `Live Template` 来实现。

项目根目录下的 `settings_duck.jar` 文件，此文件已经设置了 AS 中的 Live Template，下载此文件并导入到系统设置中 `File` -> `Import setting` 。

![14-06-02](img/14-06-02.gif)

4. 直接在 xml 中使用

~~~xml
        <TextView
            android:layout_width="200dp"
            android:layout_height="40dp"
            android:layout_marginTop="10dp"
            android:gravity="center"
            android:text="指定背景色、圆角及边框"
            android:textColor="#fff"
            android:textSize="12sp"
            app:corner="8dp"
            app:solid="@color/colorPrimary"
            app:stroke_color="@color/colorPrimaryDark"
            app:stroke_width="2dp" />

~~~

或者在代码中使用

```java
        TextView view = findViewById(R.id.text);
 
        view.setBackground(new ShapeUtils(GradientDrawable.RECTANGLE)
                .corner(10)
                .stroke(3, Color.parseColor("#0000ff"))
                .gradientLinear(GradientDrawable.Orientation.LEFT_RIGHT)
                .gradientColor(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
                .create()
        );

```



<br>

### API

| 代码                        | 功能                                         | 代码                    | 功能                                       |
| :-------------------------- | :------------------------------------------- | ----------------------- | ------------------------------------------ |
| solid                       | 填充背景色<br />（API 21支持 SelectorColor） | stroke_color            | 边框颜色<br />（API 21支持 SelectorColor） |
| corner_top_left             | 左上倒角                                     | stroke_width            | 边框宽度                                   |
| corner_top_right            | 右上倒角                                     | stroke_dash_gap         | 虚线边框单个长度                           |
| corner_bottom_left          | 左下倒角                                     | stroke_dash_width       | 虚线边框的间隔                             |
| corner_bottom_right         | 右下倒角                                     | padding_left            | 左内间距                                   |
| corner                      | 所有倒角                                     | padding_top             | 上内间距                                   |
| gradient_color_start        | 渐变初始颜色                                 | padding_right           | 右内间距                                   |
| gradient_color_center       | 渐变中心颜色                                 | padding_bottom          | 下内间距                                   |
| gradient_color_end          | 渐变结尾颜色                                 | size_width              | Shape 的宽                                 |
| gradient_linear_orientation | 线性 渐变方向                                | size_height             | Shape 的高                                 |
| gradient_sweep_centerX      | 扫描渐变中心X坐标                            | gradient_radial_centerX | 径向渐变中心点X坐标                        |
| gradient_sweep_centerY      | 扫描渐变中心Y坐标                            | gradient_radial_centerY | 径向渐变中心点Y坐标                        |
| shape                       | 图形                                         |                         |                                            |



<br>

#### 原理

在考虑用什么技术实现时，考虑这几点：

1. 任何控件都能有效，即使是自定义控件。
2. 不能有侵入性，即使更换或废弃本库，也能保证稳定性。

最开始，第一个想到的是 `LayoutInflater.Factory` ，xml 控件解析成 View时，必须经过它，也是换肤的解决方案，但这样得一个个替换成自己的，非常麻烦。

有没有更好的解决方案呢？

**得益于 AspectJ 的 AOP（面向切面编程）能力，我们可以在编译时期，直接在 View 及其子类的构造方法中插入相关代码，解析xml 中自定义的属性，最后设置到控件上。**

```java
    @Pointcut("execution(android.view.View+.new(..))")
    public void callViewConstructor() {
    }

    @After("callViewConstructor()")
    public void inject(JoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
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
```



由于 AspectJ 能遍历项目中所有依赖包，因此，无论是 support 库，还是第三方库都能得到很好支持。

但是 AOP 也存在一定问题，我们的 apk 中是不会存在系统原生 Android SDK 的，例如 `TextView` 这个系统控件，在编译时是不会打包到 apk 中，因此，AOP 技术对这种原生控件无能为力。

幸好，我们绝大部分项目为了兼容性，一般都会直接依赖官方的兼容库，即 `support` 相关的库。

在 support· 库中，会将一些原生控件，直接替换成 support 相关控件。相关代码如下：

```java
android/support/v7/app/AppCompatViewInflater

switch (name) {
            case "TextView":
                view = createTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case "ImageView":
                view = createImageView(context, attrs);
                verifyNotNull(view, name);
                break;
            case "Button":
                view = createButton(context, attrs);
                verifyNotNull(view, name);
                break;
            case "EditText":
                view = createEditText(context, attrs);
                verifyNotNull(view, name);
                break;
   			......
        }
```

而对于这些控件，我们的 AOP 都能够生效了。

在 support 库中，没有替换掉 ViewGroup 的几个常用子类，如`LinearLayout` 、`RelativeLayout`、`FrameLayout`等，

所以，我们我们仿照 support 的替换方式，直接在 `LayoutInflater.Factory.onCreateView` 方法中注入相应的替换代码。

```java
    
    @Pointcut("execution(* *..LayoutInflater.Factory+.onCreateView(..))")
    public void callLayoutInflater() {
    }

	@Around("callLayoutInflater()")
    public Object replaceView(ProceedingJoinPoint joinPoint) throws Throwable {

		....
            
        switch (name) {
            case "RelativeLayout":
                return new DuckRelativeLayout(context, attrs);
            case "LinearLayout":
                return new DuckLinearLayout(context, attrs);
            case "FrameLayout":
                return new DuckFrameLayout(context, attrs);
            case "TableLayout":
                return new DuckTableLayout(context, attrs);
            case "ScrollView":
                return new DuckScrollView(context, attrs);
            default:
                break;
        }

        return result;
    }

```



这个库的代码其实很少，我这里也只是实现了 Shape 这一个功能。

```java
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
```

这里保留的 Duck 的扩展性，如果觉得不够，可以自行实现功能更强大的 Injector 来替换默认的。

AOP 的能力远不止如此，还有很多事情可以做，建议大家可以发挥想象，进行更多的扩展。

<br>

### 初衷
这个库的由来，是因为公司一个维护了 4 年的项目。

经历 4 年的项目，产品设计不知道改了多少版，期间产生并堆砌大量`shape.xml` 文件，这些文件因为索引的问题往往还无法清理。

同时，同一个 `shape.xml` 文件，因为设计存在不规范的问题，在不同页面改动了一点颜色、倒角或线宽等，就无法复用，必须据此创建新的文件。

最后，大量的文件堆积，开发人员开发时，想复用去画时间找 `shape.xml` ，还不如自己创建新的方便，这样恶性循环， 只能 GG。

最后，我想说，Android 设计 `Shape` 的初衷是好的:  一个 APP，统一的设计规范，就应该复用 `Shape` 。

但这种情况对于国内的生态来说并不适用。

首先，相同屏幕尺寸，中文承载信息的能力远大于英文，这就导致国外大部分 APP 界面设计简洁清爽，国内就显得非常复杂，同时国内互联网更新速度很快，界面是生命周期短，人员流动，很难做到界面统一。

所有，Android 的 Shape 并不适合国内生态。

开发时，超级羡慕对面 IOS 开发们可以直接在控件上进行花式倒角、加线框等骚操作，想不通为啥 Android 不能在这一点上借鉴IOS。哎，Android 与 IOS 的宿命之争，说多了都是泪。

基于上面种种原因，所以出现了想开发这个库。

<br>

这个库只实现了最常用的 Shape 功能，但 selector 及 layout-list 并未实现，因为有两点考虑：

1. shape 使用场景更多，并且更频繁，其他两种只在少数特定场景中使用。

2. selector 及 layout-list 需要更多精细的代码控制，如全部挤在 xml 中一个控件上，会非常臃肿，难以维护。

<br>




### 不足

由于使用 AOP ，所以在编写时，无法实时预览，看看后续能否通过 AS 插件补足吧！