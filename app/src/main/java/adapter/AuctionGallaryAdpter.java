package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import interfaces.OnBidResult;
import model_classes.AuctionGallary_Model;
import com.infomanav.astaguru.Auction_Gallary_Activity;
import com.infomanav.astaguru.Before_Login_Activity;
import com.infomanav.astaguru.Bid_History;
import com.infomanav.astaguru.FragmentCurrentAuction;
import com.infomanav.astaguru.Lot_Detail_Page;
import com.infomanav.astaguru.MakeBid;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.ShowFullZoomImage;
import com.infomanav.astaguru.Verification_Activity;
import com.infomanav.astaguru.ZoomActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model_classes.Current_Auction_Model;
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
//    http://54.169.222.181/api/v2/guru/_table/Acution?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=productid=2383

    private LinearLayout linearLayout1, linearLayout2;
    private Handler handler;
    private Runnable runnable;
    LinearLayout lin_front, lin_back,lin_count;
    ImageView iv_oncce, expandedImageView, iv_zoom, iv_close,iv_remove,iv_detail;
    boolean isBackVisible = false;
    public DisplayMetrics m;
    Context mContext;
    public TextView tv_bidding,tv_lot,tvDay, tvHour, tvMinute, tvSecond, tvDayb, tvHourb, tvMinuteb, tvSecondb;
    private boolean isFirstImage = true;
    TextView tv_title,tv_countdown;
    String trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot,tvalue;
    Button btn_bidnow, btn_proxybid;
    public boolean is_us = false,is_after;
    List<AuctionGallary_Model> objects_constructer;
    TextView iv_one, iv_two,iv_oneback,iv_twoback;
    String rs_value,usvalue;
    DecimalFormat formatter;
    SessionData data;
    EditText edt_proxy;
    AlertDialog bid_now,bid_proxy,dilog_alert,dilog_bid_access;
    String str_rs_amount,str_us_amount;
    String rupee_value;
    ProgressDialog pDialog;
    String value_for_cmpr;
    String f_doller,f_pro_id,f_lot,Thumbnail,Reference,OldPriceUs,OldPriceRs,Auctionid,Ufirst_name,Ulastname,
            Bidclosingtime,strTitle="",Createdby="";
    Auction_Gallary_Activity gallaryActivity;
    Utility utility;
    Activity activity;
    int height=0,width=0;
    DisplayMetrics displaymetrics;
    private MakeBid makeBid;
    private  Auction_Gallary_Activity auction_gallary_activity;
    private ShowFullZoomImage showFullZoomImage;
    public AuctionGallaryAdpter(Context context, int textViewResourceId, List<AuctionGallary_Model> objects, final boolean is_us,Auction_Gallary_Activity auction_gallary_activity) {
        super(context, textViewResourceId, objects);
        mContext = context;
        this.objects_constructer = objects;
        this.is_us = is_us;
        this.gallaryActivity=gallaryActivity;

        utility = new Utility(mContext);
        data = new SessionData(mContext);
        displaymetrics = new DisplayMetrics();
        activity = (Activity) mContext;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        makeBid = new MakeBid(mContext);
        this.auction_gallary_activity=auction_gallary_activity;
        showFullZoomImage = new ShowFullZoomImage(mContext);

        Createdby = data.getObjectAsString("name");
    }

    public void changeCurrency(boolean is_us) {
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

    public void Upadte_GridViewWithFilter(List<AuctionGallary_Model> objects, boolean is_filter)
    {
        try
        {

            if(is_filter)
            {

                if(objects.size()==objects_constructer.size())
                {
                    for(int i=0; i<objects.size();i++)
                    {
                        objects.get(i).setIs_front(objects_constructer.get(i).getIs_front());
                        objects.get(i).setIs_front(objects_constructer.get(i).getIs_front());

                    }
                }
                else
                {
                    this.objects_constructer = objects;
                }

            }
            else
            {
                if(objects_constructer!=null&&objects_constructer.size()>0)
                {

                    for(int i=0; i<objects_constructer.size();i++)
                    {
                        objects.get(i).setIs_front(objects_constructer.get(i).getIs_front());
                    }

                    this.objects_constructer = objects;

                }
                else
                {
                    this.objects_constructer = objects;
                }
            }


            notifyDataSetChanged();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public void Upadte_GridView(List<AuctionGallary_Model> objects)
    {

        if(objects_constructer!=null&&objects_constructer.size()>0)
        {
            for(int i =0;i<objects_constructer.size();i++)
            {
                AuctionGallary_Model cp = this.objects_constructer.get(i);

                boolean is_ = cp.getIs_front();
                AuctionGallary_Model cp1 = objects.get(i);

                boolean is_1 = cp1.getIs_front();
                cp1.setIs_front(cp.getIs_front());
            }

            this.objects_constructer = objects;

        }
        else
        {
            this.objects_constructer = objects;
        }
        notifyDataSetChanged();
    }
    public static class ViewHolder {
        public View holderView,lin_front,lin_back;
        TextView tv_lot;

    }

    @Override
    public int getCount() {
        return this.objects_constructer.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        // TODO Auto-generated method stub
        View curView;
        if(convertView==null)
        {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.auction_gallary_grid, null);
        }else{
            curView = convertView;
        }




        // final Current_Auction_Model cp = getItem(position);

        final AuctionGallary_Model cp = this.objects_constructer.get(position);

        formatter = new DecimalFormat("#,###,###");

        expandedImageView = (ImageView) curView.findViewById(R.id.grid_image);
        TextView tv_date = (TextView) curView.findViewById(R.id.tv_date);
        tv_title = (TextView) curView.findViewById(R.id.tv_title);
        tv_countdown= (TextView) curView.findViewById(R.id.tv_countdown);
        tv_lot = (TextView) curView.findViewById(R.id.tv_lot);
        TextView tv_lot_back = (TextView) curView.findViewById(R.id.tv_lot_back);
        TextView tv_subtitle = (TextView) curView.findViewById(R.id.tv_subtitle);
        TextView tv_current_bid = (TextView) curView.findViewById(R.id.tv_current_bid);
        TextView tv_nextbid = (TextView) curView.findViewById(R.id.tv_nextbid);
        iv_one = (TextView) curView.findViewById(R.id.iv_one);
        iv_two = (TextView) curView.findViewById(R.id.iv_two);
        iv_remove = (ImageView) curView.findViewById(R.id.iv_remove);
        iv_detail = (ImageView) curView.findViewById(R.id.iv_detail);
        TextView tv_ac_artist = (TextView) curView.findViewById(R.id.tv_ac_artist);
        TextView tv_ac_category = (TextView) curView.findViewById(R.id.tv_ac_category);
        TextView tv_ac_medium = (TextView) curView.findViewById(R.id.tv_ac_medium);
        TextView tv_ac_year = (TextView) curView.findViewById(R.id.tv_ac_year);
        TextView tv_ac_size = (TextView) curView.findViewById(R.id.tv_ac_size);
        TextView tv_estimate = (TextView) curView.findViewById(R.id.tv_estimate);
        TextView tv_ac_bid = (TextView) curView.findViewById(R.id.tv_ac_bid);
        TextView tv_ac_nextbid = (TextView) curView.findViewById(R.id.tv_ac_nextbid);
        tv_bidding= (TextView) curView.findViewById(R.id.tv_bidding);

        tvDay = (TextView) curView.findViewById(R.id.txtTimerDay);
        tvHour = (TextView) curView.findViewById(R.id.txtTimerHour);
        tvMinute = (TextView) curView.findViewById(R.id.txtTimerMinute);
        tvSecond = (TextView) curView.findViewById(R.id.txtTimerSecond);

        tvDayb= (TextView) curView.findViewById(R.id.txtTimerDayb);
        tvHourb= (TextView) curView.findViewById(R.id.txtTimerMinuteb);
        tvMinuteb= (TextView) curView.findViewById(R.id.txtTimerHourb);
        tvSecondb= (TextView) curView.findViewById(R.id.txtTimerSecondb);
        lin_count = (LinearLayout) curView.findViewById(R.id.lin_count);

        iv_oncce = (ImageView) curView.findViewById(R.id.iv_oncce);
        iv_zoom = (ImageView) curView.findViewById(R.id.iv_zoom);

        iv_oneback= (TextView) curView.findViewById(R.id.iv_oneback);
        iv_twoback= (TextView) curView.findViewById(R.id.iv_twoback);

        btn_bidnow = (Button) curView.findViewById(R.id.btn_bidnow);
        btn_proxybid = (Button) curView.findViewById(R.id.btn_proxybid);
        tv_ac_artist.setText(cp.getArtist_name());
        tv_ac_category.setText(cp.getStr_category());
        tv_ac_medium.setText(cp.getMedium());
        tv_ac_year.setText(cp.getProductdate());
        tv_ac_size.setText(cp.getProductsize()+" in");


        f_doller = cp.getDollarRate();
        f_pro_id = cp.getStr_productid();

        System.out.println("status" +cp.getStr_Bidclosingtime());

        String str_ = cp.getStatus();

        if (cp.getStatus().equals("Upcomming"))
        {
            btn_bidnow.setVisibility(View.GONE);
            btn_proxybid.setVisibility(View.VISIBLE);

            tv_countdown.setVisibility(View.GONE);
            lin_count.setVisibility(View.INVISIBLE);

        }
        else
        {
            try
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                String str = cp.getStr_Bidclosingtime();

                Date eventDate = dateFormat.parse(str);

                String strcurrentDate =cp.getCurrentDate();
                Date currentDate = dateFormat.parse(strcurrentDate);

                is_after = currentDate.before(eventDate);



                if (is_after)
                {
                    long diff = eventDate.getTime()- currentDate.getTime();
                    long days = diff / (24 * 60 * 60 * 1000);
                    diff -= days * (24 * 60 * 60 * 1000);
                    long hours = diff / (60 * 60 * 1000);
                    diff -= hours * (60 * 60 * 1000);
                    long minutes = diff / (60 * 1000);
                    diff -= minutes * (60 * 1000);
                    long seconds = diff / 1000;

                    tvDay.setText("" + String.format("%02d",days));
                    tvHour.setText("" + String.format("%02d", hours));
                    tvMinute.setText("" + String.format("%02d", minutes));
                    tvSecond.setText("" + String.format("%02d", seconds));

                    tvDayb.setText("" + String.format("%02d",days));
                    tvHourb.setText("" + String.format("%02d", hours));
                    tvMinuteb.setText("" + String.format("%02d", minutes));
                    tvSecondb.setText("" + String.format("%02d", seconds));

                    btn_bidnow.setEnabled(true);
                    btn_proxybid.setEnabled(true);
                    btn_bidnow.setBackgroundResource(R.color.black);
                    btn_proxybid.setBackgroundResource(R.color.black);


                    btn_bidnow.setVisibility(View.VISIBLE);
                    btn_proxybid.setVisibility(View.VISIBLE);
                    tv_countdown.setVisibility(View.GONE);
                    lin_count.setVisibility(View.VISIBLE);
                }
                else
                {
                    System.out.println("status" +cp.getReference());

                    lin_count.setVisibility(View.GONE);
                    tv_countdown.setVisibility(View.VISIBLE);


                    btn_bidnow.setVisibility(View.VISIBLE);
                    btn_proxybid.setVisibility(View.VISIBLE);
                    btn_bidnow.setBackgroundResource(R.color.grey05);
                    btn_proxybid.setBackgroundResource(R.color.grey05);
                    btn_bidnow.setEnabled(false);
                    btn_proxybid.setEnabled(false);
                    tv_bidding.setVisibility(View.GONE);
                    String userid = data.getObjectAsString("userid");

                    if(userid.equals(cp.getMyUserID()))
                    {


                        btn_bidnow.setVisibility(View.GONE);
                        btn_proxybid.setVisibility(View.GONE);


                    }


//
//                tvDay.setText("" + String.format("%02d","0"));
//                tvHour.setText("" + String.format("%02d", "0"));
//                tvMinute.setText("" + String.format("%02d", "0"));
//                tvSecond.setText("" + String.format("%02d", "0"));
//
//                tvDayb.setText("" + String.format("%02d","0"));
//                tvHourb.setText("" + String.format("%02d", "0"));
//                tvMinuteb.setText("" + String.format("%02d", "0"));
//                tvSecondb.setText("" + String.format("%02d", "0"));

                }


                String user = data.getObjectAsString("login");
                String userid = data.getObjectAsString("userid");

                if(user.equals("true"))
                {
                    if(userid.equals(cp.getMyUserID()))
                    {
                        tv_lot.setBackgroundResource(R.drawable.green_btn);
                        tv_lot_back.setBackgroundResource(R.drawable.green_btn);

                        btn_bidnow.setVisibility(View.GONE);
                        btn_proxybid.setVisibility(View.GONE);

                        if(!is_after)
                        {
                            tv_bidding.setText("Lot Won");
                        }
                        else
                        {
                            tv_bidding.setText("You are currently the highest bidder.");

                        }

                        tv_bidding.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        tv_lot.setBackgroundResource(R.drawable.rounded_rectangle);
                        tv_lot_back.setBackgroundResource(R.drawable.rounded_rectangle);

                        btn_bidnow.setVisibility(View.VISIBLE);
                        btn_proxybid.setVisibility(View.VISIBLE);

                        tv_bidding.setVisibility(View.GONE);

                    }

                }
                else
                {
                    tv_lot.setBackgroundResource(R.drawable.rounded_rectangle);
                    tv_lot_back.setBackgroundResource(R.drawable.rounded_rectangle);
                    btn_bidnow.setVisibility(View.VISIBLE);
                    btn_proxybid.setVisibility(View.VISIBLE);

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        tv_lot.setText("Lot " + cp.getReference());
        tv_lot_back.setText("Lot " + cp.getReference());
        iv_close = (ImageView) curView.findViewById(R.id.iv_close);

        lin_front = (LinearLayout) curView.findViewById(R.id.lin_front);
        lin_back = (LinearLayout) curView.findViewById(R.id.lin_back);
        m = mContext.getResources().getDisplayMetrics();

        tv_title.setText(cp.getArtist_name());
        tv_subtitle.setText(cp.getStr_title());

//        if (data.getObjectAsString("currency").equals("USD"))
//        {
//            is_us = true;
//            notifyDataSetChanged();
//        }
//        else
//        {
//            is_us = false;
//            notifyDataSetChanged();
//        }


        if (is_us) {

            double next_bid_doller=0;
            String str_us = cp.getPriceus();

            double int_str = Double.parseDouble(str_us);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

            tv_estimate.setText(cp.getEstamiate());
            tv_current_bid.setText(str_ustest);
            tv_ac_bid.setText(str_ustest);


            double myNum = Double.parseDouble(cp.getPricers());
            if (myNum<10000000)
            {
                next_bid_doller = Get_10_value(cp.getPriceus());
            }
            else
            {
                next_bid_doller = Get_5_value(cp.getPriceus());
            }
            String valuenext_bid_doller= NumberFormat.getNumberInstance(Locale.US).format(next_bid_doller);
            tv_nextbid.setText(valuenext_bid_doller);
            tv_ac_nextbid.setText(valuenext_bid_doller);

            iv_one.setText("US$");
            iv_two.setText("US$");
            iv_oneback.setText("US$");
            iv_twoback.setText("US$");
        } else {
            double next_bid=0;
            String str_rs= cp.getPricers();
            double int_strrs = Double.parseDouble(str_rs);
            String str_rscomma= NumberFormat.getNumberInstance(Locale.US).format(int_strrs);

            tv_current_bid.setText(str_rscomma);

            tv_ac_bid.setText(str_rscomma);
            tv_estimate.setText(cp.getStr_collectors());


            double myNum = Double.parseDouble(cp.getPricers());

            if (myNum<10000000)
            {
                next_bid = Get_10_value(cp.getPricers());
            }
            else
            {
                next_bid = Get_5_value(cp.getPricers());
            }

            String valuebyerprimium= NumberFormat.getNumberInstance(Locale.US).format(next_bid);

            tv_nextbid.setText(valuebyerprimium);
            tv_ac_nextbid.setText(valuebyerprimium);

            iv_one.setText("₹");
            iv_two.setText("₹");
            iv_oneback.setText("₹");
            iv_twoback.setText("₹");
        }

        final boolean  is = cp.getIs_front();

        if (cp.getIs_front())
        {
            lin_front.setVisibility(View.VISIBLE);
            lin_back.setVisibility(View.GONE);

            // applyRotation(0, 90,lin_front,lin_back,true);


        }
        else
        {
            lin_front.setVisibility(View.GONE);
            lin_back.setVisibility(View.VISIBLE);

//            applyRotation(0, -90,lin_front,lin_back,false);

        }


//        if(cp.getFace() == Current_Auction_Model.ItemFace.FRONT){ // Showing the front of the card
//
//            lin_back.setVisibility(View.GONE);
//            lin_front.setVisibility(View.VISIBLE);
//
//        }
//        else{
//
//            lin_front.setVisibility(View.GONE);
//            lin_back.setVisibility(View.VISIBLE);
//
//        }


        iv_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* String Str_id = cp.getStr_image();
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", Str_id);
                mContext.startActivity(intent);*/

                showFullZoomImage.showImage(cp.getStr_image());

            }
        });

        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (is_us)
                {
                    data.setObjectAsString("currency","USD");
                }else
                {
                    data.setObjectAsString("currency","INR");

                }
                String Str_id = cp.getStr_productid();
                String reference = cp.getReference();
                Intent intent = new Intent(mContext, Lot_Detail_Page.class);
                intent.putExtra("Str_id", Str_id);
                intent.putExtra("reference", reference);
                intent.putExtra("fragment", "Current");
                intent.putExtra("MyUserID",cp.getMyUserID());
                intent.putExtra("HumanFigure",cp.getHumanFigure());
                intent.putExtra("currentDate",cp.getCurrentDate());
                intent.putExtra("Auctionname",cp.getAuctionname());

                intent.putExtra("medium",cp.getMedium());
                intent.putExtra("FirstName",cp.getStr_FirstName());
                intent.putExtra("LastName",cp.getStr_LastName());
                intent.putExtra("Profile",cp.getStr_Profile());
                intent.putExtra("is_us",is_us);
                intent.putExtra("dollar_rate",cp.getDollarRate());
                mContext.startActivity(intent);

            }
        });

        iv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (is_us)
                {
                    data.setObjectAsString("currency","USD");
                }else
                {
                    data.setObjectAsString("currency","INR");

                }
                String Str_id = cp.getStr_productid();
                String reference = cp.getReference();
                Intent intent = new Intent(mContext, Lot_Detail_Page.class);
                intent.putExtra("Str_id", Str_id);
                intent.putExtra("reference", reference);
                intent.putExtra("fragment", "Current");
                intent.putExtra("MyUserID",cp.getMyUserID());
                intent.putExtra("HumanFigure",cp.getHumanFigure());
                intent.putExtra("currentDate",cp.getCurrentDate());
                intent.putExtra("Auctionname",cp.getAuctionname());

                intent.putExtra("medium",cp.getMedium());
                intent.putExtra("FirstName",cp.getStr_FirstName());
                intent.putExtra("LastName",cp.getStr_LastName());
                intent.putExtra("Profile",cp.getStr_Profile());
                intent.putExtra("is_us",is_us);
                intent.putExtra("dollar_rate",cp.getDollarRate());
                mContext.startActivity(intent);

            }
        });


        iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bidartistuserid = cp.getBidartistuserid();
                objects_constructer.remove(position);
                notifyDataSetChanged();
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
                intent.putExtra("Picture", cp.getPicture());
                intent.putExtra("Profile", cp.getStr_Profile());
                mContext.startActivity(intent);

            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                flipGridViewItem(v, cp);
