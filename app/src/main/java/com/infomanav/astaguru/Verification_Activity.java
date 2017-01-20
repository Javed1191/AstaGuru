package com.infomanav.astaguru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import adapter.AuctionGallaryAdpter;
import butterknife.ButterKnife;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 01-10-2016.
 */

public class Verification_Activity extends AppCompatActivity {


    SessionData data;
    JSONObject json;

    private TextView tv_email_verify, tv_mobile_verify;
    private LinearLayout lin_otm_mobile, lin_otm_email;
    private Button btn_proceed,btn_email_verify,btn_mobile_verify;
    EditText edt_mobile,edt_emailid, edt_one, edt_two, edt_three, edt_four;

    EditText edt_email_one, edt_email_two, edt_email_three, edt_email_four;
TextView tv_mob_not_verify,tv_email_not_verify;
    String str_string,str_mobile, str_username, str_pin, msg,str_email;
    ProgressDialog pDialog;
    Context context;
    Utility utility;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_verification);

        context = Verification_Activity.this;

        data = new SessionData(context);

        utility = new Utility(context);


//




        init();

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Verification");

        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_emailid= (EditText) findViewById(R.id.edt_emailid);

        edt_one = (EditText) findViewById(R.id.edt_one);
        edt_two = (EditText) findViewById(R.id.edt_two);
        edt_three = (EditText) findViewById(R.id.edt_three);
        edt_four = (EditText) findViewById(R.id.edt_four);


        edt_email_one = (EditText) findViewById(R.id.edt_email_one);
        edt_email_two = (EditText) findViewById(R.id.edt_email_two);
        edt_email_three = (EditText) findViewById(R.id.edt_email_three);
        edt_email_four = (EditText) findViewById(R.id.edt_email_four);

                str_email =  data.getObjectAsString("email");
        str_mobile=  data.getObjectAsString("mobile");

        edt_mobile.setText(str_mobile);
        edt_emailid.setText(str_email);


        str_mobile = edt_mobile.getText().toString();

        str_username = data.getObjectAsString("name");


        edt_one.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub


                if(edt_one.getText().toString().length()==1)
                {

                    edt_two.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
//
//                if(sb.length()==1)
//                {
//
//                    sb.deleteCharAt(0);
//
//                }

            }

            public void afterTextChanged(Editable s) {
//                if(sb.length()==0)
//                {
//
//                    edtPasscode1.requestFocus();
//                }

            }
        });


        edt_two.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub


                if(edt_two.getText().toString().length()==1)
                {

                    edt_three.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
//
//                if(sb.length()==1)
//                {
//
//                    sb.deleteCharAt(0);
//
//                }

            }

            public void afterTextChanged(Editable s) {
//                if(sb.length()==0)
//                {
//
//                    edtPasscode1.requestFocus();
//                }

            }
        });

        edt_three.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub


                if(edt_three.getText().toString().length()==1)
                {

                    edt_four.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
//
//                if(sb.length()==1)
//                {
//
//                    sb.deleteCharAt(0);
//
//                }

            }

            public void afterTextChanged(Editable s) {
//                if(sb.length()==0)
//                {
//
//                    edtPasscode1.requestFocus();
//                }

            }
        });


        edt_email_one.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub


                if(edt_email_one.getText().toString().length()==1)
                {

                    edt_email_two.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
//
//                if(sb.length()==1)
//                {
//
//                    sb.deleteCharAt(0);
//
//                }

            }

            public void afterTextChanged(Editable s) {
//                if(sb.length()==0)
//                {
//
//                    edtPasscode1.requestFocus();
//                }

            }
        });


        edt_email_two.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub


                if(edt_email_two.getText().toString().length()==1)
                {

                    edt_email_three.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
//
//                if(sb.length()==1)
//                {
//
//                    sb.deleteCharAt(0);
//
//                }

            }

            public void afterTextChanged(Editable s) {
//                if(sb.length()==0)
//                {
//
//                    edtPasscode1.requestFocus();
//                }

            }
        });

        edt_email_three.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub


                if(edt_email_three.getText().toString().length()==1)
                {

                    edt_email_four.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
//
//                if(sb.length()==1)
//                {
//
//                    sb.deleteCharAt(0);
//
//                }

            }

            public void afterTextChanged(Editable s) {
//                if(sb.length()==0)
//                {
//
//                    edtPasscode1.requestFocus();
//                }

            }
        });


        btn_mobile_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_enter_pinemial = edt_one.getText().toString() + edt_two.getText().toString() + edt_three.getText().toString() + edt_four.getText().toString();
                System.out.println("str_enter_pin" + str_enter_pinemial);
                System.out.println("str_enter_pin" + str_pin);
                System.out.println("str_enter_pin" + str_enter_pinemial);


                if (str_pin.equalsIgnoreCase(str_enter_pinemial)) {
                    tv_mobile_verify.setText("verified");
                    Toast.makeText(context, "Mobile Number Verified", Toast.LENGTH_SHORT).show();
                    tv_mob_not_verify.setVisibility(View.INVISIBLE);
//                    tv_mobile_verify.setVisibility(View.GONE);
                } else {
                    tv_mob_not_verify.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Pls Enter Valid Code", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_email_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_enter_pin = edt_email_one.getText().toString() + edt_email_two.getText().toString() + edt_email_three.getText().toString() + edt_email_four.getText().toString();
                System.out.println("str_enter_pin" + str_pin);
                System.out.println("str_enter_pin" + str_enter_pin);
                if (str_pin.equalsIgnoreCase(str_enter_pin))
                {
                    tv_email_verify.setText("verified");
                    Toast.makeText(context, "Email Id Verified", Toast.LENGTH_SHORT).show();
                    tv_email_not_verify.setVisibility(View.INVISIBLE);
//                    lin_otm_email.setVisibility(View.GONE);
//
//                    tv_email_verify.setVisibility(View.GONE);
                }
                else
                {

                    tv_email_not_verify.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Pls Enter Valid Code", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Typeface type = Typeface.createFromAsset(getAssets(), "WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ButterKnife.bind(this);


        tv_mobile_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (edt_mobile.getText().toString().length() == 10) {
//                    if (lin_otm_mobile.getVisibility() == View.VISIBLE) {
//                        lin_otm_mobile.setVisibility(View.GONE);
//                    } else {
                        lin_otm_mobile.setVisibility(View.VISIBLE);
                        SendOtp();

                        tv_mobile_verify.setText("Resend Otp");

                    }
//                } else {
//                    Toast.makeText(context, "Pls Enter Mobile Number", Toast.LENGTH_SHORT).show();
//                }
//            }
        });
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_mobile_verify.getText().toString().equalsIgnoreCase("verified"))
                {
                    data.setObjectAsString("login","true");
                    Intent intent = new Intent(Verification_Activity.this, Complete_SignUp_Activity.class);
                    startActivity(intent);
                }
                else if(tv_email_verify.getText().toString().equalsIgnoreCase("verified"))
                {
                    data.setObjectAsString("login","true");
                    Intent intent = new Intent(Verification_Activity.this, Complete_SignUp_Activity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(context, "Pls Complete Verification", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_email_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                lin_otm_email.setVisibility(View.VISIBLE);
                str_email = edt_emailid.getText().toString();

                if (utility.checkEmail(str_email))
                {


                          lin_otm_email.setVisibility(View.VISIBLE);

                          try {
                              Send_Email();
                              tv_email_verify.setText("Resend Otp");



                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                }
                else
                {
                    Toast.makeText(context, "Pls Enter Valid Email", Toast.LENGTH_SHORT).show();

                }

            }
        });




    }



    private void SendOtp(){

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);

        hud.show();


        int randomPIN = (int) (Math.random() * 9000) + 1000;

        str_pin = String.valueOf(randomPIN);


        str_mobile = edt_mobile.getText().toString();
        System.out.println("str_mobile" + str_mobile);

        msg = "Dear " +str_username+" , One Time Password for your Mobile Verification is "+ str_pin+". Regards, Team Astaguru.";

        System.out.println("msg" + msg);
        System.out.println("str_pin" + str_pin);

        String URL_url = "http://gateway.netspaceindia.com/api/sendhttp.php?";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("mob_number " + response);

                        hud.dismiss();
                        Toast.makeText(Verification_Activity.this,"Otp Succesfully Send On Mobile ",Toast.LENGTH_LONG).show();
