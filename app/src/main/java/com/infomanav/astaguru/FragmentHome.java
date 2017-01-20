package com.infomanav.astaguru;


import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import adapter.ImageSlideAdapter;
import services.Application_Constants;
import services.Utility;
import views.PageIndicator;


public class FragmentHome extends Fragment
{

//	http://stackoverflow.com/questions/26458919/integrating-youtube-to-fragment

	private static final String API_KEY = "AIzaSyB_bApGLgdJKI_9HUq2DvSHM5oYijsm5ag";

	// YouTubeのビデオID
	private static String VIDEO_ID = "yehQjcfMuok";
	View view;
	public SliderLayout image_slider1;
	public TextView tv_title,tv_date;
	public List<String> list_title,list_date;
	public PagerIndicator pagerIndicator;
	MainActivity mainActivity;
	Context context;
	private Utility utility;
	ImageView iv_second_img,iv_four_img,iv_img_third_two,iv_img_third_one;
	 HashMap<String,String> file_maps;
	private VideoView videoView;
	private MediaController mController;
	private Uri uriYouTube;
	private ViewPager mViewPager;

	private ViewPager mViewPageryoutube;
	PageIndicator indicatoryoutube;
	YouTubePlayer gblyouTubePlayer;

	TextView tv_record;
	PageIndicator mIndicator;
	List<String> list_video = new ArrayList<String>();

	String str_tittle,str_img_url,str_date,str_img_second,str_img_four,str_img_third_one,str_img_third_two,str_btitle,str_b_url,str_bdate;

//
//	private static final int RECOVERY_REQUEST = 1;
//	private YouTubePlayerView youTubeView;
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

//
//		youTubeView = (YouTubePlayerView) view.findViewById(R.id.youtube_fragment);
//		youTubeView.initialize("AIzaSyB_bApGLgdJKI_9HUq2DvSHM5oYijsm5ag", this);


		file_maps = new HashMap<String, String>();

		// status bar color change
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getActivity().getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
		}


		image_slider1 = (SliderLayout) view.findViewById(R.id.image_slider1);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_date= (TextView) view.findViewById(R.id.tv_date);
		tv_record= (TextView) view.findViewById(R.id.tv_record);
//		iv_second_img= (ImageView) view.findViewById(R.id.iv_second_img);
		iv_four_img= (ImageView) view.findViewById(R.id.iv_four_img);
		iv_img_third_one= (ImageView) view.findViewById(R.id.iv_img_third_one);
		iv_img_third_two= (ImageView) view.findViewById(R.id.iv_img_third_two);

		mainActivity = (MainActivity) getActivity();

		loginWithCredentials();


		Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"EBGaramond12-Regular.ttf");
		tv_record.setTypeface(type);
		pagerIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);

		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

		mViewPageryoutube = (ViewPager) view.findViewById(R.id.mViewPageryoutube);
		indicatoryoutube = (CirclePageIndicator) view.findViewById(R.id.indicatoryoutube);

		list_title = new ArrayList<>();
		list_date = new ArrayList<>();

		mViewPageryoutube.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0)
			{
				//gblyouTubePlayer.loadVideo(getVids()[arg0]);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{

			}
			@Override
			public void onPageScrollStateChanged(int arg0)
			{

			}
		});




		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		image_slider1.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position)
			{
				//Toast.makeText(getActivity(), "Postion is:" + position, Toast.LENGTH_SHORT).show();
				//tv_title.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_fast));

				try
				{
					tv_title.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in));
					tv_date.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in));
					tv_title.setText(list_title.get(position));
					tv_date.setText(list_date.get(position));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}


			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		return view;
	}


