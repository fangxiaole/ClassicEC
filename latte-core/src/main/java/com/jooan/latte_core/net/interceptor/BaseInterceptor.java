package com.jooan.latte_core.net.interceptor;


import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

public abstract class BaseInterceptor implements Interceptor {

    public LinkedHashMap<String,String> getUrlParams(Chain chain){
        HttpUrl httpUrl = chain.request().url();
        int size = httpUrl.querySize();
        LinkedHashMap<String,String> params = new LinkedHashMap<>();
        for (int i=0;i<size;i++){
            params.put(httpUrl.queryParameterName(i),httpUrl.queryParameterValue(i));

        }
        return params;
    }

    public String getUrlParams(Chain chain, String key){
        HttpUrl httpUrl = chain.request().url();
        return httpUrl.queryParameter(key);
    }

    public LinkedHashMap<String,String> postParams(Chain chain){
        FormBody formBody = (FormBody) chain.request().body();
        LinkedHashMap<String,String> params = new LinkedHashMap<>();
        int size = formBody.size();
        for(int i=0;i<size;i++){
            params.put(formBody.name(i),formBody.value(i));
        }
        return params;
    }

    public String postParams(Chain chain,String key){
        return postParams(chain).get(key);

    }
}
