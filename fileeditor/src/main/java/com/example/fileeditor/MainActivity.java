package com.example.fileeditor;

import android.os.Bundle;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AbstractPermissionActivity {
	/**
	 * the goal is to save the text typed into the edit text if the user presses
	 * back on the keyboard by default the text would have gone but with this
	 * it is read again from a file
	 * <p>
	 * Note that we do not need WRITE_EXTERNAL_STORAGE for getExternalFilesDir() on
	 * API Level 19+ devices. This leads to another possible permission strategy for this
	 * app:
	 */

	@Override
	public void onReady(Bundle state) {
		super.onReady(state);
		setContentView(R.layout.activity_main);
		ViewPager pager = findViewById(R.id.pager);
		pager.setAdapter(new SampleFragment(this, getSupportFragmentManager()));

	}

	@Override
	public String[] getDesiredPermissions() {
		return (new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE});
	}


	@Override
	public void onPermissionDenied() {
		super.onPermissionDenied();
		Toast.makeText(this, R.string.msg_sorry, Toast.LENGTH_LONG).show();
		finish();
	}
}
