package nec.cst.pra.gpdp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nec.cst.pra.R;
import nec.cst.pra.gpdp.Facilitator.FacilitatorCreates;

import java.io.Serializable;
import java.util.ArrayList;


public class FacilitatorAllSurveyAdapter extends RecyclerView.Adapter<FacilitatorAllSurveyAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<FacilitatorCreates> moviesList;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView entityName,userName,designations;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            entityName = (TextView) view.findViewById(R.id.entityName);
            userName = (TextView) view.findViewById(R.id.userName);
            designations = (TextView) view.findViewById(R.id.designations);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public FacilitatorAllSurveyAdapter(Context mainActivityUser, ArrayList<FacilitatorCreates> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<FacilitatorCreates> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facilitator_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        FacilitatorCreates facilitatorCreates = moviesList.get(position);
        holder.entityName.setText(facilitatorCreates.getEntityName());
        //holder.age.setText(bean.getGeoTag());
        //holder.education.setText(bean.getId());
        holder.userName.setText(facilitatorCreates.getUserName());
        holder.designations.setText(facilitatorCreates.getDesignations());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, FacilitatorActivity.class);
                intent.putExtra("object", (Serializable) moviesList.get(position));
                mainActivityUser.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static String round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return String.valueOf((double) tmp / factor);
    }

}
