package com.infomanav.astaguru;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.Artist_Details_Adpter;
import adapter.Artist_Details_Past_Adpter;
import adapter.NotificationAdapter;
import adapter.Past_Auction_SubAdpter;
import model_classes.*;
import model_classes.Current_Auction_Model;
import model_classes.Past_sub_Model;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;
import views.ExpandableHeightGridView;

/**
 * Created by android-javed on 23-11-2016.
 */

public class Artist_Details extends AppCompatActivity implements View.OnClickListener {


    private Utility utility;
    Context context;
    ArrayList<Current_Auction_Model> currentAuctionList,pastAuctionList;
    ArrayList<Past_sub_Model> appsList;
    String str_collectors;
    String image, str_msg,Picture_path,Profile,start;
    String  artist_id,strPastAuctionUrl,Str_artistname;
    Current_Auction_Model country;
    ImageView artist_image;
    TextView tv_read_more;
    LinearLayout lin_closeactivity;

    private TextView tv_current_auction, tv_past_auction,tv_detail;
    private LinearLayout lay_current_auction,lay_past_auction;
    private View view_current_auction, view_past_auction;
    Artist_Details_Adpter artistDetailsAdpter;
    Past_Auction_SubAdpter past_auction_subAdpter;
    private Artist_Details_Past_Adpter artist_details_past_adpter;

    ExpandableHeightGridView gridviewpast,gridviewcurrent;
    String pricers, priceus, artist_name, str_Bidclosingtime,  productdate, Online;


    String str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,
            str_category,str_small_img,str_Bidpricers,str_Bidpriceus,status;

