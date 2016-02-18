package com.wlp.api.service;

import java.util.List;

import com.wlp.api.entity.WlpUser;
/**
 * 用户会员操作接口
 * @author 明华
 *
 */
public interface WlpUserService {
	/**
	 * 普通登录
	 * @param email
	 * @param password
	 * @return
	 */
	public WlpUser commonLogin(String email, String password);
	/**
	 * 交易登录
	 * @param email
	 * @param transPassword
	 * @return
	 */
	public WlpUser transLogin(String email, String transPassword);
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public WlpUser regUser(WlpUser user);
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public WlpUser updateUser(WlpUser user);
	/**
	 * 删除用户
	 * @param email
	 * @return
	 */
	public WlpUser deleteUser(String email);
	/**
	 * 获取用户的团队
	 * @param email
	 * @return
	 */
	public List<WlpUser> getMyTeamUsers(String email);
	/**
	 * 获取用户对象
	 * @param email
	 * @return
	 */
	public WlpUser getUserByEmail(String email);
	/**
	 * 激活用户交易权限
	 * @param email
	 * @param activeCode
	 * @return
	 */
	public WlpUser activeUser(String email, String activeCode);
}
