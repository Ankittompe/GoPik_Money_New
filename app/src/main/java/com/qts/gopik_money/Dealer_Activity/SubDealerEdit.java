package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Dealer_Subuser_edit_MODEL;
import com.qts.gopik_money.Pojo.Dealer_Subuser_edit_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubDealerEdit extends AppCompatActivity {
    TextView adduser;
    TextView dealercode;
    TextView name;
    TextView role;
    TextView mTxtAddBack;
    EditText username;
    EditText mobile;
    CustPrograssbar custPrograssbar;

    String networkError = "It seems your Network is unstable . Please Try again!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_dealer_edit);
        adduser = (TextView) findViewById(R.id.adduser);
        mTxtAddBack = (TextView) findViewById(R.id.txtAddBack);
        custPrograssbar = new CustPrograssbar();
        mobile = (EditText) findViewById(R.id.mobile);
        username = (EditText) findViewById(R.id.username);
        dealercode = (TextView) findViewById(R.id.dealercode);
        name = (TextView) findViewById(R.id.name);
        role = (TextView) findViewById(R.id.role);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
        username.setText(SharedPref.getStringFromSharedPref(AppConstants.SUBUName, getApplicationContext()));
        mobile.setText(SharedPref.getStringFromSharedPref(AppConstants.SUBUMOB, getApplicationContext()));
        role.setText(SharedPref.getStringFromSharedPref(AppConstants.SUBUROLE, getApplicationContext()));

        mTxtAddBack.setOnClickListener(view -> {
            finish();
        });

        adduser.setOnClickListener(view -> Dealer_Subuser_edit());
    }

    private void Dealer_Subuser_edit() {
        Dealer_Subuser_edit_POJO pojo = new Dealer_Subuser_edit_POJO(SharedPref.getStringFromSharedPref(AppConstants.SUBUSERID, getApplicationContext()),
                username.getText().toString(), mobile.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_Subuser_edit_MODEL> call = restApis.Dealer_Subuser_edit(pojo);
        call.enqueue(new Callback<Dealer_Subuser_edit_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_Subuser_edit_MODEL> call, Response<Dealer_Subuser_edit_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equals("200")) {
                        Intent it = new Intent(getApplicationContext(), MainActivity.class);
                        it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SUBUSER_DEALER_FRAGMENT);
                        startActivity(it);
                    }
                }
            }

            @Override
            public void onFailure(Call<Dealer_Subuser_edit_MODEL> call, Throwable t) {
                Toast.makeText(SubDealerEdit.this, networkError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


