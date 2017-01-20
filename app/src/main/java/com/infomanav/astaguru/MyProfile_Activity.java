package com.infomanav.astaguru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import services.Application_Constants;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 03-10-2016.
 */

public class MyProfile_Activity  extends AppCompatActivity
{


    @BindView(R.id.edt_first_name) EditText edt_first_name;
    @BindView(R.id.edt_last_name) EditText edt_last_name;
    @BindView(R.id.edt_mob_num) EditText edt_mob_num;
    @BindView(R.id.edt_tel_num)EditText edt_tel_num;
    @BindView(R.id.edt_email)EditText edt_email;
    @BindView(R.id.edt_bill_addr)EditText edt_bill_addr;
    @BindView(R.id.edt_bill_name) EditText edt_bill_name;


    @BindView(R.id.edt_city) EditText edt_city;
    @BindView(R.id.edt_state) EditText edt_state;
    @BindView(R.id.edt_zip) EditText edt_zip;





    @BindView(R.id.edt_user_name) EditText edt_user_name;

    @BindView(R.id.edt_password) EditText edt_password;
    @BindView(R.id.edt_nick_name) EditText edt_nick_name;

    @BindView(R.id.edt_bill_tel_phn) EditText edt_bill_tel_phn;


    private Utility utility;
    private KProgressHUD hud;
    Context context;
    String str_userid;
    SessionData sessionData;
    TextView toolbar_done,toolbar_cancel;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        context =this;


        sessionData = new SessionData(context);

        str_userid =sessionData.getString("userid");



       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        toolbar_done =(TextView) findViewById(R.id.toolbar_done);
        toolbar_cancel =(TextView) findViewById(R.id.toolbar_cancel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmyprofile);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setTitle("");

