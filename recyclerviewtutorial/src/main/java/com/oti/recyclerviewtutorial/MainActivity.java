package com.oti.recyclerviewtutorial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements onItemClickListener {

	public static final String EXTRA_URL = "imageUrl";
	public static final String EXTRA_CREATOR = "creator name";
	public static final String EXTRA_LIKES = "likes count";
	public boolean isDeleting = false;
	RecyclerView recyclerView;
	MyAdapter myAdapter;
	List<ListItem> listItems = new ArrayList<>();
	List<ListItem> selectionsItems = new ArrayList<>();
	List<ListItem> items = new ArrayList<>();
	List<ListItem> archivedListItems = new ArrayList<>();
	ListItem deleatedItem = null;
	private String URL_DATA;
	private AddStringTask task;
	private RequestQueue requestQueue;
	private TextView deleteTxt;
	private Toolbar toolbar;
	private int counter = 0;

	@Override
	protected void onStop() {
		if (task != null) {
			task.cancel(false);// cancelling the task
		}
		if (requestQueue != null) {
			requestQueue.stop();
		}
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		deleteTxt = findViewById(R.id.counter_text);
		deleteTxt.setVisibility(View.GONE);
		toolbar = findViewById(R.id.toolBar);
		setSupportActionBar(toolbar);

		recyclerView = findViewById(R.id.recyclerview);

		AutoFitGridLayoutManager autoFitGridLayoutManager = new AutoFitGridLayoutManager(this, 500);
		//	recyclerView.setLayoutManager(autoFitGridLayoutManager);
		recyclerView.setLayoutManager(new CustomLinearLayoutManager(this));
		recyclerView.setHasFixedSize(true);

		myAdapter = new MyAdapter(listItems, MainActivity.this);

		requestQueue = Volley.newRequestQueue(getApplicationContext());

		//URL_DATA = loadJSONFromAsset();// loading json from asset folder
		URL_DATA = "https://pixabay.com/api/?key=16034664-c417fb553aca2dc01f231913d&q=dogs&image_type=photo&pretty=true";

		loadRecyclerViewDataOnlined();
		//requestQueue.start();

		recyclerView.setAdapter(myAdapter);

		myAdapter.setOnItemClickListener(this);//getting the onclick listener form adapter and setting it

		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallBacks());
		itemTouchHelper.attachToRecyclerView(recyclerView);

		final Button addItem = findViewById(R.id.btn_add_item);
		addItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				addItemToList();
			}
		});
	}

	private void addItemToList() {

	}

	private ItemTouchHelper.Callback createHelperCallBacks() {
		ItemTouchHelper.SimpleCallback callback =
				new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

					@Override
					public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
						moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
						return true;
					}

					@Override
					public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
						switch (direction) {
							case ItemTouchHelper.LEFT:
								swipeItem(viewHolder.getAdapterPosition(), "left");

								break;

							case ItemTouchHelper.RIGHT:
								swipeItem(viewHolder.getAdapterPosition(), "right");
								break;

						}

					}

					@Override
					public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
						RecyclerViewSwipeDecorator.Builder decorator = new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
						decorator.addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
						decorator.addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
						decorator.addSwipeLeftActionIcon(R.drawable.ic_delete);
						decorator.addSwipeRightActionIcon(R.drawable.ic_archive);
						decorator.create().decorate();

						super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

					}
				};
		return callback;
	}

	private void moveItem(int oldPosition, int newPosition) {
		try {
			ListItem listItem = items.get(oldPosition);
			listItems.remove(oldPosition);
			listItems.add(newPosition, listItem);
			myAdapter.notifyItemInserted(listItems.indexOf(listItem));
		} catch (Exception ignored) {
		}
	}

	private void swipeItem(final int adapterPosition, String direction) {
		if (direction.equalsIgnoreCase("left")) {
			deleatedItem = listItems.remove(adapterPosition);
			//	listItems.remove(adapterPosition);
			myAdapter.notifyItemRemoved(adapterPosition);
			Snackbar.make(recyclerView, "you deleted " + deleatedItem.getHead(), BaseTransientBottomBar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					listItems.add(adapterPosition, deleatedItem);
					myAdapter.notifyItemInserted(adapterPosition);

				}
			}).show();

		} else {
			final ListItem list = listItems.remove(adapterPosition);
			archivedListItems.add(list);
			myAdapter.notifyItemRemoved(adapterPosition);

			Snackbar.make(recyclerView, list.getHead() + ", archived", BaseTransientBottomBar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					archivedListItems.remove(list);
					listItems.add(adapterPosition, list);
					myAdapter.notifyItemInserted(adapterPosition);

				}
			}).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menus = getMenuInflater();
		menus.inflate(R.menu.search_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.action_search) {
			SearchView searchView = (SearchView) item.getActionView();
			searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);//changing the keyboark settings
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					myAdapter.getFilter().filter(newText);
					return false;
				}
			});
		} else if (item.getItemId() == R.id.delete) {

			myAdapter.updateAdapterAfterDelete(selectionsItems);
			deleteOP();
		} else if (item.getItemId() == android.R.id.home) {
			deleteOP();
			myAdapter.notifyDataSetChanged();
		}
		return super.onOptionsItemSelected(item);
	}

	private void deleteOP() {
		isDeleting = false;

		toolbar.getMenu().clear();
		toolbar.inflateMenu(R.menu.search_menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		deleteTxt.setVisibility(View.GONE);
		deleteTxt.setText("0 item selected");
		counter = 0;
		selectionsItems.clear();
	}

	//used when loading data from a json file in assets
	private void loadRecyclerViewData() {

		try { //must be try catch
			JSONObject jsonObject = new JSONObject(URL_DATA);

			JSONArray jsonArray = jsonObject.getJSONArray("heroes");// name of the array in the json file


			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				ListItem listItem = new ListItem(object.getString("name"), object.getString("about"), object.getString("image"));
				items.add(listItem); // add to the the temporary list item to populate the list view
			}
			task = new AddStringTask(items, listItems, myAdapter, this, task);// populating the list view with style
			task.execute();

		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

	//used when loading json file from online using jsonObjectRequest
	private void loadRecyclerViewDataOnline() {

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_DATA,
				null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jsonObject) {
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("hits");

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						ListItem listItem = new ListItem(object.getString("likes"),
								object.getString("user"), object.getString("webformatURL"));

						items.add(listItem);
					}
					task = new AddStringTask(items, listItems, myAdapter, getApplicationContext(), task);
					task.execute();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		});


		requestQueue.add(jsonObjectRequest);// must be added to the requestQueue

	}

	//used when loading json file from online using stringObjectRequest
	private void loadRecyclerViewDataOnlined() {
		StringRequest
				jsonObjectRequest = new StringRequest(Request.Method.GET, URL_DATA,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							JSONObject jsonObject = new JSONObject(response);
							JSONArray jsonArray = jsonObject.getJSONArray("hits");

							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								ListItem listItem = new ListItem(object.getString("likes"),
										object.getString("user"), object.getString("webformatURL"));

								items.add(listItem);
							}
							task = new AddStringTask(items, listItems, myAdapter, getApplicationContext(), task);
							task.execute();


						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});


		requestQueue.add(jsonObjectRequest);

	}

	// when getting file from the assets this is the first stage,this saves the file to the string
	public String loadJSONFromAsset() {
		String json = null;
		try {
			InputStream is = getAssets().open("avengers.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, StandardCharsets.UTF_8);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}

	//if the recyclerview is clicked from it gets chained back to this place
	@Override
	public void onItemClick(int position, View view, ListItem listItem) {
		if (!isDeleting) {
			Intent detainIntent = new Intent(this, DetailActivity.class);
			ListItem clickedItem = listItems.get(position);
			detainIntent.putExtra(EXTRA_URL, clickedItem.getImageUri());
			detainIntent.putExtra(EXTRA_CREATOR, clickedItem.getDesc());
			detainIntent.putExtra(EXTRA_LIKES, clickedItem.getHead());
			startActivity(detainIntent);
		} else if (isDeleting) {
			prepareSelection(view, position, listItem);
		}
	}

	@Override
	public void onItemLongClicked() {
		toolbar.getMenu().clear();
		toolbar.inflateMenu(R.menu.menu_action_mode);
		deleteTxt.setVisibility(View.VISIBLE);
		isDeleting = true;
		myAdapter.notifyDataSetChanged();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void prepareSelection(View view, int position, ListItem listItem) {
		CheckBox checkBox = view.findViewById(R.id.checkBox);
		if (!checkBox.isChecked()) {

			selectionsItems.add(items.get(items.indexOf(listItem)));
			counter++;
			checkBox.setChecked(true);
			updateCounter(counter);
		} else {
			selectionsItems.remove(items.get(items.indexOf(listItem)));
			counter--;
			checkBox.setChecked(false);
			updateCounter(counter);
		}
	}

	public void updateCounter(int counter) {
		if (counter > 1) {
			deleteTxt.setText(counter + "items selected");
		}
	}

	@Override
	public void onBackPressed() {
		if (isDeleting) {
			deleteOP();
			myAdapter.notifyDataSetChanged();
		} else {
			super.onBackPressed();
		}

	}

	public ListItem getRecycledList() {
		int rand = new Random().nextInt(items.size());
		ListItem itm = new ListItem("", "", "");
		return itm;
	}

	public class CustomLinearLayoutManager extends LinearLayoutManager {
		/**
		 * Creates a vertical LinearLayoutManager
		 * well error fixed we dont need it again
		 *
		 * @param context Current context, will be used to access resources.
		 */
		public CustomLinearLayoutManager(Context context) {
			super(context);
		}

		@Override
		public boolean supportsPredictiveItemAnimations() {
			return false;
		}
		//Generate constructors

		@Override
		public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

			try {

				super.onLayoutChildren(recycler, state);

			} catch (IndexOutOfBoundsException e) {
// to prevent the bug that makes it crash
				Log.e("TAG", "Inconsistency detected");
			}

		}
	}


}
