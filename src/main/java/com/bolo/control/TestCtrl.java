package com.bolo.control;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolo.service.TestShiwu;
import com.bolo.util.TxtRwriteFile;
@Controller
@RequestMapping(value = "/test")
public class TestCtrl {
	@Autowired
	TestShiwu testShiwu;
	@RequestMapping(value = "/testshiwu1")
	@ResponseBody
	public String testshiwu(){
		try{
			testShiwu.save();
			return "ok";
		}catch(Exception ex){
			ex.printStackTrace();
			return "error";
		}
	}
	@RequestMapping(value ="/localresultTotxt")
	public void getMylocaltion2(@RequestParam("x")String x,@RequestParam("y")String y,@RequestParam("localindex")String cindex,@RequestParam("datas")String datas) throws IOException {
		TxtRwriteFile txtWriter= new TxtRwriteFile("F:/localresult.txt");
		txtWriter.writeTxtToend(x+","+y+" "+"定位方法："+cindex);
		txtWriter.writeTxtToend(datas);
	}
}
