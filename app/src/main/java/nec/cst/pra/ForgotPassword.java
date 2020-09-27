package nec.cst.pra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import nec.cst.pra.app.AppConfig;
import nec.cst.pra.app.AppController;

public class ForgotPassword extends AppCompatActivity {
    public static final String buSurveyerId = "buSurveyerIdKey";
    public static final String mypreference = "mypref";
    final String username = "gopal.ecretail@gmail.com";
    final String password = "ecretail@10";
    EditText mail;
    TextView submit;
    ProgressDialog pDialog;
    SharedPreferences sharedpreferences;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        mail = (EditText) findViewById(R.id.mail);
        submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCreateTest(mail.getText().toString());


            }
        });
    }

    private void getCreateTest(final String mail) {
        this.pDialog.setMessage("Creating...");
        showDialog();
        StringRequest local16 = new StringRequest(1, AppConfig.URL_FORGOT_PASSWORD, new Response.Listener<String>() {
            public void onResponse(String paramString) {
                Log.d("tag", "Register Response: " + paramString.toString());
                hideDialog();
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    String str = localJSONObject1.getString("message");
                    if (localJSONObject1.getInt("success") == 1) {

                        Vrp mainbean = new Gson().fromJson(localJSONObject1.getJSONArray("vrp")
                                .getJSONObject(0).getString("data"), Vrp.class);
                        if (mainbean.getGmail() != null && mainbean.getGmail().length() > 0) {
                            Properties props = new Properties();
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.starttls.enable", "true");
                            props.put("mail.smtp.host", "smtp.gmail.com");
                            props.put("mail.smtp.port", "587");

                            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                            });

                            try {
                                Message message = new MimeMessage(session);
                                message.setFrom(new InternetAddress(username));
                                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mainbean.getGmail()));
                                message.setSubject("Forgot Password");
                                message.setText(mainbean.getPassword());
                                Transport.send(message);
                                System.out.println("Mail sent successfully!");
                                finish();
                                Toast.makeText(getApplicationContext(), "Password  has been send your Mail successfully", Toast.LENGTH_SHORT).show();
                                return;

                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Your Mail Id is not Valid", Toast.LENGTH_SHORT).show();
                            return;
                        }
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

                localHashMap.put("mail", mail);


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