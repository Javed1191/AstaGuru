package com.infomanav.astaguru;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by android-javed on 07-10-2016.
 */

public class WorkSansLight extends TextView {

    public WorkSansLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public WorkSansLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WorkSansLight(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "WorkSans-Light.otf");
        setTypeface(tf);
    }
}
