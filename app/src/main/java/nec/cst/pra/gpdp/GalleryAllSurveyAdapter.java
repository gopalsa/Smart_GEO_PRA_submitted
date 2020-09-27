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


public class GalleryAllSurveyAdapter extends RecyclerView.Adapter<GalleryAllSurveyAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<GalleryBean> moviesList;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView images,remark,testmonial;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            images = (TextView) view.findViewById(R.id.images);
            remark = (TextView) view.findViewById(R.id.remark);
            testmonial = (TextView) view.findViewById(R.id.testmonial);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public GalleryAllSurveyAdapter(Context mainActivityUser, ArrayList<GalleryBean> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<GalleryBean> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        GalleryBean galleryBean = moviesList.get(position);
        holder.images.setText(galleryBean.getImages());
        //holder.age.setText(bean.getGeoTag());
        //holder.education.setText(bean.getId());
        holder.remark.setText(galleryBean.getRemark());
        holder.testmonial.setText(galleryBean.getTestmonial());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, GalleryActivity.class);
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
