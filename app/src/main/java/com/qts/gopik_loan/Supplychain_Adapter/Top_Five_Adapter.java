package com.qts.gopik_loan.Supplychain_Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class Top_Five_Adapter extends RecyclerView.Adapter<Top_Five_Adapter.ViewHolder> {
    Context applicationContext;
    ArrayList<String> Top_five_list = new ArrayList<>();
    ArrayList<String> Top_five_ID = new ArrayList<>();
    ArrayList<String> Top_Five_PO_ID = new ArrayList<>();
    ArrayList<String> Top_Five_Date = new ArrayList<>();
    ArrayList<String> Top_Five_Brand = new ArrayList<>();
    ArrayList<String> Top_Five_Quantity = new ArrayList<>();
    ArrayList<String> Top_Five_Amount = new ArrayList<>();
    ArrayList<String> Status = new ArrayList<>();
    public Top_Five_Adapter(Context applicationContext, ArrayList<String> top_five_list, ArrayList<String> top_five_ID, ArrayList<String> top_Five_PO_ID, ArrayList<String> top_Five_Date, ArrayList<String> top_Five_Quantity, ArrayList<String> top_Five_Brand,
                            ArrayList<String> top_Five_Amount,
    ArrayList<String> Status) {
        this.applicationContext = applicationContext;
        this.Top_five_list = top_five_list;
        this.Top_five_ID = top_five_ID;
        this.Top_Five_PO_ID = top_Five_PO_ID;
        this.Top_Five_Date = top_Five_Date;
        this.Top_Five_Brand = top_Five_Brand;
        this.Top_Five_Quantity = top_Five_Quantity;
        this.Top_Five_Amount = top_Five_Amount;
        this.Status = Status;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_five_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.name_tv.setText(Top_Five_Date.get(position));
       holder.serial_tv.setText(Top_Five_PO_ID.get(position));
       holder.status.setText(Status.get(position));
       Log.e("bdhbhc","bdbh"+Status.get(position));

       String po_id = Top_Five_PO_ID.get(position);
       String product_name = Top_five_list.get(position);
       String amount = Top_Five_Amount.get(position);
       String quantity = Top_Five_Quantity.get(position);
       String brand = Top_Five_Brand.get(position);

        if( Status.get(position).equals("Pending at OEM")){
            holder.status_background.setBackgroundResource(R.drawable.pending_layout);
        }
        else if( Status.get(position).equals("Rejected at OEM")){
            holder.status_background.setBackgroundResource(R.drawable.rejecteded);
        }

        else if( Status.get(position).equals("Approved at OEM")){
            holder.status_background.setBackgroundResource(R.drawable.approved_layout);
        }
        else if( Status.get(position).equals("Approved By Dealer")){
            holder.status_background.setBackgroundResource(R.drawable.approved_layout);

        }  else if( Status.get(position).equals("Rejected By Dealer")){
            holder.status_background.setBackgroundResource(R.drawable.rejecteded);
        }
        else if( Status.get(position).equals("Modified at OEM")){
            holder.status_background.setBackgroundResource(R.drawable.approve);
        }
        else if( Status.get(position).equals("Approved by financer")){
            holder.status_background.setBackgroundResource(R.drawable.approved_layout);
        }else if( Status.get(position).equals("Awaiting Disbursal")){
            holder.status_background.setBackgroundResource(R.drawable.pending_financer);
        }
        else if( Status.get(position).equals("Rejected by financer")){
            holder.status_background.setBackgroundResource(R.drawable.rejecteded);
        }

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (Status.get(position).equals("Approved at OEM")){
                   Intent intent = new Intent(applicationContext, PoDetail_Approve_OEM_Activity.class);
                   SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   applicationContext.startActivity(intent);
               }
               else if(Status.get(position).equals("Modified at OEM")){
                   Intent intent = new Intent(applicationContext, PO_Get_Modified_List.class);
                   SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   applicationContext.startActivity(intent);
               }
               else if(Status.get(position).equals("Rejected at OEM")){
                   Intent intent = new Intent(applicationContext, Po_Rejected_OEM_Activity.class);
                   SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   applicationContext.startActivity(intent);
               }
               else if(Status.get(position).equals("Pending at OEM")){
                   Intent intent = new Intent(applicationContext, PO_Generate_Pending_OEM_Activity.class);
                   SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   applicationContext.startActivity(intent);
               }
               else if(Status.get(position).equals("Approved by financer")){
                   Intent intent = new Intent(applicationContext, Po_Generate_Approved_By_Financer_Activity.class);
                   SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   applicationContext.startActivity(intent);
               }
               else if(Status.get(position).equals("Rejected by financer")){
                   Intent intent = new Intent(applicationContext, Po_Generate_Rejected_By_Financer_Activity.class);
                   SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   applicationContext.startActivity(intent);
               }
               else if(Status.get(position).equals("Approved By Dealer")){
                   Intent intent = new Intent(applicationContext, PoDetail_Approve_Dealer_Activity.class);
                   SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   applicationContext.startActivity(intent);
               }
               else if(Status.get(position).equals("Rejected By Dealer")){
                   Intent intent = new Intent(applicationContext, PoDetail_Rejected_Dealer_Activity.class);
                   SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   applicationContext.startActivity(intent);
               }

               //GotoPODetails(po_id,product_name);

           }
       });
        holder.next_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Status.get(position).equals("Approved at OEM")){
                    Intent intent = new Intent(applicationContext, PoDetail_Approve_OEM_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    applicationContext.startActivity(intent);
                }
                else if(Status.get(position).equals("Modified at OEM")){
                    Intent intent = new Intent(applicationContext, PO_Get_Modified_List.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    applicationContext.startActivity(intent);
                }
                else if(Status.get(position).equals("Rejected at OEM")){
                    Intent intent = new Intent(applicationContext, Po_Rejected_OEM_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    applicationContext.startActivity(intent);
                }
                else if(Status.get(position).equals("Pending at OEM")){
                    Intent intent = new Intent(applicationContext, PO_Generate_Pending_OEM_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    applicationContext.startActivity(intent);
                }
                else if(Status.get(position).equals("Approved by financer")){
                    Intent intent = new Intent(applicationContext, Po_Generate_Approved_By_Financer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    applicationContext.startActivity(intent);
                }
                else if(Status.get(position).equals("Rejected by financer")){
                    Intent intent = new Intent(applicationContext, Po_Generate_Rejected_By_Financer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    applicationContext.startActivity(intent);
                }
                else if(Status.get(position).equals("Approved By Dealer")){
                    Intent intent = new Intent(applicationContext, PoDetail_Approve_Dealer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    applicationContext.startActivity(intent);
                }
                else if(Status.get(position).equals("Rejected By Dealer")){
                    Intent intent = new Intent(applicationContext, PoDetail_Rejected_Dealer_Activity.class);
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, Top_Five_PO_ID.get(position), v.getContext());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    applicationContext.startActivity(intent);
                }

                //GotoPODetails(po_id,product_name);

            }
        });


    }

    private void GotoPODetails(String po_id,String product_name) {


    }


    @Override
    public int getItemCount() {
        return Top_five_ID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView serial_tv,name_tv,status;
        ConstraintLayout cardView_of_item,status_background;
        ImageView next_arrow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.status);
            serial_tv = itemView.findViewById(R.id.serial_tv);
            name_tv = itemView.findViewById(R.id.name_tv);
            cardView_of_item = itemView.findViewById(R.id.cardView_of_item);
            status_background = itemView.findViewById(R.id.status_background);
            next_arrow = itemView.findViewById(R.id.next_arrow);
        }
    }
}
