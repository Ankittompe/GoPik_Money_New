package com.qts.gopik_loan.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.Home;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Activity.Wallet_Activity;
import com.qts.gopik_loan.Adapter.Scratch_Card_Adapter;
import com.qts.gopik_loan.Adapter.WalletHistoryAdapter;
import com.qts.gopik_loan.Model.View_scratchcard_MODEL;
import com.qts.gopik_loan.Model.Wallet_balance_MODEL;
import com.qts.gopik_loan.Model.Wallethistory_MODEL;
import com.qts.gopik_loan.Pojo.View_scratchcard_POJO;
import com.qts.gopik_loan.Pojo.Wallet_balance_POJO;
import com.qts.gopik_loan.Pojo.Wallethistory_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reward#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reward extends Fragment {
    TextView textview3,rechrgnow,status_massage;
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
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Reward() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reward.
     */
    // TODO: Rename and change types and number of parameters
    public static Reward newInstance(String param1, String param2) {
        Reward fragment = new Reward();
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
        View v=inflater.inflate(R.layout.fragment_reward, container, false);
        textview3=(TextView)v.findViewById(R.id.textview3);
        custPrograssbar = new CustPrograssbar();
        rvblog=(RecyclerView)v. findViewById(R.id.rvblogg);
        status_massage=(TextView)v. findViewById(R.id.status_massage);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"2",getContext());
        loannext= (ImageView) v.findViewById(R.id.loannext);


        loannext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), Wallet_Activity.class);
                startActivity(it);

            }
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
                    Log.e("Response1", "bodyyy11" + response.body().getPayload());
                    if (response.body().getPayload().size() > 0) {
                        Log.e("Sizeee", "frommmZero");
                        for (int i = 0; i < response.body().getPayload().size(); i++) {
                            Log.e("SizeeeResponse", "frommmZeroResponseX");
                            id.add(response.body().getPayload().get(i).getId());
                            Amount.add(response.body().getPayload().get(i).getAmount());
                            status.add(response.body().getPayload().get(i).getStatus());
                            expdate.add(response.body().getPayload().get(i).getExpdate());
                            created_at.add(response.body().getPayload().get(i).getCreated_at());
                            Log.e("SizeeeResponseAmount", "SizeeeResponseAmount2");
                            if (response.body().getPayload().size() - 1 == i) {
                                Log.d("payloadLoop", "sizeee");
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                                rvblog.setLayoutManager(gridLayoutManager);
                                Log.e("recyclev", "recycleviewwww");
                                Scratch_Card_Adapter scratchAdaptor = new Scratch_Card_Adapter(getActivity(), Amount, status,expdate,created_at,
                                        id);
                                Log.e("recyclev1", "recycleviewwww2");
                                rvblog.setAdapter(scratchAdaptor);
                            }
                        }

                    }
                }


            }
            @Override
            public void onFailure(Call<View_scratchcard_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
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



            }

        });
    }

}