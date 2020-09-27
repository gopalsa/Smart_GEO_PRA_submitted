package nec.cst.pra.survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nec.cst.pra.ConvertUtils;
import nec.cst.pra.R;
import nec.cst.pra.Vrp;
import nec.cst.pra.app.AppConfig;
import nec.cst.pra.app.AppController;
import nec.cst.pra.app.HeaderFooterPageEvent;
import nec.cst.pra.db.DbVrp;
import nec.cst.pra.household.MainActivityHouseHold;


public class MainActivityAllVillagePrint extends AppCompatActivity implements PrintListener {


    private ProgressDialog pDialog;
    private RecyclerView mastersList;
    private MasterAllVillagePrintAdapter mRecyclerAdapterMaster;
    private ArrayList<GP> itemArrayList = new ArrayList<>();


    public static final String mypreference = "mypref";
    public static final String buSurveyerId = "buSurveyerIdKey";
    SharedPreferences sharedpreferences;

    DbVrp dbVrp;
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    String vrpId = "";
    public static final String tittle = "tittleKey";
    private String TAG = getClass().getSimpleName();

    private Vrp vrp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_all_survey);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        getSupportActionBar().setTitle("Village Survey");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        dbVrp = new DbVrp(this);
        if (sharedpreferences.contains(vrpid)) {
            vrpId = sharedpreferences.getString(vrpid, "").trim();
        }
        vrp = new Gson().fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(vrpId).get(1)), Vrp.class);

        FloatingActionButton addMaster = (FloatingActionButton) findViewById(R.id.addSurvey);
        addMaster.setVisibility(View.GONE);
        addMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAllVillagePrint.this, MainActivityHouseHold.class);
                startActivity(intent);
            }
        });
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mastersList = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerAdapterMaster = new MasterAllVillagePrintAdapter(this, itemArrayList, this);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mastersList.setLayoutManager(thirdManager);
        mastersList.setAdapter(mRecyclerAdapterMaster);
        getVillages();
    }

    private void getVillages() {
        this.pDialog.setMessage("Fetching...");
        showDialog();
        StringRequest local16 = new StringRequest(Request.Method.GET, AppConfig.URL_UNI_DETAIL_NAME + "?name=" + vrp.getInstitution(), new Response.Listener<String>() {
            public void onResponse(String paramString) {
                Log.d("tag", "Register Response: " + paramString.toString());
                hideDialog();
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    String str = localJSONObject1.getString("message");
                    if (localJSONObject1.getInt("success") == 1) {
                        JSONObject dataObject = localJSONObject1.getJSONArray("detail").getJSONObject(0);
                        String villagenames = dataObject.getString("VillageName");
                        String[] lists = villagenames.split(",");
                        for (int i = 0; i < lists.length; i++) {
                            String name = lists[i].trim();
                            if (name.length() > 2) {
                                itemArrayList.add(new GP(name,
                                        dataObject.getString("District"),
                                        dataObject.getString("State")));
                            }
                        }
                        mRecyclerAdapterMaster.notifyData(itemArrayList);
                    }
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    return;
                } catch (JSONException localJSONException) {
                    localJSONException.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError paramVolleyError) {
                Log.e("tag", "Fetch Error: " + paramVolleyError.getMessage());
                Toast.makeText(getApplicationContext(), paramVolleyError.getMessage(), Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> localHashMap = new HashMap<String, String>();
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(local16, TAG);
    }


    private void hideDialog() {
        if (this.pDialog.isShowing())
            this.pDialog.dismiss();
    }


    private void showDialog() {
        if (!this.pDialog.isShowing())
            this.pDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    private boolean isValidString(String string) {

        if (string == null) {
            return false;
        } else if (string.length() <= 0) {
            return false;
        } else if (string.startsWith("http")) {
            return false;
        }

        return true;
    }

    public String getfilename_from_path(String path) {
        return path.substring(path.lastIndexOf('/') + 1, path.length());

    }


    @Override
    public void onPrintClick(int position) {
        getAllData(itemArrayList.get(position).village);
    }


    private void getAllData(final String village) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Validating ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONObject dataObject = jObj.getJSONArray("datas").getJSONObject(0);
                        Survey bean = new Gson().fromJson(dataObject.getString("data"), Survey.class);
                        bean.setId(dataObject.getString("id"));
                        if (bean != null) {
                            try {
                                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";
                                File dir = new File(path);
                                if (!dir.exists())
                                    dir.mkdirs();
                                Log.d("PDFCreator", "PDF Path: " + path);
                                File file = new File(dir, bean.name + ".pdf");
                                FileOutputStream fOut = new FileOutputStream(file);
                                Document document = new Document();
                                document.setMargins(60, 60, 60, 60);
                                PdfWriter pdfWriter = PdfWriter.getInstance(document, fOut);
                                Rectangle rect = new Rectangle(175, 20, 530, 800);
                                pdfWriter.setBoxSize("art", rect);

                                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.cst_pdf);
                                Bitmap bu = BitmapFactory.decodeResource(getResources(), R.drawable.bu_logo);

                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] byteArray = stream.toByteArray();

                                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                                bu.compress(Bitmap.CompressFormat.PNG, 100, stream1);
                                byte[] byteArray1 = stream1.toByteArray();

                                HeaderFooterPageEvent event = new HeaderFooterPageEvent(Image.getInstance(byteArray), Image.getInstance(byteArray1));
                                pdfWriter.setPageEvent(event);

                                document.open();
                                AppConfig.addMetaData(document);
                                // AppConfig.addTitlePage(document);
                                AppConfig.addContent(document, bean, MainActivityAllVillagePrint.this);
                                document.close();


                            } catch (Error | Exception e) {
                                e.printStackTrace();
                            }
                            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                    getApplicationContext().getPackageName() + AppConfig.packageName + ".provider",
                                    new File(Environment.getExternalStorageDirectory()
                                            .getAbsolutePath() + "/PDF/" + bean.name + ".pdf"));

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(photoURI
                                    , "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(intent);

                        }else {

                        }

                    }
                } catch (Exception e) {
                    Log.e("xxxxxxxxxxx", e.toString());
                }
                hideDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),
                        "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap localHashMap = new HashMap();
                localHashMap.put("key", "village");
                localHashMap.put("createdby", sharedpreferences.getString(buSurveyerId, ""));
                localHashMap.put("name", village);
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}


