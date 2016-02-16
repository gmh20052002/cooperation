package com.wlp.api.service;

import java.util.List;

import com.wlp.api.entity.WlpActivecode;

public interface WlpActivecodeService {
	/**
	 * 获取用户可用激活码
	 * @param email
	 * @return
	 */
	public List<WlpActivecode> getCanUseActivecodes(String email);
	/**
	 * 获取用户已用激活码
	 * @param email
	 * @return
	 */
	public List<WlpActivecode> getUsedActivecodes(String email);
	/**
	 * 获取用户分享的激活码
	 * @param email
	 * @return
	 */
	public List<WlpActivecode> getSharedActivecodes(String email);
	/**
	 * 分享激活码
	 * @param toEmail
	 * @return
	 */
	public WlpActivecode shareActivecode(String fromEmail, String toEmail);
}
