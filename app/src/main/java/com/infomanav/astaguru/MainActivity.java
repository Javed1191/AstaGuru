package com.infomanav.astaguru;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;

import adapter.TabAdapter;
import services.SessionData;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,IOperateOnToolbar {

    private Button btn_sign_in;
    private LinearLayout lay_about_us,lay_home,lay_services,lay_privacy_policy,lay_contact_us,lay_careers,lay_how_to_sell,lay_how_to_buy,lay_category,lay_valuation
            ,lay_terms_condition;
    DrawerLayout drawer;
    public DisplayMetrics m;
    TextView toolbarTextView,tv_signin_text;
    ImageView iv_logo;
    Toolbar toolbar;
    SessionData data;
    Context context;
    ActionBarDrawerToggle toggle;
    RelativeLayout notificationCount1;
    public TextView badge_notification_1;
    public RelativeLayout relative_layout_item_count;
    String notification_count="10";
    private String NotificationID="",key="",auction="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        data = new SessionData(context);

        intVeriable();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        iv_logo  = (ImageView) toolbar.findViewById(R.id.iv_logo);
         toolbarTextView  = (TextView) toolbar.findViewById(R.id.tool_text);
        toolbarTextView.setVisibility(View.GONE);


        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        m = getResources().getDisplayMetrics();
        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");

        String value = data.getObjectAsString("login");
        if (value.equalsIgnoreCase("true"))
        {
            btn_sign_in.setText("Sign Out");
            btn_sign_in.setTypeface(type);
            tv_signin_text.setText("Sign out from My AstaGuru");

        }
        else
        {
            btn_sign_in.setText("Sign In");
            btn_sign_in.setTypeface(type);
            tv_signin_text.setText("Sign in to My AstaGuru");
        }

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");

                String value = data.getObjectAsString("login");
                if (value.equalsIgnoreCase("true"))
                {
                    System.out.println("if" + value.equalsIgnoreCase("true"));
                    btn_sign_in.setText("Sign In");
                    btn_sign_in.setTypeface(type);
                    data.setObjectAsString("login","false");
                    data.setString("BillingName","");
                    data.setString("BillingAddress","");
                    data.setString("BillingCity","");
                    data.setString("BillingState","");
                    data.setString("BillingCountry","");
                    data.setString("BillingZip","");
                    data.setString("BillingTelephone","");
                    data.setString("BillingEmail","");

                    data.setString("userid","");
                    data.setString("email","");
                    data.setString("name","");

                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtra("str_from","test");
                    startActivity(intent);
                    finish();

                }
                else
                {
                    System.out.println("else" + value.equalsIgnoreCase("true"));
                    btn_sign_in.setText("Sign In");
                    btn_sign_in.setTypeface(type);
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtra("str_from","test");
                    startActivity(intent);
                    finish();

                }


            }
        });

        Intent intent = getIntent();

        if(intent.getExtras()!=null)
        {
            //intent.putExtra("fragment","current");

            if(intent.getStringExtra("fragment")!=null)
            {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "current");
                FragmentHomeTab fragment = new FragmentHomeTab();
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("HOME TAB");
                fragmentTransaction.commit();
            }
            else if (intent.getStringExtra("NotificationID")!=null)
            {
                NotificationID = intent.getStringExtra("NotificationID");

               /* Bundle bundle = new Bundle();
                bundle.putString("fragment", "home");
                FragmentHomeTab fragment = new FragmentHomeTab();
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("HOME TAB");
                fragmentTransaction.commit();
*/

                Intent intent1 = new Intent(MainActivity.this,NotificationActivity.class);
                intent1.putExtra("NotificationID",NotificationID);
                startActivity(intent1);

                    //  notif_des = intent.getStringExtra("notif_des");
            }
            else if(intent.getStringExtra("type")!=null)
            {

                if(intent.getStringExtra("type").equalsIgnoreCase("search"))
                {

                    key = intent.getStringExtra("key");
                    auction = intent.getStringExtra("auction");

                    Bundle bundle = new Bundle();
                    bundle.putString("type", "search");
                    bundle.putString("fragment", "current");
                    bundle.putString("key", key);
                    bundle.putString("auction", auction);
                    FragmentHomeTab fragment = new FragmentHomeTab();
                    fragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("HOME TAB");
                    fragmentTransaction.commit();


                }

            }
            else
            {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "home");
                FragmentHomeTab fragment = new FragmentHomeTab();
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("HOME TAB");
                fragmentTransaction.commit();
            }
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putString("fragment", "home");
            FragmentHomeTab fragment = new FragmentHomeTab();
            fragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("HOME TAB");
            fragmentTransaction.commit();
        }

        lay_about_us.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,About_UsActivity.class);
                startActivity(intent);


            }
        });
        lay_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (drawer.isDrawerOpen(GravityCompat.START))
                {
                    drawer.closeDrawer(GravityCompat.START);
                }

                toolbar.setTitle("");
                toolbarTextView.setVisibility(View.GONE);
                iv_logo.setVisibility(View.VISIBLE);
                FragmentHomeTab fragment = new FragmentHomeTab();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("HOME TAB");
                fragmentTransaction.commit();
            }
        });
