
package com.digitalpayments.paymentform.android.sample;

import android.databinding.InverseMethod;
import com.digitalpayments.paymentform.android.sdk.models.ThemeType;

public class ThemeTypeConverter {
    @InverseMethod("toThemeType")
    public static Integer toInt(ThemeType themeType) {
        if(themeType == null){
            return null;
        }
        return themeType.ordinal();
    }

    public static ThemeType toThemeType(Integer ordinal) {
        if (ordinal == null){
            return null;
        }
        return ThemeType.values()[ordinal];
    }
}
