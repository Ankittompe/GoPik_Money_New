package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.R;

public class Success_Dealer extends AppCompatActivity {
    TextView myridee;
    TextView applicationstatus;
    TextView referenceno;
    ImageView hometoolbar;
    ImageView backarrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success__dealer);
        myridee=(TextView)findViewById(R.id.myridee);
        applicationstatus=(TextView)findViewById(R.id.applicationstatus);
        referenceno =(TextView)findViewById(R.id.referenceno);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        referenceno .setText(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()));
        applicationstatus .setText("IN_REVIEW");
        myridee.setOnClickListener(view -> {
            Intent it = new Intent(Success_Dealer.this, MainActivity.class);

            startActivity(it);
        });
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Success_Dealer.this, Customer_Details_Verification_Dealer.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Success_Dealer.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
            startActivity(it);

        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "App restricts,back button not allowed on this screen!!",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }
}

