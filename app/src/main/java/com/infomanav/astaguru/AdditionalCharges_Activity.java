package com.infomanav.astaguru;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import butterknife.ButterKnife;
import model_classes.MyPurchases_Model;
import services.Application_Constants;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 04-10-2016.
 */

public class AdditionalCharges_Activity extends AppCompatActivity {

    ArrayList<MyPurchases_Model> appsList;
    Utility utility;
    private TextView tv_billing_name,tv_billing_address,tv_city,tv_state,tv_zip,tv_cntry,tv_tel_phn,
            tv_emaiid,tv_lot,tv_ac_artist,tv_ac_category,tv_ac_medium
            ,tv_ac_year,tv_ac_size,tv_ac_hummerprice,tv_ac_byerprimium,tv_ac_vatonhummer,
            tv_ac_servicetax,tv_ac_grandtotal,tv_ac_estimate,tv_desc;

    private ImageView iv_main_img,iv_closeactivity;
    String currency_type,str_priceus,str_pricers,Auctionname,Prdescription;
    TextView tv_update_address;
    LinearLayout lin_user_details;
    SessionData data;
    Context context;
    private RelativeLayout rel_desc;
    private LinearLayout lay_art_details;
    private RelativeLayout rel_cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_charges);

        utility = new Utility(getApplicationContext());
        context = AdditionalCharges_Activity.this;
        data = new SessionData(context);

        lin_user_details = (LinearLayout) findViewById(R.id.lin_user_details);
        rel_desc = (RelativeLayout) findViewById(R.id.rel_desc);
        lay_art_details = (LinearLayout) findViewById(R.id.lay_art_details);
        rel_cat = (RelativeLayout) findViewById(R.id.rel_cat);

        String status = data.getObjectAsString("login");
        if (status.equalsIgnoreCase("false")||status.isEmpty()||status.equalsIgnoreCase("Empty"))
        {

            lin_user_details.setVisibility(View.GONE);
        }
        else
        {
            lin_user_details.setVisibility(View.VISIBLE);
        }


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

        Auctionname = intent.getStringExtra("Auctionname");
        Prdescription = intent.getStringExtra("Prdescription");
        String str_refrence =intent.getStringExtra("str_refrene");
        String str_first = intent.getStringExtra("str_FirstName");
        String str_second = intent.getStringExtra("str_LastName");
        String fullname = str_first +" "+ str_second;
        String Size = intent.getStringExtra("str_productsize");

        tv_lot.setText("Lot:"+str_refrence.trim());

        tv_ac_size.setText(Size+" in");
        str_pricers = intent.getStringExtra("str_pricers");
        currency_type = intent.getStringExtra("currency_type");
        str_priceus= intent.getStringExtra("str_priceus");
        tv_ac_estimate.setText(intent.getStringExtra("str_estimate"));
        tv_desc.setText(Html.fromHtml(Prdescription));
        if(!Auctionname.equalsIgnoreCase("Collectibles Auction"))
        {
            // if data is about artist
            lay_art_details.setVisibility(View.VISIBLE);
            rel_desc.setVisibility(View.GONE);
            rel_cat.setVisibility(View.VISIBLE);

            tv_ac_artist.setText(fullname);
            tv_ac_category.setText(intent.getStringExtra("str_category"));
            tv_ac_medium.setText(intent.getStringExtra("str_medium"));
            tv_ac_year.setText(intent.getStringExtra("str_date"));
        }
        else
        {
            // if data is not about artist
            lay_art_details.setVisibility(View.GONE);
            rel_desc.setVisibility(View.VISIBLE);
            rel_cat.setVisibility(View.GONE);
        }


        if (currency_type.equals("USD"))
        {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
            double priceInUSD = Double.parseDouble(str_priceus);
            String d_value=  currencyFormat.format(priceInUSD);

            tv_ac_hummerprice.setText("US$ "+d_value.replaceAll("\\.00", "").replace("$",""));

            double amount = Double.parseDouble(str_priceus);

            double byerprimium = (amount / 100.0f) * 15;
            int intbyerprimium = (int) byerprimium;
            String valuebyerprimium = String.valueOf(intbyerprimium);

            double dbyerprimium = Double.parseDouble(valuebyerprimium);
            String d_byerprimium = currencyFormat.format(dbyerprimium);

            tv_ac_byerprimium.setText("US$ "+d_byerprimium.replaceAll("\\.00", "").replace("$",""));

            double vatonhummer = (amount / 100.0f) * 12.5;

            int intvatonhummer = (int) vatonhummer;
            String valuevatonhummer = String.valueOf(intvatonhummer);

            double dvatonhummer = Double.parseDouble(valuevatonhummer);
            String d_vatonhummer = currencyFormat.format(dvatonhummer);


            tv_ac_vatonhummer.setText("US$ "+d_vatonhummer.replaceAll("\\.00", "").replace("$",""));

            double servicetax = (vatonhummer / 100.0f) * 14.5;

            int intservicetax = (int) servicetax;

            String valueservicetax = String.valueOf(intservicetax);


            double dservicetax = Double.parseDouble(valueservicetax);
            String d_servicetax = currencyFormat.format(dservicetax);

            d_servicetax = calculatePercentage(String.valueOf(intbyerprimium));
            tv_ac_servicetax.setText("US$ "+d_servicetax.replaceAll("\\.00", "").replace("$",""));


            Double a = new Double(Double.parseDouble(str_pricers));
            Double b = new Double(Double.parseDouble(valuebyerprimium));
            Double c = new Double(Double.parseDouble(valuevatonhummer));
            Double d = new Double(Double.parseDouble(valueservicetax));
            double sum = round(a)+round(b) +round(c)+round(d);

            int intgrandtotal = (int) sum;

            String d_grandtotal = currencyFormat.format(sum);

            tv_ac_grandtotal.setText("US$ "+d_grandtotal.replaceAll("\\.00", "").replace("$",""));
        }
        else
        {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            System.out.println("str_pricers"+str_pricers);
            format.setCurrency(Currency.getInstance("INR"));
            String str_amt = rupeeFormat(str_pricers);

            tv_ac_hummerprice.setText("₹ "+str_amt);
            double amount = Double.parseDouble(str_pricers);

            double byerprimium = (amount / 100.0f) * 15;
            int intbyerprimium = (int) byerprimium;
            String valuebyerprimium = String.valueOf(intbyerprimium);


            String vale_byerprimium = rupeeFormat(valuebyerprimium);
            tv_ac_byerprimium.setText("₹ "+vale_byerprimium);

            double vatonhummer = (amount / 100.0f) * 13.5;

            int intvatonhummer = (int) vatonhummer;
            String valuevatonhummer = String.valueOf(intvatonhummer);


            String vale_vatonhummer = rupeeFormat(valuevatonhummer);
            tv_ac_vatonhummer.setText("₹ "+vale_vatonhummer);

            double servicetax = (vatonhummer / 100.0f) * 15;

            int intservicetax = (int) servicetax;

            String valueservicetax = String.valueOf(intservicetax);


           String strSeviceTax = calculatePercentage(String.valueOf(intbyerprimium));
            String vale_servicetax = rupeeFormat(strSeviceTax);

            tv_ac_servicetax.setText("₹ "+vale_servicetax);


            Double a = new Double(Double.parseDouble(str_pricers));
            Double b = new Double(Double.parseDouble(valuebyerprimium));
            Double c = new Double(Double.parseDouble(valuevatonhummer));
            Double d = new Double(Double.parseDouble(valueservicetax));
            double sum = round(a)+round(b) +round(c)+round(d);

           // int intgrandtotal = (int) sum;


            str_pricers = str_pricers.replaceAll(",","");
            vale_vatonhummer = vale_vatonhummer.replaceAll(",","");
            strSeviceTax = strSeviceTax.replaceAll(",","");
            int intgrandtotal = Integer.parseInt(str_pricers) + intbyerprimium+Integer.parseInt(vale_vatonhummer)+ Integer.parseInt(strSeviceTax);
            String vale_grandtotal = rupeeFormat(String.valueOf(intgrandtotal));
            tv_ac_grandtotal.setText("₹ "+vale_grandtotal);
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



    }

    double round(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#");
        return Double.valueOf(twoDForm.format(d));
    }

    public static String rupeeFormat(String value)
    {
        value=value.replace(",","");
        char lastDigit=value.charAt(value.length()-1);
        String result = "";
        int len = value.length()-1;
        int nDigits = 0;
        for (int i = len - 1; i >= 0; i--)
        {
            result = value.charAt(i) + result;
            nDigits++;
            if (((nDigits % 2) == 0) && (i > 0))
            {
                result = "," + result;
            }
        }
        return (result+lastDigit);
    }

    public static String DollerFormat(String value)
    {
        value=value.replace(",","");
        char lastDigit=value.charAt(value.length()-1);
        String result = "";
        int len = value.length()-1;
        int nDigits = 0;
        for (int i = len - 1; i >= 0; i--)
        {
            result = value.charAt(i) + result;
            nDigits++;
            if (((nDigits % 3) == 0) && (i > 0))
            {
                result = "," + result;
            }
        }
        return (result+lastDigit);
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
        tv_ac_estimate  = (TextView) findViewById(R.id.tv_ac_estimate);
        tv_desc = (TextView) findViewById(R.id.tv_desc);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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
                Intent intent = new Intent(AdditionalCharges_Activity.this,Search_Activity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private String calculatePercentage(String strPrise)
    {
        String strBidPrise="";
        Integer int_bid_prise = 0,int_discount=0;

        int_bid_prise = Integer.parseInt(strPrise);
        int_discount = ((Integer.parseInt(strPrise)*15)/100);
       // int_bid_prise = int_bid_prise+int_discount;

        strBidPrise = String.valueOf(int_discount);


        return strBidPrise;
    }

    private String replaceAll(String strAmount)
    {
        return strAmount.replaceAll(",","");
    }
}


