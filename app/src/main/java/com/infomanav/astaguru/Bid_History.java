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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import adapter.Bid_History_Adpter;
import interfaces.OnBidResult;
import model_classes.Model_History;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by fox-2 on 12/7/2016.
 */

public class Bid_History extends AppCompatActivity {
    private Utility utility;
    EditText edt_proxy;
    Context context;
    ArrayList<Model_History> appsList;
    String image, str_msg,start;
    String  productid,strPastAuctionUrl;
    Model_History country;
    Bid_History_Adpter bid_history_adpter;
    private RecyclerView recyclerView;
    LinearLayout lin_closeactivity;
    public boolean is_us = false;
    TextView tv_lot,tv_ac_artist,tv_ac_category,tv_ac_medium
            ,tv_ac_year,tv_ac_size,tv_estimate,tv_noofbid;
    ImageView iv_main_img;
    TextView iv_one,iv_two,tv_bidding,tv_no_data_found;
    String str_image,Str_obj_size,fragment_type,MyUserID,doller_rate="";
    TextView tv_current_bid,tv_nextbid,tv_desc;
    Button btn_bidnow,btn_proxybid;
    SessionData data;
    String trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot,tvalue;
    String str_rs_amount,str_us_amount;
    String rupee_value;
    private TextView tvDay, tvHour, tvMinute, tvSecond;
    String value_for_cmpr;
    String rs_value,usvalue;
    AlertDialog bid_now,bid_proxy,dilog_alert,dilog_bid_access;


