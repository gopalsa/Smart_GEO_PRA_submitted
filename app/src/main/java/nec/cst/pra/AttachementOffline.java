package nec.cst.pra;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;

import java.io.File;

import nec.cst.pra.app.GlideApp;

public class AttachementOffline extends AppCompatActivity {
    private String filePath = null;
    private PhotoView imgPreview;

    private void previewMedia() {
        GlideApp.with(AttachementOffline.this).load(filePath)
                .dontAnimate()
                .thumbnail(0.5f)
                .placeholder(R.drawable.profile)
                .into(imgPreview);

        String value = changeCabs(filePath.replaceAll(".jpg", ""));
        getSupportActionBar().setTitle(value.substring(value.lastIndexOf('/') + 1, value.length()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        return;
    }

    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_mediaonline);
        imgPreview = ((PhotoView) findViewById(R.id.previewimg));
        ExoVideoView vidPreview = ((ExoVideoView) findViewById(R.id.video_view));
        imgPreview.setVisibility(View.VISIBLE);
        vidPreview.setVisibility(View.GONE);
        imgPreview.setOnMatrixChangeListener(new MatrixChangeListener());
        imgPreview.setOnPhotoTapListener(new PhotoTapListener());
        imgPreview.setOnSingleFlingListener(new SingleFlingListener());
        Intent localIntent = getIntent();
        this.filePath = localIntent.getStringExtra("filePath");
        if (this.filePath != null) {
            previewMedia();
            return;
        }
        Toast.makeText(getApplicationContext(), "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
    }


    private class PhotoTapListener implements OnPhotoTapListener {

        @Override
        public void onPhotoTap(ImageView view, float x, float y) {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;

            /// showToast(String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage, view == null ? 0 : view.getId()));
        }
    }

    private void showToast(CharSequence text) {
//        if (mCurrentToast != null) {
//            mCurrentToast.cancel();
//        }

        //   mCurrentToast = Toast.makeText(SimpleSampleActivity.this, text, Toast.LENGTH_SHORT);
        // mCurrentToast.show();
    }

    private class MatrixChangeListener implements OnMatrixChangedListener {

        @Override
        public void onMatrixChanged(RectF rect) {
            // mCurrMatrixTv.setText(rect.toString());
        }
    }

    private class SingleFlingListener implements OnSingleFlingListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Log.d("PhotoView", String.format(FLING_LOG_STRING, velocityX, velocityY));
            return true;
        }
    }

    public String changeCabs(String name) {
        StringBuilder sb = new StringBuilder(name);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }
}
/* Location:           D:\Gopal\downloads\Apk decompile java\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     smart.breed.contest.ActivityMedia
 * JD-Core Version:    0.6.0
 */