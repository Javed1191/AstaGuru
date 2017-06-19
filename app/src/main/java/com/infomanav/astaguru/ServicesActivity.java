package com.infomanav.astaguru;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import services.Utility;

public class ServicesActivity extends AppCompatActivity
{



TextView tv_Restoration,tv_Valuation;
    ImageView  iv_Auction,iv_Consultation,iv_Restoration,iv_Valuation;
    WebView wb_auction_one,wb_auction_two,wb_Consultation_one,wb_Consultation_two,wb_Restoration_one,wb_Restoration_two,wb_valuation_one,wb_valuation_two;

RelativeLayout rel_Auction,relConsultation,relRestoration,relValuation;
    private Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        init();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);

        rel_Auction = (RelativeLayout) findViewById(R.id.rel_Auction);
                relConsultation = (RelativeLayout) findViewById(R.id.relConsultation);
                relRestoration = (RelativeLayout) findViewById(R.id.relRestoration);
                relValuation = (RelativeLayout) findViewById(R.id.relValuation);

        wb_auction_one = (WebView)findViewById(R.id.wb_auction_one) ;
        wb_auction_one.loadUrl("file:///android_asset/serives_auction_one.html");

        wb_auction_two = (WebView)findViewById(R.id.wb_auction_two) ;
        wb_auction_two.loadUrl("file:///android_asset/serives_auction_two.html");

        wb_Consultation_one = (WebView)findViewById(R.id.wb_Consultation_one) ;
        wb_Consultation_one.loadUrl("file:///android_asset/consultation_one.html");

        wb_Consultation_two = (WebView)findViewById(R.id.wb_Consultation_two) ;
        wb_Consultation_two.loadUrl("file:///android_asset/consultation_two.html");

        wb_Restoration_one = (WebView)findViewById(R.id.wb_Restoration_one) ;
        wb_Restoration_one.loadUrl("file:///android_asset/Restoration_one.html");

        wb_Restoration_two = (WebView)findViewById(R.id.wb_Restoration_two) ;
        wb_Restoration_two.loadUrl("file:///android_asset/Restoration_two.html");

        wb_valuation_one = (WebView)findViewById(R.id.wb_valuation_one) ;
        wb_valuation_one.loadUrl("file:///android_asset/servicesvaluation_one.html");

        wb_valuation_two = (WebView)findViewById(R.id.wb_valuation_two) ;
        wb_valuation_two.loadUrl("file:///android_asset/servicesvaluation_twooo.html");

        iv_Auction = (ImageView) findViewById(R.id.iv_Auction);
        iv_Consultation = (ImageView) findViewById(R.id.iv_Consultation);
        iv_Restoration = (ImageView) findViewById(R.id.iv_Restoration);
        iv_Valuation = (ImageView)findViewById(R.id.iv_Valuation);



        iv_Auction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Auction();

            }
        });

        rel_Auction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Auction();

            }
        });

        iv_Consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Consultation();
            }
        });

        relConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Consultation();
            }
        });


        iv_Restoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           Restoration();
            }
        });
        relRestoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Restoration();
            }
        });

        iv_Valuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         Valuation();
            }
        });

        relValuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Valuation();
            }
        });
        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Services");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

       ButterKnife.bind(this);







    }

    public void Auction()
    {


        if (wb_auction_one.getVisibility() == View.VISIBLE) {
            wb_auction_one.setVisibility(View.GONE);
            wb_auction_two.setVisibility(View.GONE);
            iv_Auction.setBackgroundResource(R.drawable.plus);
        } else {
            wb_auction_one.setVisibility(View.VISIBLE);
            wb_auction_two.setVisibility(View.VISIBLE);

            wb_Consultation_one.setVisibility(View.GONE);
            wb_Consultation_two.setVisibility(View.GONE);

            wb_Restoration_one.setVisibility(View.GONE);
            wb_Restoration_two.setVisibility(View.GONE);

            wb_valuation_one.setVisibility(View.GONE);
            wb_valuation_two.setVisibility(View.GONE);
            iv_Auction.setBackgroundResource(R.drawable.minus);
            iv_Consultation.setBackgroundResource(R.drawable.plus);

            iv_Restoration.setBackgroundResource(R.drawable.plus);
            iv_Valuation.setBackgroundResource(R.drawable.plus);
        }


    }

    public void Consultation()
    {


        if (wb_Consultation_one.getVisibility() == View.VISIBLE) {
            wb_Consultation_one.setVisibility(View.GONE);
            wb_Consultation_two.setVisibility(View.GONE);
            iv_Consultation.setBackgroundResource(R.drawable.plus);
        } else {
            wb_Consultation_one.setVisibility(View.VISIBLE);
            wb_Consultation_two.setVisibility(View.VISIBLE);

            wb_auction_one.setVisibility(View.GONE);
            wb_auction_two.setVisibility(View.GONE);


            wb_Restoration_one.setVisibility(View.GONE);
            wb_Restoration_two.setVisibility(View.GONE);

            wb_valuation_one.setVisibility(View.GONE);
            wb_valuation_two.setVisibility(View.GONE);
            iv_Consultation.setBackgroundResource(R.drawable.minus);

            iv_Restoration.setBackgroundResource(R.drawable.plus);
            iv_Valuation.setBackgroundResource(R.drawable.plus);
            iv_Auction.setBackgroundResource(R.drawable.plus);
        }

    }

    public void Restoration()
    {

        if (wb_Restoration_one.getVisibility() == View.VISIBLE) {
            wb_Restoration_one.setVisibility(View.GONE);
            wb_Restoration_two.setVisibility(View.GONE);
            iv_Restoration.setBackgroundResource(R.drawable.plus);
        } else {
            wb_Restoration_one.setVisibility(View.VISIBLE);
            wb_Restoration_two.setVisibility(View.VISIBLE);

            wb_Consultation_one.setVisibility(View.GONE);
            wb_Consultation_two.setVisibility(View.GONE);

            wb_auction_one.setVisibility(View.GONE);
            wb_auction_two.setVisibility(View.GONE);

            wb_valuation_one.setVisibility(View.GONE);
            wb_valuation_two.setVisibility(View.GONE);
            iv_Restoration.setBackgroundResource(R.drawable.minus);
            iv_Valuation.setBackgroundResource(R.drawable.plus);
            iv_Consultation.setBackgroundResource(R.drawable.plus);
            iv_Auction.setBackgroundResource(R.drawable.plus);
        }
    }
public void Valuation()
{

    if (wb_valuation_one.getVisibility() == View.VISIBLE) {
        wb_valuation_one.setVisibility(View.GONE);
        wb_valuation_two.setVisibility(View.GONE);
        iv_Valuation.setBackgroundResource(R.drawable.plus);
    } else {
        wb_valuation_one.setVisibility(View.VISIBLE);
        wb_valuation_two.setVisibility(View.VISIBLE);

        wb_Restoration_one.setVisibility(View.GONE);
        wb_Restoration_two.setVisibility(View.GONE);

        wb_Consultation_one.setVisibility(View.GONE);
        wb_Consultation_two.setVisibility(View.GONE);

        wb_auction_one.setVisibility(View.GONE);
        wb_auction_two.setVisibility(View.GONE);
        iv_Valuation.setBackgroundResource(R.drawable.minus);
        iv_Restoration.setBackgroundResource(R.drawable.plus);
        iv_Consultation.setBackgroundResource(R.drawable.plus);
        iv_Auction.setBackgroundResource(R.drawable.plus);
    }
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

}


}
