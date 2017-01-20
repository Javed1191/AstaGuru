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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import services.Utility;

public class ServicesActivity extends AppCompatActivity
{



TextView tv_Restoration,tv_Valuation;
    ImageView  iv_Auction,iv_Consultation,iv_Restoration,iv_Valuation;
    WebView wb_auction_one,wb_auction_two,wb_Consultation_one,wb_Consultation_two,wb_Restoration_one,wb_Restoration_two,wb_valuation_one,wb_valuation_two;


    private Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);

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
        });

        iv_Consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });


        iv_Restoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });


        iv_Valuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        init();

//        tv_Auction.setText("Established in the year 2008 AstaGuru, India's premium online auction house caters to a global clientele by conducting auctions for Modern & Contemporary Indian Art and have diversified over the period of time and curate auctions for Collectibles & Antiques as well. With an overall art management experience spanning over three decades we have amalgamated all our industry related knowledge and have molded it to function on a digital structure.\n" +
//                "\n" +
//                "With the world becoming one big market place we provide a safe and secure platform ensuring the ambitions of art collectors are achieved. With a well trained and highly efficient team, artworks & collectible antiques are filtered and only the best creations are part of our auctions. Art is always born with a soul and it is our prerogative to safeguard its sanctity.");

//        tv_Consultation.setText("\n" +
//                "\n" +
//                "Our guiding force, Mr Vickram Sethi is an art connoisseur and has been associated with the world of art for over three decades. His valued insights are crucial, he not only takes into account the provenance but also has developed a keen eye to deduce and ascertain works with immense potential and seminal attributes. Art gets intertwined within the home it dwells in and its owner, we take pride in consulting and presiding this \n" +
//                "communion of joy.");

//        tv_Restoration.setText("\n" +
//                "\n" +
//                " ");

//        tv_Valuation.setText("\n" +
//                "\n" +
//                "");


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
