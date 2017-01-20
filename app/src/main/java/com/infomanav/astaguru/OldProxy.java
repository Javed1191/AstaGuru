package com.infomanav.astaguru;

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
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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

import org.json.JSONArray;
import org.json.JSONObject;

public class OldProxy extends ArrayAdapter<Current_Auction_Model> {
    private MainActivity mainActivity;
    LinearLayout lin_front, lin_back;
    ImageView iv_oncce, expandedImageView, iv_zoom, iv_close;
    boolean isBackVisible = false;
    public DisplayMetrics m;
    Context mContext;
    TextView tv_title;

    Button btn_bidnow, btn_proxybid;
    public boolean is_us = false;
    List<Current_Auction_Model> objects;
    ImageView iv_one, iv_two,iv_oneback,iv_twoback;

    DecimalFormat formatter;
    Utility utility;
    SessionData data;
    EditText edt_proxy;
    AlertDialog bid_now,bid_proxy;
    FragmentCurrentAuction currentAuction;

    ProgressDialog pDialog;
    public OldProxy(Context context, int textViewResourceId, List<Current_Auction_Model> objects, final boolean is_us,FragmentCurrentAuction currentAuction) {
        super(context, textViewResourceId, objects);
        mainActivity = (MainActivity) context;
        mContext = context;
        this.objects = objects;
        this.is_us = is_us;

         this.currentAuction = currentAuction;
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


            curView = vi.inflate(R.layout.current_grid, null);

        }

        final Current_Auction_Model cp = getItem(position);

        formatter = new DecimalFormat("#,###,###");

        expandedImageView = (ImageView) curView.findViewById(R.id.grid_image);
        TextView tv_date = (TextView) curView.findViewById(R.id.tv_date);
         tv_title = (TextView) curView.findViewById(R.id.tv_title);
        TextView tv_lot = (TextView) curView.findViewById(R.id.tv_lot);
        TextView tv_subtitle = (TextView) curView.findViewById(R.id.tv_subtitle);
        TextView tv_current_bid = (TextView) curView.findViewById(R.id.tv_current_bid);
        TextView tv_nextbid = (TextView) curView.findViewById(R.id.tv_nextbid);
        iv_one = (ImageView) curView.findViewById(R.id.iv_one);
        iv_two = (ImageView) curView.findViewById(R.id.iv_two);


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
        iv_zoom = (ImageView) curView.findViewById(R.id.iv_zoom);

        iv_oneback= (ImageView) curView.findViewById(R.id.iv_oneback);
        iv_twoback= (ImageView) curView.findViewById(R.id.iv_twoback);
        tv_lot.setText("Lot " + position);

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


        iv_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_thumbnail();
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", Str_id);
                mContext.startActivity(intent);

            }
        });

        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_productid();
                Intent intent = new Intent(mContext, Lot_Detail_Page.class);
                intent.putExtra("Str_id", Str_id);
                mContext.startActivity(intent);

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
                    String us_value = cp.getPriceus();
                    int us_value_int = Integer.parseInt(us_value);
                    String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(us_value_int);
                    tv_bidvalue.setText(str_int_us);
                    iv_icon.setImageResource(R.drawable.doller);
                }
                else
                {
                    String rs_value = cp.getPricers();
                    int rs_value_int = Integer.parseInt(rs_value);
                    String str_int_rs= NumberFormat.getNumberInstance(Locale.US).format(rs_value_int);
                    tv_bidvalue.setText(str_int_rs);
                    iv_icon.setImageResource(R.drawable.rupee);
                }


