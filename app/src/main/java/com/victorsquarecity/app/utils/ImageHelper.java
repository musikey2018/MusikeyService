package com.victorsquarecity.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.victorsquarecity.app.friends.Friendlist_ViewHolder;
import com.victorsquarecity.app.home.Placelist_ViewHolder;
import com.victorsquarecity.app.services.ImageService;

import java.io.ByteArrayOutputStream;

/**
 * Created by mujahidmasood on 19.08.17.
 */

public class ImageHelper {

    private static final String TAG = "ImageHelper";

    private ImageHelper() {
    }

    ;

    /**
     * Converts Base64 string to bitmap to show on screen.
     *
     * @param encodedImage
     * @return
     */
    public static Bitmap base64ToBitmap(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.URL_SAFE);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    /**
     * Converts given bitmap to base64 string to save in cdn like firebase.
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.URL_SAFE);
        return imageString;
    }


    public static void downloadImageWithPicasso(String url, final Context context, final ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                        imageView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    /*public static void downloadImageWithPicasso(String url, final Context context, final Friendlist_ViewHolder holder) {
        Picasso.with(context)
                .load(url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                        holder.user_imageuri.setImageDrawable(drawable);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
*/
    public static Bitmap downloadUrlImage(Context ctx, String url) {

        Log.d(TAG, "DownladUrlImage" + url);

        //TODO send download request
        String data = ImageService.getInstance(ctx).download(url);
        //TODO Convert response to Base64 String
        Log.d(TAG, "downloadUrl() data " + data);

        Bitmap bitmap = null;
        if (data !=null && !data.isEmpty()) {
            bitmap = base64ToBitmap(data);
        }

        //TODO Base64 to bitmap

        return bitmap;

    }

}

