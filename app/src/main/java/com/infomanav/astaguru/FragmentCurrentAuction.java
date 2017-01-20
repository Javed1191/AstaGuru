package com.infomanav.astaguru;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import adapter.CurrentAuctionAdapter_gridview;
import adapter.CurrentAuctionAdapter_listview;

import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;
import views.ExpandableHeightGridView;
import views.OnTaskCompleted;

/**
 * Created by android-javed on 05-10-2016.
 */

public class FragmentCurrentAuction extends Fragment implements View.OnClickListener{
    private boolean is_first = true;
    String trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot,tvalue,value_for_cmpr;
    EditText edt_proxy;
    private int mInterval = 15000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    public boolean is_us=false;
    MyInterface listener;
    View viewmain;
    TextView tv_filter;
    ArrayList<Current_Auction_Model> appsList;
    ArrayList<Current_Auction_Model> appsListbackground;
    String[] mydateList = new String[]{"July, 2016", "September, 2016", "September, 2016", "September, 2016", "December, 2016"};
    MainActivity mainActivity;
    private Utility utility;
    private GridView gridview;

    private ListView listviewupcoming;
    private boolean list_visibile = false;
    Context context;
    private ImageView iv_grid, iv_list;
    private CurrentAuctionAdapter_gridview gridViewAdapter;
    private CurrentAuctionAdapter_listview listViewAdapter;
    LinearLayout lin_front, lin_back;
    RelativeLayout rel_back;
    ImageView iv_oncce;
    boolean isBackVisible = false;
    private TextView tv_lot, tv_lettest, tv_significant, tv_popular, tv_comingsoon;
    private View view_lot, view_lettest, view_significant, view_popular, view_comingsoon;

    AnimatorSet setRightOut, setLeftIn;
    private SwipeMenuListView mListView;
    TextView tv_rs_type,tv_currency;
    Current_Auction_Model country;
    SessionData data;
    RelativeLayout re_grid,re_list;
    private KProgressHUD hud;
    AlertDialog bid_now,bid_proxy,dilog_alert;

String Thumbnail,Reference,OldPriceUs,OldPriceRs,Auctionid;
    String pricers, priceus, artist_name, str_Bidclosingtime, image, productdate, filter_url_string;

    String f_doller,f_pro_id,f_lot,rs_value,rupee_value,usvalue;;
    String str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,str_category,str_small_img,str_Bidpricers,str_Bidpriceus;
    String str_rs_amount,str_us_amount,str_collectors;
    String artistid,str_FirstName,str_LastName,str_Profile, medium, productsize,estamiate,DollarRate;
    List<Current_Auction_Model>list_is_front_obj;
    public FragmentCurrentAuction() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        viewmain = inflater.inflate(R.layout.fragment_currentauction, container, false);

        mainActivity = (MainActivity) getActivity();
        utility = new Utility(getActivity());
        setHasOptionsMenu(true);
        context = getActivity();

        data = new SessionData(context);

        mHandler = new Handler();

        appsListbackground= new ArrayList<>();
        tv_lot = (TextView) viewmain.findViewById(R.id.tv_lot);
        tv_lettest = (TextView) viewmain.findViewById(R.id.tv_lettest);
        tv_significant = (TextView) viewmain.findViewById(R.id.tv_significant);
        tv_popular = (TextView) viewmain.findViewById(R.id.tv_popular);
        tv_comingsoon = (TextView) viewmain.findViewById(R.id.tv_comingsoon);
        tv_filter = (TextView) viewmain.findViewById(R.id.tv_filter);
        tv_rs_type = (TextView) viewmain.findViewById(R.id.tv_rs_type);
        tv_currency = (TextView) viewmain.findViewById(R.id.tv_currency);
        tv_lot.setOnClickListener(this);
        tv_lettest.setOnClickListener(this);
        tv_significant.setOnClickListener(this);
        tv_popular.setOnClickListener(this);
        tv_comingsoon.setOnClickListener(this);

        view_lot = (View) viewmain.findViewById(R.id.view_lot);
        view_lettest = (View) viewmain.findViewById(R.id.view_lettest);
        view_significant = (View) viewmain.findViewById(R.id.view_significant);
        view_popular = (View) viewmain.findViewById(R.id.view_popular);
        view_comingsoon = (View) viewmain.findViewById(R.id.view_comingsoon);

        list_is_front_obj = new ArrayList<>();




        String str_argumnet = data.getObjectAsString("Filter_Data");


        if(str_argumnet.equals("yes"))
        {

            Bundle bundle=getArguments();
            if(bundle.containsKey("url_string"))
            {
                String urldata = bundle.getString("url_string");

                System.out.println("int_str" + urldata);

                 filter_url_string = urldata.substring(0,urldata.length()-2);
                data.setObjectAsString("selected","filter");
                System.out.println("data" + filter_url_string);
                getUpcomingAuction("http://restapi.infomanav.com/api/v2/guru/_table/defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter="+filter_url_string+"");

            }


        }
        else
        {

            getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed");
            data.setObjectAsString("selected","defaultlots");
        }


//        Intent intent  = getActivity().getIntent();
//        if(intent !=null)
//            if(intent.hasExtra("artistid")){
//                String strtext = intent.getStringExtra("artistid");
//
//                String url_string = strtext.substring(0,strtext.length()-2);
//
//                System.out.println("data" + url_string);
//                getUpcomingAuction("http://54.169.222.181/api/v2/guru/_table/defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter="+url_string+"");
//            }
//        else
//            {
//                getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed");
//
//            }

//
//        String filter_data = data.getObjectAsString("Filter_Data");
//
//        if(!filter_data.isEmpty())
//        {
//
//            String url_string = filter_data.substring(0,filter_data.length()-2);
//
//            System.out.println("data" + url_string);
//            getUpcomingAuction("http://54.169.222.181/api/v2/guru/_table/defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter="+url_string+"");
//        }
//        else
//        {
//            getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed");
//
//        }




        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flight_right_out);

        setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flight_left_in);


        gridview = (GridView) viewmain.findViewById(R.id.gridviewupcoming);


        gridview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

        });


        iv_oncce = (ImageView) viewmain.findViewById(R.id.iv_oncce);
