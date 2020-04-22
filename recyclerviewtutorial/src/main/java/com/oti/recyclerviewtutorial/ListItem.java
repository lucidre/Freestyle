package com.oti.recyclerviewtutorial;

public class ListItem {


	private String head;
	private String desc;
	private String imageUri;

	public ListItem(String head, String desc, String imageUri) {
		this.head = head;
		this.desc = desc;
		this.imageUri = imageUri;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
