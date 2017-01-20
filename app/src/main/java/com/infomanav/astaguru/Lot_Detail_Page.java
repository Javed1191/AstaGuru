package com.infomanav.astaguru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import adapter.MyPurchasesAdpter;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 24-10-2016.
 */

public class Lot_Detail_Page extends AppCompatActivity {
    String f_doller,f_pro_id,str_estamiate;
    String rs_value,f_lot,usvalue,value_for_cmpr;
    String trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot,tvalue;
    String str_rs_amount,str_us_amount;
    String rupee_value;
    private Utility utility;
    Context mContext;
    Button btn_proxy_bid_lot, btn_bid_now_lot;
    SessionData data;
    TextView tv_bid_histrory;

    String image, str_msg;
    String product_id, strPastAuctionUrl;
    LinearLayout iv_leftarrow;
    ImageView lot_image,  iv_zoom, iv_one, iv_two,iv_addgallary;
    TextView tv_artworksize,tv_title, tv_title_sub, tv_current_lot, tv_next_lot, tv_artist, tv_category, tv_medium, tv_year, tv_size, tv_estimate, tv_additonal_info;
    TextView tv_addinfo_one, tv_art_size, tv_addinfo_three,  tv_read_more, tv_type, tv_change_currency;
    EditText edt_proxy;
    AlertDialog bid_now, bid_proxy,dilog_alert;
    JustifiedTextView tv_abt_artist;
    String test1,test2;
    String currency_type,str_productid, str_title, str_description, str_artistid, str_collectors, str_thumbnail, str_image, str_productsize, str_Bidpricers, str_Bidpriceus;

    String reference, str_FirstName, str_LastName, str_Profile, start;
WebView wb_bidding,wb_biddingtwo;
    String str_category, str_medium, str_date, str_pricers, str_priceus;
    public boolean is_us = false;
    ProgressDialog pDialog;
    String Userid,fragment_type,Auction_id,Thumbnail,Reference,OldPriceUs,OldPriceRs,Auctionid;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot_detail);
        mContext = Lot_Detail_Page.this;
        data = new SessionData(mContext);
         Userid = data.getObjectAsString("userid");

        Intent intent = getIntent();
        product_id = intent.getStringExtra("Str_id");

        System.out.println("Str_id" + product_id);
        reference = intent.getStringExtra("reference");
        fragment_type = intent.getStringExtra("fragment");

        if (fragment_type.equals("upcomming"))
        {
            Auction_id = intent.getStringExtra("Auction_id");

        }

        f_doller = "68";
        f_pro_id =product_id;
        strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/acution/" + product_id + "?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*";


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
        tool_text.setText("Lot:"+reference);

        btn_bid_now_lot = (Button) findViewById(R.id.btn_bid_now_lot);

        btn_proxy_bid_lot = (Button) findViewById(R.id.btn_proxy_bid_lot);
        iv_addgallary= (ImageView) findViewById(R.id.iv_addgallary);
        iv_two = (ImageView) findViewById(R.id.iv_two);
        iv_one = (ImageView) findViewById(R.id.iv_one);
        lot_image = (ImageView) findViewById(R.id.lot_image);
        iv_leftarrow = (LinearLayout) findViewById(R.id.iv_leftarrow);
        iv_zoom = (ImageView) findViewById(R.id.iv_zoom);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title_sub = (TextView) findViewById(R.id.tv_title_sub);
        tv_current_lot = (TextView) findViewById(R.id.tv_current_lot);
        tv_next_lot = (TextView) findViewById(R.id.tv_next_lot);
        tv_artist = (TextView) findViewById(R.id.tv_artist);
        tv_category = (TextView) findViewById(R.id.tv_category);
        wb_bidding = (WebView)findViewById(R.id.wb_bidding) ;
        wb_biddingtwo = (WebView)findViewById(R.id.wb_biddingtwo) ;
        tv_medium = (TextView) findViewById(R.id.tv_medium);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_size = (TextView) findViewById(R.id.tv_size);
        tv_estimate = (TextView) findViewById(R.id.tv_estimate);
        tv_additonal_info = (TextView) findViewById(R.id.tv_additonal_info);
        tv_addinfo_one = (TextView) findViewById(R.id.tv_addinfo_one);
