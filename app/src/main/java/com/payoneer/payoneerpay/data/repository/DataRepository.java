package com.payoneer.payoneerpay.data.repository;

import com.payoneer.payoneerpay.data.api.ApiHelper;
import com.payoneer.payoneerpay.data.model.PaymentResponse;

import java.util.List;

import retrofit2.Call;

public class DataRepository {

    private ApiHelper mApiHelper;

    public DataRepository(ApiHelper apiHelper) {
        mApiHelper = apiHelper;
    }

    public Call<PaymentResponse> getAllPaymentTypes(){
        return mApiHelper.getAllPaymentTypes();
    }
}
