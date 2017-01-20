package adapter;

/**
 * Created by fox-2 on 12/7/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infomanav.astaguru.Model_History;
import com.infomanav.astaguru.R;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Bid_History_Adpter extends RecyclerView.Adapter<Bid_History_Adpter.MyViewHolder> {

    private ArrayList<Model_History> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_nick_name, tv_amount_d, tv_amount_r,tv_datetime;

        public MyViewHolder(View view) {
            super(view);
            tv_nick_name = (TextView) view.findViewById(R.id.tv_nick_name);
            tv_amount_d = (TextView) view.findViewById(R.id.tv_amount_d);
            tv_amount_r = (TextView) view.findViewById(R.id.tv_amount_r);
            tv_datetime = (TextView) view.findViewById(R.id.tv_datetime);
        }
    }


    public Bid_History_Adpter(ArrayList<Model_History> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bid_history_single, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Model_History movie = moviesList.get(position);
        holder.tv_nick_name.setText(movie.getStr_bid_name());
        holder.tv_amount_d.setText("$"+movie.getStr_bid_d());
        holder.tv_amount_r.setText("₹"+movie.getStr_bid_r());
        holder.tv_datetime.setText(movie.getStr_bid_date());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}