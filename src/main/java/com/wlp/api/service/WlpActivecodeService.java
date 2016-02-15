package com.wlp.api.service;

import java.util.List;

public interface WlpActivecodeService {
	public List<String> getCanUseActivecodes(String email);
	public List<String> getUsedActivecodes(String email);
	public List<String> getShareddActivecodes(String email);
	public String shareActivecode(String toEmail);
}
