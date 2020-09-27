package nec.cst.pra.gpdp.userdetail;

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
 * Created by SanAji on 14-12-2018.
 */

public class UserDetailAdapter extends RecyclerView.Adapter<UserDetailAdapter.MyViewHolder> {

    public Context mainActivityUser;
    public ArrayList<UserDetail> userDetailArrayList;
    public UserItemClick userItemClick;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView userserialno,useruserName,usermobileNo,userdesignation,userentityType,userentityName,userEmailId;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));

            userserialno =(TextView) view.findViewById(R.id.userserialno);
            useruserName =(TextView) view.findViewById(R.id.useruserName);
            usermobileNo =(TextView) view.findViewById(R.id.usermobileNo);
            useruserName =(TextView) view.findViewById(R.id.useruserName);
            userdesignation =(TextView) view.findViewById(R.id.userdesignation);
            userentityType =(TextView) view.findViewById(R.id.userentityType);
            userentityName =(TextView) view.findViewById(R.id.userentityName);
            userEmailId =(TextView) view.findViewById(R.id.userEmailId);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

        }
    }
    public UserDetailAdapter(Context mainActivityUser, ArrayList<UserDetail> userDetailArrayList, UserItemClick userItemClick) {
        this.userDetailArrayList = userDetailArrayList;
        this.mainActivityUser = mainActivityUser;
        this.userItemClick = userItemClick;
    }
    public void notifyData(ArrayList<UserDetail> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.userDetailArrayList = myList;
        notifyDataSetChanged();
    }

    public UserDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userdetail_preview, parent, false);

        return new UserDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserDetailAdapter.MyViewHolder holder, final int position) {
        UserDetail bean = userDetailArrayList.get(position);
        holder.userserialno.setText(bean.getUserserialno());
        holder.useruserName.setText(bean.getUseruserName());
        holder.usermobileNo.setText(bean.getUsermobileNo());
        holder.userdesignation.setText(bean.getUserdesignation());
        holder.userentityType.setText(bean.getUserentityType());
        holder.userentityName.setText(bean.getUserentityName());
        holder.userEmailId.setText(bean.getUserEmailId());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userItemClick.itemUserDetailClick(position);
            }
        });
    }

    public  int getItemCount(){
        return userDetailArrayList.size();
    }
}
