package com.wlp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wlp.api.entity.WlpUser;
import com.wlp.api.service.WlpUserService;

@Controller()
public class UserController {

	@Autowired
	WlpUserService wlpUserService;

	public WlpUserService getWlpUserService() {
		return wlpUserService;
	}

	public void setWlpUserService(WlpUserService wlpUserService) {
		this.wlpUserService = wlpUserService;
	}

	@RequestMapping(value = "/wlp/login", method = RequestMethod.GET)
	public @ResponseBody WlpUser login(@RequestParam(required = true)  String userName,
			@RequestParam(required = true) String password) {
		try {
			return wlpUserService.commonLogin(userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/wlp/regUser", method = RequestMethod.POST)
	public @ResponseBody WlpUser addUser(WlpUser user) {
		try {
			return wlpUserService.regUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/wlp/updateUser", method = RequestMethod.PUT)
	public @ResponseBody WlpUser updateUser(WlpUser user) {
		try {
			return wlpUserService.regUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/wlp/deleteUser", method = RequestMethod.DELETE)
	public @ResponseBody WlpUser deleteUser(String email) {
		try {
			return wlpUserService.deleteUser(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/wlp/transLogin", method = RequestMethod.GET)
	public @ResponseBody WlpUser transLogin(@RequestParam(required = true)  String userName,
			@RequestParam(required = true) String transPassword) {
		try {
			return wlpUserService.transLogin(userName, transPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
