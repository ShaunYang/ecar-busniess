package com.pyec.ecarbusiness.dataStruct;

import java.util.ArrayList;

public class AuctionListData {

	public String type;
	public String auction_id;
	public String status;
	public String title;
	public String starttime;
	public String now;
	public String endtime;
	
	public String sell_id;
	public String info;
	public String img01;
	public String area;
	public String score;
	public String regist_date;
	public String kilometer;
	
	public ArrayList<CarData> sell = new  ArrayList<CarData>();
	
	public String getAuction_id() {
		return auction_id;
	}
	public void setAuction_id(String auction_id) {
		this.auction_id = auction_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getNow() {
		return now;
	}
	public void setNow(String now) {
		this.now = now;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
	
}
