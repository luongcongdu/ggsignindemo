package com.example.googlesignindemo.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.googlesignindemo.R;
import com.example.googlesignindemo.model.Product;

import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {
    private List<Product> mListProduct;
    private OnProductListener onProductListener;

    public ProductRecyclerAdapter(List<Product> mListProduct, OnProductListener onProductListener) {
        this.mListProduct = mListProduct;
        this.onProductListener = onProductListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if (product == null) {
            return;
        }
        Glide.with(holder.imgProduct)
                .load(product.getDownloadUrl())
                .circleCrop()
                .into(holder.imgProduct);
        holder.txtAuthor.setText(product.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgProduct;
        TextView txtAuthor;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            txtAuthor = itemView.findViewById(R.id.txt_author);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onProductListener.onProductClick(v, position);
        }
    }

    public interface OnProductListener {
        void onProductClick(View v, int position);
    }
}
