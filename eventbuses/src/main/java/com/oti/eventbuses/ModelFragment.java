package com.oti.eventbuses;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.fragment.app.ListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelFragment extends Fragment {
	private static final String[] items = {"lorem", "ipsum", "dolor",
			"sit", "amet", "consectetuer", "adipiscing", "elit", "morbi",
			"vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam",
			"vel", "erat", "placerat", "ante", "porttitor", "sodales",
			"pellentesque", "augue", "purus"};
	private List<String> model = Collections.synchronizedList(new ArrayList<String>());
	private boolean isStarted = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		if (!isStarted) {
			isStarted = true;
			new ModelFragment.LoadWordsThread().start();
		}
	}

	public ArrayList<String> getModel() {
		return (new ArrayList<String>(model));
	}

	class LoadWordsThread extends Thread {
		@Override

		public void run() {
			for (String item : items) {
				if (!isInterrupted()) {
					model.add(item);
					EventBus.getDefault().post(new WordReadyEvent(item));
					SystemClock.sleep(400);
				}
			}
		}
	}

	class WordReadyEvent {
		private String word;

		WordReadyEvent(String word) {
			this.word = word;
		}

		String getWord() {
			return (word);
		}
	}

	public class AsyncDemoFragment extends ListFragment {
		private ArrayAdapter<String> adapter = null;
		private ArrayList<String> model = null;

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			adapter =
					new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_list_item_1,
							model);
			getListView().setScrollbarFadingEnabled(false);
			setListAdapter(adapter);
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			EventBus.getDefault().register(this);
		}

		@Override
		public void onDetach() {
			EventBus.getDefault().unregister(this);
			super.onDetach();
		}

		@Subscribe(threadMode = ThreadMode.MAIN)
		public void onWordReady(WordReadyEvent event) {
			adapter.add(event.getWord());
		}

		public void setModel(ArrayList<String> model) {
			this.model = model;
		}
	}
}
