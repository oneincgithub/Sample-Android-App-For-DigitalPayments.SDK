package com.digitalpayments.paymentform.android.sample;

import android.databinding.InverseMethod;
import com.digitalpayments.paymentform.android.sdk.models.AmountContext;

public class AmountContextConverter {
    @InverseMethod("toAmountContext")
    public static Integer toInt(AmountContext amountContext) {
        if(amountContext == null){
            return null;
        }
        return amountContext.ordinal();
    }

    public static AmountContext toAmountContext(Integer ordinal) {
        if (ordinal == null){
            return null;
        }
        return AmountContext.values()[ordinal];
    }
}
