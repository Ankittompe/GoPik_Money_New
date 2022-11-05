package com.qts.gopik_money.Dealer_Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Interfaces.ItemAddListener;
import com.qts.gopik_money.Model.GetCatproductModel;
import com.qts.gopik_money.Model.TagID_MODEL;
import com.qts.gopik_money.Pojo.GetCatproductPojo;
import com.qts.gopik_money.Pojo.ProductDetailsItem;
import com.qts.gopik_money.Pojo.TAG_ID_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.ItemRemoveClickListener;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoatDataAdapter extends RecyclerView.Adapter<GoatDataAdapter.ViewHolder> {
    Context context;
    ArrayList<String> mProductCatListArrayList;
    ArrayList<String> mProductCatListPriceArrayList;
    ArrayList<ProductDetailsItem> mFinalProductDetailsItemArrayList;
    String[] mGenderList = {"Select Gender", "Male", "Female"};
    ArrayAdapter mArrayAdapterGender, mArrayAdapterSpinnerBreed;
    CustPrograssbar custPrograssbar;
    ItemAddListener mItemAddListener;
    ItemRemoveClickListener mItemRemoveClickListener;
    ProductDetailsItem mProductsListModel;
    private String mBreed = "";

    private String mGender = "";


    public GoatDataAdapter(Context context, ArrayList<ProductDetailsItem> mFinalProductDetailsItemArrayList, ItemAddListener mItemAddListener, ItemRemoveClickListener mItemRemoveClickListener) {
        this.context = context;
        this.mFinalProductDetailsItemArrayList = mFinalProductDetailsItemArrayList;
        this.mItemAddListener = mItemAddListener;
        this.mItemRemoveClickListener = mItemRemoveClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goat_products_add_items_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        int position = holder.getAdapterPosition();

        mProductsListModel = mFinalProductDetailsItemArrayList.get(position);
        if (position > 0) {
            holder.mBtnRemove.setVisibility(View.VISIBLE);
            getCatProductList(holder);

        } else {
            mProductCatListPriceArrayList = new ArrayList<>();
            mProductCatListArrayList = new ArrayList<>();
            mProductCatListArrayList.add(mProductsListModel.getBreed());
            mProductCatListPriceArrayList.add(mProductsListModel.getPrice());
            mArrayAdapterSpinnerBreed = new ArrayAdapter(context, android.R.layout.simple_spinner_item, mProductCatListArrayList);
            mArrayAdapterSpinnerBreed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.mSpinnerBreed.setAdapter(mArrayAdapterSpinnerBreed);
        }

        custPrograssbar = new CustPrograssbar();
        if (mProductsListModel.getPrice() == null) {
            holder.mEdtListPerKgPrice.setText("");
        } else {
            holder.mEdtListPerKgPrice.setText(mProductsListModel.getPrice());
        }
        holder.mTxtGoatDataCount.setText("GoatData:-" + (position + 1));

        mArrayAdapterGender = new ArrayAdapter(context, android.R.layout.simple_spinner_item, mGenderList);
        mArrayAdapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.mSpinnerGender.setAdapter(mArrayAdapterGender);


        holder.mSpinnerBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = holder.mSpinnerBreed.getSelectedItemPosition();
                Log.e("mGenderList ", "pos " + position + "=" + mProductCatListArrayList.get(index));
                mBreed = mProductCatListArrayList.get(index);
                holder.mEdtListPerKgPrice.setText(mProductCatListPriceArrayList.get(i));
                mItemAddListener.onClick(position, new ProductDetailsItem(mBreed, holder.mEdtListPerKgPrice.getText().toString().trim(), holder.mEdtListTagId.getText().toString().trim(), mGender, "" + holder.mEdtListWeight.getText().toString().trim()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing to Do
            }
        });
        holder.mEdtListPerKgPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to Do
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to Do
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    String mEdtPrice = editable.toString();

                    mItemAddListener.onClick(position, new ProductDetailsItem(mBreed, mEdtPrice, holder.mEdtListTagId.getText().toString().trim(), mGender, "" + holder.mEdtListWeight.getText().toString().trim()));
                }
            }
        });
        holder.mEdtListTagId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to Do
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to Do
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    String mEdtTag = editable.toString();
                    CallEnterTagIdAPI(mEdtTag, pos, holder);
                }
            }
        });
        holder.mSpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = holder.mSpinnerGender.getSelectedItemPosition();

                mGender = mGenderList[index];

                mItemAddListener.onClick(position, new ProductDetailsItem(mBreed, holder.mEdtListPerKgPrice.getText().toString().trim(), holder.mEdtListTagId.getText().toString().trim(), mGender, "" + holder.mEdtListWeight.getText().toString().trim()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing to Do
            }
        });
        holder.mEdtListWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to Do
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to Do
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    int mWeight = Integer.parseInt(editable.toString());
                    mItemAddListener.onClick(position, new ProductDetailsItem(mBreed, holder.mEdtListPerKgPrice.getText().toString().trim(), holder.mEdtListTagId.getText().toString().trim(), mGender, "" + mWeight));
                }
            }
        });

        holder.mBtnRemove.setOnClickListener(view -> {
            mItemRemoveClickListener.onRemoveClick(position);
            Log.e("removed","-->>");
        });
    }


    private void CallEnterTagIdAPI(String mTagID, int pos, ViewHolder holder) {
        TAG_ID_POJO mTagIdPojo = new TAG_ID_POJO(mTagID);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<TagID_MODEL> call = restApis.CheckGoatTagId(mTagIdPojo);
        call.enqueue(new Callback<TagID_MODEL>() {
            @Override
            public void onResponse(Call<TagID_MODEL> call, Response<TagID_MODEL> response) {
                if (response.code() == 200) {
                    Log.e("response ", "success");
                    mItemAddListener.onClick(pos, new ProductDetailsItem(mBreed, holder.mEdtListPerKgPrice.getText().toString().trim(), mTagID, mGender, "" + holder.mEdtListWeight.getText().toString().trim()));
                } else {
                    Toast.makeText(context, "Tagid already exists!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TagID_MODEL> call, Throwable t) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFinalProductDetailsItemArrayList.size();
    }

    private void getCatProductList(ViewHolder holder) {
        GetCatproductPojo pojo = new GetCatproductPojo(SharedPref.getStringFromSharedPref(AppConstants.CAT_TYPE, context),
                SharedPref.getStringFromSharedPref(AppConstants.CAT_NAME, context), SharedPref.getStringFromSharedPref(AppConstants.BRAND, context)
        );

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<GetCatproductModel> call = restApis.getCatproduct(pojo);
        call.enqueue(new Callback<GetCatproductModel>() {
            @Override
            public void onResponse(Call<GetCatproductModel> call, Response<GetCatproductModel> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equals("200")) {
                        mProductCatListArrayList = new ArrayList<>();
                        mProductCatListPriceArrayList = new ArrayList<>();
                        if (response.body().getPayload().size() != 0) {
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                mProductCatListArrayList.add(response.body().getPayload().get(i).getProd_name());
                                mProductCatListPriceArrayList.add(response.body().getPayload().get(i).getProd_mrp());
                            }
                            mArrayAdapterSpinnerBreed = new ArrayAdapter(context, android.R.layout.simple_spinner_item, mProductCatListArrayList);
                            mArrayAdapterSpinnerBreed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            holder.mSpinnerBreed.setAdapter(mArrayAdapterSpinnerBreed);
                        } else {
                            Toast.makeText(context, "Empty Category List", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, "Something went wrong!234!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCatproductModel> call, Throwable t) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText mEdtListTagId;
        EditText  mEdtListWeight;
        EditText  mEdtListPerKgPrice;
        TextView mTxtGoatDataCount;
        Spinner mSpinnerGender;
        Spinner  mSpinnerBreed;
        Button mBtnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBtnRemove = itemView.findViewById(R.id.btnRemove);
            mSpinnerGender = itemView.findViewById(R.id.spinnerGender);
            mSpinnerBreed = itemView.findViewById(R.id.spinnerBreed);
            mEdtListPerKgPrice = itemView.findViewById(R.id.edtListPerKgPrice);
            mTxtGoatDataCount = itemView.findViewById(R.id.txtGoatDataCount);
            mEdtListTagId = itemView.findViewById(R.id.edtListTagId);
            mEdtListWeight = itemView.findViewById(R.id.edtListWeight);
        }
    }
}
