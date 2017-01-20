package com.infomanav.astaguru;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.ButterKnife;
import services.Utility;

/**
 * Created by android-javed on 05-10-2016.
 */

public class How_to_buy_Activity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener
{


    public static final String API_KEY = "AIzaSyB_bApGLgdJKI_9HUq2DvSHM5oYijsm5ag";

    //https://www.youtube.com/watch?v=<VIDEO_ID>
    public static final String VIDEO_ID = "6BP8dyxncuc";
    JustifiedTextView justify,justifyone;
    private Utility utility;
    private Button btn_why_astaguru,btn_vacancies;
    private ScrollView sc_vacncies,sc_why_asta;
    private TextView tv_bidding;
    ImageView iv_estimate,iv_resrvation,iv_RESERVES,iv_byprimium,iv_participate,iv_payment,iv_bidding,iv_delicery,iv_after_sale;
    WebView wb_buy_reurvation;
    LinearLayout lin_estimate,lin_reserve,lin_byprimium,lin_participate,lin_payment,lin_delivery,lin_aftersale;
    WebView wb_bidding,wb_parti_a,wb_parti_b;
    JustifiedTextView tv_estimate_one,tv_estimate_two,tv_estimate_three,tv_estimate_four;
    JustifiedTextView tv_reserve_one,tv_reserve_two,tv_buy_one,tv_buy_two,tv_paymentone,tv_deliv_two,tv_deliv_one;
    JustifiedTextView tv_sale_a,tv_sale_b,tv_sale_c,tv_sale_d,tv_sale_e,tv_sale_f,tv_sale_g,tv_sale_h,tv_sale_i,tv_sale_j,tv_sale_k,tv_sale_l;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_buy);

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);

        YouTubePlayerSupportFragment frag =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_player_view);
        frag.initialize(API_KEY, this);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("How To Buy");


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

        Spannable wordtoSpan = new SpannableString("I know just how to whisper, And I know just how to cry,I know just where to find the answers");

        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 15, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


//        justify.setText("If you would like to stay informed of AstaGuru's upcoming events, please register with us online.");
//        justifyone.setText("If you are interested in consigning works from your collection to our next sale, please contact us at contact@astaguru.com or at the auction help desk +91 22 2204 8138 / 39");
//
//        String test = "<html>\n" +
//        "<head>\n" +
//        "<style type=\"text/css\">\n" +
//        "@font-face {\n" +
//        "    font-family: WorkSans;\n" +
//        "    src: url(\"file:///android_asset/WorkSans-Regular.otf\")\n" +
//        "}\n" +
//        "body {\n" +
//        "    font-family: WorkSans;\n" +
//        "    font-size: 85%;\n" +
//        "    text-align: justify;\n" +
//        "    color: #606060;\n" +
//        "}\n" +
//        "</style>\n" +
//        "</head>\n" +
//        "<body>\n" +
//        " Restoration is best left to the experts, considering this service entails the longevity of the artwork. Exposure to direct sunlight or moisture are deterrents and do not facilitate a conducive environmen</body>\n" +
//        "</html>";

