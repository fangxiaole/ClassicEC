package com.jooan.latte_core.net.callback;

import android.content.Context;

import com.jooan.latte_core.ui.LatteLoader;
import com.jooan.latte_core.ui.LoaderCreator;
import com.jooan.latte_core.ui.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IRequestCallBacks implements Callback<String> {
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final LoaderStyle LOADERSTYLE;
    private final Context CONTEXT;

    public IRequestCallBacks(IRequest iRequest, ISuccess iSuccess, IError iError, IFailure iFailure,LoaderStyle loaderStyle,Context context) {
        this.IREQUEST = iRequest;
        this.ISUCCESS = iSuccess;
        this.IERROR = iError;
        this.IFAILURE = iFailure;
        this.LOADERSTYLE = loaderStyle;
        this.CONTEXT = context;
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful()){
            if(call.isExecuted()){
                if(ISUCCESS!=null){
                    ISUCCESS.onSucess(response.body());
                }
            }
        }else {
            if(IERROR!=null){
                IERROR.onError(response.code(),response.message());
            }
        }

        LatteLoader.showLoading(CONTEXT,LOADERSTYLE);

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(IFAILURE!=null){
            IFAILURE.onFailure();
        }

        if(IREQUEST!=null){
            IREQUEST.onResquestEnd();
        }
    }
}
