package com.payoneer.payoneerpay.data.model;

import java.util.List;

/**
 * POJO class for payment list item
 */
public class PaymentResponseContent {

    private String label;
    private String method;
    private Links links;
    private List<InputElement> inputElements;

    public String getLabel() {
        return label;
    }

    public String getMethod() {
        return method;
    }

    public Links getLinks() {
        return links;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public List<InputElement> getInputElements() {
        return inputElements;
    }

    public void setInputElements(List<InputElement> inputElements) {
        this.inputElements = inputElements;
    }
}
