package com.infomanav.astaguru;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import services.Utility;

/**
 * Created by android-javed on 05-10-2016.
 */

public class Valuation_Activity extends AppCompatActivity
{



    TextView tv_email,tv_call_one,tv_call_two;

    private Utility utility;
    WebView webview_three,webview_one;
    JustifiedTextView tv_valu_one,tv_valu_two,tv_valu_three,tv_valu_four;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valuation);

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);
        init();

        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("Our Valuation");

        tv_email = (TextView)findViewById(R.id.tv_email);
        tv_call_one = (TextView)findViewById(R.id.tv_call_one);
        tv_call_two = (TextView)findViewById(R.id.tv_call_two);

        webview_one = (WebView) findViewById(R.id.webview_one);
        webview_three = (WebView) findViewById(R.id.webview_three);


        webview_one.loadUrl("file:///android_asset/valuation_one.html");
        webview_three.loadUrl("file:///android_asset/valuation_two.html");


        tv_valu_one.setText("Detailed study of pricing history for the artist since 1987");
                tv_valu_two.setText("Price comparison with other works of the artist from similar periods");
                tv_valu_three.setText("Price comparison with works by other contemporary artists");
                tv_valu_four.setText("Research on historical significance of the work");

        tv_call_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (ActivityCompat.checkSelfPermission(Valuation_Activity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+91 22 2204 8138"));
                startActivity(callIntent);


            }
        });


        tv_call_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (ActivityCompat.checkSelfPermission(Valuation_Activity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent callIntent1 = new Intent(Intent.ACTION_CALL);
                callIntent1.setData(Uri.parse("tel:" + "+91 22 2204 8140"));
                startActivity(callIntent1);
            }
        });

        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"siddanth@theartstrust.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "subject");
                email.putExtra(Intent.EXTRA_TEXT, "message");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
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


        tv_valu_one = (JustifiedTextView) findViewById(R.id.tv_valu_one);
        tv_valu_two = (JustifiedTextView) findViewById(R.id.tv_valu_two);
        tv_valu_three = (JustifiedTextView) findViewById(R.id.tv_valu_three);
        tv_valu_four = (JustifiedTextView) findViewById(R.id.tv_valu_four);

    }


}

