package com.qts.gopik_loan.Gopikdost_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Utils.CustPrograssbar;


public class Home_Dost_Fragment extends Fragment {

    RecyclerView categoryrecylerview,subcategoryrecyclerview;
    CustPrograssbar custPrograssbar;

    public Home_Dost_Fragment() {

    }

    public static Home_Dost_Fragment newInstance(String param1, String param2) {
        Home_Dost_Fragment fragment = new Home_Dost_Fragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_home__dost_, container, false);
        categoryrecylerview=(RecyclerView)v.findViewById(R.id.categoryrecylerview);
        subcategoryrecyclerview=(RecyclerView)v.findViewById(R.id.subcategoryrecyclerview);

        custPrograssbar = new CustPrograssbar();
        return v;
    }
}