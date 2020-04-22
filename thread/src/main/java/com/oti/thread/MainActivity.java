package com.oti.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";
	Button button;
	volatile boolean stopThread = false;
	Handler handler = new Handler(); // it place of implementation is important as this would only work with the main thread

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = findViewById(R.id.button);
	}

	public void StartThread(View view) {
		if (!stopThread) {
			ExampleRunnable exampleRunnable = new ExampleRunnable(10);
			/**this works or the one below**/new Thread(exampleRunnable).start();
			exampleRunnable.run();// the two are not good as they are not runn on the main thread
			//so we use an handler

			//or
			new Thread(new Runnable() {
				@Override
				public void run() {
					//work
				}
			}).start();
			stopThread = !stopThread;
		}
	}

	public void StopThread(View view) {
		stopThread = !stopThread;
	}

	class ExampleThread extends Thread {
		int seconds;

		public ExampleThread(int seconds) {
			this.seconds = seconds;
		}

		@Override
		public void run() {
			for (int i = 0; i < seconds; i++) {
				Log.d(TAG, "run: " + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class ExampleRunnable implements Runnable {

		int seconds;

		public ExampleRunnable(int seconds) {
			this.seconds = seconds;
		}


		@Override
		public void run() {
			for (int i = 0; i < seconds; i++) {
				if (i == 5) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							button.setText(50 + "");
						}
					});
				}
				if (i == 7) {
					Handler handle = new Handler();//but this cant run by default on the main thread
					handle = new Handler(Looper.getMainLooper());//but this can run by default on the main thread
					handle.post(new Runnable() {
						@Override
						public void run() {
							button.setText(50 + "");
						}
					});
				}
				if (i == 8) {
					// we can also use any view object to do it
					button.post(new Runnable() {
						@Override
						public void run() {
							button.setText(50 + "");
						}
					});
				}
				if (i == 9) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							button.setText(50 + "");
						}
					});

				}
				Log.d(TAG, "run: " + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}


