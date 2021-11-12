package com.payoneer.payoneerpay.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.payoneer.payoneerpay.data.model.PaymentResponse;
import com.payoneer.payoneerpay.data.repository.DataRepository;
import com.payoneer.payoneerpay.utils.APIError;
import com.payoneer.payoneerpay.utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentResponseViewModel extends ViewModel {

    private DataRepository mDataRepository;
    public MutableLiveData<PaymentResponse> mPaymentResponse = new MutableLiveData<>();
    public MutableLiveData<APIError> error = new MutableLiveData<>();

    public PaymentResponseViewModel(DataRepository dataRepository) {
        mDataRepository = dataRepository;
    }

    public void getAllPaymentTypes() {
        mDataRepository.getAllPaymentTypes().enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response <PaymentResponse> response) {
                if (response.isSuccessful()) {
                    mPaymentResponse.postValue(response.body());
                } else {
                    APIError apiError = new APIError();
                    switch (response.code()) {
                        case 404:
                            apiError.setMessage("not found");
                            break;
                        case 500:
                            apiError.setMessage("server broken");
                            break;
                        default:
                            apiError = ErrorUtils.parseError(response);
                            break;
                    }
                    error.postValue(apiError);
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                APIError apiError = new APIError();
                apiError.setMessage("Please check your internet connection!!!");
                apiError.setStatusCode(000);
                error.postValue(apiError);
            }
        });
    }
}