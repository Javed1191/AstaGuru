package adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infomanav.astaguru.ItemSpecialist;
import com.infomanav.astaguru.MainActivity;
import com.infomanav.astaguru.R;
import com.infomanav.astaguru.UpcomingAuction;

import java.util.List;

import services.Application_Constants;

public class SpecialistGridAdapter extends BaseAdapter{
    private Context mContext;
    private List<ItemSpecialist> AppsList;

    public SpecialistGridAdapter(Context c, List<ItemSpecialist> AppsList)
    {
        mContext = c;
       this.AppsList=AppsList;

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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ItemSpecialist apps = AppsList.get(position);


        if (convertView == null)
        {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.list_specialist, null);

            ImageView country_photo = (ImageView)grid.findViewById(R.id.country_photo);
            TextView tv_title = (TextView) grid.findViewById(R.id.tv_title);
            TextView tv_post = (TextView) grid.findViewById(R.id.tv_post);
            ImageView img_email = (ImageView) grid.findViewById(R.id.img_email);




            tv_title.setText(apps.getTitle());
            tv_post.setText(apps.getPost());
            country_photo.setImageResource(apps.getPhoto());

            img_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Roshan

                    /*String[] TO = {apps.getEmmailId()};
                    String[] CC = {""};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from astaguru app");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, "+apps.getTitle());

                    try {
                        mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }*/

                    // new
                    if(!apps.getEmmailId().isEmpty())
                    {
                        Intent intent = new Intent(Intent.ACTION_SEND);

                        String[] strTo = {apps.getEmmailId()};

                        intent.putExtra(Intent.EXTRA_EMAIL, strTo);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
             /*   Uri attachments = Uri.parse(image_path);
                intent.putExtra(Intent.EXTRA_STREAM, attachments);*/

                        intent.setType("message/rfc822");

                        intent.setPackage("com.google.android.gm");

                        mContext.startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(mContext, "There is no email id with this contact.", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}