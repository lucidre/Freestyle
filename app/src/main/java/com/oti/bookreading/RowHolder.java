package com.oti.bookreading;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class RowHolder extends RecyclerView.ViewHolder {
	TextView title;

	public RowHolder(@NonNull View itemView) {
		super(itemView);
		title = itemView.findViewById(android.R.id.text1);
	}

	public void bind(String text) {
		title.setText(text);
	}
}

