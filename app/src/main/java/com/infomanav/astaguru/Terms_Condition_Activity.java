package com.infomanav.astaguru;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import services.Utility;

/**
 * Created by android-javed on 05-10-2016.
 */

public class Terms_Condition_Activity extends AppCompatActivity
{
LinearLayout lin_law,lin_intent,lin_authenticate,lin_general;
    ImageView iv_law,iv_extent,iv_Authenticate,iv_general;


    TextView tv_website;
JustifiedTextView tv_law,tv_extent_one,tv_extent_two,tv_extent_three,tv_extent_four,tv_extent_five,tv_extent_six;

    JustifiedTextView tv_auth_one,tv_auth_two,tv_auth_three,tv_auth_four,tv_auth_five,tv_auth_six;

    JustifiedTextView tv_z,tv_gen_h,tv_gen_a,tv_gen_b,tv_gen_c,tv_gen_d,tv_gen_e,tv_gen_f,tv_gen_g,tv_gen_i,tv_gen_j,tv_gen_k,tv_gen_l,tv_gen_m,tv_gen_n,tv_gen_o;
    private Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);
        init();
        tv_law = (JustifiedTextView) findViewById(R.id.tv_law);
        tv_law.setText("The terms and conditions of this Auction are subject to the laws of India, which will apply to the construction and to the effect of the clauses. All parties are subject to the exclusive jurisdiction of the courts at Mumbai, Maharashtra, India.");

        tv_extent_one.setText("AstaGuru will obtain the money from the seller and thereafter refund to the buyer the amount of purchase in case the work is not authentic.");
                tv_extent_two.setText("All damages and loss during transit are covered by the insurance policy, AstaGuru is not liable.");
                tv_extent_three.setText("AstaGuru or any member of its team is not liable for any mistakes made in the catalogue.");
                tv_extent_four.setText("AstaGuru is not liable for any claims in insurance.");
                tv_extent_five.setText("AstaGuru is not liable in case the website has any technical problems.");
                tv_extent_six.setText("If any part of the Conditions for Sale between the Buyer and AstaGuru is found by any court to be invalid, illegal or unenforceable, that part may be discounted and the rest of the conditions shall be enforceable to the fullest extent permissible by law.");


        tv_auth_one.setText("AstaGuru assures on behalf of the seller that all properties on the website are genuine work of the artist listed.");
                tv_auth_two.setText("How ever in an unlikely event if the property is proved to be inauthentic to AstaGuru's satisfaction within a period of 6 months from the collection date. The seller shall be liable to pay back the full amount to the buyer. These claims will be handled on a case-by-case basis, and will require that examinable proof which clearly demonstrates that the Property is inauthentic is provided by an established and acknowledged authority. Only the actual Buyer (as registered with AstaGuru) makes the claim.");
        tv_auth_three.setText("The property, when returned, should be in the same condition as when it was purchased.");
        tv_auth_four.setText("In case the Buyer request for a certificate of authentication for a particular artwork, Astaguru will levy the expenditure of the same onto the Buyer.");
            tv_auth_five.setText("AstaGuru shall charge the buyer in case any steps are to be taken for special expenses shall take place in order to prove the authenticity of the property.");
            tv_auth_six.setText("In case the seller fails to refund the funds. Astaguru shall be authorized by the buyer to take legal action on behalf of the buyer to recover the money at the expense of the buyer.");

        tv_z.setText("By participating in this auction, you acknowledge that you are bound by the Conditions for Sale listed below and on the website www.astaguru.com");
        tv_gen_a.setText("Making a Winning Bid results in an enforceable contract of sale.");
                tv_gen_b.setText("AstaGuru is authorized by the seller to display at AstaGuru's discretion images and description of all lots in the catalogue and on the website.");
                tv_gen_c.setText("AstaGuru can grant record and reject any bids and/or proxy bids.");
                tv_gen_d.setText("Bidding access shall be given on AstaGuru's discretion. AstaGuru may ask for a deposit on lots prior to giving bidding access.");
                tv_gen_e.setText("AstaGuru may review bid histories of specific lots periodically to preserve the efficacy of the auction process.");
                tv_gen_f.setText("AstaGuru has the right to withdraw a Property before, during or after the bidding, if it has reason to believe that the authenticity of the Property or accuracy of description is in doubt.");
                tv_gen_g.setText("All proprieties shall be sold only if the reserve price in met. Reserve price is on each Property is confidential and shall not be disclosed . AstaGuru shall raise all invoices including buyers premium and related taxes.");
                tv_gen_h.setText("The Buyers Premium shall be calculated at 15% of the hammer price, excluding related tax.");
                tv_gen_i.setText("All foreign currency exchange rates during the Auction are made on a constant of 1:62 (USD:INR). All invoicing details shall be provided by the buyer prior to the auction.");
                tv_gen_j.setText("All payments shall be made within 7 days from the date of the invoice.");
                tv_gen_k.setText("In case payment is not made within the stated time period, it shall be treated as a breach of contract and the Seller may authorise AstaGuru to take any steps (including the institution of legal proceedings).");
                tv_gen_l.setText("AstaGuru may charge a 2% late payment fine per month. If the buyer wishes to collect the property from AstaGuru, it must be collected within 30 Days from the date of the auction. The buyer shall be charged a 2% storage fee if the property is not collected.");
                tv_gen_m.setText("AstaGuru reserves the right not to award the Winning Bid to the Bidder with the highest Bid at Closing Date if it deems it necessary to do so. In an unlikely event of any technical failure and the website is inaccessible. The lot closing time shall be extended.");
                tv_gen_n.setText("Bids recorded prior to the technical problem shall stand valid according to the terms of sale.");






        lin_law = (LinearLayout)findViewById(R.id.lin_law);
        lin_intent = (LinearLayout)findViewById(R.id.lin_intent);
        lin_authenticate = (LinearLayout)findViewById(R.id.lin_authenticate);
        lin_general = (LinearLayout)findViewById(R.id.lin_general);

        iv_law = (ImageView) findViewById(R.id.iv_law);
        iv_extent = (ImageView) findViewById(R.id.iv_extent);
        iv_Authenticate = (ImageView) findViewById(R.id.iv_Authenticate);
        iv_general = (ImageView) findViewById(R.id.iv_general);


        iv_law.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_law.getVisibility() == View.VISIBLE) {
                    lin_law.setVisibility(View.GONE);
                    iv_law.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_law.setVisibility(View.VISIBLE);
                    lin_intent.setVisibility(View.GONE);
                    lin_authenticate.setVisibility(View.GONE);
                    lin_general.setVisibility(View.GONE);
                    iv_law.setBackgroundResource(R.drawable.minus);
                    iv_extent.setBackgroundResource(R.drawable.plus);
                    iv_Authenticate.setBackgroundResource(R.drawable.plus);
                    iv_general.setBackgroundResource(R.drawable.plus);
                }
            }
        });
        iv_extent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_intent.getVisibility() == View.VISIBLE) {
                    lin_intent.setVisibility(View.GONE);
                    iv_extent.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_law.setVisibility(View.GONE);
                    lin_intent.setVisibility(View.VISIBLE);
                    lin_authenticate.setVisibility(View.GONE);
                    lin_general.setVisibility(View.GONE);
                    iv_law.setBackgroundResource(R.drawable.plus);
                    iv_extent.setBackgroundResource(R.drawable.minus);
                    iv_Authenticate.setBackgroundResource(R.drawable.plus);
                    iv_general.setBackgroundResource(R.drawable.plus);
                }
            }
        });
        iv_Authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_authenticate.getVisibility() == View.VISIBLE) {
                    lin_authenticate.setVisibility(View.GONE);
                    iv_Authenticate.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_law.setVisibility(View.GONE);
                    lin_intent.setVisibility(View.GONE);
                    lin_authenticate.setVisibility(View.VISIBLE);
                    lin_general.setVisibility(View.GONE);
                    iv_law.setBackgroundResource(R.drawable.plus);
                    iv_extent.setBackgroundResource(R.drawable.plus);
                    iv_Authenticate.setBackgroundResource(R.drawable.minus);
                    iv_general.setBackgroundResource(R.drawable.plus);
                }
            }
        });
        iv_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lin_general.getVisibility() == View.VISIBLE) {
                    lin_general.setVisibility(View.GONE);
                    iv_general.setBackgroundResource(R.drawable.plus);
                } else {
                    lin_law.setVisibility(View.GONE);
                    lin_intent.setVisibility(View.GONE);
                    lin_authenticate.setVisibility(View.GONE);
                    lin_general.setVisibility(View.VISIBLE);
                    iv_law.setBackgroundResource(R.drawable.plus);
                    iv_extent.setBackgroundResource(R.drawable.plus);
                    iv_Authenticate.setBackgroundResource(R.drawable.plus);
                    iv_general.setBackgroundResource(R.drawable.minus);
                }
            }
        });



        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Terms and Conditions");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }






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

        tv_extent_one = (JustifiedTextView) findViewById(R.id.tv_extent_one);
                tv_extent_two = (JustifiedTextView) findViewById(R.id.tv_extent_two);
                tv_extent_three = (JustifiedTextView) findViewById(R.id.tv_extent_three);
                tv_extent_four = (JustifiedTextView) findViewById(R.id.tv_extent_four);
                tv_extent_five = (JustifiedTextView) findViewById(R.id.tv_extent_five);
                tv_extent_six = (JustifiedTextView) findViewById(R.id.tv_extent_six);


        tv_auth_one = (JustifiedTextView) findViewById(R.id.tv_auth_one);
        tv_auth_two = (JustifiedTextView) findViewById(R.id.tv_auth_two);
        tv_auth_three = (JustifiedTextView) findViewById(R.id.tv_auth_three);
        tv_auth_four = (JustifiedTextView) findViewById(R.id.tv_auth_four);
        tv_auth_five = (JustifiedTextView) findViewById(R.id.tv_auth_five);
        tv_auth_six = (JustifiedTextView) findViewById(R.id.tv_auth_six);



        tv_gen_a = (JustifiedTextView) findViewById(R.id.tv_gen_a);
        tv_gen_b = (JustifiedTextView) findViewById(R.id.tv_gen_b);
        tv_gen_c = (JustifiedTextView) findViewById(R.id.tv_gen_c);
        tv_gen_d = (JustifiedTextView) findViewById(R.id.tv_gen_d);
        tv_gen_e = (JustifiedTextView) findViewById(R.id.tv_gen_e);
        tv_gen_f = (JustifiedTextView) findViewById(R.id.tv_gen_f);
        tv_gen_g = (JustifiedTextView) findViewById(R.id.tv_gen_g);
        tv_gen_h = (JustifiedTextView) findViewById(R.id.tv_gen_h);
        tv_gen_i = (JustifiedTextView) findViewById(R.id.tv_gen_i);
        tv_gen_j = (JustifiedTextView) findViewById(R.id.tv_gen_j);
        tv_gen_k = (JustifiedTextView) findViewById(R.id.tv_gen_k);
        tv_gen_l = (JustifiedTextView) findViewById(R.id.tv_gen_l);
        tv_gen_m = (JustifiedTextView) findViewById(R.id.tv_gen_m);
        tv_gen_n = (JustifiedTextView) findViewById(R.id.tv_gen_n);

        tv_z= (JustifiedTextView) findViewById(R.id.tv_z);




    }


}


