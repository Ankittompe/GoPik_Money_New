package com.qts.gopik_loan.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qts.gopik_loan.Activity.Home;

import com.qts.gopik_loan.Adapter.My_Tab_Adapter;
import com.qts.gopik_loan.R;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab_Fragment extends Fragment {
    My_Tab_Adapter my_tab_adapter;
    TabLayout tabLayout;
    ViewPager2 viewPager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tab_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab_Fragment newInstance(String param1, String param2) {
        Tab_Fragment fragment = new Tab_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_tab_, container, false);

        viewPager = (ViewPager2) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);


        viewPager.setAdapter(new My_Tab_Adapter(getActivity()));

        TabLayoutMediator tmu=new TabLayoutMediator(
                tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0: {
                        tab.setText("Profile Details");
                        break;
                    }
                    case 1:{
                        tab.setText("Bank Details");
                        break;
                    }

                }
            }
        }
        );
        tmu.attach();




       /* my_tab_adapter=new My_Tab_Adapter(getFragmentManager());
        my_tab_adapter.add(new Profile_Details(), "Page 1");
        my_tab_adapter.add(new Bank_Details(), "Page 2");


        // Set the adapter
        viewPager.setAdapter(my_tab_adapter);
       // viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout = (TabLayout) view.findViewById(R.id.tab_fragment);
        tabLayout.setupWithViewPager(viewPager);*/
        /*tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        return view;





    }

}