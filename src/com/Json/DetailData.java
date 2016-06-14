package com.Json;

import android.widget.ImageView;

public class DetailData {

	private String userID,userNicheng,time,storeID,locationID,comment;
	private String ScoreOfShangpin,ScoreOfFuwu;
	private ImageView picture;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStoreID() {
		return storeID;
	}
	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}
	public String getLocationID() {
		return locationID;
	}
	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public ImageView getPicture() {
		return picture;
	}
	public void setPicture(ImageView picture) {
		this.picture = picture;
	}
	public String getScoreOfShangpin() {
		return ScoreOfShangpin;
	}
	public void setScoreOfShangpin(String shangpingZhiliang) {
		ScoreOfShangpin = shangpingZhiliang;
	}
	public String getScoreOfFuwu() {
		return ScoreOfFuwu;
	}
	public void setScoreOfFuwu(String fuwuZhiliang) {
		ScoreOfFuwu = fuwuZhiliang;
	}
	public String getUserNicheng() {
		return userNicheng;
	}
	public void setUserNicheng(String userNicheng) {
		this.userNicheng = userNicheng;
	}
	
}
