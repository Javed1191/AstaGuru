package com.infomanav.astaguru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
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
import org.json.JSONObject;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import services.Application_Constants;
import services.SessionData;
import services.Shared_Preferences_Class;
import services.Utility;

import static java.security.AccessController.getContext;

public class RegistrationActivity extends AppCompatActivity
{


    @BindView(R.id.edt_countrycode) EditText edt_countrycode;
    @BindView(R.id.edt_first_name) EditText edt_first_name;
    @BindView(R.id.edt_last_name) EditText edt_last_name;
    @BindView(R.id.edt_address) EditText edt_address;
    @BindView(R.id.edt_city) EditText edt_city;
    @BindView(R.id.edt_contry) EditText edt_contry;
    @BindView(R.id.edt_state) EditText edt_state;
    @BindView(R.id.edt_zip) EditText edt_zip;
    @BindView(R.id.edt_mobile) EditText edt_mobile;
    @BindView(R.id.edt_telephone)EditText edt_telephone;
    @BindView(R.id.edt_fax)EditText edt_fax;
    @BindView(R.id.edt_email)EditText edt_email;
    @BindView(R.id.edt_user_name) EditText edt_user_name;
    @BindView(R.id.edt_password) EditText edt_password;
    @BindView(R.id.edt_re_password) EditText edt_re_password;
    @BindView(R.id.chk_terms_condition) CheckBox chk_terms_condition;
   @BindView(R.id.btn_proceed) Button btn_proceed;

    private Utility utility;
    private Context context;
    private KProgressHUD hud;
    String strFirstName,strLastName,str_contry,strCity,strState,strZip,strMobile,strTelephone,strFax,strEmail,strUserName,
            strPassword,strConfirmPassword,str_address,str_countrycode,android_id,datetime;

    public SessionData data;
    private String userFcmId="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = RegistrationActivity.this;

        data = new SessionData(context);
        android_id = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        data.setObjectAsString("android_id",android_id);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
        datetime = dateformat.format(c.getTime());
        System.out.println("datetime" + datetime);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Sign Up");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
       ButterKnife.bind(this);

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

