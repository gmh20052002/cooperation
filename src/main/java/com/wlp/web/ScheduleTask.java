package com.wlp.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.wlp.api.entity.WlpUser;
import com.wlp.api.entity.WlpWallet;
import com.wlp.api.service.WlpUserService;
import com.wlp.api.service.WlpWalletService;

public class ScheduleTask implements InitializingBean {

	Logger log = Logger.getLogger(getClass());

	@Autowired
	WlpUserService wlpUserService;
	@Autowired
	WlpWalletService wlpWalletService;

	public void run() {
		Calendar cal = Calendar.getInstance();
		// 每天定点执行
		cal.set(Calendar.HOUR_OF_DAY, 22);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Timer timer = new Timer();
		log.info("启动定时分红计算算法。。。。。。");
		timer.schedule(new TimerTask() {
			public void run() {
				// 执行的内容
				// 查询所有用户
				List<WlpUser> userlist = wlpUserService.getMyTeamUsers(null);
				log.info("开始计算分红====================================>" + System.currentTimeMillis());
				if (userlist != null) {
					for (WlpUser user : userlist) {
						String email = user.getEmail();
						Date activeTime = user.getActiveTime();
						String status = user.getStatus();
						if ("1".equals(status) && activeTime != null) {
							WlpWallet wlpWallet = wlpWalletService.getWlpWalletByEmail(email);
							if (wlpWallet != null) {
								Calendar activeCalendar = Calendar.getInstance();
								activeCalendar.setTime(activeTime);
								Calendar currentCalendar = Calendar.getInstance();
								currentCalendar.setTime(new Date());
								int startDay = activeCalendar.get(Calendar.DAY_OF_YEAR);
								int endDay = currentCalendar.get(Calendar.DAY_OF_YEAR);

								int lengthDay = (endDay - startDay);
								// 本金
								long capital = wlpWallet.getCapital();
								// 分红
								long bonus = wlpWallet.getBonus();

								int lv = 3;
								if (lengthDay > 0) {
									lv += lengthDay;
								}
								if (capital * 1.45 >= bonus + (capital * (lv / 100))) {// 分红总盈利不能超过145%
									log.debug("用户[" + email + "]本次分红[" + capital * (lv / 100) + "]元，分红时间[" + new Date()
											+ "]");
									wlpWallet.setBonus(bonus + (capital * (lv / 100)));
									wlpWalletService.updateWlpWallet(wlpWallet);
								}
							}
						}
					}
				}
				log.info("完成计算分红====================================>" + System.currentTimeMillis());
			}
		}, cal.getTime(), 24 * 60 * 60 * 1000);
	}

	public WlpUserService getWlpUserService() {
		return wlpUserService;
	}

	public void setWlpUserService(WlpUserService wlpUserService) {
		this.wlpUserService = wlpUserService;
	}

	public WlpWalletService getWlpWalletService() {
		return wlpWalletService;
	}

	public void setWlpWalletService(WlpWalletService wlpWalletService) {
		this.wlpWalletService = wlpWalletService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		run();
	}
}
