package com.infomanav.astaguru;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.AuctionGallaryAdpter;
import adapter.CategoryAdpter;
import adapter.CurrentAuctionAdapter_gridview;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 03-10-2016.
 */

public class Auction_Gallary_Activity  extends AppCompatActivity {

    ArrayList<AuctionGallary_Model> appsList;
    String[] mydateList = new String[]{"July, 2016", "September, 2016", "September, 2016", "September, 2016", "December, 2016"};

    private String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/AuctionList?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
    private Utility utility;
    private GridView gridview;
    Context context;
    String str_userid;

    AuctionGallary_Model auctionGallary_model;
    AuctionGallaryAdpter auctionGallaryAdpter;
    SessionData sessionData;


    String str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,str_category,str_small_img,str_Bidpricers,str_Bidpriceus;

    String artistid,str_FirstName,str_LastName,str_Profile, medium, productsize,estamiate,DollarRate;

    String pricers, priceus, artist_name, str_Bidclosingtime, image, auctiondate, bidartistuserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_gallary);
//current
//        http://54.169.222.181/api/v2/guru/_table/Acution?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online=27


        context =Auction_Gallary_Activity.this;
        sessionData = new SessionData(context);

                str_userid = sessionData.getObjectAsString("userid");


        utility = new Utility(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("My Auction Gallery");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        gridview = (GridView) findViewById(R.id.gridview);


        getUpcomingAuction1();

    }



    private void getUpcomingAuction1() {

        if (utility.checkInternet()) {

             String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/getMyGallery?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=MyuserID="+str_userid+"";

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

                                    medium = Obj.getString("medium");
                                    productsize = Obj.getString("productsize");
                                    estamiate = Obj.getString("estamiate");
                                    DollarRate = Obj.getString("DollarRate");
                                    reference = Obj.getString("reference");
                                    bidartistuserid = Obj.getString("bidartistuserid");
                                    String newtext = reference.trim();


                                    str_Profile = "Profile";

                                    artist_name = str_FirstName+str_LastName;


//                                    JSONObject obj_category_by_categoryid = Obj.getJSONObject("category_by_categoryid");

                                    str_category = Obj.getString("category");




                                    auctionGallary_model = new AuctionGallary_Model( str_productid,  str_category,  artist_name,  str_Profile,  str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,  str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium, productsize,estamiate,DollarRate,newtext,bidartistuserid);
                                    appsList.add(auctionGallary_model);

                                }

                                auctionGallaryAdpter = new AuctionGallaryAdpter(context,R.layout.current_listview,appsList,false);
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
}
