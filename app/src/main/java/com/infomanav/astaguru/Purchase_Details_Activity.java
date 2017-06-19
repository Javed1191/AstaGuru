package com.infomanav.astaguru;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
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

public class Purchase_Details_Activity extends AppCompatActivity {

    ArrayList<MyPurchases_Model> appsList;
    String[] mydateList = new String[]{"July, 2016", "September, 2016", "September, 2016", "September, 2016", "December, 2016"};

    private String strPastAuctionUrl = Application_Constants.Main_URL+"AuctionList?api_key="+ Application_Constants.API_KEY;
    private Utility utility;
    private GridView gridview;
    String img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);




        utility = new Utility(getApplicationContext());


        getUpcomingAuction();

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
                    String str_status, str_msg;
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


                                    MyPurchases_Model country = new MyPurchases_Model(Category_name,img);
                                    appsList.add(country);

                                }
                               /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list_name);
                                mAutoCompleteTextView.setAdapter(adapter);*/

                                gridview.setAdapter(new MyPurchasesAdpter(getApplicationContext(),appsList));


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
}

