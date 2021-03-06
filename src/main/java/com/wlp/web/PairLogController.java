package com.wlp.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import com.wlp.api.entity.WlpPairLog;
import com.wlp.api.entity.WlpUser;
import com.wlp.api.service.WlpPairLogService;
import com.wlp.api.service.WlpUserService;

/**
 * 交易记录管理 规则： 投资1000-5000，排队后日利息为： 第一天：3% 第二天：5% 。。。 第15天：17% 总盈利不得大于145%，否则强制出局
 * 诚信金一轮出来反到账户上，半路出来不返回 匹配大款12小时
 * 
 * @author 明华
 *
 */
@Controller()
public class PairLogController {

	

	private static final String USER_NAME = "USER_NAME";

	/**
	 * 我的历程--查询我的所有交易记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/getMyWlpPairLogs", method = RequestMethod.GET)
	public @ResponseBody List<WlpPairLog> getMyWlpPairLogs(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpPairLogService wlpPairLogService= (WlpPairLogService) ac1.getBean("wlpPairLogService");
		List<WlpPairLog> logs = wlpPairLogService.getWlpPairLogs(username, null);
		if (logs != null && logs.size() > 0) {
			for (WlpPairLog log : logs) {
				log.setEmail(username);
				String state = "未完成";
				if ("1".equals(log.getStatus())) {
					state = "已确认";
				}
				log.setStatus(state);
				if ((log.getFromUser() != null && log.getFromUser().equals(username))) {
					long money = log.getPairMoney();
					money = 0 - money;
					log.setPairMoney(money);
				}
			}
		}
		return logs;
	}

	/**
	 * 根据ID和用户名查询我的指定交易记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/getMyWlpPairLogById", method = RequestMethod.POST)
	public @ResponseBody WlpPairLog getMyWlpPairLogs(HttpServletRequest request,
			@RequestParam(required = true) String pairLogId) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null || pairLogId == null || pairLogId.isEmpty()) {
			return null;
		}
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpPairLogService wlpPairLogService= (WlpPairLogService) ac1.getBean("wlpPairLogService");
		List<WlpPairLog> logs = wlpPairLogService.getWlpPairLogs(username, null);
		if (logs != null && logs.size() > 0) {
			for (WlpPairLog log : logs) {
				if (pairLogId.equals(log.getId()) && log.getToUser().equals(username)) {
					return log;
				}
			}
		}
		return null;
	}

	/**
	 * 我的未完成交易--查询我的所有交易记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/getMyUnFinishWlpPairLogs", method = RequestMethod.GET)
	public @ResponseBody List<WlpPairLog> getMyUnFinishWlpPairLogs(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpPairLogService wlpPairLogService= (WlpPairLogService) ac1.getBean("wlpPairLogService");
		ArrayList<WlpPairLog> results = new ArrayList<WlpPairLog>();
		List<WlpPairLog> logs = wlpPairLogService.getWlpPairLogs(username, null);
		WlpUserService wlpUserService = (WlpUserService) ac1.getBean("wlpUserService");
		if (logs != null && logs.size() > 0) {
			for (WlpPairLog log : logs) {
				log.setEmail(username);
				String recEmail = log.getToUser();
				if ("1".equals(log.getStatus()) || username.equals(log.getToUser()) || recEmail == null) {
					continue;
				}
				String pic=log.getOrderPic();
		       if(pic!=null&&pic.contains(".jpg")){
					continue;
				}
		       if(pic!=null&&pic.contains(".jpeg")){
					continue;
				}
		       if(pic!=null&&pic.contains(".png")){
					continue;
				}
		       if(pic!=null&&pic.contains(".gif")){
					continue;
				}
		       if(pic!=null&&pic.contains(".bmp")){
					continue;
				}
		       if(pic!=null&&pic.contains(".psd")){
					continue;
				}
				WlpUser rec_user = null;
				try {
					rec_user = wlpUserService.getUserByEmail(recEmail);
					recEmail = rec_user.getUserName();
					log.setOrderPic(rec_user.getRemark());
				} catch (Exception e) {
					log.setOrderPic("404");
					e.printStackTrace();
				}
				log.setStatus(recEmail);
				if ((log.getFromUser() != null && log.getFromUser().equals(username))) {
					long money = log.getPairMoney();
					money = 0 - money;
					log.setPairMoney(money);
				}
				results.add(log);
			}
		}
		return results;
	}

	/**
	 * 我的待确认交易
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/getMySureWlpPairLogs", method = RequestMethod.GET)
	public @ResponseBody List<WlpPairLog> getMySureWlpPairLogs(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpPairLog> results = new ArrayList<WlpPairLog>();
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpPairLogService wlpPairLogService= (WlpPairLogService) ac1.getBean("wlpPairLogService");
		List<WlpPairLog> logs = wlpPairLogService.getWlpPairLogs(username, null);
		WlpUserService wlpUserService = (WlpUserService) ac1.getBean("wlpUserService");
		if (logs != null && logs.size() > 0) {
			for (WlpPairLog log : logs) {
				log.setEmail(username);
				String recEmail = log.getFromUser();
				if ("1".equals(log.getStatus()) || username.equals(log.getFromUser()) || recEmail == null
						|| recEmail.isEmpty()) {
					continue;
				}
			
				WlpUser rec_user = null;
				try {
					rec_user = wlpUserService.getUserByEmail(recEmail);
					recEmail = rec_user.getUserName();
					log.setToUser(rec_user.getRemark());
				} catch (Exception e) {
					log.setToUser("404");
					e.printStackTrace();
				}
				log.setStatus(recEmail);			 
				results.add(log);
			}
		}
		return results;
	}

	/**
	 * 请求帮助
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/getHelp", method = RequestMethod.POST)
	public @ResponseBody WlpPairLog getHelp(HttpServletRequest request,
			@RequestParam(required = true) String money, @RequestParam(required = true) String payway,@RequestParam(required = true) String type) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		long ttype=0l;
		if("bonus".equals(type)){			
			 ttype=1l;
		}
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpPairLogService wlpPairLogService= (WlpPairLogService) ac1.getBean("wlpPairLogService");
		WlpPairLog wlpPairLog = new WlpPairLog();
		wlpPairLog.setEmail(username);
		wlpPairLog.setToUser(username);
		wlpPairLog.setPayType(payway);
		wlpPairLog.setPairMoney(Long.parseLong(money));
		wlpPairLog.setType(ttype);
		wlpPairLog.setStatus("0");
		wlpPairLog.setOrderTime(new Date());
		wlpPairLog.setExtrakType("help");
		return wlpPairLogService.addWlpPairLog(wlpPairLog);
	}

	/**
	 * 修改后完成交易
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/phonegapUp", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public @ResponseBody void phonegapUp(@RequestParam("uploadFile") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// request.setCharacterEncoding("utf-8");
		String pairid = request.getParameter("pairid");
		if (pairid == null || pairid.isEmpty()) {
		}
		String desc = request.getParameter("textarea");
		String filename = null;
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpPairLogService wlpPairLogService= (WlpPairLogService) ac1.getBean("wlpPairLogService");
		if (!file.isEmpty()) {
			ServletContext sc = request.getSession().getServletContext();
			String dir = sc.getRealPath("/upload"); // 设定文件保存的目录
			filename = file.getOriginalFilename(); // 得到上传时的文件名
			filename=getRandomString(8)+filename;
			FileUtils.writeByteArrayToFile(new File(dir, filename), file.getBytes());

		}
		response.sendRedirect("/cloud/unFinishPairLog.html");
		wlpPairLogService.completeWlpPairLog(pairid, filename, desc);
	}

	private static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }  
	/**
	 * 完成交易
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/sureWlpPairLog", method = RequestMethod.POST)
	public @ResponseBody WlpPairLog sureWlpPairLog(HttpServletRequest request,
			@RequestParam(required = true) String pairLogId) {
		HttpSession session = request.getSession();
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpPairLogService wlpPairLogService= (WlpPairLogService) ac1.getBean("wlpPairLogService");
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null || pairLogId.isEmpty()) {
			return null;
		}
		return wlpPairLogService.sureWlpPairLog(pairLogId);
	}

	/**
	 * 根据关键字查询我的历程--查询我的所有交易记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/getMyWlpPairLogsBySearch", method = RequestMethod.POST)
	public @ResponseBody List<WlpPairLog> getMyWlpPairLogsBySearch(@RequestParam(required = true) String keyword,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpPairLogService wlpPairLogService= (WlpPairLogService) ac1.getBean("wlpPairLogService");
		ArrayList<WlpPairLog> results = new ArrayList<WlpPairLog>();
		List<WlpPairLog> logs = wlpPairLogService.getWlpPairLogs(username, null);
		if (logs != null && logs.size() > 0) {
			for (WlpPairLog log : logs) {
				String state = "未完成";
				if ("1".equals(log.getStatus())) {
					state = "已确认";
				}
				log.setStatus(state);

				long money = log.getPairMoney();
				if ((log.getFromUser() != null && log.getFromUser().equals(username))) {
					money = 0 - money;
					log.setPairMoney(money);
				}
				log.setOrderPic(String.valueOf(money));
				if (keyword == null || keyword.isEmpty()) {
					results.add(log);
					continue;
				}
				String searchMoney = String.valueOf(money);
				if (searchMoney.contains(keyword) || state.contains(keyword)) {
					if (searchMoney.contains(keyword)) {
						searchMoney = searchMoney.replaceAll(keyword, "<span style='color:red'>" + keyword + "</span>");
						log.setOrderPic(searchMoney);
					}
					if (state.contains(keyword)) {
						log.setStatus(state.replaceAll(keyword, "<span style='color:red'>" + keyword + "</span>"));
					}

					results.add(log);
				}
			}
		}
		return results;
	}

	/**
	 * 根据关键字查询我的交易记录--查询我的所有交易记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/getMyAllWlpPairLogsBySearch", method = RequestMethod.POST)
	public @ResponseBody List<WlpPairLog> getMyAllWlpPairLogsBySearch(@RequestParam(required = true) String keyword,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpPairLog> results = new ArrayList<WlpPairLog>();
		String newMoney = null;
		String oldMoney = null;
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpPairLogService wlpPairLogService= (WlpPairLogService) ac1.getBean("wlpPairLogService");
		List<WlpPairLog> logs = wlpPairLogService.getWlpPairLogs(username, null);
		if (logs != null && logs.size() > 0) {
			for (WlpPairLog log : logs) {
				log.setEmail(username);
				String state = "未完成";
				if ("1".equals(log.getStatus())) {
					state = "已确认";
				}
				log.setStatus(state);

				long money = log.getPairMoney();
				if ((log.getFromUser() != null && log.getFromUser().equals(username))) {
					money = 0 - money;
					log.setPairMoney(money);
				}
				log.setOrderPic(String.valueOf(money));
				String searchMoney = String.valueOf(money);

				if (username.equals(log.getFromUser())) {
					newMoney = String.valueOf(log.getFromBalance());
					oldMoney = String.valueOf(log.getFromOldBalance());
				} else if (username.equals(log.getToUser())) {
					newMoney = String.valueOf(log.getToBalance());
					oldMoney = String.valueOf(log.getToOldBalance());
				}
				log.setRemark(oldMoney);
				log.setPayType(newMoney);
				if (keyword == null || keyword.isEmpty()) {
					results.add(log);
					continue;
				}

				if (searchMoney.contains(keyword) || state.contains(keyword) || oldMoney.contains(keyword)
						|| newMoney.contains(keyword)) {
					if (searchMoney.contains(keyword)) {
						searchMoney = searchMoney.replaceAll(keyword, "<span style='color:red'>" + keyword + "</span>");
						log.setOrderPic(searchMoney);
					}
					if (state.contains(keyword)) {
						log.setStatus(state.replaceAll(keyword, "<span style='color:red'>" + keyword + "</span>"));
					}
					if (newMoney.contains(keyword)) {
						newMoney = newMoney.replaceAll(keyword, "<span style='color:red'>" + keyword + "</span>");
						log.setPayType(newMoney);
					}
					if (oldMoney.contains(keyword)) {
						oldMoney = oldMoney.replaceAll(keyword, "<span style='color:red'>" + keyword + "</span>");
						log.setRemark(oldMoney);
					}
					results.add(log);
				}
			}
		}
		return results;
	}
}
