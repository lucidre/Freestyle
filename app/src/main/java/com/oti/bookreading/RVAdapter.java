package com.oti.bookreading;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RowHolder> {

	private final ArrayList<String> words;
	private final LayoutInflater inflater;

	public RVAdapter(ArrayList<String> words, LayoutInflater inflater) {
		this.words = words;
		this.inflater = inflater;
	}

	@NonNull
	@Override
	public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View row = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		return new RowHolder(row);
	}

	@Override
	public void onBindViewHolder(@NonNull RowHolder holder, int position) {
		holder.bind(words.get(position));
	}

	@Override
	public int getItemCount() {
		return words.size();
	}

	public void add(String word) {
		words.add(word);
		notifyItemInserted(words.size() - 1);// telling the word to be updated
	}
}
