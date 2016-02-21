package com.wlp.web;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wlp.api.entity.CommonCst;
import com.wlp.api.entity.WlpActivecode;
import com.wlp.api.entity.WlpUser;
import com.wlp.api.service.WlpActivecodeService;
import com.wlp.api.service.WlpUserService;

/**
 * 激活码管理 规则说明： 激活交易激活码30元
 * 
 * @author 明华
 *
 */
@Controller()
public class ActiveCodeController {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	WlpActivecodeService wlpActivecodeService;

	@Autowired
	WlpUserService wlpUserService;
	
	private static final String USER_NAME = "USER_NAME";

	/**
	 * 可用激活码
	 * @return
	 */
	@RequestMapping(value = "/wlp/getCanUseActivecodes", method = RequestMethod.GET)
	public @ResponseBody ArrayList<WlpActivecode> getCanUseActivecodes() {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpActivecode> codes = (ArrayList<WlpActivecode>) wlpActivecodeService.getCanUseActivecodes(username);
		return codes;
	}
	/**
	 * 已分享的激活码
	 * @return
	 */
	@RequestMapping(value = "/wlp/getUseActivecodes", method = RequestMethod.GET)
	public @ResponseBody ArrayList<WlpActivecode> getUseActivecodes() {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpActivecode> codes = (ArrayList<WlpActivecode>) wlpActivecodeService.getSharedActivecodes(username);
		return codes;
	}
	
	/**
	 * 已使用激活码
	 * @return
	 */
	@RequestMapping(value = "/wlp/getMyUsedActivecodes", method = RequestMethod.GET)
	public @ResponseBody WlpActivecode getMyUsedActivecodes() {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpActivecode> codes = (ArrayList<WlpActivecode>) wlpActivecodeService.getUsedActivecodes(username);
		if (codes== null || codes.size() < 1) {
			return null;		
		}
			for (WlpActivecode coder : codes) {
				if(coder.getStatus().equals(CommonCst.USED)){
					return coder;
				}
				
			}
		return null;
	}
	
	/**
	 * 激活码使用记录
	 * @return
	 */
	@RequestMapping(value = "/wlp/getUseActivecodesHistory", method = RequestMethod.GET)
	public @ResponseBody ArrayList<WlpActivecode> getUseActivecodesHistory() {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpActivecode> codes = (ArrayList<WlpActivecode>) wlpActivecodeService.getSharedActivecodes(username);
		if (codes != null && codes.size() > 0) {
			for (WlpActivecode code : codes) {
			    String fromusername=code.getEmail();
				 	WlpUser user = wlpUserService.getUserByEmail(fromusername);
						if (user!= null) {
							fromusername=user.getUserName();
						}
						  String tousername=code.getShareEmail();
						 	WlpUser touser = wlpUserService.getUserByEmail(tousername);
								if (touser != null) {
									tousername=touser.getUserName();
								}
								code.setEmail(fromusername);
								code.setShareEmail(tousername);
				}
			}
		return codes;
	}
	
	/**
	 * 可用激活码查询
	 * @return
	 */
	@RequestMapping(value = "/wlp/getCanUseActivecodesBySearch", method = RequestMethod.POST)
	public @ResponseBody ArrayList<WlpActivecode> getCanUseActivecodesBySearch(
			@RequestParam(required = true) String keyword) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpActivecode> results = new ArrayList<WlpActivecode>();
		ArrayList<WlpActivecode> codes = (ArrayList<WlpActivecode>) wlpActivecodeService.getCanUseActivecodes(username);
		if (keyword == null || keyword.isEmpty()) {
			return codes;
		}

		if (codes != null && codes.size() > 0) {
			for (WlpActivecode code : codes) {
				String state = "未激活";
				if ("1".equals(code.getStatus())) {
					state = "激活";
				}
				String email = code.getEmail();
				if (email.contains(keyword) || state.contains(keyword) || code.getCode().contains(keyword)) {

					if (email.contains(keyword)) {
						email = email.replaceAll(keyword, " <span style='color:red'>" + keyword + "</span>");
						code.setEmail(email);
					}
					if (state.contains(keyword)) {
						code.setStatus(state.replaceAll(keyword, " <span style='color:red'>" + keyword + "</span>"));
					}
					if (code.getCode().contains(keyword)) {
						code.setCode(
								code.getCode().replaceAll(keyword, " <span style='color:red'>" + keyword + "</span>"));
					}

					results.add(code);
				}
			}

		}
		return results;
	}
	/**
	 * 已使用激活码查询
	 * @return
	 */
	@RequestMapping(value = "/wlp/getUseActivecodesBySearch", method = RequestMethod.POST)
	public @ResponseBody ArrayList<WlpActivecode> getUseActivecodesBySearch(
			@RequestParam(required = true) String keyword) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpActivecode> results = new ArrayList<WlpActivecode>();
		ArrayList<WlpActivecode> codes = (ArrayList<WlpActivecode>) wlpActivecodeService.getSharedActivecodes(username);
		if (keyword == null || keyword.isEmpty()) {
			return codes;
		}

		if (codes != null && codes.size() > 0) {
			for (WlpActivecode code : codes) {
				String state = "未激活";
				if ("1".equals(code.getStatus())) {
					state = "激活";
				}
				String email = code.getEmail();
				if (email.contains(keyword) || state.contains(keyword) || code.getCode().contains(keyword)) {

					if (email.contains(keyword)) {
						email = email.replaceAll(keyword, " <span style='color:red'>" + keyword + "</span>");
						code.setEmail(email);
					}
					if (state.contains(keyword)) {
						code.setStatus(state.replaceAll(keyword, " <span style='color:red'>" + keyword + "</span>"));
					}
					if (code.getCode().contains(keyword)) {
						code.setCode(
								code.getCode().replaceAll(keyword, " <span style='color:red'>" + keyword + "</span>"));
					}

					results.add(code);
				}
			}

		}
		return results;
	}
	/**
	 * 共享激活码
	 * @return
	 */
	@RequestMapping(value = "/wlp/shareActivecodes", method = RequestMethod.POST)
	public @ResponseBody Boolean shareActivecodes(@RequestParam(required = true) String othername) {
		HttpSession session = request.getSession();
		Boolean flag = false;
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null || othername == null) {
			return false;
		}
		
		try {
			WlpUser user = wlpUserService.getUserByEmail(othername);
			if (user == null) {
				return false;
			}
			WlpActivecode code = wlpActivecodeService.shareActivecode(username, othername);
			if (code != null) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	/**
	 * 激活用户
	 * @return
	 */
	@RequestMapping(value = "/wlp/activeUser", method = RequestMethod.POST)
	public @ResponseBody Boolean activeUser(@RequestParam(required = true) String code) {
		HttpSession session = request.getSession();
		Boolean flag = false;
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null || code == null) {
			return false;
		}	
		try {
			WlpUser user = wlpUserService.getUserByEmail(username);
			if (user == null) {
				return false;
			}
			ArrayList<WlpActivecode> codes = (ArrayList<WlpActivecode>) wlpActivecodeService.getCanUseActivecodes(username);
			if (codes== null || codes.size() < 1) {
				return false;			
			}
				for (WlpActivecode coder : codes) {
					if(coder.getCode().equals(code)){
						user.setStatus("1");
						wlpUserService.updateUser(user);
						wlpActivecodeService.activeUser(username, code);
						 flag = true;
						break;					
					}
					
				}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
}
