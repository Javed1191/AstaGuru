package com.infomanav.astaguru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by fox-2 on 12/1/2016.
 */

public class Search_Lot_Details extends AppCompatActivity {


    private Utility utility;
    Context mContext;
    Button btn_proxy_bid_lot, btn_bid_now_lot;
    SessionData data;


    String image, str_msg;
    String product_id, strPastAuctionUrl;

    ImageView lot_image, iv_leftarrow, iv_zoom, iv_one, iv_two;
    TextView tv_title, tv_title_sub, tv_current_lot, tv_next_lot, tv_artist, tv_category, tv_medium, tv_year, tv_size, tv_estimate, tv_additonal_info;
    TextView tv_addinfo_one, tv_addinfo_two, tv_addinfo_three, tv_abt_artist, tv_read_more, tv_type, tv_change_currency;
    EditText edt_proxy;
    AlertDialog bid_now, bid_proxy;

    String str_productid, str_title, str_description, str_artistid, str_collectors, str_thumbnail, str_image, str_productsize, str_Bidpricers, str_Bidpriceus;

    String str_status, str_FirstName, str_LastName, str_Profile, start;

    String str_category, str_medium, str_date, str_pricers, str_priceus;
    public boolean is_us = false;
    ProgressDialog pDialog;
    String Userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_lot_details);
        mContext = Search_Lot_Details.this;
        data = new SessionData(mContext);
        Userid = data.getObjectAsString("userid");

        btn_bid_now_lot = (Button) findViewById(R.id.btn_bid_now_lot);

        btn_proxy_bid_lot = (Button) findViewById(R.id.btn_proxy_bid_lot);


        Intent intent = getIntent();
        product_id = intent.getStringExtra("Str_id");
        str_status = intent.getStringExtra("str_status");


        System.out.println("fromlot" + str_status);

        if (str_status.equalsIgnoreCase("Past"))
        {
            btn_bid_now_lot.setVisibility(View.GONE);
            btn_proxy_bid_lot.setVisibility(View.GONE);

        }
        else if(str_status.equalsIgnoreCase("Upcomming"))
        {
            btn_bid_now_lot.setVisibility(View.GONE);
            btn_proxy_bid_lot.setVisibility(View.VISIBLE);
        }
        else if(str_status.equalsIgnoreCase("Current"))
        {
            btn_bid_now_lot.setVisibility(View.VISIBLE);
            btn_proxy_bid_lot.setVisibility(View.VISIBLE);
        }



        strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/acution/" + product_id + "?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*";


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
        tool_text.setText("Lot 1");


        iv_two = (ImageView) findViewById(R.id.iv_two);
        iv_one = (ImageView) findViewById(R.id.iv_one);
        lot_image = (ImageView) findViewById(R.id.lot_image);
        iv_leftarrow = (ImageView) findViewById(R.id.iv_leftarrow);
        iv_zoom = (ImageView) findViewById(R.id.iv_zoom);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title_sub = (TextView) findViewById(R.id.tv_title_sub);
        tv_current_lot = (TextView) findViewById(R.id.tv_current_lot);
        tv_next_lot = (TextView) findViewById(R.id.tv_next_lot);
        tv_artist = (TextView) findViewById(R.id.tv_artist);
        tv_category = (TextView) findViewById(R.id.tv_category);
        tv_medium = (TextView) findViewById(R.id.tv_medium);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_size = (TextView) findViewById(R.id.tv_size);
        tv_estimate = (TextView) findViewById(R.id.tv_estimate);
        tv_additonal_info = (TextView) findViewById(R.id.tv_additonal_info);
        tv_addinfo_one = (TextView) findViewById(R.id.tv_addinfo_one);
        tv_change_currency = (TextView) findViewById(R.id.tv_change_currency);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_abt_artist = (TextView) findViewById(R.id.tv_abt_artist);
        tv_read_more = (TextView) findViewById(R.id.tv_read_more);
        Typeface type = Typeface.createFromAsset(getAssets(), "WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        iv_one.setImageResource(R.drawable.rupee);
        iv_two.setImageResource(R.drawable.rupee);



        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        iv_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", str_thumbnail);
                mContext.startActivity(intent);
            }
        });

        tv_additonal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search_Lot_Details.this, AdditionalCharges_Activity.class);

                intent.putExtra("str_FirstName", str_FirstName);
                intent.putExtra("str_LastName", str_LastName);
                intent.putExtra("str_category", str_category);
                intent.putExtra("str_medium", str_medium);
                intent.putExtra("str_date", str_date);
                intent.putExtra("str_productsize", str_productsize);
                intent.putExtra("str_image", str_image);
                intent.putExtra("str_pricers", str_pricers);

