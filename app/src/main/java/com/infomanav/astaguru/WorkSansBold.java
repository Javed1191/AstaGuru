package com.infomanav.astaguru;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;



public class WorkSansBold extends TextView {

    public WorkSansBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public WorkSansBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WorkSansBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "WorkSans-Bold.otf");
        setTypeface(tf);
    }
}