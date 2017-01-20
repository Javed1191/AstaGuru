package com.infomanav.astaguru;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;

import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.HomeTabAdapter;
import services.Application_Constants;
import services.SessionData;
import services.Utility;


public class FragmentHomeTab extends Fragment
{


	View view;
	private Utility utility;
	private TextView tv_action_title;
	private String jobId;
	View view1,view2,view3,view4;
	Context context;
	SessionData data;
	String url_string;
	TabLayout tabLayout;
	public FragmentHomeTab()
	{
	}


	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);

		view=inflater.inflate(R.layout.fragment_home_tab,container, false);

		context = getActivity();
		data = new SessionData(context);
		if (data.getObjectAsString("Filter_Data").equals("yes"))
		{

			Intent intent  = getActivity().getIntent();
			if(intent !=null)
				if(intent.hasExtra("artistid")){
					 url_string = intent.getStringExtra("artistid");




				}

		}





		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

		toolbar.setTitle("");

		view1 = view.findViewById(R.id.view1);
		view2 = view.findViewById(R.id.view2);
		view3 = view.findViewById(R.id.view3);
		view4 = view.findViewById(R.id.view4);


		tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
		tabLayout.addTab(tabLayout.newTab().setText("Home"));
		tabLayout.addTab(tabLayout.newTab().setText("Current"));
		tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
		tabLayout.addTab(tabLayout.newTab().setText("Past"));


		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


		//tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
		// tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);




		final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
		FragmentManager fm = getActivity().getSupportFragmentManager();
		final HomeTabAdapter adapter = new HomeTabAdapter(context,fm, tabLayout.getTabCount(),url_string);
		viewPager.setAdapter(adapter);
		changeTabsFont();
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


		LinearLayout linearLayout = (LinearLayout)tabLayout.getChildAt(0);
		linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(Color.GRAY);
		drawable.setSize(1, 1);
		linearLayout.setDividerPadding(10);
		linearLayout.setDividerDrawable(drawable);

//		for (int i = 0; i < tabLayout.getTabCount(); i++) {
//			TabLayout.Tab tab = tabLayout.getTabAt(i);
//			RelativeLayout relativeLayout = (RelativeLayout)
//					LayoutInflater.from(context).inflate(R.layout.tab_layout, tabLayout, false);
//
//			TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
//			tabTextView.setText(tab.getText());
//			tab.setCustomView(relativeLayout);
//			tab.select();
//		}

		data = new SessionData(context);
		if (data.getObjectAsString("Filter_Data").equals("yes"))
		{

		   viewPager.setCurrentItem(1);

		}


		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{

			}

			@Override
			public void onPageSelected(int position)
			{
				switch(position){

					case 0:


						view1.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;

					case 1:


						view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;

					case 2:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;
					case 3:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						break;


					default:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;
				}
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});

		tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab)
			{
				viewPager.setCurrentItem(tab.getPosition());

				if (tab.getPosition() == 0)
				{
					TextView toolbarTextView  = (TextView) ((getActivity()).findViewById(R.id.tool_text));
					toolbarTextView.setVisibility(View.GONE);
					ImageView iv_logo  = (ImageView) ((getActivity()).findViewById(R.id.iv_logo));
					iv_logo.setVisibility(View.VISIBLE);


				}
				if (tab.getPosition() == 1)
				{

					ImageView iv_logo  = (ImageView) ((getActivity()).findViewById(R.id.iv_logo));
					iv_logo.setVisibility(View.GONE);
					TextView toolbarTextView  = (TextView) ((getActivity()).findViewById(R.id.tool_text));

					toolbarTextView.setText("Current Auction");
					toolbarTextView.setVisibility(View.VISIBLE);


				}

				if (tab.getPosition() == 2)
				{
					ImageView iv_logo  = (ImageView) ((getActivity()).findViewById(R.id.iv_logo));
					iv_logo.setVisibility(View.GONE);
					TextView toolbarTextView  = (TextView) ((getActivity()).findViewById(R.id.tool_text));
					toolbarTextView.setText("Upcoming Auction");
					toolbarTextView.setVisibility(View.VISIBLE);

				}

				if (tab.getPosition() == 3)
				{
					ImageView iv_logo  = (ImageView) ((getActivity()).findViewById(R.id.iv_logo));
					iv_logo.setVisibility(View.GONE);
					TextView toolbarTextView  = (TextView) ((getActivity()).findViewById(R.id.tool_text));
					toolbarTextView.setText("Past Auction");
					toolbarTextView.setVisibility(View.VISIBLE);

				}



			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}

	private void changeTabsFont() {
		Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"WorkSans-Medium.otf");

		ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
		int tabsCount = vg.getChildCount();
		for (int j = 0; j < tabsCount; j++) {
			ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
			int tabChildsCount = vgTab.getChildCount();
			for (int i = 0; i < tabChildsCount; i++) {
				View tabViewChild = vgTab.getChildAt(i);

				if (tabViewChild instanceof TextView) {
					((TextView) tabViewChild).setTypeface(type);
					((TextView) tabViewChild).setTextSize(11);



				}
			}
		}
	}


}