//        listviewupcoming = (ListView) viewmain.findViewById(R.id.listviewupcoming);
        mListView = (SwipeMenuListView) viewmain.findViewById(R.id.listviewupcoming);
        re_grid = (RelativeLayout) viewmain.findViewById(R.id.re_grid);
        re_list = (RelativeLayout) viewmain.findViewById(R.id.re_list);

        iv_grid = (ImageView) viewmain.findViewById(R.id.iv_grid);
        iv_list = (ImageView) viewmain.findViewById(R.id.iv_list);
        re_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView();
            }
        });
        re_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView();
            }
        });
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data.setObjectAsString("Filter_Data","");

                Intent intent=new Intent(getActivity(),Filter_Activity.class);
                intent.putExtra("filter","Current");
                getActivity().startActivityForResult(intent, 2);
            }
        });

        gridview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

        });

        tv_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (tv_rs_type.getText().toString().equals("INR"))
                {
                    tv_rs_type.setText("USD");
                }
                else if (tv_rs_type.getText().toString().equals("USD"))
                {
                    tv_rs_type.setText("INR");
                }

                try
                {
                    if(gridViewAdapter!=null)
                    {

                        if (tv_rs_type.getText().toString().equals("USD"))
                        {
                            gridViewAdapter.changeCurrency(true);
                            activity_changeCurrency();
                        }
                        else if (tv_rs_type.getText().toString().equals("INR"))
                        {
                            gridViewAdapter.changeCurrency(false);
                            activity_changeCurrency();
                        }


                    }
                    if(listViewAdapter!=null)
                    {
                        if (tv_rs_type.getText().toString().equals("USD"))
                        {
                            listViewAdapter.changeCurrency(true);
                        }
                        else if (tv_rs_type.getText().toString().equals("INR"))
                        {
                            listViewAdapter.changeCurrency(false);
                        }

                    }


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem bidnow = new SwipeMenuItem(context);
                bidnow.setBackground(new ColorDrawable(Color.rgb(0, 0,0)));
                bidnow.setWidth(dp2px(60));
                bidnow.setTitle("\nBid\nNow");
                bidnow.setIcon(R.drawable.icon_bidnow);
                bidnow.setTitleSize(11);
                bidnow.setTitleColor(Color.WHITE);
                menu.addMenuItem(bidnow);

                SwipeMenuItem report = new SwipeMenuItem(context);
                report.setBackground(new ColorDrawable(Color.rgb(0, 0,0)));
                report.setWidth(dp2px(60));
                report.setTitle("\nProxy\nBid");
                report.setIcon(R.drawable.icon_proxybid);
                report.setTitleSize(11);
                report.setTitleColor(Color.WHITE);
                menu.addMenuItem(report);

                SwipeMenuItem lotdetail = new SwipeMenuItem(context);
                lotdetail.setBackground(new ColorDrawable(Color.rgb(0, 0,0)));
                lotdetail.setWidth(dp2px(60));
                lotdetail.setTitle("\nLot\nDetail");
                lotdetail.setIcon(R.drawable.icon_detail);
                lotdetail.setTitleSize(11);
                lotdetail.setTitleColor(Color.WHITE);
                menu.addMenuItem(lotdetail);


                SwipeMenuItem bidhistory = new SwipeMenuItem(context);
                bidhistory.setBackground(new ColorDrawable(Color.rgb(0, 0,0)));
                bidhistory.setWidth(dp2px(60));
                bidhistory.setTitle("\nBid\nHistory");
                bidhistory.setIcon(R.drawable.icon_bidhistory);
                bidhistory.setTitleSize(11);
                bidhistory.setTitleColor(Color.WHITE);
                menu.addMenuItem(bidhistory);

            }
        };


        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                                 Funcation_Bid_Now();


                        break;
                    case 1:

                                  Function_ProxyBid();
                        break;
                    case 2:

                        String Str_postion =  listViewAdapter.getItem(position).getStr_productid();
                        String reference =  listViewAdapter.getItem(position).getReference();


                        Intent intent = new Intent(context, Lot_Detail_Page.class);

                        intent.putExtra("Str_id", Str_postion);
                        intent.putExtra("reference", reference);
                        intent.putExtra("fragment", "Current");
                        context.startActivity(intent);

                     break;
                    case 3:
                        String productidss = listViewAdapter.getItem(position).getStr_productid();
                        String references =  listViewAdapter.getItem(position).getReference();
                        Intent intentproductid = new Intent(context, Bid_History.class);
                        intentproductid.putExtra("Str_id", productidss);
                        intentproductid.putExtra("reference", references);
                        intentproductid.putExtra("str_FirstName", listViewAdapter.getItem(position).getArtist_name());
                        intentproductid.putExtra("str_category", listViewAdapter.getItem(position).getStr_category());
                        intentproductid.putExtra("str_medium", listViewAdapter.getItem(position).getMedium());
                        intentproductid.putExtra("str_date", listViewAdapter.getItem(position).getProductdate());
                        intentproductid.putExtra("str_productsize", listViewAdapter.getItem(position).getProductsize());
                        intentproductid.putExtra("str_istimate", listViewAdapter.getItem(position).getEstamiate());
                        intentproductid.putExtra("str_lot", listViewAdapter.getItem(position).getReference());
                        intentproductid.putExtra("str_image", listViewAdapter.getItem(position).getStr_thumbnail());
                        context.startActivity(intentproductid);

                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        mListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });


        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(context, position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });




        return viewmain;
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try
            {
//                appsList.clear();
                String auction  = data.getObjectAsString("selected");
                if(auction.equalsIgnoreCase("lot"))
                {

                    getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                }
                else if(auction.equalsIgnoreCase("lettest"))
                {
                    getUpcomingAuction(Application_Constants.Main_URL+"lotslatest?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                }
                else if(auction.equalsIgnoreCase("significant"))
                {
                    getUpcomingAuction(Application_Constants.Main_URL+"lotssignificant?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                }
                else if(auction.equalsIgnoreCase("popular"))
                {
                    getUpcomingAuction(Application_Constants.Main_URL+"lotspopular?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                }
                else if(auction.equalsIgnoreCase("comingsoon"))
                {
                    getUpcomingAuction(Application_Constants.Main_URL+"lotsclosingtime?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                }
                else if(auction.equalsIgnoreCase("defaultlots"))
                {
                    getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                }
                else if(auction.equalsIgnoreCase("filter"))
                {
                    getUpcomingAuction("http://restapi.infomanav.com/api/v2/guru/_table/defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter="+filter_url_string+"");

                }

            }
            finally {

                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };
    public void startRepeatingTask() {
        mStatusChecker.run();
        is_first = false;
    }

    public void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }



    public void CallWithDelay(int miliseconds, final String methodName)
    {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                try
                {
                    String data = methodName;
                    getUpcomingAuction(Application_Constants.Main_URL+"lotspopular?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, miliseconds);
    }
    @Override
    public void onClick(View v) {

        stopRepeatingTask();

        is_first = true;
        switch (v.getId()) {

            case R.id.tv_lot:
                data.setObjectAsString("selected","lot");

                tv_lot.setTextColor(Color.parseColor("#a78e69"));
                tv_lettest.setTextColor(Color.parseColor("#000000"));
                tv_significant.setTextColor(Color.parseColor("#000000"));
                tv_popular.setTextColor(Color.parseColor("#000000"));
                tv_comingsoon.setTextColor(Color.parseColor("#000000"));
//                CallWithDelay(3000,"lotspopular");

                view_lot.setVisibility(View.VISIBLE);
                view_lettest.setVisibility(View.INVISIBLE);
                view_popular.setVisibility(View.INVISIBLE);
                view_significant.setVisibility(View.INVISIBLE);
                view_comingsoon.setVisibility(View.INVISIBLE);

                appsList.clear();
                data.setObjectAsString("Filter_Data","false");

//                getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");
                startRepeatingTask();
                break;

            case R.id.tv_lettest:
                data.setObjectAsString("selected","lettest");
                tv_lot.setTextColor(Color.parseColor("#000000"));
                tv_lettest.setTextColor(Color.parseColor("#a78e69"));
                tv_significant.setTextColor(Color.parseColor("#000000"));
                tv_popular.setTextColor(Color.parseColor("#000000"));
                tv_comingsoon.setTextColor(Color.parseColor("#000000"));

                view_lot.setVisibility(View.INVISIBLE);
                view_lettest.setVisibility(View.VISIBLE);
                view_popular.setVisibility(View.INVISIBLE);
                view_significant.setVisibility(View.INVISIBLE);
                view_comingsoon.setVisibility(View.INVISIBLE);
                appsList.clear();
                data.setObjectAsString("Filter_Data","false");
//                getUpcomingAuction(Application_Constants.Main_URL+"lotslatest?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");
                startRepeatingTask();
                break;

            case R.id.tv_significant:
                data.setObjectAsString("selected","significant");
                tv_lot.setTextColor(Color.parseColor("#000000"));
                tv_lettest.setTextColor(Color.parseColor("#000000"));
                tv_significant.setTextColor(Color.parseColor("#a78e69"));
                tv_popular.setTextColor(Color.parseColor("#000000"));
                tv_comingsoon.setTextColor(Color.parseColor("#000000"));

                view_lot.setVisibility(View.INVISIBLE);
                view_lettest.setVisibility(View.INVISIBLE);
                view_popular.setVisibility(View.INVISIBLE);
                view_significant.setVisibility(View.VISIBLE);
                view_comingsoon.setVisibility(View.INVISIBLE);
                appsList.clear();
                data.setObjectAsString("Filter_Data","false");
//                getUpcomingAuction(Application_Constants.Main_URL+"lotssignificant?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");
                startRepeatingTask();
                break;
            case R.id.tv_popular:
                data.setObjectAsString("selected","popular");
                tv_lot.setTextColor(Color.parseColor("#000000"));
                tv_lettest.setTextColor(Color.parseColor("#000000"));
                tv_significant.setTextColor(Color.parseColor("#000000"));
                tv_popular.setTextColor(Color.parseColor("#a78e69"));
                tv_comingsoon.setTextColor(Color.parseColor("#000000"));

                view_lot.setVisibility(View.INVISIBLE);
                view_lettest.setVisibility(View.INVISIBLE);
                view_popular.setVisibility(View.VISIBLE);
                view_significant.setVisibility(View.INVISIBLE);
                view_comingsoon.setVisibility(View.INVISIBLE);
                appsList.clear();
                data.setObjectAsString("Filter_Data","false");
//                getUpcomingAuction(Application_Constants.Main_URL+"lotspopular?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");
                startRepeatingTask();
                break;
            case R.id.tv_comingsoon:
                data.setObjectAsString("selected","comingsoon");
                tv_lot.setTextColor(Color.parseColor("#000000"));
                tv_lettest.setTextColor(Color.parseColor("#000000"));
                tv_significant.setTextColor(Color.parseColor("#000000"));
                tv_popular.setTextColor(Color.parseColor("#000000"));
                tv_comingsoon.setTextColor(Color.parseColor("#a78e69"));

                view_lot.setVisibility(View.INVISIBLE);
                view_lettest.setVisibility(View.INVISIBLE);
                view_popular.setVisibility(View.INVISIBLE);
                view_significant.setVisibility(View.INVISIBLE);
                view_comingsoon.setVisibility(View.VISIBLE);
                appsList.clear();
                data.setObjectAsString("Filter_Data","false");
//                getUpcomingAuction(Application_Constants.Main_URL+"lotsclosingtime?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");
                startRepeatingTask();
                break;

            default:
                break;
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
    public void activity_changeCurrency() {
        if (is_us)
        {
            is_us = false;
        }
        else
        {
            is_us = true;
        }
//        notifyDataSetChanged();
    }
    private void getUpcomingAuction11(String type) {

        if (utility.checkInternet()) {

             String strPastAuctionUrl = type;
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(getActivity());


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;
                    String MyUserID;

                  // String str_FirstName,str_LastName,str_Profile;

                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);

                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");


                                for (int i = 0; i < jsonArray.length(); i++)
                                {

//                                    JSONObject obj_category_by_categoryid = Obj.getJSONObject("category_by_categoryid");
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    str_productid = Obj.getString("productid");
                                    System.out.println("str_productid " + str_productid);
                                    str_title = Obj.getString("title");
                                    str_description = Obj.getString("description");
                                    str_artistid = Obj.getString("artistid");
                                    str_thumbnail = Obj.getString("thumbnail");
                                    str_image = Obj.getString("image");
                                    str_productsize = Obj.getString("productsize");
                                    str_small_img = Obj.getString("smallimage");

//                                    JSONObject object = Obj.getJSONObject("artist_by_artistid");
                                    str_FirstName = Obj.getString("FirstName");
                                    str_LastName = Obj.getString("LastName");
                                    pricers = Obj.getString("Bidpricers");
                                    priceus = Obj.getString("Bidpriceus");
                                    str_Bidclosingtime = Obj.getString("Bidclosingtime");

                                    medium = Obj.getString("medium");
                                    productsize = Obj.getString("productsize");
                                    estamiate = Obj.getString("estamiate");
                                    str_collectors = Obj.getString("collectors");
                                    DollarRate = Obj.getString("DollarRate");
                                    reference = Obj.getString("reference");
                                    productdate = Obj.getString("productdate");

                                    String newtext = reference.trim();

                                    String datac = data.getObjectAsString("selected");

                                    if(datac.equals("lot"))
                                    {

                                        MyUserID =  Obj.getString("MyUserID");
                                    }
                                    else
                                    {
                                        MyUserID = "9999";
                                    }




                                    str_Profile = "Profile";

                                    artist_name = str_FirstName +" "+ str_LastName;


                                    str_category = Obj.getString("category");

                                     country = new Current_Auction_Model( str_productid,  str_category,  artist_name,  str_Profile,  str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,  str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium, productsize,estamiate,DollarRate,newtext,productdate,MyUserID,str_collectors,"");
                                     appsList.add(country);

                                }

//                                setAdapters();

                                if (is_first)
                                {

                                        setAdapters();
                                    is_first = false;

                                        startRepeatingTask();

                                }
                                else
                                {
                                    gridViewAdapter.Upadte_GridView(appsList);
                                }



                            }
                            else
                            {

                                appsList.clear();
                                setAdapters();
//                                stopRepeatingTask();

                                Toast.makeText(getActivity(), "Records Not Found", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getActivity(), "Records Not Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(context, "Please Check Internet Connection.",Toast.LENGTH_LONG)
                    .show();

        }

    }



    private void getUpcomingAuction(String url)
    {

        if (is_first)
        {


            hud = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f);

            hud.show();

        }


        System.out.println("strPastAuctionUrl " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str_json = response;
                        String MyUserID,currentDate;
                        appsList = new ArrayList<>();
                        // String str_FirstName,str_LastName,str_Profile;

                        try {
                            if (str_json != null)
                            {
                                JSONObject jobject = new JSONObject(str_json);

                                if (jobject.length() > 0)
                                {
                                    JSONArray jsonArray = new JSONArray();
                                    jsonArray = jobject.getJSONArray("resource");


                                    for (int i = 0; i < jsonArray.length(); i++) {

//                                    JSONObject obj_category_by_categoryid = Obj.getJSONObject("category_by_categoryid");
                                        JSONObject Obj = jsonArray.getJSONObject(i);


                                        str_productid = Obj.getString("productid");
                                        System.out.println("str_productid " + str_productid);
                                        str_title = Obj.getString("title");
                                        str_description = Obj.getString("description");
                                        str_artistid = Obj.getString("artistid");
                                        str_thumbnail = Obj.getString("thumbnail");
                                        str_image = Obj.getString("image");
                                        str_productsize = Obj.getString("productsize");
                                        str_small_img = Obj.getString("smallimage");

//                                    JSONObject object = Obj.getJSONObject("artist_by_artistid");
                                        str_FirstName = Obj.getString("FirstName");
                                        str_LastName = Obj.getString("LastName");
                                        pricers = Obj.getString("Bidpricers");
                                        priceus = Obj.getString("Bidpriceus");
                                        str_Bidclosingtime = Obj.getString("Bidclosingtime");

                                        if(Obj.has("currentDate"))
                                        {
                                            currentDate = Obj.getString("currentDate");
                                        }
                                        else
                                        {
                                            currentDate="2017-01-10 19:55:27";
                                        }
                                        medium = Obj.getString("medium");
                                        productsize = Obj.getString("productsize");
                                        estamiate = Obj.getString("estamiate");
                                        str_collectors = Obj.getString("collectors");
                                        DollarRate = Obj.getString("DollarRate");
                                        reference = Obj.getString("reference");
                                        productdate = Obj.getString("productdate");

                                        String newtext = reference.trim();

                                        String datac = data.getObjectAsString("selected");

                                        if(datac.equals("lot"))
                                        {

                                            MyUserID =  Obj.getString("MyUserID");
                                        }
                                        else
                                        {
                                            MyUserID = "9999";
                                        }

                                        str_Profile = "Profile";

                                        artist_name = str_FirstName +" "+ str_LastName;

                                        str_category = Obj.getString("category");

                                        country = new Current_Auction_Model( str_productid,  str_category,  artist_name,  str_Profile,  str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,  str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium, productsize,estamiate,DollarRate,newtext,productdate,MyUserID,str_collectors,currentDate);
                                        appsList.add(country);

                                    }

//                                setAdapters();

                                    if (hud != null && hud.isShowing())
                                    {
                                        hud.dismiss();
                                    }

                                    if (is_first)
                                    {

                                        setAdapters();

                                        startRepeatingTask();

                                    }
                                    else
                                    {
                                        gridViewAdapter.Upadte_GridView(appsList);
                                       // gridViewAdapter.notifyDataSetChanged();
                                    }



                                }
                                else
                                {

                                    appsList.clear();
//                                    setAdapters();
//                                stopRepeatingTask();

                                    Toast.makeText(getActivity(), "Records Not Found", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(getActivity(), "Records Not Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();

                            Toast.makeText(context, "Please Check Internet Connection.",Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        Toast.makeText(context, "Please Check Internet Connection.",Toast.LENGTH_LONG)
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    @Override
    public void onPause() {
        super.onPause();
        stopRepeatingTask();
        data.setObjectAsString("Filter_Data","false");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
        data.setObjectAsString("Filter_Data","false");
    }
    private void setAdapters()
    {

        if (list_visibile)
        {
//            viewview.setVisibility(View.GONE);


            listViewAdapter = new CurrentAuctionAdapter_listview(context, R.layout.current_listview, appsList,false);
            mListView.setAdapter(listViewAdapter);


//            data.setObjectAsString("currency","RS");

            gridview.setLayoutAnimation(getgridlayoutAnim());
        }
        else
        {


            gridViewAdapter = new CurrentAuctionAdapter_gridview(context, R.layout.current_grid, appsList, false, FragmentCurrentAuction.this);
            gridview.setAdapter(gridViewAdapter);
//            data.setObjectAsString("currency","RS");

//            tv_rs_type.setText("INR");
            mListView.setLayoutAnimation(getgridlayoutAnim());


        }

    }

//       DSP_BLACK dsp_black= new DSP_BLACK();



    private void changeView() {

        //if the current view is the listview, passes to gridview
        if (list_visibile) {




            mListView.setVisibility(View.GONE);
            iv_grid.setBackgroundResource(R.drawable.grid_dark);
            iv_list.setBackgroundResource(R.drawable.list_light);
            gridview.setVisibility(View.VISIBLE);
            list_visibile = false;
            setAdapters();

            if (tv_rs_type.getText().toString().equals("USD"))
            {
                gridViewAdapter.changeCurrency(true);
                activity_changeCurrency();
            }
            else if (tv_rs_type.getText().toString().equals("INR"))
            {
                gridViewAdapter.changeCurrency(false);
                activity_changeCurrency();
            }
        } else {


            gridview.setVisibility(View.GONE);
            iv_list.setBackgroundResource(R.drawable.list_dark);
            iv_grid.setBackgroundResource(R.drawable.grid_light);
            mListView.setVisibility(View.VISIBLE);

            list_visibile = true;

            setAdapters();

            if (tv_rs_type.getText().toString().equals("USD"))
            {
                listViewAdapter.changeCurrency(true);
            }
            else if (tv_rs_type.getText().toString().equals("INR"))
            {
                listViewAdapter.changeCurrency(false);
            }
        }

    }

    public void call_data()
    {
        String str_selected = data.getObjectAsString("selected");

        if(str_selected.equals("lot"))
        {
            tv_lot.setTextColor(Color.parseColor("#a78e69"));
            tv_lettest.setTextColor(Color.parseColor("#000000"));
            tv_significant.setTextColor(Color.parseColor("#000000"));
            tv_popular.setTextColor(Color.parseColor("#000000"));
            tv_comingsoon.setTextColor(Color.parseColor("#000000"));


            view_lot.setVisibility(View.VISIBLE);
            view_lettest.setVisibility(View.INVISIBLE);
            view_popular.setVisibility(View.INVISIBLE);
            view_significant.setVisibility(View.INVISIBLE);
            view_comingsoon.setVisibility(View.INVISIBLE);

            getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

        }
        else if(str_selected.equals("lettest"))
        {
            tv_lot.setTextColor(Color.parseColor("#000000"));
            tv_lettest.setTextColor(Color.parseColor("#a78e69"));
            tv_significant.setTextColor(Color.parseColor("#000000"));
            tv_popular.setTextColor(Color.parseColor("#000000"));
            tv_comingsoon.setTextColor(Color.parseColor("#000000"));

            view_lot.setVisibility(View.INVISIBLE);
            view_lettest.setVisibility(View.VISIBLE);
            view_popular.setVisibility(View.INVISIBLE);
            view_significant.setVisibility(View.INVISIBLE);
            view_comingsoon.setVisibility(View.INVISIBLE);

            getUpcomingAuction(Application_Constants.Main_URL+"lotslatest?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

        }
        else if(str_selected.equals("significant"))
        {
            tv_lot.setTextColor(Color.parseColor("#000000"));
            tv_lettest.setTextColor(Color.parseColor("#000000"));
            tv_significant.setTextColor(Color.parseColor("#a78e69"));
            tv_popular.setTextColor(Color.parseColor("#000000"));
            tv_comingsoon.setTextColor(Color.parseColor("#000000"));

            view_lot.setVisibility(View.INVISIBLE);
            view_lettest.setVisibility(View.INVISIBLE);
            view_popular.setVisibility(View.INVISIBLE);
            view_significant.setVisibility(View.VISIBLE);
            view_comingsoon.setVisibility(View.INVISIBLE);
            getUpcomingAuction(Application_Constants.Main_URL+"lotssignificant?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

        }
        else if(str_selected.equals("popular"))
        {
            tv_lot.setTextColor(Color.parseColor("#000000"));
            tv_lettest.setTextColor(Color.parseColor("#000000"));
            tv_significant.setTextColor(Color.parseColor("#000000"));
            tv_popular.setTextColor(Color.parseColor("#a78e69"));
            tv_comingsoon.setTextColor(Color.parseColor("#000000"));

            view_lot.setVisibility(View.INVISIBLE);
            view_lettest.setVisibility(View.INVISIBLE);
            view_popular.setVisibility(View.VISIBLE);
            view_significant.setVisibility(View.INVISIBLE);
            view_comingsoon.setVisibility(View.INVISIBLE);
            getUpcomingAuction(Application_Constants.Main_URL+"lotspopular?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

        }
        else if(str_selected.equals("comingsoon"))
        {
            tv_lot.setTextColor(Color.parseColor("#000000"));
            tv_lettest.setTextColor(Color.parseColor("#000000"));
            tv_significant.setTextColor(Color.parseColor("#000000"));
            tv_popular.setTextColor(Color.parseColor("#000000"));
            tv_comingsoon.setTextColor(Color.parseColor("#a78e69"));

            view_lot.setVisibility(View.INVISIBLE);
            view_lettest.setVisibility(View.INVISIBLE);
            view_popular.setVisibility(View.INVISIBLE);
            view_significant.setVisibility(View.INVISIBLE);
            view_comingsoon.setVisibility(View.VISIBLE);
            getUpcomingAuction(Application_Constants.Main_URL+"lotsclosingtime?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=online%20=%2027&related=*");

        }
        else
        {
            getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed");

        }

    }

    public void Funcation_Bid_Now()
    {

        String status = data.getObjectAsString("login");
        if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
        {
            Intent intent = new Intent(context,Before_Login_Activity.class);
            intent.putExtra("str_from","adpter");
            context.startActivity(intent);

        }
        else
        {


            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
            dialogBuilder.setView(dialogView);

            final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
            final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
            final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
            final TextView tv_bidlot = (TextView) dialogView.findViewById(R.id.tv_bidlot);

            final TextView iv_icon = (TextView) dialogView.findViewById(R.id.iv_icon);

            String img_name = country.getStr_thumbnail();
            Thumbnail = img_name.replace("paintings/","");
            Reference=country.getReference();
            OldPriceRs=country.getPriceus();
            OldPriceUs=country.getPricers();
            Auctionid=country.getReference();

            if (is_us)
            {
                int int_bid_us =0;


                int myNum = Integer.parseInt(country.getPricers());
                if (myNum<10000000)
                {
                    int_bid_us = Get_10_value(country.getPriceus());
                }
                else
                {
                    int_bid_us = Get_5_value(country.getPriceus());
                }

                str_us_amount = String.valueOf(int_bid_us);
                str_rs_amount = String.valueOf(int_bid_us);
                String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_bid_us);

                tv_bidvalue.setText(str_int_us);
                tv_bidlot.setText("Lot " + country.getReference());
                iv_icon.setText("US$");
            }
            else
            {
                int int_bid_rs =0;


                int myNum = Integer.parseInt(country.getPricers());
                if (myNum<10000000)
                {
                    int_bid_rs = Get_10_value(country.getPricers());
                }
                else
                {
                    int_bid_rs = Get_5_value(country.getPricers());
                }

                str_rs_amount = String.valueOf(int_bid_rs);
                str_us_amount = String.valueOf(int_bid_rs);
                rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_bid_rs);

                tv_bidvalue.setText(rs_value);
                tv_bidlot.setText("Lot " + country.getReference());
                iv_icon.setText("₹");

            }


//

            tv_confim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String rs_amount = str_rs_amount;
                    String us_amount = str_us_amount;
                    String productid = country.getStr_productid();
                    String userid = data.getObjectAsString("userid");
                    String dollerrate = country.getDollarRate();
                    f_lot = country.getReference();

                    String str_us = Get_US_value(dollerrate,rs_amount);



                    if (is_us)
                    {
                        if(utility.checkInternet())
                        {
                            String Str_productid = country.getStr_productid();


                            int a = Integer.parseInt(us_amount);
                            int b = Integer.parseInt(dollerrate);

                            int str_rsonus = a*b;

                            String proxy_new_us = Integer.toString(str_rsonus);
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);

                            GetData("US",Str_productid,proxy_new_us, productid, userid, dollerrate, us_amount,f_lot);

                            System.out.println("int_str" + Str_productid);
                            System.out.println("int_str" + str_rsonus);
                            System.out.println("int_str" + productid);
                            System.out.println("int_str" + userid);
                            System.out.println("int_str" + dollerrate);
                            System.out.println("int_str" + a);
                            System.out.println("int_str" + f_lot);




                        }
                        else
                        {

                            show_dailog("Please Check Internet Connection");
                        }

                    }
                    else
                    {



                        if(utility.checkInternet())
                        {
                            String Str_productid = country.getStr_productid();
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);

                            GetData("US",Str_productid,us_amount, productid, userid, dollerrate, str_us,f_lot);

                            System.out.println("int_str" + Str_productid);
                            System.out.println("int_str" + us_amount);
                            System.out.println("int_str" + productid);
                            System.out.println("int_str" + userid);
                            System.out.println("int_str" + dollerrate);
                            System.out.println("int_str" + str_us);
                            System.out.println("int_str" + f_lot);

                        }
                        else
                        {

                            show_dailog("Please Check Internet Connection");
                        }

                    }



                }
            });
            bid_now = dialogBuilder.create();
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bid_now.dismiss();
                }
            });


            bid_now.show();
            bid_now.setCanceledOnTouchOutside(false);

        }

    }



    public void Function_ProxyBid()
    {
        String status = data.getObjectAsString("login");
        if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
        {
            Intent intent = new Intent(context,Before_Login_Activity.class);
            context.startActivity(intent);

        }
        else
        {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View dialogView = inflater.inflate(R.layout.dailog_proxybid, null);
            dialogBuilder.setView(dialogView);

            final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
            final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
            edt_proxy = (EditText) dialogView.findViewById(R.id.edt_proxy);
            final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
            final TextView tv_proxylot = (TextView) dialogView.findViewById(R.id.tv_proxylot);

            final TextView iv_iconproxy = (TextView) dialogView.findViewById(R.id.iv_iconproxy);



            if (is_us)
            {
                int int_proxy_bid_us =0;


                int myNum = Integer.parseInt(country.getPricers());
                if (myNum<10000000)
                {
                    int_proxy_bid_us = Get_10_value(country.getPriceus());
                }
                else
                {
                    int_proxy_bid_us = Get_5_value(country.getPriceus());
                }
                value_for_cmpr = String.valueOf(int_proxy_bid_us);
                String str_int_xus= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_us);

                tv_bidvalue.setText(str_int_xus);
                tv_proxylot.setText("Lot " + country.getReference());
                iv_iconproxy.setText("US$");
            }
            else
            {

                int int_proxy_bid_rs =0;

                int myNum = Integer.parseInt(country.getPricers());
                if (myNum<10000000)
                {
                    int_proxy_bid_rs = Get_10_value(country.getPriceus());
                }
                else
                {
                    int_proxy_bid_rs = Get_5_value(country.getPriceus());
                }

                value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);

                str_rs_amount = String.valueOf(int_proxy_bid_rs);
                tv_proxylot.setText("Lot " + country.getReference());
                tv_bidvalue.setText(rs_value);
                iv_iconproxy.setText("₹");
            }




            tv_confim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String dollerRate = country.getDollarRate();
                    f_lot = country.getReference();
                    String siteUserID = data.getObjectAsString("userid");
                    String productID = country.getStr_productid();
                    String str_ProxyAmt = edt_proxy.getText().toString();

                    int fb = Integer.parseInt(dollerRate);
                    int rl = Integer.parseInt(str_ProxyAmt);

                    int str_ProxyAmtus_new = rl / fb;

                    String proxy_amt_us = Integer.toString(str_ProxyAmtus_new);




                    if (str_ProxyAmt.isEmpty())
                    {
                        Toast.makeText(context, "Pls Enter Proxy Bid Value", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        String entered_value = edt_proxy.getText().toString();
                        String bid_value = value_for_cmpr;
                        int int_entered_value = Integer.parseInt(entered_value);
                        int int_bid_value = Integer.parseInt(bid_value);

                        if (int_entered_value > int_bid_value) {
                            if (is_us) {

                                String str_Proxy_for_us = edt_proxy.getText().toString();

                                int fb1 = Integer.parseInt(dollerRate);
                                int rl1 = Integer.parseInt(str_Proxy_for_us);

                                int str_ProxyAmtrs = rl1 * fb1;

                                String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                Toast.makeText(context, "from US", Toast.LENGTH_SHORT).show();


                                if (utility.checkInternet()) {
                                    ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_for_rs, f_lot);

                                } else {
                                    show_dailog("Please Check Internet Connection");

                                }


                            } else {
                                Toast.makeText(context, "From RS", Toast.LENGTH_SHORT).show();

                                if (utility.checkInternet()) {
                                    ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot);
                                } else {

                                    show_dailog("Please Check Internet Connection");
                                }


                            }

                        } else {

                            Toast.makeText(context, "Proxy Must Greater Than Current Bid Value", Toast.LENGTH_SHORT).show();

                        }


                    }

                }
            });
            bid_proxy = dialogBuilder.create();
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bid_proxy.dismiss();
                }
            });


            bid_proxy.show();
            bid_proxy.setCanceledOnTouchOutside(false);
        }

    }
    private int Get_10_value(String amt)
    {

        double amount = Double.parseDouble(amt);

        double byerprimium = (amount / 100.0f) * 10;

        double sum = amount + byerprimium;

        int intbyerprimium = (int) sum;

        return intbyerprimium;
    }
    private int Get_5_value(String amt)
    {

        double amount = Double.parseDouble(amt);

        double byerprimium = (amount / 100.0f) * 5;

        double sum = amount + byerprimium;

        int intbyerprimium = (int) sum;

        return intbyerprimium;
    }

    private String Get_US_value(String dollerrate,String rs_amount)
    {

        int fb = Integer.parseInt(dollerrate);
        int rl = Integer.parseInt(rs_amount);

        int str_Proxy_new = rl / fb;

        String proxy_new_us = Integer.toString(str_Proxy_new);

        return proxy_new_us;
    }





    private void GetData(String value,String str_productid,String rs_amount,String productid,String userid,String dollerrate,String proxy_new_us,String lot) {

        if (utility.checkInternet()) {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/Acution?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter=productid="+str_productid+"";
            System.out.println("GetDataurl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();


            tvalue = value;
            trs_amount = rs_amount;
            tproductid = productid;
            tuserid = userid;
            tdollerrate = dollerrate;
            tproxy_new_us = proxy_new_us;

            tlot=lot;
            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);


                    String str_json = result;


                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);

                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");


                                JSONObject Obj = jsonArray.getJSONObject(0);

                                if(tvalue.equals("RS"))
                                {
                                    rupee_value = Obj.getString("pricers");
                                }
                                else
                                {

                                    rupee_value = Obj.getString("priceus");
                                }




                                int value_one = Integer.parseInt(rupee_value);
                                int value_two = Integer.parseInt(trs_amount);

                                if (value_two > value_one)
                                {

                                    System.out.println("ttt"+"bid valid ");

                                    BidNow(trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot);
                                }
                                else
                                {

                                    System.out.println("ttt"+"bid not valid  ");
//                                    Toast.makeText(mContext, "Dismiss  bidding", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    int int_proxy_bid_rselse = Get_10_value(rupee_value);


                                    rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse);

                                    str_rs_amount = String.valueOf(int_proxy_bid_rselse);

                                    Toast.makeText(context,"Bid Not Valid", Toast.LENGTH_SHORT).show();



                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                    LayoutInflater inflater = (LayoutInflater) context
                                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View dialogView = inflater.inflate(R.layout.dailog_bidnow, null);
                                    dialogBuilder.setView(dialogView);

                                    final TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
                                    final TextView tv_confim = (TextView) dialogView.findViewById(R.id.tv_confim);
                                    final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
                                    final TextView iv_icon = (TextView) dialogView.findViewById(R.id.iv_icon);

                                    if (is_us)
                                    {
                                        int int_proxy_bid_rselse_us = Get_10_value(rupee_value);


                                        String str_int_us= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_us);
                                        tv_bidvalue.setText(str_int_us);
                                        iv_icon.setText("US$");

                                    }
                                    else
                                    {
                                        System.out.println("ttt"+"new value set  ");
                                        int int_proxy_bid_rselse_rs = Get_10_value(rupee_value);

                                        rs_value= NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rselse_rs);

                                        str_rs_amount = String.valueOf(int_proxy_bid_rselse_rs);

                                        tv_bidvalue.setText(rs_value);


                                        iv_icon.setText("₹");
                                    }


                                    tv_confim.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            String rs_amount = str_rs_amount;
                                            String us_amount = usvalue;
                                            String productid = f_pro_id;
                                            String  userid = data.getObjectAsString("userid");
                                            String  dollerrate =f_doller;

                                            int fb = Integer.parseInt(dollerrate);
                                            int rl = Integer.parseInt(rs_amount);

                                            int str_Proxy_new = rl / fb;

                                            String proxy_new_us = Integer.toString(str_Proxy_new);


                                            String status = data.getObjectAsString("login");
                                            if (status.equalsIgnoreCase("false"))
                                            {
                                                Intent intent = new Intent(context,Before_Login_Activity.class);
                                                context.startActivity(intent);

                                            }
                                            else {

                                                if (is_us) {
//                                                    BidNow(us_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
                                                    GetData("US",f_pro_id,us_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
                                                } else {

                                                    System.out.println("ttt"+"re bid ");

                                                    GetData("RS",f_pro_id,rs_amount, productid, userid, dollerrate, proxy_new_us,f_lot);
//                                BidNow(rs_amount, productid, userid, dollerrate, proxy_new_us);
                                                }

                                            }


                                        }
                                    });
                                    bid_now = dialogBuilder.create();
                                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bid_now.dismiss();
                                        }
                                    });


                                    bid_now.show();
                                    bid_now.setCanceledOnTouchOutside(false);


                                }




                            } else {
                                Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }


    private void BidNow(String str_Amount,String str_productID,String str_userID,String dollerrate,String proxy_new_us,String tlot)
    {

        if (utility.checkInternet())
        {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spBid("+str_Amount+","+str_productID+","+str_userID+","+dollerrate+","+proxy_new_us+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            final   String str_lot = tlot;
            final String str_amt = str_Amount;

            System.out.println("strPastAuctionUrl " + str_Amount);
            System.out.println("strPastAuctionUrl " + str_productID);
            System.out.println("strPastAuctionUrl " + str_userID);
            System.out.println("strPastAuctionUrl " + dollerrate);
            System.out.println("strPastAuctionUrl " + proxy_new_us);



            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback()
            {
                @Override
                public void onSuccess(String result) {
                    System.out.println("resultbid" + result);

                    System.out.println("strPastAuctionUrl " + result);

                    String str_json = result;

                    try {
                        if (str_json != null)
                        {

                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {
                                JSONObject Obj = jsonArray.getJSONObject(0);
                                String currentStatus = Obj.getString("currentStatus");
                                //String emailID = Obj.getString("emailID");
                                // String mobilrNum = Obj.getString("mobileNum");

//                                Toast.makeText(mContext,currentStatus, Toast.LENGTH_SHORT).show();
                                if(currentStatus.equals("1"))
                                {

                                    Toast.makeText(context, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
                                    String number = Obj.getString("mobileNum");
                                    bid_now.dismiss();

                                    show_dailog("You Succesfully Bid");
                                    registerUser(str_lot,str_amt,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    Toast.makeText(context, "U r out of bid", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    show_dailog("U r out of bid");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    Toast.makeText(context, "You can not bid for this lot right now as you are already leading for this lot.", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

                                }




                            } else {
                                Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

//                    currentAuction.call_data();
                }
            });
        }

    }
    private  void show_dailog(String msg)
    {

        String msgtest = msg;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dailog_aleart, null);
        dialogBuilder.setView(dialogView);

        final TextView tv_cancelx = (TextView) dialogView.findViewById(R.id.tv_cancelx);
        final TextView tv_bidvalue = (TextView) dialogView.findViewById(R.id.tv_bidvalue);
        tv_bidvalue.setText(msgtest);

        dilog_alert = dialogBuilder.create();

        tv_cancelx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilog_alert.dismiss();
            }
        });


        dilog_alert.show();
        dilog_alert.setCanceledOnTouchOutside(false);
    }


    private void registerUser(String lot,String value,String mobile){


        String lot_number = lot;
        String bid_value = value;
        final String mob_number=mobile;

        final String msg = "Dear User, please note you have been outbid on Lot No "+lot_number+". Next valid bid is Rs."+bid_value+". Place renewed bid on www.astaguru.com or mobile App.";

        System.out.println("msg" + msg);

        String URL_url = "http://gateway.netspaceindia.com/api/sendhttp.php?";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("authkey", "131841Aotn6vhT583570b5");
                params.put("mobiles",mob_number);
                params.put("message",msg);
                params.put("sender","AstGru");
                params.put("route","4");
                params.put("country","91");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
    private void ProxyBid(String userProxyAmount,String productID,String siteUserID,String dollerRate,String dollerAmt,String f_lot)
    {

        if (utility.checkInternet()) {
            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_proc/spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+")?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            final   String userproxy = userProxyAmount;
            final String lot_no = f_lot;




            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("result" + result);


                    String str_json = result;

                    try {
                        if (str_json != null)
                        {

                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {
                                JSONObject Obj = jsonArray.getJSONObject(0);
                                String currentStatus = Obj.getString("currentStatus");
//                                String emailID = Obj.getString("emailID");
//                                String mobilrNum = Obj.getString("mobilrNum");

                                if(currentStatus.equals("1"))
                                {

                                    String number = Obj.getString("mobileNum");
                                    bid_proxy.dismiss();

                                    show_dailog("You Succesfully Bid");
                                    registerUser(lot_no,userproxy,number);

                                }
                                else if (currentStatus.equals("2"))
                                {

                                    bid_proxy.dismiss();

                                    show_dailog("U r out of bid");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    bid_proxy.dismiss();

                                    show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

                                }




                            } else {
                                Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}