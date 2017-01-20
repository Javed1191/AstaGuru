package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infomanav.astaguru.Artist_Details;
import com.infomanav.astaguru.AuctionGallary_Model;
import com.infomanav.astaguru.Before_Login_Activity;
import com.infomanav.astaguru.Lot_Detail_Page;
import com.infomanav.astaguru.MainActivity;
import com.infomanav.astaguru.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 03-10-2016.
 */

//Error:The number of method references in a .dex file cannot exceed 64K.
//        Learn how to resolve this issue at https://developer.android.com/tools/building/multidex.html

public class AuctionGallaryAdpter  extends ArrayAdapter<AuctionGallary_Model> {
    private MainActivity mainActivity;
    private LinearLayout lin_front, lin_back;
    private ImageView iv_oncce, expandedImageView, iv_zoom, iv_close,iv_addtogallary,iv_remove;
    boolean isBackVisible = false;
    public DisplayMetrics m;
    Context mContext;
    TextView tv_title;

    private Button btn_bidnow, btn_proxybid;
    public boolean is_us = false;
    List<AuctionGallary_Model> objects;
    ImageView iv_one, iv_two,iv_oneback,iv_twoback;

    DecimalFormat formatter;
    Utility utility;
    SessionData data;
    EditText edt_proxy;
    AlertDialog bid_now,bid_proxy,dilog_alert;
    String str_rs_amount,str_us_amount;
    String rupee_value;
    String trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot,tvalue;
    ProgressDialog pDialog;
    String rs_value,usvalue;

    String value_for_cmpr;
    String f_doller,f_pro_id,f_lot;
    public AuctionGallaryAdpter(Context context, int textViewResourceId, List<AuctionGallary_Model> objects,final boolean is_us) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.objects = objects;
        this.is_us = is_us;

