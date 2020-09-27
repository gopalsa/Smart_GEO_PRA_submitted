package nec.cst.pra.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AppNewConfig {

    String name = "Press on Save Indian b_1.pdf";

    public static void CopyReadAssets(Context context, String name) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        Log.d("PDFCreator", "PDF Path: " + path);
        File file = new File(dir, name);

      //  File file = new File(context.getFilesDir(), name);
        try {
            in = assetManager.open(name);
            //out = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
            out = new FileOutputStream(file);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.parse("file://" + context.getFilesDir() + "/"+name), "application/pdf");
//
//        context.startActivity(intent);


        Uri photoURI = FileProvider.getUriForFile(context,
                context.getPackageName() + AppConfig.packageName + ".provider",
                new File(context.getFilesDir() + "/" + name));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(photoURI
                , "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);

    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
