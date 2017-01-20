package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infomanav.astaguru.Artist_Details;
import com.infomanav.astaguru.Before_Login_Activity;
import com.infomanav.astaguru.Current_Auction_Model;
import com.infomanav.astaguru.FragmentCurrentAuction;
import com.infomanav.astaguru.LoginActivity;
import com.infomanav.astaguru.Lot_Detail_Page;
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

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Current_Auction_Model> albumList;
    String f_doller,f_pro_id,f_lot;
    public boolean is_us = false;
    SessionData data;
    FragmentCurrentAuction currentAuction;
    AlertDialog bid_now,bid_proxy,dilog_alert;
    Utility utility;
    String rs_value,usvalue;
    String value_for_cmpr;
    String str_rs_amount,str_us_amount;
    String rupee_value;
    EditText edt_proxy;
    String trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot,tvalue;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_title,tv_lot,tv_lot_back,tv_subtitle,tv_current_bid,tv_nextbid,tv_ac_artist,tv_ac_category,tv_ac_medium;
        public ImageView expandedImageView, iv_one,iv_two,iv_addtogallary,iv_detail,iv_oncce,iv_zoom,iv_oneback,iv_twoback,iv_close;
        public TextView tv_ac_year,tv_ac_size,tv_estimate,tv_ac_bid,tv_ac_nextbid,tvDay,tvHour,tvMinute,tvSecond;
        public Button btn_bidnow,btn_proxybid;
        public LinearLayout lin_front,lin_back;

        public MyViewHolder(View curView) {
            super(curView);
            expandedImageView = (ImageView) curView.findViewById(R.id.grid_image);
             tv_date = (TextView) curView.findViewById(R.id.tv_date);
            tv_title = (TextView) curView.findViewById(R.id.tv_title);
            tv_lot = (TextView) curView.findViewById(R.id.tv_lot);
             tv_lot_back = (TextView) curView.findViewById(R.id.tv_lot_back);
             tv_subtitle = (TextView) curView.findViewById(R.id.tv_subtitle);
             tv_current_bid = (TextView) curView.findViewById(R.id.tv_current_bid);
             tv_nextbid = (TextView) curView.findViewById(R.id.tv_nextbid);
            iv_one = (ImageView) curView.findViewById(R.id.iv_one);
            iv_two = (ImageView) curView.findViewById(R.id.iv_two);
            iv_addtogallary = (ImageView) curView.findViewById(R.id.iv_addtogallary);
            iv_detail = (ImageView) curView.findViewById(R.id.iv_detail);
             tv_ac_artist = (TextView) curView.findViewById(R.id.tv_ac_artist);
             tv_ac_category = (TextView) curView.findViewById(R.id.tv_ac_category);
             tv_ac_medium = (TextView) curView.findViewById(R.id.tv_ac_medium);
             tv_ac_year = (TextView) curView.findViewById(R.id.tv_ac_year);
             tv_ac_size = (TextView) curView.findViewById(R.id.tv_ac_size);
             tv_estimate = (TextView) curView.findViewById(R.id.tv_estimate);
             tv_ac_bid = (TextView) curView.findViewById(R.id.tv_ac_bid);
             tv_ac_nextbid = (TextView) curView.findViewById(R.id.tv_ac_nextbid);

            tvDay = (TextView) curView.findViewById(R.id.txtTimerDay);
            tvHour = (TextView) curView.findViewById(R.id.txtTimerHour);
            tvMinute = (TextView) curView.findViewById(R.id.txtTimerMinute);
            tvSecond = (TextView) curView.findViewById(R.id.txtTimerSecond);

            iv_oncce = (ImageView) curView.findViewById(R.id.iv_oncce);
            iv_zoom = (ImageView) curView.findViewById(R.id.iv_zoom);

            iv_oneback= (ImageView) curView.findViewById(R.id.iv_oneback);
            iv_twoback= (ImageView) curView.findViewById(R.id.iv_twoback);

            iv_close = (ImageView) curView.findViewById(R.id.iv_close);

            btn_bidnow = (Button) curView.findViewById(R.id.btn_bidnow);
            btn_proxybid = (Button) curView.findViewById(R.id.btn_proxybid);

            lin_front = (LinearLayout) curView.findViewById(R.id.lin_front);
            lin_back = (LinearLayout) curView.findViewById(R.id.lin_back);
        }
    }

    public void changeCurrency() {
        if (is_us)
        {
            is_us = false;
        } else
        {
            is_us = true;
        }
        notifyDataSetChanged();
    }

    public void Upadte_GridView(List<Current_Auction_Model> objects)
    {
        this.albumList = objects;
        notifyDataSetChanged();
    }
    public AlbumsAdapter(Context context, int textViewResourceId, List<Current_Auction_Model> objects, final boolean is_us,FragmentCurrentAuction currentAuction) {
        this.mContext = context;
        this.albumList = objects;
        this.is_us = is_us;

        this.currentAuction = currentAuction;

        utility = new Utility(mContext);
        data = new SessionData(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_grid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Current_Auction_Model cp = albumList.get(position);
        holder.tv_ac_artist.setText(cp.getArtist_name());
        holder.tv_ac_category.setText(cp.getStr_category());
        holder.tv_ac_medium.setText(cp.getMedium());
        holder.tv_ac_year.setText(cp.getProductdate());
        holder.tv_ac_size.setText(cp.getProductsize());
        holder.tv_estimate.setText(cp.getEstamiate());

        holder.tv_lot.setText("Lot " + cp.getReference());
        holder.tv_lot_back.setText("Lot " + cp.getReference());

        holder.tv_title.setText(cp.getArtist_name());
        holder.tv_subtitle.setText(cp.getStr_title());


        Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getStr_thumbnail())
                .into(holder.expandedImageView);

        f_doller = cp.getDollarRate();
        f_pro_id = cp.getStr_productid();


        if (is_us) {

            String str_us = cp.getPriceus();

            int int_str = Integer.parseInt(str_us);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);


            holder.tv_current_bid.setText(str_ustest);
            holder.tv_ac_bid.setText(str_ustest);
            double amount1 = Double.parseDouble(cp.getPriceus());

            double byerprimium1 = (amount1 / 100.0f) * 10;

            double sum1 = amount1 + byerprimium1;

            int intbyerprimium1 = (int) sum1;

            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium1);

            holder.tv_nextbid.setText(valuebyerprimium1);
            holder.tv_ac_nextbid.setText(valuebyerprimium1);
            holder.iv_one.setImageResource(R.drawable.doller);
            holder.iv_two.setImageResource(R.drawable.doller);
            holder.iv_oneback.setImageResource(R.drawable.doller);
            holder.iv_twoback.setImageResource(R.drawable.doller);
        } else {

            String str_rs= cp.getPricers();
            int int_strrs = Integer.parseInt(str_rs);
            String str_rscomma= NumberFormat.getNumberInstance(Locale.US).format(int_strrs);

            holder.tv_current_bid.setText(str_rscomma);
            holder.tv_ac_bid.setText(str_rscomma);
            double amount = Double.parseDouble(cp.getPricers());

            double byerprimium = (amount / 100.0f) * 10;

            double sum = amount + byerprimium;

            int intbyerprimium = (int) sum;
            String valuebyerprimium= NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium);

