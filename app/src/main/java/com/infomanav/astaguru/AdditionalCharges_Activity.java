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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.MyPurchasesAdpter;
import butterknife.ButterKnife;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 04-10-2016.
 */

public class AdditionalCharges_Activity extends AppCompatActivity {

    ArrayList<MyPurchases_Model> appsList;
    Utility utility;
    private TextView tv_billing_name,tv_billing_address,tv_city,tv_state,tv_zip,tv_cntry,tv_tel_phn,tv_emaiid,tv_lot,tv_ac_artist,tv_ac_category,tv_ac_medium
            ,tv_ac_year,tv_ac_size,tv_ac_hummerprice,tv_ac_byerprimium,tv_ac_vatonhummer,tv_ac_servicetax,tv_ac_grandtotal;

    private ImageView iv_main_img,iv_closeactivity;
    String currency_type,str_priceus,str_pricers;
TextView tv_update_address;

    SessionData data;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_charges);

        utility = new Utility(getApplicationContext());
        context = AdditionalCharges_Activity.this;
        data = new SessionData(context);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setTitle("");

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Additional Charges");

        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ButterKnife.bind(this);

        init();
        String str_BillingName = data.getObjectAsString("BillingName");
        String str_BillingAddress = data.getObjectAsString("BillingAddress");
        String str_BillingCity = data.getObjectAsString("BillingCity");
        String str_BillingState = data.getObjectAsString("BillingState");
        String str_BillingCountry = data.getObjectAsString("BillingCountry");
        String str_BillingZip = data.getObjectAsString("BillingZip");
        String str_BillingTelephone = data.getObjectAsString("BillingTelephone");
        String str_BillingEmail = data.getObjectAsString("BillingEmail");


                tv_billing_name.setText(str_BillingName);
                tv_billing_address.setText(str_BillingAddress);
                tv_city.setText(str_BillingCity);
                tv_state.setText(str_BillingState);
                tv_zip.setText(str_BillingZip);
                tv_cntry.setText(str_BillingCountry);
                tv_tel_phn.setText(str_BillingTelephone);
                tv_emaiid.setText(str_BillingEmail);


        Intent intent = getIntent();

        String str_refrence =intent.getStringExtra("str_refrene");
        tv_lot.setText("Lot:"+str_refrence.trim());
        String str_first = intent.getStringExtra("str_FirstName");
        String str_second = intent.getStringExtra("str_LastName");
        String fullname = str_first +""+ str_second;
        tv_ac_artist.setText(fullname);
        tv_ac_category.setText(intent.getStringExtra("str_category"));
        tv_ac_medium.setText(intent.getStringExtra("str_medium"));
        tv_ac_year.setText(intent.getStringExtra("str_date"));
        tv_ac_size.setText(intent.getStringExtra("str_productsize"));
        str_pricers = intent.getStringExtra("str_pricers");
        currency_type = intent.getStringExtra("currency_type");
        str_priceus= intent.getStringExtra("str_priceus");


        if (currency_type.equals("USD"))
        {
            tv_ac_hummerprice.setText(str_priceus);
            double amount = Double.parseDouble(str_priceus);

            double byerprimium = (amount / 100.0f) * 15;
            int intbyerprimium = (int) byerprimium;
            String valuebyerprimium = String.valueOf(intbyerprimium);
            tv_ac_byerprimium.setText(valuebyerprimium);

            double vatonhummer = (amount / 100.0f) * 12.5;

            int intvatonhummer = (int) vatonhummer;
            String valuevatonhummer = String.valueOf(intvatonhummer);
            tv_ac_vatonhummer.setText(valuevatonhummer);

            double servicetax = (vatonhummer / 100.0f) * 14.5;

            int intservicetax = (int) servicetax;

            String valueservicetax = String.valueOf(intservicetax);
            tv_ac_servicetax.setText(valueservicetax);


            Double a = new Double(Double.parseDouble(str_pricers));
            Double b = new Double(Double.parseDouble(valuebyerprimium));
            Double c = new Double(Double.parseDouble(valuevatonhummer));
            Double d = new Double(Double.parseDouble(valueservicetax));
            double sum = round(a)+round(b) +round(c)+round(d);

            int intgrandtotal = (int) sum;

            tv_ac_grandtotal.setText(String.valueOf(intgrandtotal));
        }
        else
        {
            tv_ac_hummerprice.setText(str_pricers);
            double amount = Double.parseDouble(str_pricers);

            double byerprimium = (amount / 100.0f) * 15;
            int intbyerprimium = (int) byerprimium;
            String valuebyerprimium = String.valueOf(intbyerprimium);
            tv_ac_byerprimium.setText(valuebyerprimium);

            double vatonhummer = (amount / 100.0f) * 12.5;

            int intvatonhummer = (int) vatonhummer;
            String valuevatonhummer = String.valueOf(intvatonhummer);
            tv_ac_vatonhummer.setText(valuevatonhummer);

            double servicetax = (vatonhummer / 100.0f) * 14.5;

            int intservicetax = (int) servicetax;

            String valueservicetax = String.valueOf(intservicetax);
            tv_ac_servicetax.setText(valueservicetax);


            Double a = new Double(Double.parseDouble(str_pricers));
            Double b = new Double(Double.parseDouble(valuebyerprimium));
            Double c = new Double(Double.parseDouble(valuevatonhummer));
            Double d = new Double(Double.parseDouble(valueservicetax));
            double sum = round(a)+round(b) +round(c)+round(d);

            int intgrandtotal = (int) sum;

            tv_ac_grandtotal.setText(String.valueOf(intgrandtotal));
        }




        Picasso.with(getApplicationContext()).load(Application_Constants.PAST_AUCTION_IMAGE_PATH +intent.getStringExtra("str_image"))
                .into(iv_main_img);
        intent.getStringArrayExtra("str_LastName");
        intent.getStringArrayExtra("str_category");
        intent.getStringArrayExtra("str_medium");
        intent.getStringArrayExtra("str_date");
        intent.getStringArrayExtra("str_productsize");





        iv_closeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_update_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status = data.getObjectAsString("login");
                if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
                {
                    Intent intent = new Intent(context,Before_Login_Activity.class);
                    context.startActivity(intent);

                }
                else {
                    Intent intent = new Intent(context, MyProfile_Activity.class);


                    context.startActivity(intent);
                }
            }
        });


