package threebigo.medicinehawker.dboy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import threebigo.medicinehawker.dboy.R;
import threebigo.medicinehawker.dboy.model.WalletHistory;

public class WalletHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // for load more
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    public boolean isLoading;
    Activity activity;
    ArrayList<WalletHistory> histories;
    String id = "0";

    public WalletHistoryAdapter ( Activity activity,ArrayList<WalletHistory> histories ) {
        this.activity = activity;
        this.histories = histories;
    }

    public void add ( int position,WalletHistory item ) {
        histories.add (position,item);
        notifyItemInserted (position);
    }

    public void setLoaded ( ) {
        isLoading = false;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder ( ViewGroup parent,final int viewType ) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from (activity).inflate (R.layout.lyt_wallet_history_list,parent,false);
            return new WalletHistoryHolderItems (view);
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

        if (holderparent instanceof WalletHistoryHolderItems) {
            final WalletHistoryHolderItems holder = (WalletHistoryHolderItems)holderparent;
            final WalletHistory walletHistory = histories.get (position);
            id = walletHistory.getId ();

            holder.tvTxNo.setText (walletHistory.getId ());
            holder.tvTxDateAndTime.setText (walletHistory.getDate_created ());
            holder.tvTxMessage.setText (walletHistory.getMessage ());
            holder.tvTxAmount.setText (activity.getString (R.string.amount_title) + walletHistory.getAmount ());

            if (walletHistory.getStatus ().equals ("SUCCESS")) {
                holder.cardViewTxStatus.setBackgroundColor (activity.getColor (R.color.tx_success_bg));
            } else {
                holder.cardViewTxStatus.setBackgroundColor (activity.getColor (R.color.tx_fail_bg));
            }

            holder.tvTxStatus.setText (walletHistory.getStatus ());

        } else if (holderparent instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading)holderparent;
            loadingViewHolder.progressBar.setIndeterminate (true);
        }
    }

    @Override
    public int getItemCount ( ) {
        return histories.size ();
    }

    @Override
    public int getItemViewType ( int position ) {
        return histories.get (position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public long getItemId ( int position ) {
        WalletHistory product = histories.get (position);
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

    public class WalletHistoryHolderItems extends RecyclerView.ViewHolder {

        TextView tvTxNo, tvTxDateAndTime, tvTxMessage, tvTxAmount, tvTxStatus;
        CardView cardViewTxStatus;

        public WalletHistoryHolderItems ( @NonNull View itemView ) {
            super (itemView);

            tvTxNo = itemView.findViewById (R.id.tvTxNo);
            tvTxDateAndTime = itemView.findViewById (R.id.tvTxDateAndTime);
            tvTxMessage = itemView.findViewById (R.id.tvTxMessage);
            tvTxAmount = itemView.findViewById (R.id.tvTxAmount);
            tvTxStatus = itemView.findViewById (R.id.tvTxStatus);

            cardViewTxStatus = itemView.findViewById (R.id.cardViewTxStatus);


        }
    }
}