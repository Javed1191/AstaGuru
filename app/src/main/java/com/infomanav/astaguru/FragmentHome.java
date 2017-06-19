package com.infomanav.astaguru;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter.NotificationAdapter;
import model_classes.DemoData;
import model_classes.Model_Notification;
import model_classes.Second_img_Model;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;
import views.PageIndicator;


public class FragmentHome extends Fragment
{

//	http://stackoverflow.com/questions/26458919/integrating-youtube-to-fragment

	private static final String API_KEY = "AIzaSyB_bApGLgdJKI_9HUq2DvSHM5oYijsm5ag";

	// YouTubeのビデオID
	private static String VIDEO_ID = "yehQjcfMuok";
	View view;
	public SliderLayout image_slider1,image_slider2,image_video_slider;
	public TextView tv_title,tv_date;
	public List<String> list_title,list_date;
	public PagerIndicator pagerIndicator,pagerIndicator2,custom_indicator_video;
	MainActivity mainActivity;
	Context context;
	private Utility utility;
	ImageView iv_second_img,iv_four_img,iv_img_third_two,iv_img_third_one;
	 HashMap<String,String> file_maps,file_maps2,map_video;
	private VideoView videoView;
	private MediaController mController;
	private Uri uriYouTube;
	private ViewPager mViewPager;

	private ViewPager mViewPageryoutube;
	PageIndicator indicatoryoutube;
	DefaultSliderView defaultSliderView1, defaultSliderView2,videoSliderView;
	TextView tv_record;
	PageIndicator mIndicator;
	LinearLayout lin_recordprice;
	SessionData data;
	ArrayList<String> list_video;
	String str_tittle,str_img_url,str_date,str_img_second,str_img_four,str_img_third_one,str_img_third_two,str_btitle,
			str_b_url,str_bdate,str_you_tube_video,livecount,auctionPageUrl,urlID,Auctionname;

	private ViewPager awesomePager;
	private static int NUM_AWESOME_VIEWS = 20;
	LayoutInflater inflater ;
	private static final int RECOVERY_REQUEST = 1;
	private YouTubePlayerView youTubeView;
	private List<String> list_auctionPageUrl_banner1,list_urlID_banner1,list_auctionPageUrl_banner2,list_urlID_banner2,
			list_Auctionname_banner1,list_Auctionname_banner2;

	// Testing you tube view pager
	private List<DemoData> mDemoData = new ArrayList<DemoData>();
	private DemoFragmentAdapter mAdapter;

	public FragmentHome()
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

		view=inflater.inflate(R.layout.fragment_home,container, false);
		setHasOptionsMenu(true);

		mainActivity = (MainActivity) getActivity();
		context =getActivity();
		utility = new Utility(context);
        data=new SessionData(context);

		file_maps = new HashMap<String, String>();
		file_maps2= new HashMap<String, String>();
		map_video = new HashMap<String, String>();
		// status bar color change
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getActivity().getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
		}

		list_video = new ArrayList<String>();
		image_slider1 = (SliderLayout) view.findViewById(R.id.image_slider1);
		image_slider2 = (SliderLayout) view.findViewById(R.id.image_slider2);
		image_video_slider = (SliderLayout) view.findViewById(R.id.image_video_slider);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_date= (TextView) view.findViewById(R.id.tv_date);
		tv_record= (TextView) view.findViewById(R.id.tv_record);