        toolbar.setTitle("");

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.toolbar_vtitle);
        tool_text.setText("My Profile");


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
        loginWithCredentials(str_userid);

        toolbar_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar_done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String strFirstName,strLastName,strMobile,strTelephone,strEmail,str_bill_name,str_bill_addr,strCity,strState,strZip,str_bill_tel_phn,strUserName,strPasword,strNickname;
                strFirstName  = getTextToString(edt_first_name);
                strLastName  = getTextToString(edt_last_name);
                strMobile  = getTextToString(edt_mob_num);
                strTelephone  = getTextToString(edt_tel_num);
                strEmail  = getTextToString(edt_email);
                str_bill_name  = getTextToString(edt_bill_name);
                str_bill_addr  = getTextToString(edt_bill_addr);

                strCity  = getTextToString(edt_city);
                strState  = getTextToString(edt_state);
                strZip  = getTextToString(edt_zip);
                str_bill_tel_phn  = getTextToString(edt_bill_tel_phn);


                strUserName  = getTextToString(edt_user_name);
                strPasword  = getTextToString(edt_password);
                strNickname  = getTextToString(edt_nick_name);


                RegisterUser(strFirstName,strLastName,strMobile,strTelephone,strEmail,str_bill_name,str_bill_addr,strCity,strState,strZip,str_bill_tel_phn,strUserName,strPasword,strNickname);


            }
        });
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

    private void loginWithCredentials (String str_userid) {

        if (utility.checkInternet()) {
            hud = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f);

            hud.show();

            String URL = Application_Constants.Main_URL+"users/?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed" + "&filter=(userid%20=%20" + str_userid + ")";


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, URL, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("log", response.toString());
                            hud.dismiss();


                            JSONObject userObject = null;
                            try {
                                if (response.getJSONArray("resource").length() == 0) {
                                    Toast.makeText(getApplicationContext(),
                                            "Invalid Username or Password ",
                                            Toast.LENGTH_LONG)
                                            .show();
                                } else {
                                    userObject = response.getJSONArray("resource").getJSONObject(0);

                                    edt_first_name.setText(userObject.getString("name"));
                                    edt_last_name.setText(userObject.getString("lastname"));
                                    edt_mob_num.setText(userObject.getString("Mobile"));
                                    edt_tel_num.setText(userObject.getString("telephone"));
                                    edt_bill_name.setText(userObject.getString("BillingName"));
                                    edt_bill_addr.setText(userObject.getString("BillingAddress"));
                                    edt_email.setText(userObject.getString("email"));
                                    edt_city.setText(userObject.getString("city"));
                                    edt_state.setText(userObject.getString("state"));
                                    edt_zip.setText(userObject.getString("zip"));
                                    edt_bill_tel_phn.setText(userObject.getString("telephone"));
                                    edt_user_name.setText(userObject.getString("username"));
                                    edt_password.setText(userObject.getString("password"));
                                    edt_nick_name.setText(userObject.getString("nickname"));
                                    //launch home screen
//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    startActivity(intent);

                                    //finish activity to disable navigate to this page on back button.
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("test", "Error: " + error.getMessage());
                    hud.dismiss();
                    VolleyLog.e("Error: ", error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Unable to login." + error.getCause(),
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




    public void RegisterUser(String strFirstName,String strLastName,String strMobile,String strTelephone,String strEmail,String str_bill_name,String str_bill_addr,String strCity,String strState,String strZip,String str_bill_tel_phn,String strUserName,String strPassword,
                             String strNickname)
    {

        pDialog  = new ProgressDialog(MyProfile_Activity.this);
        pDialog.setMessage("Uploadig data ... ");
        pDialog.setCancelable(false);
        pDialog.show();

        //url
        String URL_url = "http://54.169.222.181/api/v2/guru/_table/users?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";

        //building params
        Map<String, String> params = new HashMap<>();
        params.put("userid",str_userid);
        params.put("name", strFirstName);
        params.put("lastname",strLastName);
        params.put("Mobile",strMobile);
        params.put("telephone",strTelephone);
        params.put("email",strEmail);
        params.put("BillingName",str_bill_name);
        params.put("BillingAddress",str_bill_addr);
        params.put("city",strCity);
        params.put("state",strState);
        params.put("zip",strZip);
        params.put("admin",str_bill_tel_phn);
        params.put("username",strUserName);
        params.put("password",strPassword);
        params.put("nickname",strNickname);
        params.put("admin","0");


        JSONArray resourcesArr = new JSONArray();
        resourcesArr.put(new JSONObject(params));

        Map<String, JSONArray> resourceParams = new HashMap<>();
        resourceParams.put("resource", resourcesArr);

        JSONObject requestParam = new JSONObject(resourceParams);
        Log.v("json Object : ",requestParam.toString());

        //jsonojectrequest with params
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL_url, requestParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try
                        {
                            VolleyLog.v("Response:%n %s", response.toString());

                            System.out.print("Response:%n %s "+ response.toString());

                            JSONObject userObject = null;

                            userObject = response.getJSONArray("resource").getJSONObject(0);
                            String data = userObject.getString("userid");

                            if (!(data==null))
                            {
                                Toast.makeText(context,"Profile Succesfully Save",Toast.LENGTH_SHORT).show();
                                loginWithCredentials(str_userid);
                            }else
                            {
                                Toast.makeText(context,"User Not Registered",Toast.LENGTH_SHORT);
                            }




//                            Intent intent = new Intent(RegistrationActivity.this,Verification_Activity.class);
//                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        VolleyLog.e("Error: ", error.getMessage());

                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT);

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);


    }

    public interface VolleyCallback
    {
        void onSuccess(String result);
    }

}
//
//if(strFirstName.isEmpty())
//        {
//        edt_first_name.requestFocus();
//        edt_first_name.setError("Enter First Name");
//        }
//        else if(strLastName.isEmpty())
//        {
//        edt_last_name.requestFocus();
//        edt_last_name.setError("Enter Last Name");
//        }
//        else if(strMobile.isEmpty())
//        {
//        edt_mob_num.requestFocus();
//        edt_city.setError("Enter Mobile Number");
//        }
//        else if(strTelephone.isEmpty())
//        {
//        edt_state.requestFocus();
//        edt_state.setError("Enter Telphone Number");
//        }
//        else if(!utility.checkEmail(strEmail))
//        {
//        edt_email.requestFocus();
//        edt_email.setError("Enter Valid Emaild Id");
//        }
//        else if(str_bill_name.isEmpty())
//        {
//        edt_bill_name.requestFocus();
//        edt_bill_name.setError("Enter Billing Name");
//        } else if(str_bill_addr.length()<10)
//        {
//        edt_bill_addr.requestFocus();
//        edt_bill_addr.setError("Enter Billing Address");
//        }
//
//        else if(strCity.isEmpty())
//        {
//        edt_city.requestFocus();
//        edt_city.setError("Enter City");
//        }
//        else if(strState.length()<10)
//        {
//        edt_state.requestFocus();
//        edt_state.setError("Enter State");
//        }
//        else if(strZip.isEmpty())
//        {
//        edt_zip.requestFocus();
//        edt_zip.setError("Enter Zip");
//        }
//
//        else if(str_bill_tel_phn.isEmpty())
//        {
//        edt_bill_tel_phn.requestFocus();
//        edt_bill_tel_phn.setError("Enter Billing Telephone");
//        }
//        else if(strUserName.isEmpty())
//        {
//        edt_user_name.requestFocus();
//        edt_user_name.setError("Enter UserName");
//        }
//        else if(strPasword.isEmpty())
//        {
//        edt_password.requestFocus();
//        edt_password.setError("Enter Password");
//        }
//        else if(!strNickname.isEmpty())
//        {
//        edt_nick_name.requestFocus();
//        edt_nick_name.setError("Enter Nick Name");
//        }
//        else
//        {
//        Toast.makeText(MyProfile_Activity.this, "Valid Form", Toast.LENGTH_SHORT).show();
//        }