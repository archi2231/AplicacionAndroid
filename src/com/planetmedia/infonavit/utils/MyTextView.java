package com.planetmedia.infonavit.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

    Context context;
    String ttfName;

    String TAG = getClass().getName();

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        for (int i = 0; i < attrs.getAttributeCount(); i++) {

            this.ttfName = attrs.getAttributeValue(
                    "http://schemas.android.com/apk/res/com.planetmedia.infonavit", "ttf_name");
            init();
        }

    }

    private void init() {
        Typeface font = Typeface.createFromAsset(context.getAssets(), ttfName);
        setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
    }

}
