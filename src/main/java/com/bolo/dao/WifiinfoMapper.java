package com.bolo.dao;


import java.util.List;
import java.util.Map;

import com.bolo.entity.Wifiinfo;
import com.bolo.entity.Wifiinfoavg;

public interface WifiinfoMapper {
	public void insertOne(Wifiinfo wifiinfo);
	public List<Wifiinfo> gettAvgByBid(String bid);
	public void insertAvgOne(Wifiinfoavg wifiinfoavg);
	public List<Map> getWekaDataByCindex(String cindex);
	public List<Map> getBssidByCindex(String cindex);
	public List<Map> getWifiAvg(Map map);
	public List<Map> getMaxAndMinByCindex(String cindex);
	public List<Wifiinfoavg> gettAvgByCindex(String cindex);
	public void insertWifiinfoAvg(String cindex);
	public List<Map> getBssidCountByCindex(String cindex);
	public List<Wifiinfoavg> getWifiAvgWithValue(Map map);
}
