package com.jooan.latte_core.net.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IRequestCallBacks implements Callback<String> {
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURE;

    public IRequestCallBacks(IRequest IREQUEST, ISuccess ISUCCESS, IError IERROR, IFailure IFAILURE) {
        this.IREQUEST = IREQUEST;
        this.ISUCCESS = ISUCCESS;
        this.IERROR = IERROR;
        this.IFAILURE = IFAILURE;
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
