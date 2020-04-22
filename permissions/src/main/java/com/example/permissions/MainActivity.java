package com.example.permissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
	private final String[] PERM = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
	};
	private final int KEY = 1337;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		boolean isGiven = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
//to check if the permission has not been denied returns true if the user revokes the permission
		/*Note that shouldShowRequestPermissionRationale() returns false if the user
		 declined the permission and checked the checkbox to ask you to stop pestering the
		 user .*/


		//cheching if a permission has not been given
		if (!canAccessCamera() || !canAccessContacts()) {// ie if it cant
			requestPermissions(PERM, KEY);
		}

//onRequestPermissionsResult(KEY); can be used also

		// a built in check self permission
		if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.MICROPHONE) == PackageManager.PERMISSION_DENIED) {
			// do something cool
		}

		if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
// do something cool
		}

	}


	// otherChecks
	private boolean canAccessLocation() {
		return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
	}

	private boolean canAccessCamera() {
		return (hasPermission(Manifest.permission.CAMERA));
	}

	private boolean canAccessContacts() {
		return (hasPermission(Manifest.permission.READ_CONTACTS));
	}

	//checking your permission yourself
	public boolean hasPermission(String perm) {
		return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
	}

}
