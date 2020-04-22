package com.example.fileeditor;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.io.File;

public class SampleFragment extends FragmentPagerAdapter {

	private static final int[] TITLES = {R.string.internal,
			R.string.external, R.string.pub};
	private static final int TAB_INTERNAL = 0;
	private static final int TAB_EXTERNAL = 1;
	private static final String FILENAME = "test.txt";
	private final Context ctxt;


	SampleFragment(Context ctxt, FragmentManager fragmentManager) {
		super(fragmentManager);
		this.ctxt = ctxt;
	}

	@NonNull
	@Override
	public Fragment getItem(int position) {
		File fileToEdit;
		switch (position) {
			case TAB_INTERNAL:
				fileToEdit = new File(ctxt.getFilesDir(), FILENAME);
				break;
			case TAB_EXTERNAL:
				fileToEdit = new File(ctxt.getExternalFilesDir(null), FILENAME);
				break;
			default:
				fileToEdit = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME);
				break;
		}


		return (EditorFragment.newInstance(fileToEdit));
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return ctxt.getString(TITLES[position]);
	}
}
