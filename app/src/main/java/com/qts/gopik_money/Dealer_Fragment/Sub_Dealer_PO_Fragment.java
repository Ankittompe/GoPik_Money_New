package com.qts.gopik_money.Dealer_Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Dealer_Adapter.POListAdapter;
import com.qts.gopik_money.Model.PayloadItem;
import com.qts.gopik_money.Model.SubDealerPOListResponseModel;
import com.qts.gopik_money.Pojo.PO_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sub_Dealer_PO_Fragment extends Fragment {
    String networkError = "It seems your Network is unstable . Please Try again!";
    String dataIsEmpty = "Data is empty";
    CustPrograssbar custPrograssbar;
    private ArrayList<PayloadItem> mPOModelArrayList;
    RecyclerView mRecVwrSubDealerPoList;
    POListAdapter mPoListAdapter;
    TextView mTxtMore;
    TextView mTxtTop5;
    TextView mTxtBackPoList;
    boolean mIsTopFiveStatus = true;

    public Sub_Dealer_PO_Fragment() {
        //Do Nothing
    }

    public static Sub_Dealer_PO_Fragment newInstance() {
        Sub_Dealer_PO_Fragment fragment = new Sub_Dealer_PO_Fragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_dealer_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(view);
    }

    private void initData(View view) {
        mRecVwrSubDealerPoList = view.findViewById(R.id.recVwSubDealerPoList);
        mTxtMore = view.findViewById(R.id.txtMore);
        mTxtTop5 = view.findViewById(R.id.txtTop5);
        mTxtBackPoList = view.findViewById(R.id.txtBackPoList);
        mTxtBackPoList.setOnClickListener(view1 -> {
            startActivity(new Intent(requireActivity(), MainActivity.class));
            requireActivity().finish();
        });
        custPrograssbar = new CustPrograssbar();
        getPOList(mIsTopFiveStatus);
        mTxtMore.setOnClickListener(new openMorePoDetailsListener());
        mTxtTop5.setOnClickListener(new openTop5PoDetailsListener());
    }

    private class openMorePoDetailsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mTxtTop5.setVisibility(View.VISIBLE);
            mTxtMore.setVisibility(View.GONE);
            getPOList(false);
        }
    }

    private class openTop5PoDetailsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mTxtMore.setVisibility(View.VISIBLE);
            mTxtTop5.setVisibility(View.GONE);
            getPOList(true);
        }
    }

    private void getPOList(boolean mIsTopFiveStatus) {
        custPrograssbar.prograssCreate(getContext());
        PO_POJO pojo = new PO_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<SubDealerPOListResponseModel> call;
        if (mIsTopFiveStatus) {
            call = restApis.GetSubDealerList(pojo);
        } else {
            call = restApis.GetSubDealerAllPOData(pojo);
        }
        call.enqueue(new Callback<SubDealerPOListResponseModel>() {
            @Override
            public void onResponse(Call<SubDealerPOListResponseModel> call, Response<SubDealerPOListResponseModel> response) {
                custPrograssbar.closePrograssBar();
                mPOModelArrayList = new ArrayList<>();
                mPoListAdapter = new POListAdapter(mPOModelArrayList, mIsTopFiveStatus);
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        if (response.body().getPayload().size() != 0) {
                            mPOModelArrayList.addAll(response.body().getPayload());
                            response.body().getPayload().get(0).getPo_id();
                            Log.e("response",">>"+response.body().getPayload().get(1).getStatus());
                            mRecVwrSubDealerPoList.setHasFixedSize(true);
                            mRecVwrSubDealerPoList.setLayoutManager(new LinearLayoutManager(requireActivity()));
                            mRecVwrSubDealerPoList.setAdapter(mPoListAdapter);
                        } else {
                            Toast.makeText(getContext(), dataIsEmpty, Toast.LENGTH_LONG).show();
                        }

                    }
                    else {
                        Toast.makeText(getContext(), dataIsEmpty, Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), dataIsEmpty, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SubDealerPOListResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                custPrograssbar.closePrograssBar();
            }
        });
    }


}