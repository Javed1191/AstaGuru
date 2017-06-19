package com.infomanav.astaguru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import adapter.Past_Auction_SubAdpter;
import adapter.Past_SubAuction_ListviewAdpter;
import interfaces.OnBidResult;
import model_classes.Past_sub_Model;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 17-10-2016.
 */

public class Past_Auction_SubActivity extends AppCompatActivity {

    ArrayList<Past_sub_Model> appsList;

    private String strPastAuctionUrl="";
    private Utility utility;
    private GridView gridview;
    private SwipeMenuListView mListView;
    Context context;
    TextView tv_currencypast,tv_rs_type,tv_auction_anyalsis,tv_no_data_found;
    Past_Auction_SubAdpter past_auction_subAdpter;
    Past_SubAuction_ListviewAdpter listViewAdapter;
    String auctiontype;
    private ImageView iv_grid, iv_list;
    private boolean list_visibile = false,is_us=false;
    LinearLayout lin_filter,lay_records_not_found;
    String Auction_id;
    LinearLayout iv_leftarrow;
    SessionData data;
    ImageView grid_image;
    private String name="",strParaAuction="",Auctionname="",auctionBanner="",key="",auction="",type="";
    private ArrayList<model_classes.Country> filterArrayList;
    private ProgressDialog pDialog;
    String f_doller,f_pro_id,f_lot,Thumbnail,Reference,OldPriceUs,OldPriceRs,Auctionid,UlastName,Current_time_date,
            value_for_cmpr,rs_value,usvalue,str_rs_amount,str_us_amount;
    private AlertDialog bid_now,bid_proxy,dilog_alert,dilog_bid_access;
    int height=0,width=0;
    DisplayMetrics displaymetrics;
    private boolean is_search=false;
    private MakeBid makeBid;
    private String strUserName="";
    private SessionData sessionData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_subscreen);
        context = Past_Auction_SubActivity.this;


        utility = new Utility(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = new SessionData(context);
        filterArrayList = new ArrayList<>();

        displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        makeBid = new MakeBid(Past_Auction_SubActivity.this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);
        tv_currencypast = (TextView) findViewById(R.id.tv_currencypast);
        tv_auction_anyalsis = (TextView) findViewById(R.id.tv_auction_anyalsis);
        tv_rs_type = (TextView) findViewById(R.id.tv_rs_type);
        lin_filter = (LinearLayout) findViewById(R.id.lin_filter);
        iv_leftarrow = (LinearLayout) findViewById(R.id.iv_leftarrow);
        lay_records_not_found = (LinearLayout) findViewById(R.id.lay_records_not_found);
        grid_image = (ImageView)findViewById(R.id.grid_image) ;
        tv_no_data_found = (TextView) findViewById(R.id.tv_no_data_found);
        sessionData = new SessionData(context);
        strUserName = sessionData.getString("name");

        final Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {

            if(intent.getStringExtra("type")!=null)
            {
                if(intent.getStringExtra("type").equalsIgnoreCase("search"))
                {
                    type = intent.getStringExtra("type");
                    key = intent.getStringExtra("key");
                    auction = intent.getStringExtra("auction");
                    auctiontype = intent.getStringExtra("auction");
                    Auction_id = intent.getStringExtra("str_id");
                    is_search = true;

                    int int_word_count = FragmentCurrentAuction.countWords(key);

                    if(auctiontype.equalsIgnoreCase("past"))
                    {
                        tv_auction_anyalsis.setVisibility(View.INVISIBLE);
                        lin_filter.setVisibility(View.INVISIBLE);
                        name = "Past Auctions";
                    }
                    else if(auctiontype.equalsIgnoreCase("upcomming"))
                    {
                        tv_auction_anyalsis.setVisibility(View.INVISIBLE);
                        lin_filter.setVisibility(View.INVISIBLE);
                        name = "Upcoming Auctions";
                    }

                    strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spSearch("+key+","+auction+","+int_word_count+")?api_key="+ Application_Constants.API_KEY;
                }
                else if(intent.getStringExtra("type").equalsIgnoreCase("artwork"))
                {
                    name = "Record Price Artworks";
                    tv_auction_anyalsis.setVisibility(View.INVISIBLE);
                    lin_filter.setVisibility(View.INVISIBLE);
                    strPastAuctionUrl = Application_Constants.Main_URL+"recordPriceArtworks?api_key="+ Application_Constants.API_KEY;
                    auctiontype = intent.getStringExtra("auction");
                }
                else
                {
                    name = intent.getStringExtra("str_auction");
                    Auction_id = intent.getStringExtra("str_id");
                    auctiontype = intent.getStringExtra("auction");

                    if(auctiontype.equalsIgnoreCase("past"))
                    {
                        strParaAuction = "P";
                        tv_auction_anyalsis.setVisibility(View.VISIBLE);
                        lin_filter.setVisibility(View.VISIBLE);
                    }
                    else if(auctiontype.equalsIgnoreCase("upcomming"))
                    {
                        strParaAuction = "U";
                        tv_auction_anyalsis.setVisibility(View.INVISIBLE);
                        lin_filter.setVisibility(View.VISIBLE);
                    }

                    strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spPastAuction("+Auction_id+","+strParaAuction+","+"%20" +")?api_key="+ Application_Constants.API_KEY;
                }
            }

            else
            {
                name = intent.getStringExtra("str_auction");
                Auction_id = intent.getStringExtra("str_id");
                auctiontype = intent.getStringExtra("auction");

                if(auctiontype.equalsIgnoreCase("past"))
                {
                    strParaAuction = "P";
                    tv_auction_anyalsis.setVisibility(View.VISIBLE);
                    lin_filter.setVisibility(View.VISIBLE);
                }
                else if(auctiontype.equalsIgnoreCase("upcomming"))
                {
                    strParaAuction = "U";
                    tv_auction_anyalsis.setVisibility(View.INVISIBLE);
                    lin_filter.setVisibility(View.VISIBLE);
                }

                strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spPastAuction("+Auction_id+","+strParaAuction+","+"%20" +")?api_key="+ Application_Constants.API_KEY;
            }

        }

        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText(Html.fromHtml(name));
        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);


        gridview = (GridView) findViewById(R.id.gridviewpastsub);

        mListView = (SwipeMenuListView) findViewById(R.id.listviewpastsub);
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

        tv_auction_anyalsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Past_Auction_SubActivity.this,ShowWebView.class);
                intent.putExtra("auction_id",Auction_id);
                startActivity(intent);
            }
        });

        lin_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Past_Auction_SubActivity.this,Filter_Activity.class);
                intent.putExtra("filter",auctiontype);
                intent.putExtra("Auction_id",Auction_id);
                intent.putExtra("Auctionname",Auctionname);
                intent.putExtra("filterlist",filterArrayList);
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
                    is_us = true;
                }
                else if (tv_rs_type.getText().toString().equals("USD"))
                {
                    tv_rs_type.setText("INR");
                    is_us = false;
                }

                try
                {
                    if(past_auction_subAdpter!=null)
                    {

                        if (tv_rs_type.getText().toString().equals("USD"))
                        {
                            past_auction_subAdpter.changeCurrency(true);
                        }
                        else if (tv_rs_type.getText().toString().equals("INR"))
                        {
                            past_auction_subAdpter.changeCurrency(false);
                        }
                    }

                    if(listViewAdapter!=null)
                    {


                        if (tv_rs_type.getText().toString().equals("USD"))
                        {
                            listViewAdapter.changeCurrency(true);
                        }
                        else if (tv_rs_type.getText().toString().equals("INR"))
                        {
                            listViewAdapter.changeCurrency(false);
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
                    is_us = true;
                }
                else if (tv_rs_type.getText().toString().equals("USD"))
                {
                    tv_rs_type.setText("INR");
                    is_us = false;
                }

                try
                {
                    if(past_auction_subAdpter!=null)
                    {

                        if (tv_rs_type.getText().toString().equals("USD"))
                        {
                            past_auction_subAdpter.changeCurrency(true);
                        }
                        else if (tv_rs_type.getText().toString().equals("INR"))
                        {
                            past_auction_subAdpter.changeCurrency(false);
                        }
                    }

                    if(listViewAdapter!=null)
                    {


                        if (tv_rs_type.getText().toString().equals("USD"))
                        {
                            listViewAdapter.changeCurrency(true);
                        }
                        else if (tv_rs_type.getText().toString().equals("INR"))
                        {
                            listViewAdapter.changeCurrency(false);
                        }

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


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu)
            {

                if(auctiontype.equalsIgnoreCase("past"))
                {
                    SwipeMenuItem lotdetail = new SwipeMenuItem(context);
                    lotdetail.setBackground(new ColorDrawable(Color.rgb(0, 0,0)));
                    lotdetail.setWidth(dp2px(60));
                    lotdetail.setTitle("\nLot\nDetail");
                    lotdetail.setIcon(R.drawable.icon_detail);
                    lotdetail.setTitleSize(11);
                    lotdetail.setTitleColor(Color.WHITE);
                    menu.addMenuItem(lotdetail);
                }
                else {

                    SwipeMenuItem menuProxyBid = new SwipeMenuItem(context);
                    menuProxyBid.setBackground(new ColorDrawable(Color.rgb(0, 0,0)));
                    menuProxyBid.setWidth(dp2px(60));
                    menuProxyBid.setTitle("\nProxy\nBid");
                    menuProxyBid.setIcon(R.drawable.icon_proxybid);
                    menuProxyBid.setTitleSize(11);
                    menuProxyBid.setTitleColor(Color.WHITE);
                    menu.addMenuItem(menuProxyBid);

                    SwipeMenuItem lotdetail = new SwipeMenuItem(context);
                    lotdetail.setBackground(new ColorDrawable(Color.rgb(0, 0,0)));
                    lotdetail.setWidth(dp2px(60));
                    lotdetail.setTitle("\nLot\nDetail");
                    lotdetail.setIcon(R.drawable.icon_detail);
                    lotdetail.setTitleSize(11);
                    lotdetail.setTitleColor(Color.WHITE);
                    menu.addMenuItem(lotdetail);



                }

            }
        };

        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index)
            {

                String strMenu = menu.getMenuItem(index).getTitle();

                System.out.print(strMenu);
                final Past_sub_Model apps = appsList.get(position);

                if(auctiontype.equalsIgnoreCase("past"))
                {
                    switch (index)
                    {
                        case 0:

                            Intent intent = new Intent(Past_Auction_SubActivity.this,Lot_Detail_Page.class);
                            intent.putExtra("Str_id",apps.getStr_productid());
                            intent.putExtra("reference", apps.getReference());
                            intent.putExtra("fragment",auctiontype);
                            intent.putExtra("Auction_id",apps.getAuction_id());
                            intent.putExtra("Auctionname",apps.getAuctionname());

                            intent.putExtra("medium",apps.getStr_medium());
                            intent.putExtra("FirstName",apps.getStr_FirstName());
                            intent.putExtra("LastName",apps.getStr_LastName());
                            intent.putExtra("Profile",apps.getStr_Profile());
                            intent.putExtra("is_us",is_us);
                            intent.putExtra("dollar_rate",apps.getDollarRate());
                            startActivity(intent);
                            break;

                    }
                }
                else
                {
                    switch (index)
                    {

                        case 0:

                            String status = data.getObjectAsString("login");
                            if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                            {
                                Intent loginIntent = new Intent(Past_Auction_SubActivity.this,Before_Login_Activity.class);
                                startActivity(loginIntent);

                            }
                            else
                            {

                                String MobileVerified = data.getObjectAsString("MobileVerified");
                                String EmailVerified = data.getObjectAsString("EmailVerified");
                                String confirmbid = data.getObjectAsString("confirmbid");
                                if (MobileVerified.equalsIgnoreCase("true") && EmailVerified.equalsIgnoreCase("true"))
                                {
                                    if (confirmbid.equals("1"))
                                    {
                                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Past_Auction_SubActivity.this);
                                        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View dialogView = inflater.inflate(R.layout.dailog_proxybid, null);
                                        dialogBuilder.setView(dialogView);

                                        final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                                        final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                                        final EditText edt_proxy = (EditText) dialogView.findViewById(R.id.edt_proxy);
                                        final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                                        final TextView tv_proxylot = (TextView) dialogView.findViewById(R.id.tv_proxylot);
                                        final TextView tv_bit_text = (TextView) dialogView.findViewById(R.id.username);

                                        tv_bit_text.setText("Start Price");

                                        final TextView iv_iconproxy = (TextView) dialogView.findViewById(R.id.iv_iconproxy);
                                        String img_name = apps.getStr_thumbnail();
                                        Thumbnail = img_name.replace("paintings/", "");
                                        Reference = apps.getReference();
                                        OldPriceRs = apps.getPriceus();
                                        OldPriceUs = apps.getPricers();
                                        Auctionid = apps.getReference();
                                        UlastName = apps.getStr_FirstName();

                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
                                        String datetime = dateformat.format(c.getTime());
                                        System.out.println("datetime" + datetime);

                                        Current_time_date = datetime;


                                        if (is_us) {
                                            int int_proxy_bid_us = 0;


                                            int myNum = Integer.parseInt(apps.getPricers());
                                            if (myNum < 10000000) {
                                                int_proxy_bid_us = Get_10_value(apps.getPriceus());
                                            } else {
                                                int_proxy_bid_us = Get_5_value(apps.getPriceus());
                                            }
                                            value_for_cmpr = String.valueOf(int_proxy_bid_us);
                                            String str_int_xus = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(apps.getPriceus()));

                                            tv_bidvalue.setText(str_int_xus);
                                            tv_proxylot.setText("Lot " + apps.getReference().trim());
                                            iv_iconproxy.setText("US$ ");
                                        } else {

                                            int int_proxy_bid_rs = 0;

                                            int myNum = Integer.parseInt(apps.getPricers());
                                            if (myNum < 10000000) {
                                                int_proxy_bid_rs = Get_10_value(apps.getPricers());
                                            } else {
                                                int_proxy_bid_rs = Get_5_value(apps.getPricers());
                                            }

                                            value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                                            rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);

                                            String str_rs = apps.getPricers();
                                            Double int_strrs = Double.parseDouble(str_rs);
                                            String str_rstest = NumberFormat.getNumberInstance(Locale.US).format(int_strrs);
                                            String str_amt_rss = rupeeFormat(str_rstest);
                                            str_rs_amount = String.valueOf(int_proxy_bid_rs);

                                            tv_proxylot.setText("Lot " + apps.getReference().trim());
                                            tv_bidvalue.setText(str_amt_rss);
                                            iv_iconproxy.setText("₹ ");
                                        }


                                        tv_confim.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v)
                                            {


                                                String dollerRate = apps.getDollarRate();
                                                f_lot = apps.getReference();
                                                String siteUserID = data.getObjectAsString("userid");
                                                String productID = apps.getStr_productid();
                                                String str_ProxyAmt = edt_proxy.getText().toString();


                                                if(!str_ProxyAmt.isEmpty())
                                                {


                                                    double fb = Double.parseDouble(dollerRate);
                                                    double rl = Double.parseDouble(str_ProxyAmt);


                                                    double str_ProxyAmtus_new = rl / fb;

                                                    String proxy_amt_us = Double.toString(str_ProxyAmtus_new);
///


                                                    if (str_ProxyAmt.isEmpty())
                                                    {
                                                        Toast.makeText(getApplicationContext(), "please Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {

                                                        String entered_value = edt_proxy.getText().toString();
                                                        String bid_value = value_for_cmpr;
                                                        int int_entered_value = Integer.parseInt(entered_value);
                                                        int int_bid_value = Integer.parseInt(bid_value);

                                                        if (int_entered_value >= int_bid_value)
                                                        {
                                                            if (is_us) {

                                                                String str_Proxy_for_us = edt_proxy.getText().toString();
                                                               // String str_us = Get_US_value(dollerRate, str_Proxy_for_us);
                                                                String str_us = MakeBid.Get_US_value(dollerRate, str_Proxy_for_us);

                                                                String Createdby = strUserName;
                                                                String Auction_id = apps.getAuction_id();
                                                                double fb1 = Double.parseDouble(dollerRate);
                                                                double rl1 = Double.parseDouble(str_Proxy_for_us);

                                                                double str_ProxyAmtrs = rl1 * fb1;

                                                                //String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                                                int int_proxy_new = (int) Math.round(str_ProxyAmtrs);
                                                                String proxy_amt_for_rs = String.valueOf(int_proxy_new);
                                                                //  Toast.makeText(mContext, "from US", Toast.LENGTH_SHORT).show();


                                                                if (utility.checkInternet()) {

                                                                    //ProxyBid(siteUserID, productID, proxy_amt_for_rs, proxy_amt_for_rs, "0", "", Createdby, Auction_id)

                                                                    makeBid.proxyBidForUpcoming(siteUserID, productID, proxy_amt_for_rs, str_Proxy_for_us, Createdby, Auction_id, f_lot,Auctionname);
                                                                    makeBid.bidResult(new OnBidResult() {
                                                                        @Override
                                                                        public void bidResult(String currentStatus, String msg) {
                                                                            bid_proxy.dismiss();
                                                                        }
                                                                    });

                                                                } else {
                                                                    show_dailog("Please Check Internet Connection");
                                                                    bid_proxy.dismiss();

                                                                }


                                                            } else {
                                                                //  Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                                                String str_Proxy = edt_proxy.getText().toString();
                                                                String Rate = apps.getDollarRate();
                                                               // String str_us = Get_US_value(Rate, str_Proxy);
                                                                String str_us = MakeBid.Get_US_value(Rate, str_Proxy);


                                                                String Createdby = strUserName;
                                                                String Auction_id = apps.getAuction_id();

                                                                if (utility.checkInternet()) {

                                                                    ///ProxyBid(siteUserID, productID, str_Proxy, str_us, "0", "", Createdby, Auction_id);
                                                                    makeBid.proxyBidForUpcoming(siteUserID, productID, str_Proxy, str_us, Createdby, Auction_id, f_lot,Auctionname);
                                                                    makeBid.bidResult(new OnBidResult() {
                                                                        @Override
                                                                        public void bidResult(String currentStatus, String msg) {
                                                                            bid_proxy.dismiss();
                                                                        }
                                                                    });
                                                                } else {

                                                                    show_dailog("Please Check Internet Connection");
                                                                    bid_proxy.dismiss();
                                                                }


                                                            }

                                                        } else {

                                                            // Toast.makeText(mContext, "Proxy Must Greater Than Current Bid Value", Toast.LENGTH_SHORT).show();

                                                            double myNum = Double.parseDouble(entered_value);
                                                            if (myNum < 10000000) {
                                                                Toast.makeText(context, "Proxy Bid value must be greater by at least 10% of current price.", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else if(myNum < 10000000)
                                                            {
                                                                Toast.makeText(context, "Proxy Bid value must be greater by at least 5% of current price", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }



                                                    }
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(), "Please Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        });
                                        bid_proxy = dialogBuilder.create();
                                        bid_proxy.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                bid_proxy.dismiss();
                                            }
                                        });


                                        bid_proxy.show();
                                        // bid_now.getWindow().setLayout(700, LinearLayout.LayoutParams.MATCH_PARENT);
                                        Window window = bid_proxy.getWindow();
                                        window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        window.setGravity(Gravity.CENTER);
                                        bid_proxy.setCanceledOnTouchOutside(false);
                                    }
                                    else
                                    {
                                        //Toast.makeText(getApplicationContext(), "You do not have bidding access", Toast.LENGTH_SHORT).show();
                                       // show_dailog("You don't have access to Bid Please contact Astagru.");
                                        bidAccessDialog("Astaguru","You don't have access to Bid Please contact Astagru.");

                                    }
                                }
                                else
                                {
                                    Intent VerificationIntent = new Intent(Past_Auction_SubActivity.this,Verification_Activity.class);
                                    VerificationIntent.putExtra("Activity","Login");
                                    startActivity(VerificationIntent);

                                }
                            }



                            break;


                        case 1:

                            Intent intent = new Intent(Past_Auction_SubActivity.this,Lot_Detail_Page.class);
                            intent.putExtra("Str_id",apps.getStr_productid());
                            intent.putExtra("reference", apps.getReference());
                            intent.putExtra("fragment",auctiontype);
                            intent.putExtra("Auction_id",apps.getAuction_id());
                            intent.putExtra("Auctionname",apps.getAuctionname());

                            intent.putExtra("medium",apps.getStr_medium());
                            intent.putExtra("FirstName",apps.getStr_FirstName());
                            intent.putExtra("LastName",apps.getStr_LastName());
                            intent.putExtra("Profile",apps.getStr_Profile());
                            intent.putExtra("is_us",is_us);
                            intent.putExtra("dollar_rate",apps.getDollarRate());
                            startActivity(intent);
                            break;

                    }
                }


                // ApplicationInfo item = mAppList.get(position);

                return false;
            }
        });

        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        mListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {

            @Override
            public void onMenuOpen(int position)
            {
                //  Toast.makeText(getActivity(),"Oepn",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMenuClose(int position)
            {
                // Toast.makeText(getActivity(),"Close",Toast.LENGTH_SHORT).show();
            }
        });


        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                //Toast.makeText(context, position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        if(intent.getExtras()!=null)
        {
            if (intent.getStringExtra("type") != null)
            {
                if(intent.getStringExtra("type").equalsIgnoreCase("artwork"))
                {
                    getArtworkAuctions(strPastAuctionUrl);
                }
                else
                {
                    getPastAuction(strPastAuctionUrl);
                }
            }
            else
            {
                getPastAuction(strPastAuctionUrl);
            }
        }
        else
        {
            getPastAuction(strPastAuctionUrl);
        }







    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request chk_fillter is same as what is passed  here it is 2
        try
        {

            if(requestCode==3)
            {
                if (data!=null)
                {
                    String artistid=data.getStringExtra("artistid");
                    filterArrayList = (ArrayList<model_classes.Country>) data.getSerializableExtra("filterlist");

//                    String strtext = artistid;

                  //  String url_string = artistid.substring(0,artistid.length()-2);

                    System.out.println("data" + artistid);
                    appsList.clear();
                    //GetFilterAuction(Application_Constants.Main_URL+"getPastAuction?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=(online=" + Auction_id + ")and" + url_string);

                    if(name.equalsIgnoreCase("Collectibles Auction"))
                    {
                        strParaAuction = "C";
                    }
                    GetFilterAuction(Application_Constants.Main_URL_Procedure+"spPastAuction("+Auction_id+","+strParaAuction+","+artistid+")?api_key="+ Application_Constants.API_KEY);

//                http://54.169.222.181/api/v2/guru/_table/getPastAuction?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=(Online=1)and((artistid=12)or(artistid=13))
                    //http://54.169.222.181/api/v2/guru/_table/getPastAuction?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=(online=1)and((artistid=171)or(artistid=302))
                }
                else {
                    getPastAuction(strPastAuctionUrl);
                }
               /* else
                {
                    FragmentCurrentAuction fragment1 = new FragmentCurrentAuction();

//                fragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.frame, fragment1).addToBackStack("HOME TAB");
                    fragmentTransaction1.commit();

                }*/
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    private void getPastAuction(String strPastAuctionUrl)
    {

        if(utility.checkInternet())
        {

           // final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(Past_Auction_SubActivity.this);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;

                    String pricelow,medium,Picture,DollarRate,str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,str_category,str_small_img,str_Bidpricers,str_Bidpriceus;

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
                                lay_records_not_found.setVisibility(View.GONE);
                                gridview.setVisibility(View.VISIBLE);
                                mListView.setVisibility(View.VISIBLE);

                                for(int i=0; i<jsonArray.length();i++)
                                {
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
                                    auctionBanner = Obj.getString("auctionBanner");
                                    String artist_name = str_FirstName+" "+ str_LastName;
                                    pricelow= Obj.getString("pricelow");
                                    medium= Obj.getString("medium");
                                    Picture = Obj.getString("Picture");


                                    str_category = Obj.getString("category");

                                    /*if(!auctiontype.equalsIgnoreCase("upcomming"))
                                    {
                                        pricers = claculatePercentage(pricers);
                                        priceus = claculatePercentage(priceus);
                                    }*/


                                    Past_sub_Model country = new Past_sub_Model(str_title, str_description, str_artistid, str_thumbnail, str_productsize, str_image, str_small_img, str_Profile, artist_name, str_category,str_productid,pricers,
                                            priceus,true,estamiate,productsize,productdate,reference,DollarRate,
                                            Auction_id,str_collectors,str_FirstName,str_LastName,pricelow,Auctionname,medium,Picture);
                                    appsList.add(country);

                                }
                                   setAdapters();

                                if(is_search)
                                {
                                    Picasso.with(context).load(auctionBanner.replace(" ","%20"))
                                            .into(grid_image);
                                }
                                else
                                {
                                    Picasso.with(context).load(Application_Constants.PAST_AUCTION_IMAGE_PATH +auctionBanner.replace(" ","%20"))
                                            .into(grid_image);
                                }

//                                past_auction_subAdpter = new Past_Auction_SubAdpter(context,appsList,false);
//
//                                gridview.setAdapter(past_auction_subAdpter);


                            }
                            else
                            {

                                lay_records_not_found.setVisibility(View.VISIBLE);
                                if(auctiontype.equalsIgnoreCase("upcomming"))
                                {
                                    tv_no_data_found.setText("There is no any upcoming auction still yet. ");

                                }
                                gridview.setVisibility(View.INVISIBLE);
                                mListView.setVisibility(View.INVISIBLE);

                                //Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
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


//
//    login changes
//            registration paramiter

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

                    String pricelow,DollarRate,str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,str_category,str_small_img,str_Bidpricers = null,str_Bidpriceus = null;

                    String str_collectors,str_FirstName,str_LastName,str_Profile,
                            str_medium,pricers,priceus,estamiate,productsize,productdate,Picture;
                    appsList = new ArrayList<>();
                    try {
                        if (str_json != null)
                        {
                            JSONArray jsonArray = new JSONArray(result);
                            if (jsonArray.length()>0)
                            {

                                lay_records_not_found.setVisibility(View.GONE);
                                gridview.setVisibility(View.VISIBLE);
                                mListView.setVisibility(View.VISIBLE);

                                for(int i=0; i<jsonArray.length();i++)
                                {
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
                                    Picture = Obj.getString("Picture");
                                    str_collectors = Obj.getString("collectors");
                                    pricelow= Obj.getString("pricelow");

                                    str_medium = Obj.getString("medium");

                                    String artist_name = str_FirstName+" "+ str_LastName;

                                    String newtext = reference.trim();

                                    str_category = Obj.getString("category");


                                   /* if(!auctiontype.equalsIgnoreCase("upcomming"))
                                    {
                                        str_Bidpricers = claculatePercentage(pricers);
                                        str_Bidpriceus = claculatePercentage(priceus);
                                    }*/

                                    Past_sub_Model country = new Past_sub_Model(str_title, str_description, str_artistid, str_thumbnail, str_productsize, str_image, str_small_img, str_Profile, artist_name, str_category,str_productid,pricers,priceus,true,estamiate,productsize,productdate,newtext,DollarRate,Auction_id,
                                            str_collectors,str_FirstName,str_LastName,pricelow,Auctionname,str_medium,Picture);
                                    appsList.add(country);

                                }
                                setAdapters();
//                                past_auction_subAdpter = new Past_Auction_SubAdpter(context,appsList,false);
//
//                                gridview.setAdapter(past_auction_subAdpter);


                            }
                            else
                            {

                                lay_records_not_found.setVisibility(View.VISIBLE);
                                gridview.setVisibility(View.INVISIBLE);
                                mListView.setVisibility(View.INVISIBLE);

                                //Toast.makeText(context, "Records Not Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Records Not Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(context, "Records Not Found", Toast.LENGTH_SHORT).show();
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

            if(past_auction_subAdpter!=null)
            {

                if (tv_rs_type.getText().toString().equals("USD"))
                {
                    past_auction_subAdpter.changeCurrency(true);
                }
                else if (tv_rs_type.getText().toString().equals("INR"))
                {
                    past_auction_subAdpter.changeCurrency(false);
                }
            }

        } else {


            gridview.setVisibility(View.GONE);
            iv_list.setBackgroundResource(R.drawable.list_dark);
            iv_grid.setBackgroundResource(R.drawable.grid_light);
            mListView.setVisibility(View.VISIBLE);

            list_visibile = true;
            setAdapters();

            if(listViewAdapter!=null)
            {


                if (tv_rs_type.getText().toString().equals("USD"))
                {
                    listViewAdapter.changeCurrency(true);
                }
                else if (tv_rs_type.getText().toString().equals("INR"))
                {
                    listViewAdapter.changeCurrency(false);
                }

            }
        }

    }
    private void setAdapters()
    {

        if (list_visibile)
        {
//            viewview.setVisibility(View.GONE);

            mListView.setVisibility(View.VISIBLE);
            gridview.setVisibility(View.GONE);
            listViewAdapter = new Past_SubAuction_ListviewAdpter(context, appsList,is_us,auctiontype);
            mListView.setAdapter(listViewAdapter);
            gridview.setLayoutAnimation(getgridlayoutAnim());
        }
        else
        {


            past_auction_subAdpter = new Past_Auction_SubAdpter(context, appsList,is_us,auctiontype);
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

    private String claculatePercentage(String strPrise)
    {
        String strBidPrise="";

        try
        {

            Integer int_bid_prise = 0,int_discount=0;

            int_bid_prise = Integer.parseInt(strPrise);
            int_discount = ((Integer.parseInt(strPrise)*15)/100);
            int_bid_prise = int_bid_prise+int_discount;

            strBidPrise = String.valueOf(int_bid_prise);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return strBidPrise;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    private void ProxyBid(String str_Userid,String str_Productid,String str_ProxyAmt,String str_ProxyAmtus,
                          String str_Status,String str_Auctionid,String Createdby,String Auction_id) {

        if (utility.checkInternet()) {
            pDialog  = new ProgressDialog(Past_Auction_SubActivity.this);
            pDialog.setMessage("Proxy Bidding ... ");
            pDialog.setCancelable(false);
            pDialog.show();
            String strPastAuctionUrl = Application_Constants.Main_URL+"ProxyAuctionDetails?api_key="+ Application_Constants.API_KEY;
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();
            params.put("Userid",str_Userid);
            params.put("Productid",str_Productid);
            params.put("ProxyAmt",str_ProxyAmt);
            params.put("ProxyAmtus",str_ProxyAmtus);
            params.put("Status",str_Status);
            params.put("Auctionid",Auction_id);
            params.put("Createdby",UlastName);
            params.put("CreatedDt",Current_time_date);



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

                                Toast.makeText(Past_Auction_SubActivity.this, "You Successfully Done Proxy Bid", Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(Past_Auction_SubActivity.this,error.getMessage(),Toast.LENGTH_SHORT);

                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(Past_Auction_SubActivity.this);
            requestQueue.add(jsonObjectRequest);
        }

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
    public static String rupeeFormat(String value)
    {
        value=value.replace(",","");
        char lastDigit=value.charAt(value.length()-1);
        String result = "";
        int len = value.length()-1;
        int nDigits = 0;
        for (int i = len - 1; i >= 0; i--)
        {
            result = value.charAt(i) + result;
            nDigits++;
            if (((nDigits % 2) == 0) && (i > 0))
            {
                result = "," + result;
            }
        }
        return (result+lastDigit);
    }
    private int Get_10_value(String amt)
    {

        double amount = Double.parseDouble(amt);

        double byerprimium = (amount / 100.0f) * 10;

        double sum = amount + byerprimium;

        int intbyerprimium = (int) sum;

        return intbyerprimium;
    }

    private  void show_dailog(String msg)
    {

        String msgtest = msg;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Past_Auction_SubActivity.this);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public void bidAccessDialog(String strTitle, String strMessage)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Past_Auction_SubActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dailog_bid_access, null);
        dialogBuilder.setView(dialogView);


        final TextView tv_title = (TextView) dialogView.findViewById(R.id.tv_title);
        final TextView tv_message = (TextView) dialogView.findViewById(R.id.tv_message);
        final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);

        tv_title.setText(strTitle);
        tv_message.setText(strMessage);
        tv_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dilog_bid_access.dismiss();

            }
        });

        dilog_bid_access = dialogBuilder.create();
        dilog_bid_access.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




        dilog_bid_access.show();



        // dilog_bid_access.getWindow().setLayout(700, LinearLayout.LayoutParams.MATCH_PARENT);
        Window window = dilog_bid_access.getWindow();
        window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dilog_bid_access.setCanceledOnTouchOutside(false);
    }

    private void getArtworkAuctions(String strPastAuctionUrl)
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

                    String pricelow,DollarRate,str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,str_category,str_small_img,str_Bidpricers,str_Bidpriceus;

                    String str_collectors,str_FirstName,str_LastName,str_Profile,pricers,priceus,
                            estamiate,productsize,productdate,medium,Picture,Online,strAuctionname;
                    appsList = new ArrayList<>();
                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);

                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");

                                for (int i = 0; i < jsonArray.length(); i++)
                                {

                                    JSONObject Obj = jsonArray.getJSONObject(i);
                                    strAuctionname = Obj.getString("Auctionname");
                                    str_productid = Obj.getString("productid");
                                    str_title = Obj.getString("title");
                                    Online = Obj.getString("Online");
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
                                    auctionBanner = Obj.getString("auctionBanner");
                                    Picture = Obj.getString("Picture");
                                    String artist_name = str_FirstName+" "+ str_LastName;


                                    pricelow= Obj.getString("pricelow");
                                    str_category = Obj.getString("category");
                                    medium= Obj.getString("medium");

                                   /* pricers = claculatePercentage(pricers);
                                    priceus = claculatePercentage(priceus);*/


                                    Past_sub_Model country = new Past_sub_Model(str_title, str_description, str_artistid,
                                            str_thumbnail, str_productsize, str_image, str_small_img, str_Profile, artist_name,
                                            str_category,str_productid,pricers,priceus,true,estamiate,productsize,
                                            productdate,reference,DollarRate,Auction_id,str_collectors,str_FirstName,
                                            str_LastName,pricelow,strAuctionname,medium,Picture);
                                    appsList.add(country);

                                }
                                setAdapters();

                                Picasso.with(context).load(auctionBanner)
                                        .into(grid_image);
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

}

