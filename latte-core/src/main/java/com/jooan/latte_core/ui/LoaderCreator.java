package com.jooan.latte_core.ui;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

public class LoaderCreator {
    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(String type, Context context){
        AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if(LOADING_MAP.get(type)==null){
            Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type,indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String name){
        if(name==null||name.isEmpty()){
            return null;
        }
        final StringBuffer drawableClassName = new StringBuffer();
        if(!name.contains(".")){
            drawableClassName.append(AVLoadingIndicatorView.class.getPackage().getName());
            drawableClassName.append(".indicators");
            drawableClassName.append(".");
        }
        drawableClassName.append(name);
        try {
            Class<?> defaultClass = Class.forName(drawableClassName.toString());
            return (Indicator) defaultClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
