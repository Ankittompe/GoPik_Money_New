package com.qts.gopik_money.Utils;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.qts.gopik_money.Adapter.POListItemDetailsAdapter;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Dealer_Fragment.Sub_Dealer_PO_Fragment;
import com.qts.gopik_money.Model.PayloadItem;
import com.qts.gopik_money.Model.RejectPOModel;
import com.qts.gopik_money.Pojo.SubDealerPODetailsResponseModel;
import com.qts.gopik_money.Pojo.POID_POJO;
import com.qts.gopik_money.Pojo.PO_ID_POJO;
import com.qts.gopik_money.Pojo.PO_MODIFY_POJO;
import com.qts.gopik_money.Pojo.PO_ModifyData;
import com.qts.gopik_money.Pojo.PayloadItemData;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.ApiConstraints;
import com.qts.gopik_money.Retro.ItemPlusClickListener;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PODetailsBottomSheet extends Fragment implements PickiTCallbacks {
    FileOutputStream fo;
    String mMultipartFormData = "multipart/form-data";
   TextView mTxtViewPoInvoice, mTxtPOPdfPath, mTxtPdfPath, mTxtBack, mTxtPOIdDetails, mTxtPODate,
            mTxtPODealerName, mTxtPOStatus, mTxtPOTotalQuantity, mTxtRejectPO,
            mTxtModifyPO, mTxtApprovePO, mTxtUploadPoInvoice;

    RecyclerView mRecViewPODetails;
    POListItemDetailsAdapter mPoListItemDetailsAdapter;
    private ArrayList<PayloadItemData> mPOModelArrayList;
    ImageView mImgInvoice, mImgSubDealerInvoice;
    CustPrograssbar custPrograssbar;
    AlertDialog mRejectPOAlertDialog, mApprovePOAlertDialog, mModifyPOAlertDialog;
    Sub_Dealer_PO_Fragment mSub_dealer_po_fragment = new Sub_Dealer_PO_Fragment();
    EditText mEdtPOEstPrice, mEdtInvoiceNo;
    File mInvoiceFile;
    private static final String IMAGE_DIRECTORY = "/GoPikMoney";
    private Dialog dialogCondition;
    ImageView CameraButton, GalleryButton, mSelectPDFButton;
    private int GALLERY = 1;
    private int   mPDF = 3;
    PickiT pickiT;
    ArrayList<PO_ModifyData> mUPoModifyDataArrayList;
    ItemPlusClickListener mItemPlusClickListener;
    LinearLayout mLinViewPo;
    Integer tempmodd = 0;
    Integer tempmod = 0;
    String networkError = "It seems your Network is unstable . Please Try again!";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.podetails_details_bottom_sheet_layout, container, false);
        custPrograssbar = new CustPrograssbar();
        dialogCondition = new Dialog(getActivity());
        pickiT = new PickiT(getActivity(), this, getActivity());
        initData(v);
        receiveData();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return v;
    }

    private void initData(View itemView) {
        mTxtPOIdDetails = itemView.findViewById(R.id.txtPOIdDetails);
        mLinViewPo = itemView.findViewById(R.id.linViewPo);
        mTxtBack = itemView.findViewById(R.id.mTxtBack);
        mTxtPODate = itemView.findViewById(R.id.txtPODate);
        mTxtPODealerName = itemView.findViewById(R.id.txtPODealerName);
        mTxtPOStatus = itemView.findViewById(R.id.txtPOStatus);
        mTxtPOTotalQuantity = itemView.findViewById(R.id.txtPOTotalQuantity);
        mRecViewPODetails = itemView.findViewById(R.id.recPODetails);
        mTxtRejectPO = itemView.findViewById(R.id.txtRejectPO);
        mTxtModifyPO = itemView.findViewById(R.id.txtModifyPO);
        mTxtApprovePO = itemView.findViewById(R.id.txtApprovePO);
        mImgInvoice = itemView.findViewById(R.id.imgInvoice);
        mTxtUploadPoInvoice = itemView.findViewById(R.id.txtUploadPoInvoice);
        mEdtPOEstPrice = itemView.findViewById(R.id.txtPOEstPrice);
        mEdtInvoiceNo = itemView.findViewById(R.id.edtInvoiceNo);
        mTxtPdfPath = itemView.findViewById(R.id.txtPdfPath);
        mTxtPOPdfPath = itemView.findViewById(R.id.txtSubDealerPdfPath);
        mImgSubDealerInvoice = itemView.findViewById(R.id.imgSubDealerInvoice);
        mTxtViewPoInvoice = itemView.findViewById(R.id.txtViewPoInvoice);

        mTxtRejectPO.setOnClickListener(new onRejectPoListener());
        mTxtModifyPO.setOnClickListener(new onModifyPoListener());
        mTxtApprovePO.setOnClickListener(new onApprovePoListener());
        mTxtUploadPoInvoice.setOnClickListener(new onUploadPOInvoiceListener());
        mTxtBack.setOnClickListener(view -> ((MainActivity) requireActivity()).addFragment(mSub_dealer_po_fragment));

        mItemPlusClickListener = (position, mPOModifyData) -> {
            mUPoModifyDataArrayList.set(position, mPOModifyData);
            tempmodd = 0;
            tempmod = 0;
            for (int i = 0; i < mUPoModifyDataArrayList.size(); i++) {
                Log.e("PO Last ", mUPoModifyDataArrayList.get(i).getId() + "=" + mUPoModifyDataArrayList.get(i).getQuantity());
                tempmodd = tempmod + Integer.parseInt(mUPoModifyDataArrayList.get(i).getQuantity());
                tempmod = tempmodd;
                mTxtPOTotalQuantity.setText("" + tempmod);
            }
        };


    }

    @Override
    public void PickiTonUriReturned() {
        //Do Nothing
    }

    @Override
    public void PickiTonStartListener() {
        //Do Nothing
    }

    @Override
    public void PickiTonProgressUpdate(int progress) {
        //Do Nothing
    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        mInvoiceFile = new File(path);
        mTxtPdfPath.setVisibility(View.VISIBLE);
        mImgInvoice.setVisibility(View.GONE);
        mTxtPdfPath.setText(path);
    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {
        //DO NOTHING
    }

    private class onRejectPoListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
            ViewGroup viewGroup = view.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.custom_dialog2, viewGroup, false);
            builder.setView(dialogView);
            mRejectPOAlertDialog = builder.create();
            Button mBtnYes = dialogView.findViewById(R.id.btnYes);
            Button mBtnNo = dialogView.findViewById(R.id.btnNo);
