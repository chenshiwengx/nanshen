package com.bolo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.bolo.dao.WifiinfoMapper;
import com.bolo.entity.Wifiinfoavg;

@Service
@Transactional(readOnly=true)
public class TestShiwu {
	@Autowired
	private WifiinfoMapper wifiinfoMapper;
	public void setWifiinfoMapper(WifiinfoMapper wifiinfoMapper){
		this.wifiinfoMapper=wifiinfoMapper;
	}
	@Transactional(readOnly=false)
	public void save(){
		wifiinfoMapper.insertWifiinfoAvg("1");
	}
}
