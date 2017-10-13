package com.infomanav.astaguru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import adapter.MyPurchasesAdpter;
import adapter.NotificationAdapter;
import interfaces.OnBidResult;
import model_classes.*;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 24-10-2016.
 */

public class Lot_Detail_Page extends AppCompatActivity {
    String Online, Str_full_name, str_productdate, Prdescription;
    String f_doller, f_pro_id, str_estamiate;
    String rs_value, f_lot, usvalue, value_for_cmpr;
    String trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us, tlot, tvalue;
    String str_rs_amount, str_us_amount;
    String rupee_value;
    private Utility utility;
    LinearLayout lin_countdown;
    Context mContext;
    Button btn_proxy_bid_lot, btn_bid_now_lot;
    SessionData data;
    TextView tv_bid_histrory, tv_bidding;
    private TextView tvDay, tvHour, tvMinute, tvSecond, tv_countdown;
    String image, str_msg;
    String product_id, strPastAuctionUrl;
    LinearLayout iv_leftarrow, lay_art_details,lin_bidhistory,lay_bought_in;
    ImageView lot_image, iv_zoom, iv_one, iv_two, iv_addgallary, iv_artwork;
    TextView tv_artworksize, tv_title, tv_title_sub, tv_current_lot, tv_next_lot, tv_artist, tv_category, tv_medium, tv_year,
            tv_size, tv_estimate, tv_additonal_info, tv_desc;
    TextView tv_addinfo_one, tv_art_size, tv_addinfo_three,tv_tax, tv_read_more, tv_type, tv_change_currency,
            tv_winning_bid_prizrs,tv_winning_bid_price_title,tv_bought_in;
    EditText edt_proxy;
    AlertDialog bid_now, bid_proxy, dilog_alert,dilog_bid_access,dilog_alert_out_of_bid;
    JustifiedTextView tv_abt_artist;
    String test1, test2;
    String currency_type, str_productid, str_title, str_description, str_artistid, str_collectors, str_thumbnail, str_image, str_productsize, str_Bidpricers, str_Bidpriceus;
    String reference, str_FirstName, str_LastName, str_Profile, start;
    WebView wb_bidding, wb_biddingtwo;
    String str_category, str_medium, str_date, str_pricers, str_priceus;
    public boolean is_us = false,is_bought_in=false;
    ProgressDialog pDialog;
    String currentDate, Ulastname, Ufirst_name, MyUserID, Userid, fragment_type, Auction_id, Thumbnail, Reference,
            Bidclosingtime, OldPriceUs, OldPriceRs, Auctionid, HumanFigure, str_Bidclosingtime, str_CurrentDate,
            Auctionname,medium,FirstName,LastName,Profile,category;
    private RelativeLayout rel_desc;
    private LinearLayout lay_winning_bid,lay_bids,lay_date_time;
    int height=0,width=0;
    DisplayMetrics displaymetrics;
    private int mInterval = 10000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    RequestQueue requestQueue;
    private MakeBid makeBid;
    private ShowFullZoomImage showFullZoomImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot_detail);
        mContext = Lot_Detail_Page.this;
        data = new SessionData(mContext);
         Userid = data.getObjectAsString("userid");
        displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        mHandler = new Handler();
        requestQueue = Volley.newRequestQueue(Lot_Detail_Page.this);
        makeBid = new MakeBid(Lot_Detail_Page.this);
        showFullZoomImage = new ShowFullZoomImage(mContext);



       // f_doller = "59";
        f_pro_id =product_id;



        utility = new Utility(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setTitle("");

        toolbar.setTitle("");

        setSupportActionBar(toolbar);
//        startRepeatingTask();



        btn_bid_now_lot = (Button) findViewById(R.id.btn_bid_now_lot);

        btn_proxy_bid_lot = (Button) findViewById(R.id.btn_proxy_bid_lot);
        iv_addgallary= (ImageView) findViewById(R.id.iv_addgallary);
        iv_two = (ImageView) findViewById(R.id.iv_two);
        iv_one = (ImageView) findViewById(R.id.iv_one);
        lot_image = (ImageView) findViewById(R.id.lot_image);

        iv_artwork = (ImageView) findViewById(R.id.iv_artwork);
        iv_leftarrow = (LinearLayout) findViewById(R.id.iv_leftarrow);

        lin_bidhistory= (LinearLayout) findViewById(R.id.lin_bidhistory);
        iv_zoom = (ImageView) findViewById(R.id.iv_zoom);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title_sub = (TextView) findViewById(R.id.tv_title_sub);
        tv_current_lot = (TextView) findViewById(R.id.tv_current_lot);
        tv_next_lot = (TextView) findViewById(R.id.tv_next_lot);
        tv_artist = (TextView) findViewById(R.id.tv_artist);
        wb_bidding = (WebView)findViewById(R.id.wb_bidding) ;
        wb_biddingtwo = (WebView)findViewById(R.id.wb_biddingtwo) ;
        tv_medium = (TextView) findViewById(R.id.tv_medium);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_size = (TextView) findViewById(R.id.tv_size);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_estimate = (TextView) findViewById(R.id.tv_estimate);
        tv_additonal_info = (TextView) findViewById(R.id.tv_additonal_info);
        tv_addinfo_one = (TextView) findViewById(R.id.tv_addinfo_one);
        tv_bidding = (TextView) findViewById(R.id.tv_bidding);
//        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_abt_artist = (JustifiedTextView) findViewById(R.id.tv_abt_artist);
        tv_read_more = (TextView) findViewById(R.id.tv_read_more);
        tv_art_size= (TextView) findViewById(R.id.tv_art_size);
        lin_countdown = (LinearLayout) findViewById(R.id.lin_countdown);
        tv_winning_bid_prizrs = (TextView) findViewById(R.id.tv_winning_bid_prizrs);
        tv_bought_in = (TextView) findViewById(R.id.tv_bought_in);
        lay_bought_in = (LinearLayout) findViewById(R.id.lay_bought_in);

        tv_bid_histrory = (TextView) findViewById(R.id.tv_bid_histrory);
        tv_tax = (TextView) findViewById(R.id.tv_tax);

        tvDay = (TextView) findViewById(R.id.txtTimerDaylist);
        tvHour = (TextView) findViewById(R.id.txtTimerHourlist);
        tvMinute = (TextView) findViewById(R.id.txtTimerMinutelist);
        tvSecond = (TextView) findViewById(R.id.txtTimerSecondlist);
        tv_countdown = (TextView) findViewById(R.id.tv_countdown);
        tv_winning_bid_price_title = (TextView) findViewById(R.id.tv_winning_bid_price_title);
        lay_date_time = (LinearLayout) findViewById(R.id.lay_date_time);

        rel_desc = (RelativeLayout) findViewById(R.id.rel_desc);
        lay_art_details = (LinearLayout) findViewById(R.id.lay_art_details);

        lay_winning_bid = (LinearLayout) findViewById(R.id.lay_winning_bid);
        lay_bids = (LinearLayout) findViewById(R.id.lay_bids);



        iv_one.setImageResource(R.drawable.rupee);
        iv_two.setImageResource(R.drawable.rupee);


        Intent intent = getIntent();

        if(intent.getExtras()!=null)
        {
            product_id = intent.getStringExtra("Str_id");
            reference = intent.getStringExtra("reference");
            fragment_type = intent.getStringExtra("fragment");
            MyUserID= intent.getStringExtra("MyUserID");
            currentDate= intent.getStringExtra("currentDate");
            HumanFigure= intent.getStringExtra("HumanFigure");
            Auctionname = intent.getStringExtra("Auctionname");
            medium = intent.getStringExtra("medium");
            FirstName = intent.getStringExtra("FirstName");
            LastName = intent.getStringExtra("LastName");
            Profile = intent.getStringExtra("Profile");
            category = intent.getStringExtra("category");
            is_us = intent.getBooleanExtra("is_us",false);
            is_bought_in = intent.getBooleanExtra("is_bought_in",false);
            f_doller = intent.getStringExtra("dollar_rate");
            str_FirstName = FirstName;
            str_LastName = LastName;

            if(!Auctionname.equalsIgnoreCase("Collectibles Auction"))
            {
                String str_medium_trim = medium.trim();

                tv_medium.setText(str_medium_trim);

                lay_art_details.setVisibility(View.VISIBLE);
                rel_desc.setVisibility(View.GONE);

                tv_title.setText(FirstName + " "+LastName);

//                            tv_current_bid.setText(str_Bidpricers);
//                            tv_next_bid.setText(str_Bidpriceus);

                tv_artist.setText(FirstName + " "+LastName);

                if(Profile.isEmpty())
                {

                    Profile = "Artist Profile Not Available.";
                    start= "Artist Profile Not Available.";
                }
                else
                {
                    String filename = Profile;

                    System.out.println("start" + filename);
                    int iend = filename.indexOf(".");
                    if(filename.length()>200)
                    {
                        start = filename.substring(0,200);
                        test1 = "<html>\n" +
                                "<head>\n" +
                                "<style type=\"text/css\">\n" +
                                "@font-face {\n" +
                                "    font-family: WorkSans;\n" +
                                "    src: url(\"file:///android_asset/WorkSans-Regular.otf\")\n" +
                                "}\n" +
                                "body {\n" +
                                "    font-family: WorkSans;\n" +
                                "    font-size: 85%;\n" +
                                "    text-align: justify;\n" +
                                "    color: #606060;\n" +
                                "margin: 0;"+
                                "padding: 0;"+
                                "}\n" +
                                "</style>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                " "+start+"</body>\n" +
                                "</html>";

//                                tv_abt_artist.setText(start);

                        wb_bidding.loadData(test1, "text/html; charset=utf-8", "utf-8");
                        wb_bidding.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        test1 = "<html>\n" +
                                "<head>\n" +
                                "<style type=\"text/css\">\n" +
                                "@font-face {\n" +
                                "    font-family: WorkSans;\n" +
                                "    src: url(\"file:///android_asset/WorkSans-Regular.otf\")\n" +
                                "}\n" +
                                "body {\n" +
                                "    font-family: WorkSans;\n" +
                                "    font-size: 85%;\n" +
                                "    text-align: justify;\n" +
                                "    color: #606060;\n" +
                                "margin: 0;"+
                                "padding: 0;"+
                                "}\n" +
                                "</style>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                " "+filename+"</body>\n" +
                                "</html>";

//                                tv_abt_artist.setText(start);

                        wb_bidding.loadData(test1, "text/html; charset=utf-8", "utf-8");
                        wb_bidding.setVisibility(View.VISIBLE);
                    }



                }

            }
            else
            {
                lay_art_details.setVisibility(View.GONE);
                rel_desc.setVisibility(View.VISIBLE);

                if(Profile.isEmpty())
                {

                    Profile = "Artist Profile Not Available.";
                    start= "Artist Profile Not Available.";
                }
                else
                {
                    String filename = Profile;
                    System.out.println("start" + filename);
                    int iend = filename.indexOf(".");
                    if(iend>0)
                    {
                        start = filename.substring(0,iend);
                        test1 = "<html>\n" +
                                "<head>\n" +
                                "<style type=\"text/css\">\n" +
                                "@font-face {\n" +
                                "    font-family: WorkSans;\n" +
                                "    src: url(\"file:///android_asset/WorkSans-Regular.otf\")\n" +
                                "}\n" +
                                "body {\n" +
                                "    font-family: WorkSans;\n" +
                                "    font-size: 85%;\n" +
                                "    text-align: justify;\n" +
                                "    color: #606060;\n" +
                                "margin: 0;"+
                                "padding: 0;"+
                                "}\n" +
                                "</style>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                " "+start+"</body>\n" +
                                "</html>";

//                                tv_abt_artist.setText(start);

                        wb_bidding.loadData(test1, "text/html; charset=utf-8", "utf-8");
                        wb_bidding.setVisibility(View.VISIBLE);
                    }



                }

            }

            if(is_bought_in)
            {
                tv_bought_in.setVisibility(View.VISIBLE);
                lay_bought_in.setVisibility(View.GONE);
            }
            else
            {
                tv_bought_in.setVisibility(View.GONE);
                lay_bought_in.setVisibility(View.VISIBLE);
            }

            if (fragment_type.equalsIgnoreCase("past"))
            {
                btn_bid_now_lot.setVisibility(View.GONE);
                btn_proxy_bid_lot.setVisibility(View.GONE);
                tv_additonal_info.setVisibility(View.INVISIBLE);
                tv_tax.setVisibility(View.VISIBLE);

                lay_winning_bid.setVisibility(View.VISIBLE);
                lay_bids.setVisibility(View.GONE);
                lin_countdown.setVisibility(View.GONE);
                iv_addgallary.setVisibility(View.GONE);
                lin_bidhistory.setVisibility(View.GONE);

            }
            else if (fragment_type.equalsIgnoreCase("upcomming"))
            {
                Auction_id = intent.getStringExtra("Auction_id");
                btn_bid_now_lot.setVisibility(View.GONE);
                btn_proxy_bid_lot.setVisibility(View.VISIBLE);
                tv_tax.setVisibility(View.GONE);
                iv_addgallary.setVisibility(View.VISIBLE);

                lin_countdown.setVisibility(View.GONE);
                lin_bidhistory.setVisibility(View.GONE);
                lay_winning_bid.setVisibility(View.VISIBLE);
                lay_bids.setVisibility(View.GONE);
                lin_countdown.setVisibility(View.GONE);
                btn_bid_now_lot.setVisibility(View.INVISIBLE);

                tv_winning_bid_price_title.setText("Start Price");

            }
            else if (fragment_type.equalsIgnoreCase("Current"))
            {
                String user = data.getObjectAsString("login");
                String userid = data.getObjectAsString("userid");
                tv_tax.setVisibility(View.GONE);

                lay_winning_bid.setVisibility(View.GONE);
                lay_bids.setVisibility(View.VISIBLE);
                lin_countdown.setVisibility(View.VISIBLE);
                iv_addgallary.setVisibility(View.VISIBLE);

                if(userid.equals(MyUserID))
                {
                    btn_bid_now_lot.setVisibility(View.GONE);
                    btn_proxy_bid_lot.setVisibility(View.GONE);
                    tv_bidding.setVisibility(View.VISIBLE);
                }
                else
                {
                    btn_bid_now_lot.setVisibility(View.VISIBLE);
                    btn_proxy_bid_lot.setVisibility(View.VISIBLE);
                    tv_bidding.setVisibility(View.GONE);
                }
            }


        }

        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Lot:"+reference);

        Typeface type = Typeface.createFromAsset(getAssets(), "WorkSans-Medium.otf");
        tool_text.setTypeface(type);




            // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        iv_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", str_image);
                mContext.startActivity(intent);*/

                showFullZoomImage.showImage(str_image);
            }
        });

        iv_addgallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(mContext,Before_Login_Activity.class);

                    mContext.startActivity(intent);

                }
                else {

                    String userid = data.getObjectAsString("userid");
                    AddToGallary(product_id, userid);
                }

            }
        });


        lot_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullZoomImage.showImage(str_image);
            }
        });

        tv_additonal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currency = data.getObjectAsString("currency");

                if (currency.equals("USD"))
                {
                    currency_type = "USD";
                }
                else
                {
                    currency_type = "INR";
                }

                Intent intent = new Intent(Lot_Detail_Page.this, AdditionalCharges_Activity.class);

                intent.putExtra("str_FirstName", FirstName);
                intent.putExtra("str_LastName", LastName);
                intent.putExtra("str_category", category);
                intent.putExtra("str_medium", medium);
                intent.putExtra("str_date", str_productdate);
                intent.putExtra("str_productsize", str_productsize);
                intent.putExtra("str_image", str_image);
                intent.putExtra("str_pricers", str_pricers);
                intent.putExtra("str_priceus", str_priceus);
                intent.putExtra("str_refrene", reference);
                intent.putExtra("currency_type", is_us);
                intent.putExtra("str_estimate",str_collectors);
                intent.putExtra("Auctionname",Auctionname);
                intent.putExtra("Prdescription",Prdescription);

