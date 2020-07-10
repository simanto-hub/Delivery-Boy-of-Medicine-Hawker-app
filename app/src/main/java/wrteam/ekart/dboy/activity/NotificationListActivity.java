package wrteam.ekart.dboy.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
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

import wrteam.ekart.dboy.R;
import wrteam.ekart.dboy.adapter.NotificationAdapter;
import wrteam.ekart.dboy.helper.ApiConfig;
import wrteam.ekart.dboy.helper.AppController;
import wrteam.ekart.dboy.helper.Constant;
import wrteam.ekart.dboy.helper.Session;
import wrteam.ekart.dboy.helper.VolleyCallback;
import wrteam.ekart.dboy.model.Notification;

import static wrteam.ekart.dboy.helper.ApiConfig.disableSwipe;

public class NotificationListActivity extends AppCompatActivity {

    Activity activity;
    RecyclerView recyclerView;
    ArrayList<Notification> notifications;
    Toolbar toolbar;
    SwipeRefreshLayout swipeLayout;
    private Session session;
    NestedScrollView scrollView;
    NotificationAdapter notificationAdapter;
    int total = 0;
    private boolean isLoadMore = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_notification_list);
        toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        getSupportActionBar ().setTitle (getString (R.string.notifications));
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
        swipeLayout = findViewById (R.id.swipeLayout);
        scrollView = findViewById (R.id.scrollView);
        activity = NotificationListActivity.this;
        recyclerView = findViewById (R.id.recyclerView);
        recyclerView.setLayoutManager (new LinearLayoutManager (activity));

        session = new Session (activity);

        if (AppController.isConnected (activity)) {
            getNotificationData (0);
        } else {
            setSnackBar (activity, getString (R.string.no_internet_message), getString (R.string.ok), Color.RED);
        }


        swipeLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh() {
                if (AppController.isConnected (activity)) {
                    session.setData (Constant.OFFSET_NOTIFICATION, "" + 0);
                    getNotificationData (0);
                    swipeLayout.setRefreshing (false);
                    disableSwipe (swipeLayout);
                } else {
                    setSnackBar (activity, getString (R.string.no_internet_message), getString (R.string.ok), Color.RED);
                }

            }
        });
    }

    private void getNotificationData(final int startoffset) {
        notifications = new ArrayList<> ();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager (activity);
        recyclerView.setLayoutManager (linearLayoutManager);

        Map<String, String> params = new HashMap<String, String> ();
        params.put (Constant.ID, session.getData (Constant.ID));
        params.put (Constant.GET_NOTIFICATION, Constant.GetVal);
        params.put (Constant.OFFSET, session.getData (Constant.OFFSET_NOTIFICATION));
        params.put (Constant.LIMIT, Constant.PRODUCT_LOAD_LIMIT);


//        System.out.println("====params " + params.toString());
        ApiConfig.RequestToVolley (new VolleyCallback () {
            @Override
            public void onSuccess(boolean result, String response) {
                if (result) {
                    try {
                        //System.out.println("====product  " + response);
                        JSONObject objectbject = new JSONObject (response);
                        if (! objectbject.getBoolean (Constant.ERROR)) {
                            total = Integer.parseInt (objectbject.getString (Constant.TOTAL));
                            session.setData (Constant.TOTAL, String.valueOf (total));

                            JSONObject object = new JSONObject (response);
                            JSONArray jsonArray = object.getJSONArray (Constant.DATA);

                            Gson g = new Gson ();

                            for (int i = 0; i < jsonArray.length (); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject (i);

                                if (jsonObject1 != null) {
                                    Notification notification = g.fromJson (jsonObject1.toString (), Notification.class);
                                    notifications.add (notification);
                                } else {
                                    break;
                                }

                            }
                            if (startoffset == 0) {
                                notificationAdapter = new NotificationAdapter (activity, notifications);
                                notificationAdapter.setHasStableIds (true);
                                recyclerView.setAdapter (notificationAdapter);
                                scrollView.setOnScrollChangeListener (new NestedScrollView.OnScrollChangeListener () {
                                    @Override
                                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                                        // if (diff == 0) {
                                        if (scrollY == (v.getChildAt (0).getMeasuredHeight () - v.getMeasuredHeight ())) {
                                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager ();
                                            if (notifications.size () < total) {
                                                if (! isLoadMore) {
                                                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition () == notifications.size () - 1) {
                                                        //bottom of list!
                                                        notifications.add (null);
                                                        notificationAdapter.notifyItemInserted (notifications.size () - 1);
                                                        new Handler ().postDelayed (new Runnable () {
                                                            @Override
                                                            public void run() {

                                                                session.setData (Constant.OFFSET_NOTIFICATION, Integer.parseInt (session.getData (Constant.OFFSET_NOTIFICATION)) + Constant.LOAD_ITEM_LIMIT);
                                                                Map<String, String> params = new HashMap<> ();
                                                                params.put (Constant.ID, session.getData (Constant.ID));
                                                                params.put (Constant.GET_NOTIFICATION, Constant.GetVal);
                                                                params.put (Constant.OFFSET, session.getData (Constant.OFFSET_NOTIFICATION));
                                                                params.put (Constant.LIMIT, Constant.PRODUCT_LOAD_LIMIT);

                                                                ApiConfig.RequestToVolley (new VolleyCallback () {
                                                                    @Override
                                                                    public void onSuccess(boolean result, String response) {

                                                                        if (result) {
                                                                            try {
                                                                                // System.out.println("====product  " + response);
                                                                                JSONObject objectbject1 = new JSONObject (response);
                                                                                if (! objectbject1.getBoolean (Constant.ERROR)) {

                                                                                    session.setData (Constant.TOTAL, objectbject1.getString (Constant.TOTAL));

                                                                                    notifications.remove (notifications.size () - 1);
                                                                                    notificationAdapter.notifyItemRemoved (notifications.size ());

                                                                                    JSONObject object = new JSONObject (response);
                                                                                    JSONArray jsonArray = object.getJSONArray (Constant.DATA);

                                                                                    Gson g = new Gson ();


                                                                                    for (int i = 0; i < jsonArray.length (); i++) {
                                                                                        JSONObject jsonObject1 = jsonArray.getJSONObject (i);

                                                                                        if (jsonObject1 != null) {
                                                                                            Notification notification = g.fromJson (jsonObject1.toString (), Notification.class);
                                                                                            notifications.add (notification);
                                                                                        } else {
                                                                                            break;
                                                                                        }

                                                                                    }
                                                                                    notificationAdapter.notifyDataSetChanged ();
                                                                                    notificationAdapter.setLoaded ();
                                                                                    isLoadMore = false;
                                                                                }
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace ();
                                                                            }
                                                                        }
                                                                    }
                                                                }, activity, Constant.MAIN_URL, params, false);

                                                            }
                                                        }, 0);
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
        }, activity, Constant.MAIN_URL, params, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId () == android.R.id.home) {
            onBackPressed ();
            return true;
        }
        return super.onOptionsItemSelected (item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed ();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed ();
        return super.onSupportNavigateUp ();

    }

    public void setSnackBar(final Activity activity, String message, String action, int color) {
        final Snackbar snackbar = Snackbar.make (activity.findViewById (android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction (action, new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                getNotificationData (0);
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