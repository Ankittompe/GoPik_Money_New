package com.qts.gopik_loan.Dealer_Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Dealer_Activity.SubDealerEdit;
import com.qts.gopik_loan.Model.Dealer_Subuser_action_MODEL;
import com.qts.gopik_loan.Pojo.Dealer_Subuser_action_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserAdapter  extends RecyclerView.Adapter<AddUserAdapter.ViewHolder>  {

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> code = new ArrayList<>();
    ArrayList<String> firm_name = new ArrayList<>();
    ArrayList<String> sub_user = new ArrayList<>();
    ArrayList<String> mob = new ArrayList<>();
    ArrayList<String> role = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> mod_timestamp = new ArrayList<>();
    String TAG="hi";
    Context context;
    String x;
    public AddUserAdapter(Context context, ArrayList<String> id, ArrayList<String> code, ArrayList<String> firm_name
            , ArrayList<String> sub_user, ArrayList<String> mob, ArrayList<String> role
            , ArrayList<String> status, ArrayList<String> mod_timestamp )
    {

        this.context = context;
        this.id = id;
        this.code = code;
        this.firm_name = firm_name;
        this.sub_user = sub_user;
        this.mob = mob;
        this.role = role;
        this.status = status;
        this.mod_timestamp = mod_timestamp;

        Log.e("anhii","anhuiii");

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_layout, parent, false);
        Log.e("anhii","anhuiii");
        return new AddUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("anhii","anhuiii");

        holder.mobileuser.setText(mob.get(position));
        holder.roleuser.setText(role.get(position));
        holder.statususer.setText(status.get(position));
        Log.e("anhii","anhuiii");
        holder.nameuser.setText(sub_user.get(position));
        if(holder.statususer.getText().equals("Active")){
            holder.actionuser.setText("Inactive");
            holder.actionuser.setBackgroundResource(R.drawable.rejecteded);
        }
        else {
            holder.actionuser.setText("Active");
            holder. actionuser.setBackgroundResource(R.drawable.approve);
        }
        SharedPref.saveStringInSharedPref(AppConstants.SUBUSERID,id.get(position),context);
        SharedPref.saveStringInSharedPref(AppConstants.STATUS_SUBUSER,status.get(position),context);
        holder.actionuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.actionuser.getText().equals("Active")){
                    x="Active";
                }
                else{
                    x="Inactive";
                }
                SharedPref.saveStringInSharedPref(AppConstants.SUBUSERID,id.get(position),context);
                Log.e("anhii","anhuiii"+id.get(position));
                Dealer_Subuser_action();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPref.saveStringInSharedPref(AppConstants.SUBUSERID,id.get(position),context);
                Intent it = new Intent(context, SubDealerEdit.class);
                context.startActivity(it);
            }
        });
    }
    private void Dealer_Subuser_action() {

        Dealer_Subuser_action_POJO pojo = new Dealer_Subuser_action_POJO(SharedPref.getStringFromSharedPref(AppConstants.SUBUSERID,context),
                x);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_Subuser_action_MODEL> call = restApis.Dealer_Subuser_action(pojo);
        call.enqueue(new Callback<Dealer_Subuser_action_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_Subuser_action_MODEL> call, Response<Dealer_Subuser_action_MODEL> response) {

                if (response.body() != null) {


                    if(response.body().getCode().equals("200")){

                        Intent it = new Intent(context, MainActivity.class);
                        it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SUBUSER_DEALER_FRAGMENT);
                        context.startActivity(it);


                    }
                    else  {

                    }




                }
            }

            @Override
            public void onFailure(Call<Dealer_Subuser_action_MODEL> call, Throwable t) {




            }

        });

    }

    @Override
    public int getItemCount() {
        return firm_name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameuser,mobileuser,roleuser,statususer,actionuser,edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameuser = (TextView) itemView.findViewById(R.id.nameuser);
            mobileuser = (TextView) itemView.findViewById(R.id.mobileuser);
            roleuser = (TextView) itemView.findViewById(R.id.roleuser);
            statususer = (TextView) itemView.findViewById(R.id.statususer);
            actionuser = (TextView) itemView.findViewById(R.id.actionuser);
            edit = (TextView) itemView.findViewById(R.id.edit);

        }
    }
}

