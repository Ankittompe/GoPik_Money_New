package com.qts.gopik_loan.Supply_Chain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.qts.gopik_loan.R;

public class View_Invoice_Activity extends AppCompatActivity {

    ImageView invoice_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invoice);
        invoice_view = findViewById(R.id.invoice_view);


    }
}