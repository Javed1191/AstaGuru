package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infomanav.astaguru.Artist_Details;
import com.infomanav.astaguru.Lot_Detail_Page;
import com.infomanav.astaguru.MainActivity;

import model_classes.Past_sub_Model;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.ShowFullZoomImage;
import com.infomanav.astaguru.ZoomActivity;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import services.Application_Constants;

/**
 * Created by fox-2 on 11/29/2016.
 */

public class Past_SubAuction_ListviewAdpter extends BaseAdapter {

    private MainActivity mainActivity;
    Context mContext;
    public boolean is_us=false;
    private List<Past_sub_Model> AppsList;
    ImageView iv_one;
    ImageView iv_oncce,past_sub_image,grid_imageback,iv_closelistpast,iv_info,iv_zoom;
    TextView iv_two,tv_sub_artist,tv_sub_category,tv_sub_medium,tv_sub_year,tv_sub_size,
            tv_sub_estimate,tv_lot_back,tv_lot,tv_nextbid,tv_tax_premium,tv_bought_in,tv_start_price;
    String auctiontype;
    LinearLayout lin_front, lin_back,lay_front_data,lay_front,lay_back,lay_bought_in;
    private ShowFullZoomImage showFullZoomImage;
    public Past_SubAuction_ListviewAdpter(Context context, List<Past_sub_Model> AppsList, final boolean is_us,String auctiontype) {
        mContext = context;
        this.AppsList = AppsList;
        this.is_us=is_us;
        this.auctiontype=auctiontype;
        showFullZoomImage = new ShowFullZoomImage(mContext);


    }
    public void changeCurrency(boolean  is_us)
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

    @Override
    public int getCount() {
        return AppsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View curView = convertView;

        if (curView == null) {
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.pastauction_sub_listview, null);
        }

        final  Past_sub_Model cp = AppsList.get(position);



        iv_one = (ImageView) curView.findViewById(R.id.iv_one);
        iv_two = (TextView) curView.findViewById(R.id.iv_two);
        TextView tv_date = (TextView) curView.findViewById(R.id.tv_date);
        TextView tv_title = (TextView) curView.findViewById(R.id.tv_title);
        TextView grid_title = (TextView) curView.findViewById(R.id.grid_title);
        CardView card_main = (CardView) curView.findViewById(R.id.card_main);
        iv_oncce = (ImageView)curView.findViewById(R.id.iv_oncce);
        past_sub_image = (ImageView)curView.findViewById(R.id.past_sub_image);
         tv_nextbid = (TextView) curView.findViewById(R.id.tv_nextbid);
         tv_lot = (TextView) curView.findViewById(R.id.tv_lot);
         tv_lot_back = (TextView) curView.findViewById(R.id.tv_lot_back);
        tv_sub_artist = (TextView) curView.findViewById(R.id.tv_sub_artist);
        tv_sub_category= (TextView) curView.findViewById(R.id.tv_sub_category);
        tv_sub_medium = (TextView) curView.findViewById(R.id.tv_sub_medium);
        tv_sub_year= (TextView) curView.findViewById(R.id.tv_sub_year);
        tv_sub_size= (TextView) curView.findViewById(R.id.tv_sub_size);
        tv_sub_estimate= (TextView) curView.findViewById(R.id.tv_sub_estimate);
        grid_imageback= (ImageView) curView.findViewById(R.id.grid_imageback);
        iv_closelistpast= (ImageView) curView.findViewById(R.id.iv_closelistpast);
        iv_info = (ImageView) curView.findViewById(R.id.iv_info);
        iv_zoom = (ImageView) curView.findViewById(R.id.iv_zoom);
        tv_tax_premium = (TextView) curView.findViewById(R.id.tv_tax_premium);
        tv_bought_in = (TextView) curView.findViewById(R.id.tv_bought_in);
        tv_start_price = (TextView) curView.findViewById(R.id.tv_start_price);
        lay_front_data = (LinearLayout) curView.findViewById(R.id.lay_front_data);
        lay_bought_in = (LinearLayout) curView.findViewById(R.id.lay_bought_in);
        lin_front = (LinearLayout) curView.findViewById(R.id.lin_front);
        lin_back = (LinearLayout) curView.findViewById(R.id.lin_back);

