package com.payoneer.payoneerpay.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.payoneer.payoneerpay.data.api.ApiHelper;
import com.payoneer.payoneerpay.data.model.PaymentResponse;
import com.payoneer.payoneerpay.data.repository.DataRepository;
import com.payoneer.payoneerpay.utils.APIError;
import com.payoneer.payoneerpay.utils.ErrorUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.mockito.Answers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@PrepareForTest(PaymentResponseViewModel.class)
@PowerMockIgnore("javax.net.ssl.*")
public class PaymentResponseViewModelTest {

    DataRepository dataRepository;
    private PaymentResponseViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        dataRepository = PowerMockito.mock(DataRepository.class);
        viewModel = new PaymentResponseViewModel(dataRepository);
    }

    @Test
    public void testNotNull() {
        Call<PaymentResponse> responseCall = new ResponseCall(Response.success(new PaymentResponse()));
        PowerMockito.when(dataRepository.getAllPaymentTypes()).thenReturn(responseCall);
        Observer<PaymentResponse> observer = paymentResponse -> {
            assertNotNull(paymentResponse);
        };
        viewModel.mPaymentResponse.observeForever(observer);
        viewModel.getAllPaymentTypes();

    }

    @Test
    public void testErrorResponse() {
        Call<PaymentResponse> responseCall = new ResponseCall(Response.error(404, ResponseBody.create(MediaType.parse("application/json"),
                "{\"key\":[\"somestuff\"]}")));
        PowerMockito.when(dataRepository.getAllPaymentTypes()).thenReturn(responseCall);
        Observer<APIError> observer = apiError -> assertEquals("not found",apiError.message());
        viewModel.error.observeForever(observer);
        viewModel.getAllPaymentTypes();
    }

    @Test(expected = IOException.class)
    public void testIOException() {
        Call<PaymentResponse> responseCall = new ResponseCall(Response.error(1000, ResponseBody.create(MediaType.parse("application/json"),
                "{\"key\":[\"somestuff\"]}")));
        PowerMockito.when(dataRepository.getAllPaymentTypes()).thenThrow(new IOException());
        Observer<APIError> observer = apiError -> assertEquals("Unknown Error",apiError.message());
        viewModel.error.observeForever(observer);
        viewModel.getAllPaymentTypes();
    }

    class ResponseCall implements Call<PaymentResponse> {

        private Response<PaymentResponse> response;
        public ResponseCall(Response<PaymentResponse> res) {
            this.response = res;
        }

        @Override
        public Response<PaymentResponse> execute() throws IOException {
            return null;
        }

        @Override
        public void enqueue(Callback<PaymentResponse> callback) {
            callback.onResponse(this, response);

        }

        @Override
        public boolean isExecuted() {
            return false;
        }

        @Override
        public void cancel() {

        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public Call<PaymentResponse> clone() {
            return null;
        }

        @Override
        public Request request() {
            return null;
        }

        @Override
        public Timeout timeout() {
            return null;
        }
    }

}
