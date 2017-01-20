package adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
 
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomanav.astaguru.Past_sub_Model;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.Second_img_Model;
import com.squareup.picasso.Picasso;

import services.Application_Constants;

public class ImageSlideAdapter extends PagerAdapter {

    List<Second_img_Model> products;
    private Context mContext;
    private List<Second_img_Model> AppsList;
    public ImageSlideAdapter(Context context, List<Second_img_Model> products)
    {
        this.mContext = context;
        this.products = products;

    }
 
    @Override
    public int getCount() {
        return products.size();
    }
 
    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.vp_image, container, false);
 
        ImageView mImageView = (ImageView) view
                .findViewById(R.id.image_display);

//        TextView tv_title =(TextView) view.findViewById(R.id.tv_title);
//        TextView tv_date =(TextView) view.findViewById(R.id.tv_date);

        final Second_img_Model apps = products.get(position);

//        tv_title.setText(apps.getImg_title());
//        tv_date.setText(apps.getImg_date());

        Picasso.with(mContext).load( apps.getImgg_url()).into(mImageView);


        container.addView(view);
        return view;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
 

}