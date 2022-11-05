package com.qts.gopik_money.Shopkeeper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Adapter.ScannedQRListAdapter;
import com.qts.gopik_money.Dealer_Adapter.WhatsAppStatusListAdapter;
import com.qts.gopik_money.Model.Dealer_QR_MODEL;
import com.qts.gopik_money.Model.QRSummaryModel;
import com.qts.gopik_money.Model.QR_DataList_MODEL;
import com.qts.gopik_money.Model.QR_ScannedList_MODEL;
import com.qts.gopik_money.Model.Shopkeeper_profile_details_MODEL;
import com.qts.gopik_money.Model.WhatsAppStatusList_MODEL;
import com.qts.gopik_money.Pojo.Dealer_CODE_POJO;
import com.qts.gopik_money.Pojo.Dealer_WhatsApp_POJO;
import com.qts.gopik_money.Pojo.QR_CODE_POJO;
import com.qts.gopik_money.Pojo.Shopkeeper_profile_details_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopkeeperQRScan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopkeeperQRScan extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ImageView mImgOpenQR;
    ImageView mImgShare;
    WebView mWebVw;
    String networkError = "It seems your Network is unstable . Please Try again!";
    CustPrograssbar custPrograssbar;
    private ArrayList<QR_DataList_MODEL> mQRScannedList;
    private ArrayList<Dealer_WhatsApp_POJO> mWAppListModelArrayList;

    RecyclerView mRecVwScannedList;
    RecyclerView  mRecVwStatusList;
    ScannedQRListAdapter mMyListAdapter;
    WhatsAppStatusListAdapter mWhatsAppStatusListAdapter;
    private TextView mTxtGenerateQRCode;
    private  TextView mTxtShowScannedList;
    private  TextView mTxtShowWhatsAppStatus;
    private  TextView mTxtName;
    private  TextView mTxtUserName;
    private  TextView mTxtTotalScan;
    private  TextView mTxtTotalSubMission;
    private  TextView mTxtTotalApprovals;
    private  TextView mTxtTotalExpCommission;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters

    public ShopkeeperQRScan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopkeeperQRScan.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopkeeperQRScan newInstance(String param1, String param2) {
        ShopkeeperQRScan fragment = new ShopkeeperQRScan();
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
        return inflater.inflate(R.layout.fragment_shopkeeper_q_r_scan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(view);
    }

    private void initData(View view) {
        mImgOpenQR = view.findViewById(R.id.imgOpenQR);
        mWebVw = view.findViewById(R.id.webVw);
        mTxtGenerateQRCode = view.findViewById(R.id.txtGenQRCode);
        mTxtShowScannedList = view.findViewById(R.id.txtScannedList);
        mTxtShowWhatsAppStatus = view.findViewById(R.id.txtTitleWhatsAppList);
        mRecVwScannedList = view.findViewById(R.id.recVwScannedList);
        mRecVwStatusList = view.findViewById(R.id.recVwStatusList);
        mTxtName = view.findViewById(R.id.txtOne);
        mTxtUserName = view.findViewById(R.id.txtUserName);
        mImgShare = view.findViewById(R.id.imgShare);
        custPrograssbar = new CustPrograssbar();
        mTxtGenerateQRCode.setOnClickListener(view1 -> {
            setQRStatus();
        });
        setQRStatus();
        shopkeeper_profile_details();



//        getQRStatus();
//        getQRCount();
        mTxtShowWhatsAppStatus.setBackground(getResources().getDrawable(R.drawable.button_click, null));
        mTxtShowScannedList.setBackground(getResources().getDrawable(R.drawable.button_click_grey, null));
        mRecVwScannedList.setVisibility(View.GONE);
        mRecVwStatusList.setVisibility(View.VISIBLE);
        getWhatsAppStatusList();

        mTxtShowScannedList.setOnClickListener(view12 -> {
            getQRCount();
            mRecVwStatusList.setVisibility(View.GONE);
            mRecVwScannedList.setVisibility(View.VISIBLE);
            mTxtShowWhatsAppStatus.setBackground(getResources().getDrawable(R.drawable.button_click_grey, null));
            mTxtShowScannedList.setBackground(getResources().getDrawable(R.drawable.button_click, null));
            getSummaryData();
        });
        mTxtShowWhatsAppStatus.setOnClickListener(view13 -> {
            mRecVwScannedList.setVisibility(View.GONE);
            mRecVwStatusList.setVisibility(View.VISIBLE);
            getWhatsAppStatusList();
            mTxtShowScannedList.setBackground(getResources().getDrawable(R.drawable.button_click_grey, null));
            mTxtShowWhatsAppStatus.setBackground(getResources().getDrawable(R.drawable.button_click, null));
            getSummaryData();
        });
        mImgOpenQR.setOnClickListener(view14 -> {
            HomeShopkeeper homeShopkeeper = new HomeShopkeeper();
            homeShopkeeper.openDrawer(getActivity());
        });
        generateQRCode();
        mImgShare.setOnClickListener(view15 -> {
            URL url = null;
            try {
                /*   url = new URL("https://filesamples.com/samples/document/pdf/sample2.pdf");*/
                url = new URL("https://gopikmoney.com/public/getPDFlink?user_id="+ SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(url))));
            } catch (Exception e) {
                e.printStackTrace();
            }

          /*  Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.scanone);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Title", null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, "Select"));*/
        });

        mTxtTotalScan = view.findViewById(R.id.txtTotalScan);
        mTxtTotalApprovals = view.findViewById(R.id.txtTotalApprovals);
        mTxtTotalSubMission = view.findViewById(R.id.txtTotalSubmission);
        mTxtTotalExpCommission = view.findViewById(R.id.txtTotalExpCommission);

        getSummaryData();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void generateQRCode() {
        String mUserCode = SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getActivity());
        String wedData = "https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=https://gopikmoney.com/public/QRScan?source_id=" + mUserCode + "&chco=000000";
        mWebVw.setWebViewClient(new MyBrowser());
        mWebVw.getSettings().setLoadsImagesAutomatically(true);
        mWebVw.getSettings().setJavaScriptEnabled(true);
        mWebVw.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebVw.loadUrl(wedData);
    }

    private static class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void getWhatsAppStatusList() {
        custPrograssbar.prograssCreate(getContext());
        Dealer_CODE_POJO pojo = new Dealer_CODE_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
//        Dealer_CODE_POJO pojo = new Dealer_CODE_POJO("82513");
        RestApis restApis = NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<WhatsAppStatusList_MODEL> call = restApis.Get_WhatsApp_Status(pojo);
        call.enqueue(new Callback<WhatsAppStatusList_MODEL>() {
            @Override
            public void onResponse(Call<WhatsAppStatusList_MODEL> call, Response<WhatsAppStatusList_MODEL> response) {
                custPrograssbar.closePrograssBar();
                mWAppListModelArrayList = new ArrayList<>();
                mWAppListModelArrayList.clear();
                mWhatsAppStatusListAdapter = new WhatsAppStatusListAdapter(mWAppListModelArrayList);
                mWhatsAppStatusListAdapter.notifyDataSetChanged();
                if (response.body().getData().size() != 0) {
                    mWAppListModelArrayList.addAll(response.body().getData());
                    mRecVwStatusList.setHasFixedSize(true);
                    mRecVwStatusList.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    mRecVwStatusList.setAdapter(mWhatsAppStatusListAdapter);

                } else {
                    Toast.makeText(getContext(), "Data is empty", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WhatsAppStatusList_MODEL> call, Throwable t) {
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                custPrograssbar.closePrograssBar();
            }
        });
    }

    private void shopkeeper_profile_details() {
        custPrograssbar.prograssCreate(getContext());
        Shopkeeper_profile_details_POJO pojo = new Shopkeeper_profile_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getActivity()), SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getActivity()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeper_profile_details_MODEL> call = restApis.shopkeeper_profile_details(pojo);
        call.enqueue(new Callback<Shopkeeper_profile_details_MODEL>() {
            @Override
            public void onResponse(Call<Shopkeeper_profile_details_MODEL> call, Response<Shopkeeper_profile_details_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        mTxtName.setText(response.body().getPayload().getProfile().get(0).getName());
                        mTxtUserName.setText(response.body().getPayload().getProfile().get(0).getName());

                    } else {
                        Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Shopkeeper_profile_details_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }

    private void setQRStatus() {
        custPrograssbar.prograssCreate(getContext());
        QR_CODE_POJO pojo = new QR_CODE_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_QR_MODEL> call = restApis.Set_QR_Status(pojo);
        call.enqueue(new Callback<Dealer_QR_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_QR_MODEL> call, Response<Dealer_QR_MODEL> response) {
//                custPrograssbar.closePrograssBar();

//                mTxtGenerateQRCode.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Dealer_QR_MODEL> call, Throwable t) {
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
//                custPrograssbar.closePrograssBar();
            }
        });
    }

    private void getQRStatus() {
        custPrograssbar.prograssCreate(getContext());
        QR_CODE_POJO pojo = new QR_CODE_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_QR_MODEL> call = restApis.Get_QR_Status(pojo);
        call.enqueue(new Callback<Dealer_QR_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_QR_MODEL> call, Response<Dealer_QR_MODEL> response) {
                custPrograssbar.closePrograssBar();

                if (response.body().getQRStatus().equals("0")) {
                    mTxtGenerateQRCode.setVisibility(View.VISIBLE);
                } else {
                    mTxtGenerateQRCode.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Dealer_QR_MODEL> call, Throwable t) {
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                custPrograssbar.closePrograssBar();
            }
        });
    }

    private void getQRCount() {
        custPrograssbar.prograssCreate(getContext());
        QR_CODE_POJO pojo = new QR_CODE_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        RestApis restApis = NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<QR_ScannedList_MODEL> call = restApis.Get_Total_Count(pojo);
        call.enqueue(new Callback<QR_ScannedList_MODEL>() {
            @Override
            public void onResponse(Call<QR_ScannedList_MODEL> call, Response<QR_ScannedList_MODEL> response) {
                custPrograssbar.closePrograssBar();
                mQRScannedList = new ArrayList<>();
                mQRScannedList.clear();
                mMyListAdapter = new ScannedQRListAdapter(mQRScannedList);
                mMyListAdapter.notifyDataSetChanged();
                if (response.body().getData().size() != 0) {
                    mQRScannedList.addAll(response.body().getData());
                    mRecVwScannedList.setHasFixedSize(true);
                    mRecVwScannedList.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    mRecVwScannedList.setAdapter(mMyListAdapter);
                    mMyListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<QR_ScannedList_MODEL> call, Throwable t) {
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                custPrograssbar.closePrograssBar();
            }
        });
    }
    private void getSummaryData() {
        custPrograssbar.prograssCreate(getContext());
        QR_CODE_POJO pojo = new QR_CODE_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        RestApis restApis = NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<QRSummaryModel> call = restApis.Get_Total_Summary(pojo);
        call.enqueue(new Callback<QRSummaryModel>() {
            @Override
            public void onResponse(Call<QRSummaryModel> call, Response<QRSummaryModel> response) {
                custPrograssbar.closePrograssBar();
                if (response.code() == 200) {
                    mTxtTotalScan.setText(""+response.body().getData().getTotalScan());
                    mTxtTotalApprovals.setText(""+response.body().getData().getTotalApproval());
                    mTxtTotalSubMission.setText(""+response.body().getData().getTotalSubmission());
                    mTxtTotalExpCommission.setText(""+response.body().getData().getTotalPending());
                } else {
                    mTxtTotalScan.setText("0");
                    mTxtTotalApprovals.setText("0");
                    mTxtTotalSubMission.setText("0");
                    mTxtTotalExpCommission.setText("0");
                }
            }
            @Override
            public void onFailure(Call<QRSummaryModel> call, Throwable t) {
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                custPrograssbar.closePrograssBar();
            }
        });
    }


}