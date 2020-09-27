package nec.cst.pra.gpdp.FacilitatorFeedback;

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

public class FacilitatorFeedbackAdapter extends RecyclerView.Adapter<FacilitatorFeedbackAdapter.MyViewHolder> {

    public Context mainActivityUser;
    public ArrayList<FacilitatorFeedback> facilitatorFeedbackArrayList;
    public FacilitatorItemClick facilitatorItemClick;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView gramName,date,people,sc,st,shg,women,department,frontLineWorker,whetherAvailable,whetherdelivered,fund,resource,gaps,resolution;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));

            gramName =(TextView) view.findViewById(R.id.gramName);
            date =(TextView) view.findViewById(R.id.date);
            people =(TextView) view.findViewById(R.id.people);
            sc =(TextView) view.findViewById(R.id.sc);
            st =(TextView) view.findViewById(R.id.st);
            shg =(TextView) view.findViewById(R.id.shg);
            women =(TextView) view.findViewById(R.id.women);
            department =(TextView) view.findViewById(R.id.department);
            frontLineWorker =(TextView) view.findViewById(R.id.frontLineWorker);
            whetherAvailable =(TextView) view.findViewById(R.id.whetherAvailable);
            whetherdelivered =(TextView) view.findViewById(R.id.whetherdelivered);
            fund =(TextView) view.findViewById(R.id.fund);
            resource =(TextView) view.findViewById(R.id.resource);
            gaps =(TextView) view.findViewById(R.id.gaps);
            resolution =(TextView) view.findViewById(R.id.resolution);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

        }
    }
    public FacilitatorFeedbackAdapter(Context mainActivityUser, ArrayList<FacilitatorFeedback> facilitatorFeedbackArrayList, FacilitatorItemClick facilitatorItemClick) {
        this.facilitatorFeedbackArrayList = facilitatorFeedbackArrayList;
        this.mainActivityUser = mainActivityUser;
        this.facilitatorItemClick = facilitatorItemClick;
    }
    public void notifyData(ArrayList<FacilitatorFeedback> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.facilitatorFeedbackArrayList = myList;
        notifyDataSetChanged();
    }

    public FacilitatorFeedbackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facilitatorfeedback_preview, parent, false);

        return new FacilitatorFeedbackAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FacilitatorFeedbackAdapter.MyViewHolder holder, final int position) {
        FacilitatorFeedback bean = facilitatorFeedbackArrayList.get(position);
        holder.gramName.setText(bean.getGramName());
        holder.date.setText(bean.getDate());
        holder.people.setText(bean.getPeople());
        holder.sc.setText(bean.getSc());
        holder.st.setText(bean.getSt());
        holder.shg.setText(bean.getShg());
        holder.women.setText(bean.getWomen());
        holder.department.setText(bean.getDepartment());
        holder.frontLineWorker.setText(bean.getFrontLineWorker());
        holder.whetherAvailable.setText(bean.getWhetherAvailable());
        holder.whetherdelivered.setText(bean.getWhetherdelivered());
        holder.fund.setText(bean.getFund());
        holder.resource.setText(bean.getResource());
        holder.gaps.setText(bean.getGaps());
        holder.resolution.setText(bean.getResolution());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facilitatorItemClick.itemFacilitatorfeedbackClick(position);
            }
        });
    }

    public  int getItemCount(){
        return facilitatorFeedbackArrayList.size();
    }
}
