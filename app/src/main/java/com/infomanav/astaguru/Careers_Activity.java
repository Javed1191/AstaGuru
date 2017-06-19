package com.infomanav.astaguru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.Careeer_Adpter;
import butterknife.ButterKnife;
import services.Application_Constants;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Utility;

/**
 * Created by android-javed on 01-10-2016.
 */

public class Careers_Activity extends AppCompatActivity
{

   private int  PICK_IMAGE_REQUEST = 1;

    private KProgressHUD hud;
    Model_Careeer country;
    ArrayList<Model_Careeer> appsList;
    ImageView img_user_pic;

    private Button btn_why_astaguru,btn_vacancies;
    ScrollView sc_form,sc_why_asta;

    WebView wb_whyastaguru;
    Context context;
    Careeer_Adpter careeer_adpter;

    ListView listView;
    Utility utility;
    Button btn_proceed;
    Spinner spn_source,spn_post;

    EditText edt_email,edt_first_name,edt_msg;
    String str_post_name,str_source,str_image_path="";
    Button btn_select_file;
    List<String> JobTitle;
    byte[]  byteArray;
    String encodedImage;
    File imageFile;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careers);
        context = Careers_Activity.this;
        utility = new Utility(context);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        GetCareerData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ButterKnife.bind(this);
        listView = (ListView)findViewById(R.id.listView);

        spn_post = (Spinner) findViewById(R.id.spn_post);
        spn_source = (Spinner) findViewById(R.id.spn_source);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Careers");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        wb_whyastaguru = (WebView)findViewById(R.id.wb_whyastaguru) ;
        wb_whyastaguru.loadUrl("file:///android_asset/whyastaguru.html");










        List<String> categories1 = new ArrayList<String>();
        categories1.add("Select Resource");
        categories1.add("News Paper");
        categories1.add("Friends");
        categories1.add("Facebook");
        categories1.add("Others");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,R.layout.spinner_row, categories1);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_source.setAdapter(dataAdapter1);

        spn_source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_source =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               str_post_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_why_astaguru.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sc_why_asta.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                sc_form.setVisibility(View.GONE);
                btn_why_astaguru.setBackgroundColor(getResources().getColor(R.color.btn_reachus));
                btn_vacancies.setBackgroundColor(getResources().getColor(R.color.btn_bg_white));
                btn_vacancies.setTextColor(getResources().getColor(R.color.btn_reachus));
                btn_why_astaguru.setTextColor(getResources().getColor(R.color.btn_bg_white));

//                btn_why_astaguru.setBackgroundColor(getResources().getColor(R.color.btn_bg_brown));
//                btn_vacancies.setBackgroundColor(getResources().getColor(R.color.btn_bg_white));


            }
        });
        btn_vacancies.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sc_why_asta.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);


                btn_vacancies.setBackgroundColor(getResources().getColor(R.color.btn_reachus));
                btn_why_astaguru.setBackgroundColor(getResources().getColor(R.color.btn_bg_white));
                btn_why_astaguru.setTextColor(getResources().getColor(R.color.btn_reachus));
                btn_vacancies.setTextColor(getResources().getColor(R.color.btn_bg_white));

//                btn_vacancies.setBackgroundColor(getResources().getColor(R.color.btn_bg_brown));
//                btn_why_astaguru.setBackgroundColor(getResources().getColor(R.color.btn_bg_white));


            }
        });

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

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strname = edt_first_name.getText().toString();
                String stremail = edt_email.getText().toString();
                String str_msg = edt_msg.getText().toString();


                if(strname.isEmpty())
                {
                    edt_first_name.requestFocus();
                    edt_first_name.setError("Enter First Name");
                }
                else if(stremail.isEmpty())
                {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Emaild Id");
                }
                else if(!utility.checkEmail(stremail))
                {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Valid Emaild Id");
                }

                else if(str_post_name.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Pls Select Post", Toast.LENGTH_SHORT).show();
                }
                else if(str_image_path.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please Upload Resume", Toast.LENGTH_SHORT).show();
                }
                else if(str_msg.isEmpty())
                {
                    edt_msg.requestFocus();
                    edt_msg.setError("Enter User Name");
                }

                else if(str_source.equals("Select Resource"))
                {
                    Toast.makeText(getApplicationContext(), "Pls Select Source", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    new ApplyForJob(strname,stremail,str_post_name,str_image_path,str_msg,str_source).execute();
                }


            }
        });


        btn_select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent i = new Intent(Intent.ACTION_PICK);