//        tv_change_currency = (TextView) findViewById(R.id.tv_change_currency);
//        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_abt_artist = (JustifiedTextView) findViewById(R.id.tv_abt_artist);
        tv_read_more = (TextView) findViewById(R.id.tv_read_more);
        tv_art_size= (TextView) findViewById(R.id.tv_art_size);


        tv_bid_histrory= (TextView) findViewById(R.id.tv_bid_histrory);
        Typeface type = Typeface.createFromAsset(getAssets(), "WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        iv_one.setImageResource(R.drawable.rupee);
        iv_two.setImageResource(R.drawable.rupee);



        if (fragment_type.equals("past"))
        {
            btn_bid_now_lot.setVisibility(View.GONE);
            btn_proxy_bid_lot.setVisibility(View.GONE);

        }
        else if (fragment_type.equals("Current"))
        {
            btn_bid_now_lot.setVisibility(View.VISIBLE);
            btn_proxy_bid_lot.setVisibility(View.VISIBLE);

        }

        else if (fragment_type.equals("upcomming")) {
            btn_bid_now_lot.setVisibility(View.GONE);
            btn_proxy_bid_lot.setVisibility(View.VISIBLE);


        }


            // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        iv_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", str_thumbnail);
                mContext.startActivity(intent);
            }
        });

        iv_addgallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  userid = data.getObjectAsString("userid");
                AddToGallary(product_id,userid);
            }
        });


        lot_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", str_thumbnail);
                mContext.startActivity(intent);
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

                intent.putExtra("str_FirstName", str_FirstName);
                intent.putExtra("str_LastName", str_LastName);
                intent.putExtra("str_category", str_category);
                intent.putExtra("str_medium", str_medium);
                intent.putExtra("str_date", str_date);
                intent.putExtra("str_productsize", str_productsize);
                intent.putExtra("str_image", str_image);
                intent.putExtra("str_pricers", str_pricers);
                intent.putExtra("str_priceus", str_priceus);
                intent.putExtra("str_refrene", reference);
                intent.putExtra("currency_type", currency_type);

//                intent.putExtra("str_lot",);

                startActivity(intent);
            }
        });
        tv_bid_histrory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        Intent intentproductid = new Intent(mContext, Bid_History.class);
        intentproductid.putExtra("Str_id", product_id);

        intentproductid.putExtra("str_FirstName",str_FirstName);
        intentproductid.putExtra("str_category",str_category);
        intentproductid.putExtra("str_medium",str_medium);
        intentproductid.putExtra("str_date",str_date);
        intentproductid.putExtra("str_productsize", str_productsize);
        intentproductid.putExtra("str_istimate",str_collectors);
        intentproductid.putExtra("str_lot",reference.trim());
        intentproductid.putExtra("str_image",str_image);
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
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
                dialogBuilder.setView(dialogView);

                final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                    final TextView iv_icon = (TextView) dialogView.findViewById(R.id.iv_icon);

                if (is_us)
                {

                    int int_bid_us=0;


                    int myNum = Integer.parseInt(str_pricers);
                    if (myNum<10000000)
                    {
                        int_bid_us = Get_10_value(str_priceus);
                    }
                    else
                    {
                        int_bid_us = Get_5_value(str_priceus);
                    }

                    str_us_amount = String.valueOf(int_bid_us);
                    str_rs_amount = String.valueOf(int_bid_us);
                    String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_bid_us);

                    tv_bidvalue.setText(str_int_us);
                    iv_icon.setText("US$");
                }
                else
                {

                    int int_bid_rs=0;


                    int myNum = Integer.parseInt(str_pricers);
                    if (myNum<10000000)
                    {
                        int_bid_rs = Get_10_value(str_pricers);
                    }
                    else
                    {
                        int_bid_rs = Get_5_value(str_pricers);
                    }

                    str_rs_amount = String.valueOf(int_bid_rs);
                    str_us_amount = String.valueOf(int_bid_rs);
                    rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_bid_rs);

                    tv_bidvalue.setText(rs_value);
                    iv_icon.setText("₹");
                }


