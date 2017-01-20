package com.infomanav.astaguru;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import adapter.ManagmentAdapter;
import adapter.PastAuctionAdapter;
import adapter.RecyclerViewAdapter;
import adapter.SpecialistGridAdapter;
import adapter.SpecialistsAdapter;
import views.ExpandableHeightGridView;


public class FragmentAboutUs extends Fragment
{
	View view;
	private GridLayoutManager lLayout,lLayout1;
	MainActivity mainActivity;
	private ExpandableHeightGridView mAppsGrid;
	private SpecialistGridAdapter specialistGridAdapter;

	public FragmentAboutUs()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_about_us,container, false);
		mainActivity = (MainActivity) getActivity();


		mAppsGrid = (ExpandableHeightGridView) view.findViewById(R.id.myId);
		mAppsGrid.setExpanded(true);



		List<ItemSpecialist> rowListItem = getSpecialistList();
		specialistGridAdapter = new SpecialistGridAdapter(getActivity(),rowListItem);
		mAppsGrid.setAdapter(specialistGridAdapter);

		/*lLayout = new GridLayoutManager(getActivity(), 2);

		RecyclerView rView = (RecyclerView)view.findViewById(R.id.recycler_view);
		rView.setNestedScrollingEnabled(false);
		//rView.setHasFixedSize(true);
		rView.setLayoutManager(lLayout);

		SpecialistsAdapter rcAdapter = new SpecialistsAdapter(getActivity(), rowListItem);
		rView.setAdapter(rcAdapter);*/

		List<ItemManagment> rowListManagmentItem = getManagmentList();

		lLayout1 = new GridLayoutManager(getActivity(), 3);

		RecyclerView rView1 = (RecyclerView)view.findViewById(R.id.recycler_view1);
		rView1.setNestedScrollingEnabled(false);
		rView1.setHasFixedSize(true);
		rView1.setLayoutManager(lLayout1);

		ManagmentAdapter rcAdapter1 = new ManagmentAdapter(getActivity(), rowListManagmentItem);
		rView1.setAdapter(rcAdapter1);




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

	private List<ItemSpecialist> getSpecialistList(){

		List<ItemSpecialist> allItems = new ArrayList<ItemSpecialist>();
		allItems.add(new ItemSpecialist("Sunny Chandiramani","Client Relations", R.drawable.client_relation,""));
		allItems.add(new ItemSpecialist("Sonal Patel","Client Relations", R.drawable.client_relation_1,""));
		allItems.add(new ItemSpecialist("Sneha Gautam","Client Relations", R.drawable.client_relation_2,""));
		allItems.add(new ItemSpecialist("Mamata Rahate","Client Relations", R.drawable.client_relation_3,""));
		allItems.add(new ItemSpecialist("Sidharth Shettey","Market Analysts", R.drawable.market_analysts,""));
		allItems.add(new ItemSpecialist("Anthony Diniz","Administrator", R.drawable.administrator,""));
		allItems.add(new ItemSpecialist("Anandita De","Marketing PR and Buisness Development" ,R.drawable.marketing,""));
		allItems.add(new ItemSpecialist("Tushar Dalvi","Marketing PR and Buisness Development", R.drawable.marketing_1,""));
		allItems.add(new ItemSpecialist("Sidhant Nayangara","Content Editor", R.drawable.sidhant,""));

		return allItems;
	}

	private List<ItemManagment> getManagmentList(){

		List<ItemManagment> allItems = new ArrayList<ItemManagment>();
		allItems.add(new ItemManagment("Vickram Sethi","Chairmain", R.drawable.chairman));
		allItems.add(new ItemManagment("Tushar Sethi","CEO", R.drawable.ceo));
		allItems.add(new ItemManagment("Digamber Sethi","COO", R.drawable.coo));

		return allItems;
	}


}
