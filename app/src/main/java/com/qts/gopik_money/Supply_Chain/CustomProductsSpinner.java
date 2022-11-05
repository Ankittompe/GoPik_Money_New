package com.qts.gopik_money.Supply_Chain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qts.gopik_money.R;

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

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_spinner_layout, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.txtProductName);
        PO_Product currentItem = getItem(position);



        if (currentItem != null) {
            textViewName.setText(currentItem.getName());
        }
        return convertView;
    }
}
