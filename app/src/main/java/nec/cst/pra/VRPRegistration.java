package nec.cst.pra;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.gson.Gson;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import nec.cst.pra.app.AppConfig;
import nec.cst.pra.app.AppController;
import nec.cst.pra.app.GlideApp;
import nec.cst.pra.db.DbVrp;
import nec.cst.pra.service.HttpService;
import katomaran.evao.lib.qrmodule.activity.QrScannerActivity;

/**
 * Created by vidhushi.g on 4/10/17.
 */

public class VRPRegistration extends AppCompatActivity

{
    private int CAMERA_PERMISSION_CODE = 23;
    DbVrp dbVrp;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    String imageUri = "";
    GPSTracker gps;

    private ProgressDialog pDialog;
    private static final String TAG = VRPRegistration.class.getSimpleName();
    private NestedScrollView scroll;
    private AutoCompleteTextView email;
    private CircleImageView image;
    CustomFontEditText name;
    CustomFontEditText geotag, contact, depNo;
    AutoCompleteTextView institution;
    CustomFontEditText password;
    CustomFontEditText address;
    CustomFontEditText whatsapp;
    CustomFontEditText confirmpassword;
    CustomFontTextView submit;
    MaterialBetterSpinner category;

//    Map<String, ArrayList<String>> institutionVillageMap = new HashMap<String, ArrayList<String>>() {{
//        put("One", new ArrayList<String>() {{"one","two","three"}});
//        put("Two", 2);
//        put("Three", 3);
//    }};