    LinearLayout tv_countdown,tv_countdownback,lin_count,lin_countback,lay_art_details;
    String str_Bidclosingtime,currentDate,product_id,f_doller,f_pro_id,f_lot,Thumbnail,Reference,
            OldPriceUs,OldPriceRs,Auctionid,Ufirst_name,Ulastname,Auctionname,Prdescription;
    private RelativeLayout rel_desc;
    int height=0,width=0;
    DisplayMetrics displaymetrics;
    RequestQueue requestQueue;
    private int mInterval = 10000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    private ShowFullZoomImage showFullZoomImage;
    MakeBid makeBid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_history);
        context = Bid_History.this;
        data = new SessionData(context);
        Intent intent = getIntent();
        productid = intent.getStringExtra("Str_id");
        makeBid = new MakeBid(context);

        mHandler = new Handler();
        requestQueue = Volley.newRequestQueue(Bid_History.this);

        displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        showFullZoomImage = new ShowFullZoomImage(context);

        strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spGetBidRecords("+productid+")?api_key="+ Application_Constants.API_KEY;


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

        getUpcomingAuction();

        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Bid Histroy");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        lin_closeactivity = (LinearLayout) findViewById(R.id.lin_closeactivity);
        tv_noofbid= (TextView) findViewById(R.id.tv_noofbid);
        btn_proxybid = (Button) findViewById(R.id.btn_proxybid);
        btn_bidnow = (Button) findViewById(R.id.btn_bidnow);
        tv_lot= (TextView) findViewById(R.id.tv_lot);
        tv_ac_artist= (TextView) findViewById(R.id.tv_ac_artist);
        tv_ac_category= (TextView) findViewById(R.id.tv_ac_category);
        tv_ac_medium= (TextView) findViewById(R.id.tv_ac_medium);
        tv_ac_year= (TextView) findViewById(R.id.tv_ac_year);
        tv_ac_size= (TextView) findViewById(R.id.tv_ac_size);
        tv_countdown= (LinearLayout) findViewById(R.id.tv_countdown);
        lin_count = (LinearLayout) findViewById(R.id.lin_count);
        tv_estimate = (TextView) findViewById(R.id.tv_estimate);
        iv_main_img = (ImageView) findViewById(R.id.iv_main_img);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_current_bid=(TextView) findViewById(R.id.tv_current_bid);
        tv_nextbid=(TextView) findViewById(R.id.tv_nextbid);
        tvDay = (TextView) findViewById(R.id.txtTimerDaylist);
        tvHour = (TextView) findViewById(R.id.txtTimerHourlist);
        tvMinute = (TextView) findViewById(R.id.txtTimerMinutelist);
        tvSecond = (TextView) findViewById(R.id.txtTimerSecondlist);
        iv_one = (TextView) findViewById(R.id.iv_one);
        iv_two = (TextView) findViewById(R.id.iv_two);
        tv_bidding = (TextView) findViewById(R.id.tv_bidding);
        lay_art_details = (LinearLayout) findViewById(R.id.lay_art_details);
        rel_desc = (RelativeLayout) findViewById(R.id.rel_desc);
        tv_no_data_found = (TextView) findViewById(R.id.tv_no_data_found);


        Auctionname = intent.getStringExtra("Auctionname");
        Prdescription = intent.getStringExtra("Prdescription");
        tv_desc.setText(Html.fromHtml(Prdescription));
        if(!Auctionname.equalsIgnoreCase("Collectibles Auction"))
        {
            // if data is about artist
            lay_art_details.setVisibility(View.VISIBLE);
            rel_desc.setVisibility(View.GONE);

            String strArtistFirstName="",strArtistLastName="";
            strArtistFirstName = intent.getStringExtra("str_FirstName");
            strArtistLastName = intent.getStringExtra("str_LastName");

            tv_ac_artist.setText(strArtistFirstName+" "+ strArtistLastName);

           /* String strCategory = intent.getStringExtra("str_category");
            if(!strCategory.equals("null"))
            {

            }*/
            tv_ac_category.setText(intent.getStringExtra("str_category"));
            tv_ac_medium.setText(intent.getStringExtra("str_medium"));
            tv_ac_year.setText(intent.getStringExtra("str_date"));
        }
        else
        {
            // if data is about artist
            lay_art_details.setVisibility(View.VISIBLE);
            rel_desc.setVisibility(View.GONE);
        }

        String p_size = intent.getStringExtra("str_productsize");
        tv_ac_size.setText(p_size+"in");

        tv_lot.setText(("Lot " +intent.getStringExtra("str_lot")));
        str_image = intent.getStringExtra("str_image");
        fragment_type = intent.getStringExtra("fragment_type");
        MyUserID= intent.getStringExtra("MyUserID");


        String img_name = intent.getStringExtra("str_image");
        Thumbnail = img_name.replace("paintings/", "");
        Reference = intent.getStringExtra("str_refrene");
        OldPriceRs = intent.getStringExtra("str_pricers");
        OldPriceUs = intent.getStringExtra("str_priceus");
        Auctionid = intent.getStringExtra("str_refrene");
        Ufirst_name = intent.getStringExtra("str_FirstName");
        Ulastname = intent.getStringExtra("str_LastName");
        currentDate= intent.getStringExtra("currentDate");
        is_us =intent.getBooleanExtra("currency_type",false);
        str_Bidclosingtime=intent.getStringExtra("str_Bidclosingtime");
        product_id=intent.getStringExtra("product_id");
        MyUserID=intent.getStringExtra("MyUserID");
        doller_rate =intent.getStringExtra("doller_rate");




        iv_main_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullZoomImage.showImage(str_image);
            }
        });





        String currency = data.getObjectAsString("currency");
        System.out.println("currency" + currency);

        if (is_us)
        {
            int next_bid_doller=0;
            int int_str = Integer.parseInt(OldPriceUs);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
            System.out.println("currency" +str_ustest);


            tv_current_bid.setText(str_ustest);
            int myNum = Integer.parseInt(OldPriceUs);
            if (myNum<10000000)
            {
                next_bid_doller = Get_10_value(OldPriceUs);
            }
            else
            {
                next_bid_doller = Get_5_value(OldPriceUs);
            }
            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(next_bid_doller);

            tv_nextbid.setText(valuebyerprimium1);
            iv_one.setText("US$");
            iv_two.setText("US$");

            tv_estimate.setText(intent.getStringExtra("str_istimate").trim());
        }
        else
        {
            int next_bid = 0;
            int int_str = Integer.parseInt(OldPriceRs);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
            String str_amt_rss = rupeeFormat(str_ustest);
            tv_current_bid.setText(str_amt_rss);

            int myNum = Integer.parseInt(OldPriceRs);
            if (myNum<10000000)
            {
                next_bid = Get_10_value(OldPriceRs);
            }
            else
            {
                next_bid = Get_5_value(OldPriceRs);
            }

            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(next_bid);
            String str_amt_rs = rupeeFormat(valuebyerprimium1);
            tv_nextbid.setText(str_amt_rs);
            iv_one.setText("₹");
            iv_two.setText("₹");

            tv_estimate.setText(intent.getStringExtra("str_collectors").trim());
        }
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            // Here Set your Event Date
            String str = str_Bidclosingtime;

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


                btn_bidnow.setEnabled(true);
                btn_proxybid.setEnabled(true);

                btn_bidnow.setBackgroundResource(R.color.black);
                btn_proxybid.setBackgroundResource(R.color.black);

                tv_countdown.setVisibility(View.GONE);
                lin_count.setVisibility(View.VISIBLE);
            }
            else
            {


                //  handler.removeCallbacks(runnable);
                lin_count.setVisibility(View.GONE);
                tv_countdown.setVisibility(View.VISIBLE);
                btn_bidnow.setVisibility(View.VISIBLE);
                btn_proxybid.setVisibility(View.VISIBLE);
                btn_bidnow.setBackgroundResource(R.color.grey05);
                btn_proxybid.setBackgroundResource(R.color.grey05);
                btn_bidnow.setEnabled(false);
                btn_proxybid.setEnabled(false);

                String userid = data.getObjectAsString("userid");

                if(userid.equals(MyUserID))
                {
                    btn_bidnow.setVisibility(View.GONE);
                    btn_proxybid.setVisibility(View.GONE);
                    tv_bidding.setVisibility(View.VISIBLE);
                }
                else
                {
                    btn_bidnow.setVisibility(View.VISIBLE);
                    btn_proxybid.setVisibility(View.VISIBLE);
                    tv_bidding.setVisibility(View.GONE);
                }
                tvDay.setText("" + String.format("%02d","0"));
                tvHour.setText("" + String.format("%02d", "0"));
                tvMinute.setText("" + String.format("%02d", "0"));
                tvSecond.setText("" + String.format("%02d", "0"));



            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Picasso.with(getApplicationContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + str_image)
                .into(iv_main_img);

        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        lin_closeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (fragment_type.equals("past"))
        {
            btn_bidnow.setVisibility(View.GONE);
            btn_proxybid.setVisibility(View.GONE);

        }
        else if (fragment_type.equals("Current"))
        {
            String user = data.getObjectAsString("login");
            String userid = data.getObjectAsString("userid");


            if(userid.equals(MyUserID))
            {
                btn_bidnow.setVisibility(View.GONE);
                btn_proxybid.setVisibility(View.GONE);
                tv_bidding.setVisibility(View.VISIBLE);
            }
            else
            {
                btn_bidnow.setVisibility(View.VISIBLE);
                btn_proxybid.setVisibility(View.VISIBLE);
                tv_bidding.setVisibility(View.GONE);

            }

        }

        else if (fragment_type.equals("upcomming"))
        {
            btn_bidnow.setVisibility(View.GONE);
            btn_proxybid.setVisibility(View.VISIBLE);


        }


        btn_bidnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(context,Before_Login_Activity.class);
                    intent.putExtra("str_from","adpter");
                    context.startActivity(intent);

                }
                else
                {

                    String MobileVerified = data.getObjectAsString("MobileVerified");
                    String EmailVerified = data.getObjectAsString("EmailVerified");
                    String confirmbid = data.getObjectAsString("confirmbid");
                    if (MobileVerified.equalsIgnoreCase("true") && EmailVerified.equalsIgnoreCase("true")) {
                        if (confirmbid.equals("1"))
                        {


                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
                            dialogBuilder.setView(dialogView);

                            final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                            final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                            final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                            final TextView tv_bidlot = (TextView) dialogView.findViewById(R.id.tv_bidlot);

                            final TextView iv_icon = (TextView) dialogView.findViewById(R.id.iv_icon);
                            iv_icon.setVisibility(View.GONE);


                            if (is_us) {
                                int int_bid_us = 0;


                                int myNum = Integer.parseInt(OldPriceUs);
                                if (myNum < 10000000) {
                                    int_bid_us = Get_10_value(OldPriceUs);
                                } else {
                                    int_bid_us = Get_5_value(OldPriceUs);
                                }

                                str_us_amount = String.valueOf(int_bid_us);
                                str_rs_amount = String.valueOf(int_bid_us);
                                String str_int_us = NumberFormat.getNumberInstance(Locale.US).format(int_bid_us);

                                tv_bidvalue.setText("US$ "+str_int_us);
                                tv_bidlot.setText("Lot " + Reference);
                                //iv_icon.setText("US$");
                            }
                            else
                            {
                                int int_bid_rs = 0;


                                int myNum = Integer.parseInt(OldPriceRs);
                                if (myNum < 10000000)
                                {
                                    int_bid_rs = Get_10_value(OldPriceRs);
                                }
                                else
                                {
                                    int_bid_rs = Get_5_value(OldPriceRs);
                                }

                                str_rs_amount = String.valueOf(int_bid_rs);
                                str_us_amount = String.valueOf(int_bid_rs);
                                rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_bid_rs);
                                String str_amt_rsbid = rupeeFormat(String.valueOf(rs_value));
                                tv_bidvalue.setText("₹ "+str_amt_rsbid);
                                tv_bidlot.setText("Lot " + Reference);
                                //iv_icon.setText("₹");

                            }



                            tv_confim.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String rs_amount = str_rs_amount;
                                    String us_amount = str_us_amount;

                                    String userid = data.getObjectAsString("userid");
                                    String dollerrate = doller_rate;
                                    f_lot =Reference;

                                   // String str_us = Get_US_value(dollerrate, rs_amount);
                                    String str_us = MakeBid.Get_US_value(dollerrate, rs_amount);


                                    if (is_us)
                                    {
                                        if (utility.checkInternet())
                                        {
                                            String Str_productid = productid;


                                            int a = Integer.parseInt(us_amount);
                                            int b = Integer.parseInt(dollerrate);

                                            int str_rsonus = a * b;

                                            String proxy_new_us = Integer.toString(str_rsonus);

                                            GetData("US", Str_productid, proxy_new_us, productid, userid, dollerrate, us_amount, f_lot);

                                        } else
                                        {

                                            show_dailog("Please Check Internet Connection");
                                        }

                                    } else
                                    {


                                        if (utility.checkInternet())
                                        {
                                            String Str_productid = productid;

                                            GetData("US", Str_productid, us_amount, productid, userid, dollerrate, str_us, f_lot);

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
                           // Toast.makeText(context, "You do not have bidding access", Toast.LENGTH_SHORT).show();
                            bidAccessDialog("Astaguru","You don't have access to Bid Please contact Astaguru.");
                        }

                    }
                    else
                    {
                        Intent intent = new Intent(context,Verification_Activity.class);
                        intent.putExtra("Activity","Login");
                        context.startActivity(intent);
                    }
                }
            }
        });


        btn_proxybid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(context,Before_Login_Activity.class);
                    context.startActivity(intent);

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

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                            LayoutInflater inflater = (LayoutInflater) context
                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View dialogView = inflater.inflate(R.layout.dailog_proxybid, null);
                            dialogBuilder.setView(dialogView);

                            final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                            final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                            edt_proxy = (EditText) dialogView.findViewById(R.id.edt_proxy);
                            final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                            final TextView tv_proxylot = (TextView) dialogView.findViewById(R.id.tv_proxylot);
                            final LinearLayout lay_bid_values = (LinearLayout) dialogView.findViewById(R.id.lay_bid_values);
                            final TextView tv_confirm_bid = (TextView) dialogView.findViewById(R.id.tv_confirm_bid);

                            final TextView iv_iconproxy = (TextView) dialogView.findViewById(R.id.iv_iconproxy);
                            iv_iconproxy.setVisibility(View.GONE);


                            if (is_us) {
                                int int_proxy_bid_us = 0;


                                int myNum = Integer.parseInt(OldPriceUs);
                                if (myNum < 10000000) {
                                    int_proxy_bid_us = Get_10_value(OldPriceUs);
                                } else {
                                    int_proxy_bid_us = Get_5_value(OldPriceUs);
                                }
                                value_for_cmpr = String.valueOf(int_proxy_bid_us);
                                String str_int_xus = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_us);

                                tv_bidvalue.setText("US$ "+str_int_xus);
                                tv_proxylot.setText("Lot " + Reference);
                               // iv_iconproxy.setText("US$");
                            } else {

                                int int_proxy_bid_rs = 0;

                                int myNum = Integer.parseInt(OldPriceRs);
                                if (myNum < 10000000) {
                                    int_proxy_bid_rs = Get_10_value(OldPriceRs);
                                } else {
                                    int_proxy_bid_rs = Get_5_value(OldPriceRs);
                                }

                                value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                                rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);

                                str_rs_amount = String.valueOf(int_proxy_bid_rs);
                                tv_proxylot.setText("Lot " + Reference);
                                tv_bidvalue.setText("₹ "+rs_value);
                               // iv_iconproxy.setText("₹");
                            }


                            tv_confim.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    String dollerRate = doller_rate;
                                    f_lot = Reference;
                                    String siteUserID = data.getObjectAsString("userid");
                                    final String productID = product_id;
                                    String str_ProxyAmt = edt_proxy.getText().toString();
                                    if (str_ProxyAmt.isEmpty())
                                    {
                                        Toast.makeText(context, "please Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();
                                    } else {

                                        double fb = Double.parseDouble(dollerRate);
                                        double rl = Double.parseDouble(str_ProxyAmt);

                                        double str_ProxyAmtus_new = rl / fb;
                                        int int_proxy_new = (int) Math.round(str_ProxyAmtus_new);
                                        String proxy_amt_us = String.valueOf(int_proxy_new);


                                        String entered_value = edt_proxy.getText().toString();
                                        String bid_value = value_for_cmpr;
                                        int int_entered_value = Integer.parseInt(entered_value);
                                        int int_bid_value = Integer.parseInt(bid_value);

                                        if (int_entered_value >= int_bid_value)
                                        {
                                            if(lay_bid_values.getVisibility()==View.VISIBLE)
                                            {
                                                lay_bid_values.setVisibility(View.GONE);
                                                tv_confirm_bid.setVisibility(View.VISIBLE);
                                            }
                                            else
                                            {
                                                if (is_us)
                                                {

                                                    String str_Proxy_for_us = edt_proxy.getText().toString();

                                                    int fb1 = Integer.parseInt(dollerRate);
                                                    int rl1 = Integer.parseInt(str_Proxy_for_us);

                                                    int str_ProxyAmtrs = rl1 * fb1;

                                                    String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                                    // Toast.makeText(context, "from US", Toast.LENGTH_SHORT).show();


                                                    if (utility.checkInternet())
                                                    {
                                                        // ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_for_rs, f_lot);

                                                        makeBid.ProxyBid(proxy_amt_for_rs, productID, siteUserID, dollerRate, str_ProxyAmt, f_lot,str_Bidclosingtime,
                                                                Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,"Untiteled");

                                                        makeBid.bidResult(new OnBidResult() {
                                                            @Override
                                                            public void bidResult(String currentStatus, String msg) {
                                                                bid_proxy.dismiss();
                                                                getTimeCountdown(productID);
                                                            }
                                                        });

                                                    } else {
                                                        show_dailog("Please Check Internet Connection");

                                                    }


                                                } else {
                                                    //  Toast.makeText(context, "From RS", Toast.LENGTH_SHORT).show();

                                                    if (utility.checkInternet())
                                                    {
                                                        //ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot);

                                                        makeBid.ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot,str_Bidclosingtime,
                                                                Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,"Untiteled");

                                                        makeBid.bidResult(new OnBidResult() {
                                                            @Override
                                                            public void bidResult(String currentStatus, String msg) {
                                                                bid_proxy.dismiss();
                                                                getTimeCountdown(productID);
                                                            }
                                                        });
                                                    } else {

                                                        show_dailog("Please Check Internet Connection");
                                                    }


                                                }
                                            }


                                        }else {

                                            // Toast.makeText(mContext, "Proxy Must Greater Than Current Bid Value", Toast.LENGTH_SHORT).show();

                                            double myNum = Double.parseDouble(entered_value);
                                            if (myNum < 10000000) {
                                                Toast.makeText(context, "Proxy Bid value must be greater by at least 10% of current price.", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(myNum < 10000000)
                                            {
                                                Toast.makeText(context, "Proxy Bid value must be greater by at least 5% of current price", Toast.LENGTH_SHORT).show();
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
                            //Toast.makeText(context, "You do not have bidding access", Toast.LENGTH_SHORT).show();
                            bidAccessDialog("Astaguru","You don't have access to Bid Please contact Astaguru.");
                        }
                    }
                    else
                    {
                        Intent intent = new Intent(context, Verification_Activity.class);
                        intent.putExtra("Activity", "Login");
                        context.startActivity(intent);

                    }
                }
            }
        });

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
    private int Get_10_value(String amt)
    {

        double amount = Double.parseDouble(amt);

        double byerprimium = (amount / 100.0f) * 10;

        double sum = amount + byerprimium;

        int intbyerprimium = (int) sum;

        return intbyerprimium;
    }

    public static String rupeeFormat(String value)
    {
        value=value.replace(",","");
        char lastDigit=value.charAt(value.length()-1);
        String result = "";
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
        return (result+lastDigit);
    }
    private String Get_US_value(String dollerrate,String rs_amount)
    {

        int fb = Integer.parseInt(dollerrate);
        int rl = Integer.parseInt(rs_amount);

        int str_Proxy_new = rl / fb;

        String proxy_new_us = Integer.toString(str_Proxy_new);

        return proxy_new_us;
    }
    private int Get_5_value(String amt)
    {

        double amount = Double.parseDouble(amt);

        double byerprimium = (amount / 100.0f) * 5;

        double sum = amount + byerprimium;

        int intbyerprimium = (int) sum;

        return intbyerprimium;
    }
    private void getUpcomingAuction()
    {
        if (utility.checkInternet())
        {
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();
            ServiceHandler serviceHandler = new ServiceHandler(context);

            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;

                    String  artist_name,str_firstname,str_lastname,  str_bid_d,  str_bid_r,  str_bid_date;

                    appsList = new ArrayList<>();
                    try {
                        if (str_json != null)
                        {
                            JSONArray jsonArray = new JSONArray(str_json);

                            if (jsonArray.length() > 0)
                            {
                                Str_obj_size = String.valueOf(jsonArray.length());
                                tv_no_data_found.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject Obj = jsonArray.getJSONObject(i);



                                    str_firstname = Obj.getString("anoname");

                                    if( Obj.getString("Lastname").equals("null"))
                                    {
                                        str_lastname ="";
                                    }
                                    else
                                    {
                                        str_lastname = Obj.getString("Lastname");
                                    }



                                    str_bid_d = Obj.getString("Bidpriceus");
                                    str_bid_r = Obj.getString("Bidpricers");
                                    str_bid_date = Obj.getString("daterec");

                                    str_bid_r = rupeeFormat(str_bid_r);

                                    if(str_bid_d.equals("null"))
                                    {
                                        str_bid_d = "0";
                                    }
                                    str_bid_d = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(str_bid_d));


                                   // str_bid_r = NumberFormat.getNumberInstance(Locale.US).format(str_bid_r);
                                    //str_bid_d = NumberFormat.getNumberInstance(Locale.US).format(str_bid_d);


                                    country = new Model_History(str_firstname,str_bid_d,str_bid_r,str_bid_date);
                                    appsList.add(country);

                                }

                                tv_noofbid.setText(Str_obj_size);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setNestedScrollingEnabled(false);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());

                                bid_history_adpter = new Bid_History_Adpter(appsList);
                                recyclerView.setAdapter(bid_history_adpter);

                                startRepeatingTask();

                            }
                            else
                                {
                                    tv_no_data_found.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                               // Toast.makeText(context, "Records Not Found", Toast.LENGTH_SHORT).show();
                                startRepeatingTask();
                            }
                        } else {
                            Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                Intent intent = new Intent(Bid_History.this,MyAstaGuru_Activity.class);
                startActivity(intent);

                return true;
            case R.id.action_search:
                // do whatever
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void BidNow(String str_Amount,String str_productID,String str_userID,String dollerrate,String proxy_new_us,String tlot)
    {
        str_Bidclosingtime = str_Bidclosingtime.replace(" ","%20");
        Auctionid = Auctionid.trim();

        if (utility.checkInternet())
        {
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spBid("+str_Amount+","+str_productID+","+str_userID+","+dollerrate+","+proxy_new_us+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+","+str_Bidclosingtime+","+Ufirst_name+","+Ulastname+")?api_key="+ Application_Constants.API_KEY;
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            final   String str_lot = tlot;
            final String str_amt = str_Amount;

            System.out.println("strPastAuctionUrl " + str_Amount);
            System.out.println("strPastAuctionUrl " + str_productID);
            System.out.println("strPastAuctionUrl " + str_userID);
            System.out.println("strPastAuctionUrl " + dollerrate);
            System.out.println("strPastAuctionUrl " + proxy_new_us);



            ServiceHandler serviceHandler = new ServiceHandler(context);


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

                                if(currentStatus.equals("1"))
                                {

                                   // Toast.makeText(context, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
                                    String number = Obj.getString("mobileNum");
                                    bid_now.dismiss();

                                    show_dailog("Your bid submitted successfully, currently you are leading for this product");
                                    registerUser(str_lot,str_amt,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    //Toast.makeText(context, "U r out of bid", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    show_dailog("Sorry You are out off bid because already higher proxybid is their, do you want to bid again");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    //show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

                                }




                            } else {
                                bid_now.dismiss();
                                Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            bid_now.dismiss();
                            Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        bid_now.dismiss();
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });
        }

    }

    private void GetData(String value, String str_productid, String rs_amount, final String productid, String userid, String dollerrate, String proxy_new_us, String lot) {

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
            ServiceHandler serviceHandler = new ServiceHandler(context);


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

                                if(rupee_value.equalsIgnoreCase("null"))
                                {
                                    rupee_value="0";
                                }


                                int value_one = Integer.parseInt(rupee_value);
                                int value_two = Integer.parseInt(trs_amount);

                                if (value_two > value_one)
                                {


                                    makeBid.BidNow(trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot,str_Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,"Untitled");
                                    makeBid.bidResult(new OnBidResult() {
                                        @Override
                                        public void bidResult(String currentStatus, String msg) {
                                            bid_now.dismiss();
                                            getTimeCountdown(productid);
                                        }
                                    });
                                }
                                else
                                {

                                    bid_now.dismiss();

                                    int int_proxy_bid_rselse = Get_10_value(rupee_value);


                                    rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse);

                                    str_rs_amount = String.valueOf(int_proxy_bid_rselse);

                                    Toast.makeText(context,"Bid Not Valid", Toast.LENGTH_SHORT).show();



                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                    LayoutInflater inflater = (LayoutInflater) context
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
                                       /// iv_icon.setText("US$");
                                    }
                                    else
                                    {
                                        int int_proxy_bid_rselse_rs = Get_10_value(rupee_value);

                                        rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_rs);

                                        str_rs_amount = String.valueOf(int_proxy_bid_rselse_rs);

                                        tv_bidvalue.setText("₹ "+rs_value);
                                       // iv_icon.setText("₹");
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
                                                Intent intent = new Intent(context,Before_Login_Activity.class);
                                                context.startActivity(intent);

                                            }
                                            else {

                                                if (is_us) {
                                                    GetData("US",f_pro_id,us_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
                                                } else {


                                                    GetData("RS",f_pro_id,rs_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
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
                                    bid_now.setCanceledOnTouchOutside(false);


                                }




                            } else {
                                Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private void ProxyBid(String userProxyAmount,String productID,String siteUserID,String dollerRate,String dollerAmt,String f_lot)
    {

        if (utility.checkInternet()) {
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+","+Ufirst_name+","+Ulastname+")?api_key="+ Application_Constants.API_KEY;
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            final   String userproxy = userProxyAmount;
            final String lot_no = f_lot;




            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);


                    String str_json = result;

                    try {
                        if (str_json != null)
                        {

                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {
                                JSONObject Obj = jsonArray.getJSONObject(0);
                                String currentStatus = Obj.getString("currentStatus");
//                                String emailID = Obj.getString("emailID");
//                                String mobilrNum = Obj.getString("mobilrNum");

                                if(currentStatus.equals("1"))
                                {

                                    String number = Obj.getString("mobileNum");
                                    bid_proxy.dismiss();

                                    show_dailog("You Succesfully Bid");
                                    registerUser(lot_no,userproxy,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    bid_proxy.dismiss();

                                    show_dailog("U r out of bid");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    bid_proxy.dismiss();

                                    show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

                                }




                            } else {
                                Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
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

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
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
                        Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

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


                                            btn_bidnow.setEnabled(true);
                                            btn_proxybid.setEnabled(true);

                                            btn_bidnow.setBackgroundResource(R.color.black);
                                            btn_proxybid.setBackgroundResource(R.color.black);

                                            tv_countdown.setVisibility(View.GONE);
                                            lin_count.setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {


                                            //  handler.removeCallbacks(runnable);
                                            lin_count.setVisibility(View.GONE);
                                            tv_countdown.setVisibility(View.VISIBLE);
                                            btn_bidnow.setVisibility(View.VISIBLE);
                                            btn_proxybid.setVisibility(View.VISIBLE);
                                            btn_bidnow.setBackgroundResource(R.color.grey05);
                                            btn_proxybid.setBackgroundResource(R.color.grey05);
                                            btn_bidnow.setEnabled(false);
                                            btn_proxybid.setEnabled(false);

                                            String userid = data.getObjectAsString("userid");

                                            if(userid.equals(MyUserID))
                                            {
                                                btn_bidnow.setVisibility(View.GONE);
                                                btn_proxybid.setVisibility(View.GONE);
                                                tv_bidding.setVisibility(View.VISIBLE);
                                            }
                                            else
                                            {
                                                btn_bidnow.setVisibility(View.VISIBLE);
                                                btn_proxybid.setVisibility(View.VISIBLE);
                                                tv_bidding.setVisibility(View.GONE);
                                            }
                                            tvDay.setText("" + String.format("%02d","0"));
                                            tvHour.setText("" + String.format("%02d", "0"));
                                            tvMinute.setText("" + String.format("%02d", "0"));
                                            tvSecond.setText("" + String.format("%02d", "0"));



                                        }


                                        if (fragment_type.equals("past"))
                                        {
                                            btn_bidnow.setVisibility(View.GONE);
                                            btn_proxybid.setVisibility(View.GONE);

                                        }
                                        else if (fragment_type.equals("Current"))
                                        {
                                            String user = data.getObjectAsString("login");
                                            String userid = data.getObjectAsString("userid");


                                            if(userid.equals(MyUserID))
                                            {
                                                btn_bidnow.setVisibility(View.GONE);
                                                btn_proxybid.setVisibility(View.GONE);


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
                                                btn_bidnow.setVisibility(View.VISIBLE);
                                                btn_proxybid.setVisibility(View.VISIBLE);
                                                tv_bidding.setVisibility(View.GONE);

                                            }

                                        }

                                        else if (fragment_type.equals("upcomming"))
                                        {
                                            btn_bidnow.setVisibility(View.GONE);
                                            btn_proxybid.setVisibility(View.VISIBLE);


                                        }

                                        if (is_us)
                                        {
                                            int next_bid_doller=0;
                                            int int_str = Integer.parseInt(priceus);
                                            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
                                            System.out.println("currency" +str_ustest);


                                            tv_current_bid.setText(str_ustest);
                                            int myNum = Integer.parseInt(priceus);
                                            if (myNum<10000000)
                                            {
                                                next_bid_doller = Get_10_value(priceus);
                                            }
                                            else
                                            {
                                                next_bid_doller = Get_5_value(priceus);
                                            }
                                            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(next_bid_doller);

                                            tv_nextbid.setText(valuebyerprimium1);
                                            iv_one.setText("US$");
                                            iv_two.setText("US$");

                                        }
                                        else
                                        {
                                            int next_bid = 0;
                                            int int_str = Integer.parseInt(pricers);
                                            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
                                            String str_amt_rss = rupeeFormat(str_ustest);
                                            tv_current_bid.setText(str_amt_rss);

                                            int myNum = Integer.parseInt(pricers);
                                            if (myNum<10000000)
                                            {
                                                next_bid = Get_10_value(pricers);
                                            }
                                            else
                                            {
                                                next_bid = Get_5_value(pricers);
                                            }

                                            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(next_bid);
                                            String str_amt_rs = rupeeFormat(valuebyerprimium1);
                                            tv_nextbid.setText(str_amt_rs);
                                            iv_one.setText("₹");
                                            iv_two.setText("₹");

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
            Toast.makeText(Bid_History.this, "Please Check Internet Connection.", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void bidAccessDialog(String strTitle, String strMessage)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Bid_History.this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
}