//
            mBtnYes.setOnClickListener(view12 -> {
                removePO(mTxtPOIdDetails.getText().toString().trim());

            });
            mBtnNo.setOnClickListener(view1 -> {
                mRejectPOAlertDialog.dismiss();
            });
            mRejectPOAlertDialog.show();
        }
    }

    private class onApprovePoListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
            ViewGroup viewGroup = view.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.approve_po_dialog, viewGroup, false);
            builder.setView(dialogView);
            mApprovePOAlertDialog = builder.create();
            Button mBtnYes = dialogView.findViewById(R.id.btnYes);
            Button mBtnNo = dialogView.findViewById(R.id.btnNo);
            mBtnYes.setOnClickListener(view12 -> {
                if (!mEdtPOEstPrice.getText().toString().trim().isEmpty()) {
                    if (!mEdtInvoiceNo.getText().toString().trim().isEmpty()) {
                        ApprovePO(mTxtPOIdDetails.getText().toString().trim(),
                                mEdtInvoiceNo.getText().toString().trim(),
                                mEdtPOEstPrice.getText().toString().trim(),
                                mInvoiceFile);
                    } else {
                        Toast.makeText(getActivity(), "Invoice number should not be empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Est. Price should not be empty", Toast.LENGTH_SHORT).show();
                }
            });
            mBtnNo.setOnClickListener(view1 -> {
                mApprovePOAlertDialog.dismiss();
            });
            mApprovePOAlertDialog.show();
        }
    }

    private class onModifyPoListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
            ViewGroup viewGroup = view.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.modify_po_dialog, viewGroup, false);
            builder.setView(dialogView);
            mModifyPOAlertDialog = builder.create();
            Button mBtnYes = dialogView.findViewById(R.id.btnYes);
            Button mBtnNo = dialogView.findViewById(R.id.btnNo);
            mBtnYes.setOnClickListener(view12 -> {
                if (!mEdtPOEstPrice.getText().toString().trim().isEmpty()) {
                    if (!mEdtInvoiceNo.getText().toString().trim().isEmpty()) {
                        ModifyPO(mTxtPOIdDetails.getText().toString().trim(),
                                mEdtInvoiceNo.getText().toString().trim(),
                                mEdtPOEstPrice.getText().toString().trim(),
                                mUPoModifyDataArrayList, mInvoiceFile);
                    } else {
                        Toast.makeText(getActivity(), "Invoice number should not be empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Est. Price should not be empty", Toast.LENGTH_SHORT).show();
                }
            });
            mBtnNo.setOnClickListener(view1 -> {
                mModifyPOAlertDialog.dismiss();
            });
            mModifyPOAlertDialog.show();
        }
    }

    private class onUploadPOInvoiceListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            SelectImageDialog();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mImgInvoice.setImageBitmap(bitmap);
            mInvoiceFile = new File(saveImageFile(bitmap));
            mTxtPdfPath.setVisibility(View.GONE);
            mImgInvoice.setVisibility(View.VISIBLE);
        } else if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), contentURI);
                    mImgInvoice.setImageBitmap(bitmap);
                    mInvoiceFile = new File(saveImageFile(bitmap));
                    mTxtPdfPath.setVisibility(View.GONE);
                    mImgInvoice.setVisibility(View.VISIBLE);
                } catch (IOException e) {

                    e.printStackTrace();


                }
            }
        } else if (requestCode == mPDF) {
            pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
        }
    }

    private void SelectImageDialog() {
        dialogCondition.setContentView(R.layout.bottomsheet_upload_image_dailog);
        CameraButton = dialogCondition.findViewById(R.id.camera_button);
        GalleryButton = dialogCondition.findViewById(R.id.gallery_button);
        mSelectPDFButton = dialogCondition.findViewById(R.id.pdf_button);
        dialogCondition.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);
        dialogCondition.show();

        CameraButton.setOnClickListener(view -> {
            dialogCondition.dismiss();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 7);
        });
        GalleryButton.setOnClickListener(view -> {
            dialogCondition.dismiss();
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY);
        });
        mSelectPDFButton.setOnClickListener(view -> {
            dialogCondition.dismiss();
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select File"), mPDF);
        });
    }

    private void receiveData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            PayloadItem mPayloadItem = (PayloadItem) bundle.getSerializable("SerializableData");
            switch (mPayloadItem.getStatus()) {
                case "Approved":
                case "Approved by dealer":
                case "Rejected by dealer":
                case "Approved by subdealer":
                case "Approved at OEM":
                case "Approved By Dealer":
                case "Approved by financer":
                case "Disbursed by financer":
                case "Approved at dealer":
                case "Awaiting Disbursal":
                case "Modified by dealer":
                case "Modified at OEM":
                case "Rejected at OEM":
                case "Rejected By Dealer":
                case "Rejected by financer":
                case "Rejected by subdealer":
                case "Pending at OEM":
                    mTxtApprovePO.setVisibility(View.GONE);
                    mTxtRejectPO.setVisibility(View.GONE);
                    mTxtModifyPO.setVisibility(View.GONE);
                    mEdtPOEstPrice.setEnabled(false);
                    mEdtInvoiceNo.setEnabled(false);
                    mTxtUploadPoInvoice.setEnabled(false);
                    break;
                case "Pending at dealer":
                    break;
            }
            getPODetails(mPayloadItem.getPo_id());
        } else {
            Log.e("", "Empty Data");
        }
    }

    private void getPODetails(String mPoId) {
        custPrograssbar.prograssCreate(getContext());
        POID_POJO pojo = new POID_POJO(mPoId);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<SubDealerPODetailsResponseModel> call = restApis.SubDealerPODetails(pojo);
        call.enqueue(new Callback<SubDealerPODetailsResponseModel>() {
            @Override
            public void onResponse(Call<SubDealerPODetailsResponseModel> call, Response<SubDealerPODetailsResponseModel> response) {
                custPrograssbar.closePrograssBar();
                mPOModelArrayList = new ArrayList<>();
                mUPoModifyDataArrayList = new ArrayList<>();
                mPoListItemDetailsAdapter = new POListItemDetailsAdapter(mPOModelArrayList, mItemPlusClickListener);
                assert response.body() != null;

                if (response.body().getPayload().size() != 0) {
                    for (int i = 0; i < response.body().getPayload().size(); i++) {
                        mTxtPOIdDetails.setText(response.body().getPayload().get(i).getPo_id());
                        mTxtPODate.setText(response.body().getPayload().get(i).getDate());
                        mTxtPOStatus.setText(response.body().getPayload().get(i).getStatus());
                        if (!response.body().getPayload().get(i).getInvoice_no().equals("NA")) {
                            mEdtInvoiceNo.setText(response.body().getPayload().get(i).getInvoice_no());
                        }
                        if (!response.body().getPayload().get(i).getDealer_name().equals("NA")) {
                            mTxtPODealerName.setText(response.body().getPayload().get(i).getDealer_name());
                        } else {
                            mTxtPODealerName.setText("");
                        }
                        if (!response.body().getPayload().get(i).getTotal_price().equals("NA")) {
                            mEdtPOEstPrice.setText(response.body().getPayload().get(i).getTotal_price());
                        }
                        if (!response.body().getPayload().get(i).getInvoice_file().equals("NA")) {
                            String mPath = ApiConstraints.IMG_URL + response.body().getPayload().get(i).getInvoice_file();
                            Log.e("Invoice mPath", mPath);
                            String extension = mPath.substring(mPath.lastIndexOf("."));
                            if (extension.equals(".pdf")) {
                                mTxtPdfPath.setVisibility(View.VISIBLE);
                                mImgInvoice.setVisibility(View.GONE);
                                mTxtPdfPath.setText(mPath);
                                mTxtViewPoInvoice.setOnClickListener(view -> {
                                    URL url;
                                    try {
                                        url = new URL(mPath);
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(url))));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                            } else {
                                mTxtPdfPath.setVisibility(View.GONE);
                                mImgInvoice.setVisibility(View.VISIBLE);

                                try {
                                    URL newurl = new URL(mPath);
                                    Bitmap mIcon_val = null;
                                    mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                    mImgInvoice.setImageBitmap(mIcon_val);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (!response.body().getPayload().get(i).getPo_img().equals("NA")) {
                            mLinViewPo.setVisibility(View.VISIBLE);
                            String mPath = ApiConstraints.IMG_URL + response.body().getPayload().get(i).getPo_img();
                            Log.e("PO Img mPath", mPath);
                            String extension = mPath.substring(mPath.lastIndexOf("."));
                            if (extension.equals(".pdf")) {
                                mTxtPOPdfPath.setVisibility(View.VISIBLE);
                                mImgSubDealerInvoice.setVisibility(View.GONE);
                                mTxtPOPdfPath.setText(mPath);

                            } else {
                                mTxtPOPdfPath.setVisibility(View.GONE);
                                mImgSubDealerInvoice.setVisibility(View.VISIBLE);
                                try {
                                    URL newurl = new URL(mPath);
                                    Bitmap mIcon_val = null;
                                    mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                    mImgSubDealerInvoice.setImageBitmap(mIcon_val);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            mLinViewPo.setVisibility(View.GONE);
                        }

                        mPOModelArrayList.add(response.body().getPayload().get(i));
                        mUPoModifyDataArrayList.add(new PO_ModifyData(response.body().getPayload().get(i).getId(), response.body().getPayload().get(i).getProdt_quantity()));
                        mRecViewPODetails.setHasFixedSize(true);
                        mRecViewPODetails.setLayoutManager(new LinearLayoutManager(requireActivity()));
                        mRecViewPODetails.setAdapter(mPoListItemDetailsAdapter);

                        if (!response.body().getPayload().get(i).getUpdate_quantity().equals("0")) {
                            tempmodd = tempmod + Integer.parseInt(response.body().getPayload().get(i).getUpdate_quantity());
                        }else{
                            tempmodd = tempmod + Integer.parseInt(response.body().getPayload().get(i).getProdt_quantity());
                        }
                        tempmod = tempmodd;
                        mTxtPOTotalQuantity.setText("" + tempmod);
                    }
                } else {
                    Toast.makeText(getContext(), "Data is empty", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SubDealerPODetailsResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                custPrograssbar.closePrograssBar();
            }
        });
    }

    private void removePO(String mPoId) {
        custPrograssbar.prograssCreate(getContext());
        PO_ID_POJO pojo = new PO_ID_POJO(mPoId);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<RejectPOModel> call = restApis.RejectPO(pojo);
        call.enqueue(new Callback<RejectPOModel>() {
            @Override
            public void onResponse(Call<RejectPOModel> call, Response<RejectPOModel> response) {
                custPrograssbar.closePrograssBar();
                mRejectPOAlertDialog.dismiss();
                ((MainActivity) getActivity()).addFragment(mSub_dealer_po_fragment);
            }

            @Override
            public void onFailure(Call<RejectPOModel> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                custPrograssbar.closePrograssBar();
            }
        });
    }

    private void ApprovePO(String mPoId, String mInvoiceNo, String mEstAmt, File mInvoiceFile) {
        custPrograssbar.prograssCreate(getContext());
        RequestBody PoId = RequestBody.create(MediaType.parse(mMultipartFormData), mPoId);
        RequestBody InvoiceNo = RequestBody.create(MediaType.parse(mMultipartFormData), mInvoiceNo);
        RequestBody EstAmt = RequestBody.create(MediaType.parse(mMultipartFormData), mEstAmt);
        RequestBody mFile1;
        MultipartBody.Part InvoiceFile;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), mInvoiceFile);
        InvoiceFile = MultipartBody.Part.createFormData("invoice", mInvoiceFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<RejectPOModel> call = restApis.ApprovePO(PoId, InvoiceNo, EstAmt, InvoiceFile);
        call.enqueue(new Callback<RejectPOModel>() {
            @Override
            public void onResponse(Call<RejectPOModel> call, Response<RejectPOModel> response) {
                if (response.body() != null&&response.body().getCode() == 200) {

                        custPrograssbar.closePrograssBar();
                        mApprovePOAlertDialog.dismiss();
                        ((MainActivity) requireActivity()).addFragment(mSub_dealer_po_fragment);

                }
            }

            @Override
            public void onFailure(Call<RejectPOModel> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ModifyPO(String mPoId, String mInvoiceNo, String mEstAmt, ArrayList<PO_ModifyData> mUPoModifyDataArrayList, File mInvoiceFile) {
        custPrograssbar.prograssCreate(getContext());
        PO_MODIFY_POJO mModify_pojo = new PO_MODIFY_POJO(mPoId, mInvoiceNo, mEstAmt, mUPoModifyDataArrayList);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<RejectPOModel> call = restApis.ModifyPO(mModify_pojo);
        call.enqueue(new Callback<RejectPOModel>() {
            @Override
            public void onResponse(Call<RejectPOModel> call, Response<RejectPOModel> response) {
                if (response.body() != null&&response.body().getCode() == 200) {

                        custPrograssbar.closePrograssBar();
                        mModifyPOAlertDialog.dismiss();
                        UploadModifiedPoFile(mPoId, mInvoiceFile);

                }
            }

            @Override
            public void onFailure(Call<RejectPOModel> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void UploadModifiedPoFile(String mPoId, File mInvoiceFile) {
        custPrograssbar.prograssCreate(getContext());
        RequestBody PoId = RequestBody.create(MediaType.parse(mMultipartFormData), mPoId);
        RequestBody mFile1;
        MultipartBody.Part InvoiceFile;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), mInvoiceFile);
        InvoiceFile = MultipartBody.Part.createFormData("po_img", mInvoiceFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<RejectPOModel> call = restApis.ModifyImagePODocUpload(PoId, InvoiceFile);
        call.enqueue(new Callback<RejectPOModel>() {
            @Override
            public void onResponse(Call<RejectPOModel> call, Response<RejectPOModel> response) {
                if (response.body() != null&&response.body().getCode() == 200) {

                        custPrograssbar.closePrograssBar();
                        ((MainActivity) requireActivity()).addFragment(mSub_dealer_po_fragment);

                }
            }

            @Override
            public void onFailure(Call<RejectPOModel> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });
    }

    public String saveImageFile(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);
        Log.e("Full Path", Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            Log.e("TAG", "File Saved5");
            wallpaperDirectory.mkdir();
            Log.e("Yest", Boolean.toString(wallpaperDirectory.mkdir()));

        }
        try {
            File f = new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "GoPikMoney");
            f.createNewFile();
            fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(requireActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);

            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        return "";
    }

}