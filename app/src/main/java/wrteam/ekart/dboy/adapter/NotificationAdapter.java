package wrteam.ekart.dboy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import wrteam.ekart.dboy.R;
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

        Notification notification = notifications.get (position);

        holder.tvOrderDate.setText (activity.getString (R.string.ordered_on) + notification.getDate_sent ());
        holder.tvTitle.setText (Html.fromHtml (notification.getTitle ()));
        holder.tvMessage.setText (Html.fromHtml (notification.getMessage ()));

        holder.tvShowMore.setOnClickListener (new View.OnClickListener () {
            @RequiresApi (api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (holder.statusShowMore) {
                    holder.tvShowMore.setText (activity.getString (R.string.show_less));
                    holder.tvShowMore.setCompoundDrawablesWithIntrinsicBounds (0, 0, R.drawable.ic_show_less, 0);
                    holder.tvMessage.setMaxLines (7);
                    holder.statusShowMore = false;
                } else {
                    holder.tvShowMore.setText (activity.getString (R.string.show_more));
                    holder.tvShowMore.setCompoundDrawablesWithIntrinsicBounds (0, 0, R.drawable.ic_show_more, 0);
                    holder.tvMessage.setMaxLines (2);
                    holder.statusShowMore = true;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return notifications.size ();
    }

    public class NotificationItemHolder extends RecyclerView.ViewHolder {

        TextView tvOrderDate, tvTitle, tvMessage, tvShowMore;
        boolean statusShowMore;


        public NotificationItemHolder(@NonNull View itemView) {
            super (itemView);

            tvOrderDate = itemView.findViewById (R.id.tvOrderDate);
            tvTitle = itemView.findViewById (R.id.tvTitle);
            tvMessage = itemView.findViewById (R.id.tvMessage);
            tvShowMore = itemView.findViewById (R.id.tvShowMore);
            statusShowMore = false;
        }
    }

}