//        getUpcomingAuction();

    }

    double round(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#");
        return Double.valueOf(twoDForm.format(d));
    }


    public void init()
    {
        utility = new Utility(getApplicationContext());

        tv_billing_name = (TextView) findViewById(R.id.tv_billing_name);
        tv_billing_address= (TextView) findViewById(R.id.tv_billing_address);
        tv_city= (TextView) findViewById(R.id.tv_city);
        tv_state= (TextView) findViewById(R.id.tv_state);
        tv_zip= (TextView) findViewById(R.id.tv_zip);
        tv_cntry= (TextView) findViewById(R.id.tv_cntry);
        tv_cntry= (TextView) findViewById(R.id.tv_cntry);
        tv_tel_phn= (TextView) findViewById(R.id.tv_tel_phn);
        tv_emaiid= (TextView) findViewById(R.id.tv_emaiid);
        tv_lot= (TextView) findViewById(R.id.tv_lot);
        tv_ac_artist= (TextView) findViewById(R.id.tv_ac_artist);
        tv_ac_category= (TextView) findViewById(R.id.tv_ac_category);
        tv_ac_medium= (TextView) findViewById(R.id.tv_ac_medium);
        tv_ac_year= (TextView) findViewById(R.id.tv_ac_year);
        tv_ac_size= (TextView) findViewById(R.id.tv_ac_size);
        tv_ac_hummerprice= (TextView) findViewById(R.id.tv_ac_hummerprice);
        tv_ac_byerprimium= (TextView) findViewById(R.id.tv_ac_byerprimium);
        tv_ac_vatonhummer= (TextView) findViewById(R.id.tv_ac_vatonhummer);
        tv_ac_servicetax= (TextView) findViewById(R.id.tv_ac_servicetax);
        tv_ac_grandtotal= (TextView) findViewById(R.id.tv_ac_grandtotal);
        iv_main_img = (ImageView) findViewById(R.id.iv_main_img);
        iv_closeactivity = (ImageView) findViewById(R.id.iv_closeactivity);
        tv_update_address= (TextView) findViewById(R.id.tv_update_address);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                String status = data.getObjectAsString("login");

                if (status.equalsIgnoreCase("true"))
                {
                    Intent intent = new Intent(AdditionalCharges_Activity.this,MyAstaGuru_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(AdditionalCharges_Activity.this,Before_Login_Activity.class);
                    startActivity(intent);
                }

                return true;
            case R.id.action_search:
                // do whatever
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}


