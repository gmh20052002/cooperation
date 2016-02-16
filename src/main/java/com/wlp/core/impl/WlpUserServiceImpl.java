package com.wlp.core.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wlp.api.entity.CommonCst;
import com.wlp.api.entity.WlpActivecode;
import com.wlp.api.entity.WlpUser;
import com.wlp.api.service.WlpUserService;
import com.wlp.core.dao.WlpActivecodeMapper;
import com.wlp.core.dao.WlpUserMapper;

@Service
public class WlpUserServiceImpl implements WlpUserService {
	@Autowired
	private WlpUserMapper wlpUserMapper;
	@Autowired
	private WlpActivecodeMapper wlpActivecodeMapper;

	public WlpActivecodeMapper getWlpActivecodeMapper() {
		return wlpActivecodeMapper;
	}

	public void setWlpActivecodeMapper(WlpActivecodeMapper wlpActivecodeMapper) {
		this.wlpActivecodeMapper = wlpActivecodeMapper;
	}

	public WlpUserMapper getWlpUserMapper() {
		return wlpUserMapper;
	}

	public void setWlpUserMapper(WlpUserMapper wlpUserMapper) {
		this.wlpUserMapper = wlpUserMapper;
	}

	@Override
	public WlpUser commonLogin(String email, String password) {
		WlpUser user = getUserByEmail(email);
		if (user != null && password.equals(user.getLoginPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public WlpUser transLogin(String email, String transPassword) {
		WlpUser user = getUserByEmail(email);
		if (user != null && transPassword.equals(user.getTransPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public WlpUser regUser(WlpUser user) {
		user.setId(UUID.randomUUID().toString());
		wlpUserMapper.insertSelective(user);
		return user;
	}

	@Override
	public List<WlpUser> getMyTeamUsers(String email) {
		WlpUser condition = new WlpUser();
		condition.setRecEmail(email);
		return wlpUserMapper.selectByCondition(condition, null, null);
	}

	@Override
	public WlpUser getUserByEmail(String email) {
		WlpUser condition = new WlpUser();
		condition.setEmail(email);
		List<WlpUser> users = wlpUserMapper.selectByCondition(condition, null, null);
		if (users != null && !users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public WlpUser updateUser(WlpUser user) {
		int ret = wlpUserMapper.updateByPrimaryKeySelective(user);
		if (ret >= 1) {
			return user;
		}
		return null;
	}

	@Override
	public WlpUser deleteUser(String email) {
		WlpUser user = getUserByEmail(email);
		if (user != null) {
			wlpUserMapper.deleteByPrimaryKey(user.getId());
			return user;
		}
		return null;
	}

	@Override
	public WlpUser activeUser(String email, String activeCode) {
		WlpUser user = getUserByEmail(email);
		WlpActivecode condition = new WlpActivecode();
		condition.setEmail(email);
		condition.setCode(activeCode);
		List<WlpActivecode> codes = wlpActivecodeMapper.selectByCondition(condition, null, null);
		if (user != null && codes != null && !codes.isEmpty()) {
			user.setStatus(CommonCst.ACTIVE);
			WlpActivecode code = codes.get(0);
			code.setStatus(CommonCst.USED);
			//将激活码状态改为已使用
			wlpActivecodeMapper.updateByPrimaryKeySelective(code);
			//将用户状态改为已激活
			this.updateUser(user);
			return user;
		}
		return null;
	}

}
