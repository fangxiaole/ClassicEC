package com.jooan.classicec;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.jooan.latte_core.app.Latte;
import com.jooan.latte_core.net.interceptor.BaseInterceptor;
import com.jooan.latte_core.net.interceptor.DebugInterceptor;

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("https://www.baidu.co")
                .withIcon(new FontAwesomeModule())
                .withInterceptor(new DebugInterceptor("test",R.raw.test))
                .configure();
    }
}
