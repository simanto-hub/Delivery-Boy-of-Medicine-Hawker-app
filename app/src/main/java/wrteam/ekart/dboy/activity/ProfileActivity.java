package wrteam.ekart.dboy.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wrteam.ekart.dboy.R;
import wrteam.ekart.dboy.helper.ApiConfig;
import wrteam.ekart.dboy.helper.AppController;
import wrteam.ekart.dboy.helper.Constant;
import wrteam.ekart.dboy.helper.Session;
import wrteam.ekart.dboy.helper.VolleyCallback;

import static wrteam.ekart.dboy.helper.ApiConfig.disableSwipe;

public class ProfileActivity extends AppCompatActivity {

    EditText edtname, edtaddress;
    TextView tvMobile;
    ImageView imglogout;
    Button btnsubmit;
    Session session;
    Toolbar toolbar;
    Activity activity;
    SwipeRefreshLayout lyt_profile_activity_swipe_refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile);

        toolbar = findViewById (R.id.toolbar);
        edtname = findViewById (R.id.edtname);
        tvMobile = findViewById (R.id.tvMobile);
        edtaddress = findViewById (R.id.edtaddress);
        imglogout = findViewById (R.id.imglogout);
        btnsubmit = findViewById (R.id.btnsubmit);
        lyt_profile_activity_swipe_refresh = findViewById (R.id.lyt_profile_activity_swipe_refresh);
        activity = ProfileActivity.this;
        session = new Session (activity);

        setSupportActionBar (toolbar);
        getSupportActionBar ().setTitle (getResources ().getString (R.string.profile));
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

        edtname.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_profile, 0, 0, 0);
        tvMobile.setCompoundDrawablesWithIntrinsicBounds (0, 0, 0, 0);
        edtaddress.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_edt_home, 0, 0, 0);

        lyt_profile_activity_swipe_refresh.setColorSchemeResources (R.color.colorPrimary);
        lyt_profile_activity_swipe_refresh.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh() {
                if (AppController.isConnected (activity)) {
                    edtname.setText (session.getData (Constant.NAME));
                    tvMobile.setText (session.getData (Constant.MOBILE));
                    edtaddress.setText (session.getData (Constant.ADDRESS));
                    disableSwipe (lyt_profile_activity_swipe_refresh);

                } else {
                    setSnackBar (activity, getString (R.string.no_internet_message), getString (R.string.retry), Color.RED);
                }
                lyt_profile_activity_swipe_refresh.setRefreshing (false);
            }
        });

        edtname.setText (session.getData (Constant.NAME));
        tvMobile.setText (session.getData (Constant.MOBILE));
        edtaddress.setText (session.getData (Constant.ADDRESS));

    }

    public void updateUserData() {
        Map<String, String> params = new HashMap<String, String> ();
        params.put (Constant.ID, session.getData (Constant.ID));
        params.put (Constant.NAME, edtname.getText ().toString ().trim ());
        params.put (Constant.ADDRESS, edtaddress.getText ().toString ().trim ());
        params.put (Constant.UPDATE_DELIVERY_BOY_PROFILE, Constant.GetVal);

        ApiConfig.RequestToVolley (new VolleyCallback () {
            @RequiresApi (api = Build.VERSION_CODES.M)
            @SuppressLint ("SetTextI18n")
            @Override
            public void onSuccess(boolean result, String response) {
                if (result) {
                    try {
                        JSONObject jsonObject = new JSONObject (response);
                        if (! jsonObject.getBoolean (Constant.ERROR)) {
                            setSnackBar (activity, jsonObject.getString (Constant.MESSAGE), getString (R.string.ok), Color.GREEN);
                            getDeliveryBoyData (activity);

                        } else {

                            setSnackBar (activity, jsonObject.getString (Constant.MESSAGE), getString (R.string.ok), Color.RED);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace ();

                    }
                }
            }
        }, activity, Constant.MAIN_URL, params, false);
    }

    public void OnClick(View view) {

        if (AppController.isConnected (activity)) {
            int id = view.getId ();

            if (id == R.id.imglogout) {
                session.logoutUserConfirmation (activity);
            } else if (id == R.id.tvChangePassword) {
                startActivity (new Intent (activity, LoginActivity.class).putExtra (Constant.FROM, "lyt_update_password"));
            } else if (id == R.id.btnsubmit) {

                String name, address;

                address = edtaddress.getText ().toString ();
                name = edtname.getText ().toString ();

                if (ApiConfig.CheckValidation (name, false, false)) {
                    edtname.setError (getString (R.string.name_required));
                } else if (ApiConfig.CheckValidation (address, false, false)) {
                    edtaddress.setError (getString (R.string.address_required));
                } else {
                    updateUserData ();
                    ApiConfig.disableButton (activity, btnsubmit);
                }
            }
        } else {
            setSnackBar (activity, getString (R.string.no_internet_message), getString (R.string.retry), Color.RED);
        }

    }


    public void getDeliveryBoyData(final Activity activity) {
        if (AppController.isConnected (activity)) {

            Map<String, String> params = new HashMap<String, String> ();
            params.put (Constant.ID, session.getData (Constant.ID));
            params.put (Constant.GET_DELIVERY_BOY_BY_ID, Constant.GetVal);

            ApiConfig.RequestToVolley (new VolleyCallback () {
                @Override
                public void onSuccess(boolean result, String response) {
                    //  System.out.println("============" + response);
                    if (result) {
                        try {
                            JSONObject jsonObject = new JSONObject (response);
                            if (! jsonObject.getBoolean (Constant.ERROR)) {
                                StartMainActivity (activity, jsonObject.getJSONArray (Constant.DATA).getJSONObject (0));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }
                    }
                }
            }, activity, Constant.MAIN_URL, params, true);
        } else {
            setSnackBar (activity, getString (R.string.no_internet_message), getString (R.string.retry), Color.RED);
        }
    }

    @SuppressLint ("SetTextI18n")
    public void StartMainActivity(Activity activity, JSONObject jsonObject) {
        if (AppController.isConnected (activity)) {
            try {
                new Session (activity).createUserLoginSession (
                        jsonObject.getString (Constant.FCM_ID),
                        jsonObject.getString (Constant.ID),
                        jsonObject.getString (Constant.NAME),
                        jsonObject.getString (Constant.MOBILE),
                        jsonObject.getString (Constant.PASSWORD),
                        jsonObject.getString (Constant.ADDRESS),
                        jsonObject.getString (Constant.BONUS),
                        jsonObject.getString (Constant.BALANCE),
                        jsonObject.getString (Constant.STATUS),
                        jsonObject.getString (Constant.CREATED_AT));

                edtname.setText (session.getData (Constant.NAME));
                DrawerActivity.tvName.setText (session.getData (Constant.NAME));
                tvMobile.setText (session.getData (Constant.MOBILE));
                edtaddress.setText (session.getData (Constant.ADDRESS));

            } catch (JSONException e) {
                e.printStackTrace ();
            }
        } else {
            setSnackBar (activity, getString (R.string.no_internet_message), getString (R.string.retry), Color.RED);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed ();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed ();

    }


    public void setSnackBar(final Activity activity, String message, String action, int color) {
        final Snackbar snackbar = Snackbar.make (findViewById (android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction (action, new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                snackbar.dismiss ();
            }
        });
        snackbar.setActionTextColor (color);
        View snackbarView = snackbar.getView ();
        TextView textView = snackbarView.findViewById (com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines (5);
        snackbar.show ();
    }
}