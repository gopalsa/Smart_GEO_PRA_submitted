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

import java.io.Serializable;
import java.util.ArrayList;

import nec.cst.pra.R;


public class GramsabhaAllSurveyAdapter extends RecyclerView.Adapter<GramsabhaAllSurveyAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<GramBean> moviesList;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView districtName,blockName,panchayat;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            districtName = (TextView) view.findViewById(R.id.districtName);
            blockName = (TextView) view.findViewById(R.id.blockName);
            panchayat = (TextView) view.findViewById(R.id.panchayat);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public GramsabhaAllSurveyAdapter(Context mainActivityUser, ArrayList<GramBean> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<GramBean> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gramsabha_list_row, parent, false);

        return new MyViewHolder(itemView);
    }


    public void onBindViewHolder(MyViewHolder holder, final int position) {
        GramBean gramBean = moviesList.get(position);
        holder.districtName.setText(gramBean.getDistrictName());
        //holder.age.setText(bean.getGeoTag());
        //holder.education.setText(bean.getId());
        holder.blockName.setText(gramBean.getBlockName());
        holder.panchayat.setText(gramBean.getPanchayat());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, GramsabhaActivity.class);
                intent.putExtra("object", (Serializable) moviesList.get(position));
                mainActivityUser.startActivity(intent);
            }
        });

    }


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
