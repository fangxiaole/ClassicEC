package com.jooan.latte_core.net;

import com.jooan.latte_core.app.ConfigType;
import com.jooan.latte_core.app.Latte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {

    public static class ParamsHodler{
        private static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();

    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHodler.PARAMS;
    }

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigType.API_HOSE.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkhttpHolder.OKHTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create()).build();

    }

    public static ArrayList<Interceptor> INTERCEPTORS = (ArrayList<Interceptor>) Latte.getConfigurations().get(ConfigType.INTERCEPTORS);
    public static OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();

    public static OkHttpClient.Builder addInterceptor(){
        if(INTERCEPTORS!=null&&!INTERCEPTORS.isEmpty()){
            for(Interceptor interceptor:INTERCEPTORS){
                BUILDER.addInterceptor(interceptor);
            }
        }
        return BUILDER;
    }
    private static final class OkhttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OKHTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).build();
    }

    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
