package com.infomanav.astaguru;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
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
import model_classes.*;
import model_classes.AuctionGallary_Model;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 03-10-2016.
 */

public class Auction_Gallary_Activity  extends AppCompatActivity {

    ArrayList<AuctionGallary_Model> appsList;
    private Utility utility;
    private GridView gridview;
    Context context;
    String str_userid;

    AuctionGallary_Model auctionGallary_model;
    AuctionGallaryAdpter auctionGallaryAdpter;
    SessionData sessionData;


    String Bidartistuserid,currentDate,str_collectors,productdate,MyUserID,HumanFigure,str_productid,
            str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,
            str_category,str_small_img,str_Bidpricers,str_Bidpriceus,Auctionname,Picture;

    String artistid,str_FirstName,str_LastName,str_Profile, medium, productsize,estamiate,DollarRate,Online;

    String pricers, priceus, artist_name, str_Bidclosingtime, image;
    private int mInterval = 10000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    private boolean is_first = true,is_start=false;
    private KProgressHUD hud;
    RequestQueue requestQueue;
    private TextView tv_no_data_found;

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
        gridview = (GridView) findViewById(R.id.gridview);

        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }





        getMyAuctionGallery();

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

    public void GetAuctionGAllary()
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




                                    auctionGallary_model = new AuctionGallary_Model( str_productid,  str_category,  artist_name,  str_Profile,
                                            str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,
                                            str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium,
                                            productsize,estamiate,DollarRate,newtext,Bidartistuserid,productdate, MyUserID, str_collectors,
                                            currentDate,HumanFigure,status,str_FirstName,str_LastName,Auctionname,Picture,Online);

                                    appsList.add(auctionGallary_model);

                                }

                                auctionGallaryAdpter = new AuctionGallaryAdpter(context,R.layout.current_listview,appsList,false,Auction_Gallary_Activity.this );
                                gridview.setAdapter(auctionGallaryAdpter);



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

    private void getMyAuctionGallery()
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

            System.out.println("strPastAuctionUrl " + url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String str_json = response,status;
                            String  currentDate;
                            appsList = new ArrayList<>();
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
                                        gridview.setVisibility(View.VISIBLE);

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




                                                auctionGallary_model = new AuctionGallary_Model( str_productid,  str_category,  artist_name,  str_Profile,
                                                        str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,
                                                        str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium,
                                                        productsize,estamiate,DollarRate,newtext,Bidartistuserid,productdate, MyUserID, str_collectors,
                                                        currentDate,HumanFigure,status,str_FirstName,str_LastName,Auctionname,Picture,Online);

                                                appsList.add(auctionGallary_model);
                                        }



                                        }
                                        else {
                                            tv_no_data_found.setVisibility(View.VISIBLE);
                                            gridview.setVisibility(View.INVISIBLE);
                                        }
                                        if (hud != null && hud.isShowing())
                                        {
                                            hud.dismiss();
                                        }

                                        if (is_first)
                                        {
                                            auctionGallaryAdpter = new AuctionGallaryAdpter(context,R.layout.current_listview,appsList,false,Auction_Gallary_Activity.this );
                                            gridview.setAdapter(auctionGallaryAdpter);

                                            startRepeatingTask();

                                        }
                                        else
                                            {
//                                            gridview.setVisibility(View.VISIBLE);

                                            if(!is_start)
                                            {
                                                auctionGallaryAdpter.Upadte_GridViewWithFilter(appsList,false);
                                                startRepeatingTask();
                                            }
                                            else
                                            {
                                                auctionGallaryAdpter.Upadte_GridViewWithFilter(appsList,false);
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
                                            gridview.setVisibility(View.INVISIBLE);

                                    }
                                } else {
                                    if (hud != null && hud.isShowing())
                                    {
                                        hud.dismiss();
                                    }
                                    tv_no_data_found.setVisibility(View.VISIBLE);
                                    gridview.setVisibility(View.INVISIBLE);
                                }

                            } catch (JSONException e) {

                                tv_no_data_found.setVisibility(View.VISIBLE);
                                gridview.setVisibility(View.INVISIBLE);
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
                            gridview.setVisibility(View.INVISIBLE);
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
}
