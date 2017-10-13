package com.infomanav.astaguru;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import adapter.HomeTabAdapter;
import services.SessionData;
import services.Utility;


public class FragmentHomeTab extends Fragment
{

	private boolean isRevealEnabled = true;
	View view;
	private Utility utility;
	private TextView tv_action_title;
	private String jobId;
	View view1,view2,view3,view4,view_search,view_login;
	Context context;
	SessionData data;
	String url_string,fragment="current",type="",key="",auction="";
	TabLayout tabLayout;
	MainActivity mainActivity;
	private HomeTabAdapter adapter;
	public FragmentHomeTab()
	{
	}


	@Override
	public void onResume()
	{
		super.onResume();

		/*if (!(adapter == null)) {

			adapter.notifyDataSetChanged();


		}*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);

		view=inflater.inflate(R.layout.fragment_home_tab,container, false);

		getActivity().setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		context = getActivity();

		mainActivity = (MainActivity) getActivity();
		data = new SessionData(context);

		if(getArguments()!=null)
		{
			fragment = getArguments().getString("fragment");

			if(getArguments().getString("type")!=null)
			{
				if(getArguments().getString("type").equalsIgnoreCase("search"))
				{
					type = getArguments().getString("type");
					key = getArguments().getString("key");
					auction = getArguments().getString("auction");

				}
			}
			this.getArguments().clear();
		}


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

        LinearLayout tabHome = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_lay_home, null);
		TextView tv_tab_home;
		tv_tab_home = (TextView) tabHome.findViewById(R.id.tab);
		tv_tab_home.setText("Home");
		//tv_tab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.unread_icon, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabHome);

		LinearLayout tabCurrent = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_lay_current, null);
		TextView tv_tab;
		tv_tab = (TextView) tabCurrent.findViewById(R.id.tab);
		tv_tab.setText("Auction");
		//tv_tab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.unread_icon, 0, 0);
		tabLayout.getTabAt(1).setCustomView(tabCurrent);

		LinearLayout tabUpcoming = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_lay_home, null);
		TextView tv_tab_upcoming;
		tv_tab_upcoming = (TextView) tabUpcoming.findViewById(R.id.tab);
		tv_tab_upcoming.setText("Upcoming");
		//tv_tab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.unread_icon, 0, 0);
		tabLayout.getTabAt(2).setCustomView(tabUpcoming);

		LinearLayout tabPast = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_lay_home, null);
		TextView tv_tab_past;
		tv_tab_past = (TextView) tabPast.findViewById(R.id.tab);
		tv_tab_past.setText("Past");
		//tv_tab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.unread_icon, 0, 0);
		tabLayout.getTabAt(3).setCustomView(tabPast);


		if (data.getObjectAsString("Filter_Data").equals("yes"))
		{

			Intent intent  = getActivity().getIntent();
			if(intent !=null)
				if(intent.hasExtra("artistid")){
					 url_string = intent.getStringExtra("artistid");

					view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
					view2.setBackgroundResource( R.drawable.selector_tab_indicator_white );
					view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
					view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );

				}

		}




		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

		toolbar.setTitle("");



//		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				showIntro(mainActivity.findViewById(R.id.action_search), INTRO_CARD);
//			}
//		}, 400);


		//createTabIcons();

		final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
		FragmentManager fm = getActivity().getSupportFragmentManager();
		adapter = new HomeTabAdapter(context,fm, tabLayout.getTabCount(),url_string,fragment,key,auction,type);
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

		if(fragment.equalsIgnoreCase("current"))
		{
			//viewPager.setCurrentItem(1,true);

			new Handler().postDelayed(
					new Runnable(){
						@Override
						public void run() {
							tabLayout.getTabAt(1).select();
						}
					}, 100);

		}
		else
		{
			//viewPager.setCurrentItem(0,true);

			new Handler().postDelayed(
					new Runnable(){
						@Override
						public void run() {
							tabLayout.getTabAt(0).select();
						}
					}, 100);

		}
		if (data.getObjectAsString("slider").equals("true"))
		{

			//viewPager.setCurrentItem(2);

			new Handler().postDelayed(
					new Runnable(){
						@Override
						public void run() {
							tabLayout.getTabAt(2).select();
						}
					}, 100);

			view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
			view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
			view3.setBackgroundResource( R.drawable.selector_tab_indicator_white );
			view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
			data.setObjectAsString("slider","false");

		}


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
						adapter.notifyDataSetChanged();
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
						adapter.notifyDataSetChanged();
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



	private void createTabIcons() {

		TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
		tabOne.setText("Tab 1");
		//tabOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		tabLayout.getTabAt(0).setCustomView(tabOne);

		TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
		tabTwo.setText("Tab 2");
		//tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		tabLayout.getTabAt(1).setCustomView(tabTwo);

		TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
		tabThree.setText("Tab 3");
		//tabThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		tabLayout.getTabAt(2).setCustomView(tabThree);

		TextView tabFour = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
		tabThree.setText("Tab 4");
		//tabThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		tabLayout.getTabAt(24).setCustomView(tabFour);
	}


	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2

    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
			case R.id.action_settings:
				String status = data.getObjectAsString("login");

				if (status.equalsIgnoreCase("true"))
				{
					Intent intent = new Intent(context,MyAstaGuru_Activity.class);
					startActivity(intent);
				}
				else
				{
					Intent intent = new Intent(context,Before_Login_Activity.class);
					startActivity(intent);
				}

				return true;
			case R.id.action_search:
				Intent intent = new Intent(context,Search_Activity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}

}
