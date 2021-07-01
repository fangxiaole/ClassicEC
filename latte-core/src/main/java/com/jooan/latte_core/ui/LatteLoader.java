package com.jooan.latte_core.ui;

import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.jooan.latte_core.R;
import com.jooan.latte_core.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatDialog;

public class LatteLoader {
    public static final int LOADING_SCALE = 8;
    public static final LoaderStyle LOADING_STYLE = LoaderStyle.BallClipRotateIndicator;
    public static ArrayList<AppCompatDialog> DIALOG_LIST = new ArrayList<>();

    public static void showLoading(Context context,Enum<LoaderStyle> loaderStyle){
        showLoading(context,loaderStyle.name());
    }

    private static void showLoading(Context context,String type){
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type,context);
        dialog.setContentView(avLoadingIndicatorView);
        int deviceWidth = DimenUtil.getScreenWith();
        int deviceHeight = DimenUtil.getScreenheight();
        final Window window = dialog.getWindow();
        if(window!=null){
            WindowManager.LayoutParams lp = window.getAttributes();
            if(lp!=null){
                lp.width = deviceWidth/LOADING_SCALE;
                lp.height = deviceHeight/LOADING_SCALE;
            }
        }
        DIALOG_LIST.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context,LOADING_STYLE);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog:DIALOG_LIST){
            if(dialog!=null){
                dialog.cancel();
            }
        }

    }
}
