package wrteam.ekart.dboy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import wrteam.ekart.dboy.R;
import wrteam.ekart.dboy.activity.OrderDetailActivity;
import wrteam.ekart.dboy.helper.Constant;
import wrteam.ekart.dboy.model.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // for load more
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    public boolean isLoading;
    Activity activity;
    ArrayList<Notification> notifications;
    String id = "0";

    public NotificationAdapter(Activity activity, ArrayList<Notification> notifications) {
        this.activity = activity;
        this.notifications = notifications;
    }

    public void add(int position, Notification item) {
        notifications.add (position, item);
        notifyItemInserted (position);
    }

    public void setLoaded() {
        isLoading = false;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from (activity).inflate (R.layout.lyt_notification_list, parent, false);
            return new OrderHolderItems (view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from (activity).inflate (R.layout.item_progressbar, parent, false);
            return new ViewHolderLoading (view);
        }

        return null;
    }


    @RequiresApi (api = Build.VERSION_CODES.M)
    @SuppressLint ("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderparent, final int position) {

        if (holderparent instanceof OrderHolderItems) {
            OrderHolderItems holder = (OrderHolderItems) holderparent;
            final Notification orderList = notifications.get (position);
            id = orderList.getId ();


            holder.lytNotification.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    Constant.Position_Value = position;
                    activity.startActivity (new Intent (activity, OrderDetailActivity.class).putExtra (Constant.ORDER_ID, orderList.getId ()));
                }
            });
        } else if (holderparent instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holderparent;
            loadingViewHolder.progressBar.setIndeterminate (true);
        }


    }

    @Override
    public int getItemCount() {
        return notifications.size ();
    }

    @Override
    public int getItemViewType(int position) {
        return notifications.get (position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public long getItemId(int position) {
        Notification product = notifications.get (position);
        if (product != null)
            return Integer.parseInt (product.getId ());
        else
            return position;
    }

    class ViewHolderLoading extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ViewHolderLoading(View view) {
            super (view);
            progressBar = view.findViewById (R.id.itemProgressbar);
        }
    }

    public class OrderHolderItems extends RecyclerView.ViewHolder {

        TextView tvCustomerName, tvCustomerMobile, tvCustomerOrderNo, tvCustomerPaymentMethod, tvCustomerOrderDate, tvStatus;
        LinearLayout lytNotification;

        public OrderHolderItems(@NonNull View itemView) {
            super (itemView);

            tvCustomerOrderNo = itemView.findViewById (R.id.tvCustomerOrderNo);
            tvCustomerOrderDate = itemView.findViewById (R.id.tvCustomerOrderDate);

            tvStatus = itemView.findViewById (R.id.tvStatus);
            tvCustomerName = itemView.findViewById (R.id.tvCustomerName);

            tvCustomerMobile = itemView.findViewById (R.id.tvCustomerMobile);
            tvCustomerPaymentMethod = itemView.findViewById (R.id.tvCustomerPaymentMethod);

            lytNotification = itemView.findViewById (R.id.lytNotification);


        }
    }
}