//		iv_second_img= (ImageView) view.findViewById(R.id.iv_second_img);
		iv_four_img= (ImageView) view.findViewById(R.id.iv_four_img);
		iv_img_third_one= (ImageView) view.findViewById(R.id.iv_img_third_one);
		iv_img_third_two= (ImageView) view.findViewById(R.id.iv_img_third_two);
		lin_recordprice= (LinearLayout) view.findViewById(R.id.lin_recordprice);
		mainActivity = (MainActivity) getActivity();
		defaultSliderView2 = new DefaultSliderView(getActivity());
		defaultSliderView1 = new DefaultSliderView(getActivity());
		videoSliderView = new DefaultSliderView(getActivity());


		//YouTubePlayerFragment youtubePlayerFragment = new YouTubePlayerFragment();


		/*String frameVideo = "<html><body>Video From YouTube<br><iframe width=\"420\" height=\"315\" src=\"https://www.youtube.com/embed/yehQjcfMuok\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

		WebView displayYoutubeVideo = (WebView) view.findViewById(R.id.mWebView);
		displayYoutubeVideo.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}
		});
		WebSettings webSettings = displayYoutubeVideo.getSettings();
		webSettings.setJavaScriptEnabled(true);
		displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
*/


		//loginWithCredentials();

		getHomeBanner();


		Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"EBGaramond12-Regular.ttf");
		tv_record.setTypeface(type);
		pagerIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
		pagerIndicator2= (PagerIndicator) view.findViewById(R.id.custom_indicator2);
		custom_indicator_video= (PagerIndicator) view.findViewById(R.id.custom_indicator_video);

		/*mViewPageryoutube = (ViewPager) view.findViewById(R.id.mViewPageryoutube);

		mDemoData.add(new DemoData("81_a8dbE0UE"));
		mDemoData.add(new DemoData("JVJxXgzsvxg"));

		// Testing you tube start
		mAdapter = new DemoFragmentAdapter(getActivity().getSupportFragmentManager());

		mViewPageryoutube.setAdapter(mAdapter);


		mAdapter.notifyDataSetChanged();*/



		list_title = new ArrayList<>();
		list_date = new ArrayList<>();

		lin_recordprice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,Past_Auction_SubActivity.class);
				intent.putExtra("type","artwork");
				intent.putExtra("auction","past");

				startActivity(intent);
			}
		});
		/*mViewPageryoutube.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		mViewPageryoutube.beginFakeDrag();
		mViewPageryoutube.endFakeDrag();*/

		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		image_slider1.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position)
			{


				/*try
				{
					tv_title.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in));
					tv_date.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in));
					tv_title.setText(list_title.get(position));
					tv_date.setText(list_date.get(position));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}*/


			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});


		/*image_video_slider.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//Log.d("MyActivity", "index selected:" + mySliderLayout.getCurrentPosition());
				Toast.makeText(getActivity(), "index selected:" + image_video_slider.getCurrentPosition(), Toast.LENGTH_SHORT).show();
			}
		});*/



		return view;
	}

	public static String extractYTId(String url) {
		String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
		String str_code="";
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.
		if (matcher.find()) {
			str_code =  matcher.group();
		}
		return  str_code;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{


		return super.onOptionsItemSelected(item);
	}

	private void loginWithCredentials () {

		if (utility.checkInternet()) {

//			hud.show();

			String URL = Application_Constants.Main_URL+"home_banner?api_key="+ Application_Constants.API_KEY;


			JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, URL, null,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							Log.d("log", response.toString());
//							hud.dismiss();
							List<Second_img_Model> products = new ArrayList<Second_img_Model>();
							String str_json = response.toString();
							videoSliderView = new DefaultSliderView(getActivity());
							JSONObject userObject = null;
							try {
								if (response.getJSONArray("resource").length() == 0) {
									Toast.makeText(context,"Invalid Username or Password ",Toast.LENGTH_LONG).show();
								} else {
									try {
										if (str_json != null)
										{
											JSONObject jobject = new JSONObject(str_json);
											JSONArray jsonArray = new JSONArray();
											if (jobject.length()>0)
											{

												jsonArray = jobject.getJSONArray("resource");

												for(int i=0; i<jsonArray.length();i++)
												{
													JSONObject Obj = jsonArray.getJSONObject(i);

													if (Obj.getString("type").equalsIgnoreCase("1"))
													{
														 str_tittle =Obj.getString("homebannerTitle");
														 str_img_url =Obj.getString("homebannerImg");
														str_date =Obj.getString("dateAdded");

														list_date.add(str_date);
														list_title.add(str_tittle);

														file_maps.put(str_tittle,str_img_url);
													}

													if (Obj.getString("type").equalsIgnoreCase("2")) {
														str_b_url = Obj.getString("homebannerImg");
														file_maps2.put("test" + i, str_b_url);
													}
														if (Obj.getString("type").equalsIgnoreCase("4"))
													{
														str_img_four = Obj.getString("homebannerImg");

													}
													if (Obj.getString("type").equalsIgnoreCase("3"))
													{
														str_img_third_one = Obj.getString("homebannerImg");
														str_img_third_two = Obj.getString("homebannerImg");

													}
													if (Obj.getString("type").equalsIgnoreCase("5"))
													{
														String str_tittle = Obj.getString("homebannerTitle");
														str_you_tube_video = Obj.getString("homebannerImg");
														str_you_tube_video = extractYTId(str_you_tube_video);
														String img_url="http://img.youtube.com/vi/"+str_you_tube_video+"/0.jpg";
														map_video.put(str_tittle+i,img_url);
														list_video.add(str_you_tube_video);

													}
												}

												Picasso.with(getContext()).load(str_img_four).into(iv_four_img);
												Picasso.with(getContext()).load(str_img_third_one).into(iv_img_third_one);
												Picasso.with(getContext()).load(str_img_third_two).into(iv_img_third_two);

												for(String name : file_maps.keySet())
												{
													DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
													defaultSliderView.image(file_maps.get(name))
															.setScaleType(BaseSliderView.ScaleType.Fit);
													image_slider1.addSlider(defaultSliderView);
												}

												for(String name : file_maps2.keySet())
												{
													defaultSliderView2 = new DefaultSliderView(getActivity());
													defaultSliderView2.image(file_maps2.get(name))
															.setScaleType(BaseSliderView.ScaleType.CenterCrop);

													defaultSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
														@Override
														public void onSliderClick(BaseSliderView slider) {
															data.setObjectAsString("slider","true");
															FragmentHomeTab newFragment = new FragmentHomeTab();
															android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
															transaction.replace(R.id.frame, newFragment);
															transaction.addToBackStack(null);
															transaction.commit();
														}
													});

													image_slider2.addSlider(defaultSliderView2);
												}
												for(String name : map_video.keySet())
												{
													//String str = map_video.get(name);

													videoSliderView = new DefaultSliderView(getActivity());

													videoSliderView.image(map_video.get(name))
															.setScaleType(BaseSliderView.ScaleType.CenterCrop);

													videoSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
														@Override
														public void onSliderClick(BaseSliderView slider) {
															Intent intent = new Intent(getActivity(),YoutubeDialogActivity.class);
															intent.putExtra("video_id",list_video.get(image_video_slider.getCurrentPosition()));
															startActivity(intent);
														}
													});
													image_video_slider.addSlider(videoSliderView);
												}

												image_slider1.setPresetTransformer(SliderLayout.Transformer.Default);
												image_slider1.setCustomAnimation(new DescriptionAnimation());
												image_slider1.setDuration(4000);
												image_slider1.setCustomIndicator(pagerIndicator);

												image_slider2.setPresetTransformer(SliderLayout.Transformer.Default);
												image_slider2.setCustomAnimation(new DescriptionAnimation());
												image_slider2.setDuration(5000);
												image_slider2.setCustomIndicator(pagerIndicator2);

												image_video_slider.setPresetTransformer(SliderLayout.Transformer.Default);
												image_video_slider.setCustomAnimation(new DescriptionAnimation());
												image_video_slider.setDuration(5000);
												image_video_slider.setCustomIndicator(custom_indicator_video);

											}
											else
											{
												Toast.makeText(getActivity(), "This may be server issue", Toast.LENGTH_SHORT).show();
											}
										} else {
											Toast.makeText(getActivity(), "This may be server issue", Toast.LENGTH_SHORT).show();
										}

									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					VolleyLog.d("test", "Error: " + error.getMessage());
					VolleyLog.e("Error: ", error.getMessage());

				}
			}) {
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					HashMap<String, String> headers = new HashMap<String, String>();
					headers.put("Content-Type", "application/json; charset=utf-8");
					return headers;
				}
			};

			RequestQueue requestQueue = Volley.newRequestQueue(context);
			requestQueue.add(jsonObjReq);
		}
	}

	private void getHomeBanner() {

		if (utility.checkInternet()) {
			String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spGetHomeBanner?api_key="+ Application_Constants.API_KEY;
			System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
			final Map<String, String> params = new HashMap<String, String>();
			final ArrayList<Model_Notification> countryList = new ArrayList<Model_Notification>();
			list_auctionPageUrl_banner1 = new ArrayList<>();
			list_urlID_banner1 = new ArrayList<>();

			list_auctionPageUrl_banner2 = new ArrayList<>();
			list_urlID_banner2 = new ArrayList<>();
			list_Auctionname_banner1 = new ArrayList<>();
			list_Auctionname_banner2= new ArrayList<>();


			ServiceHandler serviceHandler = new ServiceHandler(getActivity());


			serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("result" + result);

					String NotificationID="",IsRead="",NotificationBody="",CreatedDate="";

					try {
						if (result != null)
						{
							JSONArray jArray = new JSONArray(result);
							for (int i = 0; i < jArray.length(); i++)
							{
								JSONObject Obj = jArray.getJSONObject(i);

								if (Obj.getString("type").equalsIgnoreCase("1"))
								{
									str_tittle =Obj.getString("homebannerTitle");
									str_img_url =Obj.getString("homebannerImg");
									str_date =Obj.getString("dateAdded");
									livecount =Obj.getString("livecount");
									list_date.add(str_date);
									list_title.add(str_tittle);

									auctionPageUrl = Obj.getString("auctionPageUrl");
									urlID = Obj.getString("urlID");
									Auctionname = Obj.getString("Auctionname");

									list_auctionPageUrl_banner1.add(auctionPageUrl);
									list_urlID_banner1.add(urlID);
									list_Auctionname_banner1.add(Auctionname);


									file_maps.put(str_tittle,str_img_url);
								}

								if (Obj.getString("type").equalsIgnoreCase("2"))
								{
									str_b_url = Obj.getString("homebannerImg");
									auctionPageUrl = Obj.getString("auctionPageUrl");
									urlID = Obj.getString("urlID");
									Auctionname = Obj.getString("Auctionname");
									list_auctionPageUrl_banner2.add(auctionPageUrl);
									list_urlID_banner2.add(urlID);
									list_Auctionname_banner2.add(Auctionname);

									file_maps2.put("test" + i, str_b_url);



								}
								if (Obj.getString("type").equalsIgnoreCase("4"))
								{
									str_img_four = Obj.getString("homebannerImg");
									livecount =Obj.getString("livecount");

								}
								if (Obj.getString("type").equalsIgnoreCase("3"))
								{
									str_img_third_one = Obj.getString("homebannerImg");
									str_img_third_two = Obj.getString("homebannerImg");
									livecount =Obj.getString("livecount");

								}
								if (Obj.getString("type").equalsIgnoreCase("5"))
								{
									String str_tittle = Obj.getString("homebannerTitle");
									str_you_tube_video = Obj.getString("homebannerImg");
									livecount =Obj.getString("livecount");
									str_you_tube_video = extractYTId(str_you_tube_video);
									String img_url="http://img.youtube.com/vi/"+str_you_tube_video+"/0.jpg";
									map_video.put(str_tittle+i,img_url);
									list_video.add(str_you_tube_video);

								}


							}

							Picasso.with(getContext()).load(str_img_four).into(iv_four_img);
							Picasso.with(getContext()).load(str_img_third_one).into(iv_img_third_one);
							Picasso.with(getContext()).load(str_img_third_two).into(iv_img_third_two);

							for(String name : file_maps.keySet())
							{
								defaultSliderView1 = new DefaultSliderView(getActivity());
								defaultSliderView1.image(file_maps.get(name))
										.setScaleType(BaseSliderView.ScaleType.Fit);


								defaultSliderView1.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener()
								{
									@Override
									public void onSliderClick(BaseSliderView slider)
									{
										//data.setObjectAsString("slider","true");
										/*FragmentHomeTab newFragment = new FragmentHomeTab();
										android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
										transaction.replace(R.id.frame, newFragment);
										transaction.addToBackStack(null);
										transaction.commit();*/

										String auctiontype = list_auctionPageUrl_banner1.get(image_slider1.getCurrentPosition());
										String auctiontype1 = list_Auctionname_banner1.get(image_slider1.getCurrentPosition());

										if(auctiontype.equals("Past"))
										{
											Intent intent = new Intent(getActivity(),Past_Auction_SubActivity.class);
											intent.putExtra("str_auction",list_Auctionname_banner1.get(image_slider1.getCurrentPosition()));
											intent.putExtra("str_id",list_urlID_banner1.get(image_slider1.getCurrentPosition()));
											intent.putExtra("auction",list_auctionPageUrl_banner1.get(image_slider1.getCurrentPosition()));
											startActivity(intent);
										}
										else if(auctiontype.equals("Upcomming"))
										{
											Intent intent = new Intent(getActivity(),Past_Auction_SubActivity.class);
											intent.putExtra("str_auction",list_Auctionname_banner1.get(image_slider1.getCurrentPosition()));
											intent.putExtra("str_id",list_urlID_banner1.get(image_slider1.getCurrentPosition()));
											intent.putExtra("auction",list_auctionPageUrl_banner1.get(image_slider1.getCurrentPosition()));
											startActivity(intent);
										}
										else
										{
											Bundle bundle = new Bundle();
											bundle.putString("fragment", "current");
											FragmentHomeTab newFragment = new FragmentHomeTab();
											newFragment.setArguments(bundle);
											android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
											transaction.replace(R.id.frame, newFragment);
											transaction.addToBackStack(null);
											transaction.commit();

										}



									}
								});

								image_slider1.addSlider(defaultSliderView1);
							}

							for(String name : file_maps2.keySet())
							{
								defaultSliderView2 = new DefaultSliderView(getActivity());
								defaultSliderView2.image(file_maps2.get(name))
										.setScaleType(BaseSliderView.ScaleType.CenterCrop);

								defaultSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener()
								{
									@Override
									public void onSliderClick(BaseSliderView slider)
									{
										//data.setObjectAsString("slider","true");
										/*FragmentHomeTab newFragment = new FragmentHomeTab();
										android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
										transaction.replace(R.id.frame, newFragment);
										transaction.addToBackStack(null);
										transaction.commit();*/

										String auctiontype = list_auctionPageUrl_banner2.get(image_slider2.getCurrentPosition());
										String auctiontype1 = list_Auctionname_banner2.get(image_slider2.getCurrentPosition());

										if(auctiontype.equalsIgnoreCase("Past"))
										{
											Intent intent = new Intent(getActivity(),Past_Auction_SubActivity.class);
											intent.putExtra("str_auction",list_Auctionname_banner2.get(image_slider2.getCurrentPosition()));
											intent.putExtra("str_id",list_urlID_banner2.get(image_slider2.getCurrentPosition()));
											intent.putExtra("auction",list_auctionPageUrl_banner2.get(image_slider2.getCurrentPosition()));
											startActivity(intent);
										}
										else if(auctiontype.equalsIgnoreCase("Upcomming"))
										{
											Intent intent = new Intent(getActivity(),Past_Auction_SubActivity.class);
											intent.putExtra("str_auction",list_Auctionname_banner2.get(image_slider2.getCurrentPosition()));
											intent.putExtra("str_id",list_urlID_banner2.get(image_slider2.getCurrentPosition()));
											intent.putExtra("auction",list_auctionPageUrl_banner2.get(image_slider2.getCurrentPosition()));
											startActivity(intent);
										}
										else
										{
											Bundle bundle = new Bundle();
											bundle.putString("fragment", "current");
											FragmentHomeTab newFragment = new FragmentHomeTab();
											newFragment.setArguments(bundle);
											android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
											transaction.replace(R.id.frame, newFragment);
											transaction.addToBackStack(null);
											transaction.commit();

										}



									}
								});

								image_slider2.addSlider(defaultSliderView2);
							}
							for(String name : map_video.keySet())
							{
								//String str = map_video.get(name);

								videoSliderView = new DefaultSliderView(getActivity());

								videoSliderView.image(map_video.get(name))
										.setScaleType(BaseSliderView.ScaleType.CenterCrop);

								videoSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
									@Override
									public void onSliderClick(BaseSliderView slider) {
										Intent intent = new Intent(getActivity(),YoutubeDialogActivity.class);
										intent.putExtra("video_id",list_video.get(image_video_slider.getCurrentPosition()));
										startActivity(intent);
									}
								});
								image_video_slider.addSlider(videoSliderView);
							}

							image_slider1.setPresetTransformer(SliderLayout.Transformer.Default);
							image_slider1.setCustomAnimation(new DescriptionAnimation());
							image_slider1.setDuration(4000);
							image_slider1.setCustomIndicator(pagerIndicator);

							image_slider2.setPresetTransformer(SliderLayout.Transformer.Default);
							image_slider2.setCustomAnimation(new DescriptionAnimation());
							image_slider2.setDuration(5000);
							image_slider2.setCustomIndicator(pagerIndicator2);

							image_video_slider.setPresetTransformer(SliderLayout.Transformer.Default);
							image_video_slider.setCustomAnimation(new DescriptionAnimation());
							image_video_slider.setDuration(5000);
							image_video_slider.setCustomIndicator(custom_indicator_video);

						}
					}
					catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
			});
		}

	}

	class Adapteryoutube extends FragmentStatePagerAdapter
	{

		private String[] getVids()
		{
			return new String[]{"6BP8dyxncuc","yehQjcfMuok"};
		}

		public Adapteryoutube(final FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public android.support.v4.app.Fragment getItem(final int position)
		{
			YouTubePlayerSupportFragment fragment = new YouTubePlayerSupportFragment();
			fragment.initialize("AIzaSyB_bApGLgdJKI_9HUq2DvSHM5oYijsm5ag", new YouTubePlayer.OnInitializedListener()

			{
				@Override
				public void onInitializationSuccess(final YouTubePlayer.Provider provider,
													final YouTubePlayer youTubePlayer,
													final boolean b)
				{

					list_video.add("6BP8dyxncuc");
					list_video.add("yehQjcfMuok");
					//youTubePlayer.cueVideos(list_video);

					if (!b)
					{
						//gblyouTubePlayer = youTubePlayer;

						//youTubePlayer.loadVideo(getVids()[position]);
						//youTubePlayer.cueVideo(getVids()[position]);
						//youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
						youTubePlayer.cueVideos(list_video);


					}
//					youTubePlayer.cueVideo(getVids()[position]);

//					if (!b)
//					{
//
//
//						youTubePlayer.loadVideo(getVids()[position]);
//
//
//					}

				}

				@Override
				public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
				{

				}


			});


			return fragment;
		}

		@Override
		public int getCount()
		{
			return getVids().length;
		}

		@Override
		public CharSequence getPageTitle(final int position)
		{
			System.out.println("position" + position);

			return getVids()[position] + " [" + position + "]";
		}
	}

	// Testting youtube view pager

	private class DemoFragmentAdapter extends FragmentPagerAdapter {
		public DemoFragmentAdapter(FragmentManager fm) {
			super(fm); // super tracks this
		}

		@Override
		public Fragment getItem(int position) {
			return VideoFragment.newInstance(mDemoData.get(position));
		}

		@Override
		public int getCount() {
			return mDemoData.size();
		}
	}

}
