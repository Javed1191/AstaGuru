package com.infomanav.astaguru;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import model_classes.Model_Category;
import services.Application_Constants;
import services.Utility;

/**
 * Created by android-javed on 01-10-2016.
 */

public class Contact_Us_Activity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener
{

    private Button btn_reach_us,btn_contact_us;
    JSONObject jsonobject;
   public AutoCompleteTextView text;
    JSONArray jsonarray;
    String str_category;
    String selected;
    ProgressDialog mProgressDialog;
    ArrayList<String> worldlist;
    ArrayList<Model_Category> world;
    private LinearLayout lin_contact_us,lin_reach_us;
    private GoogleMap mMap;
    Context context;
    List<String> categories;
    TextView tv_callone,tv_calltwo,tv_mail;
    Utility  utility;
    Button btn_contactsubmit;
    List<String> listState,listStateId;
    ImageView iv_fb,iv_twitter,iv_insta,iv_pinar,iv_youtube;
EditText edt_name,edt_email,edt_number,edt_msg;
    Spinner my_spinner;
    private static final int REQUEST_PERMISSIONS = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        context = Contact_Us_Activity.this;
        utility = new Utility(context);
        if (ContextCompat.checkSelfPermission(Contact_Us_Activity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (Contact_Us_Activity.this, Manifest.permission.CALL_PHONE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (Contact_Us_Activity.this, Manifest.permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(Contact_Us_Activity.this,
                        new String[]{Manifest.permission
                                .CALL_PHONE},
                        REQUEST_PERMISSIONS);
            }
        }


//        my_spinner = (Spinner) findViewById(R.id.my_spinner);
        text=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        init();
        getState();


        text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus)
                {
                    text.showDropDown();
                }

            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    text.showDropDown();

            }
        });

        text.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                 selected = (String) parent.getItemAtPosition(position);
               // Toast.makeText(getApplicationContext(),selected,Toast.LENGTH_LONG).show();
            }
        });



        MapFragment mapFragment = (MapFragment) getFragmentManager() .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
//        addItemsOnSpinner2();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);

        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Contact Us");

        tv_callone = (TextView) findViewById(R.id.tv_callone);

        tv_calltwo = (TextView) findViewById(R.id.tv_calltwo);

        tv_mail = (TextView) findViewById(R.id.tv_mail);

        tv_callone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("result" + "Click");

                if (ActivityCompat.checkSelfPermission(Contact_Us_Activity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+91 22 2204 8138"));
                context.startActivity(callIntent);

//                if ( ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED ) {
//
//                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                    callIntent.setData(Uri.parse("+91 22 2204 8138"));
//                    startActivity(callIntent);
//                }
            }
        });

        tv_calltwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Contact_Us_Activity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+91 22 2204 8140"));
                context.startActivity(callIntent);
            }
        });

        tv_mail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
               /* Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"contact@astaguru.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));*/

                Intent intent = new Intent(Intent.ACTION_SEND);

                String[] strTo = {"contact@astaguru.com"};

                intent.putExtra(Intent.EXTRA_EMAIL, strTo);
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");

             /*   Uri attachments = Uri.parse(image_path);
                intent.putExtra(Intent.EXTRA_STREAM, attachments);*/

                intent.setType("message/rfc822");

                intent.setPackage("com.google.android.gm");

                startActivity(intent);
            }
        });
        btn_contactsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_name,str_email,str_number,str_msg;

                str_name = edt_name.getText().toString();
                str_email = edt_email.getText().toString();
                str_number = edt_number.getText().toString();
                str_msg = edt_msg.getText().toString();

                if(str_name.isEmpty())
                {
                    edt_name.requestFocus();
                    edt_name.setError("Enter Name");
                }
                else if(str_email.isEmpty())
                {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Email");
                }
                else if(!utility.checkEmail(str_email))
                {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Valid Emaild Id");
                }
                else if(str_number.isEmpty())
                {
                    edt_number.requestFocus();
                    edt_number.setError("Enter Number");
                }

                else if(str_number.length()<10)
                {
                    edt_number.requestFocus();
                    edt_number.setError("Enter Valid Mobile Number");
                }
                else if(str_msg.isEmpty())
                {
                    edt_msg.requestFocus();
                    edt_msg.setError("Enter Message");
                }
                else
                {
                    String mail_body = " Name :"+ str_name+"\n\n Email Id: "+ str_email+" \n\n Mobile Number: "+ str_number+"\n\n Category: "+ selected+"\n\n Message: "+ str_msg+"";

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"b@infomanav.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from astaguru app");
                    email.putExtra(Intent.EXTRA_TEXT,mail_body);
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                }

            }
        });

        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ButterKnife.bind(this);


        iv_fb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/astaguru/")));

            }
        });
        iv_twitter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/astagurutweets")));


            }
        });
        iv_insta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/astaguru/")));

            }
        });
        iv_pinar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://in.pinterest.com/astaguru/")));
            }
        });
        iv_youtube.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCmTqSUMAHV5l0mACoK72t7g")));
            }
        });


        edt_name.setFilters(new InputFilter[] {
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

        btn_contact_us.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lin_reach_us.setVisibility(View.GONE);
                lin_contact_us.setVisibility(View.VISIBLE);

                btn_contact_us.setBackgroundColor(getResources().getColor(R.color.btn_reachus));
                btn_reach_us.setBackgroundColor(getResources().getColor(R.color.btn_bg_white));
                btn_reach_us.setTextColor(getResources().getColor(R.color.btn_reachus));
                btn_contact_us.setTextColor(getResources().getColor(R.color.btn_bg_white));

            }
        });
        btn_reach_us.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lin_contact_us.setVisibility(View.GONE);
                lin_reach_us.setVisibility(View.VISIBLE);
                btn_reach_us.setBackgroundColor(getResources().getColor(R.color.btn_reachus));
                btn_contact_us.setBackgroundColor(getResources().getColor(R.color.btn_bg_white));
                btn_contact_us.setTextColor(getResources().getColor(R.color.btn_reachus));
                btn_reach_us.setTextColor(getResources().getColor(R.color.btn_bg_white));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        str_category = item;
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    private void getState() {

        //setup blanck spinners
        String[] st = new String[]{
                "Select state..."
        };

        listState = new ArrayList<>(Arrays.asList(st));
        listStateId = new ArrayList<>();

        String strPastAuctionUrl = Application_Constants.Main_URL+"category?api_key="+ Application_Constants.API_KEY;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strPastAuctionUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString());

                            JSONArray states = response.getJSONArray("resource");

                            String[] st = new String[]{
                                    "Select Category"
                            };
                            listState = new ArrayList<>(Arrays.asList(st));
                            listStateId = new ArrayList<>();

                            for (int i = 0; i < states.length(); i++) {
                                JSONObject state = states.getJSONObject(i);
                                listStateId.add(state.getString("categoryid"));
                                listState.add(state.getString("category"));
                            }
                            ArrayAdapter adapter = new
                                    ArrayAdapter(Contact_Us_Activity.this,android.R.layout.simple_list_item_1,listState);
                            text.setAdapter(adapter);
                            text.setThreshold(1);

                            // Initializing an ArrayAdapter
