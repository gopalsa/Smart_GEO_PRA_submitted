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
import nec.cst.pra.gpdp.userdetail.UserDetail;


public class UserAllSurveyAdapter extends RecyclerView.Adapter<UserAllSurveyAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<UserDetail> moviesList;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView userserialno,userstateName,useruserName;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            userserialno = (TextView) view.findViewById(R.id.userserialno);
            userstateName = (TextView) view.findViewById(R.id.userstateName);
            useruserName = (TextView) view.findViewById(R.id.useruserName);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public UserAllSurveyAdapter(Context mainActivityUser, ArrayList<UserDetail> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<UserDetail> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        UserDetail userDetail = moviesList.get(position);
        holder.userserialno.setText(userDetail.getUserserialno());
        //holder.age.setText(bean.getGeoTag());
        //holder.education.setText(bean.getId());
        holder.userstateName.setText(userDetail.getUserstateName());
        holder.useruserName.setText(userDetail.getUseruserName());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, UserDetailActivity.class);
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
