package com.infomanav.astaguru;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
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

import adapter.AuctionGallaryAdpter;
import adapter.AuctionGalleryUpcomingAdpter;
import adapter.Past_Auction_SubAdpter;
import model_classes.*;
import model_classes.AuctionGallary_Model;
import model_classes.Past_sub_Model;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;
import views.ExpandableHeightGridView;

/**
 * Created by android-javed on 03-10-2016.
 */

public class Auction_Gallary_Activity  extends AppCompatActivity implements View.OnClickListener {

    ArrayList<AuctionGallary_Model> appsList;
    private Utility utility;
    private ExpandableHeightGridView gridviewcurrent,gridviewpast;
    Context context;
    String str_userid;

    AuctionGallary_Model auctionGallary_model;
    AuctionGallaryAdpter auctionGallaryAdpter,auctionGallaryAdpterUpcoming;
    SessionData sessionData;


    String Bidartistuserid,currentDate,str_collectors,productdate,MyUserID,HumanFigure,str_productid,
            str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,
            str_category,str_small_img,str_Bidpricers,str_Bidpriceus,Auctionname,Picture;

    String artistid,str_FirstName,str_LastName,str_Profile, medium, productsize,estamiate,DollarRate,Online,pricelow,pricehigh;

    String pricers, priceus, artist_name, str_Bidclosingtime, image;
    private int mInterval = 10000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    private boolean is_first = true,is_start=false;
    private KProgressHUD hud;
    RequestQueue requestQueue;
    private TextView tv_no_data_found;
    private TextView tv_current_auction, tv_past_auction,tv_detail;
    private LinearLayout lay_current_auction,lay_past_auction;
    private View view_current_auction, view_past_auction;
    private NestedScrollView scroller;
    ArrayList<Past_sub_Model> appsListUpcoming;
    private boolean is_us = false;
    AuctionGalleryUpcomingAdpter auctionGalleryUpcomingAdpter;
    TextView tv_currency,tv_rs_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_gallary);

        context =Auction_Gallary_Activity.this;
        sessionData = new SessionData(context);
        str_userid = sessionData.getObjectAsString("userid");
        mHandler = new Handler();
        requestQueue = Volley.newRequestQueue(context);

        utility = new Utility(getApplicationContext());
        tv_no_data_found = (TextView) findViewById(R.id.tv_no_data_found);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("My Auction Gallery");
        gridviewcurrent = (ExpandableHeightGridView) findViewById(R.id.gridviewcurrent);
        gridviewcurrent.setExpanded(true);

        gridviewpast = (ExpandableHeightGridView) findViewById(R.id.gridviewpast);
        gridviewpast.setExpanded(true);

        tv_current_auction = (TextView) findViewById(R.id.tv_current_auction);
        tv_past_auction = (TextView) findViewById(R.id.tv_past_auction);
        lay_current_auction= (LinearLayout) findViewById(R.id.lay_current_auction);;
        lay_past_auction= (LinearLayout) findViewById(R.id.lay_past_auction);
        tv_rs_type = (TextView) findViewById(R.id.tv_rs_type);
        tv_currency = (TextView) findViewById(R.id.tv_currency);

        view_current_auction = (View) findViewById(R.id.view_current_auction);
        view_past_auction = (View) findViewById(R.id.view_past_auction);
        scroller = (NestedScrollView) findViewById(R.id.scroller);
        lay_current_auction.setOnClickListener(this);
        lay_past_auction.setOnClickListener(this);

        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        tv_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (tv_rs_type.getText().toString().equals("INR"))
                {
                    tv_rs_type.setText("USD");
                }
                else if (tv_rs_type.getText().toString().equals("USD"))
                {
                    tv_rs_type.setText("INR");
                }

                try
                {
                    if(auctionGallaryAdpter!=null)
                    {
                        if (tv_rs_type.getText().toString().equals("USD"))
                        {
                            auctionGallaryAdpter.changeCurrency(true);
                            activity_changeCurrency();
                        }
                        else if (tv_rs_type.getText().toString().equals("INR"))
                        {
                            auctionGallaryAdpter.changeCurrency(false);
                            activity_changeCurrency();
                        }

                    }
                    if(auctionGalleryUpcomingAdpter!=null)
                    {
                        if (tv_rs_type.getText().toString().equals("USD"))
                        {
                            auctionGalleryUpcomingAdpter.changeCurrency(true);
                            activity_changeCurrency();
                        }
                        else if (tv_rs_type.getText().toString().equals("INR"))
                        {
                            auctionGalleryUpcomingAdpter.changeCurrency(false);
                            activity_changeCurrency();
                        }

                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
        tv_rs_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_rs_type.getText().toString().equals("INR"))
                {
                    tv_rs_type.setText("USD");
                }
                else if (tv_rs_type.getText().toString().equals("USD"))
                {
                    tv_rs_type.setText("INR");
                }

                try
                {
                    if(auctionGallaryAdpter!=null)
                    {
                        if (tv_rs_type.getText().toString().equals("USD"))
                        {
                            auctionGallaryAdpter.changeCurrency(true);
                            activity_changeCurrency();
                        }
                        else if (tv_rs_type.getText().toString().equals("INR"))
                        {
                            auctionGallaryAdpter.changeCurrency(false);
                            activity_changeCurrency();
                        }

                    }
                    if(auctionGalleryUpcomingAdpter!=null)
                    {
                        if (tv_rs_type.getText().toString().equals("USD"))
                        {
                            auctionGalleryUpcomingAdpter.changeCurrency(true);
                            activity_changeCurrency();
                        }
                        else if (tv_rs_type.getText().toString().equals("INR"))
                        {
                            auctionGalleryUpcomingAdpter.changeCurrency(false);
                            activity_changeCurrency();
                        }

                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });



        getMyAuctionGallery();

    }

    public void activity_changeCurrency() {
        if (is_us)
        {
            is_us = false;
        }
        else
        {
            is_us = true;
        }
//        notifyDataSetChanged();
    }


