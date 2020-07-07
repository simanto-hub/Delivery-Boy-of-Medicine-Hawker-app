package wrteam.ekart.dboy.helper;

import android.app.Activity;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import wrteam.ekart.dboy.R;

public class ApiConfig {


    public static boolean CheckValidation(String item, boolean isemailvalidation, boolean ismobvalidation) {
        if (item.length () == 0)
            return true;
        else if (isemailvalidation && (! android.util.Patterns.EMAIL_ADDRESS.matcher (item).matches ()))
            return true;
        else return ismobvalidation && (item.length () < 10 || item.length () > 12);
    }

    public static String VolleyErrorMessage(VolleyError error) {
        String message = "";
        try {
            if (error instanceof NetworkError) {
                message = "Cannot connect to Internet...Please check your connection!";
            } else if (error instanceof ServerError) {
                message = "The server could not be found. Please try again after some time!!";
            } else if (error instanceof AuthFailureError) {
                message = "Cannot connect to Internet...Please check your connection!";
            } else if (error instanceof ParseError) {
                message = "Parsing error! Please try again after some time!!";
            } else if (error instanceof TimeoutError) {
                message = "Connection TimeOut! Please check your internet connection.";
            } else
                message = "";
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return message;
    }

    public static void disableButton(final Activity activity, final Button button) {

        button.setBackground (activity.getResources ().getDrawable (R.drawable.disabled_btn));
        button.setTextColor (activity.getResources ().getColor (R.color.black));
        button.setEnabled (false);
        button.postDelayed (new Runnable () {
            @Override
            public void run() {
                button.setBackground (activity.getResources ().getDrawable (R.drawable.bg_button));
                button.setTextColor (activity.getResources ().getColor (R.color.white));
                button.setEnabled (true);
            }
        }, 3000);
    }


    public static void RequestToVolley(final VolleyCallback callback, final Activity activity, final String url, final Map<String, String> params, final boolean isprogress) {
        final ProgressDisplay progressDisplay = new ProgressDisplay (activity);

        if (AppController.isConnected (activity)) {
            if (isprogress)
                progressDisplay.showProgress ();
            StringRequest stringRequest = new StringRequest (Request.Method.POST, url, new Response.Listener<String> () {
                @Override
                public void onResponse(String response) {
//                    System.out.println("================= " + url + " == " + response);
                    callback.onSuccess (true, response);
                    if (isprogress)
                        progressDisplay.hideProgress ();
                }

            },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (isprogress)
                                progressDisplay.hideProgress ();
                            Toast.makeText (activity, error.toString (), Toast.LENGTH_LONG).show ();

                            callback.onSuccess (false, "");
                            String message = VolleyErrorMessage (error);
                            if (! message.equals (""))
                                Toast.makeText (activity, message, Toast.LENGTH_SHORT).show ();
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params1 = new HashMap<String, String> ();
                    params1.put ("Authorization", "Bearer " + createJWT ("eKart", "eKart Authentication"));
                    return params1;
                }


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    params.put (Constant.AccessKey, Constant.AccessKeyVal);
                    return params;
                }
            };
            stringRequest.setRetryPolicy (new DefaultRetryPolicy (0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance ().getRequestQueue ().getCache ().clear ();
            AppController.getInstance ().addToRequestQueue (stringRequest);
        }

    }


    public static String createJWT(String issuer, String subject) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis ();
            Date now = new Date (nowMillis);
            byte[] apiKeySecretBytes = Constant.JWT_KEY.getBytes ();
            Key signingKey = new SecretKeySpec (apiKeySecretBytes, signatureAlgorithm.getJcaName ());
            JwtBuilder builder = Jwts.builder ()
                    .setIssuedAt (now)
                    .setSubject (subject)
                    .setIssuer (issuer)
                    .signWith (signatureAlgorithm, signingKey);

            return builder.compact ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return null;
    }

}
