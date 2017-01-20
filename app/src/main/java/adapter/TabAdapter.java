package adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.infomanav.astaguru.FragmentHome;
import com.infomanav.astaguru.FragmentPast;


/**
 * Created by USER on 24-12-2015.
 */
public class TabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String jobId;

    public TabAdapter(FragmentManager fm, int NumOfTabs)
    {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.jobId=jobId;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentHome fragmentHome = new FragmentHome();
                return fragmentHome;
            case 1:
                FragmentPast fragmentHome1 = new FragmentPast();
                return fragmentHome1;
            case 2:
                FragmentPast fragmentHome2 = new FragmentPast();
                return fragmentHome2;
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