package com.wlp.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

	@Autowired
	WlpPairLogService wlpPairLogService;

	
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
		ArrayList<WlpPairLog> results = new ArrayList<WlpPairLog>();
		List<WlpPairLog> logs = wlpPairLogService.getWlpPairLogs(username, null);
		 ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext()) ;
		 WlpUserService wlpUserService=(WlpUserService) ac1.getBean("wlpUserService"); 
		if (logs != null && logs.size() > 0) {
			for (WlpPairLog log : logs) {
				log.setEmail(username);
				String recEmail=log.getToUser();
				if ("1".equals(log.getStatus())||username.equals(log.getToUser())||recEmail==null) {
					continue;
				}
				WlpUser rec_user =null;
				try{
				 rec_user =wlpUserService.getUserByEmail(recEmail);
				 recEmail=rec_user.getUserName();
				 log.setOrderPic(rec_user.getRemark());
				}catch(Exception e){
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
	 * 请求帮助
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/getHelp", method = RequestMethod.POST)
	public @ResponseBody List<WlpPairLog> getHelp(HttpServletRequest request,@RequestParam(required = true) String money,@RequestParam(required = true) String payway) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		WlpPairLog wlpPairLog=new WlpPairLog();
		wlpPairLog.setEmail(username);
		wlpPairLog.setToUser(username);
		wlpPairLog.setPayType(payway);
		wlpPairLog.setPairMoney(Long.parseLong(money));
		wlpPairLog.setStatus("0");
		wlpPairLog.setOrderTime(new Date());
		wlpPairLog.setExtrakType("dynamic");
		wlpPairLogService.addWlpPairLog(wlpPairLog);
	
		return null;
	}
	
	/**
	 * 完成交易
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/completeWlpPairLog", method = RequestMethod.POST)
	public @ResponseBody WlpPairLog completeWlpPairLog(HttpServletRequest request,@RequestParam(required = true) String pairLogId,@RequestParam(required = true) String pairPic) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null||pairLogId.isEmpty()) {
			return null;
		}
		return wlpPairLogService.completeWlpPairLog(pairLogId, pairPic);
	}
	
	/**
	 * 根据关键字查询我的历程--查询我的所有交易记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wlp/getMyWlpPairLogsBySearch", method = RequestMethod.POST)
	public @ResponseBody List<WlpPairLog> getMyWlpPairLogsBySearch(@RequestParam(required = true) String keyword,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
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
				String searchMoney=String.valueOf(money);
				if (searchMoney.contains(keyword) || state.contains(keyword) ) {
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
	public @ResponseBody List<WlpPairLog> getMyAllWlpPairLogsBySearch(@RequestParam(required = true) String keyword,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpPairLog> results = new ArrayList<WlpPairLog>();
		String newMoney=null;
		String oldMoney=null;
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
				String searchMoney=String.valueOf(money);
			
				if(username.equals(log.getFromUser())){
				newMoney=String.valueOf(log.getFromBalance());
			       oldMoney=String.valueOf(log.getFromOldBalance());
				}
				else if(username.equals(log.getToUser())){
					newMoney=String.valueOf(log.getToBalance());
					oldMoney=String.valueOf(log.getToOldBalance());
				}
				log.setRemark(oldMoney);
				log.setPayType(newMoney);
				if (keyword == null || keyword.isEmpty()) {
					results.add(log);
					continue;
				}
			
				if (searchMoney.contains(keyword) || state.contains(keyword)||oldMoney.contains(keyword) || newMoney.contains(keyword)  ) {
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
