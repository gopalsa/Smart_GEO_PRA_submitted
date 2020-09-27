package nec.cst.pra.gpdp.Facilitator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nec.cst.pra.gpdp.FacilitatorItemClick;
import nec.cst.pra.R;

import java.util.ArrayList;

/**
 * Created by SanAji on 20-12-2018.
 */

public class FacilitatorCreatesAdapter extends RecyclerView.Adapter<FacilitatorCreatesAdapter.MyViewHolder> {

    public Context mainActivityUser;
    public ArrayList<FacilitatorCreates> facilitatorCreatesArrayList;
    public FacilitatorItemClick facilitatorItemClick;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView entityName,userName,designations,EmailId,mobileNo;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));

            entityName =(TextView) view.findViewById(R.id.entityName);
            userName =(TextView) view.findViewById(R.id.userName);
            designations =(TextView) view.findViewById(R.id.designations);
            EmailId =(TextView) view.findViewById(R.id.EmailId);
            mobileNo =(TextView) view.findViewById(R.id.mobileNo);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

        }
    }
    public FacilitatorCreatesAdapter(Context mainActivityUser, ArrayList<FacilitatorCreates> facilitatorCreatesArrayList, FacilitatorItemClick facilitatorItemClick) {
        this.facilitatorCreatesArrayList = facilitatorCreatesArrayList;
        this.mainActivityUser = mainActivityUser;
        this.facilitatorItemClick = facilitatorItemClick;
           }
    public void notifyData(ArrayList<FacilitatorCreates> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.facilitatorCreatesArrayList = myList;
        notifyDataSetChanged();
    }

    public FacilitatorCreatesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facilitatorcreation_preview, parent, false);

        return new FacilitatorCreatesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FacilitatorCreatesAdapter.MyViewHolder holder, final int position) {
        FacilitatorCreates bean = facilitatorCreatesArrayList.get(position);
        holder.entityName.setText(bean.getEntityName());
        holder.userName.setText(bean.getUserName());
        holder.designations.setText(bean.getDesignations());
        holder.EmailId.setText(bean.getEmailId());
        holder.mobileNo.setText(bean.getMobileNo());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facilitatorItemClick.itemFacilitatorCreationClick(position);
            }
        });
    }

    public  int getItemCount(){
        return facilitatorCreatesArrayList.size();
    }
}