        lay_front = (LinearLayout) curView.findViewById(R.id.lay_front);
        lay_back = (LinearLayout) curView.findViewById(R.id.lay_back);


        grid_title.setText(cp.getStr_title());
        tv_lot.setText("Lot:"+cp.getReference().trim());
        tv_lot_back.setText("Lot:"+cp.getReference().trim());
        tv_sub_artist.setText(cp.getArtist_name());

        if(cp.getAuctionname().equalsIgnoreCase("Collectibles Auction"))
        {
            tv_title.setVisibility(View.GONE);
        }
        else
        {
            tv_title.setText(cp.getArtist_name());
        }

        tv_sub_category.setText(cp.getStr_category());
        tv_sub_medium.setText(cp.getStr_medium());
        tv_sub_year.setText(cp.getProductdate());
        tv_sub_size.setText(cp.getProductsize()+" in");



        past_sub_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id =cp.getStr_productid();
                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
                intent.putExtra("Str_id",Str_id);
                intent.putExtra("reference", cp.getReference());
                intent.putExtra("fragment",auctiontype);
                intent.putExtra("Auction_id",cp.getAuction_id());
                intent.putExtra("Auctionname",cp.getAuctionname());

                intent.putExtra("medium",cp.getStr_medium());
                intent.putExtra("FirstName",cp.getStr_FirstName());
                intent.putExtra("LastName",cp.getStr_LastName());
                intent.putExtra("Profile",cp.getStr_Profile());
                intent.putExtra("is_us",is_us);
                intent.putExtra("dollar_rate",cp.getDollarRate());

