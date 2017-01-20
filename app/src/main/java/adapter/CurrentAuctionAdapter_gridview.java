package adapter;


import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infomanav.astaguru.Artist_Details;
import com.infomanav.astaguru.Before_Login_Activity;
import com.infomanav.astaguru.Current_Auction_Model;
import com.infomanav.astaguru.FlipAnimationNew;
import com.infomanav.astaguru.FragmentCurrentAuction;
import com.infomanav.astaguru.LoginActivity;
import com.infomanav.astaguru.Lot_Detail_Page;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.ZoomActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;
import views.DisplayNextView;
import views.Flip3dAnimation;


import android.animation.Animator;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrentAuctionAdapter_gridview extends ArrayAdapter<Current_Auction_Model> {


    private LinearLayout linearLayout1, linearLayout2;
    private Handler handler;
    private Runnable runnable;
    LinearLayout lin_front, lin_back,lin_count;
     ImageView iv_oncce, expandedImageView, iv_zoom, iv_close,iv_addtogallary,iv_detail;
    boolean isBackVisible = false;
    public DisplayMetrics m;
    Context mContext;
    public TextView tv_lot,tvDay, tvHour, tvMinute, tvSecond, tvDayb, tvHourb, tvMinuteb, tvSecondb;
    private boolean isFirstImage = true;
    TextView tv_title,tv_countdown;
    String trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot,tvalue;
    Button btn_bidnow, btn_proxybid;
    public boolean is_us = false;
    List<Current_Auction_Model> objects_constructer;
    TextView iv_one, iv_two,iv_oneback,iv_twoback;
    String rs_value,usvalue;
    DecimalFormat formatter;
    SessionData data;
    EditText edt_proxy;
    AlertDialog bid_now,bid_proxy,dilog_alert;
    FragmentCurrentAuction currentAuction;
    String str_rs_amount,str_us_amount;
    String rupee_value;
    ProgressDialog pDialog;
    String value_for_cmpr;
    String f_doller,f_pro_id,f_lot,Thumbnail,Reference,OldPriceUs,OldPriceRs,Auctionid;
    List<Current_Auction_Model> is_front_object;


    Utility utility;
    public CurrentAuctionAdapter_gridview(Context context, int textViewResourceId,
                                          List<Current_Auction_Model> objects, final boolean is_us,
                                          FragmentCurrentAuction currentAuction)
    {
        super(context, textViewResourceId, objects);
        mContext = context;
        this.objects_constructer = objects;
        this.is_us = is_us;

         this.currentAuction = currentAuction;
        utility = new Utility(mContext);
        data = new SessionData(mContext);
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

    public void Upadte_GridView(List<Current_Auction_Model> objects)
    {


        if(objects_constructer!=null&&objects_constructer.size()>0)
        {
            for(int i =0;i<objects_constructer.size();i++)
            {
                Current_Auction_Model cp = this.objects_constructer.get(i);

                boolean is_ = cp.getIs_front();
                Current_Auction_Model cp1 = objects.get(i);

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
    public List<Current_Auction_Model> getCurrentIsFrontList()
    {
       return is_front_object;
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        View curView;
        if(convertView==null)
        {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.current_grid, null);
        }else{
            curView = convertView;
        }




       // final Current_Auction_Model cp = getItem(position);

        final Current_Auction_Model cp = this.objects_constructer.get(position);

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
        iv_addtogallary = (ImageView) curView.findViewById(R.id.iv_addtogallary);
        iv_detail = (ImageView) curView.findViewById(R.id.iv_detail);
        TextView tv_ac_artist = (TextView) curView.findViewById(R.id.tv_ac_artist);
        TextView tv_ac_category = (TextView) curView.findViewById(R.id.tv_ac_category);
        TextView tv_ac_medium = (TextView) curView.findViewById(R.id.tv_ac_medium);
        TextView tv_ac_year = (TextView) curView.findViewById(R.id.tv_ac_year);
        TextView tv_ac_size = (TextView) curView.findViewById(R.id.tv_ac_size);
        TextView tv_estimate = (TextView) curView.findViewById(R.id.tv_estimate);
        TextView tv_ac_bid = (TextView) curView.findViewById(R.id.tv_ac_bid);
        TextView tv_ac_nextbid = (TextView) curView.findViewById(R.id.tv_ac_nextbid);

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


        tv_ac_artist.setText(cp.getArtist_name());
        tv_ac_category.setText(cp.getStr_category());
        tv_ac_medium.setText(cp.getMedium());
        tv_ac_year.setText(cp.getProductdate());
        tv_ac_size.setText(cp.getProductsize());


        f_doller = cp.getDollarRate();
        f_pro_id = cp.getStr_productid();


        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            // Here Set your Event Date
            String str = cp.getStr_Bidclosingtime();

            //String[] splitStr = str.split("\\s+");

            Date eventDate = dateFormat.parse(str);

            String strcurrentDate =cp.getCurrentDate();
            Date currentDate = dateFormat.parse(strcurrentDate);

            boolean is_after = currentDate.before(eventDate);

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


            }
            else
            {



                handler.removeCallbacks(runnable);
                lin_count.setVisibility(View.GONE);
                tv_countdown.setVisibility(View.VISIBLE);


                tvDay.setText("" + String.format("%02d","0"));
                tvHour.setText("" + String.format("%02d", "0"));
                tvMinute.setText("" + String.format("%02d", "0"));
                tvSecond.setText("" + String.format("%02d", "0"));

                tvDayb.setText("" + String.format("%02d","0"));
                tvHourb.setText("" + String.format("%02d", "0"));
                tvMinuteb.setText("" + String.format("%02d", "0"));
                tvSecondb.setText("" + String.format("%02d", "0"));

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




        String user = data.getObjectAsString("login");
        String userid = data.getObjectAsString("userid");

                if(user.equals("true"))
                {
                    if(userid.equals(cp.getMyUserID()))
                    {
                        tv_lot.setBackgroundResource(R.drawable.green_btn);
                        tv_lot_back.setBackgroundResource(R.drawable.green_btn);

                    }
                    else
                    {
                        tv_lot.setBackgroundResource(R.drawable.rounded_rectangle);
                        tv_lot_back.setBackgroundResource(R.drawable.rounded_rectangle);

                    }

                }
               else
                {
                  tv_lot.setBackgroundResource(R.drawable.rounded_rectangle);
                    tv_lot_back.setBackgroundResource(R.drawable.rounded_rectangle);

                }

        tv_lot.setText("Lot " + cp.getReference());
        tv_lot_back.setText("Lot " + cp.getReference());
        iv_close = (ImageView) curView.findViewById(R.id.iv_close);

        btn_bidnow = (Button) curView.findViewById(R.id.btn_bidnow);
        btn_proxybid = (Button) curView.findViewById(R.id.btn_proxybid);

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

            int next_bid_doller=0;
            String str_us = cp.getPriceus();

            int int_str = Integer.parseInt(str_us);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

            tv_estimate.setText(cp.getEstamiate());
            tv_current_bid.setText(str_ustest);
            tv_ac_bid.setText(str_ustest);


            int myNum = Integer.parseInt(cp.getPricers());
            if (myNum<10000000)
            {
                next_bid_doller = Get_10_value(cp.getPriceus());
            }
            else
            {
                next_bid_doller = Get_5_value(cp.getPriceus());
            }

            tv_nextbid.setText(next_bid_doller);
            tv_ac_nextbid.setText(next_bid_doller);

            iv_one.setText("US$");
            iv_two.setText("US$");
            iv_oneback.setText("US$");
            iv_twoback.setText("US$");
        } else {
            int next_bid=0;
            String str_rs= cp.getPricers();
            int int_strrs = Integer.parseInt(str_rs);
            String str_rscomma= NumberFormat.getNumberInstance(Locale.US).format(int_strrs);

            tv_current_bid.setText(str_rscomma);

            tv_ac_bid.setText(str_rscomma);
            tv_estimate.setText(cp.getStr_collectors());


            int myNum = Integer.parseInt(cp.getPricers());

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


            boolean  is = cp.getIs_front();
            if (cp.getIs_front())
            {
                lin_front.setVisibility(View.VISIBLE);
                lin_back.setVisibility(View.GONE);
/*
                final Animation zoomout1 = AnimationUtils.loadAnimation(mContext, R.anim.zoom_in1);
                *//*final Animation zoomoutOut = AnimationUtils.loadAnimation(mContext, R.anim.zoom_out);

                lin_back.setVisibility(View.VISIBLE);
                lin_front.startAnimation(zoomout1);*//*

                lin_front.setVisibility(View.VISIBLE);
                lin_front.startAnimation(zoomout1);*/

                //applyRotation(0, 90,lin_front,lin_back,true);

               /* ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext, R.animator.card_flip_left_in);
                anim.setTarget(lin_front);
                anim.setDuration(200);
                anim.start();*/


            }
            else
            {
               // lin_front.setVisibility(View.GONE);


                if(lin_back.getVisibility()==View.GONE)
                {
                    lin_front.setVisibility(View.GONE);
                    lin_back.setVisibility(View.VISIBLE);


                    ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext, R.animator.card_flip_left_in);
                    anim.setTarget(lin_back);
                    anim.setDuration(200);
                    anim.start();
                }


               /* FlipAnimationNew flipAnimation = new FlipAnimationNew(lin_front, lin_back);

                if (lin_front.getVisibility() == View.VISIBLE)
                {
                    flipAnimation.reverse();
                }
                curView.startAnimation(flipAnimation);*/

                //applyRotation(0, -90,lin_front,lin_back,false);

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
                String Str_id = cp.getStr_thumbnail();
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", Str_id);
                mContext.startActivity(intent);

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
                mContext.startActivity(intent);



            }
        });

        iv_addtogallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_productid();
                String  userid = data.getObjectAsString("userid");
                AddToGallary(Str_id,userid);


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
            public void onClick(View v)
            {
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
                else
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

                    currentAuction.stopRepeatingTask();
                    String img_name = cp.getStr_thumbnail();
                    Thumbnail = img_name.replace("paintings/","");
                    Reference=cp.getReference();
                    OldPriceRs=cp.getPriceus();
                    OldPriceUs=cp.getPricers();
                    Auctionid=cp.getReference();

                if (is_us)
                {
                    int int_bid_us =0;


                    int myNum = Integer.parseInt(cp.getPricers());
                    if (myNum<10000000)
                    {
                        int_bid_us = Get_10_value(cp.getPriceus());
                    }
                    else
                    {
                        int_bid_us = Get_5_value(cp.getPriceus());
                    }

                    str_us_amount = String.valueOf(int_bid_us);
                    str_rs_amount = String.valueOf(int_bid_us);
                    String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_bid_us);

                    tv_bidvalue.setText(str_int_us);
                    tv_bidlot.setText("Lot " + cp.getReference());
                    iv_icon.setText("US$");
                }
                else
                {
                    int int_bid_rs =0;


                    int myNum = Integer.parseInt(cp.getPricers());
                    if (myNum<10000000)
                    {
                        int_bid_rs = Get_10_value(cp.getPricers());
                    }
                    else
                    {
                        int_bid_rs = Get_5_value(cp.getPricers());
                    }

                    str_rs_amount = String.valueOf(int_bid_rs);
                    str_us_amount = String.valueOf(int_bid_rs);
                    rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_bid_rs);

                    tv_bidvalue.setText(rs_value);
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



        btn_proxybid.setOnClickListener(new View.OnClickListener() {
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



                if (is_us)
                {
                    int int_proxy_bid_us =0;


                    int myNum = Integer.parseInt(cp.getPricers());
                    if (myNum<10000000)
                    {
                        int_proxy_bid_us = Get_10_value(cp.getPriceus());
                    }
                    else
                    {
                        int_proxy_bid_us = Get_5_value(cp.getPriceus());
                    }
                    value_for_cmpr = String.valueOf(int_proxy_bid_us);
                    String str_int_xus= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_us);

                    tv_bidvalue.setText(str_int_xus);
                    tv_proxylot.setText("Lot " + cp.getReference());
                    iv_iconproxy.setText("US$");
                }
                else
                {

                    int int_proxy_bid_rs =0;

                    int myNum = Integer.parseInt(cp.getPricers());
                    if (myNum<10000000)
                    {
                        int_proxy_bid_rs = Get_10_value(cp.getPriceus());
                    }
                    else
                    {
                        int_proxy_bid_rs = Get_5_value(cp.getPriceus());
                    }

                    value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                    rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);

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
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spBid("+str_Amount+","+str_productID+","+str_userID+","+dollerrate+","+proxy_new_us+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
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

                                currentAuction.startRepeatingTask();
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

    private void ProxyBid(String userProxyAmount,String productID,String siteUserID,String dollerRate,String dollerAmt,String f_lot)
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
        else
        {
            Toast.makeText(mContext, "Please Check Internet Connection.",Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void applyRotation(float start, float end, LinearLayout lin_front, LinearLayout lin_back, boolean isFirstImage)
    {
// Find the center of image
        final float centerX = lin_front.getWidth() / 2.0f;
        final float centerY = lin_back.getHeight() / 2.0f;

// Create a new 3D rotation with the supplied parameter
// The animation listener is used to trigger the next animation
        final Flip3dAnimation rotation =
                new Flip3dAnimation(start, end, centerX, centerY);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(isFirstImage, lin_front, lin_back));


       /* if (isFirstImage)
        {
            lin_front.startAnimation(rotation);

        } else {
            lin_back.startAnimation(rotation);

        }*/










//
//        if (isFirstImage)
//        {
//            lin_front.startAnimation(rotation);
//        } else {
//            lin_back.startAnimation(rotation);
//        }

    }



    private void flipGridViewItem(final View v, Current_Auction_Model item){

        v.clearAnimation();

        if(item.getFace() == Current_Auction_Model.ItemFace.FRONT){

            // Set the item to back
            item.setFace(Current_Auction_Model.ItemFace.BACK);

            final Animator in = AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_left_in);
            in.setTarget(v);


            Animator.AnimatorListener flipOutListener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                  lin_front.setVisibility(View.GONE);
                   lin_back.setVisibility(View.VISIBLE);
                    in.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };

            Animator out = AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_left_out);
            out.setTarget(v);
            out.addListener(flipOutListener);
            out.start();
        }
        else{

            // Set the item to front
            item.setFace(Current_Auction_Model.ItemFace.FRONT);

            final Animator in = AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_right_in);
            in.setTarget(v);


            // A listener is required to determine the end fo the first animation
            Animator.AnimatorListener flipOutListener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    lin_front.setVisibility(View.VISIBLE);
                    lin_back.setVisibility(View.GONE);
                    in.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };

            Animator out = AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_right_out);
            out.setTarget(v);
            out.addListener(flipOutListener);
            out.start();

        }
    }

    public void countDownStart(String datestring,TextView tvDay,TextView tvHour,TextView tvMinute,TextView tvSecond)
    {
        handler = new Handler();

        final String strdate = datestring;
        final TextView tvday = tvDay;
        final TextView tvhour = tvHour;
        final TextView tvminite = tvMinute;
        final TextView tvsecounds = tvSecond;
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                handler.postDelayed(this, 1000);
                try
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    // Here Set your Event Date
                    String str = strdate;
                    String[] splitStr = str.split("\\s+");

                    Date eventDate = dateFormat.parse(splitStr[0]);
                    Date currentDate = new Date();

                    if (!currentDate.after(eventDate))
                    {
                        long diff = eventDate.getTime()- currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;

                        tvday.setText("" + String.format("%02d", days));
                        tvhour.setText("" + String.format("%02d", hours));
                        tvminite.setText("" + String.format("%02d", minutes));
                        tvsecounds.setText("" + String.format("%02d", seconds));
                    }
                    else
                    {
//                        linearLayout1.setVisibility(View.VISIBLE);
//                        linearLayout2.setVisibility(View.GONE);
//                        tvEvent.setText("Android Event Start");
                        handler.removeCallbacks(runnable);
                        // handler.removeMessages(0);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }



}