        edt_first_name.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });
        edt_last_name.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                strFirstName  = getTextToString(edt_first_name);
                strLastName  = getTextToString(edt_last_name);

                str_address= getTextToString(edt_address);
                strCity  = getTextToString(edt_city);
                strState  = getTextToString(edt_state);
                strZip  = getTextToString(edt_zip);
               str_countrycode = getTextToString(edt_countrycode);
                strMobile  = getTextToString(edt_mobile);
                strTelephone  = getTextToString(edt_telephone);
                strFax  = getTextToString(edt_fax);
                strEmail  = getTextToString(edt_email);
                strUserName  = getTextToString(edt_user_name);
                strPassword  = getTextToString(edt_password);
                strConfirmPassword  = getTextToString(edt_re_password);
                str_contry= getTextToString(edt_contry);
                if(strFirstName.isEmpty())
                {
                    edt_first_name.requestFocus();
                    edt_first_name.setError("Enter First Name");
                }
                else if(strLastName.isEmpty())
                {
                    edt_last_name.requestFocus();
                    edt_last_name.setError("Enter Last Name");
                }
                else if(str_address.isEmpty())
                {
                    edt_address.requestFocus();
                    edt_address.setError("Enter Address");
                }
                else if(str_contry.isEmpty())
                {
                    edt_contry.requestFocus();
                    edt_contry.setError("Enter Country");
                }
                else if(strState.isEmpty())
                {
                    edt_state.requestFocus();
                    edt_state.setError("Enter State");
                }
                else if(strCity.isEmpty())
                {
                    edt_city.requestFocus();
                    edt_city.setError("Enter City");
                }

                else if(strZip.isEmpty())
                {
                    edt_zip.requestFocus();
                    edt_zip.setError("Enter Zip Code");
                }
                else if(str_countrycode.isEmpty())
                {
                    edt_countrycode.requestFocus();
                    edt_countrycode.setError("Enter Country Code");
                }
                else if(str_countrycode.length()>=3)
                {
                    edt_countrycode.requestFocus();
                    edt_countrycode.setError("Enter Valid Country Code");
                }

                else if(strMobile.isEmpty())
                {
                    edt_mobile.requestFocus();
                    edt_mobile.setError("Enter Mobile Number");
                } else if(strMobile.length()<10)
                {
                    edt_mobile.requestFocus();
                    edt_mobile.setError("Enter Valid Mobile Number");
                }

                else if(strTelephone.isEmpty())
                {
                    edt_telephone.requestFocus();
                    edt_telephone.setError("Enter Telephone Number");
                }

                else if(strTelephone.length()<10)
                {
                    edt_telephone.requestFocus();
                    edt_telephone.setError("Enter Valid Telephone Number");
                }
                else if(strEmail.isEmpty())
                {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Email Id");
                }
                else if(!utility.checkEmail(strEmail))
                {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Valid Email Id");
                }
                else if(strUserName.isEmpty())
                {
                    edt_user_name.requestFocus();
                    edt_user_name.setError("Enter User Name");
                }
                else if(strPassword.isEmpty())
                {
                    edt_password.requestFocus();
                    edt_password.setError("Enter Password");
                }

                else if(strPassword.length()<6)
                {
                    edt_password.requestFocus();
                    edt_password.setError("Password Not Less Than 6 Digit");
                }
                else if(strConfirmPassword.isEmpty())
                {
                    edt_re_password.requestFocus();
                    edt_re_password.setError("Enter Re Password");
                }
                else if(!strConfirmPassword.equals(strPassword))
                {
                    edt_re_password.requestFocus();
                    edt_re_password.setError("Password Not Match");
                }

                else if(!chk_terms_condition.isChecked())
                {
                    Toast.makeText(context,"Please Accept Terms and Conditions",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Check_Valid_Email(strFirstName,strEmail);

                }


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

        int id = item.getItemId();

        if (id == R.id.action_close)
        {
            finish();
        }


        return super.onOptionsItemSelected(item);
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



    public void Check_Valid_Email(final String strFirstName,final String strEmail)
    {


        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);

        hud.show();

        int randomPIN = (int) (Math.random() * 900000) + 100000;

        final String str_pin = String.valueOf(randomPIN);

        final String nickname = "Anonymous"+str_pin;
        System.out.print("nickname"+ nickname);
        final String mobile = strMobile;
        final String email = strEmail;

        final String username = strFirstName;
        String URL_url = Application_Constants.Main_URL+"users/?api_key="+ Application_Constants.API_KEY+"&fields=userid,username,email,name&filter=(username="+strUserName+")or(email="+strEmail+")";
        Map<String, String> params = new HashMap<>();


        JSONArray resourcesArr = new JSONArray();
        resourcesArr.put(new JSONObject(params));

        Map<String, JSONArray> resourceParams = new HashMap<>();
        resourceParams.put("resource", resourcesArr);

        JSONObject requestParam = new JSONObject(resourceParams);
        Log.v("json Object : ",requestParam.toString());

        //jsonojectrequest with params
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_url, requestParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hud.dismiss();
                        try
                        {
                            JSONObject jobject = new JSONObject(response.toString());
                            JSONArray jsonArray = new JSONArray();
                            jsonArray = jobject.getJSONArray("resource");



                            if (jsonArray.length()>0)
                            {

                                Toast.makeText(context,"Email or Username already exists \n please use other Email or Username",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
//                                Intent intent = new Intent(RegistrationActivity.this,Verification_Activity.class);
//
//                                intent.putExtra("Activity","Registration");
//                                intent.putExtra("strFirstName",strFirstName);
//                                intent.putExtra("strLastName",strLastName);
//                                intent.putExtra("str_address",str_address);
//                                intent.putExtra("strCity",strCity);
//                                intent.putExtra("strState",strState);
//                                intent.putExtra("strZip",strZip);
//                                intent.putExtra("strMobile",strMobile);
//                                intent.putExtra("strTelephone",strTelephone);
//                                intent.putExtra("strFax",strFax);
//                                intent.putExtra("strEmail",strEmail);
//                                intent.putExtra("strUserName",strUserName);
//                                intent.putExtra("strPassword",strPassword);
//                                intent.putExtra("str_contry",str_contry);
//                                intent.putExtra("str_address",str_address);
//                                startActivity(intent);


                                RegisterUser(strFirstName,strLastName,strCity,strState,strZip,strMobile,strTelephone,strFax,strEmail,strUserName,
                                        strPassword,str_contry,str_address,str_countrycode);

                            }





//                                data.setObjectAsString("name",username);
//                                data.setObjectAsString("mobile",mobile);
//                                data.setObjectAsString("email",email);






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
                        hud.dismiss();
                        VolleyLog.e("Error: ", error.getMessage());

                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT);

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

        // AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public interface VolleyCallback
    {
        void onSuccess(String result);
    }
    public void RegisterUser(final String strFirstName, final String strLastName, final String strCity, final String strState, final String strZip, String strMobile, final String strTelephone, final String strFax, final String strEmail, final String strUserName,
                             final String strPassword, final String str_contry, final String str_address, String str_countrycode)
    {


        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);

        hud.show();

        int randomPIN = (int) (Math.random() * 900000) + 100000;

        final String str_pin = String.valueOf(randomPIN);

        final String nickname = "Anonymous"+str_pin;
        System.out.print("nickname"+ nickname);
        final String mobile = str_countrycode +strMobile;
        final String email = strEmail;

        final String username = strFirstName;
        String URL_url = Application_Constants.Main_URL+"users/?api_key="+ Application_Constants.API_KEY;
        //building params
        Map<String, String> params = new HashMap<>();
        params.put("name", strFirstName);
        params.put("lastname",strLastName);
        params.put("city",strCity);
        params.put("country",str_contry);
        params.put("state",strState);
        params.put("zip",strZip);
        params.put("Mobile",mobile);
        params.put("telephone",strTelephone);
        params.put("fax",strFax);
        params.put("email",strEmail);
        params.put("username",strUserName);
        params.put("password",strPassword);
        params.put("nickname",nickname);
        params.put("address1",str_address);
        params.put("t_username",strUserName);
        params.put("t_password",strPassword);
        params.put("t_firstname",strFirstName);
        params.put("t_lastname",strLastName);
        params.put("t_City",strCity);
        params.put("t_State",strState);
        params.put("t_Country",str_contry);
        params.put("t_telephone",strTelephone);
        params.put("t_fax",strFax);
        params.put("t_email",strEmail);
        params.put("t_mobile",mobile);
        params.put("t_address1",str_address);
        params.put("t_nickname",nickname);
        params.put("t_billingaddress",str_address);
        params.put("t_billingname",strFirstName);
        params.put("t_billingcity",strCity);
        params.put("t_billingstate",strState);
        params.put("t_billingcountry",str_contry);
        params.put("t_billingzip",strZip);
        params.put("t_billingtelephone",strTelephone);
        params.put("t_billingemail",strEmail);
        params.put("BillingName",strFirstName+strLastName);
        params.put("BillingCity",strCity);
        params.put("BillingState",strState);
        params.put("BillingCountry",str_contry);
        params.put("BillingZip",strZip);
        params.put("BillingAddress",str_address);
        params.put("BillingTelephone",strTelephone);
        params.put("BillingEmail",strEmail);
        params.put("SmsCode","0");
        params.put("admin","0");
        params.put("MobileVerified","0");
        params.put("EmailVerified","0");
        params.put("chatdept","Test");
        params.put("confirmbid","0");
        params.put("Visits","0");
        params.put("buy","0");
        params.put("applyforbid","1");
        params.put("applyforchange","0");
        params.put("RegistrationDate",datetime);
        params.put("deviceTocken",android_id);
        params.put("androidDeviceTocken",userFcmId); // FCM ID
        System.out.print("android_id"+ datetime);
        System.out.print("android_id"+ android_id);
//        2007-11-20 16:30:58.423


        JSONArray resourcesArr = new JSONArray();
        resourcesArr.put(new JSONObject(params));

        Map<String, JSONArray> resourceParams = new HashMap<>();
        resourceParams.put("resource", resourcesArr);

        JSONObject requestParam = new JSONObject(resourceParams);
        Log.v("json Object : ",requestParam.toString());

        //jsonojectrequest with params
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_url, requestParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hud.dismiss();
                        try
                        {
                            VolleyLog.v("Response:%n %s", response.toString());

                            System.out.print("Response:%n %s "+ response.toString());

                            JSONObject userObject = null;

                            userObject = response.getJSONArray("resource").getJSONObject(0);
                            String datadata = userObject.getString("userid");




                            if (!(datadata==null))
                            {
                                data.setObjectAsString("userid",datadata);
                                data.setObjectAsString("name",username);
                                data.setObjectAsString("mobile",mobile);
                                data.setObjectAsString("email",email);
                                data.setObjectAsString("firstname",strFirstName);
                                data.setObjectAsString("lastname",strLastName);
                                data.setObjectAsString("address",str_address);
                                data.setObjectAsString("city",strCity);
                                data.setObjectAsString("zip",strZip);
                                data.setObjectAsString("state",strState);
                                data.setObjectAsString("country",str_contry);
                                data.setObjectAsString("telephone",strTelephone);
                                data.setObjectAsString("password",strPassword);
                                data.setObjectAsString("fax",strFax);

                                Intent intent = new Intent(RegistrationActivity.this, Verification_Activity.class);
                               /* intent.putExtra("firstname",strFirstName);
                                intent.putExtra("lastname",strLastName);
                                intent.putExtra("address",str_address);
                                intent.putExtra("city",strCity);
                                intent.putExtra("zip",strZip);
                                intent.putExtra("state",strState);
                                intent.putExtra("country",str_contry);
                                intent.putExtra("telephone",strTelephone);
                                intent.putExtra("email",strEmail);
                                intent.putExtra("username",strUserName);
                                intent.putExtra("password",strPassword);
                                intent.putExtra("fax",strFax);*/
                                intent.putExtra("Activity","Registration");
                                startActivity(intent);
                                finish();
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
                        hud.dismiss();
                        VolleyLog.e("Error: ", error.getMessage());

                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT);

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

        // AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
