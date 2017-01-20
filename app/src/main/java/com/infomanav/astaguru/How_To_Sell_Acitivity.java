package com.infomanav.astaguru;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import services.Utility;

/**
 * Created by android-javed on 01-10-2016.
 */

public class How_To_Sell_Acitivity extends AppCompatActivity
{
   WebView wb_sell_payment,wb_sell_comition,wb_sell_contract,wb_sell_price,wb_sell_logistics,wb_sell_decition,wb_sell_evaluation;

ImageView iv_evalution,iv_deci_sell,iv_logistics,iv_reserve_price,iv_sell_contract,iv_sell_commition,iv_payment;

    private Utility utility;
    private Button btn_reach_us,btn_sell;
    private LinearLayout lin_contact_us,lin_reach_us;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_sell);
        init();
       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/


        iv_evalution = (ImageView) findViewById(R.id.iv_evalution) ;
        iv_deci_sell= (ImageView) findViewById(R.id.iv_deci_sell) ;
        iv_logistics= (ImageView) findViewById(R.id.iv_logistics) ;
        iv_reserve_price= (ImageView) findViewById(R.id.iv_reserve_price) ;
        iv_sell_contract= (ImageView) findViewById(R.id.iv_sell_contract) ;
        iv_sell_commition= (ImageView) findViewById(R.id.iv_sell_commition) ;
        iv_payment= (ImageView) findViewById(R.id.iv_payment) ;



        wb_sell_evaluation = (WebView)findViewById(R.id.wb_sell_evaluation) ;
        wb_sell_evaluation.loadUrl("file:///android_asset/sell_evaluation.html");

        wb_sell_decition = (WebView)findViewById(R.id.wb_sell_decition) ;
        wb_sell_decition.loadUrl("file:///android_asset/sell_decition.html");

        wb_sell_logistics = (WebView)findViewById(R.id.wb_sell_logistics) ;
        wb_sell_logistics.loadUrl("file:///android_asset/sell_logistics.html");

        wb_sell_price = (WebView)findViewById(R.id.wb_sell_price) ;
        wb_sell_price.loadUrl("file:///android_asset/sell_price.html");

        wb_sell_contract = (WebView)findViewById(R.id.wb_sell_contract) ;
        wb_sell_contract.loadUrl("file:///android_asset/sell_contract.html");

        wb_sell_comition = (WebView)findViewById(R.id.wb_sell_comition) ;
        wb_sell_comition.loadUrl("file:///android_asset/sell_comition.html");

        wb_sell_payment = (WebView)findViewById(R.id.wb_sell_payment) ;
        wb_sell_payment.loadUrl("file:///android_asset/sell_payment.html");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("How To Sell");




        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);
        btn_sell.setText("Submit Details");
        btn_sell.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ButterKnife.bind(this);


        iv_evalution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_evaluation.getVisibility() == View.VISIBLE) {
                    wb_sell_evaluation.setVisibility(View.GONE);
                    iv_evalution.setBackgroundResource(R.drawable.plus);
                } else {
                    wb_sell_evaluation.setVisibility(View.VISIBLE);

                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract.setVisibility(View.GONE);
                    wb_sell_comition.setVisibility(View.GONE);
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.minus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_sell_commition.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });


        iv_deci_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_decition.getVisibility() == View.VISIBLE) {
                    wb_sell_decition.setVisibility(View.GONE);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                } else {
                    wb_sell_evaluation.setVisibility(View.GONE);

                    wb_sell_decition.setVisibility(View.VISIBLE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract.setVisibility(View.GONE);
                    wb_sell_comition.setVisibility(View.GONE);
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.minus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_sell_commition.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });

        iv_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_logistics.getVisibility() == View.VISIBLE) {
                    wb_sell_logistics.setVisibility(View.GONE);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                } else {
                    wb_sell_evaluation.setVisibility(View.GONE);

                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.VISIBLE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract.setVisibility(View.GONE);
                    wb_sell_comition.setVisibility(View.GONE);
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.minus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_sell_commition.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });
        iv_reserve_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_price.getVisibility() == View.VISIBLE) {
                    wb_sell_price.setVisibility(View.GONE);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                } else {
                    wb_sell_evaluation.setVisibility(View.GONE);

                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.VISIBLE);
                    wb_sell_contract.setVisibility(View.GONE);
                    wb_sell_comition.setVisibility(View.GONE);
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.minus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_sell_commition.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });

        iv_sell_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_contract.getVisibility() == View.VISIBLE) {
                    wb_sell_contract.setVisibility(View.GONE);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                } else {
                    wb_sell_evaluation.setVisibility(View.GONE);

                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract.setVisibility(View.VISIBLE);
                    wb_sell_comition.setVisibility(View.GONE);
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.minus);
                    iv_sell_commition.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });

        iv_sell_commition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_comition.getVisibility() == View.VISIBLE) {
                    wb_sell_comition.setVisibility(View.GONE);
                    iv_sell_commition.setBackgroundResource(R.drawable.plus);
                } else {
                    wb_sell_evaluation.setVisibility(View.GONE);

                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract.setVisibility(View.GONE);
                    wb_sell_comition.setVisibility(View.VISIBLE);
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_sell_commition.setBackgroundResource(R.drawable.minus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });
        iv_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_payment.getVisibility() == View.VISIBLE) {
                    wb_sell_payment.setVisibility(View.GONE);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                } else {
                    wb_sell_evaluation.setVisibility(View.GONE);

                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract.setVisibility(View.GONE);
                    wb_sell_comition.setVisibility(View.GONE);
                    wb_sell_payment.setVisibility(View.VISIBLE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_sell_commition.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.minus);
                }
            }
        });

        btn_sell.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(How_To_Sell_Acitivity.this,Sellers_DetailsActivity.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close)
        {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }


    public void init()
    {
        utility = new Utility(getApplicationContext());
        btn_sell = (Button) findViewById(R.id.btn_sell);


    }


}

