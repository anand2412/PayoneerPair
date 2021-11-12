package com.payoneer.payoneerpay.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.payoneer.payoneerpay.R;
import com.payoneer.payoneerpay.data.model.InputElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG ="PaymentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setUpInputDetails();
    }

    private void setUpInputDetails() {
        Intent intent = getIntent();
        List<InputElement> inputElementsList = intent.getParcelableArrayListExtra("INPUT_ELEMENT");
        LinearLayout lytInputContents = findViewById(R.id.linearLayout_content);
        Button payButton = findViewById(R.id.btn_pay);
        payButton.setOnClickListener(v -> {
            createJsonObjectAndPostData(lytInputContents, inputElementsList);
        });
        if(inputElementsList != null && inputElementsList.size() >0) {
            for(int i =0; i< inputElementsList.size(); i++) {
                addInputLayout(inputElementsList.get(i), lytInputContents);
            }
        }
    }

    private void createJsonObjectAndPostData(LinearLayout lytInputContents, List<InputElement> inputElementsList) {
        JSONObject jsonContent = new JSONObject();
        for(int i=0; i < lytInputContents.getChildCount(); i++) {
            ConstraintLayout constraintLayout = (ConstraintLayout) lytInputContents.getChildAt(i);
            TextInputLayout inputTextLayout = (TextInputLayout) constraintLayout.getChildAt(0);
            TextInputEditText inputEditText = (TextInputEditText) inputTextLayout.getChildAt(0);
            try {
                jsonContent.put(inputElementsList.get(i).getName(), inputEditText.getText());
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, jsonContent.toString());
    }

    private void addInputLayout(InputElement inputElement, LinearLayout lytInputContents) {
        View content = LayoutInflater.from(this).inflate(R.layout.input_field, null);
        TextInputEditText inputText = content.findViewById(R.id.editText);
        inputText.setHint(inputElement.getName());
        lytInputContents.addView(content);
    }
}