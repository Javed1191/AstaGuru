package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.infomanav.astaguru.Before_Login_Activity;
import com.infomanav.astaguru.LoginActivity;
import com.infomanav.astaguru.Lot_Detail_Page;
import com.infomanav.astaguru.MainActivity;
import com.infomanav.astaguru.Past_sub_Model;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.ZoomActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

import static com.infomanav.astaguru.R.id.tv_bidvalue;

/**
 * Created by android-javed on 12-10-2016.
 */

public class Past_Auction_SubAdpter extends BaseAdapter {

    ProgressDialog pDialog;
    private Context mContext;
    private List<Past_sub_Model> AppsList;
    private MainActivity mainActivity;
    Utility utility;
    public boolean is_us = false;
    String auctiontype;
    AlertDialog bid_now,bid_proxy,dilog_alert;
    String str_rs_amount,str_us_amount;
    String rs_value,usvalue;
    String value_for_cmpr;
    String f_doller,f_pro_id,f_lot,Thumbnail,Reference,OldPriceUs,OldPriceRs,Auctionid;
    EditText edt_proxy;
    SessionData data;
    public Past_Auction_SubAdpter(Context c, List<Past_sub_Model> AppsList, final boolean is_us,String auctiontype) {
        mContext = c;
        this.AppsList = AppsList;
        this.is_us = is_us;
        this.auctiontype = auctiontype;

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

    public static class ViewHolder {
        ImageView imageView,iv_flip,iv_close_back,iv_detail,iv_zoom;
        TextView iv_two,grid_text, tv_lot, grid_text1, tv_bidvalue;
        LinearLayout lin_front,lin_back,lin_detail,lin_15;
        Button btn_proxybid;
        View view_v;
        RelativeLayout rel_artist,rel_cat,rel_medium,rel_year,rel_size,rel_etimate;

        TextView tv_sub_artist,tv_sub_category,tv_sub_medium,tv_sub_year,tv_sub_size,tv_sub_estimate,tv_lot_back;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return AppsList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder grid;


        if (convertView == null)
        {
            grid = new ViewHolder();



            convertView = LayoutInflater.from(mContext).inflate(R.layout.past_sub_single, null, false);
            grid.grid_text = (TextView) convertView.findViewById(R.id.grid_text);
            grid.grid_text1 = (TextView) convertView.findViewById(R.id.tv_sub_name);
            grid.tv_lot = (TextView) convertView.findViewById(R.id.tv_lot);
            grid.tv_lot_back = (TextView) convertView.findViewById(R.id.tv_lot_back);
            grid.imageView = (ImageView) convertView.findViewById(R.id.grid_imagepastsub);
            grid.iv_flip = (ImageView) convertView.findViewById(R.id.iv_flip);
            grid.iv_zoom= (ImageView) convertView.findViewById(R.id.iv_zoom);
            grid.iv_close_back = (ImageView) convertView.findViewById(R.id.iv_close_back);
            grid.iv_two = (TextView) convertView.findViewById(R.id.iv_two);
            grid.tv_bidvalue = (TextView) convertView.findViewById(R.id.tv_bidvalue);
            grid.btn_proxybid = (Button) convertView.findViewById(R.id.btn_proxybid);
            grid.view_v = (View) convertView.findViewById(R.id.view_v);
            grid.tv_sub_artist = (TextView) convertView.findViewById(R.id.tv_sub_artist);
            grid.tv_sub_category= (TextView) convertView.findViewById(R.id.tv_sub_category);
            grid.tv_sub_medium = (TextView) convertView.findViewById(R.id.tv_sub_medium);
            grid.tv_sub_year= (TextView) convertView.findViewById(R.id.tv_sub_year);
            grid.tv_sub_size= (TextView) convertView.findViewById(R.id.tv_sub_size);
            grid.tv_sub_estimate= (TextView) convertView.findViewById(R.id.tv_sub_estimate);
            grid.iv_detail= (ImageView) convertView.findViewById(R.id.iv_detail);

            grid.lin_front = (LinearLayout) convertView.findViewById(R.id.lin_front_sub);
            grid.lin_back = (LinearLayout) convertView.findViewById(R.id.lin_back_sub);

            grid.rel_artist = (RelativeLayout) convertView.findViewById(R.id.rel_artist);

            grid.rel_cat = (RelativeLayout) convertView.findViewById(R.id.rel_cat);
            grid.rel_medium = (RelativeLayout) convertView.findViewById(R.id.rel_medium);
            grid.rel_year = (RelativeLayout) convertView.findViewById(R.id.rel_year);
            grid.rel_size = (RelativeLayout) convertView.findViewById(R.id.rel_size);
            grid.rel_etimate= (RelativeLayout) convertView.findViewById(R.id.rel_size);
            grid.lin_detail= (LinearLayout) convertView.findViewById(R.id.lin_detail);
            grid.lin_15= (LinearLayout) convertView.findViewById(R.id.lin_15);

            convertView.setTag(grid);
        }
        else
        {
            grid = (ViewHolder) convertView.getTag();
        }
        grid.lin_front.setVisibility(View.VISIBLE);

        final Past_sub_Model apps = AppsList.get(position);
        grid.grid_text1.setText(apps.getArtist_name().trim());
        grid.tv_lot.setText("Lot:"+apps.getReference().trim());
        grid.tv_lot_back.setText("Lot:"+apps.getReference().trim());
        grid.tv_sub_artist.setText(apps.getArtist_name().trim());
        grid.tv_sub_category.setText(apps.getStr_category().trim());
        grid.tv_sub_medium.setText(apps.getStr_category().trim());
        grid.tv_sub_year.setText(apps.getProductdate().trim());
        grid.tv_sub_size.setText(apps.getProductsize().trim());



        Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + apps.getStr_thumbnail()).placeholder(R.drawable.noimage).into(grid.imageView);

        if (apps.getis_front()) {
            grid.lin_front.setVisibility(View.VISIBLE);
            grid.lin_back.setVisibility(View.GONE);
        } else {
            grid.lin_front.setVisibility(View.GONE);
            grid.lin_back.setVisibility(View.VISIBLE);
        }



        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParamsa = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        if (auctiontype.equals("past")) {
            grid.btn_proxybid.setVisibility(View.GONE);
            grid.view_v.setVisibility(View.GONE);

            grid.lin_15.setVisibility(View.VISIBLE);
            layoutParams.setMargins(0,25,0,0);
            layoutParamsa.setMargins(0,45,0,35);
            grid.rel_artist.setLayoutParams(layoutParams);
             grid.lin_detail.setLayoutParams(layoutParamsa);
//            grid.rel_cat.setLayoutParams(layoutParams);
//            grid.rel_medium.setLayoutParams(layoutParams);
//            grid.rel_year.setLayoutParams(layoutParams);
//            grid.rel_size.setLayoutParams(layoutParams);
//            grid.rel_etimate.setLayoutParams(layoutParams);

//            grid.grid_text1.setLayoutParams(params);
        } else {
            grid.lin_15.setVisibility(View.INVISIBLE);
            grid.btn_proxybid.setVisibility(View.VISIBLE);
        }


        grid.iv_close_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grid.lin_front.setVisibility(View.VISIBLE);
                grid.lin_back.setVisibility(View.GONE);

                apps.setIs_front(true);
                notifyDataSetChanged();

            }
        });

        grid.iv_flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grid.lin_front.setVisibility(View.GONE);
                grid.lin_back.setVisibility(View.VISIBLE);

                apps.setIs_front(false);
                notifyDataSetChanged();
            }
        });

        grid.iv_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Str_id = apps.getStr_thumbnail();
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", Str_id);
                mContext.startActivity(intent);
            }
        });


        if (is_us)
        {
            String str_us = apps.getPriceus();
            int int_str = Integer.parseInt(str_us);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
            grid.tv_bidvalue.setText(str_ustest);
            grid.iv_two.setText("US$");

            grid.tv_sub_estimate.setText(apps.getEstamiate().trim());

        }
        else
        {
            String str_rs = apps.getPricers();
            int int_strrs = Integer.parseInt(str_rs);
            String str_rstest = NumberFormat.getNumberInstance(Locale.US).format(int_strrs);

            grid.tv_bidvalue.setText(str_rstest);

            grid.iv_two.setText("₹");

            grid.tv_sub_estimate.setText(apps.getStr_collectors().trim());
        }

       grid.imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String Str_id =apps.getStr_productid();
               Intent intent = new Intent(mContext,Lot_Detail_Page.class);
               intent.putExtra("Str_id",Str_id);
               intent.putExtra("reference", apps.getReference());
               intent.putExtra("fragment",auctiontype);
               intent.putExtra("Auction_id",apps.getAuction_id());
               mContext.startActivity(intent);
           }
       });


        grid.iv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id =apps.getStr_productid();
                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
                intent.putExtra("Str_id",Str_id);
                intent.putExtra("fragment",auctiontype);
                intent.putExtra("reference", apps.getReference());
                intent.putExtra("Auction_id",apps.getAuction_id());
                mContext.startActivity(intent);
            }
        });

        grid.btn_proxybid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(mContext,Before_Login_Activity.class);
                    mContext.startActivity(intent);

                }
                else
                {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    LayoutInflater inflater = (LayoutInflater) mContext
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogView = inflater.inflate(R.layout.dailog_proxybid, null);
                    dialogBuilder.setView(dialogView);

                    final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                    final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                    edt_proxy = (EditText) dialogView.findViewById(R.id.edt_proxy);
                    final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                    final TextView tv_proxylot = (TextView) dialogView.findViewById(R.id.tv_proxylot);

                    final TextView iv_iconproxy = (TextView) dialogView.findViewById(R.id.iv_iconproxy);
                    String img_name = apps.getStr_thumbnail();
                    Thumbnail = img_name.replace("paintings/","");
                    Reference=apps.getReference();
                    OldPriceRs=apps.getPriceus();
                    OldPriceUs=apps.getPricers();
                    Auctionid=apps.getReference();



                    if (is_us)
                    {
                        int int_proxy_bid_us =0;


                        int myNum = Integer.parseInt(apps.getPricers());
                        if (myNum<10000000)
                        {
                            int_proxy_bid_us = Get_10_value(apps.getPriceus());
                        }
                        else
                        {
                            int_proxy_bid_us = Get_5_value(apps.getPriceus());
                        }
                        value_for_cmpr = String.valueOf(int_proxy_bid_us);
                        String str_int_xus= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_us);

                        tv_bidvalue.setText(str_int_xus);
                        tv_proxylot.setText("Lot " + apps.getReference());
                        iv_iconproxy.setText("US$");
                    }
                    else
                    {

                        int int_proxy_bid_rs =0;

                        int myNum = Integer.parseInt(apps.getPricers());
                        if (myNum<10000000)
                        {
                            int_proxy_bid_rs = Get_10_value(apps.getPriceus());
                        }
                        else
                        {
                            int_proxy_bid_rs = Get_5_value(apps.getPriceus());
                        }

                        value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                        rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);

                        str_rs_amount = String.valueOf(int_proxy_bid_rs);
                        tv_proxylot.setText("Lot " + apps.getReference());
                        tv_bidvalue.setText(rs_value);
                        iv_iconproxy.setText("₹");
                    }




                    tv_confim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            String dollerRate = apps.getDollarRate();
                            f_lot = apps.getReference();
                            String siteUserID = data.getObjectAsString("userid");
                            String productID = apps.getStr_productid();
                            String str_ProxyAmt = edt_proxy.getText().toString();



                            double fb = Double.parseDouble(dollerRate);
                            double rl = Double.parseDouble(str_ProxyAmt);


                            double str_ProxyAmtus_new = rl / fb;

                            String proxy_amt_us = Double.toString(str_ProxyAmtus_new);
