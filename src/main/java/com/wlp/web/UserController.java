package com.wlp.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
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

	private int imgWidth = 0;

	private int imgHeight = 0;

	private int codeCount = 0;

	private int x = 0;

	private int codeY;

	private Font mFont = new Font("Times New Roman", Font.PLAIN + Font.ITALIC, 19);

	private static final String USER_NAME = "USER_NAME";

	public WlpUserService getWlpUserService() {
		return wlpUserService;
	}

	public void setWlpUserService(WlpUserService wlpUserService) {
		this.wlpUserService = wlpUserService;
	}

	@RequestMapping(value = "/wlp/login", method = RequestMethod.GET)
	public @ResponseBody WlpUser login(@RequestParam(required = true) String userName,
			@RequestParam(required = true) String password, @RequestParam(required = true) String validateCode,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			WlpUser user = new WlpUser();
			user.setId("123456789");
			String validateC = (String) session.getAttribute("validateCode");
			if (validateCode == null || "".equals(validateCode) || !validateCode.equalsIgnoreCase(validateC)) {
				return user;
			}
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
			@RequestParam(required = true) String introemail, HttpServletRequest request) {
		Boolean flag = false;
		try {
			WlpUser user = new WlpUser();
			user.setUserName(username);
			user.setMobilePhone(telphone);
			user.setEmail(email);
			user.setLoginPassword(password);
			user.setTransPassword(paypassword);
			user.setRecEmail(introemail);
			int max = 40;
			int min = 1;
			Random random = new Random();
			int s = random.nextInt(max) % (max - min + 1) + min;
			user.setRemark(String.valueOf(s));
			ApplicationContext ac1 = WebApplicationContextUtils
					.getRequiredWebApplicationContext(request.getSession().getServletContext());
			WlpUserService wlpUserService = (WlpUserService) ac1.getBean("wlpUserService");
			WlpUser reuser = wlpUserService.getUserByEmail(email);
			if (reuser != null) {
				return false;
			}
			if (wlpUserService.regUser(user) != null) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@RequestMapping(value = "/wlp/checkUsernameIfExist", method = RequestMethod.POST)
	public @ResponseBody Boolean checkUsernameIfExist(@RequestParam(required = true) String username, HttpServletRequest request) {
		Boolean flag = true;
		try {
			if(username==null||username.isEmpty()){
				return false;
			}
			ApplicationContext ac1 = WebApplicationContextUtils
					.getRequiredWebApplicationContext(request.getSession().getServletContext());
			WlpUserService wlpUserService = (WlpUserService) ac1.getBean("wlpUserService");
			WlpUser reuser = wlpUserService.getUserByEmail(username);
			if (reuser != null) {
				return false;
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
			@RequestParam(required = true) String bankusername, @RequestParam(required = true) String bankacct,
			HttpServletRequest request) {
		Boolean flag = false;
		HttpSession session = request.getSession();
		String cname = (String) session.getAttribute(USER_NAME);
		if (cname == null) {
			return false;
		}
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpUserService wlpUserService = (WlpUserService) ac1.getBean("wlpUserService");
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
			@RequestParam(required = true) String newpsd, HttpServletRequest request) {
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
	public @ResponseBody Boolean updateUserImage(@RequestParam(required = true) String imageId,
			HttpServletRequest request) {
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
			@RequestParam(required = true) String newpsd, HttpServletRequest request) {
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
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpUserService wlpUserService = (WlpUserService) ac1.getBean("wlpUserService");
		WlpUser user = wlpUserService.getUserByEmail(username);
		if (user != null) {
			WlpUser user2 = wlpUserService.getUserByEmail(user.getRecEmail());
			if (user2 != null && user2.getUserName() != null) {
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

		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpUserService wlpUserService = (WlpUserService) ac1.getBean("wlpUserService");
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
	public @ResponseBody ArrayList<WlpUser> getMyTeamUsersBySearch(@RequestParam(required = true) String keyword,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute(USER_NAME);
		if (userName == null) {
			return null;
		}
		ApplicationContext ac1 = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession().getServletContext());
		WlpUserService wlpUserService = (WlpUserService) ac1.getBean("wlpUserService");
		ArrayList<WlpUser> results = new ArrayList<WlpUser>();
		ArrayList<WlpUser> codes = (ArrayList<WlpUser>) wlpUserService.getMyTeamUsers(userName);

		if (codes != null && codes.size() > 0) {
			for (int i = 0; i < codes.size(); i++) {
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

	@RequestMapping(value = "/servlet/AuthImageServlet", method = RequestMethod.GET)
	public @ResponseBody void getUserValidateCode(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 宽度
		String strWidth = "114";
		// 高度
		String strHeight = "35";
		// 字符个数
		String strCodeCount = "4";

		// 将配置的信息转换成数值
		try {
			if (strWidth != null && strWidth.length() != 0) {
				imgWidth = Integer.parseInt(strWidth);
			}
			if (strHeight != null && strHeight.length() != 0) {
				imgHeight = Integer.parseInt(strHeight);
			}
			if (strCodeCount != null && strCodeCount.length() != 0) {
				codeCount = Integer.parseInt(strCodeCount);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		x = imgWidth / (codeCount + 1);
		codeY = imgHeight - 11;
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession();

		// 在内存中创建图象
		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics2D g = image.createGraphics();

		// 生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(1, 1, imgWidth - 1, imgHeight - 1);
		g.setColor(new Color(102, 102, 102));
		g.drawRect(0, 0, imgWidth - 1, imgHeight - 1);
		g.setFont(mFont);

		g.setColor(getRandColor(160, 200));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 160; i++) {
			int x = random.nextInt(imgWidth);
			int y = random.nextInt(imgHeight);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(4位数字)
		String sRand = "";
		for (int i = 0; i < codeCount; i++) {
			int wordType = random.nextInt(3);
			char retWord = 0;
			switch (wordType) {
			case 0:
				retWord = this.getSingleNumberChar();
				break;
			case 1:
				retWord = this.getLowerOrUpperChar(0);
				break;
			case 2:
				retWord = this.getLowerOrUpperChar(1);
				break;
			}
			sRand += String.valueOf(retWord);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(String.valueOf(retWord), (i) * x, codeY);

		}
		// 将认证码存入SESSION
		session.setAttribute("validateCode", sRand);
		// 图象生效
		g.dispose();
		ServletOutputStream responseOutputStream = response.getOutputStream();
		// 输出图象到页面
		ImageIO.write(image, "JPEG", responseOutputStream);

		// 以下关闭输入流！
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private char getSingleNumberChar() {
		Random random = new Random();
		int numberResult = random.nextInt(10);
		int ret = numberResult + 48;
		return (char) ret;
	}

	private char getLowerOrUpperChar(int upper) {
		Random random = new Random();
		int numberResult = random.nextInt(26);
		int ret = 0;
		if (upper == 0) {// 小写
			ret = numberResult + 97;
		} else if (upper == 1) {// 大写
			ret = numberResult + 65;
		}
		return (char) ret;
	}
}
