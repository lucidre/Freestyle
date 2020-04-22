package com.oti.floatingactionbutton;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
	FloatingActionButton fabPlus, fab1, fab2;
	Animation fabOpen, fabClose, fabClockwise, fabAntiClockwise;
	boolean isOpen = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fabPlus = findViewById(R.id.fab_plus);
		fab1 = findViewById(R.id.fab_one);
		fab2 = findViewById(R.id.fab_two);

		fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
		fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
		fabClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
		fabAntiClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anti_clockwise);

		fabPlus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(MainActivity.this, "text", Toast.LENGTH_SHORT).show();
				if (isOpen) {
					fab1.setAnimation(fabClose);
					fab2.setAnimation(fabClose);
					fabPlus.setAnimation(fabAntiClockwise);
					fab1.setClickable(false);
					fab2.setClickable(false);
					fab1.setVisibility(View.INVISIBLE);
					fab2.setVisibility(View.INVISIBLE);
					isOpen = false;
				} else {
					fab1.setVisibility(View.VISIBLE);
					fab2.setVisibility(View.VISIBLE);
					fab1.setAnimation(fabOpen);
					fab2.setAnimation(fabOpen);
					fabPlus.setAnimation(fabClockwise);
					fab1.setClickable(true);
					fab2.setClickable(true);
					isOpen = true;
				}
			}
		});

	}
}
