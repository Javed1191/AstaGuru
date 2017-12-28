package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.infomanav.astaguru.Artist_Details;
import com.infomanav.astaguru.Before_Login_Activity;
import com.infomanav.astaguru.Lot_Detail_Page;
import com.infomanav.astaguru.MainActivity;

import interfaces.OnBidResult;
import model_classes.Past_sub_Model;

import com.infomanav.astaguru.MakeBid;
import com.infomanav.astaguru.Past_Auction_SubActivity;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.ShowFullZoomImage;
import com.infomanav.astaguru.Verification_Activity;
import com.infomanav.astaguru.ZoomActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

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
    AlertDialog bid_now,bid_proxy,dilog_alert,dilog_bid_access;
    String str_rs_amount,str_us_amount;
    String rs_value,usvalue;
    String value_for_cmpr;
    String f_doller,f_pro_id,f_lot,Thumbnail,Reference,OldPriceUs,OldPriceRs,Auctionid,UlastName,Current_time_date;
    EditText edt_proxy;
    SessionData data;
    int height=0,width=0;
    DisplayMetrics displaymetrics;
    Activity activity;
    private MakeBid makeBid;
    private ShowFullZoomImage showFullZoomImage;
    public Past_Auction_SubAdpter(Context c, List<Past_sub_Model> AppsList, final boolean is_us,String auctiontype) {
        mContext = c;
        this.AppsList = AppsList;
        this.is_us = is_us;
        this.auctiontype = auctiontype;

        utility = new Utility(mContext);
        data = new SessionData(mContext);
        makeBid = new MakeBid(mContext);
        displaymetrics = new DisplayMetrics();
        activity = (Activity) mContext;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        showFullZoomImage = new ShowFullZoomImage(mContext);
    }


    public void changeCurrency(boolean is_us)
    {
        this.is_us = is_us;
//        if (is_us)
//        {
//            this.is_us = false;
//        } else
//        {
//            this.is_us = true;
//        }
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        ImageView imageView,iv_flip,iv_close_back,iv_detail,iv_zoom,iv_addtogallary;
        TextView tv_high_value,iv_two,grid_text, tv_lot, grid_text1, tv_bidvalue,tv_subtitle,tv_start_price;
        LinearLayout lin_front_data,lin_front,lin_back,lin_detail,lin_15,lay_bought_in;
        Button btn_proxybid;
        View view_v;
        RelativeLayout rel_artist,rel_cat,rel_medium,rel_year,rel_size,rel_etimate;

        TextView tv_sub_artist,tv_sub_category,tv_sub_medium,tv_sub_year,tv_sub_size,tv_sub_estimate,
                tv_lot_back,tv_category,tv_bought_in;
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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

//            grid.tv_high_value = (TextView) convertView.findViewById(R.id.tv_high_value);
            grid.grid_text = (TextView) convertView.findViewById(R.id.grid_text);
            grid.grid_text1 = (TextView) convertView.findViewById(R.id.tv_sub_name);

            grid.tv_subtitle = (TextView) convertView.findViewById(R.id.tv_subtitle);
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
            grid.tv_sub_medium = (TextView) convertView.findViewById(R.id.tv_sub_medium);
            grid.tv_sub_year= (TextView) convertView.findViewById(R.id.tv_sub_year);
            grid.tv_sub_size= (TextView) convertView.findViewById(R.id.tv_sub_size);
            grid.tv_sub_estimate= (TextView) convertView.findViewById(R.id.tv_sub_estimate);
            grid.iv_detail= (ImageView) convertView.findViewById(R.id.iv_detail);
            grid.iv_addtogallary = (ImageView) convertView.findViewById(R.id.iv_addtogallary);
            grid.lin_front = (LinearLayout) convertView.findViewById(R.id.lin_front_sub);
            grid.lin_back = (LinearLayout) convertView.findViewById(R.id.lin_back_sub);
            grid.lin_front_data= (LinearLayout) convertView.findViewById(R.id.lin_front_data);
            grid.rel_artist = (RelativeLayout) convertView.findViewById(R.id.rel_artist);
            grid.tv_category= (TextView) convertView.findViewById(R.id.tv_category);
            grid.tv_bought_in= (TextView) convertView.findViewById(R.id.tv_bought_in);
            grid.tv_start_price = (TextView) convertView.findViewById(R.id.tv_start_price);




            grid.rel_cat = (RelativeLayout) convertView.findViewById(R.id.rel_cat);
            grid.rel_medium = (RelativeLayout) convertView.findViewById(R.id.rel_medium);
            grid.rel_year = (RelativeLayout) convertView.findViewById(R.id.rel_year);
            grid.rel_size = (RelativeLayout) convertView.findViewById(R.id.rel_size);
            grid.rel_etimate= (RelativeLayout) convertView.findViewById(R.id.rel_etimate);
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

        if(apps.getAuctionname().equalsIgnoreCase("Collectibles Auction"))
        {
            grid.grid_text1.setVisibility(View.GONE);
        }
        else
        {
            grid.grid_text1.setText(apps.getArtist_name().trim());
        }

        grid.tv_subtitle.setText(apps.getStr_title().trim());
        grid.tv_lot.setText("Lot:"+apps.getReference().trim());
        grid.tv_lot_back.setText("Lot:"+apps.getReference().trim());
        grid.tv_sub_artist.setText(apps.getArtist_name().trim());

        grid.tv_sub_medium.setText(apps.getStr_medium().trim());
        grid.tv_category.setText(apps.getStr_category().trim());
        grid.tv_sub_year.setText(apps.getProductdate().trim());
        grid.tv_sub_size.setText(apps.getProductsize().trim()+" in");




        Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + apps.getStr_thumbnail()).placeholder(R.drawable.img_default).into(grid.imageView);

        if (apps.getis_front()) {
            grid.lin_front.setVisibility(View.VISIBLE);
            grid.lin_back.setVisibility(View.GONE);
        } else {
            grid.lin_front.setVisibility(View.GONE);
            grid.lin_back.setVisibility(View.VISIBLE);
        }


       /* Double int_Pricers = Double.parseDouble(apps.getPricers());
        Double int_Pricelow = Double.parseDouble(apps.getPricelow());

        if (int_Pricelow > int_Pricers)
        {
            grid.grid_text1.setVisibility(View.INVISIBLE);
            grid.tv_subtitle.setVisibility(View.INVISIBLE);
            grid.iv_two.setVisibility(View.INVISIBLE);
            grid.tv_bidvalue.setText("testets");


        }
        else
        {
            grid.grid_text1.setVisibility(View.INVISIBLE);
            grid.tv_subtitle.setVisibility(View.INVISIBLE);
            grid.iv_two.setVisibility(View.INVISIBLE);
            grid.tv_bidvalue.setVisibility(View.VISIBLE);
        }*/




        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParamsa = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        if (auctiontype.equalsIgnoreCase("past"))
        {
            grid.btn_proxybid.setVisibility(View.GONE);
            grid.view_v.setVisibility(View.GONE);
            grid.tv_start_price.setVisibility(View.GONE);
            grid.lin_15.setVisibility(View.VISIBLE);
            layoutParams.setMargins(0,25,0,0);
            layoutParamsa.setMargins(0,45,0,35);
            grid.rel_artist.setLayoutParams(layoutParams);
             grid.lin_detail.setLayoutParams(layoutParamsa);
            grid.iv_addtogallary.setVisibility(View.GONE);

                if(Double.parseDouble(apps.getPricers()) < Double.parseDouble(apps.getPricelow()))
                {
                   // Toast.makeText(mContext,"Test",Toast.LENGTH_SHORT).show();

                    grid.lin_front_data.setVisibility(View.GONE);
                    grid.tv_bought_in.setVisibility(View.VISIBLE);
                }
                else {
                    grid.lin_front_data.setVisibility(View.VISIBLE);
                    grid.tv_bought_in.setVisibility(View.GONE);
                }



//            grid.rel_cat.setLayoutParams(layoutParams);
//            grid.rel_medium.setLayoutParams(layoutParams);
//            grid.rel_year.setLayoutParams(layoutParams);
//            grid.rel_size.setLayoutParams(layoutParams);
//            grid.rel_etimate.setLayoutParams(layoutParams);

//            grid.grid_text1.setLayoutParams(params);
        }
        else
        {
            grid.lin_15.setVisibility(View.INVISIBLE);
            grid.btn_proxybid.setVisibility(View.VISIBLE);
            grid.tv_start_price.setVisibility(View.VISIBLE);
            grid.iv_addtogallary.setVisibility(View.VISIBLE);

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
        grid.grid_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Artist_Details.class);
                intent.putExtra("Str_id", apps.getStr_artistid());
                intent.putExtra("Str_artistname", apps.getArtist_name());
                intent.putExtra("Picture", apps.getPicture());
                intent.putExtra("Profile", apps.getStr_Profile());
                intent.putExtra("is_us", is_us);
                mContext.startActivity(intent);
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

               /* String Str_id = apps.getStr_image();
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", Str_id);
                mContext.startActivity(intent);*/

                showFullZoomImage.showImage(apps.getStr_image());
            }
        });

        grid.iv_addtogallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = apps.getStr_productid();
                String  userid = data.getObjectAsString("userid");
                //AddToGallary(Str_id,userid);

                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(mContext,Before_Login_Activity.class);
                    intent.putExtra("str_from","adpter");
                    mContext.startActivity(intent);

                }
                else {
                    AddToGallary(Str_id, userid);
                }


            }
        });
        if (is_us)
        {

            String str_us="",str_ustest="0";
            str_us = apps.getPriceus();

            if(str_us!=null)
            {
                if (auctiontype.equalsIgnoreCase("past"))
                {
                    str_us = claculatePercentage(str_us);
                }

                Double int_str = Double.parseDouble(str_us);
                str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
                str_ustest = mContext.getString(R.string.doller)+" "+str_ustest;
                grid.tv_bidvalue.setText(str_ustest);
            }
            else
            {
                str_ustest = mContext.getString(R.string.doller)+" "+str_ustest;
                grid.tv_bidvalue.setText(str_ustest);
            }
            grid.iv_two.setVisibility(View.GONE);
            grid.iv_two.setText("US$");
            String strCollectors = apps.getStr_collectors();

            if(!strCollectors.equals("null"))
            {
                grid.tv_sub_estimate.setText(apps.getEstamiate().trim());
            }


        }
        else
        {
            String str_rs = apps.getPricers();
            String str_amt_rss="0";

            if(str_rs!=null)
            {
                if (auctiontype.equalsIgnoreCase("past"))
                {
                    str_rs = claculatePercentage(str_rs);
                }

                Double int_strrs = Double.parseDouble(str_rs);
                String str_rstest = NumberFormat.getNumberInstance(Locale.US).format(int_strrs);
                str_amt_rss = rupeeFormat(str_rstest);
                str_amt_rss = mContext.getString(R.string.rupees)+" "+str_amt_rss;
                grid.tv_bidvalue.setText(str_amt_rss);
            }
            else
            {
                str_amt_rss = mContext.getString(R.string.rupees)+" "+str_amt_rss;
                grid.tv_bidvalue.setText(str_amt_rss);
            }
            grid.iv_two.setVisibility(View.GONE);
            grid.iv_two.setText("₹");

            String strCollectors = apps.getStr_collectors();

            if(!strCollectors.equals("null"))
            {
                grid.tv_sub_estimate.setText(apps.getStr_collectors().trim());
            }

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
               intent.putExtra("Auctionname",apps.getAuctionname());

               intent.putExtra("medium",apps.getStr_medium());
               intent.putExtra("FirstName",apps.getStr_FirstName());
               intent.putExtra("LastName",apps.getStr_LastName());
               intent.putExtra("Profile",apps.getStr_Profile());
               intent.putExtra("is_us",is_us);
               intent.putExtra("dollar_rate",apps.getDollarRate());
               if (auctiontype.equalsIgnoreCase("past"))
               {
                   if (Double.parseDouble(apps.getPricers()) < Double.parseDouble(apps.getPricelow()))
                   {
                       intent.putExtra("is_bought_in",true);
                   }
               }

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
                intent.putExtra("Auctionname",apps.getAuctionname());

                intent.putExtra("medium",apps.getStr_medium());
                intent.putExtra("FirstName",apps.getStr_FirstName());
                intent.putExtra("LastName",apps.getStr_LastName());
                intent.putExtra("Profile",apps.getStr_Profile());
                intent.putExtra("is_us",is_us);
                intent.putExtra("dollar_rate",apps.getDollarRate());
                if (auctiontype.equalsIgnoreCase("past"))
                {
                    if (Double.parseDouble(apps.getPricers()) < Double.parseDouble(apps.getPricelow()))
                    {
                        intent.putExtra("is_bought_in",true);
                    }
                }
                mContext.startActivity(intent);
            }
        });

        grid.btn_proxybid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(mContext,Before_Login_Activity.class);
                    mContext.startActivity(intent);

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
                        final TextView tv_bit_text = (TextView) dialogView.findViewById(R.id.username);
                            final LinearLayout lay_bid_values = (LinearLayout) dialogView.findViewById(R.id.lay_bid_values);
                            final TextView tv_confirm_bid = (TextView) dialogView.findViewById(R.id.tv_confirm_bid);
                        tv_bit_text.setText("Opening Bid");

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


                        if (is_us)
                        {
                            double int_proxy_bid_us = 0;


                            double myNum = Double.parseDouble(apps.getPricers());
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

                            double int_proxy_bid_rs = 0;

                            double myNum = Double.parseDouble(apps.getPricers());
                            if (myNum < 10000000) {
                                int_proxy_bid_rs = Get_10_value(apps.getPricers());
                            } else {
                                int_proxy_bid_rs = Get_5_value(apps.getPricers());
                            }

                            value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                            //rs_value = NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(apps.getPricers()));

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
                            public void onClick(View v) {


                                String dollerRate = apps.getDollarRate();
                                f_lot = apps.getReference();
                                String siteUserID = data.getObjectAsString("userid");
                                String productID = apps.getStr_productid();
                                String str_ProxyAmt = edt_proxy.getText().toString();





                                if (str_ProxyAmt.isEmpty())
                                {
                                    Toast.makeText(mContext, "Please Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();
                                } else {

                                   /* double fb = Double.parseDouble(dollerRate);
                                    double rl = Double.parseDouble(str_ProxyAmt);


                                    double str_ProxyAmtus_new = rl / fb;

                                    String proxy_amt_us = Double.toString(str_ProxyAmtus_new);
///


                                    String entered_value = edt_proxy.getText().toString();
                                    String bid_value = value_for_cmpr;
                                    double int_entered_value = Double.parseDouble(entered_value);
                                    double int_bid_value = Double.parseDouble(bid_value);*/


                                    double fb = Double.parseDouble(dollerRate);
                                    double rl = Double.parseDouble(str_ProxyAmt);

                                    double str_ProxyAmtus_new = rl / fb;
                                    int int_proxy_new = (int) Math.round(str_ProxyAmtus_new);
                                    String proxy_amt_us = String.valueOf(int_proxy_new);


                                    String entered_value = edt_proxy.getText().toString();
                                    String bid_value = value_for_cmpr;
                                    double int_entered_value = Double.parseDouble(entered_value);
                                    double int_bid_value = Double.parseDouble(bid_value);

                                    if (int_entered_value >= int_bid_value)
                                    {

                                        if(lay_bid_values.getVisibility()==View.VISIBLE)
                                        {
                                            lay_bid_values.setVisibility(View.GONE);
                                            tv_confirm_bid.setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            if (is_us) {

                                                String str_Proxy_for_us = edt_proxy.getText().toString();
                                                String str_us = MakeBid.Get_US_value(dollerRate, str_Proxy_for_us);

                                                String Createdby = data.getObjectAsString("name");
                                                String Auction_id = apps.getAuction_id();
                                                double fb1 = Double.parseDouble(dollerRate);
                                                double rl1 = Double.parseDouble(str_Proxy_for_us);

                                                double str_ProxyAmtrs = rl1 * fb1;

                                                //String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                                int_proxy_new = (int) Math.round(str_ProxyAmtrs);
                                                String proxy_amt_for_rs = String.valueOf(int_proxy_new);


                                                if (utility.checkInternet()) {

                                                    //ProxyBid(siteUserID, productID, proxy_amt_for_rs, proxy_amt_for_rs, "0", "", Createdby, Auction_id);

                                                    makeBid.proxyBidForUpcoming(siteUserID, productID, proxy_amt_for_rs, str_Proxy_for_us, Createdby,Auction_id, f_lot,apps.getAuctionname());

                                                    makeBid.bidResult(new OnBidResult() {
                                                        @Override
                                                        public void bidResult(String currentStatus, String msg) {
                                                            bid_proxy.dismiss();
                                                        }
                                                    });

                                                } else {
                                                    show_dailog("Please Check Internet Connection");

                                                }


                                            } else {
                                                //  Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                                String str_Proxy = edt_proxy.getText().toString();
                                                String Rate = apps.getDollarRate();
                                                String str_us = MakeBid.Get_US_value(Rate, str_Proxy);

                                                String Createdby = data.getObjectAsString("name");
                                                String Auction_id = apps.getAuction_id();

                                                if (utility.checkInternet()) {

                                                    //ProxyBid(siteUserID, productID, str_Proxy, str_us, "0", "", Createdby, Auction_id);
                                                    makeBid.proxyBidForUpcoming(siteUserID, productID, str_Proxy, str_us, Createdby,Auction_id, f_lot,apps.getAuctionname());

                                                    makeBid.bidResult(new OnBidResult() {
                                                        @Override
                                                        public void bidResult(String currentStatus, String msg) {
                                                            bid_proxy.dismiss();
                                                        }
                                                    });
                                                } else {

                                                    show_dailog("Please Check Internet Connection");
                                                }


                                            }
                                        }



                                    }
                                    else
                                        {
                                            double myNum = Double.parseDouble(apps.getPricers());
                                            if (myNum < 10000000) {
                                                Toast.makeText(mContext, "Proxy Bid value must be greater by at least 10% of current price.", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(myNum < 10000000)
                                            {
                                                Toast.makeText(mContext, "Proxy Bid value must be greater by at least 5% of current price", Toast.LENGTH_SHORT).show();
                                            }



                                    }


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
                           // show_dailog("You don't have access to Bid Please contact Astagru.");
                            bidAccessDialog("Astaguru","You don't have access to Bid Please contact Astaguru.");
                        }
                    }
                    else
                    {
                        Intent intent = new Intent(mContext,Verification_Activity.class);
                        intent.putExtra("Activity","Login");
                        mContext.startActivity(intent);

                    }
                }
            }
        });



        return convertView;
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
        if(dollerrate.isEmpty())
        {
            dollerrate="60";
        }

        double fb = Double.parseDouble(dollerrate);
        double rl = Double.parseDouble(rs_amount);

        double str_Proxy_new = rl / fb;

        String proxy_new_us = Double.toString(str_Proxy_new);

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

                                Toast.makeText(mContext, "You Successfully Done Proxy Bid", Toast.LENGTH_SHORT).show();
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
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+")?api_key="+ Application_Constants.API_KEY;
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

            String addtogallaryUrl = Application_Constants.Main_URL_Procedure+"spAddToGallery("+productID+","+siteUserID+")?api_key="+ Application_Constants.API_KEY;
            System.out.println("strPastAuctionUrl " + addtogallaryUrl);
            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(mContext);


            serviceHandler.registerUser(null, addtogallaryUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);
                    Toast.makeText(mContext, "The Lot has been added to your auction gallery.", Toast.LENGTH_SHORT).show();


                    String str_json = result;


                }
            });
        }
        else
        {
            Toast.makeText(mContext, "Please Check Internet Connection.",Toast.LENGTH_LONG)
                    .show();
        }

    }

    private String claculatePercentage(String strPrise)
    {
        String strBidPrise="";

        try
        {

            double dbl_bid_prise = 0,dbl_discount=0;

            dbl_bid_prise = Double.parseDouble(strPrise);
            dbl_discount = ((Double.parseDouble(strPrise)*15)/100);
            dbl_bid_prise = dbl_bid_prise+dbl_discount;

            strBidPrise = String.valueOf(Math.round(dbl_bid_prise));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return strBidPrise;
    }

    public void bidAccessDialog(String strTitle, String strMessage)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

}