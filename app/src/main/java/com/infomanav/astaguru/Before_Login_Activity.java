package com.infomanav.astaguru;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.ButterKnife;
import services.Utility;

/**
 * Created by android-javed on 03-10-2016.
 */

public class Before_Login_Activity extends AppCompatActivity
{


    private Utility utility;
    private Button btn_signup,btn_signin;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);
        init();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setSupportActionBar(toolbar);
        TextView tool_text = (TextView) toolbar.findViewById(R.id.tool_text);
        tool_text.setText("My AstaGuru");


        Typeface type = Typeface.createFromAsset(getAssets(),"WorkSans-Medium.otf");
        tool_text.setTypeface(type);
        btn_signin.setText("Sign In");
        btn_signin.setTypeface(type);
        btn_signup.setText("Sign Up");
        btn_signup.setTypeface(type);


        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ButterKnife.bind(this);

        btn_signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(Before_Login_Activity.this,LoginActivity.class);
                intent.putExtra("str_from","test");
                startActivity(intent);

            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(Before_Login_Activity.this,RegistrationActivity.class);
                startActivity(intent);
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

        if (id == R.id.action_close)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        finish();
    }

    public void init()
    {
        utility = new Utility(getApplicationContext());
        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_signup = (Button) findViewById(R.id.btn_signup);

    }


}


