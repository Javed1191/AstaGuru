package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.infomanav.astaguru.R;

public class ManagmentViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tv_title,tv_post;
    public ImageView countryPhoto;

    public ManagmentViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        tv_title = (TextView)itemView.findViewById(R.id.tv_title);
        tv_post  = (TextView)itemView.findViewById(R.id.tv_post);
        countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}