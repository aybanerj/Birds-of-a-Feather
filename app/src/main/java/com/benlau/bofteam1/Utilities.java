package com.benlau.bofteam1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;

public class Utilities {

  public static final int IMAGE_SIZE = 128;

  public static Bitmap getImageFromUrl (String urlString) {
    Bitmap image = null;
    try {
      image = BitmapFactory.decodeStream(new URL(urlString).openConnection().getInputStream());
    } catch(IOException e) {
      System.out.println(e.getMessage());
    }
    return image;
  }

  public static Bitmap scaleProfile (Bitmap image) {
    return Bitmap.createScaledBitmap(image.copy(Bitmap.Config.ARGB_8888, true), IMAGE_SIZE, IMAGE_SIZE, true);
  }
}
