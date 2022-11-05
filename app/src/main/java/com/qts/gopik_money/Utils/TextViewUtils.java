package com.qts.gopik_money.Utils;

import static android.content.ContentValues.TAG;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

public class TextViewUtils {
    public static Spannable getColoredString(String mString, int colorId) {
        Spannable spannable = new SpannableString(mString);
        spannable.setSpan(new ForegroundColorSpan(colorId), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Log.d(TAG,spannable.toString());
        return spannable;
    }
    private TextViewUtils() {
        throw new IllegalStateException("TextViewUtils class");
    }

}
