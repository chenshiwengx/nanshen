package com.bolo.control;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolo.entity.User1;
import com.bolo.entity.Wifiinfo;
import com.bolo.service.KnnService;
import com.bolo.service.LocalAppService;

@Controller
@RequestMapping(value = "/wifiinfo")
public class WifiInfoCtrl {
	@Autowired
	LocalAppService localAppService;
	@Autowired
	KnnService knnService;
	@RequestMapping("/save")
	@ResponseBody
	public String getData2(@RequestParam("datas")String datas) throws UnsupportedEncodingException {
		try{
			String strJon=datas;
			localAppService.saveOne(strJon);
			return "ok";
		}catch(Exception ex){
			ex.printStackTrace();
			return "ERROR";
		}
		//return datas;
	}
	@RequestMapping("/save1")
	@ResponseBody
	public String getData3(@RequestParam("datas")String datas)throws UnsupportedEncodingException {
		System.out.println(URLDecoder.decode(datas, "UTF-8"));
		try{
			localAppService.saveOne(URLDecoder.decode(datas, "UTF-8"));
			return "ok";
		}catch(Exception ex){
			ex.printStackTrace();
			return "ERROR";
		}
		//return datas;
	}
	@RequestMapping("/getWekaData")
	@ResponseBody
	public String getWekaData(@RequestParam("cindex")String cindex) {
		if(localAppService.getWekaData(cindex)){
			return "生成weka文件成功！";
		}
		return "生成weka文件失败！";
	}
	@RequestMapping("/getMylocaltion1")
	@ResponseBody
	public String getMylocaltion1(@RequestParam("cindex")String cindex,@RequestParam("datas")String datas) {
		String strJon=datas;
		String result= knnService.getMylocaltion(strJon);
		return result;
	}
	@RequestMapping("/getMylocaltion2")
	@ResponseBody
	public String getMylocaltion2(@RequestParam("cindex")String cindex,@RequestParam("datas")String datas) {
		String strJon=datas;
		String result= knnService.getMylocaltionWithValue(strJon);
		return result;
	}
}
