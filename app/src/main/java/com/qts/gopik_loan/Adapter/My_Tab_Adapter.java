package com.qts.gopik_loan.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qts.gopik_loan.Fragment.Bank_Details;
import com.qts.gopik_loan.Fragment.HomeFragment;
import com.qts.gopik_loan.Fragment.Profile_Details;
import com.qts.gopik_loan.Fragment.Tab_Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class My_Tab_Adapter extends FragmentStateAdapter {


    public My_Tab_Adapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {


        switch(position)
        {
            case 0:
                return new Profile_Details();
            default:
                return new Bank_Details();


        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}