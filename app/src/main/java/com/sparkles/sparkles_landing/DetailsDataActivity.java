package com.sparkles.sparkles_landing;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sparkles.sparkles_landing.models.HomeModel;
import com.squareup.picasso.Picasso;

public class DetailsDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_data);

        ImageView imageView = findViewById(R.id.imageRes);
        TextView titleTextView = findViewById(R.id.title);
        TextView addon1TextView = findViewById(R.id.date);

        HomeModel homeModel = (HomeModel) getIntent().getSerializableExtra("homeModel");

        if (homeModel != null) {
            titleTextView.setText(homeModel.getService());
            addon1TextView.setText(homeModel.getAdded_on());
            // Load the image URI string and convert it to a Bitmap
            Uri imageUri = Uri.parse(homeModel.getImage());
            Bitmap bitmap = getBitmapFromUri(imageUri);

            // Set the Bitmap in the ImageView
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                // Handle the case where the bitmap could not be loaded
               imageView.setImageResource(R.drawable.laundry);
            }
        }

    }

    private Bitmap getBitmapFromUri(Uri imageUri) { try {
        // Load the image from the URI
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1; // You can adjust the sample size if needed
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, options);
    } catch (Exception e) {
        e.printStackTrace();
        return null; // Return null if there's an error loading the bitmap
    }

    }
}