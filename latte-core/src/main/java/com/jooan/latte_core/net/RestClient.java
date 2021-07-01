package com.jooan.latte_core.net;


import android.content.Context;

import com.jooan.latte_core.net.callback.HttpMethod;
import com.jooan.latte_core.net.callback.IError;
import com.jooan.latte_core.net.callback.IFailure;
import com.jooan.latte_core.net.callback.IRequest;
import com.jooan.latte_core.net.callback.IRequestCallBacks;
import com.jooan.latte_core.net.callback.ISuccess;
import com.jooan.latte_core.ui.LoaderStyle;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;

public class RestClient {
    private final String URL;
    private static final Map<String,Object> PARAMS = RestCreator.getParams();
    private RequestBody BODY;
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final LoaderStyle LOADERSTYLE;
    private final Context CONTEXT;

    public RestClient(String url, Map<String, Object> params, RequestBody body, IRequest iRequest, ISuccess iSuccess, IError iError, IFailure iFailure,LoaderStyle loaderStyle,Context context) {
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.IREQUEST = iRequest;
        this.ISUCCESS = iSuccess;
        this.IERROR = iError;
        this.IFAILURE = iFailure;
        this.LOADERSTYLE = loaderStyle;
        this.CONTEXT = context;
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
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
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
        request(HttpMethod.POST);
    }
    public final  void put(){
        request(HttpMethod.PUT);
    }
    public final  void delete(){
        request(HttpMethod.DELETE);
    }
}