        utility = new Utility(mContext);
        data = new SessionData(mContext);
    }

    public void changeCurrency() {
        if (is_us) {
            is_us = false;
        } else {
            is_us = true;
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View curView = convertView;

        if (curView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            curView = vi.inflate(R.layout.auction_gallary_grid, null);

        }

        final AuctionGallary_Model cp = getItem(position);

        formatter = new DecimalFormat("#,###,###");

        expandedImageView = (ImageView) curView.findViewById(R.id.grid_image);
        TextView tv_date = (TextView) curView.findViewById(R.id.tv_date);
        tv_title = (TextView) curView.findViewById(R.id.tv_title);
        TextView tv_lot = (TextView) curView.findViewById(R.id.tv_lot);
        TextView tv_lot_back = (TextView) curView.findViewById(R.id.tv_lot_back);
        TextView tv_subtitle = (TextView) curView.findViewById(R.id.tv_subtitle);
        TextView tv_current_bid = (TextView) curView.findViewById(R.id.tv_current_bid);
        TextView tv_nextbid = (TextView) curView.findViewById(R.id.tv_nextbid);
        iv_one = (ImageView) curView.findViewById(R.id.iv_one);
        iv_two = (ImageView) curView.findViewById(R.id.iv_two);
        iv_remove = (ImageView) curView.findViewById(R.id.iv_remove);

        TextView tv_ac_artist = (TextView) curView.findViewById(R.id.tv_ac_artist);
        TextView tv_ac_category = (TextView) curView.findViewById(R.id.tv_ac_category);
        TextView tv_ac_medium = (TextView) curView.findViewById(R.id.tv_ac_medium);
        TextView tv_ac_year = (TextView) curView.findViewById(R.id.tv_ac_year);
        TextView tv_ac_size = (TextView) curView.findViewById(R.id.tv_ac_size);
        TextView tv_estimate = (TextView) curView.findViewById(R.id.tv_estimate);
        TextView tv_ac_bid = (TextView) curView.findViewById(R.id.tv_ac_bid);
        TextView tv_ac_nextbid = (TextView) curView.findViewById(R.id.tv_ac_nextbid);


        tv_ac_artist.setText(cp.getArtist_name());
        tv_ac_category.setText(cp.getStr_category());
        tv_ac_medium.setText(cp.getMedium());
        tv_ac_year.setText("");
        tv_ac_size.setText(cp.getProductsize());
        tv_estimate.setText(cp.getEstamiate());

//        CardView card_main = (CardView) curView.findViewById(R.id.card_main);
        iv_oncce = (ImageView) curView.findViewById(R.id.iv_oncce);

        iv_oneback= (ImageView) curView.findViewById(R.id.iv_oneback);
        iv_twoback= (ImageView) curView.findViewById(R.id.iv_twoback);
        tv_lot.setText("Lot " + cp.getReference());
        tv_lot_back.setText("Lot " + cp.getReference());
        iv_close = (ImageView) curView.findViewById(R.id.iv_close);

        btn_bidnow = (Button) curView.findViewById(R.id.btn_bidnow);
        btn_proxybid = (Button) curView.findViewById(R.id.btn_proxybid);

        lin_front = (LinearLayout) curView.findViewById(R.id.lin_front);
        lin_back = (LinearLayout) curView.findViewById(R.id.lin_back);
        m = mContext.getResources().getDisplayMetrics();

//         rootLayout = curView.findViewById(R.id.layout_main);
//         cardFace = curView.findViewById(R.id.lin_front);
//         cardBack = curView.findViewById(R.id.rel_back);

//        card_main.requestLayout();
//        card_main.getLayoutParams().width = m.widthPixels /2;
//        card_main.getLayoutParams().height = m.widthPixels-200;

        // textView.setText(apps.getAppTitle());

        tv_title.setText(cp.getArtist_name());
        tv_subtitle.setText(cp.getStr_title());


        if (is_us) {

            String str_us = cp.getPriceus();
            int int_str = Integer.parseInt(str_us);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

            String yourFormattedString = formatter.format(100000);
            tv_current_bid.setText(str_ustest);
            tv_ac_bid.setText(str_ustest);
            double amount1 = Double.parseDouble(cp.getPriceus());

            double byerprimium1 = (amount1 / 100.0f) * 10;

            double sum1 = amount1 + byerprimium1;

            int intbyerprimium1 = (int) sum1;
//            String valuebyerprimium1 = String.valueOf(intbyerprimium1);

//            int int_str = Integer.parseInt(str_us);
            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium1);

            tv_nextbid.setText(valuebyerprimium1);
            tv_ac_nextbid.setText(valuebyerprimium1);
            iv_one.setImageResource(R.drawable.doller);
            iv_two.setImageResource(R.drawable.doller);
            iv_oneback.setImageResource(R.drawable.doller);
            iv_twoback.setImageResource(R.drawable.doller);
        } else {

            String str_rs= cp.getPricers();
            int int_strrs = Integer.parseInt(str_rs);
            String str_rscomma= NumberFormat.getNumberInstance(Locale.US).format(int_strrs);

            tv_current_bid.setText(str_rscomma);
            tv_ac_bid.setText(str_rscomma);
            double amount = Double.parseDouble(cp.getPricers());

            double byerprimium = (amount / 100.0f) * 10;

            double sum = amount + byerprimium;

            int intbyerprimium = (int) sum;
            String valuebyerprimium= NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium);

//            String valuebyerprimium = String.valueOf(intbyerprimium);
            tv_nextbid.setText(valuebyerprimium);
            tv_ac_nextbid.setText(valuebyerprimium);
            iv_one.setImageResource(R.drawable.rupee);
            iv_two.setImageResource(R.drawable.rupee);
            iv_oneback.setImageResource(R.drawable.rupee);
            iv_twoback.setImageResource(R.drawable.rupee);
        }

        if (cp.getIs_front()) {
            lin_front.setVisibility(View.VISIBLE);
            lin_back.setVisibility(View.GONE);
        } else {
            lin_front.setVisibility(View.GONE);
            lin_back.setVisibility(View.VISIBLE);
        }




        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_productid();
                Intent intent = new Intent(mContext, Lot_Detail_Page.class);
                intent.putExtra("Str_id", Str_id);
                intent.putExtra("fragment", "Current");
                intent.putExtra("reference", cp.getReference());
                mContext.startActivity(intent);

            }
        });


        iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bidartistuserid = cp.getBidartistuserid();

                Delete(bidartistuserid);


            }
        });

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_artistid();
                String Str_artistname = cp.getArtist_name();
                Intent intent = new Intent(mContext, Artist_Details.class);
                intent.putExtra("Str_id", Str_id);
                intent.putExtra("Str_artistname", Str_artistname);
                mContext.startActivity(intent);

            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*lin_front.setVisibility(View.VISIBLE);
                lin_back.setVisibility(View.GONE);*/

                cp.setIs_front(true);
                notifyDataSetChanged();

            }
        });

        iv_oncce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*lin_front.setVisibility(View.GONE);
                lin_back.setVisibility(View.VISIBLE);*/

                cp.setIs_front(false);
                notifyDataSetChanged();
            }
        });
        btn_bidnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
                dialogBuilder.setView(dialogView);

                final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                final ImageView iv_icon = (ImageView) dialogView.findViewById(R.id.iv_icon);

                if (is_us)
                {
                    int int_bid_us = Get_10_value(cp.getPriceus());

                    str_us_amount = String.valueOf(int_bid_us);
                    str_rs_amount = String.valueOf(int_bid_us);
                    String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_bid_us);

                    tv_bidvalue.setText(str_int_us);
                    iv_icon.setImageResource(R.drawable.doller);
                }
                else
                {
                    int int_bid_rs = Get_10_value(cp.getPricers());

                    str_rs_amount = String.valueOf(int_bid_rs);
                    str_us_amount = String.valueOf(int_bid_rs);
                    rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_bid_rs);

                    tv_bidvalue.setText(rs_value);
                    iv_icon.setImageResource(R.drawable.rupee);
                }


