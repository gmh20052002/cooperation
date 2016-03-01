package com.wlp.web;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wlp.api.entity.WlpUser;
import com.wlp.api.service.WlpUserService;

@Controller()
public class UserController {

	@Autowired
	WlpUserService wlpUserService;


	private static final String USER_NAME = "USER_NAME";

	public WlpUserService getWlpUserService() {
		return wlpUserService;
	}

	public void setWlpUserService(WlpUserService wlpUserService) {
		this.wlpUserService = wlpUserService;
	}

	@RequestMapping(value = "/wlp/login", method = RequestMethod.GET)
	public @ResponseBody WlpUser login(@RequestParam(required = true) String userName,
			@RequestParam(required = true) String password, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			session.setAttribute(USER_NAME, userName);
			WlpUser logined_user = wlpUserService.commonLogin(userName, password);
			session.setAttribute("logined_user", logined_user);
			return logined_user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/wlp/addUser", method = RequestMethod.POST)
	public @ResponseBody WlpUser addUser(WlpUser user) {
		try {
			return wlpUserService.regUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/wlp/regUser", method = RequestMethod.POST)
	public @ResponseBody Boolean regUser(@RequestParam(required = true) String username,
			@RequestParam(required = true) String telphone, @RequestParam(required = true) String email,
			@RequestParam(required = true) String password, @RequestParam(required = true) String paypassword,
			@RequestParam(required = true) String introemail) {
		Boolean flag = false;
		try {
			WlpUser user = new WlpUser();
			user.setUserName(username);
			user.setMobilePhone(telphone);
			user.setEmail(email);
			user.setLoginPassword(password);
			user.setTransPassword(paypassword);
			user.setRecEmail(introemail);
			  int max=40;
              int min=1;
              Random random = new Random();
              int s = random.nextInt(max)%(max-min+1) + min;
              user.setRemark(String.valueOf(s));
			if (wlpUserService.regUser(user) != null) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@RequestMapping(value = "/wlp/updateUserInfo", method = RequestMethod.POST)
	public @ResponseBody Boolean updateUserInfo(@RequestParam(required = true) String username,
			@RequestParam(required = true) String telphone, @RequestParam(required = true) String wechat,
			@RequestParam(required = true) String alipay, @RequestParam(required = true) String bankname,
			@RequestParam(required = true) String bankusername, @RequestParam(required = true) String bankacct,HttpServletRequest request) {
		Boolean flag = false;
		HttpSession session = request.getSession();
		String cname = (String) session.getAttribute(USER_NAME);
		if (cname == null) {
			return false;
		}
		try {
			WlpUser user = wlpUserService.getUserByEmail(cname);
			if (user != null) {
				user.setUserName(username);
				user.setMobilePhone(telphone);
				user.setWechat(wechat);
				user.setBankName(bankname);
				user.setBankUsername(bankusername);
				user.setBankAcct(bankacct);
				user.setAlipay(alipay);
				wlpUserService.updateUser(user);
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@RequestMapping(value = "/wlp/updateUserLoginPsd", method = RequestMethod.POST)
	public @ResponseBody Boolean updateUserLoginPsd(@RequestParam(required = true) String oldpsd,
			@RequestParam(required = true) String newpsd,HttpServletRequest request) {
		Boolean flag = false;
		HttpSession session = request.getSession();
		String cname = (String) session.getAttribute(USER_NAME);
		if (cname == null) {
			return false;
		}
		try {
			WlpUser user = wlpUserService.getUserByEmail(cname);
			if (user != null && oldpsd != null && oldpsd.equals(user.getLoginPassword())) {
				user.setLoginPassword(newpsd);
				wlpUserService.updateUser(user);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@RequestMapping(value = "/wlp/updateUserImage", method = RequestMethod.POST)
	public @ResponseBody Boolean updateUserImage(@RequestParam(required = true) String imageId,HttpServletRequest request) {
		Boolean flag = false;
		HttpSession session = request.getSession();
		String cname = (String) session.getAttribute(USER_NAME);
		if (cname == null) {
			return false;
		}
		try {
			WlpUser user = wlpUserService.getUserByEmail(cname);
			if (user != null) {
				user.setRemark(imageId);
				wlpUserService.updateUser(user);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@RequestMapping(value = "/wlp/updateUserPayPsd", method = RequestMethod.POST)
	public @ResponseBody Boolean updateUserPayPsd(@RequestParam(required = true) String oldpsd,
			@RequestParam(required = true) String newpsd,HttpServletRequest request) {
		Boolean flag = false;
		HttpSession session = request.getSession();
		String cname = (String) session.getAttribute(USER_NAME);
		if (cname == null) {
			return false;
		}
		try {
			WlpUser user = wlpUserService.getUserByEmail(cname);
			if (user != null && oldpsd != null && oldpsd.equals(user.getTransPassword())) {
				user.setTransPassword(newpsd);
				wlpUserService.updateUser(user);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
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

	@RequestMapping(value = "/wlp/getUserInfo", method = RequestMethod.GET)
	public @ResponseBody WlpUser getUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		 ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext()) ;
		 WlpUserService wlpUserService=(WlpUserService) ac1.getBean("wlpUserService"); 
		WlpUser user = wlpUserService.getUserByEmail(username);
		if (user != null) {
			WlpUser user2= wlpUserService.getUserByEmail(user.getRecEmail());
			if(user2!=null&&user2.getUserName()!=null){
				user.setRecEmail(user2.getUserName());
			}
			return user;
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

	@RequestMapping(value = "/wlp/loginout", method = RequestMethod.GET)
	public @ResponseBody void loginout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(USER_NAME);
		session.invalidate();
	}

	@RequestMapping(value = "/wlp/transLogin", method = RequestMethod.POST)
	public @ResponseBody Boolean transLogin(@RequestParam(required = true) String transPassword,
			HttpServletRequest request, HttpServletResponse reponse) {
		try {
			if (transPassword == null) {
				return false;
			}
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute(USER_NAME);
			session.setAttribute("order_logined", "true");
			if (userName == null) {
				return false;
			}
			if (wlpUserService.transLogin(userName, transPassword) != null) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取用户的团队
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/wlp/getMyTeamUsers", method = RequestMethod.GET)
	public @ResponseBody ArrayList<WlpUser> getMyTeamUsers(HttpServletRequest request) {

		 ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext()) ;
		 WlpUserService wlpUserService=(WlpUserService) ac1.getBean("wlpUserService"); 
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute(USER_NAME);
		if (userName == null) {
			return null;
		}
		return (ArrayList<WlpUser>) wlpUserService.getMyTeamUsers(userName);
	}

	/**
	 * 查询用户的团队
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/wlp/getMyTeamUsersBySearch", method = RequestMethod.POST)
	public @ResponseBody ArrayList<WlpUser> getMyTeamUsersBySearch(@RequestParam(required = true) String keyword,HttpServletRequest request) {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute(USER_NAME);
		if (userName == null) {
			return null;
		}
		 ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext()) ;
		 WlpUserService wlpUserService=(WlpUserService) ac1.getBean("wlpUserService"); 
		ArrayList<WlpUser> results = new ArrayList<WlpUser>();
		ArrayList<WlpUser> codes = (ArrayList<WlpUser>) wlpUserService.getMyTeamUsers(userName);

		if (codes != null && codes.size() > 0) {
			for (int i = 0; i < codes.size(); i++) {
				String no = String.valueOf((i + 1));
				WlpUser code = codes.get(i);
				String email = code.getEmail();
				String username = code.getUserName();
				if (keyword == null || keyword.isEmpty()) {
					results.add(code);
					continue;
				}
				if (email.contains(keyword) || username.contains(keyword)) {

					if (email.contains(keyword)) {
						email = email.replaceAll(keyword, "<span style='color:red'>" + keyword + "</span>");
						code.setEmail(email);
					}
					if (username.contains(keyword)) {
						code.setUserName(
								username.replaceAll(keyword, "<span style='color:red'>" + keyword + "</span>"));
					}

					results.add(code);
				}
			}

		}
		return results;
	}
}
