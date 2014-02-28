package com.bolo.entity;

import java.util.List;

/**
 * 待测位置的wifi集合
 * @author Administrator
 *
 */
public class Mylocaltion {
	private List<List<Wifiinfo>> wifiinfos;
	public List<List<Wifiinfo>> getWifiinfos(){
		return this.wifiinfos;
	}
	public void setWifiinfos(List<List<Wifiinfo>> wifiinfos){
		 this.wifiinfos=wifiinfos;
	}
}
