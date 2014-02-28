package com.bolo.entity;

import java.util.List;

public class Location {
	private String bid;
	private String x;
	private String y;
	private int cindex;
	private int avglevel;
	private List<Wifiinfo> wifiinfo;
	public Location(){};
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getX() {
		return x;
	}
	public void setX(String x){
		this.x = x;
	}
	public int getCindex() {
		return cindex;
	}
	public void setCindex(int cindex) {
		this.cindex = cindex;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public List<Wifiinfo> getWifiinfos() {
		return wifiinfo;
	}
	public void setWifiinfos(List<Wifiinfo> wifiinfo) {
		this.wifiinfo = wifiinfo;
	}
	public int getAvglevel() {
		return avglevel;
	}
	public void setAvglevel(int avglevel) {
		this.avglevel = avglevel;
	}

}
