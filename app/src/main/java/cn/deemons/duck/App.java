package cn.deemons.duck;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * author： deemons
 * date:    2018/9/5
 * desc:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
