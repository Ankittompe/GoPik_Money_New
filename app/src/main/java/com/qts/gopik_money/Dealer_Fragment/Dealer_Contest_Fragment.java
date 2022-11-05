package com.qts.gopik_money.Dealer_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.R;


public class Dealer_Contest_Fragment extends Fragment {




    public Dealer_Contest_Fragment() {
        // Required empty public constructor
    }



    public static Dealer_Contest_Fragment newInstance() {
        Dealer_Contest_Fragment fragment = new Dealer_Contest_Fragment();


        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getContext());
        return inflater.inflate(R.layout.fragment_kirloskar__contest_, container, false);
    }
}