package com.oti.recyclerviewtutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

	public static onItemClickListener onItemClickListener;
	ListItem listItem;
	MainActivity mainActivity;
	private List<ListItem> listItems;
	private List<ListItem> listItemsFull;
	private Context context;

	public MyAdapter(List<ListItem> listItems, Context context) {
		this.listItems = listItems;
		this.context = context;
		this.mainActivity = (MainActivity) context;
		this.listItemsFull = new ArrayList<>(listItems);/// so that if we change it it wont affect the former
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = layoutInflater.inflate(R.layout.list_item, parent, false);
		view.setTag(onItemClickListener);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		listItem = listItems.get(position);
		holder.textViewHead.setText(listItem.getHead());
		holder.textViewDesc.setText(listItem.getDesc());
		holder.listItem = listItem;

		Picasso picasso = Picasso.get();
		picasso.load(listItem.getImageUri()).fit().centerInside().into(holder.imageView);

		if (mainActivity.isDeleting) {
			holder.checkBox.setVisibility(View.VISIBLE);
			holder.checkBox.setChecked(false);
		} else {
			holder.checkBox.setVisibility(View.GONE);
			holder.checkBox.setChecked(false);
		}
	}

	@Override
	public int getItemCount() {
		return listItems.size();
	}

	public void onUpgrade(ListItem value) {
		listItems.add(value);
		listItemsFull.add(value);
		notifyItemInserted(listItems.size() - 1);
	}

	public void updateAdapterAfterDelete(List<ListItem> deleteList) {
		for (ListItem listItem : deleteList) {
			int pos = listItems.indexOf(listItem);
			listItems.remove(listItem);
			listItemsFull.remove(listItem);

		}
		notifyDataSetChanged();


	}

	@Override
	public Filter getFilter() {
		Filter rvFilter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				// automatically executed on the background thread
				List<ListItem> filteredList = new ArrayList<>();
				if (charSequence == null || charSequence.length() == 0) {
					filteredList.addAll(listItemsFull);
				} else {
					String filteredPattern = charSequence.toString().toLowerCase().trim();// trim removes empty spaces
					for (ListItem listItem : listItemsFull) {
						if (listItem.getHead().toLowerCase().contains(filteredPattern) ||
								listItem.getDesc().toLowerCase().contains(filteredPattern)) {
							filteredList.add(listItem);
						}
					}
				}
				FilterResults results = new FilterResults();
				results.values = filteredList;
				return results; // this returns result to publish result under

			}

			@Override
			protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
				List<ListItem> filteredList = (List) filterResults.values;
				listItems.clear();
				listItems.addAll(filteredList);

				notifyDataSetChanged();


			}

		};
		return rvFilter;
	}

	public void setOnItemClickListener(onItemClickListener onItemClickListener) {
		MyAdapter.onItemClickListener = onItemClickListener;
	}
}
