package com.infomanav.astaguru;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import adapter.Bid_History_Adpter;
import adapter.MoviesAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Utility;
import views.ExpandGridview;

/**
 * Created by fox-2 on 12/7/2016.
 */

public class Bid_History extends AppCompatActivity {
    private Utility utility;
    Context context;
    ArrayList<Model_History> appsList;
    String image, str_msg,Picture_path,Profile,start;
    String  productid,strPastAuctionUrl,Str_artistname;
    Model_History country;
    Bid_History_Adpter bid_history_adpter;
    private RecyclerView recyclerView;
    LinearLayout lin_closeactivity;

    TextView tv_lot,tv_ac_artist,tv_ac_category,tv_ac_medium
            ,tv_ac_year,tv_ac_size,tv_estimate,tv_noofbid;
    ImageView iv_main_img;
    String str_image;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_history);
        context = Bid_History.this;

        Intent intent = getIntent();
        productid = intent.getStringExtra("Str_id");

        strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spGetBidRecords("+productid+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";


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

        tv_lot= (TextView) findViewById(R.id.tv_lot);
        tv_ac_artist= (TextView) findViewById(R.id.tv_ac_artist);
        tv_ac_category= (TextView) findViewById(R.id.tv_ac_category);
        tv_ac_medium= (TextView) findViewById(R.id.tv_ac_medium);
        tv_ac_year= (TextView) findViewById(R.id.tv_ac_year);
        tv_ac_size= (TextView) findViewById(R.id.tv_ac_size);

        tv_estimate= (TextView) findViewById(R.id.tv_estimate);
        iv_main_img= (ImageView) findViewById(R.id.iv_main_img);




        tv_ac_artist.setText(intent.getStringExtra("str_FirstName"));
        tv_ac_category.setText(intent.getStringExtra("str_category"));
        tv_ac_medium.setText(intent.getStringExtra("str_medium"));
        tv_ac_year.setText(intent.getStringExtra("str_date"));
        tv_ac_size.setText(intent.getStringExtra("str_productsize"));
        tv_estimate.setText(intent.getStringExtra("str_istimate"));
        tv_lot.setText(intent.getStringExtra("str_lot"));
        str_image = intent.getStringExtra("str_image");


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



                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    str_firstname = Obj.getString("Firstname");

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
//                                    str_thumbnail = Obj.getString("thumbnail");

                                    artist_name = str_firstname +" "+ str_lastname;

                                    country = new Model_History(artist_name,str_bid_d,str_bid_r,str_bid_date);
                                    appsList.add(country);

                                }
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());

                                bid_history_adpter = new Bid_History_Adpter(appsList);
                                recyclerView.setAdapter(bid_history_adpter);

                            } else {
                                Toast.makeText(context, "Records Not Found", Toast.LENGTH_SHORT).show();
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


}