//        btn_sign_in.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(intent);
//            }
//        });
        lay_services.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,ServicesActivity.class);
                startActivity(intent);
            }
        });
        lay_privacy_policy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });
        lay_contact_us.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,Contact_Us_Activity.class);
                startActivity(intent);
            }
        });
        lay_careers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,Careers_Activity.class);
                startActivity(intent);
            }
        });
        lay_how_to_sell.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,How_To_Sell_Acitivity.class);
                startActivity(intent);
            }
        });
        lay_how_to_buy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,How_to_buy_Activity.class);
                startActivity(intent);
            }
        });
        lay_category.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,Categories_Acitivity.class);
                startActivity(intent);
            }
        });
        lay_valuation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,Valuation_Activity.class);
                startActivity(intent);
            }
        });
        lay_terms_condition.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,Terms_Condition_Activity.class);
                startActivity(intent);
            }
        });


    }



    public void intVeriable()
    {
        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
        tv_signin_text = (TextView) findViewById(R.id.tv_signin_text);


        lay_about_us = (LinearLayout) findViewById(R.id.lay_about_us);
        lay_home = (LinearLayout) findViewById(R.id.lay_home);
        lay_services = (LinearLayout) findViewById(R.id.lay_services);
        lay_privacy_policy = (LinearLayout) findViewById(R.id.lay_privacy_policy);
        lay_contact_us = (LinearLayout) findViewById(R.id.lay_contact_us);
        lay_careers = (LinearLayout) findViewById(R.id.lay_careers);
        lay_how_to_sell = (LinearLayout) findViewById(R.id.lay_how_to_sell);
        lay_how_to_buy = (LinearLayout) findViewById(R.id.lay_how_to_buy);
        lay_category = (LinearLayout) findViewById(R.id.lay_category);
        lay_valuation = (LinearLayout) findViewById(R.id.lay_valuation);
        lay_terms_condition = (LinearLayout) findViewById(R.id.lay_terms_condition);
        btn_sign_in.setTransformationMethod(null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request chk_fillter is same as what is passed  here it is 2
        if(requestCode==2)
        {

            if (!(data==null))
            {
                String artistid=data.getStringExtra("artistid");

                Intent intent  = getIntent();
                intent.putExtra("artistid",artistid);

                FragmentHomeTab fragment1 = new FragmentHomeTab();
                android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.frame, fragment1).addToBackStack("HOME TAB");
                fragmentTransaction1.commit();
            }
            else
            {
                FragmentCurrentAuction fragment1 = new FragmentCurrentAuction();
                android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.frame, fragment1).addToBackStack("HOME TAB");
                fragmentTransaction1.commit();
            }
        }
    }
    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ActivityCompat.finishAffinity(MainActivity.this);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


       /* MenuItem item1 = menu.findItem(R.id.action_settings);
        MenuItemCompat.setActionView(item1, R.layout.notification_update_count_layout);
        notificationCount1 = (RelativeLayout) MenuItemCompat.getActionView(item1);
        relative_layout_item_count = (RelativeLayout) notificationCount1.findViewById(R.id.relative_layout_item_count);
        badge_notification_1 = (TextView) notificationCount1.findViewById(R.id.badge_notification_1);

        if(notification_count.equals("")||notification_count.equals(null))
        {
            badge_notification_1.setVisibility(View.GONE);
        }
        else
        {
            // relative_layout_item_count.setVisibility(View.VISIBLE);
            badge_notification_1.setText(notification_count);
        }

        relative_layout_item_count.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String status = data.getObjectAsString("login");

                if (status.equalsIgnoreCase("true"))
                {
                    Intent intent = new Intent(MainActivity.this,MyAstaGuru_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this,Before_Login_Activity.class);
                    startActivity(intent);

                }
            }
        });
*/

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_settings:
               String status = data.getObjectAsString("login");

                if (status.equalsIgnoreCase("true"))
                {
                    Intent intent = new Intent(MainActivity.this,MyAstaGuru_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this,Before_Login_Activity.class);
                    startActivity(intent);

                }

                return true;
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this,Search_Activity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setImageView1Resource(String tab) {
        toolbarTextView.setText(tab);
    }


}
