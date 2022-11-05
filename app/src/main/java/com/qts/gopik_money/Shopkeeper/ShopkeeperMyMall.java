package com.qts.gopik_money.Shopkeeper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbisoft.pickit.PickiT;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.LoanLimit_Details_MODEL;
import com.qts.gopik_money.Model.SetCreditLimitForShopkeeperModel;
import com.qts.gopik_money.Pojo.SetCreditLimitForShopkeeper_POJO;
import com.qts.gopik_money.Pojo.ShopkeeperdoctoFinance_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Supply_Chain.PersonalDetails_Activity;
import com.qts.gopik_money.Utils.BottomSheetFragment;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopkeeperMyMall#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopkeeperMyMall extends Fragment {
    String networkError = "It seems your Network is unstable . Please Try again!";
    ImageView upload_document, imageView4;
    private final int PERMISSION_REQUEST_CODE = 1000;
    CustPrograssbar custPrograssbar;
    ImageView po_button, info_button;
    ConstraintLayout po_layout, doc_upload;
    String rupee_symbol = "₹";
    String zero_amount = "00";
    PickiT pickiT;
    TextView loan_status_tv, total_credit_limit_tv, total_loan_limit_tv, viewloans,mTxtNewPo;
    TextView financerNametxt,RoiText,TenureTxt;
    String usercode, doc_status;
    private static final int REQUEST = 112;
    Context mContext;
    CardView cardView9;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopkeeperMyMall() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopkeeperMyMall.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopkeeperMyMall newInstance(String param1, String param2) {
        ShopkeeperMyMall fragment = new ShopkeeperMyMall();
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
        View view = inflater.inflate(R.layout.fragment_shopkeeper_my_mall, container, false);
        custPrograssbar = new CustPrograssbar();
        upload_document = (ImageView) view.findViewById(R.id.upload_document);
        po_button = (ImageView) view.findViewById(R.id.po_button);
        po_layout = (ConstraintLayout) view.findViewById(R.id.po_layout);
        doc_upload = (ConstraintLayout) view.findViewById(R.id.doc_upload);
        viewloans = (TextView) view.findViewById(R.id.viewloans);
        info_button = (ImageView) view.findViewById(R.id.info_button);
        imageView4 = (ImageView) view.findViewById(R.id.imageView4);
        loan_status_tv = (TextView) view.findViewById(R.id.loan_status_tv);
        total_credit_limit_tv = (TextView) view.findViewById(R.id.total_credit_limit_tv);
        total_loan_limit_tv = (TextView) view.findViewById(R.id.total_loan_limit_tv);
        financerNametxt = (TextView) view.findViewById(R.id.financerNametxt);
        RoiText = (TextView) view.findViewById(R.id.RoiText);
        TenureTxt = (TextView) view.findViewById(R.id.TenureTxt);
        cardView9 = (CardView) view.findViewById(R.id.cardView9);
        info_button.setVisibility(View.VISIBLE);

        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetFragment bottomSheetDialog = BottomSheetFragment.newInstance();
                bottomSheetDialog.show(getChildFragmentManager(), "Bottom Sheet Dialog Fragment");
            }
        });

        viewloans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoanStatusShopkeeper.class);
                startActivity(intent);
            }
        });
        cardView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InvoiceTopFive.class);
                startActivity(intent);
            }
        });


        po_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent po_intent = new Intent(getActivity(), TopFivePoShopkeeper.class);
                startActivity(po_intent);
            }
        });

        upload_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doc_upload = new Intent(getActivity(), PersonalDetails_Activity.class);
                startActivity(doc_upload);
            }
        });

        SetCreditLimitForShopkeeper();
        credit_loan();
        String mNotifyData = SharedPref.getStringFromSharedPref(AppConstants.NOTIFICATION_TYPE, getActivity());
        mTxtNewPo = (TextView) view.findViewById(R.id.txtNewShopkeeperPo);
        if (mNotifyData.equals("NewPO")) {
            mTxtNewPo.setVisibility(View.VISIBLE);
        } else {
            mTxtNewPo.setVisibility(View.GONE);
            SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATION_TYPE, "10", getActivity());
        }

        po_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxtNewPo.setVisibility(View.GONE);
                SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATION_TYPE, "10", getActivity());
                Intent it = new Intent(getContext(), TopFivePoShopkeeper.class);
                startActivity(it);
            }
        });
        return view;
    }

    private void credit_loan() {
        custPrograssbar.prograssCreate(getActivity());
        ShopkeeperdoctoFinance_POJO pojo = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getActivity()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);

        Call<LoanLimit_Details_MODEL> call = restApis.credit_loan(pojo);
        call.enqueue(new Callback<LoanLimit_Details_MODEL>() {
            @Override
            public void onResponse(Call<LoanLimit_Details_MODEL> call, Response<LoanLimit_Details_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();
                        financerNametxt.setText(response.body().getPayload().getFinancer());
                        RoiText.setText(response.body().getPayload().getRoi()+"%");
                        TenureTxt.setText(response.body().getPayload().getTenure()+"days");
                        String number1 = response.body().getPayload().getAvailable_limit();
                        double amount = Double.parseDouble(number1);
                        DecimalFormat formatter = new DecimalFormat("##,##,###");
                        String formatted = formatter.format(amount);
                        total_loan_limit_tv.setText(rupee_symbol + formatted);
                        String number2 = response.body().getPayload().getTotal_credit_limit();
                        double amount2 = Double.parseDouble(number2);
                        DecimalFormat formatter2 = new DecimalFormat("##,##,###");
                        String formatted2 = formatter2.format(amount2);
                        total_credit_limit_tv.setText(rupee_symbol+formatted2);

                    }
                } else {

                  //  Toast.makeText(getActivity(), "It seems your Network is unstable . Please Try again!", Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onFailure(Call<LoanLimit_Details_MODEL> call, Throwable t) {

                Toast.makeText(getActivity(), "It seems your Network is unstable . Please Try again!", Toast.LENGTH_LONG).show();
            }

        });


    }

    private void SetCreditLimitForShopkeeper() {
        custPrograssbar.prograssCreate(getContext());
        SetCreditLimitForShopkeeper_POJO pojo = new SetCreditLimitForShopkeeper_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);

        Call<SetCreditLimitForShopkeeperModel> call = restApis.SetCreditLimitForShopkeeper(pojo);
        call.enqueue(new Callback<SetCreditLimitForShopkeeperModel>() {
            @Override
            public void onResponse(Call<SetCreditLimitForShopkeeperModel> call, Response<SetCreditLimitForShopkeeperModel> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode()==200) {
                        usercode = response.body().getPayload().getUsercode();
                        doc_status=response.body().getPayload().getDoc_status();

                        if (doc_status.equals("Rejected")){
                            loan_status_tv.setText(response.body().getPayload().getDoc_status());
                            loan_status_tv.setTextColor(Color.parseColor("#CC2828"));

                            SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON,response.body().getPayload().getReject_reasn(), getContext());
                            SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS,response.body().getPayload().getDoc_status(), getContext());
                           // total_credit_limit_tv.setText(rupee_symbol+zero_amount);

                        }else if(doc_status.equals("Approved")){
                            loan_status_tv.setText(response.body().getPayload().getDoc_status());
                            String number2 = response.body().getPayload().getCredit_limit();
                            double amount2 = Double.parseDouble(number2);
                            DecimalFormat formatter2 = new DecimalFormat("##,##,###");
                            String formatted2 = formatter2.format(amount2);
                         //   total_credit_limit_tv.setText(rupee_symbol +formatted2);
                            SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON, "Credit Limit Assigned you can raise PO", getContext());
                            SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS,response.body().getPayload().getDoc_status(), getContext());
                        }else if (doc_status.equals("Remark")){
                            loan_status_tv.setText(response.body().getPayload().getDoc_status());
                         //   total_credit_limit_tv.setText(rupee_symbol+zero_amount);
                            SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON,response.body().getPayload().getRemark_reasn(), getContext());
                            SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS,response.body().getPayload().getDoc_status(), getContext());
                        }else if (doc_status.equals("Approval Pending")){
                            loan_status_tv.setText(response.body().getPayload().getDoc_status());
                         //   total_credit_limit_tv.setText(rupee_symbol+"00");
                            loan_status_tv.setTextColor(Color.parseColor("#FFA600"));

                            //  loan_status_tv.setTextColor(getResources().getColor(R.color.orange2));
                            // loan_status_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange2));
                            //   loan_status_tv.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light));
                            info_button.setVisibility(View.GONE);
                            SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON,response.body().getPayload().getRemark_reasn(), getContext());
                            SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS,response.body().getPayload().getDoc_status(), getContext());
                        }else if (doc_status.equals("NA")){
                            loan_status_tv.setText("Upload Document");
                            loan_status_tv.setTextColor(Color.parseColor("#FFA600"));
                         //   total_credit_limit_tv.setText(rupee_symbol + zero_amount);
                            // loan_status_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange2));
                            info_button.setVisibility(View.GONE);

                        }
                        /* */

                        //  Toast.makeText(getContext(), "Ducument sent to Financer", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getContext(), "It seems your Network is unstable . Please Try again !", Toast.LENGTH_LONG).show();
                    }

                } else {
                    loan_status_tv.setText("Upload Document");
                //    total_credit_limit_tv.setText(rupee_symbol + zero_amount);
                    //  loan_status_tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange2));


                    info_button.setVisibility(View.GONE);
                    custPrograssbar.closePrograssBar();
                }


            }



            @Override
            public void onFailure(Call<SetCreditLimitForShopkeeperModel> call, Throwable t) {

                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });


    }


}