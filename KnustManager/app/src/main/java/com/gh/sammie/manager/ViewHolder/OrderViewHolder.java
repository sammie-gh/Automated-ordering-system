package com.gh.sammie.manager.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.gh.sammie.manager.Common.Interface.ItemClickListener;
import com.gh.sammie.manager.R;


/**
 * Created by A.Richard on 20/09/2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{

    public TextView txtOrderId;
    public TextView txtOrderStatus;
    public TextView txtTableNumber;
    public TextView txtOrderAddress,txtDate;
    public TextView txtOrderPrice;
    public TextView txtOrderName;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtDate = (TextView)itemView.findViewById(R.id.order_date);
        txtOrderAddress = (TextView)itemView.findViewById(R.id.order_location);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtTableNumber = (TextView)itemView.findViewById(R.id.order_phone);
        txtOrderPrice = (TextView)itemView.findViewById(R.id.order_price);
        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);


        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {



    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select The Action");

//        contextMenu.add(0,0,getAdapterPosition(),"Update");
        contextMenu.add(0,0,getAdapterPosition(),"Delete");

    }
}