//                            final ArrayAdapter<String> adapterState = new ArrayAdapter<String>(
//                                    Contact_Us_Activity.this,R.layout.spinner_row,listState){
//
//
//                                @Override
//                                public boolean isEnabled(int position){
//                                    if(position == 0)
//                                    {
//                                        // Disable the first item from Spinner
//                                        // First item will be use for hint
//                                        return false;
//                                    }
//                                    else
//                                    {
//                                        return true;
//                                    }
//                                }
//                                @Override
//                                public View getDropDownView(int position, View convertView,
//                                                            ViewGroup parent) {
//                                    View view = super.getDropDownView(position, convertView, parent);
//                                    TextView tv = (TextView) view;
//                                    if(position == 0){
//                                        // Set the hint text color gray
//                                        tv.setTextColor(Color.GRAY);
//                                    }
//                                    else {
//                                        tv.setTextColor(Color.BLACK);
//                                    }
//                                    return view;
//                                }
//                            };
//                            adapterState.setDropDownViewResource(R.layout.spinner_row);
//                            my_spinner.setAdapter(adapterState);
//
//                            my_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                                    String item = parent.getItemAtPosition(position).toString();
//
//                                    str_category = item;
//                                    if (position > 0) {
////                                        getDistrict(listStateId.get(position - 1));
//                                    }
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });




                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

//        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(18.927601, 72.832765);
        mMap.addMarker(new MarkerOptions().position(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
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
        btn_contact_us = (Button) findViewById(R.id.btn_contact_us);
        btn_reach_us = (Button) findViewById(R.id.btn_reach_us);

        btn_contactsubmit = (Button) findViewById(R.id.btn_contactsubmit);
        lin_contact_us = (LinearLayout) findViewById(R.id.lin_contact_us);
        lin_reach_us = (LinearLayout) findViewById(R.id.lin_reach_us);

                edt_name = (EditText) findViewById(R.id.edt_name);
                edt_email = (EditText) findViewById(R.id.edt_email);
                edt_number = (EditText) findViewById(R.id.edt_number);
                edt_msg = (EditText) findViewById(R.id.edt_msg);


        iv_fb= (ImageView) findViewById(R.id.iv_fb);
                iv_twitter= (ImageView) findViewById(R.id.iv_twitter);
                iv_insta= (ImageView) findViewById(R.id.iv_insta);
                iv_pinar= (ImageView) findViewById(R.id.iv_pinar);
                iv_youtube= (ImageView) findViewById(R.id.iv_youtube);

    }


}