    String[] INSTITUTIONS = new String[10];
    Map<String, String> insitutionsIdMap = new HashMap<>();
    private String[] CATEGORY = new String[]{
            "Staff", "Student"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        gps = new GPSTracker(VRPRegistration.this);

        dbVrp = new DbVrp(this);
        //dbProfile = new DbProfile(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"
                + getResources().getString(R.string.app_name) + "</font>"));
        scroll = (NestedScrollView) findViewById(R.id.scroll);
        image = (CircleImageView) findViewById(R.id.imageview);
        geotag = (CustomFontEditText) findViewById(R.id.geotag);
        depNo = (CustomFontEditText) findViewById(R.id.depNo);
        contact = (CustomFontEditText) findViewById(R.id.contact);
        institution = (AutoCompleteTextView) findViewById(R.id.institution);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, INSTITUTIONS);
        //Getting the instance of AutoCompleteTextView
        institution.setThreshold(1);//will start working from first character
        institution.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        institution.setTextColor(Color.RED);

        category = (MaterialBetterSpinner) findViewById(R.id.category);
        LinearLayout categoryLin = (LinearLayout) findViewById(R.id.categoryLin);
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CATEGORY);
        category.setAdapter(sexAdapter);

        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    categoryLin.setVisibility(View.GONE);
                } else {
                    categoryLin.setVisibility(View.VISIBLE);
                }
            }
        });

        getAllUni();
        ImageView georefresh = (ImageView) findViewById(R.id.refresh);
        email = (AutoCompleteTextView) findViewById(R.id.gmail);
        submit = (CustomFontTextView) findViewById(R.id.r_submittxt);
        password = (CustomFontEditText) findViewById(R.id.password);
        whatsapp = (CustomFontEditText) findViewById(R.id.whatspp);
        name = (CustomFontEditText) findViewById(R.id.name);
        address = (CustomFontEditText) findViewById(R.id.address);
        confirmpassword = (CustomFontEditText) findViewById(R.id.confirmpassword);

        georefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if GPS enabled
                gps = new GPSTracker(VRPRegistration.this);
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
            gps = new GPSTracker(VRPRegistration.this);
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

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        GlideApp.with(VRPRegistration.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imageUri = pickResult.getUri().toString();
                    }
                })
                        .show(VRPRegistration.this);
            }
        });


        if (sharedpreferences.contains(update)) {
            try {
                Vrp vrp = new Gson()
                        .fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(1)), Vrp.class);
                imageUri = vrp.getImage();
                geotag.setText(vrp.getGeotag());
                email.setText(vrp.getGmail());
                contact.setText(vrp.contact);
                name.setText(vrp.name);
                institution.setText(vrp.institution);
                address.setText(vrp.address);
                depNo.setText(vrp.depNo);
                whatsapp.setText(vrp.whatsapp);
                category.setText(vrp.category);
                password.setText(vrp.password);
                confirmpassword.setText(vrp.password);
                if (vrp.category.equals("Staff")) {
                    categoryLin.setVisibility(View.GONE);
                } else {
                    categoryLin.setVisibility(View.VISIBLE);
                }
                password.setText(dbVrp.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(2));
                confirmpassword.setText(dbVrp.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(2));
                GlideApp.with(VRPRegistration.this).load(imageUri)
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                submit.setText("SUBMIT");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(contact.getText().toString() != null && contact.getText().toString().length() == 10)) {
                    name.setError("Enter valid number");
                } else if (name.getText().toString() == null && name.getText().toString().length() <= 0) {
                    name.setError("Enter valid name");
                } else if (!password.getText().toString().trim().equals(confirmpassword.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Password dosn't match", Toast.LENGTH_SHORT).show();
                } else if (!insitutionsIdMap.containsKey(institution.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter Valid University Name", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().length() <= 0
                        ) {
                    Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String vrpidnew;
                    Vrp farmerdata = new Vrp();
                    farmerdata.setData(sharedpreferences.getString(vrpid, ""), imageUri
                            , name.getText().toString()
                            , geotag.getText().toString()
                            , address.getText().toString()
                            , contact.getText().toString()
                            , whatsapp.getText().toString()
                            , institution.getText().toString()
                            , email.getText().toString()
                            , depNo.getText().toString(),
                            password.getText().toString(),
                            insitutionsIdMap.get(institution.getText().toString())
                            , category.getText().toString()
                    );
                    if (sharedpreferences.contains(update)) {
                        if (checkInternetConnection()) {
                            registerUser(farmerdata, true);
                        } else {
                            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        farmerdata.setId(contact.getText().toString() + "vrp_" + String.valueOf(System.currentTimeMillis()));
                        if (checkInternetConnection()) {
                            registerUser(farmerdata
                                    , false);
                            gps = new GPSTracker(VRPRegistration.this);
                        } else {
                            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_logout, menu);
        if (!sharedpreferences.contains(update)) {
            MenuItem item = menu.findItem(R.id.logout);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                SharedPreferences.Editor editor1 = sharedpreferences.edit();
                editor1.remove(update);
                editor1.commit();
                finish();
                return true;
            case R.id.logout:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(vrpid);
                editor.remove(update);
                editor.commit();
                Intent io = new Intent(VRPRegistration.this, MainActivity.class);
                io.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                io.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(io);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QrScannerActivity.QR_REQUEST_CODE) {

        }
    }

    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(
                        new Intent(VRPRegistration.this, QrScannerActivity.class),
                        QrScannerActivity.QR_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private void registerUser(final Vrp mdata, final boolean mUpdate) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        String url = AppConfig.URL_CREATE_NEW_VRP;
        if (mUpdate) {
            pDialog.setMessage("Updating ...");
        } else {
            pDialog.setMessage("Registering ...");
        }
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        if (mUpdate) {
                            dbVrp.updatedataByvrpid(sharedpreferences.getString(vrpid, ""),
                                    new Gson().toJson(mdata));
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.remove(update);
                            editor.commit();
                            Intent io = new Intent(VRPRegistration.this, ProfileActivity.class);
                            startActivity(io);
                            finish();
                        } else {
                            dbVrp.addData(mdata.getStudentid(), new Gson().toJson(mdata));
                            dbVrp.updatePassByvrpid(mdata.getStudentid(), mdata.password);
                            Toast.makeText(getApplicationContext(), "Student successfully registered." +
                                    " Try login now!", Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(vrpid, mdata.studentid);
                            editor.commit();
                        }
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                if (mUpdate) {
                    params.put("id", mdata.getStudentid());
                }
                params.put("vrpid", mdata.getStudentid());
                params.put("data", new Gson().toJson(mdata));
                params.put("password", mdata.password);
                params.put("username", mdata.name);
                params.put("mobile", mdata.contact);
                params.put("institution", mdata.institution);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    private void getAllUni() {
        String tag_string_req = "req_register";
        pDialog.setMessage("Get All University ...");
        String url = AppConfig.URL_ALL_UNI;
        showDialog();

        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response: ", response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONArray jsonArray = jObj.getJSONArray("datas");
                        INSTITUTIONS = new String[jsonArray.length()];
                        insitutionsIdMap = new HashMap<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            INSTITUTIONS[i] = jsonObject.getString("InstitutionName");
                            insitutionsIdMap.put(jsonObject.getString("InstitutionName"), jsonObject.getString("SN"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (VRPRegistration.this, android.R.layout.select_dialog_item, INSTITUTIONS);
                        //Getting the instance of AutoCompleteTextView
                        institution.setThreshold(1);//will start working from first character
                        institution.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                        institution.setTextColor(Color.RED);
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(VRPRegistration.this,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Registration Error: ", error.getMessage());
                hideDialog();
            }
        }) {
            public Map<String, String> getHeaders() {
                HashMap localHashMap = new HashMap();
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

}
