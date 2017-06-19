package com.infomanav.astaguru;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import adapter.CurrentAuctionAdapter_gridview;
import adapter.CurrentAuctionAdapter_listview;
import model_classes.Current_Auction_Model;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 05-10-2016.
 */

public class FragmentCurrentAuction extends Fragment implements View.OnClickListener{
    private boolean is_first = true,is_start=false;
    String trs_amount, tproductid, tuserid, tdollerrate, tproxy_new_us,tlot,tvalue,value_for_cmpr,MyUserID,
            HumanFigure,Online,Picture,Profile;
    EditText edt_proxy;
    private int mInterval = 10000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    public boolean is_us=false;
    MyInterface listener;
    View viewmain;
    TextView tv_filter;
    ArrayList<Current_Auction_Model> appsList;
    ArrayList<Current_Auction_Model> appsListbackground;
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
    boolean isBackVisible = false,is_filter=false;
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
    LinearLayout lin_filter,lay_current_header;
    String Thumbnail,Reference,OldPriceUs,OldPriceRs,Auctionid,Ufirst_name,Ulastname,Bidclosingtime;
    String auctionBanner,pricers, priceus, artist_name, str_Bidclosingtime, image, productdate, filter_url_string;

    String f_doller,f_pro_id,f_lot,rs_value,rupee_value,usvalue;;
    String str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,
            str_category,str_small_img,str_Bidpricers,str_Bidpriceus,Auctionname;
    String str_rs_amount,str_us_amount,str_collectors;
    String artistid,str_FirstName,str_LastName,str_Profile, medium, productsize,estamiate,DollarRate;
    ImageView grid_image,iv_icon_filter;
    private LinearLayout lay_current_auctions,lay_no_data_found,lay_grid_list_view;
    private ImageView img_no_auctions;
    private TextView tv_no_data_found;
    SessionData sessionData;
    String strUserName="",strCurrentCallUrl="",strSortBy="lot",fragment="",type="",key="",auction="";
    int int_word_count=0;
    private ArrayList<model_classes.Country> filterArrayList;
    RequestQueue requestQueue;
    int height=0,width=0;
    DisplayMetrics displaymetrics;
    private boolean  argumentsRead = false;
    public FragmentCurrentAuction() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        viewmain = inflater.inflate(R.layout.fragment_currentauction, container, false);

        utility = new Utility(getActivity());
        setHasOptionsMenu(true);
        context = getActivity();
        data = new SessionData(context);
        filterArrayList = new ArrayList<>();
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

        tv_no_data_found = (TextView) viewmain.findViewById(R.id.tv_no_data_found);
        lay_no_data_found = (LinearLayout) viewmain.findViewById(R.id.lay_no_data_found);
        lay_grid_list_view = (LinearLayout) viewmain.findViewById(R.id.lay_grid_list_view);

        lay_current_auctions = (LinearLayout) viewmain.findViewById(R.id.lay_current_auctions);
        lay_current_header = (LinearLayout) viewmain.findViewById(R.id.lay_current_header);
        img_no_auctions = (ImageView) viewmain.findViewById(R.id.img_no_auctions);

        sessionData = new SessionData(getActivity());
        strUserName = sessionData.getString("name");

        grid_image  = (ImageView) viewmain.findViewById(R.id.grid_image);

        String str_argumnet = data.getObjectAsString("Filter_Data");

        displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        requestQueue = Volley.newRequestQueue(context);


        /*if(str_argumnet.equals("yes"))
        {
            try
            {
                Bundle bundle=getArguments();
                if(bundle.containsKey("url_string"))
                {
                    String urldata = bundle.getString("url_string");

                    System.out.println("int_str" + urldata);

                    filter_url_string = urldata.substring(0,urldata.length()-2);
                    data.setObjectAsString("selected","filter");
                    System.out.println("data" + filter_url_string);
                    getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY+"&filter="+filter_url_string+"");

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }




        }
        else
        {

            getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY);
            data.setObjectAsString("selected","defaultlots");
        }*/
/*


        Intent intent  = getActivity().getIntent();
        if(intent !=null)
            if(intent.hasExtra("artistid")){
                String strtext = intent.getStringExtra("artistid");

                String url_string = strtext.substring(0,strtext.length()-2);

                System.out.println("data" + url_string);
                getUpcomingAuction("http://54.169.222.181/api/v2/guru/_table/defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter="+url_string+"");
            }
        else
            {
                getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed");

            }


        String filter_data = data.getObjectAsString("Filter_Data");

        if(!filter_data.isEmpty())
        {

            String url_string = filter_data.substring(0,filter_data.length()-2);

            System.out.println("data" + url_string);
            getUpcomingAuction("http://54.169.222.181/api/v2/guru/_table/defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed&filter="+url_string+"");
        }
        else
        {
            getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed");

        }
*/



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

                        int_word_count = countWords(key);
                        strCurrentCallUrl = Application_Constants.Main_URL_Procedure+"spSearch("+key+","+auction+","+int_word_count+")?api_key="+ Application_Constants.API_KEY;
                        lay_current_header.setVisibility(View.GONE);

