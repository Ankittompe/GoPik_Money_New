package com.qts.gopik_money.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.qts.gopik_money.Adapter.My_Tab_Adapter;
import com.qts.gopik_money.R;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_tab_, container, false);

        viewPager = (ViewPager2) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);


        viewPager.setAdapter(new My_Tab_Adapter(getActivity()));

        TabLayoutMediator tmu=new TabLayoutMediator(
                tabLayout, viewPager, (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Profile Details");
                    } else if (position == 1) {
                        tab.setText("Bank Details");
                    }
                }
        );
        tmu.attach();





        return view;





    }

}