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
private  MainActivity mainActivity;

    public SpecialistGridAdapter(Context c, List<ItemSpecialist> AppsList)
    {
        mContext = c;
       this.AppsList=AppsList;
//        mainActivity = (MainActivity) mContext;


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

//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int width = displaymetrics.widthPixels;

        if (convertView == null)
        {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.list_specialist, null);
           // TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView country_photo = (ImageView)grid.findViewById(R.id.country_photo);
            TextView tv_title = (TextView) grid.findViewById(R.id.tv_title);
            TextView tv_post = (TextView) grid.findViewById(R.id.tv_post);
            ImageView img_email = (ImageView) grid.findViewById(R.id.img_email);
//            CardView card_specialist = (CardView) grid.findViewById(R.id.card_specialist);
            LinearLayout card_view = (LinearLayout) grid.findViewById(R.id.card_view);

//            int buttonWidth = width/2;
//            card_view.setMinimumWidth(buttonWidth);

           // textView.setText(apps.getAppTitle());

          /*  String strProductImagePath = Application_Constants.APP_IMAGE_PATH;
            String strProductImage = apps.getAppLogo();*/
            tv_title.setText(apps.getTitle());
            tv_post.setText(apps.getPost());
            country_photo.setImageResource(apps.getPhoto());

            img_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent email = new Intent(Intent.ACTION_SEND);
//                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{apps.getEmmailId()});
//                    email.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from Astaguru app");
//                    email.putExtra(Intent.EXTRA_TEXT, "message");
//                    email.setType("message/rfc822");
//                    email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(Intent.createChooser(email, "Choose an Email client :"));

                    String[] TO = {apps.getEmmailId()};
                    String[] CC = {""};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                    try {
                        mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


//            protected void sendEmail() {
//            String[] TO = {email_id};
//            String[] CC = {""};
//            Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//            emailIntent.setData(Uri.parse("mailto:"));
//            emailIntent.setType("text/plain");
//            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//            emailIntent.putExtra(Intent.EXTRA_CC, CC);
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
//            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
//
//            try {
//                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
//            }
//        }

           // imageView.setImageResource(R.drawable.hannibal);
            /*Picasso.with(mContext).load(strProductImagePath + strProductImage)
                    .into(imageView);*/

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}