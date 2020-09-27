package nec.cst.pra.gpdp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import nec.cst.pra.R;

public class MainActivityGpdp extends AppCompatActivity {

    RelativeLayout panchayat;
    RelativeLayout userDetail;
    RelativeLayout facilitator;
    RelativeLayout gramSabha;
    RelativeLayout Gallery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gpdp);

        panchayat = (RelativeLayout) findViewById(R.id.panchayat);
        userDetail = (RelativeLayout) findViewById(R.id.userDetail);
        facilitator = (RelativeLayout) findViewById(R.id.facilitator);
        gramSabha = (RelativeLayout) findViewById(R.id.gramSabha);
        Gallery = (RelativeLayout) findViewById(R.id.gallery);

        panchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent4 = new Intent(MainActivityGpdp.this, PanchayatActivityAllSurvey.class);
                startActivity(localIntent4);
            }
        });
        userDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent5 = new Intent(MainActivityGpdp.this, UserActivityAllSurvey.class);
                startActivity(localIntent5);
            }
        });
        facilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent6 = new Intent(MainActivityGpdp.this, FacilitatorActivityAllSurvey.class);
                startActivity(localIntent6);
            }
        });

        gramSabha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent7 = new Intent(MainActivityGpdp.this, GramsabhaActivityAllSurvey.class);
                startActivity(localIntent7);
            }
        });
        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent8 = new Intent(MainActivityGpdp.this, GalleryActivityAllSurvey.class);
                startActivity(localIntent8);
            }
        });


    }

}