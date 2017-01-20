package com.infomanav.astaguru;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.MyPurchasesAdpter;
import adapter.PastAuctionAdapter;
import adapter.PastAuctionListviewAdpter;
import adapter.Past_Auction_SubAdpter;
import adapter.Past_SubAuction_ListviewAdpter;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 17-10-2016.
 */

public class Past_Auction_SubActivity extends AppCompatActivity {

    ArrayList<Past_sub_Model> appsList;

    private String strPastAuctionUrl;
    private Utility utility;
    private GridView gridview;
    private ListView mListView;
    Context context;
    TextView tv_currencypast,tv_rs_type;
    Past_Auction_SubAdpter past_auction_subAdpter;
    Past_SubAuction_ListviewAdpter listViewAdapter;
    String auctiontype;
    private ImageView iv_grid, iv_list;
    private boolean list_visibile = false;
    LinearLayout lin_filter;
    String Auction_id;
    LinearLayout iv_leftarrow;
    SessionData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_subscreen);
        context = Past_Auction_SubActivity.this;


        utility = new Utility(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = new SessionData(context);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);
        tv_currencypast = (TextView) findViewById(R.id.tv_currencypast);
        tv_rs_type = (TextView) findViewById(R.id.tv_rs_type);
        lin_filter = (LinearLayout) findViewById(R.id.lin_filter);
        iv_leftarrow = (LinearLayout) findViewById(R.id.iv_leftarrow);

        Intent intent = getIntent();
        String name = intent.getStringExtra("str_auction");
        Auction_id = intent.getStringExtra("str_id");
        auctiontype = intent.getStringExtra("auction");

         strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spPastAuction("+Auction_id+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";


//        strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/acution?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%20"+str_id+"&related=*";

        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText(name);


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);



        gridview = (GridView) findViewById(R.id.gridviewpastsub);


        mListView = (ListView) findViewById(R.id.listviewpastsub);
        iv_grid = (ImageView) findViewById(R.id.iv_grid);
        iv_list = (ImageView) findViewById(R.id.iv_list);


        iv_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView();
            }
        });
        iv_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView();
            }
        });

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//
//                String Str_id =appsList.get(position).getStr_productid();
//                Intent intent = new Intent(context,Lot_Detail_Page.class);
//                intent.putExtra("Str_id",Str_id);
//                startActivity(intent);
//
//
//            }
//        });

        lin_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Past_Auction_SubActivity.this,Filter_Activity.class);
                intent.putExtra("filter","past_sub");
                startActivityForResult(intent,3);
            }
        });

        tv_currencypast.setOnClickListener(new View.OnClickListener() {
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
                    if(past_auction_subAdpter!=null)
                    {
                        past_auction_subAdpter.changeCurrency();
                    }

                    if(listViewAdapter!=null)
                    {
                        listViewAdapter.changeCurrency();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
        iv_leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getPastAuction();

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==3)
        {
            if (!(data==null))
            {
                String artistid=data.getStringExtra("artistid");

//                    String strtext = artistid;

                    String url_string = artistid.substring(0,artistid.length()-2);

                    System.out.println("data" + url_string);
                appsList.clear();
                    GetFilterAuction("http://54.169.222.181/api/v2/guru/_table/lotspopular?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter="+url_string+"");
            }
            else
            {
                FragmentCurrentAuction fragment1 = new FragmentCurrentAuction();

//                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.frame, fragment1).addToBackStack("HOME TAB");
                fragmentTransaction1.commit();

            }
        }
    }


    private void getPastAuction()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;

                    String DollarRate,str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,str_category,str_small_img,str_Bidpricers,str_Bidpriceus;

                    String str_collectors,str_FirstName,str_LastName,str_Profile,pricers,priceus,estamiate,productsize,productdate;
                    appsList = new ArrayList<>();
                    try {
                        if (str_json != null)
                        {
//                            JSONObject jobject = new JSONObject(str_json);
                            JSONArray jsonArray = new JSONArray(str_json);
//                            JSONArray jsonArray = new JSONArray();
//                            jsonArray = getJSONArray(str_json);
                          if (jsonArray.length()>0)
                            {


                                for(int i=0; i<jsonArray.length();i++)
                                {
                                    JSONObject Obj = jsonArray.getJSONObject(i);

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
                                    str_collectors = Obj.getString("collectors");
                                    productsize = Obj.getString("productsize");
                                    productdate = Obj.getString("productdate");
                                    reference = Obj.getString("reference");
                                    DollarRate = Obj.getString("DollarRate");
//                                    JSONObject object = Obj.getJSONObject("artist_by_artistid");
                                    str_FirstName = Obj.getString("FirstName");
                                    str_LastName = Obj.getString("LastName");
                                    str_Profile = Obj.getString("Profile");

                                    String artist_name = str_FirstName+" "+ str_LastName;



                                    str_category = Obj.getString("category");


                                    Past_sub_Model country = new Past_sub_Model(str_title, str_description, str_artistid, str_thumbnail, str_productsize, str_image, str_small_img, str_Profile, artist_name, str_category,str_productid,pricers,priceus,true,estamiate,productsize,productdate,reference,DollarRate,Auction_id,str_collectors);
                                    appsList.add(country);

                                }
                                   setAdapters();
//                                past_auction_subAdpter = new Past_Auction_SubAdpter(context,appsList,false);
//
//                                gridview.setAdapter(past_auction_subAdpter);


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
    private void GetFilterAuction(String URL)
    {

        if(utility.checkInternet())
        {

            String FilterUrl= URL;
            //final ArrayList<Department> feedsArrayList = new ArrayList<Department>();

            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, FilterUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;

                    String DollarRate,str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,str_category,str_small_img,str_Bidpricers,str_Bidpriceus;

                    String str_collectors,str_FirstName,str_LastName,str_Profile,pricers,priceus,estamiate,productsize,productdate;
                    appsList = new ArrayList<>();
                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            if (jobject.length()>0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");

                                for(int i=0; i<jsonArray.length();i++)
                                {
                                    JSONObject Obj = jsonArray.getJSONObject(i);

                                    str_productid = Obj.getString("productid");
                                    str_title = Obj.getString("title");
                                    str_description = Obj.getString("description");
                                    str_artistid = Obj.getString("artistid");
                                    str_thumbnail = Obj.getString("thumbnail");
                                    str_image = Obj.getString("image");
                                    str_productsize = Obj.getString("productsize");
                                    str_small_img = Obj.getString("smallimage");
                                    pricers = Obj.getString("pricers");
                                    priceus = Obj.getString("priceus");
                                    estamiate = Obj.getString("estamiate");
                                    productsize = Obj.getString("productsize");
                                    productdate = Obj.getString("productdate");
                                    reference = Obj.getString("reference");
                                    DollarRate = Obj.getString("DollarRate");
                                    str_FirstName = Obj.getString("FirstName");
                                    str_LastName = Obj.getString("LastName");
                                    str_Profile = Obj.getString("Profile");
                                    str_collectors = jobject.getString("collectors");

                                    String artist_name = str_FirstName+" "+ str_LastName;

                                    String newtext = reference.trim();

                                    str_category = Obj.getString("category");


                                    Past_sub_Model country = new Past_sub_Model(str_title, str_description, str_artistid, str_thumbnail, str_productsize, str_image, str_small_img, str_Profile, artist_name, str_category,str_productid,pricers,priceus,true,estamiate,productsize,productdate,newtext,DollarRate,Auction_id,str_collectors);
                                    appsList.add(country);

                                }
                                setAdapters();
//                                past_auction_subAdpter = new Past_Auction_SubAdpter(context,appsList,false);
//
//                                gridview.setAdapter(past_auction_subAdpter);


                            }
                            else
                            {
                                Toast.makeText(context, "Records Not Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Please Check Internet Connection.",Toast.LENGTH_LONG)
                                    .show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(context, "Please Check Internet Connection.",Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                String status = data.getObjectAsString("login");

                if (status.equalsIgnoreCase("true"))
                {
                    Intent intent = new Intent(Past_Auction_SubActivity.this,MyAstaGuru_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(Past_Auction_SubActivity.this,Before_Login_Activity.class);
                    startActivity(intent);
                }

                return true;
            case R.id.action_search:
                Intent intent = new Intent(Past_Auction_SubActivity.this,Search_Activity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void changeView() {

        //if the current view is the listview, passes to gridview
        if (list_visibile) {


            mListView.setVisibility(View.GONE);
            iv_grid.setBackgroundResource(R.drawable.grid_dark);
            iv_list.setBackgroundResource(R.drawable.list_light);
            gridview.setVisibility(View.VISIBLE);
            list_visibile = false;
            setAdapters();
        } else {


            gridview.setVisibility(View.GONE);
            iv_list.setBackgroundResource(R.drawable.list_dark);
            iv_grid.setBackgroundResource(R.drawable.grid_light);
            mListView.setVisibility(View.VISIBLE);

            list_visibile = true;
            setAdapters();
        }

    }
    private void setAdapters()
    {

        if (list_visibile)
        {
//            viewview.setVisibility(View.GONE);

            listViewAdapter = new Past_SubAuction_ListviewAdpter(context, appsList,false,auctiontype);
            mListView.setAdapter(listViewAdapter);
            gridview.setLayoutAnimation(getgridlayoutAnim());
        }
        else
        {


            past_auction_subAdpter = new Past_Auction_SubAdpter(context, appsList,false,auctiontype);
            gridview.setAdapter(past_auction_subAdpter);
            mListView.setLayoutAnimation(getgridlayoutAnim());


        }

    }



    public static LayoutAnimationController getgridlayoutAnim() {
        LayoutAnimationController controller;
        Animation anim = new Rotate3dAnimation(90f, 0f, 0.5f, 0.5f, 0.5f, false);
        anim.setDuration(500);
        controller = new LayoutAnimationController(anim, 0.1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;
    }
}