//

                tv_confim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String rs_amount = str_rs_amount;
                        String us_amount = str_us_amount;
                        String productid = cp.getStr_productid();
                        String userid = data.getObjectAsString("userid");
                        String dollerrate = cp.getDollarRate();
                        f_lot = cp.getReference();

                        String str_us = Get_US_value(dollerrate,rs_amount);

                        String status = data.getObjectAsString("login");
                        if (status.equalsIgnoreCase("false"))
                        {
                            Intent intent = new Intent(mContext,Before_Login_Activity.class);
                            mContext.startActivity(intent);

                        }
                        else {

                            if (is_us)
                            {
                                if(utility.checkInternet())
                                {
                                    String Str_productid = cp.getStr_productid();
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);

                                    GetData("US",Str_productid,us_amount, productid, userid, dollerrate, str_us,f_lot);

                                }
                                else
                                {

                                    show_dailog("Please Check Internet Connection");
                                }

                            }
                            else
                            {



                                if(utility.checkInternet())
                                {
                                    String Str_productid = cp.getStr_productid();
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);

                                    GetData("US",Str_productid,us_amount, productid, userid, dollerrate, str_us,f_lot);

                                }
                                else
                                {

                                    show_dailog("Please Check Internet Connection");
                                }

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



        btn_proxybid.setOnClickListener(new View.OnClickListener() {
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



                if (is_us)
                {
                    int int_proxy_bid_us = Get_10_value(cp.getPriceus());

                    String str_int_xus= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_us);

                    tv_bidvalue.setText(str_int_xus);
                    iv_iconproxy.setImageResource(R.drawable.doller);
                }
                else
                {

                    int int_proxy_bid_rs = Get_10_value(cp.getPricers());

                    value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                    rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);

                    str_rs_amount = String.valueOf(int_proxy_bid_rs);

                    tv_bidvalue.setText(rs_value);
                    iv_iconproxy.setImageResource(R.drawable.rupee);
                }




                tv_confim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dollerRate = cp.getDollarRate();
                        double doller = Double.parseDouble(dollerRate);

                        f_lot = cp.getReference();

                        String siteUserID = data.getObjectAsString("userid");
                        String productID = cp.getStr_productid();
                        String str_ProxyAmt = edt_proxy.getText().toString();
