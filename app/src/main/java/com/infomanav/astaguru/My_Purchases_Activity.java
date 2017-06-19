package com.infomanav.astaguru;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.MyPurchasesAdpter;
import model_classes.MyPurchases_Model;
import services.Application_Constants;
import services.ServiceHandler;
import services.Utility;

/**
 * Created by android-javed on 03-10-2016.
 */

public class My_Purchases_Activity extends AppCompatActivity {

    ArrayList<MyPurchases_Model> appsList;
    String[] mydateList = new String[]{"July, 2016", "September, 2016", "September, 2016", "September, 2016", "December, 2016"};

    private String strPastAuctionUrl = Application_Constants.Main_URL+"AuctionList?api_key="+ Application_Constants.API_KEY;
    private Utility utility;
    private GridView gridview;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypurchases);
        context = My_Purchases_Activity.this;




        utility = new Utility(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("My Purchases");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        gridview = (GridView) findViewById(R.id.gridview);

		/*appsList = new ArrayList<>();
		for(int i=0;i<mydateList.length;i++)
		{
			UpcomingAuction country = new UpcomingAuction(mydateList[i],"");
			appsList.add(country);

		}

		gridview.setAdapter(new UpcomimgAuctionAdapter(getActivity(),appsList));*/
        getUpcomingAuction();

    }


    private void getUpcomingAuction()
    {

        if(utility.checkInternet())
        {
            //final ArrayList<Department> feedsArrayList = new ArrayList<Department>();

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
                            //str_status = jobject.getString("status");
                            //str_msg = jobject.getString("msg");
                            if (jobject.length()>0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");

                                for(int i=0; i<jsonArray.length();i++)
                                {
                                    JSONObject Obj = jsonArray.getJSONObject(i);


                                    Category_name = Obj.getString("Auctionname");
                                    image = Obj.getString("image");


                                    MyPurchases_Model country = new MyPurchases_Model(Category_name,image);
                                    appsList.add(country);

                                }
                               /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list_name);
                                mAutoCompleteTextView.setAdapter(adapter);*/

                                gridview.setAdapter(new MyPurchasesAdpter(context,appsList));


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
}

