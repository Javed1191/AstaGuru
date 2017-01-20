package com.infomanav.astaguru;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 03-10-2016.
 */

public class MyAstaGuru_Activity extends AppCompatActivity
{




    private Utility utility;
    private Button btn_signout;
    private TextView tv_purchaes,tv_gallary,tv_my_profile;
    SessionData data;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myastaguru);

        context = MyAstaGuru_Activity.this;
        data = new SessionData(context);
        init();
       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("My AstaGuru");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);
        btn_signout.setText("Sign Out");
        btn_signout.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ButterKnife.bind(this);





        btn_signout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                data.setObjectAsString("login","false");

                data.setString("BillingName","");
                data.setString("BillingAddress","");
                data.setString("BillingCity","");
                data.setString("BillingState","");
                data.setString("BillingCountry","");
                data.setString("BillingZip","");
                data.setString("BillingTelephone","");
                data.setString("BillingEmail","");








                data.setString("userid","");
                data.setString("email","");
                data.setString("name","");

                Intent intent = new Intent(MyAstaGuru_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        tv_my_profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(MyAstaGuru_Activity.this,MyProfile_Activity.class);
                startActivity(intent);

            }
        });
        tv_gallary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(MyAstaGuru_Activity.this,Auction_Gallary_Activity.class);
                startActivity(intent);

            }
        });
        tv_purchaes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(MyAstaGuru_Activity.this,My_Purchases_Activity.class);
                startActivity(intent);

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


    public void init()
    {
        utility = new Utility(getApplicationContext());
        btn_signout = (Button) findViewById(R.id.btn_signout);
        tv_purchaes = (TextView) findViewById(R.id.tv_purchaes);
        tv_gallary = (TextView) findViewById(R.id.tv_gallary);
        tv_my_profile = (TextView) findViewById(R.id.tv_my_profile);


    }


}

