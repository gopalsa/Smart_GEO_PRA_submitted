package nec.cst.pra.gpdp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import nec.cst.pra.CustomFontEditText;
import nec.cst.pra.R;
import nec.cst.pra.app.AppController;
import nec.cst.pra.app.GlideApp;
import nec.cst.pra.app.Imageutils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class GalleryActivity extends AppCompatActivity implements Imageutils.ImageAttachmentListener {

    private static final int FINE_LOCATION_CODE = 199;
    ProgressDialog pDialog;
    Imageutils imageutils;

    CustomFontEditText images;
    CircleImageView profileImage;
    CircleImageView cancelImg;
    EditText remark;
    EditText testmonial;
    TextView submit;

    GalleryBean galleryBean = null;

    public static final String mypreference = "mypref";
    public static final String buSurveyerId = "buSurveyerIdKey";
    SharedPreferences sharedpreferences;
    private String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imageutils = new Imageutils(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


        images = (CustomFontEditText) findViewById(R.id.images);
        profileImage = (CircleImageView) findViewById(R.id.profileImage);
        cancelImg = (CircleImageView) findViewById(R.id.cancelImg);
        remark = (EditText) findViewById(R.id.remark);
        testmonial = (EditText) findViewById(R.id.testmonial);
        submit = (TextView) findViewById(R.id.submit);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageutils.imagepicker(1);
                imageutils.setImageAttachmentListener(new Imageutils.ImageAttachmentListener() {
                    @Override
                    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
                        imageAttachment(from, filename, file, uri, profileImage);
                    }
                });
            }
        });
        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlideApp.with(GalleryActivity.this).load(R.drawable.file)
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.file)
                        .into(profileImage);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryBean tempgalleryBean = new GalleryBean(
                        images.getText().toString(),
                        profileImage.getMfilePath(),
                        remark.getText().toString(),
                        testmonial.getText().toString());
                if (galleryBean == null) {
                    tempgalleryBean.setId(String.valueOf(System.currentTimeMillis()));
                } else {
                    tempgalleryBean.setId(galleryBean.id);
                }

                String jsonVal = new Gson().toJson(tempgalleryBean);
                getCreateTest(tempgalleryBean.id, sharedpreferences.getString(buSurveyerId, ""), jsonVal);
            }
        });
        try {
            galleryBean = (GalleryBean) getIntent().getSerializableExtra("object");
            if (galleryBean != null) {
                images.setText(galleryBean.images);
                remark.setText(galleryBean.remark);
                testmonial.setText(galleryBean.testmonial);
            }
        } catch (Exception e) {
            Log.e("xxxxxx", "Something went wrong");
        }
    }

    private void imageAttachment(int from, String filename, Bitmap file, Uri uri, CircleImageView circleImageView) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        circleImageView.setMfilePath(imageutils.getPath(uri));
        circleImageView.setIsImage("false");
        if (filename != null) {
            circleImageView.setIsImage("true");
            imageutils.createImage(file, filename, path, false);
        }

        GlideApp.with(GalleryActivity.this).load(imageutils.getPath(uri))
                .dontAnimate()
                .thumbnail(0.5f)
                .placeholder(R.drawable.file)
                .into(circleImageView);
    }

    private void getCreateTest(final String mId, final String surveyer, final String data) {
        this.pDialog.setMessage("Creating...");
        showDialog();
        StringRequest local16 = new StringRequest(1, "http://climatesmartcity.com/UBA/gpdpgallery.php", new Response.Listener<String>() {
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


    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);
    }
}