//            String valuebyerprimium = String.valueOf(intbyerprimium);
            holder.tv_nextbid.setText(valuebyerprimium);
            holder.tv_ac_nextbid.setText(valuebyerprimium);
            holder.iv_one.setImageResource(R.drawable.rupee);
            holder.iv_two.setImageResource(R.drawable.rupee);
            holder.iv_oneback.setImageResource(R.drawable.rupee);
            holder.iv_twoback.setImageResource(R.drawable.rupee);
        }

        boolean  is = cp.getIs_front();

        if (cp.getIs_front())
        {
            holder.lin_front.setVisibility(View.VISIBLE);
            holder.lin_back.setVisibility(View.GONE);

            // applyRotation(0, 90,lin_front,lin_back,true);


        }
        else
        {
            holder.lin_front.setVisibility(View.GONE);
            holder.lin_back.setVisibility(View.VISIBLE);

//            applyRotation(0, -90,lin_front,lin_back,false);

        }
        holder.iv_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_thumbnail();
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", Str_id);
                mContext.startActivity(intent);

            }
        });

        holder.expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_productid();
                String reference = cp.getReference();
                Intent intent = new Intent(mContext, Lot_Detail_Page.class);
                intent.putExtra("Str_id", Str_id);
                intent.putExtra("reference", reference);
                intent.putExtra("fragment", "Current");
                mContext.startActivity(intent);

            }
        });

        holder.iv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_productid();
                String reference = cp.getReference();
                Intent intent = new Intent(mContext, Lot_Detail_Page.class);
                intent.putExtra("Str_id", Str_id);
                intent.putExtra("reference", reference);
                intent.putExtra("fragment", "Current");
                mContext.startActivity(intent);

            }
        });

        holder.iv_addtogallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_productid();
                String  userid = data.getObjectAsString("userid");
                AddToGallary(Str_id,userid);


            }
        });

        holder.tv_title.setOnClickListener(new View.OnClickListener() {
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

        holder.iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                flipGridViewItem(v, cp);
//
                cp.setIs_front(true);
                notifyDataSetChanged();


            }
        });

        holder.iv_oncce.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

