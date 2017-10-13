package com.infomanav.astaguru;

import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.Careeer_Adpter;
import adapter.ManagmentAdapter;
import adapter.SpecialistGridAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Utility;
import views.ExpandableHeightGridView;

import static com.infomanav.astaguru.R.id.listView;
import static com.infomanav.astaguru.R.id.spn_post;

/**
 * Created by android-javed on 07-10-2016.
 */
//4B:A3:CB:A2:47:60:A5:63:54:07:37:DB:53:61:7C:05:AA:7B:C5:07;com.infomanav.astaguru
public class About_UsActivity extends AppCompatActivity {

    private GridLayoutManager lLayout,lLayout1;
    private ExpandableHeightGridView mAppsGrid;
    private SpecialistGridAdapter specialistGridAdapter;
    static Point size;
    static float density;
    private LinearLayout lay_specialist,lay_managment;
    private View view_horizontal;
    private Utility utility;
    RecyclerView rView1;
    private TextView tv_aboutus;
    private NestedScrollView scroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);
        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("About Us");
        utility = new Utility(About_UsActivity.this);

        Display display = getWindowManager().getDefaultDisplay();
        size=new Point();
        DisplayMetrics dm=new DisplayMetrics();
        display.getMetrics(dm);
        density=dm.density;
        display.getSize(size);


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        Typeface type1 = Typeface.createFromAsset(getAssets(),"WorkSans-Regular.otf");


        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        WebView webView = (WebView) findViewById(R.id.tv_textabout);
        lay_managment = (LinearLayout) findViewById(R.id.lay_managment);
        lay_specialist = (LinearLayout) findViewById(R.id.lay_specialist);
        view_horizontal = findViewById(R.id.view_horizontal);
        tv_aboutus = (TextView) findViewById(R.id.tv_aboutus);
        scroller = (NestedScrollView) findViewById(R.id.scroller);

        webView.loadUrl("file:///android_asset/About_us.html");
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url)
            {

                lay_managment.setVisibility(View.VISIBLE);
                lay_specialist.setVisibility(View.VISIBLE);
                view_horizontal.setVisibility(View.VISIBLE);

                mAppsGrid = (ExpandableHeightGridView)findViewById(R.id.myId);
                mAppsGrid.setExpanded(true);

               // List<ItemManagment> rowListManagmentItem = getManagmentList();
                lLayout1 = new GridLayoutManager(getApplicationContext(),3);

                rView1 = (RecyclerView)findViewById(R.id.recycler_view1);
                rView1.setNestedScrollingEnabled(false);
                rView1.setHasFixedSize(true);
                rView1.setLayoutManager(lLayout1);

                // do your stuff here

                getAboutUs();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close)
        {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

   /* private List<ItemSpecialist> getSpecialistList(){

        List<ItemSpecialist> allItems = new ArrayList<ItemSpecialist>();
        allItems.add(new ItemSpecialist("Sunny Chandiramani","V.P. Client Relations", R.drawable.client_relation,"sunny@astaguru.com"));
        allItems.add(new ItemSpecialist("Sonal Patel","V.P. Client Relations", R.drawable.client_relation_1,"sonal@astaguru.com"));
        allItems.add(new ItemSpecialist("Sneha Gautam","V.P. Client Relations", R.drawable.client_relation_2,"sneha@astaguru.com"));
        allItems.add(new ItemSpecialist("Razia Parveen","Client Relations", R.drawable.client_relation_3,"razia@astaguru.com"));
        allItems.add(new ItemSpecialist("Ankita Talreja","Client Relations", R.drawable.client_relation_4,"ankita@astaguru.com"));
        allItems.add(new ItemSpecialist("Tahmina Lakhani","Client Relations", R.drawable.client_relation_5,"tahmina@astaguru.com"));
        allItems.add(new ItemSpecialist("Siddanth Shetty","V.P. Business Strategy & Operations", R.drawable.business_strategy,"siddanth@theartstrust.com"));
        allItems.add(new ItemSpecialist("Anthony Diniz","Administrator", R.drawable.administrator,"anthony@theartstrust.com"));
        allItems.add(new ItemSpecialist("Karthik Lynch","Logistics" ,R.drawable.logistics,"karthik@theartstrust.com"));
       // allItems.add(new ItemSpecialist("Tushar Dalvi","Marketing PR & Business Developement", R.drawable.marketing_1,"tushar.dalvi@theartstrust.com"));
        allItems.add(new ItemSpecialist("Sidhant Nayangara","Content Editor", R.drawable.client,"s.nayangara@theartstrust.com"));
        allItems.add(new ItemSpecialist("Radhika Kerkar","Research", R.drawable.research,"radhika@theartstrust.com"));
        allItems.add(new ItemSpecialist("Snehal Pednekar","Human Resource", R.drawable.human_resource,"snehal@theartstrust.com"));
        allItems.add(new ItemSpecialist("Abhay Menon","Online Marketing Manager", R.drawable.online_marketing,"abhay@theartstrust.com"));
        allItems.add(new ItemSpecialist("Rupesh Khanaa","Market Analyst", R.drawable.market_analysts,"upesh@theartstrust.com"));

        return allItems;
    }*/

    /*private List<ItemManagment> getManagmentList(){

        List<ItemManagment> allItems = new ArrayList<ItemManagment>();
        allItems.add(new ItemManagment("Vickram Sethi","Chairman", R.drawable.chairman));
        allItems.add(new ItemManagment("Tushar Sethi","CEO", R.drawable.ceo));
        allItems.add(new ItemManagment("Digamber Sethi","COO", R.drawable.coo));

        return allItems;
    }*/


    private void getAboutUs() {

        if (utility.checkInternet())
        {


            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spGetAboutUs?api_key="+ Application_Constants.API_KEY;
            System.out.println("spGetAboutUs " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();
            final List<ItemSpecialist> allItems = new ArrayList<ItemSpecialist>();
            final List<ItemManagment> managementList = new ArrayList<ItemManagment>();
            ServiceHandler serviceHandler = new ServiceHandler(About_UsActivity.this);
            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("spGetAboutUs Json responce" + result);

                    String str_json = result,Aboutus="";

                    try {
                        if (str_json != null)
                        {
                            if (str_json.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray(str_json);

                                    JSONArray aboutJsonArr = jsonArray.getJSONArray(0);
                                    JSONArray teamjsonArr = jsonArray.getJSONArray(1);
                                    for(int j=0;j<aboutJsonArr.length();j++)
                                    {
                                        JSONObject obj = aboutJsonArr.getJSONObject(j);
                                        Aboutus = obj.getString("Aboutus");
                                    }
                                    for(int k=0;k<teamjsonArr.length();k++)
                                    {
                                        JSONObject Obj = teamjsonArr.getJSONObject(k);
                                        String empid = Obj.getString("empid");
                                        String Empname = Obj.getString("Empname");
                                        String empdesc = Obj.getString("empdesc");
                                        String empemail = Obj.getString("empemail");
                                        String empimageurl = Obj.getString("empimageurl");
                                        String empmanagement  = Obj.getString("empmanagement");

                                        Empname = Empname.replace("\n", "").replace("\r", "");
                                        empdesc = empdesc.replace("\n", "").replace("\r", "");
                                        empemail = empemail.replace("\n", "").replace("\r", "");
                                        empimageurl = empimageurl.replace("\n", "").replace("\r", "");
                                        empmanagement = empmanagement.replace("\n", "").replace("\r", "");

                                        if(empmanagement.equalsIgnoreCase("1"))
                                        {
                                            ItemManagment itemManagment = new ItemManagment(Empname,empdesc,empimageurl);
                                            managementList.add(itemManagment);

                                        }else {
                                            ItemSpecialist itemSpecialist = new ItemSpecialist(Empname,empdesc,empimageurl,empemail);
                                            allItems.add(itemSpecialist);
                                        }
                                    }
                                tv_aboutus.setText(Aboutus);
                                specialistGridAdapter = new SpecialistGridAdapter(About_UsActivity.this,allItems);
                                mAppsGrid.setAdapter(specialistGridAdapter);

                                ManagmentAdapter rcAdapter1 = new ManagmentAdapter(getApplicationContext(), managementList);
                                rView1.setAdapter(rcAdapter1);

                                scroller.post(new Runnable() {
                                    public void run() {
                                        scroller.fullScroll(ScrollView.FOCUS_UP);
                                    }
                                });

                            } else {
                                Toast.makeText(About_UsActivity.this, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(About_UsActivity.this, "This may be server issue", Toast.LENGTH_SHORT).show();
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
}