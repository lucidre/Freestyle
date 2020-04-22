package com.example.fileeditor;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class LoadTextTask extends AsyncTask<File, Void, String> {
	private boolean loaded;
	private LoadTextTask loadTask;
	private TextView editor;

	public LoadTextTask(boolean loaded, LoadTextTask loadTask, TextView editor) {
		this.loaded = loaded;
		this.loadTask = loadTask;
		this.editor = editor;
	}

	@Override
	protected String doInBackground(File... files) {
		String result = null;
		if (files[0].exists()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(files[0]));
				try {
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();
					while (line != null) {
						sb.append(line);
						sb.append("\n");
						line = br.readLine();
					}
					result = sb.toString();
				} finally {
					br.close();
				}
			} catch (IOException e) {
				Log.e(getClass().getSimpleName(), "Exception reading file", e);
			}
		}
		return (result);
	}


	@Override
	protected void onPostExecute(String s) {
		editor.setText(s);
		loadTask = null;

	}
}
