package nec.cst.pra.survey;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nec.cst.pra.R;


public class MasterAllVillagePrintAdapter extends RecyclerView.Adapter<MasterAllVillagePrintAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<GP> moviesList;
    private PrintListener printListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView village;
        ImageView editImage;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            village = (TextView) view.findViewById(R.id.village);
            editImage = (ImageView) view.findViewById(R.id.editImage);

            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }


    public MasterAllVillagePrintAdapter(Context mainActivityUser,
                                        ArrayList<GP> moviesList, PrintListener printListener) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;
        this.printListener=printListener;

    }

    public void notifyData(ArrayList<GP> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.village_survey_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        GP bean = moviesList.get(position);
        holder.village.setText(bean.getVillage());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }
        holder.editImage.setImageResource(R.drawable.ic_print_black_24dp);

        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
printListener.onPrintClick(position);
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
