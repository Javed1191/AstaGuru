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
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model_classes.MyPurchases_Model;
import services.Application_Constants;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Utility;

/**
 * Created by android-javed on 27-10-2016.
 */

public class Sellers_DetailsActivity extends AppCompatActivity {

    ArrayList<MyPurchases_Model> appsList;
    private int  PICK_IMAGE_REQUEST = 1;
    private String strPastAuctionUrl = Application_Constants.Main_URL+"AuctionList?api_key="+ Application_Constants.API_KEY;
    private Utility utility;
    private GridView gridview;
    Context context;
    TextView tv_img_name;
    private KProgressHUD hud;

  EditText edt_name,edt_email_id,edt_subject,edt_Discription,edt_contact,edt_note;
    private static final int FILE_SELECT_CODE = 0;
    Button btn_select_file,btn_submit;
    public AutoCompleteTextView text;
    String str_image_path= "";
    String selected= "";
    String[] title = {
            "News Paper",
            "Social Media",
            "Social Networking",
            "Friend"
    };

ImageView img_user_pic;
    byte[]  byteArray;
    String encodedImage;
    File imageFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sellers_details);
        context = Sellers_DetailsActivity.this;




        utility = new Utility(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);
        tv_img_name = (TextView)findViewById(R.id.tv_img_name);
        text=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        img_user_pic = (ImageView) findViewById(R.id.img_user_pic);
        edt_name=(EditText) findViewById(R.id.edt_name);
                edt_email_id=(EditText) findViewById(R.id.edt_email_id);
                edt_subject=(EditText) findViewById(R.id.edt_subject);
                edt_Discription=(EditText) findViewById(R.id.edt_Discription);
                edt_contact=(EditText) findViewById(R.id.edt_contact);
                edt_note=(EditText) findViewById(R.id.edt_note);

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
        btn_select_file = (Button) findViewById(R.id.btn_select_file);
        btn_submit= (Button) findViewById(R.id.btn_submit);
        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Seller Details");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        ArrayAdapter adapter = new
                ArrayAdapter(Sellers_DetailsActivity.this,android.R.layout.simple_list_item_1,title);
        text.setAdapter(adapter);
        text.setThreshold(1);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_name,str_email_id,str_subject,str_Discription,str_contact,str_note;

                str_name = edt_name.getText().toString();
                str_email_id = edt_email_id.getText().toString();
                str_subject = edt_subject.getText().toString();
                str_Discription = edt_Discription.getText().toString();
                str_contact = edt_contact.getText().toString();
                str_note = edt_note.getText().toString();



                if(str_name.isEmpty())
                {
                    edt_name.requestFocus();
                    edt_name.setError("Enter Name");
                }
                else if(str_email_id.isEmpty())
                {
                    edt_email_id.requestFocus();
                    edt_email_id.setError("Enter Email");
                }
                else if(!utility.checkEmail(str_email_id))
                {
                    edt_email_id.requestFocus();
                    edt_email_id.setError("Enter Valid Emaild Id");
                }
                else if(str_subject.isEmpty())
                {
                    edt_subject.requestFocus();
                    edt_subject.setError("Enter Number");
                }

                else if(str_Discription.isEmpty())
                {
                    edt_Discription.requestFocus();
                    edt_Discription.setError("Enter Valid Mobile Number");
                }

                else if(str_image_path.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Pls Select File", Toast.LENGTH_SHORT).show();
                }
                else if(str_contact.length()<10)
                {
                    edt_contact.requestFocus();
                    edt_contact.setError("Enter Valid Mobile Number");
                }
                else if(str_note.isEmpty())
                {
                    edt_note.requestFocus();
                    edt_note.setError("Enter Valid Mobile Number");
                }
                else if(selected.equals("select source") ||selected.isEmpty() )
                {
                    Toast.makeText(getApplicationContext(), "Pls Select Source", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    new ApplyForJob(str_name,str_email_id,str_subject,str_Discription,selected,str_image_path,str_contact,str_note).execute();

                }
            }
        });



        btn_select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, PICK_IMAGE_REQUEST);
            }
        });

