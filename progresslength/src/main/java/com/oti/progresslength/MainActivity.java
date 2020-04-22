package com.oti.progresslength;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	ProgressBar progressBar;
	int[] num = {0, 20, 60, -40, 0, 30, 10, -20, 40};

	ASyncTask task;
	int progress;

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressBar = findViewById(R.id.progress);
		task = new ASyncTask();
		task.execute();
		progressBar.setMax(100);
		progressBar.setMin(0);
		progress = progressBar.getProgress();


	}

	@Override
	protected void onDestroy() {
		if (task != null) {
			task.cancel(false);
		}
		super.onDestroy();
	}

	public class ASyncTask extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... integers) {
			for (int integer : num) {
				if (isCancelled()) break;
				if (integer < 100) {
					progressBar.setProgress(progress + integer);
					publishProgress(integer);
					SystemClock.sleep(300);
				}
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			Toast.makeText(MainActivity.this, "finished already", Toast.LENGTH_SHORT).show();
			progressBar.setProgress(100);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			if (!isCancelled()) {
				progressBar.setProgress(values[0], true);
			}
			super.onProgressUpdate(values);

		}


	}
}

