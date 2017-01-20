package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infomanav.astaguru.Lot_Detail_Page;
import com.infomanav.astaguru.MainActivity;
import com.infomanav.astaguru.PastAuction;
import com.infomanav.astaguru.Past_sub_Model;
import com.infomanav.astaguru.R;
import com.squareup.picasso.Picasso;

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
    ImageView iv_oncce,past_sub_image,grid_imageback,iv_closelistpast,iv_info;
    TextView iv_two,tv_sub_artist,tv_sub_category,tv_sub_medium,tv_sub_year,tv_sub_size,tv_sub_estimate,tv_lot_back,tv_lot,tv_nextbid;
    String auctiontype;
    LinearLayout lin_front, lin_back;
    public Past_SubAuction_ListviewAdpter(Context context, List<Past_sub_Model> AppsList, final boolean is_us,String auctiontype) {
        mContext = context;
        this.AppsList = AppsList;
        this.is_us=is_us;
        this.auctiontype=auctiontype;


    }
    public void changeCurrency()
    {
        if(is_us)
        {
            is_us = false;
        }
        else
        {
            is_us=true;
        }
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
        iv_info= (ImageView) curView.findViewById(R.id.iv_info);

        lin_front = (LinearLayout) curView.findViewById(R.id.lin_front);
        lin_back = (LinearLayout) curView.findViewById(R.id.lin_back);
        tv_title.setText(cp.getArtist_name());
        grid_title.setText(cp.getStr_category());
        tv_lot.setText("Lot:"+cp.getReference().trim());
        tv_lot_back.setText("Lot:"+cp.getReference().trim());

        tv_sub_artist.setText(cp.getArtist_name());
        tv_sub_category.setText(cp.getStr_category());
        tv_sub_medium.setText(cp.getArtist_name());
        tv_sub_year.setText(cp.getProductdate());
        tv_sub_size.setText(cp.getProductsize());



        past_sub_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id =cp.getStr_productid();
                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
                intent.putExtra("Str_id",Str_id);
                intent.putExtra("reference", cp.getReference());
                intent.putExtra("fragment", auctiontype);
                mContext.startActivity(intent);
            }
        });
        if (cp.getis_front()) {
            lin_front.setVisibility(View.VISIBLE);
            lin_back.setVisibility(View.GONE);
        } else {
            lin_front.setVisibility(View.GONE);
            lin_back.setVisibility(View.VISIBLE);
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


        past_sub_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_id =cp.getStr_productid();
                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
                intent.putExtra("Str_id",Str_id);
                intent.putExtra("reference", cp.getReference());
                intent.putExtra("fragment",auctiontype);
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
                mContext.startActivity(intent);
            }
        });
        if(is_us)
        {
            String str_us = cp.getPriceus();
            int int_str = Integer.parseInt(str_us);
            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);

            double amount1 = Double.parseDouble(cp.getPriceus());

            double byerprimium1 = (amount1 / 100.0f) * 10;

            double sum1 = amount1+byerprimium1;

            int intbyerprimium1 = (int) sum1;

            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium1);
            tv_nextbid.setText(valuebyerprimium1);
            iv_two.setText("US$");
            tv_sub_estimate.setText(cp.getEstamiate());
        }
        else
        {


            String str_rs= cp.getPricers();
            int int_strrs = Integer.parseInt(str_rs);
            String str_rscomma= NumberFormat.getNumberInstance(Locale.US).format(int_strrs);


            double amount = Double.parseDouble(cp.getPricers());

            double byerprimium = (amount / 100.0f) * 10;

            double sum = amount+byerprimium;

            int intbyerprimium = (int) sum;

            String valuebyerprimium= NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium);

            tv_nextbid.setText(valuebyerprimium);

            iv_two.setText("₹");
            tv_sub_estimate.setText(cp.getStr_collectors());
        }

        Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getStr_image())
                .into(past_sub_image);

        Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getStr_image())
                .into(grid_imageback);



        return curView;

    }

}

