package com.qts.gopik_money.Dealer_Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Dealer_Activity.SubDealerEdit;
import com.qts.gopik_money.Model.Dealer_Subuser_action_MODEL;
import com.qts.gopik_money.Model.FetechSubuserModel;
import com.qts.gopik_money.Pojo.Dealer_Subuser_action_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserAdapter extends RecyclerView.Adapter<AddUserAdapter.ViewHolder> implements Filterable {
    Context context;
    String x;
    String mActive ="Active" ;
    String networkError = "It seems your Network is unstable . Please Try again!";
    ArrayList<FetechSubuserModel> mSubUserModelArrayList;
    ArrayList<FetechSubuserModel> listFilter;

    public AddUserAdapter(Context context, ArrayList<FetechSubuserModel> mSubUserModelArrayList) {
        this.context = context;
        this.mSubUserModelArrayList = mSubUserModelArrayList;
        this.listFilter = new ArrayList<>(mSubUserModelArrayList);

    }

    public void setList( ArrayList<FetechSubuserModel> mSubUserModelArrayList) {
        this.mSubUserModelArrayList = mSubUserModelArrayList;
        this.listFilter = new ArrayList<>(mSubUserModelArrayList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FetechSubuserModel mFetchSubUserModel = mSubUserModelArrayList.get(position);

        holder.mobileuser.setText(mFetchSubUserModel.getMob());
        holder.roleuser.setText(mFetchSubUserModel.getRole());
        holder.statususer.setText(mFetchSubUserModel.getStatus());
        holder.nameuser.setText(mFetchSubUserModel.getSub_user());

        SharedPref.saveStringInSharedPref(AppConstants.SUBUName, mFetchSubUserModel.getSub_user(), context);
        SharedPref.saveStringInSharedPref(AppConstants.SUBUMOB, mFetchSubUserModel.getMob(), context);
        SharedPref.saveStringInSharedPref(AppConstants.SUBUROLE, mFetchSubUserModel.getRole(), context);

        if (holder.statususer.getText().equals(mActive)) {
            holder.actionuser.setText("Inactive");
            holder.actionuser.setBackgroundResource(R.drawable.rectangle_red);
        } else {
            holder.actionuser.setText(mActive);
            holder.actionuser.setBackgroundResource(R.drawable.rectangle_blue);
        }
        SharedPref.saveStringInSharedPref(AppConstants.SUBUSERID, mFetchSubUserModel.getId(), context);
        SharedPref.saveStringInSharedPref(AppConstants.STATUS_SUBUSER, mFetchSubUserModel.getStatus(), context);

        holder.actionuser.setOnClickListener(view -> {
            if (holder.actionuser.getText().equals(mActive)) {
                x = mActive;
            } else {
                x = "Inactive";
            }
            SharedPref.saveStringInSharedPref(AppConstants.SUBUSERID, mFetchSubUserModel.getId(), context);
            Dealer_Subuser_action();
        });

        holder.edit.setOnClickListener(view -> {
            SharedPref.saveStringInSharedPref(AppConstants.SUBUSERID, mFetchSubUserModel.getId(), context);
            Intent it = new Intent(context, SubDealerEdit.class);
            context.startActivity(it);
        });
    }

    private void Dealer_Subuser_action() {
        Dealer_Subuser_action_POJO pojo = new Dealer_Subuser_action_POJO(SharedPref.getStringFromSharedPref(AppConstants.SUBUSERID, context), x);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_Subuser_action_MODEL> call = restApis.Dealer_Subuser_action(pojo);
        call.enqueue(new Callback<Dealer_Subuser_action_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_Subuser_action_MODEL> call, Response<Dealer_Subuser_action_MODEL> response) {
                if (response.body() != null&&response.body().getCode().equals("200")) {

                        Intent it = new Intent(context, MainActivity.class);
                        it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SUBUSER_DEALER_FRAGMENT);
                        context.startActivity(it);
                    }
            }

            @Override
            public void onFailure(Call<Dealer_Subuser_action_MODEL> call, Throwable t) {
                Toast.makeText(context, networkError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubUserModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FetechSubuserModel> fildteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                fildteredList.addAll(listFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (FetechSubuserModel item : listFilter){
                    if( item.getMob().toLowerCase().contains(filterPattern)){
                        fildteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = fildteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mSubUserModelArrayList.clear();
            mSubUserModelArrayList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameuser;
        TextView  mobileuser;
        TextView  roleuser;
        TextView  statususer;
        TextView  actionuser;
        TextView  edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameuser = itemView.findViewById(R.id.nameuser);
            mobileuser = itemView.findViewById(R.id.mobileuser);
            roleuser = itemView.findViewById(R.id.roleuser);
            statususer = itemView.findViewById(R.id.statususer);
            actionuser = itemView.findViewById(R.id.actionuser);
            edit = itemView.findViewById(R.id.edit);

        }
    }
}

