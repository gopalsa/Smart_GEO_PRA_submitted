package nec.cst.pra;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nec.cst.pra.app.AppConfig;
import nec.cst.pra.app.GlideApp;
import nec.cst.pra.db.DbImage;
import nec.cst.pra.db.DbReport;

/**
 * Created by Gopal on 08-11-2017.
 */

public class AttendancePage extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;
    private MultiSnapRecyclerView mRecyclerViewhistory;
    private ProfileAdapter mRecyclerAdapterhistory;
    private ArrayList<Plot> historyList = new ArrayList<>();
    private String imagePath;
    GPSTracker gps;
    DbImage dbImage;
    EditText date;
    EditText time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_page);
        gps = new GPSTracker(AttendancePage.this);
        dbReport = new DbReport(this);
        dbImage = new DbImage(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }

        TextView submit = (TextView) findViewById(R.id.submit);
        TextView tittletxt = (TextView) findViewById(R.id.tittletxt);
        TextView subtittletxt = (TextView) findViewById(R.id.subtittletxt);
        TextView processadopted = (TextView) findViewById(R.id.processadopted);
        tittletxt.setText(tittleString);
        subtittletxt.setText("Group Picture & Video of " + tittleString + " participants");

        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        final EditText fmalecount = (EditText) findViewById(R.id.fmalecount);
        final EditText ffemalecount = (EditText) findViewById(R.id.ffemalecount);
        final EditText pmalecount = (EditText) findViewById(R.id.pmalecount);
        final EditText pfemalecount = (EditText) findViewById(R.id.pfemalecount);

        try {
            String attendance = dbReport.getData(vrpString, tittleString, getResources().getString(R.string.attendance));
            if (attendance != null) {
                JSONObject attendanceObject = new JSONObject(attendance);
                date.setText(attendanceObject.getString("date"));
                time.setText(attendanceObject.getString("time"));
                fmalecount.setText(attendanceObject.getString("fmalecount"));
                ffemalecount.setText(attendanceObject.getString("ffemalecount"));
                pmalecount.setText(attendanceObject.getString("pmalecount"));
                pfemalecount.setText(attendanceObject.getString("pfemalecount"));
            } else {
                pickDate(AttendancePage.this);
                pickTime(AttendancePage.this);
            }
        } catch (Exception e) {
            Log.d("Attendance ", e.toString());
            pickDate(AttendancePage.this);
            pickTime(AttendancePage.this);
        }

        processadopted.setText(getResources().getString(AppConfig.getProcess(tittleString)));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date.getText().toString().length() > 0
                        && time.getText().toString().length() > 0
                        && fmalecount.getText().toString().length() > 0
                        && ffemalecount.getText().toString().length() > 0
                        && pmalecount.getText().toString().length() > 0
                        && pfemalecount.getText().toString().length() > 0) {
                    JSONObject attendanceObject = new JSONObject();
                    try {
                        attendanceObject.put("date", date.getText().toString());
                        attendanceObject.put("time", time.getText().toString());
                        attendanceObject.put("fmalecount", fmalecount.getText().toString());
                        attendanceObject.put("ffemalecount", ffemalecount.getText().toString());
                        attendanceObject.put("pmalecount", pmalecount.getText().toString());
                        attendanceObject.put("pfemalecount", pfemalecount.getText().toString());
                    } catch (Exception e) {
                        Log.d("Attendance ", e.toString());
                    }
                    int success = dbReport.updatedata(vrpString, tittleString,
                            getResources().getString(R.string.attendance), attendanceObject.toString());
                    if (success == 0)
                        dbReport.addData(vrpString, tittleString,
                                getResources().getString(R.string.attendance), attendanceObject.toString());
                    Intent io = new Intent(AttendancePage.this, ToolReport.class);
                    startActivity(io);
                }
            }
        });


        ImageView addimage = (ImageView) findViewById(R.id.addimg);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage(-1, "", "Add Image");
            }
        });
        mRecyclerViewhistory = (MultiSnapRecyclerView) findViewById(R.id.attendancelist);
        mRecyclerAdapterhistory = new ProfileAdapter(AttendancePage.this, historyList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewhistory.setLayoutManager(thirdManager);
        mRecyclerViewhistory.setAdapter(mRecyclerAdapterhistory);
        mRecyclerViewhistory.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewhistory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                optionPicker(position,AttendancePage.this);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareData();
    }

    private void prepareData() {
        historyList = new ArrayList<>();
        List<String> history = dbImage.getAllData(vrpString, tittleString, getResources().getString(R.string.attendance));
        for (int i = 0; i < history.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(history.get(i));
                Plot plot = new Plot(jsonObject.getString("geotag"), jsonObject.getString("name"),
                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                historyList.add(plot);
            } catch (JSONException e) {
                Log.d("Attendance Page", e.toString());
            }
        }
        mRecyclerAdapterhistory.notifyData(historyList);
    }


    public void addImage(final int position, final String name, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttendancePage.this);
        LayoutInflater inflater = AttendancePage.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addimage_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontEditText geotag = (CustomFontEditText) dialogView.findViewById(R.id.geotag);
        final ImageView georefresh = (ImageView) dialogView.findViewById(R.id.refresh);
        final CustomFontEditText imagetxt = (CustomFontEditText) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            String data = dbImage.getData(vrpString, tittleString, getResources().getString(R.string.attendance), name);
            try {
                JSONObject jsonObject = new JSONObject(data);
                geotag.setText(jsonObject.getString("geotag"));
                description.setText(jsonObject.getString("description"));
                GlideApp.with(AttendancePage.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText(jsonObject.getString("name"));
            } catch (JSONException e) {
                Log.d("Attendance Page", e.toString());
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (description.getText().toString().length() <= 0
                        || imagetxt.getText().toString().length() <= 0
                        || geotag.getText().toString().length() <= 0
                        || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("geotag", geotag.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                        jsonObject.put("name", imagetxt.getText().toString());
                        if (position == -1) {
                            dbImage.addData(vrpString, tittleString, getResources().getString(R.string.attendance)
                                    , geotag.getText().toString(), jsonObject.toString());
                            Plot plot = new Plot(jsonObject.getString("geotag"), jsonObject.getString("name"),
                                    jsonObject.getString("image"), jsonObject.getString("description"), null);
                            historyList.add(plot);
                            mRecyclerAdapterhistory.notifyData(historyList);
                        } else {
                            dbImage.updatedata(vrpString, tittleString, getResources().getString(R.string.attendance)
                                    , name, geotag.getText().toString(), jsonObject.toString());
                            historyList.get(position).setPlotname(imagetxt.getText().toString());
                            historyList.get(position).setTag(geotag.getText().toString());
                            historyList.get(position).setPlotarea(description.getText().toString());
                            historyList.get(position).setPlotimage(imagePath);
                            mRecyclerAdapterhistory.notifyData(historyList);
                        }
                    } catch (JSONException e) {
                        Log.d("Attendance Page", e.toString());
                    }
                }
                b.cancel();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        GlideApp.with(AttendancePage.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                    }
                })
                        .show(AttendancePage.this);
            }
        });

        georefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if GPS enabled
                gps = new GPSTracker(AttendancePage.this);
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    geotag.setText(latitude + "," + longitude);
                    // \n is for new line
                    //       Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });
        // check if GPS enabled
        if (gps.canGetLocation()) {
            gps = new GPSTracker(AttendancePage.this);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            geotag.setText(latitude + "," + longitude);
            // \n is for new line
            //       Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        b.show();
    }

    public void pickDate(Context context) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yy");
        date.setText(sdf1.format(new Date()));     //30.06.09

    }

    private void pickTime(Context context) {
        SimpleDateFormat sdf5 = new SimpleDateFormat("h:mm");
        time.setText(sdf5.format(new Date()));     //8:29


    }


    public void optionPicker(final int position, final Context context) {

        final CharSequence[] items;

        items = new CharSequence[2];
        items[0] = "View";
        items[1] = "Edit";


        android.app.AlertDialog.Builder alertdialog = new android.app.AlertDialog.Builder(context);
        alertdialog.setTitle("Select");
        alertdialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("View")) {
                    Intent localIntent = new Intent(context, AttachementOffline.class);
                    localIntent.putExtra("filePath", historyList.get(position).getPlotimage());
                    startActivity(localIntent);
                } else if (items[item].equals("Edit")) {
                    addImage(position, historyList.get(position).getTag(), "Update images");

                }
            }
        });
        alertdialog.show();
    }

}
