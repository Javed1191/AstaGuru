package com.infomanav.astaguru;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.Artist_Details_Adpter;
import adapter.CurrentAuctionAdapter_gridview;
import services.Application_Constants;
import services.ServiceHandler;
import services.Utility;
import views.ExpandGridview;
import views.ExpandableHeightGridView;

/**
 * Created by android-javed on 23-11-2016.
 */

public class Artist_Details extends AppCompatActivity implements View.OnClickListener {


    private Utility utility;
    Context context;
    ArrayList<Current_Auction_Model> appsList;
String str_collectors;
    String image, str_msg,Picture_path,Profile,start;
    String  artist_id,strPastAuctionUrl,Str_artistname;
    Current_Auction_Model country;
    ImageView artist_image;
    TextView tv_detail,tv_read_more;
    LinearLayout lin_closeactivity;

    private TextView tv_lot, tv_lettest;
    private View view_lot, view_lettest;
    Artist_Details_Adpter artistDetailsAdpter;

//    private GridView gridview;
    ExpandableHeightGridView gridview;
//    ExpandGridview gridview;
    String pricers, priceus, artist_name, str_Bidclosingtime,  productdate, auctiontitle;


    String str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,str_category,str_small_img,str_Bidpricers,str_Bidpriceus;

    String artistid,str_FirstName,str_LastName,str_Profile, medium, productsize,estamiate,DollarRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);
        context = Artist_Details.this;


        Intent intent = getIntent();
        artist_id = intent.getStringExtra("Str_id");
        Str_artistname = intent.getStringExtra("Str_artistname");
        strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/artist?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=artistid="+artist_id+"";


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



        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText(Str_artistname);



        artist_image = (ImageView) findViewById(R.id.artist_image);
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        tv_read_more = (TextView) findViewById(R.id.tv_read_more);
        gridview = (ExpandableHeightGridView) findViewById(R.id.gridviewupcoming);
        gridview.setExpanded(true);
        lin_closeactivity = (LinearLayout) findViewById(R.id.lin_closeactivity);
        tv_lot = (TextView) findViewById(R.id.tv_lot);
        tv_lettest = (TextView) findViewById(R.id.tv_lettest);

        view_lot = (View) findViewById(R.id.view_lot);
        view_lettest = (View) findViewById(R.id.view_lettest);
        tv_lot.setOnClickListener(this);
        tv_lettest.setOnClickListener(this);
        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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


        getUpcomingAuction();
        getUpcomingAuction(Application_Constants.Main_URL+"lotspopular?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_lot:
                tv_lot.setTextColor(Color.parseColor("#a78e69"));
                tv_lettest.setTextColor(Color.parseColor("#000000"));


                view_lot.setVisibility(View.VISIBLE);
                view_lettest.setVisibility(View.INVISIBLE);

                getUpcomingAuction(Application_Constants.Main_URL+"lotspopular?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                break;

            case R.id.tv_lettest:
                tv_lot.setTextColor(Color.parseColor("#000000"));
                tv_lettest.setTextColor(Color.parseColor("#a78e69"));

                view_lot.setVisibility(View.INVISIBLE);
                view_lettest.setVisibility(View.VISIBLE);

                getUpcomingAuction(Application_Constants.Main_URL+"lotslatest?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                break;


            default:
                break;
        }

    }
    private void getUpcomingAuction()
    {

        if(utility.checkInternet())
        {
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

                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    Profile = Obj.getString("Profile");
                                    Picture_path = Obj.getString("Picture");

//                                    tv_detail.setText(Profile);





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


    private void getUpcomingAuction(String type) {

        if (utility.checkInternet()) {

            String strPastAuctionUrl = type;
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;

                    // String str_FirstName,str_LastName,str_Profile;
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

//                                    JSONObject object = Obj.getJSONObject("artist_by_artistid");
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

//                                    JSONObject obj_category_by_categoryid = Obj.getJSONObject("category_by_categoryid");

                                    str_category = Obj.getString("category");




                                    country = new Current_Auction_Model( str_productid,  str_category,  artist_name,  str_Profile,  str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,  str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium, productsize,estamiate,DollarRate,reference,productdate,"alex",str_collectors,"");
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_close)
//        {
//            finish();
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }
}


