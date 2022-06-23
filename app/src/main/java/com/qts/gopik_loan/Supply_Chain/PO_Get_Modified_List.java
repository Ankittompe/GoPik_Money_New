package com.qts.gopik_loan.Supply_Chain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Supplychain_Adapter.PoRequest_Adapter;

import java.util.ArrayList;

public class PO_Get_Modified_List extends AppCompatActivity {

    RecyclerView po_list_recyclerview;
    PoRequest_Adapter poRequest_adapter;
    ArrayList<String>po_list= new ArrayList<>();
    ArrayList<String>initial_qty= new ArrayList<>();
    ArrayList<String>later_qty= new ArrayList<>();
    ArrayList<String>final_price= new ArrayList<>();
    ImageView arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_get_list);

        po_list_recyclerview = findViewById(R.id.po_list_recyclerview);
        arrow = findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PO_Get_Modified_List.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ShowList();

    }



    private void ShowList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                PO_Get_Modified_List.this, LinearLayoutManager.VERTICAL, false
        );

        po_list_recyclerview.setLayoutManager(layoutManager);
        poRequest_adapter = new PoRequest_Adapter(getApplicationContext(),po_list);
        po_list_recyclerview.setAdapter(poRequest_adapter);
    }
}