                mContext.startActivity(intent);
            }
        });
        if (cp.getis_front()) {
            lay_front.setVisibility(View.VISIBLE);
            lay_back.setVisibility(View.GONE);
        } else {
            lay_front.setVisibility(View.GONE);
            lay_back.setVisibility(View.VISIBLE);
        }
        iv_closelistpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setIs_front(true);
               notifyDataSetChanged();

            }
        });

        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setIs_front(false);
                notifyDataSetChanged();

            }
        });

        iv_zoom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

               /* String Str_id = cp.getStr_image();
                Intent intent = new Intent(mContext, ZoomActivity.class);
                intent.putExtra("imgpath", Str_id);
                mContext.startActivity(intent);*/
                showFullZoomImage.showImage(cp.getStr_image());
            }
        });

        past_sub_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id =cp.getStr_productid();
                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
                intent.putExtra("Str_id",Str_id);
                intent.putExtra("reference", cp.getReference());
                intent.putExtra("fragment",auctiontype);
                intent.putExtra("Auction_id",cp.getAuction_id());
                intent.putExtra("Auctionname",cp.getAuctionname());

                intent.putExtra("medium",cp.getStr_medium());
                intent.putExtra("FirstName",cp.getStr_FirstName());
                intent.putExtra("LastName",cp.getStr_LastName());
                intent.putExtra("Profile",cp.getStr_Profile());
                intent.putExtra("is_us",is_us);
                intent.putExtra("dollar_rate",cp.getDollarRate());
                mContext.startActivity(intent);
            }
        });

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Artist_Details.class);
                intent.putExtra("Str_id", cp.getStr_artistid());
                intent.putExtra("Str_artistname", cp.getArtist_name());
                intent.putExtra("Picture", cp.getPicture());
                intent.putExtra("Profile", cp.getStr_Profile());
                intent.putExtra("is_us", is_us);
                mContext.startActivity(intent);
            }
        });


        grid_imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id =cp.getStr_productid();
                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
                intent.putExtra("Str_id",Str_id);
                intent.putExtra("reference", cp.getReference());
                intent.putExtra("fragment",auctiontype);
                intent.putExtra("Auction_id",cp.getAuction_id());
                intent.putExtra("Auctionname",cp.getAuctionname());

                intent.putExtra("medium",cp.getStr_medium());
                intent.putExtra("FirstName",cp.getStr_FirstName());
                intent.putExtra("LastName",cp.getStr_LastName());
                intent.putExtra("Profile",cp.getStr_Profile());
                intent.putExtra("is_us",is_us);
                intent.putExtra("dollar_rate",cp.getDollarRate());
                mContext.startActivity(intent);
            }
        });
        if(is_us)
        {
           /* String str_us = cp.getPriceus();
            int int_str = Integer.parseInt(str_us);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

            double amount1 = Double.parseDouble(cp.getPriceus());

            double byerprimium1 = (amount1 / 100.0f) * 10;

            double sum1 = amount1+byerprimium1;

            int intbyerprimium1 = (int) sum1;

            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium1);*/
            String str_us = cp.getPriceus();
            String str_ustest="0";
            if(str_us!=null)
            {
                if(auctiontype.equalsIgnoreCase("past"))
                {
                    str_us = claculatePercentage(str_us);


                }
                Double int_str = Double.parseDouble(str_us);
                str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

                str_ustest = mContext.getString(R.string.doller)+" "+str_ustest;

                tv_nextbid.setText(str_ustest);
            }
           else
            {
                str_ustest = mContext.getString(R.string.doller)+" "+str_ustest;
                tv_nextbid.setText(str_ustest);
            }


            iv_two.setVisibility(View.GONE);
            iv_two.setText("US$");
            tv_sub_estimate.setText(cp.getEstamiate());
        }
        else
        {


           /* String str_rs= cp.getPricers();
            int int_strrs = Integer.parseInt(str_rs);
            String str_rscomma= NumberFormat.getNumberInstance(Locale.US).format(int_strrs);


            double amount = Double.parseDouble(cp.getPricers());

            double byerprimium = (amount / 100.0f) * 10;

            double sum = amount+byerprimium;

            int intbyerprimium = (int) sum;

            String valuebyerprimium= NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium);*/

            String str_rs = cp.getPricers();
            String str_amt_rss="0";
            if(str_rs!=null)
            {
                if(auctiontype.equalsIgnoreCase("past"))
                {
                    str_rs = claculatePercentage(str_rs);

                }
                Double int_strrs = Double.parseDouble(str_rs);
                String str_rstest = NumberFormat.getNumberInstance(Locale.US).format(int_strrs);
                str_amt_rss = rupeeFormat(str_rstest);

                str_amt_rss = mContext.getString(R.string.rupees)+" "+str_amt_rss;
                tv_nextbid.setText(str_amt_rss);
            }
            else {
                str_amt_rss = mContext.getString(R.string.rupees)+" "+str_amt_rss;
                tv_nextbid.setText(str_amt_rss);
            }
            iv_two.setVisibility(View.GONE);
            iv_two.setText("₹");

            tv_sub_estimate.setText(cp.getStr_collectors());
        }

        Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getStr_thumbnail())
                .into(past_sub_image);

        Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getStr_thumbnail())
                .into(grid_imageback);

        if(auctiontype.equalsIgnoreCase("past"))
        {
            tv_start_price.setVisibility(View.GONE);
            tv_tax_premium.setVisibility(View.VISIBLE);
            if(Double.parseDouble(cp.getPricers()) < Double.parseDouble(cp.getPricelow()))
            {
                // Toast.makeText(mContext,"Test",Toast.LENGTH_SHORT).show();

                lay_bought_in.setVisibility(View.GONE);
                tv_bought_in.setVisibility(View.VISIBLE);
            }
            else {
                lay_bought_in.setVisibility(View.VISIBLE);
                tv_bought_in.setVisibility(View.GONE);
            }



        }
        else
        {
            tv_start_price.setVisibility(View.VISIBLE);
            tv_tax_premium.setVisibility(View.GONE);
        }

        return curView;

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
    public static String getRupeeFormat(String strAmount)
    {
        String strFormatedAmount="";
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        System.out.println(format.format(new BigDecimal("100000000")));

        strFormatedAmount = format.format(new BigDecimal(strAmount));
        return strFormatedAmount;
    }

    private String claculatePercentage(String strPrise)
    {
        String strBidPrise="";

        try
        {

            Integer int_bid_prise = 0,int_discount=0;

            int_bid_prise = Integer.parseInt(strPrise);
            int_discount = ((Integer.parseInt(strPrise)*15)/100);
            int_bid_prise = int_bid_prise+int_discount;

            strBidPrise = String.valueOf(int_bid_prise);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return strBidPrise;
    }

}

