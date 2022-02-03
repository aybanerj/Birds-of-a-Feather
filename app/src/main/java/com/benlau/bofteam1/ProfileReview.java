package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ProfileReview extends AppCompatActivity {

    ImageView URL_pic;
    Button load;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_review);

        URL_pic = findViewById(R.id.test_url);
        load = findViewById(R.id.button);
        textView = findViewById(R.id.editTextTextPersonName);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlLink = textView.getText().toString();

                LoadImage loadimage = new LoadImage(URL_pic);
                loadimage.execute(urlLink);
            }
        });

    }


    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public  LoadImage(ImageView URL_pic) {
            this.imageView = URL_pic;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }   catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            URL_pic.setImageBitmap(bitmap);
        }
    }
}