package threebigo.medicinehawker.dboy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import threebigo.medicinehawker.dboy.R;
import threebigo.medicinehawker.dboy.model.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // for load more
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    public boolean isLoading;
    Activity activity;
    ArrayList<Notification> notifications;
    String id = "0";
    boolean showMore = false;

    public NotificationAdapter ( Activity activity,ArrayList<Notification> notifications ) {
        this.activity = activity;
        this.notifications = notifications;
    }

    public void add ( int position,Notification item ) {
        notifications.add (position,item);
        notifyItemInserted (position);
    }

    public void setLoaded ( ) {
        isLoading = false;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder ( ViewGroup parent,final int viewType ) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from (activity).inflate (R.layout.lyt_notification_list,parent,false);
            return new NotificationHolderItems (view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from (activity).inflate (R.layout.item_progressbar,parent,false);
            return new ViewHolderLoading (view);
        }

        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder ( RecyclerView.ViewHolder holderparent,final int position ) {

        if (holderparent instanceof NotificationHolderItems) {
            final NotificationHolderItems holder = (NotificationHolderItems)holderparent;
            final Notification notification = notifications.get (position);
            id = notification.getId ();

            holder.tvTitle.setText (activity.getString (R.string.order_number) + notification.getOrder_id ());
            holder.tvMessage.setText (notification.getTitle ());
            holder.tvOrderDate.setText (notification.getDate_created ());
            holder.tvMessageMore.setText (notification.getMessage ());

            holder.tvShowMore.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick ( View view ) {
                    if (! showMore) {
                        showMore = true;
                        holder.tvMessageMore.setVisibility (View.GONE);
                        holder.tvShowMore.setCompoundDrawablesRelativeWithIntrinsicBounds (0,0,R.drawable.ic_show_more,0);
                    } else {
                        showMore = false;
                        holder.tvMessageMore.setVisibility (View.VISIBLE);
                        holder.tvShowMore.setCompoundDrawablesRelativeWithIntrinsicBounds (0,0,R.drawable.ic_show_less,0);

                    }
                }
            });

        } else if (holderparent instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading)holderparent;
            loadingViewHolder.progressBar.setIndeterminate (true);
        }
    }

    @Override
    public int getItemCount ( ) {
        return notifications.size ();
    }

    @Override
    public int getItemViewType ( int position ) {
        return notifications.get (position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public long getItemId ( int position ) {
        Notification product = notifications.get (position);
        if (product != null)
            return Integer.parseInt (product.getId ());
        else
            return position;
    }

    class ViewHolderLoading extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ViewHolderLoading ( View view ) {
            super (view);
            progressBar = view.findViewById (R.id.itemProgressbar);
        }
    }

    public class NotificationHolderItems extends RecyclerView.ViewHolder {

        TextView tvTitle, tvMessage, tvMessageMore, tvOrderDate, tvShowMore;
        LinearLayout lytNotification;

        public NotificationHolderItems ( @NonNull View itemView ) {
            super (itemView);

            tvTitle = itemView.findViewById (R.id.tvTitle);
            tvMessage = itemView.findViewById (R.id.tvMessage);

            tvMessageMore = itemView.findViewById (R.id.tvMessageMore);
            tvOrderDate = itemView.findViewById (R.id.tvOrderDate);

            tvShowMore = itemView.findViewById (R.id.tvShowMore);

            lytNotification = itemView.findViewById (R.id.lytNotification);


        }
    }
}