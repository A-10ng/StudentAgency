package com.example.studentagency.asyncTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/03
 * desc:
 */
public class GetBitmapTask extends AsyncTask<List<Object>, Void, Bitmap> {

    private GetBitmapListener getBitmapListener;

    public void setGetBitmapListener(GetBitmapListener getBitmapListener) {
        this.getBitmapListener = getBitmapListener;
    }

    @Override
    protected Bitmap doInBackground(List<Object>... lists) {
        Bitmap resultBitmap = null;
        try {
            resultBitmap = Glide.with((Context) lists[0].get(0))
                    .asBitmap()
                    .load((String) lists[0].get(1))
                    .submit()
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (getBitmapListener != null) {
            getBitmapListener.getBitmap(bitmap);
        }
    }

    public interface GetBitmapListener {
        void getBitmap(Bitmap bitmap);
    }
}
