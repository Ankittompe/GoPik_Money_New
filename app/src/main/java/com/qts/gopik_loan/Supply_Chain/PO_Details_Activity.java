package com.qts.gopik_loan.Supply_Chain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.qts.gopik_loan.R;

public class PO_Details_Activity extends AppCompatActivity {

    TextView Po_id_tv,name_tv,Total_amount,brand_name,quantity_count;
    String po_id,prod_name,amount,brand,quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_details);

        Po_id_tv = findViewById(R.id.po_id);
        name_tv = findViewById(R.id.name_tv);
        Total_amount = findViewById(R.id.amout_count);
        brand_name = findViewById(R.id.brand_name);
        quantity_count = findViewById(R.id.quantity_count);
        Intent intent = getIntent();

        po_id = intent.getStringExtra("po_name");
        prod_name = intent.getStringExtra("product_name");
        amount = intent.getStringExtra("amount");
        brand = intent.getStringExtra("brand");
        quantity = intent.getStringExtra("quantity");

        Po_id_tv.setText(po_id);
        name_tv.setText(prod_name);
        Total_amount.setText(amount);
        brand_name.setText(brand);
        quantity_count.setText(quantity);

    }
}