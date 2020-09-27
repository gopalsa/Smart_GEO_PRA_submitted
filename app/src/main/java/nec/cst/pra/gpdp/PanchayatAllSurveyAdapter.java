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

import java.util.ArrayList;

import nec.cst.pra.R;


public class PanchayatAllSurveyAdapter extends RecyclerView.Adapter<PanchayatAllSurveyAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<PanchayatBean> moviesList;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView userCreate,userLocate,userType;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            userCreate = (TextView) view.findViewById(R.id.userCreate);
            userLocate = (TextView) view.findViewById(R.id.userLocate);
            userType = (TextView) view.findViewById(R.id.userType);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public PanchayatAllSurveyAdapter(Context mainActivityUser, ArrayList<PanchayatBean> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<PanchayatBean> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.survey_list_row_gpdp, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        PanchayatBean panchayatBean = moviesList.get(position);
        holder.userCreate.setText(panchayatBean.getUserCreate());
        //holder.age.setText(bean.getGeoTag());
        //holder.education.setText(bean.getId());
        holder.userLocate.setText(panchayatBean.getUserLocate());
        holder.userType.setText(panchayatBean.getUserType());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, MainActivityGpdp.class);
                intent.putExtra("object", moviesList.get(position));
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
