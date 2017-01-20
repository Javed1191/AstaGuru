package adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.infomanav.astaguru.Current_Auction_Model;
import com.infomanav.astaguru.MainActivity;
import com.infomanav.astaguru.R;

import java.text.DecimalFormat;
import java.util.List;

import services.SessionData;
import services.Utility;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
 
    private List<Current_Auction_Model> moviesList;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private MainActivity mainActivity;
    LinearLayout lin_front, lin_back;
    RelativeLayout rel_back;
    ImageView iv_oncce, expandedImageView, iv_zoom, iv_close;
    boolean isBackVisible = false;
    ViewFlipper flipper;
    View rootLayout, cardFace, cardBack;
    public DisplayMetrics m;
    AnimatorSet setRightOut;
    Context mContext;
    ViewFlipper viewFlipper;
    AnimatorSet setLeftIn;
    TextView tv_title;

    Button btn_bidnow, btn_proxybid;
    int count;
    public boolean is_us = false;
    List<Current_Auction_Model> objects;
    ImageView iv_one, iv_two,iv_oneback,iv_twoback;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    DecimalFormat formatter;
    Utility utility;
    SessionData data;
    EditText edt_proxy;
    AlertDialog bid_now,bid_proxy;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
 
        public MyViewHolder(View view) {
            super(view);
            expandedImageView = (ImageView) view.findViewById(R.id.grid_image);
            TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_lot = (TextView) view.findViewById(R.id.tv_lot);
            TextView tv_subtitle = (TextView) view.findViewById(R.id.tv_subtitle);
            TextView tv_current_bid = (TextView) view.findViewById(R.id.tv_current_bid);
            TextView tv_nextbid = (TextView) view.findViewById(R.id.tv_nextbid);
            iv_one = (ImageView) view.findViewById(R.id.iv_one);
            iv_two = (ImageView) view.findViewById(R.id.iv_two);


            TextView tv_ac_artist = (TextView) view.findViewById(R.id.tv_ac_artist);
            TextView tv_ac_category = (TextView) view.findViewById(R.id.tv_ac_category);
            TextView tv_ac_medium = (TextView) view.findViewById(R.id.tv_ac_medium);
            TextView tv_ac_year = (TextView) view.findViewById(R.id.tv_ac_year);
            TextView tv_ac_size = (TextView) view.findViewById(R.id.tv_ac_size);
            TextView tv_estimate = (TextView) view.findViewById(R.id.tv_estimate);
            TextView tv_ac_bid = (TextView) view.findViewById(R.id.tv_ac_bid);
            TextView tv_ac_nextbid = (TextView) view.findViewById(R.id.tv_ac_nextbid);


            iv_oncce = (ImageView) view.findViewById(R.id.iv_oncce);
            iv_zoom = (ImageView) view.findViewById(R.id.iv_zoom);

            iv_oneback= (ImageView) view.findViewById(R.id.iv_oneback);
            iv_twoback= (ImageView) view.findViewById(R.id.iv_twoback);


            iv_close = (ImageView) view.findViewById(R.id.iv_close);

            btn_bidnow = (Button) view.findViewById(R.id.btn_bidnow);
            btn_proxybid = (Button) view.findViewById(R.id.btn_proxybid);

            lin_front = (LinearLayout) view.findViewById(R.id.lin_front);
            lin_back = (LinearLayout) view.findViewById(R.id.lin_back);
        }
    }
    public void changeCurrency() {
        if (is_us) {
            is_us = false;
        } else {
            is_us = true;
        }
        notifyDataSetChanged();
    }


    public MoviesAdapter(Context context, int textViewResourceId, List<Current_Auction_Model> objects, final boolean is_us) {
        this.moviesList = objects;

        mContext = context;
        this.objects = objects;
        this.is_us = is_us;

        utility = new Utility(mContext);
        data = new SessionData(mContext);
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_grid, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Current_Auction_Model movie = moviesList.get(position);
//        holder.title.setText(movie.getArtist_name());
//        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());
    }
 
    @Override
    public int getItemCount() {
        return moviesList.size();
    }


}
