package com.digitalpayments.android.sample;

import android.databinding.InverseMethod;
import com.digitalpayments.android.sdk.models.PaymentCategory;

public class PaymentCategoryConverter {
    @InverseMethod("toPaymentCategory")
    public static Integer toInt(PaymentCategory paymentCategory) {
        if(paymentCategory == null){
            return null;
        }
        return paymentCategory.ordinal();
    }

    public static PaymentCategory toPaymentCategory(Integer ordinal) {
        if (ordinal == null){
            return null;
        }
        return PaymentCategory.values()[ordinal];
    }
}
