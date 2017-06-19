package com.infomanav.astaguru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import adapter.MyPurchasesAdpter;
import butterknife.BindView;
import butterknife.ButterKnife;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Shared_Preferences_Class;
import services.Utility;

public class LoginActivity extends AppCompatActivity
{


    private String strPastAuctionUrl = Application_Constants.Main_URL+"users?api_key="+ Application_Constants.API_KEY+"&filter=username%20=%20babuddinjson";


    private Utility utility;
    String strEmail,strPassword;
    Context context;
    private KProgressHUD hud;
    Button btn_sign_in;
    EditText edt_email,edt_password;
            TextView tv_forgot_pass,tv_sign_up;
    SessionData sessionData;
    String str_type,userFcmId="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context =this;
        sessionData = new SessionData(context);
        str_type =getIntent().getStringExtra("str_from");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
        edt_email = (EditText) findViewById(R.id.edt_emaillogin);
        edt_password = (EditText) findViewById(R.id.edt_passwordlogin);
        tv_forgot_pass = (TextView) findViewById(R.id.tv_forgot_pass);
        tv_sign_up = (TextView) findViewById(R.id.tv_sign_up);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Sign In");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        userFcmId = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.FCM_ID,"");

        if(userFcmId.isEmpty())
        {
            // Resets Instance ID and revokes all tokens.
            try {
                // FirebaseInstanceId.getInstance().deleteInstanceId();
                userFcmId  = FirebaseInstanceId.getInstance().getToken();
                Shared_Preferences_Class.writeString(getApplicationContext(),Shared_Preferences_Class.FCM_ID,userFcmId);

                Log.i("FCMID",userFcmId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        init();


        btn_sign_in.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



                strEmail  = edt_email.getText().toString().trim();
                strPassword  = edt_password.getText().toString().trim();


                if(strEmail.isEmpty())
                {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Username");
                }


                else if(strPassword.isEmpty())
                {
                    edt_password.requestFocus();
                    edt_password.setError("Enter Password");
                }
                else
                {
                    //loginWithCredentials(strEmail,strPassword);
                    userLogin(strEmail,strPassword,userFcmId,"android");
                }



            }
        });

        tv_sign_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

