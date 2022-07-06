package com.qts.gopik_loan.Supplychain_Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Supply_Chain.PO_Generate_Pending_OEM_Activity;
import com.qts.gopik_loan.Supply_Chain.PO_Get_Modified_List;
import com.qts.gopik_loan.Supply_Chain.PoDetail_Approve_Dealer_Activity;
import com.qts.gopik_loan.Supply_Chain.PoDetail_Approve_OEM_Activity;
import com.qts.gopik_loan.Supply_Chain.PoDetail_Rejected_Dealer_Activity;
import com.qts.gopik_loan.Supply_Chain.Po_Generate_Approved_By_Financer_Activity;
import com.qts.gopik_loan.Supply_Chain.Po_Generate_Rejected_By_Financer_Activity;
import com.qts.gopik_loan.Supply_Chain.Po_Rejected_OEM_Activity;

import java.util.ArrayList;

public class All_product_list_Adapter extends RecyclerView.Adapter<All_product_list_Adapter.ViewHolder> {
    Context context;
    ArrayList<String> All_product_list = new ArrayList<>();
    ArrayList<String> All_product_id = new ArrayList<>();
    ArrayList<String> All_po_date = new ArrayList<>();
    ArrayList<String> All_po_id = new ArrayList<>();
    ArrayList<String> All_po_Status = new ArrayList<>();

    public All_product_list_Adapter(Context applicationContext, ArrayList<String> all_product_list, ArrayList<String> all_product_id, ArrayList<String> all_po_date, ArrayList<String> all_po_id, ArrayList<String> all_po_Status) {
        this.context = applicationContext;
        this.All_product_list = all_product_list;
        this.All_product_id = all_product_id;
        this.All_po_date = all_po_date;
        this.All_po_id = all_po_id;
        this.All_po_Status = all_po_Status;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_list_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Po_id_tv.setText(All_po_id.get(position));
        holder.Date_tv.setText(All_po_date.get(position));
        holder.po_status.setText(All_po_Status.get(position));
        if( All_po_Status.get(position).equals("Pending at OEM")){
            holder.all_status_layout.setBackgroundResource(R.drawable.pending_layout);
        }
        else if( All_po_Status.get(position).equals("Rejected at OEM")){
            holder.all_status_layout.setBackgroundResource(R.drawable.rejecteded);
        }

        else if( All_po_Status.get(position).equals("Approved at OEM")){
            holder.all_status_layout.setBackgroundResource(R.drawable.approved_layout);
        }
        else if( All_po_Status.get(position).equals("Approved By Dealer")){
            holder.all_status_layout.setBackgroundResource(R.drawable.approved_layout);

        }  else if( All_po_Status.get(position).equals("Rejected By Dealer")){
            holder.all_status_layout.setBackgroundResource(R.drawable.rejecteded);
        }
        else if( All_po_Status.get(position).equals("Modified at OEM")){
            holder.all_status_layout.setBackgroundResource(R.drawable.approve);
        }
        else if( All_po_Status.get(position).equals("Approved by financer")){
            holder.all_status_layout.setBackgroundResource(R.drawable.approved_layout);
        }else if( All_po_Status.get(position).equals("Awaiting Disbursal")){
            holder.all_status_layout.setBackgroundResource(R.drawable.pending_financer);
        }
        else if( All_po_Status.get(position).equals("Rejected by financer")){
            holder.all_status_layout.setBackgroundResource(R.drawable.rejecteded);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (All_po_Status.get(position).equals("Approved at OEM")){
                    Intent intent = new Intent(context, PoDetail_Approve_OEM_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Modified at OEM")){
                    Intent intent = new Intent(context, PO_Get_Modified_List.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Rejected at OEM")){
                    Intent intent = new Intent(context, Po_Rejected_OEM_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Pending at OEM")){
                    Intent intent = new Intent(context, PO_Generate_Pending_OEM_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Approved by financer")){
                    Intent intent = new Intent(context, Po_Generate_Approved_By_Financer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Rejected by financer")){
                    Intent intent = new Intent(context, Po_Generate_Rejected_By_Financer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Approved By Dealer")){
                    Intent intent = new Intent(context, PoDetail_Approve_Dealer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Rejected By Dealer")){
                    Intent intent = new Intent(context, PoDetail_Rejected_Dealer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                //GotoPODetails(po_id,product_name);

            }
        });
        holder.next_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (All_po_Status.get(position).equals("Approved at OEM")){
                    Intent intent = new Intent(context, PoDetail_Approve_OEM_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Modified at OEM")){
                    Intent intent = new Intent(context, PO_Get_Modified_List.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Rejected at OEM")){
                    Intent intent = new Intent(context, Po_Rejected_OEM_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Pending at OEM")){
                    Intent intent = new Intent(context, PO_Generate_Pending_OEM_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Approved by financer")){
                    Intent intent = new Intent(context, Po_Generate_Approved_By_Financer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Rejected by financer")){
                    Intent intent = new Intent(context, Po_Generate_Rejected_By_Financer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Approved By Dealer")){
                    Intent intent = new Intent(context, PoDetail_Approve_Dealer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(All_po_Status.get(position).equals("Rejected By Dealer")){
                    Intent intent = new Intent(context, PoDetail_Rejected_Dealer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, All_po_id.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return All_po_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Po_id_tv;
        TextView Date_tv;
        TextView po_status;
        ConstraintLayout all_status_layout;
        ImageView next_arrow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Po_id_tv = itemView.findViewById(R.id.po_id_tv);
            Date_tv = itemView.findViewById(R.id.date_tv);
            po_status = itemView.findViewById(R.id.po_status);
            all_status_layout = itemView.findViewById(R.id.all_status_layout);
            next_arrow = itemView.findViewById(R.id.next_arrow);
        }
    }
}
