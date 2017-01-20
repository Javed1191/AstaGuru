package com.infomanav.astaguru;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class WorkSansBlack extends TextView {

    public WorkSansBlack(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public WorkSansBlack(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WorkSansBlack(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "WorkSans-Black.otf");
        setTypeface(tf);
    }
}