package com.jooan.latte_core.net.interceptor;

import com.jooan.latte_core.util.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.annotation.RawRes;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DebugInterceptor extends BaseInterceptor{
    private final String DEBUG_URL;
    private final int RAW_ID;

    public DebugInterceptor(String debug_url, @RawRes int raw_id) {
        this.DEBUG_URL = debug_url;
        this.RAW_ID = raw_id;
    }


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        String url = chain.request().url().toString();
        if(url.contains(DEBUG_URL)){
            return deBugResponse(chain,RAW_ID);
        }
        return chain.proceed(chain.request());
    }

    public Response getResponse(Chain chain,String json){
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type","application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"),json))
                .message("ok")
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .build();
    }

    public Response deBugResponse(Chain chain, @RawRes int rawId){
        String json = FileUtil.getRawFile(rawId);
       return getResponse(chain,json);
    }

}
