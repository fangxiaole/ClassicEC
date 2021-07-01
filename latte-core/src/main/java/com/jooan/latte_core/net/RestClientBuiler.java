package com.jooan.latte_core.net;

import android.content.Context;

import com.jooan.latte_core.net.callback.IError;
import com.jooan.latte_core.net.callback.IFailure;
import com.jooan.latte_core.net.callback.IRequest;
import com.jooan.latte_core.net.callback.ISuccess;
import com.jooan.latte_core.ui.LatteLoader;
import com.jooan.latte_core.ui.LoaderStyle;

import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuiler {
    private String mUrl;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private RequestBody mBody;
    private IRequest mIRequest;
    private ISuccess mISuccess;
    private IError mIError;
    private IFailure mIFailure;
    private LoaderStyle mloaderStyle;
    private Context mcontext;

    public final RestClientBuiler url(String url){
        this.mUrl=url;
        return this;
    }
    public final RestClientBuiler params(WeakHashMap<String,Object> params) {
        PARAMS.putAll(params);
        return this;
    }
    public final RestClientBuiler params(String key,String value){
        PARAMS.put(key,value);
        return this;
    }

    public final RestClientBuiler raw(String raw){
        mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    public final RestClientBuiler onRequest(IRequest iRequest){
        this.mIRequest=iRequest;
        return this;
    }
    public final RestClientBuiler success(ISuccess iSuccess){
        this.mISuccess=iSuccess;
        return this;
    }
    public final RestClientBuiler error(IError iError){
        this.mIError=iError;
        return this;
    }
    public final RestClientBuiler failure(IFailure iFailure){
        this.mIFailure=iFailure;
        return this;
    }

    public final RestClientBuiler loading(LoaderStyle loaderStyle,Context context){
        this.mloaderStyle=loaderStyle;
        this.mcontext = context;
        return this;
    }

    public final RestClientBuiler loading(Context context){
        this.mloaderStyle= LatteLoader.LOADING_STYLE;
        this.mcontext = context;
        return this;
    }

    public final RestClient Builer(){
        return new RestClient(mUrl,PARAMS,mBody,mIRequest,mISuccess,mIError,mIFailure,mloaderStyle,mcontext);
    }


}

