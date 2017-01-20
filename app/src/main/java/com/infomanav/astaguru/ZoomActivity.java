package com.infomanav.astaguru;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import services.Application_Constants;
import services.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;




/**
 * Created by android-javed on 26-10-2016.
 */
public class ZoomActivity extends AppCompatActivity {
    private Utility utility;

    Context context;
    TouchImageView iv_zoom;
    ImageView iv_close_img;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoomactivity);

        context = ZoomActivity.this;

        Intent intent = getIntent();
       String  str_img = intent.getStringExtra("imgpath");
        iv_zoom = (TouchImageView) findViewById(R.id.iv_zoom);

        iv_close_img = (ImageView) findViewById(R.id.iv_close_img);

        iv_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        Picasso.with(context).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + str_img)
                .into(iv_zoom);
    }
}