//
                cp.setIs_front(true);
                notifyDataSetChanged();


            }
        });

        iv_oncce.setOnClickListener(new View.OnClickListener()
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



//        iv_oncce.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                if (isFirstImage) {
//                    applyRotation(0, 90);
//                    isFirstImage = !isFirstImage;
//
//                } else {
//                    applyRotation(0, -90);
//                    isFirstImage = !isFirstImage;
//                }
//            }
//        });
        btn_bidnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(mContext,Before_Login_Activity.class);
                    intent.putExtra("str_from","adpter");
                    mContext.startActivity(intent);

                }
                else {
                    String MobileVerified = data.getObjectAsString("MobileVerified");
                    String EmailVerified = data.getObjectAsString("EmailVerified");
                    String confirmbid = data.getObjectAsString("confirmbid");
                    if (MobileVerified.equalsIgnoreCase("true") && EmailVerified.equalsIgnoreCase("true"))
                    {
                        if (confirmbid.equals("1"))
                        {

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
                        dialogBuilder.setView(dialogView);

                        final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                        final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                        final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                        final TextView tv_bidlot = (TextView) dialogView.findViewById(R.id.tv_bidlot);

                        final TextView iv_icon = (TextView) dialogView.findViewById(R.id.iv_icon);
                            iv_icon.setVisibility(View.GONE);

                        String img_name = cp.getStr_thumbnail();
                            Thumbnail = img_name.replace("paintings/", "");
                            Reference = cp.getReference();
                            OldPriceRs = cp.getPricers();
                            OldPriceUs = cp.getPriceus();
                            Auctionid = cp.getOnline();
                            Ufirst_name = cp.getStr_FirstName();
                            Ulastname = cp.getStr_LastName();
                            Bidclosingtime= cp.getStr_Bidclosingtime();
                            strTitle= cp.getStr_title();


                            if (is_us) {
                            int int_bid_us = 0;


                            int myNum = Integer.parseInt(cp.getPricers());
                            if (myNum < 10000000) {
                                int_bid_us = Get_10_value(cp.getPriceus());
                            } else {
                                int_bid_us = Get_5_value(cp.getPriceus());
                            }

                            str_us_amount = String.valueOf(int_bid_us);
                            str_rs_amount = String.valueOf(int_bid_us);
                            String str_int_us = NumberFormat.getNumberInstance(Locale.US).format(int_bid_us);

                            tv_bidvalue.setText("US$ "+str_int_us);
                            tv_bidlot.setText("Lot " + cp.getReference());
                            iv_icon.setText("US$");
                        } else {
                            int int_bid_rs = 0;


                            int myNum = Integer.parseInt(cp.getPricers());
                            if (myNum < 10000000) {
                                int_bid_rs = Get_10_value(cp.getPricers());
                            } else {
                                int_bid_rs = Get_5_value(cp.getPricers());
                            }

                            str_rs_amount = String.valueOf(int_bid_rs);
                            str_us_amount = String.valueOf(int_bid_rs);
                            rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_bid_rs);

                            tv_bidvalue.setText("₹ "+rs_value);
                            tv_bidlot.setText("Lot " + cp.getReference());
                            iv_icon.setText("₹");

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

                               // String str_us = Get_US_value(dollerrate, rs_amount);
                                String str_us = MakeBid.Get_US_value(dollerrate, rs_amount);


                                if (is_us) {
                                    if (utility.checkInternet()) {
                                        String Str_productid = cp.getStr_productid();


                                        int a = Integer.parseInt(us_amount);
                                        int b = Integer.parseInt(dollerrate);

                                        int str_rsonus = a * b;

                                        String proxy_new_us = Integer.toString(str_rsonus);
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);

                                       // GetData("US", Str_productid, proxy_new_us, productid, userid, dollerrate, us_amount, f_lot,strTitle);

                                        makeBid.GetData("US",Str_productid, proxy_new_us, productid, userid, dollerrate, us_amount, f_lot,strTitle,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,is_us);
                                        makeBid.bidResult(new OnBidResult() {
                                            @Override
                                            public void bidResult(String currentStatus, String msg)
                                            {
                                                // Toast.makeText(mContext,currentStatus,Toast.LENGTH_SHORT).show();

                                                bid_now.dismiss();
                                                auction_gallary_activity.startRepeatingTask();
                                            }

                                        });

                                    } else {

                                        show_dailog("Please Check Internet Connection");
                                    }

                                } else {


                                    if (utility.checkInternet()) {
                                        String Str_productid = cp.getStr_productid();
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);

                                       // GetData("US", Str_productid, us_amount, productid, userid, dollerrate, str_us, f_lot,strTitle);

                                        makeBid.GetData("RS", Str_productid, us_amount, productid, userid, dollerrate, str_us, f_lot,strTitle,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,is_us);
                                        makeBid.bidResult(new OnBidResult() {
                                            @Override
                                            public void bidResult(String currentStatus, String msg)
                                            {
                                                // Toast.makeText(mContext,currentStatus,Toast.LENGTH_SHORT).show();

                                                bid_now.dismiss();
                                                auction_gallary_activity.startRepeatingTask();
                                            }

                                        });


                                    } else {

                                        show_dailog("Please Check Internet Connection");
                                    }

                                }


                            }
                        });
                        bid_now = dialogBuilder.create();
                        bid_now.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bid_now.dismiss();
                            }
                        });


                        bid_now.show();
                            // bid_now.getWindow().setLayout(700, LinearLayout.LayoutParams.MATCH_PARENT);
                            Window window = bid_now.getWindow();
                            window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
                            window.setGravity(Gravity.CENTER);
                            bid_now.setCanceledOnTouchOutside(false);


                        }
                        else
                        {
                            //Toast.makeText(mContext, "You do not have bidding access", Toast.LENGTH_SHORT).show();
                            bidAccessDialog("Astaguru","You don't have access to Bid Please contact Astaguru.");
                        }
                    } else {
                        Intent intent = new Intent(mContext, Verification_Activity.class);
                        intent.putExtra("Activity", "Login");
                        mContext.startActivity(intent);

                    }
                }
            }
        });



        btn_proxybid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false") || status.isEmpty() || status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(mContext, Before_Login_Activity.class);
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

                    final TextView iv_iconproxy = (TextView) dialogView.findViewById(R.id.iv_iconproxy);
                    final LinearLayout lay_bid_values = (LinearLayout) dialogView.findViewById(R.id.lay_bid_values);
                    final TextView tv_confirm_bid = (TextView) dialogView.findViewById(R.id.tv_confirm_bid);

                            String img_name = cp.getStr_thumbnail();

                            Thumbnail = img_name.replace("paintings/", "");
                            Reference = cp.getReference();
                            OldPriceRs = cp.getPricers();
                            OldPriceUs = cp.getPriceus();
                            Auctionid = cp.getOnline();
                            Ufirst_name = cp.getStr_FirstName();
                            Ulastname = cp.getStr_LastName();
                            Bidclosingtime= cp.getStr_Bidclosingtime();
                            strTitle= cp.getStr_title();

                    if (is_us)
                    {
                        double int_proxy_bid_us = 0;


                        double myNum = Double.parseDouble(cp.getPricers());
                        if (myNum < 10000000)
                        {
                            int_proxy_bid_us = Get_10_value(cp.getPriceus());
                        }
                        else
                        {
                            int_proxy_bid_us = Get_5_value(cp.getPriceus());
                        }
                        value_for_cmpr = String.valueOf(int_proxy_bid_us);
                        String str_int_xus = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_us);

                        tv_bidvalue.setText(str_int_xus);
                        tv_proxylot.setText("Lot " + cp.getReference());
                        iv_iconproxy.setText("US$");
                    }
                    else

                        {

                            double int_proxy_bid_rs = 0;

                            double myNum = Double.parseDouble(cp.getPricers());
                        if (myNum < 10000000)
                        {
                            int_proxy_bid_rs = Get_10_value(cp.getPricers());
                        } else
                        {
                            int_proxy_bid_rs = Get_5_value(cp.getPricers());
                        }

                        value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                        rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);

                        str_rs_amount = String.valueOf(int_proxy_bid_rs);
                        tv_proxylot.setText("Lot " + cp.getReference());
                        tv_bidvalue.setText(rs_value);
                        iv_iconproxy.setText("₹");
                    }


                    tv_confim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            String dollerRate = cp.getDollarRate();
                            f_lot = cp.getReference();
                            String siteUserID = data.getObjectAsString("userid");
                            String productID = cp.getStr_productid();
                            String str_ProxyAmt = edt_proxy.getText().toString();

                            if (str_ProxyAmt.isEmpty())
                            {
                                Toast.makeText(mContext, "Please Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                double fb = Double.parseDouble(dollerRate);
                                double rl = Double.parseDouble(str_ProxyAmt);

                                double str_ProxyAmtus_new = rl / fb;
                                int int_proxy_new = (int) Math.round(str_ProxyAmtus_new);
                                String proxy_amt_us = String.valueOf(int_proxy_new);


                                if (str_ProxyAmt.isEmpty())
                                {
                                    Toast.makeText(mContext, "Please Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

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
                                            if (is_us)
                                            {


                                                String str_Proxy_for_us = edt_proxy.getText().toString();

                                                double fb1 = Double.parseDouble(dollerRate);
                                                double rl1 = Double.parseDouble(str_Proxy_for_us);

                                                double str_ProxyAmtrs = rl1 * fb1;

                                                String proxy_amt_for_rs = Double.toString(str_ProxyAmtrs);
                                                // Toast.makeText(mContext, "from US", Toast.LENGTH_SHORT).show();


                                                if (utility.checkInternet())
                                                {
                                                    //ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_for_rs, f_lot);

                                                    if(cp.getStatus().equalsIgnoreCase("Current"))
                                                    {
                                                        makeBid.ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_for_rs, f_lot,Bidclosingtime,
                                                                Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,strTitle);

                                                        makeBid.bidResult(new OnBidResult() {
                                                            @Override
                                                            public void bidResult(String currentStatus, String msg) {
                                                                bid_proxy.dismiss();
                                                                auction_gallary_activity.startRepeatingTask();
                                                            }
                                                        });
                                                    }
                                                    else
                                                    {
                                                        makeBid.proxyBidForUpcoming(siteUserID, productID, proxy_amt_for_rs, str_Proxy_for_us, Createdby,Auctionid, f_lot,cp.getAuctionname());

                                                        makeBid.bidResult(new OnBidResult() {
                                                            @Override
                                                            public void bidResult(String currentStatus, String msg) {
                                                                bid_proxy.dismiss();
                                                            }
                                                        });
                                                    }




                                                }
                                                else
                                                {
                                                    show_dailog("Please Check Internet Connection");

                                                }


                                            }
                                            else
                                            {
                                                // Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                                if (utility.checkInternet())
                                                {
                                                    //ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot);

                                                    if(cp.getStatus().equalsIgnoreCase("Current"))
                                                    {
                                                        makeBid.ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot,Bidclosingtime,
                                                                Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,strTitle);

                                                        makeBid.bidResult(new OnBidResult() {
                                                            @Override
                                                            public void bidResult(String currentStatus, String msg) {
                                                                bid_proxy.dismiss();
                                                                auction_gallary_activity.startRepeatingTask();
                                                            }
                                                        });
                                                    }
                                                    else
                                                    {
                                                        makeBid.proxyBidForUpcoming(siteUserID, productID, str_ProxyAmt, proxy_amt_us, Createdby,Auctionid, f_lot,cp.getAuctionname());

                                                        makeBid.bidResult(new OnBidResult() {
                                                            @Override
                                                            public void bidResult(String currentStatus, String msg) {
                                                                bid_proxy.dismiss();
                                                            }
                                                        });
                                                    }


                                                }
                                                else
                                                {

                                                    show_dailog("Please Check Internet Connection");
                                                }


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
                            window.setLayout(width - 200, LinearLayout.LayoutParams.WRAP_CONTENT);
                            window.setGravity(Gravity.CENTER);
                            bid_proxy.setCanceledOnTouchOutside(false);
                    bid_proxy.setCanceledOnTouchOutside(false);

                    }
                    else
                    {
                        //Toast.makeText(mContext, "You do not have bidding access", Toast.LENGTH_SHORT).show();
                        bidAccessDialog("Astaguru","You don't have access to Bid Please contact Astaguru.");
                    }
                }
                else
                {
                    Intent intent = new Intent(mContext, Verification_Activity.class);
                    intent.putExtra("Activity", "Login");
                    mContext.startActivity(intent);

                }

            }
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


    private int Get_5_value(String amt)
    {

        double amount = Double.parseDouble(amt);

        double byerprimium = (amount / 100.0f) * 5;

        double sum = amount + byerprimium;

        int intbyerprimium = (int) sum;

        return intbyerprimium;
    }

