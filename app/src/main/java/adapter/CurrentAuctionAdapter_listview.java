package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import interfaces.OnBidResult;
import interfaces.OnCurrentAuctionItemClick;
import model_classes.Current_Auction_Model;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infomanav.astaguru.Artist_Details;
import com.infomanav.astaguru.Before_Login_Activity;
import com.infomanav.astaguru.Bid_History;
import com.infomanav.astaguru.FragmentCurrentAuction;
import com.infomanav.astaguru.Lot_Detail_Page;
import com.infomanav.astaguru.MainActivity;
import com.infomanav.astaguru.MakeBid;
import com.infomanav.astaguru.OnSwipeTouchListener;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.ShowFullZoomImage;
import com.infomanav.astaguru.Verification_Activity;
import com.infomanav.astaguru.ZoomActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 04-10-2016.
 */

public class CurrentAuctionAdapter_listview extends ArrayAdapter<Current_Auction_Model> {
    private TextView tvDay, tvHour, tvMinute, tvSecond;
    Context mContext;
    private Handler handler;
    private Runnable runnable;
    public boolean is_us=false,is_after;
    TextView iv_one,iv_two,tv_countdown;
    ImageView iv_detailslist,iv_closelist,iv_listzoom;
    List<Current_Auction_Model> objects_constructer;
    LinearLayout lin_front, lin_back,lin_countdown,lay_back,lay_front,lay_bid_now,
            lay_bid_proxy,lay_lot_detail,lay_bid_history;
    Utility utility;
    ImageView iv_addtogallary;
    SessionData data;
    Activity activity;
    int height=0,width=0;
    DisplayMetrics displaymetrics;
    String f_doller,f_pro_id,f_lot,Thumbnail,Reference,OldPriceUs,OldPriceRs,Auctionid,Ufirst_name,Ulastname,
            Bidclosingtime,str_rs_amount,str_us_amount,rs_value,usvalue,trs_amount, tproductid, tuserid, tdollerrate,
            tproxy_new_us,tlot,tvalue,rupee_value,value_for_cmpr,userid,strTitle="";
    private AlertDialog bid_now,bid_proxy,dilog_alert,dilog_bid_access;
    EditText edt_proxy;
    private OnCurrentAuctionItemClick onCurrentItenClick;
    private MakeBid makeBid;
    private FragmentCurrentAuction fragmentCurrentAuction;
    private ShowFullZoomImage showFullZoomImage;
    public CurrentAuctionAdapter_listview(Context context, int textViewResourceId, List<Current_Auction_Model> objects,final boolean is_us) {
        super(context, textViewResourceId, objects);
        mContext = context;
        this.is_us=is_us;
        this.objects_constructer = objects;
        utility = new Utility(mContext);
        data = new SessionData(mContext);
        activity = (Activity) mContext;
        displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        makeBid = new MakeBid(mContext);
        showFullZoomImage = new ShowFullZoomImage(mContext);


    }
    public CurrentAuctionAdapter_listview(Context context, int textViewResourceId, List<Current_Auction_Model> objects, final boolean is_us, FragmentCurrentAuction fragmentCurrentAuction)
    {
        super(context, textViewResourceId, objects);
        mContext = context;
        this.is_us=is_us;
        this.objects_constructer = objects;
        utility = new Utility(mContext);
        data = new SessionData(mContext);
        activity = (Activity) mContext;
        displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        makeBid = new MakeBid(mContext);
        this.fragmentCurrentAuction = fragmentCurrentAuction;


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
//    public void changeCurrency()
//    {
//        if(is_us)
//        {
//            is_us = false;
//        }
//        else
//        {
//            is_us=true;
//        }
//        notifyDataSetChanged();
//    }

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

    public void Upadte_ListViewWithFilter(List<Current_Auction_Model> objects, boolean is_filter)
    {
        try {

            if(is_filter)
            {
                if(objects.size()==objects_constructer.size())
                {
                    for(int i=0; i<objects.size();i++)
                    {
                        objects.get(i).setIs_front(objects_constructer.get(i).getIs_front());
                        objects.get(i).setIs_slide(objects_constructer.get(i).is_slide());
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



                    for(int i=0; i<objects.size();i++)
                    {
                       // objects.get(i).setIs_front(objects_constructer.get(i).getIs_front());
                        objects.get(i).setIs_slide(objects_constructer.get(i).is_slide());
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


    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getCount() {
        return this.objects_constructer.size();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View curView = convertView;

        if (curView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.current_listview, null);
        }

        final Current_Auction_Model cp = this.objects_constructer.get(position);


        ImageView  expandedImageView = (ImageView) curView.findViewById(R.id.grid_imagelistfront);

        iv_one = (TextView) curView.findViewById(R.id.iv_one);
        iv_two = (TextView) curView.findViewById(R.id.iv_two);
        iv_addtogallary = (ImageView) curView.findViewById(R.id.iv_addtogallary);
        iv_listzoom = (ImageView) curView.findViewById(R.id.iv_listzoom);
        TextView tv_date = (TextView) curView.findViewById(R.id.tv_date);
        TextView tv_title = (TextView) curView.findViewById(R.id.tv_title);
        TextView grid_title = (TextView) curView.findViewById(R.id.grid_title);
        CardView card_main = (CardView) curView.findViewById(R.id.card_main);
        iv_detailslist = (ImageView)curView.findViewById(R.id.iv_detailslist);
        iv_closelist= (ImageView)curView.findViewById(R.id.iv_closelist);
        TextView tv_current_bid = (TextView) curView.findViewById(R.id.tv_current_bid);
        TextView tv_nextbid = (TextView) curView.findViewById(R.id.tv_nextbid);
        lin_front = (LinearLayout) curView.findViewById(R.id.lin_front);
        lin_back = (LinearLayout) curView.findViewById(R.id.lin_back);
        lay_front = (LinearLayout) curView.findViewById(R.id.lay_front);
        lay_back = (LinearLayout) curView.findViewById(R.id.lay_back);
        lin_countdown = (LinearLayout) curView.findViewById(R.id.lin_countdown);
        TextView tv_lot = (TextView) curView.findViewById(R.id.tv_lotlist);
        TextView tv_lot_back = (TextView) curView.findViewById(R.id.tv_lot_backlist);
        tv_lot.setText("Lot:" + cp.getReference());
        tv_lot_back.setText("Lot:" + cp.getReference());

        TextView tv_ac_artist = (TextView) curView.findViewById(R.id.tv_ac_artistlist);
        TextView tv_ac_category = (TextView) curView.findViewById(R.id.tv_ac_categorylist);
        TextView tv_ac_medium = (TextView) curView.findViewById(R.id.tv_ac_mediumlist);
        TextView tv_ac_year = (TextView) curView.findViewById(R.id.tv_ac_yearlist);
        TextView tv_ac_size = (TextView) curView.findViewById(R.id.tv_ac_sizelist);
        TextView tv_estimate = (TextView) curView.findViewById(R.id.tv_estimatelist);


        tvDay = (TextView) curView.findViewById(R.id.txtTimerDaylist);
        tvHour = (TextView) curView.findViewById(R.id.txtTimerHourlist);
        tvMinute = (TextView) curView.findViewById(R.id.txtTimerMinutelist);
        tvSecond = (TextView) curView.findViewById(R.id.txtTimerSecondlist);
        tv_countdown = (TextView) curView.findViewById(R.id.tv_countdown);

        lay_bid_now = (LinearLayout) curView.findViewById(R.id.lay_bid_now);
        lay_bid_proxy= (LinearLayout) curView.findViewById(R.id.lay_bid_proxy);
        lay_lot_detail= (LinearLayout) curView.findViewById(R.id.lay_lot_detail);
        lay_bid_history= (LinearLayout) curView.findViewById(R.id.lay_bid_history);

        tv_ac_artist.setText(cp.getArtist_name());
        tv_ac_category.setText(cp.getStr_category());
        tv_ac_medium.setText(cp.getMedium());
        tv_ac_year.setText(cp.getProductdate());
        tv_ac_size.setText(cp.getProductsize()+" in");


        tv_title.setText(cp.getArtist_name());
        grid_title.setText(cp.getStr_title());



        String user = data.getObjectAsString("login");
        userid = data.getObjectAsString("userid");

        if(user.equals("true"))
        {
            if(userid.equals(cp.getMyUserID()))
            {
                tv_lot.setBackgroundResource(R.drawable.green_btn);
                tv_lot_back.setBackgroundResource(R.drawable.green_btn);

                lay_bid_proxy.setVisibility(View.GONE);
                lay_bid_history.setVisibility(View.GONE);
                lay_bid_now.setVisibility(View.GONE);


            }
            else
            {
                tv_lot.setBackgroundResource(R.drawable.rounded_rectangle);
                tv_lot_back.setBackgroundResource(R.drawable.rounded_rectangle);
                lay_bid_proxy.setVisibility(View.VISIBLE);
                lay_bid_history.setVisibility(View.VISIBLE);
                lay_bid_now.setVisibility(View.VISIBLE);

            }

        }
        else
        {
            tv_lot.setBackgroundResource(R.drawable.rounded_rectangle);
            tv_lot_back.setBackgroundResource(R.drawable.rounded_rectangle);
            lay_bid_proxy.setVisibility(View.VISIBLE);
            lay_bid_history.setVisibility(View.VISIBLE);
            lay_bid_now.setVisibility(View.VISIBLE);


        }


//        handler = new Handler();
//
//
//        runnable = new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                handler.postDelayed(this, 1000);
//                try
//                {
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    // Here Set your Event Date
//                    String str = "2017-02-24 12:25:00";
//
//                    String[] splitStr = str.split("\\s+");
//
//                    Date eventDate = dateFormat.parse(splitStr[0]);
//
//                    Date currentDate = new Date();
//
//
//                    if (!currentDate.after(eventDate))
//                    {
//                        long diff = eventDate.getTime()- currentDate.getTime();
//                        long days = diff / (24 * 60 * 60 * 1000);
//                        diff -= days * (24 * 60 * 60 * 1000);
//                        long hours = diff / (60 * 60 * 1000);
//                        diff -= hours * (60 * 60 * 1000);
//                        long minutes = diff / (60 * 1000);
//                        diff -= minutes * (60 * 1000);
//                        long seconds = diff / 1000;
//
//                        tvDay.setText("" + String.format("%02d",days));
//                        tvHour.setText("" + String.format("%02d", hours));
//                        tvMinute.setText("" + String.format("%02d", minutes));
//                        tvSecond.setText("" + String.format("%02d", seconds));
//
//
//
//
//                    }
//                    else
//                    {
////                        linearLayout1.setVisibility(View.VISIBLE);
////                        linearLayout2.setVisibility(View.GONE);
////                        tvEvent.setText("Android Event Start");
//                        handler.removeCallbacks(runnable);
//                        // handler.removeMessages(0);
//                    }
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        };
//        handler.postDelayed(runnable, 0);

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



        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            // Here Set your Event Date
            String str = cp.getStr_Bidclosingtime();

            //String[] splitStr = str.split("\\s+");

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

            }
            else
            {
                lin_countdown.setVisibility(View.GONE);
                tv_countdown.setVisibility(View.VISIBLE);
                tvDay.setText("" + String.format("%02d","0"));
                tvHour.setText("" + String.format("%02d", "0"));
                tvMinute.setText("" + String.format("%02d", "0"));
                tvSecond.setText("" + String.format("%02d", "0"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        iv_addtogallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_productid();
                String  userid = data.getObjectAsString("userid");
                AddToGallary(Str_id,userid);
            }
        });



        if (cp.getIs_front())
        {
           /* lin_front.setVisibility(View.VISIBLE);
            lin_back.setVisibility(View.GONE);*/

            lay_front.setVisibility(View.VISIBLE);
            lay_back.setVisibility(View.GONE);

        }
        else
            {
          /*  lin_front.setVisibility(View.GONE);
            lin_back.setVisibility(View.VISIBLE);*/

                lay_front.setVisibility(View.GONE);
                lay_back.setVisibility(View.VISIBLE);
        }


        iv_closelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cp.setIs_front(true);
                notifyDataSetChanged();

            }
        });


        iv_listzoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* String Str_thumbanial= cp.getStr_image();
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", Str_thumbanial);
                mContext.startActivity(intent);*/

                showFullZoomImage.showImage(cp.getStr_image());
            }
        });

        iv_detailslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cp.setIs_front(false);
                notifyDataSetChanged();
            }
        });
        tv_ac_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Str_id = cp.getStr_artistid();
                String Str_artistname = cp.getArtist_name();
                Intent intent = new Intent(mContext, Artist_Details.class);
                intent.putExtra("Str_id", Str_id);
                intent.putExtra("Str_artistname", Str_artistname);
                intent.putExtra("Picture", cp.getPicture());
                intent.putExtra("Profile", cp.getProfile());
                intent.putExtra("is_us", is_us);

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
                intent.putExtra("Picture", cp.getPicture());
                intent.putExtra("Profile", cp.getProfile());
                intent.putExtra("is_us", is_us);
                mContext.startActivity(intent);
            }
        });


        if(is_us)
        {
            double next_bid_doller=0;
            String str_us = cp.getPriceus();
            double int_str = Double.parseDouble(str_us);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

            str_ustest = mContext.getString(R.string.doller)+" "+str_ustest;
            tv_current_bid.setText(str_ustest);
            tv_estimate.setText(cp.getEstamiate());
            double myNum = Double.parseDouble(cp.getPricers());
            if (myNum<10000000)
            {
                next_bid_doller = MakeBid.Get_10_value(cp.getPriceus());
            }
            else
            {
                next_bid_doller = MakeBid.Get_5_value(cp.getPriceus());
            }

            String valuenext_bid_doller= NumberFormat.getNumberInstance(Locale.US).format(next_bid_doller);
//            String valuebyerprimium1 = String.valueOf(intbyerprimium1);

            valuenext_bid_doller = mContext.getString(R.string.doller)+" "+valuenext_bid_doller;
            tv_nextbid.setText(valuenext_bid_doller);

            iv_one.setVisibility(View.GONE);
            iv_two.setVisibility(View.GONE);
           /* iv_one.setText("US$");
            iv_two.setText("US$");*/
        }
        else
        {

            double next_bid=0;
            String str_rs= cp.getPricers();
            double int_strrs = Double.parseDouble(str_rs);
            String str_rscomma= NumberFormat.getNumberInstance(Locale.US).format(int_strrs);

            String str_amt_rs = rupeeFormat(str_rscomma);
            str_amt_rs = mContext.getString(R.string.rupees)+" "+str_amt_rs;
            tv_current_bid.setText(str_amt_rs);
            tv_estimate.setText(cp.getStr_collectors());


            double myNum = Double.parseDouble(cp.getPricers());

            if (myNum<10000000)
            {
                next_bid = MakeBid.Get_10_value(cp.getPricers());
            }
            else
            {
                next_bid = MakeBid.Get_5_value(cp.getPricers());
            }
            String valuebyerprimium= NumberFormat.getNumberInstance(Locale.US).format(next_bid);
            String str_amt_rss = rupeeFormat(valuebyerprimium);
            str_amt_rss = mContext.getString(R.string.rupees)+" "+str_amt_rss;
            tv_nextbid.setText(str_amt_rss);
          /*  iv_one.setText("₹");
            iv_two.setText("₹");*/

            iv_one.setVisibility(View.GONE);
            iv_two.setVisibility(View.GONE);
        }

           /* String strProductImagePath = Application_Constants.APP_IMAGE_PATH;
            String strProductImage = apps.getAppLogo();*/


        // imageView.setImageResource(apps.getAppLogo());
       

        Picasso.with(getContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getStr_thumbnail())
                .into(expandedImageView);

        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id =cp.getStr_productid();
                String reference = cp.getReference();
                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
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
                intent.putExtra("category",cp.getStr_category());
                intent.putExtra("is_us",is_us);
                intent.putExtra("dollar_rate",cp.getDollarRate());
                mContext.startActivity(intent);

            }
        });

        int int_slide = width;

        if(cp.is_slide())
        {
            lin_front.animate().translationX((-width/2)-200);
        }
        else
        {
            lin_front.animate().translationX(0);
        }


        lin_front.setOnTouchListener(new OnSwipeTouchListener(mContext)
        {
           /* public void onSwipeTop()
            {
               // Toast.makeText(mContext, "top", Toast.LENGTH_SHORT).show();

            }*/
            public void onSwipeRight() {
                //Toast.makeText(mContext, "right", Toast.LENGTH_SHORT).show();
                cp.setIs_slide(false);
                notifyDataSetChanged();
            }
            public void onSwipeLeft() {
               // Toast.makeText(mContext, "left", Toast.LENGTH_SHORT).show();

               /* Animation RightSwipe = AnimationUtils.loadAnimation(context, R.anim.slide_left);
                holder.lay_front.startAnimation(RightSwipe);*/

                //holder.lay_front.animate().translationX(-300);
                cp.setIs_slide(true);
                notifyDataSetChanged();

                //holder.lay_front.setVisibility(View.GONE);
                // holder.lay_back.setVisibility(View.VISIBLE);
            }
           /* public void onSwipeBottom() {
               // Toast.makeText(mContext, "bottom", Toast.LENGTH_SHORT).show();
            }*/

        });

        lay_bid_now.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                // Here Set your Event Date
                String str = cp.getStr_Bidclosingtime();

                //String[] splitStr = str.split("\\s+");
                Date eventDate = null,currentDate=null;
                try
                {
                    eventDate = dateFormat.parse(str);
                    String strcurrentDate =cp.getCurrentDate();
                    currentDate = dateFormat.parse(strcurrentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
               boolean is_after = currentDate.before(eventDate);
                if(is_after)
                {
                    String status = data.getObjectAsString("login");
                    if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                    {
                        Intent intent = new Intent(mContext,Before_Login_Activity.class);
                        intent.putExtra("str_from","adpter");
                        mContext.startActivity(intent);

                    }
                    else
                    {

                        String MobileVerified = data.getObjectAsString("MobileVerified");
                        String EmailVerified = data.getObjectAsString("EmailVerified");
                        String confirmbid = data.getObjectAsString("confirmbid");
                        if (MobileVerified.equalsIgnoreCase("true") && EmailVerified.equalsIgnoreCase("true")) {
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

                                // currentAuction.stopRepeatingTask();
                                String img_name = cp.getStr_thumbnail();
                                Thumbnail = img_name.replace("paintings/", "");
                                Reference = cp.getReference();
                                OldPriceRs = cp.getPricers();
                                OldPriceUs = cp.getPriceus();
                                Auctionid = cp.getOnline();
                                Ufirst_name = cp.getStr_FirstName();
                                Ulastname = cp.getStr_LastName();
                                Bidclosingtime = cp.getStr_Bidclosingtime();
                                strTitle   = cp.getStr_title();
                                if (is_us) {
                                    int int_bid_us = 0;


                                    int myNum = Integer.parseInt(cp.getPricers());
                                    if (myNum < 10000000) {
                                        int_bid_us = MakeBid.Get_10_value(cp.getPriceus());
                                    } else {
                                        int_bid_us = MakeBid.Get_5_value(cp.getPriceus());
                                    }

                                    str_us_amount = String.valueOf(int_bid_us);
                                    str_rs_amount = String.valueOf(int_bid_us);
                                    String str_int_us = NumberFormat.getNumberInstance(Locale.US).format(int_bid_us);

                                    tv_bidvalue.setText("US$ "+str_int_us);
                                    tv_bidlot.setText("Lot " + cp.getReference().trim());
                                    iv_icon.setText("US$");
                                } else {
                                    int int_bid_rs = 0;


                                    int myNum = Integer.parseInt(cp.getPricers());
                                    if (myNum < 10000000) {
                                        int_bid_rs = MakeBid.Get_10_value(cp.getPricers());
                                    } else {
                                        int_bid_rs = MakeBid.Get_5_value(cp.getPricers());
                                    }

                                    str_rs_amount = String.valueOf(int_bid_rs);
                                    str_us_amount = String.valueOf(int_bid_rs);
                                    rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_bid_rs);
                                    String str_amt_rsbid = rupeeFormat(String.valueOf(rs_value));
                                    tv_bidvalue.setText("₹ "+str_amt_rsbid);
                                    tv_bidlot.setText("Lot " + cp.getReference().trim());
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
                                            if (utility.checkInternet())
                                            {
                                                String Str_productid = cp.getStr_productid();


                                                double a = Double.parseDouble(us_amount);
                                                double b = Double.parseDouble(dollerrate);

                                                double str_rsonus = a * b;

                                                //String proxy_new_us = Double.toString(str_rsonus);

                                                int int_proxy_new = (int) Math.round(str_rsonus);
                                                String proxy_new_us = Integer.toString(int_proxy_new);

                                                //GetData("US", Str_productid, proxy_new_us, productid, userid, dollerrate, us_amount, f_lot,strTitle);

                                                makeBid.GetData("US",Str_productid, proxy_new_us, productid, userid, dollerrate, us_amount, f_lot,strTitle,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,is_us);
                                                makeBid.bidResult(new OnBidResult() {
                                                    @Override
                                                    public void bidResult(String currentStatus, String msg)
                                                    {
                                                        // Toast.makeText(mContext,currentStatus,Toast.LENGTH_SHORT).show();

                                                        bid_now.dismiss();
                                                        fragmentCurrentAuction.startRepeatingTask();
                                                    }

                                                });


                                            }
                                            else
                                            {

                                                show_dailog("Please Check Internet Connection");
                                            }

                                        } else {


                                            if (utility.checkInternet()) {
                                                String Str_productid = cp.getStr_productid();

                                                //GetData("RS", Str_productid, us_amount, productid, userid, dollerrate, str_us, f_lot,strTitle);

                                                makeBid.GetData("RS", Str_productid, us_amount, productid, userid, dollerrate, str_us, f_lot,strTitle,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,is_us);
                                                makeBid.bidResult(new OnBidResult() {
                                                    @Override
                                                    public void bidResult(String currentStatus, String msg)
                                                    {
                                                        // Toast.makeText(mContext,currentStatus,Toast.LENGTH_SHORT).show();

                                                        bid_now.dismiss();
                                                        fragmentCurrentAuction.startRepeatingTask();
                                                    }

                                                });

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
                                //show_dailog("You don't have access to Bid Please contact Astaguru.");
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
                else
                {
                    show_dailog("This auction is closed");
                }




            }
        });
        lay_bid_proxy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                // Here Set your Event Date
                String str = cp.getStr_Bidclosingtime();

                //String[] splitStr = str.split("\\s+");
                Date eventDate = null,currentDate=null;
                try
                {
                    eventDate = dateFormat.parse(str);
                    String strcurrentDate =cp.getCurrentDate();
                    currentDate = dateFormat.parse(strcurrentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean is_after = currentDate.before(eventDate);

                if(is_after)
                {
                    String status = data.getObjectAsString("login");
                    if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                    {
                        Intent intent = new Intent(mContext,Before_Login_Activity.class);
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
                                iv_iconproxy.setVisibility(View.GONE);

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
                                    int int_proxy_bid_us = 0;


                                    int myNum = Integer.parseInt(cp.getPricers());
                                    if (myNum < 10000000)
                                    {
                                        int_proxy_bid_us = MakeBid.Get_10_value(cp.getPriceus());
                                    }
                                    else
                                    {
                                        int_proxy_bid_us = MakeBid.Get_5_value(cp.getPriceus());
                                    }
                                    value_for_cmpr = String.valueOf(int_proxy_bid_us);
                                    String str_int_xus = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_us);

                                    tv_bidvalue.setText("US$ "+str_int_xus);
                                    tv_proxylot.setText("Lot " + cp.getReference().trim());
                                    iv_iconproxy.setText("US$");
                                }
                                else
                                {

                                    int int_proxy_bid_rs = 0;

                                    int myNum = Integer.parseInt(cp.getPricers());
                                    if (myNum < 10000000)
                                    {
                                        int_proxy_bid_rs = MakeBid.Get_10_value(cp.getPricers());
                                    }
                                    else
                                    {
                                        int_proxy_bid_rs = MakeBid.Get_5_value(cp.getPricers());
                                    }

                                    value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                                    rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);

                                    str_rs_amount = String.valueOf(int_proxy_bid_rs);
                                    tv_proxylot.setText("Lot " + cp.getReference().trim());
                                    tv_bidvalue.setText("₹ "+rs_value);
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
                                        else {

                                            double fb = Double.parseDouble(dollerRate);
                                            double rl = Double.parseDouble(str_ProxyAmt);

                                            double str_ProxyAmtus_new = rl / fb;

                                            int int_proxy_new = (int) Math.round(str_ProxyAmtus_new);
                                            String proxy_amt_us = Integer.toString(int_proxy_new);

                                            //String proxy_amt_us = Double.toString(str_ProxyAmtus_new);


                                            if (str_ProxyAmt.isEmpty())
                                            {
                                                Toast.makeText(mContext, "Pls Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {

                                                String entered_value = edt_proxy.getText().toString();
                                                String bid_value = value_for_cmpr;
                                                double int_entered_value = Double.parseDouble(entered_value);
                                                double int_bid_value = Double.parseDouble(bid_value);

                                                if (int_entered_value >= int_bid_value)
                                                {
                                                    if (is_us)
                                                    {

                                                        String str_Proxy_for_us = edt_proxy.getText().toString();

                                                        double fb1 = Double.parseDouble(dollerRate);
                                                        double rl1 = Double.parseDouble(str_Proxy_for_us);

                                                        double str_ProxyAmtrs = rl1 * fb1;

                                                        int_proxy_new = (int) Math.round(str_ProxyAmtrs);
                                                        String proxy_amt_for_rs = String.valueOf(int_proxy_new);

                                                        //String proxy_amt_for_rs = Double.toString(str_ProxyAmtrs);
//                                            Toast.makeText(mContext, "from US", Toast.LENGTH_SHORT).show();


                                                        if (utility.checkInternet())
                                                        {
                                                            //ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_for_rs, f_lot);

                                                            makeBid.ProxyBid(proxy_amt_for_rs, productID, siteUserID, dollerRate, str_ProxyAmt, f_lot,Bidclosingtime,
                                                                    Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,strTitle);

                                                            makeBid.bidResult(new OnBidResult() {
                                                                @Override
                                                                public void bidResult(String currentStatus, String msg) {
                                                                    bid_proxy.dismiss();
                                                                    fragmentCurrentAuction.startRepeatingTask();
                                                                }
                                                            });

                                                        }
                                                        else
                                                        {
                                                            show_dailog("Please Check Internet Connection");

                                                        }


                                                    }
                                                    else
                                                    {
//                                            Toast.makeText(mContext, "From RS", Toast.LENGTH_SHORT).show();

                                                        if (utility.checkInternet())
                                                        {
                                                            //ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot);

                                                            makeBid.ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot,Bidclosingtime,
                                                                    Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,strTitle);

                                                            makeBid.bidResult(new OnBidResult() {
                                                                @Override
                                                                public void bidResult(String currentStatus, String msg) {
                                                                    bid_proxy.dismiss();
                                                                    fragmentCurrentAuction.startRepeatingTask();
                                                                }
                                                            });
                                                        }
                                                        else
                                                        {

                                                            show_dailog("Please Check Internet Connection");
                                                        }


                                                    }

                                                }
                                                else
                                                {
                                                    //Toast.makeText(mContext, "Proxy Must Greater Than Current Bid Value", Toast.LENGTH_SHORT).show();

                                                    int myNum = Integer.parseInt(entered_value);
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
                                Window window = bid_proxy.getWindow();
                                window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
                                window.setGravity(Gravity.CENTER);
                                bid_proxy.setCanceledOnTouchOutside(false);

                            }
                            else
                            {
                                //Toast.makeText(mContext, "You do not have bidding access", Toast.LENGTH_SHORT).show();

                                //show_dailog("You don't have access to Bid Please contact Astaguru.");
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
                else
                {
                    show_dailog("This auction is closed");
                }

            }
        });
        lay_lot_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(onCurrentItenClick!=null)
                {
                    onCurrentItenClick.setOnCurrentAuctionItemClick(position,true);
                }*/

                String Str_id =cp.getStr_productid();
                String reference = cp.getReference();
                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
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
                intent.putExtra("category",cp.getStr_category());
                intent.putExtra("is_us",is_us);
                intent.putExtra("dollar_rate",cp.getDollarRate());
                mContext.startActivity(intent);
            }
        });
        lay_bid_history.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                // Here Set your Event Date
                String str = cp.getStr_Bidclosingtime();

                //String[] splitStr = str.split("\\s+");
                Date eventDate = null,currentDate=null;
                try
                {
                    eventDate = dateFormat.parse(str);
                    String strcurrentDate =cp.getCurrentDate();
                    currentDate = dateFormat.parse(strcurrentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean is_after = currentDate.before(eventDate);


                if(is_after)
                {
                    String currency_type="";

                    if (is_us)
                    {
                        currency_type = "USD";
                    }
                    else
                    {
                        currency_type = "INR";
                    }
                    Intent intentproductid = new Intent(mContext, Bid_History.class);
                    intentproductid.putExtra("Str_id", cp.getStr_productid());
                    intentproductid.putExtra("str_FirstName", cp.getStr_FirstName()+" "+cp.getStr_LastName());
                    intentproductid.putExtra("str_category", cp.getStr_category());
                    intentproductid.putExtra("str_medium", cp.getMedium());
                    intentproductid.putExtra("str_date", cp.getProductdate());
                    intentproductid.putExtra("str_productsize", cp.getStr_productsize());
                    intentproductid.putExtra("str_istimate", cp.getEstamiate());
                    intentproductid.putExtra("str_collectors", cp.getStr_collectors());
                    intentproductid.putExtra("str_lot", cp.getReference().trim());
                    intentproductid.putExtra("str_image", cp.getStr_image());
                    intentproductid.putExtra("fragment_type", "current");
                    intentproductid.putExtra("MyUserID", cp.getMyUserID());
                    intentproductid.putExtra("str_LastName", cp.getStr_LastName());
                    intentproductid.putExtra("str_FirstName", cp.getStr_FirstName());
                    intentproductid.putExtra("str_pricers", cp.getPricers());
                    intentproductid.putExtra("str_priceus", cp.getPriceus());
                    intentproductid.putExtra("str_refrene", cp.getReference().trim());
                    intentproductid.putExtra("currency_type", is_us);
                    intentproductid.putExtra("product_id", cp.getStr_productid());
                    intentproductid.putExtra("currentDate", cp.getCurrentDate());
                    intentproductid.putExtra("str_Bidclosingtime", cp.getStr_Bidclosingtime());
                    intentproductid.putExtra("Auctionname",cp.getAuctionname());
                    intentproductid.putExtra("Prdescription",cp.getStr_description());
                    intentproductid.putExtra("doller_rate",cp.getDollarRate());

                    mContext.startActivity(intentproductid);
                }
                else
                {
                    show_dailog("This auction is closed");
                }


            }


        });







        return curView;

    }



    private void BidNow(String str_Amount,String str_productID,String str_userID,String dollerrate,String proxy_new_us,String tlot)
    {

        Bidclosingtime = Bidclosingtime.replace(" ","%20");

        if (utility.checkInternet())
        {
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

                    String str_json = result,msg="";

                    try {
                        if (str_json != null)
                        {

                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {
                                JSONObject Obj = jsonArray.getJSONObject(0);
                                String currentStatus = Obj.getString("currentStatus");
                                if(Obj.has("msg"))
                                {
                                    msg = Obj.getString("msg");
                                }
                                //String emailID = Obj.getString("emailID");
                                // String mobilrNum = Obj.getString("mobileNum");

                               // currentAuction.startRepeatingTask();
//                                Toast.makeText(mContext,currentStatus, Toast.LENGTH_SHORT).show();
                                if(currentStatus.equals("1"))
                                {

                                    // Toast.makeText(mContext, "You Successfully Bid", Toast.LENGTH_SHORT).show();
                                    String number = Obj.getString("mobileNum");
                                    bid_now.dismiss();

                                    show_dailog("Your bid submitted successfully, currently you are leading for this product");
                                    registerUser(str_lot,str_amt,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    //  Toast.makeText(mContext, "U r out of bid", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    show_dailog("Sorry You are out off bid because already higher proxybid is their, do you want to bid again");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    // show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

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

                    //currentAuction.call_data();
                }
            });
        }

    }

    private void GetData(String value, String str_productid, String rs_amount, String productid, String userid, String dollerrate, String proxy_new_us, String lot, final String strTitle) {

        if (utility.checkInternet()) {
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




                                double value_one = Double.parseDouble(rupee_value);
                                double value_two = Double.parseDouble(trs_amount);

                                if (value_two > value_one)
                                {
                                   // BidNow(trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot);

                                    makeBid.BidNow(trs_amount,tproductid,tuserid,tdollerrate,tproxy_new_us,tlot,Bidclosingtime,Thumbnail,Reference,OldPriceRs,OldPriceUs,Auctionid,Ufirst_name,Ulastname,strTitle);
                                    makeBid.bidResult(new OnBidResult() {
                                        @Override
                                        public void bidResult(String currentStatus, String msg)
                                        {
                                           // Toast.makeText(mContext,currentStatus,Toast.LENGTH_SHORT).show();

                                            bid_now.dismiss();
                                            fragmentCurrentAuction.startRepeatingTask();
                                        }

                                    });
                                }
                                else
                                {

//                                    Toast.makeText(mContext, "Dismiss  bidding", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    int int_proxy_bid_rselse = MakeBid.Get_10_value(rupee_value);


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
                                        int int_proxy_bid_rselse_us = MakeBid.Get_10_value(rupee_value);


                                        String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_us);
                                        tv_bidvalue.setText(str_int_us);
                                        iv_icon.setText("US$");
                                    }
                                    else
                                    {
                                        int int_proxy_bid_rselse_rs = MakeBid.Get_10_value(rupee_value);

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
                        }
                        else
                        {
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
//                                String mobilrNum = Obj.getString("mobileNum");
                              //  currentAuction.startRepeatingTask();
                                if(currentStatus.equals("1"))
                                {

                                    String number = Obj.getString("mobileNum");
                                    bid_proxy.dismiss();

                                    show_dailog("Your Proxy bid submitted successfully,currently you are leading for this product.");
                                    registerUser(lot_no,userproxy,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    bid_proxy.dismiss();

                                    show_dailog("Sorry You are out off bid because already higher proxybid is their, do you want to bid again.");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    bid_proxy.dismiss();

                                    show_dailog("New Proxy Bid Value Should Be Greater Then Current Proxy Bid Value.");

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
                        // Toast.makeText(mContext,response,Toast.LENGTH_LONG).show();
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
                    Toast.makeText(mContext, "Auction Succesfully to Gallary", Toast.LENGTH_SHORT).show();

                    String str_json = result;


                }
            });
        }

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

        int fb = Integer.parseInt(dollerrate);
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
        dilog_alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tv_cancelx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilog_alert.dismiss();
            }
        });


        dilog_alert.show();
        Window window = dilog_alert.getWindow();
        window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dilog_alert.setCanceledOnTouchOutside(false);
    }

    public void setOnCurrentItenClick(OnCurrentAuctionItemClick onCurrentItenClick)
    {
        this.onCurrentItenClick=onCurrentItenClick;
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
