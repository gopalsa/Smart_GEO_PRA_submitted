package nec.cst.pra.gpdp.frontline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nec.cst.pra.R;
import nec.cst.pra.gpdp.UserItemClick;

import java.util.ArrayList;

/**
 * Created by SanAji on 20-12-2018.
 */

public class FrontLineAdapter extends RecyclerView.Adapter<FrontLineAdapter.MyViewHolder> {
    public Context mainActivityUser;
    public ArrayList<FrontLine> frontLineArrayList;
    public UserItemClick userItemClick;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView frontgramName,frontuserName,frontdesignation,frontmobileNo,frontEmailId,frontdeptname,frontfrontLineWorker;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));

            frontgramName = (TextView) view.findViewById(R.id.frontgramName);
            frontuserName = (TextView) view.findViewById(R.id.frontuserName);
            frontdesignation = (TextView) view.findViewById(R.id.frontdesignation);
            frontmobileNo = (TextView) view.findViewById(R.id.frontmobileNo);
            frontEmailId = (TextView) view.findViewById(R.id.frontEmailId);
            frontdeptname = (TextView) view.findViewById(R.id.frontdeptname);
            frontfrontLineWorker = (TextView) view.findViewById(R.id.frontfrontLineWorker);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }
    public FrontLineAdapter(Context mainActivityUser, ArrayList<FrontLine> frontLineArrayList, UserItemClick userItemClick) {
        this.frontLineArrayList = frontLineArrayList;
        this.mainActivityUser = mainActivityUser;
        this.userItemClick = userItemClick;
    }
    public void notifyData(ArrayList<FrontLine> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.frontLineArrayList = myList;
        notifyDataSetChanged();
    }

    public FrontLineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.frontline_preview, parent, false);

        return new FrontLineAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FrontLineAdapter.MyViewHolder holder, final int position) {
        FrontLine bean = frontLineArrayList.get(position);
        holder.frontgramName.setText(bean.frontgramName);
        holder.frontuserName.setText(bean.frontuserName);
        holder.frontdesignation.setText(bean.frontdesignation);
        holder.frontmobileNo.setText(bean.frontmobileNo);
        holder.frontEmailId.setText(bean.frontEmailId);
        holder.frontdeptname.setText(bean.frontdeptname);
        holder.frontfrontLineWorker.setText(bean.frontfrontLineWorker);

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userItemClick.itemFrontLineClick(position);
            }
        });
    }

    public  int getItemCount(){
        return frontLineArrayList.size();
    }
}
