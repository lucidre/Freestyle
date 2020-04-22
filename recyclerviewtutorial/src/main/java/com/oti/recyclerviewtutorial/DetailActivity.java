package com.oti.recyclerviewtutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import static com.oti.recyclerviewtutorial.MainActivity.EXTRA_CREATOR;
import static com.oti.recyclerviewtutorial.MainActivity.EXTRA_LIKES;
import static com.oti.recyclerviewtutorial.MainActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		// getting the intent sent from the main activity
		Intent intent = getIntent();
		String imageUri = intent.getStringExtra(EXTRA_URL);// it is using the string file not the name of the file
		String creatorName = intent.getStringExtra(EXTRA_CREATOR);
		String likes = intent.getStringExtra(EXTRA_LIKES);


		ImageView imageView = findViewById(R.id.image_view_detail);
		TextView creatorText = findViewById(R.id.text_view_creator_detail);
		TextView likesText = findViewById(R.id.text_view_likes_detail);

		//getting the image from picasso to the image view
		Picasso.get().load(imageUri).fit().centerInside().into(imageView);
		creatorText.setText(creatorName);
		String like = "Likes:" + likes;
		likesText.setText(like);
	}
}