//                        Toast.makeText(Verification_Activity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(Verification_Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("authkey", "131841Aotn6vhT583570b5");
                params.put("mobiles",str_mobile);
                params.put("message",msg);
                params.put("sender","AstGru");
                params.put("route","4");
                params.put("country","91");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Verification_Activity.this);
        requestQueue.add(stringRequest);


    }
    private void Send_Email() throws JSONException
    {

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);

        hud.show();

        int randomPIN = (int) (Math.random() * 9000) + 1000;

        str_pin = String.valueOf(randomPIN);

        try
        {


            str_email = edt_emailid.getText().toString();

       /* String strmail = ""\""+"+str_email+"+""\"";
        String strdddmail = +str_email+;*/

        msg = "Dear"+str_username+" \n One Time Password for your Mobile Verification is"+ str_pin+". Regards\n Team Astaguru.";
//            msg = "roshan";
            String URL_url = "http://52.66.4.131/api/v2/awsses/?api_key=6dee0a21388b49db917cd64559c32bcbde93460f391a594ab7cc6666824d5c26";

            final JSONArray array = new JSONArray();
            final HashMap<String, String> toArray = new HashMap<String, String>();
            toArray.put("name", "Roshan");
            toArray.put("email", str_email);

            JSONObject jb = new JSONObject();
            jb.put("name","Roshan");
            jb.put("email",str_email);

            array.put(jb);

            Map<String, String> params = new HashMap();

            params.put("template","newsletter");
//            params.put("to",array.toString());
//            params.put("to",array.toString().replace("\"",""));
            params.put("subject","Astaguru Email Validation OTP");
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
                    Toast.makeText(Verification_Activity.this,"Otp Succesfully Send On EmailID ",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    System.out.println("error" + error);

//                    Toast.makeText(Verification_Activity.this,"Error",Toast.LENGTH_LONG).show();
                }
            });

            Volley.newRequestQueue(this).add(jsonRequest);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




    }




    public interface VolleyCallback
    {
        void onSuccess(String result);
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
        tv_mobile_verify = (TextView) findViewById(R.id.tv_mobile_verify);
        tv_email_verify = (TextView) findViewById(R.id.tv_email_verify);

        tv_mob_not_verify = (TextView) findViewById(R.id.tv_mob_not_verify);
        tv_email_not_verify= (TextView) findViewById(R.id.tv_email_not_verify);
        lin_otm_mobile = (LinearLayout) findViewById(R.id.lin_otm_mobile);

        lin_otm_email = (LinearLayout) findViewById(R.id.lin_otm_email);

        btn_proceed = (Button) findViewById(R.id.btn_proceed);
        btn_mobile_verify = (Button) findViewById(R.id.btn_mobile_verify);
        btn_email_verify = (Button) findViewById(R.id.btn_email_verify);

    }


}


