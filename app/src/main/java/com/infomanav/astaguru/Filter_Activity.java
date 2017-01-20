package com.infomanav.astaguru;

/**
 * Created by android-javed on 18-10-2016.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.AdapterView.OnItemClickListener;
import adapter.CustomExpandableListAdapter;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;
import views.OnTaskCompleted;

import static android.R.attr.data;

public class Filter_Activity extends AppCompatActivity implements OnItemClickListener{
    private Utility utility;
    ListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList<Upcoming_Model> appsList;
    MyCustomAdapter dataAdapter = null;
    List<String> list_artist;
    List<String> list_category;
    FragmentCurrentAuction currentAuction;
    Button btn_clear;
    ListView listView;
    ArrayList<Country> countryList;
    private String strPastAuctionUrl ="http://54.169.222.181/api/v2/guru/_table/artistincurrentauction?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";
    Country country;
    Context context;
    String artistid, ArtistName, Date, DollarRate, image, auctiondate, auctiontitle;
    public OnTaskCompleted taskCompleted;
    SessionData sessionData;
    String filter_type,str_fillter_checked_id="";
    public String[] separated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_activity);
        context = Filter_Activity.this;
        utility = new Utility(context);
        currentAuction = new FragmentCurrentAuction();

        sessionData = new SessionData(context);

        Intent intent  = getIntent();

        filter_type = intent.getStringExtra("filter");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);


        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Filter Artist");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        getUpcomingAuction();
        checkButtonClick();


    }


    private void getUpcomingAuction() {

        if (utility.checkInternet()) {


            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);


                    str_fillter_checked_id = sessionData.getString("fillter_id_list");

                    if(str_fillter_checked_id!=null)
                    {
                        String CurrentString = "Fruit: they taste good";
                        separated = str_fillter_checked_id.split(",");

                        System.out.println(separated);

                        /*separated[0]; // this will contain "Fruit"
                        separated[1];*/
                    }

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status, str_msg;

                    appsList = new ArrayList<>();
                    list_artist = new ArrayList<String>();
                    list_category = new ArrayList<String>();

                     countryList = new ArrayList<Country>();
                    try {
                        if (str_json != null) {
                            JSONObject jobject = new JSONObject(str_json);
                            if (jobject.length() > 0)
                            {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");

                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject Obj = jsonArray.getJSONObject(i);

                                    ArtistName = Obj.getString("FirstName");
                                    auctiontitle = Obj.getString("LastName");
                                    artistid = Obj.getString("artistid");

                                    if(separated!=null)
                                    {
                                        if(artistid.equalsIgnoreCase(separated[i]))
                                        {
                                            country = new Country(artistid,ArtistName,auctiontitle,true);
                                            countryList.add(country);
                                        }
                                        else
                                        {
                                            country = new Country(artistid,ArtistName,auctiontitle,false);
                                            countryList.add(country);
                                        }
                                    }
                                    else
                                    {
                                        country = new Country(artistid,ArtistName,auctiontitle,false);
                                        countryList.add(country);
                                    }


                                }
                                dataAdapter = new MyCustomAdapter(getBaseContext(),
                                        R.layout.filter_single, countryList);
                                 listView = (ListView) findViewById(R.id.expandableListView);
                                // Assign adapter to ListView
                                listView.setAdapter(dataAdapter);


                            }
                            else
                            {
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
    private void getUpcomingAuction2() {

        if (utility.checkInternet()) {


            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strPastAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);

                    String str_json = result;
                    String str_status, str_msg;

                    appsList = new ArrayList<>();
                    list_artist = new ArrayList<String>();
                    list_category = new ArrayList<String>();

                    countryList = new ArrayList<Country>();
                    try {
                        if (str_json != null) {
                            JSONObject jobject = new JSONObject(str_json);
                            if (jobject.length() > 0) {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jobject.getJSONArray("resource");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject Obj = jsonArray.getJSONObject(i);

                                    ArtistName = Obj.getString("FirstName");
                                    auctiontitle = Obj.getString("LastName");
                                    artistid = Obj.getString("artistid");
                                    country = new Country(artistid,ArtistName,auctiontitle,false);
                                    countryList.add(country);


                                }
                                dataAdapter = new MyCustomAdapter(getBaseContext(),
                                        R.layout.filter_single, countryList);
                                listView = (ListView) findViewById(R.id.expandableListView);
                                // Assign adapter to ListView
                                listView.setAdapter(dataAdapter);





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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Country country = (Country) parent.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(),
                "Clicked on Row: " + country.getName(),
                Toast.LENGTH_LONG).show();

    }


    private class MyCustomAdapter extends ArrayAdapter<Country> {

        private ArrayList<Country> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Country> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Country>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.filter_single, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.cb_check);
                convertView.setTag(holder);


                if(country.isSelected())
                {
                    holder.name.setChecked(true);
                }
                else
                {
                    holder.name.setChecked(false);
                }

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Country country = (Country) cb.getTag();
//                        Toast.makeText(getApplicationContext(),
//                                "Clicked on Checkbox: " + cb.getText() +
//                                        " is " + cb.isChecked(),
//                                Toast.LENGTH_LONG).show();
                        country.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Country country = countryList.get(position);
            holder.code.setText(  country.getName());
            holder.name.setText(country.getCode());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.btn_refine);
         btn_clear = (Button) findViewById(R.id.btn_clear);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                StringBuffer strBuffreFilletrId = new StringBuffer();
//                responseText.append("The following were selected...\n");

                ArrayList<Country> countryList = dataAdapter.countryList;

                for(int i=0;i<countryList.size();i++)
                {
                    Country country = countryList.get(i);

                    if(country.isSelected())
                    {

                        strBuffreFilletrId.append(country.getArtistid()+",");


                        responseText.append("("+"artistid="+ country.getArtistid()+")"+"or");

                    }
                    else
                    {
                        strBuffreFilletrId.append("-1"+",");
                    }

                }
                String data = responseText.toString();
                str_fillter_checked_id = strBuffreFilletrId.toString();
                sessionData.setString("fillter_id_list",str_fillter_checked_id);
                if (data.length()>0)
                {

                    if(filter_type.equals("Current"))
                    {
                        Intent intent=new Intent();
                        intent.putExtra("artistid",data);
                        setResult(2,intent);
                        finish();

                        sessionData.setObjectAsString("Filter_Data","yes");
                        //finish();

                    }
                    else if(filter_type.equals("past_sub"))
                    {
                        Intent intent=new Intent();
                        intent.putExtra("artistid",data);
                        setResult(3,intent);
                       finish();
                    }


                }
                else {
                    Toast.makeText(context, "Pls Select Artist", Toast.LENGTH_SHORT).show();
                }



            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
                int itemCount = listView.getCount();
                System.out.println("itemCount" + itemCount);
//                for(int i=itemCount-1; i >= 0; i--){
//                    if(checkedItemPositions.get(i)){
//
//                        System.out.println("data" + checkedItemPositions.get(i));
//                        dataAdapter.remove(countryList.get(i));
//                    }
//                }
                CheckBox cb;

                for(int i=0; i<listView.getChildCount();i++)
                {
                    cb = (CheckBox)listView.getChildAt(i).findViewById(R.id.cb_check);
                    cb.setChecked(false);
                    cb.refreshDrawableState();
                }
                 getUpcomingAuction2();
                dataAdapter.notifyDataSetChanged();


                Toast.makeText(getApplicationContext(),"Checkbox Cleared ", Toast.LENGTH_LONG).show();

            }
        });

    }

}
