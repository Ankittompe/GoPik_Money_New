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


                    custPrograssbar.closePrograssBar();
                    if (response.body().getStatus()==201){
                        Log.e("1","amit1");
                        rvblog.setVisibility(View.GONE);

                        status_massage.setText(response.body().getMessage());
                        status_massage.setVisibility(View.VISIBLE);
                        Log.e("1","amit2"+response.body().getMessage());
                    }
                    else
                    {
                        rvblog.setVisibility(View.VISIBLE);
                        Log.e("1","2");
                        ArrayList<String> id = new ArrayList<>();
                        Log.e("1","amit3");
                        ArrayList<String> user_code = new ArrayList<>();
                        Log.e("1","amit4");
                        ArrayList<String> card_type = new ArrayList<>();
                        Log.e("1","amit5");
                        ArrayList<String> amount = new ArrayList<>();
                        Log.e("1","amit6");
                        ArrayList<String> status = new ArrayList<>();
                        Log.e("1","amit7");
                        ArrayList<String> created_at = new ArrayList<>();
                        Log.e("1","amit8");
                        ArrayList<String> updated_at = new ArrayList<>();
                        Log.e("1","amit9");
                        if (response.body().getPayload().size() > 0) {
                            Log.e("1","amit10");
                            Log.e(TAG, "getpayloadmethod");
                            Log.e("1","amit11");
                            Log.e(TAG, "City Payload Size: "+response.body().getPayload().size());
                            Log.e("1","amit12");
                            for (int i = 0; i < response.body().getPayload().size(); i++) {

                                Log.e("Body", "body3");

                                id.add(response.body().getPayload().get(i).getId());
                                Log.e("1","amit14");

                                if (response.body().getPayload().size() - 1 == i) {

                                    Log.e("1","amit15");
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                                    Log.e("1","amit16");
                                    rvblog.setLayoutManager(gridLayoutManager);
                                    Log.e("1","amit17");
                                    rvblog.setItemAnimator(new DefaultItemAnimator());
                                    Log.e("1","amit18");
                                    scratch_card_adapter = new Scratch_Card_Adapter(getContext(), id,  user_code,  card_type
                                            , amount,  status,  created_at
                                            ,  updated_at   );
                                    Log.e("1","amit19");
                                    rvblog.setAdapter(scratch_card_adapter);

                                }
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