//                intent.putExtra("str_lot",);

                startActivity(intent);
            }
        });

        tv_change_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_type.getText().toString().equals("INR")) {
                    tv_type.setText("USD");

                    is_us = true;

                    int int_str = Integer.parseInt(str_priceus);
                    String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

                    tv_current_lot.setText(str_ustest);
                    double amount1 = Double.parseDouble(str_priceus);

                    double byerprimium1 = (amount1 / 100.0f) * 10;

                    double sum1 = amount1 + byerprimium1;

                    int intbyerprimium1 = (int) sum1;
//            String valuebyerprimium1 = String.valueOf(intbyerprimium1);

//            int int_str = Integer.parseInt(str_us);
                    String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium1);

                    tv_next_lot.setText(valuebyerprimium1);
                    iv_one.setImageResource(R.drawable.doller);
                    iv_two.setImageResource(R.drawable.doller);
                } else if (tv_type.getText().toString().equals("USD")) {
                    tv_type.setText("INR");
                    is_us = false;

                    int int_str = Integer.parseInt(str_pricers);
                    String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

                    tv_current_lot.setText(str_ustest);
                    double amount1 = Double.parseDouble(str_pricers);

                    double byerprimium1 = (amount1 / 100.0f) * 10;

                    double sum1 = amount1 + byerprimium1;

                    int intbyerprimium1 = (int) sum1;
//            String valuebyerprimium1 = String.valueOf(intbyerprimium1);

//            int int_str = Integer.parseInt(str_us);
                    String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium1);

                    tv_next_lot.setText(valuebyerprimium1);
                    iv_one.setImageResource(R.drawable.rupee);
                    iv_two.setImageResource(R.drawable.rupee);


                }


            }
        });


        iv_leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        btn_bid_now_lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
                dialogBuilder.setView(dialogView);

                final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                final ImageView iv_icon = (ImageView) dialogView.findViewById(R.id.iv_icon);

                if (is_us) {
                    double amount = Double.parseDouble(str_priceus);

                    double byerprimium = (amount / 100.0f) * 10;

                    double sum = amount + byerprimium;

                    int intbyerprimium = (int) sum;

//                    usvalue= NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium);
                    String testv = String.valueOf(intbyerprimium);
                    int us_value_int = Integer.parseInt(testv);

                    String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(us_value_int);
                    tv_bidvalue.setText(str_int_us);
                    iv_icon.setImageResource(R.drawable.doller);
                } else {
                    double amountrs = Double.parseDouble(str_pricers);

                    double byerprimiumrs = (amountrs / 100.0f) * 10;

                    double sumrs = amountrs + byerprimiumrs;

                    int intbyerprimium = (int) sumrs;
                    String rs_value= NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium);
                    tv_bidvalue.setText(rs_value);
                    iv_icon.setImageResource(R.drawable.rupee);
                }