//

                tv_confim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String rs_amount = str_rs_amount;
                        String us_amount = str_us_amount;
                        String productid = product_id;
                        String userid = data.getObjectAsString("userid");
                        String dollerrate = "68";
                        f_lot =reference;

                        String str_us = Get_US_value(dollerrate,rs_amount);



                            if (is_us)
                            {


                                if(utility.checkInternet())
                                {
                                    String Str_productid = product_id;
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);



                                    GetData("US",Str_productid,us_amount, productid, userid, dollerrate, str_us,f_lot);

                                }
                                else
                                {

                                    show_dailog("Please Check Internet Connection");
                                }

                            }
                            else
                            {


                                if(utility.checkInternet())
                                {
                                    String Str_productid = product_id;
                                    GetData("RS",Str_productid,rs_amount, productid, userid, dollerrate, str_us,f_lot);
//                                BidNow(rs_amount, productid, userid, dollerrate, proxy_new_us);

                                }
                                else
                                {

                                    show_dailog("Please Check Internet Connection");
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


            }
        });

        btn_proxy_bid_lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(mContext,Before_Login_Activity.class);
                    intent.putExtra("str_from","adpter");
                    mContext.startActivity(intent);

                }
                else
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



                if (is_us)
                {
                    int int_proxy_bid_us=0;


                    int myNum = Integer.parseInt(str_pricers);
                    if (myNum<10000000)
                    {
                        int_proxy_bid_us = Get_10_value(str_priceus);
                    }
                    else
                    {
                        int_proxy_bid_us = Get_5_value(str_priceus);
                    }


                    String str_int_xus= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_us);

                    tv_bidvalue.setText(str_int_xus);
                    iv_iconproxy.setText("US$");
                }
                else
                {



                    int int_proxy_bid_rs=0;


                    int myNum = Integer.parseInt(str_pricers);
                    if (myNum<10000000)
                    {
                        int_proxy_bid_rs = Get_10_value(str_pricers);
                    }
                    else
                    {
                        int_proxy_bid_rs = Get_5_value(str_pricers);
                    }

                    value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                    rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);

                    str_rs_amount = String.valueOf(int_proxy_bid_rs);

                    tv_bidvalue.setText(rs_value);
                    iv_iconproxy.setText("₹");
                }

                tv_confim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dollerRate = f_doller;
                        double doller = Double.parseDouble(dollerRate);

                        f_lot = reference;

                        String siteUserID = data.getObjectAsString("userid");
                        String productID = product_id;
                        String str_ProxyAmt = edt_proxy.getText().toString().trim();
                        String str_ProxyAmtus = Double.toString(Double.parseDouble(str_ProxyAmt)/doller);

                        int fb = Integer.parseInt(dollerRate);
                        int rl = Integer.parseInt(str_ProxyAmt);

                        int str_ProxyAmtus_new = rl / fb;

                        String proxy_amt_us = Integer.toString(str_ProxyAmtus_new);

                        if (str_ProxyAmt.isEmpty())
                        {
                            Toast.makeText(mContext, "Pls Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {

                                String entered_value = edt_proxy.getText().toString();
                                String bid_value = value_for_cmpr;
                                int int_entered_value = Integer.parseInt(entered_value);
                                int int_bid_value = Integer.parseInt(bid_value);

                                if(int_entered_value > int_bid_value)
                                {
                                    if (is_us)
                                    {

                                        String str_Proxy_for_us = edt_proxy.getText().toString();

                                        int fb1 = Integer.parseInt(dollerRate);
                                        int rl1 = Integer.parseInt(str_Proxy_for_us);

                                        int str_ProxyAmtrs = rl1 * fb1;

                                        String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                        Toast.makeText(mContext, "from US", Toast.LENGTH_SHORT).show();

                                        String Createdby  = data.getObjectAsString("name");
                                        if(utility.checkInternet())
                                        {

                                            if(fragment_type.equals("upcomming"))
                                            {
                                                ProxyBid_for_upcoming(siteUserID,productID,proxy_amt_for_rs,proxy_amt_for_rs,"0","",Createdby,Auction_id);

                                            }
                                            else
                                            {
                                                ProxyBid(str_ProxyAmt,productID,siteUserID,dollerRate,proxy_amt_for_rs,f_lot);
                                            }


                                        }
                                        else
                                        {

                                            show_dailog("Please Check Internet Connection");
                                        }

                                    }
                                    else                                 {
                                        Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                        if(utility.checkInternet())
                                        {
                                            String Createdby  = data.getObjectAsString("name");

                                            if(fragment_type.equals("upcomming"))
                                            {
                                                ProxyBid_for_upcoming(siteUserID,productID,str_ProxyAmt,proxy_amt_us,"0","",Createdby,Auction_id);

                                            }
                                            else
                                            {
                                                ProxyBid(str_ProxyAmt,productID,siteUserID,dollerRate,proxy_amt_us,f_lot);
                                            }

                                        }
                                        else
                                        {

                                            show_dailog("Please Check Internet Connection");
                                        }


                                    }

                                }
                                else
                                {

                                    Toast.makeText(mContext, "Proxy Must Greater Than Current Bid Value", Toast.LENGTH_SHORT).show();

                                }


                        }


                    }
                });
                bid_proxy = dialogBuilder.create();
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bid_proxy.dismiss();
                    }
                });


                bid_proxy.show();
                bid_proxy.setCanceledOnTouchOutside(false);
                }

            }
        });


    }
    private void AddToGallary(String productID,String siteUserID) {

        if (utility.checkInternet()) {

            String addtogallaryUrl = "http://54.169.222.181/api/v2/guru/_proc/spAddToGallery("+productID+","+siteUserID+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + addtogallaryUrl);
            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, addtogallaryUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);
                    Toast.makeText(mContext, "Succesfully Added to Gallary", Toast.LENGTH_SHORT).show();


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

                    String str_json = result;


                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            JSONObject object = jobject.getJSONObject("artist_by_artistid");
                            JSONObject obj_category_by_categoryid = jobject.getJSONObject("category_by_categoryid");
                            JSONObject obj_medium_by_mediumid = jobject.getJSONObject("medium_by_mediumid");


                            str_medium = obj_medium_by_mediumid.getString("medium");

                            str_FirstName = object.getString("FirstName");
                            str_LastName = object.getString("LastName");
                            str_Profile = object.getString("Profile");


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

                            str_estamiate= jobject.getString("estamiate");
                            str_category = obj_category_by_categoryid.getString("category");

                            tv_title.setText(str_FirstName + str_LastName);
                            tv_title_sub.setText(str_title);