///



                            if (str_ProxyAmt.isEmpty())
                            {
                                Toast.makeText(mContext, "Pls Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                String entered_value = edt_proxy.getText().toString();
                                String bid_value = value_for_cmpr;
                                int int_entered_value = Integer.parseInt(entered_value);
                                int int_bid_value = Integer.parseInt(bid_value);

                                if (int_entered_value > int_bid_value) {
                                    if (is_us)
                                    {

                                        String str_Proxy_for_us = edt_proxy.getText().toString();
                                        String str_us = Get_US_value(dollerRate,str_Proxy_for_us);

                                        String Createdby  = data.getObjectAsString("name");
                                        String Auction_id= apps.getAuction_id();
                                        int fb1 = Integer.parseInt(dollerRate);
                                        int rl1 = Integer.parseInt(str_Proxy_for_us);

                                        int str_ProxyAmtrs = rl1 * fb1;

                                        String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                        Toast.makeText(mContext, "from US", Toast.LENGTH_SHORT).show();


                                        if (utility.checkInternet()) {

                                            ProxyBid(siteUserID,productID,proxy_amt_for_rs,proxy_amt_for_rs,"0","",Createdby,Auction_id);

                                        } else {
                                            show_dailog("Please Check Internet Connection");

                                        }


                                    }
                                    else
                                    {
                                        Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                        String str_Proxy = edt_proxy.getText().toString();
                                        String Rate = apps.getDollarRate();
                                        String str_us = Get_US_value(Rate,str_Proxy);

                                        String Createdby  = data.getObjectAsString("name");
                                        String Auction_id= apps.getAuction_id();

                                        if (utility.checkInternet())
                                        {

                                            ProxyBid(siteUserID,productID,str_Proxy,str_us,"0","",Createdby,Auction_id);
                                        }
                                        else
                                        {

                                            show_dailog("Please Check Internet Connection");
                                        }


                                    }

                                } else {

                                    Toast.makeText(mContext, "Proxy Must Greater Than Current Bid Value", Toast.LENGTH_SHORT).show();

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
            }
        });



        return convertView;
    }


    private int Get_10_value(String amt)
    {

        double amount = Double.parseDouble(amt);

        double byerprimium = (amount / 100.0f) * 10;

        double sum = amount + byerprimium;

        int intbyerprimium = (int) sum;

        return intbyerprimium;
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

        int fb = Integer.parseInt("68");
        int rl = Integer.parseInt(rs_amount);

        int str_Proxy_new = rl / fb;

        String proxy_new_us = Integer.toString(str_Proxy_new);

        return proxy_new_us;
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
    private void ProxyBid(String str_Userid,String str_Productid,String str_ProxyAmt,String str_ProxyAmtus,String str_Status,String str_Auctionid,String Createdby,String Auction_id) {

        if (utility.checkInternet()) {
            pDialog  = new ProgressDialog(mContext);
            pDialog.setMessage("Proxy Bidding ... ");
            pDialog.setCancelable(false);
            pDialog.show();
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/ProxyAuctionDetails?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();
            params.put("Userid",str_Userid);
            params.put("Productid",str_Productid);
            params.put("ProxyAmt",str_ProxyAmt);
            params.put("ProxyAmtus",str_ProxyAmtus);
            params.put("Status",str_Status);
            params.put("Auctionid",Auction_id);
            params.put("Createdby",Createdby);
            params.put("CreatedDt","8/19/2016 6:19:19 PM");




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

                                Toast.makeText(mContext, "You Succesfully Done Proxy Bid", Toast.LENGTH_SHORT).show();
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
    private void ProxyBidnew(String userProxyAmount,String productID,String siteUserID,String dollerRate,String dollerAmt,String f_lot)
    {

        if (utility.checkInternet()) {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
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

                                    String number = Obj.getString("mobileNum");
                                    bid_proxy.dismiss();

                                    show_dailog("You Succesfully Bid");
                                    registerUser(lot_no,userproxy,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    bid_proxy.dismiss();

                                    show_dailog("U r out of bid");

                                }
                                else if (currentStatus.equals("3"))
                                {

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

}