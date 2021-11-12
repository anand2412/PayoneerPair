package com.payoneer.payoneerpay.data.api;


import com.payoneer.payoneerpay.data.model.PaymentResponse;

import java.util.List;

import retrofit2.Call;

public class ApiHelper {

    private static ApiHelper mInstance;
    private RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

    private ApiHelper() {
    }

    public static ApiHelper getInstance() {
        if (mInstance == null) {
            //synchronized block to remove overhead
            synchronized (ApiHelper.class) {
                if(mInstance == null) {
                    mInstance = new ApiHelper();
                }
            }
        }
        return mInstance;
    }

    public Call<PaymentResponse> getAllPaymentTypes() {
        return retrofitBuilder.mApiService.getAllPaymentTypes();
    }
}
