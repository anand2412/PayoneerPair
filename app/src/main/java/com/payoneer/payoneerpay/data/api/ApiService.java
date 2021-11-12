package com.payoneer.payoneerpay.data.api;

import com.payoneer.payoneerpay.data.model.PaymentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * interface is responsible to define api call making to server
 */
public interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("optile/checkout-android/develop/shared-test/lists/listresult.json")
    Call<PaymentResponse> getAllPaymentTypes();
}
