package com.oti.bookreading;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	// words to be populated
	private static final String[] items = {"lorem", "ipsum", "dolor", "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel", "ligula",
			"vitae", "arcu", "aliquet", "mollis", "etiam", "vel", "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque", "augue", "purus"};
	RVAdapter adapter;  // recycler adapter
	private ArrayList<String> model = new ArrayList<>();    // string array to be added later
	private AddStringTask task;    // asynctask do in background

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adapter = new RVAdapter(model, getLayoutInflater());        //initializing the adapter
		task = new AddStringTask(model, items, adapter, this, task); // starting the background task
		RecyclerView rv = findViewById(R.id.rv);//setting the rv inintialization
		rv.setLayoutManager(new LinearLayoutManager(getActivity()));
		rv.addItemDecoration(new DividerItemDecoration(getActivity(),
				DividerItemDecoration.VERTICAL));
		rv.setAdapter(adapter);

		task.execute();// executing the tread

	}

	private Context getActivity() {
		return this;
	}

	@Override
	protected void onStop() {
		if (task != null) {
			task.cancel(false);// cancelling the task
		}
		super.onStop();
	}
}