//        wb_buy_reurvation.loadData(test, "text/html", "UTF-8");



        tv_estimate_one.setText("Estimates are based on an average market value of the lot.");
                tv_estimate_two.setText("These are provided only as a guide for buyers.");
                tv_estimate_three.setText("Buyers should not rely on estimates as a prediction of actual price.");
                tv_estimate_four.setText("Estimates do not include Buyers Premium.");

        tv_reserve_one.setText("The Reserve price is the minimum price at which the lot shall be sold.");
        tv_reserve_two.setText("The reserve price is confidential and will not be disclosed.");

        tv_paymentone.setText("Buyers will be required to complete payment within a period of 7 business days from the receipt of the invoice via email.");

        tv_deliv_one.setText("Works will be shipped within 7 days of the payment being cleared. Buyers may choose to collect their purchase from AstaGuru in Mumbai within 7 days from the date of the sale.");
        tv_deliv_two.setText("Buyers who have completed payment formalities and have not taken delivery of their art works from AstaGuru within 30 days of the completion of payment formalities will be charged demurrage @ 2% per month on the value of the artworks.");

        tv_buy_one.setText("In addition to the hammer price, the buyer agrees to pay Astaguru the buyer's premium calculated at 15% of the winning bid value on each lot.");
        tv_buy_two.setText("Service tax on the buyer's premium shall be applicable for paintings purchased within India.");



        tv_sale_a.setText("If you have won a lot you shall be informed via email after the auction has closed.");
                tv_sale_b.setText("You shall there after receive an email with the invoice stating buyers premium along with related taxes.");
                tv_sale_c.setText("If you are the winning bidder, you are legally bound to purchase the item from AstaGuru. Please note that purchases will not be shipped out until full payment has been received and cleared.");
                tv_sale_d.setText("All details for the invoice are to be provided prior to the auction.");
                tv_sale_e.setText("After the sale, the Buyer as invoiced is required to pay the amounts in full (including the additional charges).");
                tv_sale_f.setText("No lots shall be sent without payment made in full.");
                tv_sale_g.setText("Price estimates do not include any packing, insurance, shipping or handling charges, all of which will be borne by the buyer.");
                tv_sale_h.setText("Shipping will be charged on courier rates and are determined by the size, weight and destination of the package.");
                tv_sale_i.setText("Please also note for international shipments from India the additional charges calculated are only till the destination port. Import-related duties, taxes delivery and any other charges, wherever applicable, will be directly paid by the buyer.");
                tv_sale_j.setText("All duties and taxes shall be borne by the buyer.");
                tv_sale_k.setText("All sales in India shall attract 13.5% VAT on the winning bid and 14% service tax and 0.5% swachh bharat cess and 0.5% krishi kalyan cess on the buyer's premium International sales.");
                tv_sale_l.setText("There shall be no VAT and Service tax in International sales.");



//        tv_bidding.setText(Html.fromHtml(getString(R.string.html)));

//        tv_bidding.setText("test");
        String htmlAsString = getString(R.string.html);
