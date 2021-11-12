    package com.payoneer.payoneerpay.ui.view;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.payoneer.payoneerpay.R;
import com.payoneer.payoneerpay.data.api.ApiHelper;
import com.payoneer.payoneerpay.data.model.InputElement;
import com.payoneer.payoneerpay.data.model.PaymentResponseContent;
import com.payoneer.payoneerpay.ui.adapter.PaymentListAdapter;
import com.payoneer.payoneerpay.ui.viewmodel.PaymentResponseViewModel;
import com.payoneer.payoneerpay.ui.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

    public class PaymentTypeListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewPayment;
    private ProgressBar mProgressBar;
    private TextView mTvErrorText;
    private LinearLayout mLytErrorView;
    @VisibleForTesting
    ArrayList<PaymentResponseContent> mPaymentList = new ArrayList<>();
    private PaymentListAdapter mAdapter;
    private PaymentResponseViewModel mPaymentViewModel;
    private Toast toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);
        setupUI();
        setupObservers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        unregisterReceiver(networkStateReceiver);
        super.onPause();
    }

    private void setupObservers() {
        mPaymentViewModel = new ViewModelProvider(this, new ViewModelFactory(ApiHelper.getInstance())).get(PaymentResponseViewModel.class);
        mProgressBar.setVisibility(View.VISIBLE);
        mPaymentViewModel.getAllPaymentTypes();

        mPaymentViewModel.mPaymentResponse.observe(this, paymentResponse -> {
            mProgressBar.setVisibility(View.GONE);
            if(paymentResponse != null) {
                mRecyclerViewPayment.setVisibility(View.VISIBLE);
                mPaymentList.clear();
                mPaymentList.addAll(paymentResponse.getNetworks().getPaymentResponseList());
                mAdapter.notifyDataSetChanged();
            } else {
                mLytErrorView.setVisibility(View.VISIBLE);
            }
        });
        // Error handling for example 404, 500 and IOExceptions
        mPaymentViewModel.error.observe(this, apiError -> {
            mProgressBar.setVisibility(View.GONE);
            mLytErrorView.setVisibility(View.VISIBLE);
            mTvErrorText.setText(apiError.message());
        });
    }

    private void setupUI() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.payoneer_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mRecyclerViewPayment = findViewById(R.id.recyclerView_payment);
        mRecyclerViewPayment.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progressBar);
        mTvErrorText = findViewById(R.id.tv_errorText);
        mLytErrorView = findViewById(R.id.lyt_errorView);
        mAdapter = getPaymentListAdapter();
        mRecyclerViewPayment.setHasFixedSize(true);
        mRecyclerViewPayment.setAdapter(mAdapter);
    }

    private PaymentListAdapter getPaymentListAdapter() {
        return new PaymentListAdapter(this, mPaymentList, it -> {
            Intent intent = new Intent(PaymentTypeListActivity.this, PaymentActivity.class);
            List<InputElement> inputElement = (ArrayList<InputElement>) it.getInputElements();
            intent.putParcelableArrayListExtra("INPUT_ELEMENT", (ArrayList<? extends Parcelable>) inputElement);
            startActivity(intent);
        });
    }

    /**
     * broadcast receiver to check internet connection change
     */
    private BroadcastReceiver networkStateReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleOnReceive(context);
        }
    };

    @VisibleForTesting
    void handleOnReceive(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        if(ni !=null  && ni.isConnected()) { // Trigger API call once connection is back
            mLytErrorView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            mPaymentViewModel.getAllPaymentTypes();
        }
    }
}