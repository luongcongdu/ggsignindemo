package com.example.googlesignindemo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.googlesignindemo.R;
import com.example.googlesignindemo.model.Product;
import com.example.googlesignindemo.viewmodel.ProductDetailViewModel;

public class ProductDetailsActivity extends OptionsMenuActivity implements View.OnClickListener {

    private ProductDetailViewModel productDetailViewModel;
    ImageView imgProduct;
    TextView txtAuthor;
    Toolbar toolbar;
    Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        imgProduct = findViewById(R.id.img_product_detail);
        txtAuthor = findViewById(R.id.txt_author_detail);
        toolbar = findViewById(R.id.toolbar);

        mProduct = (Product) getIntent().getSerializableExtra("productValue");
        if (mProduct != null) {
            Glide.with(imgProduct)
                    .load(mProduct.getDownloadUrl())
                    .into(imgProduct);
            txtAuthor.setText("Author: " + mProduct.getAuthor());
        }

        productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        productDetailViewModel.getProductLiveData().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                Glide.with(imgProduct)
                        .load(product.getDownloadUrl())
                        .into(imgProduct);

                txtAuthor.setText(product.getAuthor());
                productDetailViewModel.setData(product);
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar:
                onBackPressed();
                break;
        }
    }
}