//        wb_bidding.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);

        wb_bidding.loadUrl("file:///android_asset/bidding.html");
        wb_parti_a.setVisibility(View.VISIBLE);
        wb_parti_b.setVisibility(View.VISIBLE);
        wb_parti_a.loadUrl("file:///android_asset/wb_partic_a.html");

        wb_parti_b.loadUrl("file:///android_asset/wb_partic_b.html");
        iv_estimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_estimate.getVisibility() == View.VISIBLE) {
                    lin_estimate.setVisibility(View.GONE);
                    iv_estimate.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_estimate.setVisibility(View.VISIBLE);
                    lin_reserve.setVisibility(View.GONE);
                    lin_byprimium.setVisibility(View.GONE);
                    lin_participate.setVisibility(View.GONE);
                    wb_bidding.setVisibility(View.GONE);
                    lin_payment.setVisibility(View.GONE);
                    lin_aftersale.setVisibility(View.GONE);
                    lin_delivery.setVisibility(View.GONE);

                    iv_estimate.setBackgroundResource(R.drawable.minus);
                    iv_RESERVES.setBackgroundResource(R.drawable.plus);
                    iv_byprimium.setBackgroundResource(R.drawable.plus);
                    iv_participate.setBackgroundResource(R.drawable.plus);
                    iv_bidding.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                    iv_delicery.setBackgroundResource(R.drawable.plus);
                    iv_after_sale.setBackgroundResource(R.drawable.plus);
                }
            }
        });

        iv_RESERVES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_reserve.getVisibility() == View.VISIBLE) {
                    lin_reserve.setVisibility(View.GONE);
                    iv_RESERVES.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_estimate.setVisibility(View.GONE);
                    lin_reserve.setVisibility(View.VISIBLE);
                    lin_byprimium.setVisibility(View.GONE);
                    lin_participate.setVisibility(View.GONE);
                    wb_bidding.setVisibility(View.GONE);
                    lin_payment.setVisibility(View.GONE);
                    lin_aftersale.setVisibility(View.GONE);
                    lin_delivery.setVisibility(View.GONE);
                    iv_RESERVES.setBackgroundResource(R.drawable.minus);
                    iv_estimate.setBackgroundResource(R.drawable.plus);
                    iv_byprimium.setBackgroundResource(R.drawable.plus);
                    iv_participate.setBackgroundResource(R.drawable.plus);
                    iv_bidding.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                    iv_delicery.setBackgroundResource(R.drawable.plus);
                    iv_after_sale.setBackgroundResource(R.drawable.plus);
                }
            }
        });

        iv_byprimium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_byprimium.getVisibility() == View.VISIBLE) {
                    lin_byprimium.setVisibility(View.GONE);
                    iv_byprimium.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_estimate.setVisibility(View.GONE);
                    lin_reserve.setVisibility(View.GONE);
                    lin_byprimium.setVisibility(View.VISIBLE);
                    lin_participate.setVisibility(View.GONE);
                    wb_bidding.setVisibility(View.GONE);
                    lin_payment.setVisibility(View.GONE);
                    lin_aftersale.setVisibility(View.GONE);
                    lin_delivery.setVisibility(View.GONE);

                    iv_RESERVES.setBackgroundResource(R.drawable.plus);
                    iv_estimate.setBackgroundResource(R.drawable.plus);
                    iv_byprimium.setBackgroundResource(R.drawable.minus);
                    iv_participate.setBackgroundResource(R.drawable.plus);
                    iv_bidding.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                    iv_delicery.setBackgroundResource(R.drawable.plus);
                    iv_after_sale.setBackgroundResource(R.drawable.plus);

                }
            }
        });

        iv_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_participate.getVisibility() == View.VISIBLE) {
                    lin_participate.setVisibility(View.GONE);
                    iv_participate.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_estimate.setVisibility(View.GONE);
                    lin_reserve.setVisibility(View.GONE);
                    lin_byprimium.setVisibility(View.GONE);
                    lin_participate.setVisibility(View.VISIBLE);
                    wb_bidding.setVisibility(View.GONE);
                    lin_payment.setVisibility(View.GONE);
                    lin_aftersale.setVisibility(View.GONE);
                    lin_delivery.setVisibility(View.GONE);

                    iv_participate.setBackgroundResource(R.drawable.minus);

                    iv_RESERVES.setBackgroundResource(R.drawable.plus);
                    iv_estimate.setBackgroundResource(R.drawable.plus);
                    iv_byprimium.setBackgroundResource(R.drawable.plus);

                    iv_bidding.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                    iv_delicery.setBackgroundResource(R.drawable.plus);
                    iv_after_sale.setBackgroundResource(R.drawable.plus);

                }
            }
        });

        iv_bidding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_bidding.getVisibility() == View.VISIBLE) {
                    wb_bidding.setVisibility(View.GONE);
                    iv_bidding.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_estimate.setVisibility(View.GONE);
                    lin_reserve.setVisibility(View.GONE);
                    lin_byprimium.setVisibility(View.GONE);
                    lin_participate.setVisibility(View.GONE);
                    wb_bidding.setVisibility(View.VISIBLE);
                    lin_payment.setVisibility(View.GONE);
                    lin_aftersale.setVisibility(View.GONE);
                    lin_delivery.setVisibility(View.GONE);
                    iv_bidding.setBackgroundResource(R.drawable.minus);

                    iv_participate.setBackgroundResource(R.drawable.plus);

                    iv_RESERVES.setBackgroundResource(R.drawable.plus);
                    iv_estimate.setBackgroundResource(R.drawable.plus);
                    iv_byprimium.setBackgroundResource(R.drawable.plus);


                    iv_payment.setBackgroundResource(R.drawable.plus);
                    iv_delicery.setBackgroundResource(R.drawable.plus);
                    iv_after_sale.setBackgroundResource(R.drawable.plus);
                }
            }
        });


        iv_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_payment.getVisibility() == View.VISIBLE) {
                    lin_payment.setVisibility(View.GONE);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_estimate.setVisibility(View.GONE);
                    lin_reserve.setVisibility(View.GONE);
                    lin_byprimium.setVisibility(View.GONE);
                    lin_participate.setVisibility(View.GONE);
                    wb_bidding.setVisibility(View.GONE);
                    lin_payment.setVisibility(View.VISIBLE);
                    lin_aftersale.setVisibility(View.GONE);
                    lin_delivery.setVisibility(View.GONE);
                    iv_bidding.setBackgroundResource(R.drawable.plus);

                    iv_participate.setBackgroundResource(R.drawable.plus);

                    iv_RESERVES.setBackgroundResource(R.drawable.plus);
                    iv_estimate.setBackgroundResource(R.drawable.plus);
                    iv_byprimium.setBackgroundResource(R.drawable.plus);


                    iv_payment.setBackgroundResource(R.drawable.minus);
                    iv_delicery.setBackgroundResource(R.drawable.plus);
                    iv_after_sale.setBackgroundResource(R.drawable.plus);
                }
            }
        });

        iv_delicery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_delivery.getVisibility() == View.VISIBLE) {
                    lin_delivery.setVisibility(View.GONE);
                    iv_delicery.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_estimate.setVisibility(View.GONE);
                    lin_reserve.setVisibility(View.GONE);
                    lin_byprimium.setVisibility(View.GONE);
                    lin_participate.setVisibility(View.GONE);
                    wb_bidding.setVisibility(View.GONE);
                    lin_payment.setVisibility(View.GONE);
                    lin_aftersale.setVisibility(View.GONE);
                    lin_delivery.setVisibility(View.VISIBLE);
                    iv_delicery.setBackgroundResource(R.drawable.minus);
                    iv_bidding.setBackgroundResource(R.drawable.plus);

                    iv_participate.setBackgroundResource(R.drawable.plus);

                    iv_RESERVES.setBackgroundResource(R.drawable.plus);
                    iv_estimate.setBackgroundResource(R.drawable.plus);
                    iv_byprimium.setBackgroundResource(R.drawable.plus);


                    iv_payment.setBackgroundResource(R.drawable.plus);

                    iv_after_sale.setBackgroundResource(R.drawable.plus);
                }
            }
        });

        iv_after_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_aftersale.getVisibility() == View.VISIBLE) {
                    lin_aftersale.setVisibility(View.GONE);
                    iv_after_sale.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_estimate.setVisibility(View.GONE);
                    lin_reserve.setVisibility(View.GONE);
                    lin_byprimium.setVisibility(View.GONE);
                    lin_participate.setVisibility(View.GONE);
                    wb_bidding.setVisibility(View.GONE);
                    lin_payment.setVisibility(View.GONE);
                    lin_aftersale.setVisibility(View.VISIBLE);
                    lin_delivery.setVisibility(View.GONE);

                    iv_bidding.setBackgroundResource(R.drawable.plus);

                    iv_participate.setBackgroundResource(R.drawable.plus);

                    iv_RESERVES.setBackgroundResource(R.drawable.plus);
                    iv_estimate.setBackgroundResource(R.drawable.plus);
                    iv_byprimium.setBackgroundResource(R.drawable.plus);


                    iv_payment.setBackgroundResource(R.drawable.plus);
                    iv_delicery.setBackgroundResource(R.drawable.plus);
                    iv_after_sale.setBackgroundResource(R.drawable.minus);
                }
            }
        });
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if(null== player) return;

        // Start buffering
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
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
//
//        justify  = (JustifiedTextView) findViewById(R.id.justify);
//
//        justifyone= (JustifiedTextView) findViewById(R.id.justifyone);
//        tv_bidding = (TextView) findViewById(R.id.tv_bidding);
        iv_estimate = (ImageView) findViewById(R.id.iv_estimate);
        iv_after_sale = (ImageView) findViewById(R.id.iv_after_sale) ;
        iv_RESERVES = (ImageView) findViewById(R.id.iv_RESERVES) ;
