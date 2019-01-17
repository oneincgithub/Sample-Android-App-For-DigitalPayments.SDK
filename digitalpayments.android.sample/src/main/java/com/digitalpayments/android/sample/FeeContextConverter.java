package com.digitalpayments.android.sample;

import android.databinding.InverseMethod;
import com.digitalpayments.android.sdk.models.FeeContext;

public class FeeContextConverter {
    @InverseMethod("toFeeContext")
    public static Integer toInt(FeeContext paymentContext) {
        if(paymentContext == null){
            return null;
        }
        return paymentContext.ordinal();
    }

    public static FeeContext toFeeContext(Integer ordinal) {
        if (ordinal == null){
            return null;
        }
        return FeeContext.values()[ordinal];
    }
}
