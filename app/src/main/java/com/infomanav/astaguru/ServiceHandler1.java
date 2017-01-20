package com.infomanav.astaguru;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import services.ServiceHandler;

/**
 * Created by USER on 21-01-2016.
 */
public class ServiceHandler1
{

    Context context;
    ProgressDialog progressDialog;
    String strResponse;
    Activity activity;
    private ProgressDialog  hud;
    public ServiceHandler1(Context context)
    {
        this.context = context;
    }



    public void registerUser(Map<String,String> paramets,String web_url, final VolleyCallback callback)
    {

        hud = new ProgressDialog(context);

        hud.show();


        final  Map<String,String> params = paramets;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, web_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        hud.dismiss();

                        //Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        strResponse = response;
                        callback.onSuccess(strResponse);

                    }


                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        hud.dismiss();
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                    }
                })


        {
            @Override
            protected Map<String,String> getParams()
            {

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

       /* stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
    }



    public void upoadWallWithImageName(JSONObject requestParam,String URL, final ServiceHandler.VolleyCallback callback)    {

        //progress dialog
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Uploadig data ... ");
        pDialog.setCancelable(false);
        pDialog.show();

        //url
//        String URL = "http://54.169.222.181/api/v2/guru/_table/users/?api_key=c6935db431c0609280823dc52e092388a9a35c5f8793412ff89519e967fd27ed";

        //building params
        final  JSONObject requestParam1 = requestParam;

        //jsonojectrequest with params
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, requestParam1,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            VolleyLog.v("Response:%n %s", response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });

//         AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

    }

    public interface VolleyCallback
    {
        void onSuccess(String result);
    }

}