//
//                final ImageView iv_sinin_close = (ImageView) dialogView.findViewById(R.id.iv_sinin_close);

                tv_confim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strdoller = cp.getDollarRate();

                        String rs_amount = cp.getPricers();
                        String us_amount = cp.getPriceus();
                        String productid = cp.getStr_productid();
                        String  userid = data.getObjectAsString("userid");
                        String  dollerrate = cp.getDollarRate();


                        int fb = Integer.parseInt(dollerrate);
                        int rl = Integer.parseInt(rs_amount);

                        int str_Proxy_new = rl / fb;

                        String proxy_new_us = Integer.toString(str_Proxy_new);

                        if (is_us)
                        {
                            BidNow(us_amount,productid,userid,dollerrate,proxy_new_us);
                        }
                        else
                        {
                            BidNow(rs_amount,productid,userid,dollerrate,proxy_new_us);
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
//                if (is_us)
//                {
//                    tv_bidvalue.setText(cp.getPriceus());
//                    iv_iconproxy.setImageResource(R.drawable.doller);
//                }
//                else
//                {
//                    tv_bidvalue.setText(cp.getPricers());
//                    iv_iconproxy.setImageResource(R.drawable.rupee);
//                }


                if (is_us)
                {
                    String us_value = cp.getPriceus();
                    int us_value_int = Integer.parseInt(us_value);
                    String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(us_value_int);
                    tv_bidvalue.setText(str_int_us);
                    iv_iconproxy.setImageResource(R.drawable.doller);
                }
                else
                {
                    String rs_value = cp.getPricers();
                    int rs_value_int = Integer.parseInt(rs_value);
                    String str_int_rs= NumberFormat.getNumberInstance(Locale.US).format(rs_value_int);
                    tv_bidvalue.setText(str_int_rs);
                    iv_iconproxy.setImageResource(R.drawable.rupee);
                }




                tv_confim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strdoller = cp.getDollarRate();
                        double doller = Double.parseDouble(strdoller);



                        String str_Userid = data.getObjectAsString("userid");
                        String str_Productid = cp.getStr_productid();
                        String str_ProxyAmt = edt_proxy.getText().toString();
                        String str_ProxyAmtus = Double.toString(Double.parseDouble(str_ProxyAmt)/doller);

                        int fb = Integer.parseInt(strdoller);
                        int rl = Integer.parseInt(str_ProxyAmt);

                        int str_ProxyAmtus_new = rl / fb;

                        String proxy_amt_us = Integer.toString(str_ProxyAmtus_new);


                        System.out.println("str_ProxyAmtus" + str_ProxyAmt);
                        System.out.println("proxy_amt_us" + proxy_amt_us);





                        String str_Status = "0";
                        String str_Auctionid = "27";

//                        System.out.println("str_Userid" + str_Userid);
//                        System.out.println("str_Productid" + str_Productid);
//                        System.out.println("str_ProxyAmt" + str_ProxyAmt);
//                        System.out.println("str_ProxyAmtus" + proxy_amt_us);
//                        System.out.println("str_Status" + str_Status);
//                        System.out.println("str_Auctionid" + str_Auctionid);

                        if (str_ProxyAmt.isEmpty())
                        {
                            Toast.makeText(mContext, "Pls Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();

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

                                System.out.println("proxy_amt_for_rs" + proxy_amt_for_rs);
                                System.out.println("str_Proxy_for_us" + str_Proxy_for_us);
                                ProxyBid(str_Userid,str_Productid,proxy_amt_for_rs,str_Proxy_for_us,str_Status,str_Auctionid);
                            }
                            else
                            {
                                Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                ProxyBid(str_Userid,str_Productid,str_ProxyAmt,proxy_amt_us,str_Status,str_Auctionid);
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


//            Picasso.with(getContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getAppLogo()).resize(m.widthPixels /4,m.widthPixels-500)
//                    .into(imageView);

        Picasso.with(getContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getStr_thumbnail())
                .into(expandedImageView);


        return curView;

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
                    //notifyDataSetChanged();
//                    listener.foo();

                    currentAuction.call_data();
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


    private void ProxyBid(String str_Userid,String str_Productid,String str_ProxyAmt,String str_ProxyAmtus,String str_Status,String str_Auctionid) {

        if (utility.checkInternet()) {
            pDialog  = new ProgressDialog(mContext);
            pDialog.setMessage("Uploadig data ... ");
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
            params.put("Auctionid",str_Auctionid);
            params.put("Createdby","Alex");
            params.put("CreatedDt","8/19/2016 6:19:19 PM");




            System.out.println("str_Userid" + str_Userid);
            System.out.println("str_Productid" + str_Productid);
            System.out.println("str_ProxyAmt" + str_ProxyAmt);
            System.out.println("str_ProxyAmtus" + str_ProxyAmtus);
            System.out.println("str_Status" + str_Status);
            System.out.println("str_Auctionid" + str_Auctionid);

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

                                System.out.print("Response:%n %s "+ response.toString());

                                JSONObject userObject = null;

                                userObject = response.getJSONArray("resource").getJSONObject(0);
//                                String datadata = userObject.getString("userid");

Toast.makeText(mContext, "You Succesfully Done Proxy Bid", Toast.LENGTH_SHORT).show();
        bid_proxy.dismiss();
        currentAuction.call_data();



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
//Toast.makeText(mContext, "You Succesfully Done Proxy Bid", Toast.LENGTH_SHORT).show();
//        bid_proxy.dismiss();
//        currentAuction.call_data();