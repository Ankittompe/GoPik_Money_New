package com.qts.gopik_money.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.R;
import com.qts.gopik_money.Utils.TextViewOutline;

import java.util.List;

public class Contest_Adapter extends RecyclerView.Adapter<Contest_Adapter.ViewHolder> {
    private List<String> contest_name ;
    private List<String> contest_state;
    private List<Integer> contest_points ;
    private List<Integer> contest_id ;



    Context context;


    public Contest_Adapter(Context context, List<String> contest_name, List<Integer> contest_points, List<Integer> contest_id, List<String> contest_state) {
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


    }
    @Override
    public int getItemCount() {

        return contest_points.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt;
        TextView contest_count;
        TextView state_name;
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
