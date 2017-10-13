package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
    private String url_string,fragment,key,auction,type;
    SessionData data;
    Context context;
    FragmentCurrentAuction fragmentCurrent;
    private TabLayout tabLayout;
    public HomeTabAdapter(Context context,FragmentManager fm, int NumOfTabs,String url_string,String fragment,String key,String auction, String type)
    {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.url_string=url_string;
        this.context=context;
        this.fragment=fragment;
        this.key=key;
        this.auction=auction;
        this.type=type;


    }
    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentHome fragmentHome = new FragmentHome();
                return fragmentHome;
            case 1:

                data = new SessionData(context);

                fragmentCurrent = new FragmentCurrentAuction();
                Bundle bundle = new Bundle();
                bundle.putString("url_string", url_string);
                bundle.putString("type",type);
                bundle.putString("fragment", fragment);
                bundle.putString("key", key);
                bundle.putString("auction", auction);
                System.out.println("int_stradpter" + url_string);
                fragmentCurrent.setArguments(bundle);

                // this is becoouse when we are comming from search to current fragment than it get arguments what we haev sent in search and it always show that search result only
                type = "current";

               /* if (data.getObjectAsString("Filter_Data").equals("yes"))
                {
                    fragmentCurrent = new FragmentCurrentAuction();
                    Bundle bundle = new Bundle();
                    bundle.putString("url_string", url_string);
                    bundle.putString("type",type);
                    bundle.putString("fragment", fragment);
                    bundle.putString("key", key);
                    bundle.putString("auction", auction);
                    System.out.println("int_stradpter" + url_string);
                    fragmentCurrent.setArguments(bundle);

                }
                else
                {
                    fragmentCurrent = new FragmentCurrentAuction();
                }*/

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