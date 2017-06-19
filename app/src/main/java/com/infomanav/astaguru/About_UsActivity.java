package com.infomanav.astaguru;

import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.ManagmentAdapter;
import adapter.SpecialistGridAdapter;
import views.ExpandableHeightGridView;

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

        webView.loadUrl("file:///android_asset/About_us.html");
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url)
            {

                lay_managment.setVisibility(View.VISIBLE);
                lay_specialist.setVisibility(View.VISIBLE);
                view_horizontal.setVisibility(View.VISIBLE);

                mAppsGrid = (ExpandableHeightGridView)findViewById(R.id.myId);
                mAppsGrid.setExpanded(true);

                List<ItemSpecialist> rowListItem = getSpecialistList();
                specialistGridAdapter = new SpecialistGridAdapter(About_UsActivity.this,rowListItem);
                mAppsGrid.setAdapter(specialistGridAdapter);

                List<ItemManagment> rowListManagmentItem = getManagmentList();

                lLayout1 = new GridLayoutManager(getApplicationContext(),3);

                RecyclerView rView1 = (RecyclerView)findViewById(R.id.recycler_view1);
                rView1.setNestedScrollingEnabled(false);
                rView1.setHasFixedSize(true);
                rView1.setLayoutManager(lLayout1);

                ManagmentAdapter rcAdapter1 = new ManagmentAdapter(getApplicationContext(), rowListManagmentItem);
                rView1.setAdapter(rcAdapter1);
                // do your stuff here
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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

    private List<ItemSpecialist> getSpecialistList(){

        List<ItemSpecialist> allItems = new ArrayList<ItemSpecialist>();
        allItems.add(new ItemSpecialist("Sunny Chandiramani","Client Relations", R.drawable.client_relation,"sunny@theartstrust.com"));
        allItems.add(new ItemSpecialist("Sonal Patel","Client Relations", R.drawable.client_relation_1,"sonal@theartstrust.com"));
        allItems.add(new ItemSpecialist("Sneha Gautam","Client Relations", R.drawable.client_relation_2,"sneha@theartstrust.com"));
        allItems.add(new ItemSpecialist("Razia Parveen","Client Relations", R.drawable.client_relation_3,"razia@astaguru.com"));
        allItems.add(new ItemSpecialist("Ankita Talreja","Client Relations", R.drawable.client_relation_4,"ankita@astaguru.com"));
        allItems.add(new ItemSpecialist("Tahmina Lakhani","Client Relations", R.drawable.client_relation_5,"tahmina@astaguru.com"));
        allItems.add(new ItemSpecialist("Siddanth Shetty","V.P. Business Strategy & Operations", R.drawable.market_analysts,"siddanth@theartstrust.com"));
        allItems.add(new ItemSpecialist("Anthony Diniz","Administrator", R.drawable.administrator,"anthony@theartstrust.com"));
        allItems.add(new ItemSpecialist("Karthik Lynch","Logistics" ,R.drawable.logistics,"karthik@theartstrust.com"));
       // allItems.add(new ItemSpecialist("Tushar Dalvi","Marketing PR & Business Developement", R.drawable.marketing_1,"tushar.dalvi@theartstrust.com"));
        allItems.add(new ItemSpecialist("Sidhant Nayangara","Content Editor", R.drawable.client,"s.nayangara@theartstrust.com"));
        allItems.add(new ItemSpecialist("Radhika Kerkar","Research", R.drawable.research,"radhika@theartstrust.com"));
        allItems.add(new ItemSpecialist("Snehal Pednekar","Human Resource", R.drawable.human_resource,"snehal@theartstrust.com"));

        return allItems;
    }

    private List<ItemManagment> getManagmentList(){

        List<ItemManagment> allItems = new ArrayList<ItemManagment>();
        allItems.add(new ItemManagment("Vickram Sethi","Chairman", R.drawable.chairman));
        allItems.add(new ItemManagment("Tushar Sethi","CEO", R.drawable.ceo));
        allItems.add(new ItemManagment("Digamber Sethi","COO", R.drawable.coo));

        return allItems;
    }
}