package com.bolo.entity;

public class Wifiinfo {
	private String bid;
	private String bssid;
	private String ssid;
	private double wlevel;
	public Wifiinfo(){}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	public double getWlevel() {
		return wlevel;
	}
	public void setWlevel(double wlevel) {
		this.wlevel = wlevel;
	}
	
}
