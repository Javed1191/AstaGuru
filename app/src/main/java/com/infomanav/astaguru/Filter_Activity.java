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
import android.text.Html;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
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

import model_classes.Country;
import model_classes.Upcoming_Model;
import services.Application_Constants;
import services.ServiceHandler;
import services.SessionData;
import services.Utility;

public class Filter_Activity extends AppCompatActivity implements OnItemClickListener{
    private Utility utility;
    ArrayList<Upcoming_Model> appsList;
    MyCustomAdapter dataAdapter = null;
    List<String> list_artist;
    List<String> list_category;
    FragmentCurrentAuction currentAuction;
    Button btn_clear,btn_refine;
    ListView listView;
    ArrayList<Country> countryList,filterlist;
    private String strCurrentAuctionUrl = Application_Constants.Main_URL+"artistincurrentauction?api_key="+ Application_Constants.API_KEY;
    private String strPastAuctionFilterUrl="";
    Country country;
    Context context;
    String artistid, FirstName, image,  LastName;
    SessionData sessionData;
    String filter_type,str_fillter_checked_id="",Auction_id="",Auctionname="";
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

        if(intent.getExtras()!=null)
        {
            filter_type = intent.getStringExtra("filter");
            Auction_id = intent.getStringExtra("Auction_id");
            Auctionname = intent.getStringExtra("Auctionname");
            filterlist = (ArrayList<Country>) intent.getSerializableExtra("filterlist");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);

        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText(Html.fromHtml(Auctionname));

        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if(filter_type.equalsIgnoreCase("past"))
        {
            if(Auctionname.equalsIgnoreCase("Collectibles Auction"))
            {
                strPastAuctionFilterUrl = Application_Constants.Main_URL_Procedure+"spGetAuctionCategory("+filter_type+","+Auction_id+")?api_key="+Application_Constants.API_KEY;
            }
            else
            {
                strPastAuctionFilterUrl = Application_Constants.Main_URL_Procedure+"spGetArtistincurrentauction("+filter_type+","+Auction_id+")?api_key="+ Application_Constants.API_KEY;
            }

            Log.i("Past Fillter",strPastAuctionFilterUrl);
            checkButtonClick();
            getPastAuctionFillter();
        }
        else if(filter_type.equalsIgnoreCase("upcomming"))
        {
            if(Auctionname.equalsIgnoreCase("Collectibles Auction"))
            {
                strPastAuctionFilterUrl = Application_Constants.Main_URL_Procedure+"spGetAuctionCategory("+filter_type+","+Auction_id+")?api_key="+ Application_Constants.API_KEY;
            }
            else
            {
                strPastAuctionFilterUrl = Application_Constants.Main_URL_Procedure+"spGetArtistincurrentauction("+filter_type+","+Auction_id+")?api_key="+ Application_Constants.API_KEY;
            }
            checkButtonClick();
            getPastAuctionFillter();
        }
        else
        {
            checkButtonClick();
            if(Auctionname.equalsIgnoreCase("Collectibles Auction"))
            {
                strPastAuctionFilterUrl = Application_Constants.Main_URL_Procedure+"spGetAuctionCategory("+filter_type+","+Auction_id+")?api_key="+ Application_Constants.API_KEY;
                getPastAuctionFillter();
            }
            else
            {
                getUpcomingAuction();
            }

        }


