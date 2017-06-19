package adapter;

import java.util.List;
 
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.infomanav.astaguru.R;
import model_classes.Second_img_Model;
import com.squareup.picasso.Picasso;

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