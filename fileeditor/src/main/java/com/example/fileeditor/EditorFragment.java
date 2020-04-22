package com.example.fileeditor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;

public class EditorFragment extends Fragment {

	private static String KEY_FILE = "";
	EditText editor;
	private LoadTextTask loadTask = null;
	private boolean loaded = false;

	public EditorFragment(String KEY_FILE) {
		EditorFragment.KEY_FILE = KEY_FILE;
	}

	public EditorFragment() {
	}

	public static Fragment newInstance(File fileToEdit) {
		EditorFragment editorFragment = new EditorFragment();
		Bundle args = new Bundle();
		args.putSerializable(KEY_FILE, fileToEdit);
		editorFragment.setArguments(args);

		return editorFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onDestroy() {
		if (loadTask != null) {
			loadTask.cancel(false);
		}
		super.onDestroy();
	}

	@Override
	public void onPause() {
		if (loaded) {
			new SaveThread(editor.getText().toString(), (File) getArguments().getSerializable(KEY_FILE), getContext()).start();
		}
		super.onPause();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.editor, container, false);
		editor = result.findViewById(R.id.editor);
		return (result);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		if (!loaded) {
			loadTask = new LoadTextTask(loaded, loadTask, editor);
			loadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (File) getArguments().getSerializable(KEY_FILE));
			loaded = true;

		}
	}

}
