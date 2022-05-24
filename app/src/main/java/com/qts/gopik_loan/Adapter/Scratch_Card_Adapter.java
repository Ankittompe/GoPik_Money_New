package com.qts.gopik_loan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.Activity.Scratch_Activity;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout;

public class Scratch_Card_Adapter  extends RecyclerView.Adapter<Scratch_Card_Adapter.ViewHolder> {

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> user_code = new ArrayList<>();
    ArrayList<String> card_type = new ArrayList<>();
    ArrayList<String> amount = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> created_at = new ArrayList<>();
    ArrayList<String> updated_at = new ArrayList<>();

    String TAG = "hi";
    Context context;

    public Scratch_Card_Adapter(Context context, ArrayList<String> id, ArrayList<String> user_code, ArrayList<String> card_type
            , ArrayList<String> amount, ArrayList<String> status, ArrayList<String> created_at
            , ArrayList<String> updated_at ) {

        this.context = context;
        this.id = id;
        this.user_code = user_code;
        this.card_type = card_type;
        this.amount = amount;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;


        Log.e("anhii", "anhuiii");

    }

    @NonNull
    @Override
    public Scratch_Card_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_scratchcard_layout, parent, false);
        Log.e("anhii", "anhuiii");
        return new Scratch_Card_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Scratch_Card_Adapter.ViewHolder holder, int position) {
        Log.e("anhii", "anhuiii");


    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ScratchCardLayout scratchCardLayout;
        TextView textView_points_show;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scratchCardLayout =  itemView.findViewById(R.id.idScratchCardIv);
            textView_points_show =  itemView.findViewById(R.id.textView_points_show);

        }
    }
}