//                            tv_current_bid.setText(str_Bidpricers);
//                            tv_next_bid.setText(str_Bidpriceus);

                            tv_artist.setText(str_title);
                            tv_category.setText(str_category);
                            tv_medium.setText(str_medium);
                            tv_year.setText(str_title);
                            tv_size.setText(str_productsize);
                            tv_art_size.setText(str_productsize);


                            Thumbnail = str_thumbnail;
                            Reference=reference;
                            OldPriceRs=str_pricers;
                            OldPriceUs=str_priceus;
                            Auctionid=reference;


                            String str_without_html= Html.fromHtml(str_description).toString();
                            tv_addinfo_one.setText(str_without_html);

                            Picasso.with(getApplicationContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + str_image)
                                    .into(lot_image);


                            String currency = data.getObjectAsString("currency");
                            System.out.println("currency" + currency);

                            if (currency.equals("USD")) {
//                                tv_type.setText("USD");
                                int next_bid_doller=0;
                                System.out.println("currency" + "usd");
                                System.out.println("currency" +str_priceus);
                                is_us = true;
                                tv_estimate.setText(str_estamiate);

                                int int_str = Integer.parseInt(str_priceus);
                                String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
                                System.out.println("currency" +str_ustest);
                                tv_current_lot.setText(str_ustest);
                                int myNum = Integer.parseInt(str_pricers);
                                if (myNum<10000000)
                                {
                                    next_bid_doller = Get_10_value(str_priceus);
                                }
                                else
                                {
                                    next_bid_doller = Get_5_value(str_priceus);
                                }
                                String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(next_bid_doller);

                                tv_next_lot.setText(valuebyerprimium1);
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

                                int int_str = Integer.parseInt(str_pricers);
                                String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

                                tv_current_lot.setText(str_ustest);

                                int myNum = Integer.parseInt(str_pricers);
                                if (myNum<10000000)
                                {
                                    next_bid = Get_10_value(str_pricers);
                                }
                                else
                                {
                                    next_bid = Get_5_value(str_pricers);
                                }

                                String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(next_bid);

                                tv_next_lot.setText(valuebyerprimium1);
                                iv_one.setImageResource(R.drawable.rupee);
                                iv_two.setImageResource(R.drawable.rupee);
                            }
                            if(str_Profile.isEmpty())
                            {

                                str_Profile = "Artist Profile Not Available.";
                                start= "Artist Profile Not Available.";
                            }
                            else
                            {

                                String filename = str_Profile;

                                System.out.println("start" + filename);
                                int iend = filename.indexOf(".");

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
                                        "}\n" +
                                        "</style>\n" +
                                        "</head>\n" +
                                        "<body>\n" +
                                        " "+start+"</body>\n" +
                                        "</html>";




//                                tv_abt_artist.setText(start);

                                wb_bidding.loadData(test1, "text/html", "UTF-8");
                                wb_bidding.setVisibility(View.VISIBLE);

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
                                                "}\n" +
                                                "</style>\n" +
                                                "</head>\n" +
                                                "<body>\n" +
                                                ""+str_Profile+"</body>\n" +
                                                "</html>";
                                        wb_biddingtwo.loadData(test2, "text/html", "UTF-8");
                                        wb_bidding.setVisibility(View.GONE);
                                        wb_biddingtwo.setVisibility(View.VISIBLE);
                                        System.out.println("str_Profile" + str_Profile);
                                        tv_read_more.setText("Read Less");
                                    }
                                    else if (tv_read_more.getText().equals("Read Less"))
                                    {
                                        wb_bidding.loadData(test1, "text/html", "UTF-8");
                                        wb_biddingtwo.setVisibility(View.GONE);
                                        wb_bidding.setVisibility(View.VISIBLE);
//                                        tv_abt_artist.setText(start);
                                        System.out.println("str_Profile" + start);
                                        tv_read_more.setText("Read More");

                                    }


                                }
                            });


                        } else {
                            Toast.makeText(mContext, "Please Check Internet Connection.",Toast.LENGTH_LONG)
                                    .show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();


                        Toast.makeText(mContext, "Please Check Internet Connection.",Toast.LENGTH_LONG)
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
                // do whatever
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

        if (utility.checkInternet()) {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            final   String userproxy = userProxyAmount;
            final String lot_no = f_lot;


            ServiceHandler serviceHandler = new ServiceHandler(mContext);


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

                                    Toast.makeText(mContext, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
                                    String number = Obj.getString("mobileNum");
                                    bid_proxy.dismiss();

                                    show_dailog("You Succesfully Bid");
                                    getUpcomingAuction();
                                    registerUser(lot_no,userproxy,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    Toast.makeText(mContext, "U r out of bid", Toast.LENGTH_SHORT).show();
                                    bid_proxy.dismiss();

                                    show_dailog("U r out of bid");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    Toast.makeText(mContext, "You can not bid for this lot right now as you are already leading for this lot.", Toast.LENGTH_SHORT).show();
                                    bid_proxy.dismiss();

                                    show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

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
    private void GetData(String value,String str_productid,String rs_amount,String productid,String userid,String dollerrate,String proxy_new_us,String lot) {

        if (utility.checkInternet()) {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/Acution?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=productid="+str_productid+"";
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

                                    System.out.println("ttt"+"bid valid ");

                                    BidNow(trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot);
                                }
                                else
                                {

                                    System.out.println("ttt"+"bid not valid  ");
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

                                    if (is_us)
                                    {
                                        int int_proxy_bid_rselse_us = Get_10_value(rupee_value);


                                        String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_us);
                                        tv_bidvalue.setText(str_int_us);
                                        iv_icon.setText("US$");
                                    }
                                    else
                                    {
                                        System.out.println("ttt"+"new value set  ");
                                        int int_proxy_bid_rselse_rs = Get_10_value(rupee_value);

                                        rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_rs);

                                        str_rs_amount = String.valueOf(int_proxy_bid_rselse_rs);

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

                                                if (is_us) {
//                                                    BidNow(us_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
                                                    GetData("US",f_pro_id,us_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
                                                } else {

                                                    System.out.println("ttt"+"re bid ");

                                                    GetData("RS",f_pro_id,rs_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
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

        if (utility.checkInternet())
        {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spBid("+str_Amount+","+str_productID+","+str_userID+","+dollerrate+","+proxy_new_us+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            final   String str_lot = tlot;
            final String str_amt = str_Amount;

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

                    String str_json = result;

                    try {
                        if (str_json != null)
                        {

                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {
                                JSONObject Obj = jsonArray.getJSONObject(0);
                                String currentStatus = Obj.getString("currentStatus");
                                //String emailID = Obj.getString("emailID");
                                // String mobilrNum = Obj.getString("mobileNum");


//                                Toast.makeText(mContext,currentStatus, Toast.LENGTH_SHORT).show();
                                if(currentStatus.equals("1"))
                                {

                                    Toast.makeText(mContext, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
                                    String number = Obj.getString("mobileNum");
                                    bid_now.dismiss();

                                    show_dailog("You Succesfully Bid");
                                    getUpcomingAuction();
                                    registerUser(str_lot,str_amt,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    Toast.makeText(mContext, "U r out of bid", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    show_dailog("U r out of bid");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    Toast.makeText(mContext, "You can not bid for this lot right now as you are already leading for this lot.", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

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
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/ProxyAuctionDetails?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
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

                                Toast.makeText(mContext, "You Succesfully Done Proxy Bid", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mContext,response,Toast.LENGTH_LONG).show();
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

}

