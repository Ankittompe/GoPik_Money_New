package com.qts.gopik_money.Supply_Chain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.R;
import com.qts.gopik_money.Supplychain_Adapter.Product_Cat_Adapter;

import java.util.ArrayList;

public class ProductCatActivity extends AppCompatActivity {

    RecyclerView prod_cat_rv;
    Product_Cat_Adapter product_cat_adapter;
    ArrayList<String> product_cat = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cat);
        prod_cat_rv = findViewById(R.id.prod_cat_rv);

        GetProductCatagory();


    }

    private void GetProductCatagory() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                ProductCatActivity.this, LinearLayoutManager.HORIZONTAL, false
        );

        prod_cat_rv.setLayoutManager(layoutManager);

        product_cat_adapter = new Product_Cat_Adapter(getApplicationContext(),product_cat);

        prod_cat_rv.setAdapter(product_cat_adapter);

    }
}