//        wb_buy_reurvation = (WebView)findViewById(R.id.wb_buy_reurvation) ;
        iv_byprimium = (ImageView) findViewById(R.id.iv_byprimium) ;
        iv_payment = (ImageView) findViewById(R.id.iv_payment) ;
        iv_participate= (ImageView) findViewById(R.id.iv_participate) ;
        iv_bidding= (ImageView) findViewById(R.id.iv_bidding) ;
        iv_delicery= (ImageView) findViewById(R.id.iv_delicery) ;
        lin_estimate  = (LinearLayout) findViewById(R.id.lin_estimate) ;
        lin_reserve  = (LinearLayout) findViewById(R.id.lin_reserve) ;
        lin_byprimium  = (LinearLayout) findViewById(R.id.lin_byprimium);
        lin_participate = (LinearLayout) findViewById(R.id.lin_participate);
        lin_payment = (LinearLayout) findViewById(R.id.lin_payment);
        lin_delivery= (LinearLayout) findViewById(R.id.lin_delivery);
        lin_aftersale= (LinearLayout) findViewById(R.id.lin_aftersale);
        tv_estimate_one = (JustifiedTextView) findViewById(R.id.tv_estimate_one);
        tv_estimate_two = (JustifiedTextView) findViewById(R.id.tv_estimate_two);
        tv_estimate_three = (JustifiedTextView) findViewById(R.id.tv_estimate_three);
        tv_estimate_four = (JustifiedTextView) findViewById(R.id.tv_estimate_four);

        tv_reserve_one= (JustifiedTextView) findViewById(R.id.tv_reserve_one);
        tv_reserve_two= (JustifiedTextView) findViewById(R.id.tv_reserve_two);

        tv_buy_one= (JustifiedTextView) findViewById(R.id.tv_buy_one);
        tv_buy_two= (JustifiedTextView) findViewById(R.id.tv_buy_two);
        tv_paymentone= (JustifiedTextView) findViewById(R.id.tv_paymentone);

        tv_deliv_one= (JustifiedTextView) findViewById(R.id.tv_deliv_one);
        tv_deliv_two= (JustifiedTextView) findViewById(R.id.tv_deliv_two);

        tv_sale_a= (JustifiedTextView) findViewById(R.id.tv_sale_a);
        tv_sale_b= (JustifiedTextView) findViewById(R.id.tv_sale_b);
        tv_sale_c= (JustifiedTextView) findViewById(R.id.tv_sale_c);
        tv_sale_d= (JustifiedTextView) findViewById(R.id.tv_sale_d);
        tv_sale_e= (JustifiedTextView) findViewById(R.id.tv_sale_e);
        tv_sale_f= (JustifiedTextView) findViewById(R.id.tv_sale_f);
        tv_sale_g= (JustifiedTextView) findViewById(R.id.tv_sale_g);
        tv_sale_h= (JustifiedTextView) findViewById(R.id.tv_sale_h);
        tv_sale_i= (JustifiedTextView) findViewById(R.id.tv_sale_i);
        tv_sale_j= (JustifiedTextView) findViewById(R.id.tv_sale_j);
        tv_sale_k= (JustifiedTextView) findViewById(R.id.tv_sale_k);
        tv_sale_l= (JustifiedTextView) findViewById(R.id.tv_sale_l);


        wb_bidding = (WebView)findViewById(R.id.wb_bidding) ;

        wb_parti_a= (WebView)findViewById(R.id.wb_parti_a) ;
        wb_parti_b= (WebView)findViewById(R.id.wb_parti_b) ;
    }


}


