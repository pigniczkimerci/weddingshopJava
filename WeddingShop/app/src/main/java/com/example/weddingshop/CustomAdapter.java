package com.example.weddingshop;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivity listActivity;
    List<Product> productList;
    Context context;

    public CustomAdapter(ListActivity listActivity, List<Product> productList) {
        this.listActivity = listActivity;
        this.productList = productList;

    }

   /* public CustomAdapter(com.example.weddingshop.ListActivity listActivity, List<Product> productList) {
        this.listActivity = listActivity;
        this.productList = productList;
    }*/


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String nev = productList.get(position).getNev();
                String ar = productList.get(position).getAr();
                String kep = productList.get(position).getKep();
                Toast.makeText(listActivity,nev+ " " + ar + " " + kep, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onItemLongClick(View viewm, int position) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nev.setText(productList.get(position).getNev());
        holder.ar.setText(productList.get(position).getAr());
        holder.kep.setText(productList.get(position).getKep());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
