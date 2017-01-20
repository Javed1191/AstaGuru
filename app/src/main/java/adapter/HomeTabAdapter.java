package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.infomanav.astaguru.FragmentCurrentAuction;
import com.infomanav.astaguru.FragmentHome;
import com.infomanav.astaguru.FragmentPast;
import com.infomanav.astaguru.FragmentUpcoming;

import services.SessionData;


/**
 * Created by USER on 24-12-2015.
 */
public class HomeTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String url_string;

    SessionData data;
    Context context;
    FragmentCurrentAuction fragmentCurrent;
    public HomeTabAdapter(Context context,FragmentManager fm, int NumOfTabs,String url_string)
    {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.url_string=url_string;
        this.context=context;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentHome fragmentHome = new FragmentHome();
                return fragmentHome;
            case 1:

                data = new SessionData(context);
                if (data.getObjectAsString("Filter_Data").equals("yes"))
                {

                     fragmentCurrent = new FragmentCurrentAuction();
                    Bundle bundle = new Bundle();
                    bundle.putString("url_string", url_string);

                    System.out.println("int_stradpter" + url_string);
                    fragmentCurrent.setArguments(bundle);

                }
                else
                {
                    fragmentCurrent = new FragmentCurrentAuction();
                }





                return fragmentCurrent;
            case 2:
                FragmentUpcoming fragmentUpcoming = new FragmentUpcoming();
                return fragmentUpcoming;
            case 3:
                FragmentPast fragmentPast = new FragmentPast();
                return fragmentPast;

            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}