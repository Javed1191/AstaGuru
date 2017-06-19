package com.infomanav.astaguru;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model_classes.Model_Search;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by fox-2 on 12/1/2016.
 */

public class Search_Activity extends AppCompatActivity {

    ArrayList<model_classes.Model_Search> appsList;

    private Utility utility;
    private GridView gridview;
    Context context;
    String str_userid;

    Model_Search Model_Search;
    SessionData sessionData;
    String str_search;

    String str_productid,str_title,str_description,str_artistid,reference,str_thumbnail,str_image,str_productsize,str_category,str_small_img,str_Bidpricers,str_Bidpriceus;

    String artistid,str_FirstName,str_LastName,str_Profile, medium, productsize,estamiate,DollarRate;

    String pricers, priceus, artist_name, str_Bidclosingtime, image, auctiondate, bidartistuserid;

    EditText edt_search;
    String str_status;
    TextView tv_Current,tv_Upcomming,tv_Past;

    LinearLayout lin_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);


        context =Search_Activity.this;
        sessionData = new SessionData(context);

        str_userid = sessionData.getObjectAsString("userid");

        appsList = new ArrayList<>();
        utility = new Utility(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edt_search = (EditText)toolbar.findViewById(R.id.edt_search);

        lin_text = (LinearLayout)findViewById(R.id.lin_text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        tv_Current = (TextView) findViewById(R.id.tv_Current);
         tv_Upcomming= (TextView) findViewById(R.id.tv_Upcomming);
        tv_Past= (TextView) findViewById(R.id.tv_Past);
        lin_text.setVisibility(View.VISIBLE);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                lin_text.setVisibility(View.VISIBLE);
                gridview.setVisibility(View.GONE);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                lin_text.setVisibility(View.VISIBLE);
                gridview.setVisibility(View.GONE);
            }
        });

        tv_Current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appsList.clear();
                str_search = edt_search.getText().toString().trim();
                if (str_search != null && !str_search.isEmpty())
                {
                   // GetSearchResult("current");
                  //  lin_text.setVisibility(View.GONE);
                  //  gridview.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(Search_Activity.this,MainActivity.class);
                    intent.putExtra("type","search");
                    intent.putExtra("key",str_search);
                    intent.putExtra("auction","current");
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, "Please Enter Search Key", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_Upcomming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appsList.clear();
                str_search = edt_search.getText().toString().trim();
                if (str_search != null && !str_search.isEmpty())
                {
                   // GetSearchResult("Upcomming");
                    //lin_text.setVisibility(View.GONE);
                    gridview.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(Search_Activity.this,Past_Auction_SubActivity.class);
                    intent.putExtra("type","search");
                    intent.putExtra("key",str_search);
                    intent.putExtra("auction","Upcomming");
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, "Please Enter Search Key", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_Past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appsList.clear();
                str_search = edt_search.getText().toString().trim();

                if (str_search != null && !str_search.isEmpty())
                {

                   // GetSearchResult("Past");

                  //  lin_text.setVisibility(View.GONE);
                    gridview.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(Search_Activity.this,Past_Auction_SubActivity.class);
                    intent.putExtra("type","search");
                    intent.putExtra("key",str_search);
                    intent.putExtra("auction","Past");
                    startActivity(intent);
                }
                else
                {

                    Toast.makeText(context, "Please Enter Search Key", Toast.LENGTH_SHORT).show();

                }



            }
        });


        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        gridview = (GridView) findViewById(R.id.gridview);





    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search)
        {
            finish();

        }


        return super.onOptionsItemSelected(item);
    }
}