    String artistid,str_FirstName,str_LastName,str_Profile, medium, productsize,estamiate,DollarRate,Picture,
            Auctionname="",auctionBanner="",Auction_id="";
    SessionData data;
    private int mInterval = 10000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    public boolean is_first=true,is_start=false;
    KProgressHUD hud;
    RequestQueue requestQueue;
    private boolean is_us;
    private NestedScrollView scroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);
        context = Artist_Details.this;


        Intent intent = getIntent();

        strPastAuctionUrl = Application_Constants.Main_URL+"artist?api_key="+Application_Constants.API_KEY+"&filter=artistid="+artist_id+"";

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
        data = new SessionData(Artist_Details.this);

        artist_image = (ImageView) findViewById(R.id.artist_image);
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        tv_read_more = (TextView) findViewById(R.id.tv_read_more);
        gridviewcurrent = (ExpandableHeightGridView) findViewById(R.id.gridviewcurrent);
        gridviewcurrent.setExpanded(true);
        gridviewpast = (ExpandableHeightGridView) findViewById(R.id.gridviewpast);
        gridviewpast.setExpanded(true);
        lin_closeactivity = (LinearLayout) findViewById(R.id.lin_closeactivity);
        tv_current_auction = (TextView) findViewById(R.id.tv_current_auction);
        tv_past_auction = (TextView) findViewById(R.id.tv_past_auction);
        lay_current_auction= (LinearLayout) findViewById(R.id.lay_current_auction);;
        lay_past_auction= (LinearLayout) findViewById(R.id.lay_past_auction);

        view_current_auction = (View) findViewById(R.id.view_current_auction);
        view_past_auction = (View) findViewById(R.id.view_past_auction);
        scroller = (NestedScrollView) findViewById(R.id.scroller);
        lay_current_auction.setOnClickListener(this);
        lay_past_auction.setOnClickListener(this);

        mHandler = new Handler();
        requestQueue = Volley.newRequestQueue(context);



        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        if(intent.getExtras()!=null)
        {
            artist_id = intent.getStringExtra("Str_id");
            Str_artistname = intent.getStringExtra("Str_artistname");
            Picture = intent.getStringExtra("Picture");
            Profile = intent.getStringExtra("Profile");
            is_us = intent.getBooleanExtra("is_us",false);

            if(Profile.isEmpty())
            {

                Profile = "Artist Profile Not Available.";
                start= "Artist Profile Not Available.";
                tv_read_more.setVisibility(View.GONE);

            }
            else
            {
                tv_read_more.setVisibility(View.VISIBLE);

               String filename = Profile;
               // int iend = filename.indexOf(".");

                if(filename.length()>200)
                {
                    start = filename.substring(0,200);
                }
                else
                {
                    start = filename;
                }

                tv_detail.setText(Html.fromHtml(start));
                //tv_detail.setText(Html.fromHtml(Profile));
                //makeTextViewResizable(tv_detail,100,Profile,false);


            }



            Picasso.with(context).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + Picture)
                    .into(artist_image);
        }

        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText(Str_artistname);

        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        lin_closeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_read_more.getText().equals("Read More"))
                {
                    tv_detail.setText(Html.fromHtml(Profile));
                    tv_read_more.setText("Read Less");
                }
                else if (tv_read_more.getText().equals("Read Less"))
                {
                    tv_detail.setText(Html.fromHtml(start));
                    tv_read_more.setText("Read More");
                }

            }
        });

        scroller.post(new Runnable() {
            public void run() {
                scroller.fullScroll(ScrollView.FOCUS_UP);
            }
        });


        // getUpcomingAuction();
        getArtistDetails(artist_id);
        //getUpcomingAuction(Application_Constants.Main_URL+"lotspopular?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try
            {
                getArtistDetails(artist_id);
            }
            finally {

                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };
    public void startRepeatingTask()
    {
        mStatusChecker.run();
        is_first = false;
        is_start = true;
    }

    public void stopRepeatingTask() {
        is_start = false;
        mHandler.removeCallbacks(mStatusChecker);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.lay_current_auction:
                tv_current_auction.setTextColor(Color.parseColor("#a78e69"));
                tv_past_auction.setTextColor(Color.parseColor("#000000"));


                view_current_auction.setVisibility(View.VISIBLE);
                view_past_auction.setVisibility(View.INVISIBLE);

                gridviewcurrent.setVisibility(View.VISIBLE);
                gridviewpast.setVisibility(View.GONE);

                //scroller.scrollTo(0, 0);
               // getUpcomingAuction(Application_Constants.Main_URL+"lotspopular?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                break;

            case R.id.lay_past_auction:
                tv_current_auction.setTextColor(Color.parseColor("#000000"));
                tv_past_auction.setTextColor(Color.parseColor("#a78e69"));

                view_current_auction.setVisibility(View.INVISIBLE);
                view_past_auction.setVisibility(View.VISIBLE);

                gridviewcurrent.setVisibility(View.GONE);
                gridviewpast.setVisibility(View.VISIBLE);
               // scroller.scrollTo(0, 0);
               // getUpcomingAuction(Application_Constants.Main_URL+"lotslatest?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                break;


            default:
                break;
        }

    }
    private void getUpcomingAuction()
    {

        if(utility.checkInternet())
        {

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

                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    Profile = Obj.getString("Profile");
                                    Picture_path = Obj.getString("Picture");


                                    if(Profile.isEmpty())
                                    {

                                        Profile = "Artist Profile Not Available.";
                                        start= "Artist Profile Not Available.";

                                    }
                                    else
                                    {

                                        String filename = Profile;
                                        int iend = filename.indexOf(".");
                                        start = filename.substring(0,iend);
                                        tv_detail.setText(start);

                                    }

                                    tv_read_more.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (tv_read_more.getText().equals("Read More"))
                                            {
                                                tv_detail.setText(Profile);
                                                tv_read_more.setText("Read Less");
                                            }
                                            else if (tv_read_more.getText().equals("Read Less"))
                                            {
                                                tv_detail.setText(start);
                                                tv_read_more.setText("Read More");
                                            }

                                        }
                                    });


                                }



                                Picasso.with(context).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + Picture_path)
                                        .into(artist_image);
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


   /* private void getArtistDetails(String artist_id)
    {

        if (utility.checkInternet())
        {
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spGetArtistDetailData("+artist_id+")?api_key="+ Application_Constants.API_KEY;
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();
            final ArrayList<Model_Notification> countryList = new ArrayList<Model_Notification>();
            pastAuctionList = new ArrayList<>();
            currentAuctionList = new ArrayList<>();
            appsList = new ArrayList<>();


            ServiceHandler serviceHandler = new ServiceHandler(Artist_Details.this);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);

                   String  pricelow,DollarRate,str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,
                           str_image,str_productsize,str_category,str_small_img,str_Bidpricers = null,
                           str_Bidpriceus = null,str_collectors,str_FirstName,str_LastName,str_Profile,
                           pricers,priceus,estamiate,productsize,productdate,MyUserID,currentDate="",HumanFigure="";

                    try {
                        if (result != null)
                        {
                            JSONArray jArray = new JSONArray(result);
                            for (int i = 0; i < jArray.length(); i++)
                            {
                                JSONObject Obj = jArray.getJSONObject(i);

                                Auctionname = Obj.getString("Auctionname");
                                str_productid = Obj.getString("productid");
                                str_title = Obj.getString("title");
                                str_description = Obj.getString("description");
                                str_artistid = Obj.getString("artistid");
                                str_thumbnail = Obj.getString("thumbnail");
                                str_image = Obj.getString("image");
                                str_productsize = Obj.getString("productsize");
                                str_small_img = Obj.getString("smallimage");
                                pricers = Obj.getString("Bidpricers");
                                priceus = Obj.getString("Bidpriceus");
                                estamiate = Obj.getString("estamiate");
                                productsize = Obj.getString("productsize");
                                productdate = Obj.getString("productdate");
                                reference = Obj.getString("reference");
                                DollarRate = Obj.getString("DollarRate");
                                str_FirstName = Obj.getString("FirstName");
                                str_LastName = Obj.getString("LastName");
                                str_Profile = Obj.getString("Profile");
                                str_collectors = Obj.getString("collectors");
                                pricelow= Obj.getString("pricelow");
                                status = Obj.getString("status");
                                medium = Obj.getString("medium");
                                Picture = Obj.getString("Picture");
                                String newtext = reference.trim();
                                String artist_name = str_FirstName+" "+ str_LastName;
                                MyUserID = Obj.getString("MyUserID");
                                HumanFigure = Obj.getString("HumanFigure");
                                str_Bidclosingtime = Obj.getString("Bidclosingtime");

                             //  String newtext = reference.trim();

                                str_category = Obj.getString("category");

                                if (Obj.has("currentDate")) {
                                    currentDate = Obj.getString("currentDate");
                                } else {
                                    currentDate = "2017-01-10 19:55:27";
                                }



                               *//* str_productid = Obj.getString("productid");
                                str_title = Obj.getString("title");
                                str_description = Obj.getString("description");
                                str_artistid = Obj.getString("artistid");
                                str_thumbnail = Obj.getString("thumbnail");
                                str_image = Obj.getString("image");
                                str_productsize = Obj.getString("productsize");
                                str_small_img = Obj.getString("smallimage");

                                str_FirstName = Obj.getString("FirstName");
                                str_LastName = Obj.getString("LastName");
                                pricers = Obj.getString("Bidpricers");
                                priceus = Obj.getString("Bidpriceus");
                                str_Bidclosingtime = Obj.getString("Bidclosingtime");
                                str_collectors = Obj.getString("collectors");
                                medium = Obj.getString("medium");
                                productsize = Obj.getString("productsize");
                                estamiate = Obj.getString("estamiate");
                                DollarRate = Obj.getString("DollarRate");
                                str_Profile = "Profile";
                                reference = Obj.getString("reference");
                                artist_name = str_FirstName+str_LastName;
                                productdate = Obj.getString("productdate");
                                Online = Obj.getString("Online");
                                str_category = Obj.getString("category");
                                status = Obj.getString("status");*//*

                                if(status.equalsIgnoreCase("Past"))
                                {

                                    pricers = claculatePercentage(pricers);
                                    priceus = claculatePercentage(priceus);

                                    model_classes.Past_sub_Model country = new model_classes.Past_sub_Model(str_title, str_description, str_artistid, str_thumbnail, str_productsize, str_image, str_small_img, str_Profile, artist_name, str_category,str_productid,pricers,
                                            priceus,true,estamiate,productsize,productdate,reference,DollarRate,
                                            Auction_id,str_collectors,str_FirstName,str_LastName,pricelow,Auctionname,medium,Picture);
                                    appsList.add(country);
                                }
                                else
                                {

                                   country = new Current_Auction_Model(Auctionname,str_productid, str_category, artist_name, Profile, str_small_img, str_productsize, str_image, str_thumbnail, str_artistid, str_description, str_title, str_Bidclosingtime, true, pricers, priceus, medium, productsize, estamiate, DollarRate, newtext, productdate, MyUserID, str_collectors, currentDate,HumanFigure,str_FirstName,str_LastName,Online,Picture,Profile);

                                   // country = new Current_Auction_Model(Auctionname,str_productid,  str_category,  artist_name,  str_Profile,  str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,  str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium, productsize,estamiate,DollarRate,reference,productdate,"alex",str_collectors,"","",str_FirstName,str_LastName,Online,"","");
                                    currentAuctionList.add(country);
                                }



                            }
                            artistDetailsAdpter = new Artist_Details_Adpter(context, R.layout.current_grid, currentAuctionList,false);
                            gridviewcurrent.setAdapter(artistDetailsAdpter);

                            *//*artist_details_past_adpter = new Artist_Details_Past_Adpter(context, R.layout.current_grid, pastAuctionList,false);
                            gridviewpast.setAdapter(artist_details_past_adpter);
*//*
                            past_auction_subAdpter = new Past_Auction_SubAdpter(context, appsList,false,"past");
                            gridviewpast.setAdapter(past_auction_subAdpter);

                            //startRepeatingTask();
                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
            });
        }

    }
*/


    private void getArtistDetails(String artist_id)
    {


        String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spGetArtistDetailData("+artist_id+")?api_key="+ Application_Constants.API_KEY;
        System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
        final Map<String, String> params = new HashMap<String, String>();
        final ArrayList<Model_Notification> countryList = new ArrayList<Model_Notification>();
        pastAuctionList = new ArrayList<>();
        currentAuctionList = new ArrayList<>();
        appsList = new ArrayList<>();


        if (utility.checkInternet())
        {
            if (is_first)
            {


                hud = KProgressHUD.create(context)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setDimAmount(0.5f);

                hud.show();

            }

            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, strPastAuctionUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response;
                            appsList = new ArrayList<>();
                            // String str_FirstName,str_LastName,str_Profile;

                            String  pricelow,DollarRate,str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,
                                    str_image,str_productsize,str_category,str_small_img,str_Bidpricers = null,
                                    str_Bidpriceus = null,str_collectors,str_FirstName,str_LastName,str_Profile,
                                    pricers,priceus,estamiate,productsize,productdate,MyUserID,currentDate="",HumanFigure="";

                            try {
                                if (result != null)
                                {
                                    JSONArray jArray = new JSONArray(result);
                                    for (int i = 0; i < jArray.length(); i++)
                                    {
                                        JSONObject Obj = jArray.getJSONObject(i);

                                        Auctionname = Obj.getString("Auctionname");
                                        str_productid = Obj.getString("productid");
                                        str_title = Obj.getString("title");
                                        str_description = Obj.getString("description");
                                        str_artistid = Obj.getString("artistid");
                                        str_thumbnail = Obj.getString("thumbnail");
                                        str_image = Obj.getString("image");
                                        str_productsize = Obj.getString("productsize");
                                        str_small_img = Obj.getString("smallimage");
                                        pricers = Obj.getString("Bidpricers");
                                        priceus = Obj.getString("Bidpriceus");
                                        estamiate = Obj.getString("estamiate");
                                        productsize = Obj.getString("productsize");
                                        productdate = Obj.getString("productdate");
                                        reference = Obj.getString("reference");
                                        DollarRate = Obj.getString("DollarRate");
                                        str_FirstName = Obj.getString("FirstName");
                                        str_LastName = Obj.getString("LastName");
                                        str_Profile = Obj.getString("Profile");
                                        str_collectors = Obj.getString("collectors");
                                        pricelow= Obj.getString("pricelow");
                                        status = Obj.getString("status");
                                        medium = Obj.getString("medium");
                                        Picture = Obj.getString("Picture");
                                        String newtext = reference.trim();
                                        String artist_name = str_FirstName+" "+ str_LastName;
                                        MyUserID = Obj.getString("MyUserID");
                                        HumanFigure = Obj.getString("HumanFigure");
                                        str_Bidclosingtime = Obj.getString("Bidclosingtime");

                                        if(pricers.equalsIgnoreCase("null"))
                                        {
                                            pricers = "0";
                                        }
                                        if(priceus.equalsIgnoreCase("null"))
                                        {
                                            priceus = "0";
                                        }

                                        //  String newtext = reference.trim();

                                        str_category = Obj.getString("category");

                                        if (Obj.has("currentDate")) {
                                            currentDate = Obj.getString("currentDate");
                                        } else {
                                            currentDate = "2017-01-10 19:55:27";
                                        }


                                        if(status.equalsIgnoreCase("Past"))
                                        {

                                           /* pricers = claculatePercentage(pricers);
                                            priceus = claculatePercentage(priceus);*/

                                            model_classes.Past_sub_Model country = new model_classes.Past_sub_Model(str_title, str_description, str_artistid, str_thumbnail, str_productsize, str_image, str_small_img, str_Profile, artist_name, str_category,str_productid,pricers,
                                                    priceus,true,estamiate,productsize,productdate,reference,DollarRate,
                                                    Auction_id,str_collectors,str_FirstName,str_LastName,pricelow,Auctionname,medium,Picture);
                                            appsList.add(country);
                                        }
                                        else
                                        {

                                            country = new Current_Auction_Model(Auctionname,str_productid, str_category, artist_name, Profile, str_small_img, str_productsize, str_image, str_thumbnail, str_artistid, str_description, str_title, str_Bidclosingtime, true, pricers, priceus, medium, productsize, estamiate, DollarRate, newtext, productdate, MyUserID, str_collectors, currentDate,HumanFigure,str_FirstName,str_LastName,Online,Picture,Profile,false);

                                            // country = new Current_Auction_Model(Auctionname,str_productid,  str_category,  artist_name,  str_Profile,  str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,  str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium, productsize,estamiate,DollarRate,reference,productdate,"alex",str_collectors,"","",str_FirstName,str_LastName,Online,"","");
                                            currentAuctionList.add(country);
                                        }



                                    }



                                    if (hud != null && hud.isShowing())
                                    {
                                        hud.dismiss();
                                    }

                                    if (is_first)
                                    {
                                        artistDetailsAdpter = new Artist_Details_Adpter(context, R.layout.current_grid, currentAuctionList,is_us);
                                        gridviewcurrent.setAdapter(artistDetailsAdpter);

                                        past_auction_subAdpter = new Past_Auction_SubAdpter(context, appsList,is_us,"past");
                                        gridviewpast.setAdapter(past_auction_subAdpter);

                                       startRepeatingTask();

                                    }
                                    else
                                        {
//                                            gridview.setVisibility(View.VISIBLE);

                                            if(!is_start)
                                            {
                                                artistDetailsAdpter.Upadte_GridViewWithFilter(currentAuctionList,false);
                                                startRepeatingTask();
                                            }
                                            else
                                            {
                                                artistDetailsAdpter.Upadte_GridViewWithFilter(currentAuctionList,false);
                                            }


                                               /* setAdapters();
                                                startRepeatingTask();*/

                                    }







                                    //startRepeatingTask();
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
                            hud.dismiss();
                        }
                    });


            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(context, "Please Check Internet Connection.", Toast.LENGTH_LONG)
                    .show();
        }

    }



