package com.digitalpayments.android.sample;

import android.databinding.InverseMethod;
import android.text.TextUtils;
import android.widget.EditText;

import java.math.BigDecimal;

public class AmountConverter {
    @InverseMethod("toBigDecimal")
    public static String toString(EditText view, BigDecimal value) {
        if (TextUtils.isEmpty(view.getText())) {
            return null;
        }
        String inView = view.getText().toString();
        BigDecimal parsed = new BigDecimal(inView);
        if (parsed.equals(value)) {
            return view.getText().toString();
        }

        return String.valueOf(value);
    }

    public static BigDecimal toBigDecimal(EditText view, String value) {
        if (value == null || value.equals("")) {
            return null;
        }

        return new BigDecimal(value);
    }
}
