package com.infomanav.astaguru;

import android.app.Activity;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import adapter.Careeer_Adpter;
import adapter.CurrentAuctionAdapter_listview;
import butterknife.ButterKnife;
import services.CustomMultiPartEntity;
import services.FilePath;
import services.MultipartUtility;
import services.ServiceHandler;
import services.UploadProgressListener;
import services.Utility;

/**
 * Created by android-javed on 01-10-2016.
 */

public class Careers_Activity extends AppCompatActivity
{


    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String selectedFilePath;
    private String SERVER_URL = "http://mobile.adwallz.co/beta/astaguru/PHPMailer-master/examples/smtp.php?";
    ImageView ivAttachment;
    Button bUpload;
    TextView tvFileName;
    ProgressDialog dialog;


   private int  PICK_IMAGE_REQUEST = 1;

ProgressDialog dialogProfile;
    Model_Careeer country;
    ArrayList<Model_Careeer> appsList;
    ImageView img_user_pic;
    ProgressDialog progressDialog;
    File file_1;
    long imageSize = 0;
    private Button btn_why_astaguru,btn_vacancies;
    ScrollView sc_form,sc_why_asta;
String uploadedFileName;
    WebView wb_whyastaguru;
    Context context;
    Careeer_Adpter careeer_adpter;
StringTokenizer tokens;

    ListView listView;
    Utility utility;
    Button btn_proceed;
    ProgressDialog pDialog;
    Spinner spn_source,spn_post;

    EditText edt_email,edt_first_name,edt_msg;
    String str_post_name,str_source,str_image_path="";
    Button btn_select_file;

    byte[]  byteArray;
    String strname,stremail,str_msg;
    File imageFile,file1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careers);
        context = Careers_Activity.this;
        utility = new Utility(context);
       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

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
        listView = (ListView) findViewById(R.id.listView);

        spn_post = (Spinner) findViewById(R.id.spn_post);
        spn_source = (Spinner) findViewById(R.id.spn_source);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Careers");


        Typeface type = Typeface.createFromAsset(getAssets(), "WorkSans-Medium.otf");
        tool_text.setTypeface(type);

//
        wb_whyastaguru = (WebView) findViewById(R.id.wb_whyastaguru);
        wb_whyastaguru.loadUrl("file:///android_asset/whyastaguru.html");


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("  Client Relation Excecutive");
        categories.add("  Relation Manager");
        categories.add("  Client Relation Excecutive");
        categories.add("  Relation Manager");
        categories.add("  Client Relation Excecutive");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_post.setAdapter(dataAdapter);


        List<String> categories1 = new ArrayList<String>();
        categories1.add("  Select Resource");
        categories1.add("  News Paper");
        categories1.add("  TV Shows");
        categories1.add("  Friends");
        categories1.add("  Facebook");
        categories1.add("  Others");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_source.setAdapter(dataAdapter1);

        spn_source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_source = parent.getItemAtPosition(position).toString();
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
        btn_why_astaguru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sc_why_asta.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);

//                btn_why_astaguru.setBackgroundColor(getResources().getColor(R.color.btn_bg_brown));
//                btn_vacancies.setBackgroundColor(getResources().getColor(R.color.btn_bg_white));


            }
        });
        btn_vacancies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sc_why_asta.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

