package com.jooan.classicec;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.jooan.latte_core.app.Latte;

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("")
                .withIcon(new FontAwesomeModule())
                .configure();
    }
}
