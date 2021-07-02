package com.jooan.latte_core.net;


import android.content.Context;

import com.jooan.latte_core.net.callback.HttpMethod;
import com.jooan.latte_core.net.callback.IError;
import com.jooan.latte_core.net.callback.IFailure;
import com.jooan.latte_core.net.callback.IRequest;
import com.jooan.latte_core.net.callback.IRequestCallBacks;
import com.jooan.latte_core.net.callback.ISuccess;
import com.jooan.latte_core.net.download.DownloadHandle;
import com.jooan.latte_core.ui.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestClient {
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
    private final LoaderStyle LOADERSTYLE;
    private final Context CONTEXT;
    private final File FILE;

    public RestClient(String url, Map<String, Object> params, RequestBody body, IRequest iRequest, ISuccess iSuccess, IError iError, IFailure iFailure,File file,String downdir,String extension,String name,LoaderStyle loaderStyle,Context context) {
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.IREQUEST = iRequest;
        this.ISUCCESS = iSuccess;
        this.IERROR = iError;
        this.IFAILURE = iFailure;
        this.LOADERSTYLE = loaderStyle;
        this.CONTEXT = context;
        this.FILE = file;
        this.DOWNLOAD_DIR = downdir;
        this.EXTENSION =extension;
        this.NAME = name;
    }

    private void request(HttpMethod method){
        final RestService service = RestCreator.getRestService();
        Call<String>  call = null;
        if(IREQUEST!=null){
            IREQUEST.onRequestStart();
        }
        switch(method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = service.upload(URL,body);
                break;
            default:
                break;
        }

        if(call!=null){
            call.enqueue(getIRequestCallBacks());
        }
    }

    private  IRequestCallBacks getIRequestCallBacks(){
        return new IRequestCallBacks(IREQUEST,ISUCCESS,IERROR,IFAILURE,LOADERSTYLE,CONTEXT);
    }

    public final  void get(){
        request(HttpMethod.GET);
    }
    public final  void post(){
        if(BODY==null){
            request(HttpMethod.POST);
        }else {
            if(PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }else{
                request(HttpMethod.POST_RAW);
            }
        }

    }
    public final  void put(){
        if(BODY==null){
            request(HttpMethod.PUT);
        }else {
            if(PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }else{
                request(HttpMethod.PUT_RAW);
            }
        }
    }
    public final  void delete(){
        request(HttpMethod.DELETE);
    }

    public final void upload(){
        request(HttpMethod.UPLOAD);
    }

    public final void downLoad(){
        new DownloadHandle(URL,IREQUEST,ISUCCESS,IERROR,IFAILURE,DOWNLOAD_DIR,EXTENSION,NAME).handDownload();

    }
}