/*    private void getUpcomingAuction(String type)
    {

        if (utility.checkInternet())
        {

            String strPastAuctionUrl = type;
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;

                    appsList = new ArrayList<>();
                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);

                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    str_productid = Obj.getString("productid");
                                    str_title = Obj.getString("title");
                                    str_description = Obj.getString("description");
                                    str_artistid = Obj.getString("artistid");
                                    str_thumbnail = Obj.getString("thumbnail");
                                    str_image = Obj.getString("image");
                                    str_productsize = Obj.getString("productsize");
                                    str_small_img = Obj.getString("smallimage");

                                    str_FirstName = Obj.getString("FirstName");
                                    str_LastName = Obj.getString("LastName");
                                    pricers = Obj.getString("Bidpricers");
                                    priceus = Obj.getString("Bidpriceus");
                                    str_Bidclosingtime = Obj.getString("Bidclosingtime");
                                    str_collectors = Obj.getString("collectors");
                                    medium = Obj.getString("medium");
                                    productsize = Obj.getString("productsize");
                                    estamiate = Obj.getString("estamiate");
                                    DollarRate = Obj.getString("DollarRate");
                                    str_Profile = "Profile";
                                    reference = Obj.getString("reference");
                                    artist_name = str_FirstName+str_LastName;
                                    productdate = Obj.getString("productdate");
                                    Online = Obj.getString("Online");

                                    str_category = Obj.getString("category");




                                    country = new Current_Auction_Model("AuctionName",str_productid,  str_category,  artist_name,  str_Profile,  str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,  str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium, productsize,estamiate,DollarRate,reference,productdate,"alex",str_collectors,"","",str_FirstName,str_LastName,Online);
                                    appsList.add(country);

                                }

                                artistDetailsAdpter = new Artist_Details_Adpter(context, R.layout.current_grid, appsList,false);
                                gridview.setAdapter(artistDetailsAdpter);



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

    }*/

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
                Intent intent = new Intent(Artist_Details.this,MyAstaGuru_Activity.class);
                startActivity(intent);

                return true;
            case R.id.action_search:
                // do whatever
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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

}


