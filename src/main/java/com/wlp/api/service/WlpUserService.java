package com.wlp.api.service;

import java.util.List;

import com.wlp.api.entity.WlpUser;

public interface WlpUserService {
	public WlpUser commonLogin(String email, String password);
	public WlpUser transLogin(String email, String transPassword);
	public WlpUser regUser(WlpUser user);
	public WlpUser updateUser(WlpUser user);
	public WlpUser deleteUser(String email);
	public List<WlpUser> getMyTeamUsers(String email);
	public WlpUser getUserByEmail(String email);
}