//                btn_vacancies.setBackgroundColor(getResources().getColor(R.color.btn_bg_brown));
//                btn_why_astaguru.setBackgroundColor(getResources().getColor(R.color.btn_bg_white));


            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 strname = edt_first_name.getText().toString();
               stremail = edt_email.getText().toString();
                str_msg = edt_msg.getText().toString();

                if (strname.isEmpty()) {
                    edt_first_name.requestFocus();
                    edt_first_name.setError("Enter First Name");
                } else if (stremail.isEmpty()) {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Emaild Id");
                } else if (!utility.checkEmail(stremail)) {
                    edt_email.requestFocus();
                    edt_email.setError("Enter Valid Emaild Id");
                } else if (str_post_name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pls Select Post", Toast.LENGTH_SHORT).show();
                } else if (selectedFilePath.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pls Select File", Toast.LENGTH_SHORT).show();
                } else if (str_msg.isEmpty()) {
                    edt_msg.requestFocus();
                    edt_msg.setError("Enter User Name");
                } else if (str_source.equals("Select Resource")) {
                    Toast.makeText(getApplicationContext(), "Pls Select Source", Toast.LENGTH_SHORT).show();
                } else {
                    if (selectedFilePath != null)
                    {
//                        progressDialog = createDialog();
//                        progressDialog.show();
//                        new ChangeProfilePic(strname,stremail,str_post_name,str_image_path,str_msg,str_source).execute();

                        new ImageUploader().execute();

                    }
                    else
                    {
                        Toast.makeText(Careers_Activity.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                    }

//                    new ChangeProfilePic(strname,stremail,str_post_name,str_image_path,str_msg,str_source).execute();
                }


            }
        });

        btn_select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showFileChooser();

                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, PICK_IMAGE_REQUEST);
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

            String strPastAuctionUrl = "http://54.169.222.181/api/v2/guru/_table/jobs?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
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

                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");


                                for (int i = 0; i < jsonArray.length(); i++) {

//                                    JSONObject obj_category_by_categoryid = Obj.getJSONObject("category_by_categoryid");
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    String jobID = Obj.getString("jobID");
                                    String jobTitle = Obj.getString("jobTitle");
                                    String jobDesc = Obj.getString("jobDesc");
                                    String businessUnit = Obj.getString("businessUnit");
                                    String jobResponsibility = Obj.getString("jobResponsibility");
                                    String functionalSkills = Obj.getString("functionalSkills");
                                    String technicalSkills = Obj.getString("technicalSkills");
                                    String joiningTime = Obj.getString("joiningTime:");
                                    String jobSalary = Obj.getString("jobSalary");
                                    String jobExperience = Obj.getString("jobExperience");


                                    country = new Model_Careeer( jobID,  jobTitle,  jobDesc,businessUnit,jobResponsibility,functionalSkills,technicalSkills,joiningTime,jobSalary,jobExperience,false,false);
                                    appsList.add(country);

                                }

                                careeer_adpter = new Careeer_Adpter(context, R.layout.current_listview, appsList,sc_form,listView);
                                listView.setAdapter(careeer_adpter);


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




    private ProgressDialog createDialog(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.. Uploading File");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        return progressDialog;
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i(TAG,"Selected File Path:" + selectedFilePath);

                imageSize = this.getFileSize(selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){
//                    tvFileName.setText(selectedFilePath);
                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if (requestCode == PICK_IMAGE_REQUEST) {
//
//            Uri uri = data.getData();
//
//            str_image_path = getPath(uri);
//
//
//            File filesDir = getFilesDir();
//            imageFile = new File(filesDir, "user_image.jpg");
//
//
//        }
//
//
//    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    private long getFileSize(String imagePath){
        long length = 0;

        try {

            File file = new File(imagePath);
            length = file.length();
            length = length / 1024;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return length;
    }


    private class ImageUploader extends AsyncTask<Void, Integer, Boolean> implements UploadProgressListener
    {
        @Override
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                InputStream inputStream = new FileInputStream(new File(selectedFilePath));
                byte[] data = this.convertToByteArray(inputStream);

                HttpClient httpClient = new DefaultHttpClient();
                httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,System.getProperty("http.agent"));

                HttpPost httpPost = new HttpPost(SERVER_URL);

                StringBody dataString = new StringBody("This is the sample image");

                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data),"test.jpg");

                CustomMultiPartEntity  multipartEntity = new CustomMultiPartEntity();


                multipartEntity.setUploadProgressListener(this);

                multipartEntity.addPart("strname",dataString);
                multipartEntity.addPart("stremail",dataString);
                multipartEntity.addPart("str_post_name",dataString);
                multipartEntity.addPart("str_msg",dataString);
                multipartEntity.addPart("str_source",dataString);
                multipartEntity.addPart("imageFile",inputStreamBody);

                httpPost.setEntity(multipartEntity);

                HttpResponse httpResponse = httpClient.execute(httpPost);

                // THE RESPONSE FROM SERVER
                String stringResponse =  EntityUtils.toString(httpResponse.getEntity());

                // DISPLAY RESPONSE OF THE SERVER
                Log.d("data from server",stringResponse);
                System.out.println("json_responce" + stringResponse);


            }
            catch (FileNotFoundException e1)
            {
                e1.printStackTrace();

                return false;

            }
            catch (ClientProtocolException e)
            {
                e.printStackTrace();

                return false;

            }
            catch (IOException e)
            {
                e.printStackTrace();

                return false;
            }

            return true;
        }

        @Override
        public void transferred(long num)
        {

            // COMPUTE DATA UPLOADED BY PERCENT

            long dataUploaded = ((num / 1024) * 100 ) / imageSize;

            this.publishProgress((int)dataUploaded);

        }

        private byte[] convertToByteArray(InputStream inputStream) throws IOException
        {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int next = inputStream.read();
            while (next > -1)

            {
                bos.write(next);
                next = inputStream.read();
            }

            bos.flush();

            return bos.toByteArray();
        }



        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);

            // UPDATE THE PROGRESS DIALOG

            progressDialog.setProgress(values[0]);



        }

        @Override
        protected void onPostExecute(Boolean uploaded) {
            super.onPostExecute(uploaded);
            if( uploaded)
            {
                progressDialog.dismiss();
                Toast.makeText(Careers_Activity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
            }
            else
            {
                progressDialog.setMessage("Uploading Failed");
                progressDialog.setCancelable(true);
            }

        }

    }
}

