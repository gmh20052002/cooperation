package com.wlp.api.service;

import java.util.List;

public interface WlpActivecodeService {
	/**
	 * 获取用户可用激活码
	 * @param email
	 * @return
	 */
	public List<String> getCanUseActivecodes(String email);
	/**
	 * 获取用户已用激活码
	 * @param email
	 * @return
	 */
	public List<String> getUsedActivecodes(String email);
	/**
	 * 获取用户分享的激活码
	 * @param email
	 * @return
	 */
	public List<String> getSharedActivecodes(String email);
	/**
	 * 分享激活码
	 * @param toEmail
	 * @return
	 */
	public String shareActivecode(String toEmail);
}