//        getUpcomingAuction();

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

                str_image_path = getPath(uri);

                File f = new File(str_image_path);

                String imageName = f.getName();

                tv_img_name.setText(imageName);

                System.out.println("imageName" + imageName);
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
                        }

                        catch (Exception e)
                        {
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
    class ApplyForJob extends AsyncTask<String, String, String>
    {
        String str_status;
        String url;
        String str_name,str_email_id,str_subject,str_Discription,selected,str_source,str_image_path,str_contact,str_note;
        String str_quickly_id,str_address,json_responce;
        public  ApplyForJob(String str_name,String str_email_id,String str_subject,String str_Discription,String selected,String str_image_path,String str_contact,String str_note)
        {


            this.str_name=str_name;
                    this.str_email_id=str_email_id;
                    this.str_subject=str_subject;
                    this.str_Discription=str_Discription;
                    this.selected=selected;
                    this.str_image_path=str_image_path;
                    this.str_contact=str_contact;
                    this.str_note=str_note;

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

            String url  = "http://mobile.adwallz.co/beta/astaguru/PHPMailer-master/examples/sell.php?";

            // File uploadFile1 = new File(str_image_path);
            try {
                MultipartUtility multipart = new MultipartUtility(url, charset);
                multipart.addFormField("strname",str_name);
                multipart.addFormField("stremail",str_email_id);
                multipart.addFormField("str_post_name", str_subject);
                multipart.addFormField("str_msgmsg", str_Discription);
                multipart.addFormField("str_source", selected);
                multipart.addFilePart("imageFile", imageFile);
                multipart.addFormField("contactdetail",str_contact);
                multipart.addFormField("note",str_note);

                System.out.println("json_responce" + str_name);
                System.out.println("json_responce" + str_email_id);
                System.out.println("json_responce" + str_subject);
                System.out.println("json_responce" + str_Discription);
                System.out.println("json_responce" + selected);
                System.out.println("json_responce" + imageFile);
                System.out.println("json_responce" + str_contact);
                System.out.println("json_responce" + str_note);




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
                Toast.makeText(context, "Thank you for applying at Astaguru.",Toast.LENGTH_SHORT).show();



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
    private void getUpcomingAuction()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(this);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String image, str_msg;
                    String  Category_name;
                    appsList = new ArrayList<>();
                    try {
                        if (str_json != null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            if (jobject.length()>0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");

                                for(int i=0; i<jsonArray.length();i++)
                                {
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    Category_name = Obj.getString("Auctionname");
                                    image = Obj.getString("image");



                                }



                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

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
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
//    private void showFileChooser() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//        try {
//            startActivityForResult(
//                    Intent.createChooser(intent, "Select a File to Upload"),
//                    FILE_SELECT_CODE);
//        } catch (android.content.ActivityNotFoundException ex) {
//            // Potentially direct the user to the Market with a Dialog
//            Toast.makeText(this, "Please install a File Manager.",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case FILE_SELECT_CODE:
//                if (resultCode == RESULT_OK) {
//                    // Get the Uri of the selected file
//
//                    try {
//                    Uri uri = data.getData();
//                    Log.d("path", "File Uri: " + uri.toString());
//                    // Get the path
//                    String path = getPath(this, uri);
//                    Log.d("path", "File Path: " + path);
//                    // Get the file instance
//                    // File file = new File(path);
//                    // Initiate the upload
//
//
//                    } catch (URISyntaxException e) {
////                        System.out.println("URI " + "test" + " is a malformed URL");
//                    }
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//
//    public static String getPath(Context context, Uri uri) throws URISyntaxException {
//        if ("content".equalsIgnoreCase(uri.getScheme())) {
//            String[] projection = { "_data" };
//            Cursor cursor = null;
//
//            try {
//                cursor = context.getContentResolver().query(uri, projection, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow("_data");
//                if (cursor.moveToFirst()) {
//                    return cursor.getString(column_index);
//                }
//            } catch (Exception e) {
//                // Eat it
//            }
//        }
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//
//        return null;
//    }
}

