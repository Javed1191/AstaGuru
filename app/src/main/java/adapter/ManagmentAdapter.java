package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infomanav.astaguru.ItemManagment;
import com.infomanav.astaguru.ItemObject;
import com.infomanav.astaguru.R;

import java.util.List;

public class ManagmentAdapter extends RecyclerView.Adapter<ManagmentViewHolders> {

    private List<ItemManagment> itemList;
    private Context context;

    public ManagmentAdapter(Context context, List<ItemManagment> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ManagmentViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_managment, null);
        ManagmentViewHolders rcv = new ManagmentViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ManagmentViewHolders holder, int position) {
        holder.tv_title.setText(itemList.get(position).getTitle());
        holder.tv_post.setText(itemList.get(position).getPost());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
