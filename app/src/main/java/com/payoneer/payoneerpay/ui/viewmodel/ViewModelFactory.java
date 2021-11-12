package com.payoneer.payoneerpay.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.payoneer.payoneerpay.data.api.ApiHelper;
import com.payoneer.payoneerpay.data.repository.DataRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private ApiHelper mApiHelper;
    public ViewModelFactory(ApiHelper apiHelper){
        mApiHelper = apiHelper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PaymentResponseViewModel.class)) {
            return (T) new PaymentResponseViewModel(new DataRepository(mApiHelper));
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
