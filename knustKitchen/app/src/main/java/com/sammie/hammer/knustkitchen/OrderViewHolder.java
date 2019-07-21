package com.sammie.hammer.knustkitchen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



/**
 * Created by A.Richard on 20/09/2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder
//        implements View.OnClickListener,
//     View.OnLongClickListener,
//        View.OnCreateContextMenuListener

{

    public TextView txtOrderId;
    public TextView txtOrderStatus;
    public TextView txtTableNumber;
    public TextView txtOrderAddress,txtDate;

    public Button btnEdit,btnRemove,btnDirections,btnDetail;

//    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);


        txtOrderAddress = (TextView) itemView.findViewById(R.id.order_location);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.order_status);
        txtTableNumber = (TextView) itemView.findViewById(R.id.order_phone);
        txtOrderId = (TextView) itemView.findViewById(R.id.order_id);
        txtDate = (TextView)itemView.findViewById(R.id.order_date);

        btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
        btnDetail = (Button) itemView.findViewById(R.id.btnDetail);
//        btnDirections = (Button) itemView.findViewById(R.id.btnDirection);
        btnRemove = (Button) itemView.findViewById(R.id.btnRemove);


    }


}