//                flipGridViewItem(v, cp);

//                Toast.makeText(mContext, "test", Toast.LENGTH_SHORT).show();
                cp.setIs_front(false);
                notifyDataSetChanged();
            }
        });

        holder.btn_bidnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false"))
                {
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    intent.putExtra("str_from","adpter");
                    mContext.startActivity(intent);

                }
                else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
                    dialogBuilder.setView(dialogView);

                    final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                    final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                    final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                    final TextView tv_bidlot = (TextView) dialogView.findViewById(R.id.tv_bidlot);

                    final ImageView iv_icon = (ImageView) dialogView.findViewById(R.id.iv_icon);

                    if (is_us)
                    {
                        int int_bid_us = Get_10_value(cp.getPriceus());

                        str_us_amount = String.valueOf(int_bid_us);
                        str_rs_amount = String.valueOf(int_bid_us);
                        String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_bid_us);

                        tv_bidvalue.setText(str_int_us);
                        tv_bidlot.setText("Lot " + cp.getReference());
                        iv_icon.setImageResource(R.drawable.doller);
                    }
                    else
                    {
                        int int_bid_rs = Get_10_value(cp.getPricers());

                        str_rs_amount = String.valueOf(int_bid_rs);
                        str_us_amount = String.valueOf(int_bid_rs);
                        rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_bid_rs);

                        tv_bidvalue.setText(rs_value);
                        tv_bidlot.setText("Lot " + cp.getReference());
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



                            if (is_us)
                            {
                                if(utility.checkInternet())
                                {
                                    String Str_productid = cp.getStr_productid();


                                    int a = Integer.parseInt(us_amount);
                                    int b = Integer.parseInt(dollerrate);

                                    int str_rsonus = a*b;

                                    String proxy_new_us = Integer.toString(str_rsonus);
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);

                                    GetData("US",Str_productid,proxy_new_us, productid, userid, dollerrate, us_amount,f_lot);

                                    System.out.println("int_str" + Str_productid);
                                    System.out.println("int_str" + str_rsonus);
                                    System.out.println("int_str" + productid);
                                    System.out.println("int_str" + userid);
                                    System.out.println("int_str" + dollerrate);
                                    System.out.println("int_str" + a);
                                    System.out.println("int_str" + f_lot);




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

                                    System.out.println("int_str" + Str_productid);
                                    System.out.println("int_str" + us_amount);
                                    System.out.println("int_str" + productid);
                                    System.out.println("int_str" + userid);
                                    System.out.println("int_str" + dollerrate);
                                    System.out.println("int_str" + str_us);
                                    System.out.println("int_str" + f_lot);

                                }
                                else
                                {

                                    show_dailog("Please Check Internet Connection");
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
            }
        });



        holder.btn_proxybid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false"))
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
                    final ImageView iv_iconproxy = (ImageView) dialogView.findViewById(R.id.iv_iconproxy);



                    if (is_us)
                    {
                        int int_proxy_bid_us = Get_10_value(cp.getPriceus());
                        value_for_cmpr = String.valueOf(int_proxy_bid_us);
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
                            f_lot = cp.getReference();
                            String siteUserID = data.getObjectAsString("userid");
                            String productID = cp.getStr_productid();
                            String str_ProxyAmt = edt_proxy.getText().toString();

                            int fb = Integer.parseInt(dollerRate);
                            int rl = Integer.parseInt(str_ProxyAmt);

                            int str_ProxyAmtus_new = rl / fb;

                            String proxy_amt_us = Integer.toString(str_ProxyAmtus_new);




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
                                    if (is_us) {

                                        String str_Proxy_for_us = edt_proxy.getText().toString();

                                        int fb1 = Integer.parseInt(dollerRate);
                                        int rl1 = Integer.parseInt(str_Proxy_for_us);

                                        int str_ProxyAmtrs = rl1 * fb1;

                                        String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                        Toast.makeText(mContext, "from US", Toast.LENGTH_SHORT).show();


                                        if (utility.checkInternet()) {
                                            ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_for_rs, f_lot);

                                        } else {
                                            show_dailog("Please Check Internet Connection");

                                        }


                                    } else {
                                        Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                        if (utility.checkInternet()) {
                                            ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot);
                                        } else {

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


                                    BidNow(trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot);
                                }
                                else
                                {


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

    private void AddToGallary(String productID,String siteUserID) {

        if (utility.checkInternet()) {

            String addtogallaryUrl = "http://54.169.222.181/api/v2/guru/_proc/spAddToGallery("+productID+","+siteUserID+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + addtogallaryUrl);
            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, addtogallaryUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);
                    Toast.makeText(mContext, "Succesfully Added to Gallary", Toast.LENGTH_SHORT).show();


                    String str_json = result;


                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}