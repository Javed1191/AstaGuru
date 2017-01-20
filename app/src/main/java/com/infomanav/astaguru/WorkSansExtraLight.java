package com.infomanav.astaguru;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by android-javed on 07-10-2016.
 */

public class WorkSansExtraLight extends TextView {

    public WorkSansExtraLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public WorkSansExtraLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WorkSansExtraLight(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "WorkSansExtra-Light.otf");
        setTypeface(tf);
    }
}
