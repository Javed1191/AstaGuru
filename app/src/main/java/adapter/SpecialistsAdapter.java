package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infomanav.astaguru.ItemManagment;
import com.infomanav.astaguru.ItemSpecialist;
import com.infomanav.astaguru.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpecialistsAdapter extends RecyclerView.Adapter<SpecialistsViewHolders> {

    private List<ItemSpecialist> itemList;
    private Context context;

    public SpecialistsAdapter(Context context, List<ItemSpecialist> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SpecialistsViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_specialist, null);
        SpecialistsViewHolders rcv = new SpecialistsViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SpecialistsViewHolders holder, int position)
    {
        holder.tv_title.setText(itemList.get(position).getTitle());
        holder.tv_post.setText(itemList.get(position).getPost());
        Picasso.with(context).load(itemList.get(position).getPhoto()).placeholder(R.drawable.img_default)
                .into( holder.countryPhoto);

       // holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
