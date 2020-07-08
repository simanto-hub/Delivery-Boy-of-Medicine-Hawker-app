package wrteam.ekart.dboy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import wrteam.ekart.dboy.R;
import wrteam.ekart.dboy.activity.OrderDetailActivity;
import wrteam.ekart.dboy.helper.Constant;
import wrteam.ekart.dboy.model.Notification;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationItemHolder> {
    Activity activity;
    ArrayList<Notification> notifications;

    public NotificationAdapter(Activity activity, ArrayList<Notification> notifications) {
        this.activity = activity;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.lyt_notification_list, null);
        NotificationItemHolder notificationItemHolder = new NotificationItemHolder (v);
        return notificationItemHolder;
    }

    @SuppressLint ("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.NotificationItemHolder holder, int position) {

        final Notification notification = notifications.get (position);

        holder.tvOrderDate.setText (activity.getString (R.string.ordered_on) + notification.getDate_created ());
        holder.tvTitle.setText (activity.getString (R.string.order_number) + notification.getOrder_id ());
        holder.tvMessage.setText (notification.getTitle ());
        holder.tvMessageMore.setText (notification.getMessage ());

        holder.tvShowMore.setOnClickListener (new View.OnClickListener () {
            @RequiresApi (api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (holder.statusShowMore) {
                    holder.tvShowMore.setText (activity.getString (R.string.show_more));
                    holder.tvMessageMore.setVisibility (View.GONE);
                    holder.tvShowMore.setCompoundDrawablesWithIntrinsicBounds (0, 0, R.drawable.ic_show_less, 0);
                    holder.statusShowMore = false;
                } else {
                    holder.tvShowMore.setText (activity.getString (R.string.show_less));
                    holder.tvMessageMore.setVisibility (View.VISIBLE);
                    holder.tvShowMore.setCompoundDrawablesWithIntrinsicBounds (0, 0, R.drawable.ic_show_more, 0);
                    holder.statusShowMore = true;
                }
            }
        });

        holder.lytNotification.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                activity.startActivity (new Intent (activity, OrderDetailActivity.class).putExtra (Constant.ORDER_ID, notification.getOrder_id ()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size ();
    }

    public class NotificationItemHolder extends RecyclerView.ViewHolder {

        TextView tvOrderDate, tvTitle, tvMessageMore, tvMessage, tvShowMore;
        boolean statusShowMore;
        LinearLayout lytNotification;


        public NotificationItemHolder(@NonNull View itemView) {
            super (itemView);

            tvOrderDate = itemView.findViewById (R.id.tvOrderDate);
            tvTitle = itemView.findViewById (R.id.tvTitle);
            tvMessage = itemView.findViewById (R.id.tvMessage);
            tvMessageMore = itemView.findViewById (R.id.tvMessageMore);
            tvShowMore = itemView.findViewById (R.id.tvShowMore);
            lytNotification = itemView.findViewById (R.id.lytNotification);
            statusShowMore = false;
        }
    }

}