//                intent.putExtra("str_lot",);

                startActivity(intent);
            }
        });
        tv_bid_histrory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currency = data.getObjectAsString("currency");

                if (currency.equals("USD"))
                {
                    currency_type = "USD";
                }
                else
                {
                    currency_type = "INR";
                }
                Intent intentproductid = new Intent(mContext, Bid_History.class);
                intentproductid.putExtra("Str_id", product_id);
                intentproductid.putExtra("str_FirstName", FirstName+" "+ LastName);
                intentproductid.putExtra("str_category", category);
                intentproductid.putExtra("str_medium", medium);
                intentproductid.putExtra("str_date", str_productdate);
                intentproductid.putExtra("str_productsize", str_productsize);
                intentproductid.putExtra("str_istimate", str_estamiate);
                intentproductid.putExtra("str_collectors", str_collectors);
                intentproductid.putExtra("str_lot", reference.trim());
                intentproductid.putExtra("str_image", str_image);
                intentproductid.putExtra("fragment_type", fragment_type);
                intentproductid.putExtra("MyUserID", MyUserID);
                intentproductid.putExtra("str_LastName", LastName);
                intentproductid.putExtra("str_FirstName", FirstName);
                intentproductid.putExtra("str_image", str_image);
                intentproductid.putExtra("str_pricers", str_pricers);
                intentproductid.putExtra("str_priceus", str_priceus);
                intentproductid.putExtra("str_refrene", reference);
                intentproductid.putExtra("currency_type", is_us);
                intentproductid.putExtra("product_id", product_id);
                intentproductid.putExtra("currentDate", currentDate);
                intentproductid.putExtra("str_Bidclosingtime", str_Bidclosingtime);
                intentproductid.putExtra("Auctionname",Auctionname);
                intentproductid.putExtra("Prdescription",Prdescription);
                intentproductid.putExtra("doller_rate",f_doller);

                mContext.startActivity(intentproductid);
            }
        });

        iv_leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_bid_now_lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(mContext,Before_Login_Activity.class);
                    intent.putExtra("str_from","adpter");
                    mContext.startActivity(intent);

                }
                else {


                    String MobileVerified = data.getObjectAsString("MobileVerified");
                    String EmailVerified = data.getObjectAsString("EmailVerified");
                    String confirmbid = data.getObjectAsString("confirmbid");
                    if (MobileVerified.equalsIgnoreCase("true") && EmailVerified.equalsIgnoreCase("true"))
                    {

                        if (confirmbid.equals("1"))
                        {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
                        dialogBuilder.setView(dialogView);

                        final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                        final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                        final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                        final TextView iv_icon = (TextView) dialogView.findViewById(R.id.iv_icon);

                        final TextView tv_bidlot = (TextView) dialogView.findViewById(R.id.tv_bidlot);

                        if (is_us) {

                            int int_bid_us = 0;


                            int myNum = Integer.parseInt(str_pricers);
                            if (myNum < 10000000) {
                                int_bid_us = MakeBid.Get_10_value(str_priceus);
                            } else {
                                int_bid_us = MakeBid.Get_5_value(str_priceus);
                            }

                            str_us_amount = String.valueOf(int_bid_us);
                            str_rs_amount = String.valueOf(int_bid_us);
                            String str_int_us = NumberFormat.getNumberInstance(Locale.US).format(int_bid_us);
                            tv_bidlot.setText("Lot " + reference.trim());
                            tv_bidvalue.setText(str_int_us);
                            iv_icon.setText("US$");
                        } else {

                            int int_bid_rs = 0;


                            int myNum = Integer.parseInt(str_pricers);
                            if (myNum < 10000000) {
                                int_bid_rs = MakeBid.Get_10_value(str_pricers);
                            } else {
                                int_bid_rs = MakeBid.Get_5_value(str_pricers);
                            }

                            str_rs_amount = String.valueOf(int_bid_rs);
                            str_us_amount = String.valueOf(int_bid_rs);
                            rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_bid_rs);
                            tv_bidlot.setText("Lot " + reference.trim());
                            tv_bidvalue.setText(rs_value);
                            iv_icon.setText("₹");
                        }


//

                        tv_confim.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String rs_amount = str_rs_amount;
                                final String us_amount = str_us_amount;
                                final String productid = product_id;
                                final String userid = data.getObjectAsString("userid");
                                final String dollerrate = f_doller;
                                f_lot = reference;

                                Log.i("Rupees_Amount",rs_amount);
                               // String str_us = Get_US_value(dollerrate, rs_amount);
                                final String str_us = MakeBid.Get_US_value(dollerrate, rs_amount);

                                Log.i("Us_Amount",str_us);


                                if (is_us) {


                                    if (utility.checkInternet())
                                    {
                                        final String Str_productid = product_id;
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);

                                        double a = Double.parseDouble(us_amount);
                                        double b = Double.parseDouble(dollerrate);

                                        double str_rsonus = a * b;

                                        int int_proxy_new = (int) Math.round(str_rsonus);
                                        final String proxy_new_us = Integer.toString(int_proxy_new);


                                        //GetData("US", Str_productid, proxy_new_us, productid, userid, dollerrate, us_amount, f_lot,str_title);

                                        makeBid.GetData("US",Str_productid, proxy_new_us, productid, userid, dollerrate, us_amount, f_lot,str_title,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,is_us);
                                        makeBid.bidResult(new OnBidResult() {
                                            @Override
                                            public void bidResult(String currentStatus, String msg)
                                            {

                                                if(currentStatus.equals("2"))
                                                {
                                                    bid_now.dismiss();
                                                    makeBid.GetData("US",Str_productid, proxy_new_us, productid, userid, dollerrate, us_amount, f_lot,str_title,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,is_us);
                                                    getTimeCountdown(Str_productid);
                                                }
                                                else
                                                {
                                                    bid_now.dismiss();
                                                    getTimeCountdown(Str_productid);
                                                }


                                            }

                                        });

                                    } else {

                                        show_dailog("Please Check Internet Connection");
                                    }

                                } else {


                                    if (utility.checkInternet()) {
                                        final String Str_productid = product_id;
                                        //GetData("RS", Str_productid, rs_amount, productid, userid, dollerrate, str_us, f_lot,str_title);
                                        makeBid.GetData("RS", Str_productid, us_amount, productid, userid, dollerrate, str_us, f_lot,str_title,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,is_us);
                                        makeBid.bidResult(new OnBidResult() {
                                            @Override
                                            public void bidResult(String currentStatus, String msg)
                                            {
                                                if(currentStatus.equals("2"))
                                                {
                                                    bid_now.dismiss();
                                                    makeBid.GetData("RS", Str_productid, us_amount, productid, userid, dollerrate, str_us, f_lot,str_title,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,is_us);
                                                   // currentAuction.startRepeatingTask();
                                                    getTimeCountdown(Str_productid);
                                                }
                                                else
                                                {
                                                    bid_now.dismiss();
                                                    //currentAuction.startRepeatingTask();
                                                    getTimeCountdown(Str_productid);
                                                }
                                            }

                                        });


//                                BidNow(rs_amount, productid, userid, dollerrate, proxy_new_us);

                                    } else {

                                        show_dailog("Please Check Internet Connection");
                                    }


                                }


                            }
                        });
                        bid_now = dialogBuilder.create();
                        bid_now.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bid_now.dismiss();
                            }
                        });


                        bid_now.show();
                            Window window = bid_now.getWindow();
                            window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
                            window.setGravity(Gravity.CENTER);
                        bid_now.setCanceledOnTouchOutside(false);

                        }
                        else
                        {
                           // Toast.makeText(mContext, "You do not have bidding access", Toast.LENGTH_SHORT).show();
                            //show_dailog("You don't have access to Bid Please contact Astaguru.");
                            bidAccessDialog("Astaguru","You don't have access to Bid Please contact Astaguru.");
                        }
                    }
                    else
                    {
                        Intent intent = new Intent(mContext,Verification_Activity.class);
                        intent.putExtra("Activity","Login");
                        mContext.startActivity(intent);

                    }
                }
            }
        });

        btn_proxy_bid_lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false") || status.isEmpty() || status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(mContext, Before_Login_Activity.class);
                    intent.putExtra("str_from", "adpter");
                    mContext.startActivity(intent);

                }
                else
                {
                    String MobileVerified = data.getObjectAsString("MobileVerified");
                    String EmailVerified = data.getObjectAsString("EmailVerified");
                    String confirmbid = data.getObjectAsString("confirmbid");
                    if (MobileVerified.equalsIgnoreCase("true") && EmailVerified.equalsIgnoreCase("true"))
                    {

                        if (confirmbid.equals("1"))
                        {


                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    LayoutInflater inflater = (LayoutInflater) mContext
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogView = inflater.inflate(R.layout.dailog_proxybid, null);
                    dialogBuilder.setView(dialogView);

                    final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                    final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                    edt_proxy = (EditText) dialogView.findViewById(R.id.edt_proxy);
                    final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                    final TextView iv_iconproxy = (TextView) dialogView.findViewById(R.id.iv_iconproxy);
                    final TextView tv_bit_text = (TextView) dialogView.findViewById(R.id.username);
                            final LinearLayout lay_bid_values = (LinearLayout) dialogView.findViewById(R.id.lay_bid_values);
                            final TextView tv_confirm_bid = (TextView) dialogView.findViewById(R.id.tv_confirm_bid);

                     if(fragment_type.equalsIgnoreCase("upcomming"))
                     {
                                tv_bit_text.setText("Start Price");
                     }


                    final TextView tv_proxylot = (TextView) dialogView.findViewById(R.id.tv_proxylot);

                    if (is_us) {
                        double int_proxy_bid_us = 0;


                        double myNum = Double.parseDouble(str_pricers);
                        if (myNum < 10000000) {
                            int_proxy_bid_us = MakeBid.Get_10_value(str_priceus);
                        } else {
                            int_proxy_bid_us = MakeBid.Get_5_value(str_priceus);
                        }

                        value_for_cmpr = String.valueOf(int_proxy_bid_us);
                        String str_int_xus="";
                        if(fragment_type.equalsIgnoreCase("upcomming"))
                        {
                            str_int_xus = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(str_priceus));
                        }
                        else {
                            str_int_xus = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_us);
                        }

                        tv_bidvalue.setText(str_int_xus);
                        tv_proxylot.setText("Lot " + reference.trim());
                        iv_iconproxy.setText("US$ ");
                    } else {


                        int int_proxy_bid_rs = 0;


                        double myNum = Double.parseDouble(str_pricers);
                        if (myNum < 10000000) {
                            int_proxy_bid_rs = MakeBid.Get_10_value(str_pricers);
                        } else {
                            int_proxy_bid_rs = MakeBid.Get_5_value(str_pricers);
                        }

                        value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                        if(fragment_type.equalsIgnoreCase("upcomming"))
                        {
                            rs_value = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(str_pricers));
                        }
                        else
                        {
                            rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);
                        }


                        rs_value =  rupeeFormat(rs_value);

                        str_rs_amount = String.valueOf(int_proxy_bid_rs);
                        tv_proxylot.setText("Lot " + reference.trim());
                        tv_bidvalue.setText(rs_value);
                        iv_iconproxy.setText("₹ ");
                    }

                    tv_confim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String dollerRate = f_doller;
                            double doller = Double.parseDouble(dollerRate);

                            f_lot = reference;

                            String siteUserID = data.getObjectAsString("userid");
                            final String productID = product_id;
                            String str_ProxyAmt = edt_proxy.getText().toString().trim();

                            if (str_ProxyAmt.isEmpty())
                            {
                                Toast.makeText(mContext, "Please Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();

                            } else {

                                String str_ProxyAmtus = Double.toString(Double.parseDouble(str_ProxyAmt) / doller);

                                double fb = Double.parseDouble(dollerRate);
                                double rl = Double.parseDouble(str_ProxyAmt);

                                double str_ProxyAmtus_new = rl / fb;
                                int int_proxy_new = (int) Math.round(str_ProxyAmtus_new);
                                String proxy_amt_us = String.valueOf(int_proxy_new);


                                String entered_value = edt_proxy.getText().toString();
                                String bid_value = value_for_cmpr;
                                double int_entered_value = Double.parseDouble(entered_value);
                                double int_bid_value = Double.parseDouble(bid_value);

                                if (int_entered_value >= int_bid_value)
                                {

                                    if(lay_bid_values.getVisibility()==View.VISIBLE)
                                    {
                                        lay_bid_values.setVisibility(View.GONE);
                                        tv_confirm_bid.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        if (is_us)
                                        {

                                            String str_Proxy_for_us = edt_proxy.getText().toString();

                                       /* int fb1 = Integer.parseInt(dollerRate);
                                        int rl1 = Integer.parseInt(str_Proxy_for_us);

                                        int str_ProxyAmtrs = rl1 * fb1;

                                        String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);*/

                                            double fb1 = Double.parseDouble(dollerRate);
                                            double rl1 = Double.parseDouble(str_Proxy_for_us);

                                            double str_ProxyAmtrs = rl1 * fb1;

                                            //String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                            int_proxy_new = (int) Math.round(str_ProxyAmtrs);
                                            String proxy_amt_for_rs = String.valueOf(int_proxy_new);


                                            // Toast.makeText(mContext, "from US", Toast.LENGTH_SHORT).show();

                                            String Createdby = data.getObjectAsString("name");
                                            if (utility.checkInternet())
                                            {

                                                if (fragment_type.equals("upcomming"))
                                                {
                                                    //ProxyBid_for_upcoming(siteUserID, productID, proxy_amt_for_rs, proxy_amt_for_rs, "0", "", Createdby, Auction_id);

                                                    makeBid.proxyBidForUpcoming(siteUserID, productID, proxy_amt_for_rs, str_Proxy_for_us, Createdby,Auction_id, f_lot,Auctionname);

                                                    makeBid.bidResult(new OnBidResult()
                                                    {
                                                        @Override
                                                        public void bidResult(String currentStatus, String msg)
                                                        {
                                                            bid_proxy.dismiss();
                                                            getTimeCountdown(productID);
                                                        }
                                                    });


                                                }
                                                else
                                                {
                                                    // ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_for_rs, f_lot);
                                                    makeBid.ProxyBid(proxy_amt_for_rs, productID, siteUserID, dollerRate, str_ProxyAmt, f_lot,Bidclosingtime,
                                                            Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,FirstName,LastName,str_title);

                                                    makeBid.bidResult(new OnBidResult() {
                                                        @Override
                                                        public void bidResult(String currentStatus, String msg) {
                                                            bid_proxy.dismiss();
                                                            getTimeCountdown(productID);
                                                        }
                                                    });
                                                }


                                            } else {

                                                show_dailog("Please Check Internet Connection");
                                            }

                                        }
                                        else
                                        {
                                            // Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                            if (utility.checkInternet()) {
                                                String Createdby = data.getObjectAsString("name");

                                                if (fragment_type.equals("upcomming")) {
                                                    // ProxyBid_for_upcoming(siteUserID, productID, str_ProxyAmt, proxy_amt_us, "0", "", Createdby, Auction_id);

                                                    makeBid.proxyBidForUpcoming(siteUserID, productID, str_ProxyAmt, proxy_amt_us, Createdby,Auction_id, f_lot,Auctionname);

                                                    makeBid.bidResult(new OnBidResult() {
                                                        @Override
                                                        public void bidResult(String currentStatus, String msg) {
                                                            bid_proxy.dismiss();
                                                            getTimeCountdown(productID);
                                                        }
                                                    });

                                                } else {
                                                    // ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot);

                                                    makeBid.ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot,Bidclosingtime,
                                                            Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,FirstName,LastName,str_title);

                                                    makeBid.bidResult(new OnBidResult() {
                                                        @Override
                                                        public void bidResult(String currentStatus, String msg) {
                                                            bid_proxy.dismiss();
                                                            getTimeCountdown(productID);
                                                        }
                                                    });
                                                }

                                            } else {

                                                show_dailog("Please Check Internet Connection");
                                            }

                                        }
                                    }



                                } else {

                                   // Toast.makeText(mContext, "Proxy Must Greater Than Current Bid Value", Toast.LENGTH_SHORT).show();

                                    double myNum = Double.parseDouble(entered_value);
                                    if (myNum < 10000000) {
                                        Toast.makeText(mContext, "Proxy Bid value must be greater by at least 10% of current price.", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(myNum < 10000000)
                                    {
                                        Toast.makeText(mContext, "Proxy Bid value must be greater by at least 5% of current price", Toast.LENGTH_SHORT).show();
                                    }
                                }


                            }

                        }
                    });
                    bid_proxy = dialogBuilder.create();
                    bid_proxy.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bid_proxy.dismiss();
                        }
                    });


                    bid_proxy.show();
                            Window window = bid_proxy.getWindow();
                            window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
                            window.setGravity(Gravity.CENTER);
                    bid_proxy.setCanceledOnTouchOutside(false);


                }
                else
                {
                    //Toast.makeText(mContext, "You do not have bidding access", Toast.LENGTH_SHORT).show();
                    //show_dailog("You don't have access to Bid Please contact Astaguru.");
                    bidAccessDialog("Astaguru","You don't have access to Bid Please contact Astaguru.");
                }
            }
            else
            {
                Intent intent = new Intent(mContext, Verification_Activity.class);
                intent.putExtra("Activity", "Login");
                mContext.startActivity(intent);

            }
                }

            }
        });

        strPastAuctionUrl =Application_Constants.Main_URL+"acution/" + product_id + "?api_key="+ Application_Constants.API_KEY+"&filter=online%20=%2027&related=*";
        getUpcomingAuction();

    }


    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try
            {
                getTimeCountdown(product_id);

            }
            finally {

                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };
    public void startRepeatingTask() {
        mStatusChecker.run();
    }

    public void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }




    private void AddToGallary(String productID,String siteUserID) {

        if (utility.checkInternet()) {

            String addtogallaryUrl = Application_Constants.Main_URL_Procedure+"spAddToGallery("+productID+","+siteUserID+")?api_key="+ Application_Constants.API_KEY;
            System.out.println("strPastAuctionUrl " + addtogallaryUrl);
            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, addtogallaryUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);
                    Toast.makeText(mContext, "The Lot has been added to your auction gallery.", Toast.LENGTH_SHORT).show();


                    String str_json = result;


                }
            });
        }
        else
        {
            Toast.makeText(mContext, "Please Check Internet Connection.",Toast.LENGTH_LONG)
                    .show();
        }

    }
    private void getUpcomingAuction() {

        if (utility.checkInternet()) {
            //final ArrayList<Department> feedsArrayList = new ArrayList<Department>();

            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(this);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);
                    System.out.println("strPastAuctionUrl" + strPastAuctionUrl);

                    String str_json = result;


                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);

                            Online = jobject.getString("Online");

                            Str_full_name = str_FirstName+str_LastName;
                            Prdescription = jobject.getString("Prdescription");
                            str_productid = jobject.getString("productid");
                            str_title = jobject.getString("title");
                            str_description = jobject.getString("description");
                            str_artistid = jobject.getString("artistid");
                            str_collectors = jobject.getString("collectors");
                            str_thumbnail = jobject.getString("thumbnail");
                            str_image = jobject.getString("image");
                            str_productsize = jobject.getString("productsize");
                            str_pricers = jobject.getString("pricers");
                            str_priceus = jobject.getString("priceus");
                            str_Bidclosingtime = jobject.getString("Bidclosingtime");
                            str_productdate = jobject.getString("productdate");
                            str_estamiate = jobject.getString("estamiate");
                            str_title = jobject.getString("title");
                            HumanFigure = jobject.getString("HumanFigure");


                            str_CurrentDate = "2017-01-10 19:55:27";

                            tv_title_sub.setText(str_title);
                            str_productsize =  str_productsize.replaceAll("(\\r|\\n)", "");
                            tv_desc.setText(Html.fromHtml(Prdescription));
                            tv_year.setText(str_productdate);
                            tv_size.setText(str_productsize+" in");
                            tv_art_size.setText(str_productsize+" in");
                            String img_name = str_thumbnail;

                            Thumbnail = img_name.replace("paintings/", "");


                            if(fragment_type.equalsIgnoreCase("past"))
                            {
                                str_pricers = claculatePercentage(str_pricers);
                                str_priceus = claculatePercentage(str_priceus);
                            }


                            Reference=reference;
                            OldPriceRs=str_pricers;
                            OldPriceUs=str_priceus;
                            Auctionid=Online;
                            Bidclosingtime=str_Bidclosingtime;
                            Ufirst_name=str_FirstName;
                            Ulastname=str_LastName;

                            String str_without_html= Html.fromHtml(str_description).toString();
                            tv_addinfo_one.setText(str_without_html);

                            Picasso.with(getApplicationContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + str_image).placeholder(R.drawable.placeholder)
                                    .into(lot_image);

                            Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + HumanFigure)
                                    .into(iv_artwork);

                            String currency = data.getObjectAsString("currency");
                            System.out.println("currency" + currency);

                            String formated_str_pricers = rupeeFormat(str_pricers);
                            //tv_winning_bid_prizrs.setText("₹ "+ formated_str_pricers);



                           // if (currency.equals("USD"))
                            if (is_us)
                            {
//                                tv_type.setText("USD");
                                int next_bid_doller=0;
                                System.out.println("currency" + "usd");
                                System.out.println("currency" +str_priceus);
                                is_us = true;
                                tv_estimate.setText(str_estamiate);



                                int int_str = Integer.parseInt(str_priceus);
                                String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
                                System.out.println("currency" +str_ustest);

                                tv_winning_bid_prizrs.setText("US$ "+ str_ustest);
                                tv_current_lot.setText("$ "+str_ustest);
                                int myNum = Integer.parseInt(str_pricers);
                                if (myNum<10000000)
                                {
                                    next_bid_doller = MakeBid.Get_10_value(str_priceus);
                                }
                                else
                                {
                                    next_bid_doller = MakeBid.Get_5_value(str_priceus);
                                }
                                String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(next_bid_doller);

                                tv_next_lot.setText("$ "+valuebyerprimium1);
                                iv_one.setImageResource(R.drawable.doller);
                                iv_two.setImageResource(R.drawable.doller);
                            }
                            else
                            {
//                                tv_type.setText("INR");

                                int next_bid = 0;
                                System.out.println("currency" + "inr");
                                System.out.println("currency" +str_pricers);
                                tv_estimate.setText(str_collectors);
                                is_us = false;
                                tv_winning_bid_prizrs.setText("₹ "+ formated_str_pricers);

                                int int_str = Integer.parseInt(str_pricers);
                                String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
                                String str_amt_rss = rupeeFormat(str_ustest);
                                tv_current_lot.setText("₹ "+str_amt_rss);

                                int myNum = Integer.parseInt(str_pricers);
                                if (myNum<10000000)
                                {
                                    next_bid = MakeBid.Get_10_value(str_pricers);
                                }
                                else
                                {
                                    next_bid = MakeBid.Get_5_value(str_pricers);
                                }

                                String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(next_bid);
                                String str_amt_rs = rupeeFormat(valuebyerprimium1);
                                tv_next_lot.setText("₹ "+str_amt_rs);
                                iv_one.setImageResource(R.drawable.rupee);
                                iv_two.setImageResource(R.drawable.rupee);
                            }




                            tv_read_more.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (tv_read_more.getText().equals("Read More"))
                                    {
//                                        tv_abt_artist.setText(str_Profile);


                                        test2 = "<html>\n" +
                                                "<head>\n" +
                                                "<style type=\"text/css\">\n" +
                                                "@font-face {\n" +
                                                "    font-family: WorkSans;\n" +
                                                "    src: url(\"file:///android_asset/WorkSans-Regular.otf\")\n" +
                                                "}\n" +
                                                "body {\n" +
                                                "    font-family: WorkSans;\n" +
                                                "    font-size: 85%;\n" +
                                                "    text-align: justify;\n" +
                                                "    color: #606060;\n" +
                                                "margin: 0;"+
                                                "padding: 0;"+
                                                "}\n" +
                                                "</style>\n" +
                                                "</head>\n" +
                                                "<body>\n" +
                                                ""+Profile+"</body>\n" +
                                                "</html>";
                                        wb_biddingtwo.loadData(test2, "text/html; charset=utf-8", "utf-8");
                                        wb_bidding.setVisibility(View.GONE);
                                        wb_biddingtwo.setVisibility(View.VISIBLE);
                                        System.out.println("str_Profile" + Profile);
                                        tv_read_more.setText("Read Less");
                                    }
                                    else if (tv_read_more.getText().equals("Read Less"))
                                    {
                                        wb_bidding.loadData(test1, "text/html; charset=utf-8", "UTF-8");
                                        wb_biddingtwo.setVisibility(View.GONE);
                                        wb_bidding.setVisibility(View.VISIBLE);
//                                        tv_abt_artist.setText(start);
                                        System.out.println("str_Profile" + start);
                                        tv_read_more.setText("Read More");

                                    }


                                }
                            });


                            if(!fragment_type.equalsIgnoreCase("upcomming"))
                            {
                                try
                                {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    // Here Set your Event Date
                                    String str = Bidclosingtime;

                                    //String[] splitStr = str.split("\\s+");

                                    Date eventDate = dateFormat.parse(str);

                                    String strcurrentDate =currentDate;
                                    Date currentDate = dateFormat.parse(strcurrentDate);

                                    boolean is_after = currentDate.before(eventDate);

                                    if (is_after)
                                    {
                                        long diff = eventDate.getTime()- currentDate.getTime();
                                        long days = diff / (24 * 60 * 60 * 1000);
                                        diff -= days * (24 * 60 * 60 * 1000);
                                        long hours = diff / (60 * 60 * 1000);
                                        diff -= hours * (60 * 60 * 1000);
                                        long minutes = diff / (60 * 1000);
                                        diff -= minutes * (60 * 1000);
                                        long seconds = diff / 1000;

                                        tvDay.setText("" + String.format("%02d",days));
                                        tvHour.setText("" + String.format("%02d", hours));
                                        tvMinute.setText("" + String.format("%02d", minutes));
                                        tvSecond.setText("" + String.format("%02d", seconds));

                                        btn_bid_now_lot.setEnabled(true);
                                        btn_proxy_bid_lot.setEnabled(true);

                                        btn_bid_now_lot.setBackgroundResource(R.color.black);
                                        btn_proxy_bid_lot.setBackgroundResource(R.color.black);

                                        lin_bidhistory.setVisibility(View.VISIBLE);

                                    }
                                    else
                                    {
                                        lin_bidhistory.setVisibility(View.GONE);

                                        tv_countdown.setVisibility(View.VISIBLE);
                                        lay_date_time.setVisibility(View.GONE);
                                   /* tvDay.setText("" + String.format("%02d","0"));
                                    tvHour.setText("" + String.format("%02d", "0"));
                                    tvMinute.setText("" + String.format("%02d", "0"));
                                    tvSecond.setText("" + String.format("%02d", "0"));*/

                                        btn_bid_now_lot.setBackgroundResource(R.color.grey05);
                                        btn_proxy_bid_lot.setBackgroundResource(R.color.grey05);
                                        btn_bid_now_lot.setEnabled(false);
                                        btn_proxy_bid_lot.setEnabled(false);
                                    }
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }



                            startRepeatingTask();


                        } else {
                            Toast.makeText(mContext, "This may be server issue",Toast.LENGTH_LONG)
                                    .show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();

                        Toast.makeText(mContext, e.getMessage(),Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                String status = data.getObjectAsString("login");

                if (status.equalsIgnoreCase("true"))
                {
                    Intent intent = new Intent(Lot_Detail_Page.this,MyAstaGuru_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(Lot_Detail_Page.this,Before_Login_Activity.class);
                    startActivity(intent);
                }

                return true;
            case R.id.action_search:
                Intent intent = new Intent(Lot_Detail_Page.this,Search_Activity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private int Get_10_value(String amt)
    {

        double amount = Double.parseDouble(amt);

        double byerprimium = (amount / 100.0f) * 10;

        double sum = amount + byerprimium;

        int intbyerprimium = (int) sum;

        return intbyerprimium;
    }
    private int Get_5_value(String amt)
    {

        double amount = Double.parseDouble(amt);

        double byerprimium = (amount / 100.0f) * 5;

        double sum = amount + byerprimium;

        int intbyerprimium = (int) sum;

        return intbyerprimium;
    }
    private String Get_US_value(String dollerrate,String rs_amount)
    {

        int fb = Integer.parseInt(dollerrate);
        int rl = Integer.parseInt(rs_amount);

        int str_Proxy_new = rl / fb;

        String proxy_new_us = Integer.toString(str_Proxy_new);

        return proxy_new_us;
    }



    private void ProxyBid(String userProxyAmount,String productID,String siteUserID,String dollerRate,String dollerAmt,String f_lot) {
        Bidclosingtime = Bidclosingtime.replace(" ","%20");
        Auctionid = Auctionid.trim();
        if (utility.checkInternet()) {
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+","+Thumbnail+","+Reference+","+OldPriceRs+","+OldPriceUs+","+Auctionid+","+Bidclosingtime+","+FirstName+","+LastName+")?api_key="+ Application_Constants.API_KEY;
            System.out.println("Proxy Bid URL " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            final   String userproxy = userProxyAmount;
            final String lot_no = f_lot;


            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);


                    String str_json = result,msg;

                    try {
                        if (str_json != null)
                        {

                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {
                                JSONObject Obj = jsonArray.getJSONObject(0);
                                String currentStatus = Obj.getString("currentStatus");

                                if(Obj.has("msg"))
                                {
                                    msg = Obj.getString("msg");
                                }

//                                String emailID = Obj.getString("emailID");
//                                String mobilrNum = Obj.getString("mobilrNum");

                                if(currentStatus.equals("1"))
                                {

                                   // Toast.makeText(mContext, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
                                    String number = Obj.getString("mobileNum");
                                    bid_proxy.dismiss();

                                    show_dailog("Your Proxy bid submitted successfully,currently you are leading for this product");
                                    getUpcomingAuction();
                                    registerUser(lot_no,userproxy,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                   // Toast.makeText(mContext, "U r out of bid", Toast.LENGTH_SHORT).show();
                                    bid_proxy.dismiss();

                                    show_dailog("Sorry You are out off bid because already higher proxybid is their, do you want to bid again.");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                   //Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                    bid_proxy.dismiss();

                                   show_dailog("New Proxy Bid Value Should Be Greater Then Current Proxy Bid Value.");

                                }




                            } else {
                                Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }
    private void GetData(String value, String str_productid, final String rs_amount, final String productid, final String userid, final String dollerrate, final String proxy_new_us, String lot, final String str_title) {

        if (utility.checkInternet()) {
            String strPastAuctionUrl = Application_Constants.Main_URL+"Acution?api_key="+ Application_Constants.API_KEY+"&filter=productid="+str_productid+"";
            System.out.println("GetDataurl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();


            tvalue = value;
            trs_amount = rs_amount;
            tproductid = productid;
            tuserid = userid;
            tdollerrate = dollerrate;
            tproxy_new_us = proxy_new_us;

            tlot=lot;
            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);


                    String str_json = result;


                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);

                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");


                                JSONObject Obj = jsonArray.getJSONObject(0);

                                if(tvalue.equals("RS"))
                                {
                                    rupee_value = Obj.getString("pricers");
                                }
                                else
                                {

                                    rupee_value = Obj.getString("priceus");
                                }
                                int value_one = Integer.parseInt(rupee_value);
                                int value_two = Integer.parseInt(trs_amount);

                                if (value_two > value_one)
                                {


                                   // BidNow(trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot);

                                    makeBid.BidNow(trs_amount,tproductid,tuserid,tdollerrate,tproxy_new_us,tlot,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname, str_title);
                                    makeBid.bidResult(new OnBidResult() {
                                        @Override
                                        public void bidResult(String currentStatus, String msg)
                                        {
                                            bid_now.dismiss();

                                            if(currentStatus.equals("2"))
                                            {
                                                if (is_us)
                                                {
//                                                    BidNow(us_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
                                                    getTimeCountdown(tproductid);
                                                    GetData("US",tproductid,usvalue, productid, userid, dollerrate, proxy_new_us,f_lot,str_title);
                                                }
                                                else
                                                {

                                                    getTimeCountdown(tproductid);
                                                    GetData("RS",tproductid,rs_amount, productid, userid, dollerrate, proxy_new_us,f_lot,str_title);
//                                BidNow(rs_amount, productid, userid, dollerrate, proxy_new_us);
                                                }
                                            }
                                            else
                                            {
                                                getTimeCountdown(tproductid);
                                            }
                                           /* if(currentStatus.equals("2"))
                                            {
                                                bid_now.dismiss();
                                                makeBid.GetData("RS", Str_productid, us_amount, productid, userid, dollerrate, str_us, f_lot,strTitle,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,is_us);
                                                getTimeCountdown(tproductid);
                                            }
                                            else
                                            {
                                                bid_now.dismiss();
                                                getTimeCountdown(tproductid);
                                            }*/
                                        }
                                    });
                                }
                                else
                                {

//                                    Toast.makeText(mContext, "Dismiss  bidding", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    int int_proxy_bid_rselse = Get_10_value(rupee_value);


                                    rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse);

                                    str_rs_amount = String.valueOf(int_proxy_bid_rselse);

                                    Toast.makeText(mContext,"Bid Not Valid", Toast.LENGTH_SHORT).show();



                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                                    LayoutInflater inflater = (LayoutInflater) mContext
                                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
                                    dialogBuilder.setView(dialogView);

                                    final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                                    final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                                    final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                                    final TextView iv_icon = (TextView) dialogView.findViewById(R.id.iv_icon);
                                    iv_icon.setVisibility(View.GONE);

                                    if (is_us)
                                    {
                                        int int_proxy_bid_rselse_us = Get_10_value(rupee_value);


                                        String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_us);
                                        tv_bidvalue.setText("US$ "+str_int_us);
                                        iv_icon.setText("US$");
                                    }
                                    else
                                    {
                                        System.out.println("ttt"+"new value set  ");
                                        int int_proxy_bid_rselse_rs = Get_10_value(rupee_value);

                                        rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_rs);

                                        str_rs_amount = String.valueOf("₹ "+int_proxy_bid_rselse_rs);

                                        tv_bidvalue.setText(rs_value);
                                        iv_icon.setText("₹");
                                    }


                                    tv_confim.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            String rs_amount = str_rs_amount;
                                            String us_amount = usvalue;
                                            String productid = f_pro_id;
                                            String  userid = data.getObjectAsString("userid");
                                            String  dollerrate =f_doller;

                                            int fb = Integer.parseInt(dollerrate);
                                            int rl = Integer.parseInt(rs_amount);

                                            int str_Proxy_new = rl / fb;

                                            String proxy_new_us = Integer.toString(str_Proxy_new);


                                            String status = data.getObjectAsString("login");
                                            if (status.equalsIgnoreCase("false"))
                                            {
                                                Intent intent = new Intent(mContext,Before_Login_Activity.class);
                                                mContext.startActivity(intent);

                                            }
                                            else {

                                                if (is_us)
                                                {
//                                                    BidNow(us_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
                                                    GetData("US",f_pro_id,us_amount, productid, userid, dollerrate, proxy_new_us,f_lot,str_title);
                                                }
                                                else
                                                    {

                                                    System.out.println("ttt"+"re bid ");

                                                    GetData("RS",f_pro_id,rs_amount, productid, userid, dollerrate, proxy_new_us,f_lot,str_title);
//                                BidNow(rs_amount, productid, userid, dollerrate, proxy_new_us);
                                                }

                                            }


                                        }
                                    });
                                    bid_now = dialogBuilder.create();
                                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bid_now.dismiss();
                                        }
                                    });


                                    bid_now.show();

                                    Window window = bid_now.getWindow();
                                   // window.setLayout(850, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    window.setGravity(Gravity.CENTER);
                                    bid_now.setCanceledOnTouchOutside(false);


                                }




                            } else {
                                Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private  void show_dailog(String msg)
    {

        String msgtest = msg;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dailog_aleart, null);
        dialogBuilder.setView(dialogView);

        final TextView tv_cancelx = (TextView) dialogView.findViewById(R.id.tv_cancelx);
        final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
        tv_bidvalue.setText(msgtest);

        dilog_alert = dialogBuilder.create();

        tv_cancelx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilog_alert.dismiss();
            }
        });


        dilog_alert.show();
        dilog_alert.setCanceledOnTouchOutside(false);
    }


    private void BidNow(String str_Amount,String str_productID,String str_userID,String dollerrate,String proxy_new_us,String tlot)
    {
        Bidclosingtime = Bidclosingtime.replace(" ","%20");
        Auctionid = Auctionid.trim();
        if (utility.checkInternet())
        {
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spBid("+str_Amount+","+str_productID+","+str_userID+","+dollerrate+","+proxy_new_us+","+Thumbnail+","+Reference+","+OldPriceRs+","+OldPriceUs+","+Auctionid+","+Bidclosingtime+","+FirstName+","+LastName+")?api_key="+ Application_Constants.API_KEY;
            System.out.println("Normal Bid URL " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            final   String str_lot = tlot;
            final String str_amt = str_Amount;

//          (22965,2386,5101,62,370,oct16MA-7t.jpg,7,20878,336,27   ,2017-02-04 15:24:00,B.,Prabha)?
//          (22965,2386,5101,68,337,oct16MA-7t.jpg,7,336,20878,7)?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed
//            paintings/oct16MA-7t.jpg


        System.out.println("strPastAuctionUrl " + str_Amount);
            System.out.println("strPastAuctionUrl " + str_productID);
            System.out.println("strPastAuctionUrl " + str_userID);
            System.out.println("strPastAuctionUrl " + dollerrate);
            System.out.println("strPastAuctionUrl " + proxy_new_us);

            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback()
            {
                @Override
                public void onSuccess(String result) {
                    System.out.println("resultbid" + result);

                    System.out.println("strPastAuctionUrl " + result);

                    String str_json = result,msg="";

                    try {
                        if (str_json != null)
                        {

                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {
                                JSONObject Obj = jsonArray.getJSONObject(0);
                                String currentStatus = Obj.getString("currentStatus");
                                if(Obj.has("msg"))
                                {
                                    msg = Obj.getString("msg");
                                }
                                //String emailID = Obj.getString("emailID");
                                // String mobilrNum = Obj.getString("mobileNum");


//                                Toast.makeText(mContext,currentStatus, Toast.LENGTH_SHORT).show();
                                if(currentStatus.equals("1"))
                                {

                                  //  Toast.makeText(mContext, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
                                    String number = Obj.getString("mobileNum");
                                    bid_now.dismiss();

                                    show_dailog("Your bid submitted successfully, currently you are leading for this product");
                                    getUpcomingAuction();
                                    registerUser(str_lot,str_amt,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                   // Toast.makeText(mContext, "U r out of bid", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    show_dailog("Sorry You are out off bid because already higher proxybid is their, do you want to bid again");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                   Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    //show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

                                }




                            } else {
                                bid_now.dismiss();
                                Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            bid_now.dismiss();
                            Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        bid_now.dismiss();
                        e.printStackTrace();
                    }

//                    currentAuction.call_data();
                }
            });
        }

    }



    private void ProxyBid_for_upcoming(String str_Userid,String str_Productid,String str_ProxyAmt,String str_ProxyAmtus,String str_Status,String str_Auctionid,String Createdby,String Auction_id) {

        if (utility.checkInternet()) {
            pDialog  = new ProgressDialog(mContext);
            pDialog.setMessage("Proxy Bidding ... ");
            pDialog.setCancelable(false);
            pDialog.show();
            String strPastAuctionUrl = Application_Constants.Main_URL+"ProxyAuctionDetails?api_key="+ Application_Constants.API_KEY;
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();
            params.put("Userid",str_Userid);
            params.put("Productid",str_Productid);
            params.put("ProxyAmt",str_ProxyAmt);
            params.put("ProxyAmtus",str_ProxyAmtus);
            params.put("Status",str_Status);
            params.put("Auctionid",Auction_id);
            params.put("Createdby",Createdby);
            params.put("CreatedDt","8/19/2016 6:19:19 PM");




            System.out.println("str" + str_Userid);
            System.out.println("str" + str_Productid);
            System.out.println("str" + str_ProxyAmt);
            System.out.println("str" + str_ProxyAmtus);
            System.out.println("str" + str_Status);
            System.out.println("str" + str_Auctionid);




            JSONArray resourcesArr = new JSONArray();
            resourcesArr.put(new JSONObject(params));

            Map<String, JSONArray> resourceParams = new HashMap<>();
            resourceParams.put("resource", resourcesArr);

            JSONObject requestParam = new JSONObject(resourceParams);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, strPastAuctionUrl, requestParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            pDialog.dismiss();
                            try
                            {
                                VolleyLog.v("Response:%n %s", response.toString());

                                System.out.print("str"+ response.toString());

                                JSONObject userObject = null;

                                userObject = response.getJSONArray("resource").getJSONObject(0);

//                                {"resource":[{"Proxyid":43010}]}
//                                String datadata = userObject.getString("userid");

                                Toast.makeText(mContext, "You Successfully Done Proxy Bid", Toast.LENGTH_SHORT).show();
                                bid_proxy.dismiss();
//                                currentAuction.call_data();



//                            Intent intent = new Intent(RegistrationActivity.this,Verification_Activity.class);
//                            startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.dismiss();
                            VolleyLog.e("Error: ", error.getMessage());

                            Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_SHORT);

                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(jsonObjectRequest);
        }

    }


    private void registerUser(String lot,String value,String mobile){


        String lot_number = lot;
        String bid_value = value;
        final String mob_number=mobile;

        System.out.println("mob_number " + lot_number);
        System.out.println("mob_number " + bid_value);
        System.out.println("mob_number " + mob_number);
        final String msg = "Dear User, please note you have been outbid on Lot No "+lot_number+". Next valid bid is Rs."+bid_value+". Place renewed bid on www.astaguru.com or mobile App.";

        System.out.println("msg" + msg);

        String URL_url = "http://gateway.netspaceindia.com/api/sendhttp.php?";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("mob_number " + response);
                       // Toast.makeText(mContext,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("authkey", "131841Aotn6vhT583570b5");
                params.put("mobiles",mob_number);
                params.put("message",msg);
                params.put("sender","AstGru");
                params.put("route","4");
                params.put("country","91");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);


    }
    public static String rupeeFormat(String value)
    {
        String result = "";
        char lastDigit = 0;
        try
        {
            value=value.replace(",","");
            lastDigit=value.charAt(value.length()-1);

            int len = value.length()-1;
            int nDigits = 0;
            for (int i = len - 1; i >= 0; i--)
            {
                result = value.charAt(i) + result;
                nDigits++;
                if (((nDigits % 2) == 0) && (i > 0))
                {
                    result = "," + result;
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return (result+lastDigit);
    }

    private String claculatePercentage(String strPrise)
    {
        String strBidPrise="";

        try
        {

            double dbl_bid_prise = 0,dbl_discount=0;

            dbl_bid_prise = Double.parseDouble(strPrise);
            dbl_discount = ((Double.parseDouble(strPrise)*15)/100);
            dbl_bid_prise = dbl_bid_prise+dbl_discount;

            strBidPrise = String.valueOf(Math.round(dbl_bid_prise));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return strBidPrise;
    }


    public static void makeTextViewResizable(final TextView tv,
                                             final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0,
                            lineEndIndex - expandText.length() + 1)
                            + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText()
                                            .toString(), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0,
                            lineEndIndex - expandText.length() + 1)
                            + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText()
                                            .toString(), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(
                            tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex)
                            + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText()
                                            .toString(), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(
            final String strSpanned, final TextView tv, final int maxLine,
            final String spanableText, final boolean viewMore) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (strSpanned.contains(spanableText)) {
            ssb.setSpan(
                    new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {

                            if (viewMore) {
                                tv.setLayoutParams(tv.getLayoutParams());
                                tv.setText(tv.getTag().toString(),
                                        TextView.BufferType.SPANNABLE);
                                tv.invalidate();
                                makeTextViewResizable(tv, -5, "...Read Less",
                                        false);
                                tv.setTextColor(Color.BLACK);
                            } else {
                                tv.setLayoutParams(tv.getLayoutParams());
                                tv.setText(tv.getTag().toString(),
                                        TextView.BufferType.SPANNABLE);
                                tv.invalidate();
                                makeTextViewResizable(tv, 5, "...Read More",
                                        true);
                                tv.setTextColor(Color.BLACK);
                            }

                        }
                    }, strSpanned.indexOf(spanableText),
                    strSpanned.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

   /* private void getTimeCountdown(String product_id) {

        if (utility.checkInternet()) {
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spGetBidPrice("+product_id+")?api_key="+ Application_Constants.API_KEY;
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();
            final ArrayList<Model_Notification> countryList = new ArrayList<Model_Notification>();


            ServiceHandler serviceHandler = new ServiceHandler(Lot_Detail_Page.this);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);

                    String pricers="",priceus="",Bidclosingtime="",strcurrentDate="",MyUserID="";

                    try {
                        if (result != null)
                        {
                            JSONArray jArray = new JSONArray(result);
                            for (int i = 0; i < jArray.length(); i++)
                            {
                                JSONObject Obj = jArray.getJSONObject(i);
                                // A category variables


                                pricers = Obj.getString("pricers");
                                priceus = Obj.getString("priceus");
                                Bidclosingtime = Obj.getString("Bidclosingtime");
                                strcurrentDate = Obj.getString("currentDate");
                                MyUserID = Obj.getString("MyUserID");

                            }


                            try
                            {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                // Here Set your Event Date
                                String str = Bidclosingtime;

                                //String[] splitStr = str.split("\\s+");

                                Date eventDate = dateFormat.parse(str);

                                Date currentDate = dateFormat.parse(strcurrentDate);

                                boolean is_after = currentDate.before(eventDate);

                                if (is_after)
                                {
                                    long diff = eventDate.getTime()- currentDate.getTime();
                                    long days = diff / (24 * 60 * 60 * 1000);
                                    diff -= days * (24 * 60 * 60 * 1000);
                                    long hours = diff / (60 * 60 * 1000);
                                    diff -= hours * (60 * 60 * 1000);
                                    long minutes = diff / (60 * 1000);
                                    diff -= minutes * (60 * 1000);
                                    long seconds = diff / 1000;

                                    tvDay.setText("" + String.format("%02d",days));
                                    tvHour.setText("" + String.format("%02d", hours));
                                    tvMinute.setText("" + String.format("%02d", minutes));
                                    tvSecond.setText("" + String.format("%02d", seconds));

                                    btn_bid_now_lot.setEnabled(true);
                                    btn_proxy_bid_lot.setEnabled(true);

                                    btn_bid_now_lot.setBackgroundResource(R.color.black);
                                    btn_proxy_bid_lot.setBackgroundResource(R.color.black);

                                    lin_bidhistory.setVisibility(View.VISIBLE);

                                }
                                else
                                {
                                    lin_bidhistory.setVisibility(View.GONE);

                                    tv_countdown.setVisibility(View.VISIBLE);
                                    lay_date_time.setVisibility(View.GONE);
                                   *//* tvDay.setText("" + String.format("%02d","0"));
                                    tvHour.setText("" + String.format("%02d", "0"));
                                    tvMinute.setText("" + String.format("%02d", "0"));
                                    tvSecond.setText("" + String.format("%02d", "0"));*//*

                                    btn_bid_now_lot.setBackgroundResource(R.color.grey05);
                                    btn_proxy_bid_lot.setBackgroundResource(R.color.grey05);
                                    btn_bid_now_lot.setEnabled(false);
                                    btn_proxy_bid_lot.setEnabled(false);
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });
        }

    }*/

    private void getTimeCountdown(String product_id)
    {

        if (utility.checkInternet())
        {
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spGetBidPrice("+product_id+")?api_key="+ Application_Constants.API_KEY;


            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, strPastAuctionUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response;

                            String pricers="",priceus="",Bidclosingtime="",strcurrentDate="",MyUserID="";

                            try {
                                if (result != null)
                                {
                                    JSONArray jArray = new JSONArray(result);
                                    for (int i = 0; i < jArray.length(); i++)
                                    {
                                        JSONObject Obj = jArray.getJSONObject(i);
                                        // A category variables
                                        pricers = Obj.getString("pricers");
                                        priceus = Obj.getString("priceus");
                                        Bidclosingtime = Obj.getString("Bidclosingtime");
                                        strcurrentDate = Obj.getString("currentDate");
                                        MyUserID = Obj.getString("MyUserID");
                                        str_pricers = pricers;
                                        str_priceus = priceus;


                                    }

                                    if(!fragment_type.equalsIgnoreCase("upcomming"))
                                    {
                                        try
                                        {
                                            String  userid = data.getObjectAsString("userid");
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                            // Here Set your Event Date
                                            String str = Bidclosingtime;

                                            //String[] splitStr = str.split("\\s+");

                                            Date eventDate = dateFormat.parse(str);

                                            Date currentDate = dateFormat.parse(strcurrentDate);

                                            boolean is_after = currentDate.before(eventDate);

                                            if (is_after)
                                            {
                                                long diff = eventDate.getTime()- currentDate.getTime();
                                                long days = diff / (24 * 60 * 60 * 1000);
                                                diff -= days * (24 * 60 * 60 * 1000);
                                                long hours = diff / (60 * 60 * 1000);
                                                diff -= hours * (60 * 60 * 1000);
                                                long minutes = diff / (60 * 1000);
                                                diff -= minutes * (60 * 1000);
                                                long seconds = diff / 1000;

                                                tvDay.setText("" + String.format("%02d",days));
                                                tvHour.setText("" + String.format("%02d", hours));
                                                tvMinute.setText("" + String.format("%02d", minutes));
                                                tvSecond.setText("" + String.format("%02d", seconds));

                                                btn_bid_now_lot.setEnabled(true);
                                                btn_proxy_bid_lot.setEnabled(true);

                                                btn_bid_now_lot.setBackgroundResource(R.color.black);
                                                btn_proxy_bid_lot.setBackgroundResource(R.color.black);

                                                lin_bidhistory.setVisibility(View.VISIBLE);

                                            }
                                            else
                                            {
                                                lin_bidhistory.setVisibility(View.GONE);

                                                tv_countdown.setVisibility(View.VISIBLE);
                                                lay_date_time.setVisibility(View.GONE);

                                                btn_bid_now_lot.setBackgroundResource(R.color.grey05);
                                                btn_proxy_bid_lot.setBackgroundResource(R.color.grey05);
                                                btn_bid_now_lot.setEnabled(false);
                                                btn_proxy_bid_lot.setEnabled(false);
                                            }

                                           /* if(userid.equals(MyUserID))
                                            {
                                                btn_bid_now_lot.setVisibility(View.GONE);
                                                btn_proxy_bid_lot.setVisibility(View.GONE);
                                                tv_bidding.setVisibility(View.VISIBLE);
                                            }
                                            else
                                            {
                                                btn_bid_now_lot.setVisibility(View.VISIBLE);
                                                btn_proxy_bid_lot.setVisibility(View.VISIBLE)
                                                tv_bidding.setVisibility(View.GONE)
                                            }*/

                                            if (fragment_type.equalsIgnoreCase("past"))
                                            {
                                                btn_bid_now_lot.setVisibility(View.GONE);
                                                btn_proxy_bid_lot.setVisibility(View.GONE);
                                                tv_additonal_info.setVisibility(View.INVISIBLE);
                                                tv_tax.setVisibility(View.VISIBLE);

                                                lay_winning_bid.setVisibility(View.VISIBLE);
                                                lay_bids.setVisibility(View.GONE);
                                                lin_countdown.setVisibility(View.GONE);
                                                iv_addgallary.setVisibility(View.GONE);
                                                lin_bidhistory.setVisibility(View.GONE);

                                            }
                                            else if (fragment_type.equalsIgnoreCase("upcomming"))
                                            {
                                                btn_bid_now_lot.setVisibility(View.GONE);
                                                btn_proxy_bid_lot.setVisibility(View.VISIBLE);
                                                tv_tax.setVisibility(View.GONE);

                                                lin_countdown.setVisibility(View.GONE);
                                                iv_addgallary.setVisibility(View.GONE);
                                                lin_bidhistory.setVisibility(View.GONE);
                                                lay_winning_bid.setVisibility(View.VISIBLE);
                                                lay_bids.setVisibility(View.GONE);
                                                lin_countdown.setVisibility(View.GONE);
                                                btn_bid_now_lot.setVisibility(View.INVISIBLE);

                                                tv_winning_bid_price_title.setText("Start Price");

                                            }
                                            else if (fragment_type.equalsIgnoreCase("Current"))
                                            {
                                                String user = data.getObjectAsString("login");
                                                tv_tax.setVisibility(View.GONE);

                                                lay_winning_bid.setVisibility(View.GONE);
                                                lay_bids.setVisibility(View.VISIBLE);
                                                lin_countdown.setVisibility(View.VISIBLE);
                                                iv_addgallary.setVisibility(View.VISIBLE);

                                                if(userid.equals(MyUserID))
                                                {
                                                    btn_bid_now_lot.setVisibility(View.GONE);
                                                    btn_proxy_bid_lot.setVisibility(View.GONE);


                                                    if(!is_after)
                                                    {
                                                        tv_bidding.setText("Lot Won");
                                                    }
                                                    else
                                                    {
                                                        tv_bidding.setText("You are currently the highest bidder.");

                                                    }
                                                    tv_bidding.setVisibility(View.VISIBLE);
                                                }
                                                else
                                                {
                                                    btn_bid_now_lot.setVisibility(View.VISIBLE);
                                                    btn_proxy_bid_lot.setVisibility(View.VISIBLE);
                                                    tv_bidding.setVisibility(View.GONE);
                                                }
                                                if(is_us)
                                                {
                                                    int next_bid_doller=0;

                                                    priceus = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(priceus));
                                                    tv_current_lot.setText("$ "+priceus);
                                                    int myNum = Integer.parseInt(pricers);
                                                    if (myNum<10000000)
                                                    {
                                                        next_bid_doller = MakeBid.Get_10_value(priceus);
                                                    }
                                                    else
                                                    {
                                                        next_bid_doller = MakeBid.Get_5_value(priceus);
                                                    }
                                                    String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(next_bid_doller);

                                                    tv_next_lot.setText("$ "+valuebyerprimium1);
                                                }
                                                else
                                                {
                                                    int next_bid=0;
                                                    int int_str = Integer.parseInt(pricers);
                                                    String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
                                                    String str_amt_rss = rupeeFormat(str_ustest);
                                                    tv_current_lot.setText("₹ "+str_amt_rss);
                                                    int myNum = Integer.parseInt(pricers);
                                                    if (myNum<10000000)
                                                    {
                                                        next_bid = MakeBid.Get_10_value(pricers);
                                                    }
                                                    else
                                                    {
                                                        next_bid = MakeBid.Get_5_value(pricers);
                                                    }

                                                    String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(next_bid);
                                                    String str_amt_rs = rupeeFormat(valuebyerprimium1);
                                                    tv_next_lot.setText("₹ "+str_amt_rs);
                                                }

                                            }


                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }



                                }
                            }
                            catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            error.printStackTrace();
                        }
                    });


            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(Lot_Detail_Page.this, "Please Check Internet Connection.", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void bidAccessDialog(String strTitle, String strMessage)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Lot_Detail_Page.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dailog_bid_access, null);
        dialogBuilder.setView(dialogView);


        final TextView tv_title = (TextView) dialogView.findViewById(R.id.tv_title);
        final TextView tv_message = (TextView) dialogView.findViewById(R.id.tv_message);
        final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);

        tv_title.setText(strTitle);
        tv_message.setText(strMessage);
        tv_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dilog_bid_access.dismiss();

            }
        });

        dilog_bid_access = dialogBuilder.create();
        dilog_bid_access.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dilog_bid_access.show();


        // dilog_bid_access.getWindow().setLayout(700, LinearLayout.LayoutParams.MATCH_PARENT);
        Window window = dilog_bid_access.getWindow();
        window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dilog_bid_access.setCanceledOnTouchOutside(false);
    }

  /*  private  void show_outofbid_dailog(final String msg, final String currentStatus, final String servermsg)
    {

        String msgtest = msg;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Lot_Detail_Page.this);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dailog_aleart_out_of_bid, null);
        dialogBuilder.setView(dialogView);

        final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
        final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
        final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
        tv_bidvalue.setText(msgtest);

        dilog_alert_out_of_bid = dialogBuilder.create();

        tv_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(onBidResult!=null)
                {
                    dilog_alert_out_of_bid.dismiss();
                    onBidResult.bidResult(currentStatus,servermsg);

                }
            }
        });
        tv_confim.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(onBidResult!=null)
                {
                    dilog_alert_out_of_bid.dismiss();
                    onBidResult.bidResult(currentStatus,servermsg);

                }
            }
        });


        dilog_alert_out_of_bid.show();
        dilog_alert_out_of_bid.setCanceledOnTouchOutside(false);
    }*/

}

