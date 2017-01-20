package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomanav.astaguru.Category_Model;
import com.infomanav.astaguru.MainActivity;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.UpcomingAuction;
import com.squareup.picasso.Picasso;

import java.util.List;

import services.Application_Constants;

/**
 * Created by android-javed on 03-10-2016.
 */

public class CategoryAdpter extends BaseAdapter {
    private Context mContext;
    private List<Category_Model> AppsList;
   private MainActivity mainActivity;

    public CategoryAdpter(Context c, List<Category_Model> AppsList) {
       this.mContext = c;
        this.AppsList = AppsList;
       // mainActivity = (MainActivity) mContext;
    }

    public static class ViewHolder {
        TextView tv_title;
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

        final Category_Model apps = AppsList.get(position);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.category_grid, null);

            TextView tv_title = (TextView) grid.findViewById(R.id.tv_title);

            tv_title.setText(apps.getCategory_name());


        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}