//	@Override
//	public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
//		if (!wasRestored) {
//			player.cueVideo("yehQjcfMuok"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
//		}
//	}
//
//	@Override
//	public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
//		if (errorReason.isUserRecoverableError()) {
//			errorReason.getErrorDialog(getActivity(), RECOVERY_REQUEST).show();
//		} else {
////			String error = String.format(getString(R.string.player_error), errorReason.toString());
////			Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
//		}
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == RECOVERY_REQUEST) {
//			// Retry initialization if user performed a recovery action
//			getYouTubePlayerProvider().initialize("AIzaSyB_bApGLgdJKI_9HUq2DvSHM5oYijsm5ag", this);
//		}
//	}
//
//	protected YouTubePlayer.Provider getYouTubePlayerProvider() {
//		return youTubeView;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{


		return super.onOptionsItemSelected(item);
	}

	private void loginWithCredentials () {

		if (utility.checkInternet()) {

//			hud.show();

			String URL = Application_Constants.Main_URL+"home_banner?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";


			JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, URL, null,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							Log.d("log", response.toString());
//							hud.dismiss();
							List<Second_img_Model> products = new ArrayList<Second_img_Model>();
							String str_json = response.toString();

							JSONObject userObject = null;
							try {
								if (response.getJSONArray("resource").length() == 0) {
									Toast.makeText(context,"Invalid Username or Password ",Toast.LENGTH_LONG).show();
								} else {
									try {
										if (str_json != null)
										{
											JSONObject jobject = new JSONObject(str_json);

											if (jobject.length()>0)
											{
												JSONArray jsonArray = new JSONArray();
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

													if (Obj.getString("type").equalsIgnoreCase("2"))
													{
														str_btitle = Obj.getString("homebannerTitle");
														str_b_url =Obj.getString("homebannerImg");
														str_bdate =Obj.getString("dateAdded");

														Second_img_Model  secondImgModel = new Second_img_Model(str_b_url,str_btitle,str_date);
														products.add(secondImgModel);

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



												}

//												Picasso.with(getContext()).load(str_img_second).into(iv_second_img);
												Picasso.with(getContext()).load(str_img_four).into(iv_four_img);
												Picasso.with(getContext()).load(str_img_third_one).into(iv_img_third_one);
												Picasso.with(getContext()).load(str_img_third_two).into(iv_img_third_two);

												for(String name : file_maps.keySet())
												{
													DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
													defaultSliderView.image(file_maps.get(name))
															.setScaleType(BaseSliderView.ScaleType.Fit);

													TextSliderView textSliderView = new TextSliderView(getActivity());
													// initialize a SliderLayout
													textSliderView
															.description(name)
															.image(file_maps.get(name))
															.setScaleType(BaseSliderView.ScaleType.Fit);
													//.setOnSliderClickListener(getActivity);

													//add your extra information
													textSliderView.bundle(new Bundle());
													textSliderView.getBundle()
															.putString("extra", name);

													image_slider1.addSlider(defaultSliderView);
												}

												image_slider1.setPresetTransformer(SliderLayout.Transformer.Default);
												image_slider1.setCustomAnimation(new DescriptionAnimation());
												image_slider1.setDuration(4000);
												image_slider1.setCustomIndicator(pagerIndicator);
												mViewPager.setAdapter(new ImageSlideAdapter(getActivity(), products));

												mIndicator.setViewPager(mViewPager);


												Adapteryoutube adapter = new Adapteryoutube(getChildFragmentManager());
												mViewPageryoutube.setAdapter(adapter);
												indicatoryoutube.setViewPager(mViewPageryoutube);
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
//					hud.dismiss();
					VolleyLog.e("Error: ", error.getMessage());
					Toast.makeText(context,
							"Please Check Internet Connection.",
							Toast.LENGTH_LONG)
							.show();
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

	private String[] getVids()
	{
		return new String[]{"95I5VaR7GeU","plJTJKoPAZk"};
	}

	class Adapteryoutube extends FragmentStatePagerAdapter
	{



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
//					youTubePlayer.cueVideo(getVids()[position]);



					list_video.add("qC977wZ2w4Q");
					list_video.add("SO9vW25OVqY");
					//youTubePlayer.cueVideos(list_video);

					if (!b)
					{
						//gblyouTubePlayer = youTubePlayer;

						//youTubePlayer.loadVideo(getVids()[position]);
						//youTubePlayer.cueVideo(getVids()[position]);
						youTubePlayer.cueVideos(list_video);


					}

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


	public class YoutubeAdapter extends PagerAdapter
	{

		List<Second_img_Model> products;
		private Context mContext;
		private List<Second_img_Model> AppsList;
		public YoutubeAdapter(Context context, List<Second_img_Model> products,final FragmentManager fm)
		{

			this.mContext = context;
			this.products = products;


		}

		@Override
		public int getCount() {
			return products.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, final int position) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.vp_image, container, false);

			ImageView mImageView = (ImageView) view
					.findViewById(R.id.image_display);

//        TextView tv_title =(TextView) view.findViewById(R.id.tv_title);
//        TextView tv_date =(TextView) view.findViewById(R.id.tv_date);

			final Second_img_Model apps = products.get(position);

//        tv_title.setText(apps.getImg_title());
//        tv_date.setText(apps.getImg_date());

			Picasso.with(mContext).load( apps.getImgg_url()).into(mImageView);

			mImageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					YouTubePlayerSupportFragment fragment = new YouTubePlayerSupportFragment();
					fragment.initialize("AIzaSyB_bApGLgdJKI_9HUq2DvSHM5oYijsm5ag", new YouTubePlayer.OnInitializedListener()

					{
						@Override
						public void onInitializationSuccess(final YouTubePlayer.Provider provider,
															final YouTubePlayer youTubePlayer,
															final boolean b)
						{
//					youTubePlayer.cueVideo(getVids()[position]);

							if (!b)
							{
								//gblyouTubePlayer = youTubePlayer;

								//youTubePlayer.loadVideo(getVids()[position]);


							}

						}

						@Override
						public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
						{

						}


					});

				}
			});


			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}


	}


}
