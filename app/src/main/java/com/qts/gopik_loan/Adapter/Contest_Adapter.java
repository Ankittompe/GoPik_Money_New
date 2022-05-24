package com.qts.gopik_loan.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Utils.TextViewOutline;

import java.util.ArrayList;

public class Contest_Adapter extends RecyclerView.Adapter<Contest_Adapter.ViewHolder> {
    public ArrayList<String> contest_name = new ArrayList<>();
    public ArrayList<String> contest_state = new ArrayList<>();
    public ArrayList<Integer> contest_points = new ArrayList<>();
    public ArrayList<Integer> contest_id = new ArrayList<>();
    public float required =150;


    Context context;


    public Contest_Adapter(Context context, ArrayList<String> contest_name, ArrayList<Integer> contest_points, ArrayList<Integer> contest_id, ArrayList<String> contest_state) {
        Log.e("ghfdddssss","fftfy");
        this.context = context;
        this.contest_name = contest_name;
        this.contest_points = contest_points;
        this.contest_id = contest_id;
        this.contest_state = contest_state;



    }
    @NonNull
    @Override
    public Contest_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new Contest_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Contest_Adapter.ViewHolder holder, final int position) {

        holder.txt.setText(contest_name.get(position));
        holder.contest_points.setText((Integer.toString(contest_points.get(position))));
        holder.contest_count.setText((Integer.toString(contest_id.get(position))));
        if (contest_points.get(position) >= 150) {
            holder.Crown_Image.setVisibility(View.VISIBLE);
        } else {
            holder.Crown_Image.setVisibility(View.GONE);
        }

        holder.state_name.setText(contest_state.get(position));
        /*GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.BLACK); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(20);
        gd.setStroke(1, Color.BLACK);
        holder.contest_points.setBackground(gd);*/


    }
    @Override
    public int getItemCount() {

        return contest_points.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt,contest_count,state_name;
        TextViewOutline contest_points;
        CardView cardview;
        ImageView Crown_Image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt=itemView.findViewById(R.id.contest_name);
            contest_count=itemView.findViewById(R.id.contest_count);
            cardview = itemView.findViewById(R.id.cardview);
            contest_points = itemView.findViewById(R.id.user_points);
            Crown_Image = itemView.findViewById(R.id.Crown_Image);
            state_name=itemView.findViewById(R.id.state_name);
        }
    }
}
