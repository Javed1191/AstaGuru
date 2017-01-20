package com.infomanav.astaguru;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;

import services.JWTToken;
import services.RootUtil;
import services.Shared_Preferences_Class;
import services.Utility;


public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    private String employe_code="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        employe_code = Shared_Preferences_Class.readString(getApplicationContext(), Shared_Preferences_Class.EMPLOYEE_CODE, "");
       /* String root = Environment.getExternalStorageDirectory().toString();
        File vcfFile = new File(root, "00001.vcf");*/

       /* final MimeTypeMap mime = MimeTypeMap.getSingleton();
        String tmptype = mime.getMimeTypeFromExtension("vcf");
        *//**//*final File file = new File(Environment.getExternalStorageDirectory().toString()
                + "/contacts.vcf");*//**//*
       // Intent i = new Intent();
        //i.setAction(android.content.Intent.ACTION_VIEW);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(vcfFile));
       // i.setDataAndType(Uri.fromFile(vcfFile), "text/x-vcard");
        i.setType("**//*/*//*");
        startActivity(i);*/

       /* Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
        sendIntent.putExtra("sms_body", "some text");
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/contacts/r.vcf"));
        sendIntent.setType("text/x-vcard");
        startActivity(sendIntent);;*/

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(SplashScreenActivity.this, Before_Login_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

            }
        }, SPLASH_TIME_OUT);

    }


}
