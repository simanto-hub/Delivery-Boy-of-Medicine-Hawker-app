package wrteam.ekart.dboy.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wrteam.ekart.dboy.R;
import wrteam.ekart.dboy.adapter.WalletHistoryAdapter;
import wrteam.ekart.dboy.helper.ApiConfig;
import wrteam.ekart.dboy.helper.AppController;
import wrteam.ekart.dboy.helper.Constant;
import wrteam.ekart.dboy.helper.Session;
import wrteam.ekart.dboy.helper.VolleyCallback;
import wrteam.ekart.dboy.model.WalletHistory;

public class WalletHistoryActivity extends AppCompatActivity {


    public Session session;

    ArrayList<WalletHistory> walletHistories;
    WalletHistoryAdapter walletHistoryAdapter;

    RecyclerView recyclerViewWalletHistory;
    Toolbar toolbar;
    Activity activity;
    SwipeRefreshLayout lyt_wallet_history_activity_swipe_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_wallet_history);


        activity = WalletHistoryActivity.this;
        session = new Session (activity);
        toolbar = findViewById (R.id.toolbar);
        lyt_wallet_history_activity_swipe_refresh = findViewById (R.id.lyt_wallet_history_activity_swipe_refresh);


        recyclerViewWalletHistory = findViewById (R.id.recyclerViewWalletHistory);
        recyclerViewWalletHistory.setLayoutManager (new LinearLayoutManager (activity));

        setSupportActionBar (toolbar);
        getSupportActionBar ().setTitle (R.string.wallet_history);
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

        if (AppController.isConnected (activity)) {
            getWalletHistory (activity);
        } else {
            setSnackBar (activity, getString (R.string.no_internet_message), getString (R.string.retry));
        }

        lyt_wallet_history_activity_swipe_refresh.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {

            @Override
            public void onRefresh() {
                lyt_wallet_history_activity_swipe_refresh.setColorSchemeResources (R.color.colorPrimary);
                if (AppController.isConnected (activity)) {
                    getWalletHistory (activity);
                } else {
                    setSnackBar (activity, getString (R.string.no_internet_message), getString (R.string.retry));
                }
                lyt_wallet_history_activity_swipe_refresh.setRefreshing (false);
            }
        });
    }

    public void getWalletHistory(final Activity activity) {
        if (AppController.isConnected (activity)) {

            Map<String, String> params = new HashMap<String, String> ();
            params.put (Constant.ID, session.getData (Constant.ID));
            params.put (Constant.GET_FUND_TRANSFERS, Constant.GetVal);

            ApiConfig.RequestToVolley (new VolleyCallback () {
                @SuppressLint ("SetTextI18n")
                @Override
                public void onSuccess(boolean result, String response) {
                    if (result) {
                        try {
                            JSONObject jsonObject = new JSONObject (response);

                            if (! jsonObject.getBoolean (Constant.ERROR)) {
                                JSONArray jsonArray = jsonObject.getJSONArray (Constant.DATA);

                                walletHistories = new ArrayList<> ();

                                Gson g = new Gson ();

                                for (int i = 0; i < jsonArray.length (); i++) {


                                    JSONObject jsonObject1 = jsonArray.getJSONObject (i);

                                    WalletHistory walletHistory = g.fromJson (jsonObject1.toString (), WalletHistory.class);
                                    walletHistories.add (walletHistory);

                                }

                                walletHistoryAdapter = new WalletHistoryAdapter (walletHistories, activity);
                                recyclerViewWalletHistory.setAdapter (walletHistoryAdapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }
                    }
                }
            }, activity, Constant.MAIN_URL, params, true);

        } else {
            setSnackBar (activity, getString (R.string.no_internet_message), getString (R.string.retry));
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

    public void setSnackBar(final Activity activity, String message, String action) {
        final Snackbar snackbar = Snackbar.make (activity.findViewById (android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction (action, new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                getWalletHistory (activity);
                snackbar.dismiss ();
            }
        });
        snackbar.setActionTextColor (Color.RED);
        View snackbarView = snackbar.getView ();
        TextView textView = snackbarView.findViewById (com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines (5);
        snackbar.show ();
    }
}