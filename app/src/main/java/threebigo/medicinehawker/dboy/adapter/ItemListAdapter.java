package threebigo.medicinehawker.dboy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import threebigo.medicinehawker.dboy.R;
import threebigo.medicinehawker.dboy.helper.ApiConfig;
import threebigo.medicinehawker.dboy.helper.Constant;
import threebigo.medicinehawker.dboy.model.Items;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.HolderItems> {
    Activity activity;
    ArrayList<Items> items;

    public ItemListAdapter(Activity activity, ArrayList<Items> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public HolderItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_order_item_list, null);
        ItemListAdapter.HolderItems holderItems = new ItemListAdapter.HolderItems(v);
        return holderItems;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderItems holder, int position) {
        Items items1 = items.get(position);

        holder.tvProductListName.setText(items1.getName());
        holder.tvSize.setText("(" + items1.getUnit() + ")");
        holder.tvProductListPrice.setText(items1.getPrice());
        holder.tvProductListQty.setText(items1.getQuantity());
        holder.tvProductListSubTotal.setText(items1.getSubtotal());
        holder.imgProduct.setDefaultImageResId(R.drawable.placeholder);
        holder.imgProduct.setErrorImageResId(R.drawable.placeholder);
        holder.imgProduct.setImageUrl(items1.getProduct_image(), Constant.imageLoader);

        if (items1.getActive_status().equalsIgnoreCase("cancelled") || items1.getActive_status().equalsIgnoreCase("returned")) {
            holder.tvActiveStatus.setText(ApiConfig.toTitleCase(items1.getActive_status()));
            holder.tvActiveStatus.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HolderItems extends RecyclerView.ViewHolder {
        TextView tvSize, tvProductListName, tvProductListPrice, tvProductListQty, tvProductListSubTotal, tvActiveStatus;
        NetworkImageView imgProduct;

        public HolderItems(@NonNull View itemView) {
            super(itemView);
            tvProductListName = itemView.findViewById(R.id.tvProductListName);
            tvSize = itemView.findViewById(R.id.tvSize);
            tvProductListPrice = itemView.findViewById(R.id.tvProductListPrice);
            tvProductListQty = itemView.findViewById(R.id.tvProductListQty);
            tvProductListSubTotal = itemView.findViewById(R.id.tvProductListSubTotal);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvActiveStatus = itemView.findViewById(R.id.tvActiveStatus);

        }
    }
}
