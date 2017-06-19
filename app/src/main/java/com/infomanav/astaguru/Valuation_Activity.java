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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import services.Utility;

/**
 * Created by android-javed on 05-10-2016.
 */
public class Valuation_Activity extends AppCompatActivity {
    TextView tv_email, tv_call_one, tv_call_two;
    private Utility utility;
    WebView webview_three, webview_one;
    JustifiedTextView tv_valu_one, tv_valu_two, tv_valu_three, tv_valu_four;
    private static final int REQUEST_PERMISSIONS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_call_one = (TextView) findViewById(R.id.tv_call_one);
        tv_call_two = (TextView) findViewById(R.id.tv_call_two);
        webview_one = (WebView) findViewById(R.id.webview_one);
        webview_three = (WebView) findViewById(R.id.webview_three);
        webview_one.loadUrl("file:///android_asset/valuation_one.html");
        webview_three.loadUrl("file:///android_asset/valuation_two.html");
        tv_valu_one.setText("Detailed study of pricing history for the artist since 1987");
        tv_valu_two.setText("Price comparison with other works of the artist from similar periods");
        tv_valu_three.setText("Price comparison with works by other contemporary artists");
        tv_valu_four.setText("Research on historical significance of the work");
        if (ContextCompat.checkSelfPermission(Valuation_Activity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (Valuation_Activity.this, Manifest.permission.CALL_PHONE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (Valuation_Activity.this, Manifest.permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(Valuation_Activity.this,
                        new String[]{Manifest.permission
                                .CALL_PHONE},
                        REQUEST_PERMISSIONS);
            }
        }
        tv_call_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+91 22 2204 8138"));
                if (ActivityCompat.checkSelfPermission(Valuation_Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });
        tv_call_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent1 = new Intent(Intent.ACTION_CALL);
                callIntent1.setData(Uri.parse("tel:" + "+91 22 2204 8140"));
                if (ActivityCompat.checkSelfPermission(Valuation_Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent1);
            }
        });

        tv_email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               /* Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"siddanth@theartstrust.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from astaguru app");
                email.putExtra(Intent.EXTRA_TEXT, "Hello,"+"Siddanth Shetty");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));*/


                Intent intent = new Intent(Intent.ACTION_SEND);

                String[] strTo = {tv_email.getText().toString()};

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
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

