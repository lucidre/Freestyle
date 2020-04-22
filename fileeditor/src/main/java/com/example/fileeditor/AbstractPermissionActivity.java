package com.example.fileeditor;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class AbstractPermissionActivity extends AppCompatActivity {
	private static final String STATE_IN_PERMISSION = "permission";
	private static final int REQUEST_PERMISSION = 1338;
	/**
	 * getDesiredPermissions(), which returns the names of the permissions that
	 * the app wants
	 * • onReady(), which will be called once permission is granted by the user , and
	 * serves as an onCreate() substitute for the subclass
	 * • onPermissionDenied(), which will be called if the user declines granting the
	 * permission, so the subclass can do something (e.g., show a Toast, then
	 * finish() and go away)
	 */
	Bundle state;
	boolean isInPermission = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.state = savedInstanceState;

		if (state != null) {
			isInPermission = state.getBoolean(STATE_IN_PERMISSION, false);
		}
		if (hasAllPermissions(getDesiredPermissions())) {
			// check if it has all permission
			onReady(state);
		} else if (!isInPermission) {
			//if it has not been asked
			isInPermission = true;
			ActivityCompat.requestPermissions(this, netPermissions(getDesiredPermissions()), REQUEST_PERMISSION);
		}
	}

	public String[] getDesiredPermissions() {
		return null;
	}

	public boolean hasAllPermissions(String[] perms) {
		for (String perm : perms) {
			if (!hasPermission(perm)) {
				return false;
			}
		}
		return true;
	}

	public boolean hasPermission(String perm) {
		return (ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED);
	}

	public String[] netPermissions(String[] wanted) {
		//request for the one not added
		ArrayList<String> result = new ArrayList<>();
		for (String perm : wanted) {
			if (!hasPermission(perm)) {
				result.add(perm);
			}
		}
		String[] arr = new String[result.size()];
		return (result.toArray(arr));
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		isInPermission = false;
		if (requestCode == REQUEST_PERMISSION) {
			if (hasAllPermissions(getDesiredPermissions())) {
				onReady(state);
			} else {
				onPermissionDenied();
			}
		}
	}

	public void onReady(Bundle state) {
		// what the class would call as on create
	}

	public void onPermissionDenied() {
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(STATE_IN_PERMISSION, isInPermission);
	}
}
