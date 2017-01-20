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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import services.SessionData;
import services.Utility;

public class RegistrationActivity extends AppCompatActivity
{


    @BindView(R.id.edt_first_name) EditText edt_first_name;
    @BindView(R.id.edt_last_name) EditText edt_last_name;
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
            strPassword,strConfirmPassword;

    public SessionData data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = RegistrationActivity.this;

        data = new SessionData(context);




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


        init();

        btn_proceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                strFirstName  = getTextToString(edt_first_name);
                strLastName  = getTextToString(edt_last_name);
                strCity  = getTextToString(edt_city);
                strState  = getTextToString(edt_state);
                strZip  = getTextToString(edt_zip);
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
                    edt_telephone.setError("Enter Telephone");
                }

                else if(strTelephone.length()<10)
                {
                    edt_telephone.requestFocus();
                    edt_telephone.setError("Enter Valid Telephone");
                }
                else if(strEmail.isEmpty())
                {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Emaild Id");
                }
                else if(!utility.checkEmail(strEmail))
                {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Valid Emaild Id");
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
                   RegisterUser(strFirstName,strLastName,strCity,strState,strZip,strMobile,strTelephone,strFax,strEmail,strUserName,
                           strPassword,str_contry);
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



    public void RegisterUser(String strFirstName,String strLastName,String strCity,String strState,String strZip,String strMobile,String strTelephone,String strFax,String strEmail,String strUserName,
                             String strPassword,String str_contry)
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

        //url
        String URL_url = "http://54.169.222.181/api/v2/guru/_table/users/?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";

        //building params
        Map<String, String> params = new HashMap<>();
        params.put("name", strFirstName);
        params.put("lastname",strLastName);
        params.put("city",strCity);
        params.put("country",str_contry);
        params.put("state",strState);
        params.put("zip",strZip);
        params.put("Mobile",strMobile);
        params.put("telephone",strTelephone);
        params.put("fax",strFax);
        params.put("email",strEmail);
        params.put("username",strUserName);
        params.put("password",strPassword);
        params.put("nickname",nickname);
        params.put("admin","0");


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

                                data.setObjectAsString("name",username);
                                data.setObjectAsString("mobile",mobile);
                                data.setObjectAsString("email",email);
                                Intent intent = new Intent(RegistrationActivity.this,Verification_Activity.class);
                                startActivity(intent);
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

    public interface VolleyCallback
    {
        void onSuccess(String result);
    }


}
