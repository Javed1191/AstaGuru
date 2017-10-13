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

import com.android.volley.DefaultRetryPolicy;
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
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 01-10-2016.
 */

public class Verification_Activity extends AppCompatActivity {


    SessionData data;
    JSONObject json;

    private TextView tv_email_verify, tv_mobile_verify,tv_mob_green;
    private LinearLayout lin_otm_mobile, lin_otm_email;
    private Button btn_back,btn_proceed,btn_email_verify,btn_mobile_verify;
    EditText edt_mobile,edt_emailid, edt_one, edt_two, edt_three, edt_four;

    EditText edt_email_one, edt_email_two, edt_email_three, edt_email_four;
TextView tv_textemail,tv_mob_not_verify,tv_email_not_verify;
    String str_string,str_mobile, str_username, str_pin="", msg,str_email;
    ProgressDialog pDialog;
    Context context;
    Utility utility;
    private KProgressHUD hud;
    String activity,MobileVerified_value,EmailVerified_value,username,password,email,telephone,country,state,zip,city,fax,
            address,lastname,firstname;
    String strFirstName,strLastName,str_contry,strCity,strState,strZip,strMobile,strTelephone,strFax,strEmail,strUserName,
            strPassword,strConfirmPassword,str_address,MobileVerified,EmailVerified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_verification);

        context = Verification_Activity.this;
        data = new SessionData(context);
        utility = new Utility(context);
        init();

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


        //tv_textemail.setText("We have sent verification code to"+str_email+". Please check your inbox and put the 4 digit verification code here");
        tv_textemail.setText("We have sent verification code to "+str_email+".  please check your inbox and put 4 digit verification code here");



        str_mobile = edt_mobile.getText().toString();
        str_username = data.getObjectAsString("name");

        firstname = data.getObjectAsString("firstname");
        lastname = data.getObjectAsString("lastname");
        address = data.getObjectAsString("address");
        city = data.getObjectAsString("city");
        zip = data.getObjectAsString("zip");
        state = data.getObjectAsString("state");
        country = data.getObjectAsString("country");
        telephone = data.getObjectAsString("telephone");
        email = data.getObjectAsString("email");
        username = data.getObjectAsString("name");
        password = data.getObjectAsString("password");
        fax = data.getObjectAsString("fax");




        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {

            activity  = intent.getStringExtra("Activity");

        }


        if (activity.equals("Registration"))
        {
//            strFirstName = intent.getStringExtra("strFirstName");
//            strLastName = intent.getStringExtra("strLastName");
//            str_contry = intent.getStringExtra("str_address");
//            strCity = intent.getStringExtra("strCity");
//            strState = intent.getStringExtra("strState");
//            strZip = intent.getStringExtra("strZip");
//            strMobile = intent.getStringExtra("strMobile");
//            strTelephone = intent.getStringExtra("strTelephone");
//            strFax = intent.getStringExtra("strFax");
//            strEmail = intent.getStringExtra("strEmail");
//            strUserName = intent.getStringExtra("strUserName");
//            strPassword = intent.getStringExtra("strPassword");
//            str_address = intent.getStringExtra("str_contry");
//            str_contry = intent.getStringExtra("str_address");
        }
        else if (activity.equals("Login"))
        {
            MobileVerified_value = data.getObjectAsString("MobileVerified");
            EmailVerified_value  =  data.getObjectAsString("EmailVerified");


            if(MobileVerified_value.equals("true"))
            {
                lin_otm_mobile.setVisibility(View.GONE);
                tv_mobile_verify.setText("Verified");
                tv_mobile_verify.setTextColor(getResources().getColor(R.color.CLR_GREEN));
                btn_mobile_verify.setEnabled(false);
            }
            else
            {
                lin_otm_mobile.setVisibility(View.VISIBLE);

            }

            if(EmailVerified_value.equals("true"))
            {
                lin_otm_email.setVisibility(View.GONE);

                btn_email_verify.setEnabled(false);
                tv_email_verify.setText("Verified");
                tv_email_verify.setTextColor(getResources().getColor(R.color.CLR_GREEN));
            }
            else
            {

                lin_otm_email.setVisibility(View.VISIBLE);
            }
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Verification_Activity.this,MainActivity.class);
                intent.putExtra("fragment","home");
                startActivity(intent);
                finish();

            }
        });

        edt_one.addTextChangedListener(new TextWatcher()
        {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if(edt_one.getText().toString().length()==1)
                {
                    edt_two.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,int after)
            {

            }

            public void afterTextChanged(Editable s)
            {

            }
        });
        edt_two.addTextChangedListener(new TextWatcher()
        {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(edt_two.getText().toString().length()==1)
                {
                    edt_three.requestFocus();
                }
                if(edt_two.getText().toString().length()==0)
                {
                    edt_one.requestFocus();
//                    edt_one.setText("");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {


            }

            public void afterTextChanged(Editable s)
            {

            }
        });

        edt_three.addTextChangedListener(new TextWatcher()
        {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(edt_three.getText().toString().length()==1)
                {
                    edt_four.requestFocus();
                }

                if(edt_three.getText().toString().length()==0)
                {
                    edt_two.requestFocus();
//                    edt_two.setText("");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,int after)
            {

            }

            public void afterTextChanged(Editable s)
            {

            }
        });


        edt_four.addTextChangedListener(new TextWatcher()
        {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if(edt_four.getText().toString().length()==0)
                {
                    edt_three.requestFocus();
//                    edt_three.setText("");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,int after)
            {

            }

            public void afterTextChanged(Editable s)
            {

            }
        });

        edt_email_one.addTextChangedListener(new TextWatcher()
        {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(edt_email_one.getText().toString().length()==1)
                {
                    edt_email_two.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,int after)
            {

            }

            public void afterTextChanged(Editable s)
            {

            }
        });


        edt_email_two.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(edt_email_two.getText().toString().length()==1)
                {

                    edt_email_three.requestFocus();

                }
                if(edt_email_two.getText().toString().length()==0)
                {
                    edt_email_one.requestFocus();
//                    edt_one.setText("");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            public void afterTextChanged(Editable s)
            {

            }
        });

        edt_email_three.addTextChangedListener(new TextWatcher()
        {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if(edt_email_three.getText().toString().length()==1)
                {

                    edt_email_four.requestFocus();

                }
                if(edt_email_three.getText().toString().length()==0)
                {
                    edt_email_two.requestFocus();
//                    edt_one.setText("");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,int after)
            {


            }

            public void afterTextChanged(Editable s)
            {

            }
        });
        edt_email_four.addTextChangedListener(new TextWatcher()
        {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {


                if(edt_email_four.getText().toString().length()==0)
                {
                    edt_email_three.requestFocus();
//                    edt_one.setText("");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,int after)
            {


            }

            public void afterTextChanged(Editable s)
            {

            }
        });


        btn_mobile_verify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String str_enter_pinemial = edt_one.getText().toString() + edt_two.getText().toString() + edt_three.getText().toString() + edt_four.getText().toString();
                System.out.println("str_enter_pin" + str_enter_pinemial);
                System.out.println("str_enter_pin" + str_pin);
                System.out.println("str_enter_pin" + str_enter_pinemial);

                if (str_enter_pinemial.isEmpty())
                {
                    Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (str_pin.equalsIgnoreCase(str_enter_pinemial))
                    {
                        tv_mobile_verify.setText("Verified");
                        tv_mobile_verify.setTextColor(getResources().getColor(R.color.CLR_GREEN));
                        Toast.makeText(context, "Mobile Number Verified", Toast.LENGTH_SHORT).show();
                        tv_mob_not_verify.setVisibility(View.INVISIBLE);
                        tv_mobile_verify.setClickable(false);
                        tv_mobile_verify.setEnabled(false);
                        tv_mobile_verify.setLinksClickable(false);
                        lin_otm_mobile.setVisibility(View.GONE);
//                    tv_mobile_verify.setVisibility(View.GONE);
                    }
                    else {

                        tv_mob_not_verify.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Please Enter Valid Code", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        btn_email_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_enter_pin = edt_email_one.getText().toString() + edt_email_two.getText().toString() + edt_email_three.getText().toString() + edt_email_four.getText().toString();
                System.out.println("str_enter_pin" + str_pin);
                System.out.println("str_enter_pin" + str_enter_pin);


                if(str_enter_pin.isEmpty())
                {
                    Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (str_pin.equalsIgnoreCase(str_enter_pin))
                    {
                        tv_email_verify.setText("Verified");
                        lin_otm_email.setVisibility(View.GONE);
                        tv_email_verify.setTextColor(getResources().getColor(R.color.CLR_GREEN));
                        Toast.makeText(context, "Email Id Verified", Toast.LENGTH_SHORT).show();
                        tv_email_not_verify.setVisibility(View.INVISIBLE);
                        tv_email_verify.setClickable(false);
                        tv_email_verify.setEnabled(false);
                        tv_email_verify.setLinksClickable(false);
//                    lin_otm_email.setVisibility(View.GONE);
//
//                    tv_email_verify.setVisibility(View.GONE);
                    }
                    else
                    {

                        tv_email_not_verify.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Please Enter Valid Code", Toast.LENGTH_SHORT).show();
                    }
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

                        tv_mobile_verify.setText("Resend OTP");

                    }
//                } else {
//                    Toast.makeText(context, "Pls Enter Mobile Number", Toast.LENGTH_SHORT).show();
//                }
//            }
        });
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_mobile_verify.getText().toString().equalsIgnoreCase("Verified") &&
                        tv_email_verify.getText().toString().equalsIgnoreCase("Verified"))
                {
                    data.setObjectAsString("login","true");

                    MobileVerified="1";
                    EmailVerified="1";


                        UpdateUser_Table();


                }
//                else if(tv_email_verify.getText().toString().equalsIgnoreCase("verified"))
//                {
//                    data.setObjectAsString("login","true");
//
//
//
//                                       RegisterUser(strFirstName,strLastName,strCity,strState,strZip,strMobile,strTelephone,strFax,strEmail,strUserName,
//                           strPassword,str_contry,str_address);
//
//                }
                else
                {
                    Toast.makeText(context, "Please Complete Verification", Toast.LENGTH_SHORT).show();
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
                              tv_email_verify.setText("Resend OTP");



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
                        Toast.makeText(Verification_Activity.this,"OTP sent to your number "+str_mobile,Toast.LENGTH_LONG).show();
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

            msg = "Dear "+str_username+", \n\nThank you for choosing AstaGuru Online Auction House. We are glad that you have given us this opportunity to cater to your Indian Art related requirements. Looking forward to building a longstanding relationship with you.\n\n" +
                    "Please enter the OTP "+ str_pin+" on the App to complete email verification process.\n\n" +
                    "In case you are unable to open the link, please write to us at, contact@astaguru.com or call us on 91-22 2204 8138/39. We will be glad to assist you further.\n\nThanking You,\n\n Warm Regards, \n\n Team of AstaGuru";
           // String URL_url = "http://52.66.4.131/api/v2/awsses/?api_key=6dee0a21388b49db917cd64559c32bcbde93460f391a594ab7cc6666824d5c26";

            String  URL_url = "http://restapi.infomanav.com/api/v2/awsses/?api_key=6dee0a21388b49db917cd64559c32bcbde93460f391a594ab7cc6666824d5c26";

            final JSONArray array = new JSONArray();

            JSONObject jb = new JSONObject();
            jb.put("name",str_username);
            jb.put("email",str_email);

            array.put(jb);
            Map<String, String> params = new HashMap();
            params.put("template","newsletter");
            params.put("subject","Warm Greetings from AstaGuru Online Auction House");
            params.put("body_text",msg);
            params.put("from_name","AstaGuru");
            params.put("from_email","info@infomanav.com");
            params.put("reply_to_name","AstaGuru");
            params.put("reply_to_email","info@infomanav.com");

            JSONObject parameters = new JSONObject(params);
            parameters.put("to",array);

            System.out.println("parameters" + parameters);

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL_url, parameters, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("response" + response);
                    hud.dismiss();
                    Toast.makeText(Verification_Activity.this,"OTP sent to your email "+str_email ,Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    System.out.println("error" + error);

                }
            });

            Volley.newRequestQueue(this).add(jsonRequest);
            jsonRequest.setShouldCache(false);
            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
            Intent intent = new Intent(Verification_Activity.this,MainActivity.class);
            intent.putExtra("fragment","home");
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }


    public void init()
    {
        utility = new Utility(getApplicationContext());

        tv_textemail = (TextView) findViewById(R.id.tv_textemail);
        tv_mobile_verify = (TextView) findViewById(R.id.tv_mobile_verify);
        tv_email_verify = (TextView) findViewById(R.id.tv_email_verify);
        tv_mob_green = (TextView) findViewById(R.id.tv_mob_green);
        tv_mob_not_verify = (TextView) findViewById(R.id.tv_mob_not_verify);
        tv_email_not_verify= (TextView) findViewById(R.id.tv_email_not_verify);
        lin_otm_mobile = (LinearLayout) findViewById(R.id.lin_otm_mobile);

        lin_otm_email = (LinearLayout) findViewById(R.id.lin_otm_email);

        btn_proceed = (Button) findViewById(R.id.btn_proceed);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_mobile_verify = (Button) findViewById(R.id.btn_mobile_verify);
        btn_email_verify = (Button) findViewById(R.id.btn_email_verify);

    }

    public void UpdateUser_Table()
    {


        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);

        hud.show();



        String userid = data.getObjectAsString("userid");

       // final String username = strFirstName;
        String URL_url = Application_Constants.Main_URL+"users/?api_key="+ Application_Constants.API_KEY;
        Map<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("MobileVerified",MobileVerified);
        params.put("EmailVerified",EmailVerified);

        JSONArray resourcesArr = new JSONArray();
        resourcesArr.put(new JSONObject(params));

        Map<String, JSONArray> resourceParams = new HashMap<>();
        resourceParams.put("resource", resourcesArr);

        JSONObject requestParam = new JSONObject(resourceParams);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL_url, requestParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hud.dismiss();
                        try
                        {
                            System.out.print("Response"+ response.toString());

                            JSONObject userObject = null;

                            userObject = response.getJSONArray("resource").getJSONObject(0);
                            String datadata = userObject.getString("userid");

                            if (!(datadata==null))
                            {
                                data.setObjectAsString("userid",datadata);
                                data.setObjectAsString("MobileVerified","true");
                                data.setObjectAsString("EmailVerified","true");
                                Intent intent = new Intent(Verification_Activity.this, Complete_SignUp_Activity.class);
                                intent.putExtra("firstname",firstname);
                                intent.putExtra("lastname",lastname);
                                intent.putExtra("address",address);
                                intent.putExtra("city",city);
                                intent.putExtra("zip",zip);
                                intent.putExtra("state",state);
                                intent.putExtra("country",country);
                                intent.putExtra("telephone",telephone);
                                intent.putExtra("email",email);
                                intent.putExtra("username",username);
                                intent.putExtra("password",password);
                                intent.putExtra("fax",fax);
                                startActivity(intent);
                                finish();
                            }else
                            {
                                Toast.makeText(context,"User Not Update",Toast.LENGTH_SHORT);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hud.dismiss();
                        VolleyLog.e("Error: ", error.getMessage());

                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT);

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
}


