package com.infomanav.astaguru;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.BidHistory_Adpter;
import adapter.MyPurchasesAdpter;
import butterknife.ButterKnife;

import services.ServiceHandler;
import services.Utility;

/**
 * Created by android-javed on 04-10-2016.
 */

public class Bid_History_Activity extends AppCompatActivity {

    ArrayList<Bid_History_Model> appsList;
    String[] mydateList = new String[]{"July, 2016", "September, 2016", "September, 2016", "September, 2016", "December, 2016"};

    private String strPastAuctionUrl = "http://54.169.244.245/api/v2/guru/_table/AuctionList?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
    private Utility utility;

    private BidHistory_Adpter mAdapter;
    private Button btn_bid_now,btn_proxy_bid;
    private TextView tv_lot,tv_artist,tv_category,tv_medium,tv_year,tv_size,tv_estimate,tv_no_bids;


    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);




        utility = new Utility(getApplicationContext());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Bid Hostory");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new BidHistory_Adpter(appsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new com.infomanav.astaguru.DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        init();

        getUpcomingAuction();

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

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status, str_msg;
                    String  Category_name;
                    appsList = new ArrayList<>();
                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            //str_status = jobject.getString("status");
                            //str_msg = jobject.getString("msg");
                            if (jobject.length()>0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");

                                for(int i=0; i<jsonArray.length();i++)
                                {
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    Category_name = Obj.getString("Auctionname");


                                    Bid_History_Model country = new Bid_History_Model(Category_name,  Category_name,  Category_name,  Category_name);

                                    appsList.add(country);

                                }
                               /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list_name);
                                mAutoCompleteTextView.setAdapter(adapter);*/




                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }


    public void init()
    {
        utility = new Utility(getApplicationContext());
        btn_bid_now = (Button) findViewById(R.id.btn_bid_now);
        btn_proxy_bid = (Button) findViewById(R.id.btn_proxy_bid);

        tv_lot = (TextView) findViewById(R.id.tv_lot);
        tv_artist = (TextView) findViewById(R.id.tv_artist);
        tv_category = (TextView) findViewById(R.id.tv_category);
        tv_medium = (TextView) findViewById(R.id.tv_medium);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_size = (TextView) findViewById(R.id.tv_size);
        tv_estimate = (TextView) findViewById(R.id.tv_estimate);
        tv_no_bids = (TextView) findViewById(R.id.tv_no_bids);



    }

}

