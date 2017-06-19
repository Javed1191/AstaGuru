package com.infomanav.astaguru;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import services.SessionData;
import services.Utility;

/**
 * Created by android-javed on 05-10-2016.
 */

public class Complete_SignUp_Activity extends AppCompatActivity
{

    private Utility utility;
    private Button btn_currentauction;
    SessionData sessionData;
    Context context;
    String activity,MobileVerified_value,EmailVerified_value,username,password,email,telephone,country,state,zip,city,fax,
            address,lastname,firstname;
    private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compelte_signup);
        context = Complete_SignUp_Activity.this;
        sessionData = new SessionData(context);

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


//
//
        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Complete Sign Up");


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

        Intent intent = getIntent();

        if(intent.getExtras()!=null)
        {
            firstname = intent.getStringExtra("firstname");
            lastname = intent.getStringExtra("lastname");
            address = intent.getStringExtra("address");
            city = intent.getStringExtra("city");
            zip = intent.getStringExtra("zip");
            state = intent.getStringExtra("state");
            country = intent.getStringExtra("country");
            telephone = intent.getStringExtra("telephone");
            email = intent.getStringExtra("email");
            username = intent.getStringExtra("username");
            password = intent.getStringExtra("password");
            fax = intent.getStringExtra("fax");
        }

        btn_currentauction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionData.setObjectAsString("login","true");
                Intent intent = new Intent(Complete_SignUp_Activity.this,MainActivity.class);
                intent.putExtra("fragment","current");
                startActivity(intent);


            }
        });

        Send_Email();
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
            sessionData.setObjectAsString("login","true");
            Intent intent = new Intent(Complete_SignUp_Activity.this,MainActivity.class);
            intent.putExtra("fragment","home");
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }


    public void init()
    {
        utility = new Utility(getApplicationContext());
        btn_currentauction = (Button) findViewById(R.id.btn_currentauction);
    }


    private void Send_Email()
    {

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        hud.show();
        try
        {

           String  msg = "Dear "+firstname+" "+ lastname+",\n\nThank you for the information provided. Your account security is of paramount importance to us.\n" +
                   "Therefore we will have one of our representative call you on the number provided within the next 24\n" +
                   "hours to verify the following details. Further to which we will provide you with bidding access for our\n" +
                   "auctions.\n" +
                   "In case you would like to edit any details, please notify our representative during the course of your\n" +
                   "conversation.\n"+"\n\n Name: "+ firstname+"\n Last Name: "+ lastname+"\n Address: "+ address+"\n City: "+ city+
                   "\n Zip: "+ zip+"\n State: "+ state+"\n Country: "+ country+"\n Telephone: "+ telephone+"\n Fax: "+ fax+
                   "\n Email: "+ email+"\n Username: "+ username+"\n Password: "+ password+"\n\n For any further assistance please feel free to write to us at, contact@astaguru.com or call us on 91-22\n" +
                   "\n" +
                   "2204 8138/39. We will be glad to assist you. \n\nThanking You,\n\n Warm Regards, \n\n Team of AstaGuru";
           // String URL_url = "http://52.66.4.131/api/v2/awsses/?api_key=6dee0a21388b49db917cd64559c32bcbde93460f391a594ab7cc6666824d5c26";
            String  URL_url = "http://restapi.infomanav.com/api/v2/awsses/?api_key=6dee0a21388b49db917cd64559c32bcbde93460f391a594ab7cc6666824d5c26";

            final JSONArray array = new JSONArray();

            JSONObject jb = new JSONObject();
            jb.put("name",username);
            jb.put("email",email);

            array.put(jb);
            Map<String, String> params = new HashMap();
            params.put("template","newsletter");
            params.put("subject","Your Registration with Astaguru.com is confirmed and complete.");
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
                  //  Toast.makeText(Complete_SignUp_Activity.this,"Otp Succesfully Send On EmailID ",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    hud.dismiss();
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
        catch (JSONException e)
        {
            hud.dismiss();
            System.out.println("error" + e.getMessage());
        }

    }

}


