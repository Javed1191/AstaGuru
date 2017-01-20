package services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Map;

/**
 * Created by USER on 21-01-2016.
 */
public class ServiceHandler
{

    Context context;
    String strResponse;
    Activity activity;
    private KProgressHUD hud;
    public ServiceHandler(Context context)
    {
        this.context = context;
    }



    public void registerUser(Map<String,String> paramets,String web_url, final VolleyCallback callback)
    {

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);

        hud.show();

        final  Map<String,String> params = paramets;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, web_url,
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
                        Toast.makeText(context, "Please Check Internet Connection.",Toast.LENGTH_LONG)
                                .show();
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




    public interface VolleyCallback{
        void onSuccess(String result);
    }

}
