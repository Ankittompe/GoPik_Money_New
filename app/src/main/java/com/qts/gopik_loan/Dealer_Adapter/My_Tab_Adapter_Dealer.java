package com.qts.gopik_loan.Dealer_Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qts.gopik_loan.Dealer_Fragment.Bank_Details_Dealer;
import com.qts.gopik_loan.Dealer_Fragment.Profile_Details_Dealer;
import com.qts.gopik_loan.Fragment.Bank_Details;
import com.qts.gopik_loan.Fragment.Profile_Details;

import org.jetbrains.annotations.NotNull;

public class My_Tab_Adapter_Dealer extends FragmentStateAdapter {


    public My_Tab_Adapter_Dealer(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {


        switch(position)
        {
            case 0:
                return new Profile_Details_Dealer();
            default:
                return new Bank_Details_Dealer();


        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

