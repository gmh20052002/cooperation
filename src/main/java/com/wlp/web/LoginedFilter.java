package com.wlp.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.wlp.api.entity.WlpUser;

public class LoginedFilter implements Filter {

	public final PathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 需要登录
	 */
	private String whiteUrlStr;
	/**
	 * 不需要登录
	 */
	private String blankUrlStr;
	/**
	 * 需要二次登录
	 */
	private String orderUrlStr;

	public boolean isWhiteURL(String currentURL) {
		if (whiteUrlStr == null || "".equals(whiteUrlStr)) {
			return true;
		}
		String[] whiteListURLs = whiteUrlStr.split(";");
		for (String whiteURL : whiteListURLs) {
			if (pathMatcher.match(whiteURL, currentURL)) {
				return true;
			}
		}
		return false;
	}

	public boolean isBlackURL(String currentURL) {
		String[] blackListURLs = blankUrlStr.split(";");
		for (String blackURL : blackListURLs) {
			if (pathMatcher.match(blackURL, currentURL)) {
				return true;
			}
		}
		return false;
	}

	public boolean isOrderUrl(String currentURL) {
		String[] orderUrlStrs = orderUrlStr.split(";");
		for (String orderUrl : orderUrlStrs) {
			if (pathMatcher.match(orderUrl, currentURL)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String currentURL = ((HttpServletRequest) request).getRequestURI();
		if (isBlackURL(currentURL)) {// 不校验登录
			chain.doFilter(request, response);
			return;
		}

		if (isWhiteURL(currentURL)) {// 登录过滤
			doMyFilter(request, response, chain);
			return;
		}
	}

	public void doMyFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		WlpUser logined_user = (WlpUser) httpServletRequest.getSession().getAttribute("logined_user");
		String order_logined = (String) httpServletRequest.getSession().getAttribute("order_logined");
		if (logined_user != null && logined_user.getEmail() != null) {
			// 校验交易登录
			String currentURL = ((HttpServletRequest) request).getServletPath();
			if (isOrderUrl(currentURL)) {// request请求地址为交易相关页面
				if ("true".equals(order_logined)) {

				} else {
					redirectLoginPage(httpServletRequest, httpServletResponse, "dealPsd.html?hisUrl=" + currentURL);
				}
			}
			chain.doFilter(request, response);
		} else {
			redirectLoginPage(httpServletRequest, httpServletResponse, "index.html");
			return;
		}
	}

	public void redirectLoginPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			String loginPage) throws IOException, ServletException {
		String path = httpServletRequest.getContextPath();
		String redirectUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":"
				+ httpServletRequest.getServerPort() + path + "/" + loginPage;
		httpServletResponse.encodeRedirectURL(redirectUrl);
		System.out.println("login-server-redirectUrl----->" + redirectUrl);
		httpServletResponse.sendRedirect(redirectUrl); // 重定回目的地址
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		whiteUrlStr = config.getInitParameter("whiteUrlStr");
		blankUrlStr = config.getInitParameter("blankUrlStr");
		orderUrlStr = config.getInitParameter("orderUrlStr");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public String getWhiteUrlStr() {
		return whiteUrlStr;
	}

	public void setWhiteUrlStr(String whiteUrlStr) {
		this.whiteUrlStr = whiteUrlStr;
	}

	public String getBlankUrlStr() {
		return blankUrlStr;
	}

	public void setBlankUrlStr(String blankUrlStr) {
		this.blankUrlStr = blankUrlStr;
	}

	public String getOrderUrlStr() {
		return orderUrlStr;
	}

	public void setOrderUrlStr(String orderUrlStr) {
		this.orderUrlStr = orderUrlStr;
	}
}
