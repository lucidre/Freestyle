package com.oti.androidreading;

import android.hardware.SensorManager;
import android.preference.PreferenceFragment;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
			getFragmentManager().beginTransaction().add(android.R.id.content, new pref()).commit();

		}
	}


	public static class pref extends PreferenceFragment {
		@Override
		public void onCreate(@Nullable Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
		}
	}
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               