//                        String str_ProxyAmtus = Double.toString(Double.parseDouble(str_ProxyAmt)/doller);

                        int fb = Integer.parseInt(dollerRate);
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
                                String entered_value = edt_proxy.getText().toString();
                                String bid_value = value_for_cmpr;
                                int int_entered_value = Integer.parseInt(entered_value);
                                int int_bid_value = Integer.parseInt(bid_value);

                                if(int_entered_value > int_bid_value)
                                {
                                    if (is_us)
                                    {

                                        String str_Proxy_for_us = edt_proxy.getText().toString();

                                        int fb1 = Integer.parseInt(dollerRate);
                                        int rl1 = Integer.parseInt(str_Proxy_for_us);

                                        int str_ProxyAmtrs = rl1 * fb1;

                                        String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                        Toast.makeText(mContext, "from US", Toast.LENGTH_SHORT).show();


                                        if(utility.checkInternet())
                                        {
                                            ProxyBid(str_ProxyAmt,productID,siteUserID,dollerRate,proxy_amt_for_rs,f_lot);

                                        }
                                        else
                                        {
                                            show_dailog("Please Check Internet Connection");

                                        }



                                    }
                                    else                                 {
                                        Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                        if(utility.checkInternet())
                                        {
                                            ProxyBid(str_ProxyAmt,productID,siteUserID,dollerRate,proxy_amt_us,f_lot);
                                        }
                                        else
                                        {

                                            show_dailog("Please Check Internet Connection");
                                        }


                                    }

                                }
                                else
                                {

                                    Toast.makeText(mContext, "Proxy Must Greater Than Current Bid Value", Toast.LENGTH_SHORT).show();

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



        Picasso.with(getContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getStr_thumbnail())
                .into(expandedImageView);


        return curView;

    }



    private  void show_dailog(String msg)
    {

        String msgtest = msg;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    private int Get_10_value(String amt)
    {

        double amount = Double.parseDouble(amt);

        double byerprimium = (amount / 100.0f) * 10;

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

    private void BidNow(String str_Amount,String str_productID,String str_userID,String dollerrate,String proxy_new_us,String tlot)
    {

        if (utility.checkInternet())
        {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spBid("+str_Amount+","+str_productID+","+str_userID+","+dollerrate+","+proxy_new_us+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            final   String str_lot = tlot;
            final String str_amt = str_Amount;

            System.out.println("strPastAuctionUrl " + str_Amount);
            System.out.println("strPastAuctionUrl " + str_productID);
            System.out.println("strPastAuctionUrl " + str_userID);
            System.out.println("strPastAuctionUrl " + dollerrate);
            System.out.println("strPastAuctionUrl " + proxy_new_us);

            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback()
            {
                @Override
                public void onSuccess(String result) {
                    System.out.println("resultbid" + result);

                    System.out.println("strPastAuctionUrl " + result);

                    String str_json = result;

                    try {
                        if (str_json != null)
                        {

                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {
                                JSONObject Obj = jsonArray.getJSONObject(0);
                                String currentStatus = Obj.getString("currentStatus");
                                //String emailID = Obj.getString("emailID");
                                // String mobilrNum = Obj.getString("mobileNum");


//                                Toast.makeText(mContext,currentStatus, Toast.LENGTH_SHORT).show();
                                if(currentStatus.equals("1"))
                                {

                                    Toast.makeText(mContext, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
                                    String number = Obj.getString("mobileNum");
                                    bid_now.dismiss();

                                    show_dailog("You Succesfully Bid");
                                    registerUser(str_lot,str_amt,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    Toast.makeText(mContext, "U r out of bid", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    show_dailog("U r out of bid");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    Toast.makeText(mContext, "You can not bid for this lot right now as you are already leading for this lot.", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

                                }




                            } else {
                                Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

//                    currentAuction.call_data();
                }
            });
        }

    }

    private void GetData(String value,String str_productid,String rs_amount,String productid,String userid,String dollerrate,String proxy_new_us,String lot) {

        if (utility.checkInternet()) {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/Acution?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=productid="+str_productid+"";
            System.out.println("GetDataurl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();


            tvalue = value;
            trs_amount = rs_amount;
            tproductid = productid;
            tuserid = userid;
            tdollerrate = dollerrate;
            tproxy_new_us = proxy_new_us;

            tlot=lot;
            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);


                    String str_json = result;


                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);

                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");


                                JSONObject Obj = jsonArray.getJSONObject(0);

                                if(tvalue.equals("RS"))
                                {
                                    rupee_value = Obj.getString("pricers");
                                }
                                else
                                {

                                    rupee_value = Obj.getString("priceus");
                                }




                                int value_one = Integer.parseInt(rupee_value);
                                int value_two = Integer.parseInt(trs_amount);

                                if (value_two > value_one)
                                {

                                    System.out.println("ttt"+"bid valid ");

                                    BidNow(trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot);
                                }
                                else
                                {

                                    System.out.println("ttt"+"bid not valid  ");
//                                    Toast.makeText(mContext, "Dismiss  bidding", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    int int_proxy_bid_rselse = Get_10_value(rupee_value);


                                    rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse);

                                    str_rs_amount = String.valueOf(int_proxy_bid_rselse);

                                    Toast.makeText(mContext,"Bid Not Valid", Toast.LENGTH_SHORT).show();



                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                                    LayoutInflater inflater = (LayoutInflater) mContext
                                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
                                    dialogBuilder.setView(dialogView);

                                    final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                                    final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                                    final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                                    final ImageView iv_icon = (ImageView) dialogView.findViewById(R.id.iv_icon);

                                    if (is_us)
                                    {
                                        int int_proxy_bid_rselse_us = Get_10_value(rupee_value);


                                        String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_us);
                                        tv_bidvalue.setText(str_int_us);
                                        iv_icon.setImageResource(R.drawable.doller);
                                    }
                                    else
                                    {
                                        System.out.println("ttt"+"new value set  ");
                                        int int_proxy_bid_rselse_rs = Get_10_value(rupee_value);

                                        rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_rs);

                                        str_rs_amount = String.valueOf(int_proxy_bid_rselse_rs);

                                        tv_bidvalue.setText(rs_value);
                                        iv_icon.setImageResource(R.drawable.rupee);
                                    }


                                    tv_confim.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            String rs_amount = str_rs_amount;
                                            String us_amount = usvalue;
                                            String productid = f_pro_id;
                                            String  userid = data.getObjectAsString("userid");
                                            String  dollerrate =f_doller;

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
//                                                    BidNow(us_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
                                                    GetData("US",f_pro_id,us_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
                                                } else {

                                                    System.out.println("ttt"+"re bid ");

                                                    GetData("RS",f_pro_id,rs_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
//                                BidNow(rs_amount, productid, userid, dollerrate, proxy_new_us);
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




                            } else {
                                Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private void ProxyBid(String userProxyAmount,String productID,String siteUserID,String dollerRate,String dollerAmt,String f_lot) {

        if (utility.checkInternet()) {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            final   String userproxy = userProxyAmount;
            final String lot_no = f_lot;


            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);


                    String str_json = result;

                    try {
                        if (str_json != null)
                        {

                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {
                                JSONObject Obj = jsonArray.getJSONObject(0);
                                String currentStatus = Obj.getString("currentStatus");
//                                String emailID = Obj.getString("emailID");
//                                String mobilrNum = Obj.getString("mobilrNum");

                                if(currentStatus.equals("1"))
                                {

                                    Toast.makeText(mContext, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
                                    String number = Obj.getString("mobileNum");
                                    bid_proxy.dismiss();

                                    show_dailog("You Succesfully Bid");
                                    registerUser(lot_no,userproxy,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    Toast.makeText(mContext, "U r out of bid", Toast.LENGTH_SHORT).show();
                                    bid_proxy.dismiss();

                                    show_dailog("U r out of bid");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    Toast.makeText(mContext, "You can not bid for this lot right now as you are already leading for this lot.", Toast.LENGTH_SHORT).show();
                                    bid_proxy.dismiss();

                                    show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

                                }




                            } else {
                                Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }
    private void registerUser(String lot,String value,String mobile){


        String lot_number = lot;
        String bid_value = value;
        final String mob_number=mobile;

        System.out.println("mob_number " + lot_number);
        System.out.println("mob_number " + bid_value);
        System.out.println("mob_number " + mob_number);
        final String msg = "Dear User, please note you have been outbid on Lot No "+lot_number+". Next valid bid is Rs."+bid_value+". Place renewed bid on www.astaguru.com or mobile App.";

        System.out.println("msg" + msg);

        String URL_url = "http://gateway.netspaceindia.com/api/sendhttp.php?";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("mob_number " + response);
                        Toast.makeText(mContext,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("authkey", "131841Aotn6vhT583570b5");
                params.put("mobiles",mob_number);
                params.put("message",msg);
                params.put("sender","AstGru");
                params.put("route","4");
                params.put("country","91");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);


    }


    private void Delete(String str_productid) {

        if (utility.checkInternet()) {
            pDialog  = new ProgressDialog(mContext);
            pDialog.setMessage("Uploadig data ... ");
            pDialog.setCancelable(false);
            pDialog.show();
            String strPastAuctionUrl = Application_Constants.Main_URL+"bidartistuser?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=bidartistuserid="+str_productid+"";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, strPastAuctionUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            pDialog.dismiss();
                            try
                            {
                                VolleyLog.v("Response:%n %s", response.toString());

                                System.out.print("Response:%n %s "+ response.toString());

                                JSONObject userObject = null;

                                userObject = response.getJSONArray("resource").getJSONObject(0);
//                                String datadata = userObject.getString("userid");

                                Toast.makeText(mContext, "You Succesfully Delete Item", Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_SHORT);

                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(jsonObjectRequest);
        }

    }

}