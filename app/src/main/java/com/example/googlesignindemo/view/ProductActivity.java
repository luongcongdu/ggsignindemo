package com.example.googlesignindemo.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.googlesignindemo.R;
import com.example.googlesignindemo.model.Product;
import com.example.googlesignindemo.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends OptionsMenuActivity {
    private Toolbar toolbar;
    private RecyclerView rcvProduct;
    private ProductRecyclerAdapter adapter;
    private ProductViewModel productViewModel;
    private ProgressBar progressBar;
    private boolean isSetAdapter;
    ProductRecyclerAdapter.OnProductListener onProductListener;
    boolean isScrolling;
    int currentItem, totalItems, scrollOutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        toolbar = findViewById(R.id.toolbar);
        rcvProduct = findViewById(R.id.rcv_product);
        progressBar = findViewById(R.id.progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);
        rcvProduct.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rcvProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItem = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItem + scrollOutItems) == totalItems) {
                    isScrolling = false;
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            productViewModel.fetchData();
                            progressBar.setVisibility(View.GONE);
                        }
                    }, 1000);
                }
            }
        });

        onProductListener = (v, position) -> productViewModel.onProductItemClick(v, position);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getmListProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                if (!isSetAdapter) {
                    adapter = new ProductRecyclerAdapter(productList, onProductListener);
                    rcvProduct.setAdapter(adapter);
                    isSetAdapter = true;
                }
                adapter.notifyDataSetChanged();
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setLogo(null);
        Log.i("ProductActivity", "onCreate");
    }

}