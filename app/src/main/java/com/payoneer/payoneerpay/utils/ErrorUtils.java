package com.payoneer.payoneerpay.utils;

import com.payoneer.payoneerpay.data.api.RetrofitBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Class to parse unknown error from response body
 */
public class ErrorUtils {

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                RetrofitBuilder.getRetrofitInstance()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            APIError apiError = new APIError();
            apiError.setMessage("Unknown Error");
            apiError.setStatusCode(1000);
            return apiError;
        }

        return error;
    }
}
