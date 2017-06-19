package adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomanav.astaguru.MainActivity;
import model_classes.MyPurchases_Model;
import com.infomanav.astaguru.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import services.Application_Constants;

/**
 * Created by android-javed on 03-10-2016.
 */

public class MyPurchasesAdpter extends BaseAdapter {
    private Context mContext;
    private List<MyPurchases_Model> AppsList;
    private MainActivity mainActivity;
    public DisplayMetrics m;
    private ImageView imageView;
    public MyPurchasesAdpter(Context c, List<MyPurchases_Model> AppsList) {
        mContext = c;
        this.AppsList = AppsList;
//        this.mainActivity = (MainActivity) mContext;
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
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final MyPurchases_Model apps = AppsList.get(position);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.mypurchase_grid, null);
            // TextView textView = (TextView) grid.findViewById(R.id.grid_text);

            TextView tv_title = (TextView) grid.findViewById(R.id.tv_purchase_title);
//            CardView card_main = (CardView) grid.findViewById(R.id.card_main);
            imageView = (ImageView) grid.findViewById(R.id.grid_image_purchase);
            m = mContext.getResources().getDisplayMetrics();

//            card_main.requestLayout();
//            card_main.getLayoutParams().width = m.widthPixels /2;
//            card_main.getLayoutParams().height = m.widthPixels-200;

            // textView.setText(apps.getAppTitle());

            tv_title.setText(apps.getPur_auc_name().subSequence(0,15));

           /* String strProductImagePath = Application_Constants.APP_IMAGE_PATH;
            String strProductImage = apps.getAppLogo();*/


//            // imageView.setImageResource(apps.getAppLogo());
//            Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + apps.getImage())
//                    .into(imageView);

            Picasso.with(mContext).load(Application_Constants.PAST_AUCTION_IMAGE_PATH + apps.getImage()).resize(m.widthPixels /4,m.widthPixels-500)
                    .into(imageView);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
