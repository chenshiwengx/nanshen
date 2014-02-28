package com.bolo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bolo.dao.LocationMapper;
import com.bolo.dao.WifiinfoMapper;
import com.bolo.entity.Location;
import com.bolo.entity.Wifiinfo;
import com.bolo.entity.Wifiinfoavg;
import com.bolo.util.TxtRwriteFile;
import com.google.gson.Gson;
/**
 * 位置信息与wifi信息处理服务
 * @author Administrator
 *
 */
@Component
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)//设置默认的事务管理策略，即没有标注@Transactional的方法的事务处理方式，意思为不要求方法必须在一个事务中运行
public class LocalAppService {
	@Autowired
	private LocationMapper locationMapper;
	@Autowired
	private WifiinfoMapper wifiinfoMapper;
	public LocationMapper getLocationMapper() {
		return locationMapper;
	}

	public void setLocationMapper(LocationMapper locationMapper) {
		this.locationMapper = locationMapper;
	}
	public WifiinfoMapper getWifiinfoMapper() {
		return wifiinfoMapper;
	}

	public void setWifiinfoMapper(WifiinfoMapper wifiinfoMapper) {
		this.wifiinfoMapper = wifiinfoMapper;
	}
	@Transactional(readOnly=false)
	public void saveOne(String strJon){
		Gson gson=new Gson(); 
		Location local=gson.fromJson(strJon, Location.class);
		System.out.println(strJon);
		locationMapper.insertOne(local);
		System.out.println(local.getBid());
		List<Wifiinfo> wifiinfos=local.getWifiinfos();
		System.out.println(wifiinfos.size());
		for(int i=0;i<wifiinfos.size();i++){
			Wifiinfo wfinfo=wifiinfos.get(i);
			wfinfo.setBid(local.getBid());
			wifiinfoMapper.insertOne(wfinfo);
		}
	}
	public List<Wifiinfo> gettAvgByBid(){
		return wifiinfoMapper.gettAvgByBid("");
	}
	public boolean getWekaData(String cindex){
		List<Map> list=wifiinfoMapper.getWekaDataByCindex(cindex);
		List<Map> list2=wifiinfoMapper.getBssidByCindex(cindex);
		TxtRwriteFile txtWriter= new TxtRwriteFile("F:/wekadata1.arff");
		List<String> bssidMetaList=new ArrayList<String>();
		try{
			txtWriter.writeTxtToend("@relation wifiinfoData");
			txtWriter.writeTxtToend("@attribute x numeric");
			txtWriter.writeTxtToend("@attribute y numeric");
			for (int i = 0; i < list2.size(); i++ ) {
		           Map map = (Map) list.get(i);
		           String bssid = (String) map.get("bssid");
		           String ssid = (String) map.get("ssid");
		           txtWriter.writeTxtToend("@attribute "+bssid+"("+ssid+")"+" numeric");
		           bssidMetaList.add(bssid);
		    }
			txtWriter.writeTxtToend("@data");
		    String x = "";
	        String y = "";
			if(list.size()>0){
				Map firstmap = (Map) list.get(0);
	            x = (String) firstmap.get("x");
	            y = (String) firstmap.get("y");
		    }
			String oneWeka="";
	        Map<String,String> bssidMap=new HashMap<String,String>();
			for (int i = 0; i < list.size(); i++ ) {
		           Map map = (Map) list.get(i);
		           String x1 = (String) map.get("x");
		           String y1 = (String) map.get("y");
		           if(!x.equals(x1)||!y.equals(y1)){
		        	   oneWeka=x+","+y+",";
		        	   for(int j=0;j<bssidMetaList.size();j++){
		        		  String bssidkey=bssidMetaList.get(j);
		        		 if( bssidMap.containsKey(bssidkey)){
		        			 oneWeka+=bssidMap.get(bssidkey)+",";
		        		 }else{
		        			 oneWeka+="0,";
		        		 }
		        	   }
		        	   oneWeka=oneWeka.substring(0,oneWeka.length()-1);
		        	   txtWriter.writeTxtToend(oneWeka);
		        	   bssidMap.clear();
		        	   oneWeka="";
		           }
	        	   x=x1;y=y1;
		           bssidMap.put(String.valueOf(map.get("bssid")), String.valueOf(map.get("wlevel")));   
		    }
			if(!bssidMap.isEmpty()){
	        	   oneWeka=x+","+y+",";
	        	   for(int j=0;j<bssidMetaList.size();j++){
	        		  String bssidkey=bssidMetaList.get(j);
	        		 if( bssidMap.containsKey(bssidkey)){
	        			 oneWeka+=bssidMap.get(bssidkey)+",";
	        		 }else{
	        			 oneWeka+="0,";
	        		 }
	        	   }
	        	   oneWeka=oneWeka.substring(0,oneWeka.length()-1);
	        	   txtWriter.writeTxtToend(oneWeka);
	        	   bssidMap.clear();
	        	   oneWeka="";
			}
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
}
