package nec.cst.pra.gpdp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import nec.cst.pra.R;
import nec.cst.pra.app.AppController;
import nec.cst.pra.gpdp.Facilitator.FacilitatorCreates;
import nec.cst.pra.gpdp.Facilitator.FacilitatorCreatesAdapter;
import nec.cst.pra.gpdp.FacilitatorFeedback.FacilitatorFeedback;
import nec.cst.pra.gpdp.FacilitatorFeedback.FacilitatorFeedbackAdapter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FacilitatorActivity extends AppCompatActivity implements FacilitatorItemClick{

    ProgressDialog pDialog;
    private String TAG = getClass().getSimpleName();

    String id;
    Button submit;
    FacilitatorBean facilitatorBean=null;

    public static final String mypreference = "mypref";
    public static final String buSurveyerId = "buSurveyerIdKey";
    SharedPreferences sharedpreferences;

    private RecyclerView fDetailList;
    private ArrayList<FacilitatorCreates> facilitatorCreatesArrayList = new ArrayList<>();
    FacilitatorCreatesAdapter facilitatorCreatesAdapter;

    private RecyclerView ffDetailList;
    private ArrayList<FacilitatorFeedback> facilitatorFeedbackArrayList = new ArrayList<>();
    FacilitatorFeedbackAdapter facilitatorFeedbackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilitator);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        submit =(Button) findViewById(R.id.submit);



        fDetailList = (RecyclerView) findViewById(R.id.fDetailList);
        facilitatorCreatesAdapter = new FacilitatorCreatesAdapter(this, facilitatorCreatesArrayList, this);
        final LinearLayoutManager facilitator = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fDetailList.setLayoutManager(facilitator);
        fDetailList.setAdapter(facilitatorCreatesAdapter);
        TextView facilitatorCreation = (TextView) findViewById(R.id.facilitatorCreation);
        ImageView fDetailImg = (ImageView) findViewById(R.id.fDetailImg);
        facilitatorCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFacilitatorCreationDialog(-1);
            }
        });
        fDetailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFacilitatorCreationDialog(-1);
            }
        });


        ffDetailList = (RecyclerView) findViewById(R.id.ffDetailList);
        facilitatorFeedbackAdapter = new FacilitatorFeedbackAdapter(this, facilitatorFeedbackArrayList, this);
        final LinearLayoutManager facilitatorfeedback = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ffDetailList.setLayoutManager(facilitatorfeedback);
        ffDetailList.setAdapter(facilitatorFeedbackAdapter);
        TextView fFeedback = (TextView) findViewById(R.id.fFeedback);
        ImageView ffDetailImg = (ImageView) findViewById(R.id.ffDetailImg);
        fFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFacilitatorfeedbackDialog(-1);
            }
        });
        ffDetailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFacilitatorfeedbackDialog(-1);
            }
        });


        final Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FacilitatorBean tempfacilitatorBean = new FacilitatorBean(
                        facilitatorCreatesArrayList,
                        facilitatorFeedbackArrayList);

                String jsonVal = new Gson().toJson(tempfacilitatorBean);
                if (facilitatorBean == null) {
                    tempfacilitatorBean.setId(String.valueOf(System.currentTimeMillis()));
                } else {
                    tempfacilitatorBean.setId(facilitatorBean.id);
                }

                getCreateTest(tempfacilitatorBean.id,  sharedpreferences.getString(buSurveyerId, ""), jsonVal);

            }
        });

    }

    public void showFacilitatorCreationDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FacilitatorActivity.this);
        LayoutInflater inflater = FacilitatorActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.facilitatorcreation, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText entityName = (EditText) dialogView.findViewById(R.id.entityName);
        final EditText userName = (EditText) dialogView.findViewById(R.id.userName);
        final EditText designations = (EditText) dialogView.findViewById(R.id.designations);
        final EditText EmailId = (EditText) dialogView.findViewById(R.id.EmailId);
        final EditText mobileNo = (EditText) dialogView.findViewById(R.id.mobileNo);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            FacilitatorCreates bean = facilitatorCreatesArrayList.get(position);
            entityName.setText(bean.getEntityName());
            userName.setText(bean.getUserName());
            designations.setText(bean.getDesignations());
            EmailId.setText(bean.getEmailId());
            mobileNo.setText(bean.getMobileNo());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    FacilitatorCreates bean = new FacilitatorCreates(entityName.getText().toString(),userName.getText().toString(),designations.getText().toString(),mobileNo.getText().toString(),
                            EmailId.getText().toString()

                    );
                    facilitatorCreatesArrayList.add(bean);
                } else {
                    facilitatorCreatesArrayList.get(position).setEntityName(entityName.getText().toString());
                    facilitatorCreatesArrayList.get(position).setUserName(userName.getText().toString());
                    facilitatorCreatesArrayList.get(position).setDesignation(designations.getText().toString());
                    facilitatorCreatesArrayList.get(position).setMobileNo(mobileNo.getText().toString());
                    facilitatorCreatesArrayList.get(position).setEmailId(EmailId.getText().toString());
                }
                facilitatorCreatesAdapter.notifyData(facilitatorCreatesArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showFacilitatorfeedbackDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FacilitatorActivity.this);
        LayoutInflater inflater = FacilitatorActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.facilitatorfeedback, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText gramName = (EditText) dialogView.findViewById(R.id.gramName);
        final EditText date = (EditText) dialogView.findViewById(R.id.date);
        final EditText people = (EditText) dialogView.findViewById(R.id.people);
        final EditText sc = (EditText) dialogView.findViewById(R.id.sc);
        final EditText st = (EditText) dialogView.findViewById(R.id.st);
        final EditText shg = (EditText) dialogView.findViewById(R.id.shg);
        final EditText women = (EditText) dialogView.findViewById(R.id.women);
        final EditText department = (EditText) dialogView.findViewById(R.id.department);
        final EditText frontLineWorker = (EditText) dialogView.findViewById(R.id.frontLineWorker);
        final EditText whetherAvailable = (EditText) dialogView.findViewById(R.id.whetherAvailable);
        final EditText whetherdelivered = (EditText) dialogView.findViewById(R.id.whetherdelivered);
        final EditText fund = (EditText) dialogView.findViewById(R.id.fund);
        final EditText resource = (EditText) dialogView.findViewById(R.id.resource);
        final EditText gaps = (EditText) dialogView.findViewById(R.id.gaps);
        final EditText resolution = (EditText) dialogView.findViewById(R.id.resolution);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            FacilitatorFeedback bean = facilitatorFeedbackArrayList.get(position);
            gramName.setText(bean.getGramName());
            date.setText(bean.getDate());
            people.setText(bean.getPeople());
            sc.setText(bean.getSc());
            st.setText(bean.getSt());
            shg.setText(bean.getShg());
            women.setText(bean.getWomen());
            department.setText(bean.getDepartment());
            frontLineWorker.setText(bean.getFrontLineWorker());
            whetherAvailable.setText(bean.getWhetherAvailable());
            whetherdelivered.setText(bean.getWhetherdelivered());
            fund.setText(bean.getFund());
            resource.setText(bean.getResource());
            gaps.setText(bean.getGaps());
            resolution.setText(bean.getResolution());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    FacilitatorFeedback bean = new FacilitatorFeedback(
                            gramName.getText().toString(),
                            date.getText().toString(),
                            people.getText().toString(),
                            sc.getText().toString(),
                            st.getText().toString(),
                            shg.getText().toString(),
                            women.getText().toString(),
                            department.getText().toString(),
                            frontLineWorker.getText().toString(),
                            whetherAvailable.getText().toString(),
                            whetherdelivered.getText().toString(),
                            fund.getText().toString(),
                            resource.getText().toString(),
                            gaps.getText().toString(),
                            resolution.getText().toString()
                    );
                    facilitatorFeedbackArrayList.add(bean);
                } else {
                    facilitatorFeedbackArrayList.get(position).setGramName(gramName.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setDate(date.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setPeople(people.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setSc(sc.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setSt(st.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setShg(shg.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setWomen(women.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setDepartment(department.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setFrontLineWorker(frontLineWorker.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setWhetherAvailable(whetherAvailable.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setWhetherdelivered(whetherdelivered.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setFund(fund.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setResource(resource.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setGaps(gaps.getText().toString());
                    facilitatorFeedbackArrayList.get(position).setResolution(resolution.getText().toString());
                }
                facilitatorFeedbackAdapter.notifyData(facilitatorFeedbackArrayList);
                b.cancel();
            }
        });
        b.show();
    }


    private void getCreateTest(final String mId, final String surveyer, final String data) {
        this.pDialog.setMessage("Creating...");
        showDialog();
        StringRequest local16 = new StringRequest(1, "http://climatesmartcity.com/UBA/gpdpfacilitator.php", new Response.Listener<String>() {
            public void onResponse(String paramString) {
                Log.d("tag", "Register Response: " + paramString.toString());
                hideDialog();
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    String str = localJSONObject1.getString("message");
                    if (localJSONObject1.getInt("success") == 1) {
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        finish();
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
                if (mId != null) {
                    localHashMap.put("id", String.valueOf(mId));
                }
                localHashMap.put("formId", String.valueOf(mId));
                localHashMap.put("surveyer", surveyer);
                localHashMap.put("data", data);


                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(local16, TAG);
    }

    private void hideDialog() {

        if (this.pDialog.isShowing()) this.pDialog.dismiss();
    }

    private void showDialog() {

        if (!this.pDialog.isShowing()) this.pDialog.show();
    }

    public void itemFacilitatorCreationClick(int position) {

        showFacilitatorCreationDialog(position);

    }

    @Override
    public void itemFacilitatorfeedbackClick(int position) {

        showFacilitatorfeedbackDialog(position);
    }

}
