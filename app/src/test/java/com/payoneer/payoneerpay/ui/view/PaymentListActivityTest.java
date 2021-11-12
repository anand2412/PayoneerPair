package com.payoneer.payoneerpay.ui.view;

import androidx.recyclerview.widget.RecyclerView;

import com.payoneer.payoneerpay.BuildConfig;
import com.payoneer.payoneerpay.R;
import com.payoneer.payoneerpay.data.model.Links;
import com.payoneer.payoneerpay.data.model.PaymentResponse;
import com.payoneer.payoneerpay.data.model.PaymentResponseContent;
import com.payoneer.payoneerpay.ui.adapter.PaymentListAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml", packageName = "com.payoneer.payoneerpay")
public class PaymentListActivityTest {

    private PaymentTypeListActivity activity;
    private RecyclerView paymentRecyclerView;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(PaymentTypeListActivity.class).setup().get();
    }
    @Test
    public void ensureRecyclerViewVisible() {
        paymentRecyclerView = activity.findViewById(R.id.recyclerView_payment);
        assertThat(paymentRecyclerView,notNullValue());
    }

    @Test
    public void clickItem() {
        activity.mPaymentList.clear();
        PaymentResponseContent paymentResponseContent = new PaymentResponseContent();
        paymentResponseContent.setLabel("American Express");
        paymentResponseContent.setLinks(new Links());
        paymentResponseContent.setMethod("credit card");
        activity.mPaymentList.add(paymentResponseContent);
        PaymentListAdapter adapter = (PaymentListAdapter) paymentRecyclerView.getAdapter();
        adapter.notifyDataSetChanged();
        paymentRecyclerView.getChildAt(0).performClick();
        assertThat(ShadowToast.getTextOfLatestToast().toString(), equalToIgnoringCase("American Express"));
    }
}
