package com.payoneer.payoneerpay.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.payoneer.payoneerpay.R;
import com.payoneer.payoneerpay.data.model.PaymentResponse;
import com.payoneer.payoneerpay.data.model.PaymentResponseContent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.ViewHolder> {

    private final ArrayList<PaymentResponseContent> mPaymentList;
    private static Context mContext;
    private final OnItemClickListener listener;

    public PaymentListAdapter(Context context, ArrayList<PaymentResponseContent> paymentTypeList, OnItemClickListener listener){
        mPaymentList = paymentTypeList;
        mContext = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.payment_list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mPaymentList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mPaymentList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(PaymentResponseContent item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgViewLogo;
        private final TextView tvTitle;
        private final TextView tvMethod;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewLogo= itemView.findViewById(R.id.ivPaymentLogo);
            tvTitle= itemView.findViewById(R.id.tvTitle);
            tvMethod= itemView.findViewById(R.id.tvMethod);
        }

        public void bind(final PaymentResponseContent response, final OnItemClickListener listener) {
            tvTitle.setText(response.getLabel());
            tvMethod.setText(response.getMethod());
            //Glide to load images
            Glide.with(mContext).load(response.getLinks().getLogo())
                    .placeholder(R.drawable.ic_noimg)
                    .error(R.drawable.ic_error_image)
                    .fallback(R.drawable.ic_noimg)
                    .into(imgViewLogo);
            itemView.setOnClickListener(v -> listener.onItemClick(response));
        }
    }

}
