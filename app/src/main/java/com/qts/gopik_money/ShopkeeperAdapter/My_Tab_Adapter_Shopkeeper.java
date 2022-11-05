package com.qts.gopik_money.ShopkeeperAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qts.gopik_money.Shopkeeper.Bank_Details_Shopkeeper;
import com.qts.gopik_money.Shopkeeper.Profile_Details_Shopkeeper;

import org.jetbrains.annotations.NotNull;

public class My_Tab_Adapter_Shopkeeper extends FragmentStateAdapter {

    public My_Tab_Adapter_Shopkeeper(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {


        switch(position)
        {
            case 0:
                return new Profile_Details_Shopkeeper();
            default:
                return new Bank_Details_Shopkeeper();


        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}



