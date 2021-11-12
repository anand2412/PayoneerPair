package com.payoneer.payoneerpay.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * POJO to receive list object in network
 */
public class Networks {

    @SerializedName("applicable")
    private List<PaymentResponseContent> paymentResponseList;

    public List<PaymentResponseContent> getPaymentResponseList() {
        return paymentResponseList;
    }
}
