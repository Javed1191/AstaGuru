package com.infomanav.astaguru;


import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.CurrentAuctionAdapter_gridview;
import adapter.CurrentAuctionAdapter_listview;
import adapter.PastAuctionAdapter;
import adapter.PastAuctionListviewAdpter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Utility;


public class FragmentPast extends Fragment
{


	View view;
	ArrayList<PastAuction> appsList;
	int[] myImageList = new int[]{R.drawable.img_past_1, R.drawable.img_past_2,R.drawable.img_past_3,R.drawable.img_past_4,R.drawable.img_past_5,
			R.drawable.img_past_6};

	private String strPastAuctionUrl = Application_Constants.Main_URL+"AuctionList?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=status=past";
	MainActivity mainActivity;
	private Utility utility;
	private GridView gridview;

	AnimatorSet setRightOut, setLeftIn;
	private SwipeMenuListView mListView;
	private ImageView iv_grid, iv_list;
	private boolean list_visibile = false;

	PastAuctionListviewAdpter listViewAdapter;

	PastAuctionAdapter pastAuctionAdapter;
	Context context;
	public FragmentPast()
	{
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_past,container, false);
		mainActivity = (MainActivity) getActivity();

		utility = new Utility(getActivity());

		context = getActivity();



		gridview = (GridView) view.findViewById(R.id.gridviewpast);


		mListView = (SwipeMenuListView) view.findViewById(R.id.listviewupcomingpast);
		iv_grid = (ImageView) view.findViewById(R.id.iv_grid);
		iv_list = (ImageView) view.findViewById(R.id.iv_list);


		iv_list.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changeView();
			}
		});
		iv_grid.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changeView();
			}
		});

		// status bar color change
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getActivity().getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
		}



		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView tv =(TextView) parent.findViewById(R.id.grid_text);
				String name = appsList.get(position).getAuctionname();
				String str_id = appsList.get(position).getAuctionId();
				Intent intent = new Intent(getContext(),Past_Auction_SubActivity.class);
				intent.putExtra("str_auction",name);
				intent.putExtra("str_id",str_id);
				intent.putExtra("auction","past");
				startActivity(intent);

			}
		});
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView tv =(TextView) parent.findViewById(R.id.grid_text);
				String name = appsList.get(position).getAuctionname();
				String str_id = appsList.get(position).getAuctionId();
				Intent intent = new Intent(getContext(),Past_Auction_SubActivity.class);
				intent.putExtra("str_auction",name);
				intent.putExtra("str_id",str_id);
				startActivity(intent);

			}
		});
		/*appsList = new ArrayList<>();
		for(int i=0;i<myImageList.length;i++)
		{
			PastAuction country = new PastAuction("Morderen Indian Art",myImageList[i]);
			appsList.add(country);

		}
*/
		getPastAuction();


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
	private void getPastAuction()
	{

		if(utility.checkInternet())
		{
			//final ArrayList<Department> feedsArrayList = new ArrayList<Department>();

			final Map<String, String> params = new HashMap<String, String>();

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());


			serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("str_get_category_url Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status, str_msg;
					String  AuctionId,Auctionname,Date,	DollarRate,image,auctiondate,auctiontitle,totalSaleValueRs,totalSaleValueUs;
					appsList = new ArrayList<>();
					try {
						if (str_json != null)
						{
							JSONObject jobject = new JSONObject(str_json);
							//str_status = jobject.getString("status");
							//str_msg = jobject.getString("msg");
							if (jobject.length()>0)
							{
								JSONArray jsonArray = new JSONArray();
								jsonArray = jobject.getJSONArray("resource");

								for(int i=0; i<jsonArray.length();i++)
								{
									JSONObject Obj = jsonArray.getJSONObject(i);

									AuctionId = Obj.getString("AuctionId");
									Auctionname = Obj.getString("Auctionname");
									Date = Obj.getString("Date");
									DollarRate = Obj.getString("DollarRate");
									image = Obj.getString("image");
									auctiondate = Obj.getString("auctiondate");
									auctiontitle = Obj.getString("auctiontitle");
									totalSaleValueRs= Obj.getString("totalSaleValueRs");
											totalSaleValueUs= Obj.getString("totalSaleValueUs");
									PastAuction country = new PastAuction( AuctionId,  auctiontitle,  auctiondate,  image,  DollarRate,  Date,  Auctionname,totalSaleValueRs,totalSaleValueUs,true);
									appsList.add(country);

								}

								setAdapters();
                               /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list_name);
                                mAutoCompleteTextView.setAdapter(adapter);*/

//								gridview.setAdapter(new PastAuctionAdapter(getActivity(),appsList));
//



							}
							else
							{
								Toast.makeText(getActivity(), "Records Not Found", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(context, "Please Check Internet Connection.",Toast.LENGTH_LONG)
									.show();
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "Please Check Internet Connection.",Toast.LENGTH_LONG)
								.show();
					}
				}
			});
		}

	}
	private void changeView() {

		//if the current view is the listview, passes to gridview
		if (list_visibile) {


			mListView.setVisibility(View.GONE);
			iv_grid.setBackgroundResource(R.drawable.grid_dark);
			iv_list.setBackgroundResource(R.drawable.list_light);
			gridview.setVisibility(View.VISIBLE);
			list_visibile = false;
			setAdapters();
		} else {


			gridview.setVisibility(View.GONE);
			iv_list.setBackgroundResource(R.drawable.list_dark);
			iv_grid.setBackgroundResource(R.drawable.grid_light);
			mListView.setVisibility(View.VISIBLE);

			list_visibile = true;
			setAdapters();
		}

	}
	private void setAdapters()
	{

		if (list_visibile)
		{
//            viewview.setVisibility(View.GONE);

			listViewAdapter = new PastAuctionListviewAdpter(context, R.layout.current_listview, appsList,false);
			mListView.setAdapter(listViewAdapter);
			gridview.setLayoutAnimation(getgridlayoutAnim());
		}
		else
		{


			pastAuctionAdapter = new PastAuctionAdapter(context, R.layout.current_listview, appsList,false);
			gridview.setAdapter(pastAuctionAdapter);
			mListView.setLayoutAnimation(getgridlayoutAnim());


		}

	}



	public static LayoutAnimationController getgridlayoutAnim() {
		LayoutAnimationController controller;
		Animation anim = new Rotate3dAnimation(90f, 0f, 0.5f, 0.5f, 0.5f, false);
		anim.setDuration(500);
		controller = new LayoutAnimationController(anim, 0.1f);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		return controller;
	}
}
