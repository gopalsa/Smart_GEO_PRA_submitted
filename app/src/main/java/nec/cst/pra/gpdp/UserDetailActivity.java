package nec.cst.pra.gpdp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import nec.cst.pra.CustomFontEditText;
import nec.cst.pra.R;
import nec.cst.pra.app.AppController;
import nec.cst.pra.gpdp.frontline.FrontLine;
import nec.cst.pra.gpdp.frontline.FrontLineAdapter;
import nec.cst.pra.gpdp.userdetail.UserDetail;
import nec.cst.pra.gpdp.userdetail.UserDetailAdapter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDetailActivity extends AppCompatActivity implements UserItemClick {

    ProgressDialog pDialog;
    private String TAG = getClass().getSimpleName();


    EditText deptname;
    EditText blockName;
    Button submit;

    UserBean userBean=null;

    public static final String mypreference = "mypref";
    public static final String buSurveyerId = "buSurveyerIdKey";
    SharedPreferences sharedpreferences;

    private RecyclerView userCreationList;
    private ArrayList<UserDetail> userDetailArrayList = new ArrayList<>();
    UserDetailAdapter userDetailAdapter;

    private RecyclerView frontDetailList;
    private ArrayList<FrontLine> frontLineArrayList = new ArrayList<>();
    FrontLineAdapter frontLineAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        deptname = (EditText) findViewById(R.id.deptname);
        blockName = (EditText) findViewById(R.id.blockName);
        submit = (Button) findViewById(R.id.submit);

        userCreationList = (RecyclerView) findViewById(R.id.userCreationList);
        userDetailAdapter = new UserDetailAdapter(this, userDetailArrayList, this);
        final LinearLayoutManager user = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        userCreationList.setLayoutManager(user);
        userCreationList.setAdapter(userDetailAdapter);
        TextView userCreation = (TextView) findViewById(R.id.userCreation);
        ImageView userDetailImg = (ImageView) findViewById(R.id.userDetailImg);
        userCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserDetailDialog(-1);
            }
        });
        userDetailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserDetailDialog(-1);
            }
        });


        frontDetailList = (RecyclerView) findViewById(R.id.frontDetailList);
        frontLineAdapter = new FrontLineAdapter(this, frontLineArrayList, this);
        final LinearLayoutManager frontline = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        frontDetailList.setLayoutManager(frontline);
        frontDetailList.setAdapter(frontLineAdapter);
        TextView frontlines = (TextView) findViewById(R.id.frontlines);
        ImageView frontDetailImg = (ImageView) findViewById(R.id.frontDetailImg);
        frontlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrontLineDialog(-1);
            }
        });
        frontDetailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrontLineDialog(-1);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent4 = new Intent(UserDetailActivity.this, MainActivityGpdp.class);
                startActivity(localIntent4);

                UserBean tempuserBean = new UserBean(
                        deptname.getText().toString(),
                        blockName.getText().toString(),
                        userDetailArrayList,
                        frontLineArrayList
                );
                String jsonVal = new Gson().toJson(tempuserBean);
                Log.e("xxxxxxxxxxxxx", jsonVal);
                if (userBean == null) {
                    tempuserBean.setId(String.valueOf(System.currentTimeMillis()));
                } else {
                    tempuserBean.setId(userBean.id);
                }
                getCreateTest(tempuserBean.id, sharedpreferences.getString(buSurveyerId, ""), jsonVal);

            }
        });

        try{
            userBean = (UserBean) getIntent().getSerializableExtra("object");
            if (userBean != null) {
                deptname.setText(userBean.deptname);
                blockName.setText(userBean.blockName);
            }
        }catch (Exception e) {
            Log.e("xxxxxx","Something went Wrong");
        }

    }

    public void showUserDetailDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UserDetailActivity.this);
        LayoutInflater inflater = UserDetailActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.userdetails, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText userserialno = (EditText) dialogView.findViewById(R.id.userserialno);
        final EditText userstateName = (EditText) dialogView.findViewById(R.id.userstateName);
        final EditText useruserName = (EditText) dialogView.findViewById(R.id.useruserName);
        final EditText usermobileNo = (EditText) dialogView.findViewById(R.id.usermobileNo);
        final EditText userdesignation = (EditText) dialogView.findViewById(R.id.userdesignation);
        final EditText userentityType = (EditText) dialogView.findViewById(R.id.userentityType);
        final EditText userentityName = (EditText) dialogView.findViewById(R.id.userentityName);
        final EditText userEmailId = (EditText) dialogView.findViewById(R.id.userEmailId);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            UserDetail bean = userDetailArrayList.get(position);
            userserialno.setText(bean.getUserserialno());
            userstateName.setText(bean.getUserstateName());
            useruserName.setText(bean.getUseruserName());
            usermobileNo.setText(bean.getUsermobileNo());
            userdesignation.setText(bean.getUserdesignation());
            userentityType.setText(bean.getUserentityType());
            userentityName.setText(bean.getUserentityName());
            userEmailId.setText(bean.getUserEmailId());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    UserDetail bean = new UserDetail(
                            userserialno.getText().toString(),
                            userstateName.getText().toString(),
                            useruserName.getText().toString(),
                            usermobileNo.getText().toString(),
                            userdesignation.getText().toString(),
                            userentityType.getText().toString(),
                            userentityName.getText().toString(),
                            userEmailId.getText().toString()
                    );
                    userDetailArrayList.add(bean);
                } else {
                    userDetailArrayList.get(position).setUserserialno(userserialno.getText().toString());
                    userDetailArrayList.get(position).setUserstateName(userstateName.getText().toString());
                    userDetailArrayList.get(position).setUseruserName(useruserName.getText().toString());
                    userDetailArrayList.get(position).setUsermobileNo(usermobileNo.getText().toString());
                    userDetailArrayList.get(position).setUserdesignation(userdesignation.getText().toString());
                    userDetailArrayList.get(position).setUserentityType(userentityType.getText().toString());
                    userDetailArrayList.get(position).setUserentityName(userentityName.getText().toString());
                    userDetailArrayList.get(position).setUserEmailId(userEmailId.getText().toString());
                }
                userDetailAdapter.notifyData(userDetailArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showFrontLineDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UserDetailActivity.this);
        LayoutInflater inflater = UserDetailActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.frontline, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText frontgramName = (EditText) dialogView.findViewById(R.id.frontgramName);
        final EditText frontuserName = (EditText) dialogView.findViewById(R.id.frontuserName);
        final EditText frontdesignation = (EditText) dialogView.findViewById(R.id.frontdesignation);
        final EditText frontmobileNo = (EditText) dialogView.findViewById(R.id.frontmobileNo);
        final EditText frontEmailId = (EditText) dialogView.findViewById(R.id.frontEmailId);
        final EditText frontdeptname = (CustomFontEditText) dialogView.findViewById(R.id.frontdeptname);
        final EditText frontfrontLineWorker = (EditText) dialogView.findViewById(R.id.frontfrontLineWorker);


        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            FrontLine bean = frontLineArrayList.get(position);
            frontgramName.setText(bean.getFrontgramName());
            frontuserName.setText(bean.getFrontuserName());
            frontdesignation.setText(bean.getFrontdesignation());
            frontmobileNo.setText(bean.getFrontmobileNo());
            frontEmailId.setText(bean.getFrontEmailId());
            frontdeptname.setText(bean.getFrontdeptname());
            frontfrontLineWorker.setText(bean.getFrontfrontLineWorker());

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    FrontLine bean = new FrontLine(frontgramName.getText().toString(),
                            frontuserName.getText().toString(),
                            frontdesignation.getText().toString(),
                            frontmobileNo.getText().toString(),
                            frontEmailId.getText().toString(),
                            frontdeptname.getText().toString(),
                            frontfrontLineWorker.getText().toString()
                    );
                    frontLineArrayList.add(bean);
                } else {
                    frontLineArrayList.get(position).setFrontgramName(frontgramName.getText().toString());
                    frontLineArrayList.get(position).setFrontuserName(frontuserName.getText().toString());
                    frontLineArrayList.get(position).setFrontdesignation(frontdesignation.getText().toString());
                    frontLineArrayList.get(position).setFrontmobileNo(frontmobileNo.getText().toString());
                    frontLineArrayList.get(position).setFrontEmailId(frontEmailId.getText().toString());
                    frontLineArrayList.get(position).setFrontdeptname(frontdeptname.getText().toString());
                    frontLineArrayList.get(position).setFrontfrontLineWorker(frontfrontLineWorker.getText().toString());
                }
                frontLineAdapter.notifyData(frontLineArrayList);
                b.cancel();
            }
        });
        b.show();
    }


    private void getCreateTest(final String mId, final String surveyer, final String data) {
        this.pDialog.setMessage("Creating...");
        showDialog();
        StringRequest local16 = new StringRequest(1, "http://climatesmartcity.com/UBA/gpdpuser.php", new Response.Listener<String>() {
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


    @Override
    public void itemUserDetailClick(int position) {
        showUserDetailDialog(position);

    }

    public void itemFrontLineClick(int position) {
        showFrontLineDialog(position);

    }



}

