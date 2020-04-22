package com.oti.bookreading;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import java.util.List;

public class AddStringTask extends AsyncTask<Void, String, Void> {

	String[] item;
	RVAdapter adapter;
	Context context;
	AddStringTask task;
	private List<String> list;

	public AddStringTask(List<String> list, String[] item, RVAdapter adapter, Context context, AddStringTask task) {
		this.list = list;
		this.item = item;
		this.adapter = adapter;
		this.context = context;
		this.task = task;
	}

	@Override
	protected Void doInBackground(Void... voids) {// called next

		for (String item : item) {
			if (isCancelled()) {
				break;
			}
			publishProgress(item);// this activates the on progress update
			SystemClock.sleep(500);
		}
		return null;
	}

	@Override
	protected void onPreExecute() {// called first
		SystemClock.sleep(1000);
		Toast.makeText(context, "calling pre execute", Toast.LENGTH_SHORT).show();
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		SystemClock.sleep(500);
		Toast.makeText(context, "calling post execute", Toast.LENGTH_SHORT).show();
		task = null;// stopint the task when done to save memory
		super.onPostExecute(aVoid);
	}

	@Override
	protected void onProgressUpdate(String... values) {// if called update the text
		if (!isCancelled()) {
			String tex = values[0];
			adapter.add(values[0]);
		}
		super.onProgressUpdate(values);
	}
}
