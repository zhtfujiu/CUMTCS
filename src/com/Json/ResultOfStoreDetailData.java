package com.Json;

import java.util.List;

public class ResultOfStoreDetailData {

	private int Key;
	private List<DetailStoreItem> details;
	public int getKey() {
		return Key;
	}
	public void setKey(int key) {
		Key = key;
	}
	public List<DetailStoreItem> getDetails() {
		return details;
	}
	public void setDetails(List<DetailStoreItem> details) {
		this.details = details;
	}
}