/*    @Override
    public void onPause(){
        super.onPause();
        stopRepeatingTask();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startRepeatingTask();
    }*/

  /*  public void GetAuctionGAllary()
    {

        if (utility.checkInternet())
        {

             String strPastAuctionUrl = Application_Constants.Main_URL+"getMyGallery?api_key="+ Application_Constants.API_KEY+"&filter=(userid="+str_userid+")";

            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;

                    String status;

                    appsList = new ArrayList<>();
                    appsListUpcoming = new ArrayList<>();
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

                                    Auctionname = Obj.getString("Auctionname");
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
                                    Bidartistuserid= Obj.getString("bidartistuserid");
                                    medium = Obj.getString("medium");
                                    productsize = Obj.getString("productsize");
                                    estamiate = Obj.getString("estamiate");
                                    DollarRate = Obj.getString("DollarRate");
                                    reference = Obj.getString("reference");
                                    productdate = Obj.getString("productdate");
                                    HumanFigure = Obj.getString("HumanFigure");
                                    str_collectors = Obj.getString("collectors");
                                    MyUserID = Obj.getString("MyUserID");
                                    Picture = Obj.getString("Picture");
                                    str_Profile = Obj.getString("Profile");
                                    Online = Obj.getString("Online");

                                    if(pricers.equalsIgnoreCase("null"))
                                    {
                                        pricers = "0";
                                    }
                                    if(priceus.equalsIgnoreCase("null"))
                                    {
                                        priceus = "0";
                                    }

                                    status= Obj.getString("status");

                                    if (Obj.has("currentDate")) {
                                        currentDate = Obj.getString("currentDate");
                                    } else {
                                        currentDate = "2017-01-10 19:55:27";
                                    }
                                    String newtext = reference.trim();


                                   // str_Profile = "Profile";

                                    artist_name = str_FirstName+str_LastName;



                                    str_category = Obj.getString("category");

                                    if(status.equalsIgnoreCase("Current"))
                                    {
                                        auctionGallary_model = new AuctionGallary_Model( str_productid,  str_category,  artist_name,  str_Profile,
                                                str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,
                                                str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium,
                                                productsize,estamiate,DollarRate,newtext,Bidartistuserid,productdate, MyUserID, str_collectors,
                                                currentDate,HumanFigure,status,str_FirstName,str_LastName,Auctionname,Picture,Online);

                                        appsList.add(auctionGallary_model);
                                    }
                                    else {
                                        auctionGallary_model = new AuctionGallary_Model( str_productid,  str_category,  artist_name,  str_Profile,
                                                str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,
                                                str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium,
                                                productsize,estamiate,DollarRate,newtext,Bidartistuserid,productdate, MyUserID, str_collectors,
                                                currentDate,HumanFigure,status,str_FirstName,str_LastName,Auctionname,Picture,Online);

                                        appsListUpcoming.add(auctionGallary_model);
                                    }


                                }

                                auctionGallaryAdpter = new AuctionGallaryAdpter(context,R.layout.current_listview,appsList,false,Auction_Gallary_Activity.this );
                                auctionGallaryAdpterUpcoming = new AuctionGallaryAdpter(context,R.layout.current_listview,appsListUpcoming,false,Auction_Gallary_Activity.this );

                                gridviewcurrent.setAdapter(auctionGallaryAdpter);
                                gridviewpast.setAdapter(auctionGallaryAdpterUpcoming);



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

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try
            {
                getMyAuctionGallery();
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_close)
        {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    public void getMyAuctionGallery()
    {

        if (utility.checkInternet())
        {

            if (is_first) {


                hud = KProgressHUD.create(context)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setDimAmount(0.5f);

                hud.show();

            }
            String url = Application_Constants.Main_URL+"getMyGallery?api_key="+ Application_Constants.API_KEY+"&filter=(userid="+str_userid+")";

            System.out.println("MyAuctionGallery " + url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String str_json = response,status;
                            String  currentDate;
                            appsList = new ArrayList<>();
                            appsListUpcoming = new ArrayList<>();
                            // String str_FirstName,str_LastName,str_Profile;

                            try {
                                if (str_json != null)
                                {
                                    JSONObject jobject = new JSONObject(str_json);

                                    if (jobject.length() > 0)
                                    {
                                        JSONArray jsonArray = new JSONArray();
                                        jsonArray = jobject.getJSONArray("resource");
                                        tv_no_data_found.setVisibility(View.GONE);
                                        gridviewcurrent.setVisibility(View.VISIBLE);
                                        gridviewpast.setVisibility(View.GONE);

                                        if(jsonArray.length()>0)
                                        {
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject Obj = jsonArray.getJSONObject(i);

                                                Auctionname = Obj.getString("Auctionname");
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
                                                Bidartistuserid= Obj.getString("bidartistuserid");
                                                medium = Obj.getString("medium");
                                                productsize = Obj.getString("productsize");
                                                estamiate = Obj.getString("estamiate");
                                                DollarRate = Obj.getString("DollarRate");
                                                reference = Obj.getString("reference");
                                                productdate = Obj.getString("productdate");
                                                HumanFigure = Obj.getString("HumanFigure");
                                                str_collectors = Obj.getString("collectors");
                                                MyUserID = Obj.getString("MyUserID");
                                                Picture = Obj.getString("Picture");
                                                str_Profile = Obj.getString("Profile");
                                                Online = Obj.getString("Online");
                                                pricelow = Obj.getString("pricelow");
                                                pricehigh = Obj.getString("pricehigh");

                                                status= Obj.getString("status");

                                                if (Obj.has("currentDate")) {
                                                    currentDate = Obj.getString("currentDate");
                                                } else {
                                                    currentDate = "2017-01-10 19:55:27";
                                                }
                                                String newtext = reference.trim();


                                                // str_Profile = "Profile";

                                                artist_name = str_FirstName+str_LastName;



                                                str_category = Obj.getString("category");




                                                if(status.equalsIgnoreCase("Current"))
                                                {
                                                    auctionGallary_model = new AuctionGallary_Model( str_productid,  str_category,  artist_name,  str_Profile,
                                                            str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,
                                                            str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium,
                                                            productsize,estamiate,DollarRate,newtext,Bidartistuserid,productdate, MyUserID, str_collectors,
                                                            currentDate,HumanFigure,status,str_FirstName,str_LastName,Auctionname,Picture,Online);

                                                    appsList.add(auctionGallary_model);
                                                }
                                                else {

                                                    Past_sub_Model country = new Past_sub_Model(str_title, str_description, str_artistid, str_thumbnail, str_productsize, str_image, str_small_img, str_Profile, artist_name, str_category,str_productid,pricers,
                                                            priceus,true,estamiate,productsize,productdate,reference,DollarRate,
                                                            Online,str_collectors,str_FirstName,str_LastName,pricelow,Auctionname,medium,Picture,Bidartistuserid);
                                                    appsListUpcoming.add(country);
                                                }
                                        }


                                        }
                                        else {
                                            tv_no_data_found.setVisibility(View.VISIBLE);
                                            gridviewcurrent.setVisibility(View.INVISIBLE);
                                            gridviewpast.setVisibility(View.INVISIBLE);
                                        }
                                        if (hud != null && hud.isShowing())
                                        {
                                            hud.dismiss();
                                        }

                                        if (is_first)
                                        {

                                            auctionGallaryAdpter = new AuctionGallaryAdpter(context,R.layout.current_listview,appsList,false,Auction_Gallary_Activity.this );
                                           // auctionGallaryAdpterUpcoming = new AuctionGallaryAdpter(context,R.layout.current_listview,appsListUpcoming,false,Auction_Gallary_Activity.this );
                                            auctionGalleryUpcomingAdpter = new AuctionGalleryUpcomingAdpter(context, appsListUpcoming,is_us,"upcomming");

                                            gridviewcurrent.setAdapter(auctionGallaryAdpter);
                                            gridviewpast.setAdapter(auctionGalleryUpcomingAdpter);

                                            startRepeatingTask();

                                        }
                                        else
                                            {
//                                            gridview.setVisibility(View.VISIBLE);

                                            if(!is_start)
                                            {
                                                auctionGallaryAdpter.Upadte_GridViewWithFilter(appsList,false);
                                               // auctionGallaryAdpterUpcoming.Upadte_GridViewWithFilter(appsListUpcoming,false);
                                                startRepeatingTask();
                                            }
                                            else
                                            {
                                                auctionGallaryAdpter.Upadte_GridViewWithFilter(appsList,false);
                                               // auctionGallaryAdpterUpcoming.Upadte_GridViewWithFilter(appsListUpcoming,false);
                                            }



                                            // startRepeatingTask();

                                               /* setAdapters();
                                                startRepeatingTask();*/

                                        }





                                    }
                                    else
                                        {
                                        if (hud != null && hud.isShowing())
                                        {
                                            hud.dismiss();
                                        }
                                            tv_no_data_found.setVisibility(View.VISIBLE);
                                            gridviewcurrent.setVisibility(View.INVISIBLE);
                                            gridviewpast.setVisibility(View.INVISIBLE);

                                    }
                                } else {
                                    if (hud != null && hud.isShowing())
                                    {
                                        hud.dismiss();
                                    }
                                    tv_no_data_found.setVisibility(View.VISIBLE);
                                    gridviewcurrent.setVisibility(View.INVISIBLE);
                                    gridviewpast.setVisibility(View.INVISIBLE);
                                }

                            } catch (JSONException e) {

                                tv_no_data_found.setVisibility(View.VISIBLE);
                                gridviewcurrent.setVisibility(View.INVISIBLE);
                                gridviewpast.setVisibility(View.INVISIBLE);

                                if (hud != null && hud.isShowing())
                                {
                                    hud.dismiss();
                                }
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            tv_no_data_found.setVisibility(View.VISIBLE);
                            gridviewcurrent.setVisibility(View.INVISIBLE);
                            gridviewpast.setVisibility(View.INVISIBLE);
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
                startRepeatingTask();

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

                stopRepeatingTask();
                // scroller.scrollTo(0, 0);
                // getUpcomingAuction(Application_Constants.Main_URL+"lotslatest?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                break;


            default:
                break;
        }
    }
}
