package com.example.ecom.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecom.ProductDetails;
import com.example.ecom.R;
import com.example.ecom.models.ProductModel;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductModel> list;

    public ProductAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_view_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductModel product = list.get(position);

        holder.pTitle.setText(product.getpName());
        holder.pPrice.setText(product.getpPrice()+"৳");

        holder.pTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("id",product.getpID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        holder.logo_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("id",product.getpID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pTitle,pPrice;
        ImageView logo_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pTitle = itemView.findViewById(R.id.pTitle);
            pPrice = itemView.findViewById(R.id.pPrice);
            logo_img = itemView.findViewById(R.id.logo_img);


        }
    }
}

