package com.bolo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolo.entity.User1;
import com.bolo.service.UserServiceImp;

@Controller
@RequestMapping(value = "/user")
public class UserCtrl {
	@Autowired
	    UserServiceImp userService;
	public void setUserService(UserServiceImp userService) {
		this.userService = userService;
	}
    @RequestMapping("/data/{id}")
    public String getData(@PathVariable("id")String id,Model model) {
      User1 user = userService.queryStudentById(id);
      model.addAttribute("user", user);
      return "user";
    }
    @RequestMapping("/data1/{id}")
    public String getData1(@PathVariable("id")String id,Model model) {
      User1 user = userService.queryStudentById(id);
      model.addAttribute("user", user);
      return "user";
    }
}