//    private int Get_int_value(String amt)
//    {
//
//        double amount = Double.parseDouble(amt);
//
//        double byerprimium = (amount / 100.0f) * 5;
//
//        double sum = amount + byerprimium;
//
//        int intbyerprimium = (int) amt;
//
//        return intbyerprimium;
//    }

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
           // String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spBid("+str_Amount+","+str_productID+","+str_userID+","+dollerrate+","+proxy_new_us+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+")?api_key="+ Application_Constants.API_KEY;

            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spBid("+str_Amount+","+str_productID+","+str_userID+","+dollerrate+","+proxy_new_us+","+Thumbnail+","+Reference+","+OldPriceRs+","+OldPriceUs+","+Auctionid+","+Bidclosingtime+","+Ufirst_name+","+Ulastname+")?api_key="+ Application_Constants.API_KEY;
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

                                auction_gallary_activity.startRepeatingTask();
//                                Toast.makeText(mContext,currentStatus, Toast.LENGTH_SHORT).show();
                                if(currentStatus.equals("1"))
                                {

                                   // Toast.makeText(mContext, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
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

    private void GetData(String value, String str_productid, String rs_amount, String productid, String userid, String dollerrate, String proxy_new_us, String lot, final String strTitle) {

        if (utility.checkInternet()) {
            //String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/Acution?api_key="+ Application_Constants.API_KEY+"&filter=productid="+str_productid+"";
            String strPastAuctionUrl = Application_Constants.Main_URL+"Acution?api_key="+ Application_Constants.API_KEY+"&filter=productid="+str_productid+"";
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
                                   // BidNow(trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot);

                                    makeBid.BidNow(trs_amount,tproductid,tuserid,tdollerrate,tproxy_new_us,tlot,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,strTitle);
                                    makeBid.bidResult(new OnBidResult() {
                                        @Override
                                        public void bidResult(String currentStatus, String msg)
                                        {

                                            bid_now.dismiss();
                                            auction_gallary_activity.startRepeatingTask();
                                        }

                                    });
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
                                    final TextView iv_icon = (TextView) dialogView.findViewById(R.id.iv_icon);

                                    if (is_us)
                                    {
                                        int int_proxy_bid_rselse_us = Get_10_value(rupee_value);


                                        String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_us);
                                        tv_bidvalue.setText(str_int_us);
                                        iv_icon.setText("US$");
                                    }
                                    else
                                    {
                                        int int_proxy_bid_rselse_rs = Get_10_value(rupee_value);

                                        rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_rs);

                                        str_rs_amount = String.valueOf(int_proxy_bid_rselse_rs);

                                        tv_bidvalue.setText(rs_value);
                                        iv_icon.setText("₹");
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
                                                    GetData("US",f_pro_id,us_amount, productid, userid, dollerrate, proxy_new_us,f_lot,strTitle);
                                                } else {


                                                    GetData("RS",f_pro_id,rs_amount, productid, userid, dollerrate, proxy_new_us,f_lot,strTitle);
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

    private void ProxyBid(String userProxyAmount,String productID,String siteUserID,String dollerRate,String dollerAmt,String f_lot)
    {

        if (utility.checkInternet()) {
           // String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+")?api_key="+ Application_Constants.API_KEY;
            Bidclosingtime = Bidclosingtime.replace(" ","%20");

            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+","+Thumbnail+","+Reference+","+OldPriceRs+","+OldPriceUs+","+Auctionid+","+Bidclosingtime+","+Ufirst_name+","+Ulastname+")?api_key="+ Application_Constants.API_KEY;
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

            String addtogallaryUrl = "http://54.169.222.181/api/v2/guru/_proc/spAddToGallery("+productID+","+siteUserID+")?api_key="+ Application_Constants.API_KEY;
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



    private void Delete(String str_productid) {

        if (utility.checkInternet()) {
            pDialog  = new ProgressDialog(mContext);
            pDialog.setMessage("Deleting Item ... ");
            pDialog.setCancelable(false);
            pDialog.show();
            String strPastAuctionUrl = Application_Constants.Main_URL+"bidartistuser?api_key="+ Application_Constants.API_KEY+"&filter=bidartistuserid="+str_productid+"";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, strPastAuctionUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            pDialog.dismiss();
                            try
                            {

                                System.out.print("Response"+ response.toString());



                                Toast.makeText(mContext, "You Succesfully Delete Item", Toast.LENGTH_SHORT).show();



                                gallaryActivity.getMyAuctionGallery();


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



    public void bidAccessDialog(String strTitle, String strMessage)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
