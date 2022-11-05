package com.qts.gopik_money.Shopkeeper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qts.gopik_money.R;
import com.qts.gopik_money.ShopkeeperAdapter.My_Tab_Adapter_Shopkeeper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab_Fragment_Shopkeeper#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab_Fragment_Shopkeeper extends Fragment {
    My_Tab_Adapter_Shopkeeper my_tab_adapter_shopkeeper;
    TabLayout tabLayout;
    ViewPager2 viewPager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tab_Fragment_Shopkeeper() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab_Fragment_Shopkeeper.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab_Fragment_Shopkeeper newInstance(String param1, String param2) {
        Tab_Fragment_Shopkeeper fragment = new Tab_Fragment_Shopkeeper();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tab___shopkeeper, container, false);
        viewPager = (ViewPager2) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);


        viewPager.setAdapter(new My_Tab_Adapter_Shopkeeper(getActivity()));

        TabLayoutMediator tmu = new TabLayoutMediator(
                tabLayout, viewPager, (tab, position) -> {
                    switch (position) {
                        case 0: {
                            tab.setText("Profile Details");
                            break;
                        }
                        case 1: {
                            tab.setText("Bank Details");
                            break;
                        }

                        default:
                           break;
                    }
                }
        );
        tmu.attach();


        return view;


    }

}

