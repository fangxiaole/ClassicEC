package com.jooan.latte_core.net.download;

import android.os.AsyncTask;

import com.jooan.latte_core.net.RestCreator;
import com.jooan.latte_core.net.callback.IError;
import com.jooan.latte_core.net.callback.IFailure;
import com.jooan.latte_core.net.callback.IRequest;
import com.jooan.latte_core.net.callback.ISuccess;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandle {
    private final String URL;
    private static final Map<String,Object> PARAMS = RestCreator.getParams();
    private RequestBody BODY;
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    public DownloadHandle(String url, IRequest iRequest, ISuccess iSuccess, IError iError, IFailure iFailure, String downdir, String extension, String name) {
        this.URL = url;
        this.IREQUEST = iRequest;
        this.ISUCCESS = iSuccess;
        this.IERROR = iError;
        this.IFAILURE = iFailure;
        this.DOWNLOAD_DIR = downdir;
        this.EXTENSION = extension;
        this.NAME = name;
    }

    public void handDownload(){
        if(IREQUEST!=null){
           IREQUEST.onRequestStart();
        }
        RestCreator.getRestService().download(URL,PARAMS).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    final SaveFileTask saveFileTask = new SaveFileTask(IREQUEST,ISUCCESS);
                    saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR,EXTENSION,NAME,responseBody);
                    //这里注意判断，否则可能文件下载不全
                    if(saveFileTask.isCancelled()){
                        if(IREQUEST!=null){
                            IREQUEST.onResquestEnd();
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
