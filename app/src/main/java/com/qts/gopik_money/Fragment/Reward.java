package com.qts.gopik_money.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Activity.Wallet_Activity;
import com.qts.gopik_money.Adapter.Scratch_Card_Adapter;
import com.qts.gopik_money.Adapter.WalletHistoryAdapter;
import com.qts.gopik_money.Model.View_scratchcard_MODEL;
import com.qts.gopik_money.Model.Wallet_balance_MODEL;
import com.qts.gopik_money.Pojo.View_scratchcard_POJO;
import com.qts.gopik_money.Pojo.Wallet_balance_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reward#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reward extends Fragment {
    String networkError = "It seems your Network is unstable . Please Try again!";
    TextView textview3;
    TextView status_massage;
    CustPrograssbar custPrograssbar;
    WalletHistoryAdapter walletHistoryAdapter;
    RecyclerView rvblog;
    ImageView loannext;
    Scratch_Card_Adapter scratch_card_adapter;

    ArrayList<String>Amount= new ArrayList<>();
    ArrayList<String>id= new ArrayList<>();
    ArrayList<String>user_code= new ArrayList<>();
    ArrayList<String>card_type= new ArrayList<>();
    ArrayList<String>expdate= new ArrayList<>();
    ArrayList<String>status= new ArrayList<>();
    ArrayList<String>created_at= new ArrayList<>();
    ArrayList<String>updated_at= new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match




    public Reward() {
        // Required empty public constructor
    }


    public static Reward newInstance() {
        Reward fragment = new Reward();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_reward, container, false);
        textview3=(TextView)v.findViewById(R.id.textview3);
        custPrograssbar = new CustPrograssbar();
        rvblog=(RecyclerView)v. findViewById(R.id.rvblogg);
        status_massage=(TextView)v. findViewById(R.id.status_massage);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"2",getContext());
        loannext= (ImageView) v.findViewById(R.id.loannext);


        loannext.setOnClickListener(v1 -> {
            Intent it = new Intent(getContext(), Wallet_Activity.class);
            startActivity(it);

        });
        wallet_balance();

        view_scratchcard();
        return v;
    }

    private void view_scratchcard() {
        custPrograssbar.prograssCreate(getActivity());
        View_scratchcard_POJO pojo = new View_scratchcard_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<View_scratchcard_MODEL> call = restApis.view_scratchcard(pojo);
        call.enqueue(new Callback<View_scratchcard_MODEL>() {
            @Override
            public void onResponse(Call<View_scratchcard_MODEL> call, Response<View_scratchcard_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getPayload().size() > 0) {

                        for (int i = 0; i < response.body().getPayload().size(); i++) {

                            id.add(response.body().getPayload().get(i).getId());
                            Amount.add(response.body().getPayload().get(i).getAmount());
                            status.add(response.body().getPayload().get(i).getStatus());
                            expdate.add(response.body().getPayload().get(i).getExpdate());
                            created_at.add(response.body().getPayload().get(i).getCreated_at());
                            Log.e("SizeeeResponseAmount", "SizeeeResponseAmount2");
                            if (response.body().getPayload().size() - 1 == i) {
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                                rvblog.setLayoutManager(gridLayoutManager);
                                Scratch_Card_Adapter scratchAdaptor = new Scratch_Card_Adapter(getActivity(), Amount, status,expdate,created_at,
                                        id);
                                rvblog.setAdapter(scratchAdaptor);
                            }
                        }

                    }
                    else{
                        status_massage.setVisibility(View.VISIBLE);
                        status_massage.setText("Book more loans for more rewards...");

                    }
                }


            }
            @Override
            public void onFailure(Call<View_scratchcard_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }


    private void wallet_balance() {
        custPrograssbar.prograssCreate(getActivity());
        Wallet_balance_POJO pojo = new Wallet_balance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Wallet_balance_MODEL> call = restApis.wallet_balance(pojo);
        call.enqueue(new Callback<Wallet_balance_MODEL>() {
            @Override
            public void onResponse(Call<Wallet_balance_MODEL> call, Response<Wallet_balance_MODEL> response) {
                if (response.body() != null) {

                    custPrograssbar.closePrograssBar();
                    textview3.setText("Current Balance: "+response.body().getBalance());


                }
            }

            @Override
            public void onFailure(Call<Wallet_balance_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_SHORT).show();

            }

        });
    }

}