                        getSearchResult(strCurrentCallUrl);

                    }
                    else
                    {
                        lay_current_header.setVisibility(View.VISIBLE);
                        strCurrentCallUrl = Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY;
                        getUpcomingAuction(strCurrentCallUrl);
                        data.setObjectAsString("selected","defaultlots");
                    }
                }
                else
                {
                    lay_current_header.setVisibility(View.VISIBLE);
                    strCurrentCallUrl = Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY;
                    getUpcomingAuction(strCurrentCallUrl);
                    data.setObjectAsString("selected","defaultlots");
                }
            }
            else
            {
                lay_current_header.setVisibility(View.VISIBLE);
                strCurrentCallUrl = Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY;
                getUpcomingAuction(strCurrentCallUrl);
                data.setObjectAsString("selected","defaultlots");
            }



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
        lin_filter = (LinearLayout) viewmain.findViewById(R.id.lin_filter);
        iv_icon_filter= (ImageView) viewmain.findViewById(R.id.iv_icon_filter);
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
       /* tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                data.setObjectAsString("Filter_Data","");

                Intent intent=new Intent(getActivity(),Filter_Activity.class);
                intent.putExtra("filter","Current");
                startActivityForResult(intent, 2);

            }
        });*/

       /* iv_icon_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                data.setObjectAsString("Filter_Data","");

                Intent intent=new Intent(getActivity(),Filter_Activity.class);
                intent.putExtra("filter","Current");
                startActivityForResult(intent, 2);

            }
        });*/

        lin_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                data.setObjectAsString("Filter_Data","");

                Intent intent=new Intent(getActivity(),Filter_Activity.class);
                intent.putExtra("filter","Current");
                intent.putExtra("Auctionname",Auctionname);
                intent.putExtra("Auction_id",Online);
                intent.putExtra("filterlist",filterArrayList);
                startActivityForResult(intent, 2);

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
        tv_rs_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        //9022776105 dipen
       /* SwipeMenuCreator creator = new SwipeMenuCreator() {

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


                        String user = data.getObjectAsString("login");
                        String userid = data.getObjectAsString("userid");

                        if(user.equals("true"))
                        {
                            if(userid.equals(listViewAdapter.getItem(position).getMyUserID()))
                            {
                                   show_dailog("You are currently leading on this bid");
                            }
                            else
                            {
                                Funcation_Bid_Now(position);

                            }

                        }
                        else
                        {
                            Funcation_Bid_Now(position);

                        }



                        break;
                    case 1:

                        String usera = data.getObjectAsString("login");
                        String userida = data.getObjectAsString("userid");

                        if(usera.equals("true"))
                        {
                            if(userida.equals(listViewAdapter.getItem(position).getMyUserID()))
                            {
                                show_dailog("You are currently leading on this bid");
                            }
                            else
                            {
                                Function_ProxyBid(position);

                            }

                        }
                        else
                        {
                            Function_ProxyBid(position);

                        }


                        break;
                    case 2:

                        String Str_postion =  listViewAdapter.getItem(position).getStr_productid();
                        String reference =  listViewAdapter.getItem(position).getReference();

                        Intent intent = new Intent(context, Lot_Detail_Page.class);
                        intent.putExtra("Str_id", Str_postion);
                        intent.putExtra("reference", reference);
                        intent.putExtra("fragment", "Current");
                        intent.putExtra("MyUserID",listViewAdapter.getItem(position).getMyUserID());
                        intent.putExtra("HumanFigure",listViewAdapter.getItem(position).getHumanFigure());
                        intent.putExtra("currentDate",listViewAdapter.getItem(position).getCurrentDate());
                        intent.putExtra("Auctionname",listViewAdapter.getItem(position).getAuctionname());

                        intent.putExtra("medium",listViewAdapter.getItem(position).getMedium());
                        intent.putExtra("FirstName",listViewAdapter.getItem(position).getStr_FirstName());
                        intent.putExtra("LastName",listViewAdapter.getItem(position).getStr_LastName());
                        intent.putExtra("Profile",listViewAdapter.getItem(position).getStr_Profile());
                        intent.putExtra("category",listViewAdapter.getItem(position).getStr_category());
                        context.startActivity(intent);

                     break;
                    case 3:
                        String productidss = listViewAdapter.getItem(position).getStr_productid();
                        String references =  listViewAdapter.getItem(position).getReference();
                        Intent intentproductid = new Intent(context, Bid_History.class);
                        intentproductid.putExtra("Str_id", productidss);
                       // intentproductid.putExtra("str_refrene", references);
                        intentproductid.putExtra("str_FirstName", listViewAdapter.getItem(position).getArtist_name());
                        intentproductid.putExtra("str_category", listViewAdapter.getItem(position).getStr_category());
                        intentproductid.putExtra("str_medium", listViewAdapter.getItem(position).getMedium());
                        intentproductid.putExtra("str_date", listViewAdapter.getItem(position).getProductdate());
                        intentproductid.putExtra("str_productsize", listViewAdapter.getItem(position).getProductsize());
                        intentproductid.putExtra("str_istimate", listViewAdapter.getItem(position).getEstamiate());
                        intentproductid.putExtra("str_lot", listViewAdapter.getItem(position).getReference());
                        intentproductid.putExtra("str_image", listViewAdapter.getItem(position).getStr_thumbnail());
                        intentproductid.putExtra("fragment_type","Current");
                        intentproductid.putExtra("MyUserID",listViewAdapter.getItem(position).getMyUserID());
                        intentproductid.putExtra("str_LastName",listViewAdapter.getItem(position).getStr_LastName());
                        intentproductid.putExtra("str_FirstName",listViewAdapter.getItem(position).getStr_FirstName());
                        intentproductid.putExtra("str_image",listViewAdapter.getItem(position).getStr_image());
                        intentproductid.putExtra("str_pricers",listViewAdapter.getItem(position).getPricers());
                        intentproductid.putExtra("str_priceus",listViewAdapter.getItem(position).getPriceus());
                        intentproductid.putExtra("str_refrene",listViewAdapter.getItem(position).getReference());
                        intentproductid.putExtra("currency_type",listViewAdapter.getItem(position).getMyUserID());
                        intentproductid.putExtra("product_id",listViewAdapter.getItem(position).getStr_productid());
                        intentproductid.putExtra("currentDate",listViewAdapter.getItem(position).getCurrentDate());
                        intentproductid.putExtra("str_Bidclosingtime",listViewAdapter.getItem(position).getStr_Bidclosingtime());
                        intentproductid.putExtra("Auctionname",listViewAdapter.getItem(position).getAuctionname());
                        intentproductid.putExtra("Prdescription",listViewAdapter.getItem(position).getStr_description());
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
            public void onMenuOpen(int position)
            {
              //  Toast.makeText(getActivity(),"Oepn",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMenuClose(int position)
            {
               // Toast.makeText(getActivity(),"Close",Toast.LENGTH_SHORT).show();
            }
        });


        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                //Toast.makeText(context, position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/




        return viewmain;
    }



    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();
        //startRepeatingTask();

       // getUpcomingAuction(strCurrentCallUrl);
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

                if(type.equalsIgnoreCase("search"))
                {

                    getSearchResult(strCurrentCallUrl);
                }
                else
                {
                    if(is_filter)
                    {
                        getUpcomingAuction(strCurrentCallUrl);
                    }
                    else
                    {
                        if(auction.equalsIgnoreCase("lot"))
                        {
                            strCurrentCallUrl =  Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY;
                            getUpcomingAuction(strCurrentCallUrl);

                        }
                        else if(auction.equalsIgnoreCase("lettest"))
                        {
                            strCurrentCallUrl =  Application_Constants.Main_URL+"lotslatest?api_key="+ Application_Constants.API_KEY;
                            getUpcomingAuction(strCurrentCallUrl);

                        }
                        else if(auction.equalsIgnoreCase("significant"))
                        {

                            strCurrentCallUrl =  Application_Constants.Main_URL+"lotssignificant?api_key="+ Application_Constants.API_KEY;
                            getUpcomingAuction(strCurrentCallUrl);

                        }
                        else if(auction.equalsIgnoreCase("popular"))
                        {

                            strCurrentCallUrl =  Application_Constants.Main_URL+"lotspopular?api_key="+ Application_Constants.API_KEY;
                            getUpcomingAuction(strCurrentCallUrl);

                        }
                        else if(auction.equalsIgnoreCase("comingsoon"))
                        {
                            strCurrentCallUrl =  Application_Constants.Main_URL+"lotsclosingtime?api_key="+ Application_Constants.API_KEY;
                            getUpcomingAuction(strCurrentCallUrl);

                        }
                        else if(auction.equalsIgnoreCase("defaultlots"))
                        {
                            strCurrentCallUrl =  Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY;
                            getUpcomingAuction(strCurrentCallUrl);

                        }
                        else if(auction.equalsIgnoreCase("filter"))
                        {
                            strCurrentCallUrl =  Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY+"&filter="+filter_url_string+"";
                            getUpcomingAuction(strCurrentCallUrl);

                        }
                        else
                        {
                            strCurrentCallUrl =  Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY;
                            getUpcomingAuction(strCurrentCallUrl);

                        }

                    }
                }

            }
            finally {

                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };
    public void startRepeatingTask()
    {
        mStatusChecker.run();
        is_first = false;
        is_start = true;
    }

    public void stopRepeatingTask() {
        is_start = false;
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

                    strCurrentCallUrl =  Application_Constants.Main_URL+"lotspopular?api_key="+ Application_Constants.API_KEY+"&filter=online%20=%2027&related=*";
                    getUpcomingAuction(strCurrentCallUrl);

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
        is_filter = false;
        switch (v.getId())
        {

            case R.id.tv_lot:
                data.setObjectAsString("selected","lot");
                strSortBy = "lot";

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
                strSortBy = "lettest";
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
                strSortBy = "significant";
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
                strSortBy = "popular";
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
                strSortBy = "comingsoon";
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

    private void getUpcomingAuction(String url)
    {

        if (utility.checkInternet())
        {

            if (is_first) {


                hud = KProgressHUD.create(context)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setDimAmount(0.5f);

                hud.show();

            }

            System.out.println("strCurrentAuctionUrl " + url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String str_json = response;
                            String  currentDate;
                            appsList = new ArrayList<>();
                            // String str_FirstName,str_LastName,str_Profile;

                            try {
                                if (str_json != null)
                                {
                                    JSONObject jobject = new JSONObject(str_json);

                                    if (jobject.length() > 0)
                                    {
                                        JSONArray jsonArray = new JSONArray();

                                        lay_no_data_found.setVisibility(View.GONE);
                                        lay_current_auctions.setVisibility(View.VISIBLE);
                                        img_no_auctions.setVisibility(View.GONE);
                                        lay_grid_list_view.setVisibility(View.VISIBLE);
                                       // gridview.setVisibility(View.VISIBLE);
                                       // mListView.setVisibility(View.VISIBLE);
                                        jsonArray = jobject.getJSONArray("resource");
                                        if (jsonArray.length() > 0)
                                        {
                                            for (int i = 0; i < jsonArray.length(); i++)
                                            {

                                                JSONObject Obj = jsonArray.getJSONObject(i);

                                                Auctionname = Obj.getString("Auctionname");
                                                str_productid = Obj.getString("productid");
                                                str_title = Obj.getString("title");
                                                str_description = Obj.getString("description");
                                                str_artistid = Obj.getString("artistid");
                                                str_thumbnail = Obj.getString("thumbnail");
                                                str_image = Obj.getString("image");
                                                str_productsize = Obj.getString("productsize");
                                                str_small_img = Obj.getString("smallimage");
                                                Online = Obj.getString("Online");
                                                str_FirstName = Obj.getString("FirstName");
                                                str_LastName = Obj.getString("LastName");
                                                pricers = Obj.getString("Bidpricers");
                                                priceus = Obj.getString("Bidpriceus");


                                                if(pricers.equalsIgnoreCase(null))
                                                {
                                                    pricers = "0";
                                                }
                                                if(priceus.equalsIgnoreCase(null))
                                                {
                                                    priceus = "0";
                                                }

                                                str_Bidclosingtime = Obj.getString("Bidclosingtime");
                                                HumanFigure = Obj.getString("HumanFigure");
                                                Picture = Obj.getString("Picture");
                                                Profile = Obj.getString("Profile");

                                                if (Obj.has("currentDate")) {
                                                    currentDate = Obj.getString("currentDate");
                                                } else {
                                                    currentDate = "2017-01-10 19:55:27";
                                                }
                                                medium = Obj.getString("medium");
                                                productsize = Obj.getString("productsize");
                                                estamiate = Obj.getString("estamiate");
                                                str_collectors = Obj.getString("collectors");
                                                DollarRate = Obj.getString("DollarRate");
                                                reference = Obj.getString("reference");
                                                productdate = Obj.getString("productdate");
                                                String onlinevalue = Obj.getString("Online");
                                                Online = onlinevalue.trim();
                                                auctionBanner= Obj.getString("auctionBanner");
                                                String newtext = reference.trim();
                                                String datac = data.getObjectAsString("selected");

                                                MyUserID = Obj.getString("MyUserID");
                                                /*if (datac.equals("lot") || datac.equals("defaultlots"))
                                                {

                                                    MyUserID = Obj.getString("MyUserID");
                                                } else {
                                                    MyUserID = "9999";
                                                }*/

                                               // str_Profile = "Profile";

                                                artist_name = str_FirstName + " " + str_LastName;

                                                str_category = Obj.getString("category");

                                                country = new Current_Auction_Model(Auctionname,str_productid, str_category, artist_name, Profile, str_small_img, str_productsize, str_image, str_thumbnail, str_artistid, str_description, str_title, str_Bidclosingtime, true, pricers, priceus, medium, productsize, estamiate, DollarRate, newtext, productdate, MyUserID, str_collectors, currentDate,HumanFigure,str_FirstName,str_LastName,Online,Picture,Profile,false);
                                                appsList.add(country);

                                            }


                                            if (hud != null && hud.isShowing())
                                            {
                                                hud.dismiss();
                                            }

                                            if (is_first)
                                            {
                                                if(auctionBanner!=null)
                                                {
                                                    Picasso.with(getContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH +auctionBanner.replace(" ","%20"))
                                                            .into(grid_image);
                                                }
                                                setAdapters();
                                               startRepeatingTask();
                                                //webCallTimer();

                                            }
                                            else {
//                                            gridview.setVisibility(View.VISIBLE);

                                                if(!is_start)
                                                {
                                                    gridViewAdapter.Upadte_GridViewWithFilter(appsList,is_filter);
                                                    listViewAdapter.Upadte_ListViewWithFilter(appsList,is_filter);
                                                    startRepeatingTask();
                                                    //webCallTimer();
                                                }
                                                else
                                                {
                                                    gridViewAdapter.Upadte_GridViewWithFilter(appsList,is_filter);
                                                    listViewAdapter.Upadte_ListViewWithFilter(appsList,is_filter);
                                                }



                                               // startRepeatingTask();

                                               /* setAdapters();
                                                startRepeatingTask();*/

                                            }

                                        }
                                        else
                                        {
                                            hud.dismiss();

                                            if(strSortBy.equalsIgnoreCase("lot"))
                                            {
                                                lay_no_data_found.setVisibility(View.GONE);
                                                lay_current_auctions.setVisibility(View.GONE);
                                                img_no_auctions.setVisibility(View.VISIBLE);
                                                lay_grid_list_view.setVisibility(View.GONE);
                                            }
                                            else if(strSortBy.equalsIgnoreCase("comingsoon"))
                                            {
                                                lay_current_auctions.setVisibility(View.VISIBLE);
                                                img_no_auctions.setVisibility(View.GONE);
                                                lay_no_data_found.setVisibility(View.VISIBLE);
                                                lay_grid_list_view.setVisibility(View.GONE);
                                               // gridview.setVisibility(View.GONE);
                                              //  mListView.setVisibility(View.GONE);
                                                tv_no_data_found.setText("Currently there are no closing lots in the next 30 minutes.");
                                            }
                                            else
                                            {
                                                lay_current_auctions.setVisibility(View.VISIBLE);
                                                img_no_auctions.setVisibility(View.GONE);
                                                lay_no_data_found.setVisibility(View.VISIBLE);
                                                lay_grid_list_view.setVisibility(View.GONE);
                                                tv_no_data_found.setText("No records found");
                                            }


                                        }


                                    }
                                    else
                                        {
                                            hud.dismiss();

                                        appsList.clear();
//                                    setAdapters();
//                                stopRepeatingTask();

                                            lay_current_auctions.setVisibility(View.GONE);
                                            img_no_auctions.setVisibility(View.VISIBLE);

                                    }
                                }
                                else
                                    {
                                        hud.dismiss();
                                        lay_current_auctions.setVisibility(View.GONE);
                                        img_no_auctions.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                hud.dismiss();
                                lay_current_auctions.setVisibility(View.GONE);
                                img_no_auctions.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            hud.dismiss();
                            lay_current_auctions.setVisibility(View.GONE);
                            img_no_auctions.setVisibility(View.VISIBLE);
                        }
                    });


            requestQueue.add(stringRequest);
             }
            else
            {
                Toast.makeText(context, "Please Check Internet Connection.", Toast.LENGTH_LONG)
                        .show();
            }

    }


    private void getSearchResult(String strUrl)
    {


        String strSearchCurrentAuctionUrl = strUrl;


        if (utility.checkInternet())
        {

            if (is_first) {


                hud = KProgressHUD.create(context)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setDimAmount(0.5f);

                hud.show();

            }

            System.out.println("strPastAuctionUrl " + strSearchCurrentAuctionUrl);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, strSearchCurrentAuctionUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String str_json = response;

                            System.out.println("Search_Result " + str_json);
                            String  currentDate;
                            appsList = new ArrayList<>();
                            // String str_FirstName,str_LastName,str_Profile;

                            try {
                                if (str_json != null)
                                {
                                    //JSONObject jobject = new JSONObject(str_json);

                                    JSONArray jsonArray = new JSONArray(str_json);
                                    if (jsonArray.length() > 0)
                                    {


                                        lay_no_data_found.setVisibility(View.GONE);
                                        lay_current_auctions.setVisibility(View.VISIBLE);
                                        img_no_auctions.setVisibility(View.GONE);
                                        lay_grid_list_view.setVisibility(View.VISIBLE);
                                        // gridview.setVisibility(View.VISIBLE);
                                        // mListView.setVisibility(View.VISIBLE);
                                       // jsonArray = jobject.getJSONArray("resource");
                                        if (jsonArray.length() > 0)
                                        {
                                            for (int i = 0; i < jsonArray.length(); i++)
                                            {

                                                JSONObject Obj = jsonArray.getJSONObject(i);

                                                Auctionname = Obj.getString("Auctionname");
                                                str_productid = Obj.getString("productid");
                                                str_title = Obj.getString("title");
                                                str_description = Obj.getString("description");
                                                str_artistid = Obj.getString("artistid");
                                                str_thumbnail = Obj.getString("thumbnail");
                                                str_image = Obj.getString("image");
                                                str_productsize = Obj.getString("productsize");
                                                str_small_img = Obj.getString("smallimage");
                                                Online = Obj.getString("Online");
                                                str_FirstName = Obj.getString("FirstName");
                                                str_LastName = Obj.getString("LastName");
                                                pricers = Obj.getString("Bidpricers");
                                                priceus = Obj.getString("Bidpriceus");


                                                if(pricers.equalsIgnoreCase(null))
                                                {
                                                    pricers = "0";
                                                }
                                                if(priceus.equalsIgnoreCase(null))
                                                {
                                                    priceus = "0";
                                                }

                                                str_Bidclosingtime = Obj.getString("Bidclosingtime");
                                                HumanFigure = Obj.getString("HumanFigure");
                                                Picture = Obj.getString("Picture");
                                                Profile = Obj.getString("Profile");

                                                if (Obj.has("currentDate")) {
                                                    currentDate = Obj.getString("currentDate");
                                                } else {
                                                    currentDate = "2017-01-10 19:55:27";
                                                }
                                                medium = Obj.getString("medium");
                                                productsize = Obj.getString("productsize");
                                                estamiate = Obj.getString("estamiate");
                                                str_collectors = Obj.getString("collectors");
                                                DollarRate = Obj.getString("DollarRate");
                                                reference = Obj.getString("reference");
                                                productdate = Obj.getString("productdate");
                                                String onlinevalue = Obj.getString("Online");
                                                Online = onlinevalue.trim();
                                                auctionBanner= Obj.getString("auctionBanner");
                                                String newtext = reference.trim();
                                                String datac = data.getObjectAsString("selected");

                                                    MyUserID = Obj.getString("MyUserID");

                                                // str_Profile = "Profile";

                                                artist_name = str_FirstName + " " + str_LastName;

                                                str_category = Obj.getString("category");

                                                country = new Current_Auction_Model(Auctionname,str_productid, str_category, artist_name, Profile, str_small_img, str_productsize, str_image, str_thumbnail, str_artistid, str_description, str_title, str_Bidclosingtime, true, pricers, priceus, medium, productsize, estamiate, DollarRate, newtext, productdate, MyUserID, str_collectors, currentDate,HumanFigure,str_FirstName,str_LastName,Online,Picture,Profile,false);
                                                appsList.add(country);

                                            }


                                            if (hud != null && hud.isShowing())
                                            {
                                                hud.dismiss();
                                            }

                                            if (is_first)
                                            {
                                                if(auctionBanner!=null)
                                                {
                                                    Picasso.with(getContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH +auctionBanner.replace(" ","%20"))
                                                            .into(grid_image);
                                                }
                                                setAdapters();
                                               // startRepeatingTask();

                                            }
                                            else {
//                                            gridview.setVisibility(View.VISIBLE);

                                                if(!is_start)
                                                {
                                                    gridViewAdapter.Upadte_GridViewWithFilter(appsList,is_filter);
                                                    listViewAdapter.Upadte_ListViewWithFilter(appsList,is_filter);
                                                   // startRepeatingTask();
                                                }
                                                else
                                                {
                                                    gridViewAdapter.Upadte_GridViewWithFilter(appsList,is_filter);
                                                    listViewAdapter.Upadte_ListViewWithFilter(appsList,is_filter);
                                                }



                                                // startRepeatingTask();

                                               /* setAdapters();
                                                startRepeatingTask();*/

                                            }

                                        }
                                        else
                                        {
                                            hud.dismiss();

                                            if(strSortBy.equalsIgnoreCase("lot"))
                                            {
                                                lay_no_data_found.setVisibility(View.GONE);
                                                lay_current_auctions.setVisibility(View.GONE);
                                                img_no_auctions.setVisibility(View.VISIBLE);
                                                lay_grid_list_view.setVisibility(View.GONE);
                                            }
                                            else if(strSortBy.equalsIgnoreCase("comingsoon"))
                                            {
                                                lay_current_auctions.setVisibility(View.VISIBLE);
                                                img_no_auctions.setVisibility(View.GONE);
                                                lay_no_data_found.setVisibility(View.VISIBLE);
                                                lay_grid_list_view.setVisibility(View.GONE);
                                                // gridview.setVisibility(View.GONE);
                                                //  mListView.setVisibility(View.GONE);
                                                tv_no_data_found.setText("Currently there are no closing lots in the next 30 minutes.");
                                            }
                                            else
                                            {
                                                lay_current_auctions.setVisibility(View.VISIBLE);
                                                img_no_auctions.setVisibility(View.GONE);
                                                lay_no_data_found.setVisibility(View.VISIBLE);
                                                lay_grid_list_view.setVisibility(View.GONE);
                                                tv_no_data_found.setText("No records found");
                                            }


                                        }


                                    }
                                    else
                                    {
                                        hud.dismiss();

                                        appsList.clear();
//                                    setAdapters();
//                                stopRepeatingTask();

                                        lay_current_auctions.setVisibility(View.GONE);
                                        img_no_auctions.setVisibility(View.VISIBLE);

                                    }
                                }
                                else
                                {
                                    hud.dismiss();
                                    lay_current_auctions.setVisibility(View.GONE);
                                    img_no_auctions.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                hud.dismiss();
                                lay_current_auctions.setVisibility(View.GONE);
                                img_no_auctions.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            hud.dismiss();
                            lay_current_auctions.setVisibility(View.GONE);
                            img_no_auctions.setVisibility(View.VISIBLE);
                        }
                    });


            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(context, "Please Check Internet Connection.", Toast.LENGTH_LONG)
                    .show();
        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag", "FragmentA.onDestroyView() has been called.");
       // stopRepeatingTask();
    }

    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
       // super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            if (data!=null)
            {
                String message=data.getStringExtra("artistid");
                filterArrayList = (ArrayList<model_classes.Country>) data.getSerializableExtra("filterlist");
                filter_url_string = message;
                is_filter = true;
                //Toast.makeText(getActivity(),filter_url_string,Toast.LENGTH_LONG).show();
                strCurrentCallUrl = Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY+"&filter="+filter_url_string+"";
                getUpcomingAuction(strCurrentCallUrl);
                Log.i("Aetist Id",message);
                // textView1.setText(message);
            }
            else {
                is_filter = false;
            }


        }
        else
        {
            is_filter = false;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
       // stopRepeatingTask();
        data.setObjectAsString("Filter_Data","false");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      // stopRepeatingTask();
        data.setObjectAsString("Filter_Data","false");
    }
    private void setAdapters()
    {

        if (list_visibile)
        {
            listViewAdapter = new CurrentAuctionAdapter_listview(context, R.layout.current_listview, appsList,false,FragmentCurrentAuction.this);
            mListView.setAdapter(listViewAdapter);
            gridview.setLayoutAnimation(getgridlayoutAnim());
        }
        else
        {

            gridViewAdapter = new CurrentAuctionAdapter_gridview(context, R.layout.current_grid, appsList, false, FragmentCurrentAuction.this);
            gridview.setAdapter(gridViewAdapter);
            mListView.setLayoutAnimation(getgridlayoutAnim());


            listViewAdapter = new CurrentAuctionAdapter_listview(context, R.layout.current_listview, appsList,false,FragmentCurrentAuction.this);
            mListView.setAdapter(listViewAdapter);
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
           // setAdapters();

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

           // setAdapters();

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

            getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY+"&filter=online%20=%2027&related=*");

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

            getUpcomingAuction(Application_Constants.Main_URL+"lotslatest?api_key="+ Application_Constants.API_KEY+"&filter=online%20=%2027&related=*");

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
            getUpcomingAuction(Application_Constants.Main_URL+"lotssignificant?api_key="+ Application_Constants.API_KEY+"&filter=online%20=%2027&related=*");

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
            getUpcomingAuction(Application_Constants.Main_URL+"lotspopular?api_key="+ Application_Constants.API_KEY+"&filter=online%20=%2027&related=*");

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
            getUpcomingAuction(Application_Constants.Main_URL+"lotsclosingtime?api_key="+ Application_Constants.API_KEY+"&filter=online%20=%2027&related=*");

        }
        else
        {
            getUpcomingAuction(Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY);

        }

    }

    public void Funcation_Bid_Now(int position)
    {

        String status = data.getObjectAsString("login");
        final Current_Auction_Model country = appsList.get(position);

        if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
        {
            Intent intent = new Intent(context,Before_Login_Activity.class);
            intent.putExtra("str_from","adpter");
            context.startActivity(intent);

        }
        else
        {
            String MobileVerified = data.getObjectAsString("MobileVerified");
            String EmailVerified = data.getObjectAsString("EmailVerified");
            String confirmbid = data.getObjectAsString("confirmbid");
            if (MobileVerified.equalsIgnoreCase("true") && EmailVerified.equalsIgnoreCase("true"))
            {
                if (confirmbid.equals("1"))
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
                    iv_icon.setVisibility(View.GONE);

                String img_name = country.getStr_thumbnail();
                Thumbnail = img_name.replace("paintings/", "");
                Reference = country.getReference();
                OldPriceRs = country.getPriceus();
                OldPriceUs = country.getPricers();
                Auctionid = country.getOnline();
                    Bidclosingtime= country.getStr_Bidclosingtime();
                Ufirst_name = country.getStr_FirstName();
                Ulastname = country.getStr_LastName();

                if (is_us)
                {
                    int int_bid_us = 0;
                    int myNum = Integer.parseInt(country.getPricers());
                    if (myNum < 10000000)
                    {
                        int_bid_us = Get_10_value(country.getPriceus());
                    }
                    else
                    {
                        int_bid_us = Get_5_value(country.getPriceus());
                    }

                    str_us_amount = String.valueOf(int_bid_us);
                    str_rs_amount = String.valueOf(int_bid_us);
                    String str_int_us = NumberFormat.getNumberInstance(Locale.US).format(int_bid_us);

                    tv_bidvalue.setText("US$ "+str_int_us);
                    tv_bidlot.setText("Lot " + country.getReference().trim());
                    iv_icon.setText("US$");
                }
                else
                {
                    int int_bid_rs = 0;


                    int myNum = Integer.parseInt(country.getPricers());
                    if (myNum < 10000000)
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

                    tv_bidvalue.setText("₹ "+rs_value);
                    tv_bidlot.setText("Lot " + country.getReference().trim());
                    iv_icon.setText("₹");

                }
                tv_confim.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        String rs_amount = str_rs_amount;
                        String us_amount = str_us_amount;
                        String productid = country.getStr_productid();
                        String userid = data.getObjectAsString("userid");
                        String dollerrate = country.getDollarRate();
                        f_lot = country.getReference();

                        //String str_us = Get_US_value(dollerrate, rs_amount);
                        String str_us = MakeBid.Get_US_value(dollerrate, rs_amount);

                        if (is_us)
                        {
                            if (utility.checkInternet())
                            {
                                String Str_productid = country.getStr_productid();


                                int a = Integer.parseInt(us_amount);
                                int b = Integer.parseInt(dollerrate);

                                int str_rsonus = a * b;

                                String proxy_new_us = Integer.toString(str_rsonus);
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);

                                GetData("US", Str_productid, proxy_new_us, productid, userid, dollerrate, us_amount, f_lot);

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


                            if (utility.checkInternet())
                            {
                                String Str_productid = country.getStr_productid();
//                                BidNow(us_amount, productid, userid, dollerrate, str_us,f_lot);

                                GetData("US", Str_productid, us_amount, productid, userid, dollerrate, str_us, f_lot);

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
                bid_now.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bid_now.dismiss();
                    }
                });

                bid_now.show();
                Window window = bid_now.getWindow();
               // window.setLayout(850, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                bid_now.setCanceledOnTouchOutside(false);

                }
                else
                {
                    //Toast.makeText(context, "You do not have bidding access", Toast.LENGTH_SHORT).show();
                    show_dailog("You don't have access to Bid Please contact Astaguru.");
                }
            }
                else
                {
                    Intent intent = new Intent(context,Verification_Activity.class);
                    intent.putExtra("Activity","Login");
                    context.startActivity(intent);

                }
        }




    }

    public void Function_ProxyBid(int position)
    {
        String status = data.getObjectAsString("login");
        final Current_Auction_Model country = appsList.get(position);

        if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
        {
            Intent intent = new Intent(context,Before_Login_Activity.class);
            context.startActivity(intent);

        }
        else {

            String MobileVerified = data.getObjectAsString("MobileVerified");
            String EmailVerified = data.getObjectAsString("EmailVerified");
            String confirmbid = data.getObjectAsString("confirmbid");
            if (MobileVerified.equalsIgnoreCase("true") && EmailVerified.equalsIgnoreCase("true"))
            {

                if (confirmbid.equals("1"))
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
                    String img_name = country.getStr_thumbnail();
                    Thumbnail = img_name.replace("paintings/", "");
                    Reference = country.getReference();
                    OldPriceRs = country.getPriceus();
                    OldPriceUs = country.getPricers();
                    Auctionid = country.getOnline();
                    Bidclosingtime= country.getStr_Bidclosingtime();
                    Ufirst_name = country.getStr_FirstName();
                    Ulastname = country.getStr_LastName();

                    iv_iconproxy.setVisibility(View.GONE);

                if (is_us) {
                    int int_proxy_bid_us = 0;


                    int myNum = Integer.parseInt(country.getPricers());
                    if (myNum < 10000000) {
                        int_proxy_bid_us = Get_10_value(country.getPriceus());
                    } else {
                        int_proxy_bid_us = Get_5_value(country.getPriceus());
                    }
                    value_for_cmpr = String.valueOf(int_proxy_bid_us);
                    String str_int_xus = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_us);

                    tv_bidvalue.setText("US$ "+str_int_xus);
                    tv_proxylot.setText("Lot " + country.getReference().trim());
                    iv_iconproxy.setText("US$");
                }
                else
                {

                    int int_proxy_bid_rs = 0;

                    int myNum = Integer.parseInt(country.getPricers());
                    if (myNum < 10000000)
                    {
                        int_proxy_bid_rs = Get_10_value(country.getPriceus());
                    }
                    else
                    {
                        int_proxy_bid_rs = Get_5_value(country.getPriceus());
                    }

                    value_for_cmpr = String.valueOf(int_proxy_bid_rs);
                    rs_value = NumberFormat.getNumberInstance(Locale.US).format(int_proxy_bid_rs);

                    str_rs_amount = String.valueOf("₹ "+int_proxy_bid_rs);
                    tv_proxylot.setText("Lot " + country.getReference().trim());
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
                        else
                        {

                            String entered_value = edt_proxy.getText().toString();
                            String bid_value = value_for_cmpr;
                            int int_entered_value = Integer.parseInt(entered_value);
                            int int_bid_value = Integer.parseInt(bid_value);

                            if (int_entered_value > int_bid_value)
                            {
                                if (is_us)
                                {

                                    String str_Proxy_for_us = edt_proxy.getText().toString();

                                    int fb1 = Integer.parseInt(dollerRate);
                                    int rl1 = Integer.parseInt(str_Proxy_for_us);

                                    int str_ProxyAmtrs = rl1 * fb1;

                                    String proxy_amt_for_rs = Integer.toString(str_ProxyAmtrs);
                                   // Toast.makeText(context, "from US", Toast.LENGTH_SHORT).show();


                                    if (utility.checkInternet())
                                    {
                                        ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_for_rs, f_lot);

                                    }
                                    else
                                    {
                                        show_dailog("Please Check Internet Connection");

                                    }


                                }
                                else
                                {
                                   // Toast.makeText(context, "From RS", Toast.LENGTH_SHORT).show();

                                    if (utility.checkInternet())
                                    {
                                        ProxyBid(str_ProxyAmt, productID, siteUserID, dollerRate, proxy_amt_us, f_lot);
                                    }
                                    else
                                    {

                                        show_dailog("Please Check Internet Connection");
                                    }


                                }

                            }
                            else
                            {

                                Toast.makeText(context, "Proxy Must Greater Than Current Bid Value", Toast.LENGTH_SHORT).show();

                            }


                        }

                    }
                });
                bid_proxy = dialogBuilder.create();
                bid_proxy.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bid_proxy.dismiss();
                    }
                });


                bid_proxy.show();
                Window window = bid_proxy.getWindow();
                window.setLayout(width-200, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                bid_proxy.setCanceledOnTouchOutside(false);

            }
            else
            {
                //Toast.makeText(context, "You do not have bidding access", Toast.LENGTH_SHORT).show();
                show_dailog("You don't have access to Bid Please contact Astaguru.");
            }
            }
            else
            {
                Intent intent = new Intent(context, Verification_Activity.class);
                intent.putExtra("Activity", "Login");
                context.startActivity(intent);

            }
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

        if (utility.checkInternet())
        {
            String strPastAuctionUrl =Application_Constants.Main_URL+"Acution?api_key="+ Application_Constants.API_KEY+"&filter=productid="+str_productid+"";
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
        Bidclosingtime = Bidclosingtime.replace(" ","%20");
        if (utility.checkInternet())
        {
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spBid("+str_Amount+","+str_productID+","+str_userID+","+dollerrate+","+proxy_new_us+","+Thumbnail+","+Reference+","+OldPriceRs+","+OldPriceUs+","+Auctionid+","+Bidclosingtime+","+Ufirst_name+","+Ulastname+")?api_key="+ Application_Constants.API_KEY;

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
                                String msg = Obj.getString("msg");
                                //String emailID = Obj.getString("emailID");
                                // String mobilrNum = Obj.getString("mobileNum");

//                                Toast.makeText(mContext,currentStatus, Toast.LENGTH_SHORT).show();
                                if(currentStatus.equals("1"))
                                {

                                   // Toast.makeText(context, "You Succesfully Bid", Toast.LENGTH_SHORT).show();
                                    String number = Obj.getString("mobileNum");
                                    bid_now.dismiss();

                                    show_dailog("Your bid submitted successfully, currently you are leading for this product");
                                    userOutOfBid(str_lot,str_amt,number);
                                    sendOutOfBidMail(str_lot,str_amt,Obj.getString("emailID"));

                                }
                                else if (currentStatus.equals("2"))
                                {

                                   // Toast.makeText(context, "U r out of bid", Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                    show_dailog("Sorry You are out off bid because already higher proxybid is their, do you want to bid again");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                   Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                    bid_now.dismiss();

                                   // show_dailog("You can not bid for this lot right now as you are already leading for this lot.");

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


    private void userOutOfBid(String lot,String value,String mobile){

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


    private void sendOutOfBidMail(String str_lot,String str_amt,String strEmail) throws JSONException
    {

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        hud.show();

        try
        {

         String  msg = "Dear User \n We would like to bring it to your notice that you have been outbid on Lot# "+ str_lot+", in the ongoing AstaGuru Online Auction. Your highest bid was on Rs.51,44,612($82,978). The current highest bid stands at Rs.56,59,073 ($91,275). Continue to contest for Lot #1, please place your updated bid here, click here.\n\nThanking You,\n\n Warm Regards, \n Team of AstaGuru";
            String URL_url = "http://52.66.4.131/api/v2/awsses/?api_key=6dee0a21388b49db917cd64559c32bcbde93460f391a594ab7cc6666824d5c26";

            final JSONArray array = new JSONArray();
           /* final HashMap<String, String> toArray = new HashMap<String, String>();
            toArray.put("name",str_username);
            toArray.put("email", str_email);*/

            JSONObject jb = new JSONObject();
            jb.put("name",strUserName);
            jb.put("email",strEmail);

            array.put(jb);
            Map<String, String> params = new HashMap();
            params.put("template","newsletter");
            params.put("subject"," AstaGuru - You have been Outbid on Lot#" + str_lot);
            params.put("body_text",msg);
            params.put("from_name","NetSpace India SES");
            params.put("from_email","beta@netspaceindia.com");
            params.put("reply_to_name","NetSpace India");
            params.put("reply_to_email","beta@netspaceindia.com");

            JSONObject parameters = new JSONObject(params);
            parameters.put("to",array);

            System.out.println("parameters" + parameters);

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL_url, parameters, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("response" + response);
                    hud.dismiss();
                    Toast.makeText(getActivity(),"Mail Send Successfully ",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    System.out.println("error" + error);

                }
            });

            Volley.newRequestQueue(getActivity()).add(jsonRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void ProxyBid(String userProxyAmount,String productID,String siteUserID,String dollerRate,String dollerAmt,String f_lot)
    {
        Bidclosingtime = Bidclosingtime.replace(" ","%20");
        if (utility.checkInternet()) {
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spCurrentProxyBid("+userProxyAmount+","+productID+","+siteUserID+","+dollerRate+","+dollerAmt+","+Thumbnail+","+Reference+","+OldPriceUs+","+OldPriceRs+","+Auctionid+","+Bidclosingtime+","+Ufirst_name+","+Ulastname+")?api_key="+ Application_Constants.API_KEY;

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
                               // String msg = Obj.getString("msg");
//                                String emailID = Obj.getString("emailID");
//                                String mobilrNum = Obj.getString("mobilrNum");

                                if(currentStatus.equals("1"))
                                {

                                    String number = Obj.getString("mobileNum");
                                    bid_proxy.dismiss();

                                    show_dailog("Your Proxy bid submitted successfully,currently you are leading for this product");
                                    userOutOfBid(lot_no,userproxy,number);
                                    sendOutOfBidMail(lot_no,userproxy,Obj.getString("emailID"));


                                }
                                else if (currentStatus.equals("2"))
                                {

                                    bid_proxy.dismiss();

                                    show_dailog("Sorry You are out off bid because already higher proxybid is their, do you want to bid again");

                                }
                                else if (currentStatus.equals("3"))
                                {

                                    bid_proxy.dismiss();

                                    show_dailog("New Proxy Bid Value Should Be Greater Then Current Proxy Bid Value.");

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

    public void clearLink()
    {
        strCurrentCallUrl = Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY;
    }
    public static int countWords(String s)
    {
        int wordCount = 0;
        try
        {


            boolean word = false;
            int endOfLine = s.length() - 1;

            for (int i = 0; i < s.length(); i++) {
                // if the char is a letter, word = true.
                if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                    word = true;
                    // if char isn't a letter and there have been letters before,
                    // counter goes up.
                } else if (!Character.isLetter(s.charAt(i)) && word) {
                    wordCount++;
                    word = false;
                    // last word of String; if it doesn't end with a non letter, it
                    // wouldn't count without this.
                } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                    wordCount++;
                }
            }
            Log.d("WORD_COUND",""+wordCount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        return wordCount;
    }

    public void webCallTimer()
    {
        is_first = false;
        is_start = true;

        Thread closeActivity = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(10000);

                    try
                    {
//                appsList.clear();
                        String auction  = data.getObjectAsString("selected");

                        if(is_filter)
                        {
                            getUpcomingAuction(strCurrentCallUrl);
                        }
                        else
                        {
                            if(auction.equalsIgnoreCase("lot"))
                            {
                                strCurrentCallUrl =  Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY;
                                getUpcomingAuction(strCurrentCallUrl);

                            }
                            else if(auction.equalsIgnoreCase("lettest"))
                            {
                                strCurrentCallUrl =  Application_Constants.Main_URL+"lotslatest?api_key="+ Application_Constants.API_KEY;
                                getUpcomingAuction(strCurrentCallUrl);

                            }
                            else if(auction.equalsIgnoreCase("significant"))
                            {

                                strCurrentCallUrl =  Application_Constants.Main_URL+"lotssignificant?api_key="+ Application_Constants.API_KEY;
                                getUpcomingAuction(strCurrentCallUrl);

                            }
                            else if(auction.equalsIgnoreCase("popular"))
                            {

                                strCurrentCallUrl =  Application_Constants.Main_URL+"lotspopular?api_key="+ Application_Constants.API_KEY;
                                getUpcomingAuction(strCurrentCallUrl);

                            }
                            else if(auction.equalsIgnoreCase("comingsoon"))
                            {
                                strCurrentCallUrl =  Application_Constants.Main_URL+"lotsclosingtime?api_key="+ Application_Constants.API_KEY;
                                getUpcomingAuction(strCurrentCallUrl);

                            }
                            else if(auction.equalsIgnoreCase("defaultlots"))
                            {
                                strCurrentCallUrl =  Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY;
                                getUpcomingAuction(strCurrentCallUrl);

                            }
                            else if(auction.equalsIgnoreCase("filter"))
                            {
                                strCurrentCallUrl =  Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY+"&filter="+filter_url_string+"";
                                getUpcomingAuction(strCurrentCallUrl);

                            }
                            else
                            {
                                strCurrentCallUrl =  Application_Constants.Main_URL+"defaultlots?api_key="+ Application_Constants.API_KEY;
                                getUpcomingAuction(strCurrentCallUrl);

                            }

                        }



                    }
                    finally {

                        mHandler.postDelayed(mStatusChecker, mInterval);
                    }



                    // Do some stuff
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }
        });
        closeActivity.start();
    }


/*    private void GetSearchResult(String auction, String str_search)
    {

        if (utility.checkInternet()) {

            String Auction_Type = auction;

            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spSearch("+str_search+","+Auction_Type+")?api_key="+ Application_Constants.API_KEY;

            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;


                    try {
                        if (str_json != null)
                        {
                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    str_productid = Obj.getString("productid");
                                    str_title = Obj.getString("title");
//                                    str_description = Obj.getString("description");
                                    str_description = "no data for this";
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
                                    DollarRate = Obj.getString("DollarRate");
                                    reference = Obj.getString("reference");
                                    str_status = Obj.getString("status");
                                    bidartistuserid = "12";
                                    String newtext = reference.trim();

                                    System.out.println("str_status" + str_status);
                                    str_Profile = "Profile";

                                    artist_name = str_FirstName+str_LastName;


//                                    JSONObject obj_category_by_categoryid = Obj.getJSONObject("category_by_categoryid");

                                    str_category = Obj.getString("category");




                                    Model_Search  = new Model_Search( str_productid,  str_category,  artist_name,  str_Profile,  str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,  str_description,  str_title,str_Bidclosingtime,true,pricers,priceus,medium, productsize,estamiate,DollarRate,newtext,bidartistuserid);
                                    appsList.add(Model_Search);

                                }

                                search_adpter = new Search_Adpter(context,R.layout.search_single,appsList,false);
                                gridview.setAdapter(search_adpter);



                            } else {
                                Toast.makeText(context, "Result Not Found", Toast.LENGTH_SHORT).show();
                                gridview.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(context, "Result Not Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }*/
}