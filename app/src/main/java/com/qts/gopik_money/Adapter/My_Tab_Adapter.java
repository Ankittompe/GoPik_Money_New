package com.qts.gopik_money.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qts.gopik_money.Fragment.Bank_Details;
import com.qts.gopik_money.Fragment.Profile_Details;

import org.jetbrains.annotations.NotNull;


public class My_Tab_Adapter extends FragmentStateAdapter {


    public My_Tab_Adapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {


        if (position == 0) {
            return new Profile_Details();
        }
        return new Bank_Details();

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}