package com.oti.recyclerviewtutorial;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.animation.AnimationUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, ExpandableLayout.OnExpansionUpdateListener {
	public TextView textViewHead;
	public TextView textViewDesc;
	public Button btnToggleExpansion;
	public CheckBox checkBox;
	public ImageView imageView;
	public LinearLayout linearLayout;
	public ExpandableLayout expandableLayout;
	public onItemClickListener onItemClickListener;
	public ListItem listItem;
	public boolean isExpanded;
	boolean longClicked = false;

	public ViewHolder(@NonNull View itemView) {
		super(itemView);

		textViewHead = itemView.findViewById(R.id.textViewHead);
		textViewDesc = itemView.findViewById(R.id.textViewDesc);
		imageView = itemView.findViewById(R.id.imageView);
		btnToggleExpansion = itemView.findViewById(R.id.btnToggleExpansion);
		checkBox = itemView.findViewById(R.id.checkBox);
		expandableLayout = itemView.findViewById(R.id.expandable_layout);
		linearLayout = itemView.findViewById(R.id.linearLayout);


		isExpanded = expandableLayout.isExpanded();
		expandableLayout.setOnExpansionUpdateListener(this);
		// added a gat of the identity of the onclicklistener now getting it back
		onItemClickListener = (onItemClickListener) itemView.getTag();
		//setting the click listener to the view row
		itemView.setOnClickListener(this);
		itemView.setOnLongClickListener(this);
		btnToggleExpansion.setOnClickListener(this);
	}

	public ObjectAnimator changeRotation(Button button, float from, float to, Context context) {
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(button, "rotation", from, to);
		objectAnimator.setDuration(300);
		objectAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
		return objectAnimator;
	}

	@Override
	public void onClick(View view) {
		int position = getAdapterPosition();// use adapterposition not get position
		if (view.getId() != btnToggleExpansion.getId()) {
			if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {// there is no position
				onItemClickListener.onItemClick(position, view, listItem);//chained to the adapter class
			}
		} else {
			Drawable image;
			if (!isExpanded) {
				expandableLayout.setExpanded(true, true);
				//	image = view.getResources().getDrawable(R.drawable.ic_expanded_up);
				//	btnToggleExpansion.setBackground(image);
				changeRotation((Button) view, 0f, 180f, view.getContext()).start();
			} else {
				expandableLayout.setExpanded(false, true);
				//	image = view.getResources().getDrawable(R.drawable.ic_expanded_down);
				//	btnToggleExpansion.setBackground(image);
				changeRotation((Button) view, 180f, 0f, view.getContext()).start();

			}


		}
	}

	@Override
	public boolean onLongClick(View view) {
		//	if(checkBox.getVisibility() == View.GONE){
		onItemClickListener.onItemLongClicked();
		//	}
		return true;
	}

	/**
	 * Callback for expansion updates
	 *
	 * @param expansionFraction Value between 0 (collapsed) and 1 (expanded) representing the the expansion progress
	 * @param state             One of  repesenting the current expansion state
	 */
	@Override
	public void onExpansionUpdate(float expansionFraction, int state) {
		isExpanded = state != 0;
	}
}
