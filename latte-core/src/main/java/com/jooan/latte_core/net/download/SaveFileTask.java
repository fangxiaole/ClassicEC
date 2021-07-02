package com.jooan.latte_core.net.download;


import android.os.AsyncTask;
import android.text.TextUtils;

import com.jooan.latte_core.net.callback.IRequest;
import com.jooan.latte_core.net.callback.ISuccess;
import com.jooan.latte_core.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class SaveFileTask extends AsyncTask<Object,Void,File> {
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;

    public SaveFileTask(IRequest iRequest, ISuccess iSuccess) {
        this.IREQUEST = iRequest;
        this.ISUCCESS = iSuccess;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downdir = (String) params[0];
        String extension= (String) params[1];
        String name = (String) params[2];
        ResponseBody responseBody = (ResponseBody) params[3];
        InputStream is = responseBody.byteStream();
        if(TextUtils.isEmpty(downdir)){

        }
        if(TextUtils.isEmpty(extension)){

        }
        if(TextUtils.isEmpty(name)){
            return FileUtil.writeToDisk(is,downdir,extension.toUpperCase(),extension);
        }else{
            return FileUtil.writeToDisk(is,downdir,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(ISUCCESS!=null){
            ISUCCESS.onSucess(file.getPath());
        }
        if(IREQUEST!=null){
            IREQUEST.onResquestEnd();
        }
    }
}