       // getUpcomingAuction();



    }



    private void getPastAuctionFillter() {

        if (utility.checkInternet()) {


            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strPastAuctionFilterUrl, new ServiceHandler.VolleyCallback() {
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
                            JSONArray jsonArray = new JSONArray(str_json);
                            if (jsonArray.length() > 0)
                            {

                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject Obj = jsonArray.getJSONObject(i);

                                    FirstName = Obj.getString("FirstName");
                                    LastName = Obj.getString("LastName");
                                    artistid = Obj.getString("artistid");
                                    String artist_name = FirstName + " " + LastName;



                                    if(filterlist.size()>0)
                                    {
                                        if(filterlist.get(i).isSelected())
                                        {
                                            country = new Country(artistid,artist_name,LastName,true);
                                            countryList.add(country);
                                        }
                                        else
                                        {
                                            country = new Country(artistid,artist_name,LastName,false);
                                            countryList.add(country);
                                        }
                                    }
                                    else {
                                        country = new Country(artistid,artist_name,LastName,false);
                                        countryList.add(country);
                                    }


                                   /* if(separated!=null)
                                    {
                                        if(artistid.equalsIgnoreCase(separated[i]))
                                        {
                                            country = new Country(artistid,artist_name,LastName,true);
                                            countryList.add(country);
                                        }
                                        else
                                        {
                                            country = new Country(artistid,artist_name,LastName,false);
                                            countryList.add(country);
                                        }
                                    }
                                    else
                                    {
                                        country = new Country(artistid,artist_name,LastName,false);
                                        countryList.add(country);
                                    }
*/

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

    private void getUpcomingAuction() {

        if (utility.checkInternet()) {


            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(context);


            serviceHandler.registerUser(params, strCurrentAuctionUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("str_get_category_url Json responce" + result);


                   /* str_fillter_checked_id = sessionData.getString("fillter_id_list");

                    if(str_fillter_checked_id!=null)
                    {
                        String CurrentString = "Fruit: they taste good";
                        separated = str_fillter_checked_id.split(",");

                        System.out.println(separated);

                        *//*separated[0]; // this will contain "Fruit"
                        separated[1];*//*
                    }*/

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

                                    FirstName = Obj.getString("FirstName");
                                    LastName = Obj.getString("LastName");
                                    artistid = Obj.getString("artistid");
                                    String artist_name = FirstName + " " + LastName;


                                    if(filterlist.size()>0)
                                    {
                                        if(filterlist.get(i).isSelected())
                                        {
                                            country = new Country(artistid,artist_name,LastName,true);
                                            countryList.add(country);
                                        }
                                        else
                                        {
                                            country = new Country(artistid,artist_name,LastName,false);
                                            countryList.add(country);
                                        }
                                    }
                                    else {
                                        country = new Country(artistid,artist_name,LastName,false);
                                        countryList.add(country);
                                    }

                                   /* if(separated!=null)
                                    {
                                        if(artistid.equalsIgnoreCase(separated[i]))
                                        {
                                            country = new Country(artistid,artist_name,LastName,true);
                                            countryList.add(country);
                                        }
                                        else
                                        {
                                            country = new Country(artistid,artist_name,LastName,false);
                                            countryList.add(country);
                                        }
                                    }
                                    else
                                    {
                                        country = new Country(artistid,artist_name,LastName,false);
                                        countryList.add(country);
                                    }*/


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


            serviceHandler.registerUser(params, strCurrentAuctionUrl, new ServiceHandler.VolleyCallback() {
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

                                    FirstName = Obj.getString("FirstName");
                                    LastName = Obj.getString("LastName");
                                    artistid = Obj.getString("artistid");
                                    String artist_name = FirstName + " " + LastName;
                                    country = new Country(artistid,artist_name,LastName,false);
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
       /* Toast.makeText(getApplicationContext(),
                "Clicked on Row: " + country.getName(),
                Toast.LENGTH_LONG).show();*/

    }


    private class MyCustomAdapter extends ArrayAdapter<Country> {

        private ArrayList<Country> countryList;

        private MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Country> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Country>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView chk_text;
            CheckBox cb_check;

        }
        private void clearList()
        {
            for(int i=0; i<countryList.size();i++)
            {
                Country country = countryList.get(i);
                country.setSelected(false);
            }
            notifyDataSetChanged();

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
                holder.chk_text = (TextView) convertView.findViewById(R.id.chk_text);
                holder.cb_check = (CheckBox) convertView.findViewById(R.id.cb_check);

                convertView.setTag(holder);


                
                if(country.isSelected())
                {
                    holder.cb_check.setChecked(true);
                }
                else
                {
                    holder.cb_check.setChecked(false);
                }

                holder.cb_check.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Country country = (Country) cb.getTag();

                        country.setSelected(cb.isChecked());
                    }
                });

                holder.chk_text.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Country country = (Country) cb.getTag();

                        country.setSelected(cb.isChecked());
                    }
                });



            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Country country = countryList.get(position);
            //holder.chk_text.setText(  country.getName());
            holder.cb_check.setText(country.getartist_name());
            holder.cb_check.setChecked(country.isSelected());
            holder.cb_check.setTag(country);

            return convertView;

        }

    }

    private void checkButtonClick()
    {


        btn_refine = (Button) findViewById(R.id.btn_refine);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_refine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


               // Toast.makeText(Filter_Activity.this,"Test",Toast.LENGTH_SHORT).show();

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

                        if(!filter_type.equalsIgnoreCase("Current"))
                        {
                            responseText.append(country.getArtistid()+"K");
                        }
                        else
                        {
                            if(Auctionname.equalsIgnoreCase("Collectibles Auction"))
                            {
                                responseText.append("("+"categoryid="+ country.getArtistid()+")"+"or");
                                //responseText.append("("+"categoryid="+ country.getArtistid()+")");
                            }
                            else {
                                responseText.append("("+"artistid="+ country.getArtistid()+")"+"or");
                                //responseText.append("("+"artistid="+ country.getArtistid()+")");
                            }
                            // categoryid
                        }

                        //responseText.append("("+"artistid="+ country.getArtistid()+")"+"or");



                    }
                    else
                    {
                        strBuffreFilletrId.append("-1"+",");
                    }

                }




                String data = responseText.toString();


                if(!filter_type.equalsIgnoreCase("Current"))
                {
                    data = trimString(data,1);
                }
                else
                {
                    data = trimString(data,2);
                }
                str_fillter_checked_id = strBuffreFilletrId.toString();
                sessionData.setString("fillter_id_list",str_fillter_checked_id);
                if (data.length()>0)
                {

                    if(filter_type.equals("Current"))
                    {
                        Intent intent=new Intent();
                        intent.putExtra("artistid",data);
                        intent.putExtra("filterlist",countryList);
                        setResult(2,intent);
                        finish();

                        sessionData.setObjectAsString("Filter_Data","yes");
                        //finish();

                    }
                    else if(filter_type.equals("past"))
                    {
                        Intent intent=new Intent();
                        intent.putExtra("artistid",data);
                        intent.putExtra("filterlist",countryList);
                        setResult(3,intent);
                         finish();
                    }
                    else if(filter_type.equals("upcomming"))
                    {
                        Intent intent=new Intent();
                        intent.putExtra("artistid",data);
                        intent.putExtra("filterlist",countryList);
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

              /*  SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
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
                    Country country = countryList.get(i);
                    country.setSelected(false);

                   *//* cb = (CheckBox)listView.getChildAt(i).findViewById(R.id.cb_check);
                    cb.setChecked(false);
                    cb.refreshDrawableState();*//*
                }

               *//* if(filter_type.equalsIgnoreCase("past"))
                {
                   getPastAuctionFillter();
                }

                 getUpcomingAuction2();*/
                dataAdapter.clearList();


                //Toast.makeText(getApplicationContext(),"Checkbox Cleared ", Toast.LENGTH_LONG).show();

            }
        });

    }

    public String trimString(String str, int fromChar)
    {
        if (str != null && str.length() > 0) {
            return str.substring(0,str.length()-fromChar);
        }
        return str;
    }

}
