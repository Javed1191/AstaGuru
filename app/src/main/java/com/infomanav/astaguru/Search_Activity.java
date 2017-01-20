package com.infomanav.astaguru;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.AuctionGallaryAdpter;
import adapter.Search_Adpter;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by fox-2 on 12/1/2016.
 */

public class Search_Activity extends AppCompatActivity {

    ArrayList<Model_Search> appsList;

    private Utility utility;
    private GridView gridview;
    Context context;
    String str_userid;

    Model_Search Model_Search;
    Search_Adpter search_adpter;
    SessionData sessionData;
    String str_search;

    String str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,str_category,str_small_img,str_Bidpricers,str_Bidpriceus;

    String artistid,str_FirstName,str_LastName,str_Profile, medium, productsize,estamiate,DollarRate;

    String pricers, priceus, artist_name, str_Bidclosingtime, image, auctiondate, bidartistuserid;

    EditText edt_search;
    String str_status;
    TextView tv_Current,tv_Upcomming,tv_Past;

    LinearLayout lin_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);


        context =Search_Activity.this;
        sessionData = new SessionData(context);

        str_userid = sessionData.getObjectAsString("userid");

        appsList = new ArrayList<>();
        utility = new Utility(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edt_search = (EditText)toolbar.findViewById(R.id.edt_search);

        lin_text = (LinearLayout)findViewById(R.id.lin_text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        tv_Current = (TextView) findViewById(R.id.tv_Current);
         tv_Upcomming= (TextView) findViewById(R.id.tv_Upcomming);
        tv_Past= (TextView) findViewById(R.id.tv_Past);
        lin_text.setVisibility(View.VISIBLE);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                lin_text.setVisibility(View.VISIBLE);
                gridview.setVisibility(View.GONE);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                lin_text.setVisibility(View.VISIBLE);
                gridview.setVisibility(View.GONE);
            }
        });

        tv_Current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appsList.clear();
                str_search = edt_search.getText().toString().trim();
                if (str_search != null && !str_search.isEmpty())
                {
                    GetSearchResult("Upcomming");
                    lin_text.setVisibility(View.GONE);
                    gridview.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(context, "Please Enter Search Key", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_Upcomming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appsList.clear();
                str_search = edt_search.getText().toString().trim();
                if (str_search != null && !str_search.isEmpty())
                {
                    GetSearchResult("Upcomming");
                    lin_text.setVisibility(View.GONE);
                    gridview.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(context, "Please Enter Search Key", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_Past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appsList.clear();
                str_search = edt_search.getText().toString().trim();

                if (str_search != null && !str_search.isEmpty())
                {

                    GetSearchResult("Past");

                    lin_text.setVisibility(View.GONE);
                    gridview.setVisibility(View.VISIBLE);
                }
                else
                {

                    Toast.makeText(context, "Please Enter Search Key", Toast.LENGTH_SHORT).show();

                }



            }
        });


        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        gridview = (GridView) findViewById(R.id.gridview);



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String str_id = appsList.get(position).getStr_productid();
                Intent intent = new Intent(context,Search_Lot_Details.class);
                intent.putExtra("str_status",str_status);
                System.out.println("str_status" + str_status);
                intent.putExtra("str_id",str_id);
                startActivity(intent);

            }
        });

    }



    private void GetSearchResult(String auction) {

        if (utility.checkInternet()) {

            String Auction_Type = auction;

            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spSearch("+str_search+","+Auction_Type+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";

            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;


                    try {
                        if (str_json != null)
                        {
                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    str_productid = Obj.getString("productid");
                                    str_title = Obj.getString("title");
//                                    str_description = Obj.getString("description");
                                    str_description = "no data for this";
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
                                    str_status = Obj.getString("status");
                                    bidartistuserid = "12";
                                    String newtext = reference.trim();

                                    System.out.println("str_status" + str_status);
                                    str_Profile = "Profile";

                                    artist_name = str_FirstName+str_LastName;


//                                    JSONObject obj_category_by_categoryid = Obj.getJSONObject("category_by_categoryid");

                                    str_category = Obj.getString("category");




                                    Model_Search  = new Model_Search( str_productid,  str_category,  artist_name,  str_Profile,  str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,  str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium, productsize,estamiate,DollarRate,newtext,bidartistuserid);
                                    appsList.add(Model_Search);

                                }

                                search_adpter = new Search_Adpter(context,R.layout.search_single,appsList,false);
                                gridview.setAdapter(search_adpter);



                            } else {
                                Toast.makeText(context, "Result Not Found", Toast.LENGTH_SHORT).show();
                                gridview.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(context, "Result Not Found", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search)
        {
            finish();

        }


        return super.onOptionsItemSelected(item);
    }
}

