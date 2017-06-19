package com.infomanav.astaguru;

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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import services.Utility;

/**
 * Created by android-javed on 01-10-2016.
 */

public class How_To_Sell_Acitivity extends AppCompatActivity
{
   WebView wb_sell_comition;

ImageView iv_evalution,iv_deci_sell,iv_logistics,iv_sell_contract,iv_sell_commition,iv_payment,iv_reserve_price;
RelativeLayout rel_Evaluation,rel_Decision,rel_Logistics,rel_Reserve,rel_Seller_Contract,rel_Seller_Commission,rel_Payment;
    private Utility utility;
    private Button btn_reach_us,btn_sell;
    private LinearLayout lin_contact_us,lin_reach_us;
LinearLayout wb_sell_contract_main,wb_sell_evaluation,wb_sell_decition,wb_sell_logistics,wb_sell_price,wb_sell_payment;
    JustifiedTextView tv_evaluation_a,tv_sell_decition,tv_logistics_decition,tv_reserve_price,tv_sell_contract,tv_sell_payment;
JustifiedTextView tv_Reserveprice,tv_commission;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_sell);
        init();

        iv_evalution = (ImageView) findViewById(R.id.iv_evalution);
        iv_deci_sell= (ImageView) findViewById(R.id.iv_deci_sell);
        iv_logistics= (ImageView) findViewById(R.id.iv_logistics);
        iv_reserve_price= (ImageView) findViewById(R.id.iv_reserve_price);
        iv_sell_contract= (ImageView) findViewById(R.id.iv_sell_contract);
        iv_payment= (ImageView) findViewById(R.id.iv_payment);

        wb_sell_evaluation = (LinearLayout)findViewById(R.id.wb_sell_evaluation) ;
        wb_sell_decition = (LinearLayout)findViewById(R.id.wb_sell_decition) ;
        wb_sell_logistics = (LinearLayout) findViewById(R.id.wb_sell_logistics) ;
        wb_sell_price = (LinearLayout) findViewById(R.id.wb_sell_price) ;
        wb_sell_contract_main = (LinearLayout)findViewById(R.id.wb_sell_contract_main) ;
        wb_sell_payment = (LinearLayout)findViewById(R.id.wb_sell_payment) ;

        tv_evaluation_a.setText("The first step in the process is to arrange a consultation with one of our representatives. You can contact us or email us with details of your property. We shall then study the property and give you a valuation on the same. We will respond to your auction estimate request within 3 working days. It is very important to AstaGuru to provide the highest level of service; accordingly, we cannot rush valuations.");
        tv_sell_decition.setText("Based on the valuation result, you and AstaGuru' s experts will decide whether your property is appropriate for sale at auction. We will also recommend an appropriate venue and possible sale timing. If you decide to proceed, you will sign a contract, and AstaGuru will take the property in for cataloguing and photography.Please note that, restoration or another action required such as reframing, stretching and so on in order to enhance the artwork's final presentation will be invoiced to the consignor, irrespective of the final auction outcome.");
        tv_logistics_decition.setText("Our Art Transport or Shipping Department can help you arrange to have your property delivered to our offices, if necessary. As the consignor, you are responsible for packing, shipping and insurance charges.");

        tv_reserve_price.setText("The reserve is the confidential minimum selling price to which a consignor (you) and AstaGuru agree before the sale - your property's \"floor\" price, below which no bid will be accepted. If bidding on your item fails to reach the reserve, we will not sell the piece and will advise you of your options. It is important to consider the reserve price in light of the fact that AstaGuru will assess fees and handling costs for unsold lots.");

        tv_sell_contract.setText("The seller contract covers two important issues that will affect your bottom line: the reserve price and AstaGuru's commissions.");

        tv_sell_payment.setText("Shortly after the sale, you will receive a listing of the final hammer price for each item you consigned. We will issue the payment within 40 days from the day of the sale provided we are in receipt of the buyer's payment.");

        tv_Reserveprice.setText("The reserve is the confidential minimum selling price to which a consignor (you) and AstaGuru agree before the sale - your property's \"floor\" price, below which no bid will be accepted. If bidding on your item fails to reach the reserve, we will not sell the piece and will advise you of your options. It is important to consider the reserve price in light of the fact that AstaGuru will assess fees and handling costs for unsold lots.");
        tv_commission.setText("Sellers pay a commission that is deducted, along with any agreed-upon expenses, from the hammer price. Should you have any specific questions regarding the selling commission, please call the appropriate representative for more information.");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("How To Sell");




        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);
        btn_sell.setText("Submit Details");
        btn_sell.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ButterKnife.bind(this);


        iv_evalution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_evaluation.getVisibility() == View.VISIBLE)
                {
                    wb_sell_evaluation.setVisibility(View.GONE);
                    iv_evalution.setBackgroundResource(R.drawable.plus);
                }
                else
                {
                    wb_sell_evaluation.setVisibility(View.VISIBLE);
                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract_main.setVisibility(View.GONE);
                   
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.minus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
                
                
            }
        });


        iv_deci_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_decition.getVisibility() == View.VISIBLE)
                {
                    wb_sell_decition.setVisibility(View.GONE);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                }
                else
                {
                    wb_sell_evaluation.setVisibility(View.GONE);
                    wb_sell_decition.setVisibility(View.VISIBLE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract_main.setVisibility(View.GONE);
                   
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.minus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });

        iv_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_logistics.getVisibility() == View.VISIBLE)
                {
                    wb_sell_logistics.setVisibility(View.GONE);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                }
                else
                {
                    wb_sell_evaluation.setVisibility(View.GONE);
                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.VISIBLE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract_main.setVisibility(View.GONE);
                   
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.minus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });
        iv_reserve_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_price.getVisibility() == View.VISIBLE)
                {
                    wb_sell_price.setVisibility(View.GONE);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                }
                else
                {
                    wb_sell_evaluation.setVisibility(View.GONE);
                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.VISIBLE);
                    wb_sell_contract_main.setVisibility(View.GONE);
                   
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.minus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });

        iv_sell_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_contract_main.getVisibility() == View.VISIBLE)
                {
                    wb_sell_contract_main.setVisibility(View.GONE);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                }
                else
                {
                    wb_sell_evaluation.setVisibility(View.GONE);
                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract_main.setVisibility(View.VISIBLE);
                   
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.minus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });


        iv_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_payment.getVisibility() == View.VISIBLE)
                {
                    wb_sell_payment.setVisibility(View.GONE);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
                else
                {
                    wb_sell_evaluation.setVisibility(View.GONE);
                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract_main.setVisibility(View.GONE);
                   
                    wb_sell_payment.setVisibility(View.VISIBLE);

                    iv_evalution.setBackgroundResource(R.drawable.plus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.minus);
                }
            }
        });

        rel_Evaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wb_sell_evaluation.getVisibility() == View.VISIBLE)
                {
                    wb_sell_evaluation.setVisibility(View.GONE);
                    iv_evalution.setBackgroundResource(R.drawable.plus);
                }
                else
                {
                    wb_sell_evaluation.setVisibility(View.VISIBLE);
                    wb_sell_decition.setVisibility(View.GONE);
                    wb_sell_logistics.setVisibility(View.GONE);
                    wb_sell_price.setVisibility(View.GONE);
                    wb_sell_contract_main.setVisibility(View.GONE);
                    wb_sell_payment.setVisibility(View.GONE);

                    iv_evalution.setBackgroundResource(R.drawable.minus);
                    iv_deci_sell.setBackgroundResource(R.drawable.plus);
                    iv_logistics.setBackgroundResource(R.drawable.plus);
                    iv_reserve_price.setBackgroundResource(R.drawable.plus);
                    iv_sell_contract.setBackgroundResource(R.drawable.plus);
                    iv_payment.setBackgroundResource(R.drawable.plus);
                }
            }
        });

        rel_Decision.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (wb_sell_decition.getVisibility() == View.VISIBLE)
                        {
                            wb_sell_decition.setVisibility(View.GONE);
                            iv_deci_sell.setBackgroundResource(R.drawable.plus);
                        }
                        else
                        {
                            wb_sell_evaluation.setVisibility(View.GONE);
                            wb_sell_decition.setVisibility(View.VISIBLE);
                            wb_sell_logistics.setVisibility(View.GONE);
                            wb_sell_price.setVisibility(View.GONE);
                            wb_sell_contract_main.setVisibility(View.GONE);
                            wb_sell_payment.setVisibility(View.GONE);

                            iv_evalution.setBackgroundResource(R.drawable.plus);
                            iv_deci_sell.setBackgroundResource(R.drawable.minus);
                            iv_logistics.setBackgroundResource(R.drawable.plus);
                            iv_reserve_price.setBackgroundResource(R.drawable.plus);
                            iv_sell_contract.setBackgroundResource(R.drawable.plus);
                            iv_payment.setBackgroundResource(R.drawable.plus);
                        }
                    }
                });

        rel_Logistics.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (wb_sell_logistics.getVisibility() == View.VISIBLE)
                        {
                            wb_sell_logistics.setVisibility(View.GONE);
                            iv_logistics.setBackgroundResource(R.drawable.plus);
                        }
                        else
                        {
                            wb_sell_evaluation.setVisibility(View.GONE);

                            wb_sell_decition.setVisibility(View.GONE);
                            wb_sell_logistics.setVisibility(View.VISIBLE);
                            wb_sell_price.setVisibility(View.GONE);
                            wb_sell_contract_main.setVisibility(View.GONE);
                            wb_sell_payment.setVisibility(View.GONE);

                            iv_evalution.setBackgroundResource(R.drawable.plus);
                            iv_deci_sell.setBackgroundResource(R.drawable.plus);
                            iv_logistics.setBackgroundResource(R.drawable.minus);
                            iv_reserve_price.setBackgroundResource(R.drawable.plus);
                            iv_sell_contract.setBackgroundResource(R.drawable.plus);
                            iv_payment.setBackgroundResource(R.drawable.plus);
                        }
                    }
                });
                rel_Reserve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (wb_sell_price.getVisibility() == View.VISIBLE)
                        {
                            wb_sell_price.setVisibility(View.GONE);
                            iv_reserve_price.setBackgroundResource(R.drawable.plus);
                        }
                        else
                        {
                            wb_sell_evaluation.setVisibility(View.GONE);
                            wb_sell_decition.setVisibility(View.GONE);
                            wb_sell_logistics.setVisibility(View.GONE);
                            wb_sell_price.setVisibility(View.VISIBLE);
                            wb_sell_contract_main.setVisibility(View.GONE);
                            wb_sell_payment.setVisibility(View.GONE);

                            iv_evalution.setBackgroundResource(R.drawable.plus);
                            iv_deci_sell.setBackgroundResource(R.drawable.plus);
                            iv_logistics.setBackgroundResource(R.drawable.plus);
                            iv_reserve_price.setBackgroundResource(R.drawable.minus);
                            iv_sell_contract.setBackgroundResource(R.drawable.plus);
                            iv_payment.setBackgroundResource(R.drawable.plus);
                        }
                    }
                });

        rel_Seller_Contract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (wb_sell_contract_main.getVisibility() == View.VISIBLE)
                        {
                            wb_sell_contract_main.setVisibility(View.GONE);
                            iv_sell_contract.setBackgroundResource(R.drawable.plus);
                        }
                        else
                        {
                            wb_sell_evaluation.setVisibility(View.GONE);

                            wb_sell_decition.setVisibility(View.GONE);
                            wb_sell_logistics.setVisibility(View.GONE);
                            wb_sell_price.setVisibility(View.GONE);
                            wb_sell_contract_main.setVisibility(View.VISIBLE);
                            wb_sell_payment.setVisibility(View.GONE);

                            iv_evalution.setBackgroundResource(R.drawable.plus);
                            iv_deci_sell.setBackgroundResource(R.drawable.plus);
                            iv_logistics.setBackgroundResource(R.drawable.plus);
                            iv_reserve_price.setBackgroundResource(R.drawable.plus);
                            iv_sell_contract.setBackgroundResource(R.drawable.minus);
                            iv_payment.setBackgroundResource(R.drawable.plus);
                        }
                    }
                });

