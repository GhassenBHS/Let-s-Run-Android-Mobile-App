package com.jogging.bhs.managers;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.jogging.bhs.R;

import java.io.InputStream;

import dmax.dialog.SpotsDialog;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    private Context context;
    SpotsDialog dialog;

    public DownloadImage(ImageView bmImage,Context context) {
        this.context=context ;
        this.bmImage = bmImage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        dialog = new ProgressDialog(context);
//        dialog.show();
//        dialog.setMessage("Downloading your profile image...");
//        dialog.setCancelable(false);

        dialog=new SpotsDialog(context, R.style.Custom);
        dialog.show();


    }



    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {

        bmImage.setImageBitmap(result);
        dialog.dismiss();
    }
}