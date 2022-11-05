package com.qts.gopik_money.Dealer_Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.hbisoft.pickit.PickiT;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.LoanLimit_Details_MODEL;
import com.qts.gopik_money.Model.SetCreditLimitForDealer_MODEL;
import com.qts.gopik_money.Pojo.Loanlimitdetails_POJO;
import com.qts.gopik_money.Pojo.SetCreditLimitForDealer_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Supply_Chain.BusinessDetails_Activity;
import com.qts.gopik_money.Supply_Chain.InvoiceTopFiveStatusDealer;
import com.qts.gopik_money.Supply_Chain.LoanStatus;
import com.qts.gopik_money.Supply_Chain.PO_TOP_FIVE_Activity;
import com.qts.gopik_money.Supply_Chain.PersonalDetails_Activity;
import com.qts.gopik_money.Utils.BottomSheetFragment;
import com.qts.gopik_money.Utils.CustPrograssbar;


import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link My_Mall_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class My_Mall_Fragment extends Fragment {
    String colorOrange = "#FFA600";
    String colorRed = "#CC2828";
    String uploadDocument = "Upload Document";


    String mDecimalFormat = "##,##,###";
    Dialog custumdialogCondition;
    String networkError = "It seems your Network is unstable . Please Try again!";
    ImageView upload_document;
    ImageView  imageView4;

    CustPrograssbar custPrograssbar;
    ImageView po_button;
    ImageView info_button;
    ConstraintLayout po_layout;
    ConstraintLayout  doc_upload;
    String rupee_symbol = "₹";
    String zero_amount = "00";
    PickiT pickiT;
    TextView loan_status_tv;
    TextView total_credit_limit_tv;
    TextView total_loan_limit_tv;
    TextView viewloans;
    TextView mTxtNewPo;
    TextView financerNametxt;
    TextView RoiText;
    TextView TenureTxt;
    String usercode;
    String doc_status;
    ConstraintLayout invoiceButton;
    CardView cardView9;

    Call<SetCreditLimitForDealer_MODEL> mSetCreditLimitForDealer;


    public My_Mall_Fragment() {
        // Required empty public constructor
    }



    public static My_Mall_Fragment newInstance() {
        My_Mall_Fragment fragment = new My_Mall_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my__mall_, container, false);
        custumdialogCondition = new Dialog(getActivity());


        custumdialogCondition.setContentView(R.layout.custumpro_dailog);
        custumdialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        custumdialogCondition.setCancelable(true);


        custPrograssbar = new CustPrograssbar();
        upload_document = (ImageView) v.findViewById(R.id.upload_document);
        po_button = (ImageView) v.findViewById(R.id.po_button);
        po_layout = (ConstraintLayout) v.findViewById(R.id.po_layout);
        doc_upload = (ConstraintLayout) v.findViewById(R.id.doc_upload);
        viewloans = (TextView) v.findViewById(R.id.viewloans);
        info_button = (ImageView) v.findViewById(R.id.info_button);
        imageView4 = (ImageView) v.findViewById(R.id.imageView4);
        invoiceButton = (ConstraintLayout) v.findViewById(R.id.invoiceButton);
        cardView9 = (CardView) v.findViewById(R.id.cardView9);

        loan_status_tv = (TextView) v.findViewById(R.id.loan_status_tv);
        total_credit_limit_tv = (TextView) v.findViewById(R.id.total_credit_limit_tv);
        total_loan_limit_tv = (TextView) v.findViewById(R.id.total_loan_limit_tv);
        financerNametxt = (TextView) v.findViewById(R.id.financerNametxt);
        RoiText = (TextView) v.findViewById(R.id.RoiText);
        TenureTxt = (TextView) v.findViewById(R.id.TenureTxt);
        info_button.setVisibility(View.VISIBLE);
        invoiceButton.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), InvoiceTopFiveStatusDealer.class);
            startActivity(intent);
        });
        cardView9.setOnClickListener(v12 -> {

            Intent intent = new Intent(getActivity(), InvoiceTopFiveStatusDealer.class);
            startActivity(intent);

        });

        info_button.setOnClickListener(v13 -> {


            BottomSheetFragment bottomSheetDialog = BottomSheetFragment.newInstance();
            bottomSheetDialog.show(getChildFragmentManager(), "Bottom Sheet Dialog Fragment");
        });

        viewloans.setOnClickListener(v14 -> {
            Intent intent = new Intent(getActivity(), LoanStatus.class);
            startActivity(intent);
        });

        GetLoanLimitDetails();

        doc_upload.setOnClickListener(v15 -> {
//            Intent po_intent = new Intent(getActivity(), PersonalDetails_Activity.class);
            Intent po_intent = new Intent(getActivity(), BusinessDetails_Activity.class);
            startActivity(po_intent);
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            TypedValue outValue = new TypedValue();
            getActivity().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            po_layout.setBackgroundResource(outValue.resourceId);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            TypedValue outValue = new TypedValue();
            getActivity().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            doc_upload.setBackgroundResource(outValue.resourceId);
        }
        po_button.setOnClickListener(v16 -> {
            Intent po_intent = new Intent(getActivity(), PO_TOP_FIVE_Activity.class);
            startActivity(po_intent);
        });

        upload_document.setOnClickListener(v17 -> {
            Intent doc_upload = new Intent(getActivity(), PersonalDetails_Activity.class);
            startActivity(doc_upload);
        });
        SetCreditLimitForDealer();
        String mNotifyData = SharedPref.getStringFromSharedPref(AppConstants.NOTIFICATION_TYPE, getActivity());
        mTxtNewPo = (TextView) v.findViewById(R.id.txtNewPo);
        if (mNotifyData.equals("NewPO")) {
            mTxtNewPo.setVisibility(View.VISIBLE);
        } else {
            mTxtNewPo.setVisibility(View.GONE);
            SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATION_TYPE, "10", getActivity());
        }

        po_layout.setOnClickListener(v18 -> {
            mTxtNewPo.setVisibility(View.GONE);
            SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATION_TYPE, "10", getActivity());
            Intent po_intent = new Intent(getActivity(), PO_TOP_FIVE_Activity.class);
            startActivity(po_intent);
        });
        return v;

    }

    private void SetCreditLimitForDealer() {
        custPrograssbar.prograssCreate(getActivity());
        String mUserType= SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getActivity());


        SetCreditLimitForDealer_POJO pojo = new SetCreditLimitForDealer_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getActivity()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);

        if (mUserType.equals("Dealer")) {
            mSetCreditLimitForDealer = restApis.SetCreditLimitForDealer(pojo);
            mSetCreditLimitForDealer.enqueue(new Callback<SetCreditLimitForDealer_MODEL>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<SetCreditLimitForDealer_MODEL> call, Response<SetCreditLimitForDealer_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        if (response.body().getCode() == 200) {
                            usercode = response.body().getPayload().getUsercode();
                            doc_status = response.body().getPayload().getDoc_status();

                            if (doc_status.equals("Rejected")) {
                                loan_status_tv.setText(response.body().getPayload().getDoc_status());

                                loan_status_tv.setTextColor(Color.parseColor(colorRed));
                                SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON, response.body().getPayload().getReject_reasn(), getActivity());
                                SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS, response.body().getPayload().getDoc_status(), getActivity());

                            } else if (doc_status.equals("Approved")) {
                                loan_status_tv.setText(response.body().getPayload().getDoc_status());

                                String number2 = response.body().getPayload().getCredit_limit();
                                double amount2 = Double.parseDouble(number2);
                                DecimalFormat formatter2 = new DecimalFormat(mDecimalFormat);
                                String formatted2 = formatter2.format(amount2);

                                //  total_credit_limit_tv.setText(rupee_symbol + formatted2);

                                SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON, "Credit Limit Assigned you can raise PO", getActivity());
                                SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS, response.body().getPayload().getDoc_status(), getActivity());
                            } else if (doc_status.equals("Remark")) {
                                loan_status_tv.setText(response.body().getPayload().getDoc_status());
                                // total_credit_limit_tv.setText(rupee_symbol + zero_amount);

                                SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON, response.body().getPayload().getRemark_reasn(), getActivity());
                                SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS, response.body().getPayload().getDoc_status(), getActivity());
                            } else if (doc_status.equals("Approval Pending")) {
                                loan_status_tv.setText(response.body().getPayload().getDoc_status());

                                loan_status_tv.setTextColor(Color.parseColor(colorOrange));

                                info_button.setVisibility(View.GONE);
                                SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON, response.body().getPayload().getRemark_reasn(), getActivity());
                                SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS, response.body().getPayload().getDoc_status(), getActivity());
                            } else if (doc_status.equals("NA")) {
                                loan_status_tv.setText(uploadDocument);

                                loan_status_tv.setTextColor(Color.parseColor(colorOrange));
                                info_button.setVisibility(View.GONE);

                            }

                        } else {

                            Toast.makeText(getActivity(), networkError, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        loan_status_tv.setText(uploadDocument);
                        //    total_credit_limit_tv.setText(rupee_symbol + zero_amount);
                        loan_status_tv.setTextColor(Color.parseColor(colorOrange));
                        //  loan_status_tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange2));
                        info_button.setVisibility(View.GONE);

                    }


                }


                @Override
                public void onFailure(Call<SetCreditLimitForDealer_MODEL> call, Throwable t) {


                    Toast.makeText(getActivity(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals("SubDealer") ) {
            mSetCreditLimitForDealer = restApis.SetCreditLimitForSubDealer(pojo);
            mSetCreditLimitForDealer.enqueue(new Callback<SetCreditLimitForDealer_MODEL>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<SetCreditLimitForDealer_MODEL> call, Response<SetCreditLimitForDealer_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        if (response.body().getCode() == 200) {
                            usercode = response.body().getPayload().getUsercode();
                            doc_status = response.body().getPayload().getDoc_status();

                            if (doc_status.equals("Rejected")) {
                                loan_status_tv.setText(response.body().getPayload().getDoc_status());
                                loan_status_tv.setTextColor(Color.parseColor(colorRed));
                                SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON, response.body().getPayload().getReject_reasn(), getActivity());
                                SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS, response.body().getPayload().getDoc_status(), getActivity());
                                //  total_credit_limit_tv.setText(rupee_symbol + zero_amount);
                            } else if (doc_status.equals("Approved")) {
                                loan_status_tv.setText(response.body().getPayload().getDoc_status());

                                String number2 = response.body().getPayload().getCredit_limit();
                                double amount2 = Double.parseDouble(number2);
                                DecimalFormat formatter2 = new DecimalFormat(mDecimalFormat);
                                String formatted2 = formatter2.format(amount2);

                                //  total_credit_limit_tv.setText(rupee_symbol + formatted2);

                                SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON, "Credit Limit Assigned you can raise PO", getActivity());
                                SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS, response.body().getPayload().getDoc_status(), getActivity());
                            } else if (doc_status.equals("Remark")) {
                                loan_status_tv.setText(response.body().getPayload().getDoc_status());
                                //    total_credit_limit_tv.setText(rupee_symbol + zero_amount);

                                SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON, response.body().getPayload().getRemark_reasn(), getActivity());
                                SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS, response.body().getPayload().getDoc_status(), getActivity());
                            } else if (doc_status.equals("Approval Pending")) {
                                loan_status_tv.setText(response.body().getPayload().getDoc_status());
                                //     total_credit_limit_tv.setText(rupee_symbol + "00");

                                loan_status_tv.setTextColor(Color.parseColor(colorOrange));
                                info_button.setVisibility(View.GONE);
                                SharedPref.saveStringInSharedPref(AppConstants.STATUS_REASON, response.body().getPayload().getRemark_reasn(), getActivity());
                                SharedPref.saveStringInSharedPref(AppConstants.DOC_STATUS, response.body().getPayload().getDoc_status(), getActivity());
                            } else if (doc_status.equals("NA")) {
                                loan_status_tv.setText(uploadDocument);
                                loan_status_tv.setTextColor(Color.parseColor(colorOrange));
                                //   total_credit_limit_tv.setText(rupee_symbol + zero_amount);
                                info_button.setVisibility(View.GONE);

                            }


                        } else {

                            Toast.makeText(getActivity(), networkError, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        loan_status_tv.setText(uploadDocument);
                        //   total_credit_limit_tv.setText(rupee_symbol + zero_amount);
                        loan_status_tv.setTextColor(Color.parseColor(colorOrange));
                        //  loan_status_tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange2));
                        info_button.setVisibility(View.GONE);

                    }


                }


                @Override
                public void onFailure(Call<SetCreditLimitForDealer_MODEL> call, Throwable t) {


                    Toast.makeText(getActivity(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }


    }
    private void GetLoanLimitDetails() {
        custPrograssbar.prograssCreate(getActivity());
        String mUserType;
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getActivity());

        Loanlimitdetails_POJO pojo = new Loanlimitdetails_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getActivity()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<LoanLimit_Details_MODEL> call;
        if (mUserType.equals("SubDealer")) {
            call = restApis.subdealerloanlimitdetails(pojo);
        } else if (mUserType.equals("Dealer")) {
            call = restApis.loanlimitdetails(pojo);
        } else {
            call = restApis.loanlimitdetails(pojo);
        }

        call.enqueue(new Callback<LoanLimit_Details_MODEL>() {
            @Override
            public void onResponse(Call<LoanLimit_Details_MODEL> call, Response<LoanLimit_Details_MODEL> response) {
                if (response.body() != null&&response.body().getCode() == 200) {

                        custPrograssbar.closePrograssBar();

                        financerNametxt.setText(response.body().getPayload().getFinancer());
                        RoiText.setText(response.body().getPayload().getRoi()+"%");
                        TenureTxt.setText(response.body().getPayload().getTenure()+"days");
                        String number1 = response.body().getPayload().getAvailable_limit();
                        double amount = Double.parseDouble(number1);
                        DecimalFormat formatter = new DecimalFormat(mDecimalFormat);
                        String formatted = formatter.format(amount);
                        total_loan_limit_tv.setText(rupee_symbol + formatted);
                        String number2 = response.body().getPayload().getTotal_credit_limit();
                        double amount2 = Double.parseDouble(number2);
                        DecimalFormat formatter2 = new DecimalFormat(mDecimalFormat);
                        String formatted2 = formatter2.format(amount2);
                        total_credit_limit_tv.setText(rupee_symbol+formatted2);
                    }


            }


            @Override
            public void onFailure(Call<LoanLimit_Details_MODEL> call, Throwable t) {

                Toast.makeText(getActivity(), networkError, Toast.LENGTH_LONG).show();
            }

        });


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }







}