//
//                final ImageView iv_sinin_close = (ImageView) dialogView.findViewById(R.id.iv_sinin_close);

                tv_confim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String rs_amount = str_pricers;
                        String us_amount = str_priceus;
                        String productid = str_productid;
                        String userid = data.getObjectAsString("userid");
                        String dollerrate = "68";


                        int fb = Integer.parseInt(dollerrate);
                        int rl = Integer.parseInt(rs_amount);

                        int str_Proxy_new = rl / fb;

                        String proxy_new_us = Integer.toString(str_Proxy_new);
                        String status = data.getObjectAsString("login");
                        if (status.equalsIgnoreCase("false"))
                        {
                            Intent intent = new Intent(mContext,Before_Login_Activity.class);
                            mContext.startActivity(intent);

                        }
                        else {
                            if (is_us) {
                                BidNow(us_amount, productid, userid, dollerrate, proxy_new_us);
                            } else {
                                BidNow(rs_amount, productid, userid, dollerrate, proxy_new_us);
                            }

                        }


                    }
                });
                bid_now = dialogBuilder.create();
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bid_now.dismiss();
                    }
                });


                bid_now.show();
                bid_now.setCanceledOnTouchOutside(false);

            }
        });

        btn_proxy_bid_lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dailog_proxybid, null);
                dialogBuilder.setView(dialogView);

                final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                edt_proxy = (EditText) dialogView.findViewById(R.id.edt_proxy);
                final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                final ImageView iv_iconproxy = (ImageView) dialogView.findViewById(R.id.iv_iconproxy);


                if (is_us) {
                    double amount = Double.parseDouble(str_priceus);

                    double byerprimium = (amount / 100.0f) * 10;

                    double sum = amount + byerprimium;

                    int intbyerprimium = (int) sum;

//                    usvalue= NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium);
                    String testv = String.valueOf(intbyerprimium);
                    int us_value_int = Integer.parseInt(testv);

                    String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(us_value_int);
                    tv_bidvalue.setText(str_int_us);
                    iv_iconproxy.setImageResource(R.drawable.doller);
                } else {
                    double amountrs = Double.parseDouble(str_pricers);

                    double byerprimiumrs = (amountrs / 100.0f) * 10;

                    double sumrs = amountrs + byerprimiumrs;

                    int intbyerprimium = (int) sumrs;
                    String rs_value= NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium);
                    tv_bidvalue.setText(rs_value);
                    iv_iconproxy.setImageResource(R.drawable.rupee);
                }





                tv_confim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strdoller = "68";
                        double doller = Double.parseDouble(strdoller);


                        String str_Userid = data.getObjectAsString("userid");
                        String str_Productid = product_id;
                        String str_ProxyAmt = edt_proxy.getText().toString();
                        String str_ProxyAmtus = Double.toString(Double.parseDouble(str_ProxyAmt) / doller);

                        int fb = Integer.parseInt(strdoller);
                        int rl = Integer.parseInt(str_ProxyAmt);

                        int str_ProxyAmtus_new = rl / fb;

                        String proxy_amt_us = Integer.toString(str_ProxyAmtus_new);




                        if (str_ProxyAmt.isEmpty())
                        {
                            Toast.makeText(mContext, "Pls Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            String status = data.getObjectAsString("login");
                            if (status.equalsIgnoreCase("false"))
                            {
                                Intent intent = new Intent(mContext,Before_Login_Activity.class);
                                mContext.startActivity(intent);

                            }
                            else
                            {



                                if (is_us)
                                {

                                    String str_Proxy_for_us = edt_proxy.getText().toString();

                                    int fb1 = Integer.parseInt(strdoller);
                                    int rl1 = Integer.parseInt(str_Proxy_for_us);

                                    int str_ProxyAmtrs = rl1 * fb1;

                                    String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                    Toast.makeText(mContext, "from US", Toast.LENGTH_SHORT).show();

                                    ProxyBid(str_ProxyAmt,product_id,Userid,strdoller,proxy_amt_for_rs);
                                }
                                else                                 {
                                    Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                    ProxyBid(str_ProxyAmt,product_id,Userid,strdoller,proxy_amt_us);
                                }


                            }



                        }

                    }
                });
                bid_proxy = dialogBuilder.create();
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bid_proxy.dismiss();
                    }
                });


                bid_proxy.show();
                bid_proxy.setCanceledOnTouchOutside(false);


            }
        });


    }

    private void getUpcomingAuction() {

        if (utility.checkInternet()) {
            //final ArrayList<Department> feedsArrayList = new ArrayList<Department>();

            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(this);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;


                    try {
                        if (str_json != null) {
                            JSONObject jobject = new JSONObject(str_json);
                            JSONObject object = jobject.getJSONObject("artist_by_artistid");
                            JSONObject obj_category_by_categoryid = jobject.getJSONObject("category_by_categoryid");
                            JSONObject obj_medium_by_mediumid = jobject.getJSONObject("medium_by_mediumid");
//                            JSONArray obj_mygallery_by_productid = jobject.getJSONArray("mygallery_by_productid");

                            str_medium = obj_medium_by_mediumid.getString("medium");

                            str_FirstName = object.getString("FirstName");
                            str_LastName = object.getString("LastName");
                            str_Profile = object.getString("Profile");


                            str_productid = jobject.getString("productid");
                            str_title = jobject.getString("title");
                            str_description = jobject.getString("description");
                            str_artistid = jobject.getString("artistid");
                            str_collectors = jobject.getString("collectors");
                            str_thumbnail = jobject.getString("thumbnail");
                            str_image = jobject.getString("image");
                            str_productsize = jobject.getString("productsize");
                            str_pricers = jobject.getString("pricers");
                            str_priceus = jobject.getString("priceus");


                            str_category = obj_category_by_categoryid.getString("category");




                            tv_title.setText(str_FirstName + str_LastName);
                            tv_title_sub.setText(str_title);
//                            tv_current_bid.setText(str_Bidpricers);
//                            tv_next_bid.setText(str_Bidpriceus);

                            tv_artist.setText(str_title);
                            tv_category.setText(str_category);
                            tv_medium.setText(str_medium);
                            tv_year.setText(str_title);
                            tv_size.setText(str_productsize);
                            tv_estimate.setText(str_collectors);


                            tv_addinfo_one.setText(str_description);

                            Picasso.with(getApplicationContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + str_image)
                                    .into(lot_image);

                            String filename = str_Profile;
                            int iend = filename.indexOf(".");

                            start = filename.substring(0, 50);

                            tv_abt_artist.setText(start);


                            double amountrs = Double.parseDouble(str_pricers);

                            double byerprimiumrs = (amountrs / 100.0f) * 10;

                            double sumrs = amountrs + byerprimiumrs;

                            int intbyerprimiumrs = (int) amountrs;

                            String str_pricers_failbal = NumberFormat.getNumberInstance(Locale.US).format(intbyerprimiumrs);


                            tv_current_lot.setText(str_pricers_failbal);



                            double amount1 = Double.parseDouble(str_pricers);

                            double byerprimium1 = (amount1 / 100.0f) * 10;

                            double sum1 = amount1 + byerprimium1;

                            int intbyerprimium1 = (int) sum1;

                            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium1);

                            tv_next_lot.setText(valuebyerprimium1);


                            tv_read_more.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (tv_read_more.getText().equals("Read More")) {
                                        tv_abt_artist.setText(str_Profile);
                                        tv_read_more.setText("Read Less");
                                    } else if (tv_read_more.getText().equals("Read Less")) {
                                        tv_abt_artist.setText(start);
                                        tv_read_more.setText("Read More");
                                    }


                                }
                            });


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
//                Intent intent = new Intent(Lot_Detail_Page.this, MyAstaGuru_Activity.class);
//                startActivity(intent);

                return true;
            case R.id.action_search:
                // do whatever
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    private void BidNow(String str_Amount,String str_productID,String str_userID,String dollerrate,String proxy_new_us) {

        if (utility.checkInternet()) {

            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spBid("+str_Amount+","+str_productID+","+str_userID+","+dollerrate+","+proxy_new_us+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);
                    Toast.makeText(mContext, "You Succesfully Bid", Toast.LENGTH_SHORT).show();

                    bid_now.dismiss();
                    getUpcomingAuction();
                    String str_json = result;


                }
            });
        }

    }
    private void ProxyBid(String userProxyAmount,String productID,String siteUserID,String dollerRate,String dollerAmt) {

        if (utility.checkInternet()) {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);


                    bid_proxy.dismiss();
                    //notifyDataSetChanged();
//                    listener.foo();
                    Toast.makeText(mContext, "You Succesfully Done Proxy Bid", Toast.LENGTH_SHORT).show();
                    String str_json = result;

//                    try {
//                        if (str_json != null)
//                        {
//                            JSONObject jobject = new JSONObject(str_json);
//
////                            if (jobject.length() > 0)
////                            {
////                                JSONArray jsonArray = new JSONArray();
////                                jsonArray = jobject.getJSONArray("resource");
////
////                                Toast.makeText(mContext, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
////
////                                bid_now.dismiss();
//////                                for (int i = 0; i < jsonArray.length(); i++) {
//////                                    JSONObject Obj = jsonArray.getJSONObject(i);
//////
//////
////////                                    str_productid = Obj.getString("productid");
////////                                    str_title = Obj.getString("title");
////////                                    str_description = Obj.getString("description");
////////                                    str_artistid = Obj.getString("artistid");
//////
//////
//////                                }
////
////
////
////
////                            } else {
////                                Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
////                            }
//                        } else {
//                            Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
//                        }
//
//                    } catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
                }
            });
        }

    }




}

