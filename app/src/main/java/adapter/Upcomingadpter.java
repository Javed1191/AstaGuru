package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomanav.astaguru.MainActivity;
import com.infomanav.astaguru.PastAuction;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.Upcoming_Model;
import com.squareup.picasso.Picasso;

import java.util.List;

import services.Application_Constants;

import static com.infomanav.astaguru.R.id.tv_auction;
import static com.infomanav.astaguru.R.id.tv_date;

/**
 * Created by android-javed on 05-10-2016.
 */

public class  Upcomingadpter extends BaseAdapter {
    private Context mContext;
    private List<Upcoming_Model> AppsList;
    private MainActivity mainActivity;


    public Upcomingadpter(Context c, List<Upcoming_Model> AppsList) {
        mContext = c;
        this.AppsList=AppsList;
        mainActivity = (MainActivity) mContext;
    }
    public static class ViewHolder {
        ImageView imageView;
        TextView tv_auction,tv_date;
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

        ViewHolder grid;
        LayoutInflater inflator = mainActivity.getLayoutInflater();

        if (convertView == null) {
            grid = new ViewHolder();
            convertView = inflator.inflate(R.layout.upcoming_single, null);
            grid.tv_auction = (TextView) convertView.findViewById(tv_auction);
            grid.tv_date = (TextView) convertView.findViewById(tv_date);
            grid.imageView = (ImageView) convertView.findViewById(R.id.grid_image);

            convertView.setTag(grid);   // <<-- H E R E
        } else {
            grid = (ViewHolder) convertView.getTag();   // <<-- H E R E
        }

        final Upcoming_Model apps = AppsList.get(position);
        grid.tv_auction.setText(apps.getStr_auction());
        grid.tv_date.setText(apps.getStr_date());
        Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + "images/auction/18.jpg")
                .into(grid.imageView);
        return convertView;
    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        // TODO Auto-generated method stub
//        View grid;
//        LayoutInflater inflater = (LayoutInflater) mContext
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        final Upcoming_Model apps = AppsList.get(position);
//
//        if (convertView == null)
//        {
//
//            grid = new View(mContext);
//            grid = inflater.inflate(R.layout.upcoming_single, null);
//            TextView tv_auction = (TextView) grid.findViewById(R.id.tv_auction);
//            TextView tv_date = (TextView) grid.findViewById(R.id.tv_date);
//
//
//            tv_auction.setText(apps.getStr_auction());
//            tv_date.setText(apps.getStr_date());
//
//        } else {
//            grid = (View) convertView;
//        }
//
//        return grid;
//    }
}