//        rel_Seller_Commission.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if (wb_sell_comition.getVisibility() == View.VISIBLE) {
//                           
//                            iv_sell_commition.setBackgroundResource(R.drawable.plus);
//                        } else {
//                            wb_sell_evaluation.setVisibility(View.GONE);
//
//                            wb_sell_decition.setVisibility(View.GONE);
//                            wb_sell_logistics.setVisibility(View.GONE);
//                            wb_sell_price.setVisibility(View.GONE);
//                            wb_sell_contract_main.setVisibility(View.GONE);
//                            wb_sell_comition.setVisibility(View.VISIBLE);
//                            wb_sell_payment.setVisibility(View.GONE);
//
//                            iv_evalution.setBackgroundResource(R.drawable.plus);
//                            iv_deci_sell.setBackgroundResource(R.drawable.plus);
//                            iv_logistics.setBackgroundResource(R.drawable.plus);
//                            iv_reserve_price.setBackgroundResource(R.drawable.plus);
//                            iv_sell_contract.setBackgroundResource(R.drawable.plus);
//                            iv_sell_commition.setBackgroundResource(R.drawable.minus);
//                            iv_payment.setBackgroundResource(R.drawable.plus);
//                        }
//                    }
//                });
                rel_Payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (wb_sell_payment.getVisibility() == View.VISIBLE)
                        {
                            wb_sell_payment.setVisibility(View.GONE);
                            iv_payment.setBackgroundResource(R.drawable.plus);
                        }
                        else
                        {
                            wb_sell_evaluation.setVisibility(View.GONE);

                            wb_sell_decition.setVisibility(View.GONE);
                            wb_sell_logistics.setVisibility(View.GONE);
                            wb_sell_price.setVisibility(View.GONE);
                            wb_sell_contract_main.setVisibility(View.GONE);
                            wb_sell_payment.setVisibility(View.VISIBLE);

                            iv_evalution.setBackgroundResource(R.drawable.plus);
                            iv_deci_sell.setBackgroundResource(R.drawable.plus);
                            iv_logistics.setBackgroundResource(R.drawable.plus);
                            iv_reserve_price.setBackgroundResource(R.drawable.plus);
                            iv_sell_contract.setBackgroundResource(R.drawable.plus);
                            iv_payment.setBackgroundResource(R.drawable.minus);
                        }
                    }
                });

        btn_sell.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(How_To_Sell_Acitivity.this,Sellers_DetailsActivity.class);
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
        btn_sell = (Button) findViewById(R.id.btn_sell);

        rel_Evaluation = (RelativeLayout) findViewById(R.id.rel_Evaluation);
        rel_Decision= (RelativeLayout) findViewById(R.id.rel_Decision);
        rel_Logistics= (RelativeLayout) findViewById(R.id.rel_Logistics);
        rel_Reserve= (RelativeLayout) findViewById(R.id.rel_Reserve);
        rel_Seller_Contract= (RelativeLayout) findViewById(R.id.rel_Seller_Contract);
        rel_Payment= (RelativeLayout) findViewById(R.id.rel_Payment);

        tv_evaluation_a = (JustifiedTextView)findViewById(R.id.tv_evaluation_a);
        tv_sell_decition = (JustifiedTextView)findViewById(R.id.tv_sell_decition);
        tv_logistics_decition= (JustifiedTextView)findViewById(R.id.tv_logistics_decition);
        tv_reserve_price= (JustifiedTextView)findViewById(R.id.tv_reserve_price);
        tv_sell_contract= (JustifiedTextView)findViewById(R.id.tv_sell_contract);
        tv_sell_payment= (JustifiedTextView)findViewById(R.id.tv_sell_payment);

        tv_Reserveprice= (JustifiedTextView)findViewById(R.id.tv_Reserveprice);
        tv_commission= (JustifiedTextView)findViewById(R.id.tv_commission);
    }


}

