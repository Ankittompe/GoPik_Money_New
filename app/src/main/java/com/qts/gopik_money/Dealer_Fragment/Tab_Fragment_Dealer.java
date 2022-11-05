package com.qts.gopik_money.Dealer_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qts.gopik_money.Dealer_Adapter.My_Tab_Adapter_Dealer;
import com.qts.gopik_money.R;


public class Tab_Fragment_Dealer extends Fragment {
    My_Tab_Adapter_Dealer my_tab_adapter_dealer;
    TabLayout tabLayout;
    ViewPager2 viewPager;





    public Tab_Fragment_Dealer() {
        // Nothing to do
    }


    public static Tab_Fragment_Dealer newInstance() {
        Tab_Fragment_Dealer fragment = new Tab_Fragment_Dealer();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab___dealer, container, false);
        viewPager = (ViewPager2) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);


        viewPager.setAdapter(new My_Tab_Adapter_Dealer(getActivity()));

        TabLayoutMediator tmu = new TabLayoutMediator(
                tabLayout, viewPager, (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Profile Details");
                    } else if (position == 1) {
                        tab.setText("Bank Details");
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

