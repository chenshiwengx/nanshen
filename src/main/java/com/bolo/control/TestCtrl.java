package com.bolo.control;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
<<<<<<< HEAD
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolo.entity.NanshenUser;
import com.bolo.entity.User1;
import com.bolo.service.TestShiwu;
import com.bolo.service.UserMngSv;
=======
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolo.service.TestShiwu;
>>>>>>> 4af4f083d4032782e3d973a406a38556f84b7d9c
import com.bolo.util.TxtRwriteFile;
@Controller
@RequestMapping(value = "/test")
public class TestCtrl {
<<<<<<< HEAD

	@Autowired
	UserMngSv userMng;	
	@RequestMapping(value = "/nanshen")
	@ResponseBody
	public NanshenUser testNanshen(@RequestParam("id")int id){
		try{	
			NanshenUser user=userMng.queryUserById(id);
			return user;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value = "newuser", method = RequestMethod.GET)
    public String getData2(){
		System.out.print("1111");
		return "login";
    }
	@RequestMapping("/index")
    public String getData3(){
		return "index";
    }
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return "index";
		//return "baidumap/hw";
=======
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
>>>>>>> 4af4f083d4032782e3d973a406a38556f84b7d9c
	}
}
