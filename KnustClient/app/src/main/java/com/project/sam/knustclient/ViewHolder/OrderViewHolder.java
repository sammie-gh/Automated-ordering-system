package com.project.sam.knustclient.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.project.sam.knustclient.Interface.ItemClickListener;

/**
 * Created by A.Richard on 17/09/2017.
 */

public class OrderViewHolder extends
        RecyclerView.ViewHolder implements
        View.OnClickListener{

    public TextView txtOrderId;
    public TextView txtOrderStatus;
    public TextView txtOrderPhone;
    public TextView txtOrderAddress,username;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);


//        txtOrderAddress = (TextView)itemView.findViewById(R.id.order_location);
//        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
//        txtOrderPhone = (TextView)itemView.findViewById(R.id.order_phone);
//        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
//        username = (TextView)itemView.findViewById(R.id.userame);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);
        return  ;


    }
}
