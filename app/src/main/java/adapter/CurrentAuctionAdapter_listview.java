package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infomanav.astaguru.Current_Auction_Model;
import com.infomanav.astaguru.Lot_Detail_Page;
import com.infomanav.astaguru.MainActivity;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.UpcomingAuction;
import com.infomanav.astaguru.ZoomActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
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
    private MainActivity mainActivity;
    Context mContext;
    private Handler handler;
    private Runnable runnable;
    public boolean is_us=false;
    List<Current_Auction_Model> objects;
    TextView iv_one,iv_two;
    ImageView iv_detailslist,iv_closelist,grid_imagelist,iv_listzoom;

    LinearLayout lin_front, lin_back;
    Utility utility;
    ImageView iv_addtogallary;
    SessionData data;
    public CurrentAuctionAdapter_listview(Context context, int textViewResourceId, List<Current_Auction_Model> objects,final boolean is_us) {
        super(context, textViewResourceId, objects);
        mainActivity = (MainActivity) context;
        mContext = context;
        this.objects=objects;
        this.is_us=is_us;

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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View curView = convertView;

        if (curView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.current_listview, null);
        }

        final  Current_Auction_Model cp = getItem(position);

        grid_imagelist = (ImageView)curView.findViewById(R.id.grid_imagelist);

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

        TextView tv_lot = (TextView) curView.findViewById(R.id.tv_lotlist);
        TextView tv_lot_back = (TextView) curView.findViewById(R.id.tv_lot_backlist);
        tv_lot.setText("Lot " + cp.getReference());
        tv_lot_back.setText("Lot " + cp.getReference());

        TextView tv_ac_artist = (TextView) curView.findViewById(R.id.tv_ac_artistlist);
        TextView tv_ac_category = (TextView) curView.findViewById(R.id.tv_ac_categorylist);
        TextView tv_ac_medium = (TextView) curView.findViewById(R.id.tv_ac_mediumlist);
        TextView tv_ac_year = (TextView) curView.findViewById(R.id.tv_ac_yearlist);
        TextView tv_ac_size = (TextView) curView.findViewById(R.id.tv_ac_sizelist);
        TextView tv_estimate = (TextView) curView.findViewById(R.id.tv_estimatelist);


        tvDay = (TextView) curView.findViewById(R.id.txtTimerDay);
        tvHour = (TextView) curView.findViewById(R.id.txtTimerHour);
        tvMinute = (TextView) curView.findViewById(R.id.txtTimerMinute);
        tvSecond = (TextView) curView.findViewById(R.id.txtTimerSecond);

        tv_ac_artist.setText(cp.getArtist_name());
        tv_ac_category.setText(cp.getStr_category());
        tv_ac_medium.setText(cp.getMedium());
        tv_ac_year.setText(cp.getProductdate());
        tv_ac_size.setText(cp.getProductsize());
        tv_estimate.setText(cp.getEstamiate());

        tv_title.setText(cp.getArtist_name());
        grid_title.setText(cp.getStr_title());



        String user = data.getObjectAsString("login");
        String userid = data.getObjectAsString("userid");

        if(user.equals("true"))
        {
            if(userid.equals(cp.getMyUserID()))
            {
                tv_lot.setBackgroundResource(R.drawable.green_btn);


            }
            else
            {
                tv_lot.setBackgroundResource(R.drawable.rounded_rectangle);

            }

        }
        else
        {
            tv_lot.setBackgroundResource(R.drawable.rounded_rectangle);

        }


        handler = new Handler();


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
                    String str = "2017-02-24 12:25:00";

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

                        tvDay.setText("" + String.format("%02d",days));
                        tvHour.setText("" + String.format("%02d", hours));
                        tvMinute.setText("" + String.format("%02d", minutes));
                        tvSecond.setText("" + String.format("%02d", seconds));




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


        iv_addtogallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id = cp.getStr_productid();
                String  userid = data.getObjectAsString("userid");
                AddToGallary(Str_id,userid);
            }
        });



        if (cp.getIs_front()) {
            lin_front.setVisibility(View.VISIBLE);
            lin_back.setVisibility(View.GONE);
        } else {
            lin_front.setVisibility(View.GONE);
            lin_back.setVisibility(View.VISIBLE);
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

                String Str_thumbanial= cp.getStr_thumbnail();
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", Str_thumbanial);
                mContext.startActivity(intent);
            }
        });

        iv_detailslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                cp.setIs_front(false);
                notifyDataSetChanged();
            }
        });

        if(is_us)
        {
            String str_us = cp.getPriceus();
            int int_str = Integer.parseInt(str_us);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

            tv_current_bid.setText(str_ustest);
            double amount1 = Double.parseDouble(cp.getPriceus());

            double byerprimium1 = (amount1 / 100.0f) * 10;

            double sum1 = amount1+byerprimium1;

            int intbyerprimium1 = (int) sum1;

            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium1);
//            String valuebyerprimium1 = String.valueOf(intbyerprimium1);
            tv_nextbid.setText(valuebyerprimium1);
            iv_one.setText("US$");
            iv_two.setText("US$");
        }
        else
        {


            String str_rs= cp.getPricers();
            int int_strrs = Integer.parseInt(str_rs);
            String str_rscomma= NumberFormat.getNumberInstance(Locale.US).format(int_strrs);
            tv_current_bid.setText(str_rscomma);

            double amount = Double.parseDouble(cp.getPricers());

            double byerprimium = (amount / 100.0f) * 10;

            double sum = amount+byerprimium;

            int intbyerprimium = (int) sum;
//            String valuebyerprimium = String.valueOf(intbyerprimium);
            String valuebyerprimium= NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium);

            tv_nextbid.setText(valuebyerprimium);
            iv_one.setText("₹");
            iv_two.setText("₹");
        }

           /* String strProductImagePath = Application_Constants.APP_IMAGE_PATH;
            String strProductImage = apps.getAppLogo();*/


        // imageView.setImageResource(apps.getAppLogo());
        Picasso.with(getContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getStr_thumbnail())
                .into(grid_imagelist);

        Picasso.with(getContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getStr_thumbnail())
                .into(expandedImageView);

        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id =cp.getStr_productid();
                String reference = cp.getReference();
                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
                intent.putExtra("Str_id",Str_id);
                intent.putExtra("reference", reference);
                intent.putExtra("fragment", "Current");
                mContext.startActivity(intent);

            }
        });

        grid_imagelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id =cp.getStr_productid();
                String reference = cp.getReference();
                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
                intent.putExtra("Str_id",Str_id);
                intent.putExtra("reference", reference);
                intent.putExtra("fragment", "Current");
                mContext.startActivity(intent);

            }
        });


        return curView;

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
                    Toast.makeText(mContext, "Auction Succesfully to Gallary", Toast.LENGTH_SHORT).show();


                    String str_json = result;


                }
            });
        }

    }
}
