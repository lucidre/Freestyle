package com.oti.runnable;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Runnable {
	public final int PERIOD = 5000;
	public View root = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		root = findViewById(android.R.id.content);

	}

	@Override
	protected void onStart() {
		run();
		super.onStart();
	}

	@Override
	protected void onStop() {
		root.removeCallbacks(this);
		super.onStop();
	}


	@Override
	public void run() {
		Toast.makeText(this, "who oh!!!", Toast.LENGTH_SHORT).show();
		root.postDelayed(this, PERIOD);
	}
}
