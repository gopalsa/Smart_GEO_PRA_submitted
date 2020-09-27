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

import nec.cst.pra.R;
import nec.cst.pra.app.AppController;

public class GramsabhaActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    private String TAG = getClass().getSimpleName();


    String id;
    EditText districtName;
    EditText blockName;
    EditText panchayat;
    EditText date;
    EditText facilitator;
    EditText secretaryName;
    EditText secretaryMobile;
    EditText available;
    EditText sarpanchName;
    EditText sarpanchMobile;
    EditText address;
    Button submit;

    GramBean gramBean=null;

    public static final String mypreference = "mypref";
    public static final String buSurveyerId = "buSurveyerIdKey";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gramsabha);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


        districtName =(EditText) findViewById(R.id.districtName);
        blockName =(EditText) findViewById(R.id.blockName);
        panchayat =(EditText) findViewById(R.id.panchayat);
        date =(EditText) findViewById(R.id.date);
        facilitator =(EditText) findViewById(R.id.facilitator);
        secretaryName =(EditText) findViewById(R.id.secretaryName);
        secretaryMobile =(EditText) findViewById(R.id.secretaryMobile);
        available =(EditText) findViewById(R.id.available);
        sarpanchName =(EditText) findViewById(R.id.sarpanchName);
        sarpanchMobile =(EditText) findViewById(R.id.sarpanchMobile);
        address =(EditText) findViewById(R.id.address);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GramBean tempgramBean = new GramBean(
                        districtName.getText().toString(),
                        blockName.getText().toString(),
                        panchayat.getText().toString(),
                        date.getText().toString(),
                        facilitator.getText().toString(),
                        secretaryName.getText().toString(),
                        secretaryMobile.getText().toString(),
                        available.getText().toString(),
                        sarpanchName.getText().toString(),
                        sarpanchMobile.getText().toString(),
                        address.getText().toString()
                );
                String jsonVal = new Gson().toJson(tempgramBean);
                Log.e("xxxxxxxxxxxxx", jsonVal);
                if (gramBean == null) {
                    tempgramBean.setId(String.valueOf(System.currentTimeMillis()));
                } else {
                    tempgramBean.setId(gramBean.id);
                }
                getCreateTest(tempgramBean.id,  sharedpreferences.getString(buSurveyerId, ""), jsonVal);

            }
        });
    }
    private void getCreateTest(final String mId, final String surveyer, final String data) {
        this.pDialog.setMessage("Creating...");
        showDialog();
        StringRequest local16 = new StringRequest(1, "http://climatesmartcity.com/UBA/gpdpgramsabha.php", new Response.Listener<String>() {
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
}
