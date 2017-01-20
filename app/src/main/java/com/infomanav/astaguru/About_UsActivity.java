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
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
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
    MainActivity mainActivity;
    private ExpandableHeightGridView mAppsGrid;
    private SpecialistGridAdapter specialistGridAdapter;
    TextView tv_text;
    static Point size;
    static float density;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        String htmlText = " %s ";
        String myData = "AstaGuru was conceptualised in the year 2008, with the sole purpose of creating a safe and secure platform to conduct online auctions for 'Contemporary &amp; Modern Indian Art', however we are constantly innovating &amp;\n" +
                "venturing into exciting new spectrums such as vintage collectibles and rear antiques, which include fine writing instruments, timepieces, celebrity memorabilia &amp;\n" +
                "aristocratic jewelry. The word AstaGuru is a combination of Asta which means auction in Italian and Guru which\n" +
                "indicates our mastery at conducting the same. With the world becoming whole on a digital level, we are able to showcase Indian Art &amp; Antiques with a prevalent legacy, on a global scale. Successfully linking the perspective buyer and consignor without any geographic constraints. With AstaGuru it is now possible for Art collectors and admirers to par take and get their fill of the exploding Indian Art culture. Our stringent selection process\n" +
                "ensures only prime artworks &amp; exquisite antiques are part of our auctions. Numerous criteria such as the\n" +
                "provenance of the work, the rarity of the work, and its physical condition are assessed before their inclusion in our auctions.\\n\\nUnder the leadership of Mr Vickram Sethi, founder &amp;\n" +
                "chairman of The Arts Trust, Institute of Contemporary Indian Art Gallery, Mumbai &amp; AstaGuru and Mr Tushar Sethi, CEO AstaGuru, we are treading towards a horizon filled with Art, Antiques &amp; Bliss.\\n\\nTheir keen eye and impeccable knowledge leverages us with insights of current &amp; future trends. Our aim is to manifest Indian Art globally and create a conducive\n" +
                "environment that spurts it's growth and to constantly\n" +
                "reinvent ourselves in order to cater to the needs of our esteemed clientele. ";

        WebView webView = (WebView) findViewById(R.id.tv_textabout);
        String text = "<html><body>"
                         + "<p align=\"justify\">"
                       + "AstaGuru was conceptualised in the year 2008, with the sole purpose of creating a safe and secure platform to conduct online auctions for 'Contemporary &amp; Modern Indian Art', however we are constantly innovating &amp;\n" +
                "venturing into exciting new spectrums such as vintage collectibles and rear antiques, which include fine writing instruments, timepieces, celebrity memorabilia &amp;\n" +
                "aristocratic jewelry. The word AstaGuru is a combination of Asta which means auction in Italian and Guru which\n" +
                "indicates our mastery at conducting the same. With the world becoming whole on a digital level, we are able to showcase Indian Art &amp; Antiques with a prevalent legacy, on a global scale. Successfully linking the perspective buyer and consignor without any geographic constraints. With AstaGuru it is now possible for Art collectors and admirers to par take and get their fill of the exploding Indian Art culture. Our stringent selection process\n" +
                "ensures only prime artworks &amp; exquisite antiques are part of our auctions. Numerous criteria such as the\n" +
                "provenance of the work, the rarity of the work, and its physical condition are assessed before their inclusion in our auctions.\\n\\nUnder the leadership of Mr Vickram Sethi, founder &amp;\n" +
                "chairman of The Arts Trust, Institute of Contemporary Indian Art Gallery, Mumbai &amp; AstaGuru and Mr Tushar Sethi, CEO AstaGuru, we are treading towards a horizon filled with Art, Antiques &amp; Bliss.\\n\\nTheir keen eye and impeccable knowledge leverages us with insights of current &amp; future trends. Our aim is to manifest Indian Art globally and create a conducive\n" +
                "environment that spurts it's growth and to constantly\n" +
                "reinvent ourselves in order to cater to the needs of our esteemed clientele. "
                         + "</p> "
                         + "</body></html>";
        String head1 = "<head><style>@font-face {font-family: 'arial';src: url('file:///android_asset/fonts/WorkSans-Regular.otf');}body {font-family: 'verdana';}</style></head>";

//        webView.loadData(text, "text/html", "utf-8");
        webView.loadUrl("file:///android_asset/About_us.html");
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        allItems.add(new ItemSpecialist("Mamata Rahate","Client Relations", R.drawable.client_relation_3,"mamta@theartstrust.com"));
        allItems.add(new ItemSpecialist("Siddanth Shettey","V.P. Business Strategy & Operations", R.drawable.market_analysts,"siddanth@theartstrust.com"));
        allItems.add(new ItemSpecialist("Anthony Diniz","Administrator", R.drawable.administrator,"anthony@theartstrust.com"));
        allItems.add(new ItemSpecialist("Anandita De","Marketing PR & Business Developement" ,R.drawable.marketing,"anandita@theartstrust.com"));
        allItems.add(new ItemSpecialist("Tushar Dalvi","Marketing PR & Business Developement", R.drawable.marketing_1,"tushar.dalvi@theartstrust.com"));
        allItems.add(new ItemSpecialist("Sidhant Nayangara","Content Editor", R.drawable.client,"s.nayangara@theartstrust.com"));

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