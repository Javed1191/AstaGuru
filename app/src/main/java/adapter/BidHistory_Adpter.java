package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infomanav.astaguru.Bid_History_Model;
import com.infomanav.astaguru.R;

import java.util.List;

/**
 * Created by android-javed on 04-10-2016.
 */

public class BidHistory_Adpter extends RecyclerView.Adapter<BidHistory_Adpter.MyViewHolder> {

    private List<Bid_History_Model> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_nick_name, tv_amt_doller, tv_amt_rupee,tv_date_time;

        public MyViewHolder(View view) {
            super(view);
            tv_nick_name = (TextView) view.findViewById(R.id.tv_nick_name);
            tv_amt_doller = (TextView) view.findViewById(R.id.tv_amt_doller);
            tv_amt_rupee = (TextView) view.findViewById(R.id.tv_amt_rupee);
            tv_date_time = (TextView) view.findViewById(R.id.tv_date_time);
        }
    }


    public BidHistory_Adpter(List<Bid_History_Model> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_single_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Bid_History_Model movie = moviesList.get(position);
        holder.tv_nick_name.setText(movie.getStr_nickname());
        holder.tv_amt_doller.setText(movie.getStr_price_doller());
        holder.tv_amt_rupee.setText(movie.getStr_price_rupee());
        holder.tv_date_time.setText(movie.getStr_timedate());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
