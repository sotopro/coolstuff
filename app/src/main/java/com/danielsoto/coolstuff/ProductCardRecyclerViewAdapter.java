package com.danielsoto.coolstuff;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danielsoto.coolstuff.network.ImageRequest;
import com.danielsoto.coolstuff.network.ProductEntry;

import java.util.List;

public class ProductCardRecyclerViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {
    private  List<ProductEntry> productList;
    private ImageRequest imageRequest;

    ProductCardRecyclerViewAdapter(List<ProductEntry> productList) {
        this.productList = productList;
        imageRequest = ImageRequest.getInstance();
    }
    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        return new ProductCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {
        if(productList != null && position < productList.size()) {
            ProductEntry product = productList.get(position);
            holder.productTitle.setText(product.title);
            holder.productPrice.setText(product.price);
            imageRequest.setImageFromUrl(holder.productImage, product.url);
        }
    }

    @Override
    public int getItemCount() {return productList.size();}
}
