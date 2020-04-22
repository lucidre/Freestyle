package com.oti.swipeuppicture;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {

	boolean isOpen = false;
	ConstraintSet set1, set2;
	ConstraintLayout constraintLayout;
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		set1 = new ConstraintSet();
		set2 = new ConstraintSet();

		imageView = findViewById(R.id.cover);
		constraintLayout = findViewById(R.id.constraint);

		set2.clone(this, R.layout.profile_epanded);

		set1.clone(constraintLayout);

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!isOpen) {
					TransitionManager.beginDelayedTransition(constraintLayout);
					set2.applyTo(constraintLayout);
					isOpen = !isOpen;
				} else {
					TransitionManager.beginDelayedTransition(constraintLayout);
					set1.applyTo(constraintLayout);
					isOpen = !isOpen;
				}
			}
		});


	}
}