//                i.setType("file/*");
//                startActivityForResult(i, PICK_IMAGE_REQUEST);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
                startActivityForResult(intent, PICK_IMAGE_REQUEST);


            }
        });

//
//        iv_plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (tv_job.getVisibility() == View.VISIBLE) {
//                    tv_job.setVisibility(View.GONE);
//                    iv_plus.setBackgroundResource(R.drawable.plus);
//                } else {
//                    tv_job.setVisibility(View.VISIBLE);
//                    iv_plus.setBackgroundResource(R.drawable.minus);
//                }
//            }
//        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null)
        {
            if (requestCode == PICK_IMAGE_REQUEST)
            {

                Uri uri = data.getData();

                str_image_path = getRealPathFromURI(uri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));

                    img_user_pic.setImageBitmap(bitmap);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);


                    byteArray= stream.toByteArray();
                    // String  imageString= Base64.encode(byteArray);

                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    // String url_image = str_change_profile_url+userId;
                    // new post_Async(url_image).execute();

                    // btn_submit_photo.setVisibility(View.VISIBLE);


                    if(utility.checkInternet())
                    {

                        BitmapDrawable drawable = (BitmapDrawable) img_user_pic.getDrawable();
                        Bitmap bitmap1 = drawable.getBitmap();


                        if (drawable == null) {
                            bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.action_man);
                        }


                        File filesDir = getFilesDir();
                        imageFile = new File(filesDir, "user_image.jpg");

                        OutputStream os;
                        try {
                            os = new FileOutputStream(imageFile);
                            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, os);
                            os.flush();
                            os.close();
                        } catch (Exception e) {
                            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                        }

                    }

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }




    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
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
        btn_why_astaguru = (Button) findViewById(R.id.btn_why_astaguru);
        btn_vacancies = (Button) findViewById(R.id.btn_vacancies);
        btn_proceed =  (Button) findViewById(R.id.btn_proceed);
        btn_select_file=  (Button) findViewById(R.id.btn_select_file);
        sc_why_asta = (ScrollView) findViewById(R.id.sc_why_asta);
        sc_form= (ScrollView) findViewById(R.id.sc_form);

        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_first_name = (EditText) findViewById(R.id.edt_first_name);
        edt_msg = (EditText) findViewById(R.id.edt_msg);
        edt_first_name = (EditText) findViewById(R.id.edt_first_name);

        img_user_pic = (ImageView) findViewById(R.id.img_user_pic);
    }

    private void GetCareerData() {

        if (utility.checkInternet()) {

            String strPastAuctionUrl = Application_Constants.Main_URL+"jobs?api_key="+ Application_Constants.API_KEY;
            System.out.println("strPastAuctionUrl " + strPastAuctionUrl);
            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;

                    // String str_FirstName,str_LastName,str_Profile;
                    appsList = new ArrayList<>();
                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            String jobTitle;
                            JobTitle = new ArrayList<String>();
                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");


                                for (int i = 0; i < jsonArray.length(); i++) {

//                                    JSONObject obj_category_by_categoryid = Obj.getJSONObject("category_by_categoryid");
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    String jobID = Obj.getString("jobID");
                                     jobTitle = Obj.getString("jobTitle");
                                    String jobDesc = Obj.getString("jobDesc");
                                    String businessUnit = Obj.getString("businessUnit");
                                    String jobResponsibility = Obj.getString("jobResponsibility");
                                    String functionalSkills = Obj.getString("functionalSkills");
                                    String technicalSkills = Obj.getString("technicalSkills");
                                    String joiningTime = Obj.getString("joiningTime:");
                                    String jobSalary = Obj.getString("jobSalary");
                                    String jobExperience = Obj.getString("jobExperience");

                                    JobTitle.add(jobTitle);
                                    country = new Model_Careeer( jobID,  jobTitle,  jobDesc,businessUnit,jobResponsibility,functionalSkills,technicalSkills,joiningTime,jobSalary,jobExperience,false,false);
                                    appsList.add(country);

                                }

                                careeer_adpter = new Careeer_Adpter(context, R.layout.current_listview, appsList,sc_form,listView);
                                listView.setAdapter(careeer_adpter);



                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,R.layout.spinner_row, JobTitle);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_post.setAdapter(dataAdapter);


                            } else {
                                Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }


    class ApplyForJob extends AsyncTask<String, String, String>
    {
        String str_status;
        String url;
       String strname,stremail,str_post_name,str_image_path,str_msgmsg,str_source;
        String str_quickly_id,str_address,json_responce;
        public  ApplyForJob( String strname,String stremail, String str_post_name, String str_image_path,String str_msg,String str_source)
        {
            this.strname=strname;
            this.stremail=stremail;
            this.str_post_name=str_post_name;
            this.str_image_path=str_image_path;
            this.str_msgmsg=str_msg;
            this.str_source=str_source;


        }
        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            hud = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f);

            hud.show();


        }

        @Override
        protected String doInBackground(String... params)
        {
            // TODO Auto-generated method stub


            String charset = "UTF-8";

            String url  = "http://mobile.adwallz.co/beta/astaguru/PHPMailer-master/examples/smtp.php?";

            // File uploadFile1 = new File(str_image_path);
            try {
                MultipartUtility multipart = new MultipartUtility(url, charset);
                multipart.addFormField("strname",strname);
                multipart.addFormField("stremail",stremail);
                multipart.addFormField("str_post_name", str_post_name);
                multipart.addFormField("str_msgmsg", str_msgmsg);
                multipart.addFormField("str_source", str_source);
                multipart.addFilePart("imageFile", imageFile);


                System.out.println("json_responce" + strname);
                System.out.println("json_responce" + stremail);
                System.out.println("json_responce" + str_post_name);
                System.out.println("json_responce" + str_msgmsg);
                System.out.println("json_responce" + str_source);
                System.out.println("json_responce" + imageFile);



                json_responce= multipart.finish();



            } catch (IOException ex) {
                System.err.println(ex);
            }

            try {
                if(json_responce!=null)
                {

                    System.out.println("json_responce" + json_responce);

                    JSONObject jobject = new JSONObject(json_responce);
                    //JSONObject str_status_obj =jobject.getJSONObject("STATUS");

                    str_status = jobject.getString("status");



                    if(str_status.equalsIgnoreCase("success"))
                    {
//                        Toast.makeText(context, "Application Submitted Succesfully", Toast.LENGTH_SHORT).show();

                    }

                    else if(str_status.equalsIgnoreCase("error"))
                    {

                    }
                }
                else
                {
//                    Toast.makeText(context, "This may be server issue", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            // TODO Auto-generated method stub
            //pDialog.dismiss();
            hud.dismiss();

            super.onPostExecute(result);

            if(str_status.equalsIgnoreCase("success"))
            {
                Toast.makeText(context, str_status,Toast.LENGTH_SHORT).show();

                listView.setVisibility(View.VISIBLE);
                sc_form.setVisibility(View.GONE);

            }
            else if(str_status.equalsIgnoreCase("error"))
            {

                Toast.makeText(context, str_status, Toast.LENGTH_SHORT).show();

            }
            else
            {
                //Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
            }


        }

    }
}

