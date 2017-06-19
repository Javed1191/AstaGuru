package com.infomanav.astaguru;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import adapter.Upcomingadpter;
import model_classes.Upcoming_Model;
import services.Application_Constants;
import services.ServiceHandler;
import services.Utility;


public class FragmentUpcoming extends Fragment
{


	View view;
	ArrayList<Upcoming_Model> appsList;
	int[] myImageList = new int[]{R.drawable.img_past_1, R.drawable.img_past_2,R.drawable.img_past_3,R.drawable.img_past_4,R.drawable.img_past_5,
			R.drawable.img_past_6};

	private String strPastAuctionUrl = Application_Constants.Main_URL+"getAuctionList?api_key="+ Application_Constants.API_KEY+"&filter=status=upcomming";
	MainActivity mainActivity;
	private Utility utility;
	private GridView gridview;
	Context context;
	private TextView tv_no_data_found;

	public FragmentUpcoming()
	{
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);

		view=inflater.inflate(R.layout.fragment_upcoming,container, false);
		mainActivity = (MainActivity) getActivity();

		gridview = (GridView) view.findViewById(R.id.gridviewupcom);
		tv_no_data_found = (TextView) view.findViewById(R.id.tv_no_data_found);

		utility = new Utility(getActivity());
		context = getActivity();


		// status bar color change
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getActivity().getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
		}


		getPastAuction();


		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final Upcoming_Model apps = appsList.get(position);

				if(Integer.parseInt(apps.getUpcomingCountVal())>0)
				{
					TextView tv =(TextView) parent.findViewById(R.id.grid_text);
					String name = appsList.get(position).getAuctionname();
					String str_id = appsList.get(position).getAuctionId();
					Intent intent = new Intent(getContext(),Past_Auction_SubActivity.class);
					intent.putExtra("str_auction",name);
					intent.putExtra("str_id",str_id);
					intent.putExtra("auction","upcomming");
					startActivity(intent);
				}



			}
		});


		return view;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{


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
					String  AuctionId,Auctionname,Date,	DollarRate,image,auctiondate,auctiontitle,upcomingCountVal = "";
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
								if(jsonArray.length()>0)
								{
									tv_no_data_found.setVisibility(View.GONE);
									gridview.setVisibility(View.VISIBLE);

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
										upcomingCountVal = Obj.getString("upcomingCountVal");

										Upcoming_Model country = new Upcoming_Model(auctiontitle,  Date,AuctionId,Auctionname,image,upcomingCountVal);
										appsList.add(country);

									}
                               /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list_name);
                                mAutoCompleteTextView.setAdapter(adapter);*/

									gridview.setAdapter(new Upcomingadpter(context,appsList));
								}
								else
								{
									tv_no_data_found.setVisibility(View.VISIBLE);
									gridview.setVisibility(View.GONE);
								}

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


}