        tv_forgot_pass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this,Forgot_Password.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.default_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        if (id == R.id.action_close)
        {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
           finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginWithCredentials (String strEmail, String strPassword) {

        if (utility.checkInternet()) {

            hud = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f);

            hud.show();

            String URL = Application_Constants.Main_URL+"users/?api_key="+ Application_Constants.API_KEY+"&filter=(username%20=%20" + strEmail + ")%20and%20(password%20=%20" + strPassword + ")";


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, URL, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("log", response.toString());
                            hud.dismiss();

                            System.out.println("response" + response.toString());
                            JSONObject userObject = null;
                            try {
                                if (response.getJSONArray("resource").length() == 0) {
                                    Toast.makeText(getApplicationContext(),
                                            "Invalid Username or Password ",
                                            Toast.LENGTH_LONG)
                                            .show();
                                } else {
                                    userObject = response.getJSONArray("resource").getJSONObject(0);

                                    String str_user_id = userObject.getString("userid");
                                    String str_email = userObject.getString("email");
                                    String str_name = userObject.getString("name");
                                    String str_mobile= userObject.getString("Mobile");
                                    String str_BillingName = userObject.getString("BillingName");
                                    String str_BillingAddress = userObject.getString("address1");
                                    String str_BillingCity = userObject.getString("city");
                                    String str_BillingState = userObject.getString("state");
                                    String str_BillingCountry = userObject.getString("country");
                                    String str_BillingZip = userObject.getString("zip");
                                    String str_BillingTelephone = userObject.getString("telephone");
                                    String str_BillingEmail = userObject.getString("email");
                                    String MobileVerified = userObject.getString("MobileVerified");
                                    String EmailVerified = userObject.getString("EmailVerified");
                                    String confirmbid= userObject.getString("confirmbid");
                                    if (MobileVerified.equals("1"))
                                    {
                                        sessionData.setObjectAsString("MobileVerified","true");
                                    }
                                    else
                                    {
                                        sessionData.setObjectAsString("MobileVerified","false");
                                    }

                                    if (EmailVerified.equals("1"))
                                    {
                                        sessionData.setObjectAsString("EmailVerified","true");
                                    }
                                    else
                                    {
                                        sessionData.setObjectAsString("EmailVerified","false");
                                    }

                                    sessionData.setString("BillingName",str_BillingName);
                                    sessionData.setString("BillingAddress",str_BillingAddress);
                                    sessionData.setString("BillingCity",str_BillingCity);
                                    sessionData.setString("BillingState",str_BillingState);
                                    sessionData.setString("BillingCountry",str_BillingCountry);
                                    sessionData.setString("BillingZip",str_BillingZip);
                                    sessionData.setString("BillingTelephone",str_BillingTelephone);
                                    sessionData.setString("BillingEmail",str_BillingEmail);


                                    System.out.println("sssss" + str_BillingName);
                                    System.out.println("sssss" + str_BillingAddress);
                                    System.out.println("sssss" + str_BillingCity);
                                    System.out.println("sssss" + str_BillingState);
                                    System.out.println("sssss" + str_BillingCountry);
                                    System.out.println("sssss" + str_BillingZip);
                                    System.out.println("sssss" + str_BillingTelephone);
                                    System.out.println("sssss" + str_BillingEmail);

                                    sessionData.setString("userid",str_user_id);
                                    sessionData.setString("email",str_email);
                                    sessionData.setString("mobile",str_mobile);
                                    sessionData.setString("name",str_name);
                                    sessionData.setString("confirmbid",confirmbid);
                                    sessionData.setObjectAsString("login","true");

                                    //launch home screen


                                    if (str_type.equals("adpter"))
                                    {
                                        finish();
                                    }
                                    else
                                    {
                                        if (MobileVerified.equals("true") && EmailVerified.equals("true"))
                                        {

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                        {

                                            Intent intent = new Intent(getApplicationContext(), Verification_Activity.class);
                                            intent.putExtra("Activity","Login");
//                                            intent.putExtra("MobileVerified",MobileVerified);
//                                            intent.putExtra("EmailVerified",EmailVerified);
                                            startActivity(intent);

                                        }

//                                        finish();
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("test", "Error: " + error.getMessage());
                    hud.dismiss();
                    VolleyLog.e("Error: ", error.getMessage());
                    Toast.makeText(getApplicationContext(),"Pls Enter Valid Username",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonObjReq);
        }

    }





    private void userLogin(String inUserName,String inUserPassword,String inDeviceTocken,String inDeviceType)
    {
        if (utility.checkInternet())
        {
            String strPastAuctionUrl = Application_Constants.Main_URL_Procedure+"spUserLogin("+inUserName+","+inUserPassword+","+inDeviceTocken+","+inDeviceType+")?api_key="+ Application_Constants.API_KEY;

            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(null, strPastAuctionUrl, new ServiceHandler.VolleyCallback()
            {
                @Override
                public void onSuccess(String result) {
                    System.out.println("response" + result.toString());
                    JSONObject userObject = null;

                    try {
                        JSONArray userArray = new JSONArray(result);

                        if (userArray.length() == 0)
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Invalid Username or Password ",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                        else
                        {
                            userObject = userArray.getJSONObject(0);
                            String str_user_id = userObject.getString("userid");
                            String str_email = userObject.getString("email");
                            String str_name = userObject.getString("name");
                            String str_mobile= userObject.getString("Mobile");
                            String str_BillingName = userObject.getString("BillingName");
                            String str_BillingAddress = userObject.getString("address1");
                            String str_BillingCity = userObject.getString("city");
                            String str_BillingState = userObject.getString("state");
                            String str_BillingCountry = userObject.getString("country");
                            String str_BillingZip = userObject.getString("zip");
                            String str_BillingTelephone = userObject.getString("telephone");
                            String str_BillingEmail = userObject.getString("email");
                            String MobileVerified = userObject.getString("MobileVerified");
                            String EmailVerified = userObject.getString("EmailVerified");
                            String confirmbid= userObject.getString("confirmbid");
                            String name = userObject.getString("name");
                            String lastname = userObject.getString("lastname");
                            if (MobileVerified.equals("1"))
                            {
                                sessionData.setObjectAsString("MobileVerified","true");
                            }
                            else
                            {
                                sessionData.setObjectAsString("MobileVerified","false");
                            }

                            if (EmailVerified.equals("1"))
                            {
                                sessionData.setObjectAsString("EmailVerified","true");
                            }
                            else
                            {
                                sessionData.setObjectAsString("EmailVerified","false");
                            }

                            sessionData.setString("BillingName",str_BillingName);
                            sessionData.setString("BillingAddress",str_BillingAddress);
                            sessionData.setString("BillingCity",str_BillingCity);
                            sessionData.setString("BillingState",str_BillingState);
                            sessionData.setString("BillingCountry",str_BillingCountry);
                            sessionData.setString("BillingZip",str_BillingZip);
                            sessionData.setString("BillingTelephone",str_BillingTelephone);
                            sessionData.setString("BillingEmail",str_BillingEmail);

                            if(!name.equals("null"))
                            {
                                sessionData.setString("name",name);
                            }
                            if(!lastname.equals("null"))
                            {
                                sessionData.setString("lastname",lastname);
                            }

                            System.out.println("sssss" + str_BillingName);
                            System.out.println("sssss" + str_BillingAddress);
                            System.out.println("sssss" + str_BillingCity);
                            System.out.println("sssss" + str_BillingState);
                            System.out.println("sssss" + str_BillingCountry);
                            System.out.println("sssss" + str_BillingZip);
                            System.out.println("sssss" + str_BillingTelephone);
                            System.out.println("sssss" + str_BillingEmail);

                            sessionData.setString("userid",str_user_id);
                            sessionData.setString("email",str_email);
                            sessionData.setString("mobile",str_mobile);
                            sessionData.setString("name",str_name);
                            sessionData.setString("confirmbid",confirmbid);
                            sessionData.setObjectAsString("login","true");

                            //launch home screen
                            if (str_type.equals("adpter"))
                            {
                                finish();
                            }
                            else
                            {
                                if (MobileVerified.equals("1") && EmailVerified.equals("1"))
                                {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {

                                    Intent intent = new Intent(getApplicationContext(), Verification_Activity.class);
                                    intent.putExtra("Activity","Login");
//                                            intent.putExtra("MobileVerified",MobileVerified);
//                                            intent.putExtra("EmailVerified",EmailVerified);
                                    startActivity(intent);

                                }

//                                        finish();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    currentAuction.call_data();
                }
            });
        }

    }

public void init()
{
    utility = new Utility(getApplicationContext());

}

    private String getTextToString(EditText editText)
    {
        String strValue = editText.getText().toString().trim();
        return  strValue;
    }

}
