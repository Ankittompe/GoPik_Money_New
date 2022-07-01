package com.qts.gopik_loan.Supply_Chain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Supply_Chain.PO_Product;

import java.util.ArrayList;

public class CustomProductsSpinner extends ArrayAdapter<PO_Product> {

    public CustomProductsSpinner(Context context, ArrayList<PO_Product> po_productArrayList) {
        super(context, 0, po_productArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_spinner_layout, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.txtProductName);
        PO_Product currentItem = getItem(position);
        Log.e("currentItem ",currentItem.getName()+"="+currentItem.getPrice());

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getName());
        }
        return convertView;
    }
}
