package com.wlp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wlp.api.entity.WlpWallet;
import com.wlp.api.service.WlpWalletService;

/**
 * 钱包管理
 * @author 明华
 *
 */
@Controller()
public class WalletController {
	private static final String USER_NAME = "USER_NAME";
	
	@Autowired
	WlpWalletService wlpWalletService;
	
	@RequestMapping(value = "/wlp/getMyWallet", method = RequestMethod.GET)
	public @ResponseBody WlpWallet getMyWallet(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		WlpWallet wallet = wlpWalletService.getWlpWalletByEmail(username);
		if (wallet != null) {
			return wallet;
		}
		return null;
	}
}
