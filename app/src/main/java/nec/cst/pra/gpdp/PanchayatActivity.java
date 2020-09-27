package nec.cst.pra.gpdp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nec.cst.pra.CustomFontEditText;
import nec.cst.pra.R;
import nec.cst.pra.app.AppController;

public class PanchayatActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    private String TAG = getClass().getSimpleName();

    String id;
    CustomFontEditText userCreate;
    CustomFontEditText userLocate;
    CustomFontEditText userType;
    EditText search;
    Button submit;
    PanchayatBean panchayatBean=null;

    public static final String mypreference = "mypref";
    public static final String buSurveyerId = "buSurveyerIdKey";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panchayat);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        userCreate = (CustomFontEditText) findViewById(R.id.userCreate);
        userLocate = (CustomFontEditText) findViewById(R.id.userLocate);
        userType = (CustomFontEditText) findViewById(R.id.userType);
        search = (EditText) findViewById(R.id.search);
        submit =(Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PanchayatBean temppanchayatBean = new PanchayatBean(
                        userCreate.getText().toString(),
                        userLocate.getText().toString(),
                        userType.getText().toString(),
                        search.getText().toString()
                );
                String jsonVal = new Gson().toJson(temppanchayatBean);
                Log.e("xxxxxxxxxxxxx", jsonVal);
                if (panchayatBean == null) {
                    temppanchayatBean.setId(String.valueOf(System.currentTimeMillis()));
                } else {
                    temppanchayatBean.setId(panchayatBean.id);
                }

                getCreateTest(temppanchayatBean.id, sharedpreferences.getString(buSurveyerId, ""), jsonVal);
            }
        });
        try{
            panchayatBean = (PanchayatBean) getIntent().getSerializableExtra("object");
            if (panchayatBean != null) {
                userCreate.setText(panchayatBean.userCreate);
                userLocate.setText(panchayatBean.userLocate);
                userType.setText(panchayatBean.userType);
                search.setText(panchayatBean.search);
            }
        }catch (Exception e) {
            Log.e("xxxxxx","Something went Wrong");
        }

    }

    private void getCreateTest(final String mId, final String surveyer, final String data) {
        this.pDialog.setMessage("Creating...");
        showDialog();
        StringRequest local16 = new StringRequest(1, "http://climatesmartcity.com/UBA/gpdppanchayat.php", new Response.Listener<String>() {
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
                    localHashMap.put("id", mId);
                }
                localHashMap.put("formId", mId);
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

}
