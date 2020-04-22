package com.oti.recyclerviewtutorial;

import android.view.View;

interface onItemClickListener {

	void onItemClick(int position, View view, ListItem listItem);

	void onItemLongClicked();
}
