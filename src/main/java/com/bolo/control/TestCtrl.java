package com.bolo.control;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.bolo.util.TxtRwriteFile;
@Controller
@RequestMapping(value = "/test")
public class TestCtrl {

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
	}
}
