package nec.cst.pra.survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.ImageViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.magorasystems.materialtoolbarspinner.MaterialToolbarSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nec.cst.pra.ConvertUtils;
import nec.cst.pra.R;
import nec.cst.pra.Vrp;
import nec.cst.pra.app.AppConfig;
import nec.cst.pra.app.AppController;
import nec.cst.pra.app.HeaderFooterPageEvent;
import nec.cst.pra.app.UserGroup;
import nec.cst.pra.app.UserGroupToolbarSpinnerAdapter;
import nec.cst.pra.db.DbVrp;
import nec.cst.pra.household.Mainbean;
import nec.cst.pra.household.SurveyItem;
import nec.cst.pra.survey.adapters.BaseStudentAdapter;
import nec.cst.pra.survey.adapters.StudentAdapterByName;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ColumnChartActivity extends AppCompatActivity implements SurveyItemClick,
        AdapterView.OnItemSelectedListener,
        View.OnClickListener,
        SearchView.OnCloseListener {

    ProgressDialog pDialog;
    private String TAG = getClass().getSimpleName();
    private Toolbar toolbar;

    private MaterialToolbarSpinner spinner;

    private UserGroupToolbarSpinnerAdapter spinnerAdapter;
    public static final String buSurveyerId = "buSurveyerIdKey";
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    String vrpId = "";
    public static final String tittle = "tittleKey";

    private Vrp vrp;
    DbVrp dbVrp;
    String selectedVillage = "";

    private static final int CAMERA_GALLERY_CODE = 100;

    static private ArrayList<Mainbean> surveyArrayList = new ArrayList<>();
    public static final String mypreference = "mypref";
    public static final String buStudentId = "buStudentIdKey";
    SharedPreferences sharedpreferences;


    static List<DataEntry> data = new ArrayList<>();
    static List<DataEntry> dataLine = new ArrayList<>();

    static String title = "Smart survey";
    static int invalidResponse = 0;
    static List<nec.cst.pra.survey.adapters.Response> responses = new ArrayList<>();
    static Map<String, Integer> departmentCountMap = new HashMap<>();
    private SurveyQuestionsAdapter mRecyclerAdapterMaster;
    private RecyclerView mastersList;
    ArrayList<SurveyItem> surveyItems = new ArrayList<>();
    static boolean isGraph = false;
    static NestedScrollView scrollview;
    static boolean isPrint = false;
    static PrintSurveyItem[] printSurveyItems = null;
    static int questionItem = 0;
    static String questionName;
    static String studentId;
    static ProgressDialog progressDialog;
    static String chartType = "bar";
    static int printTime = 0;
    static String finalDistLongMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        dbVrp = new DbVrp(this);
        if (sharedpreferences.contains(vrpid)) {
            vrpId = sharedpreferences.getString(vrpid, "").trim();
        }
        vrp = new Gson().fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(vrpId).get(1)), Vrp.class);


        initToolbar();


        if (!checkPermission(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE})) {
            requestPermission(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, CAMERA_GALLERY_CODE);
        }

        // getSupportActionBar().setTitle("Smart Survey");

        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        studentId = sharedpreferences.getString(buStudentId, "");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        surveyItems = AppConfig.loadHouseQuestions(this);
        mastersList = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerAdapterMaster = new SurveyQuestionsAdapter(this, surveyItems, this);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mastersList.setLayoutManager(thirdManager);
        mastersList.setAdapter(mRecyclerAdapterMaster);


        getVillages();

    }

    private void getAllData(final String villageName) {
        String tag_string_req = "req_register";
        showDialog();

        String url = null;
        boolean newString;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            newString = false;
            url = AppConfig.URL_GET_All_NEC_SURVEY;
        } else {
            newString = extras.getBoolean("isUser");
            if (newString) {
                url = AppConfig.URL_GET_All_NEC_SURVEY;
            } else {
                url = AppConfig.URL_GET_All_NEC_SURVEY;
            }
        }


        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                Log.d("Register Response: ", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONArray jsonArray = jObj.getJSONArray("uba");
                        surveyArrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject dataObject = jsonArray.getJSONObject(i);
                                Mainbean survey = new Gson().fromJson(dataObject.getString("data"), Mainbean.class);
                                survey.setId(dataObject.getString("surveyer").split("_")[0]);
                                //survey.setStudentid(dataObject.getString("studentid"));
                                if (villageName.equals("All")) {
                                    surveyArrayList.add(survey);
                                } else if (survey.getGramPanchayat().trim().equals(villageName.trim())) {
                                    surveyArrayList.add(survey);
                                }
                            } catch (Exception e) {
                                Log.e("xxxxxxxxxx", String.valueOf(i) + "        " + e.toString());
                            }
                        }
                        loadHomeFragment(1, surveyItems.get(1).question,
                                surveyItems.get(1).field, surveyItems.get(1).options, false);
                    }
                } catch (JSONException e) {
                    Log.e("xxxxxxxxxxx", e.toString());
                    Toast.makeText(getApplicationContext(), "Some Network Error.Try after some time", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Log.e("Registration Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                finish();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap localHashMap = new HashMap();

                localHashMap.put("key", "ALLDATA");
                localHashMap.put("db", "nec");
                localHashMap.put("university", sharedpreferences.getString(buSurveyerId, ""));

                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void loadHomeFragment(final int position, String question, String surveyName, String options,
                                  boolean isYesNo) {

        questionName = surveyName;
        invalidResponse = 0;
        data = new ArrayList<>();
        responses = new ArrayList<>();
        LinkedHashMap<String, LinkedHashMap<String, Long>> distLongMap = new LinkedHashMap<>();
        int total = 0;
        LinkedHashMap<String, LinkedHashMap<String, Long>> distLongMap1 = new LinkedHashMap<>();
        for (int i = 0; i < surveyArrayList.size(); i++) {
            LinkedHashMap<String, Long> responseLongMap = new LinkedHashMap<>();
            LinkedHashMap<String, Long> responseLongMap1 = new LinkedHashMap<>();
            nec.cst.pra.survey.adapters.Response response = new nec.cst.pra.survey.adapters.Response();
            Mainbean survey = surveyArrayList.get(i);
            response.setStudent(survey.id);
            response.setGps(survey.geoTag);

            String value = null;


            try {
                Class<?> clazz = survey.getClass();

                Field distField = clazz.getField("district");
                String distvalue = (String) distField.get(survey);
                distvalue = distvalue.replaceAll(("\n"), "").trim().toUpperCase();
                if (distvalue.length() > 0) {
                    if (distLongMap.containsKey(distvalue)) {
                        responseLongMap = distLongMap.get(distvalue);
                    }
                    if (distLongMap1.containsKey(distvalue)) {
                        responseLongMap1 = distLongMap1.get(distvalue);
                    }
                    String[] surveyFields = surveyName.split(",");
                    for (int f = 0; f < surveyFields.length; f++) {
                        try {
                            Field field = clazz.getField(surveyFields[f]);
                            value = (String) field.get(survey);
                            value = value.replaceAll(("\n"), "");
                            if (value != null && value.length() > 0) {
                                int intVal = Integer.parseInt(value);
                                total = total + intVal;
                                if (responseLongMap.containsKey(surveyFields[f])) {
                                    int newVal = responseLongMap.get(surveyFields[f]).intValue();
                                    long result = intVal + newVal;
                                    responseLongMap.put(surveyFields[f], result);
                                } else {
                                    long result = intVal;
                                    responseLongMap.put(surveyFields[f], result);
                                }


                                if (responseLongMap1.containsKey(surveyFields[f])) {
                                    int newVal = responseLongMap1.get(surveyFields[f]).intValue();
                                    long result = intVal + newVal;
                                    responseLongMap1.put(surveyFields[f], result);
                                } else {
                                    long result = intVal;
                                    responseLongMap1.put(surveyFields[f], result);
                                }
                            } else {
                                value = null;
                            }
                        } catch (Exception e) {
                            value = null;
                            Log.e("xxxxxxxxx", e.toString());
                        }
                    }
                    distLongMap.put(distvalue, responseLongMap);
                    distLongMap1.put(distvalue, responseLongMap1);
                }
            } catch (Exception e) {
                Log.e("xxxxxxxxx", e.toString());
            }

            if (value == null) {
                invalidResponse = invalidResponse + 1;
                response.setIsValid("Invalid");
            } else {
                response.setIsValid("Valid");
            }
            responses.add(response);
        }


        Iterator it = distLongMap.entrySet().iterator();
        while (it.hasNext()) {
            try {
                LinkedHashMap.Entry pair = (LinkedHashMap.Entry) it.next();
                LinkedHashMap<String, Long> keyMap = (LinkedHashMap<String, Long>) pair.getValue();
                Iterator itTemp = keyMap.entrySet().iterator();
                LinkedHashMap.Entry pairTemp = (LinkedHashMap.Entry) itTemp.next();
                long mainVal = (Long) pairTemp.getValue();
                long totalVal = new Long(total);
                double divVal = (double) mainVal / totalVal;
                double keyMapTemp = divVal * 100;
                Number val = getNumber(round(keyMapTemp, 2));
                data.add(new ValueDataEntry((String) pair.getKey(), val));
                dataLine.add(new CustomDataEntry((String) pair.getKey(), keyMap, val, totalVal));
                itTemp.remove();
            } catch (Exception e) {
                Log.e("xxxxxxxxxx", e.toString());
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        finalDistLongMap = surveyItems.get(position).question;
        title = question;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, new ExampleFragment(), question);
        fragmentTransaction.commitAllowingStateLoss();


    }

    @Override
    public void itemClick(int position) {

        if (surveyArrayList.size() <= 0) {
            Toast.makeText(getApplicationContext(), "No survey found.Please wait", Toast.LENGTH_SHORT).show();
        } else {
            isGraph = surveyItems.get(position).isGraph;
            loadHomeFragment(position, surveyItems.get(position).question, surveyItems.get(position).field,
                    surveyItems.get(position).options, surveyItems.get(position).isYesNo);
        }

    }


    public static class ExampleFragment extends Fragment implements BaseStudentAdapter.OnItemClickListener {

        private AnyChartView anyChartView;
        private View view;
        private StudentAdapterByName mSectionedRecyclerAdapter;
        private FloatingActionButton printImg;
        private ColumnChartActivity activity;
        private EditText relevanceToTheSurvey;
        private EditText infer;
        private Button btnSubmit;
        private ProgressBar progressInfer;
        private LinearLayout inferLayout;
        private TextView noGraphQues;
        private NestedScrollView mainLayout;
        ImageView barGraph;
        ImageView pieGraph;
        ImageView lineGraph;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.activity_col_chart_frag, container, false);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            scrollview = (NestedScrollView) view.findViewById(R.id.scrollview);
            printTime = 0;

            activity = (ColumnChartActivity) getActivity();

            anyChartView = view.findViewById(R.id.any_chart_view);
            printImg = view.findViewById(R.id.printImg);
            noGraphQues = view.findViewById(R.id.noGraphQues);
            TextView titleText = view.findViewById(R.id.title);
            TextView exttitle = view.findViewById(R.id.exttitle);
            infer = view.findViewById(R.id.infer);
            btnSubmit = view.findViewById(R.id.btnSubmit);
            progressInfer = view.findViewById(R.id.progressInfer);
            inferLayout = view.findViewById(R.id.inferLayout);
            mainLayout = view.findViewById(R.id.mainLayout);

            infer = view.findViewById(R.id.infer);
            relevanceToTheSurvey = view.findViewById(R.id.relevanceToTheSurvey);
            TextView subtitle = view.findViewById(R.id.subtitle);
            anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

            getUser();

            int validResponse = surveyArrayList.size() - invalidResponse;
            departmentCountMap.put("Invalid", invalidResponse);
            departmentCountMap.put("Valid", validResponse);
            String sub = "Total Survey : " + String.valueOf(surveyArrayList.size()) + "\nValid Responses : " + String.valueOf(validResponse)
                    + "\nInvalid or Skipped : " + String.valueOf(invalidResponse);
            titleText.setText("NEC UBA 2.0 Household survey\n" + title);
            exttitle.setVisibility(View.GONE);

            if (isGraph) {
                exttitle.setVisibility(View.GONE);
                exttitle.setText("No Graph for this Question");
                anyChartView.setVisibility(View.GONE);
                noGraphQues.setVisibility(View.VISIBLE);
            } else {
                anyChartView.setVisibility(View.VISIBLE);
                noGraphQues.setVisibility(View.GONE);
            }
            subtitle.setText(sub);


            if (studentId.equals("7339277826") || studentId.equals("9626047547")
                    || studentId.equals("9080593193") || studentId.equals("7812031342")) {

                infer.setEnabled(true);
                relevanceToTheSurvey.setEnabled(true);
                btnSubmit.setVisibility(View.VISIBLE);
            } else {
                infer.setEnabled(false);
                relevanceToTheSurvey.setEnabled(false);
                btnSubmit.setVisibility(View.GONE);
            }


            barGraph = (ImageView) view.findViewById(R.id.columnFab);
            pieGraph = (ImageView) view.findViewById(R.id.pieFab);
            lineGraph = (ImageView) view.findViewById(R.id.lineFab);

            if (chartType.equals("bar")) {
                if (printTime != 0) {
                    anyChartView.clear();
                }
                Cartesian cartesian = AnyChart.column();
                Column column = cartesian.column(data);
                column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.CENTER_BOTTOM)
                        .anchor(Anchor.CENTER_BOTTOM)
                        .offsetX(0d)
                        .offsetY(5d)
                        .format("{%Value}{groupsSeparator: }%");
                cartesian.animation(true);
                cartesian.title(title);
                cartesian.labels().enabled(true);
                cartesian.labels().position("inside");
                cartesian.labels().fontColor("#000000");
                cartesian.yScale().minimum(0d);
                cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.interactivity().hoverMode(HoverMode.BY_X);
                cartesian.xAxis(0).title("District");
                cartesian.yAxis(0).title("Value");
                anyChartView.setChart(cartesian);
                ImageViewCompat.setImageTintList(barGraph, ColorStateList.valueOf(Color.parseColor("#00cc66")));
                ImageViewCompat.setImageTintList(pieGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                ImageViewCompat.setImageTintList(lineGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            } else if (chartType.equals("pie")) {
                ImageViewCompat.setImageTintList(pieGraph, ColorStateList.valueOf(Color.parseColor("#00cc66")));
                ImageViewCompat.setImageTintList(barGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                ImageViewCompat.setImageTintList(lineGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                if (printTime != 0) {
                    anyChartView.clear();
                }
                Pie pie = AnyChart.pie();
                pie.data(data);
                pie.labels().position("outside");
                pie.legend().title().enabled(false);
                pie.legend()
                        .position("bottom")
                        .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
                        .align(Align.RIGHT);

                anyChartView.setChart(pie);
            } else if (chartType.equals("line")) {
                ImageViewCompat.setImageTintList(lineGraph, ColorStateList.valueOf(Color.parseColor("#00cc66")));
                ImageViewCompat.setImageTintList(barGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                ImageViewCompat.setImageTintList(pieGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                if (printTime != 0) {
                    anyChartView.clear();
                }
                Cartesian cartesian = AnyChart.line();
                cartesian.animation(true);
                cartesian.padding(10d, 20d, 5d, 20d);
                cartesian.crosshair().enabled(true);
                cartesian.crosshair()
                        .yLabel(true)
                        // TODO ystroke
                        .yStroke((Stroke) null, null, null, (String) null, (String) null);
                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.yAxis(0).title("Count (%)");
                cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
                cartesian.labels().enabled(true);
                cartesian.labels().position("inside");
                cartesian.labels().fontColor("#000000");
                Set set = Set.instantiate();
                set.data(data);
                Mapping series1Mapping = set.mapAs("{ x: 'x', value: '" + "value" + "' }");
                Line series1 = cartesian.line(series1Mapping);
                series1.name(title);
                series1.hovered().markers().enabled(true);
                series1.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series1.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d)
                        .format("{%Value}%");
                cartesian.legend().enabled(true);
                cartesian.legend().fontSize(13d);
                cartesian.legend().padding(0d, 0d, 10d, 0d);
                anyChartView.setChart(cartesian);
            }


            barGraph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chartType = "bar";
                    printTime = 10;
                    ImageViewCompat.setImageTintList(barGraph, ColorStateList.valueOf(Color.parseColor("#00cc66")));
                    ImageViewCompat.setImageTintList(pieGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    ImageViewCompat.setImageTintList(lineGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                    anyChartView.clear();
                    Cartesian cartesian = AnyChart.column();
                    Column column = cartesian.column(data);
                    column.tooltip()
                            .titleFormat("{%X}")
                            .position(Position.CENTER_BOTTOM)
                            .anchor(Anchor.CENTER_BOTTOM)
                            .offsetX(0d)
                            .offsetY(5d)
                            .format("{%Value}{groupsSeparator: }%");
                    cartesian.animation(true);
                    cartesian.labels().enabled(true);
                    cartesian.labels().position("inside");
                    cartesian.labels().fontColor("#000000");
                    cartesian.yScale().minimum(0d);
                    cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
                    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                    cartesian.interactivity().hoverMode(HoverMode.BY_X);
                    cartesian.xAxis(0).title("District");
                    cartesian.yAxis(0).title("Value");
                    anyChartView.setChart(cartesian);
                }
            });

            pieGraph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chartType = "pie";
                    printTime = 10;

                    ImageViewCompat.setImageTintList(pieGraph, ColorStateList.valueOf(Color.parseColor("#00cc66")));
                    ImageViewCompat.setImageTintList(barGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    ImageViewCompat.setImageTintList(lineGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                    anyChartView.clear();
                    Pie pie = AnyChart.pie();
                    pie.data(data);
                    pie.labels().position("outside");
                    pie.legend().title().enabled(false);
                    pie.legend()
                            .position("bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
                            .align(Align.RIGHT);

                    anyChartView.setChart(pie);
                }
            });

            lineGraph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    chartType = "line";
                    printTime = 10;

                    ImageViewCompat.setImageTintList(lineGraph, ColorStateList.valueOf(Color.parseColor("#00cc66")));
                    ImageViewCompat.setImageTintList(barGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    ImageViewCompat.setImageTintList(pieGraph, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                    anyChartView.clear();
                    Cartesian cartesian = AnyChart.line();
                    cartesian.animation(true);
                    cartesian.padding(10d, 20d, 5d, 20d);
                    cartesian.crosshair().enabled(true);
                    cartesian.crosshair()
                            .yLabel(true)
                            // TODO ystroke
                            .yStroke((Stroke) null, null, null, (String) null, (String) null);
                    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                    cartesian.yAxis(0).title("Count (%)");
                    cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
                    cartesian.labels().enabled(true);
                    cartesian.labels().position("inside");
                    cartesian.labels().fontColor("#000000");
                    Set set = Set.instantiate();
                    set.data(data);
                    Mapping series1Mapping = set.mapAs("{ x: 'x', value: '" + "value" + "' }");
                    Line series1 = cartesian.line(series1Mapping);
                    series1.name(title);
                    series1.hovered().markers().enabled(true);
                    series1.hovered().markers()
                            .type(MarkerType.CIRCLE)
                            .size(4d);
                    series1.tooltip()
                            .position("right")
                            .anchor(Anchor.LEFT_CENTER)
                            .offsetX(5d)
                            .offsetY(5d)
                            .format("{%Value}%");
                    cartesian.legend().enabled(true);
                    cartesian.legend().fontSize(13d);
                    cartesian.legend().padding(0d, 0d, 10d, 0d);
                    anyChartView.setChart(cartesian);

                }
            });


            anyChartView.setOnRenderedListener(new AnyChartView.OnRenderedListener() {
                @Override
                public void onRendered() {
                    if (isPrint) {


                        Thread logoTimer = new Thread() {
                            public void run() {
                                try {
                                    sleep(4000);

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                            if (isPrint) {
                                                printImg.setVisibility(View.GONE);
                                                inferLayout.setVisibility(View.GONE);
                                                int height = scrollview.getChildAt(0).getHeight();
                                                int width = scrollview.getChildAt(0).getWidth();
                                                if (height > 0 && width > 0) {
                                                    PrintSurveyItem printSurveyItem = new PrintSurveyItem(
                                                            titleText.getText().toString(),
                                                            "Survey Responses: Valid: " + String.valueOf(validResponse)
                                                                    + "  Invalid: " + String.valueOf(invalidResponse) +
                                                                    "  Total: " + String.valueOf(surveyArrayList.size()),
                                                            createBitmap(mainLayout), infer.getText().toString(),
                                                            relevanceToTheSurvey.getText().toString());
                                                    printSurveyItems[questionItem - 1] = printSurveyItem;
                                                }
                                                printImg.setVisibility(View.VISIBLE);
                                                inferLayout.setVisibility(View.VISIBLE);
                                                activity.callPrintMethod();

                                            }
                                        }
                                    });

                                } catch (Exception e) {
                                    Log.e("Error", e.toString());
                                } finally {

                                }
                            }
                        };
                        logoTimer.start();
                    }
                }
            });

            printImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    printSurveyItems = new PrintSurveyItem[1];
                    printImg.setVisibility(View.GONE);
                    inferLayout.setVisibility(View.GONE);
                    int height = scrollview.getChildAt(0).getHeight();
                    int width = scrollview.getChildAt(0).getWidth();
                    if (height > 0 && width > 0) {
                        PrintSurveyItem printSurveyItem = new PrintSurveyItem(
                                titleText.getText().toString(),
                                "Survey Responses: Valid: " + String.valueOf(validResponse)
                                        + "  Invalid: " + String.valueOf(invalidResponse) +
                                        "  Total: " + String.valueOf(surveyArrayList.size())
                                , createBitmap(mainLayout), infer.getText().toString(),
                                relevanceToTheSurvey.getText().toString());
                        printSurveyItems[0] = printSurveyItem;
                    }
                    printImg.setVisibility(View.VISIBLE);
                    inferLayout.setVisibility(View.VISIBLE);
                    activity.printFunction();

//                    pie.print(PaperSize.A4, false);

                }
            });
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            Comparator<nec.cst.pra.survey.adapters.Response> movieComparator =
                    (o1, o2) -> o1.getIsValid().compareTo(o2.getIsValid());
            Collections.sort(responses, movieComparator);
            mSectionedRecyclerAdapter = new StudentAdapterByName(responses, getContext(), departmentCountMap);
            mSectionedRecyclerAdapter.setOnItemClickListener(this);
            recyclerView.setAdapter(mSectionedRecyclerAdapter);
            mSectionedRecyclerAdapter.collapseAllSections();

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerUser();
                }
            });


            return view;
        }

        @Override
        public void onItemClicked(nec.cst.pra.survey.adapters.Response task) {

        }

        @Override
        public void onSubheaderClicked(int position) {
            if (mSectionedRecyclerAdapter.isSectionExpanded(mSectionedRecyclerAdapter.getSectionIndex(position))) {
                mSectionedRecyclerAdapter.collapseSection(mSectionedRecyclerAdapter.getSectionIndex(position));
            } else {
                mSectionedRecyclerAdapter.expandSection(mSectionedRecyclerAdapter.getSectionIndex(position));
            }
        }


        private void registerUser() {
            String tag_string_req = "req_register";
            progressInfer.setVisibility(View.VISIBLE);
            inferLayout.setVisibility(View.GONE);
            // showDialog();
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_CREATE_SURVEYANS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Register Response: ", response.toString());
                    progressInfer.setVisibility(View.GONE);
                    inferLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(activity, "Successfully updated", Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("Registration Error: ", error.getMessage());
                    Toast.makeText(activity,
                            "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                    progressInfer.setVisibility(View.GONE);
                    inferLayout.setVisibility(View.VISIBLE);
                }
            }) {
                protected Map<String, String> getParams() {
                    HashMap localHashMap = new HashMap();
                    localHashMap.put("name", questionName);
                    localHashMap.put("infer", infer.getText().toString());
                    localHashMap.put("relevance", relevanceToTheSurvey.getText().toString());
                    return localHashMap;
                }
            };
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }

        private void getUser() {
            String tag_string_req = "req_register";
            progressInfer.setVisibility(View.VISIBLE);
            inferLayout.setVisibility(View.GONE);
            // showDialog();
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_GET_All_SURVEYANS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Register Response: ", response.toString());
                    progressInfer.setVisibility(View.GONE);
                    inferLayout.setVisibility(View.VISIBLE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.getBoolean("error")) {
                            JSONObject dataObject = jsonObject.getJSONArray("survey").getJSONObject(0);

                            infer.setText(dataObject.getString("infer"));
                            relevanceToTheSurvey.setText(dataObject.getString("relevance"));


                        }
                    } catch (JSONException e) {
                        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("Registration Error: ", error.getMessage());
                    Toast.makeText(activity,
                            "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                    progressInfer.setVisibility(View.GONE);
                    inferLayout.setVisibility(View.VISIBLE);
                }
            }) {
                protected Map<String, String> getParams() {
                    HashMap localHashMap = new HashMap();
                    localHashMap.put("key", questionName);
                    return localHashMap;
                }
            };
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }

    }

    private static Bitmap createBitmap(NestedScrollView z) {
        View u = z;
        int height = z.getChildAt(0).getHeight();
        int width = z.getChildAt(0).getWidth();
        Bitmap b = AppConfig.getBitmapFromView(u, height, width);
        return Bitmap.createScaledBitmap(b, (int) PageSize.A6.getWidth(), (int) PageSize.A6.getHeight(), true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_print, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.print) {
            printSurveyItems = new PrintSurveyItem[surveyItems.size()];
            questionItem = 0;
            callPrintMethod();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void callPrintMethod() {
        if (questionItem < surveyItems.size() - 1) {

            progressDialog.setMessage("Converting Graphs into Pdf " + String.valueOf(questionItem + 1)
                    + "/" + String.valueOf(surveyItems.size()));
            if (questionItem == 0) {
                progressDialog.show();
            }
            isGraph = surveyItems.get(questionItem).isGraph;
            isPrint = true;
            loadHomeFragment(questionItem, surveyItems.get(questionItem).question, surveyItems.get(questionItem).field,
                    surveyItems.get(questionItem).options, surveyItems.get(questionItem).isYesNo);

        } else

        {
            isPrint = false;
            printFunction();
        }

        questionItem++;
    }


    private void printFunction() {

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            Log.d("PDFCreator", "PDF Path: " + path);
            File file = new File(dir, "demo" + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            Document document = new Document();
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
            AppConfig.addContent(document, printSurveyItems);
            document.close();


        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        progressDialog.hide();

        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                getApplicationContext().getPackageName() + AppConfig.packageName + ".provider",
                new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/PDF/" + "demo" + ".pdf"));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(photoURI
                , "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);

    }

    private boolean checkPermission(String[] permissions) {
        boolean result = false;
        for (int i = 0; i < permissions.length; i++) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[i]) == 0;
            if (!result) {
                return false;
            }
        }
        return result;
    }

    private void requestPermission(String[] permissions, int code) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CAMERA_GALLERY_CODE) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (locationAccepted && cameraAccepted) {
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access Storage.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access Storage", Toast.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            showMessageOKCancel("Permission Denied, You cannot access Storage",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                        CAMERA_GALLERY_CODE);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }
            }
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ColumnChartActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    // region AdapterView.OnItemSelectedListener (Spinner item selected)
    @Override
    public void onItemSelected(AdapterView<?> parent,
                               View view, int position, long id) {
        UserGroup userGroup = spinnerAdapter.getItem(position);
        selectedVillage = userGroup.getName();
        if (userGroup.getName().length() > 0) {
            getAllData(userGroup.getName());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(ColumnChartActivity.this,
                "Nothing is selected", Toast.LENGTH_SHORT).show();
    }
    // endregion

    // region View.OnClickListener
    @Override
    public void onClick(View view) {
        spinner.setVisibility(View.GONE);
        Toast.makeText(ColumnChartActivity.this, "Open search",
                Toast.LENGTH_SHORT).show();

    }
    // endregion

    // region SearchView.OnCloseListener
    @Override
    public boolean onClose() {
        spinner.setVisibility(View.VISIBLE);
        Toast.makeText(ColumnChartActivity.this, "Close search",
                Toast.LENGTH_SHORT).show();

        return false;
    }
    // endregion

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_print);
        toolbar.getMenu().findItem(R.id.print).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                printSurveyItems = new PrintSurveyItem[surveyItems.size()];
                questionItem = 0;
                callPrintMethod();
                return false;
            }
        });
    }


    private void hideDialog() {

        if (this.pDialog.isShowing()) this.pDialog.dismiss();
    }

    private void showDialog() {

        if (!this.pDialog.isShowing()) this.pDialog.show();
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

                        List<UserGroup> userGroupList = new ArrayList<>();
                        userGroupList.add(new UserGroup("All"));
                        for (int i = 0; i < lists.length; i++) {
                            String name = lists[i].trim();
                            if (name.length() > 2) {
                                UserGroup userGroup1 = new UserGroup();
                                userGroup1.setName(name);
                                userGroupList.add(userGroup1);
                            }
                        }

                        spinner = (MaterialToolbarSpinner)
                                toolbar.findViewById(R.id.mt_spinner);
                        spinnerAdapter = new UserGroupToolbarSpinnerAdapter(ColumnChartActivity.this);
                        spinnerAdapter.setUserGroupList(userGroupList);
                        spinner.setAdapter(spinnerAdapter);
                        spinner.setOnItemSelectedListener(ColumnChartActivity.this);
//                        if (userGroupList.size() > 0) {
//                            getAllData(userGroupList.get(0).getName());
//                        }
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


    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Map<String, Long> valueSet, Number val, double total) {
            super(x, val);
            Iterator it = valueSet.entrySet().iterator();
            int i = 2;
            while (it.hasNext()) {
                Map.Entry pairTemp = (Map.Entry) it.next();
                Long keyMapTemp = (Long) pairTemp.getValue();
                long mainVal = (Long) pairTemp.getValue();
                double divVal = (double) mainVal / total;
                Number valTemp = getNumber(round((divVal * 100), 2));
                setValue("value" + String.valueOf(i), valTemp);
                it.remove();
                i++;
            }
        }

    }

    private static Number getNumber(Number val) {
        return val;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}




