package com.oti.recyclerviewtutorial;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import java.util.List;

public class AddStringTask extends AsyncTask<Void, ListItem, Void> {

	List<ListItem> listItems;
	List<ListItem> listItemAdapter;
	MyAdapter adapter;
	Context context;
	AddStringTask task;

	public AddStringTask(List<ListItem> listItems, List<ListItem> listItemAdapter, MyAdapter adapter, Context context, AddStringTask task) {
		this.listItems = listItems;
		this.listItemAdapter = listItemAdapter;
		this.adapter = adapter;
		this.context = context;
		this.task = task;
	}

	@Override
	protected Void doInBackground(Void... voids) {
		for (ListItem listItem : listItems) {
			if (isCancelled()) {
				break;
			}
			publishProgress(listItem);
			SystemClock.sleep(1000);

		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		SystemClock.sleep(1000);
		Toast.makeText(context, "calling pre execute", Toast.LENGTH_LONG).show();
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		task = null;
		Toast.makeText(context, "task is working", Toast.LENGTH_LONG).show();
		super.onPostExecute(aVoid);
	}

	@Override
	protected void onProgressUpdate(ListItem... values) {
		if (!isCancelled()) {
			adapter.onUpgrade(values[0]);
		}
		super.onProgressUpdate(values);

	}

	@Override
	protected void onCancelled(Void aVoid) {
		super.onCancelled(aVoid);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}
}
