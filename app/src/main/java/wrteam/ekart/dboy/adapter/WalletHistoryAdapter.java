package wrteam.ekart.dboy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import wrteam.ekart.dboy.R;
import wrteam.ekart.dboy.helper.AppController;
import wrteam.ekart.dboy.helper.Constant;
import wrteam.ekart.dboy.model.WalletHistory;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.HolderWalletTransaction> {

    ArrayList<WalletHistory> walletHistories;
    Activity activity;

    public WalletHistoryAdapter(ArrayList<WalletHistory> walletHistories, Activity activity) {
        this.walletHistories = walletHistories;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HolderWalletTransaction onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.lyt_wallet_history_list, null);
        WalletHistoryAdapter.HolderWalletTransaction holderItems = new WalletHistoryAdapter.HolderWalletTransaction (v);
        return holderItems;
    }

    @RequiresApi (api = Build.VERSION_CODES.M)
    @SuppressLint ("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderWalletTransaction holder, int position) {
        final WalletHistory walletHistory = walletHistories.get (position);

        holder.tvTxNo.setText (walletHistory.getId ());
        holder.tvTxDateAndTime.setText (walletHistory.getDate_created ());
        holder.tvTxMessage.setText (walletHistory.getMessage ());

        holder.tvTxAmount.setText (activity.getString (R.string.amount_title) + (Float.parseFloat (walletHistories.get (position).getOpening_balance ()) - Float.parseFloat (walletHistories.get (position).getClosing_balance ())));

        if (walletHistory.getStatus ().equals (Constant.SUCCESS)) {
            holder.cardViewTxStatus.setCardBackgroundColor (activity.getColor (R.color.tx_success_bg));
            holder.tvTxStatus.setTextColor (activity.getColor (R.color.tx_success_text));
        } else {
            holder.cardViewTxStatus.setCardBackgroundColor (activity.getColor (R.color.tx_fail_bg));
            holder.tvTxStatus.setTextColor (activity.getColor (R.color.tx_fail_text));
        }
        holder.tvTxStatus.setText (AppController.toTitleCase (walletHistory.getStatus ()));


    }

    @Override
    public int getItemCount() {
        return walletHistories.size ();
    }

    public class HolderWalletTransaction extends RecyclerView.ViewHolder {

        TextView tvTxNo, tvTxDateAndTime, tvTxStatus, tvTxMessage, tvTxAmount;
        CardView cardViewTxStatus;

        public HolderWalletTransaction(@NonNull View itemView) {
            super (itemView);
            tvTxNo = itemView.findViewById (R.id.tvTxNo);
            tvTxDateAndTime = itemView.findViewById (R.id.tvTxDateAndTime);
            tvTxStatus = itemView.findViewById (R.id.tvTxStatus);
            tvTxMessage = itemView.findViewById (R.id.tvTxMessage);
            cardViewTxStatus = itemView.findViewById (R.id.cardViewTxStatus);
            tvTxAmount = itemView.findViewById (R.id.tvTxAmount);
        }
    }
}
