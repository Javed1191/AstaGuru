package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomanav.astaguru.Current_Auction_Model;
import com.infomanav.astaguru.Lot_Detail_Page;
import com.infomanav.astaguru.MainActivity;
import com.infomanav.astaguru.PastAuction;
import com.infomanav.astaguru.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import services.Application_Constants;

/**
 * Created by android-javed on 28-11-2016.
 */

public class PastAuctionListviewAdpter extends ArrayAdapter<PastAuction> {

    private MainActivity mainActivity;
    Context mContext;
    public boolean is_us=false;
    List<PastAuction> objects;
    ImageView iv_one,iv_two;
    ImageView iv_oncce;
    public PastAuctionListviewAdpter(Context context, int textViewResourceId, List<PastAuction> objects, final boolean is_us) {
        super(context, textViewResourceId, objects);
        mainActivity = (MainActivity) context;
        mContext = context;
        this.objects=objects;
        this.is_us=is_us;


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
    public View getView(int position, View convertView, ViewGroup parent) {
        View curView = convertView;

        if (curView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.pastauction_listview, null);
        }

        final  PastAuction cp = getItem(position);

        ImageView imageView = (ImageView)curView.findViewById(R.id.grid_image);

        ImageView  expandedImageView = (ImageView) curView.findViewById(R.id.grid_image);

        iv_one = (ImageView) curView.findViewById(R.id.iv_one);
        iv_two = (ImageView) curView.findViewById(R.id.iv_two);
        TextView tv_date = (TextView) curView.findViewById(R.id.tv_date);
        TextView tv_title = (TextView) curView.findViewById(R.id.tv_title);
        TextView grid_title = (TextView) curView.findViewById(R.id.grid_title);
        CardView card_main = (CardView) curView.findViewById(R.id.card_main);
        iv_oncce = (ImageView)curView.findViewById(R.id.iv_oncce);

        TextView tv_current_bid = (TextView) curView.findViewById(R.id.tv_current_bid);
        TextView tv_nextbid = (TextView) curView.findViewById(R.id.tv_nextbid);


//        card_main.requestLayout();
//        card_main.getLayoutParams().width = mainActivity.m.widthPixels;
//        card_main.getLayoutParams().height = mainActivity.m.widthPixels-400;

        // textView.setText(apps.getAppTitle());

        tv_title.setText(cp.getAuctionname());
        grid_title.setText(cp.getAuctiontitle());

//        tv_current_bid.setText(cp.getPricers());
//
//        double amount = Double.parseDouble(cp.getPricers());
//
//        double byerprimium = (amount / 100.0f) * 10;
//
//        double sum = amount+byerprimium;
//
//        int intbyerprimium = (int) sum;
//        String valuebyerprimium = String.valueOf(intbyerprimium);
//        tv_nextbid.setText(valuebyerprimium);


//
//        if(is_us)
//        {
//            String str_us = cp.getPriceus();
//            int int_str = Integer.parseInt(str_us);
//            String str_ustest = NumberFormat.getNumberInstance(Locale.US).format(int_str);
//
//            tv_current_bid.setText(str_ustest);
//            double amount1 = Double.parseDouble(cp.getPriceus());
//
//            double byerprimium1 = (amount1 / 100.0f) * 10;
//
//            double sum1 = amount1+byerprimium1;
//
//            int intbyerprimium1 = (int) sum1;
//
//            String valuebyerprimium1 = NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium1);
////            String valuebyerprimium1 = String.valueOf(intbyerprimium1);
//            tv_nextbid.setText(valuebyerprimium1);
//            iv_one.setImageResource(R.drawable.doller);
//            iv_two.setImageResource(R.drawable.doller);
//        }
//        else
//        {
//
//
//            String str_rs= cp.getPricers();
//            int int_strrs = Integer.parseInt(str_rs);
//            String str_rscomma= NumberFormat.getNumberInstance(Locale.US).format(int_strrs);
//            tv_current_bid.setText(str_rscomma);
//
//            double amount = Double.parseDouble(cp.getPricers());
//
//            double byerprimium = (amount / 100.0f) * 10;
//
//            double sum = amount+byerprimium;
//
//            int intbyerprimium = (int) sum;
////            String valuebyerprimium = String.valueOf(intbyerprimium);
//            String valuebyerprimium= NumberFormat.getNumberInstance(Locale.US).format(intbyerprimium);
//
//            tv_nextbid.setText(valuebyerprimium);
//            iv_one.setImageResource(R.drawable.rupee);
//            iv_two.setImageResource(R.drawable.rupee);
//        }

           /* String strProductImagePath = Application_Constants.APP_IMAGE_PATH;
            String strProductImage = apps.getAppLogo();*/


        // imageView.setImageResource(apps.getAppLogo());
        Picasso.with(getContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + cp.getImage())
                .into(imageView);

//        expandedImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String Str_id =cp.getStr_productid();
//                Intent intent = new Intent(mContext,Lot_Detail_Page.class);
//                intent.putExtra("Str_id",Str_id);
//                mContext.startActivity(intent);
//
//            }
//        });

        return curView;

    }

}
