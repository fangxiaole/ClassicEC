package com.jooan.latte_core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.jooan.latte_core.app.Latte;

public class DimenUtil {
    public static int getScreenWith(){
        Resources resources = Latte.getApplication().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenheight(){
        Resources resources = Latte.getApplication().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
