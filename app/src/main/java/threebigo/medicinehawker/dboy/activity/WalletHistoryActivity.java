package threebigo.medicinehawker.dboy.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
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

import threebigo.medicinehawker.dboy.R;
import threebigo.medicinehawker.dboy.adapter.WalletHistoryAdapter;
import threebigo.medicinehawker.dboy.helper.ApiConfig;
import threebigo.medicinehawker.dboy.helper.AppController;
import threebigo.medicinehawker.dboy.helper.Constant;
import threebigo.medicinehawker.dboy.helper.Session;
import threebigo.medicinehawker.dboy.helper.VolleyCallback;
import threebigo.medicinehawker.dboy.model.WalletHistory;

import static threebigo.medicinehawker.dboy.helper.ApiConfig.disableSwipe;

public class WalletHistoryActivity extends AppCompatActivity {


    public Session session;

    ArrayList<WalletHistory> walletHistories;
    WalletHistoryAdapter walletHistoryAdapter;

    RecyclerView recyclerViewWalletHistory;
    Toolbar toolbar;
    Activity activity;
    SwipeRefreshLayout lyt_wallet_history_activity_swipe_refresh;
    int total = 0;
    private boolean isLoadMore = false;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_wallet_history);


        activity = WalletHistoryActivity.this;
        session = new Session (activity);
        toolbar = findViewById (R.id.toolbar);
        lyt_wallet_history_activity_swipe_refresh = findViewById (R.id.lyt_wallet_history_activity_swipe_refresh);
        scrollView = findViewById (R.id.scrollView);


        recyclerViewWalletHistory = findViewById (R.id.recyclerViewWalletHistory);
        recyclerViewWalletHistory.setLayoutManager (new LinearLayoutManager (activity));

        setSupportActionBar (toolbar);
        getSupportActionBar ().setTitle (R.string.wallet_history);
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

        if (AppController.isConnected (activity)) {
            getWalletHistory (0);
        } else {
            setSnackBar (activity,getString (R.string.no_internet_message),getString (R.string.retry));
        }

        lyt_wallet_history_activity_swipe_refresh.setColorSchemeResources (R.color.colorPrimary);

        lyt_wallet_history_activity_swipe_refresh.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {

            @Override
            public void onRefresh ( ) {
                if (AppController.isConnected (activity)) {
                    session.setData (Constant.OFFSET_WALLET,"" + 0);
                    getWalletHistory (0);
                    lyt_wallet_history_activity_swipe_refresh.setRefreshing (false);
                    disableSwipe (lyt_wallet_history_activity_swipe_refresh);
                } else {
                    setSnackBar (activity,getString (R.string.no_internet_message),getString (R.string.retry));
                }
            }
        });
    }

    private void getWalletHistory ( final int startoffset ) {
        walletHistories = new ArrayList<> ();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager (activity);
        recyclerViewWalletHistory.setLayoutManager (linearLayoutManager);

        Map<String,String> params = new HashMap<String,String> ();
        params.put (Constant.ID,session.getData (Constant.ID));
        params.put (Constant.GET_FUND_TRANSFERS,Constant.GetVal);
        params.put (Constant.OFFSET,session.getData (Constant.OFFSET_WALLET));
        params.put (Constant.LIMIT,Constant.PRODUCT_LOAD_LIMIT);


//        System.out.println("====params " + params.toString());
        ApiConfig.RequestToVolley (new VolleyCallback () {
            @Override
            public void onSuccess ( boolean result,String response ) {
                if (result) {
                    try {
                        //System.out.println("====product  " + response);
                        JSONObject objectbject = new JSONObject (response);
                        if (! objectbject.getBoolean (Constant.ERROR)) {
                            total = Integer.parseInt (objectbject.getString (Constant.TOTAL));
                            session.setData (Constant.TOTAL,String.valueOf (total));

                            JSONObject object = new JSONObject (response);
                            JSONArray jsonArray = object.getJSONArray (Constant.DATA);

                            Gson g = new Gson ();

                            for (int i = 0; i < jsonArray.length (); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject (i);

                                if (jsonObject1 != null) {
                                    WalletHistory notification = g.fromJson (jsonObject1.toString (),WalletHistory.class);
                                    walletHistories.add (notification);
                                } else {
                                    break;
                                }

                            }
                            if (startoffset == 0) {
                                walletHistoryAdapter = new WalletHistoryAdapter (activity,walletHistories);
                                walletHistoryAdapter.setHasStableIds (true);
                                recyclerViewWalletHistory.setAdapter (walletHistoryAdapter);
                                scrollView.setOnScrollChangeListener (new NestedScrollView.OnScrollChangeListener () {
                                    @Override
                                    public void onScrollChange ( NestedScrollView v,int scrollX,int scrollY,int oldScrollX,int oldScrollY ) {

                                        // if (diff == 0) {
                                        if (scrollY == ( v.getChildAt (0).getMeasuredHeight () - v.getMeasuredHeight () )) {
                                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerViewWalletHistory.getLayoutManager ();
                                            if (walletHistories.size () < total) {
                                                if (! isLoadMore) {
                                                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition () == walletHistories.size () - 1) {
                                                        //bottom of list!
                                                        walletHistories.add (null);
                                                        walletHistoryAdapter.notifyItemInserted (walletHistories.size () - 1);
                                                        new Handler ().postDelayed (new Runnable () {
                                                            @Override
                                                            public void run ( ) {

                                                                session.setData (Constant.OFFSET_WALLET,Integer.parseInt (session.getData (Constant.OFFSET_WALLET)) + Constant.LOAD_ITEM_LIMIT);
                                                                Map<String,String> params = new HashMap<> ();
                                                                params.put (Constant.ID,session.getData (Constant.ID));
                                                                params.put (Constant.GET_FUND_TRANSFERS,Constant.GetVal);
                                                                params.put (Constant.OFFSET,session.getData (Constant.OFFSET_WALLET));
                                                                params.put (Constant.LIMIT,Constant.PRODUCT_LOAD_LIMIT);


                                                                ApiConfig.RequestToVolley (new VolleyCallback () {
                                                                    @Override
                                                                    public void onSuccess ( boolean result,String response ) {

                                                                        if (result) {
                                                                            try {
                                                                                // System.out.println("====product  " + response);
                                                                                JSONObject objectbject1 = new JSONObject (response);
                                                                                if (! objectbject1.getBoolean (Constant.ERROR)) {

                                                                                    session.setData (Constant.TOTAL,objectbject1.getString (Constant.TOTAL));

                                                                                    walletHistories.remove (walletHistories.size () - 1);
                                                                                    walletHistoryAdapter.notifyItemRemoved (walletHistories.size ());

                                                                                    JSONObject object = new JSONObject (response);
                                                                                    JSONArray jsonArray = object.getJSONArray (Constant.DATA);

                                                                                    Gson g = new Gson ();


                                                                                    for (int i = 0; i < jsonArray.length (); i++) {
                                                                                        JSONObject jsonObject1 = jsonArray.getJSONObject (i);

                                                                                        if (jsonObject1 != null) {
                                                                                            WalletHistory notification = g.fromJson (jsonObject1.toString (),WalletHistory.class);
                                                                                            walletHistories.add (notification);
                                                                                        } else {
                                                                                            break;
                                                                                        }

                                                                                    }
                                                                                    walletHistoryAdapter.notifyDataSetChanged ();
                                                                                    walletHistoryAdapter.setLoaded ();
                                                                                    isLoadMore = false;
                                                                                }
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace ();
                                                                            }
                                                                        }
                                                                    }
                                                                },activity,Constant.MAIN_URL,params,false);

                                                            }
                                                        },0);
                                                        isLoadMore = true;
                                                    }

                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace ();
                    }
                }
            }
        },activity,Constant.MAIN_URL,params,true);
    }

    @Override
    public boolean onSupportNavigateUp ( ) {
        onBackPressed ();
        return true;
    }

    @Override
    public void onBackPressed ( ) {
        super.onBackPressed ();

    }

    public void setSnackBar ( final Activity activity,String message,String action ) {
        final Snackbar snackbar = Snackbar.make (activity.findViewById (android.R.id.content),message,Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction (action,new View.OnClickListener () {
            @Override
            public void onClick ( View view ) {
                getWalletHistory (0);
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