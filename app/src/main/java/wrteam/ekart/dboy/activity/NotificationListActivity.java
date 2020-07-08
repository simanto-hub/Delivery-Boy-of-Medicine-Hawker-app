package wrteam.ekart.dboy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wrteam.ekart.dboy.R;
import wrteam.ekart.dboy.adapter.NotificationAdapter;
import wrteam.ekart.dboy.helper.ApiConfig;
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
    TextView tvAlert;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_notification_list);
        toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        getSupportActionBar ().setTitle (getString (R.string.notifications));
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
        swipeLayout = findViewById (R.id.swipeLayout);
        activity = NotificationListActivity.this;
        recyclerView = findViewById (R.id.recyclerView);
        recyclerView.setLayoutManager (new LinearLayoutManager (activity));

        session = new Session (activity);

        getNotificationData (activity);


        swipeLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh() {
                getNotificationData (activity);
                swipeLayout.setRefreshing (false);
                disableSwipe (swipeLayout);
            }
        });

    }

    public void getNotificationData(final Activity activity) {
        Map<String, String> params = new HashMap<String, String> ();
        params.put (Constant.ID, session.getData (Constant.ID));
        params.put (Constant.GET_NOTIFICATION, Constant.GetVal);
        ApiConfig.RequestToVolley (new VolleyCallback () {
            @Override
            public void onSuccess(boolean result, String response) {
                if (result) {
                    try {
//                        System.out.println ("===n response " + response);
                        notifications = new ArrayList<> ();
                        JSONObject object = new JSONObject (response);
                        JSONArray jsonArray = object.getJSONArray (Constant.DATA);
                        Gson g = new Gson ();

                        for (int i = 0; i < jsonArray.length (); i++) {
                            Notification notification = g.fromJson (jsonArray.getJSONObject (i).toString (), Notification.class);
                            notifications.add (notification);
                        }
                        NotificationAdapter notificationAdapter = new NotificationAdapter (activity, notifications);
                        recyclerView.setAdapter (notificationAdapter);
                    } catch (Exception e) {
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
}