package com.bolo.entity;

public class Wifiinfoavg {
	private String bid;
	private String bssid;
	private String ssid;
	private double wlevel;
	private String x;
	private String y;
	private int cindex;
	public Wifiinfoavg(){}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBssid() {
		return bssid;
	}
	public void setSsid(String ssid) {
		this.ssid = bssid;
	}
	public String getSsid() {
		return ssid;
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
}
